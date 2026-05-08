package GameModes

import Characters.Character


import Characters.Subclasses.Monster
import Equipment.Equipment
import Equipment.Weapon
import Equipment.Armor
import Equipment.Heirloom
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

    private dungeon: Dungeon;


    private var playerParty: ArrayList<Character> = ArrayList()
    fun playGauntlet() {
        var remainingCombats = 10

        while (remainingCombats > 0 && playerParty.isNotEmpty()) {

            val combatPrize = Combat.groupCombat(playerParty, dungeon.randomFight())

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


        private fun setPlayerParty(playerParty: ArrayList<Character>) {
        this.playerParty = ArrayList(playerParty)
    }

    private fun setName(name: String) {

    }

    private fun setLevel(level: Int) {

    }

    private fun setMonsters(monsters: HashSet<Monster>) {

    }

    private fun getMonsters(): HashSet<Monster> {

        return getMonsters()

    }

    private fun equipPrize(player: Character, prize: Equipment): Boolean {
        var result = false

        when (prize.javaClass.simpleName) {
            "Weapon" -> result = player.equipWeapon(prize as Weapon)
            "Armor" -> result = player.equipArmor(prize as Armor)
            "Heirloom" -> result = player.equipHeirloom(prize as Heirloom)
        }

        return result
    }
}
