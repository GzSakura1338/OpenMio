package me.mioclient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import me.mioclient.module.movement.ElytraFly;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HoleSnapSearchHelper4.class */
public class HoleSnapSearchHelper4 implements SearchHelper_4 {
    public static boolean is2005(PlayerEntity playerEntity) {
        return minecraftClient.world.getStatesInBox(playerEntity.getBoundingBox()).anyMatch(blockState -> {
            return blockState.isOf(Blocks.COBWEB);
        });
    }

    public static boolean is2006(PlayerEntity playerEntity) {
        return PhaseESPSearchHelper4_2.getBlock3044(BlockPos.ofFloored(playerEntity.getX(), playerEntity.getBoundingBox().minY, playerEntity.getZ())) == Blocks.WATER || playerEntity.isTouchingWater();
    }

    public static boolean is2007(PlayerEntity playerEntity) {
        return is2006(playerEntity) || playerEntity.isInLava();
    }

    public static BlockPos getBlockPos1333() {
        return getBlockPos2008(minecraftClient.player);
    }

    public static BlockPos getBlockPos2008(LivingEntity livingEntity) {
        return BlockPos.ofFloored(livingEntity.getX(), Math.round(livingEntity.getY()), livingEntity.getZ());
    }

    public static Direction getDirection2009() {
        double[] doubleArray2508 = HoleSnapSearchHelper4_3.getDoubleArray2508(minecraftClient.player.getYaw(), minecraftClient.player.input, 1.0);
        return Direction.fromRotation(Math.toDegrees(Math.atan2(doubleArray2508[1], doubleArray2508[0])) - FreecamHelper.num2);
    }

    public static List<BlockPos> getList2010(LivingEntity livingEntity) {
        ArrayList arrayList = new ArrayList();
        Set<BlockPos> set2011 = getSet2011(livingEntity);
        for (BlockPos blockPos : set2011) {
            for (Vec3i vec3i : BlockerPredicateMode.SURROUND.getVec3iArray2842()) {
                BlockPos add = blockPos.add(vec3i);
                if (!set2011.contains(add)) {
                    arrayList.add(add);
                }
            }
        }
        return arrayList;
    }

    public static Set<BlockPos> getSet2011(LivingEntity livingEntity) {
        BlockPos blockPos2008 = getBlockPos2008(livingEntity);
        HashSet hashSet = new HashSet();
        Box expand = livingEntity.getBoundingBox().expand(-SearchHelper.val);
        hashSet.add(BlockPos.ofFloored(expand.minX, blockPos2008.getY(), expand.minZ));
        hashSet.add(BlockPos.ofFloored(expand.minX, blockPos2008.getY(), expand.maxZ));
        hashSet.add(BlockPos.ofFloored(expand.maxX, blockPos2008.getY(), expand.minZ));
        hashSet.add(BlockPos.ofFloored(expand.maxX, blockPos2008.getY(), expand.maxZ));
        return hashSet;
    }

    public static boolean is2012() {
        return minecraftClient.player.getHungerManager().getFoodLevel() < 6;
    }

    public static boolean is2013(LivingEntity livingEntity) {
        Iterator it = livingEntity.getArmorItems().iterator();
        while (it.hasNext()) {
            if (!((ItemStack) it.next()).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean is2014(PlayerEntity playerEntity) {
        return playerEntity.getInventory().getArmorStack(EquipmentSlot.CHEST.getEntitySlotId()).isOf(Items.ELYTRA);
    }

    public static boolean is955() {
        if (ElytraFly.flag) {
            return minecraftClient.player.isFallFlying();
        }
        ElytraFly.flag = true;
        boolean isFallFlying = minecraftClient.player.isFallFlying();
        ElytraFly.flag = false;
        return isFallFlying;
    }

    public static Hand getHand2015(Hand hand) {
        return hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
    }
}
