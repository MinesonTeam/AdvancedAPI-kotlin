package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

fun Inventory.open(player: Player) {
    player.openInventory(this)
}