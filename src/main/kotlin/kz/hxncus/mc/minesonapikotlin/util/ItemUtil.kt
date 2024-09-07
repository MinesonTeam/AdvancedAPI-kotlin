package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConstructor
import org.bukkit.configuration.file.YamlRepresenter
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.util.*

private val YAML_CONSTRUCTOR = YamlConstructor()
private val YAML_REPRESENTER = YamlRepresenter()
private val DUMPER_OPTIONS = DumperOptions()
private val yaml = Yaml(YAML_CONSTRUCTOR, YAML_REPRESENTER, DUMPER_OPTIONS)

/**
 * Serialize string.
 *
 * @param item the item
 * @return the string
 */
fun serialize(item: ItemStack): String {
    val root = Collections.singletonMap<String, Any>("item", item)
    return yaml.dumpAs(root, null, DumperOptions.FlowStyle.BLOCK)
}

/**
 * Deserialize item stack.
 *
 * @param textItem the text item
 * @return the item stack
 */
fun deserialize(textItem: String?): ItemStack? {
    val root = yaml.load<Map<String, Any>>(textItem)
    return root["item"] as ItemStack?
}

/**
 * Sets pdc.
 *
 * @param <T>           the type parameter
 * @param namespacedKey the namespaced key
 * @param pdt           the persistent data type
 * @param value         the value
 * @return the pdc
 */
fun <T : Any> ItemStack.setPDC(namespacedKey: NamespacedKey, pdt: PersistentDataType<T, T>, value: T) {
    val meta = itemMeta
    meta?.persistentDataContainer?.set(namespacedKey, pdt, value)
    setItemMeta(meta)
}

/**
 * Gets pdc.
 *
 * @param <T>           the type parameter
 * @param namespacedKey the namespaced key
 * @param pdt           the persistent data type
 * @return the pdc
 */
fun <T : Any> ItemStack.getPDC(namespacedKey: NamespacedKey, pdt: PersistentDataType<T, T>): T? {
    return itemMeta?.persistentDataContainer?.get(namespacedKey, pdt)
}

/**
 * Gets or default pdc.
 *
 * @param <T>           the type parameter
 * @param namespacedKey the namespaced key
 * @param pdt           the persistent data type
 * @param def           the def value
 * @return the or default pdc
 */
fun <T : Any> ItemStack.getOrDefaultPDC(namespacedKey: NamespacedKey, pdt: PersistentDataType<T, out T>, def: T): T {
    val value = itemMeta?.persistentDataContainer?.get(namespacedKey, pdt)
    return value ?: def
}

/**
 * Has pdc boolean.
 *
 * @param <T>           the type parameter
 * @param namespacedKey the namespaced key
 * @param pdt           the persistent data type
 * @return the boolean
 */
fun <T> ItemStack.hasPDC(namespacedKey: NamespacedKey, pdt: PersistentDataType<T, T>): Boolean {
    return itemMeta?.persistentDataContainer?.has(namespacedKey, pdt) ?: false
}

/**
 * Remove pdc item builder.
 *
 * @param namespacedKey the namespaced key
 * @return the item builder
 */
fun ItemStack.removePDC(namespacedKey: NamespacedKey) {
    val meta = itemMeta
    meta?.persistentDataContainer?.remove(namespacedKey)
    setItemMeta(meta)
}
