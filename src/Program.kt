import java.util.*
import kotlin.system.exitProcess

var displayMenu = true
var langProfFlag = true
var character = CharacterBasic()
var health = Health()
var stats = Stats()
var racialBonus = Stats()
var abilityScores = Stats()
var abilityModifiers = Stats()
var savingThrows = Stats()
var languages = Languages()
var skills = Skills()
var equipment = Equipment(bag = ArrayList(), raceBag = ArrayList(), classBag = ArrayList())
var features = Features(race = arrayListOf(""), cClass = arrayListOf(""), background = arrayListOf(""))
var proficiencies = Proficiency(2, arms = ArrayList(), skills = ArrayList(), tools = ArrayList(), savingThrows = arrayListOf("", ""))
var race = CharacterRace("", "", "", 0, 0, "", racialBonus, features = ArrayList(), raceArms = ArrayList(), raceSkills = ArrayList(), raceTools = ArrayList(), cantrips = ArrayList())
var characterClass = CharacterClass()
var spellCasting = SpellBook()

private var scan = Scanner(System.`in`)

fun main(args: Array<String>) {
    Program.startUp()
    while (displayMenu) {
        displayMenu = mainMenu()
    }
}

fun mainMenu(): Boolean {
    refreshValues()
    Dashboard.menu(character, health, abilityScores, abilityModifiers, savingThrows, equipment, languages, proficiencies, skills, characterClass, features,  langProfFlag)
    when (Dashboard.controller()) {
        "Name" -> {
            print("Name your character: ")
            character.name = scan.nextLine()
            character.nameSet = true
            return true
        }
        "Gender" -> {
            print("")
            character.gender = ""
            character.gender = character.setGender()
            character.genderSet = true
            return true
        }
        "Race" -> {
            character.race = ""
            raceListsReset()
            while (character.race == "") {
                character.race = race.setRace()
            }
            racialBonus = CharacterRace.conditionals(character, race, racialBonus, equipment, languages, proficiencies, features)
            languages.list = arrayListOf("Common", languages.native)
            equipment.raceBag.clear()
            proficiencies.raceTools.clear()
            proficiencies.raceArms.clear()
            character.raceSet = true
            langProfFlag = true
            return true
        }
        "Background & Class" -> {
            classListsReset()
            character.background = ""
            character.cClass = ""
            val backgroundPrevious = character.background
            character.background = characterClass.setBackground()
            if (character.background != backgroundPrevious) {
                languages.list = arrayListOf()
            }
            character.cClass = characterClass.setClass()
            characterClass = CharacterClass.controller(character.cClass)!!
            CharacterClass.conditionals(character, health, abilityModifiers, equipment, languages, proficiencies, characterClass, features, spellCasting)
            equipment.classBag.clear()
            languages.background.clear()
            proficiencies.classTools.clear()
            proficiencies.classArms.clear()
            proficiencies.classSkills.clear()
            character.backgroundSet = true
            character.cClassSet = true
            langProfFlag = true
            return true
        }
        "Alignment" -> {
            character.alignment = ""
            character.alignment = character.setAlignment()
            character.alignmentSet = true
            return true
        }
        "Stats" -> {
            stats = Stats.menu(character, stats, racialBonus)
            stats.set = true
            return true
        }
        "Language & Proficiency" -> {
            if (character.race == "" || character.cClass == "") {
                print("\u001B[31mThis isn't available yet, select a race and class first.\n\u001B[39m" +
                        "Press Enter to return to the main menu.")
                scan.nextLine()
                return true
            } else {
                langProfFlag = false
                languages.list = languages.selectLanguages(languages.native, character.race, character.background)
                proficiencies.skills = Proficiency.Configuration.selectSkills(character, characterClass)
            }
            return true
        }
        "Quit" -> {
            print("\u001B[31mType \"quit\" to confirm quit. Press enter to cancel quit. \u001B[39m")
            if (scan.nextLine().lowercase(Locale.getDefault()) == "quit") {
                exitProcess(0)
            } else {
                return true
            }
        }
        "Null" -> return true
        "Exit" -> return false
    }
    return false
}

fun refreshValues() {
    equipment.bag = Proficiency.Configuration.listHelper(equipment.bag, equipment.raceBag, equipment.classBag)
    equipment.bag = Equipment.duplicateItemStacker(equipment.bag)
    abilityScores = Stats.abilityScoreAssigner(stats, racialBonus)
    abilityModifiers = Stats.abilityModifierAssigner(abilityScores)

    if (stats.set) {
        abilityScores.set = true
        abilityModifiers.set = true
        health.initiativeSet = true
    }
    if (health.hitPointsConstantSet && abilityModifiers.set && character.raceSet) {
        health.hitPointsSet = true
    }
    if (character.cClassSet) {
        health.hitPointsConstantSet = true
        health.hitDiceSet = true
        if (abilityModifiers.set) {
            health.armorClassSet = true
        }
    }

    savingThrows = Skills.savingThrowAssigner(savingThrows, abilityModifiers)

    if (proficiencies.savingThrows.contains("Constitution")) {
        savingThrows.constitution += proficiencies.proficiencyBonus
    }
    if (proficiencies.savingThrows.contains("Strength")) {
        savingThrows.strength += proficiencies.proficiencyBonus
    }
    if (proficiencies.savingThrows.contains("Dexterity")) {
        savingThrows.dexterity += proficiencies.proficiencyBonus
    }
    if (proficiencies.savingThrows.contains("Intelligence")) {
        savingThrows.intelligence += proficiencies.proficiencyBonus
    }
    if (proficiencies.savingThrows.contains("Wisdom")) {
        savingThrows.wisdom += proficiencies.proficiencyBonus
    }
    if (proficiencies.savingThrows.contains("Charisma")) {
        savingThrows.charisma += proficiencies.proficiencyBonus
    }
    val skillsBuilder = arrayOfNulls<IntArray>(5)
    skillsBuilder[0] = intArrayOf(abilityModifiers.strength, skills.athletics)
    skillsBuilder[1] = intArrayOf(abilityModifiers.dexterity, skills.acrobatics, skills.sleightOfHand, skills.stealth)
    skillsBuilder[2] = intArrayOf(abilityModifiers.intelligence, skills.arcana, skills.history, skills.investigation, skills.nature, skills.religion)
    skillsBuilder[3] = intArrayOf(abilityModifiers.wisdom, skills.animalHandling, skills.insight, skills.medicine, skills.perception, skills.survival)
    skillsBuilder[4] = intArrayOf(abilityModifiers.charisma, skills.deception, skills.intimidation, skills.performance, skills.persuasion)
    for (item in skillsBuilder) {
        for (i in 1 until item?.size!!) {
            item[i] = item[0]
        }
    }

    skills.athletics = skillsBuilder[0]!![0]
    if (proficiencies.skills.contains("athletics")) {
        skills.athletics += proficiencies.proficiencyBonus
    }
    skills.acrobatics = skillsBuilder[1]!![0]
    if (proficiencies.skills.contains("acrobatics")) {
        skills.acrobatics += proficiencies.proficiencyBonus
    }
    skills.sleightOfHand = skillsBuilder[1]!![1]
    if (proficiencies.skills.contains("slightOfHand")) {
        skills.sleightOfHand += proficiencies.proficiencyBonus
    }
    skills.stealth = skillsBuilder[1]!![2]
    if (proficiencies.skills.contains("stealth")) {
        skills.stealth += proficiencies.proficiencyBonus
    }
    skills.arcana = skillsBuilder[2]!![0]
    if (proficiencies.skills.contains("arcana")) {
        skills.arcana += proficiencies.proficiencyBonus
    }
    skills.history = skillsBuilder[2]!![1]
    if (proficiencies.skills.contains("history")) {
        skills.history += proficiencies.proficiencyBonus
    }
    skills.investigation = skillsBuilder[2]!![2]
    if (proficiencies.skills.contains("investigation")) {
        skills.investigation += proficiencies.proficiencyBonus
    }
    skills.nature = skillsBuilder[2]!![3]
    if (proficiencies.skills.contains("nature")) {
        skills.nature += proficiencies.proficiencyBonus
    }
    skills.religion = skillsBuilder[2]!![4]
    if (proficiencies.skills.contains("religion")) {
        skills.religion += proficiencies.proficiencyBonus
    }
    skills.animalHandling = skillsBuilder[3]!![0]
    if (proficiencies.skills.contains("animalHandling")) {
        skills.animalHandling += proficiencies.proficiencyBonus
    }
    skills.insight = skillsBuilder[3]!![1]
    if (proficiencies.skills.contains("insight")) {
        skills.insight += proficiencies.proficiencyBonus
    }
    skills.medicine = skillsBuilder[3]!![2]
    if (proficiencies.skills.contains("medicine")) {
        skills.medicine += proficiencies.proficiencyBonus
    }
    skills.perception = skillsBuilder[3]!![3]
    if (proficiencies.skills.contains("perception")) {
        skills.perception += proficiencies.proficiencyBonus
    }
    skills.survival = skillsBuilder[3]!![4]
    if (proficiencies.skills.contains("survival")) {
        skills.survival += proficiencies.proficiencyBonus
    }
    skills.deception = skillsBuilder[4]!![0]
    if (proficiencies.skills.contains("deception")) {
        skills.deception += proficiencies.proficiencyBonus
    }
    skills.intimidation = skillsBuilder[4]!![1]
    if (proficiencies.skills.contains("intimidation")) {
        skills.intimidation += proficiencies.proficiencyBonus
    }
    skills.performance = skillsBuilder[4]!![2]
    if (proficiencies.skills.contains("performance")) {
        skills.performance += proficiencies.proficiencyBonus
    }
    skills.persuasion = skillsBuilder[4]!![3]
    if (proficiencies.skills.contains("persuasion")) {
        skills.persuasion += proficiencies.proficiencyBonus
    }
}

fun classListsReset() {
    equipment.bag.clear()
    equipment.classBag.clear()
    languages.background.clear()
    proficiencies.tools.clear()
    proficiencies.classTools.clear()
    proficiencies.arms.clear()
    proficiencies.classArms.clear()
    proficiencies.skills.clear()
    proficiencies.classSkills.clear()
}

fun raceListsReset() {
    equipment.raceBag.clear()
    languages.list = arrayListOf("Common")
    languages.native = ""
    proficiencies.tools.clear()
    proficiencies.raceTools.clear()
    proficiencies.arms.clear()
    proficiencies.raceArms.clear()
    proficiencies.skills.clear()
    proficiencies.raceSkills.clear()
}

class Program {
    companion object {
        fun startUp() {
            // The first line says "Dungeons and Dragons character creator" but fancy colored
            print("\u001B[38;5;93mD\u001B[38;5;99mu\u001B[38;5;105mn\u001B[38;5;111mg\u001B[38;5;117me\u001B[38;5;123mo\u001B[38;5;159mn\u001B[38;5;153ms \u001B[38;5;147ma\u001B[38;5;141mn\u001B[38;5;135md \u001B[38;5;129mD\u001B[38;5;128mr\u001B[38;5;134ma\u001B[38;5;140mg\u001B[38;5;146mo\u001B[38;5;152mn\u001B[38;5;158ms \u001B[38;5;122mc\u001B[38;5;116mh\u001B[38;5;110ma\u001B[38;5;104mr\u001B[38;5;98ma\u001B[38;5;92mc\u001B[38;5;91mt\u001B[38;5;97me\u001B[38;5;103mr \u001B[38;5;109mc\u001B[38;5;115mr\u001B[38;5;121me\u001B[38;5;157ma\u001B[38;5;151mt\u001B[38;5;145mo\u001B[38;5;139mr\n" +
                    "\u001B[1;4;34mTutorial:\n" +
                    "\u001B[0;34mNumbered selections will appear like this:\n" +
                    "1. Option 2. Another Option\n" +
                    "So if you want to select 'Another Option' type 2 and press enter\n" +
                    "The main menu is fairly big, be warned. Selectable options are at the top.\n" +
                    "Every time you finish entering data, the main menu will reprint with updated values.\n" +
                    "\u001B[1;39mPress enter to continue\u001B[0;39m")
            scan.nextLine()
        }
    }
}