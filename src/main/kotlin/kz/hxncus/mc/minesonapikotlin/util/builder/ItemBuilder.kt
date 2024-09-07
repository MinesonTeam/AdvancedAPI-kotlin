package kz.hxncus.mc.minesonapikotlin.util.builder

import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.persistence.PersistentDataType
import java.util.function.Consumer

/**
 * Class Item builder.
 *
 * @author Hxncus
 * @since 1.0.0
 */
class ItemBuilder {
    private val itemStack: ItemStack
    private val itemMeta: ItemMeta?

    /**
     * Instantiates a new Item builder.
     *
     * @param itemStack the item stack
     */
    constructor(itemStack: ItemStack) {
        this.itemStack = itemStack
        this.itemMeta = itemStack.itemMeta
    }

    /**
     * Instantiates a new Item builder.
     *
     * @param type the type
     */
    constructor(type: Material) {
        this.itemStack = ItemStack(type)
        this.itemMeta = itemStack.itemMeta
    }

    /**
     * Instantiates a new Item builder.
     *
     * @param type   the type
     * @param amount the amount
     */
    constructor(type: Material, amount: Int) {
        this.itemStack = ItemStack(type, amount)
        this.itemMeta = itemStack.itemMeta
    }

    /**
     * Display name item builder.
     *
     * @param name the name
     * @return the item builder
     */
    fun setDisplayName(name: String): ItemBuilder {
        itemMeta?.setDisplayName(name)
        return this
    }

    /**
     * Lore item builder.
     *
     * @param lore the lore
     * @return the item builder
     */
    fun lore(lore: String): ItemBuilder {
        return this.lore(listOf(lore))
    }

    /**
     * Lore string item builder.
     *
     * @param lore the lore
     * @return the item builder
     */
    fun lore(lore: List<String>): ItemBuilder {
        itemMeta?.lore = lore
        return this
    }

    /**
     * Lore item builder.
     *
     * @param lore the lore
     * @return the item builder
     */
    fun lore(vararg lore: String): ItemBuilder {
        return this.lore(listOf(*lore))
    }

    /**
     * Add lore item builder.
     *
     * @param line the line
     * @return the item builder
     */
    fun addLore(line: String): ItemBuilder {
        return this.addLore(listOf(line))
    }

    /**
     * Add lore item builder.
     *
     * @param lines the lines
     * @return the item builder
     */
    fun addLore(vararg lines: String): ItemBuilder {
        return this.addLore(listOf(*lines))
    }

    /**
     * Add lore item builder.
     *
     * @param lines the lines
     * @return the item builder
     */
    fun addLore(lines: List<String>): ItemBuilder {
        val lore = itemMeta?.lore
        lore?.addAll(lines)
        this.itemMeta?.lore = lore
        return this
    }

    /**
     * Sets material.
     *
     * @param material the material
     * @return the material
     */
    fun setMaterial(material: Material): ItemBuilder {
        itemStack.type = material
        return this
    }

    /**
     * Sets material.
     *
     * @param material the material
     * @return the material
     */
    fun setType(material: Material): ItemBuilder {
        itemStack.type = material
        return this
    }

    /**
     * Amount item builder.
     *
     * @param amount the amount
     * @return the item builder
     */
    fun setAmount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }

    /**
     * Color item builder.
     *
     * @param color the color
     * @return the item builder
     */
    fun color(color: Int): ItemBuilder {
        return this.color(Color.fromRGB(color))
    }

    /**
     * Color item builder.
     *
     * @param color the color
     * @return the item builder
     */
    fun color(color: Color): ItemBuilder {
        if (itemMeta is LeatherArmorMeta) {
            itemMeta.setColor(color)
        } else if (itemStack.type.name.endsWith("_wool")) {
            val dyeColor = DyeColor.getByColor(color) ?: return this
            this.setData(dyeColor.woolData.toInt())
        }
        return this
    }


    /**
     * Sets data.
     *
     * @param data the data
     * @return the data
     */
    fun setData(data: Int): ItemBuilder {
        itemStack.durability = data.toShort()
        return this
    }

    /**
     * Meta item builder.
     *
     * @param <T>          the type parameter
     * @param metaClass    the meta-class
     * @param metaConsumer the meta-consumer
     * @return the item builder
     */
    fun <T : ItemMeta?> meta(metaClass: Class<T>, metaConsumer: Consumer<in T>): ItemBuilder {
        if (metaClass.isInstance(this.itemMeta)) {
            metaConsumer.accept(metaClass.cast(this.itemMeta))
        }
        return this
    }

    /**
     * Color item builder.
     *
     * @param color the color
     * @return the item builder
     */
    fun color(color: DyeColor): ItemBuilder {
        return this.color(color.color)
    }

    /**
     * Enchant item builder.
     *
     * @param enchantment the enchantment
     * @param level       the level
     * @param override    the override
     * @return the item builder
     */
    @JvmOverloads
    fun addEnchant(enchantment: Enchantment, level: Int, override: Boolean = true): ItemBuilder {
        itemMeta?.addEnchant(enchantment, level, override)
        return this
    }

    /**
     * Remove enchant item builder.
     *
     * @param enchantment the enchantment
     * @return the item builder
     */
    fun removeEnchant(enchantment: Enchantment): ItemBuilder {
        itemMeta?.removeEnchant(enchantment)
        return this
    }

    /**
     * Clear enchants an item builder.
     *
     * @return the item builder
     */
    fun clearEnchants(): ItemBuilder {
        itemMeta?.enchants?.forEach { (enchantment: Enchantment?, _: Int?) ->
            itemMeta.removeEnchant(enchantment ?: return@forEach)
        }
        return this
    }

    /**
     * Flags item builder.
     *
     * @return the item builder
     */
    fun addAllFlags(): ItemBuilder {
        return this.addFlags(*ItemFlag.entries.toTypedArray())
    }

    /**
     * Flags item builder.
     *
     * @param flags the flags
     * @return the item builder
     */
    fun addFlags(vararg flags: ItemFlag): ItemBuilder {
        itemMeta?.addItemFlags(*flags)
        return this
    }

    /**
     * Remove flags item builder.
     *
     * @return the item builder
     */
    fun clearFlags(): ItemBuilder {
        return this.removeFlags(*ItemFlag.entries.toTypedArray())
    }

    /**
     * Remove flags item builder.
     *
     * @param flags the flags
     * @return the item builder
     */
    fun removeFlags(vararg flags: ItemFlag): ItemBuilder {
        itemMeta?.removeItemFlags(*flags)
        return this
    }

    /**
     * Sets pdc.
     *
     * @param <T>           the type parameter
     * @param namespacedKey the namespaced key
     * @param pdt           the pdt
     * @param value         the value
     * @return the pdc
     */
    fun <T : Any> ItemStack.setPDC(namespacedKey: NamespacedKey, pdt: PersistentDataType<T, T>, value: T) {
        itemMeta?.persistentDataContainer?.set(namespacedKey, pdt, value)
    }

    /**
     * Gets pdc.
     *
     * @param <T>           the type parameter
     * @param namespacedKey the namespaced key
     * @param pdt           the pdt
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
     * @param pdt           the pdt
     * @param def           the def
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
     * @param pdt           the pdt
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
        itemMeta?.persistentDataContainer?.remove(namespacedKey)
    }

    /**
     * Build item stack.
     *
     * @return the item stack
     */
    fun build(): ItemStack {
        itemStack.setItemMeta(this.itemMeta)
        return this.itemStack
    }
}
