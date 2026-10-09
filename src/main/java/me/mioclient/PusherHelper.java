package me.mioclient;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PusherHelper.class */
public final class PusherHelper {
    public Direction getDirection2399(float[] fArr) {
        float intBitsToFloat = fArr[1] * 0.01745329238474369f;
        float intBitsToFloat2 = (-fArr[0]) * 0.01745329238474369f;
        float sin = MathHelper.sin(intBitsToFloat);
        float cos = MathHelper.cos(intBitsToFloat);
        float sin2 = MathHelper.sin(intBitsToFloat2);
        float cos2 = MathHelper.cos(intBitsToFloat2);
        boolean z = sin2 > 0.0f;
        boolean z2 = sin < 0.0f;
        boolean z3 = cos2 > 0.0f;
        float f = z ? sin2 : -sin2;
        float f2 = z2 ? -sin : sin;
        float f3 = z3 ? cos2 : -cos2;
        float f4 = f * cos;
        float f5 = f3 * cos;
        Direction direction = z ? Direction.EAST : Direction.WEST;
        Direction direction2 = z2 ? Direction.UP : Direction.DOWN;
        return f > f3 ? f2 > f4 ? direction2 : direction : f2 > f5 ? direction2 : z3 ? Direction.SOUTH : Direction.NORTH;
    }
}
