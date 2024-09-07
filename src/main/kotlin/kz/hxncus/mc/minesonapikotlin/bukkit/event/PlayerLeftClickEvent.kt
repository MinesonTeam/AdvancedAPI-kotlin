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

class PlayerLeftClickEvent(who: Player,
                                 private var action: Action,
                                 private var item: ItemStack?,
                                 private var blockClicked: Block?,
                                 private var blockFace: BlockFace,
                                 private val hand: EquipmentSlot?,
                                 private val clickedPosition: Vector?) : PlayerEvent(who), Cancellable {
    companion object {
        val handlerList: HandlerList = HandlerList()
    }

    private var useClickedBlock = if (blockClicked == null) Result.DENY else Result.ALLOW
    private var useItemInHand = Result.DEFAULT

    override fun isCancelled(): Boolean {
        return this.useClickedBlock == Result.DENY
    }

    override fun setCancelled(cancel: Boolean) {
        this.useClickedBlock = (if (cancel) Result.DENY else if (this.useClickedBlock == Result.DENY) Result.DEFAULT else this.useClickedBlock)
        this.useItemInHand = (if (cancel) Result.DENY else if (this.useItemInHand == Result.DENY) Result.DEFAULT else this.useItemInHand)
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }
}
