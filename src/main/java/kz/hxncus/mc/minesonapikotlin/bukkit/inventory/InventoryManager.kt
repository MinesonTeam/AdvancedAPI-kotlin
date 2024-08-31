package kz.hxncus.mc.minesonapikotlin.bukkit.inventory

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.bukkit.event.EventManager
import kz.hxncus.mc.minesonapikotlin.bukkit.inventory.marker.ItemMarker
import kz.hxncus.mc.minesonapikotlin.bukkit.inventory.marker.PDCItemMarker
import kz.hxncus.mc.minesonapikotlin.bukkit.inventory.marker.UnavailableItemMarker
import kz.hxncus.mc.minesonapikotlin.util.isAfterOrEqual
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDropItemEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.PlayerInventory
import java.util.concurrent.ConcurrentHashMap

open class InventoryManager(private val plugin: MinesonAPI) {
    companion object {
        protected val inventories: MutableMap<Inventory, SimpleInventory> = ConcurrentHashMap()
    }
    val itemMarker: ItemMarker

    init {
        this.itemMarker = this.getItemMarker(this.plugin)
        this.registerEvents(this.plugin.eventManager)
    }

    private fun getItemMarker(plugin: MinesonAPI): ItemMarker {
        return if (isAfterOrEqual(1140)) {
            PDCItemMarker(plugin)
        } else {
            UnavailableItemMarker()
        }
    }

    fun registerEvents(eventManager: EventManager) {
        eventManager.register(EntityPickupItemEvent::class.java) { event ->
            if (itemMarker.isItemMarked(event.item.itemStack)) {
                event.isCancelled = true
                event.item.remove()
                this.plugin.logger.info("Someone picked up a Custom Inventory item. Removing it.")
            }
        }
        eventManager.register(EntityDropItemEvent::class.java) { event ->
            if (itemMarker.isItemMarked(event.itemDrop.itemStack)) {
                event.isCancelled = true
                event.itemDrop.remove()
                plugin.logger.info("Someone dropped a Custom Inventory item. Removing it.")
            }
        }
        eventManager.register(PlayerLoginEvent::class.java) { event ->
            plugin.server.scheduler.runTaskLater(this.plugin, { _ ->
                val inventory: PlayerInventory = event.player.inventory
                    for (item in inventory) {
                        if (item != null && itemMarker.isItemMarked(item)) {
                            inventory.remove(item)
                            this.plugin.logger.info("Player logged in with a Custom Inventory item in their inventory. Removing it.")
                        }
                    }
                }, 10L)
        }
        eventManager.register(InventoryClickEvent::class.java) { event ->
            val simpleInventory = inventories[event.inventory]
            if (event.clickedInventory != null && simpleInventory != null) {
                simpleInventory.handleClick(event)
            }
        }
        eventManager.register(InventoryOpenEvent::class.java) { event ->
            val simpleInventory = inventories[event.inventory]
            simpleInventory?.handleOpen(event)
        }
        eventManager.register(InventoryCloseEvent::class.java) { event ->
            val simpleInventory = inventories[event.inventory]
            if (simpleInventory != null && simpleInventory.handleClose(event)) {
                simpleInventory.open(event.player)
            }
        }
    }

    fun closeAll() {
        Bukkit.getOnlinePlayers().forEach { player: Player ->
            if (inventories.containsKey(player.openInventory.topInventory)) {
                player.closeInventory()
            }
        }
    }

    fun registerInventory(inventory: SimpleInventory) {
        inventories[inventory.inventory] = inventory
    }

    fun unregisterInventory(inventory: SimpleInventory) {
        inventories[inventory.inventory] = inventory
    }
}
