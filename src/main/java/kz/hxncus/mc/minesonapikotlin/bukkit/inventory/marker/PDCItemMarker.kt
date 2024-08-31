package kz.hxncus.mc.minesonapikotlin.bukkit.inventory.marker

import kz.hxncus.mc.minesonapikotlin.util.hasPDC
import kz.hxncus.mc.minesonapikotlin.util.removePDC
import kz.hxncus.mc.minesonapikotlin.util.setPDC
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin

class PDCItemMarker(plugin: Plugin) : ItemMarker {
    private val mark = NamespacedKey(plugin, "MinesonAPI")

    override fun markItem(item: ItemStack): ItemStack {
        item.setPDC(this.mark, PersistentDataType.BYTE, 1.toByte())
        return item
    }

    override fun unmarkItem(item: ItemStack): ItemStack {
        item.removePDC(this.mark)
        return item
    }

    override fun isItemMarked(item: ItemStack): Boolean {
        return item.hasPDC(this.mark, PersistentDataType.BYTE)
    }
}
