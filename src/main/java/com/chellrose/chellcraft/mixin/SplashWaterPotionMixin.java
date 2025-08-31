package com.chellrose.chellcraft.mixin;

import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.chellrose.chellcraft.callbacks.SplashWaterPotionCallback;


@Mixin(PotionEntity.class)
public class SplashWaterPotionMixin {
	@Inject(at = @At("RETURN"), method = "explodeWaterPotion")
	private void dropHeldStack(ServerWorld world, CallbackInfo info) {
		SplashWaterPotionCallback.EVENT.invoker().onSplashWaterPotion((PotionEntity) (Object) this, world);
		// No fail implemented
	}
}