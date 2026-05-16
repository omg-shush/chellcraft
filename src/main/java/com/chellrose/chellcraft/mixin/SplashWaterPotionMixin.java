package com.chellrose.chellcraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.chellrose.chellcraft.callbacks.SplashWaterPotionCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;


@Mixin(AbstractThrownPotion.class)
public class SplashWaterPotionMixin {
	@Inject(at = @At("RETURN"), method = "onHitAsWater")
	private void dropHeldStack(ServerLevel world, CallbackInfo info) {
		SplashWaterPotionCallback.EVENT.invoker().onSplashWaterPotion((AbstractThrownPotion) (Object) this, world);
		// No fail implemented
	}
}