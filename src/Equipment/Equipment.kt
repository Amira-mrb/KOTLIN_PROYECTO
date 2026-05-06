package Equipment

import Characters.Stats

abstract class Equipment {
    private var name: String = ""
    private var equipmentStats: Stats = Stats()
    private var rarity: String = ""
    private var value: Int = -1;


    constructor(other: Equipment) {
        this.name = other.name;
        this.equipmentStats = other.equipmentStats;
        this.value = other.value;
        this.rarity = other.rarity;
    }

    constructor(name: String, equipmentStats: Stats, rarity: String, value: Int) {
        setName(name)
        setEquipmentStats(equipmentStats)
        setRarity(rarity)
        setValue(value)
    }


    //setter de rarity
    @Throws(IllegalArgumentException::class)
    fun setRarity(rarity: String) {
        when (rarity.lowercase()) {
            "common", "rare", "epic", "legendary" -> this.rarity = rarity
            else -> throw IllegalArgumentException("rarity must be one of the following")
        }
    }

    @Throws(IllegalArgumentException::class)
    fun setValue(value: Int) {
        if (value < 1) {
            throw IllegalArgumentException("value must be greater than 1")
        }
        this.value = value;
    }

    fun setName(name: String) {
        val finalName =
            if (name.length > 20) name.substring(0, 19) + "."
            else name
        this.name = finalName;
    }

    fun getName(): String {
        return name
    }

    fun getRarity(): String {
        return rarity
    };
    fun getValue(): Int {
        return value
    };

    fun getStat(stat: String?): Int {
        return equipmentStats.getStat(stat)
    }


    override fun toString(): String {
        return getEquipmentType() + ":" + name + "\n" +
                getEquipmentSpecs() + "\n" +
                "Rarity:" + rarity + "\n" +
                "Value:" + value + "$.\n" +
                equipmentStats.toString()
    }

    fun equals(other: Equipment): Boolean {
        if (this.name != other.name) return false
        if (this.equipmentStats != other.equipmentStats) return false
        if (this.rarity != other.rarity) return false
        if (this.value != other.value) return false
        return true
    }

    protected abstract fun getEquipmentType(): String
    protected abstract fun getEquipmentSpecs(): String
}


