class Skills(athletics: Int = 0, acrobatics: Int = 0, sleightOfHand: Int = 0, stealth: Int = 0, arcana: Int = 0, history: Int = 0, investigation: Int = 0, nature: Int = 0, religion: Int = 0, animalHandling: Int = 0, insight: Int = 0, medicine: Int = 0, perception: Int = 0, survival: Int = 0, deception: Int = 0, intimidation: Int = 0, performance: Int = 0, persuasion: Int = 0) {
    var skillList = ArrayList<String>()
    var athletics: Int
    var acrobatics: Int
    var sleightOfHand: Int
    var stealth: Int
    var arcana: Int
    var history: Int
    var investigation: Int
    var nature: Int
    var religion: Int
    var animalHandling: Int
    var insight: Int
    var medicine: Int
    var perception: Int
    var survival: Int
    var deception: Int
    var intimidation: Int
    var performance: Int
    var persuasion: Int

    var set = false
    var athleticsSet = false
    var acrobaticsSet = false
    var sleightOfHandSet = false
    var stealthSet = false
    var arcanaSet = false
    var historySet = false
    var investigationSet = false
    var natureSet = false
    var religionSet = false
    var animalHandlingSet = false
    var insightSet = false
    var medicineSet = false
    var perceptionSet = false
    var survivalSet = false
    var deceptionSet = false
    var intimidationSet = false
    var performanceSet = false
    var persuasionSet = false

    init {
        this.athletics = athletics
        this.acrobatics = acrobatics
        this.sleightOfHand = sleightOfHand
        this.stealth = stealth
        this.arcana = arcana
        this.history = history
        this.investigation = investigation
        this.nature = nature
        this.religion = religion
        this.animalHandling = animalHandling
        this.insight = insight
        this.medicine = medicine
        this.perception = perception
        this.survival = survival
        this.deception = deception
        this.survival = survival
        this.deception = deception
        this.intimidation = intimidation
        this.performance = performance
        this.persuasion = persuasion
    }

    companion object {
        fun savingThrowAssigner(savingThrows: Stats, mods: Stats): Stats {
            val savingThrowsArray = arrayOf(savingThrows.constitution, savingThrows.strength, savingThrows.dexterity, savingThrows.intelligence, savingThrows.wisdom, savingThrows.charisma)
            val modsArray = arrayOf(mods.constitution, mods.strength, mods.dexterity, mods.intelligence, mods.wisdom, mods.charisma)
            for (i in savingThrowsArray.indices) {
                savingThrowsArray[i] = modsArray[i]
            }
            return Stats(savingThrowsArray[0], savingThrowsArray[1], savingThrowsArray[2], savingThrowsArray[3], savingThrowsArray[4], savingThrowsArray[5])
        }
    }
}