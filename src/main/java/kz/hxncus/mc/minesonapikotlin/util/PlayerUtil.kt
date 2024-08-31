package kz.hxncus.mc.minesonapikotlin.util

import net.minecraft.server.level.ServerPlayer
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.entity.Player

val Player.nms: ServerPlayer get() = (this as CraftPlayer).handle