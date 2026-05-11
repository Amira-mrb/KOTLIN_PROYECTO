package GameDataManagement

import Characters.Character
import Characters.Stats
import Characters.Subclasses.*

class CharacterFactory {

    companion object {

        fun createFromDataArray(data: Array<String>): Character {
            var character: Character? = null
            var isCPU = true

            // Stats básicas
            val characterStats = Stats(
                data[4].toInt(), // hp
                data[5].toInt(), // atk
                data[6].toInt(), // arm
                data[7].toInt(), // spd
                data[8].toInt()  // res
            )

            val className = data[1]

            when (className) {

                "Warrior" -> {
                    if (data[9] == "Player")
                        isCPU = false

                    character = Warrior(
                        data[0],             // nombre
                        data[2],             // raza
                        data[3].toInt(),     // nivel
                        characterStats,
                        isCPU
                    )
                }

                "Mage" -> {
                    if (data[10] == "Player")
                        isCPU = false

                    characterStats.setMag(data[9].toInt()) // magia extra

                    character = Mage(
                        data[0],
                        data[2],
                        data[3].toInt(),
                        characterStats,
                        isCPU
                    )
                }

                "Thief" -> {
                    if (data[9] == "Player")
                        isCPU = false

                    character = Thief(
                        data[0],
                        data[2],
                        data[3].toInt(),
                        characterStats,
                        isCPU
                    )
                }

                "Hunter" -> {
                    if (data[9] == "Player")
                        isCPU = false
                    // Hunter no implementado
                }

                "Paladin" -> {
                    if (data[10] == "Player")
                        isCPU = false

                    characterStats.setFth(data[9].toInt()) // fe extra

                    character = Paladin(
                        data[0],
                        data[2],
                        data[3].toInt(),
                        characterStats,
                        isCPU
                    )
                }

                "Priest" -> {
                    if (data[10] == "Player")
                        isCPU = false

                    characterStats.setFth(data[9].toInt()) // fe extra

                    character = Priest(
                        data[0],
                        data[2],
                        data[3].toInt(),
                        characterStats,
                        isCPU
                    )
                }

                "Monster" -> {
                    if (data[9] == "Player")
                        isCPU = false

                    character = Monster(
                        data[0],
                        data[2],
                        data[3].toInt(),
                        characterStats,
                        isCPU
                    )
                }

                else -> {
                    throw IllegalArgumentException("${data[1]} is not a valid class. Character cannot be generated.")
                }
            }

            return character!!
        }
    }
}
