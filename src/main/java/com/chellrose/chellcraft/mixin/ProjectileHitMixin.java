package com.chellrose.chellcraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.chellrose.chellcraft.callbacks.ProjectileHitCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;

@Mixin(AbstractArrow.class)
public class ProjectileHitMixin {
	@Inject(at = @At("HEAD"), method = "onHitEntity", cancellable = true)
	private void onEntityHit(EntityHitResult hit, CallbackInfo info) {
		InteractionResult result = ProjectileHitCallback.EVENT.invoker().onEntityHit(hit, (AbstractArrow) (Object) this);
		if (result == InteractionResult.FAIL) {
			info.cancel();
		}
	}
}