package me.mioclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import me.mioclient.HoleSnapData;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HoleSnapSearchHelper4_5.class */
public final class HoleSnapSearchHelper4_5 implements SearchHelper_4 {
    public List<HoleSnapData> list = new ArrayList();
    public boolean flag;
    public Future<?> future;

    public HoleSnapSearchHelper4_5() {
        baritoneHelper.do1796(this);
        this.future = executorService.submit(new Runnable(this));
    }

    public boolean is2723(BlockPos blockPos) {
        return this.list.stream().anyMatch(holeSnapData -> {
            return holeSnapData.getBox799().intersects(new Box(blockPos));
        });
    }

    public static HoleSnapData getHoleSnapData2724(BlockPos blockPos, Direction direction) {
        HoleSnapData holeSnapData2724;
        if (is2725(blockPos) || !is2725(blockPos.down()) || is2725(blockPos.up())) {
            return null;
        }
        if (minecraftClient.world.getBlockState(blockPos.down()).isOf(Blocks.END_PORTAL)) {
            return null;
        }
        boolean is2725 = is2725(blockPos.up(2));
        HoleSnapData.HoleSnapDataMode holeSnapDataMode = HoleSnapData.HoleSnapDataMode.SAFE;
        Direction direction2 = null;
        for (Direction direction3 : Direction.values()) {
            if (direction != direction3.getOpposite() && direction3 != Direction.UP) {
                BlockPos offset = blockPos.offset(direction3);
                Block block = minecraftClient.world.getBlockState(offset).getBlock();
                if (is2725(offset)) {
                    if (block == Blocks.RESPAWN_ANCHOR) {
                        return null;
                    }
                    if ((block.getBlastResistance() < 600.0f && block.getBlastResistance() >= 0.0f) || block.getHardness() == 0.0f) {
                        return null;
                    }
                    if (block.getBlastResistance() >= 600.0f && block.getHardness() >= 0.0f) {
                        holeSnapDataMode = HoleSnapData.HoleSnapDataMode.UNSAFE;
                    }
                } else {
                    if (direction2 != null || direction != null || (holeSnapData2724 = getHoleSnapData2724(blockPos.offset(direction3), direction3)) == null) {
                        return null;
                    }
                    if (!holeSnapData2724.is2171()) {
                        is2725 = false;
                    }
                    if (holeSnapDataMode == HoleSnapData.HoleSnapDataMode.SAFE) {
                        holeSnapDataMode = holeSnapData2724.getHoleSnapDataMode2170();
                    }
                    direction2 = direction3;
                }
            }
        }
        return new HoleSnapData(holeSnapDataMode, blockPos.toImmutable(), direction2 == null ? new Box(blockPos) : new Box(blockPos).stretch(direction2.getVector().getX(), 0.0d, direction2.getVector().getZ()), is2725);
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (this.future.isDone() || this.future.isCancelled()) {
            this.future = executorService.submit(new Runnable(this));
        }
        this.flag = is2723(HoleSnapSearchHelper4.getBlockPos1333());
    }

    public static boolean is2725(BlockPos blockPos) {
        BlockState blockState = minecraftClient.world.getBlockState(blockPos);
        return (blockState.isAir() || blockState.isOf(Blocks.FIRE) || blockState.isOf(Blocks.SOUL_FIRE) || (blockState.getBlock() instanceof ButtonBlock) || (blockState.getBlock() instanceof TorchBlock) || (blockState.getBlock() instanceof RailBlock) || blockState.isOf(Blocks.LIGHT)) ? false : true;
    }

    public List<HoleSnapData> getList2726() {
        return this.list;
    }

    public void do2727(List<HoleSnapData> list) {
        this.list = list;
    }

    public boolean is2728() {
        return this.flag;
    }
}
