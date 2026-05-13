package Equipment

import Characters.Stats

/**
 * Clase Heirloom.
 * Representa un objeto especial que puede equipar el personaje.
 * Puede ser un anillo o un amuleto.
 */
class Heirloom : Equipment {

    private var type: String = ""   // ring o amulet

    /**
     * Constructor por defecto. Crea un heirloom vacío.
     */
    constructor() : super() {
        type = ""
    }

    /**
     * Constructor con parámetros para crear un heirloom completo.
     */
    constructor(
        name: String,
        equipmentStats: Stats,
        rarity: String,
        value: Int,
        type: String
    ) : super(name, equipmentStats, rarity, value) {
        setType(type)
    }

    /**
     * Constructor de copia.
     */
    constructor(other: Heirloom) : super(other) {
        this.type = other.type
    }

    /** Devuelve el tipo de heirloom. */
    fun getType(): String = type

    /**
     * Asigna el tipo de heirloom.
     * Solo acepta ring o amulet.
     */
    fun setType(type: String) {
        when (type.lowercase()) {
            "ring", "amulet" -> this.type = type
            else -> throw IllegalArgumentException("Heirlooms can only be rings or amulets.")
        }
    }

    /** Tipo de equipamiento. */
    override fun getEquipmentType(): String = "Heirloom"

    /** Información específica del objeto. */
    override fun getEquipmentSpecs(): String = "Type:$type"

    /**
     * Compara dos heirlooms.
     * Devuelve true si tienen los mismos datos.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Heirloom) return false
        if (!super.equals(other)) return false

        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
