package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import org.bukkit.GameMode
import org.bukkit.World

var World.whitelist: Boolean get() = false
                             set(value) {}
var World.perWorldInventory: Boolean get() = false
                                     set(value) {}
var World.perWorldEnderChest: Boolean get() = false
                                      set(value) {}

/**
 * Game mode simple world.
 *
 * @param gameMode the game mode
 * @return the simple world
 */
fun World.setPlayerGameMode(gameMode: GameMode) {
    for (player in this.players) {
        player.gameMode = gameMode
    }
}

fun World.unloadPlayers() {
    this.players.forEach { player ->
        val bedLocation = player.bedLocation
        if (bedLocation.world == this) {
            player.kick()
        } else {
            player.smartTeleport(bedLocation)
        }
    }
}

/**
 * Delete world boolean.
 *
 * @return the boolean
 */
fun World.delete(): Boolean {
    if (unload(false)) {
        worldFolder.deleteFolder()
    }
    return false
}

/**
 * Delete world boolean.
 *
 * @return the boolean
 */
fun World.unload(save: Boolean): Boolean {
    unloadPlayers()
    return MinesonAPI.plugin.server.unloadWorld(this.name, save)
}
