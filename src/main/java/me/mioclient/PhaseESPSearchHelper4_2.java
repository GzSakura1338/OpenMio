package me.mioclient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.mioclient.feature.Stopwatch;
import me.mioclient.mixin.ducks.DuckAbstractBlock;
import me.mioclient.mixin.ducks.DuckLivingEntity;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.misc.KillEffects;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.EggItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.SnowballItem;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.item.TridentItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PhaseESPSearchHelper4_2.class */
public class PhaseESPSearchHelper4_2 implements SearchHelper_4 {
    public static final Stopwatch stopwatch = new Stopwatch();
    public static boolean flag = false;
    public static final Direction[] directionArr = {Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};
    public static AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static KillEffects killEffects = (KillEffects) BaritoneHelper_3.baritoneHelper_4.getModule117(KillEffects.class);

    public static Direction getDirection3028(BlockPos blockPos) {
        return getDirection3029(blockPos, false);
    }

    public static Direction getDirection3029(BlockPos blockPos, boolean z) {
        return getDirection3030(blockPos, z, false);
    }

    public static Direction getDirection3030(BlockPos blockPos, boolean z, boolean z2) {
        for (Direction direction : directionArr) {
            if (blockPos.add(direction.getVector()).getY() < minecraftClient.world.getTopY()) {
                BlockPos offset = blockPos.offset(direction);
                if (z2) {
                    if (!SearchHelper4_7.is2432(AutoCraftMode.X8.getList900(offset, direction.getOpposite()))) {
                        continue;
                    }
                }
                if (BaritoneHelper_3.stashFinderSearchHelper4.is1557(offset)) {
                    if (z) {
                        if (!getList3031(offset).contains(direction.getOpposite())) {
                        }
                    }
                    return direction;
                }
                continue;
            }
        }
        return null;
    }

    public static List<Direction> getList3031(BlockPos blockPos) {
        ArrayList arrayList = new ArrayList(List.of((Object[]) Direction.values()));
        for (Direction direction : Direction.values()) {
            BlockPos offset = blockPos.offset(direction);
            BlockState blockState = minecraftClient.world.getBlockState(offset);
            if (!blockState.isReplaceable() && blockState.isOpaqueFullCube(minecraftClient.world, offset)) {
                arrayList.remove(direction);
            }
        }
        if (blockPos.getY() + 1 >= minecraftClient.world.getTopY()) {
            arrayList.remove(Direction.UP);
        }
        double d = minecraftClient.player.getEyePos().x - blockPos.toCenterPos().x;
        double d2 = minecraftClient.player.getEyePos().z - blockPos.toCenterPos().z;
        if (d > FreecamHelper.val2) {
            arrayList.remove(Direction.WEST);
        } else if (d < (-FreecamHelper.val2)) {
            arrayList.remove(Direction.EAST);
        } else {
            arrayList.remove(Direction.WEST);
            arrayList.remove(Direction.EAST);
        }
        if (d2 > FreecamHelper.val2) {
            arrayList.remove(Direction.NORTH);
        } else if (d2 < (-FreecamHelper.val2)) {
            arrayList.remove(Direction.SOUTH);
        } else {
            arrayList.remove(Direction.NORTH);
            arrayList.remove(Direction.SOUTH);
        }
        if (minecraftClient.player.getEyePos().y < blockPos.getY() + (antiCheat.strictDirection.getValue() == Mode_6.GRIM ? 1 : 0)) {
            arrayList.remove(Direction.UP);
        }
        if (minecraftClient.player.getEyePos().y > blockPos.getY()) {
            arrayList.remove(Direction.DOWN);
        }
        return arrayList;
    }

    public static BlockPos getBlockPos3032(BlockPos blockPos, int i, boolean z, boolean z2) {
        return getBlockPos3033(blockPos, i, z, z2, blockPos2 -> {
            return true;
        });
    }

    public static BlockPos getBlockPos3033(BlockPos blockPos, int i, boolean z, boolean z2, java.util.function.Predicate<BlockPos> predicate) {
        for (int i2 = 1; i2 <= i; i2++) {
            for (Direction direction : directionArr) {
                BlockPos offset = blockPos.offset(direction, i2);
                if (predicate.test(offset) && getDirection3029(offset, z2) != null) {
                    if (minecraftClient.world.getEntitiesByClass(PlayerEntity.class, new Box(offset), playerEntity -> {
                        return z;
                    }).isEmpty()) {
                        return offset;
                    }
                }
            }
        }
        return null;
    }

    public static boolean is3034(BlockPos blockPos, boolean z) {
        return is3035(blockPos, z, Hand.MAIN_HAND);
    }

    public static boolean is3035(BlockPos blockPos, boolean z, Hand hand) {
        return is3036(blockPos, (Vec3d) null, getDirection3028(blockPos), z, hand);
    }

    public static boolean is3036(BlockPos blockPos, Vec3d vec3d, Direction direction, boolean z, Hand hand) {
        if (minecraftClient.world == null || minecraftClient.player == null || minecraftClient.interactionManager == null) {
            return false;
        }
        BlockItem item = (minecraftClient.player.getStackInHand(hand).getItem()) instanceof BlockItem ? (BlockItem) (minecraftClient.player.getStackInHand(hand).getItem()) : null;
        if (!is3039(blockPos, item instanceof BlockItem ? item.getBlock() : null, false)) {
            return false;
        }
        boolean z2 = z && direction == null;
        if (direction == null) {
            if (!z) {
                return false;
            }
            BlockHitResult blockHitResult = (minecraftClient.crosshairTarget) instanceof BlockHitResult ? (BlockHitResult) (minecraftClient.crosshairTarget) : null;
            direction = blockHitResult instanceof BlockHitResult ? blockHitResult.getSide() : Direction.DOWN;
        }
        BlockPos offset = z2 ? blockPos : blockPos.offset(direction);
        boolean is3051 = is3051(offset);
        if (vec3d == null) {
            vec3d = blockPos.toCenterPos().add(Vec3d.of(direction.getVector()).multiply(FreecamHelper.val2));
        }
        if (is3051) {
            minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
        }
        flag = true;
        ActionResult interactBlock = minecraftClient.interactionManager.interactBlock(minecraftClient.player, hand, new BlockHitResult(vec3d, z2 ? direction : direction.getOpposite(), offset, false));
        flag = false;
        if (interactBlock.shouldSwingHand()) {
            minecraftClient.player.swingHand(hand);
        }
        if (!is3051) {
            return true;
        }
        minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
        return true;
    }

    public static boolean is3037(BlockPos blockPos, Direction direction, boolean z, Hand hand) {
        return is3038(blockPos, (Vec3d) null, direction, z, hand);
    }

    public static boolean is3038(BlockPos blockPos, Vec3d vec3d, Direction direction, boolean z, Hand hand) {
        if (minecraftClient.world == null || minecraftClient.player == null || minecraftClient.interactionManager == null) {
            return false;
        }
        BlockItem item = (minecraftClient.player.getStackInHand(hand).getItem()) instanceof BlockItem ? (BlockItem) (minecraftClient.player.getStackInHand(hand).getItem()) : null;
        if (!is3039(blockPos, item instanceof BlockItem ? item.getBlock() : null, false)) {
            return false;
        }
        boolean z2 = z && direction == null;
        if (direction == null) {
            List<Direction> list3031 = getList3031(blockPos);
            if (!z) {
                return false;
            }
            direction = list3031.isEmpty() ? Direction.DOWN : (Direction) list3031.getFirst();
        }
        BlockPos offset = z2 ? blockPos : blockPos.offset(direction);
        boolean is3051 = is3051(offset);
        if (is3051) {
            minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
        }
        if (vec3d == null) {
            vec3d = blockPos.toCenterPos().add(Vec3d.of(direction.getVector()).multiply(FreecamHelper.val2));
        }
        AutoSignSearchHelper4.do2556(hand, new BlockHitResult(vec3d, z2 ? direction : direction.getOpposite(), offset, false));
        if (!antiCheat.is238() || !antiCheat.movementSync.getValue().booleanValue()) {
            minecraftClient.player.swingHand(hand);
        } else if (!minecraftClient.player.handSwinging || minecraftClient.player.handSwingTicks >= ((DuckLivingEntity) minecraftClient.player).getSwingDuration() / 2 || minecraftClient.player.handSwingTicks < 0) {
            minecraftClient.player.handSwingTicks = -1;
            minecraftClient.player.handSwinging = true;
            minecraftClient.player.preferredHand = hand;
        }
        if (!is3051) {
            return true;
        }
        minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
        return true;
    }

    public static boolean is3039(BlockPos blockPos, Block block, boolean z) {
        return is3040(blockPos, block, z, false);
    }

    public static boolean is3040(BlockPos blockPos, Block block, boolean z, boolean z2) {
        if (!minecraftClient.world.getBlockState(blockPos).isReplaceable() || !minecraftClient.world.getWorldBorder().contains(blockPos) || !World.isValid(blockPos) || blockPos.getY() >= minecraftClient.world.getTopY() || blockPos.getY() < minecraftClient.world.getBottomY()) {
            return false;
        }
        VoxelShape empty = block == null ? VoxelShapes.empty() : block.getDefaultState().getCollisionShape(minecraftClient.world, BlockPos.ORIGIN);
        Box box = empty.isEmpty() ? new Box(blockPos) : SearchHelper.getBox226(empty.getBoundingBox(), blockPos);
        if (block != null && !((DuckAbstractBlock) block).isCollidable()) {
            return true;
        }
        for (Entity clientPlayerEntity : minecraftClient.world.getEntitiesByClass(Entity.class, box, entity -> {
            return ((entity instanceof ExperienceBottleEntity) || (entity instanceof ItemEntity) || (entity instanceof ItemFrameEntity) || (entity instanceof ExperienceOrbEntity)) ? false : true;
        })) {
            if (SearchHelper.is235(box, ((Entity) clientPlayerEntity).getBoundingBox()) || clientPlayerEntity == minecraftClient.player) {
                if (clientPlayerEntity instanceof ArrowEntity) {
                    continue;
                } else if (clientPlayerEntity instanceof PlayerEntity) {
                    if (((Entity) clientPlayerEntity).getBoundingBox().intersects(box)) {
                        return false;
                    }
                } else if (!(clientPlayerEntity instanceof EndCrystalEntity) || !z2) {
                    return !z;
                }
            }
        }
        return true;
    }

    public static boolean is3041(BlockPos blockPos, boolean z) {
        return is3042(blockPos, z, false);
    }

    public static boolean is3042(BlockPos blockPos, boolean z, boolean z2) {
        if (!minecraftClient.world.getBlockState(blockPos).isReplaceable() || !minecraftClient.world.getWorldBorder().contains(blockPos)) {
            return false;
        }
        for (Entity clientPlayerEntity : minecraftClient.world.getEntitiesByClass(Entity.class, new Box(blockPos), entity -> {
            return ((entity instanceof ExperienceBottleEntity) || (entity instanceof ItemEntity) || (entity instanceof ExperienceOrbEntity)) ? false : true;
        })) {
            if (SearchHelper.is235(new Box(blockPos), ((Entity) clientPlayerEntity).getBoundingBox()) || clientPlayerEntity == minecraftClient.player) {
                if (!(clientPlayerEntity instanceof EndCrystalEntity) || !z2) {
                    if (!(clientPlayerEntity instanceof ArrowEntity) && ((clientPlayerEntity instanceof PlayerEntity) || z)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean is3043(BlockPos blockPos, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        if (!SearchHelper4_7.is2445(blockPos)) {
            return false;
        }
        BlockPos blockPos2 = blockPos.add(0, 1, 0);
        Block block = minecraftClient.world.getBlockState(blockPos2).getBlock();
        if (z4) {
            if (block != Blocks.AIR && block != Blocks.FIRE) {
                return false;
            }
        } else if (block != Blocks.AIR) {
            return false;
        }
        if (z) {
            if (minecraftClient.world.getBlockState(blockPos.add(0, 2, 0)).getBlock() != Blocks.AIR) {
                return false;
            }
        }
        Box box = new Box(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ(), blockPos2.getX() + 1, blockPos2.getY() + (z5 ? 0.9 : 2.0), blockPos2.getZ() + 1);
        int i = 0;
        Iterator it = minecraftClient.world.getEntities().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Entity clientPlayerEntity = (Entity) it.next();
            if (!((Entity) clientPlayerEntity).isRemoved() && ((Entity) clientPlayerEntity).isAlive()) {
                Box boundingBox = ((Entity) clientPlayerEntity).getBoundingBox();
                if ((clientPlayerEntity instanceof PlayerEntity) && clientPlayerEntity != minecraftClient.player && z6) {
                    boundingBox = boundingBox.expand(0.01);
                } else if ((clientPlayerEntity instanceof EndCrystalEntity) && !z3) {
                }
                if (boundingBox.intersects(box)) {
                    i = 0 + 1;
                    break;
                }
            }
        }
        return !z2 || i == 0;
    }

    public static Block getBlock3044(BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        return minecraftClient.world.getBlockState(blockPos).getBlock();
    }

    public static boolean is3045(BlockPos blockPos) {
        return ((DuckAbstractBlock) getBlock3044(blockPos)).isCollidable();
    }

    public static Entity getEntity3046(BlockPos blockPos, int i) {
        for (Entity entity : minecraftClient.world.getEntities()) {
            if (entity instanceof EndCrystalEntity) {
                if (entity.getBoundingBox().intersects(new Box(blockPos)) && entity.age >= i) {
                    do3047(entity);
                    return entity;
                }
            }
        }
        return null;
    }

    public static void do3047(Entity entity) {
        do3048(entity, false);
    }

    public static void do3048(Entity entity, boolean z) {
        if (BaritoneHelper_3.scaffoldHelper.is1112(entity.getId())) {
            if (killEffects.isToggled() && (entity instanceof PlayerEntity)) {
                killEffects.do3003((PlayerEntity) entity);
            }
            minecraftClient.player.resetLastAttackedTicks();
        }
    }

    public static void do3049(Entity entity, Hand hand) {
        minecraftClient.interactionManager.interactEntityAtLocation(minecraftClient.player, entity, new EntityHitResult(entity), hand);
        minecraftClient.interactionManager.interactEntity(minecraftClient.player, entity, hand);
        minecraftClient.player.swingHand(hand);
    }

    public static boolean is3050(Block block) {
        return (block instanceof CraftingTableBlock) || (block instanceof AnvilBlock) || (block instanceof ButtonBlock) || (block instanceof AbstractPressurePlateBlock) || (block instanceof BlockWithEntity) || (block instanceof BedBlock) || (block instanceof FenceGateBlock) || (block instanceof DoorBlock) || (block instanceof NoteBlock) || (block instanceof TrapdoorBlock);
    }

    public static boolean is3051(BlockPos blockPos) {
        BlockState blockState = minecraftClient.world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        return block == Blocks.RESPAWN_ANCHOR || blockState.hasBlockEntity() || (block instanceof BedBlock) || (block instanceof AbstractChestBlock) || (block instanceof DoorBlock) || (block instanceof TrapdoorBlock) || (block instanceof ButtonBlock) || (block instanceof ComposterBlock) || (block instanceof NoteBlock) || (block instanceof AnvilBlock) || (block instanceof AbstractFurnaceBlock) || (block instanceof CraftingTableBlock) || (block instanceof EnchantingTableBlock) || (block instanceof CakeBlock);
    }

    public Direction getDirection3052(Direction direction, int i) {
        return Direction.values()[2 + ((direction.getId() + i) % Direction.values().length)];
    }

    public static boolean is3053(Item item) {
        return (item instanceof EggItem) || (item instanceof SnowballItem) || (item instanceof EnderPearlItem) || (item instanceof ExperienceBottleItem) || (item instanceof ThrowablePotionItem) || (item instanceof TridentItem);
    }

    public static void do3054() {
        stopwatch.reset();
    }

    public static boolean is3055() {
        return !stopwatch.is419(50L);
    }
}
