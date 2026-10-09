package me.mioclient.module.combat;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import me.mioclient.AnchorAuraData;
import me.mioclient.AntiPhaseSearchHelper4;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.ArmorSearchHelper4;
import me.mioclient.AutoCraftMode;
import me.mioclient.AutoCrystalData_2;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.FreecamHelper;
import me.mioclient.HoleSnapEvent;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_3;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.Listen;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/AnchorAura.class */
public class AnchorAura extends Module {
    public static final AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static final Offhand offhand = (Offhand) BaritoneHelper_3.baritoneHelper_4.getModule117(Offhand.class);
    public static final AutoCrystal autoCrystal = (AutoCrystal) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoCrystal.class);
    public Setting<Integer> delay;
    public Setting<Float> range;
    public Setting<Float> wallRange;
    public Setting<Float> targetRange;
    public Setting<Float> minDamage;
    public Setting<Float> maxSelfDamage;
    public Setting<Boolean> antiSuicide;
    public Setting<Boolean> rotate;
    public Setting<Boolean> strictDirection;
    public Setting<Boolean> airPlace;
    public Setting<Boolean> liquidPlace;
    public Setting<Boolean> inhibit;
    public Setting<Boolean> sequential;
    public Setting<Boolean> render;
    public Setting<Color> fill;
    public Setting<Color> outline;
    public Setting<Boolean> fade;
    public Setting<Float> fadeTime;
    public Setting<Boolean> pause;
    public Setting<Boolean> mining;
    public Setting<Boolean> eating;
    public Setting<Float> health;
    public CompletableFuture<AnchorAuraData> completableFuture;
    public final BlockPos.Mutable mutable;
    public final Stopwatch stopwatch;
    public final AtomicReference<AnchorAuraData> atomicReference;
    public AutoCrystalData_2 autoCrystalData_2;

    public AnchorAura() {
        super("AnchorAura", new ArgumentTypeHelper().getArgumentTypeHelper2919(String.valueOf(Formatting.RED)).getString2921("Explodes respawn anchors to kill players. \n\u0001Doesn't work in the nether."), Category.COMBAT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.mutable = new BlockPos.Mutable();
        this.stopwatch = new Stopwatch();
        this.atomicReference = new AtomicReference<>();
        BaritoneHelper_3.antiPhaseSearchHelper4.register(new AntiPhaseSearchHelper4.Record(this, this.fill, this.outline, () -> {
            return Float.valueOf(1.0f);
        }, this.fadeTime, () -> {
            return false;
        }, this.fade, 450));
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.autoCrystalData_2 = null;
        if (this.completableFuture != null) {
            try {
                this.completableFuture.complete(null);
            } catch (CancellationException e) {
            }
        }
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        this.atomicReference.set(null);
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        if (this.autoCrystalData_2 != null) {
            return "%s, %.1f".formatted(this.autoCrystalData_2.getPlayerEntity626().getName().getString(), Double.valueOf(this.autoCrystalData_2.get14()));
        }
        return null;
    }

    @Listen
    public void onEvent2(HoleSnapEvent holeSnapEvent) {
        AnchorAuraData anchorAuraData;
        if (minecraftClient.world.getDimension().respawnAnchorWorks()) {
            MixinMessageIndicatorHelper.do345(Text.of("Doesn't work in the nether!"), MixinMessageIndicatorHelper.getMessageSignatureData337(-1), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode);
            disable();
            return;
        }
        do2869();
        if (is929() || (anchorAuraData = this.atomicReference.get()) == null || getHand2084() == null) {
            return;
        }
        BlockPos blockPos12 = anchorAuraData.getBlockPos12();
        BlockState blockState = minecraftClient.world.getBlockState(blockPos12);
        int i = minecraftClient.player.getInventory().selectedSlot;
        if (blockState.isOf(Blocks.RESPAWN_ANCHOR)) {
            Direction direction2870 = getDirection2870(blockPos12);
            if (direction2870 == null) {
                return;
            }
            if (!this.stopwatch.is419(this.delay.getValue().intValue() + BaritoneHelper_3.holeSnapSearchHelper4_4.get1730())) {
                return;
            }
            if (((Integer) blockState.get(RespawnAnchorBlock.CHARGES)).intValue() == 0) {
                do2866(blockPos12, direction2870, i);
            }
            if (this.rotate.getValue().booleanValue()) {
                BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2484(blockPos12.toCenterPos()), 100);
            }
            do2868(blockPos12, direction2870, Vec3d.ofCenter((Vec3i) blockPos12), Hand.OFF_HAND);
            if (this.inhibit.getValue().booleanValue()) {
                minecraftClient.world.setBlockState(blockPos12, Blocks.AIR.getDefaultState());
            }
        }
        do2867(blockPos12, anchorAuraData.getDirection1462(), getHand2084());
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (!this.sequential.getValue().booleanValue() || this.atomicReference.get() == null) {
            return;
        }
        BlockUpdateS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof BlockUpdateS2CPacket ? (BlockUpdateS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof BlockUpdateS2CPacket) {
            BlockUpdateS2CPacket blockUpdateS2CPacket = packet904;
            if (this.autoCrystalData_2 != null && blockUpdateS2CPacket.getState().isAir() && blockUpdateS2CPacket.getPos().equals(this.atomicReference.get().getBlockPos12())) {
                do2867(this.atomicReference.get().getBlockPos12(), this.atomicReference.get().getDirection1462(), getHand2084());
            }
        }
    }

    public void do2866(BlockPos blockPos, Direction direction, int i) {
        Vec3d offset = blockPos.toCenterPos().offset(direction, FreecamHelper.val2);
        Hand hand450 = FireworksHelper.getHand450(Items.GLOWSTONE);
        if (this.rotate.getValue().booleanValue()) {
            BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2484(offset), 100);
        }
        if (hand450 != null) {
            do2868(blockPos, direction, offset, Hand.MAIN_HAND);
        } else {
            int i2 = FireworksHelper.get447(Items.GLOWSTONE);
            if (i2 != -1) {
                FireworksHelper.do456(i2);
                do2868(blockPos, direction, offset, Hand.MAIN_HAND);
                FireworksHelper.do456(i);
            }
        }
        this.stopwatch.reset();
    }

    public void do2867(BlockPos blockPos, Direction direction, Hand hand) {
        if (is2876(blockPos)) {
            return;
        }
        int i = minecraftClient.player.getInventory().selectedSlot;
        int i2 = FireworksHelper.get447(Items.RESPAWN_ANCHOR);
        boolean z = PhaseESPSearchHelper4_2.getDirection3028(blockPos) == null && antiCheat.is238() && this.airPlace.getValue().booleanValue();
        if (i2 == -1 && hand == Hand.MAIN_HAND) {
            return;
        }
        if (this.rotate.getValue().booleanValue()) {
            BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2485(blockPos.toCenterPos(), direction), 100);
        }
        boolean z2 = !minecraftClient.player.getMainHandStack().isOf(Items.RESPAWN_ANCHOR);
        if (z2) {
            FireworksHelper.do456(i2);
        }
        if (z) {
            hand = HoleSnapSearchHelper4.getHand2015(hand);
            AutoSignSearchHelper4.do2568();
        }
        if (PhaseESPSearchHelper4_2.is3038(blockPos, getVec3d2871(blockPos.offset(direction)), direction, this.airPlace.getValue().booleanValue(), hand) && this.render.getValue().booleanValue()) {
            BaritoneHelper_3.antiPhaseSearchHelper4.do2132(this, blockPos);
        }
        if (z) {
            AutoSignSearchHelper4.do2568();
        }
        if (z2) {
            FireworksHelper.do456(i);
        }
    }

    public void do2868(BlockPos blockPos, Direction direction, Vec3d vec3d, Hand hand) {
        BaritoneHelper_3.antiPhaseSearchHelper4_2.do2226(() -> {
            AutoSignSearchHelper4.do2556(Hand.MAIN_HAND, new BlockHitResult(vec3d, direction, blockPos, false));
            AutoSignSearchHelper4.do2559(hand);
        });
    }

    public void do2869() {
        if (this.atomicReference.get() != null && is2875(this.atomicReference.get().getBlockPos12())) {
            this.atomicReference.set(null);
        }
        if (this.completableFuture != null && this.completableFuture.isDone() && this.atomicReference.get() == null) {
            try {
                AnchorAuraData anchorAuraData = this.completableFuture.get();
                if (anchorAuraData == null) {
                    this.atomicReference.set(null);
                } else {
                    this.atomicReference.set(anchorAuraData);
                    this.autoCrystalData_2 = anchorAuraData.getAutoCrystalData_22057();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (this.completableFuture == null || this.completableFuture.isDone() || this.completableFuture.isCancelled()) {
            if (this.autoCrystalData_2 != null) {
                if (AutoCrystalData_2.is625(this.autoCrystalData_2.getPlayerEntity626(), this.targetRange.getValue().floatValue())) {
                    this.autoCrystalData_2 = null;
                }
            }
            this.completableFuture = CompletableFuture.supplyAsync(this::getAnchorAuraData2872, executorService).exceptionally(th -> {
                return null;
            });
        }
    }

    public Direction getDirection2870(BlockPos blockPos) {
        List<Direction> list3031 = PhaseESPSearchHelper4_2.getList3031(blockPos);
        Direction direction = this.strictDirection.getValue().booleanValue() ? null : Direction.DOWN;
        if (!list3031.isEmpty()) {
            direction = list3031.get(0);
        }
        return direction;
    }

    public Vec3d getVec3d2871(BlockPos blockPos) {
        return AutoCraftMode.X8.getList899(blockPos).stream().filter(vec3d -> {
            return SearchHelper4_7.is2433(vec3d) || minecraftClient.player.getEyePos().distanceTo(vec3d) <= ((double) this.wallRange.getValue().floatValue());
        }).min(Comparator.comparing(vec3d2 -> {
            return Double.valueOf(minecraftClient.player.getEyePos().distanceTo(vec3d2));
        })).orElse(blockPos.toCenterPos());
    }

    public AnchorAuraData getAnchorAuraData2872() {
        AnchorAuraData anchorAuraData = null;
        double d = -Math.floor(this.range.getValue().floatValue());
        while (true) {
            double d2 = d;
            if (d2 >= Math.ceil(this.range.getValue().floatValue())) {
                return anchorAuraData;
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
                            if ((PhaseESPSearchHelper4_2.is3040(this.mutable, Blocks.RESPAWN_ANCHOR, true, false) || is2876(this.mutable)) && this.mutable.getY() >= minecraftClient.world.getBottomY() && this.mutable.getY() <= minecraftClient.world.getTopY()) {
                                boolean is2432 = SearchHelper4_7.is2432(AutoCraftMode.X8.getList899(this.mutable));
                                double distanceTo = minecraftClient.player.getEyePos().distanceTo(this.mutable.toCenterPos());
                                if ((is2432 || distanceTo <= this.wallRange.getValue().floatValue()) && distanceTo <= this.range.getValue().floatValue()) {
                                    Direction direction3030 = PhaseESPSearchHelper4_2.getDirection3030(this.mutable, this.strictDirection.getValue().booleanValue(), this.wallRange.getValue().floatValue() <= 0.0f);
                                    AutoCrystalData_2 autoCrystalData_22873 = getAutoCrystalData_22873(this.mutable);
                                    if ((direction3030 == null && this.airPlace.getValue().booleanValue()) || is2876(this.mutable)) {
                                        direction3030 = Direction.DOWN;
                                    }
                                    if (direction3030 == null) {
                                        if (!minecraftClient.world.getFluidState(this.mutable).isEmpty() && this.liquidPlace.getValue().booleanValue()) {
                                            direction3030 = Direction.DOWN;
                                        }
                                    }
                                    if (direction3030 != null && autoCrystalData_22873 != null && !is2874(this.mutable, autoCrystalData_22873)) {
                                        AnchorAuraData anchorAuraData2 = new AnchorAuraData(this.mutable.toImmutable(), direction3030, autoCrystalData_22873);
                                        if (is2876(this.mutable)) {
                                            return anchorAuraData2;
                                        }
                                        anchorAuraData = anchorAuraData2.getAnchorAuraData2056(anchorAuraData);
                                    }
                                }
                            }
                            d5 = d6 + 1.0;
                        } else {
                            break;
                        }
                    }
                } else {
                    break;
                }
                d3 = d4 + 1.0;
            }
            d = d2 + 1.0;
        }
    }

    public AutoCrystalData_2 getAutoCrystalData_22873(BlockPos blockPos) {
        AutoCrystalData_2 autoCrystalData_2 = null;
        for (LivingEntity livingEntity : minecraftClient.world.getPlayers()) {
            if (!AutoCrystalData_2.is625((PlayerEntity) livingEntity, this.targetRange.getValue().floatValue())) {
                double d = ArmorSearchHelper4.get1900(blockPos.toCenterPos(), livingEntity, ((PlayerEntity) livingEntity).getBoundingBox(), 5.0, true, is2876(blockPos) ? blockPos : null, (BlockPos) null);
                if (this.minDamage.getValue().floatValue() <= d && d >= FreecamHelper.val2 && (autoCrystalData_2 == null || d > autoCrystalData_2.get14())) {
                    autoCrystalData_2 = new AutoCrystalData_2((PlayerEntity) livingEntity, d);
                }
            }
        }
        return autoCrystalData_2;
    }

    public Hand getHand2084() {
        boolean z = offhand.isToggled() && offhand.item.getValue().getItem1241(false) == Items.RESPAWN_ANCHOR;
        Hand hand450 = FireworksHelper.getHand450(Items.RESPAWN_ANCHOR);
        if (z && hand450 == null) {
            return null;
        }
        return z ? Hand.OFF_HAND : Hand.MAIN_HAND;
    }

    public boolean is2874(BlockPos blockPos, AutoCrystalData_2 autoCrystalData_2) {
        if (autoCrystalData_2 == null) {
            return true;
        }
        boolean is2876 = is2876(blockPos);
        double d = ArmorSearchHelper4.get1900(Vec3d.ofCenter((Vec3i) blockPos), minecraftClient.player, minecraftClient.player.getBoundingBox(), 5.0, true, is2876(blockPos) ? blockPos : null, (BlockPos) null);
        boolean z = autoCrystalData_2.get14() >= ((double) SearchHelper_3.get644(autoCrystalData_2.getPlayerEntity626()));
        double d2 = ArmorSearchHelper4.get1900(Vec3d.ofCenter((Vec3i) blockPos), autoCrystalData_2.getPlayerEntity626(), autoCrystalData_2.getPlayerEntity626().getBoundingBox(), 5.0, true, is2876(blockPos) ? blockPos : null, (BlockPos) null);
        boolean z2 = (!z && d >= ((double) this.maxSelfDamage.getValue().floatValue())) || (d >= ((double) SearchHelper_3.get643()) && this.antiSuicide.getValue().booleanValue());
        if (is2876 && !z2) {
            return false;
        }
        if (this.minDamage.getValue().floatValue() > d2 || d2 < FreecamHelper.val2) {
            return true;
        }
        return z2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0146, code lost:
    
        if (me.mioclient.PhaseESPSearchHelper4_2.getDirection3030(r6, r5.strictDirection.getValue().booleanValue(), r5.wallRange.getValue().floatValue() <= 0.0f) == null) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean is2875(BlockPos blockPos) {
        double floatValue = (!SearchHelper4_7.is2432(AutoCraftMode.X8.getList899(blockPos)) ? this.wallRange.getValue() : this.range.getValue()).floatValue();
        if (is2874(blockPos, getAutoCrystalData_22873(blockPos))) {
            return true;
        }
        if (minecraftClient.player.getEyePos().distanceTo(blockPos.toCenterPos()) > floatValue) {
            return true;
        }
        if (minecraftClient.world.getBlockState(blockPos).isAir()) {
            if (getDirection2870(blockPos) != null) {
            }
            if (!this.airPlace.getValue().booleanValue()) {
                return true;
            }
        }
        return (PhaseESPSearchHelper4_2.is3040(blockPos, Blocks.RESPAWN_ANCHOR, true, false) || is2876(blockPos)) ? false : true;
    }

    public boolean is2876(BlockPos blockPos) {
        return minecraftClient.world.getBlockState(blockPos).isOf(Blocks.RESPAWN_ANCHOR);
    }

    public boolean is929() {
        if (is1469() || minecraftClient.player.isSleeping()) {
            return true;
        }
        if (minecraftClient.player.isUsingItem() && this.eating.getValue().booleanValue()) {
            return true;
        }
        if (minecraftClient.interactionManager.isBreakingBlock() && this.mining.getValue().booleanValue()) {
            return true;
        }
        return (autoCrystal.isToggled() && autoCrystal.atomicReference.get() != null && this.autoCrystalData_2 != null && autoCrystal.atomicReference.get().get14() > this.autoCrystalData_2.get14()) || SearchHelper_3.get643() < this.health.getValue().floatValue();
    }
}
