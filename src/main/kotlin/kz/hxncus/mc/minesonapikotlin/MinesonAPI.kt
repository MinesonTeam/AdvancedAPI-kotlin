package kz.hxncus.mc.minesonapikotlin

import kz.hxncus.mc.minesonapikotlin.bukkit.inventory.InventoryManager
import kz.hxncus.mc.minesonapikotlin.color.ColorManager
import kz.hxncus.mc.minesonapikotlin.util.unregisterAll
import org.bukkit.plugin.java.JavaPlugin

class MinesonAPI : JavaPlugin() {
    companion object {
        lateinit var plugin: MinesonAPI
    }

    val colorManager: ColorManager = ColorManager()
    val inventoryManager: InventoryManager = InventoryManager(this)

    init {
        plugin = this
    }

    override fun onEnable() {
        
    }

    override fun onDisable() {
        inventoryManager.closePlayersInventory()
        server.pluginManager.unregisterAll()
    }
}