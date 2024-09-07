package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import org.bukkit.attribute.Attributable
import org.bukkit.attribute.AttributeInstance

/**
 * Default value of Generic armor.
 */
const val GENERIC_ARMOR: Double = 0.0

/**
 * Default value of generic armor toughness.
 */
const val GENERIC_ARMOR_TOUGHNESS: Double = 0.0

/**
 * Default value of generic attack damage.
 */
const val GENERIC_ATTACK_DAMAGE: Double = 1.0

/**
 * Default value of generic attack knockback.
 */
const val GENERIC_ATTACK_KNOCKBACK: Double = 0.0

/**
 * Default value of generic attack speed.
 */
const val GENERIC_ATTACK_SPEED: Double = 4.0

/**
 * The Generic fall damage multiplier.
 */
const val GENERIC_FALL_DAMAGE_MULTIPLIER: Double = 1.0

/**
 * The Generic gravity.
 */
const val GENERIC_GRAVITY: Double = 0.08

/**
 * The Generic jump strength.
 */
const val GENERIC_JUMP_STRENGTH: Double = 0.41999998688697815

/**
 * The Generic knockback resistance.
 */
const val GENERIC_KNOCKBACK_RESISTANCE: Double = 0.0

/**
 * The Generic luck.
 */
const val GENERIC_LUCK: Double = 0.0

/**
 * The Generic max absorption.
 */
const val GENERIC_MAX_ABSORPTION: Double = 0.0

/**
 * The Generic max health.
 */
const val GENERIC_MAX_HEALTH: Double = 0.0

/**
 * The Generic movement speed.
 */
const val GENERIC_MOVEMENT_SPEED: Double = 0.10000000149011612

/**
 * The Generic safe fall distance.
 */
const val GENERIC_SAFE_FALL_DISTANCE: Double = 3.0

/**
 * The Generic scale.
 */
const val GENERIC_SCALE: Double = 1.0

/**
 * The Generic step height.
 */
const val GENERIC_STEP_HEIGHT: Double = 0.6

/**
 * The Player block break speed.
 */
const val PLAYER_BLOCK_BREAK_SPEED: Double = 1.0

/**
 * The Player block interaction range.
 */
const val PLAYER_BLOCK_INTERACTION_RANGE: Double = 4.5

/**
 * The Player entity interaction range.
 */
const val PLAYER_ENTITY_INTERACTION_RANGE: Double = 3.0

/**
 * The Zombie spawn reinforcements.
 */
const val ZOMBIE_SPAWN_REINFORCEMENTS: Double = 0.0929132596958409

/**
 * Gets attribute instances.
 *
 * @param attributable Entity that has attributable interface
 * @return All attribute instance that attributable has
 */
fun getAttributeInstances(attributable: Attributable): Set<AttributeInstance> {
    val attributes = MinesonAPI.plugin.server.attributes
    val attributeInstances: MutableSet<AttributeInstance> = HashSet(attributes.size)
    for (attribute in attributes) {
        val attributeInstance = attributable.getAttribute(attribute) ?: continue
        attributeInstances.add(attributeInstance)
    }
    return attributeInstances
}