package me.mioclient.module.movement;

import me.mioclient.AutoCrystalMode_6;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.KeyPearlMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.AttackHookPostEvent;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.ExplosionVelocityEvent;
import me.mioclient.event.InteractBlockEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.SprintUpdateEvent;
import me.mioclient.feature.Event_3;
import me.mioclient.feature.MotionEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.movement.ElytraFly;
import me.mioclient.module.player.RotationLock;
import me.mioclient.module.player.Scaffold;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/Sprint.class */
public class Sprint extends Module {
    public static final Scaffold scaffold = (Scaffold) BaritoneHelper_3.baritoneHelper_4.getModule117(Scaffold.class);
    public static final ElytraFly efly = (ElytraFly) BaritoneHelper_3.baritoneHelper_4.getModule117(ElytraFly.class);
    public static final LongJump longJump = (LongJump) BaritoneHelper_3.baritoneHelper_4.getModule117(LongJump.class);
    public static final FastFall fastFall = (FastFall) BaritoneHelper_3.baritoneHelper_4.getModule117(FastFall.class);
    public static final Speed speed = (Speed) BaritoneHelper_3.baritoneHelper_4.getModule117(Speed.class);
    public static final FastWeb fastWeb = (FastWeb) BaritoneHelper_3.baritoneHelper_4.getModule117(FastWeb.class);
    public static final AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static RotationLock rotationLock = (RotationLock) BaritoneHelper_3.baritoneHelper_4.getModule117(RotationLock.class);
    public Setting<SprintPredicateMode> mode;
    public Setting<Boolean> forceSprint;
    public Setting<Boolean> vanillaSlowdown;
    public Setting<Boolean> boost;
    public Setting<Float> boostSpeed;
    public Setting<Boolean> stopInLiquids;
    public Setting<Boolean> directionSpoof;
    public Setting<Boolean> keep;
    public final Stopwatch stopwatch;
    public float val;
    public boolean flag;
    public boolean flag2;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/Sprint$SprintPredicateMode.class */
    public enum SprintPredicateMode implements EnumSettingHelper {
        LEGIT("Legit"),
        RAGE("Rage"),
        INSTANT("Instant");

        public final String name;

        SprintPredicateMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public Sprint() {
        super("Sprint", "Sprints automatically.", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.val = 0.0f;
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return FontsSearchHelper4.getString1684(this.mode.getValue());
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (channelRead0Event.getPacket904() instanceof PlayerPositionLookS2CPacket) {
            this.val = 0.0f;
        }
    }

    @Listen
    public void onInteractBlock(InteractBlockEvent interactBlockEvent) {
        if (!BaritoneHelper_3.obstaclePasserHelper.is702() && antiCheat.movementSync.getValue().booleanValue() && antiCheat.rotations.getValue() == AutoCrystalMode_6.SILENT && this.directionSpoof.getValue().booleanValue() && BaritoneHelper_3.searchHelper4_8.getElytraFlyData2475() == null && HoleSnapSearchHelper4_3.is2181() && HoleSnapSearchHelper4_3.get2517() != minecraftClient.player.getYaw() && !this.flag2) {
            AutoSignSearchHelper4.do2571(new PlayerMoveC2SPacket.Full(minecraftClient.player.getX(), minecraftClient.player.getY(), minecraftClient.player.getZ(), minecraftClient.player.getYaw(), minecraftClient.player.getPitch(), minecraftClient.player.isOnGround()));
        }
    }

    @Listen
    public void do598(ExplosionVelocityEvent explosionVelocityEvent) {
        if (BaritoneHelper_3.obstaclePasserHelper.is702()) {
            return;
        }
        Vec3d vec3d = new Vec3d(explosionVelocityEvent.get515(), explosionVelocityEvent.get692(), explosionVelocityEvent.get516());
        if (HoleSnapSearchHelper4_3.is2181() && BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(750L) && this.boost.getValue().booleanValue() && Math.sqrt(minecraftClient.player.squaredDistanceTo(vec3d)) <= 6.0) {
            this.stopwatch.reset();
            this.val = ((float) Math.hypot(explosionVelocityEvent.get767(), explosionVelocityEvent.get771())) * this.boostSpeed.getValue().floatValue();
        }
    }

    @Listen(get219= Helper_7.num2)
    public void do31(MotionEvent motionEvent) {
        if (BaritoneHelper_3.obstaclePasserHelper.is702() || is1469() || is2286() || rotationLock.isToggled() || !this.directionSpoof.getValue().booleanValue() || !HoleSnapSearchHelper4_3.is2181() || minecraftClient.player.isFallFlying()) {
            return;
        }
        motionEvent.setYaw(HoleSnapSearchHelper4_3.get2517());
    }

    @Listen
    public void onSprintUpdate(SprintUpdateEvent sprintUpdateEvent) {
        if (BaritoneHelper_3.obstaclePasserHelper.is702() || is1469() || is2286()) {
            return;
        }
        if (this.mode.getValue() != SprintPredicateMode.INSTANT || this.forceSprint.getValue().booleanValue()) {
            do2287(is2181());
        }
    }

    @Listen(get219= 1000)
    public void do28(MoveEvent moveEvent) {
        if (BaritoneHelper_3.obstaclePasserHelper.is702() || is1469() || is2286() || this.mode.getValue() != SprintPredicateMode.INSTANT || HoleSnapSearchHelper4.is2012() || minecraftClient.player.isFallFlying() || minecraftClient.player.getAbilities().flying || minecraftClient.player.isTouchingWater() || minecraftClient.player.isInLava() || minecraftClient.player.isSneaking() || minecraftClient.player.isInSwimmingPose() || fastFall.flag || longJump.isToggled() || speed.isToggled()) {
            return;
        }
        double d = HoleSnapSearchHelper4_3.get2511(this.vanillaSlowdown.getValue().booleanValue());
        if (BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(500L)) {
            d = Math.max(d, Math.hypot(moveEvent.get515(), moveEvent.get516()));
        }
        if (this.vanillaSlowdown.getValue().booleanValue()) {
            net.minecraft.block.Block r0 = minecraftClient.world.getBlockState(((me.mioclient.mixin.ducks.DuckEntity)(Object) minecraftClient.player).mio$getVelocityAffectingPos()).getBlock();
            if (r0 == Blocks.SLIME_BLOCK) {
                d *= 0.7;
            }
            d *= r0.getVelocityMultiplier();
        }
        if (this.boost.getValue().booleanValue() && !this.stopwatch.is419(500L)) {
            d += this.val;
        }
        HoleSnapSearchHelper4_3.getDoubleArray2507(moveEvent, d);
    }

    @Listen
    public void do33(Event_3 event_3) {
        if (!BaritoneHelper_3.obstaclePasserHelper.is702() && this.directionSpoof.getValue().booleanValue() && HoleSnapSearchHelper4_3.is2181() && !minecraftClient.player.isFallFlying()) {
            if ((event_3.is2925() || minecraftClient.player.isInSwimmingPose()) && !is2286()) {
                if ((!minecraftClient.player.isCrawling() || event_3.is2925()) && !rotationLock.isToggled()) {
                    event_3.setYaw(HoleSnapSearchHelper4_3.get2517());
                    event_3.do1162();
                }
            }
        }
    }

    @Listen
    public void onAttackHookPost(AttackHookPostEvent attackHookPostEvent) {
        if (!BaritoneHelper_3.obstaclePasserHelper.is702() && this.keep.getValue().booleanValue()) {
            if (attackHookPostEvent.getKeyPearlMode1472() == KeyPearlMode.Pre) {
                this.flag = minecraftClient.player.isSprinting();
                if (this.flag) {
                    AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING, 0);
                    return;
                }
                return;
            }
            if (this.flag) {
                minecraftClient.player.setSprinting(true);
                AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.START_SPRINTING, 0);
            }
        }
    }

    public boolean is2181() {
        if (is2286()) {
            return false;
        }
        if (this.directionSpoof.getValue().booleanValue() && (BaritoneHelper_3.searchHelper4_8.getElytraFlyData2475() != null || minecraftClient.player.isFallFlying())) {
            return false;
        }
        boolean z = this.mode.getValue() == SprintPredicateMode.LEGIT || minecraftClient.player.isSubmergedInWater();
        if (HoleSnapSearchHelper4.is2012()) {
            return false;
        }
        if ((z && (minecraftClient.player.input.movementForward <= 0.0f || minecraftClient.player.horizontalCollision)) || is2288()) {
            return false;
        }
        if (fastWeb.is1534() && HoleSnapSearchHelper4.is2005(minecraftClient.player) && (BaritoneHelper_3.antiPhaseSearchHelper4_2.get2231() < 2 || minecraftClient.player.input.jumping)) {
            return false;
        }
        return (minecraftClient.player.input.movementForward == 0.0f && minecraftClient.player.input.movementSideways == 0.0f) ? false : true;
    }

    public boolean is2286() {
        if (BaritoneHelper_3.obstaclePasserHelper.is709() && BaritoneHelper_3.obstaclePasserHelper.is702()) {
            return true;
        }
        return (scaffold.isToggled() && scaffold.stopSprinting.getValue().booleanValue()) || (HoleSnapSearchHelper4.is2007(minecraftClient.player) && this.stopInLiquids.getValue().booleanValue()) || (efly.isToggled() && efly.mode.getValue() == ElytraFly.ElytraFlyPredicateMode.BOUNCE && efly.is606());
    }

    public void do2287(boolean z) {
        try {
            minecraftClient.player.setSprinting(z);
        } catch (Exception e) {
        }
    }

    public boolean is2288() {
        if (minecraftClient.player.isOnGround()) {
            return false;
        }
        return BlockPos.stream(minecraftClient.player.getBoundingBox().stretch(0.0d, -0.1, 0.0d)).anyMatch(blockPos -> {
            return minecraftClient.world.isWater(blockPos);
        });
    }
}
