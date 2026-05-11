package GameMap

import Characters.Subclasses.Monster
import kotlin.random.Random

class Dungeon {

    private var name: String = ""
    private var level: Int = 0
    private var monsters: HashSet<Monster> = HashSet()

    constructor() {
        name = ""
        level = 0
        monsters = HashSet()
    }

    constructor(name: String, level: Int, monsters: HashSet<Monster>) {
        this.name = name
        this.level = level
        this.monsters = HashSet(monsters)
    }

    fun getName(): String { return name }
    fun setName(name: String) { this.name = name }

    fun getLevel(): Int { return level }
    fun setLevel(level: Int) { this.level = level }

    fun getMonsters(): HashSet<Monster> { return monsters }
    fun setMonsters(monsters: HashSet<Monster>) { this.monsters = HashSet(monsters) }

    fun randomEncounter(): Monster {
        val encounter = monsters.elementAt(Random.nextInt(monsters.size))

        val times = Random.nextInt(level - 3, level + 3)
        for (i in 0 until times) {
            encounter.levelUp()
        }

        return encounter
    }

    fun randomFight(): ArrayList<Monster> {
        val encounters = ArrayList<Monster>()
        val count = Random.nextInt(1, 4)

        for (i in 0 until count) {
            encounters.add(randomEncounter())
        }

        return encounters
    }
}
