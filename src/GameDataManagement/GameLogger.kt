package GameDataManagement

import Characters.Character
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.Arrays

/**
 * Clase GameLogger.
 * Gestiona lectura y escritura de personajes y grupos en archivos.
 * También permite comprobar duplicados y leer resultados de combate.
 */
class GameLogger {

    companion object {

        /** Carpeta por defecto donde se guardan los archivos. */
        protected val defaultDirectory: String = "./OutputFiles/"

        /**
         * Lee un personaje desde un archivo de texto.
         * El archivo debe tener líneas con formato "Campo:Valor".
         */
        @Throws(IOException::class)
        fun readCharacter(characterSheet: File): Character {
            val data = Array(11) { "" }
            var i = 0

            BufferedReader(FileReader(characterSheet)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    if (line!!.contains(":")) {
                        val mapping = line!!.split(":")
                        data[i] = mapping[1]
                        i++
                    }
                }
            }

            return CharacterFactory.createFromDataArray(data)
        }

        /**
         * Imprime la ficha del personaje en la carpeta por defecto.
         */
        @Throws(IOException::class)
        fun printCharacterSheet(c: Character) {
            val file = File(defaultDirectory, "${c.getName()}.txt")

            BufferedWriter(FileWriter(file)).use { bw ->
                println("Printing ${c.getName()}'s character sheet.")
                bw.write(c.toString())
            }
        }

        /**
         * Imprime la ficha del personaje en un directorio específico.
         */
        @Throws(IOException::class)
        fun printCharacterSheet(c: Character, d: File) {
            if (d.isDirectory) {
                val file = File(d, "${c.getName()}.txt")

                BufferedWriter(FileWriter(file)).use { bw ->
                    println("Printing ${c.getName()}'s character sheet in the specified directory")
                    bw.write(c.toString())
                }
            }
        }

        /**
         * Imprime la ficha de todo el grupo en un solo archivo.
         * Ordena el grupo antes de imprimir.
         */
        @Throws(IOException::class)
        fun printPartySheet(party: Array<Character>) {
            Arrays.sort(party)

            val file = File(defaultDirectory, "party${party[0].getName()}.txt")

            BufferedWriter(FileWriter(file)).use { bw ->
                for (c in party) {
                    bw.write(c.toString())
                    bw.write("\n----------------\n")
                }
            }
        }

        /**
         * Comprueba si existe un personaje con ese nombre en una lista de archivos.
         */
        @Throws(IOException::class)
        fun checkIfCharacterExists(characterSheets: Array<File>, name: String): Boolean {
            for (f in characterSheets) {
                if (f.isFile && f.canRead()) {
                    val c = readCharacter(f)
                    if (c.getName() == name)
                        return true
                } else {
                    throw IllegalArgumentException("Error. Provide an adecuate list of File paths.")
                }
            }
            return false
        }

        /**
         * Comprueba si hay clases repetidas en una lista de personajes.
         */
        @Throws(IOException::class)
        fun checkIfRepeatedClass(characterSheets: Array<File>): Boolean {
            val classChecklist = BooleanArray(6) // 0W, 1M, 2T, 3H, 4Pl, 5Pr

            for (f in characterSheets) {
                if (f.isFile && f.canRead()) {
                    val className = getClassNameFromFile(f)
                    val index = getClassIndex(className)

                    if (index == -1)
                        throw RuntimeException("Class not found in the index database.")

                    if (classChecklist[index])
                        return false

                    classChecklist[index] = true
                }
            }

            return true
        }

        /**
         * Comprueba si un personaje ganó el último combate leyendo la última línea del log.
         */
        @Throws(IOException::class)
        fun readCombatWin(c: Character, combatLog: File): Boolean {
            if (combatLog.isFile && combatLog.canRead()) {

                var lastLine = ""

                BufferedReader(FileReader(combatLog)).use { br ->
                    var line: String?

                    while (br.readLine().also { line = it } != null) {
                        if (line!!.trim().isNotEmpty()) {
                            lastLine = line!!
                        }
                    }
                }

                return if (lastLine.startsWith(c.getName())) {
                    println("This character won the last combat, and thus levels up.")
                    true
                } else {
                    System.err.println("This character didn't win last combat.")
                    false
                }

            } else {
                throw IllegalArgumentException("Error. You must procure a valid combat log.")
            }
        }

        /**
         * Lee la clase del personaje desde un archivo.
         */
        @Throws(IOException::class)
        private fun getClassNameFromFile(f: File): String {
            BufferedReader(FileReader(f)).use { br ->
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    if (line!!.startsWith("Class:")) {
                        return line!!.split(":")[1].trim()
                    }
                }
            }
            return ""
        }

        /**
         * Devuelve el índice de la clase para el array de comprobación.
         */
        private fun getClassIndex(className: String): Int {
            return when (className) {
                "Warrior" -> 0
                "Mage" -> 1
                "Thief" -> 2
                "Hunter" -> 3
                "Paladin" -> 4
                "Priest" -> 5
                else -> -1
            }
        }
    }
}
