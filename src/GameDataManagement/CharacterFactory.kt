package GameDataManagement

import Characters.Character
import Characters.Stats
import Characters.Subclasses.*

class CharacterFactory {

    companion object {

        fun createFromDataArray(data: Array<String>): Character {
            var character: Character? = null
            var isCPU = true

            val characterStats = Stats(
                data[4].toInt(),
                data[5].toInt(),
                data[6].toInt(),
                data[7].toInt(),
                data[8].toInt()
            )

            val className = data[1]

            when (className) {

                "Warrior" -> {
                    if (data[9] == "Player") isCPU = false
                    character = Warrior(data[0], data[2], data[3].toInt(), characterStats, isCPU)
                }

                "Mage" -> {
                    if (data[10] == "Player") isCPU = false
                    characterStats.setMag(data[9].toInt())
                    character = Mage(data[0], data[2], data[3].toInt(), characterStats, isCPU)
                }

                "Thief" -> {
                    if (data[9] == "Player") isCPU = false
                    character = Thief(data[0], data[2], data[3].toInt(), characterStats, isCPU)
                }

                "Hunter" -> {
                    if (data[9] == "Player") isCPU = false
                    character = Hunter(data[0], data[2], data[3].toInt(), characterStats, isCPU)
                }

                "Paladin" -> {
                    if (data[10] == "Player") isCPU = false
                    characterStats.setFth(data[9].toInt())
                    character = Paladin(data[0], data[2], data[3].toInt(), characterStats, isCPU)
                }

                "Priest" -> {
                    if (data[10] == "Player") isCPU = false
                    characterStats.setFth(data[9].toInt())
                    character = Priest(data[0], data[2], data[3].toInt(), characterStats, isCPU)
                }

                "Monster" -> {
                    if (data[9] == "Player") isCPU = false
                    character = Monster(data[0], data[2], data[3].toInt(), characterStats, isCPU)
                }

                else -> throw IllegalArgumentException("${data[1]} is not a valid class. Character cannot be generated.")
            }

            return character!!
        }
    }
}
