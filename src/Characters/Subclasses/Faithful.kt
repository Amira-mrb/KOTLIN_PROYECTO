package Characters.Subclasses

import Characters.Character
import Characters.Stats
import Combat.Attack
import GameDataManagement.CombatLogManager
import java.io.File
import java.io.IOException

/**
 * Clase abstracta para personajes que usan milagros.
 * Añade una acción especial basada en la fe.
 */
abstract class Faithful : Character {

    /**
     * Constructor por defecto.
     */
    constructor() : super()

    /**
     * Constructor con datos básicos.
     */
    constructor(name: String, race: String, lvl: Int, isCPU: Boolean)
            : super(name, race, lvl, isCPU)

    /**
     * Constructor con stats.
     */
    constructor(name: String, race: String, lvl: Int, stats: Stats, isCPU: Boolean)
            : super(name, race, lvl, stats, isCPU)

    /**
     * Constructor de copia.
     */
    constructor(other: Faithful) : super(other)

    /**
     * Constructor desde archivo.
     */
    @Throws(IOException::class)
    constructor(characterSheet: File) : super(characterSheet)

    /**
     * Texto que aparece en el menú de acciones especiales.
     */
    override fun displaySpecialAction(): String {
        return "2. Pray for a miracle."
    }

    /**
     * Acción especial del personaje.
     * Llama al método que lanza el milagro.
     */
    override fun performSpecialAction(): Attack {
        CombatLogManager.out("${getName()} gets ready to call on their god!")
        return castMiracle()
    }

    /**
     * Cada clase hija define su propio milagro.
     */
    protected abstract fun castMiracle(): Attack
}
