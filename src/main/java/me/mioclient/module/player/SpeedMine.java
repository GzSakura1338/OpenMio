package me.mioclient.module.player;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import me.mioclient.AutoCraftMode;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BreakingProgressHelper;
import me.mioclient.FireworksHelper;
import me.mioclient.FreecamHelper;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapEvent;
import me.mioclient.KeyPearlMode;
import me.mioclient.MatrixStackEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.ScaffoldMode_2;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchHelper_3;
import me.mioclient.SpeedMineEvent;
import me.mioclient.SpeedMineHelper_2;
import me.mioclient.SpeedMineHelper_3;
import me.mioclient.SpeedMineMode;
import me.mioclient.SpeedMineMode_2;
import me.mioclient.SpeedMineMode_3;
import me.mioclient.SpeedMineMode_4;
import me.mioclient.SpeedMineMode_5;
import me.mioclient.SpeedMineRunnable;
import me.mioclient.SpeedMineSearchHelper4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.AttackBlockEvent;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.DisconnectEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.combat.AutoCrystal;
import me.mioclient.module.combat.AutoExp;
import me.mioclient.module.combat.AutoMine;
import me.mioclient.module.movement.NoSlow;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/player/SpeedMine.class */
public class SpeedMine extends Module {
    public static boolean flag;
    public Setting<ScaffoldMode_2> mode;
    public Setting<Float> damage;
    public Setting<Float> range;
    public Setting<Boolean> extraBreak;
    public Setting<Boolean> tPSSync;
    public Setting<Boolean> swing;
    public Setting<Boolean> rebreak;
    public Setting<SpeedMineMode_2> rebreak2;
    public Setting<Boolean> assistant;
    public Setting<Float> instantDelay;
    public Setting<Boolean> clickReset;
    public Setting<Boolean> holdingBest;
    public Setting<Boolean> autoSwap2;
    public Setting<SpeedMineMode_3> autoSwap;
    public Setting<Boolean> swapBack;
    public Setting<SpeedMineMode_4> f121;
    public Setting<Boolean> alternative;
    public Setting<Boolean> swapReset;
    public Setting<Boolean> rotate;
    public Setting<Float> limit;
    public Setting<Boolean> render;
    public Setting<Float> lineWidth;
    public Setting<SpeedMineMode> renderMode;
    public Setting<Boolean> air;
    public Setting<Boolean> fade;
    public Setting<SpeedMineMode_5> colorMode;
    public Setting<Color> fill;
    public Setting<Color> outline;
    public Setting<Set<Block>> blocks;
    public final ArrayDeque<Boolean> arrayDeque;
    public final Stopwatch stopwatch;
    public final Stopwatch stopwatch2;
    public final Stopwatch stopwatch3;
    public final Stopwatch stopwatch4;
    public final AtomicBoolean atomicBoolean2;
    public final SpeedMineHelper_2 speedMineHelper_2;
    public final SpeedMineHelper_3 speedMineHelper_3;

    @Nullable
    public SpeedMineRunnable speedMineRunnable;
    public float val;
    public float val2;
    public int num;
    public int num2;
    public SpeedMineSearchHelper4 speedMineSearchHelper4;
    public BlockPos blockPos;
    public BlockPos blockPos2;
    public volatile boolean flag2;
    public static final AutoExp autoExp = (AutoExp) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoExp.class);
    public static AutoCrystal autoCrystal = (AutoCrystal) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoCrystal.class);
    public static final AutoMine autoMine = (AutoMine) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoMine.class);
    public static final NoGlitchBlocks noGlitchBlocks = (NoGlitchBlocks) BaritoneHelper_3.baritoneHelper_4.getModule117(NoGlitchBlocks.class);
    public static final AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static final NoSlow noSlow = (NoSlow) BaritoneHelper_3.baritoneHelper_4.getModule117(NoSlow.class);
    public static ItemSaver itemSaver = (ItemSaver) BaritoneHelper_3.baritoneHelper_4.getModule117(ItemSaver.class);
    public static final AtomicBoolean atomicBoolean = new AtomicBoolean();

    public SpeedMine() {
        super("SpeedMine", "Mines blocks silently.", Category.PLAYER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.arrayDeque = new ArrayDeque<>();
        this.stopwatch = new Stopwatch();
        this.stopwatch2 = new Stopwatch();
        this.stopwatch3 = new Stopwatch();
        this.stopwatch4 = new Stopwatch();
        this.atomicBoolean2 = new AtomicBoolean();
        this.speedMineHelper_2 = new SpeedMineHelper_2(this);
        this.speedMineHelper_3 = new SpeedMineHelper_3();
        this.num = -1;
        this.speedMineSearchHelper4 = null;
        this.flag2 = false;
        this.rebreak.do2329("RebreakPage");
        this.autoSwap2.do2329("SwapPage");
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        reset();
        this.blockPos2 = null;
        atomicBoolean.set(false);
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        if (SearchHelper4_7.is2435(this.blockPos)) {
            return "%.2f".formatted(Float.valueOf(MathHelper.clamp(get1052() / this.damage.getValue().floatValue(), 0.0f, 1.0f)));
        }
        return null;
    }

    @Listen
    public void onAttackBlock(AttackBlockEvent attackBlockEvent) {
        if (!is1056(attackBlockEvent.getBlockPos386())) {
            this.blockPos2 = null;
            reset();
            return;
        }
        if (minecraftClient.player.isSpectator() || minecraftClient.player.isCreative()) {
            return;
        }
        attackBlockEvent.do1162();
        if (!this.stopwatch.is419(100L) && !flag) {
            if (attackBlockEvent.getBlockPos386().equals(this.blockPos)) {
                this.stopwatch.reset();
                return;
            }
            return;
        }
        if (SearchHelper4_7.is2435(attackBlockEvent.getBlockPos386()) || flag) {
            if (this.extraBreak.getValue().booleanValue() && this.speedMineSearchHelper4 != null && attackBlockEvent.getBlockPos386().equals(this.speedMineSearchHelper4.getBlockPos386())) {
                return;
            }
            if (minecraftClient.player.getEyePos().squaredDistanceTo(attackBlockEvent.getBlockPos386().toCenterPos()) > this.range.getValue().floatValue() * this.range.getValue().floatValue()) {
                return;
            }
            this.stopwatch.reset();
            boolean z = false;
            if (this.blockPos != null && !this.blockPos.equals(attackBlockEvent.getBlockPos386())) {
                reset();
                z = true;
            }
            if (!z && this.blockPos != null && this.blockPos.equals(attackBlockEvent.getBlockPos386())) {
                if (this.clickReset.getValue().booleanValue() && this.stopwatch3.is419(100L)) {
                    this.blockPos2 = null;
                    reset();
                    return;
                }
                return;
            }
            if (this.alternative.getValue().booleanValue() && this.autoSwap.getValue() == SpeedMineMode_3.SILENT && this.swapReset.getValue().booleanValue()) {
                if (this.flag2) {
                    this.flag2 = false;
                    reset();
                    return;
                } else if (autoExp.isToggled() && autoExp.mode.getValue() == AutoExp.SpeedMineMode.SILENT && !autoExp.flag) {
                    reset();
                    return;
                }
            }
            if (!flag) {
                autoMine.do2752(true);
            }
            BlockPos blockPos386 = attackBlockEvent.getBlockPos386();
            this.blockPos2 = blockPos386;
            this.blockPos = blockPos386;
            Direction direction1055 = getDirection1055(this.blockPos);
            if (this.swing.getValue().booleanValue()) {
                minecraftClient.player.swingHand(Hand.MAIN_HAND);
            }
            int i = get1050(this.blockPos);
            int i2 = minecraftClient.player.getInventory().selectedSlot;
            boolean z2 = i != -1;
            if (this.autoSwap.getValue() == SpeedMineMode_3.NORMAL) {
                i = FireworksHelper.get459(this.blockPos, true);
                if (i != i2 && this.swapBack.getValue().booleanValue()) {
                    this.num2 = i2;
                }
            }
            if (i != -1) {
                if (this.alternative.getValue().booleanValue() && this.autoSwap.getValue() == SpeedMineMode_3.SILENT) {
                    FireworksHelper.do439(i);
                } else if (i2 != i) {
                    FireworksHelper.do456(i);
                }
            }
            if (this.extraBreak.getValue().booleanValue()) {
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, this.blockPos, direction1055);
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, this.blockPos, direction1055);
                if (this.speedMineSearchHelper4 == null) {
                    this.speedMineSearchHelper4 = new SpeedMineSearchHelper4(this.blockPos);
                }
            } else {
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, this.blockPos, direction1055);
                if (z2) {
                    AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, this.blockPos, direction1055);
                }
            }
            if (z2 && i != -1) {
                if (this.autoSwap.getValue() == SpeedMineMode_3.SILENT) {
                    if (this.alternative.getValue().booleanValue()) {
                        FireworksHelper.do439(i);
                    } else if (i2 != i) {
                        FireworksHelper.do456(i2);
                    }
                }
                BaritoneHelper_3.stashFinderSearchHelper4.do1549(this.blockPos);
                this.stopwatch.setTime(-1L);
            }
            minecraftClient.player.swingHand(Hand.MAIN_HAND);
            this.stopwatch3.reset();
        }
    }

    @Listen
    public void onDisconnect(DisconnectEvent disconnectEvent) {
        reset();
        this.blockPos2 = null;
    }

    @Listen(get219= Helper_7.num2)
    public void onEvent3(SpeedMineEvent speedMineEvent) {
        if (this.alternative.getValue().booleanValue() && this.autoSwap.getValue() == SpeedMineMode_3.SILENT && this.swapReset.getValue().booleanValue()) {
            this.atomicBoolean2.set(true);
            this.val2 = 0.0f;
            this.val = 0.0f;
            BaritoneHelper_3.tooltipsSearchHelper4_2.do164(() -> {
                this.atomicBoolean2.set(false);
                BlockPos blockPos = this.blockPos;
                if (blockPos == null) {
                    return;
                }
                if (this.swing.getValue().booleanValue()) {
                    AutoSignSearchHelper4.do2559(Hand.MAIN_HAND);
                }
                if (SearchHelper4_7.is2435(blockPos)) {
                    flag = true;
                    reset();
                    onAttackBlock(new AttackBlockEvent(blockPos, getDirection1055(blockPos)));
                    flag = false;
                    return;
                }
                if (this.rebreak2.getValue() == SpeedMineMode_2.FAST) {
                    AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, getDirection1055(blockPos));
                    this.arrayDeque.clear();
                }
            }, 1);
        }
    }

    @Listen
    public void onChannelRead0(ChannelRead0Event channelRead0Event) {
        this.speedMineHelper_2.onChannelRead0(channelRead0Event);
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if (!SearchHelper4_8.is724() || this.damage.getValue().floatValue() >= 1.0f || antiCheat.is238()) {
            return;
        }
        PlayerActionC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof PlayerActionC2SPacket ? (PlayerActionC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if (packet904 instanceof PlayerActionC2SPacket) {
            PlayerActionC2SPacket playerActionC2SPacket = packet904;
            if (playerActionC2SPacket.getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, playerActionC2SPacket.getPos().add(0, 500, 0), playerActionC2SPacket.getDirection());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:131:0x0874, code lost:
    
        if (me.mioclient.BaritoneHelper_3.stashFinderSearchHelper4.is1556(r8.blockPos) != false) goto L143;
     */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0396 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0397  */
    @Listen(get219= Helper_7.num2)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onEvent2(HoleSnapEvent holeSnapEvent) {
        boolean z;
        if (atomicBoolean.get()) {
            return;
        }
        if (this.autoSwap.getValue() == SpeedMineMode_3.NONE && !ItemSaver.is905(minecraftClient.player.getMainHandStack()) && itemSaver.isToggled()) {
            return;
        }
        if (SearchHelper_3.get643() <= 4.0f) {
            if (minecraftClient.player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                z = false;
                boolean z2 = z;
                if (this.speedMineSearchHelper4 == null && !this.speedMineSearchHelper4.getBlockPos386().equals(this.blockPos) && this.speedMineSearchHelper4.get2142() >= 1.0f && !noSlow.is3108() && z2) {
                    atomicBoolean.set(true);
                    int i = minecraftClient.player.getInventory().selectedSlot;
                    int i2 = FireworksHelper.get459(this.speedMineSearchHelper4.getBlockPos386(), !this.alternative.getValue().booleanValue());
                    if (this.alternative.getValue().booleanValue()) {
                        FireworksHelper.do439(i2);
                    } else {
                        AutoSignSearchHelper4.do2570(i2, true);
                    }
                    do1045(this.speedMineSearchHelper4.getBlockPos386());
                    this.stopwatch4.reset();
                    autoMine.speedMineHelper.do1227(this.speedMineSearchHelper4.getBlockPos386());
                    this.speedMineSearchHelper4 = null;
                    BaritoneHelper_3.tooltipsSearchHelper4_2.do164(() -> {
                        if (this.alternative.getValue().booleanValue()) {
                            FireworksHelper.do439(i2);
                        } else {
                            AutoSignSearchHelper4.do2570(i, true);
                            minecraftClient.player.getInventory().selectedSlot = i;
                        }
                        atomicBoolean.set(false);
                    }, 2);
                    return;
                }
                if (this.num2 == -1 && !SearchHelper4_7.is2435(this.blockPos) && this.autoSwap.getValue() == SpeedMineMode_3.NORMAL && this.swapBack.getValue().booleanValue()) {
                    FireworksHelper.do456(this.num2);
                    this.num2 = -1;
                    return;
                }
                if (is1044() && !minecraftClient.player.isSpectator() && !minecraftClient.player.isCreative() && SearchHelper4_7.is2435(this.blockPos)) {
                    if (this.limit.getValue().floatValue() != 1.0f || (get1052() / this.damage.getValue().floatValue() >= this.limit.getValue().floatValue() && (this.autoSwap.getValue() != SpeedMineMode_3.NONE || !this.holdingBest.getValue().booleanValue() || this.num == minecraftClient.player.getInventory().selectedSlot))) {
                        do1045(this.blockPos);
                    }
                    if (get1052() >= this.damage.getValue().floatValue()) {
                        return;
                    }
                    if (this.rebreak2.getValue() == SpeedMineMode_2.INSTANT) {
                        if (!this.stopwatch2.is418(this.instantDelay.getValue().floatValue(), TimeUnit.SECONDS)) {
                            return;
                        }
                    }
                    if (noSlow.is3108()) {
                        return;
                    }
                    if (!this.assistant.getValue().booleanValue() || is1049()) {
                        int i3 = minecraftClient.player.getInventory().selectedSlot;
                        BlockState blockState = minecraftClient.world.getBlockState(this.blockPos);
                        this.speedMineRunnable = new SpeedMineRunnable(this, minecraftClient.player.getMainHandStack().getItem(), this.num, minecraftClient.player.getInventory().selectedSlot);
                        Direction direction1055 = getDirection1055(this.blockPos);
                        if (this.autoSwap.getValue() == SpeedMineMode_3.NONE || this.num == -1 || minecraftClient.player.getInventory().selectedSlot == this.num) {
                            if (i3 != this.num && this.rebreak2.getValue() == SpeedMineMode_2.INSTANT && this.holdingBest.getValue().booleanValue()) {
                                return;
                            }
                        } else if (this.autoSwap.getValue() == SpeedMineMode_3.SILENT && this.alternative.getValue().booleanValue()) {
                            FireworksHelper.do439(this.num);
                        } else {
                            this.num2 = minecraftClient.player.getInventory().selectedSlot;
                            FireworksHelper.do456(this.num);
                        }
                        this.stopwatch2.reset();
                        if (SearchHelper4_7.is2435(this.blockPos)) {
                            this.stopwatch4.reset();
                        }
                        if (this.swing.getValue().booleanValue()) {
                            AutoSignSearchHelper4.do2559(Hand.MAIN_HAND);
                        }
                        if (!SearchHelper4_8.is724() && this.rebreak2.getValue() != SpeedMineMode_2.INSTANT) {
                            AutoSignSearchHelper4.do2562(minecraftClient.player.getX(), minecraftClient.player.getY(), minecraftClient.player.getZ(), minecraftClient.player.isOnGround());
                        }
                        BlockPos blockPos = this.blockPos;
                        this.speedMineHelper_2.do2081(this.blockPos);
                        AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, this.blockPos, direction1055);
                        if (antiCheat.is238() && this.rebreak2.getValue() == SpeedMineMode_2.NONE) {
                            AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.blockPos, direction1055);
                        }
                        BaritoneHelper_3.stashFinderSearchHelper4.do1550(this.blockPos);
                        if (this.rebreak2.getValue() == SpeedMineMode_2.FAST) {
                            AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.blockPos, direction1055);
                            AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, this.blockPos, direction1055);
                            AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, this.blockPos, direction1055);
                            this.blockPos = null;
                        }
                        if (this.autoSwap.getValue() == SpeedMineMode_3.SILENT && this.num != -1) {
                            int i4 = this.num;
                            Runnable runnable = () -> {
                                if (this.alternative.getValue().booleanValue() && this.autoSwap.getValue() == SpeedMineMode_3.SILENT) {
                                    FireworksHelper.do439(i4);
                                } else if (i3 != this.num || this.f121.getValue() == SpeedMineMode_4.TICK) {
                                    FireworksHelper.do456(i3);
                                }
                                autoCrystal.atomicBoolean.set(false);
                            };
                            if (this.f121.getValue() == SpeedMineMode_4.TICK) {
                                atomicBoolean.set(true);
                                BaritoneHelper_3.tooltipsSearchHelper4_2.do164(() -> {
                                    atomicBoolean.set(false);
                                    runnable.run();
                                }, 2);
                            } else {
                                runnable.run();
                            }
                        }
                        if (this.autoSwap.getValue() == SpeedMineMode_3.SILENT && this.alternative.getValue().booleanValue()) {
                            BaritoneHelper_3.tooltipsSearchHelper4_2.do164(this.speedMineRunnable, 2);
                        }
                        if (this.rebreak2.getValue() == SpeedMineMode_2.INSTANT) {
                            if (blockState != null && (blockState.getBlock() instanceof ShulkerBoxBlock)) {
                            }
                            if (this.rotate.getValue().booleanValue() || !SearchHelper4_8.is1144()) {
                                return;
                            }
                            BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2487(SearchHelper4_8.getFloatArray2484(blockPos.toCenterPos()), BaritoneHelper_3.searchHelper4_8.get2474()), 150);
                            return;
                        }
                        reset();
                        if (this.rotate.getValue().booleanValue()) {
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                }
                return;
            }
        }
        z = true;
        boolean z22 = z;
        if (this.speedMineSearchHelper4 == null) {
        }
        if (this.num2 == -1) {
        }
        if (is1044()) {
            return;
        }
        if (this.limit.getValue().floatValue() != 1.0f) {
        }
        do1045(this.blockPos);
        if (get1052() >= this.damage.getValue().floatValue()) {
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        float f = this.tPSSync.getValue().booleanValue() ? BaritoneHelper_3.holeSnapSearchHelper4_4.get2621() : 1.0f;
        if (this.speedMineSearchHelper4 != null) {
            this.speedMineSearchHelper4.do2143((float) (SearchHelper4_7.get2436(minecraftClient.player.getInventory().getStack(FireworksHelper.get459(this.speedMineSearchHelper4.getBlockPos386(), !this.alternative.getValue().booleanValue())), minecraftClient.world.getBlockState(this.speedMineSearchHelper4.getBlockPos386()), minecraftClient.player.isOnGround()) * f));
            if (minecraftClient.world.isAir(this.speedMineSearchHelper4.getBlockPos386())) {
                this.speedMineSearchHelper4 = null;
            }
        }
        if (this.blockPos2 != null) {
            if (minecraftClient.world.getBlockState(this.blockPos2).getBlock() instanceof ShulkerBoxBlock) {
                if (BaritoneHelper_3.stashFinderSearchHelper4.is1556(this.blockPos2)) {
                    this.blockPos2 = null;
                }
            }
        }
        if (this.atomicBoolean2.get()) {
            return;
        }
        this.val2 = this.val;
        if (this.blockPos == null) {
            if (this.blockPos2 == null || minecraftClient.player.isCreative() || !SearchHelper4_7.is2435(this.blockPos2) || this.rebreak2.getValue() != SpeedMineMode_2.FAST) {
                return;
            }
            this.blockPos = this.blockPos2;
            return;
        }
        this.num = FireworksHelper.get459(this.blockPos, !this.alternative.getValue().booleanValue());
        if (this.rebreak2.getValue() == SpeedMineMode_2.INSTANT && this.blockPos != null) {
            if (minecraftClient.world.getBlockState(this.blockPos).getBlock() == Blocks.AIR) {
                this.stopwatch2.reset();
            }
        }
        if (!SearchHelper4_7.is2435(this.blockPos)) {
            if (this.rebreak2.getValue() != SpeedMineMode_2.FAST) {
                if (this.rebreak2.getValue() != SpeedMineMode_2.INSTANT) {
                    reset();
                    return;
                }
                return;
            } else {
                this.arrayDeque.add(Boolean.valueOf(minecraftClient.player.isOnGround()));
                if (this.arrayDeque.size() > 200) {
                    this.arrayDeque.removeLast();
                    return;
                }
                return;
            }
        }
        BlockState blockState = minecraftClient.world.getBlockState(this.blockPos);
        ItemStack stack = minecraftClient.player.getInventory().getStack(get1047());
        if (get1047() != minecraftClient.player.getInventory().selectedSlot && this.f121.getValue() == SpeedMineMode_4.SLOW) {
            stack = new ItemStack(stack.getItem());
        }
        boolean z = false;
        if (this.rebreak2.getValue() == SpeedMineMode_2.FAST) {
            while (!this.arrayDeque.isEmpty()) {
                this.val += (float) (SearchHelper4_7.get2436(stack, blockState, this.arrayDeque.poll().booleanValue()) * f);
                z = true;
            }
            this.val2 = this.val;
        }
        if (!z || !blockState.isOf(Blocks.OBSIDIAN)) {
            this.val += (float) (SearchHelper4_7.get2436(stack, blockState, minecraftClient.player.isOnGround()) * f);
        } else {
            this.val = MathHelper.clamp(this.val, 0.0f, 0.9900000095367432f);
            this.val2 = this.val;
        }
    }

    @Listen
    public void do31(MotionEvent motionEvent) {
        if (motionEvent.getKeyPearlMode1472() != KeyPearlMode.Pre || this.blockPos == null) {
            return;
        }
        if (minecraftClient.player.getEyePos().distanceTo(this.blockPos.toCenterPos()) <= this.range.getValue().floatValue()) {
            return;
        }
        do1048();
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (this.render.getValue().booleanValue()) {
            if (this.extraBreak.getValue().booleanValue() && this.speedMineSearchHelper4 != null && !this.speedMineSearchHelper4.getBlockPos386().equals(this.blockPos)) {
                this.speedMineSearchHelper4.do2141(this, inner_3);
            }
            if (this.blockPos == null) {
                return;
            }
            VoxelShape outlineShape = minecraftClient.world.getBlockState(this.blockPos).getOutlineShape(minecraftClient.world, this.blockPos);
            boolean z = this.rebreak2.getValue() == SpeedMineMode_2.INSTANT;
            if (!outlineShape.isEmpty() && SearchHelper4_7.is2435(this.blockPos)) {
                this.speedMineHelper_3.do2258();
            } else {
                if (!z) {
                    return;
                }
                if (!this.air.getValue().booleanValue() && !this.fade.getValue().booleanValue()) {
                    return;
                }
            }
            float clamp = MathHelper.clamp(MathHelper.lerp(SearchHelper_2.get536(), this.val2, get1052()) / this.damage.getValue().floatValue(), 0.0f, 1.0f);
            Color[] colorArray1773 = this.colorMode.getValue().getColorArray1773(this, clamp);
            Box offset = this.renderMode.getValue().getBox809(this, outlineShape.isEmpty() ? new Box(0.0d, 0.0d, 0.0d, 1.0, 1.0, 1.0) : outlineShape.getBoundingBox(), clamp).offset(this.blockPos);
            if (minecraftClient.world.getBlockState(this.blockPos).isAir()) {
                offset = new Box(this.blockPos);
            }
            if (!this.fade.getValue().booleanValue() || this.air.getValue().booleanValue()) {
                PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), offset, colorArray1773[0]);
                PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), offset, colorArray1773[1], this.lineWidth.getValue().floatValue());
                return;
            }
            this.speedMineHelper_3.do2259(offset);
            this.speedMineHelper_3.do2260(this.lineWidth.getValue().floatValue());
            this.speedMineHelper_3.do2261(inner_3.getMatrixStack472(), colorArray1773[0], colorArray1773[1], z ? 600.0f : 1.0f, true);
        }
    }

    public boolean is1044() {
        if (this.rebreak2.getValue() != SpeedMineMode_2.INSTANT || this.autoSwap.getValue() == SpeedMineMode_3.NONE) {
            return false;
        }
        this.speedMineHelper_2.do466();
        boolean is2082 = this.speedMineHelper_2.is2082();
        BlockPos blockPos = this.blockPos;
        if (is2082) {
            reset();
            this.atomicBoolean2.set(true);
            BaritoneHelper_3.tooltipsSearchHelper4_2.do164(() -> {
                this.atomicBoolean2.set(false);
                minecraftClient.interactionManager.attackBlock(blockPos, Direction.UP);
            }, 1);
        }
        return is2082;
    }

    public void do1045(BlockPos blockPos) {
        if (!this.rotate.getValue().booleanValue() || SearchHelper4_8.is1144()) {
            return;
        }
        Vec3d vec3d2430 = SearchHelper4_7.getVec3d2430(blockPos, AutoCraftMode.X8);
        if (vec3d2430 == null) {
            vec3d2430 = blockPos.toCenterPos();
        }
        BaritoneHelper_3.searchHelper4_8.do2478(SearchHelper4_8.getFloatArray2487(SearchHelper4_8.getFloatArray2484(vec3d2430), BaritoneHelper_3.searchHelper4_8.get2474()), 150, true);
    }

    public double get1046() {
        if (this.blockPos == null) {
            return 0.0d;
        }
        return SearchHelper4_7.get2436(minecraftClient.player.getInventory().getStack(get1047()), minecraftClient.world.getBlockState(this.blockPos), minecraftClient.player.isOnGround()) * (this.tPSSync.getValue().booleanValue() ? BaritoneHelper_3.holeSnapSearchHelper4_4.get2621() : 1.0f);
    }

    public int get1047() {
        return this.num < 0 ? minecraftClient.player.getInventory().selectedSlot : this.num;
    }

    public void do1048() {
        this.blockPos2 = null;
        reset();
    }

    public void reset() {
        if (this.blockPos != null && !is1469()) {
            AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.blockPos, getDirection1055(this.blockPos));
            AutoSignSearchHelper4.do2559(Hand.MAIN_HAND);
            ((BreakingProgressHelper)(Object) minecraftClient.interactionManager).setBreakingBlock(false);
            ((BreakingProgressHelper)(Object) minecraftClient.interactionManager).setBreakingProgress(0.0f);
        }
        this.blockPos = null;
        this.val = 0.0f;
        this.num = -1;
        this.arrayDeque.clear();
        this.stopwatch3.reset();
        this.speedMineHelper_2.reset();
    }

    public boolean is1049() {
        if (!minecraftClient.world.getBlockState(getBlockPos1053()).isOf(Blocks.OBSIDIAN)) {
            return true;
        }
        Box box = new Box(getBlockPos1053());
        boolean z = false;
        for (Entity entity : minecraftClient.world.getEntities()) {
            if ((entity instanceof PlayerEntity) && entity != minecraftClient.player && !z) {
                if (entity.getBoundingBox().stretch(0.0d, 1.0, 0.0d).expand(1.0, 0.0d, 1.0).intersects(box)) {
                    z = true;
                }
            } else if (entity instanceof EndCrystalEntity) {
                if (entity.getBoundingBox().expand(0.0d, FreecamHelper.val3, 0.0d).intersects(box)) {
                    return true;
                }
            } else {
                continue;
            }
        }
        if (z) {
            if (!this.stopwatch4.is419((long) (1000.0f / BaritoneHelper_3.holeSnapSearchHelper4_4.get2621()))) {
                return false;
            }
        }
        return true;
    }

    public int get1050(BlockPos blockPos) {
        if (noGlitchBlocks.isToggled() && noGlitchBlocks.break_.getValue().booleanValue()) {
            return -1;
        }
        int i = FireworksHelper.get459(this.blockPos, !this.alternative.getValue().booleanValue());
        int i2 = minecraftClient.player.getInventory().selectedSlot;
        double d = SearchHelper4_7.get2436(minecraftClient.player.getInventory().getStack(i), minecraftClient.world.getBlockState(blockPos), minecraftClient.player.isOnGround());
        if ((this.autoSwap.getValue() != SpeedMineMode_3.NONE || i == i2) && d > this.damage.getValue().floatValue()) {
            return i;
        }
        return -1;
    }

    public BlockPos getBlockPos1051() {
        if (isToggled()) {
            return this.blockPos;
        }
        if (!((BreakingProgressHelper)(Object) minecraftClient.interactionManager).isBreakingBlock() || ((BreakingProgressHelper)(Object) minecraftClient.interactionManager).getBreakingProgress() < 0.9) {
            return null;
        }
        return ((BreakingProgressHelper)(Object) minecraftClient.interactionManager).getCurrentBreakingBlock();
    }

    public float get1052() {
        return MathHelper.clamp(this.val, 0.0f, 1.0f);
    }

    public BlockPos getBlockPos1053() {
        return this.blockPos;
    }

    public BlockPos getBlockPos1054() {
        switch (this.rebreak2.getValue()) {
            case NONE:
                return null;
            case FAST:
                return this.blockPos2;
            case INSTANT:
                return this.blockPos;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public Direction getDirection1055(BlockPos blockPos) {
        List<Direction> list3031 = PhaseESPSearchHelper4_2.getList3031(blockPos);
        return !list3031.isEmpty() ? list3031.get(0) : Direction.UP;
    }

    public boolean is1056(BlockPos blockPos) {
        return this.mode.getValue().is1392(minecraftClient.world.getBlockState(blockPos).getBlock(), this.blocks);
    }

    public float get99() {
        return this.lineWidth.getValue().floatValue();
    }

    public SpeedMineRunnable getSpeedMineRunnable1057() {
        return this.speedMineRunnable;
    }

    public boolean is1058(long j) {
        return this.stopwatch4.is419(j);
    }

    public SpeedMineSearchHelper4 getSpeedMineSearchHelper41059() {
        return this.speedMineSearchHelper4;
    }

    public static boolean is1060() {
        return atomicBoolean.get();
    }
}
