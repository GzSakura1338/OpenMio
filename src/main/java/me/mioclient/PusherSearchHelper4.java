package me.mioclient;

import java.util.Comparator;
import java.util.List;
import me.mioclient.module.combat.Pusher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PusherSearchHelper4.class */
public final class PusherSearchHelper4 implements SearchHelper_4 {
    public static final List<Block> list = List.of(Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.REINFORCED_DEEPSLATE, Blocks.RESPAWN_ANCHOR);
    public final Pusher pusher;

    public PusherSearchHelper4(Pusher pusher) {
        this.pusher = pusher;
    }

    public PlayerEntity getPlayerEntity886() {
        return (PlayerEntity) minecraftClient.world.getPlayers().stream().filter(abstractClientPlayerEntity -> {
            return abstractClientPlayerEntity.isAlive() && abstractClientPlayerEntity != minecraftClient.player && SearchHelper_3.is647((LivingEntity) abstractClientPlayerEntity) && minecraftClient.player.distanceTo((Entity) abstractClientPlayerEntity) <= this.pusher.get1965() && !BaritoneHelper_3.searchHelper4_14.is520((PlayerEntity) abstractClientPlayerEntity);
        }).filter(abstractClientPlayerEntity2 -> {
            return !this.pusher.ignoreNaked.getValue().booleanValue() || HoleSnapSearchHelper4.is2013((LivingEntity) abstractClientPlayerEntity2);
        }).filter((v1) -> {
            return is1525(v1);
        }).min(Comparator.comparing(abstractClientPlayerEntity3 -> {
            return Float.valueOf(MathHelper.angleBetween(minecraftClient.player.getYaw(), SearchHelper4_8.getFloatArray2483((Entity) abstractClientPlayerEntity3)[0]));
        })).orElse(null);
    }

    public boolean is1525(PlayerEntity playerEntity) {
        BlockPos blockPos = playerEntity.getBlockPos();
        BlockState blockState = minecraftClient.world.getBlockState(blockPos.up());
        if (list.contains(blockState.getBlock())) {
            return false;
        }
        if (blockState.getHardness(minecraftClient.world, blockPos.up()) == -1.0f) {
            return false;
        }
        if (BaritoneHelper_3.stashFinderSearchHelper4.is1557(blockPos.up(2))) {
            return false;
        }
        if (this.pusher.onlyHole.getValue().booleanValue() && !BaritoneHelper_3.holeSnapSearchHelper4_5.is2723(blockPos)) {
            return false;
        }
        if (this.pusher.onlySafe.getValue().booleanValue() && !BaritoneHelper_3.holeSnapSearchHelper4_5.is2723(blockPos)) {
            if (minecraftClient.world.getBlockState(playerEntity.getBlockPos()).getBlock().getBlastResistance() < 600.0f) {
                return false;
            }
        }
        return HoleSnapSearchHelper4.getList2010((LivingEntity) playerEntity).size() <= 6;
    }
}
