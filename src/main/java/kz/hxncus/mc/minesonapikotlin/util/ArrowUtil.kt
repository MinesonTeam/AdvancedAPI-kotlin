package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.Metadatable

/**
 * Get Bow from Metadatable shot Arrow
 *
 * @param metadatable Arrow
 * @return Bow ItemStack
 */
fun getBowFromArrow(metadatable: Metadatable): ItemStack? {
    val values = metadatable.getMetadata("shot-from")
    val value = values.first().value()
    if (value !is ItemStack) {
        return null
    }
    return value
}
