package kz.hxncus.mc.minesonapikotlin.bukkit.server

import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.command.SimpleCommandMap
import org.bukkit.plugin.SimplePluginManager

class ServerManager(private val server: Server) {
    val knownCommands: Map<String, Command> get() {
        try {
            val commandMap = this.simpleCommandMap
            val knownCommandsField = commandMap.javaClass.getDeclaredField("knownCommands")
            knownCommandsField.isAccessible = true
            return knownCommandsField[commandMap] as Map<String, Command>
        } catch (e: NoSuchFieldException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
    }

    val simpleCommandMap: SimpleCommandMap get() {
        try {
            val commandMapField = simplePluginManager.javaClass.getDeclaredField("commandMap")
            commandMapField.isAccessible = true
            return commandMapField[simplePluginManager] as SimpleCommandMap
        } catch (e: NoSuchFieldException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
    }

    val simplePluginManager: SimplePluginManager get() = server.pluginManager as SimplePluginManager

    val isPaperServer: Boolean get() {
        if ("Paper".equals(server.name, ignoreCase = true)) {
            return true
        }
        try {
            Class.forName("com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent")
            return true
        } catch (e: ClassNotFoundException) {
            return false
        }
    }

    val isFoliaServer: Boolean get() = "folia".equals(server.name, ignoreCase = true)
}
