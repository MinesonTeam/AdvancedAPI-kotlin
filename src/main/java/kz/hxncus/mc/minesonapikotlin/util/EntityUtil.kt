package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.bukkit.server.ServerManager
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.ItemDisplay
import org.bukkit.event.player.PlayerTeleportEvent

/**
 * Is underwater boolean.
 *
 * @return the boolean
 */
fun Entity.isUnderWater(): Boolean {
    val serverManager: ServerManager = MinesonAPI.plugin.serverManager
    return if (serverManager.isPaperServer && isAfterOrEqual(1190)) {
        this.isUnderWater()
    } else {
        this.isInWater
    }
}

/**
 * Is fixed boolean.
 *
 * @return the boolean
 */
fun ItemDisplay.isFixed(): Boolean {
    return this.itemDisplayTransform == ItemDisplay.ItemDisplayTransform.FIXED
}

/**
 * Is none boolean?
 *
 * @return the boolean
 */
fun ItemDisplay.isNone(): Boolean {
    return this.itemDisplayTransform == ItemDisplay.ItemDisplayTransform.NONE
}

/**
 * Teleport.
 *
 * @param location the location
 * @param cause    the cause
 */
fun Entity.tp(location: Location, cause: PlayerTeleportEvent.TeleportCause) {
    val serverManager: ServerManager = MinesonAPI.plugin.serverManager
    if (isAfterOrEqual(1194) && serverManager.isPaperServer || serverManager.isFoliaServer) {
        //            entity.teleportAsync(location, cause);
        this.teleport(location)
    } else {
        this.teleport(location)
    }
}

/**
 * Teleport.
 *
 * @param location the location
 */
fun Entity.tp(location: Location) {
    val serverManager: ServerManager = MinesonAPI.plugin.serverManager
    if (isAfterOrEqual(1194) && (serverManager.isPaperServer || serverManager.isFoliaServer)) {
        //            entity.teleportAsync(location);
        this.teleport(location)
    } else {
        this.teleport(location)
    }
}
