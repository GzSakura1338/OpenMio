package me.mioclient;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.ConnectEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.exploit.FastLatency;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HoleSnapSearchHelper4_4.class */
public final class HoleSnapSearchHelper4_4 implements SearchHelper_4 {
    public static final FastLatency fastLatency = (FastLatency) BaritoneHelper_3.baritoneHelper_4.getModule117(FastLatency.class);
    public static final Pattern pattern = Pattern.compile("\\d+ ping");
    public final ArrayDeque<Float> arrayDeque = new ArrayDeque<>(20);
    public final Stopwatch stopwatch = new Stopwatch();
    public final Stopwatch stopwatch2 = new Stopwatch();
    public float val;
    public float val2;
    public long num;
    public long num2;
    public int num3;
    public boolean flag;
    public boolean flag2;
    public ServerInfo serverInfo;
    public int num4;
    public boolean flag3;

    public HoleSnapSearchHelper4_4() {
        baritoneHelper.do1796(this);
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        this.num = System.currentTimeMillis();
        PlayerPositionLookS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof PlayerPositionLookS2CPacket ? (PlayerPositionLookS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof PlayerPositionLookS2CPacket) {
            this.num3 = packet904.getTeleportId();
            if (this.stopwatch2.is419(50L)) {
                this.stopwatch.reset();
            }
            this.flag = minecraftClient.player.isSprinting();
        }
        if (channelRead0Event.getPacket904() instanceof WorldTimeUpdateS2CPacket) {
            if (this.num2 != 0) {
                if (this.arrayDeque.size() > 20) {
                    this.arrayDeque.poll();
                }
                this.val = Math.max(0.0f, Math.min(20.0f, 20.0f * (1000.0f / ((float) (System.currentTimeMillis() - this.num2)))));
                this.arrayDeque.add(Float.valueOf(this.val));
                float f = 0.0f;
                Iterator<Float> it = this.arrayDeque.iterator();
                while (it.hasNext()) {
                    f += Math.max(0.0f, Math.min(20.0f, it.next().floatValue()));
                }
                if (this.arrayDeque.size() > 0) {
                    f /= this.arrayDeque.size();
                }
                this.val2 = f;
            }
            this.num2 = System.currentTimeMillis();
        }
        BundleS2CPacket packet9042 = (channelRead0Event.getPacket904()) instanceof BundleS2CPacket ? (BundleS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet9042 instanceof BundleS2CPacket) {
            Iterator it2 = packet9042.getPackets().iterator();
            while (it2.hasNext()) {
                baritoneHelper.getObject1794(new ChannelRead0Event((Packet) it2.next()));
            }
        }
        PlayerListHeaderS2CPacket packet9043 = (channelRead0Event.getPacket904()) instanceof PlayerListHeaderS2CPacket ? (PlayerListHeaderS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet9043 instanceof PlayerListHeaderS2CPacket) {
            PlayerListHeaderS2CPacket playerListHeaderS2CPacket = packet9043;
            if (minecraftClient.player.networkHandler.getServerInfo() == null) {
                return;
            }
            Matcher matcher = pattern.matcher(playerListHeaderS2CPacket.footer().getString());
            if (matcher.find()) {
                String group = matcher.group();
                try {
                    this.num4 = Integer.parseInt(group.substring(0, group.length() - 5));
                } catch (Throwable th) {
                }
            }
        }
    }

    @Listen(get219= Helper_7.num)
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        ClientCommandC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof ClientCommandC2SPacket ? (ClientCommandC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if (packet904 instanceof ClientCommandC2SPacket) {
            ClientCommandC2SPacket clientCommandC2SPacket = packet904;
            if (clientCommandC2SPacket.getMode() == ClientCommandC2SPacket.Mode.START_SPRINTING) {
                if (this.flag) {
                    sendImmediatelyEvent.do1162();
                }
                this.flag = true;
            }
            if (clientCommandC2SPacket.getMode() == ClientCommandC2SPacket.Mode.STOP_SPRINTING) {
                if (!this.flag) {
                    sendImmediatelyEvent.do1162();
                }
                this.flag = false;
            }
            if (clientCommandC2SPacket.getMode() == ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY) {
                this.flag2 = true;
            }
            if (clientCommandC2SPacket.getMode() == ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY) {
                this.flag2 = false;
            }
        }
        if (sendImmediatelyEvent.getPacket904() instanceof TeleportConfirmC2SPacket) {
            this.stopwatch2.reset();
        }
    }

    @Listen
    public void onConnect(ConnectEvent connectEvent) {
        this.flag3 = false;
        this.num4 = 0;
    }

    public int get1730() {
        if (minecraftClient.player == null) {
            return 0;
        }
        if (fastLatency.isToggled()) {
            return fastLatency.get1730();
        }
        PlayerListEntry playerListEntry = minecraftClient.player.networkHandler.getPlayerListEntry(minecraftClient.player.getGameProfile().getId());
        int latency = playerListEntry == null ? 0 : playerListEntry.getLatency();
        return latency == 0 ? this.num4 : latency;
    }

    public Stopwatch getStopwatch2615() {
        return this.stopwatch;
    }

    public Stopwatch getStopwatch2616() {
        return this.stopwatch2;
    }

    public int get2617() {
        return this.num3;
    }

    public long get2618() {
        return this.num;
    }

    public float get2619() {
        return this.val;
    }

    public float get2620() {
        return this.val2;
    }

    public float get2621() {
        return this.val2 / 20.0f;
    }

    public ServerInfo getServerInfo2622() {
        return this.serverInfo;
    }

    public void do2623(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    public boolean is2624() {
        return this.flag;
    }

    public boolean is2625() {
        return this.flag2;
    }
}
