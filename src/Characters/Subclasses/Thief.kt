package Characters.Subclasses

import Characters.Character
import Characters.Stats
import Combat.Attack
import Equipment.Armor
import Equipment.Weapon
import GameDataManagement.CombatLogManager
import java.util.Random
import javax.xml.stream.events.Characters

class Thief : Characters {

    constructor() : super()

    constructor(name: String, race: String, lvl: Int, isCPU: Boolean)
            : super(name, race, lvl, isCPU)

    constructor(name: String, race: String, lvl: Int, stats: Stats, isCPU: Boolean)
            : super(name, race, lvl, stats, isCPU)

    constructor(other: Thief) : super(other)

    override fun getClassName(): String {
        return "Thief"
    }

    override fun onLevelUp() {
        val r = Random()

        if (r.nextInt(0, 100) >= 60) // HP increase
            getStats().setHp(getStats().getHp() + (getStats().getHp() * 0.1).toInt())

        if (r.nextInt(0, 100) >= 40) // ATK increase
            getStats().setAtk(getStats().getAtk() + 1)

        if (r.nextInt(0, 100) >= 60) // ARM increase
            getStats().setArm(getStats().getArm() + 1)

        if (r.nextInt(0, 100) >= 15) // SPD increase
            getStats().setSpd(getStats().getSpd() + 2)

        if (r.nextInt(0, 100) >= 60) // RES increase
            getStats().setRes(getStats().getRes() + 1)
    }

    override fun initializeStats(): Stats {
        return Stats(90, 12, 8, 15, 8)
    }

    override fun displaySpecialAction(): String {
        return "2. Steal"
    }

    override fun performSpecialAction(): Attack {
        CombatLogManager.out("${getName()} violently mugs their opponent!")
        return Attack(getStats().getSpd(), "MAG")
    }

    override fun isWeaponValid(w: Weapon): Boolean {
        return w.getType() == "Dagger" || w.getType() == "Sword"
    }

    override fun isArmorValid(a: Armor): Boolean {
        return a.getType() == "Leather"
    }
}
