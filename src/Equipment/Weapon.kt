package Equipment

import Characters.Stats

/**
 * Clase Weapon.
 * Representa un arma que puede equipar el personaje.
 * Incluye su tipo y si usa una o dos manos.
 */
class Weapon : Equipment {

    private var hands: Int = -1      // 1 o 2 manos
    private var type: String = ""    // tipo de arma

    /**
     * Constructor por defecto.
     */
    constructor() : super() {
        hands = -1
        type = ""
    }

    /**
     * Constructor con datos.
     */
    constructor(
        name: String,
        equipmentStats: Stats,
        rarity: String,
        value: Int,
        type: String
    ) : super(name, equipmentStats, rarity, value) {
        setType(type)
        setHands()
    }

    /**
     * Constructor de copia.
     */
    constructor(other: Weapon) : super(other) {
        this.hands = other.hands
        this.type = other.type
    }

    /** Devuelve cuántas manos necesita el arma. */
    fun getHands(): Int = hands

    /**
     * Asigna si es de 1 o 2 manos según el tipo.
     */
    fun setHands() {
        when (type.lowercase()) {
            "sword", "mace", "axe", "scepter", "dagger" -> hands = 1
            "greatsword", "hammer", "bow", "staff" -> hands = 2
            else -> throw IllegalArgumentException("Can't set weapon hands with wrong weapon type.")
        }
    }

    /** Devuelve el tipo de arma. */
    fun getType(): String = type

    /**
     * Asigna el tipo de arma.
     */
    fun setType(type: String) {
        when (type.lowercase()) {
            "sword", "mace", "axe", "scepter", "dagger",
            "greatsword", "hammer", "bow", "staff" -> this.type = type
            else -> throw IllegalArgumentException("Weapon type not admited.")
        }
    }

    /** Tipo de equipamiento. */
    override fun getEquipmentType(): String = "Weapon"

    /** Información específica del arma. */
    override fun getEquipmentSpecs(): String = "Type:$type"

    /**
     * Compara dos armas.
     * Devuelve true si tienen los mismos datos.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Weapon) return false
        if (!super.equals(other)) return false

        if (hands != other.hands) return false
        if (type != other.type) return false

        return true
    }

    /**
     * hashCode según los datos del arma.
     */
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + hands
        result = 31 * result + type.hashCode()
        return result
    }
}
