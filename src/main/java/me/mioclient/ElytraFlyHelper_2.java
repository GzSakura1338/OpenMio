package me.mioclient;

import java.util.ArrayDeque;
import java.util.Queue;
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
import me.mioclient.module.movement.ObstaclePasser;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.screen.slot.SlotActionType;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ElytraFlyHelper_2.class */
public class ElytraFlyHelper_2 extends ElytraFlyHelper {
    public static final ObstaclePasser obstaclePasser = (ObstaclePasser) BaritoneHelper_3.baritoneHelper_4.getModule117(ObstaclePasser.class);
    public final Queue<Packet<?>> queue;
    public int num;
    public boolean flag;

    public ElytraFlyHelper_2(ElytraFly elytraFly) {
        super(elytraFly);
        this.queue = new ArrayDeque();
        this.num = 0;
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void onDisable() {
        minecraftClient.options.jumpKey.setPressed(false);
        minecraftClient.player.setSprinting(false);
        this.num = 0;
        do604();
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do27(TickEvent tickEvent) {
        if (this.elytraFly.is952() || is605()) {
            return;
        }
        if (minecraftClient.player.isFallFlying()) {
            minecraftClient.player.horizontalSpeed = 0.0f;
        }
        if (BaritoneHelper_3.antiPhaseSearchHelper4_2.get2232() >= 40) {
            do604();
        }
        if (HoleSnapSearchHelper4.is2007(minecraftClient.player) || HoleSnapSearchHelper4.is2005(minecraftClient.player)) {
            do604();
            return;
        }
        if (this.elytraFly.compensate.getValue().booleanValue()) {
            this.num--;
            if (this.num == 0) {
                do604();
                return;
            }
            return;
        }
        if (this.num != 0) {
            this.num = 0;
            do604();
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do28(MoveEvent moveEvent) {
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do29(ChannelRead0Event channelRead0Event) {
        if ((channelRead0Event.getPacket904() instanceof DeathMessageS2CPacket) || (channelRead0Event.getPacket904() instanceof PlayerPositionLookS2CPacket)) {
            do604();
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if (is605()) {
            return;
        }
        DuckPlayerMoveC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof DuckPlayerMoveC2SPacket ? (DuckPlayerMoveC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if (packet904 instanceof DuckPlayerMoveC2SPacket) {
            DuckPlayerMoveC2SPacket duckPlayerMoveC2SPacket = packet904;
            if (is606() && this.elytraFly.silent.getValue().booleanValue() && this.elytraFly.pitchLock.getValue().booleanValue() && HoleSnapSearchHelper4.is955() && !this.elytraFly.is952()) {
                duckPlayerMoveC2SPacket.setPitch(this.elytraFly.pitch2.getValue().floatValue());
            }
        }
        if (!this.elytraFly.compensate.getValue().booleanValue() || !minecraftClient.player.isFallFlying() || !is606() || this.elytraFly.is952() || (sendImmediatelyEvent.getPacket904() instanceof ClientCommandC2SPacket) || (sendImmediatelyEvent.getPacket904() instanceof CommonPongC2SPacket)) {
            return;
        }
        this.queue.add(sendImmediatelyEvent.getPacket904());
        sendImmediatelyEvent.do1162();
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do31(MotionEvent motionEvent) {
        if (!this.elytraFly.is952() && is606() && !is605() && this.elytraFly.pitchLock.getValue().booleanValue() && this.elytraFly.silent.getValue().booleanValue()) {
            motionEvent.setPitch(this.elytraFly.pitch2.getValue().floatValue());
        }
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do32(TickPostEvent tickPostEvent) {
        this.flag = this.elytraFly.is956();
        if (minecraftClient.player.isFallFlying() && BaritoneHelper_3.antiPhaseSearchHelper4_2.get2231() > 5) {
            minecraftClient.player.stopFallFlying();
            return;
        }
        boolean is606 = is606();
        if (HoleSnapSearchHelper4.is2007(minecraftClient.player) || HoleSnapSearchHelper4.is2005(minecraftClient.player) || this.elytraFly.is952() || is605() || !is606) {
            return;
        }
        if (!HoleSnapSearchHelper4.is955() && BaritoneHelper_3.antiPhaseSearchHelper4_2.get2231() < 3 && !this.flag) {
            minecraftClient.player.startFallFlying();
            AutoSignSearchHelper4.do948();
        }
        if (this.elytraFly.infDurability.getValue().booleanValue() && !this.flag) {
            minecraftClient.interactionManager.clickSlot(0, 6, 0, SlotActionType.PICKUP, minecraftClient.player);
            minecraftClient.interactionManager.clickSlot(0, 6, 0, SlotActionType.PICKUP, minecraftClient.player);
            AutoSignSearchHelper4.do948();
        }
        if (!minecraftClient.player.horizontalCollision) {
            if (HoleSnapSearchHelper4_3.is2181()) {
                minecraftClient.player.setSprinting(true);
                if (minecraftClient.player.isFallFlying()) {
                    minecraftClient.player.setSneaking(false);
                }
            }
            if (minecraftClient.player.isOnGround() && this.elytraFly.compensate.getValue().booleanValue()) {
                this.num = 3;
            }
        }
        if (!this.elytraFly.pitchLock.getValue().booleanValue() || this.elytraFly.silent.getValue().booleanValue()) {
            return;
        }
        minecraftClient.player.setPitch(this.elytraFly.pitch2.getValue().floatValue());
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do33(Event_3 event_3) {
        if (!is606() || is605() || !this.elytraFly.pitchLock.getValue().booleanValue() || this.elytraFly.is952()) {
            return;
        }
        event_3.setPitch(this.elytraFly.pitch2.getValue().floatValue());
        event_3.do1162();
    }

    @Override // me.mioclient.ElytraFlyHelper
    public void do329(TickEvent_2 tickEvent_2) {
        if (!is605() && is606() && HoleSnapSearchHelper4.is955() && !this.elytraFly.is952()) {
            tickEvent_2.getInput806().jumping = true;
            tickEvent_2.getInput806().pressingForward = true;
            tickEvent_2.getInput806().movementForward = tickEvent_2.is808() ? tickEvent_2.get807() : 1.0f;
            minecraftClient.player.setSprinting(true);
        }
    }

    public void do604() {
        Packet<?> poll;
        if (is1469()) {
            this.queue.clear();
            return;
        }
        while (!this.queue.isEmpty() && (poll = this.queue.poll()) != null) {
            AutoSignSearchHelper4.do2573(poll);
        }
    }

    public boolean is605() {
        if (obstaclePasser == null) {
            return false;
        }
        return obstaclePasser.is929();
    }

    public boolean is606() {
        return this.elytraFly.is606() || this.flag;
    }
}
