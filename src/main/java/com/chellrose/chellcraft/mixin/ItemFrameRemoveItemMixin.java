package com.chellrose.chellcraft.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.chellrose.chellcraft.callbacks.ItemFrameRemoveItemCallback;


@Mixin(ItemFrameEntity.class)
public class ItemFrameRemoveItemMixin {
	@Inject(at = @At("RETURN"), method = "dropHeldStack")
	private void dropHeldStack(ServerWorld world, @Nullable Entity entity, boolean dropSelf, CallbackInfo info) {
		ItemFrameRemoveItemCallback.EVENT.invoker().onItemFrameUnequip((ItemFrameEntity) (Object) this, entity instanceof PlayerEntity ? (PlayerEntity) entity : null);
		// No fail implemented
	}
}