package me.mioclient;

import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.ExplosionVelocityEvent;
import me.mioclient.mixin.ducks.DuckEntityVelocityUpdateS2CPacket;
import me.mioclient.module.movement.Velocity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/VelocityHelper_2.class */
public class VelocityHelper_2 extends VelocityHelper {
    public VelocityHelper_2(Velocity velocity) {
        super(velocity);
    }

    @Override // me.mioclient.VelocityHelper
    public void do29(ChannelRead0Event channelRead0Event) {
        int intValue = this.velocity.horizontal.getValue().intValue();
        int intValue2 = this.velocity.vertical.getValue().intValue();
        int i = this.velocity.inverse.getValue().booleanValue() ? -1 : 1;
        EntityVelocityUpdateS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof EntityVelocityUpdateS2CPacket ? (EntityVelocityUpdateS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof EntityVelocityUpdateS2CPacket) {
            EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket = packet904;
            if (entityVelocityUpdateS2CPacket.getEntityId() == minecraftClient.player.getId()) {
                DuckEntityVelocityUpdateS2CPacket duckEntityVelocityUpdateS2CPacket = (DuckEntityVelocityUpdateS2CPacket) entityVelocityUpdateS2CPacket;
                duckEntityVelocityUpdateS2CPacket.setX((int) (entityVelocityUpdateS2CPacket.getVelocityX() * 8000.0 * intValue * 0.009999999776482582f * i));
                duckEntityVelocityUpdateS2CPacket.setY((int) (entityVelocityUpdateS2CPacket.getVelocityY() * 8000.0 * intValue2 * 0.009999999776482582f));
                duckEntityVelocityUpdateS2CPacket.setZ((int) (entityVelocityUpdateS2CPacket.getVelocityZ() * 8000.0 * intValue * 0.009999999776482582f * i));
                if (intValue == 0 && intValue2 == 0) {
                    channelRead0Event.do1162();
                }
            }
        }
    }

    @Override // me.mioclient.VelocityHelper
    public void do598(ExplosionVelocityEvent explosionVelocityEvent) {
        if (this.velocity.explosions.getValue().booleanValue()) {
            int intValue = this.velocity.horizontal.getValue().intValue();
            int intValue2 = this.velocity.vertical.getValue().intValue();
            int i = this.velocity.inverse.getValue().booleanValue() ? -1 : 1;
            explosionVelocityEvent.do768((int) (explosionVelocityEvent.get767() * intValue * 0.009999999776482582f * i));
            explosionVelocityEvent.do770((int) (explosionVelocityEvent.get769() * intValue2 * 0.009999999776482582f));
            explosionVelocityEvent.do772((int) (explosionVelocityEvent.get771() * intValue * 0.009999999776482582f * i));
        }
    }
}
