package kz.hxncus.mc.minesonapikotlin.bukkit.workload

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.util.scheduler
import kz.hxncus.mc.minesonapikotlin.util.timer
import java.util.*

open class WorkloadRunnable protected constructor() : Runnable {
    companion object {
        private const val MAX_MILLIS_PER_TICK = 10.0
        private const val MAX_NANOS_PER_TICK = (MAX_MILLIS_PER_TICK * 1.0E6).toInt()
        private var instance: WorkloadRunnable? = null
        fun get(): WorkloadRunnable? {
            // Техника, которую мы здесь применяем, называется «блокировка с двойной
            // проверкой» (Double-Checked Locking). Она применяется, чтобы
            // предотвратить создание нескольких объектов-одиночек, если метод будет
            // вызван из нескольких потоков одновременно.
            //
            // Хотя переменная `result` вполне оправданно кажется здесь лишней, она
            // помогает избежать подводных камней реализации DCL в Java.
            //
            // Больше об этой проблеме можно почитать здесь:
            // https://ru.wikipedia.org/wiki/%D0%91%D0%BB%D0%BE%D0%BA%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B0_%D1%81_%D0%B4%D0%B2%D0%BE%D0%B9%D0%BD%D0%BE%D0%B9_%D0%BF%D1%80%D0%BE%D0%B2%D0%B5%D1%80%D0%BA%D0%BE%D0%B9
            val result = instance
            if (result != null) {
                return result
            }
            synchronized(WorkloadRunnable::class.java) {
                if (instance == null) {
                    MinesonAPI.plugin.scheduler.timer(1L, 1L, WorkloadRunnable().also { instance = it })
                }
                return instance
            }
        }
    }

    private val workloads: Deque<Runnable> = ArrayDeque()

    fun add(runnable: Runnable) {
        workloads.add(runnable)
    }

    override fun run() {
        val stopTime = System.nanoTime() + MAX_NANOS_PER_TICK
        while (System.nanoTime() <= stopTime) {
            workloads.poll()?.run()
        }
    }
}
