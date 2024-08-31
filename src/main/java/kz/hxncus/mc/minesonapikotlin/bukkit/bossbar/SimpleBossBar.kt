package kz.hxncus.mc.minesonapikotlin.bukkit.bossbar

import org.bukkit.boss.BossBar
import org.bukkit.entity.Player

/**
 * The type Simple boss bar.
 * @author Hxncus
 * @since  1.0.1
 */
open class SimpleBossBar {
    private val bossBarList: MutableList<BossBar> = mutableListOf()
    private val playerList: MutableList<Player> = mutableListOf()

    /**
     * Add player.
     *
     * @param player the player
     */
    fun addPlayer(player: Player?) {
    }

    /**
     * Remove player.
     *
     * @param player the player
     */
    fun removePlayer(player: Player?) {
    }
}
