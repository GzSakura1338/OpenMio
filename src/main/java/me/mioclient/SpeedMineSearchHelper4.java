package me.mioclient;

import java.awt.Color;
import me.mioclient.module.player.SpeedMine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SpeedMineSearchHelper4.class */
public final class SpeedMineSearchHelper4 implements SearchHelper_4 {
    public final BlockPos blockPos;
    public float val;
    public float val2;

    public SpeedMineSearchHelper4(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public void do2141(SpeedMine speedMine, MatrixStackEvent matrixStackEvent) {
        VoxelShape outlineShape = minecraftClient.world.getBlockState(this.blockPos).getOutlineShape(minecraftClient.world, this.blockPos);
        if (outlineShape.isEmpty() || !SearchHelper4_7.is2435(this.blockPos)) {
            return;
        }
        float f = get2142();
        Color[] colorArray1773 = speedMine.colorMode.getValue().getColorArray1773(speedMine, f);
        Box offset = speedMine.renderMode.getValue().getBox809(speedMine, outlineShape.getBoundingBox(), f).offset(this.blockPos);
        PhaseESPSearchHelper4.do1590(matrixStackEvent.getMatrixStack472(), offset, colorArray1773[0]);
        PhaseESPSearchHelper4.do1593(matrixStackEvent.getMatrixStack472(), offset, colorArray1773[1], speedMine.lineWidth.getValue().floatValue());
    }

    public BlockPos getBlockPos386() {
        return this.blockPos;
    }

    public float get2142() {
        return MathHelper.clamp(MathHelper.lerp(SearchHelper_2.get536(), this.val, this.val2), 0.0f, 1.0f);
    }

    public void do2143(float f) {
        do2144(this.val2 + f);
    }

    public void do2144(float f) {
        this.val = this.val2;
        this.val2 = f;
    }
}
