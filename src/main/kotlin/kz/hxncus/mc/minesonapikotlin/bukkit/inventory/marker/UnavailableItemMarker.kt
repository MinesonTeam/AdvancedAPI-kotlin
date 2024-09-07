package kz.hxncus.mc.minesonapikotlin.bukkit.inventory.marker

import org.bukkit.inventory.ItemStack

class UnavailableItemMarker : ItemMarker {
    override fun markItem(item: ItemStack): ItemStack {
        return item
    }

    override fun unmarkItem(item: ItemStack): ItemStack {
        return item
    }

    override fun isItemMarked(item: ItemStack): Boolean {
        return false
    }
}
