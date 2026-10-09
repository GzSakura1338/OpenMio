package me.mioclient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PingSpoofHelper.class */
public class PingSpoofHelper implements SearchHelper_4 {
    public static double get368(double d, int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        return BigDecimal.valueOf(d).setScale(i, RoundingMode.FLOOR).doubleValue();
    }

    public static float get369(float f, int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        return BigDecimal.valueOf(f).setScale(i, RoundingMode.FLOOR).floatValue();
    }

    public static float get370(float f, float f2) {
        return f + (new Random().nextFloat() * (f2 - f));
    }

    public static int get371(int i, int i2) {
        return i + (new Random().nextInt() * (i2 - i));
    }

    public static double get372(double d, double d2, double d3) {
        return d + ((d2 - d) * d3);
    }

    public static float get373(float f) {
        return f * FreecamHelper.val4;
    }

    public static float get374(float f) {
        return f * FreecamHelper.val5;
    }

    public static Vec3d getVec3d375(float f, float f2) {
        float f3 = get373(f);
        float f4 = get373(-f2);
        float cos = MathHelper.cos(f4);
        float sin = MathHelper.sin(f4);
        float cos2 = MathHelper.cos(f3);
        return new Vec3d(sin * cos2, -MathHelper.sin(f3), cos * cos2);
    }

    public static double get376(Vec3d vec3d, double d) {
        return get377(minecraftClient.gameRenderer.getCamera().getPos(), vec3d, d);
    }

    public static double get377(Vec3d vec3d, Vec3d vec3d2, double d) {
        double distanceTo = vec3d.distanceTo(vec3d2);
        double intValue = ((Integer) minecraftClient.options.getFov().getValue()).intValue() / 110.0;
        if (intValue < 1.0) {
            intValue = 1.0;
        }
        return distanceTo <= 10.0 / d ? Math.min(1.0, d) * intValue : distanceTo * d * 0.1 * intValue;
    }

    public static float get378(PlayerEntity playerEntity) {
        return MathHelper.angleBetween(minecraftClient.player.getYaw(), SearchHelper4_8.getFloatArray2484(playerEntity.getPos())[0]);
    }

    public static double get379(double d, double d2, double d3) {
        return d3 - d > d - d2 ? d2 : d3;
    }

    public static boolean is380(int i) {
        return i != 0 && new Random().nextInt(100) <= i;
    }

    public static float get381(float f) {
        return (f < 0.0f ? f + 360.0f : f) % 360.0f;
    }

    public static float get382(float f, float f2) {
        return MathHelper.lerp(SearchHelper_2.get536(), f, f2);
    }

    public static double get383(double d, double d2) {
        return MathHelper.lerp(SearchHelper_2.get536(), d, d2);
    }

    public static double get384(BlockPos blockPos) {
        return get385(blockPos.toCenterPos());
    }

    public static double get385(Vec3d vec3d) {
        return minecraftClient.gameRenderer.getCamera().getPos().distanceTo(vec3d);
    }
}
