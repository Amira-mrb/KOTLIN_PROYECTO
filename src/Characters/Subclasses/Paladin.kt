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
 * Clase Paladin.
 * Guerrero sagrado que combina fuerza física y fe.
 */
class Paladin : Faithful {

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
    constructor(other: Paladin) : super(other)

    /**
     * Constructor desde archivo.
     */
    @Throws(IOException::class)
    constructor(characterSheet: File) : super(characterSheet)

    /**
     * Nombre de la clase.
     */
    override fun getClassName(): String = "Paladin"

    /**
     * Subida de nivel del paladín.
     * Aumenta stats según probabilidades.
     */
    override fun onLevelUp() {
        val r = Random()

        if (r.nextInt(0, 100) >= 50)
            getStats().hp += (getStats().hp * 0.15).toInt()

        if (r.nextInt(0, 100) >= 40)
            getStats().atk += 1

        if (r.nextInt(0, 100) >= 30)
            getStats().arm += 2

        if (r.nextInt(0, 100) >= 85)
            getStats().spd += 1

        if (r.nextInt(0, 100) >= 50)
            getStats().res += 1

        if (r.nextInt(0, 100) >= 70)
            getStats().fth += 3
    }

    /**
     * Stats base del paladín.
     */
    override fun initializeStats(): Stats = Stats(120, 10, 15, 5, 8, 0, 10)

    /**
     * Armas permitidas para el paladín.
     * No puede usar arcos ni báculos.
     */
    override fun isWeaponValid(w: Weapon): Boolean {
        return w.getType() != "Bow" && w.getType() != "Staff"
    }

    /**
     * Armadura permitida para el paladín.
     * Solo puede usar armadura metálica.
     */
    override fun isArmorValid(a: Armor): Boolean {
        return a.getType() == "Metal"
    }

    /**
     * Milagros del paladín.
     * Elige uno y devuelve su efecto.
     */
    override fun castMiracle(): Attack {
        val scan = Scanner(System.`in`)

        println("1. Imbue weapon - Coat your weapon with the holiness of your faith.")
        println("2. Bastion of faith - Protect yourself, faithfully.")
        println("3. Sacred blast - Blind your opponent with your god's splendor.")
        println("What do you want to cast?")

        return when (scan.nextLine()) {
            "1" -> {
                CombatLogManager.out("${getName()} prays: Imbue weapon!")
                Attack(0, "STA")
            }
            "2" -> {
                CombatLogManager.out("${getName()} prays: Bastion of faith!")
                Attack(0, "STA")
            }
            "3" -> {
                CombatLogManager.out("${getName()} prays: Sacred blast!")
                Attack(0, "STA")
            }
            else -> throw IllegalArgumentException("Choose a valid miracle.")
        }
    }
}
