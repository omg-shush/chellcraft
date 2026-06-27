package com.chellrose.chellcraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.Vec3;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow
    private Vec3 awaitingPositionFromClient;
    
    @ModifyExpressionValue(method = "handleUseItemOn", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;awaitingPositionFromClient:Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 forceAwaitingPositionNull(Vec3 original) {
        // Work around seeming bug causing the server to send a frivolous Build Limit Message
        // to the client when the player interacts with a block while mounting, as a result of the Sit feature
        return null;
    }
}
