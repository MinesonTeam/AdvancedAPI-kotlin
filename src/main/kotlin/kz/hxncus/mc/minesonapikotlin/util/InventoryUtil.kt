package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.bukkit.inventory.Pagination
import kz.hxncus.mc.minesonapikotlin.util.lazy.LazyWithReceiver
import net.minecraft.world.Container
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventory
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Predicate

val Inventory.title: String by LazyWithReceiver<Inventory, String> {
    return@LazyWithReceiver this.viewers.first().name
}
var Inventory.pagination: Pagination? get() = null
                                      set(value) { }
private val Inventory.itemHandlers: MutableMap<Int, BiConsumer<Inventory, InventoryClickEvent>> by lazy { HashMap() }
private val Inventory.openHandlers: MutableList<BiConsumer<Inventory, InventoryOpenEvent>> by lazy { ArrayList() }
private val Inventory.closeHandlers: MutableList<BiConsumer<Inventory, InventoryCloseEvent>> by lazy { ArrayList() }
private val Inventory.clickHandlers: MutableList<BiConsumer<Inventory, InventoryClickEvent>> by lazy { ArrayList() }
private var Inventory.closeFilter: Predicate<Player>? get() = null
                                                      set(value) { }
private var Inventory.marking: Boolean get() = true
                                       set(value) { }
val Inventory.nms: Container? by LazyWithReceiver<Inventory, Container> {
    return@LazyWithReceiver (this as CraftInventory).inventory
}

fun Inventory(name: String, rows: Int, pagination: Pagination): Inventory {
    val inventory = MinesonAPI.plugin.server.createInventory(null, rows * 9, name)
    inventory.pagination = pagination
    return inventory
}

fun Inventory.isMainInventory(): Boolean {
    return pagination?.inventory?.equals(this) ?: false
}

fun Inventory.addItem(item: ItemStack): Inventory {
    return this.addItem(item, null)
}

fun Inventory.addItem(item: ItemStack, handler: BiConsumer<Inventory, InventoryClickEvent>?): Inventory {
    val slot = this.firstEmpty()
    if (slot != -1) {
        this.setItem(slot, item, handler)
    }
    return this
}

fun Inventory.getItems(slotFrom: Short, slotTo: Short): Array<ItemStack?> {
    require(!(slotFrom == slotTo || slotFrom > slotTo)) { "slotFrom must be less than slotTo" }
    val items = arrayOfNulls<ItemStack>(slotTo - slotFrom + 1)
    for (i in slotFrom..slotTo) {
        items[i - slotFrom] = this.getItem(i)
    }
    return items
}

fun Inventory.getItems(vararg slots: Int): Array<ItemStack?> {
    val items = arrayOfNulls<ItemStack>(slots.size)
    for (i in slots.indices) {
        items[i] = this.getItem(slots[i])
    }
    return items
}

fun Inventory.setItemIf(slot: Int, item: ItemStack, predicate: Predicate<Inventory?>): Inventory {
    if (predicate.test(this)) {
        this.setItem(slot, item)
    }
    return this
}

fun Inventory.setItem(item: ItemStack, slot: Int): Inventory {
    return this.setItem(slot, item, null)
}

fun Inventory.setItems(slotFrom: Int, slotTo: Int, item: ItemStack): Inventory {
    return this.setItems(slotFrom, slotTo, item, null)
}

fun Inventory.setItems(slotFrom: Int, slotTo: Int, item: ItemStack, handler: BiConsumer<Inventory, InventoryClickEvent>?): Inventory {
    for (i in slotFrom..slotTo) {
        this.setItem(i, item, handler)
    }
    return this
}

fun Inventory.setItems(item: ItemStack, vararg slots: Int): Inventory {
    return this.setItems(item, null, *slots)
}

fun Inventory.setItems(item: ItemStack, handler: BiConsumer<Inventory, InventoryClickEvent>?, vararg slots: Int): Inventory {
    for (slot in slots) {
        this.setItem(slot, item, handler)
    }
    return this
}

fun Inventory.setItem(slot: Int, item: ItemStack, handler: BiConsumer<Inventory, InventoryClickEvent>?): Inventory {
    this.setItem(slot, if (this.marking) MinesonAPI.plugin.inventoryManager.itemMarker.markItem(item) else item
    )
    if (handler != null) {
        itemHandlers[slot] = handler
    } else {
        itemHandlers.remove(slot)
    }
    return this
}

fun Inventory.removeItems(vararg slots: Int) {
    for (slot in slots) {
        this.removeItem(slot)
    }
}

fun Inventory.removeItems(slotFrom: Short, slotTo: Short): Inventory {
    for (i in slotFrom..slotTo) {
        this.removeItem(i)
    }
    return this
}

fun Inventory.removeItem(slot: Int): Inventory {
    this.clear(slot)
    itemHandlers.remove(slot)
    return this
}

fun Inventory.open(humanEntity: HumanEntity): InventoryView? {
    return humanEntity.openInventory(this)
}

fun Inventory.addOpenHandler(openHandler: BiConsumer<Inventory, InventoryOpenEvent>): Inventory {
    openHandlers.add(openHandler)
    return this
}

fun Inventory.addCloseHandler(closeHandler: BiConsumer<Inventory, InventoryCloseEvent>): Inventory {
    closeHandlers.add(closeHandler)
    return this
}

fun Inventory.addClickHandler(clickHandler: BiConsumer<Inventory, InventoryClickEvent>): Inventory {
    clickHandlers.add(clickHandler)
    return this
}

fun Inventory.handleOpen(event: InventoryOpenEvent): Inventory {
    openHandlers.forEach(Consumer { open: BiConsumer<Inventory, InventoryOpenEvent> ->
        open.accept(this, event)
    })
    return this
}

fun Inventory.handleClose(event: InventoryCloseEvent): Boolean {
    closeHandlers.forEach(Consumer { close: BiConsumer<Inventory, InventoryCloseEvent> ->
        close.accept(this, event)
    })
    return closeFilter?.test(event.player as Player) ?: false
}

fun Inventory.handleClick(event: InventoryClickEvent): Inventory {
    clickHandlers.forEach(Consumer { click: BiConsumer<Inventory, InventoryClickEvent> ->
        click.accept(this, event)
    })
    val clickConsumer = itemHandlers[event.rawSlot]
    clickConsumer?.accept(this, event)
    return this
}
