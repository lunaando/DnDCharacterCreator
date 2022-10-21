import java.util.*
import kotlin.collections.ArrayList

class Dashboard {

    companion object {

        private val scan = Scanner(System.`in`)

        fun controller(): String {
            var check = true
            var choice = 0
            val menuOption = arrayOf("Name", "Gender", "Race", "Background & Class", "Alignment", "Stats", "Language & Proficiency", "Quit")
            while (check) {
                try {
                    choice = scan.nextInt() - 1
                    check = false
                } catch (e: InputMismatchException) {
                    choice = 7
                    check = false
                }
            }
            if (choice < 0 || choice > 8) {
                choice = 7
            }
            return menuOption[choice]
        }

        fun menu(character: CharacterBasic, health: Health, abilityScores: Stats, abilityModifiers: Stats, savingThrows: Stats, equipment: Equipment, languages: Languages, proficiencies: Proficiency, skills: Skills, characterClass: CharacterClass, features: Features, langProfFlag: Boolean) {
            basicInformation(character, abilityScores, langProfFlag)
            savingThrows(character, proficiencies)
            healthInformation(health, character, abilityModifiers, equipment)
            abilityScores(abilityScores, abilityModifiers)
            commaHelper("\nRace Features: ", features.race)
            classFeatures(character, features)
            commaHelper("\nLanguages: ", languages.list)
            commaHelper("\nEquipment: ", equipment.bag)
            commaHelper("\nTools Proficiency: ", proficiencies.tools)
            commaHelper("\nArms Proficiency: ", proficiencies.arms)
            commaHelper("\nSkills: ", proficiencies.skills)
            skills(savingThrows, skills, abilityScores)
            print("\nEnter selection: ")
        }

        private fun basicInformation(character: CharacterBasic, abilityScores: Stats, langProfFlag: Boolean) {
            val dashboardItems = arrayOf(
                    "1.  Name:          ${character.name}\n",
                    "2.  Gender:        ${character.gender}\n",
                    "3.  Race:          ${character.subRace}${character.draconic} ${character.race}\n",
                    "4.  Background:    ${character.background}\n",
                    "    Class:         ${character.subClass} ${character.cClass}\n",
                    "5.  Alignment:     ${character.alignment}\n",
                    "6.  Stats          \n")
            val dashboardSets = arrayOf(character.nameSet, character.genderSet, character.raceSet, character.backgroundSet, character.cClassSet, character.alignmentSet, abilityScores.set)
            for (i in dashboardItems.indices) {
                if (!dashboardSets[i]) {
                    print("\u001B[31m${dashboardItems[i]}\u001B[39m")
                } else if (dashboardSets[i]) {
                    print("\u001B[32m${dashboardItems[i]}\u001B[39m")
                }
            }
            if (langProfFlag) {
                print("\u001B[31m7.  Language & Proficiency\u001B[39m")
            } else if (!langProfFlag) {
                print("\u001B[32m7.  Language & Proficiency\u001B[39m")
            }
            print("\n\u001B[31m8.  Quit\u001B[39m")
        }

        private fun healthInformation(health: Health, character: CharacterBasic, abilityModifiers: Stats, equipment: Equipment) {
            var hillDwarf = 0
            if (character.subRace == "Hill" && character.race == "Dwarf") {
                hillDwarf = (1 * character.level)
            }
            health.hitPoints = health.hitPointsConstant + abilityModifiers.constitution + hillDwarf
            health.armorClass = Health.setArmorClass(abilityModifiers, equipment)
            health.initiative = abilityModifiers.dexterity
            val valueList = arrayOf("HP: ", "AC: ", "Hit Dice: ", "Initiative: ", "Speed: ")
            val blankerList = arrayOf(health.hitPoints.toString(), health.armorClass.toString(), health.hitDice, health.initiative.toString(), character.speed.toString())
            val boolList = arrayOf(health.hitPointsSet, health.armorClassSet, health.hitDiceSet, health.initiativeSet, character.raceSet)
            for (i in boolList.indices) {
                if (!boolList[i]) {
                    blankerList[i] = ""
                }
            }
            for (i in valueList.indices) {
                if (blankerList[i] == "") {
                    print("\u001B[31m${valueList[i]}${blankerList[i]}\u001B[39m ")
                } else {
                    print("\u001B[32m${valueList[i]}${blankerList[i]}\u001B[39m ")
                }
            }
        }

        private fun abilityScores(abilityScores: Stats, abilityModifiers: Stats) {
            var display = arrayOf("-", "-", "-", "-", "-", "-")
            if (!abilityScores.set) {
                print("\n\u001B[31mCon ${abilityScores.constitution}(${display[0]})  Str ${abilityScores.strength}(${display[1]})  Dex ${abilityScores.dexterity}(${display[2]})  Int ${abilityScores.intelligence}(${display[3]})  Wis ${abilityScores.wisdom}(${display[4]})  Cha ${abilityScores.charisma}(${display[5]})\u001B[39m")
            } else {
                print("\n\u001B[32mCon ${abilityScores.constitution}(${display[0]})  Str ${abilityScores.strength}(${display[1]})  Dex ${abilityScores.dexterity}(${display[2]})  Int ${abilityScores.intelligence}(${display[3]})  Wis ${abilityScores.wisdom}(${display[4]})  Cha ${abilityScores.charisma}(${display[5]})\u001B[39m")
                val modDisplay = arrayOf(abilityModifiers.constitution.toString(), abilityModifiers.strength.toString(), abilityModifiers.dexterity.toString(), abilityModifiers.intelligence.toString(), abilityModifiers.wisdom.toString(), abilityModifiers.charisma.toString())
                display = modDisplay
            }
        }

        private fun savingThrows(character: CharacterBasic, proficiencies: Proficiency) {
            if (!character.cClassSet) {
                print("\n\u001B[31mSaving Throws: ${proficiencies.savingThrows[0]} ${proficiencies.savingThrows[1]}\u001B[39m ")
            } else {
                print("\n\u001B[32mSaving Throws: ${proficiencies.savingThrows[0]} ${proficiencies.savingThrows[1]}\u001B[39m ")
            }
        }

        private fun skills(savingThrows: Stats, skills: Skills, abilityScores: Stats) {
            val skillList = arrayOf(savingThrows.constitution, savingThrows.strength, savingThrows.dexterity, savingThrows.intelligence, savingThrows.wisdom, savingThrows.charisma,
                    skills.athletics, skills.acrobatics, skills.sleightOfHand, skills.stealth, skills.arcana, skills.history, skills.investigation, skills.nature, skills.religion, skills.animalHandling, skills.insight, skills.medicine, skills.perception, skills.survival, skills.deception, skills.intimidation, skills.performance, skills.persuasion)
            val itr = skillList.iterator()
            val skillListDashArray: ArrayList<String> = ArrayList()
            while (itr.hasNext()) {
                skillListDashArray.add(itr.next().toString())
            }
            val skillListDash: Array<String> = skillListDashArray.toTypedArray()
            if (!abilityScores.set) {
                for (i in skillList.indices) {
                    skillListDash[i] = "-"
                }
            } else {
                for (i in skillList.indices) {
                    skillListDash[i] = skillList[i].toString()
                }
                for (i in skillListDash.indices) {
                    if (!skillListDash[i].contains("-")) {
                        skillListDash[i] = " ${skillListDash[i]}"
                    }
                }
            }
            print("\n" +
                    "        Constitution:    ${skillListDash[0]}    Athletics(Str):         ${skillListDash[6]}     Investigation(Int):     ${skillListDash[12]}     Perception(Wis):    ${skillListDash[18]}\n" +
                    "        Strength:        ${skillListDash[1]}    Acrobatics(Dex):        ${skillListDash[7]}     Nature(Int):            ${skillListDash[13]}     Survival(Wis):      ${skillListDash[19]}\n" +
                    "        Dexterity:       ${skillListDash[2]}    Sleight of Hand(Dex):   ${skillListDash[8]}     Religion(Int):          ${skillListDash[14]}     Deception(Cha):     ${skillListDash[20]}\n" +
                    "        Intelligence:    ${skillListDash[3]}    Stealth(Dex):           ${skillListDash[9]}     Animal Handling(Wis):   ${skillListDash[15]}     Intimidation(Cha):  ${skillListDash[21]}\n" +
                    "        Wisdom:          ${skillListDash[4]}    Arcana(Int):            ${skillListDash[10]}     Insight(Wis):           ${skillListDash[16]}     Performance(Cha):   ${skillListDash[22]}\n" +
                    "        Charisma:        ${skillListDash[5]}    History(Int)            ${skillListDash[11]}     Medicine(Wis):          ${skillListDash[17]}     Persuasion(Cha):    ${skillListDash[23]}\n")
        }

        private fun classFeatures(character: CharacterBasic, features: Features) {
            commaHelper("\nClass Features: ", features.cClass)
        }

        private fun commaHelper(prompt: String, list: ArrayList<String>) {
            print(prompt)
            list.remove("")
            for (item in list) {
                if (item != list.last()) {
                    print("$item, ")
                } else {
                    print("$item ")
                }
            }
        }
    }
}