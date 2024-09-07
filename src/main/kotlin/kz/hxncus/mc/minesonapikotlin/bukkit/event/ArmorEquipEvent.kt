package kz.hxncus.mc.minesonapikotlin.bukkit.event

import org.bukkit.entity.Entity
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent
import org.bukkit.inventory.ItemStack

/**
 * Class Armor equip event.
 *
 * @author Hxncus
 * @since 1.0.1
 */
class ArmorEquipEvent(what: Entity,
                      private val equipMethod: EquipMethod,
                      private val armorType: ArmorType,
                      private val oldArmorPiece: ItemStack,
                      private val newArmorPiece: ItemStack) : EntityEvent(what), Cancellable {
    companion object {
        private val handlers = HandlerList()
    }

    private var cancelled = false

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
    }

    override fun getHandlers(): HandlerList {
        return Companion.handlers
    }

    /**
     * The enum Armor type.
     *
     * @author Hxncus
     * @since 1.0.1
     */
    enum class ArmorType(private val slot: Int) {
        /**
         * Represents armor belonging to the helmet slot, e.g., helmets, skulls, and carved pumpkins.
         */
        HELMET(5),

        /**
         * Represents armor belonging to the chestplate slot, e.g., chestplates and elytras.
         */
        CHESTPLATE(6),

        /**
         * Represents leggings.
         */
        LEGGINGS(7),

        /**
         * Represents boots.
         */
        BOOTS(8);

        companion object {
            /**
             * Attempts to match the ArmorType for the specified ItemStack.
             *
             * @param itemStack The ItemStack to parse the type of.
             * @return The parsed ArmorType, or null if not found.
             */
            fun matchType(itemStack: ItemStack): ArmorType? {
                val type = itemStack.type
                val typeName = type.name
                return if (typeName.endsWith("_HELMET") || typeName.endsWith("_SKULL") ||
                           typeName.endsWith("_HEAD") || typeName.endsWith("CARVED_PUMPKIN")) {
                    HELMET
                } else if (typeName.endsWith("_CHESTPLATE") || typeName.endsWith("ELYTRA")) {
                    CHESTPLATE
                } else if (typeName.endsWith("_LEGGINGS")) {
                    LEGGINGS
                } else if (typeName.endsWith("_BOOTS")) {
                    BOOTS
                } else {
                    return null
                }
            }
        }
    }

    enum class EquipMethod {
        /**
         * When you shift, click an armor piece to equip or unequip.
         */
        SHIFT_CLICK,

        /**
         * When you drag and drop the item to equip or unequip.
         */
        DRAG,

        /**
         * When you manually equip or unequip the item. Use to be DRAG.
         */
        PICK_DROP,

        /**
         * When you right-click an armor piece in the hotbar without the inventory open to equip.
         */
        HOTBAR,

        /**
         * When you press the hotbar slot number while hovering over the armor slot to equip or unequip.
         */
        HOTBAR_SWAP,

        /**
         * When in range of a dispenser that shoots an armor piece to equip.<br></br>
         * Requires the spigot version to have [org.bukkit.event.block.BlockDispenseArmorEvent] implemented.
         */
        DISPENSER,

        /**
         * When an armor piece is removed due to it losing all durabilities.
         */
        BROKE,

        /**
         * When you die, causing all armors to unequip
         */
        DEATH,
    }
}
