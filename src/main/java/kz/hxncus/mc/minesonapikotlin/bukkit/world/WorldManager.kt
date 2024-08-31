package kz.hxncus.mc.minesonapikotlin.bukkit.world

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.bukkit.event.EventManager
import kz.hxncus.mc.minesonapikotlin.util.deleteFolder
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.event.world.WorldInitEvent
import org.bukkit.event.world.WorldUnloadEvent
import java.io.File
import java.io.IOException
import java.util.*

/**
 * Class World manager.
 *
 * @author GeliusIHe
 * @since 1.0.1
 */
class WorldManager(plugin: MinesonAPI) {
    private val simpleWorlds: MutableList<SimpleWorld> = ArrayList(16)

    init {
        this.registerEvents(plugin.eventManager)
        for (world in Bukkit.getWorlds()) {
            simpleWorlds.add(SimpleWorld(world))
        }
    }

    private fun registerEvents(eventManager: EventManager) {
        eventManager.register(WorldInitEvent::class.java) { event ->
            val world: World = event.world
            if (simpleWorlds.stream().noneMatch { sw: SimpleWorld -> val swWorld: World = sw.world
                    swWorld == world }) {
                simpleWorlds.add(SimpleWorld(world))
            }
        }
        eventManager.register(WorldInitEvent::class.java) { event ->
            val world: World = event.world
            if (simpleWorlds.stream().noneMatch { sw: SimpleWorld -> sw.world == world }) {
                simpleWorlds.add(SimpleWorld(world))
            }
        }
        eventManager.register(WorldUnloadEvent::class.java) { event ->
            val world: World = event.world
            simpleWorlds.removeIf { sw: SimpleWorld -> sw.world == world }
        }
    }

    /**
     * Create world simple world.
     *
     * @param worldCreator the world creator
     * @return the simple world
     */
    fun createWorld(worldCreator: WorldCreator): SimpleWorld {
        val simpleWorld = SimpleWorld(worldCreator)
        simpleWorlds.add(simpleWorld)
        return simpleWorld
    }

    /**
     * Load world simple world.
     *
     * @param name the name
     * @return the simple world
     */
    fun loadWorld(name: String): SimpleWorld? {
        val world = Bukkit.getWorld(name)
        if (world != null) {
            val simpleWorld = SimpleWorld(world)
            simpleWorlds.add(simpleWorld)
            return simpleWorld
        }
        return null
    }

    /**
     * Delete world boolean.
     *
     * @param name the name
     * @return the boolean
     */
    fun deleteWorld(name: String): Boolean {
        if (this.unloadWorld(name)) {
            val worldFolder = File(Bukkit.getWorldContainer(), name)
            try {
                deleteFolder(worldFolder)
                return true
            } catch (e: IOException) {
                throw RuntimeException("Failed to delete folder: " + worldFolder.absolutePath)
            }
        }
        return false
    }

    /**
     * Unload world boolean.
     *
     * @param name the name
     * @return the boolean
     */
    fun unloadWorld(name: String?): Boolean {
        val world = Bukkit.getWorld(name!!)
        if (world != null && Bukkit.unloadWorld(world, true)) {
            simpleWorlds.removeIf { sw: SimpleWorld ->
                sw.world == world
            }
            return true
        }
        return false
    }

    val worlds: List<World>
        /**
         * Gets worlds.
         *
         * @return the worlds
         */
        get() = Bukkit.getWorlds()

    /**
     * Gets simple worlds.
     *
     * @return the simple worlds
     */
    fun getSimpleWorlds(): List<SimpleWorld> {
        return Collections.unmodifiableList(this.simpleWorlds)
    }

    /**
     * Apply settings to all worlds.
     *
     * @param settings the settings
     */
    fun applySettingsToAllWorlds(settings: WorldSettings) {
        for (simpleWorld in this.simpleWorlds) {
            settings.apply(simpleWorld)
        }
    }
}
