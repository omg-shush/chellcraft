package com.chellrose.chellcraft.mixin;

import net.minecraft.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.chellrose.chellcraft.callbacks.EntityDismountCallback;

@Mixin(Entity.class)
public class EntityDismountMixin {
	@Inject(at = @At("RETURN"), method = "removePassenger")
	private void onDismount(Entity passenger, CallbackInfo info) {
		EntityDismountCallback.EVENT.invoker().onDismount(passenger, (Entity) (Object) this);
		// No fail implemented
	}
}