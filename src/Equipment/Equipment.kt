package Equipment

import Characters.Stats

/**
 * Clase base abstracta para cualquier tipo de equipamiento.
 * Tiene nombre, estadísticas, rareza y valor.
 */
abstract class Equipment {

    private var name: String = ""
    private var equipmentStats: Stats = Stats()
    private var rarity: String = ""
    private var value: Int = -1

    /**
     * Constructor por defecto.
     */
    constructor() {
        name = ""
        rarity = ""
        equipmentStats = Stats()
        value = -1
    }

    /**
     * Constructor con datos.
     */
    constructor(name: String, equipmentStats: Stats, rarity: String, value: Int) {
        setName(name)
        setEquipmentStats(equipmentStats)
        setRarity(rarity)
        setValue(value)
    }

    /**
     * Constructor de copia.
     */
    constructor(other: Equipment) {
        this.name = other.name
        this.equipmentStats = Stats(other.equipmentStats)
        this.rarity = other.rarity
        this.value = other.value
    }

    /** Devuelve el nombre del objeto. */
    fun getName(): String = name

    /**
     * Asigna el nombre.
     * Si es muy largo, se recorta.
     */
    fun setName(name: String) {
        val finalName =
            if (name.length > 20) name.substring(0, 19) + "."
            else name
        this.name = finalName
    }

    /** Devuelve las estadísticas del objeto. */
    fun getEquipmentStats(): Stats = equipmentStats

    /** Asigna las estadísticas del objeto. */
    fun setEquipmentStats(equipmentStats: Stats) {
        this.equipmentStats = equipmentStats
    }

    /** Devuelve la rareza. */
    fun getRarity(): String = rarity

    /**
     * Asigna la rareza.
     * Solo acepta common, rare, epic o legendary.
     */
    fun setRarity(rarity: String) {
        when (rarity.lowercase()) {
            "common", "rare", "epic", "legendary" -> this.rarity = rarity
            else -> throw IllegalArgumentException(
                "Rarity must be one of the following: common, rare, epic or legendary."
            )
        }
    }

    /** Devuelve el valor. */
    fun getValue(): Int = value

    /**
     * Asigna el valor.
     * Debe ser mayor que 1.
     */
    fun setValue(value: Int) {
        if (value < 1) {
            throw IllegalArgumentException("Equipment value must be higher than 1.")
        }
        this.value = value
    }

    /**
     * Devuelve el valor de una estadística concreta.
     */
    fun getStat(stat: String): Int {
        return equipmentStats.getStat(stat)
    }

    /**
     * Texto con toda la información del objeto.
     */
    override fun toString(): String {
        return getEquipmentType() + ":" + name + "\n" +
                getEquipmentSpecs() + "\n" +
                "Rarity:" + rarity + "\n" +
                "Value:" + value + "$.\n" +
                equipmentStats.toString()
    }

    /**
     * Compara este objeto con otro.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Equipment) return false

        if (name != other.name) return false
        if (rarity != other.rarity) return false
        if (value != other.value) return false
        if (equipmentStats != other.equipmentStats) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + equipmentStats.hashCode()
        result = 31 * result + rarity.hashCode()
        result = 31 * result + value
        return result
    }

    /** Tipo general de equipamiento. */
    protected abstract fun getEquipmentType(): String

    /** Información específica del objeto. */
    protected abstract fun getEquipmentSpecs(): String
}
