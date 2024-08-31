package kz.hxncus.mc.minesonapikotlin.config

import kz.hxncus.mc.minesonapikotlin.util.EMPTY_UUID
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File
import java.io.IOException
import java.net.URI
import java.util.*

/**
 * The type Simple config.
 * @author Hxncus
 * @since  1.0.1
 */
class SimpleConfig : YamlConfiguration {
    private val file: File

    /**
     * Instantiates a new Simple config.
     *
     * @param parent the parent
     * @param child  the child
     */
    constructor(parent: File, child: String) {
        this.file = File(parent, child)
        this.reloadConfig()
    }

    /**
     * Reload config.
     */
    fun reloadConfig() {
        this.createFile()
        try {
            this.load(this.file)
        } catch (e: InvalidConfigurationException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Create a file.
     */
    fun createFile() {
        try {
            file.parentFile.mkdirs()
            file.createNewFile()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Instantiates a new Simple config.
     *
     * @param parent the parent
     * @param child  the child
     */
    constructor(parent: String, child: String) {
        this.file = File(parent, child)
        this.reloadConfig()
    }

    /**
     * Instantiates a new Simple config.
     *
     * @param path the path
     */
    constructor(path: String) {
        this.file = File(path)
        this.reloadConfig()
    }

    /**
     * Instantiates a new Simple config.
     *
     * @param uri the uri
     */
    constructor(uri: URI) {
        this.file = File(uri)
        this.reloadConfig()
    }

    /**
     * Sets and save.
     *
     * @param path  the path
     * @param value the value
     */
    fun setAndSave(path: String, value: Any) {
        this[path] = value
        this.save()
    }

    /**
     * Save.
     */
    fun save() {
        try {
            this.save(this.file)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Is uuid boolean.
     *
     * @param path the path
     * @return the boolean
     */
    fun isUuid(path: String): Boolean {
        return this.getUuid(path) !== EMPTY_UUID
    }

    /**
     * Gets uuid.
     *
     * @param path the path
     * @return the uuid
     */
    fun getUuid(path: String): UUID {
        return try {
            UUID.fromString(
                this[path, ""].toString()
            )
        } catch (e: IllegalArgumentException) {
            EMPTY_UUID
        }
    }

    /**
     * Gets material.
     *
     * @param path the path
     * @param def  the def
     * @return the material
     */
    fun getMaterial(path: String, def: Material): Material {
        val material = this.getMaterial(path)
        return material ?: def
    }

    /**
     * Gets material.
     *
     * @param path the path
     * @return the material
     */
    fun getMaterial(path: String): Material? {
        return Material.getMaterial(this.getString(path, "") ?: return null)
    }

    /**
     * Is material boolean.
     *
     * @param path the path
     * @return the boolean
     */
    fun isMaterial(path: String): Boolean {
        return this.getMaterial(path) != null
    }

    /**
     * Gets a material list.
     *
     * @param path the path
     * @return the material list
     */
    fun getMaterialList(path: String): MutableList<Material?> {
        val list: MutableList<Material?> = LinkedList()
        val finalPath = "$path."
        for (key in this.getKeys(path, false)) {
            list.add(this.getMaterial(finalPath + key))
        }
        return list
    }

    /**
     * Gets keys.
     *
     * @param path the path
     * @param deep the deep
     * @return the keys
     */
    fun getKeys(path: String, deep: Boolean): Set<String> {
        val section = this.getConfigurationSection(path)
            ?: return emptySet()
        return section.getKeys(deep)
    }

    /**
     * Gets entity.
     *
     * @param path the path
     * @param def  the def
     * @return the entity
     */
    fun getEntity(path: String, def: Entity): Entity {
        val entity = Bukkit.getEntity(this.getUuid(path))
        return entity ?: def
    }

    /**
     * Is entity boolean?
     *
     * @param path the path
     * @return the boolean
     */
    fun isEntity(path: String): Boolean {
        return this.getEntity(path) != null
    }

    /**
     * Gets entity.
     *
     * @param path the path
     * @return the entity
     */
    fun getEntity(path: String): Entity? {
        return Bukkit.getEntity(this.getUuid(path))
    }

    /**
     * Gets an entity list.
     *
     * @param path the path
     * @return the entity list
     */
    fun getEntityList(path: String): List<Entity?> {
        val list: MutableList<Entity?> = LinkedList()
        for (key in this.getKeys(path, false)) {
            list.add(this.getEntity("$path.$key"))
        }
        return list
    }

    /**
     * Gets an offline player list.
     *
     * @param path the path
     * @return the offline player list
     */
    fun getOfflinePlayerList(path: String): List<OfflinePlayer> {
        val list: MutableList<OfflinePlayer> = LinkedList()
        for (key in this.getKeys(path, false)) {
            list.add(this.getOfflinePlayer("$path.$key"))
        }
        return list
    }

    override fun getOfflinePlayer(path: String): OfflinePlayer {
        val uuid = this.getUuid(path)
        return if (uuid === EMPTY_UUID) {
            Bukkit.getOfflinePlayer(this.getString(path, "")!!)
        } else {
            Bukkit.getOfflinePlayer(uuid)
        }
    }

    /**
     * Gets online player.
     *
     * @param path the path
     * @param def  the def
     * @return the online player
     */
    fun getOnlinePlayer(path: String, def: Player): Player {
        val player = this.getOnlinePlayer(path)
        return player ?: def
    }

    /**
     * Gets online player.
     *
     * @param path the path
     * @return the online player
     */
    fun getOnlinePlayer(path: String): Player? {
        val uuid = this.getUuid(path)
        return if (uuid === EMPTY_UUID) {
            Bukkit.getPlayer(this.getString(path, "") ?: return null)
        } else {
            Bukkit.getPlayer(uuid)
        }
    }

    /**
     * Is online player boolean?
     *
     * @param path the path
     * @return the boolean
     */
    fun isOnlinePlayer(path: String): Boolean {
        return this.getOnlinePlayer(path) != null
    }

    /**
     * Gets an online player list.
     *
     * @param path the path
     * @return the online player list
     */
    fun getOnlinePlayerList(path: String): List<Player?> {
        val list: MutableList<Player?> = LinkedList()
        for (key in this.getKeys(path, false)) {
            list.add(this.getOnlinePlayer("$path.$key"))
        }
        return list
    }

    /**
     * Gets item stack list.
     *
     * @param path the path
     * @return the item stack list
     */
    fun getItemStackList(path: String): List<ItemStack> {
        val list: MutableList<ItemStack> = LinkedList()
        for (key in this.getKeys(path, false)) {
            list.add(this.getItemStack("$path.$key")!!)
        }
        return list
    }
}
