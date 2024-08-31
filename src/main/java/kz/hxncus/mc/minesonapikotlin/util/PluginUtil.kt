package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler

fun Plugin.registerEvents(listener: Listener) {
    this.server.pluginManager.registerEvents(listener, this)
}

val Plugin.scheduler: BukkitScheduler get() = this.server.scheduler