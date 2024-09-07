package kz.hxncus.mc.minesonapikotlin.bukkit.inventory.marker

import org.bukkit.inventory.ItemStack

interface ItemMarker {
    fun markItem(item: ItemStack): ItemStack

    fun unmarkItem(item: ItemStack): ItemStack

    fun isItemMarked(item: ItemStack): Boolean
}
