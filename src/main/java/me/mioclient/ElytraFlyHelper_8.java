package me.mioclient;

import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickEvent_2;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Event_3;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.movement.ElytraFly;
import net.minecraft.client.input.Input;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ElytraFlyHelper_8.class */
public class ElytraFlyHelper_8 extends ElytraFlyHelper implements Helper_13 {
    public static AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);

    public ElytraFlyHelper_8(ElytraFly elytraFly) {
        super(elytraFly);
    }

    @Override // me.mioclient.Helper_13
    public boolean is328() {
        if (is1469()) {
            return false;
        }
        return !HoleSnapSearchHelper4.is955() || minecraftClient.player.isOnGround();
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do32(TickPostEvent tickPostEvent) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if (is328()) {
            antiCheat.getBaritoneSearchHelper4239().do1623(sendImmediatelyEvent);
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do28(MoveEvent moveEvent) {
        boolean z = !BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(300L);
        if (HoleSnapSearchHelper4.is955() || z) {
            return;
        }
        float floatValue = this.elytraFly.speed3.getValue().floatValue();
        Input input = minecraftClient.player.input;
        if (input.movementForward == 0.0f && input.movementSideways == 0.0f) {
            input.movementForward = 1.0f;
        }
        double[] doubleArray2508 = HoleSnapSearchHelper4_3.getDoubleArray2508(minecraftClient.player.getYaw(SearchHelper_2.get536()), input, floatValue);
        moveEvent.do691(doubleArray2508[0], doubleArray2508[1]);
        this.elytraFly.stopwatch4.reset();
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do329(TickEvent_2 tickEvent_2) {
        if (minecraftClient.player.isOnGround()) {
            return;
        }
        tickEvent_2.getInput806().jumping = minecraftClient.player.age % 3 == 0;
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do33(Event_3 event_3) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do31(MotionEvent motionEvent) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do27(TickEvent tickEvent) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do29(ChannelRead0Event channelRead0Event) {
    }
}
