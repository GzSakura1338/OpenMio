package me.mioclient;

import me.mioclient.event.MoveEvent;
import me.mioclient.module.movement.LongJump;
import me.mioclient.module.movement.Speed;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SpeedHelper_6.class */
public class SpeedHelper_6 extends SpeedHelper {
    public static LongJump longJump = (LongJump) BaritoneHelper_3.baritoneHelper_4.getModule117(LongJump.class);

    public SpeedHelper_6(Speed speed) {
        super(speed);
        this.num = 4;
    }

    @Override // me.mioclient.SpeedHelper
    public void do242(MoveEvent moveEvent) {
        double d;
        double d2;
        if (this.speed.is1815() || this.speed.is1814() || HoleSnapSearchHelper4.is2012() || minecraftClient.player.isFallFlying() || minecraftClient.player.isSpectator() || longJump.isToggled()) {
            return;
        }
        double d3 = moveEvent.get692();
        if (this.speed.useTimer.getValue().booleanValue() && BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(250L)) {
            BaritoneHelper_3.holeSnapSearchHelper4_2.do2018(this.speed, 1.0887999534606934f);
        } else if (this.speed.useTimer.getValue().booleanValue()) {
            BaritoneHelper_3.holeSnapSearchHelper4_2.do2017(this.speed);
        }
        float intBitsToFloat = (this.speed.stopwatch.is419(1000L) || !this.speed.boost.getValue().booleanValue()) ? 1.0f : 1.2000000476837158f;
        if (this.num == 1 && HoleSnapSearchHelper4_3.is2181()) {
            this.val2 = (1.35 * HoleSnapSearchHelper4_3.get2512(false, (0.2873 * this.speed.speed.getValue().floatValue()) * intBitsToFloat)) - 0.01;
        } else if (this.num == 2 && HoleSnapSearchHelper4_3.is2181() && minecraftClient.player.groundCollision) {
            d3 = 0.42 + HoleSnapSearchHelper4_3.get2513();
            this.val2 *= 1.535;
        } else if (this.num == 3) {
            this.val2 = this.val3 - (0.6600000262260437 * (this.val3 - HoleSnapSearchHelper4_3.get2512(true, (0.2873 * this.speed.speed.getValue().floatValue()) * intBitsToFloat)));
        } else {
            if ((is390() || minecraftClient.player.verticalCollision) && this.num > 0) {
                this.num = HoleSnapSearchHelper4_3.is2181() ? 1 : 0;
            }
            this.val2 = this.val3 - (this.val3 / 159.0);
        }
        this.val2 = Math.max(this.val2, HoleSnapSearchHelper4_3.get2512(false, 0.2873 * this.speed.speed.getValue().floatValue() * intBitsToFloat));
        if (HoleSnapSearchHelper4_3.is2181()) {
            double[] doubleArray2508 = HoleSnapSearchHelper4_3.getDoubleArray2508(minecraftClient.player.getYaw(SearchHelper_2.get536()), minecraftClient.player.input, this.val2);
            d = doubleArray2508[0];
            d2 = doubleArray2508[1];
        } else {
            d = 0.0d;
            d2 = 0.0d;
        }
        MixinLivingEntityHelper_2.do2581(moveEvent.getVec3d689(), d, d3, d2);
        if (HoleSnapSearchHelper4_3.is2181()) {
            this.num++;
        }
    }
}
