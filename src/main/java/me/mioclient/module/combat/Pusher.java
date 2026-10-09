package me.mioclient.module.combat;

import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Iterator;
import me.mioclient.AntiPhaseSearchHelper4;
import me.mioclient.AutoCraftMode;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.HoleSnapEvent;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.PusherHelper;
import me.mioclient.PusherSearchHelper4;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/Pusher.class */
public class Pusher extends Module {
    public Setting<Float> range;
    public Setting<Integer> delay;
    public Setting<Boolean> strictDirection;
    public Setting<Boolean> rotate;
    public Setting<Boolean> raytrace;
    public Setting<Boolean> multiTask;
    public Setting<Boolean> autoDisable;
    public Setting<Boolean> targeting;
    public Setting<Boolean> ignoreNaked;
    public Setting<Boolean> onlySafe;
    public Setting<Boolean> onlyHole;
    public Setting<Boolean> render;
    public Setting<Color> fill;
    public Setting<Color> outline;
    public Setting<Float> lineWidth;
    public Setting<Boolean> fade;
    public Setting<Float> fadeTime;
    public final Stopwatch stopwatch;
    public PlayerEntity playerEntity;
    public boolean flag;
    public BlockPos blockPos;
    public boolean flag2;
    public final PusherHelper pusherHelper;
    public final PusherSearchHelper4 pusherSearchHelper4;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/Pusher$Record.class */
    public static final class Record {
        public final Direction direction;
        public final BlockPos blockPos;
        public final Vec3d vec3d;

        public Record(Direction direction, BlockPos blockPos, Vec3d vec3d) {
            this.direction = direction;
            this.blockPos = blockPos;
            this.vec3d = vec3d;
        }




        public Direction getDirection842() {
            return this.direction;
        }

        public BlockPos getBlockPos12() {
            return this.blockPos;
        }

        public Vec3d getVec3d843() {
            return this.vec3d;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/Pusher$Record_2.class */
    public static final class Record_2 {
        public final Direction direction;
        public final BlockPos blockPos;

        public Record_2(Direction direction, BlockPos blockPos) {
            this.direction = direction;
            this.blockPos = blockPos;
        }




        public Direction getDirection842() {
            return this.direction;
        }

        public BlockPos getBlockPos12() {
            return this.blockPos;
        }
    }

    public Pusher() {
        super("Pusher", "Pushes your enemies out of their holes.", Category.COMBAT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.pusherHelper = new PusherHelper();
        this.pusherSearchHelper4 = new PusherSearchHelper4(this);
        BaritoneHelper_3.antiPhaseSearchHelper4.register(new AntiPhaseSearchHelper4.Record(this, this.fill, this.outline, this.lineWidth, this.fadeTime, () -> {
            return true;
        }, this.fade, 450));
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.flag2 = false;
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return this.playerEntity != null ? this.playerEntity.getGameProfile().getName() : super.getInfo();
    }

    @Listen
    public void onEvent2(HoleSnapEvent holeSnapEvent) {
        if (minecraftClient.isInSingleplayer()) {
            if (this.flag2) {
                return;
            }
            BaritoneHelper_3.tooltipsSearchHelper4_2.do164(() -> {
                MixinMessageIndicatorHelper.do345(Text.literal("Pusher doesn't work in singleplayer."), MixinMessageIndicatorHelper.getMessageSignatureData337(-2), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode);
            }, 1);
            this.flag2 = true;
            return;
        }
        if (!minecraftClient.player.isUsingItem() || this.multiTask.getValue().booleanValue()) {
            this.playerEntity = this.pusherSearchHelper4.getPlayerEntity886();
            if (this.playerEntity == null) {
                return;
            }
            if (this.autoDisable.getValue().booleanValue()) {
                if (BlockPos.stream(this.playerEntity.getBoundingBox()).anyMatch(blockPos -> {
                    if (!minecraftClient.world.getBlockState(blockPos).isOf(Blocks.PISTON_HEAD)) {
                        if (!minecraftClient.world.getBlockState(blockPos).isOf(Blocks.PISTON)) {
                            return false;
                        }
                    }
                    return true;
                })) {
                    disable();
                    return;
                }
            }
            if (this.stopwatch.is419(this.delay.getValue().intValue())) {
                this.flag = false;
                this.stopwatch.reset();
                do1958();
                do1959();
            }
        }
    }

    public void do1958() {
        int i = FireworksHelper.get447(Items.REDSTONE_BLOCK);
        boolean z = false;
        if (i == -1) {
            i = FireworksHelper.get447(Items.REDSTONE_TORCH);
            z = true;
        }
        int i2 = minecraftClient.player.getInventory().selectedSlot;
        if (i == -1) {
            return;
        }
        Record_2 record_2 = null;
        Iterator it = Direction.Type.HORIZONTAL.iterator();
        while (it.hasNext()) {
            Direction direction = (Direction) it.next();
            BlockPos offset = this.playerEntity.getBlockPos().up().offset(direction);
            if ((SearchHelper4_7.getBlock2449(offset) instanceof PistonBlock) && !is1963(this.playerEntity, offset, direction)) {
                record_2 = getRecord_21960(z, offset);
            }
        }
        if (record_2 == null) {
            return;
        }
        this.flag = true;
        if (this.rotate.getValue().booleanValue()) {
            BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2485(record_2.getBlockPos12().toCenterPos(), record_2.getDirection842()), 500);
        }
        FireworksHelper.do456(i);
        PhaseESPSearchHelper4_2.is3037(record_2.getBlockPos12(), record_2.getDirection842(), false, Hand.MAIN_HAND);
        AutoSignSearchHelper4.do2559(Hand.MAIN_HAND);
        FireworksHelper.do456(i2);
        if (this.render.getValue().booleanValue()) {
            BaritoneHelper_3.antiPhaseSearchHelper4.do2132(this, record_2.getBlockPos12());
        }
    }

    public void do1959() {
        Record record1962;
        if (this.flag) {
            return;
        }
        int i = FireworksHelper.get446(Items.PISTON, Items.STICKY_PISTON);
        int i2 = minecraftClient.player.getInventory().selectedSlot;
        if (i == -1) {
            return;
        }
        Record record = null;
        Iterator it = Direction.Type.HORIZONTAL.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Direction direction = (Direction) it.next();
            BlockPos offset = this.playerEntity.getBlockPos().up().offset(direction);
            if (!is1963(this.playerEntity, offset, direction)) {
                if (getDirection1964(offset) == direction.getOpposite()) {
                    record = null;
                    break;
                } else if (PhaseESPSearchHelper4_2.is3041(offset, true) && (record1962 = getRecord1962(offset, direction.getOpposite())) != null) {
                    record = record1962;
                }
            }
        }
        if (record == null) {
            return;
        }
        this.blockPos = record.getBlockPos12();
        BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2484(record.getVec3d843()), 500);
        FireworksHelper.do456(i);
        PhaseESPSearchHelper4_2.is3037(record.getBlockPos12(), record.getDirection842(), false, Hand.MAIN_HAND);
        AutoSignSearchHelper4.do2559(Hand.MAIN_HAND);
        FireworksHelper.do456(i2);
        if (this.render.getValue().booleanValue()) {
            BaritoneHelper_3.antiPhaseSearchHelper4.do2132(this, record.getBlockPos12());
        }
    }

    public Record_2 getRecord_21960(boolean z, BlockPos blockPos) {
        Record_2 record_2 = null;
        for (Direction direction : Direction.values()) {
            BlockPos offset = blockPos.offset(direction);
            if (minecraftClient.world.getBlockState(offset).isOf(Blocks.REDSTONE_BLOCK)) {
                return null;
            }
            if (PhaseESPSearchHelper4_2.is3041(offset, true)) {
                Direction direction1961 = getDirection1961(z, offset, direction.getOpposite());
                if (direction1961 != null) {
                    record_2 = new Record_2(direction1961, offset);
                }
            }
        }
        return record_2;
    }

    public Direction getDirection1961(boolean z, BlockPos blockPos, Direction direction) {
        if (!z) {
            return PhaseESPSearchHelper4_2.getDirection3029(blockPos, this.strictDirection.getValue().booleanValue());
        }
        for (Direction direction2 : Direction.values()) {
            BlockPos offset = blockPos.offset(direction2);
            if (direction != direction2 && BaritoneHelper_3.stashFinderSearchHelper4.is1557(offset)) {
                if (this.strictDirection.getValue().booleanValue()) {
                    if (!PhaseESPSearchHelper4_2.getList3031(offset).contains(direction2.getOpposite())) {
                    }
                }
                return direction2;
            }
        }
        return null;
    }

    public Record getRecord1962(BlockPos blockPos, Direction direction) {
        for (Direction direction2 : Direction.values()) {
            BlockPos offset = blockPos.offset(direction2);
            if (BaritoneHelper_3.stashFinderSearchHelper4.is1557(offset)) {
                if (this.strictDirection.getValue().booleanValue()) {
                    if (!PhaseESPSearchHelper4_2.getList3031(offset).contains(direction2.getOpposite())) {
                        continue;
                    }
                }
                if (!this.raytrace.getValue().booleanValue() || SearchHelper4_7.is2432(AutoCraftMode.X8.getList899(offset))) {
                    if (!this.rotate.getValue().booleanValue()) {
                        return new Record(direction2, blockPos, minecraftClient.player.getEyePos().offset(direction.getOpposite(), 1.0));
                    }
                    for (Vec3d vec3d : AutoCraftMode.X8.getList900(offset, direction2.getOpposite())) {
                        if (this.pusherHelper.getDirection2399(SearchHelper4_8.getFloatArray2484(vec3d)) == direction.getOpposite()) {
                            return new Record(direction2, blockPos, vec3d);
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean is1963(LivingEntity livingEntity, BlockPos blockPos, Direction direction) {
        if (minecraftClient.player.getEyePos().distanceTo(blockPos.toCenterPos()) > get1965()) {
            return true;
        }
        BlockPos offset = blockPos.offset(direction.getOpposite(), 2);
        int i = 0;
        Iterator<BlockPos> it = HoleSnapSearchHelper4.getList2010(livingEntity).iterator();
        while (it.hasNext()) {
            if (BaritoneHelper_3.stashFinderSearchHelper4.is1557(it.next())) {
                i++;
            }
        }
        return (i > 2 && minecraftClient.world.getBlockState(offset.down()).isReplaceable()) || !minecraftClient.world.getBlockState(offset).isReplaceable();
    }

    public Direction getDirection1964(BlockPos blockPos) {
        BlockState blockState = minecraftClient.world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof PistonBlock) {
            return blockState.get(FacingBlock.FACING);
        }
        return null;
    }

    public float get1965() {
        return this.range.getValue().floatValue();
    }
}
