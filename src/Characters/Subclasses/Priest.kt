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

/**
 * Clase Priest.
 * Personaje centrado en la fe y los milagros de apoyo.
 */
class Priest : Faithful {

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
    constructor(other: Priest) : super(other)

    /**
     * Constructor desde archivo.
     */
    @Throws(IOException::class)
    constructor(characterSheet: File) : super(characterSheet)

    /**
     * Nombre de la clase.
     */
    override fun getClassName(): String = "Priest"

    /**
     * Subida de nivel del sacerdote.
     * Aumenta stats según probabilidades.
     */
    override fun onLevelUp() {
        val r = Random()

        if (r.nextInt(0, 100) >= 80)
            getStats().hp += (getStats().hp * 0.1).toInt()

        if (r.nextInt(0, 100) >= 90)
            getStats().atk += 1

        if (r.nextInt(0, 100) >= 80)
            getStats().arm += 2

        if (r.nextInt(0, 100) >= 50)
            getStats().spd += 1

        if (r.nextInt(0, 100) >= 20)
            getStats().res += 2

        if (r.nextInt(0, 100) >= 20)
            getStats().fth += 4
    }

    /**
     * Stats base del sacerdote.
     */
    override fun initializeStats(): Stats = Stats(75, 5, 5, 10, 15, 0, 20)

    /**
     * Armas permitidas para el sacerdote.
     * Solo puede usar báculos.
     */
    override fun isWeaponValid(w: Weapon): Boolean = w.getType() == "Staff"

    /**
     * Armadura permitida para el sacerdote.
     * Solo puede usar tela.
     */
    override fun isArmorValid(a: Armor): Boolean = a.getType() == "Cloth"

    /**
     * Milagros del sacerdote.
     * Elige uno y devuelve su efecto.
     */
    override fun castMiracle(): Attack {
        val scan = Scanner(System.`in`)

        println("1. Heal - Cure some of your target's wounds.")
        println("2. Holy prayer - Partially restores all of your party members.")
        println("3. Divine punishment - Ask your god to smite your enemies in a blast of light.")
        println("What do you want to cast?")

        return when (scan.nextLine()) {
            "1" -> {
                CombatLogManager.out("${getName()} prays: Heal!")
                Attack(0, "STA")
            }
            "2" -> {
                CombatLogManager.out("${getName()} prays: Holy prayer!")
                Attack(0, "STA")
            }
            "3" -> {
                CombatLogManager.out("${getName()} prays: Divine punishment!")
                Attack((getStats().fth * 0.55).toInt(), "MAG")
            }
            else -> throw IllegalArgumentException("Choose a valid miracle.")
        }
    }
}
