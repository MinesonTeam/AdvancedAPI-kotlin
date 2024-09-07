package kz.hxncus.mc.minesonapikotlin.bukkit.command.argument

/**
 * The type Abstract argument.
 */
abstract class AbstractArgument protected constructor(private val nodeName: String) : Argument {
    private val suggestions: Set<String> = HashSet(100)
    private val optional = false
}