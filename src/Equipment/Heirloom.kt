package Equipment

import Characters.Stats

class Heirloom : Equipment {

    private var type: String = "";

    // monstructor con parámetros
    constructor(name: String, equipmentStats: Stats, rarity: String, value: Int, type: String)
            : super(name, equipmentStats, rarity, value) {
        setType(type)
    }

    // constructor de copia
    constructor(other: Heirloom) : super(other) {
        this.type = other.type
    }

    fun getType(): String {
        return type
    }

    @Throws(IllegalArgumentException::class)
    fun setType(type: String) {
        when (type.lowercase()) {
            "ring", "amulet" -> this.type = type
            else -> throw IllegalArgumentException("Heirlooms can only be rings or amulets.")
        }
    }

    override fun getEquipmentType(): String {
        return "Heirloom"
    }

    override fun getEquipmentSpecs(): String {
        return "Type:" + type
    }
}
