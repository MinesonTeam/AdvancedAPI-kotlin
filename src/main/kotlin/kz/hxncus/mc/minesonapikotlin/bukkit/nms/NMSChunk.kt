package kz.hxncus.mc.minesonapikotlin.bukkit.nms

import org.bukkit.World

interface NMSChunk {
    fun setBlockInNativeChunk(world: World?, x: Int, y: Int, z: Int, blockId: Int, applyPhysics: Boolean)
}
