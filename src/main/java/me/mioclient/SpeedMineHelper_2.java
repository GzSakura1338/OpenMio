package me.mioclient;

import me.mioclient.event.ChannelRead0Event;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.player.SpeedMine;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SpeedMineHelper_2.class */
public final class SpeedMineHelper_2 {
    public static final long num = 750;
    public final SpeedMine speedMine;
    public final Stopwatch stopwatch = new Stopwatch();
    public boolean flag;
    public BlockPos blockPos;

    public SpeedMineHelper_2(SpeedMine speedMine) {
        this.speedMine = speedMine;
    }

    public void onChannelRead0(ChannelRead0Event channelRead0Event) {
        BlockUpdateS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof BlockUpdateS2CPacket ? (BlockUpdateS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof BlockUpdateS2CPacket) {
            BlockUpdateS2CPacket blockUpdateS2CPacket = packet904;
            if (blockUpdateS2CPacket.getState().isAir() && blockUpdateS2CPacket.getPos().equals(this.blockPos)) {
                reset();
            }
        }
        ChunkDeltaUpdateS2CPacket packet9042 = (channelRead0Event.getPacket904()) instanceof ChunkDeltaUpdateS2CPacket ? (ChunkDeltaUpdateS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet9042 instanceof ChunkDeltaUpdateS2CPacket) {
            packet9042.visitUpdates((blockPos, blockState) -> {
                if (blockPos.equals(this.blockPos) && blockState.isAir()) {
                    reset();
                }
            });
        }
    }

    public void do2081(BlockPos blockPos) {
        if (this.flag) {
            return;
        }
        this.flag = true;
        this.blockPos = blockPos;
        this.stopwatch.reset();
    }

    public void do466() {
        if (this.flag) {
            if (!SearchHelper4_7.is2435(this.blockPos) || SearchHelper4_7.is2446(this.blockPos)) {
                reset();
            }
            if (this.blockPos == null || this.blockPos.equals(this.speedMine.getBlockPos1053())) {
                return;
            }
            reset();
        }
    }

    public boolean is2082() {
        if (!this.flag || this.blockPos == null) {
            return false;
        }
        long max = Math.max((long) (Math.ceil((BaritoneHelper_3.holeSnapSearchHelper4_4.get1730() * 15.0f) / 50.0f) * 50.0), 750L);
        if (this.speedMine.tPSSync.getValue().booleanValue()) {
            max = (long) (((float) max) / BaritoneHelper_3.holeSnapSearchHelper4_4.get2621());
        }
        return this.stopwatch.is419((long) (((float) max) + this.speedMine.instantDelay.getValue().floatValue()));
    }

    public void reset() {
        this.flag = false;
        this.blockPos = null;
    }
}
