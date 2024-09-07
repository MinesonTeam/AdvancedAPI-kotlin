package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File
import java.io.IOException
import java.net.URI
import java.util.*

var ConfigurationSection.file: File? get() = null
                                     set(value) {
                                         value?.let {
                                             if (!it.exists()) {
                                                 it.createNewFile()
                                             }
                                         }
                                     }

fun YamlConfiguration(file: File): YamlConfiguration {
    val yamlConfiguration = YamlConfiguration()
    yamlConfiguration.file = file
    yamlConfiguration.load()
    return yamlConfiguration
}


fun ConfigurationSection.file(uri: URI) {
    this.file = File(uri)
}

fun ConfigurationSection.file(file: File, parent: String) {
    this.file = File(file, parent)
}

fun ConfigurationSection.file(child: String, parent: String) {
    this.file = File(child, parent)
}

fun ConfigurationSection.file(pathName: String) {
    this.file = File(pathName)
}

fun ConfigurationSection.file(file: File) {
    this.file = file
}

/**
 * Sets and save.
 *
 * @param path  the path
 * @param value the value
 */
fun FileConfiguration.setAndSave(path: String, value: Any) {
    this[path] = value
    this.save()
}

/**
 * Save.
 */
fun FileConfiguration.save() {
    try {
        this.file?.let { this.save(it) }
    } catch (e: IOException) {
        throw RuntimeException(e)
    }
}

/**
 * Save.
 */
fun FileConfiguration.load() {
    try {
        this.file?.let { this.load(it) }
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
fun ConfigurationSection.isUuid(path: String): Boolean {
    return this.getUuid(path) !== EMPTY_UUID
}

/**
 * Gets uuid.
 *
 * @param path the path
 * @return the uuid
 */
fun ConfigurationSection.getUuid(path: String): UUID {
    return this[path].toString().toUuid()
}

/**
 * Gets material.
 *
 * @param path the path
 * @param def  the def
 * @return the material
 */
fun ConfigurationSection.getMaterial(path: String, def: Material): Material {
    val material = this.getMaterial(path)
    return material ?: def
}

/**
 * Gets material.
 *
 * @param path the path
 * @return the material
 */
fun ConfigurationSection.getMaterial(path: String): Material? {
    return Material.getMaterial(this.getString(path).toString())
}

/**
 * Is material boolean.
 *
 * @param path the path
 * @return the boolean
 */
fun ConfigurationSection.isMaterial(path: String): Boolean {
    return this.getMaterial(path) != null
}

/**
 * Gets a material list.
 *
 * @param path the path
 * @return the material list
 */
fun ConfigurationSection.getMaterialList(path: String): MutableList<Material?> {
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
fun ConfigurationSection.getKeys(path: String, deep: Boolean): Set<String> {
    val section = this.getConfigurationSection(path)
    return section?.getKeys(deep) ?: return emptySet()
}

/**
 * Gets entity.
 *
 * @param path the path
 * @param def  the def
 * @return the entity
 */
fun ConfigurationSection.getEntity(path: String, def: Entity): Entity {
    val entity = MinesonAPI.plugin.server.getEntity(this.getUuid(path))
    return entity ?: def
}

/**
 * Is entity boolean?
 *
 * @param path the path
 * @return the boolean
 */
fun ConfigurationSection.isEntity(path: String): Boolean {
    return this.getEntity(path) != null
}

/**
 * Gets entity.
 *
 * @param path the path
 * @return the entity
 */
fun ConfigurationSection.getEntity(path: String): Entity? {
    return MinesonAPI.plugin.server.getEntity(this.getUuid(path))
}

/**
 * Gets an entity list.
 *
 * @param path the path
 * @return the entity list
 */
fun ConfigurationSection.getEntityList(path: String): List<Entity?> {
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
fun ConfigurationSection.getOfflinePlayerList(path: String): List<OfflinePlayer> {
    val list = this.getList(path)
    if (list == null) {
        return ArrayList(0)
    } else {
        val result: MutableList<OfflinePlayer> = ArrayList()
        val iterator: Iterator<*> = list.iterator()
        while (true) {
            var obj: Any?
            do {
                if (!iterator.hasNext()) {
                    return result
                }
                obj = iterator.next()
            } while (obj !is OfflinePlayer)
            result.add(obj)
        }
    }
}

/**
 * Gets online player.
 *
 * @param path the path
 * @param def  the def
 * @return the online player
 */
fun ConfigurationSection.getOnlinePlayer(path: String, def: Player): Player {
    val player = this.getOnlinePlayer(path)
    return player ?: def
}

/**
 * Gets online player.
 *
 * @param path the path
 * @return the online player
 */
fun ConfigurationSection.getOnlinePlayer(path: String): Player? {
    return this.getOfflinePlayer(path)?.player
}

/**
 * Is online player boolean?
 *
 * @param path the path
 * @return the boolean
 */
fun ConfigurationSection.isOnlinePlayer(path: String): Boolean {
    return this.getOnlinePlayer(path) != null
}

/**
 * Gets an online player list.
 *
 * @param path the path
 * @return the online player list
 */
fun ConfigurationSection.getOnlinePlayerList(path: String): List<Player?> {
    val list = this.getList(path)
    if (list == null) {
        return ArrayList(0)
    } else {
        val result: MutableList<Player?> = ArrayList()
        val iterator: Iterator<*> = list.iterator()
        while (true) {
            var obj: Any?
            do {
                if (!iterator.hasNext()) {
                    return result
                }
                obj = iterator.next()
            } while (obj !is String)
            val uuid = obj.toUuid()
            if (uuid !== EMPTY_UUID) {
                result.add(MinesonAPI.plugin.server.getPlayer(uuid))
            } else {
                result.add(MinesonAPI.plugin.server.getPlayer(obj))
            }
        }
    }
}

/**
 * Gets item stack list.
 *
 * @param path the path
 * @return the item stack list
 */
fun ConfigurationSection.getItemStackList(path: String): List<ItemStack> {
    val list = this.getList(path)
    if (list == null) {
        return ArrayList(0)
    } else {
        val result: MutableList<ItemStack> = ArrayList()
        val iterator: Iterator<*> = list.iterator()
        while (true) {
            var obj: Any?
            do {
                if (!iterator.hasNext()) {
                    return result
                }
                obj = iterator.next()
            } while (obj !is ItemStack)
            result.add(obj)
        }
    }
}


fun ConfigurationSection.getLocationList(path: String): List<Location> {
    val list = this.getList(path)
    if (list == null) {
        return ArrayList(0)
    } else {
        val result: MutableList<Location> = ArrayList()
        val iterator: Iterator<*> = list.iterator()
        while (true) {
            var obj: Any?
            do {
                if (!iterator.hasNext()) {
                    return result
                }
                obj = iterator.next()
            } while (obj !is Location)
            result.add(obj)
        }
    }
}