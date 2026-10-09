package me.mioclient;

import java.util.Iterator;
import me.mioclient.event.MoveEvent;
import net.minecraft.client.input.Input;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HoleSnapSearchHelper4_3.class */
public class HoleSnapSearchHelper4_3 implements SearchHelper_4 {
    public static float val = 0.2872999906539917f;
    public static float val2 = val * 0.20000000298023224f;

    public static double[] getDoubleArray2507(MoveEvent moveEvent, double d) {
        if (!is2181()) {
            moveEvent.do691(0.0d, 0.0d);
            return new double[]{0.0d, 0.0d};
        }
        double[] doubleArray2508 = getDoubleArray2508(minecraftClient.player.getYaw(SearchHelper_2.get536()), minecraftClient.player.input, d);
        moveEvent.do691(doubleArray2508[0], doubleArray2508[1]);
        return doubleArray2508;
    }

    public static double[] getDoubleArray2508(float f, Input input, double d) {
        float f2 = input.movementForward;
        float f3 = input.movementSideways;
        float f4 = f;
        if (f2 != 0.0f) {
            if (f3 > 0.0f) {
                f4 += f2 > 0.0f ? -FreecamHelper.num : FreecamHelper.num;
            } else if (f3 < 0.0f) {
                f4 += f2 > 0.0f ? FreecamHelper.num : -FreecamHelper.num;
            }
            f3 = 0.0f;
            if (f2 > 0.0f) {
                f2 = 1.0f;
            } else if (f2 < 0.0f) {
                f2 = -1.0f;
            }
        }
        return new double[]{(f2 * d * (-Math.sin(Math.toRadians(f4)))) + (f3 * d * Math.cos(Math.toRadians(f4))), ((f2 * d) * Math.cos(Math.toRadians(f4))) - ((f3 * d) * (-Math.sin(Math.toRadians(f4))))};
    }

    public static double get2509(double d, double d2, double d3, long j) {
        if (d2 >= d) {
            return d;
        }
        return Math.min(d2 + ((d - d2) * MathHelper.clamp((System.currentTimeMillis() - j) / (d3 * 1000.0), 0.0d, 1.0)), d);
    }

    public static boolean is2510(PlayerEntity playerEntity) {
        if (playerEntity == null) {
            return false;
        }
        return (playerEntity.forwardSpeed == 0.0f && playerEntity.sidewaysSpeed == 0.0f) ? false : true;
    }

    public static boolean is2181() {
        return is2510(minecraftClient.player);
    }

    public static double get2511(boolean z) {
        return get2512(z, val);
    }

    public static double get2512(boolean z, double d) {
        double d2 = d;
        if (minecraftClient.player.hasStatusEffect(StatusEffects.SPEED)) {
            d2 *= 1.0 + (0.2 * (minecraftClient.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1));
        }
        if (z) {
            if (minecraftClient.player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                d2 /= 1.0 + (0.2 * (minecraftClient.player.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier() + 1));
            }
        }
        return d2;
    }

    public static double get2513() {
        double d = 0.0d;
        if (minecraftClient.player.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
            d = 0.0d + ((minecraftClient.player.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1);
        }
        return d;
    }

    public static double get2514(double d) {
        if (!(minecraftClient.player.isOnGround() && minecraftClient.player.horizontalCollision)) {
            return 0.0d;
        }
        double longBitsToDouble = -1.0;
        Box expand = minecraftClient.player.getBoundingBox().offset(0.0d, 0.05, 0.0d).expand(0.05);
        Iterator it = minecraftClient.world.getCollisions(minecraftClient.player, expand.withMaxY(expand.maxY + d)).iterator();
        while (it.hasNext()) {
            for (Box box : ((VoxelShape) it.next()).getBoundingBoxes()) {
                if (box.maxY > longBitsToDouble) {
                    longBitsToDouble = box.maxY;
                }
            }
        }
        double y = longBitsToDouble - minecraftClient.player.getY();
        if (y <= 0.0d || y > d) {
            return 0.0d;
        }
        return y;
    }

    public static boolean is2515(PlayerEntity playerEntity, Vec3d vec3d) {
        Vec3d velocity = playerEntity.getVelocity();
        if (is2510(playerEntity)) {
            return playerEntity.getPos().add(velocity.getX() * 10000.0, 0.0d, velocity.getZ() * 10000.0).squaredDistanceTo(vec3d) < playerEntity.getPos().add(velocity.getX() * -10000.0, 0.0d, velocity.getZ() * -10000.0).squaredDistanceTo(vec3d);
        }
        return false;
    }

    public static boolean is2516(Vec3d vec3d) {
        return is2515(minecraftClient.player, vec3d);
    }

    public static float get2517() {
        if (minecraftClient.player.isFallFlying()) {
            double[] doubleArray2508 = getDoubleArray2508(minecraftClient.player.getYaw(), minecraftClient.player.input, 1.0);
            return (float) (Math.toDegrees(Math.atan2(doubleArray2508[1], doubleArray2508[0])) - FreecamHelper.num2);
        }
        float yaw = minecraftClient.player.getYaw();
        Input input = minecraftClient.player.input;
        boolean z = input.pressingForward != input.pressingBack;
        if (input.pressingBack) {
            yaw += 180.0f;
        }
        if (input.pressingRight && !z) {
            yaw += FreecamHelper.num2 * (input.pressingBack ? -1 : 1);
        }
        if (input.pressingLeft && !z) {
            yaw -= FreecamHelper.num2 * (input.pressingBack ? -1 : 1);
        }
        return yaw;
    }
}
