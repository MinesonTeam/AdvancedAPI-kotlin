package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.util.lazy.LazyWithReceiver
import org.bukkit.Server
import org.bukkit.attribute.Attribute
import org.bukkit.boss.KeyedBossBar
import org.bukkit.command.Command
import org.bukkit.command.SimpleCommandMap
import org.bukkit.plugin.SimplePluginManager

/**
 * The Attributes.
 */
val Server.attributes: Array<Attribute> by lazy {
    Attribute.entries.toTypedArray()
}

val Server.knownCommands: Map<String, Command> by LazyWithReceiver<Server, Map<String, Command>> {
    try {
        val commandMap = this.simpleCommandMap
        val knownCommandsField = commandMap.javaClass.getDeclaredField("knownCommands")
        knownCommandsField.isAccessible = true
        return@LazyWithReceiver knownCommandsField[commandMap] as Map<String, Command>
    } catch (e: NoSuchFieldException) {
        throw RuntimeException(e)
    } catch (e: IllegalAccessException) {
        throw RuntimeException(e)
    }
}

val Server.simpleCommandMap: SimpleCommandMap by LazyWithReceiver<Server, SimpleCommandMap> {
    try {
        val commandMapField = this.simplePluginManager.javaClass.getDeclaredField("commandMap")
        commandMapField.isAccessible = true
        return@LazyWithReceiver commandMapField[this.simplePluginManager] as SimpleCommandMap
    } catch (e: NoSuchFieldException) {
        throw RuntimeException(e)
    } catch (e: IllegalAccessException) {
        throw RuntimeException(e)
    }
}

val Server.simplePluginManager: SimplePluginManager by LazyWithReceiver<Server, SimplePluginManager> {
    return@LazyWithReceiver this.pluginManager as SimplePluginManager
}

val Server.isPaperServer: Boolean by LazyWithReceiver<Server, Boolean> {
    if ("Paper".equals(this.name, ignoreCase = true)) {
        return@LazyWithReceiver true
    }
    try {
        Class.forName("com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent")
        return@LazyWithReceiver true
    } catch (e: ClassNotFoundException) {
        return@LazyWithReceiver false
    }
}

val Server.isFoliaServer: Boolean by LazyWithReceiver<Server, Boolean> {
    return@LazyWithReceiver "folia".equals(this.name, ignoreCase = true)
}

fun Server.getBossBar(key: String): KeyedBossBar? {
    return this.getBossBar(NamespacedKey(key))
}
