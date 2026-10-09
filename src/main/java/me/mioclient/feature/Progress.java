package me.mioclient.feature;

import java.util.function.Supplier;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.HUDHelper_2;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/feature/Progress.class */
public class Progress extends HUDHelper_2 {
    public Progress(float f, boolean z) {
        super(f, z);
    }

    public Progress(float f) {
        super(f);
    }

    public Progress(Supplier<Float> supplier, boolean z) {
        super(supplier, z);
    }

    @Override // me.mioclient.HUDHelper_2
    public float get172() {
        float f = BaritoneHelper_3.hitmarkerSearchHelper4.get3095(0.5f) * this.supplier.get().floatValue();
        if (this.flag) {
            double longBitsToDouble = 1.0;
            float intBitsToFloat = f - 1.0f;
            f = (float) (longBitsToDouble - (intBitsToFloat * Math.pow(intBitsToFloat, 3.0)));
        }
        this.val = this.val3 + ((this.val - this.val3) * f);
        if (this.val == this.val2 || Math.abs(this.val2 - this.val) < 0.01) {
            do171(this.val2);
        }
        if (BaritoneHelper_3.hitmarkerSearchHelper4.get3094() <= 15) {
            this.val = this.val2;
        }
        return MathHelper.clamp(this.val - 1.0f, 0.0f, 1.0f);
    }

    @Override // me.mioclient.HUDHelper_2
    public void do1737(float f) {
        super.do1737(f + 1.0f);
    }

    public float get2138() {
        return this.val;
    }

    public void do2139(boolean z) {
        do1737(z ? 1.0f : 0.0f);
    }

    public void do2140(boolean z) {
        do171(z ? 2.0f : 1.0f);
    }

    public void reset() {
        do1737(0.0f);
    }
}
