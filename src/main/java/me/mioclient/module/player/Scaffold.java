package me.mioclient.module.player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import me.mioclient.AntiPhaseSearchHelper4;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FreecamHelper;
import me.mioclient.HoleSnapEvent;
import me.mioclient.HoleSnapHelper_2;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.HoleSnapSearchHelper4_6;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.ScaffoldData;
import me.mioclient.ScaffoldData_2;
import me.mioclient.ScaffoldMode;
import me.mioclient.ScaffoldMode_2;
import me.mioclient.ScaffoldMode_3;
import me.mioclient.SearchHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ClipAtLedgeEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.mixin.ducks.DuckAbstractBlock;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/player/Scaffold.class */
public class Scaffold extends Module {
    public static AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public Setting<ScaffoldMode_2> selection;
    public Setting<Set<Block>> blocks;
    public Setting<ScaffoldMode_3> swap;
    public Setting<Integer> delay;
    public Setting<Integer> offset;
    public Setting<Boolean> tower;
    public Setting<Boolean> safeWalk;
    public Setting<Boolean> airPlace;
    public Setting<Boolean> strictDirection;
    public Setting<Boolean> telly;
    public Setting<Boolean> rotate;
    public Setting<Boolean> stopSprinting;
    public Setting<Boolean> attack;
    public Setting<Boolean> render;
    public Setting<Float> lineWidth;
    public Setting<Color> fill;
    public Setting<Color> outline;
    public Setting<Boolean> fade;
    public Setting<Float> fadeTime;
    public final Stopwatch stopwatch;
    public final Stopwatch stopwatch2;
    public final Stopwatch stopwatch3;
    public final Stopwatch stopwatch4;
    public final Stopwatch stopwatch5;
    public ScaffoldData_2 scaffoldData_2;
    public float[] floatArr;
    public int num;
    public int num2;
    public double val;
    public float val2;

    public Scaffold() {
        super("Scaffold", "Speedbridges for you automatically.", Category.PLAYER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.stopwatch2 = new Stopwatch();
        this.stopwatch3 = new Stopwatch();
        this.stopwatch4 = new Stopwatch();
        this.stopwatch5 = new Stopwatch();
        this.num2 = -1;
        this.val2 = -1.0f;
        BaritoneHelper_3.antiPhaseSearchHelper4.register(new AntiPhaseSearchHelper4.Record(this, this.fill, this.outline, this.lineWidth, this.fadeTime, () -> {
            return false;
        }, this.fade, 450));
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        this.stopwatch.reset();
        this.stopwatch2.reset();
        this.scaffoldData_2 = null;
        this.floatArr = null;
        this.num = 0;
        this.num2 = -1;
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        if (is1469()) {
            return;
        }
        this.val = minecraftClient.player.getY();
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x012b  */
    @Listen
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onEvent2(HoleSnapEvent holeSnapEvent) {
        boolean z;
        boolean z2;
        if (this.val2 > 0.0f) {
            do511(this.val2);
            this.val2 = -1.0f;
        }
        if (minecraftClient.player.isRiding()) {
            return;
        }
        if (!this.stopwatch4.is419(50L) && minecraftClient.player.isOnGround() && antiCheat.movementSync.getValue().booleanValue()) {
            return;
        }
        if (!HoleSnapSearchHelper4_3.is2181() || minecraftClient.player.horizontalCollision) {
            this.val = minecraftClient.player.getY();
        }
        do503();
        this.scaffoldData_2 = getScaffoldData_2507();
        if (this.num2 != -1) {
            if (!minecraftClient.player.getInventory().getStack(this.num2).isEmpty()) {
                z = false;
                z2 = z;
                if (this.swap.getValue() == ScaffoldMode_3.NONE && get504(minecraftClient.player.getMainHandStack()) == 0) {
                    z2 = true;
                }
                if (!z2) {
                    this.scaffoldData_2 = null;
                    return;
                }
                do501(this.scaffoldData_2);
                if (!SearchHelper4_8.is1144() && this.scaffoldData_2 != null) {
                    do502(getFloatArray506(ScaffoldMode.PRE));
                }
                if (this.floatArr != null) {
                    BaritoneHelper_3.searchHelper4_8.do2477(this.floatArr, 5);
                }
                if (this.stopwatch2.is419(500L) || SearchHelper4_8.is724()) {
                    this.floatArr = null;
                }
                do500();
                if (this.scaffoldData_2 != null) {
                    if (this.stopwatch3.is419(this.delay.getValue().intValue())) {
                        if (!is509(this.scaffoldData_2.getBlockPos12(), this.num2)) {
                            return;
                        }
                        this.swap.getValue().do2742(this.num2, () -> {
                            Vec3d add;
                            if (this.render.getValue().booleanValue()) {
                                BaritoneHelper_3.antiPhaseSearchHelper4.do2132(this, this.scaffoldData_2.getBlockPos12());
                            }
                            ScaffoldData scaffoldData1460 = ScaffoldData.getScaffoldData1460(this.scaffoldData_2, true);
                            Vec3d vec3d843 = scaffoldData1460.getVec3d843();
                            if (scaffoldData1460.getDirection1462() == Direction.DOWN) {
                                add = vec3d843.withAxis(Direction.Axis.Y, this.scaffoldData_2.getBlockPos12().getY());
                            } else {
                                add = vec3d843.add(0.0d, scaffoldData1460.getVoxelShape1463().isEmpty() ? FreecamHelper.val2 : scaffoldData1460.getVoxelShape1463().getBoundingBox().maxY - 0.01, 0.0d);
                            }
                            boolean is2625 = BaritoneHelper_3.holeSnapSearchHelper4_4.is2625();
                            AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY, 0);
                            PhaseESPSearchHelper4_2.is3036(this.scaffoldData_2.getBlockPos12(), add, scaffoldData1460.getDirection1462(), this.airPlace.getValue().booleanValue(), Hand.MAIN_HAND);
                            if (!is2625) {
                                AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY, 0);
                            }
                            if (SearchHelper4_8.is1144()) {
                                do502(getFloatArray506(ScaffoldMode.POST.getScaffoldMode874(scaffoldData1460, add)));
                            }
                            this.stopwatch3.reset();
                            this.stopwatch2.reset();
                            this.stopwatch.reset();
                        });
                    }
                }
                if (this.stopwatch.is419(150L)) {
                    this.stopwatch.reset();
                    this.scaffoldData_2 = null;
                    return;
                }
                return;
            }
        }
        z = true;
        z2 = z;
        if (this.swap.getValue() == ScaffoldMode_3.NONE) {
            z2 = true;
        }
        if (!z2) {
        }
    }

    @Listen
    public void onClipAtLedge(ClipAtLedgeEvent clipAtLedgeEvent) {
        if (!this.safeWalk.getValue().booleanValue() || minecraftClient.player.isRiding()) {
            return;
        }
        clipAtLedgeEvent.do1162();
    }

    @Listen
    public void do28(MoveEvent moveEvent) {
        if (this.safeWalk.getValue().booleanValue() && SearchHelper4_8.is724() && minecraftClient.player.isOnGround() && !minecraftClient.player.isRiding()) {
            Box offset = minecraftClient.player.getBoundingBox().offset(moveEvent.get515(), 0.0d, moveEvent.get516());
            boolean isSneaking = minecraftClient.player.isSneaking();
            if (minecraftClient.world.isSpaceEmpty(offset.stretch(0.0d, -1.0, 0.0d))) {
                if (!isSneaking) {
                    minecraftClient.player.setSneaking(true);
                }
                moveEvent.do691(0.0d, 0.0d);
            } else {
                if (isSneaking) {
                    return;
                }
                minecraftClient.player.setSneaking(false);
            }
        }
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if (!sendImmediatelyEvent.is2403() && (sendImmediatelyEvent.getPacket904() instanceof ClickSlotC2SPacket)) {
            this.stopwatch4.reset();
        }
    }

    public void do500() {
        int floor = (int) Math.floor(minecraftClient.player.getY());
        if (this.num != floor || SearchHelper4_8.is724()) {
            this.num = floor;
            if (this.tower.getValue().booleanValue() && minecraftClient.player.input.jumping && minecraftClient.player.forwardSpeed == 0.0f && minecraftClient.player.sidewaysSpeed == 0.0f) {
                if (SearchHelper4_8.is724()) {
                    if (minecraftClient.player.isOnGround()) {
                        this.val2 = get510();
                        do511(0.36812999844551086f);
                        return;
                    }
                    return;
                }
                if (this.scaffoldData_2 != null) {
                    minecraftClient.player.setVelocity(0.0d, 0.41999998688698, 0.0d);
                }
            }
        }
    }

    public void do501(ScaffoldData_2 scaffoldData_2) {
        if (scaffoldData_2 != null && this.attack.getValue().booleanValue()) {
            if (this.stopwatch5.is419(BaritoneHelper_3.scaffoldHelper.get1115())) {
                Entity entity3046 = PhaseESPSearchHelper4_2.getEntity3046(scaffoldData_2.getBlockPos12(), BaritoneHelper_3.scaffoldHelper.get1116());
                if (entity3046 != null) {
                    do502(SearchHelper4_8.getFloatArray2483(entity3046));
                    this.stopwatch5.reset();
                }
            }
        }
    }

    public void do502(float[] fArr) {
        if (this.rotate.getValue().booleanValue()) {
            if (!SearchHelper4_8.is1144()) {
                this.floatArr = fArr;
            } else {
                BaritoneHelper_3.searchHelper4_8.do2481(fArr, 10);
                AutoSignSearchHelper4.do2563(minecraftClient.player.getX(), minecraftClient.player.getY(), minecraftClient.player.getZ(), fArr[0], fArr[1], minecraftClient.player.isOnGround());
            }
        }
    }

    public void do503() {
        if (get504(minecraftClient.player.getMainHandStack()) > 0) {
            this.num2 = minecraftClient.player.getInventory().selectedSlot;
            return;
        }
        int i = minecraftClient.player.getInventory().selectedSlot;
        int i2 = get504(minecraftClient.player.getInventory().getStack(i));
        for (int i3 = 0; i3 < 9; i3++) {
            int i4 = get504(minecraftClient.player.getInventory().getStack(i3));
            if (i4 > i2) {
                i = i3;
                i2 = i4;
            }
        }
        if (i2 == 0) {
            this.num2 = -1;
        } else {
            this.num2 = i;
        }
    }

    public int get504(ItemStack itemStack) {
        // 转型必须在 instanceof 检查之后——手持非方块物品(末影水晶/空气等)否则崩溃。
        net.minecraft.item.Item rawItem = itemStack.getItem();
        if (!(rawItem instanceof BlockItem)) {
            return 0;
        }
        BlockItem blockItem = (BlockItem) rawItem;
        if (this.selection.getValue().is1391(blockItem.getBlock(), this.blocks.getValue()) && ((DuckAbstractBlock)(Object) blockItem.getBlock()).isCollidable() && is505(blockItem)) {
            return SearchHelper.is230(blockItem.getBlock().getDefaultState().getCollisionShape(minecraftClient.world, BlockPos.ORIGIN)) ? 2 : 1;
        }
        return 0;
    }

    public boolean is505(BlockItem blockItem) {
        return !(blockItem.getBlock() instanceof FallingBlock) || HoleSnapSearchHelper4_6.get2786() <= 1;
    }

    public float[] getFloatArray506(ScaffoldMode scaffoldMode) {
        if (scaffoldMode != ScaffoldMode.PRE) {
            return SearchHelper4_8.getFloatArray2484(scaffoldMode.vec3d.add(0.0d, scaffoldMode.scaffoldData.getVoxelShape1463().isEmpty() ? FreecamHelper.val2 : scaffoldMode.scaffoldData.getVoxelShape1463().getBoundingBox().maxY - 0.01, 0.0d));
        }
        ScaffoldData scaffoldData1460 = ScaffoldData.getScaffoldData1460(this.scaffoldData_2, false);
        float[] floatArray2484 = SearchHelper4_8.getFloatArray2484(scaffoldData1460.getVec3d843().add(0.0d, scaffoldData1460.getVoxelShape1463().isEmpty() ? FreecamHelper.val2 : scaffoldData1460.getVoxelShape1463().getBoundingBox().maxY - 0.01, 0.0d));
        if (HoleSnapSearchHelper4_3.is2181()) {
            double[] doubleArray2508 = HoleSnapSearchHelper4_3.getDoubleArray2508(minecraftClient.player.getYaw(), minecraftClient.player.input, 1.0);
            float degrees = (float) (Math.toDegrees(Math.atan2(doubleArray2508[1], doubleArray2508[0])) - 270.0);
            if (MathHelper.angleBetween(degrees, floatArray2484[0]) < 60.0f) {
                floatArray2484[0] = degrees;
            }
        }
        this.stopwatch2.reset();
        return floatArray2484;
    }

    public ScaffoldData_2 getScaffoldData_2507() {
        BlockPos blockPos = null;
        Vec3d vec3d222 = SearchHelper.getVec3d222(minecraftClient.player.getBoundingBox());
        if (this.telly.getValue().booleanValue()) {
            vec3d222 = vec3d222.withAxis(Direction.Axis.Y, this.val);
        }
        boolean z = minecraftClient.player.hasVehicle() ? minecraftClient.player.getVehicle().groundCollision : minecraftClient.player.groundCollision;
        if (minecraftClient.player.hasVehicle()) {
            vec3d222 = SearchHelper.getVec3d222(minecraftClient.player.getVehicle().getBoundingBox());
        }
        double d = get508((float) vec3d222.getY());
        for (int i = 0; i < this.offset.getValue().intValue() + 1 && ((HoleSnapSearchHelper4_3.is2181() && minecraftClient.player.isOnGround()) || i <= 0); i++) {
            blockPos = BlockPos.ofFloored(vec3d222.withAxis(Direction.Axis.Y, d)).offset(HoleSnapSearchHelper4.getDirection2009(), i);
            if (!z && !this.telly.getValue().booleanValue() && !minecraftClient.world.getBlockState(blockPos).isReplaceable()) {
                blockPos = blockPos.up();
            }
            if (is509(blockPos, this.num2)) {
                break;
            }
            blockPos = null;
        }
        if (blockPos == null) {
            return null;
        }
        Direction direction3029 = PhaseESPSearchHelper4_2.getDirection3029(blockPos, this.strictDirection.getValue().booleanValue());
        if (direction3029 != null) {
            return new ScaffoldData_2(blockPos, direction3029);
        }
        for (int i2 = 1; i2 < 3; i2++) {
            for (Direction direction : Direction.values()) {
                if (direction != Direction.UP) {
                    Direction direction30292 = PhaseESPSearchHelper4_2.getDirection3029(blockPos.offset(direction, i2), this.strictDirection.getValue().booleanValue());
                    if (direction30292 != null) {
                        return new ScaffoldData_2(blockPos.offset(direction), direction30292);
                    }
                }
            }
        }
        return null;
    }

    public double get508(float f) {
        double y;
        double longBitsToDouble;
        Box boundingBox = minecraftClient.player.getBoundingBox();
        boolean isOnGround = minecraftClient.player.isOnGround();
        if (minecraftClient.player.hasVehicle()) {
            boundingBox = minecraftClient.player.getVehicle().getBoundingBox();
            isOnGround = minecraftClient.player.getVehicle().isOnGround();
        }
        double d = boundingBox.minX;
        double d2 = boundingBox.minZ;
        double d3 = boundingBox.maxX;
        double d4 = boundingBox.maxZ;
        ArrayList<Vec3d> arrayList = new ArrayList();
        arrayList.add(SearchHelper.getVec3d222(boundingBox).withAxis(Direction.Axis.Y, f));
        if (isOnGround) {
            arrayList.addAll(List.of(new Vec3d(d, f, d2), new Vec3d(d3, f, d2), new Vec3d(d, f, d4), new Vec3d(d3, f, d4)));
        }
        double longBitsToDouble2 = -999.0;
        for (Vec3d vec3d : arrayList) {
            BlockHitResult blockHitResult2784 = HoleSnapSearchHelper4_6.getBlockHitResult2784(new HoleSnapHelper_2.Inner(vec3d, vec3d.add(0.0d, -1.0, 0.0d)).getInner1603(minecraftClient.player.hasVehicle() ? minecraftClient.player.getVehicle() : minecraftClient.player).getHoleSnapHelper_21606());
            if (blockHitResult2784.getType() == HitResult.Type.MISS) {
                y = vec3d.getY();
                longBitsToDouble = 1.0;
            } else {
                y = blockHitResult2784.getPos().getY();
                longBitsToDouble = 0.009999999776482582;
            }
            longBitsToDouble2 = Math.max(longBitsToDouble2, y - longBitsToDouble);
        }
        return longBitsToDouble2;
    }

    public boolean is509(BlockPos blockPos, int i) {
        BlockItem item = (minecraftClient.player.getInventory().getStack(i == -1 ? minecraftClient.player.getInventory().selectedSlot : i).getItem()) instanceof BlockItem ? (BlockItem) (minecraftClient.player.getInventory().getStack(i == -1 ? minecraftClient.player.getInventory().selectedSlot : i).getItem()) : null;
        if (!(item instanceof BlockItem)) {
            return PhaseESPSearchHelper4_2.is3042(blockPos, true, this.attack.getValue().booleanValue());
        }
        return PhaseESPSearchHelper4_2.is3040(blockPos, item.getBlock(), true, this.attack.getValue().booleanValue());
    }

    public float get510() {
        return (float) minecraftClient.player.getAttributeInstance(EntityAttributes.GENERIC_JUMP_STRENGTH).getBaseValue();
    }

    public void do511(float f) {
        minecraftClient.player.getAttributeInstance(EntityAttributes.GENERIC_JUMP_STRENGTH).setBaseValue(f);
    }
}
