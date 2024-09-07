package kz.hxncus.mc.minesonapikotlin.bukkit.event

import org.bukkit.event.Event
import org.bukkit.event.Listener

/**
 * The interface Event consumer.
 *
 * @param <E> the type parameter
 * @author Hxncus
 * @since 1.0.
</E> */
@FunctionalInterface
fun interface EventConsumer<E : Event?> : Listener {
    /**
     * Append event consumer.
     *
     * @param other the other
     * @return the event consumer
     */
    fun append(other: EventConsumer<in E>): EventConsumer<E> {
        return EventConsumer { event: E ->
            this.accept(event)
            other.accept(event)
        }
    }

    /**
     * Accept.
     *
     * @param event the event
     */
    fun accept(event: E)

    /**
     * Prepend event consumer.
     *
     * @param other the other
     * @return the event consumer
     */
    fun prepend(other: EventConsumer<in E>): EventConsumer<E> {
        return EventConsumer { event: E ->
            other.accept(event)
            this.accept(event)
        }
    }
}