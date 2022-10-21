import java.util.*
import kotlin.collections.ArrayList

class CharacterRace(race: String, subRace: String, draconic: String, speed: Int, extraProficiency: Int, nativeLanguage: String, racialBonus: Stats, features: ArrayList<String>, raceArms: ArrayList<String>, raceSkills: ArrayList<String>, raceTools: ArrayList<String>, cantrips: ArrayList<String>) {
    var set = false
    var race: String
    var subRace: String
    var draconic: String
    var speed: Int
    private var extraProficiency: Int
    var nativeLanguage: String
    var racialBonus: Stats
    var features: ArrayList<String>
    var raceArmsProficiencies: ArrayList<String>
    var raceSkillsProficiencies: ArrayList<String>
    var raceToolsProficiencies: ArrayList<String>
    var cantrips: ArrayList<String>

    private val scan = Scanner(System.`in`)

    init {
        this.race = race
        this.subRace = subRace
        this.draconic = draconic
        this.speed = speed
        this.extraProficiency = extraProficiency
        this.nativeLanguage = nativeLanguage
        this.racialBonus = racialBonus
        this.features = features
        this.raceArmsProficiencies = raceArms
        this.raceSkillsProficiencies = raceSkills
        this.raceToolsProficiencies = raceTools
        this.cantrips = cantrips
    }

    fun setRace(): String {
        var choice = 0
        var check = true
        val races = arrayOf("Dwarf", "Elf", "Halfling", "Human", "Dragonborn", "Gnome", "Half-Elf", "Half-Orc", "Tiefling")

        while (check) {
            print("1. Dwarf            +2 Constitution +1 Wisdom    +1 HP per lvl\n" +
                    "                    Artisans Tools: Smith's, Brewer's, Mason's. Advantage & resistance against poison \n" +

                    "\n2. Elf              +2 Dexterity    +1 Intelligence \n" +
                    "                    1 Wizard cantrip; Proficiency in Perception, 1 extra language \n" +
                    "                    Advantage on saving throws against being charmed or put to sleep by magic \n" +

                    "\n3. Halfling         +2 Dexterity    +1 Charisma\n" +
                    "                    1 re-roll on a d20 for attack roll, ability check, or saving throw \n" +

                    "\n4. Human            +1 All; 1 extra language \n" +

                    "\n5. Dragonborn       +2 Strength     +1 Charisma\n" +
                    "                    Breath weapon/damage resistance based on draconic heritage \n" +

                    "\n6. Gnome            +2 Intelligence +1 Constitution\n" +
                    "                    Advantage on all Int, Wis, or Cha saving throws against magic \n" +

                    "\n7. Half-Elf         +2 Charisma     +1 Any +1 Any \n" +
                    "                    Proficiency in 2 skills; 1 extra language \n" +
                    "                    Advantage on saving throws against being charmed or put to sleep by magic \n" +

                    "\n8. Half-Orc         +2 Strength     +1 Constitution \n" +
                    "                    Proficiency in Intimidation \n" +
                    "                    endure a strike and only drop to 1HP; roll an extra weapon damage dice on critical hits \n" +

                    "\n9. Tiefling         +2 Charisma     +1 Intelligence \n" +
                    "                    Fire Resistance; Thaumaturgy cantrip\n")
            print("Select a race: ")
            try {
                choice = scan.nextInt() - 1
                check = false
            } catch (e: Exception) {
                            }
        }

        return try {
            races[choice]
        } catch (e: IndexOutOfBoundsException) {
                        print("Improper selection.\n" +
                    "Press enter to try again.")
            scan.nextLine()
            ""
        }
    }

    companion object {
        fun conditionals(character: CharacterBasic, race: CharacterRace, racialBonus: Stats, equipment: Equipment, languages: Languages, proficiencies: Proficiency, features: Features): Stats {
            val race = controller(character.race)
            character.subRace = race.subRace
            character.draconic = race.draconic
            character.speed = race.speed
            val racialBonus = race.racialBonus
            languages.native = race.nativeLanguage
            proficiencies.raceTools = race.raceToolsProficiencies
            proficiencies.raceArms = race.raceArmsProficiencies
            proficiencies.raceSkills = race.raceSkillsProficiencies
            features.race = race.features
            proficiencies.tools = Proficiency.Configuration.listHelper(proficiencies.tools, proficiencies.raceTools, proficiencies.classTools)
            proficiencies.arms = Proficiency.Configuration.listHelper(proficiencies.arms, proficiencies.raceArms, proficiencies.classSkills)
            proficiencies.skills = Proficiency.Configuration.listHelper(proficiencies.skills, proficiencies.raceSkills, proficiencies.classSkills)
            return racialBonus
        }

        private fun controller(race: String): CharacterRace {
            val raceSettings = CharacterRace("", "", "", 0, 0,
                nativeLanguage = "",
                racialBonus = Stats(0, 0, 0, 0, 0, 0),
                features = arrayListOf(""),
                raceArms = arrayListOf(""),
                raceSkills = arrayListOf(""),
                raceTools = arrayListOf(""),
                cantrips = arrayListOf(""))
                        when (race) {
                "Dragonborn" -> dragonborn(raceSettings)
                "Dwarf" -> dwarf(raceSettings)
                "Elf" -> elf(raceSettings)
                "Halfling" -> halfling(raceSettings)
                "Human" -> human(raceSettings)
                "Gnome" -> gnome(raceSettings)
                "Half-Elf" -> halfElf(raceSettings)
                "Half-Orc" -> halfOrc(raceSettings)
                "Tiefling" -> tiefling(raceSettings)
            }
            return raceSettings
        }

        private fun dragonborn(raceSettings: CharacterRace): CharacterRace {
            val draconicHeritage = arrayOf("Black", "Blue", "Brass", "Bronze", "Copper", "Gold", "Green", "Red", "Silver", "White")
            raceSettings.race = "Dragonborn"
            raceSettings.subRace = ""
            raceSettings.draconic = Builder.selection("\nSelect a draconic heritage:\n", draconicHeritage, draconicHeritage)
            raceSettings.racialBonus = Stats(0, 2, 0, 0, 0, 1)
            raceSettings.speed = 30
            raceSettings.nativeLanguage = "Draconic"
            raceSettings.features = arrayListOf("Draconic Ancestry",  "Breath Weapon", "Damage Resistance")
            raceSettings.raceSkillsProficiencies = ArrayList()
            raceSettings.raceToolsProficiencies = ArrayList()
            return raceSettings
        }

        private fun dwarf(raceSettings: CharacterRace): CharacterRace {
            val toolOptions = arrayOf("Smith's Tools", "Brewer's Tools", "Mason's Tools")
            raceSettings.race = "Dwarf"
            raceSettings.subRace = "Hill"
            raceSettings.draconic = ""
            raceSettings.racialBonus = Stats(2, 0, 0, 0, 0, 0)
            raceSettings.speed = 25
            raceSettings.nativeLanguage = "Dwarven"
            raceSettings.features = arrayListOf("Darkvision", "Dwarven Resilience", "Dwarven Combat Training", "Stonecutting")
            raceSettings.raceArmsProficiencies = arrayListOf("Battleaxe", "Handaxe", "Light Hammer", "Warhammer")
            raceSettings.raceToolsProficiencies.add(Builder.itemToList("Select a tool to gain proficiency in: ", "", toolOptions, toolOptions))

            if (raceSettings.subRace == "Hill") {
                raceSettings.racialBonus.wisdom = 1
                raceSettings.features.add("Dwarven Toughness")
            }
            return raceSettings
        }

        private fun elf(raceSettings: CharacterRace): CharacterRace {
            raceSettings.race = "Dwarf"
            raceSettings.subRace = "High"
            raceSettings.draconic = ""
            raceSettings.speed = 30
            raceSettings.racialBonus = Stats(0, 0, 2, 0, 0, 0)
            raceSettings.nativeLanguage = "Elvish"
            raceSettings.features = arrayListOf("Darkvision", "Keen Senses", "Fey Ancestry", "Trance")
            raceSettings.raceSkillsProficiencies = arrayListOf("Perception")
            if (raceSettings.subRace == "High") {
                raceSettings.racialBonus.intelligence = 1
                raceSettings.features.add("Elf Weapon Training")
                raceSettings.raceArmsProficiencies = arrayListOf("Longsword", "Shortsword", "Shortbow", "Longbow")
                                val elf = Wizard()
                val cantripToList = elf.spellList(0)
                    ?.let { Builder.itemToList("Select a wizard cantrip: ", "", it, it) }
                raceSettings.cantrips.add(cantripToList!!)
                raceSettings.features.add(cantripToList)
            }
            return raceSettings
        }

        private fun halfling(raceSettings: CharacterRace): CharacterRace {
            raceSettings.race = "Halfling"
            raceSettings.subRace = "Lightfoot"
            raceSettings.speed = 25
            raceSettings.racialBonus = Stats(0, 0, 2, 0, 0, 0)
            raceSettings.nativeLanguage = "Halfling"
            raceSettings.features = arrayListOf("Lucky", "Brave", "Halfling Nimbleness")
            if (raceSettings.subRace == "Lightfoot") {
                raceSettings.racialBonus.charisma = 1
                raceSettings.features.add("Naturally Stealthy")
            }
            return raceSettings
        }

        private fun human(raceSettings: CharacterRace): CharacterRace {
            val languageChoice = arrayOf("Dwarven", "Elvish", "Giant", "Gnomish", "Goblin", "Halfling", "Orc", "Abyssal", "Celestial", "Draconic", "Deep-Speech", "Infernal", "Primordial", "Sylvan", "Undercommon")
            raceSettings.race = "Human"
            raceSettings.subRace = ""
            raceSettings.draconic = ""
            raceSettings.speed = 30
            raceSettings.racialBonus = Stats(1, 1, 1, 1, 1, 1)
            raceSettings.features = arrayListOf()
            return raceSettings
        }

        private fun gnome(raceSettings: CharacterRace): CharacterRace {
            raceSettings.race = "Gnome"
            raceSettings.subRace = "Rock"
            raceSettings.draconic = ""
            raceSettings.speed = 25
            raceSettings.racialBonus = Stats(0, 0, 0, 2, 0, 0)
            raceSettings.nativeLanguage = "Gnomish"
            raceSettings.features = arrayListOf("Darkvision", "Gnome Cunning")
            if (raceSettings.subRace == "Rock") {
                raceSettings.racialBonus.constitution = 1
                raceSettings.features.add("Artificer's Lore")
                raceSettings.features.add("Tinker")
                raceSettings.raceToolsProficiencies.add("Tinker's Tools")
            }
            return raceSettings
        }

        private fun halfElf(raceSettings: CharacterRace): CharacterRace {
                        val statBonus = arrayOf("Constitution", "Strength", "Dexterity", "Intelligence", "Wisdom", "Charisma")
            raceSettings.race = "Half-Elf"
            raceSettings.subRace = ""
            raceSettings.draconic = ""
            raceSettings.speed = 30
            raceSettings.racialBonus = Stats(0, 0, 0, 0, 0, 2)
            raceSettings.nativeLanguage = "Elvish"
            raceSettings.features = arrayListOf("Darkvision", "Fey Ancestry", "Skill Versatility")
            print("Half-Elves receive 2 bonus values to apply to any Ability Score")
            for (i in 0..1) {
                when (Builder.itemToList("\nAssign 2 bonus values\n", "", statBonus, statBonus)) {
                    "Constitution" -> raceSettings.racialBonus.constitution += 1
                    "Strength" -> raceSettings.racialBonus.strength += 1
                    "Dexterity" -> raceSettings.racialBonus.dexterity += 1
                    "Intelligence" -> raceSettings.racialBonus.intelligence += 1
                    "Wisdom" -> raceSettings.racialBonus.wisdom += 1
                    "Charisma" -> raceSettings.racialBonus.charisma += 1
                }
            }
            return raceSettings
        }

        private fun halfOrc(raceSettings: CharacterRace): CharacterRace {
            raceSettings.race = "Half-Orc"
            raceSettings.subRace = ""
            raceSettings.draconic = ""
            raceSettings.speed = 30
            raceSettings.racialBonus = Stats(1, 2, 0, 0, 0, 0)
            raceSettings.nativeLanguage = "Orc"
            raceSettings.features = arrayListOf("Darkvision", "Menacing", "Relentless Endurance", "Savage Attacks")
            raceSettings.raceSkillsProficiencies = arrayListOf("Intimidation")
            return raceSettings
        }

        private fun tiefling(raceSettings: CharacterRace): CharacterRace {
            raceSettings.race = "Tiefling"
            raceSettings.subRace = ""
            raceSettings.draconic = ""
            raceSettings.speed = 30
            raceSettings.racialBonus = Stats(0, 0, 0, 1, 0, 2)
            raceSettings.nativeLanguage = "Infernal"
            raceSettings.features = arrayListOf("Darkvision", "Hellish Resistance", "Infernal Legacy")
            raceSettings.cantrips = arrayListOf("Thaumaturgy")
            return raceSettings
        }
    }
}