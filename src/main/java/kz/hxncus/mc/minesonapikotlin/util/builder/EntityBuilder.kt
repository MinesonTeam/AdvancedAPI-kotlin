package kz.hxncus.mc.minesonapikotlin.util.builder

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.entity.Item
import org.bukkit.inventory.meta.FireworkMeta
import java.util.function.Consumer

class EntityBuilder(location: Location, world: World, type: EntityType) {
    private val entity: Entity = world.spawnEntity(location, type)

    fun fireworkMeta(consumer: Consumer<FireworkMeta?>): EntityBuilder {
        return this.meta(Firework::class.java) { firework: Firework ->
            val meta = firework.fireworkMeta
            consumer.accept(meta)
            firework.fireworkMeta = meta
        }
    }

    fun <T : Entity?> meta(metaClass: Class<T>, metaConsumer: Consumer<T>): EntityBuilder {
        return this.meta { meta: Entity? ->
            if (metaClass.isInstance(meta)) {
                metaConsumer.accept(metaClass.cast(meta))
            }
        }
    }

    fun meta(metaConsumer: Consumer<Entity?>): EntityBuilder {
        metaConsumer.accept(this.entity)
        return this
    }

    fun itemMeta(consumer: Consumer<Item>): EntityBuilder {
        return this.meta(Item::class.java, consumer)
    }
}
