package Characters.Subclasses

import Characters.Character
import Characters.Stats
import Combat.Attack
import Equipment.Armor
import Equipment.Heirloom
import Equipment.Weapon
import GameDataManagement.CombatLogManager
import java.io.File
import java.io.IOException
import java.util.Random

class Hunter : Character {

    private var pet: AnimalCompanion

    constructor() : super() {
        this.pet = AnimalCompanion()
    }

    constructor(name: String, race: String, lvl: Int, isCPU: Boolean, petName: String, petRace: String)
            : super(name, race, lvl, isCPU) {
        this.pet = AnimalCompanion(petName, petRace, lvl, true)
    }

    constructor(name: String, race: String, lvl: Int, stats: Stats, isCPU: Boolean, petName: String, petRace: String)
            : super(name, race, lvl, stats, isCPU) {
        this.pet = AnimalCompanion(petName, petRace, lvl, true)
    }

    constructor(other: Hunter) : super(other) {
        this.pet = AnimalCompanion(other.pet)
    }

    @Throws(IOException::class)
    constructor(characterSheet: File) : super(characterSheet) {
        this.pet = AnimalCompanion()
    }

    fun getPetName(): String = pet.getName()
    fun getPetRace(): String = pet.getRace()

    override fun attack(): Attack {
        CombatLogManager.out("${getName()} and ${pet.getName()} attack in perfect sync!")
        return Attack(super.attack().dmgValue + pet.attack().dmgValue, "PHY")
    }

    override fun getClassName(): String = "Hunter"

    override fun equipHeirloom(h: Heirloom): Boolean {
        if (super.equipHeirloom(h)) return true
        return pet.equipHeirloom(h)
    }

    override fun onLevelUp() {
        val r = Random()
        if (r.nextInt(0, 100) >= 60)
            getStats().setHp(getStats().getHp() + (getStats().getHp() * 0.1).toInt())
        if (r.nextInt(0, 100) >= 40)
            getStats().setAtk(getStats().getAtk() + 1)
        if (r.nextInt(0, 100) >= 60)
            getStats().setArm(getStats().getArm() + 1)
        if (r.nextInt(0, 100) >= 15)
            getStats().setSpd(getStats().getSpd() + 2)
        if (r.nextInt(0, 100) >= 60)
            getStats().setRes(getStats().getRes() + 1)
        this.pet.levelUp()
    }

    override fun initializeStats(): Stats = Stats(100, 10, 10, 12, 10)

    override fun displaySpecialAction(): String = "2. Cheer on your pet"

    override fun performSpecialAction(): Attack {
        CombatLogManager.out("\"C'mon, ${pet.getName()} go get'em!\"")
        return Attack(0, "STA")
    }

    override fun isWeaponValid(w: Weapon): Boolean {
        return w.getType() == "Sword" || w.getType() == "Axe" || w.getType() == "Dagger" || w.getType() == "Bow"
    }

    override fun isArmorValid(a: Armor): Boolean = a.getType() == "Leather"

    private inner class AnimalCompanion : Character {

        constructor() : super()

        constructor(name: String, race: String, lvl: Int, isCPU: Boolean)
                : super(name, race, lvl, isCPU)

        constructor(name: String, race: String, lvl: Int, stats: Stats, isCPU: Boolean)
                : super(name, race, lvl, stats, isCPU)

        constructor(other: AnimalCompanion) : super(other)

        override fun equipHeirloom(heirloom: Heirloom): Boolean {
            if (heirloom.getType() != "Amulet") return false
            return super.equipHeirloom(heirloom)
        }

        override fun onLevelUp() {
            this.setStats(updateStats())
        }

        override fun initializeStats(): Stats = updateStats()

        override fun getClassName(): String = "Pet"

        override fun displaySpecialAction(): String = ""

        override fun performSpecialAction(): Attack = Attack(0, "STA")

        override fun isWeaponValid(w: Weapon): Boolean = false

        override fun isArmorValid(a: Armor): Boolean = false

        private fun updateStats(): Stats {
            val hunterStats = this@Hunter.getStats()
            return when (getRace().lowercase()) {
                "canid" -> Stats(
                    (hunterStats.getHp() * 0.2).toInt(), (hunterStats.getAtk() * 0.2).toInt(),
                    (hunterStats.getArm() * 0.2).toInt(), (hunterStats.getSpd() * 0.2).toInt(),
                    (hunterStats.getRes() * 0.2).toInt()
                )
                "feline" -> Stats(
                    (hunterStats.getHp() * 0.15).toInt(), (hunterStats.getAtk() * 0.3).toInt(),
                    (hunterStats.getArm() * 0.15).toInt(), (hunterStats.getSpd() * 0.3).toInt(),
                    (hunterStats.getRes() * 0.15).toInt()
                )
                "raptor" -> Stats(
                    (hunterStats.getHp() * 0.05).toInt(), (hunterStats.getAtk() * 0.15).toInt(),
                    (hunterStats.getArm() * 0.05).toInt(), (hunterStats.getSpd() * 0.35).toInt(),
                    (hunterStats.getRes() * 0.25).toInt()
                )
                else -> throw IllegalArgumentException("The animal companion species must be either Canid, Feline or Raptor.")
            }
        }
    }
}