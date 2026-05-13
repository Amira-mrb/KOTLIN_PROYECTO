package Characters.Subclasses

import Characters.Character
import Characters.Stats
import Combat.Attack
import Equipment.Armor
import Equipment.Weapon
import GameDataManagement.CombatLogManager
import java.io.File
import java.util.Random

/**
 * Clase Warrior.
 * Personaje físico que puede entrar en estado de furia.
 */
class Warrior : Character {

    private var fury: Boolean = false
    private var extraWeapon: Weapon? = null

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
    constructor(other: Warrior) : super(other) {
        extraWeapon = if (other.extraWeapon != null) Weapon(other.extraWeapon!!) else null
        fury = other.fury
    }

    /**
     * Constructor desde archivo.
     */
    constructor(characterSheet: File) : super(characterSheet)

    /**
     * Devuelve si el guerrero está en furia.
     */
    fun isFury(): Boolean = fury

    /**
     * Activa o desactiva la furia.
     */
    fun toggleFury() {
        if (fury)
            println("${getName()} calms down and face their enemy.")
        else
            println("${getName()} feels the RAGE!")
        fury = !fury
    }

    /**
     * Ataque del guerrero.
     * Si está en furia, hace el doble de daño físico.
     */
    override fun attack(): Attack {
        return if (fury) {
            CombatLogManager.out("${getName()} is furious and attacks recklessly!")
            Attack(getStats().atk * 2, "PHY")
        } else {
            super.attack()
        }
    }

    /**
     * Defensa del guerrero.
     * Reduce daño según armadura o resistencia.
     */
    override fun defend(attack: Attack) {
        val totalDmg = when (attack.getDmgType()) {
            "PHY" -> attack.getDmgValue() - (getStats().arm / 2)
            "MAG" -> attack.getDmgValue() - (getStats().res / 2)
            else -> throw IllegalArgumentException("Damage type must be PHY for physical or MAG for magical.")
        }

        if (totalDmg <= 0 && attack.getDmgType() != "STA")
            CombatLogManager.out("It doesn't even make a dent.")
        else if (attack.getDmgType() != "STA") {
            CombatLogManager.out("${getName()} receives $totalDmg points of damage.")
            getStats().receiveDmg(totalDmg)
        }
    }

    /**
     * Nombre de la clase.
     */
    override fun getClassName(): String = "Warrior"

    /**
     * Permite equipar un arma secundaria si el arma principal es de una mano.
     */
    fun equipExtraWeapon(w: Weapon): Boolean {
        if (getWeapon()!!.getHands() != 1 || !isWeaponValid(w)) return false
        extraWeapon = Weapon(w)
        return true
    }

    /**
     * Subida de nivel del guerrero.
     * Aumenta stats según probabilidades.
     */
    override fun onLevelUp() {
        val r = Random()

        if (r.nextInt(0, 100) >= 20)
            getStats().hp += (getStats().hp * 0.1).toInt()

        if (r.nextInt(0, 100) >= 25)
            getStats().atk += 2

        if (r.nextInt(0, 100) >= 25)
            getStats().arm += 1

        if (r.nextInt(0, 100) >= 50)
            getStats().spd += 1

        if (r.nextInt(0, 100) >= 80)
            getStats().res += 1
    }

    /**
     * Stats base del guerrero.
     */
    override fun initializeStats(): Stats = Stats(120, 15, 10, 10, 5)

    /**
     * Texto de la acción especial.
     */
    override fun displaySpecialAction(): String = "2. Toggle fury."

    /**
     * Activa o desactiva la furia.
     */
    override fun performSpecialAction(): Attack {
        toggleFury()
        return Attack(0, "STA")
    }

    /**
     * Armas permitidas para el guerrero.
     */
    override fun isWeaponValid(w: Weapon): Boolean {
        return w.getType() == "Scepter" ||
                w.getType() == "Bow" ||
                w.getType() == "Staff"
    }

    /**
     * Armadura permitida para el guerrero.
     */
    override fun isArmorValid(a: Armor): Boolean {
        return a.getType() == "Metal"
    }
}
