package me.mioclient;

import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ZoomHelper.class */
public class ZoomHelper {
    public float val;
    public float val2;
    public long startTime;
    public long num;

    public void do169(float f, long j) {
        if (this.val2 == f) {
            return;
        }
        this.val = get172();
        this.val2 = f;
        this.startTime = System.currentTimeMillis();
        this.num = j;
    }

    public void do170(boolean z, long j) {
        do169(z ? 1.0f : 0.0f, j);
    }

    public void do171(float f) {
        do169(f, 0L);
    }

    public float get172() {
        return ((1.0f - MathHelper.clamp(((float) ((this.startTime + this.num) - System.currentTimeMillis())) / ((float) Math.max(this.num, 1L)), 0.0f, 1.0f)) * (this.val2 - this.val)) + this.val;
    }
}
