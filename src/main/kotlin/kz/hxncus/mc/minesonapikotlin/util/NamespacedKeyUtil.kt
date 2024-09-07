package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import org.bukkit.NamespacedKey

fun NamespacedKey(name: String): NamespacedKey = NamespacedKey(MinesonAPI.plugin, name)
