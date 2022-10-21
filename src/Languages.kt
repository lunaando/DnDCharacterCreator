class Languages {
    var list: ArrayList<String> = ArrayList()
    lateinit var native: String
    val background: ArrayList<String> = ArrayList()

    fun selectLanguages(native: String, race: String, background: String): ArrayList<String> {
        
        var choices = 0
        if (background == "Acolyte") {
            choices += 2
        }
        if (race == "Elf" || race == "Human" || race == "Half-Elf") {
            choices += 1
        }

        val knownLanguages: ArrayList<String> = arrayListOf("Common")
        knownLanguages.add(native)
        val languageList = arrayListOf("Dwarven", "Elvish", "Giant", "Gnomish", "Goblin", "Halfling", "Orc", "Abyssal", "Celestial", "Draconic", "Deep-Speech", "Infernal", "Primordial", "Sylvan", "Undercommon")
        if (languageList.contains(native)) {
            languageList.remove(native)
        }
        var languageArray: Array<String> = languageList.toTypedArray()
        for (i in 0 until choices) {
            knownLanguages.add(Builder.itemToList("Your character will have an extra language if its race is Human, Elf, or Half-Elf.\n" +
                    "Your character will have two extra languages if its background is Acolyte.\n" +
                    "Your current settings grant $choices extra languages.\n\n" +
                    "Selection ${i + 1}\n", "", languageArray, languageArray))
            languageArray = Proficiency.Configuration.proficiencyListUpdate(knownLanguages, languageArray)
        }
        val knownLanguagesDistinct = ArrayList<String>()
        for (item in knownLanguages) {
            if (!knownLanguagesDistinct.contains(item)) {
                knownLanguagesDistinct.add(item)
            }
        }
        return knownLanguagesDistinct
    }
}
