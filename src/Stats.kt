import java.util.*
import kotlin.properties.Delegates

class Stats(constitution: Int = 0, strength: Int = 0, dexterity: Int = 0, intelligence: Int = 0, wisdom: Int = 0, charisma: Int = 0) {
    var set = false
    var constitution by Delegates.notNull<Int>()
    var strength by Delegates.notNull<Int>()
    var dexterity by Delegates.notNull<Int>()
    var intelligence by Delegates.notNull<Int>()
    var wisdom by Delegates.notNull<Int>()
    var charisma by Delegates.notNull<Int>()
    var initiative by Delegates.notNull<Int>()

    init {
        this.constitution = constitution
        this.strength = strength
        this.dexterity = dexterity
        this.intelligence = intelligence
        this.wisdom = wisdom
        this.charisma = charisma
    }

    companion object {

        private val scan = Scanner(System.`in`)

        fun menu(character: CharacterBasic, stats: Stats, racialBonus: Stats): Stats {
            var stats = stats
            stats = Stats()
            val emptyList = ArrayList<String>()
            val statNames: ArrayList<String> = ArrayList()
            statNames.add("Constitution")
            statNames.add("Strength")
            statNames.add("Dexterity")
            statNames.add("Intelligence")
            statNames.add("Wisdom")
            statNames.add("Charisma")
            var statSelection = ""
            val unassignedScores: ArrayList<Int> = statProcessor()
            Builder.dataLine("\nStat results: ", emptyList, unassignedScores)
            println("\nPress enter to continue...")
            scan.nextLine()

            for (i in 0..5) {
                when (Builder.selection("Stat Pool: ${unassignedScores[0]} ${unassignedScores[1]} ${unassignedScores[2]} ${unassignedScores[3]} ${unassignedScores[4]} ${unassignedScores[5]}\n\n" +
                        "Optimal stat arrangement:\n" +
                        "Cleric: Wisdom, Fighter: Strength, Rogue: Dexterity, Wizard: Intelligence\n\n" +
                        "Ability Modifier Table:\n" +
                        "Ability Score:     0   2   4   6   8   10   12   14   16   18   20   22   24   26   28   30\n" +
                        "Ability Modifier: -5  -4  -3  -2  -1   0    1    2    3    4    5    6    7    8    9    10\n\n" +
                        "Racial Bonus (${character.race}) Con: ${racialBonus.constitution} Str: ${racialBonus.strength} Dex: ${racialBonus.dexterity} Int: ${racialBonus.intelligence} Wis: ${racialBonus.wisdom} Cha: ${racialBonus.charisma}\n\n" +
                        "Ability Scores: Con: ${stats.constitution} Str: ${stats.strength} Dex: ${stats.dexterity} Int: ${stats.intelligence} Wis: ${stats.wisdom} Cha: ${stats.charisma}\n" +
                        "\nAssign: ${unassignedScores[i]}. ", statNames.toTypedArray(), statNames.toTypedArray())) {
                    "Constitution" -> {
                        stats.constitution = unassignedScores[i]
                        statNames.remove("Constitution")
                    }
                    "Strength" -> {
                        stats.strength = unassignedScores[i]
                        statNames.remove("Strength")
                    }
                    "Dexterity" -> {
                        stats.dexterity = unassignedScores[i]
                        statNames.remove("Dexterity")
                    }
                    "Intelligence" -> {
                        stats.intelligence = unassignedScores[i]
                        statNames.remove("Intelligence")
                    }
                    "Wisdom" -> {
                        stats.wisdom = unassignedScores[i]
                        statNames.remove("Wisdom")
                    }
                    "Charisma" -> {
                        stats.charisma = unassignedScores[i]
                        statNames.remove("Charisma")
                    }
                    else -> {
                        throw Exception("Builder.selection did not return a recognized value")
                    }
                }
            }
            return stats
        }

        private fun statProcessor(): ArrayList<Int> {
            var processedValues: ArrayList<Int> = ArrayList()
            val statMode: Array<String> = arrayOf("Standard Array", "Roll")
            var statSelection = ""
            while (statSelection == "") {
                statSelection = Builder.selection("How do you want the character's ability scores to be determined?\n\n" +
                    "Standard array gives fixed numbers to assign:\n" +
                    "15, 14, 13, 12, 10, 9\n\n" +
                    "Rolling gives you 6 random numbers.\n\n", statMode, statMode)
                when (statSelection) {
                    "Standard Array" -> {
                        processedValues.add(15)
                        processedValues.add(14)
                        processedValues.add(13)
                        processedValues.add(12)
                        processedValues.add(10)
                        processedValues.add(9)
                    }
                    "Roll" -> processedValues = DiceRoll.rollForStats()
                    else -> {}
                }
            }
            return processedValues
        }

        fun abilityScoreAssigner(stats: Stats, racialBonus: Stats): Stats {
            val statsArray: Array<Int> = arrayOf(stats.constitution, stats.strength, stats.dexterity, stats.intelligence, stats.wisdom, stats.charisma)
            val racialBonusArray: Array<Int> = arrayOf(racialBonus.constitution, racialBonus.strength, racialBonus.dexterity, racialBonus.intelligence, racialBonus.wisdom, racialBonus.charisma)
            val compilerArray = IntArray(6)
            for (i in statsArray.indices) {
                compilerArray[i] = statsArray[i] + racialBonusArray[i]
            }
            return Stats(compilerArray[0], compilerArray[1], compilerArray[2], compilerArray[3], compilerArray[4], compilerArray[5])
        }

        fun abilityModifierAssigner(scores: Stats): Stats {
            val scoresArray: Array<Int> = arrayOf(scores.constitution, scores.strength, scores.dexterity, scores.intelligence, scores.wisdom, scores.charisma)
            val compilerArray = IntArray(6)
            val tableX1: Array<Int> = arrayOf(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30)
            val tableX2: Array<Int> = arrayOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)
            val tableY: Array<Int> = arrayOf(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

            for (i in scoresArray.indices) {
                compilerArray[i] = configureByTable(scoresArray[i], tableX1, tableX2, tableY)
            }
            return Stats(compilerArray[0], compilerArray[1], compilerArray[2], compilerArray[3], compilerArray[4], compilerArray[5])
        }

        private fun configureByTable(score: Int, tableX1: Array<Int>, tableX2: Array<Int>, tableY: Array<Int>): Int {
            var score = score
            var result = 0
            for (i in tableY.indices) {
                if (score == tableX1[i]) {
                    result = tableY[i]
                }
                else if (score == tableX2[i]) {
                    result = tableY[i]
                }
            }
            score = result
            return score
        }
    }
}