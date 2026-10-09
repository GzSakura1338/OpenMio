package me.mioclient.module.player;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FireworksHelper;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.HoleSnapEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/player/Nuker.class */
public class Nuker extends Module {
    public static final AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static final ItemSaver itemSaver = (ItemSaver) BaritoneHelper_3.baritoneHelper_4.getModule117(ItemSaver.class);
    public static final SpeedMine speedMine = (SpeedMine) BaritoneHelper_3.baritoneHelper_4.getModule117(SpeedMine.class);
    public Setting<NukerMode_2> mode;
    public Setting<NukerMode_3> shape;
    public Setting<NukerMode> sort;
    public Setting<Integer> delay;
    public Setting<Float> range;
    public Setting<Float> wallRange;
    public Setting<Boolean> strictDirection;
    public Setting<Boolean> rotate;
    public Setting<Boolean> creative;
    public Setting<Boolean> flatten;
    public Setting<Boolean> baritoneArea;
    public Setting<Set<Block>> whitelist;
    public final List<Record> list;
    public final Stopwatch stopwatch;
    public final Stopwatch stopwatch2;
    public BlockPos blockPos;
    public int num;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/player/Nuker$NukerMode.class */
    public enum NukerMode implements EnumSettingHelper {
        CLOSEST("Closest", blockPos -> {
            return Double.valueOf(SearchHelper_4.minecraftClient.player.getEyePos().squaredDistanceTo(blockPos.toCenterPos()));
        }),
        FURTHEST("Furthest", blockPos2 -> {
            return Double.valueOf(-SearchHelper_4.minecraftClient.player.getEyePos().squaredDistanceTo(blockPos2.toCenterPos()));
        }),
        TOP("Top", blockPos3 -> {
            return Double.valueOf(-blockPos3.getY());
        }),
        BOTTOM("Bottom", blockPos4 -> {
            return Double.valueOf(blockPos4.getY());
        });

        public final String name;
        public final Function<BlockPos, Double> function;

        NukerMode(String str, Function<BlockPos, Double> function) {
            this.name = str;
            this.function = function;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/player/Nuker$NukerMode_2.class */
    public static enum NukerMode_2 implements EnumSettingHelper {
        nukerMode_2("Shulkers") {
            @Override
            public boolean is621(BlockPos blockPos, Block block, Nuker nuker) {
                return block instanceof ShulkerBoxBlock;
            }
        },
        nukerMode_22("Bed") {
            @Override
            public boolean is621(BlockPos blockPos, Block block, Nuker nuker) {
                if (block instanceof BedBlock) {
                    if (SearchHelper_4.minecraftClient.world.getBlockState(blockPos).get(BedBlock.PART) == BedPart.HEAD) {
                        return true;
                    }
                }
                return false;
            }
        },
        nukerMode_23("WhiteList") {
            @Override
            public boolean is621(BlockPos blockPos, Block block, Nuker nuker) {
                return nuker.whitelist.getValue().contains(block);
            }
        },
        nukerMode_24("BlackList") {
            @Override
            public boolean is621(BlockPos blockPos, Block block, Nuker nuker) {
                return !nukerMode_23.is621(blockPos, block, nuker);
            }
        };

        public final String name;

        NukerMode_2(String str2) {
            this.name = str2;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public boolean is621(BlockPos blockPos, Block block, Nuker nuker) {
            return false;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/player/Nuker$NukerMode_3.class */
    public static enum NukerMode_3 implements EnumSettingHelper {
        nukerMode_3("Sphere") {
            @Override
            public List<BlockPos> getList437(Nuker nuker) {
                return SearchHelper4_7.getList2429(SearchHelper_4.minecraftClient.player.getPos(), nuker.range.getValue().floatValue(), true);
            }
        },
        nukerMode_32("Tunnel") {
            @Override
            public List<BlockPos> getList437(Nuker nuker) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i <= Math.ceil(nuker.range.getValue().floatValue()); i++) {
                    for (int i2 = 0; i2 < Math.ceil(SearchHelper_4.minecraftClient.player.getHeight()); i2++) {
                        arrayList.add(SearchHelper_4.minecraftClient.player.getBlockPos().up(i2).offset(SearchHelper_4.minecraftClient.player.getHorizontalFacing(), i));
                    }
                }
                return arrayList;
            }
        },
        nukerMode_33("Crosshair") {
            @Override
            public List<BlockPos> getList437(Nuker nuker) {
                BlockHitResult blockHitResult = (SearchHelper_4.minecraftClient.crosshairTarget) instanceof BlockHitResult ? (BlockHitResult) (SearchHelper_4.minecraftClient.crosshairTarget) : null;
                return blockHitResult instanceof BlockHitResult ? Collections.singletonList(blockHitResult.getBlockPos()) : Collections.emptyList();
            }
        };

        public final String name;

        NukerMode_3(String str2) {
            this.name = str2;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public List<BlockPos> getList437(Nuker nuker) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/player/Nuker$Record.class */
    public static final class Record {
        public final BlockPos blockPos;
        public final long num;

        public Record(BlockPos blockPos, long j) {
            this.blockPos = blockPos;
            this.num = j;
        }




        public BlockPos getBlockPos12() {
            return this.blockPos;
        }

        public long get798() {
            return this.num;
        }
    }

    public Nuker() {
        super("Nuker", "Breaks blocks nearby.", Category.PLAYER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.list = new ArrayList();
        this.stopwatch = new Stopwatch();
        this.stopwatch2 = new Stopwatch();
        this.blockPos = null;
        this.baritoneArea.do2344();
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return FontsSearchHelper4.getString1684(this.mode.getValue());
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        minecraftClient.options.attackKey.setPressed(false);
    }

    @Listen
    public void onEvent(HoleSnapEvent holeSnapEvent) {
        if (is1469()) {
            return;
        }
        this.list.removeIf(record -> {
            return System.currentTimeMillis() - record.num > 1000;
        });
        this.blockPos = null;
        this.num = 0;
        if (this.stopwatch2.is419(this.delay.getValue().intValue())) {
            if (speedMine.isToggled()) {
                if (!speedMine.is1058(this.delay.getValue().intValue())) {
                    return;
                }
            }
            boolean z = this.creative.getValue().booleanValue() || speedMine.extraBreak.getValue().booleanValue();
            List<BlockPos> list437 = this.shape.getValue().getList437(this);
            list437.sort(Comparator.comparing(blockPos -> {
                return this.sort.getValue().function.apply(blockPos);
            }));
            for (BlockPos blockPos2 : list437) {
                if (is2162()) {
                    return;
                }
                if (!this.flatten.getValue().booleanValue() || blockPos2.getY() >= minecraftClient.player.getY()) {
                    if (is2161(blockPos2)) {
                        do2160(blockPos2);
                        this.stopwatch2.reset();
                        if (!z && this.blockPos != null) {
                            return;
                        }
                        if (speedMine.isToggled() && speedMine.extraBreak.getValue().booleanValue() && this.num >= 2) {
                            return;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    public void do2160(BlockPos blockPos) {
        int i;
        if (SearchHelper4_7.is2435(blockPos)) {
            Direction direction = Direction.UP;
            if (this.strictDirection.getValue().booleanValue()) {
                List<Direction> list3031 = PhaseESPSearchHelper4_2.getList3031(blockPos);
                if (list3031.isEmpty()) {
                    return;
                } else {
                    direction = list3031.get(0);
                }
            }
            if (this.rotate.getValue().booleanValue()) {
                BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2485(blockPos.toCenterPos(), direction), 5);
            }
            this.num++;
            if (speedMine.isToggled()) {
                SpeedMine.flag = true;
                minecraftClient.interactionManager.attackBlock(blockPos, direction);
                SpeedMine.flag = false;
                this.blockPos = blockPos;
                this.stopwatch.reset();
                return;
            }
            int i2 = -1;
            double longBitsToDouble = -1.0;
            BlockState blockState = minecraftClient.world.getBlockState(blockPos);
            for (i = 0; i < 9; i++) {
                double miningSpeedMultiplier = minecraftClient.player.getInventory().getStack(i).getMiningSpeedMultiplier(blockState);
                if (itemSaver.isToggled()) {
                    ItemSaver itemSaver2 = itemSaver;
                    i = ItemSaver.is905(minecraftClient.player.getInventory().getStack(i)) ? 0 : i + 1;
                }
                if (miningSpeedMultiplier > longBitsToDouble) {
                    longBitsToDouble = miningSpeedMultiplier;
                    i2 = i;
                }
            }
            this.blockPos = blockPos;
            this.stopwatch.reset();
            if (!this.creative.getValue().booleanValue()) {
                FireworksHelper.do456(i2);
                minecraftClient.interactionManager.updateBlockBreakingProgress(blockPos, direction);
                minecraftClient.player.swingHand(Hand.MAIN_HAND);
                return;
            }
            if (antiCheat.is238()) {
                FireworksHelper.do456(i2);
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, this.blockPos, direction);
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, this.blockPos, direction);
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, this.blockPos, direction);
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.blockPos.add(0, 500, 0), direction);
            } else {
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, this.blockPos, direction);
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, this.blockPos, direction);
            }
            AutoSignSearchHelper4.do2559(Hand.MAIN_HAND);
            this.list.add(new Record(this.blockPos, System.currentTimeMillis()));
        }
    }

    public boolean is2161(BlockPos blockPos) {
        if (this.list.stream().anyMatch(record -> {
            return record.getBlockPos12().equals(blockPos);
        })) {
            return false;
        }
        double sqrt = Math.sqrt(blockPos.getSquaredDistance(minecraftClient.player.getEyePos()));
        if (!SearchHelper4_7.is2431((Vec3i) blockPos) && sqrt > this.wallRange.getValue().floatValue()) {
            return false;
        }
        if (this.mode.getValue() == NukerMode_2.nukerMode_2 && BaritoneHelper_3.stashFinderSearchHelper4.is1556(blockPos)) {
            return false;
        }
        if (this.baritoneArea.getValue().booleanValue() && BaritoneHelper_3.obstaclePasserHelper.is709() && !BaritoneHelper_3.obstaclePasserHelper.is708(blockPos)) {
            return false;
        }
        return this.mode.getValue().is621(blockPos, PhaseESPSearchHelper4_2.getBlock3044(blockPos), this);
    }

    public boolean is2162() {
        if (!speedMine.isToggled()) {
            return false;
        }
        BlockPos blockPos1053 = speedMine.getBlockPos1053();
        if (blockPos1053 == null) {
            blockPos1053 = speedMine.getBlockPos1054();
        }
        BlockPos blockPos = null;
        if (speedMine.getSpeedMineSearchHelper41059() != null) {
            blockPos = speedMine.getSpeedMineSearchHelper41059().getBlockPos386();
        }
        if (SearchHelper4_7.is2435(blockPos1053)) {
            return blockPos == null || !blockPos.equals(blockPos1053);
        }
        return false;
    }

    public BlockHitResult getBlockHitResult2163() {
        BlockHitResult blockHitResult = (minecraftClient.crosshairTarget) instanceof BlockHitResult ? (BlockHitResult) (minecraftClient.crosshairTarget) : null;
        if (!(blockHitResult instanceof BlockHitResult)) {
            return null;
        }
        BlockHitResult blockHitResult2 = blockHitResult;
        if (blockHitResult2.getType() == HitResult.Type.BLOCK) {
            return blockHitResult2;
        }
        return null;
    }
}
