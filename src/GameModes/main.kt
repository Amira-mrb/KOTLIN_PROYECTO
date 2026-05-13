import Characters.Stats
import Characters.Subclasses.*
import Combat.Attack
import Equipment.*
import GameDataManagement.CombatLogManager

fun main() {
    println("========== TEST ALL CLASSES ==========")

    val warrior = Warrior("Arthas", "Human", 1, false)
    val mage = Mage("Jaina", "Human", 1, Stats(80, 5, 5, 10, 6, 15, 0), false)
    val priest = Priest("Anduin", "Human", 1, false)
    val paladin = Paladin("Uther", "Human", 1, false)
    val thief = Thief("Valeera", "Elf", 1, false)
    val hunter = Hunter("Rexxar", "Orc", 1, false, "Misha", "Canid")
    val monster = Monster("Abomination", "Undead", 3, true)
    val chars = listOf(warrior, mage, priest, paladin, thief, hunter, monster)

    println("\n--- Characters created ---")
    for (c in chars)
        println("  ${c.getClassName()} - ${c.getName()} (Lvl ${c.getLvl()}) - ${c.getStats()}")

    println("\n--- Leveling up ---")
    warrior.levelUp()
    println("  Warrior Lvl ${warrior.getLvl()} -> ${warrior.getStats()}")

    println("\n--- Equipping ---")
    val sword = Weapon("Longsword", Stats(8, 0, 0, 0, 0, 0, 0), "common", 1, "Sword")
    val staff = Weapon("Wizard Staff", Stats(3, 0, 0, 0, 0, 5, 0), "common", 2, "Staff")
    val dagger = Weapon("Shadow Dagger", Stats(5, 0, 1, 0, 0, 0, 0), "common", 1, "Dagger")
    val helm = Armor("Steel Helm", Stats(0, 0, 0, 0, 0, 0, 0), "common", 1, "Helm", "Metal")
    val chest = Armor("Steel Chest", Stats(0, 0, 0, 0, 0, 0, 0), "common", 1, "Chestguard", "Metal")
    val ring = Heirloom("Ring of Power", Stats(0, 2, 0, 0, 0, 0, 0), "rare", 1, "Ring")

    warrior.equipWeapon(sword)
    println("  Warrior equipped weapon: ${warrior.getWeapon()?.getName()}")
    warrior.equipArmor(helm)
    warrior.equipArmor(chest)
    println("  Warrior armor pieces: ${warrior.getArmor().size}")
    warrior.equipHeirloom(ring)
    println("  Warrior heirlooms: ${warrior.getHeirlooms().size}")

    mage.equipWeapon(staff)
    println("  Mage equipped weapon: ${mage.getWeapon()?.getName()}")
    thief.equipWeapon(dagger)
    println("  Thief equipped weapon: ${thief.getWeapon()?.getName()}")

    println("\n--- CombatLogManager ---")
    CombatLogManager.out("Test log entry")

    println("\n--- Combat simulation ---")
    val atk = warrior.attack()
    println("  Warrior attacks: ${atk.dmgValue} ${atk.dmgType}")
    thief.defend(atk)
    println("  Thief HP after: ${thief.getStats().getHp()}")

    println("\n--- Warrior fury ---")
    warrior.toggleFury()
    val furyAtk = warrior.attack()
    println("  Furious attack: ${furyAtk.dmgValue} ${furyAtk.dmgType}")

    println("\n--- Sorting by SPD ---")
    val sorted = chars.sortedDescending()
    for (c in sorted)
        println("  ${c.getClassName()} - ${c.getName()} (SPD: ${c.getStats().getSpd()}, Lvl: ${c.getLvl()})")

    println("\n--- Character sheets ---")
    println(warrior.toString())

    println("\n--- Copy constructor ---")
    val warriorCopy = Warrior(warrior)
    println("  Copy: ${warriorCopy.getClassName()} - ${warriorCopy.getName()}")
    println("  Stats match: ${warrior.getStats().getHp() == warriorCopy.getStats().getHp()}")

    println("\n--- Monster ---")
    println("  ${monster.getClassName()} - ${monster.getName()} (${monster.getRace()})")
    println("  Stats: ${monster.getStats()}")

    println("\n--- Hunter companion ---")
    println("  ${hunter.getName()} has pet ${hunter.getPetName()} (${hunter.getPetRace()})")

    println("\n========== ALL TESTS PASSED ==========")
}