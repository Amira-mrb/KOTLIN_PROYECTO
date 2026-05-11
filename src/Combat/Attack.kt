package Combat

/**
 * Clase Attack.
 * Representa un ataque con un valor de daño y un tipo de daño.
 */
class Attack {

    var dmgValue: Int      // cantidad de daño
    var dmgType: String    // tipo de daño (PHY, MAG o STA)

    /**
     * Constructor por defecto.
     * Deja el daño a 0 y el tipo vacío.
     */
    constructor() {
        dmgType = ""
        dmgValue = 0
    }

    /**
     * Constructor con parámetros.
     *
     * @param dmgValue valor del daño
     * @param dmgType  tipo de daño
     */
    constructor(dmgValue: Int, dmgType: String) {
        this.dmgValue = dmgValue
        this.dmgType = dmgType
    }

    /**
     * Asigna el tipo de daño.
     * Solo acepta PHY (físico), MAG (mágico) o STA (estado).
     */
    fun setDmgTypeSafe(dmgType: String) {
        when (dmgType.uppercase()) {
            "PHY", "MAG", "STA" -> this.dmgType = dmgType
            else -> throw IllegalArgumentException(
                "Damage type must be either PHY for physical, MAG for magical or STA for status change."
            )
        }
    }
}
