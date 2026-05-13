package Equipment

import Characters.Stats

/**
 * Clase Armor.
 * Representa una pieza de armadura que puede equipar el personaje.
 * Incluye el tipo de material y la parte del cuerpo que cubre.
 */
class Armor : Equipment {

    private var type: String = ""   // cloth, leather o metal
    private var piece: String = ""  // helm, chestguard, etc.

    /**
     * Constructor por defecto.
     */
    constructor() : super() {
        type = ""
        piece = ""
    }

    /**
     * Constructor con datos.
     *
     * @param name nombre del objeto
     * @param equipmentStats estadísticas que aporta
     * @param rarity rareza
     * @param value valor
     * @param piece parte del cuerpo que cubre
     * @param type tipo de material
     */
    constructor(
        name: String,
        equipmentStats: Stats,
        rarity: String,
        value: Int,
        piece: String,
        type: String
    ) : super(name, equipmentStats, rarity, value) {
        setPiece(piece)
        setType(type)
    }

    /**
     * Constructor de copia.
     */
    constructor(other: Armor) : super(other) {
        this.type = other.type
        this.piece = other.piece
    }

    /** Devuelve el tipo de material. */
    fun getType(): String = type

    /**
     * Asigna el tipo de material.
     * Solo acepta cloth, leather o metal.
     */
    fun setType(type: String) {
        when (type.lowercase()) {
            "cloth", "leather", "metal" -> this.type = type
            else -> throw IllegalArgumentException("Armor can only be either cloth, leather or metal.")
        }
    }

    /** Devuelve la pieza de armadura. */
    fun getPiece(): String = piece

    /**
     * Asigna la pieza de armadura.
     * Solo acepta piezas válidas.
     */
    fun setPiece(piece: String) {
        when (piece.lowercase()) {
            "helm", "pauldrons", "chestguard", "gauntlets", "greaves", "boots" ->
                this.piece = piece
            else -> throw IllegalArgumentException(
                "Armor pieces can only be helm, pauldrons, chestguard, gauntlets, greaves or boots."
            )
        }
    }

    /** Tipo de equipamiento. */
    override fun getEquipmentType(): String = "Armor"

    /** Información específica de la armadura. */
    override fun getEquipmentSpecs(): String {
        return "Piece:$piece\nArmor type:$type"
    }

    /**
     * Compara dos armaduras.
     * Devuelve true si tienen los mismos datos.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Armor) return false
        if (!super.equals(other)) return false

        if (type != other.type) return false
        if (piece != other.piece) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + piece.hashCode()
        return result
    }
}
