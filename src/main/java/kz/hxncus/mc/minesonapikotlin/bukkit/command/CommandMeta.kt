package kz.hxncus.mc.minesonapikotlin.bukkit.command

import org.bukkit.command.CommandSender
import java.util.function.Predicate

/**
 * The type Command meta.
 */
data class CommandMeta(var commandName: String?) {
    private var permission: String = ""
    private var aliases: Array<String?> = arrayOfNulls(8)
    private var requirements: Predicate<CommandSender> = Predicate { true }
    private var shortDescription: String = ""
    private var fullDescription: String = ""
    private var usageDescription: Array<String?> = arrayOfNulls(8)
    private var helpTopic: String = ""

    /**
     * Instantiates a new Command meta.
     *
     * @param original the original
     */
    constructor(original: CommandMeta) : this(original.commandName) {
        this.permission = original.permission
        this.aliases = original.aliases.copyOf()
        this.requirements = original.requirements
        this.shortDescription = original.shortDescription
        this.fullDescription = original.fullDescription
        this.usageDescription = original.usageDescription
        this.helpTopic = original.helpTopic
    }
}
