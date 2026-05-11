package GameModes

import Characters.Subclasses.Monster
import Equipment.*
import GameDataManagement.CombatLogManager
import GameMap.Dungeon
import Combat.Combat
import Characters.Character
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.ArrayList
import java.util.HashSet

class Gauntlet {

    private var dungeon: Dungeon? = null
    private var playerParty: ArrayList<Character> = ArrayList()

    @Throws(IOException::class)
    fun initGauntlet(dungeonFile: File, players: ArrayList<Character>) {
        loadDungeon(dungeonFile)
        setPlayerParty(players)
    }

    fun playGauntlet() {
        var remainingCombats = 10

        while (remainingCombats > 0 && playerParty.isNotEmpty()) {

            val combatPrize =
                Combat.groupCombat(playerParty, dungeon!!.randomFight())

            if (playerParty.isNotEmpty()) {
                for (equip in combatPrize) {
                    var indexOfPlayer = 0
                    while (!equipPrize(playerParty[indexOfPlayer], equip)) {
                        indexOfPlayer++
                    }
                }
            }

            remainingCombats--
        }

        if (playerParty.isNotEmpty())
            CombatLogManager.out("Congratulations! You defeated the gauntlet and triumphed over your quest!")
        else
            CombatLogManager.out("The adventurers died an ignominious death...")
    }

    @Throws(IOException::class)
    private fun loadDungeon(dungeonFile: File) {
        val dungeonName: String
        val dungeonLevel: Int

        if (dungeonFile.exists() && dungeonFile.canRead()) {

            val br = BufferedReader(FileReader(dungeonFile))
            var line: String?
            var split: List<String>
            val monsterList = HashSet<Monster>()

            line = br.readLine()
            split = line.split(",")
            dungeonName = split[0]
            dungeonLevel = split[1].trim().toInt()

            while (br.readLine().also { line = it } != null) {
                split = line!!.split(",")
                monsterList.add(Monster(split[1], split[0]))
            }

            dungeon = Dungeon(dungeonName, dungeonLevel, monsterList)
            br.close()

        } else {
            throw IOException("Can't read dungeon file.")
        }
    }

    private fun setPlayerParty(playerParty: ArrayList<Character>) {
        this.playerParty = ArrayList(playerParty)
    }

    private fun equipPrize(player: Character, prize: Equipment): Boolean {
        return when (prize.javaClass.simpleName) {
            "Weapon" -> player.equipWeapon(prize as Weapon)
            "Armor" -> player.equipArmor(prize as Armor)
            "Heirloom" -> player.equipHeirloom(prize as Heirloom)
            else -> false
        }
    }
}
