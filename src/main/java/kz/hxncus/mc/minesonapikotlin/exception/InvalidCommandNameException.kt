package kz.hxncus.mc.minesonapikotlin.exception

/**
 * The type Invalid command name exception.
 * @author Hxncus
 * @since  1.0.1
 */
class InvalidCommandNameException (commandName: String) : RuntimeException("Command name $commandName is not a valid.")
