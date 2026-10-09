package me.mioclient.module.player;

import me.mioclient.BaritoneHelper_3;
import me.mioclient.FreecamHelper;
import me.mioclient.Helper_7;
import me.mioclient.KeyPearlMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Event_3;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.Module;
import me.mioclient.module.render.FreeLook;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/player/RotationLock.class */
public class RotationLock extends Module {
    public static FreeLook freelook = (FreeLook) BaritoneHelper_3.baritoneHelper_4.getModule117(FreeLook.class);
    public Setting<Boolean> yaw;
    public Setting<Boolean> custom;
    public Setting<Float> value2;
    public Setting<Boolean> pitch;
    public Setting<Float> value;

    public RotationLock() {
        super("RotationLock", "Locks your rotation.", Category.PLAYER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.value2.do2329("YawValue");
        this.value.do2329("PitchValue");
    }

    @Listen(get219= Helper_7.num)
    public void do31(MotionEvent motionEvent) {
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Pre && freelook.isToggled()) {
            if (this.yaw.getValue().booleanValue()) {
                motionEvent.setYaw(get751());
            }
            if (this.pitch.getValue().booleanValue()) {
                motionEvent.setPitch(get752());
            }
        }
    }

    @Listen(get219= Helper_7.num)
    public void do33(Event_3 event_3) {
        if (freelook.isToggled()) {
            if (this.yaw.getValue().booleanValue()) {
                event_3.setYaw(get751());
                event_3.do1162();
            }
            if (this.pitch.getValue().booleanValue()) {
                event_3.setPitch(get752());
                event_3.do1162();
            }
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (this.yaw.getValue().booleanValue()) {
            minecraftClient.player.setYaw(get751());
        }
        if (this.pitch.getValue().booleanValue()) {
            minecraftClient.player.setPitch(get752());
        }
    }

    public float get751() {
        return !this.custom.getValue().booleanValue() ? Math.round((minecraftClient.player.getYaw() + 1.0f) / FreecamHelper.num) * FreecamHelper.num : this.value2.getValue().floatValue();
    }

    public float get752() {
        return this.value.getValue().floatValue();
    }
}
