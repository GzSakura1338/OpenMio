package me.mioclient;

import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickEvent_2;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Event_3;
import me.mioclient.feature.MotionEvent;
import me.mioclient.mixin.ducks.DuckPlayerMoveC2SPacket;
import me.mioclient.module.movement.ElytraFly;
import me.mioclient.module.movement.Fireworks;
import me.mioclient.module.movement.ObstaclePasser;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ElytraFlyHelper_3.class */
public class ElytraFlyHelper_3 extends ElytraFlyHelper {
    public static final ObstaclePasser obstaclePasser = (ObstaclePasser) BaritoneHelper_3.baritoneHelper_4.getModule117(ObstaclePasser.class);
    public static Fireworks fireworks = (Fireworks) BaritoneHelper_3.baritoneHelper_4.getModule117(Fireworks.class);
    public Vec3d vec3d;
    public int num;
    public double val;
    public long num2;
    public long num3;

    public ElytraFlyHelper_3(ElytraFly elytraFly) {
        super(elytraFly);
        this.vec3d = Vec3d.ZERO;
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void onEnable() {
        this.val = 0.0d;
        long currentTimeMillis = System.currentTimeMillis();
        this.num2 = currentTimeMillis;
        this.num3 = currentTimeMillis;
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do27(TickEvent tickEvent) {
        if (!minecraftClient.player.isFallFlying()) {
            this.num3 = System.currentTimeMillis();
        }
        if (minecraftClient.player.input.jumping || minecraftClient.player.input.sneaking) {
            return;
        }
        this.num3 = System.currentTimeMillis();
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do28(MoveEvent moveEvent) {
        if (is605()) {
            return;
        }
        if (!minecraftClient.player.isFallFlying() || this.elytraFly.is954()) {
            this.num2 = System.currentTimeMillis();
            return;
        }
        if (is863() && this.elytraFly.is606()) {
            if (!HoleSnapSearchHelper4_3.is2181()) {
                this.num2 = System.currentTimeMillis();
            }
            float floatValue = this.elytraFly.vSpeed.getValue().floatValue();
            if (this.elytraFly.accelerate.getValue().booleanValue()) {
                this.val = (float) HoleSnapSearchHelper4_3.get2509(this.elytraFly.speed3.getValue().floatValue(), this.elytraFly.accelMin.getValue().floatValue(), this.elytraFly.accelTime.getValue().floatValue(), this.num2);
                floatValue = (float) HoleSnapSearchHelper4_3.get2509(floatValue, this.elytraFly.verAccelMin.getValue().floatValue(), this.elytraFly.accelTime.getValue().floatValue(), this.num3);
            } else {
                this.val = this.elytraFly.speed3.getValue().floatValue();
            }
            this.val += fireworks.get142(false);
            if (this.num == 0) {
                if (minecraftClient.player.input.jumping && this.elytraFly.vertical.getValue() == ElytraFly.ElytraFlyMode.STRICT && this.val >= 2.0) {
                    this.val = 2.0;
                }
                double[] doubleArray2507 = HoleSnapSearchHelper4_3.getDoubleArray2507(moveEvent, this.val);
                minecraftClient.player.setVelocity(doubleArray2507[0], minecraftClient.player.getVelocity().y, doubleArray2507[1]);
                this.vec3d = minecraftClient.player.getVelocity();
                if (this.elytraFly.antiKick.getValue().booleanValue() && minecraftClient.player.age % 4 == 0) {
                    moveEvent.setY(moveEvent.get692() - 0.0001);
                    minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().add(0.0d, -0.0001, 0.0d));
                }
            }
            if (minecraftClient.player.input.jumping && minecraftClient.player.isFallFlying()) {
                if (this.elytraFly.vertical.getValue() == ElytraFly.ElytraFlyMode.STRICT) {
                    this.num++;
                }
                if (this.elytraFly.vertical.getValue() != ElytraFly.ElytraFlyMode.PLAIN) {
                    if (this.elytraFly.vertical.getValue() == ElytraFly.ElytraFlyMode.STRICT && HoleSnapSearchHelper4_3.is2181()) {
                        do861();
                        return;
                    }
                    return;
                }
                moveEvent.setY(floatValue);
                minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, floatValue));
                return;
            }
            if (minecraftClient.player.input.sneaking) {
                this.num = 0;
                moveEvent.setY(-floatValue);
                minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, -floatValue));
                return;
            }
            this.num = 0;
            moveEvent.setY(0.0d);
            minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, 0.0d));
            if (minecraftClient.player.verticalCollision || this.elytraFly.glide.getValue().floatValue() == 0.0f) {
                return;
            }
            minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().add(0.0d, -this.elytraFly.glide.getValue().floatValue(), 0.0d));
            moveEvent.setY(minecraftClient.player.getVelocity().y);
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do29(ChannelRead0Event channelRead0Event) {
        if (channelRead0Event.getPacket904() instanceof PlayerPositionLookS2CPacket) {
            this.num2 = System.currentTimeMillis();
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if (is605()) {
            return;
        }
        DuckPlayerMoveC2SPacket packet904 = (DuckPlayerMoveC2SPacket)(sendImmediatelyEvent.getPacket904());
        if (packet904 instanceof PlayerMoveC2SPacket) {
            DuckPlayerMoveC2SPacket duckPlayerMoveC2SPacket = (DuckPlayerMoveC2SPacket)((PlayerMoveC2SPacket) packet904);
            if (minecraftClient.player.isFallFlying() && !this.elytraFly.is954() && is863() && this.elytraFly.is606()) {
                if (is862()) {
                    duckPlayerMoveC2SPacket.setPitch(get752());
                    return;
                }
                if (HoleSnapSearchHelper4_3.is2181()) {
                    if (this.elytraFly.spoofPitch.getValue().booleanValue()) {
                        duckPlayerMoveC2SPacket.setPitch(this.elytraFly.pitch.getValue().floatValue());
                    }
                    if (this.num > 0) {
                        duckPlayerMoveC2SPacket.setPitch(-this.elytraFly.vPitch.getValue().floatValue());
                    }
                }
            }
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do31(MotionEvent motionEvent) {
        if (minecraftClient.player.isFallFlying() && !this.elytraFly.is954() && is863() && this.elytraFly.is606() && motionEvent.getKeyPearlMode1472() == KeyPearlMode.Pre && !is605()) {
            if (is862()) {
                motionEvent.setPitch(get752());
                return;
            }
            if (HoleSnapSearchHelper4_3.is2181()) {
                motionEvent.setYaw(get751());
                if (this.elytraFly.spoofPitch.getValue().booleanValue()) {
                    motionEvent.setPitch(this.elytraFly.pitch.getValue().floatValue());
                }
                if (this.num > 0) {
                    motionEvent.setPitch(-this.elytraFly.vPitch.getValue().floatValue());
                }
            }
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do32(TickPostEvent tickPostEvent) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do33(Event_3 event_3) {
        if (this.elytraFly.is606() && minecraftClient.player.isFallFlying() && !this.elytraFly.is954() && is863() && !is605()) {
            if (is862()) {
                event_3.setPitch(get752());
            }
            if (HoleSnapSearchHelper4_3.is2181()) {
                event_3.setYaw(get751());
            }
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do329(TickEvent_2 tickEvent_2) {
        if (tickEvent_2.getInput806() == null || minecraftClient.player.input == null || is605() || !this.elytraFly.is954()) {
            return;
        }
        tickEvent_2.getInput806().jumping = true;
        tickEvent_2.getInput806().pressingForward = true;
        tickEvent_2.getInput806().movementForward = tickEvent_2.is808() ? tickEvent_2.get807() : 1.0f;
    }

    public void do861() {
        double d = PingSpoofHelper.get373(-this.elytraFly.vPitch.getValue().floatValue());
        Vec3d vec3d = this.vec3d;
        Vec3d vec3d375 = PingSpoofHelper.getVec3d375(-this.elytraFly.vPitch.getValue().floatValue(), minecraftClient.player.getYaw());
        double horizontalLength = vec3d.horizontalLength();
        double horizontalLength2 = vec3d375.horizontalLength();
        double length = vec3d375.length();
        double cos = Math.cos(d);
        Vec3d add = vec3d.add(0.0d, 0.08 * (-1.0 + (cos * cos * Math.min(1.0, length / 0.4) * 0.75)), 0.0d);
        if (d < 0.0d && horizontalLength2 > 0.0d) {
            double longBitsToDouble = horizontalLength * (-Math.sin(d)) * 0.04;
            add = add.add(((-vec3d375.x) * longBitsToDouble) / horizontalLength2, longBitsToDouble * 3.2, ((-vec3d375.z) * longBitsToDouble) / horizontalLength2);
        }
        minecraftClient.player.setVelocity(add.multiply(0.99, 0.98, 0.99));
        this.vec3d = minecraftClient.player.getVelocity();
    }

    public float get751() {
        double[] doubleArray2508 = HoleSnapSearchHelper4_3.getDoubleArray2508(minecraftClient.player.getYaw(), minecraftClient.player.input, 1.0);
        return (float) (Math.toDegrees(Math.atan2(doubleArray2508[1], doubleArray2508[0])) - FreecamHelper.num2);
    }

    public float get752() {
        float pitch = minecraftClient.player.getPitch();
        if (is862()) {
            pitch = FreecamHelper.num2;
            if (HoleSnapSearchHelper4_3.is2181()) {
                pitch = FreecamHelper.num;
            }
            if (minecraftClient.player.input.jumping) {
                pitch = -pitch;
            }
        }
        return pitch;
    }

    public boolean is862() {
        return minecraftClient.player.input.jumping || (minecraftClient.player.input.sneaking && this.elytraFly.vertical.getValue() == ElytraFly.ElytraFlyMode.PLAIN && SearchHelper4_8.is724());
    }

    public boolean is863() {
        if (minecraftClient.player.isFallFlying() && this.elytraFly.is606() && fireworks.autoLaunch.getValue().booleanValue() && fireworks.isToggled() && fireworks.stopwatch.is419(500L)) {
            return fireworks.is141();
        }
        return true;
    }

    public boolean is605() {
        if (obstaclePasser == null) {
            return false;
        }
        return obstaclePasser.is928();
    }
}
