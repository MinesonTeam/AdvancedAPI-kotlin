package kz.hxncus.mc.minesonapikotlin.bukkit.command

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.bukkit.command.argument.Argument
import kz.hxncus.mc.minesonapikotlin.util.knownCommands
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

/**
 * The type Abstract command.
 */
abstract class AbstractCommand protected constructor(commandName: String) : ICommand {
    private val commandMeta = CommandMeta(commandName)
    private val arguments: MutableList<Argument> = mutableListOf()
    private val subcommands: List<ICommand> = ArrayList(10)

    /**
     * With arguments abstract command.
     *
     * @param args the args
     * @return the abstract command
     */
    fun withArguments(args: Collection<Argument>): AbstractCommand {
        arguments.addAll(args)
        return this
    }

    /**
     * With arguments abstract command.
     *
     * @param args the args
     * @return the abstract command
     */
    fun withArguments(vararg args: Argument): AbstractCommand {
        arguments.addAll(mutableListOf(*args))
        return this
    }

    /**
     * With optional arguments abstract command.
     *
     * @param args the args
     * @return the abstract command
     */
    fun withOptionalArguments(args: Iterable<Argument>): AbstractCommand {
        for (arg in args) {
            arg.setOptional(true)
            arguments.add(arg)
        }
        return this
    }

    /**
     * With optional arguments abstract command.
     *
     * @param args the args
     * @return the abstract command
     */
    fun withOptionalArguments(vararg args: Argument): AbstractCommand {
        for (arg in args) {
            arg.setOptional(true)
            arguments.add(arg)
        }
        return this
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>?): Boolean {
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>?): List<String>? {
        return emptyList()
    }

    /**
     * Register command.
     *
     * @param plugin the plugin
     */
    fun register(plugin: MinesonAPI) {
        plugin.server.knownCommands.plus(Pair(commandMeta.commandName, null))
    }
}