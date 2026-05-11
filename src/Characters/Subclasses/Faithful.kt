package Characters.Subclasses

import Characters.Character
import Characters.Stats
import Combat.Attack
import GameDataManagement.CombatLogManager
import java.io.File
import java.io.IOException

abstract class Faithful : Character {

    constructor() : super()

    constructor(name: String, race: String, lvl: Int, isCPU: Boolean)
            : super(name, race, lvl, isCPU)

    constructor(name: String, race: String, lvl: Int, stats: Stats, isCPU: Boolean)
            : super(name, race, lvl, stats, isCPU)

    constructor(other: Faithful) : super(other)

    @Throws(IOException::class)
    constructor(characterSheet: File) : super(characterSheet)

    override fun displaySpecialAction(): String {
        return "2. Pray for a miracle."
    }

    override fun performSpecialAction(): Attack {
        CombatLogManager.out("${getName()} gets ready to call on their god!")
        return castMiracle()
    }

    protected abstract fun castMiracle(): Attack
}
