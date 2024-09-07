package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.entity.Arrow
import org.bukkit.inventory.ItemStack

/**
 * Get Bow from Metadatable shot Arrow
 *
 * @return Bow ItemStack
 */
fun Arrow.getBowFromArrow(): ItemStack? {
    val values = this.getMetadata("shot-from")
    val value = values.first().value()
    if (value is ItemStack) {
        return value
    }
    return null
}
