package kz.hxncus.mc.minesonapikotlin.config

import org.bukkit.plugin.Plugin
import java.util.concurrent.ConcurrentHashMap

/**
 * Class Config manager.
 *
 * @author Hxncus
 * @since 1.0.0
 */
class ConfigManager(private val plugin: Plugin) {
    private val stringConfigMap: MutableMap<String, SimpleConfig> = ConcurrentHashMap(8)

    /**
     * Gets or create config.
     *
     * @param name the name
     * @return the or creation config
     */
    fun getOrCreateConfig(name: String): SimpleConfig {
        return stringConfigMap.computeIfAbsent(name) { nameFunc: String ->
            SimpleConfig(plugin.dataFolder, nameFunc)
        }
    }

    /**
     * Gets or create config.
     *
     * @param parent the parent
     * @param child  the child
     * @return the or creation config
     */
    fun getOrCreateConfig(parent: String, child: String): SimpleConfig {
        return stringConfigMap.computeIfAbsent(child) { nameFunc: String ->
            SimpleConfig(plugin.dataFolder.toString() + parent, nameFunc)
        }
    }
}
