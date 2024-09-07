package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.util.lazy.LazyWithReceiver
import net.minecraft.server.level.ServerPlayer
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.entity.Player

val Player.nms: ServerPlayer by LazyWithReceiver<Player, ServerPlayer> {
    return@LazyWithReceiver (this as CraftPlayer).handle
}
