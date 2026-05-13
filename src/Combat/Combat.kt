package Combat

import Characters.Character
import Characters.Stats
import Characters.Subclasses.Monster
import Equipment.Equipment
import Equipment.Armor
import Equipment.Heirloom
import Equipment.Weapon
import GameDataManagement.CombatLogManager
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import kotlin.random.Random

/**
 * Gestiona combates individuales y de grupo.
 * También carga tesoros desde archivos CSV.
 */
class Combat {

    companion object {

        /** Lista de tesoros disponibles para recompensas. */
        private val treasures: ArrayList<Equipment> = loadTreasures()

        /**
         * Ejecuta un combate 1vs1 entre dos personajes.
         * Devuelve un objeto aleatorio como recompensa.
         */
        @Throws(IOException::class)
        fun combat(player: Character, CPU: Character): Equipment {

            if (!CombatLogManager.hasLogStarted())
                CombatLogManager.startLog("${player.getName()} vs ${CPU.getName()}")

            CombatLogManager.out("The showdown between ${player.getName()} and ${CPU.getName()}... BEGINS!")

            val c1: Character
            val c2: Character

            if (checkFasterCharacter(player, CPU)) {
                c1 = player
                c2 = CPU
            } else {
                c1 = CPU
                c2 = player
            }

            while (!anybodyDead(c1, c2)) {

                if (doubleHit(c1, c2) && !anybodyDead(c1, c2)) {
                    c2.defend(c1.takeTurn())
                    visualizeHP(c1, c2)
                }

                if (!anybodyDead(c1, c2)) {
                    c2.defend(c1.takeTurn())
                    visualizeHP(c1, c2)
                }

                if (!anybodyDead(c1, c2)) {
                    c1.defend(c2.takeTurn())
                    visualizeHP(c1, c2)
                }
            }

            announceWinner(c1, c2)
            CombatLogManager.closeLog()

            return treasures[Random.nextInt(treasures.size)]
        }

        /**
         * Combate entre grupos.
         */
        @Throws(IOException::class)
        fun groupCombat(players: ArrayList<Character>, CPU: ArrayList<Monster>): ArrayList<Equipment> {

            players.sortWith(compareByDescending { it.getLvl() })
            CPU.sortWith(compareByDescending { it.getLvl() })

            val playerLeader = players.first()
            val CPULeader = CPU.first()

            val prize = ArrayList<Equipment>()

            CombatLogManager.startLog("${playerLeader.getName()}'s party vs ${CPULeader.getName()}'s group.")

            while (players.isNotEmpty() && CPU.isNotEmpty()) {

                val combatPrize = combat(players.first(), CPU.first())

                if (CPU.first().isDead()) {
                    prize.add(combatPrize)
                    CPU.removeAt(0)
                } else {
                    players.removeAt(0)
                }
            }

            if (players.isEmpty()) {
                CombatLogManager.out("${playerLeader.getClassName()}'s party was wiped out from existence.")
            } else {
                CombatLogManager.out("${playerLeader.getClassName()}'s party was victorious!")
            }

            return prize
        }

        /** Muestra la vida actual de los combatientes. */
        private fun visualizeHP(c1: Character, c2: Character) {
            if (c1.getStats().getHp() > 0)
                println("${c1.getName()}: ${c1.getStats().getHp()}HP")
            else
                println("${c1.getName()}: DEAD")

            if (c2.getStats().getHp() > 0)
                println("${c2.getName()}: ${c2.getStats().getHp()}HP")
            else
                println("${c2.getName()}: DEAD")
        }

        /** Comprueba quién es más rápido. */
        private fun checkFasterCharacter(c1: Character, c2: Character): Boolean {
            return c1.calculateTotalStat("SPD") >= c2.calculateTotalStat("SPD")
        }

        /** Comprueba si un personaje es el doble de rápido. */
        private fun doubleHit(c1: Character, c2: Character): Boolean {
            return c1.calculateTotalStat("SPD") >= c2.calculateTotalStat("SPD") * 2
        }

        /** Comprueba si alguno está muerto. */
        private fun anybodyDead(c1: Character, c2: Character): Boolean {
            return c1.isDead() || c2.isDead()
        }

        /** Anuncia el ganador. */
        private fun announceWinner(c1: Character, c2: Character) {
            if (c1.isDead())
                CombatLogManager.out("${c2.getName()} towers over ${c1.getName()} corpse.")
            if (c2.isDead())
                CombatLogManager.out("${c1.getName()} towers over ${c2.getName()} corpse.")
        }

        /** Carga todos los tesoros. */
        private fun loadTreasures(): ArrayList<Equipment> {
            val treasures = ArrayList<Equipment>()
            treasures.addAll(loadWeapons())
            treasures.addAll(loadArmors())
            treasures.addAll(loadHeirlooms())
            return treasures
        }

        /** Carga armas. */
        private fun loadWeapons(): ArrayList<Weapon> {
            val result = ArrayList<Weapon>()
            try {
                BufferedReader(FileReader("./DataFiles/Treasures/weapons.csv")).use { reader ->
                    reader.readLine()
                    var s: String?

                    while (reader.readLine().also { s = it } != null) {
                        val split = s!!.split(",")
                        val w = Weapon(
                            split[0],
                            generateStatsFromString(split[3], 1),
                            split[1],
                            split[4].toInt(),
                            split[2]
                        )
                        result.add(w)
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            return result
        }

        /** Carga armaduras. */
        private fun loadArmors(): ArrayList<Armor> {
            val result = ArrayList<Armor>()
            try {
                BufferedReader(FileReader("./DataFiles/Treasures/armor.csv")).use { reader ->
                    reader.readLine()
                    var s: String?

                    while (reader.readLine().also { s = it } != null) {
                        val split = s!!.split(",")
                        val a = Armor(
                            split[0],
                            generateStatsFromString(split[4], 2),
                            split[1],
                            split[5].toInt(),
                            split[2],
                            split[3]
                        )
                        result.add(a)
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            return result
        }

        /** Carga reliquias. */
        private fun loadHeirlooms(): ArrayList<Heirloom> {
            val result = ArrayList<Heirloom>()
            try {
                BufferedReader(FileReader("./DataFiles/Treasures/heirlooms.csv")).use { reader ->
                    reader.readLine()
                    var s: String?

                    while (reader.readLine().also { s = it } != null) {
                        val split = s!!.split(",")
                        val h = Heirloom(
                            split[0],
                            generateStatsFromString(split[3], 3),
                            split[1],
                            split[4].toInt(),
                            split[2]
                        )
                        result.add(h)
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            return result
        }

        /** Genera stats desde string */
        private fun generateStatsFromString(s: String, equipmentType: Int): Stats {
            val stats = Stats()
            val split = s.split("-")

            when (equipmentType) {
                1 -> {
                    stats.atk = split[0].toInt()
                    stats.spd = split[1].toInt()
                    stats.mag = split[2].toInt()
                    stats.fth = split[3].toInt()
                }

                2 -> {
                    stats.arm = split[0].toInt()
                    stats.res = split[1].toInt()
                    stats.hp = split[2].toInt()
                }

                3 -> {
                    stats.atk = split[0].toInt()
                    stats.spd = split[1].toInt()
                    stats.mag = split[2].toInt()
                    stats.fth = split[3].toInt()
                    stats.arm = split[4].toInt()
                    stats.res = split[5].toInt()
                    stats.hp = split[6].toInt()
                }

                else -> throw IllegalArgumentException("Invalid equipment type.")
            }

            return stats
        }
    }
}
