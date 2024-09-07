package kz.hxncus.mc.minesonapikotlin.bukkit.event

import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

/**
 * Class Player right-click event.
 *
 * @author Hxncus
 * @since 1.0.1
 */
class PlayerRightClickEvent(who: Player,
                            private val action: Action,
                            private val item: ItemStack?,
                            private val blockClicked: Block?,
                            private val blockFace: BlockFace,
                            private val hand: EquipmentSlot?,
                            private val clickedPosition: Vector?) : PlayerEvent(who), Cancellable {
    private var useClickedBlock = if (blockClicked == null) Result.DENY else Result.ALLOW
    private var useItemInHand = Result.DEFAULT

    override fun getHandlers(): HandlerList {
        return Companion.handlers
    }

    override fun isCancelled(): Boolean {
        return this.useClickedBlock == Result.DENY
    }

    override fun setCancelled(cancel: Boolean) {
        this.useClickedBlock = (if (cancel) Result.DENY else if (this.useClickedBlock == Result.DENY) Result.DEFAULT else this.useClickedBlock)
        this.useItemInHand = (if (cancel) Result.DENY else if (this.useItemInHand == Result.DENY) Result.DEFAULT else this.useItemInHand)
    }

    companion object {
        private val handlers = HandlerList()
    }
}
