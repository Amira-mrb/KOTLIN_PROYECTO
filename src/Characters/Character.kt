package Characters

import Combat.Attack
import Equipment.Armor
import Equipment.Heirloom
import Equipment.Weapon
import GameDataManagement.CombatLogManager
import GameDataManagement.GameLogger
import java.io.File
import java.io.IOException
import java.util.Scanner

abstract class Character : Comparable<Character> {

    private var name: String = ""
    private var race: String = ""
    private var lvl: Int = 0
    private var stats: Stats = Stats()
    private var isDefending: Boolean = false
    private var isCPU: Boolean = false
    private var weapon: Weapon? = null
    private var armor: HashMap<String, Armor> = HashMap()
    private var heirlooms: ArrayList<Heirloom> = ArrayList()

    constructor()

    constructor(name: String, race: String, lvl: Int, isCPU: Boolean) {
        setName(name)
        setRace(race)
        setLvl(1)
        stats = initializeStats()
        for (i in 0 until lvl - 1)
            levelUp()
        this.isCPU = isCPU
        weapon = null
        armor = HashMap()
        heirlooms = ArrayList()
    }

    constructor(name: String, race: String, lvl: Int, stats: Stats, isCPU: Boolean) {
        setName(name)
        setRace(race)
        setLvl(lvl)
        setStats(stats)
        this.isCPU = isCPU
        weapon = null
        armor = HashMap()
        heirlooms = ArrayList()
    }

    constructor(other: Character) {
        this.name = other.name
        this.race = other.race
        this.lvl = other.lvl
        this.stats = Stats(other.stats)
        this.isCPU = other.isCPU
        this.weapon = other.weapon?.let { Weapon(it) }
        this.armor = HashMap<String, Armor>().apply {
            for ((key, value) in other.armor)
                put(key, Armor(value))
        }
        this.heirlooms = ArrayList(other.heirlooms.map { Heirloom(it) })
    }

    @Throws(IOException::class)
    constructor(characterSheet: File) {
        if (!characterSheet.isFile || !characterSheet.canRead())
            throw IllegalArgumentException("Error. You must procure a valid character sheet path.")
        val c = GameLogger.readCharacter(characterSheet)
        setName(c.name)
        setRace(c.race)
        setStats(c.stats)
        setLvl(c.lvl)
        setCPU(c.isCPU)
        weapon = null
        armor = HashMap()
        heirlooms = ArrayList()
    }

    fun getName(): String = name
    fun setName(name: String) {
        if (name.isEmpty()) throw IllegalArgumentException("Name cannot be empty.")
        this.name = name
    }
    fun getRace(): String = race
    fun setRace(race: String) {
        if (race.isEmpty()) throw IllegalArgumentException("Race cannot be empty.")
        this.race = race
    }
    fun getLvl(): Int = lvl
    fun setLvl(lvl: Int) {
        if (lvl <= 0) throw IllegalArgumentException("Level must be greater than zero.")
        this.lvl = lvl
    }
    fun getStats(): Stats = stats
    fun setStats(stats: Stats) { this.stats = Stats(stats) }
    fun isDefending(): Boolean = isDefending
    fun setDefending(defending: Boolean) { isDefending = defending }
    fun isCPU(): Boolean = isCPU
    fun setCPU(isCPU: Boolean) { this.isCPU = isCPU }
    fun isDead(): Boolean = stats.getHp() <= 0
    fun getWeapon(): Weapon? = weapon
    fun getArmor(): HashMap<String, Armor> = armor
    fun getHeirlooms(): ArrayList<Heirloom> = heirlooms

    fun levelUp() {
        lvl++
        onLevelUp()
    }

    open fun attack(): Attack {
        CombatLogManager.out("$name attacks!")
        return Attack(calculateTotalStat("ATK"), "PHY")
    }

    open fun defend(attack: Attack) {
        var totalDmg = 0
        when (attack.dmgType) {
            "PHY" -> totalDmg = attack.dmgValue - calculateTotalStat("ARM")
            "MAG" -> totalDmg = attack.dmgValue - calculateTotalStat("RES")
            "STA" -> {}
            else -> throw IllegalArgumentException("Damage type must be PHY, MAG or STA.")
        }
        if (totalDmg <= 0 && attack.dmgType != "STA")
            CombatLogManager.out("It doesn't even make a dent.")
        else if (attack.dmgType != "STA") {
            CombatLogManager.out("$name receives $totalDmg points of damage.")
            stats.receiveDmg(totalDmg)
        }
    }

    open fun takeTurn(): Attack {
        if (!isCPU) {
            val scan = Scanner(System.`in`)
            if (isDefending) {
                setDefending(false)
                CombatLogManager.out("$name drops their guard and gets ready.")
                getStats().dropGuard()
            }
            println("1. Attack.")
            println(displaySpecialAction())
            println("3. Defend.")
            println("4. Pass turn.")
            print("What would you like to do? ")
            return when (scan.nextLine()) {
                "1" -> attack()
                "2" -> performSpecialAction()
                "3" -> {
                    if (!isDefending) {
                        setDefending(true)
                        getStats().raiseGuard()
                    }
                    println("$name raises its guard.")
                    Attack(0, "STA")
                }
                "4" -> {
                    println("$name does nothing.")
                    Attack(0, "STA")
                }
                else -> throw RuntimeException("Input a valid move")
            }
        } else {
            return attack()
        }
    }

    @Throws(IOException::class)
    fun compareToCharacterSheet(characterSheet: File) {
        if (!characterSheet.isFile || !characterSheet.canRead())
            throw IllegalArgumentException("Error. You must procure a valid character sheet.")
        val c = GameLogger.readCharacter(characterSheet)
        if (!c.equals(this) && c.name == this.name) {
            println("Character doesn't match with the character sheet provided. Updating character")
            updateCharacter(c)
        } else println("This character doesn't need updating.")
    }

    @Throws(IOException::class)
    fun updateOnCombatWin(combatLog: File) {
        if (GameLogger.readCombatWin(this, combatLog))
            levelUp()
    }

    fun equipWeapon(w: Weapon): Boolean {
        if (this.weapon != null) return false
        if (!isWeaponValid(w)) return false
        this.weapon = Weapon(w)
        return true
    }

    fun equipArmor(a: Armor): Boolean {
        if (this.armor.size >= 6) return false
        if (this.armor.containsKey(a.getPiece())) return false
        this.armor[a.getPiece()] = a
        return true
    }

    open fun equipHeirloom(h: Heirloom): Boolean {
        if (!validateHeirlooms(h)) return false
        heirlooms.add(h)
        return true
    }

    fun calculateTotalStat(stat: String): Int {
        var totalStat = stats.getStat(stat)
        if (stat == "ARM" || stat == "RES" || stat == "HP") {
            for (a in armor.values)
                totalStat += a.getStat(stat)
        } else if (weapon != null) {
            totalStat += weapon!!.getStat(stat)
        }
        for (h in heirlooms)
            totalStat += h.getStat(stat)
        return totalStat
    }

    abstract fun getClassName(): String

    override fun toString(): String {
        val character = "Name:$name\n" +
                "Class:${getClassName()}\n" +
                "Race:$race\n" +
                "Level:$lvl\n" +
                "HP:${stats.getHp()}\n" +
                "ATK:${stats.getAtk()}\n" +
                "ARM:${stats.getArm()}\n" +
                "SPD:${stats.getSpd()}\n" +
                "RES:${stats.getRes()}\n" +
                printStatsIfValid() +
                "Type:"
        return character + if (isCPU) "CPU" else "Player"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Character) return false
        return name == other.name && race == other.race &&
                lvl == other.lvl && stats == other.stats && isCPU == other.isCPU
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + race.hashCode()
        result = 31 * result + lvl
        result = 31 * result + stats.hashCode()
        result = 31 * result + isCPU.hashCode()
        return result
    }

    override operator fun compareTo(other: Character): Int {
        val result = Integer.compare(other.stats.getSpd(), this.stats.getSpd())
        if (result == 0) return Integer.compare(other.lvl, this.lvl)
        return result
    }

    protected abstract fun onLevelUp()
    protected abstract fun initializeStats(): Stats
    protected abstract fun displaySpecialAction(): String
    protected abstract fun performSpecialAction(): Attack
    protected abstract fun isWeaponValid(w: Weapon): Boolean
    protected abstract fun isArmorValid(a: Armor): Boolean

    private fun printStatsIfValid(): String {
        var extraStats = ""
        if (stats.getMag() != 0) extraStats += "MAG:${stats.getMag()}\n"
        if (stats.getFth() != 0) extraStats += "FTH:${stats.getFth()}\n"
        return extraStats
    }

    private fun updateCharacter(update: Character) {
        this.race = update.race
        this.lvl = update.lvl
        this.stats = Stats(update.stats)
        this.isCPU = update.isCPU
    }

    private fun validateHeirlooms(heirloom: Heirloom): Boolean {
        var ringCount = 0
        var amuletFound = false
        for (h in heirlooms) {
            if (h.getType() == "Ring") ringCount++
            if (h.getType() == "Amulet") amuletFound = true
        }
        if (heirloom.getType() == "Ring" && ringCount > 2) return false
        if (heirloom.getType() == "Amulet" && amuletFound) return false
        return true
    }
}