package me.mioclient;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PositionData.class */
public final class PositionData {
    public final int num;
    public final int num2;

    public PositionData(int i, int i2) {
        this.num = i;
        this.num2 = i2;
    }

    public double get1219(PositionData positionData) {
        return Math.pow(this.num - positionData.num, 2.0) + Math.pow(this.num2 - positionData.num2, 2.0);
    }

    public double get1220(PositionData positionData) {
        return Math.sqrt(get1219(positionData));
    }

    public PositionData getPositionData1221(PositionData positionData) {
        return new PositionData(this.num + positionData.num, this.num2 + positionData.num2);
    }




    public int get476() {
        return this.num;
    }

    public int get1222() {
        return this.num2;
    }
}
