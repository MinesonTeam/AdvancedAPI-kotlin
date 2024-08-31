package kz.hxncus.mc.minesonapikotlin.config

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration

/**
 * The enum Messages.
 * @author Hxncus
 * @since  1.0.1
 */
enum class Messages(private val path: String) {
    PREFIX("general.prefix"), TEST("general.test");

    /**
     * To path string.
     *
     * @return the string
     */
    fun toPath(): String {
        return this.path
    }

    override fun toString(): String {
        return this.process(
            value
                .toString()
        )
    }

    /**
     * To string string.
     *
     * @param def the def
     * @return the string
     */
    fun toString(def: String?): String {
        return this.process(
            this.getValue(def)
                .toString()
        )
    }

    /**
     * Process string.
     *
     * @param message the message
     * @return the string
     */
    fun process(message: String): String {
        return this.colorize(this.format(message))
    }

    /**
     * Gets value.
     *
     * @param def the def
     * @return the value
     */
    fun getValue(def: Any?): Any {
        return languages[path, def]!!
    }

    private fun colorize(message: String): String {
        return MinesonAPI.plugin.colorManager.process(message)
    }

    private fun format(message: String, vararg args: Any?): String {
        var result = message
        for (i in args.indices) {
            result = result.replace("{$i}", args[i].toString())
        }
        return result.replace("{PREFIX}", this.colorize(value.toString()))
    }

    val languages: YamlConfiguration
        /**
         * Gets languages.
         *
         * @return the languages
         */
        get() = MinesonAPI.plugin.configManager.getOrCreateConfig("languages.yml")

    val value: Any
        /**
         * Gets value.
         *
         * @return the value
         */
        get() {
            val obj = languages[path]
            return obj ?: ""
        }

    /**
     * To string string.
     *
     * @param args the args
     * @return the string
     */
    fun toString(vararg args: Any): String {
        return this.process(
            value
                .toString(), *args
        )
    }

    /**
     * To string a list.
     *
     * @return the list
     */
    fun toStringList(): List<String> {
        val stringList = languages
            .getStringList(this.path)
        stringList.replaceAll { message: String -> this.process(message) }
        return stringList
    }

    /**
     * Process string.
     *
     * @param message the message
     * @param args    the args
     * @return the string
     */
    fun process(message: String, vararg args: Any?): String {
        return this.colorize(this.format(message, *args))
    }

    private fun send(sender: CommandSender, msg: String?, vararg args: Any?) {
        // No need to send an empty or null message.
        if (msg.isNullOrEmpty()) {
            return
        }
        sender.sendMessage(this.process(msg, *args))
    }

    private fun sendList(sender: CommandSender, vararg args: Any?) {
        for (str in this.toStringList()) {
            this.send(sender, str, *args)
        }
    }

    /**
     * Send.
     *
     * @param sender the sender
     * @param args   the args
     */
    fun send(sender: CommandSender, vararg args: Any?) {
        if (value is List<*>) {
            this.sendList(sender, *args)
        } else {
            this.send(sender, this.toString(), *args)
        }
    }

    /**
     * Log.
     *
     * @param args the args
     */
    fun log(vararg args: Any) {
        if (value is List<*>) {
            this.sendList(Bukkit.getConsoleSender(), *args)
        } else {
            this.send(Bukkit.getConsoleSender(), this.toString(), *args)
        }
    }
}
