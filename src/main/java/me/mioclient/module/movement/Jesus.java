package me.mioclient.module.movement;

import me.mioclient.EnumSettingHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.VoxelShapeEvent;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.mixin.ducks.DuckPlayerMoveC2SPacket;
import me.mioclient.module.Module;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShapes;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/Jesus.class */
public class Jesus extends Module {
    public Setting<JesusMode> mode;
    public Setting<Boolean> strict;
    public Setting<Float> ascending;
    public final double[] doubleArr;
    public int num;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/Jesus$JesusMode.class */
    public enum JesusMode implements EnumSettingHelper {
        SOLID("Solid"),
        DOLPHIN("Dolphin");

        public final String name;

        JesusMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public Jesus() {
        super("Jesus", "Allows you to walk on water.", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.doubleArr = new double[]{0.08232659236482401, 0.13927999979019162, 0.1654239999427795, 0.11427136035293574, 0.04194693730418576, 0.01236341326161235};
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        this.num = 0;
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (is240() && minecraftClient.player.isTouchingWater()) {
            minecraftClient.player.addVelocity(0.0d, this.mode.getValue() == JesusMode.DOLPHIN ? 0.0399 : 0.08 * this.ascending.getValue().floatValue(), 0.0d);
        }
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if (this.mode.getValue() == JesusMode.SOLID && is240()) {
            PlayerMoveC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof PlayerMoveC2SPacket ? (PlayerMoveC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
            if (packet904 instanceof PlayerMoveC2SPacket) {
                PlayerMoveC2SPacket playerMoveC2SPacket = packet904;
                if (this.strict.getValue().booleanValue() && is241()) {
                    ((DuckPlayerMoveC2SPacket) playerMoveC2SPacket).setOnGround(false);
                    double y = playerMoveC2SPacket.getY(0.0d);
                    double[] dArr = this.doubleArr;
                    int i = this.num;
                    this.num = i + 1;
                    ((DuckPlayerMoveC2SPacket) playerMoveC2SPacket).setY(y + dArr[i]);
                    this.num %= this.doubleArr.length;
                }
            }
        }
    }

    @Listen
    public void onEvent(VoxelShapeEvent voxelShapeEvent) {
        if (is1469() || minecraftClient.player.isTouchingWater() || this.mode.getValue() != JesusMode.SOLID || !is240()) {
            return;
        }
        if (voxelShapeEvent.getBlockState670().isOf(Blocks.WATER)) {
            voxelShapeEvent.do666(VoxelShapes.cuboid(new Box(0.0d, 0.0d, 0.0d, 1.0, 0.98, 1.0)));
        }
    }

    public boolean is240() {
        return (is1469() || minecraftClient.player.input == null || minecraftClient.player.hasVehicle() || minecraftClient.player.input.sneaking) ? false : true;
    }

    public boolean is241() {
        return minecraftClient.world.getBlockState(BlockPos.ofFloored(minecraftClient.player.getPos().subtract(0.0d, 0.01, 0.0d))).isOf(Blocks.WATER);
    }
}
