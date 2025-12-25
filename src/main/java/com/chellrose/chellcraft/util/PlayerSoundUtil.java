package com.chellrose.chellcraft.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class PlayerSoundUtil {
    public static void playSoundToPlayer(PlayerEntity player, SoundEvent soundEvent, SoundCategory category, float volume, float pitch) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        serverPlayer.networkHandler.sendPacket(new PlaySoundS2CPacket(
            Registries.SOUND_EVENT.getEntry(soundEvent),
            category,
            player.getX(),
            player.getY(),
            player.getZ(),
            volume,
            pitch,
            player.getRandom().nextLong()));
    }
}
