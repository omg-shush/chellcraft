package com.chellrose.chellcraft.util;

import org.jspecify.annotations.NonNull;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class PlayerSoundUtil {
    public static void playSoundToPlayer(Player player, SoundEvent soundEvent, @NonNull SoundSource category, float volume, float pitch) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(new ClientboundSoundPacket(
            BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent),
            category,
            player.getX(),
            player.getY(),
            player.getZ(),
            volume,
            pitch,
            player.getRandom().nextLong()));
    }
}
