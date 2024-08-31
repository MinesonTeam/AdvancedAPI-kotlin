package kz.hxncus.mc.minesonapikotlin.bukkit.bossbar

import org.bukkit.scheduler.BukkitScheduler

/**
 * The interface Animatable.
 * @author Hxncus
 * @since  1.0.1
 */
interface Animatable {
    /**
     * Stop animation.
     */
    fun stopAnimation()

    /**
     * Start animation.
     *
     * @param scheduler the scheduler
     */
    fun startAnimation(scheduler: BukkitScheduler)
}
