package kz.hxncus.mc.minesonapikotlin.bukkit.inventory

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.util.later
import kz.hxncus.mc.minesonapikotlin.util.scheduler
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Predicate

open class SimpleInventory {
    val inventory: Inventory
    private val inventories: MutableList<SimpleInventory> = ArrayList()
    private val itemHandlers: MutableMap<Int, BiConsumer<SimpleInventory, InventoryClickEvent>> = HashMap()
    private val openHandlers: MutableList<BiConsumer<SimpleInventory, InventoryOpenEvent>> = ArrayList()
    private val closeHandlers: MutableList<BiConsumer<SimpleInventory, InventoryCloseEvent>> = ArrayList()
    private val clickHandlers: MutableList<BiConsumer<SimpleInventory, InventoryClickEvent>> = ArrayList()
    private var closeFilter: Predicate<Player>? = null
    private var marking = true
    val contents: Array<out ItemStack?> get() = inventory.contents
    val size: Int get() = inventory.size

    constructor(type: InventoryType?) : this(Bukkit.createInventory(null, type!!))

    constructor(inventory: Inventory) {
        this.inventory = inventory
        this.registerInventory()
    }

    private fun registerInventory() {
        val plugin = MinesonAPI.plugin
        plugin.inventoryManager.registerInventory(this)
        plugin.scheduler.later(1L) { this.onInitialize() }
    }

    protected fun onInitialize() {
        // method is called when the inventory is initialized
    }

    constructor(type: InventoryType?, title: String?) : this(Bukkit.createInventory(null, type!!, title!!))

    constructor(size: Int) : this(Bukkit.createInventory(null, size))

    constructor(size: Int, title: String?) : this(Bukkit.createInventory(null, size, title!!))

    constructor(simpleInventory: SimpleInventory) {
        this.inventory = simpleInventory.inventory
        inventories.addAll(simpleInventory.inventories)
        itemHandlers.putAll(simpleInventory.itemHandlers)
        openHandlers.addAll(simpleInventory.openHandlers)
        closeHandlers.addAll(simpleInventory.closeHandlers)
        clickHandlers.addAll(simpleInventory.clickHandlers)
        this.closeFilter = simpleInventory.closeFilter
        this.marking = simpleInventory.marking
        this.registerInventory()
    }

    fun getPage(pageIndex: Int): SimpleInventory {
        return inventories[pageIndex]
    }

    fun addPages(vararg inventories: SimpleInventory): SimpleInventory {
        for (simpleInventory in inventories) {
            this.addPage(simpleInventory)
        }
        return this
    }

    fun addPage(inventory: SimpleInventory): SimpleInventory {
        inventories.add(inventory)
        inventory.inventories.add(this)
        return this
    }

    fun setPage(pageIndex: Int, inventory: SimpleInventory): SimpleInventory {
        inventories[pageIndex] = inventory
        return this
    }

    fun addItem(item: ItemStack): SimpleInventory {
        return this.addItem(item, null)
    }

    fun addItem(item: ItemStack, handler: BiConsumer<SimpleInventory, InventoryClickEvent>?): SimpleInventory {
        val slot = inventory.firstEmpty()
        if (slot != -1) {
            this.setItem(slot, item, handler)
        }
        return this
    }

    fun setItem(slot: Int, item: ItemStack, handler: BiConsumer<SimpleInventory, InventoryClickEvent>?): SimpleInventory {
        inventory.setItem(slot, if (this.marking) MinesonAPI.plugin.inventoryManager.itemMarker.markItem(item) else item
        )
        if (handler != null) {
            itemHandlers[slot] = handler
        } else {
            itemHandlers.remove(slot)
        }
        return this
    }

    fun getItems(slotFrom: Int, slotTo: Int): Array<ItemStack?> {
        require(!(slotFrom == slotTo || slotFrom > slotTo)) { "slotFrom must be less than slotTo" }
        val items = arrayOfNulls<ItemStack>(slotTo - slotFrom + 1) // 20
        for (i in slotFrom..slotTo) { // 0; i <= 20
            items[i - slotFrom] = inventory.getItem(i)
        }
        return items
    }

    fun getItems(vararg slots: Int): Array<ItemStack?> {
        val items = arrayOfNulls<ItemStack>(slots.size)
        for (i in slots.indices) {
            items[i] = this.getItem(slots[i])
        }
        return items
    }

    fun getItem(slot: Int): ItemStack? {
        return inventory.getItem(slot)
    }

    fun setItemIf(slot: Int, item: ItemStack, predicate: Predicate<SimpleInventory?>): SimpleInventory {
        if (predicate.test(this)) {
            return this.setItem(slot, item)
        }
        return this
    }

    fun setItem(slot: Int, item: ItemStack): SimpleInventory {
        return this.setItem(slot, item, null)
    }

    fun setItems(slotFrom: Int, slotTo: Int, item: ItemStack): SimpleInventory {
        return this.setItems(slotFrom, slotTo, item, null)
    }

    fun setItems(slotFrom: Int, slotTo: Int, item: ItemStack, handler: BiConsumer<SimpleInventory, InventoryClickEvent>?): SimpleInventory {
        for (i in slotFrom..slotTo) {
            this.setItem(i, item, handler)
        }
        return this
    }

    fun setItems(item: ItemStack, vararg slots: Int): SimpleInventory {
        return this.setItems(item, null, *slots)
    }

    fun setItems(item: ItemStack, handler: BiConsumer<SimpleInventory, InventoryClickEvent>?, vararg slots: Int): SimpleInventory {
        for (slot in slots) {
            this.setItem(slot, item, handler)
        }
        return this
    }

    fun removeItems(vararg slots: Int): SimpleInventory {
        for (slot in slots) {
            this.removeItem(slot)
        }
        return this
    }

    fun removeItem(slot: Int): SimpleInventory {
        inventory.clear(slot)
        itemHandlers.remove(slot)
        return this
    }

    fun removeItems(slotFrom: Int, slotTo: Int): SimpleInventory {
        for (i in slotFrom..slotTo) {
            this.removeItem(i)
        }
        return this
    }

    fun clear(): SimpleInventory {
        inventory.clear()
        return this
    }

    fun setCloseFilter(closeFilter: Predicate<Player>?): SimpleInventory {
        this.closeFilter = closeFilter
        return this
    }

    fun addOpenHandler(openHandler: BiConsumer<SimpleInventory, InventoryOpenEvent>): SimpleInventory {
        openHandlers.add(openHandler)
        return this
    }

    fun addCloseHandler(closeHandler: BiConsumer<SimpleInventory, InventoryCloseEvent>): SimpleInventory {
        closeHandlers.add(closeHandler)
        return this
    }

    fun addClickHandler(clickHandler: BiConsumer<SimpleInventory, InventoryClickEvent>): SimpleInventory {
        clickHandlers.add(clickHandler)
        return this
    }

    fun open(humanEntity: HumanEntity): SimpleInventory {
        humanEntity.openInventory(this.inventory)
        return this
    }

    fun handleOpen(event: InventoryOpenEvent) {
        this.onOpen(event)
        openHandlers.forEach(Consumer { open: BiConsumer<SimpleInventory, InventoryOpenEvent> ->
            open.accept(
                this, event
            )
        })
    }

    protected fun onOpen(event: InventoryOpenEvent) {
        // method is called every time when inventory opens
        event.isAsynchronous
    }

    fun handleClose(event: InventoryCloseEvent): Boolean {
        this.onClose(event)
        closeHandlers.forEach(Consumer { close: BiConsumer<SimpleInventory, InventoryCloseEvent> ->
            close.accept(
                this, event
            )
        })
        return this.closeFilter != null && closeFilter!!.test(event.player as Player)
    }

    protected fun onClose(event: InventoryCloseEvent) {
        // method is called every time when inventory closes
        event.isAsynchronous
    }

    fun handleClick(event: InventoryClickEvent) {
        this.onClick(event)

        clickHandlers.forEach(Consumer { click: BiConsumer<SimpleInventory, InventoryClickEvent> ->
            click.accept(
                this, event
            )
        })

        val clickConsumer = itemHandlers[event.rawSlot]

        clickConsumer?.accept(this, event)
    }

    protected fun onClick(event: InventoryClickEvent) {
        // method is called every time an item is clicked
        event.isAsynchronous
    }

    fun copy(): SimpleInventory {
        return SimpleInventory(this)
    }
}
