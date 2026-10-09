package me.mioclient.module.combat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BooleanSetting;
import me.mioclient.Feature_14;
import me.mioclient.FireworksHelper;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.NumberSetting;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SpawnTimeHelper_2;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChunkDeltaUpdateEvent;
import me.mioclient.event.Listen;
import me.mioclient.mixin.ducks.DuckLivingEntity;
import me.mioclient.module.Delay;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/Range.class */
public class Range extends Delay {
    public Setting<Float> setting;
    public Setting<Boolean> setting2;
    public Setting<Boolean> setting3;
    public Setting<Boolean> setting4;
    public Setting<Boolean> setting5;
    public Setting<Boolean> setting6;
    public Setting<Boolean> setting7;
    public Setting<Boolean> setting8;
    public Setting<Boolean> setting9;
    public PlayerEntity playerEntity;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/Range$Record.class */
    private static final class Record {
        public final BlockPos blockPos;
        public final BlockState blockState;

        public Record(BlockPos blockPos, BlockState blockState) {
            this.blockPos = blockPos;
            this.blockState = blockState;
        }




        public BlockPos getBlockPos12() {
            return this.blockPos;
        }

        public BlockState getBlockState2780() {
            return this.blockState;
        }
    }

    public Range(String str, String str2, Category category) {
        super(str, str2, category, new String[0]);
        this.setting = add(new NumberSetting("Range", Float.valueOf(5.0f), Float.valueOf(1.0f), Float.valueOf(8.0f)).getNumberSetting3023("m"));
        this.setting2 = add(new BooleanSetting("Sequential", false));
        this.setting3 = add(new BooleanSetting("Targets", true).getSetting2337());
        this.setting4 = add(new BooleanSetting("Head", true).getSetting2342(this.setting3));
        this.setting5 = add(new BooleanSetting("Face", true).getSetting2342(this.setting3));
        this.setting6 = add(new BooleanSetting("Legs", false).getSetting2342(this.setting3));
        this.setting7 = add(new BooleanSetting("AntiStep", false));
        this.setting8 = add(new BooleanSetting("IgnoreNakeds", false));
        this.setting9 = add(new BooleanSetting("OnlyObby", false), this.setting5);
    }

    public Range() {
        super("AutoTrap", "Traps your enemies with obby.", Category.COMBAT, new String[0]);
        this.setting = add(new NumberSetting("Range", Float.valueOf(5.0f), Float.valueOf(1.0f), Float.valueOf(8.0f)).getNumberSetting3023("m"));
        this.setting2 = add(new BooleanSetting("Sequential", false));
        this.setting3 = add(new BooleanSetting("Targets", true).getSetting2337());
        this.setting4 = add(new BooleanSetting("Head", true).getSetting2342(this.setting3));
        this.setting5 = add(new BooleanSetting("Face", true).getSetting2342(this.setting3));
        this.setting6 = add(new BooleanSetting("Legs", false).getSetting2342(this.setting3));
        this.setting7 = add(new BooleanSetting("AntiStep", false));
        this.setting8 = add(new BooleanSetting("IgnoreNakeds", false));
        this.setting9 = add(new BooleanSetting("OnlyObby", false), this.setting5);
        PhaseESPHelper.do1351(this);
        do1334(true);
        do1335();
        this.setting9.do2334(false);
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        if (this.playerEntity != null) {
            return this.playerEntity.getGameProfile().getName();
        }
        return null;
    }

    @Override // me.mioclient.module.Delay
    public List<BlockPos> getList876() {
        this.playerEntity = getPlayerEntity886();
        if (this.playerEntity == null || Math.hypot(this.playerEntity.getX() - this.playerEntity.prevX, this.playerEntity.getZ() - this.playerEntity.prevZ) * 20.0 * 3.6 > 20.0) {
            return Collections.emptyList();
        }
        if (Math.abs(this.playerEntity.prevY - this.playerEntity.getY()) > 0.6) {
            return Collections.emptyList();
        }
        Position vec3d885 = getVec3d885(this.playerEntity);
        List<BlockPos> list877 = getList877(new ArrayList(), BlockPos.ofFloored(vec3d885), 0);
        list877.removeIf(this::is880);
        list877.sort(Comparator.comparing(blockPos -> {
            return Integer.valueOf(-blockPos.getY());
        }));
        if (this.setting5.getValue().booleanValue()) {
            list877.sort(Comparator.comparing(blockPos2 -> {
                return Double.valueOf(-minecraftClient.player.getPos().distanceTo(blockPos2.toCenterPos()));
            }));
        }
        return list877;
    }

    @Override // me.mioclient.module.Delay
    public int get499() {
        return this.setting9.getValue().booleanValue() ? FireworksHelper.get447(Items.OBSIDIAN) : super.get499();
    }

    @Listen
    public void onChunkDeltaUpdate(ChunkDeltaUpdateEvent chunkDeltaUpdateEvent) {
        if (this.setting2.getValue().booleanValue()) {
            do1323();
        }
    }

    public List<BlockPos> getList877(List<Integer> list, BlockPos blockPos, int i) {
        BlockPos blockPos878;
        ArrayList arrayList = new ArrayList();
        list.add(Integer.valueOf(blockPos.hashCode()));
        for (Vec3d vec3d : getList882(blockPos, i, this.playerEntity.getHeight())) {
            BlockPos add = blockPos.add((int) vec3d.x, (int) vec3d.y, (int) vec3d.z);
            if (minecraftClient.world.getBlockState(add).isReplaceable()) {
                if (!minecraftClient.world.getEntitiesByClass(PlayerEntity.class, new Box(add), playerEntity -> {
                    return true;
                }).isEmpty()) {
                    if (!list.contains(Integer.valueOf(add.hashCode())) && vec3d.y == i) {
                        arrayList.addAll(getList877(list, add, i));
                    }
                }
                if (getDirection881(add, this.setting4.getValue().booleanValue(), arrayList) == null && !this.setting3.getValue().booleanValue() && (blockPos878 = getBlockPos878(add, arrayList)) != null) {
                    arrayList.add(blockPos878);
                }
                arrayList.add(add);
            }
        }
        return arrayList;
    }

    public BlockPos getBlockPos878(BlockPos blockPos, List<BlockPos> list) {
        Stream<Direction> stream = Arrays.stream(Direction.values());
        Objects.requireNonNull(blockPos);
        return (BlockPos) stream.map(blockPos::offset).sorted(Comparator.comparingDouble(this::get879)).filter(blockPos2 -> {
            if (getDirection881(blockPos2, this.setting4.getValue().booleanValue(), list) != null) {
                if (PhaseESPSearchHelper4_2.is3042(blockPos2, true, this.setting7.getValue().booleanValue())) {
                    return true;
                }
            }
            return false;
        }).findFirst().orElse(null);
    }

    public double get879(BlockPos blockPos) {
        if (!this.setting5.getValue().booleanValue()) {
            return blockPos.getY();
        }
        return -minecraftClient.player.getPos().squaredDistanceTo(blockPos.toCenterPos());
    }

    public boolean is880(BlockPos blockPos) {
        if (this.playerEntity == null) {
            return true;
        }
        Box box = new Box(blockPos.up());
        for (Entity entity : minecraftClient.world.getEntities()) {
            if ((entity instanceof SpawnTimeHelper_2) && !((SpawnTimeHelper_2) entity).isMioAttacked() && entity.getBoundingBox().intersects(box) && BlockPos.ofFloored(entity.getPos()).equals(blockPos.up())) {
                return true;
            }
        }
        if (minecraftClient.player.getEyePos().distanceTo(blockPos.toCenterPos()) > this.setting.getValue().floatValue()) {
            return true;
        }
        if (!this.setting6.getValue().booleanValue() && blockPos.getY() == this.playerEntity.getBlockPos().getY()) {
            if (!minecraftClient.world.getBlockState(blockPos.up()).isReplaceable()) {
                return true;
            }
        }
        return false;
    }

    public Direction getDirection881(BlockPos blockPos, boolean z, List<BlockPos> list) {
        for (Direction direction : Direction.values()) {
            BlockPos offset = blockPos.offset(direction);
            if (!minecraftClient.world.getBlockState(offset).isReplaceable() || list.contains(offset)) {
                if (z) {
                    if (!PhaseESPSearchHelper4_2.getList3031(offset).contains(direction.getOpposite())) {
                    }
                }
                return direction;
            }
        }
        return null;
    }

    public List<Vec3d> getList882(BlockPos blockPos, int i, float f) {
        ArrayList arrayList = new ArrayList();
        if (is887()) {
            arrayList.add(new Vec3d(0.0d, i + f + 1.0f, 0.0d));
            Iterator<Vec3d> it = getList883(i, f).iterator();
            if (it.hasNext()) {
                Vec3d next = it.next();
                BlockPos add = BlockPos.ofFloored((Position) next).add((Vec3i) blockPos);
                if (minecraftClient.world.getBlockState(add).isReplaceable()) {
                    if (PhaseESPSearchHelper4_2.getDirection3030(add, this.setting4.getValue().booleanValue(), this.setting5.getValue().booleanValue()) == null) {
                        if (next.getY() > i) {
                            arrayList.add(new Vec3d(-1.0, i + 1, 0.0d));
                        } else {
                            arrayList.add(new Vec3d(-1.0, i, 0.0d));
                        }
                    }
                }
            }
        } else if (this.setting4.getValue().booleanValue()) {
            arrayList.add(new Vec3d(0.0d, i + f + 1.0f, 0.0d));
        }
        for (Vec3d vec3d : getList883(i, f)) {
            if (vec3d.getY() <= f && (this.setting5.getValue().booleanValue() || vec3d.getY() <= i)) {
                if (this.setting6.getValue().booleanValue() || vec3d.getY() != i) {
                    arrayList.add(vec3d);
                }
            }
        }
        if (this.setting7.getValue().booleanValue()) {
            arrayList.add(new Vec3d(0.0d, i + f + 2.0f, 0.0d));
        }
        return arrayList;
    }

    public List<Vec3d> getList883(int i, float f) {
        return Arrays.asList(new Vec3d(0.0d, i + f + 1.0f, 0.0d), new Vec3d(1.0, i, 0.0d), new Vec3d(0.0d, i, 1.0), new Vec3d(0.0d, i, -1.0), new Vec3d(-1.0, i, 0.0d), new Vec3d(1.0, i + 1, 0.0d), new Vec3d(0.0d, i + 1, 1.0), new Vec3d(0.0d, i + 1, -1.0), new Vec3d(-1.0, i + 1, 0.0d));
    }

    public boolean is884(PlayerEntity playerEntity) {
        if (playerEntity.isDead() || !playerEntity.isAlive() || playerEntity == minecraftClient.player || BaritoneHelper_3.searchHelper4_14.is520(playerEntity)) {
            return false;
        }
        return (!this.setting8.getValue().booleanValue() || HoleSnapSearchHelper4.is2013((LivingEntity) playerEntity)) && playerEntity.distanceTo(minecraftClient.player) <= this.setting.getValue().floatValue() && playerEntity.isOnGround();
    }

    public Vec3d getVec3d885(PlayerEntity playerEntity) {
        Vec3d pos = playerEntity.getPos();
        if (!(this.playerEntity instanceof Feature_14.OtherClientPlayerEntity) && !(this.playerEntity instanceof ClientPlayerEntity)) {
            DuckLivingEntity duckLivingEntity = (DuckLivingEntity)(this.playerEntity);
            pos = new Vec3d(duckLivingEntity.mio$getServerX(), duckLivingEntity.mio$getServerY(), duckLivingEntity.mio$getServerZ());
        }
        return pos;
    }

    public PlayerEntity getPlayerEntity886() {
        return (PlayerEntity) minecraftClient.world.getPlayers().stream().filter((v1) -> {
            return is884(v1);
        }).min(Comparator.comparing(abstractClientPlayerEntity -> {
            return Float.valueOf(MathHelper.angleBetween(minecraftClient.player.getYaw(), SearchHelper4_8.getFloatArray2483((Entity) abstractClientPlayerEntity)[0]));
        })).orElse(null);
    }

    public boolean is887() {
        return this.setting4.getValue().booleanValue() && !this.setting5.getValue().booleanValue();
    }

    @Override // me.mioclient.module.Delay
    public int get888() {
        return 150;
    }
}
