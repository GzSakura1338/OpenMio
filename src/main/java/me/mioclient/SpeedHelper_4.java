package me.mioclient;

import me.mioclient.event.MoveEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.movement.Speed;
import net.minecraft.entity.Entity;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SpeedHelper_4.class */
public final class SpeedHelper_4 extends SpeedHelper {
    public SpeedHelper_4(Speed speed) {
        super(speed);
        this.num = 2;
    }

    @Override // me.mioclient.SpeedHelper
    public void do242(MoveEvent moveEvent) {
        if (is594()) {
            this.speed.reset();
            HoleSnapSearchHelper4_3.getDoubleArray2507(moveEvent, HoleSnapSearchHelper4_3.get2511(true));
            return;
        }
        if (this.num == 4) {
            this.speed.reset();
        }
        if (minecraftClient.player.isOnGround() || this.num == 3) {
            if ((!minecraftClient.player.horizontalCollision && minecraftClient.player.forwardSpeed != 0.0f) || minecraftClient.player.sidewaysSpeed != 0.0f) {
                if (this.num == 2) {
                    this.val2 *= 2.149;
                    this.num = 3;
                } else if (this.num == 3) {
                    this.num = 2;
                    this.val2 = this.val3 - (0.66 * (this.val3 - HoleSnapSearchHelper4_3.get2511(true)));
                }
            }
            this.val2 = Math.min(this.val2, this.speed.speed.getValue().floatValue());
            this.val2 = Math.max(this.val2, HoleSnapSearchHelper4_3.get2511(true));
            double[] doubleArray2508 = HoleSnapSearchHelper4_3.getDoubleArray2508(minecraftClient.player.getYaw(SearchHelper_2.get536()), minecraftClient.player.input, this.val2);
            if (!minecraftClient.world.isSpaceEmpty(minecraftClient.player.getBoundingBox().stretch(doubleArray2508[0], 0.0d, doubleArray2508[1]))) {
                this.speed.reset();
                this.val2 = HoleSnapSearchHelper4_3.get2511(true);
            }
            HoleSnapSearchHelper4_3.getDoubleArray2507(moveEvent, this.val2);
        }
    }

    @Override // me.mioclient.SpeedHelper
    public void do388(MotionEvent motionEvent) {
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Post || is594()) {
            return;
        }
        super.do388(motionEvent);
        if (this.num == 3) {
            motionEvent.setY(motionEvent.get692() + (is390() ? 0.2 : 0.4) + HoleSnapSearchHelper4_3.get2513());
        }
    }

    public boolean is594() {
        Entity entity = minecraftClient.player;
        double x = entity.getX() - entity.prevX;
        double y = entity.getY() - entity.prevY;
        double z = entity.getZ() - entity.prevZ;
        if (!minecraftClient.world.isSpaceEmpty(minecraftClient.player.getBoundingBox().offset(x, y, z))) {
            return true;
        }
        if (!minecraftClient.world.isSpaceEmpty(minecraftClient.player.getBoundingBox().stretch(0.0d, -1.0, 0.0d)) && !minecraftClient.player.horizontalCollision && minecraftClient.player.getY() <= Math.floor(minecraftClient.player.getY()) && is1827()) {
            if (BaritoneHelper_3.mainhandHelper_2.getBox1109(minecraftClient.player, 3).minY >= minecraftClient.player.getY()) {
                return false;
            }
        }
        return true;
    }

    public boolean is1827() {
        return BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(500L);
    }
}
