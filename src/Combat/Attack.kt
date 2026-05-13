package Combat

/**
 * Clase Attack.
 * Representa un ataque con un valor de daño y un tipo de daño.
 */
class Attack() {

    var dmgValue: Int = 0      // cantidad de daño

    var dmgType: String = ""   // tipo de daño (PHY, MAG o STA)
        set(value) {
            when (value.uppercase()) {
                "PHY", "MAG", "STA" -> field = value
                else -> throw IllegalArgumentException(
                    "Damage type must be either PHY for physical, MAG for magical or STA for status change."
                )
            }
        }

    /**
     * Constructor con parámetros.
     *
     * @param dmgValue valor del daño
     * @param dmgType  tipo de daño
     */
    constructor(dmgValue: Int, dmgType: String) : this() {
        this.dmgValue = dmgValue
        this.dmgType = dmgType   // pasa por el setter validado
    }

    /**
     * Setter alternativo (compatibilidad con Java).
     */
    fun setDmgTypeSafe(dmgType: String) {
        this.dmgType = dmgType   // también pasa por la validación
    }
}
