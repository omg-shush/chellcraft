package com.chellrose.chellcraft.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.chellrose.chellcraft.callbacks.ItemFrameRemoveItemCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;


@Mixin(ItemFrame.class)
public class ItemFrameRemoveItemMixin {
	@Inject(at = @At("RETURN"), method = "dropItem(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Z)V")
	private void dropHeldStack(ServerLevel world, @Nullable Entity entity, boolean dropSelf, CallbackInfo info) {
		ItemFrameRemoveItemCallback.EVENT.invoker().onItemFrameUnequip((ItemFrame) (Object) this, entity instanceof Player ? (Player) entity : null);
		// No fail implemented
	}
}