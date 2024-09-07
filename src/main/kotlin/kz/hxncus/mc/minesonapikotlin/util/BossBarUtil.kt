package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar

fun BossBar(key: String, title: String, barColor: BarColor, barStyle: BarStyle, vararg barFlag: BarFlag): BossBar {
    return Bukkit.createBossBar(NamespacedKey(key), title, barColor, barStyle, *barFlag)
}
