package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import org.bukkit.Location
import org.bukkit.Server
import org.bukkit.entity.Entity
import org.bukkit.entity.ItemDisplay
import org.bukkit.event.player.PlayerTeleportEvent
import java.util.function.Consumer

fun <T : Entity> Entity.meta(metaClass: Class<T>, metaConsumer: Consumer<T>) {
    this.meta { entity: Entity ->
        if (metaClass.isInstance(entity)) {
            metaConsumer.accept(metaClass.cast(entity))
        }
    }
}

fun Entity.meta(metaConsumer: (Entity) -> Unit) {
    metaConsumer.invoke(this)
}

/**
 * Is underwater boolean.
 *
 * @return the boolean
 */
fun Entity.isInUnderWater(): Boolean {
    val serverManager: Server = MinesonAPI.plugin.server
    return if (serverManager.isPaperServer && isAfterOrEqual(1190)) {
        this.isUnderWater
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
fun Entity.smartTeleport(location: Location, cause: PlayerTeleportEvent.TeleportCause): Boolean {
    val serverManager: Server = MinesonAPI.plugin.server
    return if (isAfterOrEqual(1194) && serverManager.isPaperServer || serverManager.isFoliaServer) {
        this.teleportAsync(location, cause).get()
    } else {
        this.teleport(location, cause)
    }
}

/**
 * Teleport.
 *
 * @param location the location
 */
fun Entity.smartTeleport(location: Location): Boolean {
    val serverManager: Server = MinesonAPI.plugin.server
    return if (isAfterOrEqual(1194) && (serverManager.isPaperServer || serverManager.isFoliaServer)) {
        this.teleportAsync(location).get()
    } else {
        this.teleport(location)
    }
}
