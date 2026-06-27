package com.chellrose.chellcraft.features.wrench;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.BlockHitResult;

public class ListenerWrenchBlock {
    public ListenerWrenchBlock() {
        UseBlockCallback.EVENT.register(this::block);
    }

    private @NonNull InteractionResult block(Player player, Level level, @NonNull InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        if (WrenchItem.isWrench(stack)) {
            BlockState state = level.getBlockState(hitResult.getBlockPos());
            Class<?> clazz = state.getBlock().getClass();
            WrenchBlockClipboard clazzFactory = CLIPBOARDS.get(getMappedClassName(clazz));
            if (clazz != null && clazzFactory != null) {
                if (player.isShiftKeyDown()) {
                    // Copy data
                    WrenchItem.setBlockState(stack, getMappedClassName(clazz), state);
                    player.sendOverlayMessage(
                        Component.literal("Copied " + clazz.getSimpleName())
                            .withStyle(style -> style.withItalic(true).withColor(TextColor.parseColor("#d468cb").getOrThrow())));
                    level.playSound(null, hitResult.getBlockPos(), SoundEvents.VILLAGER_YES, SoundSource.PLAYERS, 1.0f, 1.2f);
                } else {
                    // Paste data
                    BlockState template = WrenchItem.getBlockState(stack, getMappedClassName(clazz));
                    if (template == null) {
                        player.sendOverlayMessage(
                            Component.literal("No block data to paste. Sneak-right-click an equivalent block type to copy")
                                .withStyle(style -> style.withItalic(true).withColor(TextColor.parseColor("#d468cb").getOrThrow())));
                        level.playSound(null, hitResult.getBlockPos(), SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0f, 1.2f);
                    } else {
                        BlockState newState = clazzFactory.paste(template, state);
                        level.setBlock(hitResult.getBlockPos(), newState, 2 | 16); // Suppress block updates
                        player.sendOverlayMessage(
                            Component.literal("Pasted " + clazz.getSimpleName())
                                .withStyle(style -> style.withItalic(true).withColor(TextColor.parseColor("#d468cb").getOrThrow())));
                        level.playSound(null, hitResult.getBlockPos(), newState.getSoundType().getPlaceSound(), SoundSource.PLAYERS, 1.0f, 1.2f);
                        ((ServerLevel) level).addParticle(FabricParticleTypes.simple(), hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, 0.25, 0.25, 0.25);
                    }
                }

                
            }
            return InteractionResult.FAIL; // Never actually place the wrench on a block
        }


        return InteractionResult.PASS;
    }

    private static final Map<String, String> CLASS_MAP = Map.of(
        "net.minecraft.world.level.block.BlastFurnaceBlock", "net.minecraft.world.level.block.FurnaceBlock",
        "net.minecraft.world.level.block.SmokerBlock", "net.minecraft.world.level.block.FurnaceBlock"
    );

    private static @NonNull String getMappedClassName(@NonNull Class<?> clazz) {
        String className = clazz.getName();
        if (CLASS_MAP.containsKey(className)) {
            return Objects.requireNonNull(CLASS_MAP.get(className));
        }
        return Objects.requireNonNull(className);
    }

    private static interface WrenchBlockClipboard {
        /**
         * Pastes the given block data onto the specified block data.
         * Only allowed fields (ie, not waterlogged) will be pasted.
         *
         * @param template the block data to be pasted
         * @param target   the block data which should be pasted onto
         * @return the modified target block data
         */
        public @NonNull BlockState paste(@NonNull BlockState template, @NonNull BlockState target);
    }

    private static final Map<String, WrenchBlockClipboard> CLIPBOARDS = new HashMap<>();
    
    static {
        // Stairs
        CLIPBOARDS.put(StairBlock.class.getName(), new WrenchBlockClipboard() {
            @Override
            public @NonNull BlockState paste(@NonNull BlockState template, @NonNull BlockState target) {
                return Objects.requireNonNull(target
                    .setValue(BlockStateProperties.HALF, template.getValue(BlockStateProperties.HALF))
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, template.getValue(BlockStateProperties.HORIZONTAL_FACING))
                    .setValue(BlockStateProperties.STAIRS_SHAPE, template.getValue(BlockStateProperties.STAIRS_SHAPE)));
            }
        });

        // Slabs
        CLIPBOARDS.put(SlabBlock.class.getName(), new WrenchBlockClipboard() {
            @Override
            public @NonNull BlockState paste(@NonNull BlockState template, @NonNull BlockState target) {
                if (template.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.DOUBLE)
                    || target.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.DOUBLE)) {
                    // Don't paste double slabs
                    return target;
                }
                return Objects.requireNonNull(target
                    .setValue(BlockStateProperties.SLAB_TYPE, template.getValue(BlockStateProperties.SLAB_TYPE)));
            }
        });

        // Fences
        CLIPBOARDS.put(FenceBlock.class.getName(), new WrenchBlockClipboard() {
            @Override
            public @NonNull BlockState paste(@NonNull BlockState template, @NonNull BlockState target) {
                for (BooleanProperty face : CrossCollisionBlock.PROPERTY_BY_DIRECTION.values()) {
                    if (face != null) {
                        target = Objects.requireNonNull(target.setValue(face, template.getValue(face)));
                    }
                }
                return target;
            }
        });

        // Walls
        CLIPBOARDS.put(WallBlock.class.getName(), new WrenchBlockClipboard() {
            @Override
            @SuppressWarnings("unchecked")
            public @NonNull BlockState paste(@NonNull BlockState template, @NonNull BlockState target) {
                for (EnumProperty<WallSide> side : (EnumProperty<WallSide>[])new EnumProperty<?>[]{BlockStateProperties.NORTH_WALL, BlockStateProperties.EAST_WALL, BlockStateProperties.SOUTH_WALL, BlockStateProperties.WEST_WALL}) {
                    if (side != null) {
                        target = Objects.requireNonNull(target.setValue(side, template.getValue(side)));
                    }
                }
                target = Objects.requireNonNull(target.setValue(BlockStateProperties.UP, template.getValue(BlockStateProperties.UP)));
                return target;
            }
        });

        // Glass panes
        CLIPBOARDS.put(IronBarsBlock.class.getName(), new WrenchBlockClipboard() {
            @Override
            public @NonNull BlockState paste(@NonNull BlockState template, @NonNull BlockState target) {
                for (BooleanProperty face : CrossCollisionBlock.PROPERTY_BY_DIRECTION.values()) {
                    if (face != null) {
                        target = Objects.requireNonNull(target.setValue(face, template.getValue(face)));
                    }
                }
                return target;
            }
        });

        // Furnaces
        CLIPBOARDS.put(FurnaceBlock.class.getName(), new WrenchBlockClipboard() {
            @Override
            public @NonNull BlockState paste(@NonNull BlockState template, @NonNull BlockState target) {
                return Objects.requireNonNull(target
                    .setValue(AbstractFurnaceBlock.FACING, template.getValue(AbstractFurnaceBlock.FACING))
                    .setValue(AbstractFurnaceBlock.LIT, template.getValue(AbstractFurnaceBlock.LIT)));
            }
        });
    
        // Lanterns
        CLIPBOARDS.put(LanternBlock.class.getName(), new WrenchBlockClipboard() {
            @Override
            public @NonNull BlockState paste(@NonNull BlockState template, @NonNull BlockState target) {
                return Objects.requireNonNull(target
                    .setValue(LanternBlock.HANGING, template.getValue(LanternBlock.HANGING)));
            }
        });
    }
}
