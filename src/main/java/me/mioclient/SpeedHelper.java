package me.mioclient;

import me.mioclient.event.MoveEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.movement.Speed;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SpeedHelper.class */
public abstract class SpeedHelper implements SearchHelper_4 {
    public final Speed speed;
    public final double val = 0.2873;
    public double val2;
    public double val3;
    public int num;

    public SpeedHelper(Speed speed) {
        this.speed = speed;
    }

    public abstract void do242(MoveEvent moveEvent);

    public void do388(MotionEvent motionEvent) {
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Post) {
            return;
        }
        if (!HoleSnapSearchHelper4_3.is2181()) {
            this.num = 4;
            this.val2 = 0.0d;
        }
        double x = minecraftClient.player.getX() - minecraftClient.player.prevX;
        double z = minecraftClient.player.getZ() - minecraftClient.player.prevZ;
        this.val3 = Math.sqrt((x * x) + (z * z));
    }

    public void do389() {
    }

    public void onEnable() {
    }

    public boolean is390() {
        return !minecraftClient.world.isSpaceEmpty(minecraftClient.player.getBoundingBox().offset(0.0d, minecraftClient.player.getVelocity().y, 0.0d));
    }
}
