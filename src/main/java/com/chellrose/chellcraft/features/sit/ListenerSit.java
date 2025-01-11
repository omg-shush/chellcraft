package com.chellrose.chellcraft.features.sit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.util.EntityDismountCallback;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ListenerSit {

    public static final double DELTA_Y = 0.35;
    public static final String SEAT_TAG = "chellcraft:is_seat";
    public static final long SIT_COOLDOWN = 1000L;
    public static final Logger LOGGER = ChellCraft.LOGGER;

    private Map<UUID, Long> lastSit;

    public ListenerSit() {
        lastSit = new HashMap<>();
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> sit(player, world, hand, hitResult));
        EntityDismountCallback.EVENT.register((passenger, mount) -> stand(passenger, mount));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> disconnect(handler, server));
        ServerLifecycleEvents.SERVER_STOPPING.register((server) -> stopping(server));
    }

    private ActionResult sit(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        long currentTime = System.currentTimeMillis();
        UUID uuid = player.getUuid();
        if (!this.lastSit.containsKey(uuid) || currentTime - this.lastSit.get(uuid) > 1_000) {
            if (hand.equals(Hand.MAIN_HAND) && player.getStackInHand(hand).isEmpty()) {
                BlockState b = world.getBlockState(hitResult.getBlockPos());
                String key = b.getBlock().getTranslationKey();
                boolean validStairs = key.contains("stairs") && b.get(StairsBlock.HALF) == BlockHalf.BOTTOM;
                boolean validSlab = key.contains("slab") && b.get(SlabBlock.TYPE) == SlabType.BOTTOM;
                boolean validCampfire = key.equals("block.minecraft.campfire") && !b.get(CampfireBlock.LIT);
                boolean validEndRod = key.equals("block.minecraft.end_rod") && b.get(EndRodBlock.FACING) != Direction.DOWN && b.get(EndRodBlock.FACING) != Direction.UP;
                boolean topFace = hitResult.getSide() == Direction.UP;
                boolean opaqueAbove = world.getBlockState(hitResult.getBlockPos().up()).isOpaque();
                boolean support = false;
                BlockPos[] adjacentBlocks = new BlockPos[] {
                    hitResult.getBlockPos().north(),
                    hitResult.getBlockPos().east(),
                    hitResult.getBlockPos().south(),
                    hitResult.getBlockPos().west()
                };
                for (BlockPos pos : adjacentBlocks) {
                    String t = world.getBlockState(pos).getBlock().getTranslationKey();
                    if (t.contains("sign") || t.contains("trapdoor")) {
                        support = true;
                        break;
                    }
                }
                if (topFace && !opaqueAbove && support && (validStairs || validSlab || validCampfire || validEndRod)) {
                    BeeEntity bee = new BeeEntity(EntityType.BEE, world);
                    bee.setPosition(hitResult.getBlockPos().toBottomCenterPos().add(0.0, DELTA_Y, 0.0));
                    bee.setYaw((player.getYaw() + 180.0F) % 360.0F);
                    bee.setBodyYaw(bee.getYaw());
                    bee.setHeadYaw(bee.getYaw());
                    bee.setNoGravity(true);
                    bee.setVelocity(Vec3d.ZERO);
                    bee.setInvulnerable(true);
                    bee.setSilent(true);
                    bee.setInvisible(true);
                    bee.setAiDisabled(true);
                    Multimap<RegistryEntry<EntityAttribute>,EntityAttributeModifier> multimap = HashMultimap.create();
                    multimap.put(EntityAttributes.SCALE, new EntityAttributeModifier(Identifier.of("generic.scale"), 0.0625 - 1.0, Operation.ADD_MULTIPLIED_BASE));
                    multimap.put(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Identifier.of("generic.attack_damage"), -1.0, Operation.ADD_MULTIPLIED_BASE));
                    bee.getAttributes().addTemporaryModifiers(multimap);
                    bee.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, -1, 1, true, false, false));
                    bee.addCommandTag(SEAT_TAG);
                    world.spawnEntity(bee);
                    player.startRiding(bee);
                    this.lastSit.put(uuid, currentTime);
                }
            }
        }
        return ActionResult.PASS;
    }

    private ActionResult stand(Entity passenger, Entity mount) {
        if (passenger instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) passenger;
            if (mount instanceof BeeEntity) {
                BeeEntity bee = (BeeEntity) mount;
                if (bee.getCommandTags().contains(SEAT_TAG)) {
                    player.stopRiding();
                    player.setPosition(mount.getPos().add(0.0, 1.0 - DELTA_Y, 0.0));
                    mount.remove(RemovalReason.DISCARDED);
                }
            }
        }
        return ActionResult.PASS;
    }

    private ActionResult disconnect(ServerPlayNetworkHandler handler, MinecraftServer server) {
        if (handler.player != null) {
            handler.player.stopRiding();
        }
        return ActionResult.PASS;
    }

    private ActionResult stopping(MinecraftServer server) {
        int count = 0;
        for (ServerWorld world : server.getWorlds()) {
            for (BeeEntity entity : world.getEntitiesByType(EntityType.BEE, (bee) -> bee.getCommandTags().contains(SEAT_TAG))) {
                entity.remove(RemovalReason.DISCARDED);
                count++;
            }
        }
        if (count > 0) {
            LOGGER.warn("chellcraft: Removed " + count + " seats");
        }
        return ActionResult.PASS;
    }
}
