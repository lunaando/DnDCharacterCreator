class CharacterClass(subClass: String = "", hitPointsConstant: Int = 0, hitDice: String = "", classArms: ArrayList<String> = ArrayList(), classTools: ArrayList<String> = ArrayList(), savingThrows: ArrayList<String> = ArrayList(), classSkills: Array<String>? = null, features: ArrayList<String> = ArrayList()) {
    var set = false
    private var subClass: String
    private var hitPointsConstant: Int
    private var hitDice: String
    private var classArmsProficiencies: ArrayList<String>
    private var classToolsProficiencies: ArrayList<String>
    private var savingThrows: ArrayList<String>
    lateinit var classSkillProficiencies: Array<String>
    lateinit var skills: ArrayList<String>
    private var features: ArrayList<String>

    init {
        this.subClass = subClass
        this.hitPointsConstant = hitPointsConstant
        this.hitDice = hitDice
        classArmsProficiencies = classArms
        classToolsProficiencies = classTools
        this.savingThrows = savingThrows
        if (classSkills != null) {
            classSkillProficiencies = classSkills
        }
        this.features = features
    }

    fun setBackground(): String {
        val background = arrayOf("None", "Acolyte")
        var backgroundOption = ""
        while (backgroundOption == "") {
            backgroundOption = Builder.selection("Choose your character background:\n*Note: The only background available right now is Acolyte.\n\n" +
                    "Acolyte:\n" +
                    "Skill Proficiencies: Insight, Religion\n" +
                    "Language Proficiencies: +2 Any\n" +
                    "Equipment: Holy Symbol\n" +
                    "Feature: Shelter of the Faithful\n", background, background)
        }
        return backgroundOption
    }

    fun setClass(): String {
        var cClass = ""
        val basicClass = arrayOf("Cleric", "Fighter", "Rogue", "Wizard")
        while (cClass == "") {
            cClass = Builder.selection("Choose your character class: ", basicClass, basicClass)
        }
        return cClass
    }

    companion object {
        fun controller(cClass: String): CharacterClass? {
            val classFeatures: ArrayList<String> = ArrayList()
            var characterClass: CharacterClass? = null
            when (cClass) {
                "Cleric" -> characterClass = cleric()
                "Fighter" -> characterClass = fighter()
                "Rogue" -> characterClass = rogue()
                "Wizard" -> characterClass = wizard()
            }
            return characterClass
        }

        private fun cleric(): CharacterClass {
            return CharacterClass("Life", 8, "1d8",
            arrayListOf("Light Armor", "Medium Armor", "Simple Weapons"),
            arrayListOf(""),
            arrayListOf("Wisdom", "Charisma"),
            arrayOf("History", "Insight", "Medicine", "Persuasion", "Religion"),
            arrayListOf())
        }

        private fun fighter(): CharacterClass {
            return CharacterClass("Champion", 10, "1d10",
            arrayListOf("Light Armor", "Medium Armor", "Heavy Armor", "Shields", "Simple Weapons", "Martial Weapons"),
            arrayListOf(""),
            arrayListOf("Strength", "Constitution"),
            arrayOf("Acrobatics", "Animal Handling", "Athletics", "History", "Insight", "Intimidation", "Perception", "Survival"),
            arrayListOf("Fighting Style: ", "Second Wind"))
        }

        private fun rogue(): CharacterClass {
            return CharacterClass("Thief", 8, "1d8",
            arrayListOf("Light Armor", "Medium Armor", "Simple Weapons", "Hand-Crossbows", "Longswords", "Shortswords", "Rapiers"),
            arrayListOf("Thieves Tools"),
            arrayListOf("Dexterity", "Intelligence"),
            arrayOf("Acrobatics", "Athletics", "Deception", "Insight",  "Intimidation", "Investigation", "Perception", "Performance", "Persuasion", "Slight of Hand", "Stealth"),
            arrayListOf("Expertise", "Sneak Attack", "Thieves' Cant"))
        }

        private fun wizard(): CharacterClass {
            return CharacterClass("Evocation", 6, "1d6",
            arrayListOf("Daggers", "Darts", "Slings", "Quarterstaffs", "Light-Crossbows"),
            arrayListOf(""),
            arrayListOf("Intelligence", "Wisdom"),
            arrayOf("Arcana", "History", "Insight", "Investigation", "Medicine", "Religion"),
            arrayListOf())
        }

        fun conditionals(character: CharacterBasic, health: Health, abilityModifiers: Stats, equipment: Equipment, languages: Languages, proficiencies: Proficiency, characterClass: CharacterClass, features: Features, spellCasting: SpellBook) {
            var characterClass = characterClass
            characterClass = controller(character.cClass)!!
            character.subClass = characterClass.subClass
            health.hitPointsConstant = characterClass.hitPointsConstant
            health.hitDice = characterClass.hitDice
            proficiencies.classArms = characterClass.classArmsProficiencies
            proficiencies.savingThrows = characterClass.savingThrows
            features.cClass = characterClass.features
            equipment.classBag = Equipment.controller(character.cClass, character, abilityModifiers, features)
            equipment.bag = Proficiency.Configuration.listHelper(equipment.bag, equipment.raceBag, equipment.classBag)
            proficiencies.tools = Proficiency.Configuration.listHelper(proficiencies.tools, proficiencies.raceTools, proficiencies.classTools)
            proficiencies.arms = Proficiency.Configuration.listHelper(proficiencies.arms, proficiencies.raceArms, proficiencies.classArms)
            proficiencies.skills = Proficiency.Configuration.listHelper(proficiencies.skills, proficiencies.raceSkills, proficiencies.classSkills)

            if (characterClass.subClass == "Life") {
                val cleric = Cleric()
                cleric.domainSpells = arrayListOf("Bless", "Cure Wounds")
                cleric.canTrips = cleric.selectSpells(cleric, 0, abilityModifiers)
                for (cantrip in cleric.canTrips) {
                    features.cClass.add(cantrip)
                }
                features.cClass.add("Bless")
                features.cClass.add("Cure Wounds")
                features.cClass.add("Disciple of Life")
                characterClass.classArmsProficiencies.add("Heavy Armor")
            }
            if (characterClass.subClass == "Evocation") {
                val wizard = Wizard()
                wizard.canTrips = wizard.selectSpells(wizard, 0, abilityModifiers)
                wizard.spellBook = wizard.fillSpellBook(wizard)
                for (cantrip in wizard.canTrips) {
                    features.cClass.add(cantrip)
                }
                for (spell in wizard.spellBook) {
                    features.cClass.add(spell)
                }
                features.cClass.add("Arcane Recovery")
            }
            if (character.background == "Acolyte") {
                equipment.bag.add("Holy Symbol")
                features.background.add(BackgroundFeatures.acolyte())
            }

        }
    }
}