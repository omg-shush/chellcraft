package com.chellrose.chellcraft.features.sit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.callbacks.EntityDismountCallback;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.EndRodBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ListenerSit {

    public static final double DELTA_Y = 0.35;
    public static final String SEAT_TAG = "chellcraft:is_seat";
    public static final long SIT_COOLDOWN = 1000L;
    public static final Logger LOGGER = ChellCraft.LOGGER;

    private Map<UUID, Long> lastSit;

    public ListenerSit() {
        lastSit = new HashMap<>();
        UseBlockCallback.EVENT.register(this::sit);
        EntityDismountCallback.EVENT.register(this::stand);
        ServerPlayConnectionEvents.DISCONNECT.register(this::disconnect);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::stopping);
    }

    private @NonNull InteractionResult sit(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
        long currentTime = System.currentTimeMillis();
        UUID uuid = player.getUUID();
        if (!this.lastSit.containsKey(uuid) || currentTime - this.lastSit.get(uuid) > 1_000) {
            if (hand.equals(InteractionHand.MAIN_HAND) && player.getItemInHand(hand).isEmpty()) {
                BlockState b = world.getBlockState(hitResult.getBlockPos());
                String key = b.getBlock().getDescriptionId();
                boolean validStairs = key.contains("stairs") && b.getValue(StairBlock.HALF) == Half.BOTTOM;
                boolean validSlab = key.contains("slab") && b.getValue(SlabBlock.TYPE) == SlabType.BOTTOM;
                boolean validCampfire = key.equals("block.minecraft.campfire") && !b.getValue(CampfireBlock.LIT);
                boolean validEndRod = key.equals("block.minecraft.end_rod") && b.getValue(EndRodBlock.FACING) != Direction.DOWN && b.getValue(EndRodBlock.FACING) != Direction.UP;
                boolean topFace = hitResult.getDirection() == Direction.UP;
                boolean opaqueAbove = world.getBlockState(hitResult.getBlockPos().above()).canOcclude();
                boolean support = false;
                BlockPos[] adjacentBlocks = new BlockPos[] {
                    hitResult.getBlockPos().north(),
                    hitResult.getBlockPos().east(),
                    hitResult.getBlockPos().south(),
                    hitResult.getBlockPos().west()
                };
                for (BlockPos pos : adjacentBlocks) {
                    String t = world.getBlockState(Objects.requireNonNull(pos)).getBlock().getDescriptionId();
                    if (t.contains("sign") || t.contains("trapdoor")) {
                        support = true;
                        break;
                    }
                }
                if (topFace && !opaqueAbove && support && (validStairs || validSlab || validCampfire || validEndRod)) {
                    Bee bee = new Bee(EntityTypes.BEE, world);
                    BlockPos pos = hitResult.getBlockPos();
                    bee.setPos(pos.getX() + 0.5, pos.getY() + DELTA_Y, pos.getZ() + 0.5);
                    bee.setYRot((player.getYRot() + 180.0F) % 360.0F);
                    bee.setYBodyRot(bee.getYRot());
                    bee.setYHeadRot(bee.getYRot());
                    bee.setNoGravity(true);
                    bee.setDeltaMovement(Vec3.ZERO);
                    bee.setInvulnerable(true);
                    bee.setSilent(true);
                    bee.setInvisible(true);
                    bee.setNoAi(true);
                    Multimap<Holder<Attribute>,AttributeModifier> multimap = HashMultimap.create();
                    multimap.put(Attributes.SCALE, new AttributeModifier(Identifier.parse("generic.scale"), 0.0625 - 1.0, Operation.ADD_MULTIPLIED_BASE));
                    multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.parse("generic.attack_damage"), -1.0, Operation.ADD_MULTIPLIED_BASE));
                    bee.getAttributes().addTransientAttributeModifiers(multimap);
                    bee.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, -1, 1, true, false, false));
                    bee.addTag(SEAT_TAG);
                    world.addFreshEntity(bee);
                    Objects.requireNonNull(world.getServer()).execute(() -> player.startRiding(bee, false, false));
                    this.lastSit.put(uuid, currentTime);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    private @NonNull InteractionResult stand(Entity passenger, Entity mount) {
        if (passenger instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) passenger;
            if (mount instanceof Bee) {
                Bee bee = (Bee) mount;
                if (bee.entityTags().contains(SEAT_TAG)) {
                    player.stopRiding();
                    player.setPos(mount.position().add(0.0, 1.0 - DELTA_Y, 0.0));
                    mount.remove(RemovalReason.DISCARDED);
                }
            }
        }
        return InteractionResult.PASS;
    }

    private @NonNull InteractionResult disconnect(ServerGamePacketListenerImpl handler, MinecraftServer server) {
        if (handler.player != null) {
            handler.player.stopRiding();
        }
        return InteractionResult.PASS;
    }

    private @NonNull InteractionResult stopping(MinecraftServer server) {
        int count = 0;
        for (ServerLevel world : server.getAllLevels()) {
            for (Bee entity : world.getEntities(EntityTypes.BEE, (bee) -> bee.entityTags().contains(SEAT_TAG))) {
                Objects.requireNonNull(entity).remove(RemovalReason.DISCARDED);
                count++;
            }
        }
        if (count > 0) {
            LOGGER.warn("chellcraft: Removed " + count + " seats");
        }
        return InteractionResult.PASS;
    }
}
