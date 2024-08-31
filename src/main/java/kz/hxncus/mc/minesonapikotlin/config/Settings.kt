package kz.hxncus.mc.minesonapikotlin.config

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration

/**
 * The enum Settings.
 * @since 1.0.1
 * @author Hxncus
 */
enum class Settings(private val path: String) {
    VERSION("version"),
    DEBUG("debug");

    override fun toString(): String {
        return this.colorize(settings[path] as String)
    }

    /**
     * Colorize string.
     *
     * @param input the input
     * @return the string
     */
    fun colorize(input: String): String {
        return MinesonAPI.plugin.colorManager.process(input)
    }

    var value: Any?
        /**
         * Gets value.
         *
         * @return the value
         */
        get() = settings[path]
        /**
         * Sets value.
         *
         * @param value the value
         */
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
                settings.save(MinesonAPI.plugin.dataFolder
                                        .toPath()
                                        .resolve("settings.yml")
                                        .toFile())
            } catch (e: Exception) {
                MinesonAPI.plugin.logger.severe("Failed to apply changes to settings.yml")
            }
        }
    }

    val settings: YamlConfiguration get() = MinesonAPI.plugin.configManager.getOrCreateConfig("settings.yml")

    /**
     * To string string.
     *
     * @param def the def
     * @return the string
     */
    fun toString(def: Any?): String {
        return this.colorize(this.getValue(def) as String)
    }

    /**
     * Gets value.
     *
     * @param def the def
     * @return the value
     */
    fun getValue(def: Any?): Any {
        return settings[path, def]!!
    }

    /**
     * To bool boolean.
     *
     * @return the boolean
     */
    fun toBool(): Boolean {
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
        stringList.replaceAll { input: String -> this.colorize(input) }
        return stringList
    }

    /**
     * To config section configuration section.
     *
     * @return the configuration section
     */
    fun toConfigSection(): ConfigurationSection {
        return settings.getConfigurationSection(this.path)!!
    }
}
