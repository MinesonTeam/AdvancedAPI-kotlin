package kz.hxncus.mc.minesonapikotlin

import kz.hxncus.mc.minesonapikotlin.bukkit.event.EventManager
import kz.hxncus.mc.minesonapikotlin.bukkit.inventory.InventoryManager
import kz.hxncus.mc.minesonapikotlin.bukkit.server.ServerManager
import kz.hxncus.mc.minesonapikotlin.bukkit.world.WorldManager
import kz.hxncus.mc.minesonapikotlin.color.ColorManager
import kz.hxncus.mc.minesonapikotlin.config.ConfigManager
import org.bukkit.plugin.java.JavaPlugin

class MinesonAPI : JavaPlugin() {
    companion object {
        lateinit var plugin: MinesonAPI
    }

    val colorManager: ColorManager = ColorManager()
    val configManager: ConfigManager = ConfigManager(this)
    val eventManager: EventManager = EventManager(this)
    val inventoryManager: InventoryManager = InventoryManager(this)
    val serverManager: ServerManager = ServerManager(server)
    val worldManager: WorldManager = WorldManager(this)

    init {
        plugin = this
    }

    override fun onEnable() {
    }

    override fun onDisable() {
        inventoryManager.closeAll()
        eventManager.unregisterAll()
    }
}