package kz.hxncus.mc.minesonapikotlin.bukkit.event

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.bukkit.event.EventManager.EventConsumer
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
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager

/**
 * Class Event manager.
 *
 * @author Hxncus
 * @since 1.0.
 */
class EventManager(private val plugin: Plugin) {
    private val pluginManager: PluginManager

    init {
        val server = plugin.server
        this.pluginManager = server.pluginManager
        this.registerCustomEvents()
    }

    private fun registerCustomEvents() {
        this.register(PlayerInteractEvent::class.java) { event: PlayerInteractEvent ->
            val action = event.action
            when (action) {
                Action.LEFT_CLICK_BLOCK, Action.LEFT_CLICK_AIR -> this.callEvent(PlayerLeftClickEvent(
                        event.player, event.action, event.item, event.clickedBlock,
                        event.blockFace, event.hand, event.clickedPosition))
                Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR -> this.callEvent(PlayerRightClickEvent(
                        event.player, event.action, event.item, event.clickedBlock,
                        event.blockFace, event.hand, event.clickedPosition))
                else -> this.callEvent(PlayerPhysicalInteractEvent(
                        event.player, event.action, event.item,
                        event.clickedBlock, event.blockFace, event.hand, event.clickedPosition))
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
        this.register(InventoryClickEvent::class.java) { }
        this.register(BlockDispenseArmorEvent::class.java) { event: BlockDispenseArmorEvent ->
            val armorType: ArmorEquipEvent.ArmorType = ArmorEquipEvent.ArmorType.matchType(event.item) ?: return@register
            val armorEquipEvent = ArmorEquipEvent(event.targetEntity, ArmorEquipEvent.EquipMethod.DISPENSER, armorType, ItemStack(Material.AIR), event.item)
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
    fun <E : Event> register(event: Class<E>, consumer: EventConsumer<E>) {
        this.register(event, EventPriority.NORMAL, consumer)
    }

    /**
     * Call event.
     *
     * @param event Event that needs to call
     */
    fun callEvent(event: Event) {
        pluginManager.callEvent(event)
    }

    /**
     * Register.
     *
     * @param <E>      the type parameter
     * @param event    the event
     * @param priority the priority
     * @param consumer the consumer
    </E> */
    fun <E : Event> register(event: Class<out E>, priority: EventPriority, consumer: Listener) {
        pluginManager.registerEvent(event, consumer, priority, { listener: Listener, events: Event ->
            if (event.isInstance(events)) {
                (listener as EventConsumer<E>).accept(event.cast(events))
            }
        }, this.plugin)
    }

    /**
     * Unregister all.
     */
    fun unregisterAll() {
        HandlerList.unregisterAll(MinesonAPI.plugin)
    }

    /**
     * The interface Event consumer.
     *
     * @param <E> the type parameter
     * @author Hxncus
     * @since 1.0.
    </E> */
    @FunctionalInterface
    fun interface EventConsumer<E : Event?> : Listener {
        /**
         * Append event consumer.
         *
         * @param other the other
         * @return the event consumer
         */
        fun append(other: EventConsumer<in E>): EventConsumer<E> {
            return EventConsumer { event: E ->
                this.accept(event)
                other.accept(event)
            }
        }

        /**
         * Accept.
         *
         * @param event the event
         */
        fun accept(event: E)

        /**
         * Prepend event consumer.
         *
         * @param other the other
         * @return the event consumer
         */
        fun prepend(other: EventConsumer<in E>): EventConsumer<E> {
            return EventConsumer { event: E ->
                other.accept(event)
                this.accept(event)
            }
        }
    }
}
