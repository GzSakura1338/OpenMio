package me.mioclient;

import java.util.List;
import me.mioclient.module.combat.AntiPhase;
import net.minecraft.block.LadderBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AntiPhaseHelper.class */
public class AntiPhaseHelper implements SearchHelper_4, AntiPhaseHelper_3 {
    public static final List<AntiPhaseHelperData> list = List.of(AntiPhaseHelperData.getAntiPhaseHelperData1239(Direction.EAST, LadderBlock.EAST_SHAPE), AntiPhaseHelperData.getAntiPhaseHelperData1239(Direction.WEST, LadderBlock.WEST_SHAPE), AntiPhaseHelperData.getAntiPhaseHelperData1239(Direction.NORTH, LadderBlock.NORTH_SHAPE), AntiPhaseHelperData.getAntiPhaseHelperData1239(Direction.SOUTH, LadderBlock.SOUTH_SHAPE));

    @Override // me.mioclient.AntiPhaseHelper_3
    public AntiPhaseData getAntiPhaseData844(AntiPhase antiPhase, PlayerEntity playerEntity) {
        BlockPos blockPos2008 = HoleSnapSearchHelper4.getBlockPos2008((LivingEntity) playerEntity);
        Direction direction = null;
        Box box = null;
        float intBitsToFloat = 999.0f;
        if (!SearchHelper4_7.is2446(blockPos2008)) {
            return null;
        }
        for (AntiPhaseHelperData antiPhaseHelperData : list) {
            Direction direction842 = antiPhaseHelperData.getDirection842();
            BlockPos offset = blockPos2008.offset(direction842);
            if (!is845(blockPos2008, playerEntity, antiPhaseHelperData) && (!antiPhase.strictDirection.getValue().booleanValue() || !is846(offset, direction842))) {
                float squaredDistanceTo = (float) playerEntity.getPos().squaredDistanceTo(offset.toCenterPos());
                if (squaredDistanceTo < intBitsToFloat) {
                    box = antiPhaseHelperData.getBox799();
                    direction = direction842;
                    intBitsToFloat = squaredDistanceTo;
                }
            }
        }
        if (direction == null) {
            return null;
        }
        return new AntiPhaseData(blockPos2008.offset(direction), direction.getOpposite(), box.offset(blockPos2008));
    }

    public boolean is845(BlockPos blockPos, PlayerEntity playerEntity, AntiPhaseHelperData antiPhaseHelperData) {
        return SearchHelper.getBox234((LivingEntity) playerEntity).intersects(antiPhaseHelperData.getBox799().offset(blockPos)) || minecraftClient.world.getBlockState(blockPos.offset(antiPhaseHelperData.getDirection842())).isReplaceable();
    }

    public boolean is846(BlockPos blockPos, Direction direction) {
        return !PhaseESPSearchHelper4_2.getList3031(blockPos).contains(direction.getOpposite());
    }
}
