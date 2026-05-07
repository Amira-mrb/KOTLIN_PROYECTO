package Equipment

import Characters.Stats

class Weapon : Equipment {

    private var hands: Int = -1
    private var type: String = ""

    // Constructor por defecto
    constructor() : super() {
        hands = -1
        type = ""
    }

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

    // Constructor de copia
    constructor(other: Weapon) : super(other) {
        this.hands = other.hands
        this.type = other.type
    }

    fun getHands(): Int {
        return hands
    }

    @Throws(IllegalArgumentException::class)
    fun setHands() {
        when (type.lowercase()) {
            "sword", "mace", "axe", "scepter", "dagger" -> hands = 1
            "greatsword", "hammer", "bow", "staff" -> hands = 2
            else -> throw IllegalArgumentException("Can't set weapon hands with wrong weapon type.")
        }
    }

    fun getType(): String {
        return type
    }

    @Throws(IllegalArgumentException::class)
    fun setType(type: String) {
        when (type.lowercase()) {
            "sword", "mace", "axe", "scepter", "dagger",
            "greatsword", "hammer", "bow", "staff" -> this.type = type
            else -> throw IllegalArgumentException("Weapon type not admited.")
        }
    }

    override fun getEquipmentType(): String {
        return "Weapon"
    }

    override fun getEquipmentSpecs(): String {
        return "Type:" + type
    }

    fun equals(other: Weapon): Boolean {
        if (!super.equals(other)) return false
        if (this.hands != other.hands) return false
        if (this.type != other.type) return false
        return true
    }
}
