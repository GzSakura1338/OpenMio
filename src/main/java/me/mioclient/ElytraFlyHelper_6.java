package me.mioclient;

import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Event_3;
import me.mioclient.feature.MotionEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.movement.ElytraFly;
import net.minecraft.util.math.Direction;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ElytraFlyHelper_6.class */
public class ElytraFlyHelper_6 extends ElytraFlyHelper {
    public long num;
    public float val;
    public final Stopwatch stopwatch;

    public ElytraFlyHelper_6(ElytraFly elytraFly) {
        super(elytraFly);
        this.stopwatch = new Stopwatch();
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void onEnable() {
        this.val = 0.0f;
        this.num = System.currentTimeMillis();
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do27(TickEvent tickEvent) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do28(MoveEvent moveEvent) {
        if (this.elytraFly.is949()) {
            if (!HoleSnapSearchHelper4_3.is2181()) {
                this.num = System.currentTimeMillis();
            }
            if (this.elytraFly.accelerate.getValue().booleanValue()) {
                this.val = (float) HoleSnapSearchHelper4_3.get2509(this.elytraFly.speed3.getValue().floatValue(), this.elytraFly.accelMin.getValue().floatValue(), this.elytraFly.accelTime.getValue().floatValue(), this.num);
            } else {
                this.val = this.elytraFly.speed3.getValue().floatValue();
            }
            double[] doubleArray2507 = HoleSnapSearchHelper4_3.getDoubleArray2507(moveEvent, this.val);
            float f = -this.elytraFly.glide.getValue().floatValue();
            minecraftClient.player.setVelocity(doubleArray2507[0], f, doubleArray2507[1]);
            moveEvent.setY(f);
            if (minecraftClient.player.input.sneaking) {
                moveEvent.setY(-0.5);
                minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, -0.5));
            }
            AutoSignSearchHelper4.do948();
            if (this.elytraFly.antiAfk.getValue().booleanValue() && moveEvent.get515() == 0.0d && moveEvent.get516() == 0.0d && this.stopwatch.is419(2500L)) {
                moveEvent.do691(Math.sin(Math.toRadians(minecraftClient.player.age % FreecamHelper.num3)) * 0.03, Math.cos(Math.toRadians(minecraftClient.player.age % FreecamHelper.num3)) * 0.03);
            } else if (HoleSnapSearchHelper4_3.is2181()) {
                this.stopwatch.reset();
            }
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do29(ChannelRead0Event channelRead0Event) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do31(MotionEvent motionEvent) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do32(TickPostEvent tickPostEvent) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do33(Event_3 event_3) {
    }
}
