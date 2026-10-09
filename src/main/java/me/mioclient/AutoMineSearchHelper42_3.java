package me.mioclient;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import me.mioclient.module.combat.AutoCrystal;
import me.mioclient.module.combat.AutoMine;
import me.mioclient.module.player.SpeedMine;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AutoMineSearchHelper42_3.class */
public final class AutoMineSearchHelper42_3 extends AutoMineSearchHelper4_2 {
    public static AutoCrystal autoCrystal = (AutoCrystal) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoCrystal.class);
    public static SpeedMine speedmine = (SpeedMine) BaritoneHelper_3.baritoneHelper_4.getModule117(SpeedMine.class);
    public static final List<Vec3i> list = List.of(new Vec3i(1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(0, 1, 0), new Vec3i(0, 0, -1), new Vec3i(-1, 0, 0));

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/AutoMineSearchHelper42_3$Record.class */
    public static final class Record {
        public final BlockPos blockPos;
        public final BlockPos blockPos2;

        public Record(BlockPos blockPos, BlockPos blockPos2) {
            this.blockPos = blockPos;
            this.blockPos2 = blockPos2;
        }




        public BlockPos getBlockPos2290() {
            return this.blockPos;
        }

        public BlockPos getBlockPos2291() {
            return this.blockPos2;
        }
    }

    public AutoMineSearchHelper42_3(AutoMine autoMine) {
        super(autoMine);
    }

    @Override // me.mioclient.AutoMineHelper_2
    public void do722(AutoMineHelper autoMineHelper) {
        LivingEntity playerEntity886 = this.autoMine.autoMineSearchHelper4.getPlayerEntity886();
        boolean z = this.autoMine.fish.getValue().booleanValue() && autoMineHelper.get888() == 400 && autoMineHelper.getBlockPos2898() != null;
        if (playerEntity886 == null || is2891((Entity) playerEntity886)) {
            return;
        }
        if (!is1210((PlayerEntity) playerEntity886) || z) {
            BlockPos ofFloored = BlockPos.ofFloored(((PlayerEntity) playerEntity886).getPos());
            List<BlockPos> list2010 = HoleSnapSearchHelper4.getList2010(playerEntity886);
            Block block = minecraftClient.world.getBlockState(ofFloored).getBlock();
            boolean z2 = block.getBlastResistance() >= 600.0f && block != Blocks.AIR;
            if (!this.autoMine.speedMineHelper.is1228(500L) || ofFloored.equals(this.autoMine.speedMineHelper.getBlockPos386())) {
                z2 = false;
            }
            Record record1207 = getRecord1207(list2010, z2, z);
            if (record1207.blockPos == null) {
                return;
            }
            autoMineHelper.do2901(600, autoMineHelper2 -> {
                if (autoMineHelper2.getBlockPos2898() == null) {
                    autoMineHelper2.do2899(record1207.blockPos2);
                }
                autoMineHelper2.do667(record1207.blockPos);
            });
        }
    }

    public Record getRecord1207(List<BlockPos> list2, boolean z, boolean z2) {
        BlockPos blockPos = null;
        BlockPos blockPos2 = null;
        boolean z3 = false;
        list2.sort(Comparator.comparing(blockPos3 -> {
            if (minecraftClient.player.getBoundingBox().intersects(new Box(blockPos3))) {
                return Double.valueOf(-9999.0);
            }
            if (HoleSnapSearchHelper4.getList2010(minecraftClient.player).contains(blockPos3)) {
                return Double.valueOf(-999.0);
            }
            boolean z4 = false;
            Iterator<Vec3i> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Vec3i next = it.next();
                if (next.getY() == 0) {
                    if (is1211(blockPos3.add(next).down())) {
                        z4 = true;
                        break;
                    }
                }
            }
            double squaredDistance = blockPos3.getSquaredDistance(minecraftClient.player.getPos());
            if (!z4) {
                squaredDistance += Math.pow(speedmine.range.getObject2326().floatValue(), 2.0);
            }
            return Double.valueOf(-squaredDistance);
        }));
        Iterator<BlockPos> it = list2.iterator();
        while (it.hasNext()) {
            BlockPos next = it.next();
            if (z2) {
                next = next.down();
            }
            boolean z4 = SearchHelper4_7.is2446(next) && next.equals(speedmine.getBlockPos1053());
            boolean z5 = this.autoMine.logic.getValue() == Mode_12.GRIM || this.autoMine.logic.getValue() == Mode_12.GRIMV3;
            if (z) {
                z4 = false;
            }
            if (speedmine.is1058(400L) && z5) {
                z4 = false;
            }
            if (is1208(next) || z2 || z5) {
                if (is1212(next) || z4) {
                    if (is2890(next)) {
                        blockPos = next;
                        z3 = true;
                    } else if (z3) {
                        blockPos2 = next;
                    } else {
                        blockPos2 = blockPos;
                        blockPos = next;
                    }
                }
            }
        }
        return new Record(blockPos, blockPos2);
    }

    public boolean is1208(BlockPos blockPos) {
        Iterator<Vec3i> it = list.iterator();
        while (it.hasNext()) {
            if (is1211(blockPos.add(it.next()).down())) {
                return true;
            }
        }
        return false;
    }

    public boolean is1209(BlockPos blockPos) {
        if (this.autoMine.safe.getValue().booleanValue()) {
            Iterator<BlockPos> it = HoleSnapSearchHelper4.getList2010(minecraftClient.player).iterator();
            while (it.hasNext()) {
                if (it.next().equals(blockPos)) {
                    return true;
                }
            }
        }
        List list2 = BlockPos.stream(minecraftClient.player.getBoundingBox().withMaxY(minecraftClient.player.getY())).map((v0) -> {
            return v0.toImmutable();
        }).filter(blockPos2 -> {
            return minecraftClient.world.getBlockState(blockPos2).getBlock().getBlastResistance() >= 600.0f;
        }).toList();
        return list2.size() == 1 && list2.contains(blockPos);
    }

    public boolean is1210(PlayerEntity playerEntity) {
        return BlockPos.stream(playerEntity.getBoundingBox().withMaxY(playerEntity.getY())).map((v0) -> {
            return v0.toImmutable();
        }).allMatch(blockPos -> {
            return minecraftClient.world.getBlockState(blockPos).isOf(Blocks.BEDROCK);
        });
    }

    public boolean is1211(BlockPos blockPos) {
        return PhaseESPSearchHelper4_2.is3043(blockPos, autoCrystal.f112.getValue().booleanValue(), true, false, false, autoCrystal.lowerHitBox.getValue().booleanValue(), autoCrystal.hBFix.getValue().booleanValue());
    }

    @Override // me.mioclient.AutoMineSearchHelper4_2, me.mioclient.AutoMineHelper_2
    public boolean is465() {
        return this.autoMine.feet.getValue().booleanValue();
    }

    @Override // me.mioclient.AutoMineSearchHelper4_2
    public boolean is1212(BlockPos blockPos) {
        if (is1209(blockPos) || SearchHelper4_7.getBlock2449(blockPos) == Blocks.COBWEB) {
            return false;
        }
        return super.is1212(blockPos);
    }
}
