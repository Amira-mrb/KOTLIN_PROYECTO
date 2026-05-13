package Characters.Subclasses

import Characters.Character
import Characters.Stats
import Combat.Attack
import Equipment.Armor
import Equipment.Weapon
import GameDataManagement.CombatLogManager
import java.io.File
import java.io.IOException
import java.util.Random
import java.util.Scanner

/**
 * Clase Mage.
 * Personaje centrado en magia y hechizos.
 */
class Mage : Character {

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
    constructor(other: Mage) : super(other)

    /**
     * Constructor desde archivo.
     */
    @Throws(IOException::class)
    constructor(characterSheet: File) : super(characterSheet)

    /**
     * Nombre de la clase.
     */
    override fun getClassName(): String = "Mage"

    /**
     * Lanza un hechizo según la opción elegida.
     */
    private fun castSpell(spell: String): Attack {
        return when (spell) {
            "1" -> {
                CombatLogManager.out("${getName()} incantates: Fireball!")
                Attack((getStats().mag * 0.7).toInt(), "MAG")
            }
            "2" -> {
                CombatLogManager.out("${getName()} incantates: Arcane shield!")
                Attack(0, "STA")
            }
            "3" -> {
                CombatLogManager.out("${getName()} incantates: Zephyr!")
                Attack((getStats().mag * 0.3).toInt(), "MAG")
            }
            "4" -> {
                CombatLogManager.out("${getName()} incantates: Mind surge!")
                Attack(0, "STA")
            }
            else -> throw IllegalArgumentException("Choose a valid spell.")
        }
    }

    /**
     * Subida de nivel del mago.
     * Aumenta stats según probabilidades.
     */
    override fun onLevelUp() {
        val r = Random()

        if (r.nextInt(0, 100) >= 65)
            getStats().hp += (getStats().hp * 0.1).toInt()

        if (r.nextInt(0, 100) >= 85)
            getStats().atk += 1

        if (r.nextInt(0, 100) >= 65)
            getStats().arm += 1

        if (r.nextInt(0, 100) >= 35)
            getStats().spd += 1

        if (r.nextInt(0, 100) >= 20)
            getStats().res += 1

        if (r.nextInt(0, 100) >= 15)
            getStats().mag += 3
    }

    /**
     * Stats base del mago.
     */
    override fun initializeStats(): Stats = Stats(90, 5, 8, 10, 15, 30, 0)

    /**
     * Texto de la acción especial.
     */
    override fun displaySpecialAction(): String = "2. Cast a spell"

    /**
     * Muestra los hechizos y lanza el elegido.
     */
    override fun performSpecialAction(): Attack {
        val scan = Scanner(System.`in`)
        println("1. Fireball - Blast your opponent with a fiery incandescent orb.")
        println("2. Arcane shield - Protect yourself, magely.")
        println("3. Zephyr - Strong wind blasts your opponent away.")
        println("4. Mind surge - Your magic improves your reaction speed.")
        println("What do you want to cast?")
        return castSpell(scan.nextLine())
    }

    /**
     * Armas permitidas para el mago.
     */
    override fun isWeaponValid(w: Weapon): Boolean {
        return w.getType() == "Staff" || w.getType() == "Scepter"
    }

    /**
     * Armadura permitida para el mago.
     */
    override fun isArmorValid(a: Armor): Boolean {
        return a.getType() == "Cloth"
    }
}
