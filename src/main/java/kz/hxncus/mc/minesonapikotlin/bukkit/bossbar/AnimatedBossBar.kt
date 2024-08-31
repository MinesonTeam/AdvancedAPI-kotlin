package kz.hxncus.mc.minesonapikotlin.bukkit.bossbar

import kz.hxncus.mc.minesonapikotlin.util.timerAsync
import org.bukkit.scheduler.BukkitScheduler

/**
 * The type Animated boss bar.
 * @author Hxncus
 * @since  1.0.1
 */
class AnimatedBossBar (private val animationType: AnimationType, private val delay: Long, private val period: Long) : SimpleBossBar(), Animatable {
    override fun stopAnimation() {
    }

    override fun startAnimation(scheduler: BukkitScheduler) {
        scheduler.timerAsync(this.delay, this.period) { _ ->
            when (this.animationType) {
                AnimationType.STATIC -> this.startStaticAnimation()
                AnimationType.PROGRESSIVE -> this.startProgressiveAnimation()
            }
        }
    }

    private fun startStaticAnimation() {
    }

    private fun startProgressiveAnimation() {
    }
}
