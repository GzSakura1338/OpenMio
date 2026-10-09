package me.mioclient;

import java.util.function.Supplier;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HUDHelper_2.class */
public class HUDHelper_2 {
    public float val;
    public float val2;
    public float val3;
    public boolean flag;
    public Supplier<Float> supplier;

    public HUDHelper_2(float f, boolean z) {
        this.supplier = () -> {
            return Float.valueOf(f);
        };
        this.flag = z;
    }

    public HUDHelper_2(float f) {
        this(f, true);
    }

    public HUDHelper_2(Supplier<Float> supplier, boolean z) {
        this.supplier = supplier;
        this.flag = z;
    }

    public float get172() {
        float f = BaritoneHelper_3.hitmarkerSearchHelper4.get3095(0.5f) * this.supplier.get().floatValue();
        if (this.flag) {
            double longBitsToDouble = 1.0;
            float intBitsToFloat = f - 1.0f;
            f = (float) (longBitsToDouble - (intBitsToFloat * Math.pow(intBitsToFloat, 3.0)));
        }
        this.val = this.val3 + ((this.val - this.val3) * f);
        if (this.val == this.val2 || Math.abs(this.val2 - this.val) < FreecamHelper.val2) {
            do171(this.val2);
        }
        if (BaritoneHelper_3.hitmarkerSearchHelper4.get3094() <= 15) {
            this.val = this.val2;
        }
        return this.val;
    }

    public void do1737(float f) {
        if (this.val == f) {
            return;
        }
        this.val3 = this.val;
        this.val = f;
        this.val2 = f;
    }

    public void do171(float f) {
        if (this.val == f) {
            return;
        }
        this.val3 = f;
        this.val = f;
        this.val2 = f;
    }

    public Supplier<Float> getSupplier1738() {
        return this.supplier;
    }

    public void do1739(Supplier<Float> supplier) {
        this.supplier = supplier;
    }

    public void do1740(boolean z) {
        this.flag = z;
    }
}
