import kotlin.properties.Delegates

class CharacterBasic(name: String = "", gender: String = "", race: String = "", subRace: String = "", draconic: String = "", cClass: String = "", subClass: String = "", background: String = "", alignment: String = "", level: Int = 1, speed: Int = 0) {
    var set = false
    var nameSet = false
    var genderSet = false
    var raceSet = false
    var cClassSet = false
    var backgroundSet = false
    var alignmentSet = false
    var name: String
    var gender: String
    var race: String
    var subRace: String
    var draconic: String
    var cClass: String
    var subClass: String
    var background: String
    var alignment: String
    var level by Delegates.notNull<Int>()
    var speed by Delegates.notNull<Int>()

    init {
        this.name = name
        this.gender = gender
        this.race = race
        this.subRace = subRace
        this.draconic = draconic
        this.cClass = cClass
        this.subClass = subClass
        this.background = background
        this.alignment = alignment
        this.level = level
        this.speed = speed
    }

    fun setGender(): String {
        val genderOptions = arrayOf("Male", "Female", "Construct")
        val genderSelection = arrayOf("Male", "Female", "Construct")
        while (gender == "")  {
            gender = Builder.selection("Choose your character's gender: ", genderOptions, genderSelection)
        }
        return gender
    }

    fun setAlignment(): String {
        val alignments: Array<String> = arrayOf("Lawful Good", "Neutral Good", "Chaotic Good", "Lawful Neutral", "True Neutral", "Chaotic Neutral", "Lawful Evil", "Neutral Evil", "Chaotic Evil", "HELP")
        while (alignment == "") {
            alignment = Builder.selection("What is your character's alignment? ", alignments, alignments)
        }

        if (alignment == "HELP") {
            alignment = alignmentHelp()
        }
        return alignment
    }

    companion object {
        fun alignmentHelp(): String {
            var choice1 = ""
            var choice2 = ""
            val alignment1 = arrayOf("Lawful", "Neutral", "Chaotic")
            val alignment2 = arrayOf("Good", "Neutral", "Evil")
            val options1 = arrayOf("Yes", "Sometimes", "No")
            val options2 = arrayOf("Yes", "Sometimes", "No")

            while (choice1 == "") {
                choice1 = Builder.selection("Does your character obey laws, rules, etc.? ", options1, alignment1)
            }
            while (choice2 == "") {
                choice2 = Builder.selection("Does your character have morals? ", options2, alignment2)
            }

            var combine = "$choice1 $choice2"
            if (combine == "Neutral Neutral") {
                combine = "True Neutral"
            }
            return combine
        }
    }
}

class Health(hitPoints: Int = 0, hitPointsConstant: Int = 0, armorClass: Int = 0, hitDice: String = "", initiative: Int = -5) {
    var hitPoints by Delegates.notNull<Int>()
    var hitPointsConstant by Delegates.notNull<Int>()
    var armorClass by Delegates.notNull<Int>()
    var initiative by Delegates.notNull<Int>()
    var hitDice: String
    var hitPointsSet = false
    var hitPointsConstantSet = false
    var armorClassSet = false
    var initiativeSet = false
    var hitDiceSet = false

    init {
        this.hitPoints = hitPoints
        this.hitPointsConstant = hitPointsConstant
        this.armorClass = armorClass
        this.hitDice = hitDice
        this.initiative = initiative
    }

    companion object {
        fun setArmorClass(abilityModifiers: Stats, equipment: Equipment): Int {
            var armor = 0
            val dexBonus = abilityModifiers.dexterity
            var shield = 0
            val leatherArmor = 11 + dexBonus
            var scaleMail = 13 + dexBonus
            val chainMail = 16
            if (scaleMail > 15) {
                scaleMail = 15
            }
            val armorSets = arrayOf("Leather Armor", "Scale Mail", "Chain Mail")
            val armorValues = arrayOf(leatherArmor, scaleMail, chainMail)
            for (i in armorSets.indices) {
                if (equipment.bag.contains(armorSets[i])) {
                    armor = armorValues[i]
                }
            }
            if (equipment.bag.contains("Shield")) {
                shield = 2
            }
            return armor + shield
        }
    }
}