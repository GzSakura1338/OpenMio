package me.mioclient;

import java.util.LinkedList;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HitmarkerSearchHelper4.class */
public final class HitmarkerSearchHelper4 implements SearchHelper_4 {
    public final LinkedList<Long> linkedList = new LinkedList<>();
    public int num;

    public HitmarkerSearchHelper4() {
        baritoneHelper.do1796(this);
    }

    public void do3093() {
        long nanoTime = System.nanoTime();
        this.linkedList.add(Long.valueOf(nanoTime));
        while (nanoTime - this.linkedList.getFirst().longValue() > 1000000000) {
            this.linkedList.remove();
        }
        this.num = this.linkedList.size();
    }

    public int get3094() {
        return this.num;
    }

    public float get3095(float f) {
        return MathHelper.clamp(1.0f / (this.num * f), 0.0f, 1.0f);
    }
}
