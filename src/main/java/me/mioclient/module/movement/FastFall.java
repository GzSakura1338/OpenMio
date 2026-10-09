package me.mioclient.module.movement;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.HoleSnapSearchHelper4_6;
import me.mioclient.MixinLivingEntityHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.exploit.Phase;
import me.mioclient.module.movement.Speed;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/FastFall.class */
public class FastFall extends Module {
    public static Speed speed2 = (Speed) BaritoneHelper_3.baritoneHelper_4.getModule117(Speed.class);
    public static HoleSnap holeSnap = (HoleSnap) BaritoneHelper_3.baritoneHelper_4.getModule117(HoleSnap.class);
    public static AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static Phase phase = (Phase) BaritoneHelper_3.baritoneHelper_4.getModule117(Phase.class);
    public Setting<FastFallMode> mode;
    public Setting<Float> height;
    public Setting<Float> speed;
    public Setting<Boolean> stopInLiquids;
    public Setting<Boolean> onlyHole;
    public Setting<Boolean> safeDisable;
    public boolean flag;
    public boolean flag2;
    public boolean flag3;
    public boolean flag4;
    public boolean flag5;
    public boolean flag6;
    public double val;
    public double val2;
    public final Stopwatch stopwatch;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/FastFall$FastFallMode.class */
    public enum FastFallMode implements EnumSettingHelper {
        PLAIN("Plain"),
        STRICT("Strict"),
        INSTANT("Instant");

        public final String name;

        FastFallMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public FastFall() {
        super("FastFall", "Allows you to get in holes with ease!", Category.MOVEMENT, "reversestep");
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        this.flag = false;
        this.flag6 = false;
    }

    @Listen
    public void do32(TickPostEvent tickPostEvent) {
        if (this.safeDisable.getValue().booleanValue() && BaritoneHelper_3.holeSnapSearchHelper4_5.is2728() && this.flag6) {
            disable();
            return;
        }
        if (!is1195() || (!this.flag4 && this.onlyHole.getValue().booleanValue())) {
            do1196();
            return;
        }
        if (minecraftClient.player.isOnGround()) {
            this.flag2 = true;
            BaritoneHelper_3.inner.do2017(this);
            if (BaritoneHelper_3.antiPhaseSearchHelper4_2.get2231() > 3) {
                this.stopwatch.reset();
                this.flag3 = true;
                return;
            }
            return;
        }
        if (this.stopwatch.is419(500L)) {
            this.flag3 = !this.flag3;
            this.stopwatch.reset();
        }
        if (minecraftClient.player.getVelocity().y >= 0.0d || !this.flag2 || !this.flag3) {
            do1196();
        } else {
            BaritoneHelper_3.inner.do2018(this, 2.0f);
            this.flag6 = true;
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        boolean z = HoleSnapSearchHelper4_3.is2181() || (holeSnap.isToggled() && holeSnap.getHoleSnapData_2129() != null);
        if ((this.flag4 || !this.onlyHole.getValue().booleanValue()) && z) {
            this.flag5 = false;
            if (!((me.mioclient.mixin.ducks.DuckClientPlayerEntity) minecraftClient.player).lastOnGround() || minecraftClient.player.getVelocity().getY() > 0.0d || !is1193() || is1194()) {
                return;
            }
            Box withMaxY = minecraftClient.player.getBoundingBox().stretch(0.0d, -this.height.getValue().floatValue(), 0.0d).withMaxY(minecraftClient.player.getY());
            if (!minecraftClient.world.isSpaceEmpty(minecraftClient.player, withMaxY) || minecraftClient.world.containsFluid(withMaxY)) {
                Iterable<net.minecraft.util.shape.VoxelShape> blockCollisions = minecraftClient.world.getBlockCollisions(minecraftClient.player, withMaxY);
                AtomicReference atomicReference = new AtomicReference(Double.valueOf(minecraftClient.world.getBottomY()));
                blockCollisions.forEach(voxelShape -> {
                    atomicReference.set(Double.valueOf(Math.max(((Double) atomicReference.get()).doubleValue(), voxelShape.getMax(Direction.Axis.Y))));
                });
                if (((Double) atomicReference.get()).doubleValue() < minecraftClient.player.getY()) {
                    double doubleValue = ((Double) atomicReference.get()).doubleValue();
                    Vec3d velocity = minecraftClient.player.getVelocity();
                    boolean z2 = minecraftClient.world.isAir(BlockPos.ofFloored(minecraftClient.player.getX(), doubleValue, minecraftClient.player.getZ()).add(new Vec3i((int) velocity.x, -1, (int) velocity.z))) || !BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2616().is419(25L) || this.mode.getValue() == FastFallMode.PLAIN;
                    if (minecraftClient.player.getY() - doubleValue > 2.0 || z2) {
                        doubleValue = minecraftClient.player.getY() - 2.0;
                        this.flag5 = true;
                        float speedValue = this.mode.getValue() == FastFallMode.PLAIN ? this.speed.getValue().floatValue() : 1.0f;
                        if (speedValue != 0.0f) {
                            minecraftClient.player.setVelocity(velocity.withAxis(Direction.Axis.Y, -speedValue));
                        }
                    }
                    if (!z2) {
                        minecraftClient.player.setPosition(minecraftClient.player.getX(), doubleValue, minecraftClient.player.getZ());
                    }
                    phase.do1761();
                    this.flag6 = true;
                }
            }
        }
    }

    @Listen
    public void do28(MoveEvent moveEvent) {
        this.val = HoleSnapSearchHelper4_6.get2787();
        this.val2 = HoleSnapSearchHelper4_6.get2786();
        this.flag4 = BaritoneHelper_3.holeSnapSearchHelper4_5.is2723(minecraftClient.player.getBlockPos().down((int) this.val2));
        if (this.flag4 || !this.onlyHole.getValue().booleanValue()) {
            double longBitsToDouble = 0.3;
            boolean z = holeSnap.isToggled() && holeSnap.stopwatch2.is419(500L) && holeSnap.getHoleSnapData_2129() != null;
            if ((is1194() && is1193() && is1191() && minecraftClient.player.isOnGround()) || (this.flag5 && !z && is1192())) {
                MixinLivingEntityHelper_2.do2581(moveEvent.getVec3d689(), moveEvent.get515() * longBitsToDouble, moveEvent.get692(), moveEvent.get516() * longBitsToDouble);
            }
        }
    }

    public boolean is1191() {
        Box boundingBox = minecraftClient.player.getBoundingBox();
        Vec3d center = boundingBox.getCenter();
        double d = boundingBox.minX;
        double d2 = boundingBox.minZ;
        double d3 = boundingBox.maxX;
        double d4 = boundingBox.maxZ;
        HashMap<Vec3d, Vec3d> hashMap = new HashMap<>();
        hashMap.put(center, new Vec3d(center.x, center.y - 1.0, center.z));
        hashMap.put(new Vec3d(d, center.y, d2), new Vec3d(d, center.y - 1.0, d2));
        hashMap.put(new Vec3d(d3, center.y, d2), new Vec3d(d3, center.y - 1.0, d2));
        hashMap.put(new Vec3d(d, center.y, d4), new Vec3d(d, center.y - 1.0, d4));
        hashMap.put(new Vec3d(d3, center.y, d4), new Vec3d(d3, center.y - 1.0, d4));
        for (Vec3d vec3d : hashMap.keySet()) {
            BlockHitResult raycast = minecraftClient.world.raycast(new RaycastContext(vec3d, (Vec3d) hashMap.get(vec3d), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, minecraftClient.player));
            if (raycast != null && raycast.getType() == HitResult.Type.BLOCK) {
                return false;
            }
        }
        if (!is1192()) {
            return false;
        }
        BlockState blockState = minecraftClient.world.getBlockState(BlockPos.ofFloored(minecraftClient.player.getX(), minecraftClient.player.getY() - 1.0, minecraftClient.player.getZ()));
        return blockState == null || blockState.getBlock() == Blocks.AIR;
    }

    public boolean is1192() {
        Vec3d velocity = minecraftClient.player.getVelocity();
        double d = velocity.x;
        double d2 = velocity.z;
        double signum = Math.abs(d2) > Math.abs(d) ? 0.0d : Math.signum(d);
        BlockState blockState = minecraftClient.world.getBlockState(BlockPos.ofFloored(minecraftClient.player.getX() + signum, minecraftClient.player.getY() - 1.0, minecraftClient.player.getZ() + (Math.abs(signum) > Math.abs(d2) ? 0.0d : Math.signum(d2))));
        if (blockState != null) {
            return ((me.mioclient.mixin.ducks.DuckAbstractBlock) (Object) blockState.getBlock()).isCollidable();
        }
        return true;
    }

    public boolean is1193() {
        if ((!speed2.isToggled() || speed2.mode.getValue() == Speed.SpeedPredicateMode.speedPredicateMode4 || speed2.mode.getValue() == Speed.SpeedPredicateMode.speedPredicateMode3) && !minecraftClient.player.isSneaking() && !minecraftClient.player.isFallFlying() && !minecraftClient.player.isInsideWall() && ((!HoleSnapSearchHelper4.is2007(minecraftClient.player) || !this.stopInLiquids.getValue().booleanValue()) && BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(500L))) {
            if (!(minecraftClient.world.getBlockState(BlockPos.ofFloored(minecraftClient.player.getPos())).getBlock() instanceof BedBlock)) {
                return true;
            }
        }
        return false;
    }

    public boolean is1194() {
        return this.mode.getValue() == FastFallMode.STRICT;
    }

    public boolean is1195() {
        return is1193() && is1194() && this.val != 0.0d && this.val2 != 0.0d && this.val2 <= ((double) this.height.getValue().floatValue());
    }

    public void do1196() {
        if (is1194()) {
            this.flag2 = false;
            BaritoneHelper_3.inner.do2017(this);
        }
    }
}
