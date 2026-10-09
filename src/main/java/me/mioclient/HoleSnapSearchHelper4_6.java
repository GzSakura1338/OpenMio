package me.mioclient;

import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HoleSnapSearchHelper4_6.class */
public class HoleSnapSearchHelper4_6 implements SearchHelper_4 {
    public static BlockHitResult getBlockHitResult2784(HoleSnapHelper_2 holeSnapHelper_2) {
        return (BlockHitResult) BlockView.raycast(holeSnapHelper_2.getVec3d2218(), holeSnapHelper_2.getVec3d2219(), holeSnapHelper_2, (holeSnapHelper_22, blockPos) -> {
            BlockState blockState = minecraftClient.world.getBlockState(blockPos);
            ReachMode test = holeSnapHelper_2.getHoleSnapHelper2223().test(blockPos, blockState);
            if (test == ReachMode.IGNORE) {
                blockState = Blocks.AIR.getDefaultState();
            } else if (test == ReachMode.SOLID) {
                blockState = Blocks.BEDROCK.getDefaultState();
            }
            Vec3d vec3d2218 = holeSnapHelper_22.getVec3d2218();
            Vec3d vec3d2219 = holeSnapHelper_22.getVec3d2219();
            BlockHitResult raycastBlock = minecraftClient.world.raycastBlock(vec3d2218, vec3d2219, blockPos, holeSnapHelper_22.getVoxelShape2224(blockState, minecraftClient.world, blockPos), blockState);
            BlockHitResult raycast = VoxelShapes.empty().raycast(vec3d2218, vec3d2219, blockPos);
            return (raycastBlock == null ? 1.7976931348623157e+308 : holeSnapHelper_22.getVec3d2218().squaredDistanceTo(raycastBlock.getPos())) <= (raycast == null ? 1.7976931348623157e+308 : holeSnapHelper_22.getVec3d2219().squaredDistanceTo(raycast.getPos())) ? raycastBlock : raycast;
        }, holeSnapHelper_23 -> {
            Vec3d subtract = holeSnapHelper_23.getVec3d2218().subtract(holeSnapHelper_23.getVec3d2219());
            return BlockHitResult.createMissed(holeSnapHelper_23.getVec3d2218(), Direction.getFacing(subtract.x, subtract.y, subtract.z), BlockPos.ofFloored(holeSnapHelper_23.getVec3d2219()));
        });
    }

    public static HitResult getHitResult2785(Vec3d vec3d, float f, float f2, float f3) {
        float f4 = f2 * FreecamHelper.val4;
        float f5 = (-f) * FreecamHelper.val4;
        float cos = MathHelper.cos(f5);
        float sin = MathHelper.sin(f5);
        float cos2 = MathHelper.cos(f4);
        Vec3d vec3d2 = new Vec3d(sin * cos2, -MathHelper.sin(f4), cos * cos2);
        return minecraftClient.world.raycast(new RaycastContext(vec3d, vec3d.add(vec3d2.x * f3, vec3d2.y * f3, vec3d2.z * f3), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, minecraftClient.player));
    }

    public static int get2786() {
        return (int) Math.round(minecraftClient.player.getY() - minecraftClient.world.raycast(new RaycastContext(minecraftClient.player.getPos(), new Vec3d(minecraftClient.player.getX(), minecraftClient.world.getBottomY(), minecraftClient.player.getZ()), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, minecraftClient.player)).getPos().y);
    }

    public static int get2787() {
        return (minecraftClient.world.raycast(new RaycastContext(minecraftClient.player.getPos(), new Vec3d(minecraftClient.player.getX(), minecraftClient.world.getTopY(), minecraftClient.player.getZ()), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, minecraftClient.player)).getBlockPos().getY() - (((int) Math.round(minecraftClient.player.getY())) + 1)) - 1;
    }

    public static boolean is2788(Entity entity) {
        Box boundingBox = entity.getBoundingBox();
        float f = 0.0f;
        while (true) {
            float f2 = f;
            if (f2 > 1.0f) {
                return false;
            }
            List<Vec3d> list228 = SearchHelper.getList228(boundingBox, boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * f2));
            for (int i = 0; i < list228.size() - 1; i++) {
                if (is2789(minecraftClient.player.getEyePos(), list228.get(i))) {
                    return true;
                }
            }
            f = f2 + 0.5f;
        }
    }

    public static boolean is2789(Vec3d vec3d, Vec3d vec3d2) {
        return minecraftClient.world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, minecraftClient.player)).getType() == HitResult.Type.MISS;
    }
}
