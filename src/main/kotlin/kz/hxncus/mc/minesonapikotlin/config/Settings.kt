package kz.hxncus.mc.minesonapikotlin.config

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.util.YamlConfiguration
import kz.hxncus.mc.minesonapikotlin.util.colorize
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * The enum Settings.
 * @since 1.0.1
 * @author Hxncus
 */
enum class Settings(private val path: String) {
    VERSION("version"),
    DEBUG("debug");

    val settings: YamlConfiguration by lazy {
        val plugin = MinesonAPI.plugin
        val fileName = "settings.yml"
        return@lazy YamlConfiguration(File(plugin.dataFolder, fileName))
    }

    override fun toString(): String {
        return settings[path].toString().colorize()
    }

    var value: Any? get() = settings[path]
                    set(value) {
                        this.setValue(value, true)
                    }

    /**
     * Sets value.
     *
     * @param value the value
     * @param save  the save
     */
    fun setValue(value: Any?, save: Boolean) {
        settings[path] = value
        if (save) {
            try {
                settings.save(MinesonAPI.plugin.dataFolder.toPath()
                                                          .resolve("settings.yml")
                                                          .toFile())
            } catch (e: Exception) {
                MinesonAPI.plugin.logger.severe("Didn't apply changes to settings.yml")
            }
        }
    }

    /**
     * To string string.
     *
     * @param def the def
     * @return the string
     */
    fun toString(def: Any): String {
        return this.getValue(def).toString().colorize()
    }

    /**
     * Gets value.
     *
     * @param def the def
     * @return the value
     */
    fun getValue(def: Any): Any {
        return settings[path] ?: def
    }

    /**
     * To bool boolean.
     *
     * @return the boolean
     */
    fun toBoolean(): Boolean {
        return value as Boolean
    }

    /**
     * To number.
     *
     * @return the number
     */
    fun toNumber(): Number {
        return value as Number
    }

    /**
     * To string a list.
     *
     * @return the list
     */
    fun toStringList(): List<String> {
        val stringList = settings.getStringList(this.path)
        return stringList.colorize()
    }

    /**
     * To config section configuration section.
     *
     * @return the configuration section
     */
    fun toConfigSection(): ConfigurationSection? {
        return settings.getConfigurationSection(this.path)
    }
}
