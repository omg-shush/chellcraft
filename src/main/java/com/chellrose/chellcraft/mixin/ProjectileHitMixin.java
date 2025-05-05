package com.chellrose.chellcraft.mixin;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.chellrose.chellcraft.util.ProjectileHitCallback;

@Mixin(ProjectileEntity.class)
public class ProjectileHitMixin {
	@Inject(at = @At("HEAD"), method = "onEntityHit", cancellable = true)
	private void onEntityHit(EntityHitResult hit, CallbackInfo info) {
		ActionResult result = ProjectileHitCallback.EVENT.invoker().onEntityHit(hit, (ProjectileEntity) (Object) this);
		if (result == ActionResult.FAIL) {
			info.cancel();
		}
	}
}