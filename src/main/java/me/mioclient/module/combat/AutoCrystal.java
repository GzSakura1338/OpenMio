package me.mioclient.module.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import me.mioclient.AntiPhaseSearchHelper4;
import me.mioclient.ArmorSearchHelper4;
import me.mioclient.AutoCraftMode;
import me.mioclient.AutoCrystalData;
import me.mioclient.AutoCrystalDataMode;
import me.mioclient.AutoCrystalData_3;
import me.mioclient.AutoCrystalData_4;
import me.mioclient.AutoCrystalHelper;
import me.mioclient.AutoCrystalHelper_2;
import me.mioclient.AutoCrystalHelper_4;
import me.mioclient.AutoCrystalMode;
import me.mioclient.AutoCrystalMode_2;
import me.mioclient.AutoCrystalMode_3;
import me.mioclient.AutoCrystalMode_4;
import me.mioclient.AutoCrystalMode_5;
import me.mioclient.AutoCrystalMode_6;
import me.mioclient.AutoCrystalMode_7;
import me.mioclient.AutoCrystalSearchHelper4;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.FreecamHelper;
import me.mioclient.HoleSnapEvent;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.KeyPearlMode;
import me.mioclient.MatrixStackEvent;
import me.mioclient.NewChunksHelper_4;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.ScaffoldHelperMode;
import me.mioclient.SearchHelper;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchHelper_3;
import me.mioclient.SpawnTimeHelper_2;
import me.mioclient.SpeedMineEvent;
import me.mioclient.SpeedMineMode_2;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.DisconnectEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.RenderEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.mixin.ducks.DuckEntityStatusS2CPacket;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.misc.KillEffects;
import me.mioclient.module.player.SpeedMine;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/AutoCrystal.class */
public class AutoCrystal extends Module {
    public static final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    public static SpeedMine speedmine = (SpeedMine) BaritoneHelper_3.baritoneHelper_4.getModule117(SpeedMine.class);
    public static AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static AnchorAura anchorAura = (AnchorAura) BaritoneHelper_3.baritoneHelper_4.getModule117(AnchorAura.class);
    public Setting<Boolean> place2;
    public Setting<Integer> delay;
    public Setting<Float> range;
    public Setting<Float> wallRange;
    public Setting<Float> minDamage2;
    public Setting<Float> maxSelfDamage;
    public Setting<Float> damageRatio;
    public Setting<AutoCrystalMode_5> sequential;
    public Setting<Boolean> f112;
    public Setting<Boolean> strictDirection;
    public Setting<Boolean> mineIgnore;
    public Setting<Boolean> limit;
    public Setting<Boolean> lowerHitBox;
    public Setting<Boolean> smartTrace;
    public Setting<Boolean> basePlace;
    public Setting<Boolean> air;
    public Setting<Boolean> break_;
    public Setting<Integer> delay2;
    public Setting<Integer> factor;
    public Setting<Float> range2;
    public Setting<Float> wallRange2;
    public Setting<Float> minDamage;
    public Setting<Float> maxSelfDamage2;
    public Setting<Boolean> antiSuicide;
    public Setting<Boolean> balance;
    public Setting<Boolean> resync;
    public Setting<Boolean> inhibit;
    public Setting<AutoCrystalMode> instant;
    public Setting<Boolean> await;
    public Setting<Integer> ticksExisted;
    public Setting<ScaffoldHelperMode> weakness;
    public Setting<Boolean> rotate;
    public Setting<Boolean> place;
    public Setting<Boolean> break_2;
    public Setting<Boolean> facePlace;
    public Setting<Float> health2;
    public Setting<Integer> armorThreshold;
    public Setting<Boolean> damageSync;
    public Setting<Boolean> extrapolation;
    public Setting<Integer> ticks;
    public Setting<Boolean> draw;
    public Setting<Boolean> render;
    public Setting<Color> fill;
    public Setting<Color> outline;
    public Setting<Float> lineWidth;
    public Setting<Boolean> fade;
    public Setting<Boolean> multiFade;
    public Setting<Float> fadeTime;
    public Setting<Boolean> pause;
    public Setting<Boolean> mining;
    public Setting<Boolean> eating;
    public Setting<Float> health;
    public Setting<Boolean> targeting;
    public Setting<Float> targetRange;
    public Setting<Float> crystalRange;
    public Setting<Boolean> antiFriendKill;
    public Setting<Boolean> forceSuicide;
    public Setting<Boolean> ignoreNakeds;
    public Setting<Boolean> assumeBestArmor;
    public Setting<Boolean> assumeInvincibility;
    public Setting<Boolean> self;
    public Setting<Float> timeout;
    public Setting<Boolean> misc;
    public Setting<AutoCrystalMode_2> autoSwap;
    public Setting<AutoCrystalMode_4> silent;
    public Setting<Boolean> swapBack;
    public Setting<Boolean> strictTiming;
    public Setting<Float> swapDelay;
    public Setting<Float> swapPenalty;
    public Setting<AutoCraftMode> multipoint;
    public Setting<Boolean> hBFix;
    public final Stopwatch stopwatch;
    public final AtomicReference<AutoCrystalData> atomicReference;
    public final AtomicReference<Vec3d> atomicReference2;
    public final AtomicBoolean atomicBoolean;
    public final Stopwatch stopwatch2;
    public final Stopwatch stopwatch3;
    public final Stopwatch stopwatch4;
    public final Stopwatch stopwatch5;
    public final Stopwatch stopwatch6;
    public final Stopwatch stopwatch7;
    public final Stopwatch stopwatch8;
    public final Stopwatch stopwatch9;
    public final LinkedList<Long> linkedList;
    public final List<Integer> list;
    public final BlockPos.Mutable mutable;
    public CompletableFuture<AutoCrystalData> completableFuture;
    public volatile BlockPos blockPos;
    public volatile BlockPos blockPos2;
    public volatile BlockPos blockPos3;
    public volatile Direction direction;
    public volatile boolean flag;
    public volatile boolean flag2;
    public volatile boolean flag3;
    public volatile float val;
    public int num;
    public int num2;
    public int num3;
    public int num4;
    public BlockPos blockPos4;
    public boolean skip;
    public float[] floatArr;
    public AutoCrystalData_3 autoCrystalData_3;
    public PlayerEntity playerEntity;
    public PlayerEntity playerEntity2;

    public AutoCrystal() {
        super("AutoCrystal", "Kills people with end crystals (if you're good).", Category.COMBAT, "crystalaura", "ca", "ac");
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.atomicReference = new AtomicReference<>();
        this.atomicReference2 = new AtomicReference<>();
        this.atomicBoolean = new AtomicBoolean();
        this.stopwatch2 = new Stopwatch();
        this.stopwatch3 = new Stopwatch();
        this.stopwatch4 = new Stopwatch();
        this.stopwatch5 = new Stopwatch();
        this.stopwatch6 = new Stopwatch();
        this.stopwatch7 = new Stopwatch();
        this.stopwatch8 = new Stopwatch();
        this.stopwatch9 = new Stopwatch();
        this.linkedList = new LinkedList<>();
        this.list = Collections.synchronizedList(new ArrayList());
        this.mutable = new BlockPos.Mutable();
        this.num = -1;
        BaritoneHelper_3.antiPhaseSearchHelper4.register(new AntiPhaseSearchHelper4.Record(this, this.fill, this.outline, this.lineWidth, this.fadeTime, this.multiFade, this.fade, 1000));
        this.delay.do2329("PlaceDelay");
        this.range.do2329("PlaceRange");
        this.wallRange.do2329("PlaceWallRange");
        this.minDamage2.do2329("MinPlaceDamage");
        this.maxSelfDamage.do2329("MaxSelfPlace");
        this.delay2.do2329("HitDelay");
        this.factor.do2329("HitFactor");
        this.range2.do2329("BreakRange");
        this.wallRange2.do2329("BreakWallRange");
        this.minDamage.do2329("MinBreakDamage");
        this.maxSelfDamage2.do2329("MaxSelfHit");
        this.place.do2329("PlaceRotate");
        this.break_2.do2329("BreakRotate");
        this.health2.do2329("FacePlaceHealth");
        this.armorThreshold.do2329("FacePlaceArmor");
        this.extrapolation.do2329("ExtraTicks");
        this.timeout.do2329("InvincTimeout");
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.num = -1;
        this.playerEntity2 = this.playerEntity;
        this.playerEntity = null;
        this.direction = Direction.DOWN;
        this.blockPos = null;
        this.atomicReference.set(null);
        if (this.completableFuture != null) {
            try {
                this.completableFuture.complete(null);
            } catch (CancellationException e) {
            }
        }
        this.stopwatch5.reset();
        this.stopwatch9.setTime(-1L);
        this.num4 = -1;
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        do1152();
    }

    @Listen
    public void do31(MotionEvent motionEvent) {
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Pre) {
            this.flag = false;
            if (this.flag3) {
                this.flag3 = false;
                return;
            }
            this.flag3 = false;
            PlayerEntity playerEntity = (this.playerEntity != null || is1149(this.playerEntity2)) ? this.playerEntity : this.playerEntity2;
            if (playerEntity == null) {
                double longBitsToDouble = 1.7976931348623157e+308;
                for (PlayerEntity playerEntity2 : minecraftClient.world.getPlayers()) {
                    double distanceTo = minecraftClient.player.distanceTo((Entity) playerEntity2);
                    if (!is1149((LivingEntity) playerEntity2) && distanceTo <= longBitsToDouble) {
                        longBitsToDouble = distanceTo;
                        playerEntity = playerEntity2;
                    }
                }
                if (playerEntity == null) {
                    return;
                } else {
                    this.playerEntity = playerEntity;
                }
            }
            for (Entity entity : minecraftClient.world.getEntities()) {
                if (entity instanceof ItemEntity) {
                    ItemEntity itemEntity = (ItemEntity) entity;
                    if (itemEntity.getStack().getItem() == Items.OBSIDIAN && itemEntity.getStack().getCount() >= 8 && entity.isAlive()) {
                        Box boundingBox = playerEntity.getBoundingBox();
                        if (entity.getBoundingBox().intersects(boundingBox.withMinY(boundingBox.getCenter().getY()).expand(FreecamHelper.val2, 0.0d, FreecamHelper.val2))) {
                            this.flag3 = true;
                            return;
                        }
                    }
                }
            }
        }
    }

    @Listen
    public void onRender(RenderEvent renderEvent) {
        if (is1469() || is1161()) {
            return;
        }
        if (this.completableFuture != null && this.completableFuture.isDone()) {
            try {
                AutoCrystalData autoCrystalData = this.completableFuture.get();
                if (autoCrystalData == null) {
                    this.atomicReference.set(null);
                    this.val = 0.0f;
                } else {
                    this.atomicReference.set(autoCrystalData);
                    this.val = (float) autoCrystalData.get14();
                    this.playerEntity = autoCrystalData.getPlayerEntity13();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (this.completableFuture == null || this.completableFuture.isDone() || this.completableFuture.isCancelled()) {
            do1148();
            this.completableFuture = CompletableFuture.supplyAsync(this::getAutoCrystalData1131, executorService).exceptionally(th -> {
                return getAutoCrystalData1160(th, "PlaceThread");
            });
        }
        synchronized (this.list) {
            Iterator<Integer> it = this.list.iterator();
            while (it.hasNext()) {
                Entity entityById2336 = minecraftClient.world.getEntityById(it.next().intValue());
                SpawnTimeHelper_2 entityById = entityById2336 instanceof EndCrystalEntity ? (SpawnTimeHelper_2) entityById2336 : null;
                if (entityById instanceof EndCrystalEntity) {
                    entityById.setMioAttacked(true);
                }
            }
            this.list.removeIf(num -> {
                return minecraftClient.world.getEntityById(num.intValue()) != null;
            });
        }
    }

    @Listen
    public void onEvent2(HoleSnapEvent holeSnapEvent) {
        this.flag2 = false;
        this.blockPos2 = null;
        Entity entityById = minecraftClient.world.getEntityById(this.num);
        if (entityById == null || !entityById.isAlive()) {
            this.num2 = 0;
        }
        if (minecraftClient.player.age % 4 == 0) {
            long currentTimeMillis = System.currentTimeMillis();
            while (!this.linkedList.isEmpty() && this.linkedList.get(0) != null && currentTimeMillis - this.linkedList.get(0).longValue() > 1000) {
                try {
                    this.linkedList.remove();
                } catch (NullPointerException e) {
                }
            }
        }
        KillEffects killEffects = (KillEffects) BaritoneHelper_3.keyPearlSearchHelper4.getEnumSettingHelper120(KillEffects.class);
        if (killEffects != null && killEffects.isToggled() && !is1149(this.playerEntity)) {
            killEffects.do3003(this.playerEntity);
        }
        if (is929()) {
            return;
        }
        do1135();
        if (this.stopwatch3.is419(this.delay.getValue().intValue())) {
            NewChunksHelper_4.do2149(this::do1132);
        }
        Vec3d vec3d = this.atomicReference2.get();
        if (this.rotate.getValue().booleanValue()) {
            if (vec3d == null || (SearchHelper4_8.is1144() && !is1140())) {
                if (!SearchHelper4_8.is1144() || is1140() || this.floatArr == null) {
                    return;
                }
                AutoSignSearchHelper4.do2563(minecraftClient.player.getX(), minecraftClient.player.getY(), minecraftClient.player.getZ(), this.floatArr[0], this.floatArr[1], minecraftClient.player.isOnGround());
                this.floatArr = null;
                return;
            }
            BaritoneHelper_3.searchHelper4_8.do2478(SearchHelper4_8.getFloatArray2487(SearchHelper4_8.getFloatArray2484(vec3d), BaritoneHelper_3.searchHelper4_8.get2474()), 100, true);
            if (this.stopwatch2.is419(500L)) {
                this.atomicReference.lazySet(null);
                this.atomicReference2.lazySet(null);
            }
        }
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (this.draw.getValue().booleanValue() && this.extrapolation.getValue().booleanValue() && this.playerEntity != null) {
            for (int i = 1; i < SearchHelper.get231(this.playerEntity, this.ticks); i++) {
                SearchHelper_2.searchHelper_2.do561(inner_3.getMatrixStack472(), SearchHelper.getVec3d222(BaritoneHelper_3.mainhandHelper_2.getBox1109(this.playerEntity, i - 1)), SearchHelper.getVec3d222(BaritoneHelper_3.mainhandHelper_2.getBox1109(this.playerEntity, i)), this.outline.getValue());
            }
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        EntitySpawnS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof EntitySpawnS2CPacket ? (EntitySpawnS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof EntitySpawnS2CPacket) {
            EntitySpawnS2CPacket entitySpawnS2CPacket = packet904;
            this.autoCrystalData_3 = AutoCrystalData_3.getAutoCrystalData_32406(entitySpawnS2CPacket);
            if (!is1142()) {
                try {
                    if (is929()) {
                        return;
                    } else {
                        do1130(entitySpawnS2CPacket);
                    }
                } catch (Throwable th) {
                }
            }
        }
        DuckEntityStatusS2CPacket packet9042 = (DuckEntityStatusS2CPacket)(channelRead0Event.getPacket904());
        if (packet9042 instanceof EntityStatusS2CPacket) {
            DuckEntityStatusS2CPacket duckEntityStatusS2CPacket = (DuckEntityStatusS2CPacket)((EntityStatusS2CPacket) packet9042);
            if (((EntityStatusS2CPacket) duckEntityStatusS2CPacket).getStatus() == 3 && this.playerEntity != null && duckEntityStatusS2CPacket.getId() == this.playerEntity.getId()) {
                ((AutoCrystalHelper_4) this.playerEntity).setServerSideDead(true);
            }
        }
        if (channelRead0Event.getPacket904() instanceof DeathMessageS2CPacket) {
            this.forceSuicide.do2333(false);
        }
    }

    @Listen
    public void onEvent3(SpeedMineEvent speedMineEvent) {
        if (this.num3 > 0) {
            this.num3--;
        } else {
            this.stopwatch6.reset();
            this.stopwatch5.reset();
        }
    }

    @Listen
    public void onDisconnect(DisconnectEvent disconnectEvent) {
        this.forceSuicide.do2333(false);
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return (this.playerEntity == null || is1149(this.playerEntity)) ? super.getInfo() : "%s, %.1f, %d".formatted(this.playerEntity.getGameProfile().getName(), Float.valueOf(this.val), Integer.valueOf(this.linkedList.size()));
    }

    public void do1130(EntitySpawnS2CPacket entitySpawnS2CPacket) {
        if (entitySpawnS2CPacket.getEntityType() != EntityType.END_CRYSTAL || this.playerEntity == null) {
            return;
        }
        Vec3d vec3d = new Vec3d(entitySpawnS2CPacket.getX(), entitySpawnS2CPacket.getY(), entitySpawnS2CPacket.getZ());
        if (this.blockPos != null && vec3d.squaredDistanceTo(this.blockPos.toCenterPos()) <= 9.0) {
            this.linkedList.add(Long.valueOf(System.currentTimeMillis()));
        }
        if (this.instant.getValue() == AutoCrystalMode.NONE || !this.break_.getValue().booleanValue() || is1155(this.playerEntity)) {
            return;
        }
        if (is1157() && is1158()) {
            return;
        }
        if (minecraftClient.player.getEyePos().distanceTo(vec3d) > (SearchHelper4_7.is2433(vec3d) ? this.range2.getValue() : this.wallRange2.getValue()).floatValue()) {
            return;
        }
        double d = ArmorSearchHelper4.get1895(vec3d, this.playerEntity);
        double d2 = ArmorSearchHelper4.get1895(vec3d, minecraftClient.player);
        AutoCrystalMode_7 autoCrystalMode_71882 = AutoCrystalMode_7.getAutoCrystalMode_71882(this.playerEntity, d);
        if (!this.balance.getValue().booleanValue() || d > d2 || this.forceSuicide.getValue().booleanValue() || autoCrystalMode_71882.flag) {
            if (d >= AutoCrystalMode_3.HIT.get847(this.playerEntity)) {
                if (AutoCrystalMode_3.HIT.is849(this.playerEntity, d2, d, autoCrystalMode_71882)) {
                    return;
                }
                BlockPos ofFloored = BlockPos.ofFloored((Position) vec3d);
                if (this.instant.getValue() == AutoCrystalMode.STRICT && this.blockPos4.equals(ofFloored)) {
                    return;
                }
                if (this.rotate.getValue().booleanValue() && this.break_2.getValue().booleanValue()) {
                    do1138(vec3d);
                }
                if (BaritoneHelper_3.scaffoldHelper.is1113(entitySpawnS2CPacket.getEntityId(), true)) {
                    this.blockPos2 = ofFloored;
                    this.blockPos4 = ofFloored;
                    this.num = entitySpawnS2CPacket.getEntityId();
                    this.num2 = 0;
                    this.stopwatch9.reset();
                }
                boolean z = minecraftClient.player.isHolding(Items.END_CRYSTAL) || SearchHelper_3.get643() >= 8.0f;
                if (!antiCheat.is238()) {
                    z = true;
                }
                if (this.sequential.getValue().is1102() && !this.flag && z) {
                    this.stopwatch7.reset();
                    NewChunksHelper_4.do2149(this::do1132);
                    if (this.sequential.getValue() == AutoCrystalMode_5.STRICT) {
                        this.flag = true;
                    }
                }
                do1139(entitySpawnS2CPacket.getEntityId());
            }
        }
    }

    public AutoCrystalData getAutoCrystalData1131() {
        AutoCrystalData autoCrystalData = this.atomicReference.get();
        AutoCrystalData_4 autoCrystalData_4 = null;
        AutoCrystalData autoCrystalData2 = null;
        AutoCrystalSearchHelper4 autoCrystalSearchHelper42080 = new AutoCrystalSearchHelper4().getAutoCrystalSearchHelper42071(this.f112.getValue().booleanValue()).getAutoCrystalSearchHelper42075(this.lowerHitBox.getValue().booleanValue()).getAutoCrystalSearchHelper42076(this.hBFix.getValue().booleanValue() && !is1157()).getAutoCrystalSearchHelper42077(this.basePlace.getValue().booleanValue() && is1151()).getAutoCrystalSearchHelper42078(is1157() && speedmine.rebreak2.getValue() != SpeedMineMode_2.INSTANT).getAutoCrystalSearchHelper42079(this.extrapolation.getValue().booleanValue() ? this.ticks.getValue().intValue() : 0).getAutoCrystalSearchHelper42080(this.range2.getValue().floatValue());
        if (autoCrystalData != null) {
            if (autoCrystalSearchHelper42080.is2070(autoCrystalData.getBlockPos12())) {
                autoCrystalData_4 = getAutoCrystalData_41147(autoCrystalData.getBlockPos12(), SearchHelper4_7.is2445(autoCrystalData.getBlockPos12()));
            }
        }
        double d = -Math.floor(this.range.getValue().floatValue());
        while (true) {
            double d2 = d;
            if (d2 >= Math.ceil(this.range.getValue().floatValue())) {
                return autoCrystalData2;
            }
            double d3 = -Math.floor(this.range.getValue().floatValue());
            while (true) {
                double d4 = d3;
                if (d4 < Math.ceil(this.range.getValue().floatValue())) {
                    double d5 = -Math.floor(this.range.getValue().floatValue());
                    while (true) {
                        double d6 = d5;
                        if (d6 < Math.ceil(this.range.getValue().floatValue())) {
                            this.mutable.set(minecraftClient.player.getX() + d2, minecraftClient.player.getEyeY() + d4, minecraftClient.player.getZ() + d6);
                            boolean is2445 = SearchHelper4_7.is2445(this.mutable);
                            if (autoCrystalData2 == null || autoCrystalData2.getAutoCrystalDataMode15() == AutoCrystalDataMode.OBSIDIAN || is2445) {
                                double distanceTo = minecraftClient.player.getEyePos().distanceTo(this.mutable.toCenterPos());
                                if (distanceTo <= this.range.getValue().floatValue() && autoCrystalSearchHelper42080.is2070(this.mutable)) {
                                    boolean is2432 = SearchHelper4_7.is2432(this.multipoint.getValue().getList899(this.mutable));
                                    if ((is2432 || distanceTo <= this.wallRange.getValue().floatValue() || this.smartTrace.getValue().booleanValue()) && (!this.strictDirection.getValue().booleanValue() || !PhaseESPSearchHelper4_2.getList3031(this.mutable).isEmpty())) {
                                        double d7 = ArmorSearchHelper4.get1900(Vec3d.ofCenter(this.mutable, 1.0), minecraftClient.player, getBox1145(minecraftClient.player), 6.0, true, (BlockPos) null, this.mutable);
                                        AutoCrystalData_4 autoCrystalData_41147 = getAutoCrystalData_41147(this.mutable, is2445);
                                        if (autoCrystalData_41147 != null) {
                                            if (!AutoCrystalMode_3.PLACE.is849(autoCrystalData_41147.getPlayerEntity13(), d7, autoCrystalData_41147.get14(), AutoCrystalMode_7.getAutoCrystalMode_71882(autoCrystalData_41147.getPlayerEntity13(), autoCrystalData_41147.get14()))) {
                                                AutoCrystalDataMode autoCrystalDataMode = AutoCrystalDataMode.NONE;
                                                if (autoCrystalData_4 == null || autoCrystalData.getBlockPos12().equals(this.mutable) || Math.round(autoCrystalData_4.get14()) < autoCrystalData_41147.get14()) {
                                                    if (!is2432 && distanceTo > this.wallRange.getValue().floatValue()) {
                                                        autoCrystalDataMode = AutoCrystalDataMode.RAYTRACE;
                                                    }
                                                    boolean store = true;
                                                    if (this.basePlace.getValue().booleanValue() && !is2445) {
                                                        if (getBox1145(autoCrystalData_41147.getPlayerEntity13()).union(autoCrystalData_41147.getPlayerEntity13().getBoundingBox()).intersects(new Box(this.mutable))) {
                                                            store = false;
                                                        } else if (!PhaseESPSearchHelper4_2.is3041(this.mutable, false)) {
                                                            store = false;
                                                        } else if (!this.air.getValue().booleanValue() && PhaseESPSearchHelper4_2.getDirection3029(this.mutable, this.strictDirection.getValue().booleanValue()) == null) {
                                                            store = false;
                                                        } else {
                                                            autoCrystalDataMode = AutoCrystalDataMode.OBSIDIAN;
                                                        }
                                                    }
                                                    if (store) {
                                                        autoCrystalData2 = new AutoCrystalData(this.mutable.toImmutable(), autoCrystalData_41147.getPlayerEntity13(), autoCrystalData_41147.get14(), autoCrystalDataMode).getAutoCrystalData11(autoCrystalData2);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            d5 = d6 + 1.0;
                        } else {
                            break;
                        }
                    }
                    d3 = d4 + 1.0;
                } else {
                    break;
                }
            }
            d = d2 + 1.0;
        }
    }

    public void do1132() {
        if (!is1157()) {
            this.atomicBoolean.set(false);
        }
        if (!this.place2.getValue().booleanValue() || is1142() || this.atomicBoolean.get()) {
            return;
        }
        boolean z = minecraftClient.player.getOffHandStack().getItem() == Items.END_CRYSTAL;
        boolean isHolding = minecraftClient.player.isHolding(Items.END_CRYSTAL);
        if ((this.damageSync.getValue().booleanValue() || this.flag3) && this.val < this.minDamage2.getValue().floatValue() && this.val < 5.0f) {
            if (!this.stopwatch3.is419(get1153())) {
                return;
            }
        }
        AutoCrystalData autoCrystalData = this.atomicReference.get();
        if (autoCrystalData == null) {
            do1152();
            return;
        }
        BlockPos blockPos12 = autoCrystalData.getBlockPos12();
        if (blockPos12 == null || this.playerEntity == null) {
            do1152();
            return;
        }
        double d = AutoCrystalMode_3.PLACE.get847(this.playerEntity);
        double d2 = ArmorSearchHelper4.get1900(Vec3d.ofCenter((Vec3i) blockPos12, 1.0), this.playerEntity, getBox1145(this.playerEntity), 6.0, true, is1157() ? speedmine.getBlockPos1051() : null, blockPos12);
        if (this.extrapolation.getValue().booleanValue() && this.ticks.getValue().intValue() > 0 && d2 > 2.0 && d2 < d) {
            d2 = Math.max(d2, get1146(blockPos12, this.playerEntity, SearchHelper.getBox234(this.playerEntity), true));
        }
        if (d2 < d) {
            do1152();
            return;
        }
        if (!SearchHelper4_7.is2445(blockPos12)) {
            if (autoCrystalData.getAutoCrystalDataMode15() == AutoCrystalDataMode.OBSIDIAN && this.basePlace.getValue().booleanValue()) {
                do1134(blockPos12);
                return;
            }
            return;
        }
        int i = -1;
        int i2 = FireworksHelper.get447(Items.END_CRYSTAL);
        if (this.limit.getValue().booleanValue()) {
            if (!PhaseESPSearchHelper4_2.is3043(blockPos12, this.f112.getValue().booleanValue(), true, true, true, this.lowerHitBox.getValue().booleanValue(), this.hBFix.getValue().booleanValue())) {
                return;
            }
        }
        if (minecraftClient.world.getBlockState(blockPos12.up()).getBlock() == Blocks.FIRE) {
            minecraftClient.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos12.up(), Direction.DOWN));
            minecraftClient.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos12.up(), Direction.DOWN));
            return;
        }
        boolean z2 = false;
        boolean z3 = is1144() && this.silent.getValue() == AutoCrystalMode_4.ALTERNATIVE;
        boolean z4 = false;
        if (!isHolding && is1157() && speedmine.swapReset.getValue().booleanValue() && speedmine.alternative.getValue().booleanValue()) {
            z3 = true;
            z4 = true;
        }
        if (z3 && (!this.skip || !minecraftClient.player.currentScreenHandler.getCursorStack().isEmpty())) {
            this.skip = true;
            return;
        }
        if (z3 && i2 == -1) {
            i2 = FireworksHelper.get443(Items.END_CRYSTAL);
        }
        if (this.autoSwap.getValue() != AutoCrystalMode_2.NONE && !isHolding && !is1143() && i2 != -1) {
            i = minecraftClient.player.getInventory().selectedSlot;
            if (z3) {
                FireworksHelper.do439(i2);
                z2 = true;
                this.atomicBoolean.set(z4);
            } else if (is1144() && this.silent.getValue() == AutoCrystalMode_4.PICK) {
                minecraftClient.interactionManager.pickFromInventory(i2);
            } else {
                if (this.autoSwap.getValue() == AutoCrystalMode_2.SILENT) {
                    speedmine.flag2 = true;
                    do1159();
                }
                if (is1150()) {
                    this.num4 = i;
                }
                FireworksHelper.do456(i2);
            }
        }
        if ((i2 == -1 || this.autoSwap.getValue() == AutoCrystalMode_2.NONE) && !isHolding) {
            return;
        }
        do1133(blockPos12, z, this.direction);
        if (is1144() || z2) {
            if (i != -1) {
                if (z2) {
                    FireworksHelper.do439(i2);
                } else if (this.autoSwap.getValue() == AutoCrystalMode_2.SILENT) {
                    if (is1144() && this.silent.getValue() == AutoCrystalMode_4.PICK) {
                        minecraftClient.interactionManager.pickFromInventory(i2);
                    } else {
                        do1159();
                        FireworksHelper.do456(i);
                    }
                }
            }
            if (this.strictTiming.getValue().booleanValue() || z4 || this.skip) {
                this.skip = !this.skip;
            }
        }
    }

    public void do1133(BlockPos blockPos, boolean z, Direction direction) {
        if (blockPos == null || direction == null) {
            return;
        }
        if (blockPos.equals(this.blockPos2) || this.blockPos2 == null || this.sequential.getValue().is1102()) {
            List<Direction> list3031 = PhaseESPSearchHelper4_2.getList3031(blockPos);
            Direction direction2 = direction;
            if (!list3031.isEmpty()) {
                direction2 = list3031.get(0);
            }
            Vec3d offset = blockPos.toCenterPos().offset(direction2, FreecamHelper.val2);
            double longBitsToDouble = 1.7976931348623157e+308;
            double[] dArr = {0.05, 0.95};
            for (double d : dArr) {
                for (double d2 : new double[]{0.05, 0.95, 0.99}) {
                    for (double d3 : dArr) {
                        Vec3d add = Vec3d.of((Vec3i) blockPos).add(d, d2, d3);
                        BlockHitResult raycast = minecraftClient.world.raycast(new RaycastContext(minecraftClient.player.getEyePos(), add, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, minecraftClient.player));
                        double distanceTo = raycast.getPos().distanceTo(minecraftClient.player.getEyePos());
                        if ((raycast.getType() == HitResult.Type.MISS || raycast.getBlockPos().equals(blockPos)) && add.distanceTo(minecraftClient.player.getEyePos()) <= this.range.getValue().floatValue() && distanceTo < longBitsToDouble && (list3031.contains(raycast.getSide()) || !this.strictDirection.getValue().booleanValue())) {
                            offset = raycast.getPos();
                            direction2 = raycast.getSide();
                            longBitsToDouble = distanceTo;
                        }
                    }
                }
            }
            if (this.place.getValue().booleanValue() && this.rotate.getValue().booleanValue() && !this.flag2) {
                do1138(offset);
            }
            Hand hand = z ? Hand.OFF_HAND : Hand.MAIN_HAND;
            AutoSignSearchHelper4.do2556(hand, new BlockHitResult(blockPos.toCenterPos().offset(direction2, FreecamHelper.val2), direction2, blockPos, false));
            minecraftClient.player.swingHand(hand);
            this.blockPos = blockPos;
            if (this.render.getValue().booleanValue()) {
                BaritoneHelper_3.antiPhaseSearchHelper4.do2132(this, blockPos);
            }
            this.stopwatch3.reset();
        }
    }

    public void do1134(BlockPos blockPos) {
        if (is1151()) {
            int i = FireworksHelper.get447(Items.END_CRYSTAL);
            if (this.autoSwap.getValue() == AutoCrystalMode_2.SILENT && this.silent.getValue() == AutoCrystalMode_4.ALTERNATIVE) {
                i = FireworksHelper.get443(Items.END_CRYSTAL);
            }
            if (minecraftClient.player.getOffHandStack().isOf(Items.END_CRYSTAL) || i != -1) {
                int i2 = minecraftClient.player.getInventory().selectedSlot;
                int i3 = FireworksHelper.get447(Items.OBSIDIAN);
                Direction direction3029 = PhaseESPSearchHelper4_2.getDirection3029(blockPos, this.strictDirection.getValue().booleanValue());
                boolean z = (is1144() && this.silent.getValue() == AutoCrystalMode_4.ALTERNATIVE) || (this.silent.getValue() == AutoCrystalMode_4.PICK && is1157() && speedmine.getBlockPos1051() != null);
                boolean z2 = this.air.getValue().booleanValue() && direction3029 == null && antiCheat.is238();
                Hand hand = Hand.MAIN_HAND;
                if (z && i3 == -1) {
                    i3 = FireworksHelper.get443(Items.OBSIDIAN);
                }
                if (i3 != -1) {
                    if ((direction3029 != null || this.air.getValue().booleanValue()) && PhaseESPSearchHelper4_2.is3041(blockPos, false)) {
                        if (i3 != i2) {
                            if (z) {
                                FireworksHelper.do439(i3);
                            } else if (is1144() && this.silent.getValue() == AutoCrystalMode_4.PICK) {
                                minecraftClient.interactionManager.pickFromInventory(i3);
                            } else {
                                speedmine.flag2 = true;
                                FireworksHelper.do456(i3);
                            }
                        }
                        if (z2) {
                            AutoSignSearchHelper4.do2568();
                            hand = Hand.OFF_HAND;
                        }
                        PhaseESPSearchHelper4_2.is3037(blockPos, direction3029, this.air.getValue().booleanValue(), hand);
                        this.stopwatch9.reset();
                        if (z2) {
                            AutoSignSearchHelper4.do2568();
                        }
                        if (this.place.getValue().booleanValue() && this.rotate.getValue().booleanValue()) {
                            Vec3d centerPos = blockPos.toCenterPos();
                            if (direction3029 != null) {
                                centerPos = centerPos.offset(direction3029, 0.5);
                            }
                            do1138(centerPos);
                        }
                        this.blockPos3 = blockPos;
                        if (i2 == -1 || i3 == i2) {
                            return;
                        }
                        if (z) {
                            FireworksHelper.do439(i3);
                        } else if (is1144() && this.silent.getValue() == AutoCrystalMode_4.PICK) {
                            minecraftClient.interactionManager.pickFromInventory(i3);
                        } else {
                            FireworksHelper.do456(i2);
                        }
                    }
                }
            }
        }
    }

    public void do1135() {
        if (this.break_.getValue().booleanValue()) {
            if (!this.stopwatch4.is419(this.delay2.getValue().intValue()) || is1142() || is1155(this.playerEntity)) {
                return;
            }
            if (this.stopwatch7.is419(BaritoneHelper_3.holeSnapSearchHelper4_4.get1730()) || this.instant.getValue() == AutoCrystalMode.NONE || !is1158() || is1157()) {
                SpeedMine speedMine = (SpeedMine) BaritoneHelper_3.keyPearlSearchHelper4.getEnumSettingHelper120(SpeedMine.class);
                Entity entity = null;
                double longBitsToDouble = -1337.0;
                boolean is1157 = is1157();
                if (!SearchHelper4_7.is2435(speedmine.getBlockPos1051())) {
                    is1157 = false;
                }
                BlockPos blockPos1051 = speedMine.getBlockPos1051();
                if (!SearchHelper4_7.is2435(blockPos1051)) {
                    blockPos1051 = null;
                }
                for (Entity entity2 : minecraftClient.world.getEntities()) {
                    if (entity2 instanceof EndCrystalEntity) {
                        if (is1137((EndCrystalEntity) entity2)) {
                            double d = this.skip ? ArmorSearchHelper4.get1896(entity2.getPos(), this.playerEntity, getBox1145(this.playerEntity)) : get1146(BlockPos.ofFloored(entity2.getPos()).down(), this.playerEntity, getBox1145(this.playerEntity), false);
                            double d2 = ArmorSearchHelper4.get1896(entity2.getPos(), minecraftClient.player, minecraftClient.player.getBoundingBox());
                            AutoCrystalMode_7 autoCrystalMode_71882 = AutoCrystalMode_7.getAutoCrystalMode_71882(this.playerEntity, d);
                            if (!this.balance.getValue().booleanValue() || d > d2 || this.forceSuicide.getValue().booleanValue() || autoCrystalMode_71882.flag) {
                                if (!AutoCrystalMode_3.HIT.is849(this.playerEntity, d2, d, autoCrystalMode_71882)) {
                                    if (this.blockPos != null) {
                                        if (entity2.getBoundingBox().intersects(new Box(this.blockPos.up()).stretch(0.0d, 1.0, 0.0d)) && entity == null && (!is1157 || blockPos1051 == null)) {
                                            entity = entity2;
                                        }
                                    }
                                    if (d >= AutoCrystalMode_3.HIT.get847(this.playerEntity) && d > longBitsToDouble) {
                                        longBitsToDouble = d;
                                        entity = entity2;
                                    }
                                }
                            }
                        }
                    }
                }
                if (entity == null) {
                    return;
                }
                do1136((EndCrystalEntity) entity, minecraftClient.player.getOffHandStack().getItem() == Items.END_CRYSTAL);
            }
        }
    }

    public void do1136(EndCrystalEntity endCrystalEntity, boolean z) {
        if (endCrystalEntity.getId() == this.num && this.stopwatch8.is419(500L) && endCrystalEntity.isAlive() && !endCrystalEntity.isRemoved()) {
            this.num2++;
        } else {
            this.num2 = 0;
        }
        this.num = endCrystalEntity.getId();
        if (this.num2 >= 5) {
            this.stopwatch8.reset();
        }
        if (is1140() && this.resync.getValue().booleanValue()) {
            return;
        }
        if (BaritoneHelper_3.scaffoldHelper.is1112(endCrystalEntity.getId())) {
            ((SpawnTimeHelper_2) endCrystalEntity).setMioAttacked(true);
            do1139(endCrystalEntity.getId());
            this.blockPos2 = endCrystalEntity.getBlockPos().down();
            if (this.break_2.getValue().booleanValue() && this.rotate.getValue().booleanValue()) {
                do1138(SearchHelper.getVec3d223(endCrystalEntity.getBoundingBox()));
                this.flag2 = true;
            }
            this.stopwatch4.reset();
            this.stopwatch9.reset();
        }
    }

    public boolean is1137(EndCrystalEntity endCrystalEntity) {
        if ((endCrystalEntity.age < this.ticksExisted.getValue().intValue() && !is1157()) || endCrystalEntity.isRemoved() || this.playerEntity == null) {
            return false;
        }
        return minecraftClient.player.getEyePos().distanceTo(endCrystalEntity.getPos()) <= ((double) (minecraftClient.player.canSee((Entity) endCrystalEntity) ? this.range2.getValue() : this.wallRange2.getValue()).floatValue());
    }

    public void do1138(Vec3d vec3d) {
        if (vec3d == null) {
            return;
        }
        if (SearchHelper4_8.is724() && antiCheat.rotations.getValue() == AutoCrystalMode_6.SILENT && !is1141(100L)) {
            float[] floatArray2484 = SearchHelper4_8.getFloatArray2484(vec3d);
            BaritoneHelper_3.searchHelper4_8.do2480(floatArray2484);
            this.floatArr = floatArray2484;
        }
        this.stopwatch2.reset();
        this.atomicReference2.set(vec3d);
    }

    public void do1139(int i) {
        if (this.factor.is2327()) {
            return;
        }
        for (int i2 = 1; i2 < this.factor.getValue().intValue(); i2++) {
            int i3 = i + i2;
            if (antiCheat.movementSync.getValue().booleanValue()) {
                i3--;
            }
            for (Entity entity : minecraftClient.world.getEntities()) {
                if (((entity instanceof LivingEntity) && entity.isRemoved()) || !entity.isAlive()) {
                    return;
                }
            }
            if (this.autoCrystalData_3 != null && this.autoCrystalData_3.getEntityType2407() != EntityType.END_CRYSTAL && this.autoCrystalData_3.get391() == i3) {
                return;
            }
            AutoSignSearchHelper4.do2564(i3);
            AutoSignSearchHelper4.do2559(Hand.MAIN_HAND);
            this.list.add(Integer.valueOf(i3));
        }
    }

    public boolean is1140() {
        return is1141(500L);
    }

    public boolean is1141(long j) {
        if (BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(200L)) {
            return !this.stopwatch8.is419((long) (((float) j) / BaritoneHelper_3.holeSnapSearchHelper4_4.get2621()));
        }
        return true;
    }

    public boolean is929() {
        if (is1469() || PhaseESPSearchHelper4_2.is3055() || Offhand.is929() || SpeedMine.is1060() || is1149(this.playerEntity) || minecraftClient.player.isSleeping()) {
            return true;
        }
        if (minecraftClient.player.isUsingItem() && this.eating.getValue().booleanValue()) {
            return true;
        }
        if ((minecraftClient.interactionManager.isBreakingBlock() || (SearchHelper4_7.is2435(speedmine.getBlockPos1053()) && speedmine.isToggled())) && this.mining.getValue().booleanValue()) {
            return true;
        }
        if (this.forceSuicide.getValue().booleanValue()) {
            return false;
        }
        if (!anchorAura.isToggled() || anchorAura.atomicReference.get() == null || anchorAura.autoCrystalData_2 == null || this.atomicReference.get() == null || anchorAura.autoCrystalData_2.get14() <= this.atomicReference.get().get14()) {
            return SearchHelper_3.get643() < this.health.getValue().floatValue() && !this.forceSuicide.getValue().booleanValue();
        }
        return true;
    }

    public boolean is1142() {
        return !this.stopwatch6.is419((long) (50.0f * this.swapPenalty.getValue().floatValue()));
    }

    public boolean is1143() {
        if (this.autoSwap.getValue() == AutoCrystalMode_2.NORMAL) {
            if (!this.stopwatch5.is419((long) (50.0f * this.swapDelay.getValue().floatValue()))) {
                return true;
            }
        }
        return false;
    }

    public boolean is1144() {
        return this.autoSwap.getValue() == AutoCrystalMode_2.SILENT;
    }

    public Box getBox1145(PlayerEntity playerEntity) {
        if (!this.extrapolation.getValue().booleanValue()) {
            return SearchHelper.getBox234((LivingEntity) playerEntity);
        }
        return BaritoneHelper_3.mainhandHelper_2.getBox1109(playerEntity, SearchHelper.get231(playerEntity, this.ticks));
    }

    public double get1146(BlockPos blockPos, LivingEntity livingEntity, Box box, boolean z) {
        return ArmorSearchHelper4.get1900(Vec3d.ofCenter((Vec3i) blockPos, 1.0), livingEntity, box, 6.0, true, is1157() ? speedmine.getBlockPos1051() : null, z ? blockPos.toImmutable() : null);
    }

    public AutoCrystalData_4 getAutoCrystalData_41147(BlockPos blockPos, boolean z) {
        AutoCrystalData_4 autoCrystalData_4 = null;
        Iterator it = new ArrayList(minecraftClient.world.getPlayers()).iterator();
        while (it.hasNext()) {
            LivingEntity livingEntity = (AbstractClientPlayerEntity) it.next();
            if (!is1149(livingEntity) && (blockPos.getY() <= ((AbstractClientPlayerEntity) livingEntity).getY() + 1.5 || z)) {
                Box box1145 = getBox1145((PlayerEntity) livingEntity);
                double d = AutoCrystalMode_3.PLACE.get847((PlayerEntity) livingEntity);
                double d2 = get1146(blockPos, livingEntity, box1145, true);
                AutoCrystalMode_7 autoCrystalMode_71882 = AutoCrystalMode_7.getAutoCrystalMode_71882(livingEntity, d2);
                if (!this.ignoreNakeds.getValue().booleanValue() || HoleSnapSearchHelper4.is2013(livingEntity)) {
                    if (SearchHelper.getVec3d222(box1145).distanceTo(Vec3d.ofBottomCenter((Vec3i) blockPos)) <= this.crystalRange.getValue().floatValue() || autoCrystalMode_71882.flag || this.flag3) {
                        if (!BaritoneHelper_3.searchHelper4_14.is520((PlayerEntity) livingEntity) || livingEntity == minecraftClient.player) {
                            if ((d <= d2 && d2 >= FreecamHelper.val2) || autoCrystalMode_71882.flag) {
                                if (autoCrystalData_4 == null || d2 > autoCrystalData_4.get14()) {
                                    autoCrystalData_4 = new AutoCrystalData_4((PlayerEntity) livingEntity, d2, AutoCrystalDataMode.NONE, autoCrystalMode_71882);
                                }
                            }
                        } else if (this.antiFriendKill.getValue().booleanValue() && d2 > SearchHelper_3.get644((Entity) livingEntity)) {
                            return null;
                        }
                    }
                }
            }
        }
        return autoCrystalData_4;
    }

    public void do1148() {
        if (is1149(this.playerEntity)) {
            this.playerEntity = null;
        }
    }

    public boolean is1149(LivingEntity livingEntity) {
        if (livingEntity == null) {
            return true;
        }
        return this.forceSuicide.getValue().booleanValue() ? livingEntity != minecraftClient.player : BaritoneHelper_3.searchHelper4_14.is520((PlayerEntity) livingEntity) || !livingEntity.isAlive() || livingEntity.isDead() || livingEntity.distanceTo(minecraftClient.player) > this.targetRange.getValue().floatValue() || ((AutoCrystalHelper_4) livingEntity).isServerSideDead() || minecraftClient.player == livingEntity || livingEntity.isSpectator() || ((PlayerEntity) livingEntity).getAbilities().creativeMode;
    }

    public boolean is1150() {
        return this.autoSwap.getValue() == AutoCrystalMode_2.NORMAL && this.swapBack.getValue().booleanValue();
    }

    public boolean is1151() {
        if (this.blockPos3 != null && this.playerEntity != null) {
            if (!PhaseESPSearchHelper4_2.is3043(this.blockPos3, this.f112.getValue().booleanValue(), true, false, true, this.lowerHitBox.getValue().booleanValue(), this.hBFix.getValue().booleanValue())) {
                return true;
            }
            boolean is2432 = SearchHelper4_7.is2432(this.multipoint.getValue().getList899(this.blockPos3));
            double distanceTo = minecraftClient.player.getEyePos().distanceTo(this.blockPos3.toCenterPos());
            if (distanceTo > this.range.getValue().floatValue()) {
                return true;
            }
            if (!is2432 && distanceTo > this.wallRange.getValue().floatValue() && !this.smartTrace.getValue().booleanValue()) {
                return true;
            }
            Vec3d ofCenter = Vec3d.ofCenter(this.blockPos3, 1.0);
            LivingEntity livingEntity = this.playerEntity;
            if (ArmorSearchHelper4.get1900(ofCenter, livingEntity, getBox1145(this.playerEntity), 6.0, true, (BlockPos) null, (BlockPos) null) >= AutoCrystalMode_3.PLACE.get847(this.playerEntity)) {
                return false;
            }
        }
        return this.stopwatch9.is419(get1153());
    }

    public void do1152() {
        if (is1469() || !is1150() || this.num4 == -1) {
            return;
        }
        FireworksHelper.do456(this.num4);
        this.num4 = -1;
    }

    public long get1153() {
        return (long) (500.0f / BaritoneHelper_3.holeSnapSearchHelper4_4.get2621());
    }

    public float get1154() {
        return !this.assumeInvincibility.getValue().booleanValue() ? -1.0f : this.timeout.getValue().floatValue();
    }

    public boolean is1155(PlayerEntity playerEntity) {
        if (is1156()) {
            return false;
        }
        AutoCrystalHelper_2 playerListEntry = (AutoCrystalHelper_2)(minecraftClient.player.networkHandler.getPlayerListEntry(playerEntity.getGameProfile().getId()));
        return playerListEntry != null && ((float) (System.currentTimeMillis() - playerListEntry.mio$getJoinTime())) <= get1154() * 1000.0f;
    }

    public boolean is1156() {
        return this.self.getValue().booleanValue() && ((float) minecraftClient.player.age) / 20.0f < get1154();
    }

    public boolean is1157() {
        return this.mineIgnore.getValue().booleanValue() && ((((((double) speedmine.get1052()) + (speedmine.get1046() * ((double) 2.5f))) > ((double) speedmine.damage.getValue().floatValue()) ? 1 : ((((double) speedmine.get1052()) + (speedmine.get1046() * ((double) 2.5f))) == ((double) speedmine.damage.getValue().floatValue()) ? 0 : -1)) >= 0 && speedmine.getBlockPos1051() != null && SearchHelper4_7.is2435(speedmine.getBlockPos1051())) || !speedmine.is1058(100L));
    }

    public boolean is1158() {
        return this.await.getValue().booleanValue();
    }

    public void do1159() {
        this.num3++;
    }

    public AutoCrystalData getAutoCrystalData1160(Throwable th, String str) {
        return null;
    }

    public boolean is1161() {
        boolean isHolding = minecraftClient.player.isHolding(itemStack -> {
            return AutoCrystalHelper.is135(itemStack.getItem());
        });
        if (!isHolding && this.stopwatch.is419(50L)) {
            return false;
        }
        if (isHolding) {
            this.stopwatch.reset();
        }
        if (this.completableFuture == null || this.completableFuture.isDone()) {
            return true;
        }
        do1162();
        return true;
    }

    public void do1162() {
        CompletableFuture<AutoCrystalData> completableFuture = this.completableFuture;
        if (completableFuture == null || completableFuture.isDone() || completableFuture.isCancelled()) {
            return;
        }
        completableFuture.complete(null);
    }
}
