class Equipment(bag: ArrayList<String>, raceBag: ArrayList<String>, classBag: ArrayList<String>) {
    var bag: ArrayList<String>
    var raceBag: ArrayList<String>
    var classBag: ArrayList<String>

    init {
        this.bag = bag
        this.raceBag = raceBag
        this.classBag = classBag
    }

    companion object {
        fun controller(cClass: String, character: CharacterBasic, abilityModifiers: Stats, features: Features):ArrayList<String> {
            var equipment: ArrayList<String> = ArrayList()
            when (cClass) {
                "Cleric" -> equipment = cleric(character, abilityModifiers, features)
                "Fighter" -> equipment = fighter(character, abilityModifiers, features)
                "Rogue" -> equipment = rogue(character, abilityModifiers, features)
                "Wizard" -> equipment = wizard(character, abilityModifiers, features)
            }
            return equipment
        }

        private fun cleric(character: CharacterBasic, abilityModifiers: Stats, features: Features): ArrayList<String> {
            val equipment: ArrayList<String> = arrayListOf("Shield", "Holy Symbol")
            if (character.race == "Dwarf") {
                val startingInitialWeapon: Array<String> = arrayOf("Mace", "Warhammer")
                equipment.add(Builder.itemToList("Select a starting weapon: ", "", startingInitialWeapon, startingInitialWeapon))
            }
            else {
                equipment.add("Mace")
            }
            val firstWeapon = equipment.last()
            val startingArmor = arrayOf("Scale Mail    AC: 13 + Dex modifier (2 max)\n",
                    "Leather Armor    AC: 11 + Dex modifier",
                    "Chain Mail    AC: 16")
            val startingArmorSelection = arrayOf("Scale Mail", "Leather Armor", "Chain Mail")
            equipment.add(Builder.itemToList("Select a starting armor: ","", startingArmor, startingArmorSelection))
            val startingWeapons = arrayOf("Club: 1d4 bludgeoning, light",
                "Dagger: 1d4 piercing, finesse, light, thrown (range 20/60)",
                "Greatclub: 1d8 bludgeoning, Two-handed",
                "Handaxe: 1d6 slashing, Light, thrown (range 20/60)",
                "Javelin: 1d6 piercing, Thrown (range 30/120)",
                "Light Hammer: 1d4 bludgeoning, Light, thrown (range 20/60)",
                "Mace: 1d6 bludgeoning",
                "Quarterstaff: 1d6 bludgeoning, Versatile (1d8)",
                "Sickle: 1d4 slashing, Light",
                "Spear: 1d6 piercing, Thrown (range 20/60), versatile (1d8)",
                "Crossbow, 20 bolts: 1d8 piercing, Ammunition (range 80/320), loading, two-handed",
                "Dart: 1d4 piercing, Finesse, thrown (range 20/60)",
                "Shortbow: 1d6 piercing, Ammunition (range 80/320),two-handed",
                "Sling: 1d4 bludgeoning, Ammunition (30/120)")
            val startingWeaponsSelection = arrayOf("Club", "Dagger", "Greatclub", "Handaxe", "Javelin", "Light Hammer", "Mace", "Quarterstaff", "Sickle", "Spear", "Crossbow w/ 20 bolts", "Dart", "Shortbow", "Sling")
            equipment.add(Builder.itemToList("Your Cleric has a $firstWeapon. \n" +
                    "Select a second weapon: ", "", startingWeapons, startingWeaponsSelection))
            val equipmentPack = arrayOf("Priest's Pack:\n" +
                    "backpack, blanket, 10 candles, tinderbox, alms box, 2 blocks of incense, censer, vestments, 2 days rations, waterskin",
                    "Explorer's Pack:\n" +
                    "backpack, bedroll, mess kit, tinderbox, 10 torches, 10 days rations, waterskin, 50 ft of hempen rope")
            val equipmentPackSelection = arrayOf("Priest's Pack", "Explorer's Pack")
            equipment.add(Builder.itemToList("Select an equipment pack: ", "", equipmentPack, equipmentPackSelection))
            return equipment
        }

        private fun fighter(character: CharacterBasic, abilityModifiers: Stats, features: Features): ArrayList<String> {
            val features = features
            val equipment: ArrayList<String> = ArrayList()
            val fightingStylesOption = arrayOf("Archery", "Defense", "Dueling", "Great Weapon Fighting", "Protection", "Two Weapon Fighting")
            for (i in 0..0) {
                features.cClass[0] = "Fighting Style: ${Builder.itemToList("Select a fighting style: ", "", fightingStylesOption, fightingStylesOption)}"
            }
            val startingArmor = arrayOf("Leather Armor, Longbow w/ 20 arrows         AC: 11 + Dex modifier",
                "Chain Mail                                  AC: 16")
            val startingArmorSelection = arrayOf("Leather Armor  Longbow w/ 20 arrows", "Chain Mail")
            when (Builder.itemToList("Select starting armor: ", "", startingArmor,  startingArmorSelection)) {
                "Leather Armor  Longbow w/ 20 armors" -> {
                    equipment.add("Leather Armor")
                    equipment.add("Longbow w/ 20 arrows")
                }
                "Chain Mail" -> equipment.add("Chain Mail")
            }
            
            val martialWeapons = arrayOf(" Battleaxe     1d8 slashing, Versatile (1d10)",
                " Flail         1d8 bludgeoning",
                " Glaive        1d10 slashing, Heavy, reach, two-handed",
                " Greataxe      1d12 slashing, Heavy, two-handed",
                " Greatsword    2d6 slashing, Heavy, two-handed",
                " Halberd       1d10 slashing, Heavy, reach, two-handed",
                " Longsword     1d8 slashing, Versatile",
                " Lance         1d12 piercing, Reach, special",
                " Maul          2d6 bludgeoning, Heavy, two-handed",
                "Morningstar   1d8 piercing",
                "Pike          1d6 piercing,	Heavy, reach, two-handed",
                "Rapier        1d6 piercing, Finesse",
                "Scimitar      1d6 slashing, Finesse, light",
                "Shortsword    1d4 piercing, Finesse, light",
                "Trident       1d8 piercing, Thrown (range 20/60), versatile (1d8)",
                "War pick      1d8 piercing",
                "Warhammer     1d8 bludgeoning, Versatile (1d10)",
                "Whip          1d4 slashing, Finesse, reach")
            val martialWeaponsSelected = arrayOf("Battleaxe", "Flail", "Glaive", "Greataxe", "Greatsword", "Halberd", "Lance", "Longsword", "Maul", "Morningstar", "Pike", "Rapier", "Scimitar", "Shortsword", "Trident", "War pick", "Warhammer", "Whip")
            val armsChoice = arrayOf("Weapon and Shield", "2 Weapons")
            when (Builder.itemToList("You can choose between a martial weapon w/ shield or 2 martial weapons. A shield adds +2 to AC when equipped.", "", armsChoice, armsChoice)) {
                "Weapon and Shield" -> {
                    equipment.add("Shield")
                    equipment.add(Builder.itemToList("Select a martial weapon: ", "", martialWeapons, martialWeaponsSelected))
                }
                "2 Weapons" -> {
                    for (i in 0..1) {
                        
                        equipment.add(Builder.itemToList("Select 2 martial weapons.\nSelection ${i + 1}:", "", martialWeapons, martialWeaponsSelected))
                    }
                }
            }
            val armsChoice2 = arrayOf("Light Crossbow w/ 20 bolts", "2 Handaxe")
            equipment.add(Builder.itemToList("Select between a Crossbow or 2 Handaxes: ", "", armsChoice2, armsChoice2))
            val equipmentPack = arrayOf("Dungeoneer's Pack: \n" +
                "backpack, crowbar, hammer, 10 pitons, 10 torches, tinderbox, 10 day's rations, waterskin. 50 feet of hempen rope\n",
                "Explorer’s Pack: \n" +
                "backpack, bedroll, mess kit, tinderbox, 10 torches, 10 day's rations, waterskin, 50 ft of hempen rope\n")
            val equipmentPackSelection = arrayOf("Dungeoneer's Pack", "Explorer's Pack")
            equipment.add(Builder.itemToList("Select an equipment pack: ", "", equipmentPack, equipmentPackSelection))
            
            return equipment
        }

        private fun rogue(character: CharacterBasic, abilityModifiers: Stats, features: Features): ArrayList<String> {
            val equipment = arrayListOf("Leather Armor", "2 Daggers", "Thieves' Tools")
            val armsChoice = arrayOf("Rapier         1d6 piercing, Finesse", "Shortsword     1d4 piercing, Finesse, light")
            val armsChoiceSelection = arrayOf("Rapier", "Shortsword")
            equipment.add(Builder.itemToList("Select one of two: ", "", armsChoice, armsChoiceSelection))
            val armsChoice2 = arrayOf("Shortbow & Quiver  1d6 piercing, Ammunition (range 80/320),two-handed\n" +
                    "  w/ 20 arrows", "Shortsword         1d4 piercing, Finesse, light")
            val armsChoiceSelection2 = arrayOf("Shortbow & Quiver w/ 20 arrows", "Shortsword")
            when (Builder.itemToList("Select one of two: ", "", armsChoice2, armsChoiceSelection2)) {
                "Shortbow & Quiver w/ 20 arrows" -> {
                    equipment.add("Shortbow & Quiver")
                    equipment.add("20 arrows")
                }
                "Shortsword" -> equipment.add("Shortsword")
            }
            val equipmentPack = arrayOf("Dungeoneer's Pack: \n" +
                    "backpack, crowbar, hammer, 10 pitons, 10 torches, tinderbox, 10 day's rations, waterskin. 50 feet of hempen rope\n",
                "Explorer's Pack: \n" +
                        "backpack, bedroll, mess kit, tinderbox, 10 torches, 10 day's rations, waterskin, 50 ft of hempen rope\n")
            val equipmentPackSelection = arrayOf("Dungeoneer's Pack", "Explorer's Pack")
            equipment.add(Builder.itemToList("Select an equipment pack: ", "", equipmentPack, equipmentPackSelection))
            return equipment
        }

        private fun wizard(character: CharacterBasic, abilityModifiers: Stats, features: Features): ArrayList<String> {
            val equipment = arrayListOf("Spellbook")
            val magicMedium = arrayOf("Component Pouch\n" +
                    "A small, watertight leather belt pouch that has compartments to hold all the material components and other\n" +
                    "special items you need to cast your spells, except for those components that have a specific cost\n" +
                    "(as indicated in a spell's description).\n",
                    "Arcane Focus\n" +
                    "A special item— an orb, a crystal, a rod, a specially constructed staff, a wand-like length of wood, or\n" +
                    "some similar item— designed to channel the power of arcane spells.\n")
            val magicMediumSelection = arrayOf("Component Pouch", "Arcane Focus")
            var medium = Builder.itemToList("Select a magic item:\n", "", magicMedium, magicMediumSelection)
            if (medium == "Arcane Focus") {
                val focusOptions = arrayOf("Orb", "Crystal", "Rod", "Staff", "Wooden Wand", "Other")
                val focusSelection = arrayOf("Orb", "Crystal", "Rod", "Staff", "Wooden Wand", "")
                medium = Builder.itemToList("Select a magic item:\n", "", focusOptions, focusSelection)
            }
            equipment.add(medium)
            val armsChoice = arrayOf("Quarterstaff  1d6 bludgeoning, Versatile (1d8)", "Dagger        1d4 piercing, finesse, light, thrown (range 20/60)")
            val armsChoiceSelection = arrayOf("Quarterstaff", "Dagger")
            equipment.add(Builder.itemToList("Select a weapon:\n", "", armsChoice, armsChoiceSelection))
            val equipmentPack = arrayOf("Scholar’s Pack:\n" +
                    "backpack, book of lore, bottle of ink, ink pen, 10 sheets of parchment, little bag of sand, small knife.\n",
                    "Explorer’s Pack:\n" +
                    "backpack, bedroll, mess kit, tinderbox, 10 torches, 10 day's rations, waterskin, 50 ft of hempen rope")
            val equipmentPackSelection = arrayOf("Scholar's Pack", "Explorer's Pack")
            equipment.add(Builder.itemToList("Select an equipment pack: ", "", equipmentPack, equipmentPackSelection))
            return equipment
        }

        fun duplicateItemStacker(list: ArrayList<String>): ArrayList<String> {
            val compiledList = ArrayList<String>()
            for (item in list) {
                var itemCount = 0
                for (i in list.indices) {
                    if (item == list[i]) {
                        itemCount++
                    }
                }
                if (itemCount == 1) {
                    compiledList.add(item)
                }
                else {
                    compiledList.add("$itemCount $item")
                }
            }
            return ArrayList(compiledList.distinct())
        }
    }
}