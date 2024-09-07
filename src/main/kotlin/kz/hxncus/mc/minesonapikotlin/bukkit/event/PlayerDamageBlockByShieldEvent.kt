package kz.hxncus.mc.minesonapikotlin.bukkit.event

import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * PlayerDamageBlockByShieldEvent
 */
class PlayerDamageBlockByShieldEvent(who: Player,
                                     private val source: Entity,
                                     private val damage: Double) : PlayerEvent(who), Cancellable {
    companion object {
        private val handlers = HandlerList()
    }

    private var cancelled = false

    override fun isCancelled(): Boolean {
        return this.cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
        if (cancel) {
            player.damage(this.damage, this.source)
            val inventory = player.inventory
            val item = if (inventory.itemInMainHand.type == Material.SHIELD) {
                inventory.itemInMainHand
            } else if (inventory.itemInOffHand.type == Material.SHIELD) {
                inventory.itemInOffHand
            } else {
                return
            }
            item.durability = 1.toShort()
        }
    }

    override fun getHandlers(): HandlerList {
        return Companion.handlers
    }
}
