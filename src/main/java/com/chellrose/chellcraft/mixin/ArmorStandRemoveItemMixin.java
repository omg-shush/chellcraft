package com.chellrose.chellcraft.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.chellrose.chellcraft.callbacks.ArmorStandRemoveItemCallback;


@Mixin(ArmorStandEntity.class)
public class ArmorStandRemoveItemMixin {
	@Inject(at = @At("RETURN"), method = "equip")
	private void onArmorStandEquip(PlayerEntity player, EquipmentSlot slot, ItemStack stack, Hand hand, CallbackInfoReturnable<Boolean> info) {
		if ((stack.isEmpty() || stack.getCount() <= 1) && info.getReturnValue()) {
			ArmorStandRemoveItemCallback.EVENT.invoker().onArmorStandUnequip((ArmorStandEntity) (Object) this, player);
		}
		// No fail implemented
	}
}