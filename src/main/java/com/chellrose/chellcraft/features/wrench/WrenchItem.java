package com.chellrose.chellcraft.features.wrench;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.mojang.datafixers.util.Pair;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.state.BlockState;

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

    public static boolean isWrench(ItemStack item) {
        if (item == null || item.isEmpty()) {
            return false;
        }
        if (item.getItem() != WRENCH_MATERIAL) {
            return false;
        }
        CustomData data = item.get(DataComponents.CUSTOM_DATA);
        if (data == null) {
            return false;
        }
        CompoundTag tag = data.copyTag();
        return tag.getBoolean(IS_WRENCH_KEY).orElse(false);
    }

    @SuppressWarnings("null")
    public static void setBlockState(ItemStack wrench, String clazz, BlockState state) {
        Optional<@NonNull Tag> encoded = BlockState.CODEC.encode(state, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).result();
        if (encoded.isPresent()) {
            Tag encodedState = encoded.get();
            CompoundTag customDataTag = wrench.get(DataComponents.CUSTOM_DATA).copyTag();
            CompoundTag blockDataTag = customDataTag.getCompoundOrEmpty(BLOCK_DATA_KEY);
            blockDataTag.put(clazz, encodedState);
            customDataTag.put(BLOCK_DATA_KEY, blockDataTag);
            wrench.set(DataComponents.CUSTOM_DATA, CustomData.of(customDataTag));
        }
    }

    @SuppressWarnings("null")
    public static @Nullable BlockState getBlockState(ItemStack wrench, String clazz) {
        CompoundTag customDataTag = Objects.requireNonNull(wrench.get(DataComponents.CUSTOM_DATA)).copyTag();
        Optional<CompoundTag> maybeBlockDataTag = customDataTag.getCompound(BLOCK_DATA_KEY);
        if (maybeBlockDataTag.isPresent()) {
            CompoundTag blockDataTag = maybeBlockDataTag.get();
            @Nullable Tag encodedState = blockDataTag.get(Objects.requireNonNull(clazz));
            if (encodedState != null) {
                Optional<Pair<BlockState, Tag>> result = BlockState.CODEC.decode(NbtOps.INSTANCE, encodedState).result();
                if (result.isPresent()) {
                    BlockState storedState = result.get().getFirst();
                    return storedState;
                }
            }
        }
        return null;
    }
}
