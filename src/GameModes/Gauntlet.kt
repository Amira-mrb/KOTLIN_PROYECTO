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

/**
 * Clase Gauntlet.
 * Modo de juego donde el grupo de jugadores debe superar 10 combates seguidos.
 * Cada combate da recompensas que los jugadores pueden equiparse.
 */
class Gauntlet {

    private var dungeon: Dungeon? = null
    private var playerParty: ArrayList<Character> = ArrayList()

    /**
     * Inicia el modo Gauntlet cargando la mazmorra y el grupo de jugadores.
     */
    @Throws(IOException::class)
    fun initGauntlet(dungeonFile: File, players: ArrayList<Character>) {
        loadDungeon(dungeonFile)
        setPlayerParty(players)
    }

    /**
     * Ejecuta el Gauntlet completo.
     * Son 10 combates o hasta que el grupo muera.
     */
    fun playGauntlet() {
        var remainingCombats = 10

        while (remainingCombats > 0 && playerParty.isNotEmpty()) {

            val combatPrize =
                Combat.groupCombat(playerParty, dungeon!!.randomFight())

            if (playerParty.isNotEmpty()) {
                for (equip in combatPrize) {
                    var indexOfPlayer = 0

                    // Evita IndexOutOfBounds si nadie puede equiparlo
                    while (indexOfPlayer < playerParty.size &&
                        !equipPrize(playerParty[indexOfPlayer], equip)) {
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

    /**
     * Carga la mazmorra desde un archivo.
     * El archivo debe tener: nombre, nivel y lista de monstruos.
     */
    @Throws(IOException::class)
    private fun loadDungeon(dungeonFile: File) {
        val dungeonName: String
        val dungeonLevel: Int

        if (dungeonFile.exists() && dungeonFile.canRead()) {

            BufferedReader(FileReader(dungeonFile)).use { br ->
                var line = br.readLine()
                val split = line.split(",")

                dungeonName = split[0]
                dungeonLevel = split[1].trim().toInt()

                val monsterList = HashSet<Monster>()

                while (br.readLine().also { line = it } != null) {
                    val parts = line!!.split(",")
                    monsterList.add(Monster(parts[1], parts[0]))
                }

                dungeon = Dungeon(dungeonName, dungeonLevel, monsterList)
            }

        } else {
            throw IOException("Can't read dungeon file.")
        }
    }

    /**
     * Guarda el grupo de jugadores.
     */
    private fun setPlayerParty(playerParty: ArrayList<Character>) {
        this.playerParty = ArrayList(playerParty)
    }

    /**
     * Intenta equipar una recompensa a un jugador.
     * Según el tipo de objeto, llama al método correspondiente.
     */
    private fun equipPrize(player: Character, prize: Equipment): Boolean {
        return when (prize.javaClass.simpleName) {
            "Weapon" -> player.equipWeapon(prize as Weapon)
            "Armor" -> player.equipArmor(prize as Armor)
            "Heirloom" -> player.equipHeirloom(prize as Heirloom)
            else -> false
        }
    }
}
