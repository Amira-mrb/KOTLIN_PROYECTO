package Characters

/**
 * Clase que guarda todas las estadísticas del personaje.
 * Incluye vida, ataque, armadura, velocidad, resistencia, magia y fe.
 */
class Stats {

    var hp: Int      // vida
    var atk: Int     // ataque
    var arm: Int     // armadura
    var spd: Int     // velocidad
    var res: Int     // resistencia
    var mag: Int     // magia
    var fth: Int     // fe

    /**
     * Constructor que inicia todas las stats a 0.
     */
    constructor() {
        hp = 0
        atk = 0
        arm = 0
        spd = 0
        res = 0
        mag = 0
        fth = 0
    }

    /**
     * Constructor con stats básicas.
     * Magia y fe empiezan a 0.
     */
    constructor(hp: Int, atk: Int, arm: Int, spd: Int, res: Int) {
        this.hp = hp
        this.atk = atk
        this.arm = arm
        this.spd = spd
        this.res = res
        this.mag = 0
        this.fth = 0
    }

    /**
     * Constructor con todas las stats.
     */
    constructor(hp: Int, atk: Int, arm: Int, spd: Int, res: Int, mag: Int, fth: Int) {
        this.hp = hp
        this.atk = atk
        this.arm = arm
        this.spd = spd
        this.res = res
        this.mag = mag
        this.fth = fth
    }

    /**
     * Constructor de copia.
     */
    constructor(other: Stats) {
        this.hp = other.hp
        this.atk = other.atk
        this.arm = other.arm
        this.spd = other.spd
        this.res = other.res
        this.mag = other.mag
        this.fth = other.fth
    }

    /**
     * Resta vida al personaje cuando recibe daño.
     */
    fun receiveDmg(dmg: Int) {
        this.hp -= dmg
    }

    /**
     * Sube la guardia: aumenta armadura y resistencia.
     */
    fun raiseGuard() {
        this.arm = (this.arm * 1.2).toInt()
        this.res = (this.res * 1.2).toInt()
    }

    /**
     * Baja la guardia: reduce armadura y resistencia.
     */
    fun dropGuard() {
        this.arm = (this.arm * 0.8).toInt()
        this.res = (this.res * 0.8).toInt()
    }

    /**
     * Devuelve el valor de la stat pedida.
     */
    fun getStat(stat: String): Int {
        return when (stat) {
            "HP" -> hp
            "ATK" -> atk
            "ARM" -> arm
            "SPD" -> spd
            "RES" -> res
            "MAG" -> mag
            "FTH" -> fth
            else -> 0
        }
    }

    /**
     * Compara si dos objetos Stats son iguales.
     */
    fun equals(other: Stats): Boolean {
        return hp == other.hp &&
                atk == other.atk &&
                arm == other.arm &&
                spd == other.spd &&
                res == other.res &&
                mag == other.mag &&
                fth == other.fth
    }

    /**
     * Devuelve las stats principales en formato texto.
     */
    override fun toString(): String {
        return "Stats{hp=$hp, atk=$atk, arm=$arm, spd=$spd, res=$res}"
    }
}
