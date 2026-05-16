package com.chellrose.chellcraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.chellrose.chellcraft.callbacks.ArmorStandRemoveItemCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;


@Mixin(ArmorStand.class)
public class ArmorStandRemoveItemMixin {
	@Inject(at = @At("RETURN"), method = "swapItem")
	private void onArmorStandEquip(Player player, EquipmentSlot slot, ItemStack stack, InteractionHand hand, CallbackInfoReturnable<Boolean> info) {
		if ((stack.isEmpty() || stack.getCount() <= 1) && info.getReturnValue()) {
			ArmorStandRemoveItemCallback.EVENT.invoker().onArmorStandUnequip((ArmorStand) (Object) this, player);
		}
		// No fail implemented
	}
}