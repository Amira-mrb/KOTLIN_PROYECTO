package Equipment

import Characters.Stats

class Armor : Equipment {

    private var type: String = ""
    private var piece: String = ""

    // Constructor por defecto
    constructor() : super() {
        type = ""
        piece = ""
    }

    // Constructor con parámetros (igual que en Java)
    constructor(
        name: String,
        equipmentStats: Stats,
        rarity: String,
        value: Int,
        type: String,
        piece: String
    ) : super(name, equipmentStats, rarity, value) {
        setType(type)
        setPiece(piece)
    }

    // Constructor de copia
    constructor(other: Armor) : super(other) {
        this.type = other.type
        this.piece = other.piece
    }

    fun getType(): String {
        return type
    }

    @Throws(IllegalArgumentException::class)
    fun setType(type: String) {
        when (type.lowercase()) {
            "cloth", "leather", "metal" -> this.type = type
            else -> throw IllegalArgumentException("Armor can only be either cloth, leather or metal.")
        }
    }

    fun getPiece(): String {
        return piece
    }

    @Throws(IllegalArgumentException::class)
    fun setPiece(piece: String) {
        when (piece.lowercase()) {
            "helm", "pauldrons", "chestguard", "gauntlets", "greaves", "boots" ->
                this.piece = piece
            else -> throw IllegalArgumentException(
                "Armor pieces can only be helm, pauldrons, chestguard, gauntlets, greaves or boots."
            )
        }
    }

    override fun getEquipmentType(): String {
        return "Armor"
    }

    override fun getEquipmentSpecs(): String {
        return "Piece:" + piece + "\n" +
                "Armor type:" + type
    }
}
