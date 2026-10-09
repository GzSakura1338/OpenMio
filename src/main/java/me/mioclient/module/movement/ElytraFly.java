package me.mioclient.module.movement;

import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.ElytraFlyData;
import me.mioclient.ElytraFlyHelper;
import me.mioclient.ElytraFlyHelper_2;
import me.mioclient.ElytraFlyHelper_3;
import me.mioclient.ElytraFlyHelper_4;
import me.mioclient.ElytraFlyHelper_5;
import me.mioclient.ElytraFlyHelper_6;
import me.mioclient.ElytraFlyHelper_7;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FireworksHelper;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.HoleSnapEvent;
import me.mioclient.HoleSnapMode;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.InteractItemEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.PlayEvent;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickEvent_2;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Event_3;
import me.mioclient.feature.MotionEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.mixin.ducks.DuckMinecraftClient;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.misc.MiddleClick;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/ElytraFly.class */
public class ElytraFly extends Module {
    public static final MiddleClick middleClick = (MiddleClick) BaritoneHelper_3.baritoneHelper_4.getModule117(MiddleClick.class);
    public static final ObstaclePasser obstaclePasser = (ObstaclePasser) BaritoneHelper_3.baritoneHelper_4.getModule117(ObstaclePasser.class);
    public static final NoSlow noSlow = (NoSlow) BaritoneHelper_3.baritoneHelper_4.getModule117(NoSlow.class);
    public static final AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static boolean flag = false;
    public Setting<ElytraFlyPredicateMode> mode;
    public Setting<ElytraFlyMode_2> takeoff;
    public Setting<Boolean> factorize;
    public Setting<Boolean> always;
    public Setting<Boolean> nCPBoost;
    public Setting<Float> minBoost;
    public Setting<Integer> verticalBoost;
    public Setting<Float> limit;
    public Setting<Boolean> autoJump;
    public Setting<Boolean> inLava;
    public Setting<Float> speed;
    public Setting<Boolean> autoBoost;
    public Setting<Integer> minY;
    public Setting<Integer> add;
    public Setting<Float> boostPitch;
    public Setting<Float> speed3;
    public Setting<ElytraFlyMode> vertical;
    public Setting<Float> vSpeed;
    public Setting<Float> vPitch;
    public Setting<Boolean> antiKick;
    public Setting<Float> glide;
    public Setting<Boolean> spoofPitch;
    public Setting<Float> pitch;
    public Setting<Boolean> antiAfk;
    public Setting<Boolean> vertical2;
    public Setting<Boolean> accelerate;
    public Setting<Float> verAccelMin;
    public Setting<Float> accelMin;
    public Setting<Float> accelTime;
    public Setting<Float> start;
    public Setting<Float> speed2;
    public Setting<Integer> deployTime;
    public Setting<Boolean> pitchLock;
    public Setting<Boolean> silent;
    public Setting<Float> pitch2;
    public Setting<Boolean> compensate;
    public Setting<Boolean> infDurability;
    public Setting<Boolean> muteElytra;
    public Setting<Boolean> grimDurability;
    public final ElytraFlyHelper_4<ElytraFlyPredicateMode, ElytraFlyHelper> elytraFlyHelper_4;
    public final Stopwatch stopwatch;
    public final Stopwatch stopwatch2;
    public final Stopwatch stopwatch3;
    public final Stopwatch stopwatch4;
    public final Stopwatch stopwatch5;
    public boolean flag2;
    public boolean flag3;
    public boolean flag4;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/ElytraFly$ElytraFlyMode.class */
    public enum ElytraFlyMode implements EnumSettingHelper {
        NONE("None"),
        PLAIN("Plain"),
        MANUAL("Manual"),
        STRICT("Strict");

        public final String name;

        ElytraFlyMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/ElytraFly$ElytraFlyMode_2.class */
    public enum ElytraFlyMode_2 implements EnumSettingHelper {
        NONE("None"),
        PLAIN("Plain"),
        STRICT("Strict");

        public final String name;

        ElytraFlyMode_2(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/ElytraFly$ElytraFlyPredicateMode.class */
    public enum ElytraFlyPredicateMode implements EnumSettingHelper {
        CONTROL("Control"),
        BOOST("Boost"),
        PACKET("Packet"),
        STRICT("Strict"),
        BOUNCE("Bounce");

        public final String name;

        ElytraFlyPredicateMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public ElytraFly() {
        super("ElytraFly", "Turns you into a block game boeing.", Category.MOVEMENT, "elytraflight");
        PhaseESPHelper.do1351(this);
        this.elytraFlyHelper_4 = new ElytraFlyHelper_4<>(this.mode);
        this.stopwatch = new Stopwatch();
        this.stopwatch2 = new Stopwatch();
        this.stopwatch3 = new Stopwatch();
        this.stopwatch4 = new Stopwatch();
        this.stopwatch5 = new Stopwatch();
        this.speed.do2329("LavaSpeed");
        this.vertical2.do2329("VerticalPacket");
        this.speed2.do2329("StrictSpeed");
        this.pitch2.do2329("BouncePitch");
        this.minBoost.getSetting2338("GrimV3", HoleSnapMode.MAX);
        this.limit.getSetting2338("Unlimited", HoleSnapMode.MAX);
        this.verticalBoost.do2343(num -> {
            return !this.minBoost.is2327();
        });
        this.infDurability.do2343(bool -> {
            return !this.grimDurability.getValue().booleanValue();
        });
        this.elytraFlyHelper_4.do997(ElytraFlyPredicateMode.BOOST, new ElytraFlyHelper_5(this));
        this.elytraFlyHelper_4.do997(ElytraFlyPredicateMode.CONTROL, new ElytraFlyHelper_3(this));
        this.elytraFlyHelper_4.do997(ElytraFlyPredicateMode.PACKET, new ElytraFlyHelper_6(this));
        this.elytraFlyHelper_4.do997(ElytraFlyPredicateMode.STRICT, new ElytraFlyHelper_7(this));
        this.elytraFlyHelper_4.do997(ElytraFlyPredicateMode.BOUNCE, new ElytraFlyHelper_2(this));
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.stopwatch4.setTime(-1L);
        this.flag2 = true;
        if (is1469()) {
            return;
        }
        this.elytraFlyHelper_4.getObject996().onEnable();
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        if (!is1469()) {
            this.elytraFlyHelper_4.getObject996().onDisable();
        }
        if (!is1469() && this.grimDurability.getValue().booleanValue() && this.grimDurability.is2349() && !minecraftClient.player.isSneaking()) {
            AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY, 0);
            AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY, 0);
        }
        this.stopwatch.reset();
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        try {
            return FontsSearchHelper4.getString1684(this.mode.getValue().getName());
        } catch (Exception e) {
            return null;
        }
    }

    @Listen
    public void onEvent(HoleSnapEvent holeSnapEvent) {
        if (HoleSnapSearchHelper4.is955()) {
            ElytraFlyHelper object996 = this.elytraFlyHelper_4.getObject996();
            if (object996 instanceof ElytraFlyHelper_5) {
                ElytraFlyHelper_5 elytraFlyHelper_5 = (ElytraFlyHelper_5) object996;
                if (this.autoBoost.getValue().booleanValue()) {
                    ElytraFlyData elytraFlyData2475 = BaritoneHelper_3.searchHelper4_8.getElytraFlyData2475();
                    BaritoneHelper_3.searchHelper4_8.do2478(new float[]{elytraFlyData2475 != null ? elytraFlyData2475.getFloatArray218()[0] : holeSnapEvent.get751(), elytraFlyHelper_5.get752()}, 999, true);
                }
            }
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (!minecraftClient.player.isFallFlying()) {
            this.flag2 = true;
        }
        this.elytraFlyHelper_4.getObject996().do27(tickEvent);
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        PlayerActionC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof PlayerActionC2SPacket ? (PlayerActionC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if ((packet904 instanceof PlayerActionC2SPacket) && packet904.getAction() == PlayerActionC2SPacket.Action.RELEASE_USE_ITEM) {
            this.stopwatch3.reset();
        }
        this.elytraFlyHelper_4.getObject996().do30(sendImmediatelyEvent);
    }

    @Listen
    public void onInteractItem(InteractItemEvent interactItemEvent) {
        if (this.flag4) {
            return;
        }
        if (minecraftClient.player.getStackInHand(interactItemEvent.getHand2084()).isOf(Items.FIREWORK_ROCKET)) {
            this.flag3 = true;
            interactItemEvent.do1162();
        }
    }

    @Listen
    public void do32(TickPostEvent tickPostEvent) {
        int i = FireworksHelper.get447(Items.ELYTRA);
        boolean z = i == -1;
        if (i == -1) {
            i = FireworksHelper.get443(Items.ELYTRA);
        }
        boolean is956 = is956();
        if (this.stopwatch3.is419(200L)) {
            if (antiCheat.is238() && z) {
                return;
            }
            if (obstaclePasser == null || !obstaclePasser.is929()) {
                if (is956) {
                    if (HoleSnapSearchHelper4.is2014(minecraftClient.player)) {
                        minecraftClient.interactionManager.clickSlot(0, 6, 0, SlotActionType.PICKUP, minecraftClient.player);
                        minecraftClient.interactionManager.clickSlot(0, 6, 0, SlotActionType.PICKUP, minecraftClient.player);
                    } else if (i != -1) {
                        int i2 = i;
                        if (z) {
                            i = 0;
                            minecraftClient.interactionManager.clickSlot(0, i2, 0, SlotActionType.SWAP, minecraftClient.player);
                        }
                        minecraftClient.interactionManager.clickSlot(0, 6, i, SlotActionType.SWAP, minecraftClient.player);
                        do947();
                        if (this.flag3 && this.stopwatch.is420(150L)) {
                            this.flag4 = true;
                            if (middleClick.isToggled()) {
                                middleClick.do1492();
                            } else {
                                ((DuckMinecraftClient) minecraftClient).interact();
                            }
                            this.flag4 = false;
                            this.flag3 = false;
                            this.stopwatch3.reset();
                        }
                        minecraftClient.interactionManager.clickSlot(0, 6, i, SlotActionType.SWAP, minecraftClient.player);
                        if (z) {
                            minecraftClient.interactionManager.clickSlot(0, i2, 0, SlotActionType.SWAP, minecraftClient.player);
                        }
                    }
                }
                this.elytraFlyHelper_4.getObject996().do32(tickPostEvent);
            }
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        EntitySpawnS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof EntitySpawnS2CPacket ? (EntitySpawnS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof EntitySpawnS2CPacket) {
            EntitySpawnS2CPacket entitySpawnS2CPacket = packet904;
            if (entitySpawnS2CPacket.getEntityType() == EntityType.FIREWORK_ROCKET) {
                if (new Vec3d(entitySpawnS2CPacket.getX(), entitySpawnS2CPacket.getY(), entitySpawnS2CPacket.getZ()).squaredDistanceTo(minecraftClient.player.getPos()) <= 36.0) {
                    this.stopwatch3.reset();
                }
            }
        }
        this.elytraFlyHelper_4.getObject996().do29(channelRead0Event);
    }

    @Listen
    public void do242(MoveEvent moveEvent) {
        this.elytraFlyHelper_4.getObject996().do28(moveEvent);
    }

    @Listen
    public void do31(MotionEvent motionEvent) {
        this.elytraFlyHelper_4.getObject996().do31(motionEvent);
    }

    @Listen
    public void do33(Event_3 event_3) {
        this.elytraFlyHelper_4.getObject996().do33(event_3);
    }

    @Listen
    public void do329(TickEvent_2 tickEvent_2) {
        this.elytraFlyHelper_4.getObject996().do329(tickEvent_2);
    }

    @Listen
    public void onPlay(PlayEvent playEvent) {
        if (!is956() || playEvent.getSoundInstance1914() == null || playEvent.getSoundInstance1914().getId() == null || !playEvent.getSoundInstance1914().getId().toString().contains("item.armor.equip")) {
            return;
        }
        playEvent.do1162();
    }

    public void do947() {
        if (!this.stopwatch4.is419(500L) || this.grimDurability.is2349()) {
            AutoSignSearchHelper4.do948();
            minecraftClient.player.startFallFlying();
        }
    }

    public void do948() {
        if (!is606() || minecraftClient.player.isFallFlying() || minecraftClient.player.isOnGround() || this.takeoff.getValue() == ElytraFlyMode_2.NONE) {
            BaritoneHelper_3.holeSnapSearchHelper4_2.do2017(this);
            return;
        }
        if (this.takeoff.getValue() == ElytraFlyMode_2.STRICT) {
            BaritoneHelper_3.holeSnapSearchHelper4_2.do2018(this, 0.10000000149011612f);
        }
        long j = 100;
        if (this.mode.getValue() == ElytraFlyPredicateMode.STRICT) {
            j = this.deployTime.getValue().intValue();
        }
        if (this.stopwatch2.is419(j)) {
            minecraftClient.player.startFallFlying();
            AutoSignSearchHelper4.do948();
            this.stopwatch2.reset();
        }
    }

    public boolean is606() {
        return HoleSnapSearchHelper4.is2014(minecraftClient.player);
    }

    public boolean is949() {
        if (is1469() || minecraftClient.player.input == null) {
            return false;
        }
        return !(this.vertical2.getValue().booleanValue() && minecraftClient.player.input.jumping) && isToggled() && !minecraftClient.player.isOnGround() && this.mode.getValue() == ElytraFlyPredicateMode.PACKET && is606();
    }

    public boolean is950() {
        if (this.grimDurability.getValue().booleanValue() || BaritoneHelper_3.obstaclePasserHelper.is702()) {
            return false;
        }
        if ((obstaclePasser != null && obstaclePasser.is929()) || !is951()) {
            return false;
        }
        minecraftClient.player.startFallFlying();
        return true;
    }

    public boolean is951() {
        if (BaritoneHelper_3.obstaclePasserHelper.is702()) {
            return false;
        }
        if ((obstaclePasser == null || !obstaclePasser.is929()) && !minecraftClient.player.getAbilities().flying && !minecraftClient.player.hasVehicle() && !minecraftClient.player.isClimbing() && is606() && !minecraftClient.player.isTouchingWater()) {
            if (!minecraftClient.player.hasStatusEffect(StatusEffects.LEVITATION) && minecraftClient.player.input.jumping) {
                return true;
            }
        }
        return false;
    }

    public boolean is952() {
        return (antiCheat.is238() || !minecraftClient.player.isUsingItem() || (noSlow.isToggled() && noSlow.items.getValue().booleanValue())) ? false : true;
    }

    public boolean is953() {
        return is954() && minecraftClient.player.getPitch() > 0.0f;
    }

    public boolean is954() {
        if (is1469()) {
            return false;
        }
        return (minecraftClient.player.input.jumping || minecraftClient.player.input.sneaking) && this.vertical.getValue() == ElytraFlyMode.MANUAL && minecraftClient.player.isFallFlying() && is606();
    }

    public boolean is955() {
        if (flag) {
            return minecraftClient.player.isFallFlying();
        }
        flag = true;
        boolean isFallFlying = minecraftClient.player.isFallFlying();
        flag = false;
        return isFallFlying;
    }

    public boolean is956() {
        if (BaritoneHelper_3.obstaclePasserHelper.is702() || is1469() || flag) {
            return false;
        }
        int i = FireworksHelper.get447(Items.ELYTRA);
        if (i == -1) {
            i = FireworksHelper.get443(Items.ELYTRA);
        }
        boolean z = i != -1;
        if (z) {
            this.stopwatch5.reset();
        }
        boolean z2 = z | (!this.stopwatch5.is419(150L));
        if (HoleSnapSearchHelper4.is2014(minecraftClient.player)) {
            z2 = true;
        }
        if (z2) {
            return isToggled() && (this.grimDurability.getValue().booleanValue() && this.grimDurability.is2349());
        }
        return false;
    }
}
