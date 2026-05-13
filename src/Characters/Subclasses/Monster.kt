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

class Monster : Character {

    constructor() : super()

    constructor(name: String, race: String) : super(name, race, 1, true)

    constructor(name: String, race: String, lvl: Int, isCPU: Boolean)
            : super(name, race, lvl, isCPU)

    constructor(name: String, race: String, lvl: Int, stats: Stats, isCPU: Boolean)
            : super(name, race, lvl, stats, isCPU)

    constructor(other: Character) : super(other)

    @Throws(IOException::class)
    constructor(characterSheet: File) : super(characterSheet)

    override fun attack(): Attack {
        CombatLogManager.out("The ${getRace()} ${getName()} attacks!")
        return super.attack()
    }

    override fun takeTurn(): Attack = attack()

    override fun getClassName(): String = "Monster"

    override fun equipHeirloom(h: Heirloom): Boolean {
        if (getRace() != "Beast" || h.getType() != "Amulet") return false
        return super.equipHeirloom(h)
    }

    override fun onLevelUp() {
        val r = Random()
        when (getRace()) {
            "Beast" -> {
                if (r.nextInt(0, 100) >= 50)
                    getStats().setHp(getStats().getHp() + (getStats().getHp() * 0.1).toInt())
                if (r.nextInt(0, 100) >= 20)
                    getStats().setAtk(getStats().getAtk() + 1)
                if (r.nextInt(0, 100) >= 85)
                    getStats().setArm(getStats().getArm() + 1)
                if (r.nextInt(0, 100) >= 20)
                    getStats().setSpd(getStats().getSpd() + 1)
                if (r.nextInt(0, 100) >= 85)
                    getStats().setRes(getStats().getRes() + 1)
            }
            "Undead" -> {
                if (r.nextInt(0, 100) >= 70)
                    getStats().setHp(getStats().getHp() + (getStats().getHp() * 0.1).toInt())
                if (r.nextInt(0, 100) >= 50)
                    getStats().setAtk(getStats().getAtk() + 1)
                if (r.nextInt(0, 100) >= 70)
                    getStats().setArm(getStats().getArm() + 1)
                if (r.nextInt(0, 100) >= 95)
                    getStats().setSpd(getStats().getSpd() + 1)
                if (r.nextInt(0, 100) >= 30)
                    getStats().setRes(getStats().getRes() + 4)
            }
            "Giant" -> {
                if (r.nextInt(0, 100) >= 50)
                    getStats().setHp(getStats().getHp() + (getStats().getHp() * 0.2).toInt())
                else
                    getStats().setHp(getStats().getHp() + (getStats().getHp() * 0.3).toInt())
                if (r.nextInt(0, 100) >= 50)
                    getStats().setAtk(getStats().getAtk() + 1)
                if (r.nextInt(0, 100) >= 50)
                    getStats().setArm(getStats().getArm() + 1)
                if (r.nextInt(0, 100) >= 90)
                    getStats().setSpd(getStats().getSpd() + 1)
                if (r.nextInt(0, 100) >= 90)
                    getStats().setRes(getStats().getRes() + 1)
            }
            else -> throw IllegalArgumentException("Race of the monster must be either Beast, Undead or Giant")
        }
    }

    override fun initializeStats(): Stats = when (getRace()) {
        "Beast" -> Stats(100, 12, 5, 12, 5)
        "Undead" -> Stats(70, 10, 6, 2, 20)
        "Giant" -> Stats(150, 10, 10, 2, 2)
        else -> throw IllegalArgumentException("Race of the monster must be either Beast, Undead or Giant")
    }

    override fun displaySpecialAction(): String = ""

    override fun performSpecialAction(): Attack = Attack(0, "STA")

    override fun isWeaponValid(w: Weapon): Boolean = getRace() == "Undead"

    override fun isArmorValid(a: Armor): Boolean = getRace() == "Giant"
}