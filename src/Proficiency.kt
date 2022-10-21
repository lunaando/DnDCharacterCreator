import kotlin.properties.Delegates

class Proficiency(proficiencyBonus: Int, arms: ArrayList<String>, skills: ArrayList<String>, tools: ArrayList<String>, savingThrows: ArrayList<String>) {
    var proficiencyBonus by Delegates.notNull<Int>()
    var arms: ArrayList<String>
    var raceArms: ArrayList<String> = ArrayList()
    var classArms: ArrayList<String> = ArrayList()
    var skills: ArrayList<String>
    var raceSkills: ArrayList<String> = ArrayList()
    var classSkills: ArrayList<String> = ArrayList()
    var tools: ArrayList<String>
    var raceTools: ArrayList<String> = ArrayList()
    var classTools: ArrayList<String> = ArrayList()
    var savingThrows: ArrayList<String>
    var options by Delegates.notNull<Int>()

    init {
        this.proficiencyBonus = proficiencyBonus
        this.arms = arms
        this.skills = skills
        this.tools = tools
        this.savingThrows = savingThrows
    }

    class Configuration {
        companion object {
            fun listHelper(main: ArrayList<String>, race: ArrayList<String>, cClass: ArrayList<String>): ArrayList<String> {
                for (item in race) {
                    main.add(item)
                }
                for (item in cClass) {
                    main.add(item)
                }
                return main
            }

            fun selectSkills(character: CharacterBasic, characterClass: CharacterClass): ArrayList<String> {
                var options = 2
                val knownProficiencies: ArrayList<String> = ArrayList()

                val raceSkill: String = when (character.race) {
                    "Elf" -> "Perception"
                    "Half-Orc" -> "Intimidation"
                    else -> ""
                }
                knownProficiencies.add(raceSkill)
                if (character.cClass == "Rogue") {
                    options += 2
                }
                val classOptions: ArrayList<String> = characterClass.classSkillProficiencies.toList() as ArrayList<String>
                val allProficiencies: ArrayList<String> = arrayListOf("Athletics", "Acrobatics", "SleightOfHand", "Stealth", "Arcana", "History", "Investigation", "Nature", "Religion", "AnimalHandling", "Insight", "Medicine", "Perception", "Survival", "Deception", "Intimidation", "Performance", "Persuasion")

                if (classOptions.contains(raceSkill)) {
                    classOptions.remove(raceSkill)
                }
                var acolytePrompt = "The 'Acolyte' background isn't enabled, which would enable proficiency in Insight and Religion.\n"
                if (character.background == "Acolyte") {
                    knownProficiencies.add("Insight")
                    knownProficiencies.add("Religion")
                    if (classOptions.contains("Insight")) {
                        classOptions.remove("Insight")
                    }
                    if (classOptions.contains("Religion")) {
                        classOptions.remove("Religion")
                    }
                    var proficiencyOptions = classOptions.toTypedArray()
                    acolytePrompt = "The 'Acolyte' background grants proficiency in Insight and Religion.\n"
                    for (i in 0 until options) {
                        knownProficiencies.add(Builder.itemToList("$acolytePrompt The ${character.cClass} can choose proficiency in $options of the following skills:\n\n" +
                                "Selection ${i + 1}:\n", "", proficiencyOptions, proficiencyOptions))
                        proficiencyOptions = proficiencyListUpdate(knownProficiencies, proficiencyOptions)
                    }
                    options = 0
                } else {
                    for (i in 0 until options) {
                        knownProficiencies.add(Builder.itemToList("$acolytePrompt The ${character.cClass} can choose proficiency in $options of the following skills:\n\n" +
                                "Selection ${i + 1}:\n", "", characterClass.classSkillProficiencies, characterClass.classSkillProficiencies))
                        characterClass.classSkillProficiencies = proficiencyListUpdate(knownProficiencies, characterClass.classSkillProficiencies)
                    }
                    options = 0
                }
                if (character.race == "Half-Elf") {
                    options += 2
                }
                if (options > 0) {
                    var proficiencyOptions = allProficiencies.toTypedArray()
                    proficiencyOptions = proficiencyListUpdate(knownProficiencies, proficiencyOptions)
                    for (i in 0 until options) {
                        knownProficiencies.add(Builder.itemToList("Half-Elves gain 2 proficiencies:\n\n" +
                                "Selection ${i + 1}:\n", "", proficiencyOptions, proficiencyOptions))
                        proficiencyOptions = proficiencyListUpdate(knownProficiencies, proficiencyOptions)
                    }
                }
                return knownProficiencies
            }

            fun proficiencyListUpdate(knownProficiencies: ArrayList<String>, proficiencyList: Array<String>): Array<String> {
                val bufferList: ArrayList<String> = ArrayList()

                for (skill in proficiencyList) {
                    if (!knownProficiencies.contains(skill)) {
                        bufferList.add(skill)
                    }
                }
                return bufferList.toTypedArray()
            }
        }
    }
}