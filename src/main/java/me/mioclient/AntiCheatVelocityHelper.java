package me.mioclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.ExplosionVelocityEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.mixin.ducks.DuckEntityVelocityUpdateS2CPacket;
import me.mioclient.module.movement.FastWeb;
import me.mioclient.module.movement.Velocity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AntiCheatVelocityHelper.class */
public class AntiCheatVelocityHelper extends VelocityHelper {
    public static final FastWeb fastWeb = (FastWeb) BaritoneHelper_3.baritoneHelper_4.getModule117(FastWeb.class);
    public final List<Long> list;
    public final Stopwatch stopwatch;
    public boolean flag;

    public AntiCheatVelocityHelper(Velocity velocity) {
        super(velocity);
        this.list = Collections.synchronizedList(new ArrayList());
        this.stopwatch = new Stopwatch();
    }

    @Override // me.mioclient.VelocityHelper
    public void do29(ChannelRead0Event channelRead0Event) {
        EntityVelocityUpdateS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof EntityVelocityUpdateS2CPacket ? (EntityVelocityUpdateS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof EntityVelocityUpdateS2CPacket) {
            EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket = packet904;
            if (entityVelocityUpdateS2CPacket.getEntityId() == minecraftClient.player.getId() && is951()) {
                boolean z = fastWeb.isToggled() && fastWeb.is1534() && HoleSnapSearchHelper4.is2005(minecraftClient.player);
                DuckEntityVelocityUpdateS2CPacket duckEntityVelocityUpdateS2CPacket = (DuckEntityVelocityUpdateS2CPacket) entityVelocityUpdateS2CPacket;
                duckEntityVelocityUpdateS2CPacket.setZ((int) (entityVelocityUpdateS2CPacket.getVelocityZ() * 8000.0 * 0.0f));
                duckEntityVelocityUpdateS2CPacket.setX((int) (entityVelocityUpdateS2CPacket.getVelocityX() * 8000.0 * 0.0f));
                if ((this.flag || z) && entityVelocityUpdateS2CPacket.getVelocityY() > 0.0d) {
                    duckEntityVelocityUpdateS2CPacket.setY((int) (entityVelocityUpdateS2CPacket.getVelocityY() * -8000.0));
                }
            }
        }
        if (channelRead0Event.getPacket904() instanceof PlayerPositionLookS2CPacket) {
            this.list.add(Long.valueOf(System.currentTimeMillis()));
        }
    }

    @Override // me.mioclient.VelocityHelper
    public void do31(MotionEvent motionEvent) {
    }

    @Override // me.mioclient.VelocityHelper
    public void do598(ExplosionVelocityEvent explosionVelocityEvent) {
        if (is951()) {
            explosionVelocityEvent.do768(0.0f);
            explosionVelocityEvent.do770(0.0f);
            explosionVelocityEvent.do772(0.0f);
        }
    }

    @Override // me.mioclient.VelocityHelper
    public void do711() {
        this.flag = is1884();
        this.list.removeIf(l -> {
            return System.currentTimeMillis() > l.longValue() + 1000;
        });
        if (this.list.size() > 5) {
            this.stopwatch.reset();
        }
    }

    public boolean is951() {
        if (fastWeb.isToggled() && fastWeb.is1534() && HoleSnapSearchHelper4.is2005(minecraftClient.player)) {
            return true;
        }
        return this.stopwatch.is419(500L) && this.flag;
    }

    public static boolean is1884() {
        return is1886();
    }

    public static boolean is1885() {
        HashSet<BlockPos> hashSet = new HashSet<>();
        hashSet.add(getBlockPos1887(1.0f, 0.0f));
        hashSet.add(getBlockPos1887(-1.0f, 0.0f));
        hashSet.add(getBlockPos1887(0.0f, 1.0f));
        hashSet.add(getBlockPos1887(0.0f, -1.0f));
        return hashSet.stream().filter(AntiCheatVelocityHelper::is1888).count() >= 2;
    }

    public static boolean is1886() {
        return BlockPos.stream(minecraftClient.player.getBoundingBox().shrink(SearchHelper.val, 0.0d, SearchHelper.val).withMaxY(minecraftClient.player.getY()).offset(0.0d, 0.10000000149011612, 0.0d)).anyMatch(AntiCheatVelocityHelper::is1888);
    }

    public static BlockPos getBlockPos1887(float f, float f2) {
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
        float lengthX = (float) (clientPlayerEntity.getBoundingBox().getLengthX() / 2.0);
        return BlockPos.ofFloored(clientPlayerEntity.getX() + (f * lengthX * 1.0000009536743164f), Math.round(clientPlayerEntity.getY()), clientPlayerEntity.getZ() + (f2 * lengthX * 1.0000009536743164f));
    }

    public static boolean is1888(BlockPos blockPos) {
        return !minecraftClient.world.getBlockState(blockPos).getCollisionShape(minecraftClient.world, blockPos).isEmpty();
    }
}
