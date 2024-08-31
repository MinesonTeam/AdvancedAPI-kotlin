package kz.hxncus.mc.minesonapikotlin.bukkit.world

import org.bukkit.*
import org.bukkit.block.Biome
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

/**
 * Class Simple world.
 *
 * @author GeliusIHe
 * @since 1.0.1
 */
class SimpleWorld {
    val world: World
    val worldBorder: WorldBorder
    val worldName: String get() = world.name
    val worldPlayers: List<Player> get() = world.players
    val gameMode: GameMode? get() = if (world.players.isEmpty()) null else worldPlayers.first().gameMode
    val difficulty: Difficulty get() = world.difficulty
    val time: Long get() = world.time
    val seaLevel: Int get() = world.seaLevel
    val maxHeight: Int get() = world.maxHeight
    val isAnimalsAllowed: Boolean get() = world.allowAnimals
    val isMonstersAllowed: Boolean get() = world.allowMonsters
    val worldEntities: List<Entity> get() = world.entities
    val worldLivingEntities: List<LivingEntity> get() = world.livingEntities
    val spawnLocation: Location get() = world.spawnLocation
    val autoSave: Boolean get() = world.isAutoSave
    val pvp: Boolean get() = world.pvp
    val keepSpawnInMemory: Boolean get() = world.keepSpawnInMemory

    /**
     * Instantiates a new Simple world.
     *
     * @param worldCreator the world creator
     */
    constructor(worldCreator: WorldCreator) {
        val name = worldCreator.name()
        if (Bukkit.getWorlds()
                .stream()
                .map { obj: World -> obj.name }
                .anyMatch { worldName: String -> worldName == name }
        ) {
            throw RuntimeException("World $name already exists")
        }
        this.world = worldCreator.createWorld() ?: throw RuntimeException("World creation failed")
        this.worldBorder = world.worldBorder
    }

    /**
     * Instantiates a new Simple world.
     *
     * @param world the world
     */
    constructor(world: World) {
        this.world = world
        this.worldBorder = world.worldBorder
    }

    /**
     * Game mode simple world.
     *
     * @param gameMode the game mode
     * @return the simple world
     */
    fun setWorldPlayersGameMode(gameMode: GameMode): SimpleWorld {
        for (player in this.worldPlayers) {
            player.gameMode = gameMode
        }
        return this
    }

    /**
     * Sets storm.
     *
     * @param storm the storm
     * @return the storm
     */
    fun setStorm(storm: Boolean): SimpleWorld {
        world.setStorm(storm)
        return this
    }

    /**
     * World border center simple world.
     *
     * @param x the x
     * @param z the z
     * @return the simple world
     */
    fun setWorldBorderCenter(x: Double, z: Double): SimpleWorld {
        worldBorder.setCenter(x, z)
        return this
    }

    /**
     * World border size simple world.
     *
     * @param size the size
     * @return the simple world
     */
    fun setWorldBorderSize(size: Double): SimpleWorld {
        worldBorder.size = size
        return this
    }

    /**
     * Death message visibility simple world.
     *
     * @param visible the visible
     * @return the simple world
     */
    fun setDeathMessageVisibility(visible: Boolean): SimpleWorld {
        for (player in this.worldPlayers) {
            player.setPlayerListName(if (visible) player.name else "")
        }
        return this
    }

    /**
     * Sets difficulty.
     *
     * @param difficulty the difficulty
     * @return the difficulty
     */
    fun setDifficulty(difficulty: Difficulty?): SimpleWorld {
        world.difficulty = difficulty!!
        return this
    }

    /**
     * Is stormy boolean?
     *
     * @return the boolean
     */
    fun hasStorm(): Boolean {
        return world.hasStorm()
    }

    /**
     * Sets time.
     *
     * @param time the time
     * @return the time
     */
    fun setTime(time: Long): SimpleWorld {
        world.time = time
        return this
    }

    /**
     * Sets clear weather duration.
     *
     * @param duration the duration
     * @return the clear weather duration
     */
    fun setClearWeatherDuration(duration: Int): SimpleWorld {
        world.clearWeatherDuration = duration
        return this
    }

    /**
     * Sets thunder duration.
     *
     * @param duration the duration
     * @return the thunder duration
     */
    fun setThunderDuration(duration: Int): SimpleWorld {
        world.thunderDuration = duration
        return this
    }

    /**
     * Sets weather duration.
     *
     * @param duration the duration
     * @return the weather duration
     */
    fun setWeatherDuration(duration: Int): SimpleWorld {
        world.weatherDuration = duration
        return this
    }

    /**
     * Sets thundering.
     *
     * @param thundering the thundering
     * @return the thundering
     */
    fun setThundering(thundering: Boolean): SimpleWorld {
        world.isThundering = thundering
        return this
    }

    /**
     * Sets weather clear.
     *
     * @return the weather clear
     */
    fun setWeatherClear(): SimpleWorld {
        world.setStorm(false)
        world.isThundering = false
        return this
    }

    /**
     * Save a simple world.
     *
     * @return the simple world
     */
    fun save(): SimpleWorld {
        world.save()
        return this
    }

    /**
     * Reset world border simple world.
     *
     * @return the simple world
     */
    fun resetWorldBorder(): SimpleWorld {
        worldBorder.reset()
        return this
    }

    /**
     * Sets world border damage amount.
     *
     * @param damageAmount the damage amount
     * @return the world border damage amount
     */
    fun setWorldBorderDamageAmount(damageAmount: Double): SimpleWorld {
        worldBorder.damageAmount = damageAmount
        return this
    }

    /**
     * Sets world border damage buffer.
     *
     * @param damageBuffer the damage buffer
     * @return the world border damage buffer
     */
    fun setWorldBorderDamageBuffer(damageBuffer: Double): SimpleWorld {
        worldBorder.damageBuffer = damageBuffer
        return this
    }

    /**
     * Sets world border warning distance.
     *
     * @param warningDistance the warning distance
     * @return the world border warning distance
     */
    fun setWorldBorderWarningDistance(warningDistance: Int): SimpleWorld {
        worldBorder.warningDistance = warningDistance
        return this
    }

    /**
     * Sets world border warning time.
     *
     * @param warningTime the warning time
     * @return the world border warning time
     */
    fun setWorldBorderWarningTime(warningTime: Int): SimpleWorld {
        worldBorder.warningTime = warningTime
        return this
    }

    /**
     * Gets game rule value.
     *
     * @param rule the rule
     * @return the game rule value
     */
    fun getGameRule(rule: GameRule<*>): String {
        return world.getGameRuleValue(rule) as String
    }

    /**
     * Sets game rule value.
     *
     * @param <T>   the type parameter
     * @param rule  the rule
     * @param value the value
     * @return the game rule value
    </T> */
    fun <T : Any> setGameRule(rule: GameRule<T>, value: T): SimpleWorld {
        world.setGameRule(rule, value)
        return this
    }

    /**
     * Play affects a simple world.
     *
     * @param location the location
     * @param effect   the effect
     * @param data     the data
     * @return the simple world
     */
    fun playEffect(location: Location?, effect: Effect?, data: Int): SimpleWorld {
        world.playEffect(location!!, effect!!, data)
        return this
    }

    /**
     * Play soundly simple world.
     *
     * @param location the location
     * @param sound    the sound
     * @param volume   the volume
     * @param pitch    the pitch
     * @return the simple world
     */
    fun playSound(location: Location?, sound: Sound?, volume: Float, pitch: Float): SimpleWorld {
        world.playSound(location!!, sound!!, volume, pitch)
        return this
    }

    /**
     * Spawn entity.
     *
     * @param location the location
     * @param type     the type
     * @return the entity
     */
    fun spawnEntity(location: Location?, type: EntityType?): Entity {
        return world.spawnEntity(location!!, type!!)
    }

    /**
     * Gets nearby entities.
     *
     * @param location the location
     * @param x        the x
     * @param y        the y
     * @param z        the z
     * @return the nearby entities
     */
    fun getNearbyEntities(location: Location?, x: Double, y: Double, z: Double): Collection<Entity> {
        return world.getNearbyEntities(location!!, x, y, z)
    }

    /**
     * Strike lightning simple world.
     *
     * @param location the location
     * @return the simple world
     */
    fun strikeLightning(location: Location?): SimpleWorld {
        world.strikeLightning(location!!)
        return this
    }

    /**
     * Strike lightning effect simple world.
     *
     * @param location the location
     * @return the simple world
     */
    fun strikeLightningEffect(location: Location?): SimpleWorld {
        world.strikeLightningEffect(location!!)
        return this
    }

    /**
     * Gets highest block at.
     *
     * @param x the x
     * @param z the z
     * @return the highest block at
     */
    fun getHighestBlockAt(x: Int, z: Int): Block {
        return world.getHighestBlockAt(x, z)
    }

    /**
     * Gets highest block y at.
     *
     * @param x the x
     * @param z the z
     * @return the highest block y at
     */
    fun getHighestBlockYAt(x: Int, z: Int): Int {
        return world.getHighestBlockYAt(x, z)
    }

    /**
     * Sets spawn location.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the spawn location
     */
    fun setSpawnLocation(x: Int, y: Int, z: Int): SimpleWorld {
        return this.setSpawnLocation(x, y, z, 0.0f)
    }

    /**
     * Sets spawn location.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @param angle the angle
     * @return the spawn location
     */
    fun setSpawnLocation(x: Int, y: Int, z: Int, angle: Float): SimpleWorld {
        world.setSpawnLocation(x, y, z, angle)
        return this
    }

    /**
     * Sets spawn location.
     *
     * @param location the location
     * @return the spawn location
     */
    fun setSpawnLocation(location: Location?): SimpleWorld {
        world.setSpawnLocation(location!!)
        return this
    }

    /**
     * Sets auto save.
     *
     * @param autoSave the auto save
     * @return the auto save
     */
    fun setAutoSave(autoSave: Boolean): SimpleWorld {
        world.isAutoSave = autoSave
        return this
    }

    /**
     * Sets biome.
     *
     * @param x     the x
     * @param y     the y
     * @param z     the z
     * @param biome the biome
     * @return the biome
     */
    fun setBiome(x: Int, y: Int, z: Int, biome: Biome?): SimpleWorld {
        world.setBiome(x, y, z, biome!!)
        return this
    }

    /**
     * Gets biome.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the biome
     */
    fun getBiome(x: Int, y: Int, z: Int): Biome {
        return world.getBiome(x, y, z)
    }

    /**
     * Sets biome.
     *
     * @param location the location
     * @param biome the biome
     * @return the biome
     */
    fun setBiome(location: Location?, biome: Biome?): SimpleWorld {
        world.setBiome(location!!, biome!!)
        return this
    }

    /**
     * Gets biome.
     *
     * @param location the location
     * @return the biome
     */
    fun getBiome(location: Location?): Biome {
        return world.getBiome(location!!)
    }

    /**
     * Sets pvp.
     *
     * @param pvp the pvp
     * @return the pvp
     */
    fun setPVP(pvp: Boolean): SimpleWorld {
        world.pvp = pvp
        return this
    }

    /**
     * Sets keep spawn in memory.
     *
     * @param keepSpawn the keep spawn
     * @return the keep spawn in memory
     */
    fun setKeepSpawnInMemory(keepSpawn: Boolean): SimpleWorld {
        world.keepSpawnInMemory = keepSpawn
        return this
    }
}