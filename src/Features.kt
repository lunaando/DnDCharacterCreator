class Features(race: ArrayList<String>, cClass: ArrayList<String>, background: ArrayList<String>) {
    var race: ArrayList<String>
    var cClass: ArrayList<String>
    var background: ArrayList<String>
    init {
        this.race = race
        this.cClass = cClass
        this.background = background
    }

    fun listFlatten(list: Array<String>): String {
        var flatList = ""
        for (i in list.indices) {
            flatList = ("$flatList, ${list[i]}")
        }
        return flatList
    }

    companion object {
        fun raceFeatures() {

        }
    }
}

class BackgroundFeatures {
    companion object {
        fun acolyte(): String {
            return "Shelter of the Faithful"
        }
    }
}

open class SpellBook {
    var canTrips: ArrayList<String> = ArrayList()
    val preparedSpells: Array<String>
        get() {TODO()}
    val lvl1SpellSlots: Array<String>
        get() {TODO()}
    val spellSaveDC: Int
        get() {TODO()}
    val spellAttackModifier: Int
        get() {TODO()}
}

class Cleric: SpellBook() {
    lateinit var domainSpells: ArrayList<String>

    private fun spellList(lvl: Int): Array<String>? {
        val spellsLvls: Array<Array<String>?> = arrayOfNulls(2)
        spellsLvls[0] = arrayOf("Guidance", "Light", "Mending", "Resistance", "Sacred Flame", "Spare the Dying", "Thaumaturgy")
        spellsLvls[1] = arrayOf("Bane", "Bless", "Command", "Create or Destroy Water", "Cure Wounds", "Detect Evil and Good", "Detect Magic", "Detect Poison and Disease", "Guiding Bolt", "Healing Word", "Inflict Wounds", "Purify Food and Drink", "Sanctuary", "Shield of Faith")
        return spellsLvls[lvl]
    }

    fun selectSpells(cleric: Cleric, spellLvl: Int, abilityModifiers: Stats): ArrayList<String> {
        val count: Int
        val prompt: String
        if (spellLvl == 0) {
            count = 3
            prompt = "Cantrips are level 0 spells with unlimited uses. Unlike prepared spells, they are permanent.\n" +
                    "Select 3:\n"
        }
        else {
            count = abilityModifiers.wisdom + 1
            prompt = "Clerics prepare spells daily. The amount of spells available is determined by Wisdom and Cleric level.\n" +
                    "Character level: 1\n" +
                    "Wisdom: ${abilityModifiers.wisdom}\n" +
                    "Select $count spells:\n"
        }
        val spellList: ArrayList<String> = ArrayList()
        var spellArray: Array<String>? = cleric.spellList(spellLvl)
        for (i in 0 until count) {
            var spellToList = ""
            while (spellToList == "") {
                spellToList = spellArray?.let { Builder.selection(prompt, it, it) }.toString()
            }
            spellList.add(spellToList)
            spellArray = spellArray?.let { Proficiency.Configuration.proficiencyListUpdate(spellList, it) }
        }
        return spellList
    }
}

class Wizard: SpellBook() {
    var spellBook: ArrayList<String> = ArrayList()

    fun spellList(lvl: Int): Array<String>? {
        val spellsLvls: Array<Array<String>?> = arrayOfNulls(2)
        spellsLvls[0] = arrayOf("Acid Splash", "Chill Touch", "Dancing Lights", "Fire Bolt", "Light", "Mage Hand", "Mending", "Message", "Minor Illusion", "Poison Spray", "Prestidigitation", "Ray of Frost", "Shocking Grasp", "True Strike")
        spellsLvls[1] = arrayOf("Alarm", "Burning Hands", "Charm Person", "Color Spray", "Comprehend Languages", "Detect Magic", "Disguise Self", "Expeditious Retreat", "False Life", "Feather Fall", "Find Familiar", "Floating Disk", "Fog Cloud", "Grease", "Hideous Laughter", "Identify", "Illusory Script", "Jump", "Longstrider", "Mage Armor", "Magic Missile", "Protection from Evil and Good", "Shield", "Silent Image", "Sleep", "Thunderwave", "Unseen Servant")
        return spellsLvls[lvl]
    }

    fun fillSpellBook(wizard: Wizard): ArrayList<String> {
        val spellList: ArrayList<String> = ArrayList()
        var spellArray: Array<String>? = wizard.spellList(1)
        for (i in 0..5) {
            val spellToList: String? = spellArray?.let {
                Builder.itemToList("Wizards start with 6 spells in their spell book.\n" +
                        "Select your spells: #${i + 1} ", "", it, it)
            }
            spellList.add(spellToList!!)
            spellArray = spellArray?.let { Proficiency.Configuration.proficiencyListUpdate(spellList, it) }
        }
        return spellList
    }

    fun selectSpells(wizard: Wizard, spellLvl: Int, abilityModifiers: Stats): ArrayList<String> {
        val count: Int
        val prompt: String
        if (spellLvl == 0) {
            count = 3
            prompt = "Cantrips are 0 level spells with unlimited uses.\n" +
                    "Select three:"
        }
        else {
            count = abilityModifiers.intelligence + 1
            prompt = "Wizards prepare a daily list of spells. The amount of spells is determined by Intelligence and Wizard level.\n" +
                    "Character level: 1\n" +
                    "Intelligence Modifier: ${abilityModifiers.intelligence}\n" +
                    "Select $count spells:\n"
        }
        val spellList: ArrayList<String> = ArrayList()
        var spellArray = wizard.spellList(spellLvl)
        for (i in 0 until count) {
            val spellToList: String? =
                spellArray?.let { Builder.itemToList("$prompt #${i + 1} ", "", it, it) }
            spellList.add(spellToList!!)
            spellArray = spellArray?.let { Proficiency.Configuration.proficiencyListUpdate(spellList, it) }
        }
        return spellList
    }
}