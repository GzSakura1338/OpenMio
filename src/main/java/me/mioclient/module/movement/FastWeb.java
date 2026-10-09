package me.mioclient.module.movement;

import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.KeyPearlMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.SprintUpdateEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/FastWeb.class */
public class FastWeb extends Module {
    public static final AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public Setting<FastWebPredicateMode> mode;
    public Setting<Boolean> horizontal;
    public Setting<Float> speed;
    public Setting<Boolean> vertical;
    public Setting<Float> vSpeed;
    public Setting<Boolean> upwards;
    public boolean flag;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/FastWeb$FastWebPredicateMode.class */
    public enum FastWebPredicateMode implements EnumSettingHelper {
        PLAIN("Plain"),
        STRICT("Strict"),
        LAGBACK("LagBack"),
        GRIM("Grim");

        public final String name;

        FastWebPredicateMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public FastWeb() {
        super("FastWeb", "Makes you faster in cobwebs.", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.flag = false;
        setDrawn(false);
    }

    @Listen
    public void do31(MotionEvent motionEvent) {
        if (motionEvent.getKeyPearlMode1472() != KeyPearlMode.Pre) {
            return;
        }
        Vec3d vec3d = new Vec3d(minecraftClient.player.getX() - minecraftClient.player.prevX, minecraftClient.player.getY() - minecraftClient.player.prevY, minecraftClient.player.getZ() - minecraftClient.player.prevZ);
        if (is1534() && vec3d.lengthSquared() > 0.0d) {
            BlockPos.stream(minecraftClient.player.getBoundingBox()).map((v0) -> {
                return v0.toImmutable();
            }).filter(blockPos -> {
                return minecraftClient.world.getBlockState(blockPos).isOf(Blocks.COBWEB);
            }).forEach(blockPos2 -> {
                if (!antiCheat.is238()) {
                    AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, blockPos2, Direction.UP);
                }
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos2, Direction.UP);
            });
        }
        if (this.mode.getValue() == FastWebPredicateMode.GRIM && minecraftClient.player.input.sneaking && !minecraftClient.player.isOnGround() && HoleSnapSearchHelper4.is2005(minecraftClient.player)) {
            motionEvent.setY(minecraftClient.player.getY() + Math.random() + 1.0);
        }
    }

    @Listen(get219= Helper_7.num4)
    public void onSprintUpdate(SprintUpdateEvent sprintUpdateEvent) {
        if (is1534() && !minecraftClient.player.isOnGround() && minecraftClient.player.input.jumping && HoleSnapSearchHelper4.is2005(minecraftClient.player)) {
            minecraftClient.player.setSprinting(false);
        }
    }

    @Listen
    public void do28(MoveEvent moveEvent) {
        if (!BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(500L) || !HoleSnapSearchHelper4.is2005(minecraftClient.player) || this.mode.getValue() == FastWebPredicateMode.GRIM) {
            reset();
            return;
        }
        if (this.vertical.getValue().booleanValue()) {
            if (!minecraftClient.player.input.sneaking || minecraftClient.player.isOnGround()) {
                reset();
            } else if (is1194()) {
                this.flag = true;
                BaritoneHelper_3.inner.do2018(this, this.vSpeed.getValue().floatValue() * 6.0f);
            } else {
                reset();
                minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, -this.vSpeed.getValue().floatValue()));
                moveEvent.setY(-this.vSpeed.getValue().floatValue());
            }
            if (this.upwards.getValue().booleanValue() && minecraftClient.player.input.jumping && !is1194()) {
                minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, this.vSpeed.getValue().floatValue()));
                moveEvent.setY(this.vSpeed.getValue().floatValue());
            }
        }
        if (HoleSnapSearchHelper4_3.is2181() && this.horizontal.getValue().booleanValue()) {
            double[] doubleArray2507 = HoleSnapSearchHelper4_3.getDoubleArray2507(moveEvent, HoleSnapSearchHelper4_3.get2511(true) * this.speed.getValue().floatValue());
            minecraftClient.player.setVelocity(doubleArray2507[0], minecraftClient.player.getVelocity().y, doubleArray2507[1]);
        }
    }

    public boolean is1194() {
        return this.mode.getValue() == FastWebPredicateMode.STRICT;
    }

    public boolean is1533() {
        return this.flag;
    }

    public void reset() {
        BaritoneHelper_3.inner.do2017(this);
        this.flag = false;
    }

    public boolean is1534() {
        return isToggled() && this.mode.getValue() == FastWebPredicateMode.GRIM;
    }
}
