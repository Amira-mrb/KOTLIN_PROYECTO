package Characters.Subclasses

import Characters.Character
import Characters.Stats
import Combat.Attack
import Equipment.Armor
import Equipment.Weapon
import GameDataManagement.CombatLogManager
import java.util.Random

/**
 * Clase Thief.
 * Personaje rápido y centrado en el daño por velocidad.
 */
class Thief : Character {

    /**
     * Constructor por defecto.
     */
    constructor() : super()

    /**
     * Constructor con datos básicos.
     */
    constructor(name: String, race: String, lvl: Int, isCPU: Boolean)
            : super(name, race, lvl, isCPU)

    /**
     * Constructor con stats.
     */
    constructor(name: String, race: String, lvl: Int, stats: Stats, isCPU: Boolean)
            : super(name, race, lvl, stats, isCPU)

    /**
     * Constructor de copia.
     */
    constructor(other: Thief) : super(other)

    /**
     * Nombre de la clase.
     */
    override fun getClassName(): String = "Thief"

    /**
     * Subida de nivel del ladrón.
     * Aumenta stats según probabilidades.
     */
    override fun onLevelUp() {
        val r = Random()

        if (r.nextInt(0, 100) >= 60)
            getStats().hp += (getStats().hp * 0.1).toInt()

        if (r.nextInt(0, 100) >= 40)
            getStats().atk += 1

        if (r.nextInt(0, 100) >= 60)
            getStats().arm += 1

        if (r.nextInt(0, 100) >= 15)
            getStats().spd += 2

        if (r.nextInt(0, 100) >= 60)
            getStats().res += 1
    }

    /**
     * Stats base del ladrón.
     */
    override fun initializeStats(): Stats = Stats(90, 12, 8, 15, 8)

    /**
     * Texto de la acción especial.
     */
    override fun displaySpecialAction(): String = "2. Steal"

    /**
     * Acción especial del ladrón.
     * Hace daño basado en la velocidad.
     */
    override fun performSpecialAction(): Attack {
        CombatLogManager.out("${getName()} violently mugs their opponent!")
        return Attack(getStats().spd, "MAG")
    }

    /**
     * Armas permitidas para el ladrón.
     */
    override fun isWeaponValid(w: Weapon): Boolean {
        return w.getType() == "Dagger" || w.getType() == "Sword"
    }

    /**
     * Armadura permitida para el ladrón.
     */
    override fun isArmorValid(a: Armor): Boolean {
        return a.getType() == "Leather"
    }
}
