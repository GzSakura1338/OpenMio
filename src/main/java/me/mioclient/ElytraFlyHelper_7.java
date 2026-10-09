package me.mioclient;

import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Event_3;
import me.mioclient.feature.MotionEvent;
import me.mioclient.mixin.ducks.DuckPlayerMoveC2SPacket;
import me.mioclient.module.movement.ElytraFly;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ElytraFlyHelper_7.class */
public class ElytraFlyHelper_7 extends ElytraFlyHelper {
    public long num;
    public boolean flag;
    public boolean flag2;
    public boolean flag3;

    public ElytraFlyHelper_7(ElytraFly elytraFly) {
        super(elytraFly);
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void onEnable() {
        if (minecraftClient.player.isFallFlying() && !minecraftClient.player.isOnGround()) {
            this.flag = true;
            minecraftClient.player.stopFallFlying();
        }
        this.flag2 = false;
        this.flag3 = false;
        this.num = System.currentTimeMillis();
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do27(TickEvent tickEvent) {
        if (minecraftClient.player.isOnGround()) {
            this.flag = false;
        } else if (minecraftClient.player.isFallFlying()) {
            this.flag = true;
        }
        boolean z = minecraftClient.player.input.jumping;
        if (this.elytraFly.takeoff.getValue() == ElytraFly.ElytraFlyMode_2.STRICT) {
            z = !minecraftClient.player.isOnGround() && minecraftClient.player.getVelocity().getY() < 0.1;
        } else {
            BaritoneHelper_3.holeSnapSearchHelper4_2.do2017(this.elytraFly);
        }
        if (z || this.flag2) {
            this.elytraFly.do948();
            this.flag2 = !minecraftClient.player.isFallFlying();
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do28(MoveEvent moveEvent) {
        if (!minecraftClient.player.isFallFlying() && this.flag && !minecraftClient.player.horizontalCollision && minecraftClient.player.getVelocity().getY() < 0.0d) {
            this.flag2 = true;
        }
        if (minecraftClient.player.isFallFlying()) {
            if (!HoleSnapSearchHelper4_3.is2181()) {
                this.num = System.currentTimeMillis();
            }
            float floatValue = this.elytraFly.speed2.getValue().floatValue();
            double[] doubleArray2507 = HoleSnapSearchHelper4_3.getDoubleArray2507(moveEvent, MathHelper.lerp(MathHelper.clamp(((float) (System.currentTimeMillis() - this.num)) / 500.0f, 0.0f, 1.0f), this.elytraFly.start.getValue().floatValue(), floatValue));
            minecraftClient.player.setVelocity(doubleArray2507[0], 0.0d, doubleArray2507[1]);
            moveEvent.setY(0.0d);
            minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, 0.0d));
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do29(ChannelRead0Event channelRead0Event) {
        if ((channelRead0Event.getPacket904() instanceof PlayerPositionLookS2CPacket) && minecraftClient.player.isFallFlying() && !minecraftClient.player.isOnGround()) {
            this.num = System.currentTimeMillis();
            minecraftClient.player.stopFallFlying();
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        DuckPlayerMoveC2SPacket packet904 = (DuckPlayerMoveC2SPacket)(sendImmediatelyEvent.getPacket904());
        if (packet904 instanceof PlayerMoveC2SPacket) {
            DuckPlayerMoveC2SPacket duckPlayerMoveC2SPacket = (DuckPlayerMoveC2SPacket)((PlayerMoveC2SPacket) packet904);
            if (!minecraftClient.player.isFallFlying() || minecraftClient.player.isOnGround() || minecraftClient.player.getVelocity().getY() >= 0.1 || !HoleSnapSearchHelper4_3.is2181()) {
                return;
            }
            duckPlayerMoveC2SPacket.setPitch(-5.0f);
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do31(MotionEvent motionEvent) {
        if (motionEvent.getKeyPearlMode1472() != KeyPearlMode.Pre) {
            return;
        }
        boolean z = false;
        if (this.elytraFly.is606() && !motionEvent.is2228()) {
            if (!minecraftClient.player.isFallFlying()) {
                z = true;
                this.flag3 = false;
            } else if (!this.flag3) {
                if (HoleSnapSearchHelper4_3.is2181()) {
                    this.flag3 = true;
                } else {
                    z = true;
                }
            }
        }
        if (z) {
            motionEvent.setYaw(motionEvent.get751() + (minecraftClient.player.age % 2 == 0 ? -2 : 2));
            motionEvent.setPitch(motionEvent.get752() + (minecraftClient.player.age % 2 == 0 ? -2 : 2));
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do32(TickPostEvent tickPostEvent) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do33(Event_3 event_3) {
    }
}
