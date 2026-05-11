package GameDataManagement

import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter

class CombatLogManager {

    companion object {

        private var writer: PrintWriter? = null

        fun startLog(combatLog: String) {
            try {
                writer = PrintWriter(
                    FileWriter(GameLogger.defaultDirectory + combatLog + ".txt", true)
                )
            } catch (e: IOException) {
                System.err.println("Unable to start logging the combat.")
            }
        }

        fun out(msg: String) {
            println(msg)

            writer?.let {
                it.println(msg)
                it.flush()
            }
        }

        fun closeLog() {
            writer?.close()
        }

        fun hasLogStarted(): Boolean {
            return writer != null
        }
    }
}
