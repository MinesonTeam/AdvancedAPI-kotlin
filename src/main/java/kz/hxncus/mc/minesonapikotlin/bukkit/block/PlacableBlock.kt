package kz.hxncus.mc.minesonapikotlin.bukkit.block

import kz.hxncus.mc.minesonapikotlin.bukkit.nms.NMSChunk
import kz.hxncus.mc.minesonapikotlin.bukkit.nms.NMSHandler
import kz.hxncus.mc.minesonapikotlin.bukkit.workload.Workload
import org.bukkit.Bukkit
import org.bukkit.Material
import java.util.*

/**
 * The type-Placable block.
 * @author Hxncus
 * @since  1.0.1
 */
class PlacableBlock : Workload {
    private val chunk: NMSChunk? = NMSHandler.getChunk()
    private val worldUuid: UUID? = null
    private val blockX = 0
    private val blockY = 0
    private val blockZ = 0
    private val material: Material? = null

    override fun compute() {
        val world = Bukkit.getWorld(worldUuid ?: return) ?: return
        chunk?.setBlockInNativeChunk(world, this.blockX, this.blockY, this.blockZ, 0, false)
    }
}
