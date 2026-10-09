package me.mioclient.module.movement;

import me.mioclient.ArmorSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.ElytraFlyHelper_4;
import me.mioclient.EnumSettingHelper;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.KeyPearlMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SpeedHelper;
import me.mioclient.SpeedHelper_2;
import me.mioclient.SpeedHelper_3;
import me.mioclient.SpeedHelper_4;
import me.mioclient.SpeedHelper_5;
import me.mioclient.SpeedHelper_6;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.ExplosionVelocityEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.MoveEvent_2;
import me.mioclient.event.SprintUpdateEvent;
import me.mioclient.feature.Event_3;
import me.mioclient.feature.MotionEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/Speed.class */
public class Speed extends Module {
    public static ObstaclePasser obstaclePasser = (ObstaclePasser) BaritoneHelper_3.baritoneHelper_4.getModule117(ObstaclePasser.class);
    public Setting<SpeedPredicateMode> mode;
    public Setting<Float> speed;
    public Setting<Boolean> boost;
    public Setting<Boolean> inLiquid;
    public Setting<Boolean> useTimer;
    public Setting<Boolean> pauseInBlocks;
    public boolean flag;
    public final Stopwatch stopwatch;
    public final ElytraFlyHelper_4<SpeedPredicateMode, SpeedHelper> elytraFlyHelper_4;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/Speed$Inner.class */
    class Inner extends SpeedHelper {
        public Inner(Speed speed, Speed speed2) {
            super(speed2);
        }

        @Override // me.mioclient.SpeedHelper
        public void do242(MoveEvent moveEvent) {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/Speed$SpeedPredicateMode.class */
    public static enum SpeedPredicateMode implements EnumSettingHelper {
        speedPredicateMode {
            @Override // me.mioclient.EnumSettingHelper
            public String getName() {
                return "Strafe";
            }
        },
        speedPredicateMode2 {
            @Override // me.mioclient.EnumSettingHelper
            public String getName() {
                return "StrafeStrict";
            }
        },
        speedPredicateMode3 {
            @Override // me.mioclient.EnumSettingHelper
            public String getName() {
                return "Vanilla";
            }
        },
        speedPredicateMode4 {
            @Override // me.mioclient.EnumSettingHelper
            public String getName() {
                return "OnGround";
            }
        },
        speedPredicateMode5 {
            @Override // me.mioclient.EnumSettingHelper
            public String getName() {
                return "Grim";
            }
        },
        speedPredicateMode6 {
            @Override // me.mioclient.EnumSettingHelper
            public String getName() {
                return "None";
            }
        };

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return null;
        }
    }

    public Speed() {
        super("Speed", "Makes you move faster.", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.elytraFlyHelper_4 = new ElytraFlyHelper_4<>(this.mode);
        ElytraFlyHelper_4 elytraFlyHelper_4 = this.elytraFlyHelper_4;
        elytraFlyHelper_4.do997(SpeedPredicateMode.speedPredicateMode, new SpeedHelper_6(this));
        ElytraFlyHelper_4 elytraFlyHelper_42 = this.elytraFlyHelper_4;
        elytraFlyHelper_42.do997(SpeedPredicateMode.speedPredicateMode2, new SpeedHelper_2(this));
        ElytraFlyHelper_4 elytraFlyHelper_43 = this.elytraFlyHelper_4;
        elytraFlyHelper_43.do997(SpeedPredicateMode.speedPredicateMode3, new SpeedHelper_3(this));
        ElytraFlyHelper_4 elytraFlyHelper_44 = this.elytraFlyHelper_4;
        elytraFlyHelper_44.do997(SpeedPredicateMode.speedPredicateMode4, new SpeedHelper_4(this));
        ElytraFlyHelper_4 elytraFlyHelper_45 = this.elytraFlyHelper_4;
        elytraFlyHelper_45.do997(SpeedPredicateMode.speedPredicateMode5, new SpeedHelper_5(this));
        ElytraFlyHelper_4 elytraFlyHelper_46 = this.elytraFlyHelper_4;
        elytraFlyHelper_46.do997(SpeedPredicateMode.speedPredicateMode6, new Inner(this, this));
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return this.mode.getValue().getName();
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.elytraFlyHelper_4.getObject996().onEnable();
    }

    @Listen
    public void onSprintUpdate(SprintUpdateEvent sprintUpdateEvent) {
        if (this.mode.getValue() != SpeedPredicateMode.speedPredicateMode2 || is130()) {
            return;
        }
        minecraftClient.player.setSprinting(true);
    }

    @Listen
    public void do33(Event_3 event_3) {
        if (event_3.is2925()) {
            return;
        }
        this.elytraFlyHelper_4.getObject996().do389();
    }

    @Listen
    public void do242(MoveEvent moveEvent) {
        if (!BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2616().is419(10L) || this.flag) {
            return;
        }
        this.elytraFlyHelper_4.getObject996().do242(moveEvent);
    }

    @Listen
    public void onMove(MoveEvent_2 moveEvent_2) {
        if (moveEvent_2.getKeyPearlMode1472() == KeyPearlMode.Post && minecraftClient.player.isOnGround() && moveEvent_2.get990() > 0.6) {
            this.flag = true;
        }
    }

    @Listen(get219= Helper_7.num2)
    public void do388(MotionEvent motionEvent) {
        this.flag = false;
        if (BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2616().is419(10L)) {
            this.elytraFlyHelper_4.getObject996().do388(motionEvent);
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (channelRead0Event.getPacket904() instanceof PlayerPositionLookS2CPacket) {
            reset();
        }
    }

    @Listen
    public void do598(ExplosionVelocityEvent explosionVelocityEvent) {
        Vec3d vec3d = new Vec3d(explosionVelocityEvent.get515(), explosionVelocityEvent.get692(), explosionVelocityEvent.get516());
        double d = ArmorSearchHelper4.get1900(vec3d, minecraftClient.player, minecraftClient.player.getBoundingBox(), 6.0, true, (BlockPos) null, (BlockPos) null);
        if (HoleSnapSearchHelper4_3.is2181() && BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(500L) && this.boost.getValue().booleanValue() && Math.sqrt(minecraftClient.player.squaredDistanceTo(vec3d)) <= 6.0 && d >= 4.0) {
            this.stopwatch.reset();
        }
    }

    public void reset() {
        this.elytraFlyHelper_4.getObject996().val3 = 0.0d;
        this.elytraFlyHelper_4.getObject996().val2 = 0.0d;
        this.elytraFlyHelper_4.getObject996().num = 4;
        if (this.mode.getValue() != SpeedPredicateMode.speedPredicateMode4) {
            return;
        }
        this.elytraFlyHelper_4.getObject996().num = 2;
    }

    public boolean is1814() {
        return HoleSnapSearchHelper4.is2007(minecraftClient.player) && !this.inLiquid.getValue().booleanValue();
    }

    public boolean is1815() {
        Box boundingBox = minecraftClient.player.getBoundingBox();
        return minecraftClient.world.canCollide(minecraftClient.player, new Box((double) minecraftClient.player.getBlockPos().getX(), boundingBox.minY, (double) minecraftClient.player.getBlockPos().getZ(), ((double) minecraftClient.player.getBlockPos().getX()) + 1.0, boundingBox.maxY, ((double) minecraftClient.player.getBlockPos().getZ()) + 1.0).contract(1e-07)) && this.pauseInBlocks.getValue().booleanValue();
    }

    public boolean is2824() {
        if (obstaclePasser == null) {
            return false;
        }
        return obstaclePasser.is929();
    }

    public boolean is130() {
        return is1469() || is1815() || is1814() || HoleSnapSearchHelper4.is2012() || minecraftClient.player.isFallFlying() || minecraftClient.player.isSpectator();
    }
}
