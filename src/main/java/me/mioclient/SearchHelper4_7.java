package me.mioclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
import me.mioclient.HoleSnapHelper_2;
import me.mioclient.feature.IllegalConstructorCall;
import me.mioclient.module.combat.AutoCrystal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.vehicle.VehicleEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SearchHelper4_7.class */
public class SearchHelper4_7 implements SearchHelper_4 {
    public static final AutoCrystal autoCrystal = (AutoCrystal) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoCrystal.class);
    public static final List<Block> list = List.of(Blocks.BLACKSTONE, Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.GILDED_BLACKSTONE);
    public static final List<Block> list2 = List.of(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES, Blocks.DEEPSLATE, Blocks.CHISELED_DEEPSLATE, Blocks.SCULK);
    public static final List<Block> list3 = List.of(Blocks.WAXED_OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.WAXED_COPPER_BLOCK, Blocks.WAXED_OXIDIZED_COPPER_GRATE);

    public static List<WorldChunk> getList2426() {
        ArrayList arrayList = new ArrayList();
        int intValue = ((Integer) minecraftClient.options.getViewDistance().getValue()).intValue();
        for (int i = -intValue; i <= intValue; i++) {
            for (int i2 = -intValue; i2 <= intValue; i2++) {
                WorldChunk worldChunk = minecraftClient.world.getChunkManager().getWorldChunk((((int) minecraftClient.player.getX()) / 16) + i, (((int) minecraftClient.player.getZ()) / 16) + i2);
                if (worldChunk != null) {
                    arrayList.add(worldChunk);
                }
            }
        }
        return arrayList;
    }

    public static List<BlockEntity> getList2427() {
        ArrayList arrayList = new ArrayList();
        for (WorldChunk worldChunk : getList2426()) {
            if (!worldChunk.getBlockEntities().isEmpty()) {
                try {
                    arrayList.addAll(new ArrayList(worldChunk.getBlockEntities().values()));
                } catch (Throwable th) {
                    autoCrystal.do1162();
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x01dc, code lost:
    
        if (r0 == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x01e1, code lost:
    
        if (r15 != false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x01e4, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean is2428(Chunk chunk, BlockPos blockPos, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        BlockState blockState = chunk.getBlockState(blockPos);
        if (!blockState.isOf(Blocks.CHEST)) {
            return false;
        }
        BlockState blockState2 = chunk.getBlockState(blockPos.down());
        boolean z6 = blockState.get(ChestBlock.CHEST_TYPE) == ChestType.SINGLE;
        if (getStashFinderMode2438() == StashFinderMode.THE_NETHER) {
            if (z && z6 && blockState2.isOf(Blocks.NETHER_BRICKS)) {
                return true;
            }
            if (z2) {
                if (list.contains(blockState2.getBlock())) {
                    return true;
                }
            }
            return false;
        }
        if (getStashFinderMode2438() != StashFinderMode.OVERWORLD || !z6) {
            return false;
        }
        if (z5 && blockPos.getY() <= 34) {
            boolean isOf = blockState2.isOf(Blocks.CHISELED_TUFF_BRICKS);
            if (list3.contains(blockState2.getBlock()) || isOf) {
                boolean z7 = false;
                Direction[] values = Direction.values();
                int length = values.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Direction direction = values[i];
                    if (direction.getAxis().isHorizontal()) {
                        BlockState blockState3 = chunk.getBlockState(blockPos.offset(direction));
                        if (!isOf) {
                            if (list3.contains(blockState3.getBlock())) {
                                return true;
                            }
                        } else if (!blockState3.isOf(Blocks.AIR)) {
                            z7 = true;
                            break;
                        }
                    }
                    i++;
                }
            }
        }
        if (z3 && blockPos.getY() <= -32 && blockPos.getY() >= -51) {
            if (list2.contains(blockState2.getBlock())) {
                return true;
            }
        }
        return z4 && (blockState2.isOf(Blocks.MOSSY_COBBLESTONE) || blockState2.isOf(Blocks.COBBLESTONE));
    }

    public static List<BlockPos> getList2429(Vec3d vec3d, float f, boolean z) {
        ArrayList arrayList = new ArrayList();
        BlockPos ofFloored = BlockPos.ofFloored((Position) vec3d);
        float f2 = -f;
        while (true) {
            float f3 = f2;
            if (f3 >= f) {
                return arrayList;
            }
            float f4 = -f;
            while (true) {
                float f5 = f4;
                if (f5 < f) {
                    float f6 = -f;
                    while (true) {
                        float f7 = f6;
                        if (f7 < f) {
                            BlockPos add = ofFloored.add((int) f3, (int) f5, (int) f7);
                            if (ofFloored.isWithinDistance((Vec3i) add, f) && (!z || !minecraftClient.world.getBlockState(add).isAir())) {
                                arrayList.add(add);
                            }
                            f6 = f7 + 1.0f;
                        } else {
                            break;
                        }
                    }
                    f4 = f5 + 1.0f;
                } else {
                    break;
                }
            }
            f2 = f3 + 1.0f;
        }
    }

    public static Vec3d getVec3d2430(BlockPos blockPos, AutoCraftMode autoCraftMode) {
        for (Vec3d vec3d : autoCraftMode.getList899(blockPos)) {
            if (is2433(vec3d)) {
                return vec3d;
            }
        }
        return null;
    }

    public static boolean is2431(Vec3i vec3i) {
        return is2433(Vec3d.ofCenter(vec3i));
    }

    public static boolean is2432(Collection<Vec3d> collection) {
        Iterator<Vec3d> it = collection.iterator();
        while (it.hasNext()) {
            if (is2433(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean is2433(Vec3d vec3d) {
        Vec3d eyePos = minecraftClient.player.getEyePos();
        BlockPos ofFloored = BlockPos.ofFloored((Position) vec3d);
        if (vec3d.distanceTo(eyePos) > 128.0) {
            return false;
        }
        BlockHitResult blockHitResult2784 = HoleSnapSearchHelper4_6.getBlockHitResult2784(new HoleSnapHelper_2.Inner(minecraftClient.player.getEyePos(), vec3d).getInner1604(HoleSnapHelper.getHoleSnapHelper1674(ofFloored)).getHoleSnapHelper_21606());
        return ((HitResult) blockHitResult2784).getType() == HitResult.Type.MISS || ((HitResult) blockHitResult2784).getPos().equals(vec3d) || ((blockHitResult2784 instanceof BlockHitResult) && blockHitResult2784.getBlockPos().equals(ofFloored));
    }

    public static boolean is2434(Vec3d vec3d, Vec3d vec3d2) {
        Vec3d eyePos = minecraftClient.player.getEyePos();
        if (vec3d.distanceTo(eyePos) > 128.0) {
            return false;
        }
        if (minecraftClient.world.raycast(new RaycastContext(eyePos, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, minecraftClient.player)).getType() == HitResult.Type.MISS) {
            return true;
        }
        if (minecraftClient.player.getY() >= vec3d.y) {
            return minecraftClient.world.raycast(new RaycastContext(eyePos, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, minecraftClient.player)).getType() == HitResult.Type.MISS;
        }
        return false;
    }

    public static boolean is2435(BlockPos blockPos) {
        if (blockPos == null) {
            return false;
        }
        BlockState blockState = minecraftClient.world.getBlockState(blockPos);
        return ((!minecraftClient.player.isCreative() && blockState.getHardness(minecraftClient.world, blockPos) < 0.0f) || blockState.isAir() || blockState.getOutlineShape(minecraftClient.world, blockPos) == VoxelShapes.empty()) ? false : true;
    }

    public static double get2436(ItemStack itemStack, BlockState blockState, boolean z) {
        float hardness = blockState.getHardness((BlockView) null, (BlockPos) null);
        if (hardness == -1.0f) {
            return 0.0d;
        }
        return (get2437(itemStack, blockState, z) / hardness) / ((!blockState.isToolRequired() || itemStack.isSuitableFor(blockState)) ? 30 : 100);
    }

    public static double get2437(ItemStack itemStack, BlockState blockState, boolean z) {
        float intBitsToFloat;
        int i;
        double miningSpeedMultiplier = itemStack.getMiningSpeedMultiplier(blockState);
        if (miningSpeedMultiplier > 1.0 && (i = IllegalConstructorCall.get1413(Enchantments.EFFICIENCY, itemStack)) > 0 && !itemStack.isEmpty()) {
            miningSpeedMultiplier += (i * i) + 1;
        }
        if (StatusEffectUtil.hasHaste(minecraftClient.player)) {
            miningSpeedMultiplier *= 1.0f + ((StatusEffectUtil.getHasteAmplifier(minecraftClient.player) + 1) * 0.20000000298023224f);
        }
        if (minecraftClient.player.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
            switch (minecraftClient.player.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) {
                case 0:
                    intBitsToFloat = 0.30000001192092896f;
                    break;
                case 1:
                    intBitsToFloat = 0.09000000357627869f;
                    break;
                case 2:
                    intBitsToFloat = 0.0027000000700354576f;
                    break;
                default:
                    intBitsToFloat = 0.0008099999977275729f;
                    break;
            }
            miningSpeedMultiplier *= intBitsToFloat;
        }
        if (minecraftClient.player.isSubmergedIn(FluidTags.WATER)) {
            if (!IllegalConstructorCall.is1418(Enchantments.AQUA_AFFINITY, EquipmentSlot.HEAD)) {
                miningSpeedMultiplier /= 5.0;
            }
        }
        if (!z) {
            miningSpeedMultiplier /= 5.0;
        }
        return miningSpeedMultiplier;
    }

    public static StashFinderMode getStashFinderMode2438() {
        if (minecraftClient.world == null) {
            return StashFinderMode.OVERWORLD;
        }
        String path = minecraftClient.world.getRegistryKey().getValue().getPath();
        int z = -1;
        switch (path.hashCode()) {
            case -1350117363:
                if (path.equals("the_end")) {
                    z = 1;
                    break;
                }
                break;
            case 1272296422:
                if (path.equals("the_nether")) {
                    z = 0;
                    break;
                }
                break;
        }
        switch (z) {
            case 0:
                return StashFinderMode.THE_NETHER;
            case 1:
                return StashFinderMode.THE_END;
            default:
                return StashFinderMode.OVERWORLD;
        }
    }

    public static Entity getEntity2439(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, float f, float f2) {
        return getEntity2440(z, z2, z3, z4, z5, z6, f, f2, false, false);
    }

    public static Entity getEntity2440(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, float f, float f2, boolean z7, boolean z8) {
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
        Objects.requireNonNull(clientPlayerEntity);
        return getEntity2441(z, z2, z3, z4, z5, z6, f, f2, false, false, Comparator.comparing(clientPlayerEntity::squaredDistanceTo), (java.util.function.Predicate) null);
    }

    public static Entity getEntity2441(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, float f, float f2, boolean z7, boolean z8, Comparator<Entity> comparator, java.util.function.Predicate<Entity> predicate) {
        Entity entity = (Entity) StreamSupport.stream(minecraftClient.world.getEntities().spliterator(), false).filter(entity2 -> {
            return !(entity2 instanceof ExperienceBottleEntity) && is2442(entity2) && (((entity2 instanceof PlayerEntity) && z && (!z8 || HoleSnapSearchHelper4.is2013((PlayerEntity) entity2))) || ((SearchHelper_3.is646(entity2) && z3) || ((SearchHelper_3.is645(entity2) && z4) || (((entity2 instanceof PiglinEntity) && z2) || (((entity2 instanceof Angerable) && z2) || (((entity2 instanceof EndCrystalEntity) && z5) || ((entity2 instanceof ProjectileEntity) && z6 && entity2.isAttackable())))))));
        }).filter(entity3 -> {
            return get2443(entity3) <= ((double) (HoleSnapSearchHelper4_6.is2788(entity3) ? f2 : f));
        }).filter(entity4 -> {
            return predicate == null || predicate.test(entity4);
        }).min(comparator).orElse(null);
        if (entity != null && !(entity instanceof PlayerEntity) && entity.hasCustomName() && z7) {
            entity = null;
        }
        return entity;
    }

    public static boolean is2442(Entity entity) {
        if (entity == minecraftClient.player) {
            return false;
        }
        if ((entity instanceof PlayerEntity) && BaritoneHelper_3.searchHelper4_14.is520((PlayerEntity) entity)) {
            return false;
        }
        return entity.isAlive();
    }

    public static double get2443(Entity entity) {
        Box boundingBox = entity.getBoundingBox();
        Vec3d add = SearchHelper.getBox234(minecraftClient.player).getBottomCenter().add(0.0d, minecraftClient.player.getStandingEyeHeight(), 0.0d);
        if (!(entity instanceof EnderDragonEntity)) {
            return get2444(add, boundingBox);
        }
        double longBitsToDouble = 9999.0;
        for (EnderDragonPart enderDragonPart : ((EnderDragonEntity) entity).getBodyParts()) {
            longBitsToDouble = Math.min(longBitsToDouble, get2444(add, enderDragonPart.getBoundingBox()));
        }
        return longBitsToDouble;
    }

    public static double get2444(Vec3d vec3d, Box box) {
        return vec3d.distanceTo(SearchHelper.getVec3d232(vec3d, box));
    }

    public static boolean is2445(BlockPos blockPos) {
        return minecraftClient.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || minecraftClient.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN;
    }

    public static boolean is2446(BlockPos blockPos) {
        return minecraftClient.world.getBlockState(blockPos).isAir();
    }

    public static boolean is2447(BlockPos blockPos) {
        return !minecraftClient.world.getFluidState(blockPos).isEmpty();
    }

    public static boolean is2448(BlockPos blockPos) {
        return BaritoneHelper_3.stashFinderSearchHelper4.is1557(blockPos);
    }

    public static Block getBlock2449(BlockPos blockPos) {
        return minecraftClient.world.getBlockState(blockPos).getBlock();
    }

    public static boolean is2450(HitResult hitResult) {
        if (hitResult == null) {
            return false;
        }
        if (hitResult instanceof BlockHitResult) {
            BlockState blockState = minecraftClient.world.getBlockState(((BlockHitResult) hitResult).getBlockPos());
            if (blockState.hasBlockEntity() || PhaseESPSearchHelper4_2.is3050(blockState.getBlock())) {
                return true;
            }
        }
        if (!(hitResult instanceof EntityHitResult)) {
            return false;
        }
        Entity entity = ((EntityHitResult) hitResult).getEntity();
        return (entity instanceof VehicleEntity) || (entity instanceof VillagerEntity);
    }
}
