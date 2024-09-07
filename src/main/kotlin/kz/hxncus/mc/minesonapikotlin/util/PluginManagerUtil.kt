package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.bukkit.event.*
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockDispenseArmorEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerStatisticIncrementEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.PluginManager


fun PluginManager.registerCustomEvents() {
    this.register(PlayerInteractEvent::class.java) { event: PlayerInteractEvent ->
        val action = event.action
        when (action) {
            Action.LEFT_CLICK_BLOCK, Action.LEFT_CLICK_AIR -> this.callEvent(
                PlayerLeftClickEvent(
                event.player, event.action, event.item, event.clickedBlock,
                event.blockFace, event.hand, event.clickedPosition)
            )
            Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR -> this.callEvent(
                PlayerRightClickEvent(
                event.player, event.action, event.item, event.clickedBlock,
                event.blockFace, event.hand, event.clickedPosition)
            )
            else -> this.callEvent(
                PlayerPhysicalInteractEvent(
                event.player, event.action, event.item,
                event.clickedBlock, event.blockFace, event.hand, event.clickedPosition)
            )
        }
    }
    this.register(PlayerStatisticIncrementEvent::class.java) { event: PlayerStatisticIncrementEvent ->
        val statistic = event.statistic
        val player = event.player
        if (statistic == Statistic.JUMP) {
            this.callEvent(PlayerJumpEvent(player, player.location, player.location.clone().add(player.velocity)))
        } else if (statistic == Statistic.DAMAGE_BLOCKED_BY_SHIELD) {
            val lastDamageCause = player.lastDamageCause ?: return@register
            this.callEvent(
                PlayerDamageBlockByShieldEvent(player, lastDamageCause.entity, (event.newValue - event.previousValue).toDouble())
            )
        }
    }
    this.register(InventoryClickEvent::class.java) { event: InventoryClickEvent ->
//        var shift = false
//        var numberkey = false
//        if (event.getAction() === InventoryAction.NOTHING) {
//            return@register
//        }
//        if (event.getClick().equals(ClickType.SHIFT_LEFT) || event.getClick().equals(ClickType.SHIFT_RIGHT)) {
//            shift = true
//        }
//        if (event.getClick().equals(ClickType.NUMBER_KEY)) {
//            numberkey = true
//        }
//        if (event.getClick() === ClickType.SWAP_OFFHAND) {
//            numberkey = true
//        }
//        if (event.getSlotType() !== SlotType.ARMOR && event.getSlotType() !== SlotType.QUICKBAR && event.getSlotType() !== SlotType.CONTAINER) return
//        if (event.getClickedInventory() != null && !event.getClickedInventory().getType()
//                .equals(InventoryType.PLAYER)
//        ) return@register
//        if (!event.getInventory().getType().equals(InventoryType.CRAFTING) && !event.getInventory().getType().equals(
//                InventoryType.PLAYER
//            )
//        ) return@register
//        if (event.getWhoClicked() !is Player) return@register
//        var newArmorType = ArmorType.matchType(if (shift) event.getCurrentItem() else event.getCursor())
//        if (!shift && newArmorType != null && event.getRawSlot() !== newArmorType.getSlot()) {
//            // Used for drag and drop checking to make sure you aren't trying to place a helmet in the boots slot.
//            return@register
//        }
//        if (shift) {
//            newArmorType = ArmorType.matchType(event.getCurrentItem())
//            if (newArmorType != null) {
//                var equipping = true
//                if (event.getRawSlot() === newArmorType.getSlot()) {
//                    equipping = false
//                }
//                if (newArmorType == ArmorType.HELMET && (if (equipping) isEmpty(
//                        event.getWhoClicked().getInventory().getHelmet()
//                    ) else !isEmpty(event.getWhoClicked().getInventory().getHelmet())) || newArmorType == ArmorType.CHESTPLATE &&
//                    (if (equipping) isEmpty(event.getWhoClicked().getInventory().getChestplate()
//                    ) else !isEmpty(
//                        event.getWhoClicked().getInventory().getChestplate()
//                    )) || (newArmorType == ArmorType.LEGGINGS && (if (equipping) isEmpty(
//                        event.getWhoClicked().getInventory().getLeggings()
//                    ) else !isEmpty(
//                        event.getWhoClicked().getInventory().getLeggings()
//                    ))) || (newArmorType == ArmorType.BOOTS && (if (equipping) isEmpty(
//                        event.getWhoClicked().getInventory().getBoots()
//                    ) else !isEmpty(event.getWhoClicked().getInventory().getBoots())))
//                ) {
//                    val armorEquipEvent = ArmorEquipEvent(
//                        event.getWhoClicked() as Player, ArmorEquipEvent.EquipMethod.SHIFT_CLICK, newArmorType,
//                        (if (equipping) null else event.getCurrentItem())!!,
//                        (if (equipping) event.getCurrentItem() else null)!!
//                    )
//                    Bukkit.getServer().pluginManager.callEvent(armorEquipEvent)
//                    if (armorEquipEvent.isCancelled) {
//                        event.setCancelled(true)
//                    }
//                }
//            }
//        } else {
//            var newArmorPiece: ItemStack = event.getCursor()
//            var oldArmorPiece: ItemStack? = event.getCurrentItem()
//            if (numberkey) {
//                //System.out.println("NumberKey");
//                if (event.getClickedInventory().getType()
//                        .equals(InventoryType.PLAYER)
//                ) { // Prevents shit in the 2by2 crafting
//                    // e.getClickedInventory() == The players inventory
//                    // e.getHotBarButton() == key people are pressing to equip or unequip the item to or from.
//                    // e.getRawSlot() == The slot the item is going to.
//                    // e.getSlot() == Armor slot, can't use e.getRawSlot() as that gives a hotbar slot ;-;
//                    var hotbarItem: ItemStack? = null
//                    if (event.getHotbarButton() !== -1) {
//                        hotbarItem = event.getClickedInventory().getItem(event.getHotbarButton())
//                    } else if (event.getHotbarButton() === -1 && event.getClickedInventory() is PlayerInventory) {
//                        hotbarItem = (event.getClickedInventory() as PlayerInventory).getItem(EquipmentSlot.OFF_HAND)
//                    }
//                    if (!isEmpty(hotbarItem)) { // Equipping
//                        newArmorType = ArmorType.matchType(hotbarItem!!)
//                        newArmorPiece = hotbarItem
//                        oldArmorPiece = event.getClickedInventory().getItem(event.getSlot())
//                    } else { // Unequipping
//                        newArmorType =
//                            ArmorType.matchType(if (!isEmpty(event.getCurrentItem())) event.getCurrentItem() else event.getCursor())
//                    }
//                }
//            } else {
//                if (isEmpty(event.getCursor()) && !isEmpty(event.getCurrentItem())) { // unequip with no new item going into the slot.
//                    newArmorType = ArmorType.matchType(event.getCurrentItem())
//                }
//                // e.getCurrentItem() == Unequip
//                // e.getCursor() == Equip
//                // newArmorType = ArmorType.matchType(!isEmpty(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor());
//            }
//            if (newArmorType != null && event.getRawSlot() === newArmorType.) {
//                var method = ArmorEquipEvent.EquipMethod.PICK_DROP
//                if (event.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey) method =
//                    ArmorEquipEvent.EquipMethod.HOTBAR_SWAP
//                val armorEquipEvent = ArmorEquipEvent(
//                    event.getWhoClicked() as Player,
//                    method, newArmorType, oldArmorPiece, newArmorPiece
//                )
//                Bukkit.getServer().pluginManager.callEvent(armorEquipEvent)
//                if (armorEquipEvent.isCancelled) {
//                    event.setCancelled(true)
//                }
//            }
//        }
    }
    this.register(BlockDispenseArmorEvent::class.java) { event: BlockDispenseArmorEvent ->
        val armorType: ArmorEquipEvent.ArmorType = ArmorEquipEvent.ArmorType.matchType(event.item) ?: return@register
        val armorEquipEvent = ArmorEquipEvent(event.targetEntity, ArmorEquipEvent.EquipMethod.DISPENSER, armorType, ItemStack(
            Material.AIR), event.item)
        this.callEvent(armorEquipEvent)
        if (armorEquipEvent.isCancelled) {
            event.isCancelled = true
        }
    }
}

/**
 * Register.
 *
 * @param <E>      the type parameter
 * @param event    the event
 * @param consumer the consumer
 */
fun <E : Event> PluginManager.register(event: Class<E>, consumer: EventConsumer<E>) {
    this.register(event, EventPriority.NORMAL, consumer)
}

/**
 * Register.
 *
 * @param <E>      the type parameter
 * @param event    the event
 * @param priority the priority
 * @param consumer the consumer
</E> */
fun <E : Event> PluginManager.register(event: Class<out E>, priority: EventPriority, consumer: Listener) {
    this.registerEvent(event, consumer, priority, { listener: Listener, events: Event ->
        if (event.isInstance(events)) {
            (listener as EventConsumer<E>).accept(event.cast(events))
        }
    }, MinesonAPI.plugin)
}

/**
 * Unregister all.
 */
fun PluginManager.unregisterAll() {
    HandlerList.unregisterAll(MinesonAPI.plugin)
}
