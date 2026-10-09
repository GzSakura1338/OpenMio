package me.mioclient;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AntiPhaseHelper3_2.class */
public class AntiPhaseHelper3_2 extends AntiPhaseHelper3 {
    public static final List<AntiPhaseHelperData> list = List.of(AntiPhaseHelperData.getAntiPhaseHelperData1239(Direction.EAST, Block.createCuboidShape(0.0d, 2.0, 2.0, 1.0, 14.0, 14.0)), AntiPhaseHelperData.getAntiPhaseHelperData1239(Direction.WEST, Block.createCuboidShape(15.0, 2.0, 2.0, 16.0, 14.0, 14.0)), AntiPhaseHelperData.getAntiPhaseHelperData1239(Direction.NORTH, Block.createCuboidShape(2.0, 2.0, 0.0d, 14.0, 14.0, 1.0)), AntiPhaseHelperData.getAntiPhaseHelperData1239(Direction.SOUTH, Block.createCuboidShape(2.0, 2.0, 15.0, 14.0, 14.0, 16.0)), AntiPhaseHelperData.getAntiPhaseHelperData1239(Direction.DOWN, Block.createCuboidShape(2.0, 0.0d, 2.0, 14.0, 1.0, 14.0)), AntiPhaseHelperData.getAntiPhaseHelperData1239(Direction.UP, Block.createCuboidShape(2.0, 15.0, 2.0, 14.0, 16.0, 14.0)));

    @Override // me.mioclient.AntiPhaseHelper3
    public Box getBox2417(BlockPos blockPos, Direction direction) {
        for (AntiPhaseHelperData antiPhaseHelperData : list) {
            if (antiPhaseHelperData.getDirection842() == direction.getOpposite()) {
                return antiPhaseHelperData.getBox799().offset(blockPos);
            }
        }
        return new Box(blockPos);
    }
}
