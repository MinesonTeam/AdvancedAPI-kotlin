package kz.hxncus.mc.minesonapikotlin.config

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.util.YamlConfiguration
import kz.hxncus.mc.minesonapikotlin.util.colorize
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * The enum Messages.
 * @author Hxncus
 * @since  1.0.1
 */
enum class Messages(private val path: String) {
    PREFIX("general.prefix");

    val languages: YamlConfiguration by lazy {
        val plugin = MinesonAPI.plugin
        val fileName = "languages.yml"
        return@lazy YamlConfiguration(File(plugin.dataFolder, fileName))
    }

    /**
     * To path string.
     *
     * @return the string
     */
    fun toPath(): String {
        return this.path
    }

    override fun toString(): String {
        return this.process(value.toString())
    }

    /**
     * To string string.
     *
     * @param def the def
     * @return the string
     */
    fun toString(def: String): String {
        return this.process(this.getValue(def).toString())
    }

    /**
     * Process string.
     *
     * @param message the message
     * @return the string
     */
    fun process(message: String): String {
        return this.format(message).colorize()
    }

    /**
     * Gets value.
     *
     * @param def the def
     * @return the value
     */
    fun getValue(def: Any): Any {
        return this.languages[this.path] ?: def
    }

    private fun format(message: String, vararg args: Any?): String {
        var result = message
        for (i in args.indices) {
            result = result.replace("{$i}", args[i].toString())
        }
        return result.replace("{PREFIX}", value.toString().colorize())
    }

    val value: Any get() {
        val obj = this.languages[this.path]
        return obj ?: ""
    }

    /**
     * To string string.
     *
     * @param args the args
     * @return the string
     */
    fun toString(vararg args: Any): String {
        return this.process(value.toString(), *args)
    }

    /**
     * To string a list.
     *
     * @return the list
     */
    fun toStringList(): List<String> {
        val stringList = languages.getStringList(this.path)
        return stringList.colorize()
    }

    /**
     * Process string.
     *
     * @param message the message
     * @param args    the args
     * @return the string
     */
    fun process(message: String, vararg args: Any?): String {
        return this.format(message, *args).colorize()
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
        val consoleSender = MinesonAPI.plugin.server.consoleSender
        if (value is List<*>) {
            this.sendList(consoleSender, *args)
        } else {
            this.send(consoleSender, this.toString(), *args)
        }
    }
}
