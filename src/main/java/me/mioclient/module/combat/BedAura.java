package me.mioclient.module.combat;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import me.mioclient.AntiPhaseSearchHelper4;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BedAuraData;
import me.mioclient.BedAuraHelper;
import me.mioclient.BedAuraSearchHelper46;
import me.mioclient.FireworksHelper;
import me.mioclient.HoleSnapEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/BedAura.class */
public final class BedAura extends Module {
    public static final AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public Setting<Boolean> place;
    public Setting<Integer> delay2;
    public Setting<Float> range2;
    public Setting<Boolean> strictDirection;
    public Setting<Boolean> rotate;
    public Setting<Boolean> liquidPlace;
    public Setting<Boolean> airPlace;
    public Setting<Boolean> break_;
    public Setting<Integer> delay;
    public Setting<Float> range;
    public Setting<Boolean> tPSSync;
    public Setting<Boolean> instant;
    public Setting<Boolean> extrapolation;
    public Setting<Integer> ticks;
    public Setting<Boolean> refill;
    public Setting<Integer> slot;
    public Setting<Boolean> render;
    public Setting<Color> fill;
    public Setting<Color> outline;
    public Setting<Boolean> fade;
    public Setting<Float> fadeTime;
    public Setting<Boolean> targeting;
    public Setting<Float> minDamage;
    public Setting<Float> maxSelfDamage;
    public Setting<Boolean> antiSucide;
    public final BedAuraHelper bedAuraHelper;
    public final BedAuraSearchHelper46 bedAuraSearchHelper46;
    public final Stopwatch stopwatch;
    public final Stopwatch stopwatch2;
    public PlayerEntity playerEntity;
    public BedAuraData bedAuraData;
    public BlockPos blockPos;

    public BedAura() {
        super("BedAura", new ArgumentTypeHelper().getArgumentTypeHelper2919(String.valueOf(Formatting.RED)).getString2921("Blows your enemies up using beds. \n\u0001Doesn't work in the overworld."), Category.COMBAT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.bedAuraHelper = new BedAuraHelper(this);
        this.bedAuraSearchHelper46 = new BedAuraSearchHelper46(this);
        this.stopwatch = new Stopwatch();
        this.stopwatch2 = new Stopwatch();
        BaritoneHelper_3.antiPhaseSearchHelper4.register(new AntiPhaseSearchHelper4.Record(this, this.fill, this.outline, () -> {
            return Float.valueOf(1.0f);
        }, this.fadeTime, () -> {
            return false;
        }, this.fade, 1000));
        this.range2.do2329("PlaceRange");
        this.range.do2329("BreakRange");
        this.delay2.do2329("PlaceDelay");
        this.delay.do2329("BreakDelay");
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        if (this.playerEntity == null) {
            return null;
        }
        return this.playerEntity.getGameProfile().getName();
    }

    @Listen
    public void onEvent(HoleSnapEvent holeSnapEvent) {
        this.playerEntity = getPlayerEntity886();
        if (this.playerEntity == null) {
            this.bedAuraData = null;
        } else {
            this.bedAuraData = this.bedAuraHelper.getObject970(this.playerEntity);
            this.blockPos = this.bedAuraSearchHelper46.getObject970(this.playerEntity);
        }
        if (this.bedAuraData == null) {
            return;
        }
        do1424();
        if (this.break_.getValue().booleanValue()) {
            if (is1427(this.stopwatch2, this.delay)) {
                do1423(this.blockPos);
            }
        }
        if (this.place.getValue().booleanValue()) {
            if (is1427(this.stopwatch, this.delay2)) {
                do1421(this.bedAuraData);
            }
        }
    }

    public PlayerEntity getPlayerEntity886() {
        PlayerEntity playerEntity = null;
        float intBitsToFloat = 99999.0f;
        for (PlayerEntity playerEntity2 : minecraftClient.world.getPlayers()) {
            if (playerEntity2 != minecraftClient.player && !BaritoneHelper_3.searchHelper4_14.is520(playerEntity2)) {
                if (intBitsToFloat > minecraftClient.player.getEyePos().distanceTo(playerEntity2.getPos())) {
                    playerEntity = playerEntity2;
                    intBitsToFloat = (float) minecraftClient.player.getEyePos().distanceTo(playerEntity2.getPos());
                }
            }
        }
        return playerEntity;
    }

    public void do1421(BedAuraData bedAuraData) {
        int i = FireworksHelper.get448(itemStack -> {
            return itemStack.getItem() instanceof BedItem;
        });
        int i2 = minecraftClient.player.getInventory().selectedSlot;
        if (i == -1) {
            return;
        }
        FireworksHelper.do438(i);
        do1422(bedAuraData);
        FireworksHelper.do438(i2);
    }

    public void do1422(BedAuraData bedAuraData) {
        float[] floatArray2486;
        if (bedAuraData != null && PhaseESPSearchHelper4_2.is3041(bedAuraData.getBlockPos153(), true)) {
            if (this.rotate.getValue().booleanValue()) {
                floatArray2486 = SearchHelper4_8.getFloatArray2484(bedAuraData.getBlockHitResult157().getPos());
            } else {
                floatArray2486 = SearchHelper4_8.getFloatArray2486(bedAuraData.getBlockPos153().toCenterPos(), bedAuraData.getBlockPos154().toCenterPos());
            }
            BaritoneHelper_3.searchHelper4_8.do2477(floatArray2486, 500);
            BlockHitResult blockHitResult157 = bedAuraData.getBlockHitResult157();
            BaritoneHelper_3.antiPhaseSearchHelper4_2.do2227(() -> {
                AutoSignSearchHelper4.do2556(Hand.MAIN_HAND, blockHitResult157);
            });
            minecraftClient.player.swingHand(Hand.MAIN_HAND);
            Box enclosing = Box.enclosing(bedAuraData.getBlockPos153(), bedAuraData.getBlockPos154());
            Box expand = enclosing.withMaxY(enclosing.minY + 0.5625).expand(0.0020000000949949026);
            if (this.render.getValue().booleanValue()) {
                BaritoneHelper_3.antiPhaseSearchHelper4.do2133(this, expand);
            }
            if (this.instant.getValue().booleanValue()) {
                BaritoneHelper_3.antiPhaseSearchHelper4_2.do2226(() -> {
                    AutoSignSearchHelper4.do2556(Hand.MAIN_HAND, new BlockHitResult(bedAuraData.getBlockPos153().toCenterPos(), getDirection1425(bedAuraData.getBlockPos153(), Direction.UP), bedAuraData.getBlockPos153(), false));
                    minecraftClient.player.swingHand(Hand.MAIN_HAND);
                });
            }
            this.stopwatch.reset();
        }
    }

    public void do1423(BlockPos blockPos) {
        if (blockPos == null) {
            return;
        }
        BlockHitResult blockHitResult = new BlockHitResult(blockPos.toCenterPos(), getDirection1425(blockPos, Direction.UP), blockPos, false);
        BaritoneHelper_3.antiPhaseSearchHelper4_2.do2226(() -> {
            minecraftClient.interactionManager.interactBlock(minecraftClient.player, Hand.MAIN_HAND, blockHitResult);
            minecraftClient.player.swingHand(Hand.MAIN_HAND);
        });
        Iterator it = Direction.Type.HORIZONTAL.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            BlockPos offset = blockPos.offset((Direction) it.next());
            if (minecraftClient.world.getBlockState(offset).getBlock() instanceof BedBlock) {
                BaritoneHelper_3.stashFinderSearchHelper4.do1549(offset);
                break;
            }
        }
        BaritoneHelper_3.stashFinderSearchHelper4.do1549(blockPos);
        this.stopwatch2.reset();
    }

    public void do1424() {
        int i;
        if (this.refill.getValue().booleanValue()) {
            int intValue = this.slot.getValue().intValue() - 1;
            if ((minecraftClient.player.getInventory().getStack(intValue).getItem() instanceof BedItem) || (i = FireworksHelper.get445(itemStack -> {
                return itemStack.getItem() instanceof BedItem;
            }, true)) == -1) {
                return;
            }
            FireworksHelper.do440(i, FireworksHelper.get453(intValue));
        }
    }

    public Direction getDirection1425(BlockPos blockPos, Direction direction) {
        List<Direction> list3031 = PhaseESPSearchHelper4_2.getList3031(blockPos);
        if (!list3031.isEmpty()) {
            list3031.getFirst();
        }
        return direction;
    }

    public boolean is1426(BlockPos blockPos, BedPart bedPart) {
        BlockState blockState = minecraftClient.world.getBlockState(blockPos);
        return (blockState.getBlock() instanceof BedBlock) && blockState.get(BedBlock.PART) == bedPart;
    }

    public boolean is1427(Stopwatch stopwatch, Setting<Integer> setting) {
        return stopwatch.is419((long) (((float) (50 * setting.getValue().intValue())) * (this.tPSSync.getValue().booleanValue() ? BaritoneHelper_3.holeSnapSearchHelper4_4.get2621() : 1.0f)));
    }

    public Box getBox1428(LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            if (this.extrapolation.getValue().booleanValue()) {
                return BaritoneHelper_3.mainhandHelper_2.getBox1109(playerEntity, this.ticks.getValue().intValue());
            }
        }
        return livingEntity.getBoundingBox();
    }
}
