package kz.hxncus.mc.minesonapikotlin.bukkit.inventory

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.bukkit.inventory.marker.ItemMarker
import kz.hxncus.mc.minesonapikotlin.util.*
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDropItemEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.inventory.PlayerInventory
import org.bukkit.plugin.PluginManager

open class InventoryManager(private val plugin: MinesonAPI) {
    val itemMarker: ItemMarker by lazy {
        getItemMarker(this.plugin)
    }

    init {
        this.registerEvents(this.plugin.server.pluginManager)
    }

    fun registerEvents(pluginManager: PluginManager) {
        val logger = this.plugin.logger
        pluginManager.register(EntityPickupItemEvent::class.java) { event ->
            if (this.itemMarker.isItemMarked(event.item.itemStack)) {
                event.isCancelled = true
                event.item.remove()
                logger.info("Someone picked up a Custom Inventory item. Removing it.")
            }
        }
        pluginManager.register(EntityDropItemEvent::class.java) { event ->
            if (this.itemMarker.isItemMarked(event.itemDrop.itemStack)) {
                event.isCancelled = true
                event.itemDrop.remove()
                logger.info("Someone dropped a Custom Inventory item. Removing it.")
            }
        }
        pluginManager.register(PlayerLoginEvent::class.java) { event ->
            val scheduler = this.plugin.server.scheduler
            scheduler.runTaskLater(this.plugin, { _ ->
                val player = event.player
                val inventory: PlayerInventory = player.inventory
                for (item in inventory) {
                    if (item != null && this.itemMarker.isItemMarked(item)) {
                        inventory.remove(item)
                        logger.info("Player logged in with a Custom Inventory item in their inventory. Removing it.")
                    }
                }
                }, 10L)
        }
        pluginManager.register(InventoryClickEvent::class.java) { event ->
            val inventory = event.inventory
            inventory.handleClick(event)
        }
        pluginManager.register(InventoryOpenEvent::class.java) { event ->
            val inventory = event.inventory
            inventory.handleOpen(event)
        }
        pluginManager.register(InventoryCloseEvent::class.java) { event ->
            val player = event.player
            val inventory = event.inventory
            if (inventory.handleClose(event)) {
                inventory.open(player)
            }
        }
    }

    fun closePlayersInventory() {
        MinesonAPI.plugin.server.onlinePlayers.forEach { player: Player ->
            player.closeInventory()
        }
    }
}
