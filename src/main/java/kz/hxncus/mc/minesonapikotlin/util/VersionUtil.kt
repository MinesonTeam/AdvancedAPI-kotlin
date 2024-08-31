package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.BlockIterator
import java.util.regex.Pattern

/**
 * The Nms version by int.
 */
val NMS_VERSION_BY_INT: Map<Int, String> = mapOf(
    180 to "1_8_R1",
    183 to "1_8_R2",
    184 to "1_8_R3",
    185 to "1_8_R3",
    186 to "1_8_R3",
    187 to "1_8_R3",
    188 to "1_8_R3",
    190 to "1_9_R1",
    192 to "1_9_R1",
    194 to "1_9_R2",
    1100 to "1_10_R1",
    1102 to "1_10_R1",
    1110 to "1_11_R1",
    1111 to "1_11_R1",
    1112 to "1_11_R1",
    1120 to "1_12_R1",
    1121 to "1_12_R1",
    1122 to "1_12_R1",
    1130 to "1_13_R1",
    1131 to "1_13_R2",
    1132 to "1_13_R2",
    1140 to "1_14_R1",
    1141 to "1_14_R1",
    1142 to "1_14_R1",
    1143 to "1_14_R1",
    1144 to "1_14_R1",
    1150 to "1_15_R1",
    1151 to "1_15_R1",
    1152 to "1_15_R1",
    1160 to "1_16_R1",
    1161 to "1_16_R1",
    1162 to "1_16_R2",
    1163 to "1_16_R2",
    1164 to "1_16_R3",
    1165 to "1_16_R3",
    1170 to "1_17_R1",
    1171 to "1_17_R1",
    1180 to "1_18_R1",
    1181 to "1_18_R1",
    1182 to "1_18_R2",
    1190 to "1_19_R1",
    1191 to "1_19_R1",
    1192 to "1_19_R1",
    1193 to "1_19_R2",
    1194 to "1_19_R3",
    1200 to "1_20_R1",
    1201 to "1_20_R1",
    1202 to "1_20_R2",
    1203 to "1_20_R3",
    1204 to "1_20_R3",
    1205 to "1_20_R3",
    1206 to "1_20_R4",
    1210 to "1_21_R1",
)

/**
 * The Current version.
 */
val CURRENT_VERSION: Int = currentVersion

/**
 * The Nms version.
 */
val NMS_VERSION: String? = NMS_VERSION_BY_INT[CURRENT_VERSION]

/**
 * The Is pdc version.
 */
val IS_PDC_VERSION: Boolean = CURRENT_VERSION >= 1140

/**
 * The Is hex version.
 */
val IS_HEX_VERSION: Boolean = CURRENT_VERSION >= 1160

/**
 * The Is target block version.
 */
val IS_TARGET_BLOCK_VERSION: Boolean = CURRENT_VERSION >= 1140

/**
 * The Is namespaced key version.
 */
val IS_NAMESPACED_KEY_VERSION: Boolean = CURRENT_VERSION >= 1120

/**
 * The Is spawn egg meta-version.
 */
val IS_SPAWN_EGG_META_VERSION: Boolean = CURRENT_VERSION >= 1110

/**
 * The Is potion color version.
 */
val IS_POTION_COLOR_VERSION: Boolean = CURRENT_VERSION >= 1110

/**
 * The Is potion data version.
 */
val IS_POTION_DATA_VERSION: Boolean = CURRENT_VERSION >= 190

/**
 * The Sign.
 */
val SIGN: Material = sign

/**
 * Is equal boolean.
 *
 * @param minor the minor
 * @return the boolean
 */
fun isEqual(minor: Int): Boolean {
    return CURRENT_VERSION == minor
}

/**
 * Is after boolean.
 *
 * @param minor the minor
 * @return the boolean
 */
fun isAfter(minor: Int): Boolean {
    return CURRENT_VERSION > minor
}

/**
 * Is after or equal boolean.
 *
 * @param minor the minor
 * @return the boolean
 */
fun isAfterOrEqual(minor: Int): Boolean {
    return CURRENT_VERSION >= minor
}

/**
 * Is before boolean.
 *
 * @param minor the minor
 * @return the boolean
 */
fun isBefore(minor: Int): Boolean {
    return CURRENT_VERSION < minor
}

/**
 * Is before or equal boolean.
 *
 * @param minor the minor
 * @return the boolean
 */
fun isBeforeOrEqual(minor: Int): Boolean {
    return CURRENT_VERSION <= minor
}

private val currentVersion: Int
    get() {
        val matcher = Pattern.compile("(?<version>\\d+\\.\\d+)(?<patch>\\.\\d+)?")
            .matcher(Bukkit.getBukkitVersion())
        val stringBuilder = StringBuilder(8)
        if (matcher.find()) {
            stringBuilder.append(matcher.group("version").replace(".", ""))
            val patch = matcher.group("patch")
            if (patch == null) {
                stringBuilder.append('0')
            } else {
                stringBuilder.append(patch.replace(".", ""))
            }
        }
        try {
            return stringBuilder.toString().toInt()
        } catch (ignored: NumberFormatException) {
            throw RuntimeException("Could not retrieve server version!")
        }
    }

private val sign: Material
    get() = if (CURRENT_VERSION < 1140) {
        Material.valueOf("SIGN")
    } else {
        Material.valueOf("OAK_SIGN")
    }

/**
 * Gets target block.
 *
 * @param player   the player
 * @param distance the distance
 * @return the target block
 */
fun getTargetBlock(player: Player, distance: Int): Block {
    if (IS_TARGET_BLOCK_VERSION) {
        val targetBlock = player.getTargetBlockExact(distance)
        if (targetBlock != null) {
            return targetBlock
        }
    } else {
        val iterator = BlockIterator(player, distance)
        while (iterator.hasNext()) {
            val block = iterator.next()
            if (block.type != Material.AIR) {
                return block
            }
        }
    }
    return player.location.block
}

/**
 * Gets enchantment name.
 *
 * @param enchantment the enchantment
 * @return the enchantment name
 */
fun getEnchantmentName(enchantment: Enchantment): String {
    return if (IS_NAMESPACED_KEY_VERSION) {
        enchantment.key.key
    } else {
        enchantment.name
    }
}

/**
 * Remove falling block after land.
 *
 * @param plugin       the plugin
 * @param fallingBlock the falling block
 */
// TODO Check when FallingBlock#setCancelDrop(boolean cancel) is created
fun removeFallingBlockAfterLand(plugin: Plugin, fallingBlock: Entity) {
    plugin.server.scheduler.runTaskTimer(plugin, { task: BukkitTask ->
        if (!fallingBlock.isValid) {
            fallingBlock.location
                .block.type = Material.AIR
            task.cancel()
        }
    }, 0L, 10L)
}
