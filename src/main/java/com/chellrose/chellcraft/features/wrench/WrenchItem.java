package com.chellrose.chellcraft.features.wrench;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;

public class WrenchItem {
    public static final Logger LOGGER = ChellCraft.LOGGER;

    public static final @NonNull Item WRENCH_MATERIAL = Items.TRIPWIRE_HOOK;
    private static final @NonNull String IS_WRENCH_KEY = "caa_wrench";
    private static final @NonNull String BLOCK_DATA_KEY = "caa_block_data";

    @SuppressWarnings("null")
    public static @NonNull ItemStackTemplate getTemplate() {
        DataComponentPatch.Builder wrenchBuilder = DataComponentPatch.builder();
        
        // Wrench flag
        CompoundTag customDataTag = new CompoundTag();
        customDataTag.putBoolean(IS_WRENCH_KEY, true);
        wrenchBuilder.set(DataComponents.CUSTOM_DATA, CustomData.of(customDataTag));

        // Item properties
        Style borderStyle = Style.EMPTY.withItalic(false).withColor(TextColor.parseColor("#cccccc").getOrThrow());
        Style textStyle = Style.EMPTY.withItalic(true).withColor(TextColor.parseColor("#118686").getOrThrow());
        Style nameStyle = Style.EMPTY.withItalic(false).withBold(true).withColor(TextColor.parseColor("#d468cb").getOrThrow());
        wrenchBuilder.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        wrenchBuilder.set(DataComponents.CUSTOM_NAME, Component.literal("Turbo Encabulator").withStyle(
            style -> nameStyle.applyTo(style)));
        wrenchBuilder.set(DataComponents.UNBREAKABLE, Unit.INSTANCE);
        wrenchBuilder.set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 0.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, 0.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND)
            .build();

        ItemLore lore = new ItemLore(List.of(
            Component.literal("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓")
                .withStyle(style -> borderStyle.applyTo(style)),
            Component.literal("   For a number of years now, work has been proceeding in order to bring ")
                .withStyle(style -> textStyle.applyTo(style)),
            Component.literal("   perfection to the crudely conceived idea of a transmission that would not only ")
                .withStyle(style -> textStyle.applyTo(style)),
            Component.literal("   supply inverse reactive current for use in unilateral phase detractors, but ")
                .withStyle(style -> textStyle.applyTo(style)),
            Component.literal("   would also be capable of automatically synchronizing cardinal grammeters. ")
                .withStyle(style -> textStyle.applyTo(style)),
            Component.literal("   Such an instrument is the ")
                .withStyle(style -> textStyle.applyTo(style))
                .append(Component.literal("Turbo Encabulator").withStyle(style -> nameStyle.applyTo(style)))
                .append(Component.literal(".").withStyle(style -> textStyle.applyTo(style))),
            Component.literal("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛")
                .withStyle(style -> borderStyle.applyTo(style))
        ));
        wrenchBuilder.set(DataComponents.LORE, lore);

        return new ItemStackTemplate(WRENCH_MATERIAL, wrenchBuilder.build());
    }
}
