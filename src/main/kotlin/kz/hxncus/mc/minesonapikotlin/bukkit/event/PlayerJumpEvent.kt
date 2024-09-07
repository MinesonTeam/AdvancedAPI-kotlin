package kz.hxncus.mc.minesonapikotlin.bukkit.event

import kz.hxncus.mc.minesonapikotlin.util.ZERO_VECTOR
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PlayerJumpEvent(who: Player,
                      private val from: Location,
                      private val to: Location) : PlayerEvent(who), Cancellable {
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
            player.velocity = ZERO_VECTOR
        }
    }

    override fun getHandlers(): HandlerList {
        return Companion.handlers
    }
}
