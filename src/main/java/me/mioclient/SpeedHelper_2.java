package me.mioclient;

import me.mioclient.event.MoveEvent;
import me.mioclient.module.movement.LongJump;
import me.mioclient.module.movement.Speed;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SpeedHelper_2.class */
public final class SpeedHelper_2 extends SpeedHelper {
    public static LongJump longJump = (LongJump) BaritoneHelper_3.baritoneHelper_4.getModule117(LongJump.class);

    public SpeedHelper_2(Speed speed) {
        super(speed);
    }

    @Override // me.mioclient.SpeedHelper
    public void do242(MoveEvent moveEvent) {
        if (is130()) {
            return;
        }
        if (this.speed.useTimer.getValue().booleanValue() && BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(250L)) {
            HoleSnapSearchHelper4_2 holeSnapSearchHelper4_2 = BaritoneHelper_3.holeSnapSearchHelper4_2;
            holeSnapSearchHelper4_2.do2018(this.speed, 1.0887999534606934f);
        } else {
            BaritoneHelper_3.holeSnapSearchHelper4_2.do2017(this.speed);
        }
        if (!HoleSnapSearchHelper4_3.is2181()) {
            moveEvent.do691(0.0d, 0.0d);
            return;
        }
        double d = HoleSnapSearchHelper4_3.get2511(false);
        if (BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(500L)) {
            d = Math.max(d, Math.hypot(moveEvent.get515(), moveEvent.get516()));
        }
        HoleSnapSearchHelper4_3.getDoubleArray2507(moveEvent, d);
    }

    @Override // me.mioclient.SpeedHelper
    public void do389() {
        if (!is130() && HoleSnapSearchHelper4_3.is2181() && minecraftClient.player.groundCollision) {
            double[] doubleArray2508 = HoleSnapSearchHelper4_3.getDoubleArray2508(minecraftClient.player.getYaw(), minecraftClient.player.input, 1.0);
            float degrees = (float) (Math.toDegrees(Math.atan2(doubleArray2508[1], doubleArray2508[0])) - FreecamHelper.num2);
            Vec3d velocity = minecraftClient.player.getVelocity();
            float radians = (float) Math.toRadians(degrees);
            float intBitsToFloat = 0.20000000298023224f;
            minecraftClient.player.setVelocity(velocity.x, 0.4 + HoleSnapSearchHelper4_3.get2513(), velocity.z);
            minecraftClient.player.addVelocityInternal(new Vec3d((-MathHelper.sin(radians)) * intBitsToFloat, 0.0d, MathHelper.cos(radians) * intBitsToFloat));
            minecraftClient.player.setSprinting(true);
        }
    }

    public boolean is130() {
        return this.speed.is1815() || this.speed.is1814() || HoleSnapSearchHelper4.is2012() || minecraftClient.player.isFallFlying() || minecraftClient.player.isSpectator() || longJump.isToggled();
    }
}
