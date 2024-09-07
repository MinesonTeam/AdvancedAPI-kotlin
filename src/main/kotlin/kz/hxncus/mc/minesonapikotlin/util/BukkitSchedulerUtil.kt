package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer
import java.util.function.IntConsumer
import java.util.stream.IntStream

/**
 * Run (to–from) time
 *
 * @param from      от скольки раз
 * @param to        до скольки раз
 * @param consumer функция
 */
fun BukkitScheduler.runNTime(from: Int, to: Int, consumer: IntConsumer) {
    IntStream.range(from, to).forEach(consumer)
}

/**
 * Запустить num раз
 *
 * @param num      сколько раз запустить
 * @param consumer функция
 */
fun BukkitScheduler.runNTime(num: Int, consumer: IntConsumer) {
    IntStream.range(0, num).forEach(consumer)
}

/**
 * Run numbs
 *
 * @param numbs    numbs an array to run
 * @param consumer function with numbing
 */
fun BukkitScheduler.runNTime(consumer: IntConsumer, vararg numbs: Int) {
    Arrays.stream(numbs).forEach(consumer)
}

/**
 * Запустить таймер n раз
 *
 * @param i        сколько раз запустить
 * @param period   через сколько повторять
 * @param time     тип времени
 * @param consumer функция
 */
fun BukkitScheduler.timerNTime(i: Int, period: Long, time: TimeUnit, consumer: IntConsumer) {
    timerNTime(i, 0, time.toTicks(period), consumer)
}

/**
 * Запустить таймер n раз в тиках
 *
 * @param i        сколько раз запустить
 * @param period   через сколько повторять
 * @param consumer функция
 */
fun BukkitScheduler.timerNTime(i: Int, period: Long, consumer: IntConsumer) {
    timerNTime(i, 0L, period, consumer)
}

/**
 * Запустить таймер n раз
 *
 * @param i        сколько раз запустить
 * @param delay    через сколько выполнить
 * @param period   через сколько повторять
 * @param time     тип времени
 * @param consumer функция
 */
fun BukkitScheduler.timerNTime(i: Int, delay: Long, period: Long, time: TimeUnit, consumer: IntConsumer) {
    timerNTime(i, time.toTicks(delay), time.toTicks(period), consumer)
}

/**
 * Запустить таймер n раз в тиках
 *
 * @param i        сколько раз запустить
 * @param delay    через сколько выполнить
 * @param period   через сколько повторять
 * @param consumer функция
 */
fun BukkitScheduler.timerNTime(i: Int, delay: Long, period: Long, consumer: IntConsumer) {
    val integer = AtomicInteger()
    this.runTaskTimer(MinesonAPI.plugin, { task: BukkitTask ->
        consumer.accept(integer.getAndIncrement())
        if (integer.get() == i) {
            task.cancel()
        }
    }, delay, period)
}

/**
 * Повторять таймер каждые i раз в тиках
 *
 * @param i        сколько раз запустить
 * @param period   через сколько повторять
 * @param consumer функция
 * @return the bukkit task
 */
fun BukkitScheduler.timerRepeatNTime(i: Int, period: Long, consumer: IntConsumer): BukkitTask {
    return timerRepeatNTime(i, 0L, period, consumer)
}

/**
 * Повторять таймер каждые i раз
 *
 * @param i        сколько раз запустить
 * @param period   через сколько повторять
 * @param time     тип времени
 * @param consumer функция
 * @return BukkitTask bukkit task
 */
fun BukkitScheduler.timerRepeatNTime(i: Int, period: Long, time: TimeUnit, consumer: IntConsumer): BukkitTask {
    return timerRepeatNTime(i, 0L, time.toTicks(period), consumer)
}

/**
 * Повторять таймер каждые n раз
 *
 * @param i        сколько раз запустить
 * @param delay    через сколько выполнить
 * @param period   через сколько повторять
 * @param time     тип времени
 * @param consumer функция
 * @return BukkitTask bukkit task
 */
fun BukkitScheduler.timerRepeatNTime(i: Int, delay: Long, period: Long, time: TimeUnit, consumer: IntConsumer): BukkitTask {
    return timerRepeatNTime(i, time.toTicks(delay), time.toTicks(period), consumer)
}

/**
 * Повторять таймер каждые n раз в тиках
 *
 * @param i        сколько раз запустить
 * @param delay    через сколько выполнить
 * @param period   через сколько повторять
 * @param consumer функция
 * @return BukkitTask bukkit task
 */
fun BukkitScheduler.timerRepeatNTime(i: Int, delay: Long, period: Long, consumer: IntConsumer): BukkitTask {
    val integer = AtomicInteger()
    val task = this.runTaskTimer(MinesonAPI.plugin, Runnable {
        consumer.accept(integer.getAndIncrement())
        if (integer.get() == i) {
            integer.set(0)
        }
    }, delay, period)
    return task
}

/**
 * Запустить таймер в тиках
 *
 * @param period   через сколько повторять
 * @param time     тип времени
 * @param runnable таск
 * @return bukkit task
 */
fun BukkitScheduler.timer(period: Long, time: TimeUnit, runnable: Runnable): BukkitTask {
    return timer(0L, time.toTicks(period), runnable)
}

/**
 * Запустить таймер
 *
 * @param delay    через сколько выполнить
 * @param period   через сколько повторять
 * @param time     тип времени
 * @param runnable таск
 * @return bukkit task
 */
fun BukkitScheduler.timer(delay: Long, period: Long, time: TimeUnit, runnable: Runnable): BukkitTask {
    return timer(time.toTicks(delay), time.toTicks(period), runnable)
}

/**
 * Запустить таймер
 *
 * @param delay    через сколько выполнить в тиках
 * @param period   через сколько повторять в тиках
 * @param runnable таск
 * @return bukkit task
 */
fun BukkitScheduler.timer(delay: Long, period: Long, runnable: Runnable): BukkitTask {
    return runTaskTimer(MinesonAPI.plugin, runnable, delay, period)
}

/**
 * Запустить таймер в тиках
 *
 * @param period через сколько повторять
 * @param time   тип времени
 * @param task   таск
 */
fun BukkitScheduler.timer(period: Long, time: TimeUnit, task: Consumer<in BukkitTask>) {
    timer(0, time.toTicks(period), task)
}

/**
 * Запустить таймер в тиках
 *
 * @param delay  через сколько выполнять
 * @param period через сколько повторять
 * @param time   тип времени
 * @param task   таск
 */
fun BukkitScheduler.timer(delay: Long, period: Long, time: TimeUnit, task: Consumer<in BukkitTask>) {
    timer(time.toTicks(delay), time.toTicks(period), task)
}

/**
 * Запустить таймер в тиках
 *
 * @param delay  через сколько выполнять
 * @param period через сколько повторять
 * @param task   таск
 */
fun BukkitScheduler.timer(delay: Long, period: Long, task: Consumer<in BukkitTask>) {
    runTaskTimer(MinesonAPI.plugin, task, delay, period)
}

/**
 * Запустить таймер
 *
 * @param period   через сколько повторять
 * @param time     тип времени
 * @param runnable таск
 * @return bukkit task
 */
fun BukkitScheduler.timerAsync(period: Long, time: TimeUnit, runnable: Runnable): BukkitTask {
    return timerAsync(0L, time.toTicks(period), runnable)
}

/**
 * Запустить таймер
 *
 * @param delay    через сколько выполнить
 * @param period   через сколько повторять
 * @param time     тип времени
 * @param runnable таск
 * @return bukkit task
 */
fun BukkitScheduler.timerAsync(delay: Long, period: Long, time: TimeUnit, runnable: Runnable): BukkitTask {
    return timerAsync(time.toTicks(delay), time.toTicks(period), runnable)
}

/**
 * Запустить таймер
 *
 * @param delay    через сколько выполнить
 * @param period   через сколько повторять
 * @param runnable таск
 * @return bukkit task
 */
fun BukkitScheduler.timerAsync(delay: Long, period: Long, runnable: Runnable): BukkitTask {
    return runTaskTimerAsynchronously(MinesonAPI.plugin, runnable, delay, period)
}

/**
 * Запустить таймер
 *
 * @param period через сколько повторять
 * @param time   тип времени
 * @param task   таск
 */
fun BukkitScheduler.timerAsync(period: Long, time: TimeUnit, task: Consumer<BukkitTask>) {
    timerAsync(0L, time.toTicks(period), task)
}

/**
 * Запустить таймер
 *
 * @param delay  через сколько выполнить
 * @param period через сколько повторять
 * @param time   тип времени
 * @param task   таск
 */
fun BukkitScheduler.timerAsync(delay: Long, period: Long, time: TimeUnit, task: Consumer<BukkitTask>) {
    timerAsync(time.toTicks(delay), time.toTicks(period), task)
}

/**
 * Запустить таймер
 *
 * @param delay  через сколько выполнить
 * @param period через сколько повторять
 * @param task   таск
 */
fun BukkitScheduler.timerAsync(delay: Long, period: Long, task: Consumer<BukkitTask>) {
    runTaskTimerAsynchronously(MinesonAPI.plugin, task, delay, period)
}

/**
 * Выполнить позже
 *
 * @param delay    через сколько выполнить
 * @param time     тип времени
 * @param runnable таск
 * @return bukkit task
 */
fun BukkitScheduler.later(delay: Long, time: TimeUnit, runnable: Runnable): BukkitTask {
    return later(time.toTicks(delay), runnable)
}

/**
 * Выполнить позже
 *
 * @param delay    через сколько выполнить
 * @param runnable таск
 * @return bukkit task
 */
fun BukkitScheduler.later(delay: Long, runnable: Runnable): BukkitTask {
    return runTaskLater(MinesonAPI.plugin, runnable, delay)
}

/**
 * Выполнить позже
 *
 * @param delay    через сколько выполнить
 * @param time     тип времени
 * @param runnable таск
 * @return bukkit task
 */
fun BukkitScheduler.laterAsync(delay: Long, time: TimeUnit, runnable: Runnable): BukkitTask {
    return laterAsync(time.toTicks(delay), runnable)
}

/**
 * Выполнить позже
 *
 * @param delay    через сколько выполнить
 * @param runnable таск
 * @return bukkit task
 */
fun BukkitScheduler.laterAsync(delay: Long, runnable: Runnable): BukkitTask {
    return runTaskLaterAsynchronously(MinesonAPI.plugin, runnable, delay)
}

/**
 * Cancel all plugin tasks.
 *
 * @param plugin the plugin
 */
fun BukkitScheduler.cancelPluginTasks(plugin: Plugin) {
    for (activeWorker in this.activeWorkers) {
        if (activeWorker.owner == plugin) {
            cancelTask(activeWorker.taskId)
        }
    }
}

/**
 * Cancel all tasks.
 */
fun BukkitScheduler.cancelAllTasks() {
    for (activeWorker in this.activeWorkers) {
        cancelTask(activeWorker.taskId)
    }
}
