package me.mioclient;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SpeedMineHelper_3.class */
public class SpeedMineHelper_3 {
    public Box box;
    public long num;
    public float val = 1.0f;

    public void do2258() {
        this.num = System.currentTimeMillis() + 150;
    }

    public void do667(BlockPos blockPos) {
        this.box = new Box(blockPos);
    }

    public void do2259(Box box) {
        this.box = box;
    }

    public void do2260(float f) {
        this.val = f;
    }

    public void do2261(MatrixStack matrixStack, Color color, Color color2, float f, boolean z) {
        if (this.box == null || !SearchHelper4_8.is2492(this.box)) {
            return;
        }
        float f2 = get2262(f);
        PhaseESPSearchHelper4.do1590(matrixStack, this.box, z ? MixinMessageIndicatorHelper_2.getColor816(color, (int) (color.getAlpha() * f2)) : color);
        PhaseESPSearchHelper4.do1593(matrixStack, this.box, z ? MixinMessageIndicatorHelper_2.getColor816(color2, (int) (color2.getAlpha() * f2)) : color2, this.val);
        if (f2 == 0.0f) {
            this.box = null;
        }
    }

    public float get2262(float f) {
        return MathHelper.clamp(1.0f - (((float) Math.max(System.currentTimeMillis() - this.num, 0L)) / f), 0.0f, 1.0f);
    }

    public Box getBox2263() {
        return this.box;
    }
}
