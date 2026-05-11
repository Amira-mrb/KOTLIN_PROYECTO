package Characters.Subclasses

import Characters.Stats
import Combat.Attack
import Equipment.Armor
import Equipment.Weapon
import GameDataManagement.CombatLogManager
import java.io.File
import java.io.IOException
import java.util.Random
import java.util.Scanner

class Paladin : Faithful {

    constructor() : super()

    constructor(name: String, race: String, lvl: Int, isCPU: Boolean)
            : super(name, race, lvl, isCPU)

    constructor(name: String, race: String, lvl: Int, stats: Stats, isCPU: Boolean)
            : super(name, race, lvl, stats, isCPU)

    constructor(other: Paladin) : super(other)

    @Throws(IOException::class)
    constructor(characterSheet: File) : super(characterSheet)

    fun getClassName(): String {
        return "Paladin"
    }

    override fun onLevelUp() {
        val r = Random()

        if (r.nextInt(0, 100) >= 50) // HP increase
            getStats().setHp(getStats().getHp() + (getStats().getHp() * 0.15).toInt())

        if (r.nextInt(0, 100) >= 40) // ATK increase
            getStats().setAtk(getStats().getAtk() + 1)

        if (r.nextInt(0, 100) >= 30) // ARM increase
            getStats().setArm(getStats().getArm() + 2)

        if (r.nextInt(0, 100) >= 85) // SPD increase
            getStats().setSpd(getStats().getSpd() + 1)

        if (r.nextInt(0, 100) >= 50) // RES increase
            getStats().setRes(getStats().getRes() + 1)

        if (r.nextInt(0, 100) >= 70) // FTH increase
            getStats().setFth(getStats().getFth() + 3)
    }

    override fun initializeStats(): Stats {
        return Stats(120, 10, 15, 5, 8, 0, 10)
    }

    override fun isWeaponValid(w: Weapon): Boolean {
        return w.getType() != "Bow" && w.getType() != "Staff"
    }

    override fun isArmorValid(a: Armor): Boolean {
        return a.getType() == "Metal"
    }

    fun castMiracle(): Attack {
        val scan = Scanner(System.`in`)
        val cast: Attack

        println("1. Imbue weapon - Coat your weapon with the holiness of your faith.")
        println("2. Bastion of faith - Protect yourself, faithfully.")
        println("3. Sacred blast - Blind your opponent with your god's splendor.")
        println("What do you want to cast?")

        when (scan.nextLine()) {
            "1" -> {
                CombatLogManager.out("${getName()} prays: Imbue weapon!")
                cast = Attack(0, "STA")
            }
            "2" -> {
                CombatLogManager.out("${getName()} prays: Bastion of faith!")
                cast = Attack(0, "STA")
            }
            "3" -> {
                CombatLogManager.out("${getName()} prays: Sacred blast!")
                cast = Attack(0, "STA")
            }
            else -> throw IllegalArgumentException("Choose a valid miracle.")
        }

        return cast
    }
}
