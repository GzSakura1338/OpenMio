package me.mioclient.module.misc;

import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.mixin.ducks.DuckPlayerPositionLookS2CPacket;
import me.mioclient.module.Module;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/misc/NoRotate.class */
public class NoRotate extends Module {
    public Setting<Boolean> inBlocks;
    public Setting<Boolean> stopInWebs;

    public NoRotate() {
        super("NoRotate", "Cancels the rotations server sets for you.", Category.MISC, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if (!this.inBlocks.getValue().booleanValue() || HoleSnapSearchHelper4_3.is2181() || minecraftClient.player.isHoldingOntoLadder() || !(sendImmediatelyEvent.getPacket904() instanceof PlayerMoveC2SPacket)) {
            return;
        }
        if (this.stopInWebs.getValue().booleanValue() && HoleSnapSearchHelper4.is2005(minecraftClient.player)) {
            return;
        }
        BlockState blockState = minecraftClient.world.getBlockState(minecraftClient.player.getBlockPos());
        if (blockState.getBlock() instanceof FallingBlock) {
            return;
        }
        VoxelShape collisionShape = blockState.getCollisionShape(minecraftClient.world, BlockPos.ORIGIN);
        if (collisionShape.isEmpty() || minecraftClient.player.getY() - Math.floor(minecraftClient.player.getY()) >= collisionShape.getBoundingBox().maxY) {
            return;
        }
        if (collisionShape.getBoundingBox().offset(minecraftClient.player.getBlockPos()).intersects(minecraftClient.player.getBoundingBox().expand(-0.05))) {
            sendImmediatelyEvent.do1162();
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (minecraftClient.isInSingleplayer() || minecraftClient.player.isHoldingOntoLadder()) {
            return;
        }
        if (!SearchHelper4_8.is724() || minecraftClient.player.age > 20) {
            DuckPlayerPositionLookS2CPacket packet904 = (DuckPlayerPositionLookS2CPacket)(channelRead0Event.getPacket904());
            if (packet904 instanceof PlayerPositionLookS2CPacket) {
                DuckPlayerPositionLookS2CPacket duckPlayerPositionLookS2CPacket = (DuckPlayerPositionLookS2CPacket)((PlayerPositionLookS2CPacket) packet904);
                if (is1469() || minecraftClient.player.age <= 5) {
                    return;
                }
                float yaw = minecraftClient.player.getYaw();
                float pitch = minecraftClient.player.getPitch();
                if (((PlayerPositionLookS2CPacket) duckPlayerPositionLookS2CPacket).getFlags().contains(PositionFlag.X_ROT)) {
                    yaw = 0.0f;
                }
                if (((PlayerPositionLookS2CPacket) duckPlayerPositionLookS2CPacket).getFlags().contains(PositionFlag.Y_ROT)) {
                    pitch = 0.0f;
                }
                duckPlayerPositionLookS2CPacket.setYaw(yaw);
                duckPlayerPositionLookS2CPacket.setPitch(pitch);
            }
        }
    }
}
