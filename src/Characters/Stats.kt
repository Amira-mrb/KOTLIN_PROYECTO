package Characters

class Stats {

    private var _hp: Int = 0
    private var _atk: Int = 0
    private var _arm: Int = 0
    private var _spd: Int = 0
    private var _res: Int = 0
    private var _mag: Int = 0
    private var _fth: Int = 0

    fun getHp(): Int = _hp
    fun setHp(value: Int) { _hp = value }
    fun getAtk(): Int = _atk
    fun setAtk(value: Int) { _atk = value }
    fun getArm(): Int = _arm
    fun setArm(value: Int) { _arm = value }
    fun getSpd(): Int = _spd
    fun setSpd(value: Int) { _spd = value }
    fun getRes(): Int = _res
    fun setRes(value: Int) { _res = value }
    fun getMag(): Int = _mag
    fun setMag(value: Int) { _mag = value }
    fun getFth(): Int = _fth
    fun setFth(value: Int) { _fth = value }

    constructor()

    constructor(hp: Int, atk: Int, arm: Int, spd: Int, res: Int) {
        _hp = hp; _atk = atk; _arm = arm; _spd = spd; _res = res
    }

    constructor(hp: Int, atk: Int, arm: Int, spd: Int, res: Int, mag: Int, fth: Int) {
        _hp = hp; _atk = atk; _arm = arm; _spd = spd; _res = res; _mag = mag; _fth = fth
    }

    constructor(other: Stats) {
        _hp = other._hp; _atk = other._atk; _arm = other._arm
        _spd = other._spd; _res = other._res; _mag = other._mag; _fth = other._fth
    }

    fun receiveDmg(dmg: Int) {
        _hp -= dmg
        if (_hp < 0) _hp = 0
    }

    fun raiseGuard() {
        _arm = (_arm * 1.2).toInt()
        _res = (_res * 1.2).toInt()
    }

    fun dropGuard() {
        _arm = (_arm * 0.8).toInt()
        _res = (_res * 0.8).toInt()
    }

    fun getStat(stat: String): Int {
        return when (stat.uppercase()) {
            "HP" -> _hp; "ATK" -> _atk; "ARM" -> _arm
            "SPD" -> _spd; "RES" -> _res; "MAG" -> _mag; "FTH" -> _fth
            else -> 0
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Stats) return false
        return _hp == other._hp && _atk == other._atk && _arm == other._arm &&
                _spd == other._spd && _res == other._res && _mag == other._mag && _fth == other._fth
    }

    override fun hashCode(): Int {
        return _hp + _atk * 2 + _arm * 3 + _spd * 4 + _res * 5 + _mag * 6 + _fth * 7
    }

    override fun toString(): String {
        return "Stats{hp=$_hp, atk=$_atk, arm=$_arm, spd=$_spd, res=$_res}"
    }
}