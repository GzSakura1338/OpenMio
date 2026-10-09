package me.mioclient.module.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.SearchHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.ChunkDeltaUpdateEvent;
import me.mioclient.event.Listen;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Delay;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/FeetPlace.class */
public class FeetPlace extends Delay {
    public Setting<Boolean> sequential;
    public Setting<Boolean> center;
    public Setting<Boolean> flatten;
    public Setting<Boolean> onlyObby;
    public final Stopwatch stopwatch;
    public boolean flag;

    public FeetPlace() {
        super("FeetPlace", "Surrounds your feet with obby.", Category.COMBAT, "surround");
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        do1334(true);
    }

    @Override // me.mioclient.module.Delay, me.mioclient.module.Module
    public void onEnable() {
        if (!is1469()) {
            do1405();
        }
        super.onEnable();
        this.stopwatch.reset();
    }

    @Override // me.mioclient.module.Delay
    public List<BlockPos> getList876() {
        List<BlockPos> list877 = getList877(new ArrayList(), getBlockPos1333(), 0);
        list877.sort(getComparator1402());
        return list877;
    }

    @Override // me.mioclient.module.Delay
    public int get888() {
        return 999;
    }

    @Override // me.mioclient.module.Delay
    public int get499() {
        return this.onlyObby.getValue().booleanValue() ? FireworksHelper.get447(Items.OBSIDIAN) : super.get499();
    }

    @Override // me.mioclient.module.Delay
    public void do1323() {
        if (minecraftClient.player.hasVehicle()) {
            return;
        }
        this.flag = !BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2616().is419(100L);
        if (this.flag) {
            this.val = minecraftClient.player.getY();
        }
        try {
            super.do1323();
        } catch (Throwable th) {
        }
    }

    @Override // me.mioclient.module.Delay
    public void do1324(List<BlockPos> list) {
    }

    @Override // me.mioclient.module.Delay
    public boolean is1330() {
        if (this.flag) {
            return false;
        }
        return super.is1330();
    }

    @Listen
    public void onChunkDeltaUpdate(ChunkDeltaUpdateEvent chunkDeltaUpdateEvent) {
        if (this.sequential.getValue().booleanValue()) {
            do1323();
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        PlayerPositionLookS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof PlayerPositionLookS2CPacket ? (PlayerPositionLookS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof PlayerPositionLookS2CPacket) {
            PlayerPositionLookS2CPacket playerPositionLookS2CPacket = packet904;
            if (minecraftClient.player.getPos().squaredDistanceTo(new Vec3d(playerPositionLookS2CPacket.getX(), playerPositionLookS2CPacket.getY(), playerPositionLookS2CPacket.getZ())) > 9.0) {
                this.val = playerPositionLookS2CPacket.getY();
                BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2616().reset();
            }
        }
    }

    public List<BlockPos> getList877(List<Integer> list, BlockPos blockPos, int i) {
        int i2 = get499();
        BlockState defaultState = Blocks.OBSIDIAN.getDefaultState();
        if (i2 != -1) {
            BlockItem item = (minecraftClient.player.getInventory().getStack(i2).getItem()) instanceof BlockItem ? (BlockItem) (minecraftClient.player.getInventory().getStack(i2).getItem()) : null;
            if (item instanceof BlockItem) {
                defaultState = item.getBlock().getDefaultState();
            }
        }
        ArrayList arrayList = new ArrayList();
        list.add(Integer.valueOf(blockPos.hashCode()));
        Iterator<Vec3d> it = getList1401(i).iterator();
        while (it.hasNext()) {
            Position position = (Vec3d) it.next();
            BlockPos add = blockPos.add(BlockPos.ofFloored(position));
            if (((Vec3d) position).y == -1.0) {
                if (!BaritoneHelper_3.holeSnapSearchHelper4_5.is2723(add) || minecraftClient.player.isOnGround()) {
                    if (this.flatten.getValue().booleanValue() && !minecraftClient.player.isInSwimmingPose() && !add.equals(BaritoneHelper_3.stashFinderSearchHelper4.getBlockPos1551())) {
                    }
                }
            }
            VoxelShape collisionShape = defaultState.getCollisionShape(minecraftClient.world, BlockPos.ORIGIN);
            Box box = collisionShape.isEmpty() ? new Box(add) : SearchHelper.getBox226(collisionShape.getBoundingBox(), add);
            do1328(add);
            if (!minecraftClient.world.getBlockState(add).isReplaceable()) {
                if (!SearchHelper.is235(minecraftClient.player.getBoundingBox(), new Box(add))) {
                }
            }
            if (!minecraftClient.world.getEntitiesByClass(LivingEntity.class, box, livingEntity -> {
                return livingEntity == minecraftClient.player || SearchHelper.is235(box, livingEntity.getBoundingBox());
            }).isEmpty()) {
                if (!list.contains(Integer.valueOf(add.hashCode())) && ((Vec3d) position).y == i) {
                    arrayList.addAll(getList877(list, add, i));
                }
            }
            if (minecraftClient.world.getBlockState(add).isReplaceable()) {
                if (PhaseESPSearchHelper4_2.getDirection3029(add, this.setting4.getValue().booleanValue()) == null) {
                    BlockPos blockPos3032 = PhaseESPSearchHelper4_2.getBlockPos3032(add, 2, true, this.setting4.getValue().booleanValue());
                    if (blockPos3032 != null) {
                        arrayList.add(blockPos3032);
                    }
                }
                arrayList.add(add);
            }
        }
        return arrayList;
    }

    public List<Vec3d> getList1401(int i) {
        return Arrays.asList(new Vec3d(-1.0, i, 0.0d), new Vec3d(1.0, i, 0.0d), new Vec3d(0.0d, i, 1.0), new Vec3d(0.0d, i, -1.0), new Vec3d(0.0d, i - 1, 0.0d));
    }

    public Comparator<BlockPos> getComparator1402() {
        return BaritoneHelper_3.feetPlaceSearchHelper4.get2636() <= 10.0 ? Comparator.comparing(blockPos -> {
            return Float.valueOf(get1403(minecraftClient.player.getYaw(), blockPos));
        }) : Comparator.comparing(blockPos2 -> {
            return Float.valueOf(get1404(HoleSnapSearchHelper4_3.get2517(), blockPos2));
        });
    }

    public float get1403(float f, BlockPos blockPos) {
        if (blockPos.getY() != HoleSnapSearchHelper4.getBlockPos1333().getY() || !is1406(minecraftClient.player)) {
            return get1404(f, blockPos);
        }
        return (float) HoleSnapSearchHelper4.getBlockPos1333().getSquaredDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public float get1404(float f, BlockPos blockPos) {
        return MathHelper.angleBetween(f, SearchHelper4_8.getFloatArray2484(blockPos.toCenterPos())[0]) + (blockPos.getY() * 0.0010000000474974513f);
    }

    public void do1405() {
        if (is1469() || !this.center.getValue().booleanValue() || !minecraftClient.player.isOnGround() || BaritoneHelper_3.holeSnapSearchHelper4_5.is2728() || HoleSnapSearchHelper4.getList2010(minecraftClient.player).size() <= 4) {
            return;
        }
        Vec3d vec3d = new Vec3d(Math.floor(minecraftClient.player.getX()) + 0.5, minecraftClient.player.getY(), Math.floor(minecraftClient.player.getZ()) + 0.5);
        minecraftClient.player.setVelocity((vec3d.x - minecraftClient.player.getX()) * 0.5, minecraftClient.player.getVelocity().getY(), (vec3d.z - minecraftClient.player.getZ()) * 0.5);
        minecraftClient.player.setPosition(vec3d);
    }

    public boolean is1406(LivingEntity livingEntity) {
        Iterator<BlockPos> it = HoleSnapSearchHelper4.getSet2011(livingEntity).iterator();
        while (it.hasNext()) {
            VoxelShape collisionShape = minecraftClient.world.getBlockState(it.next()).getCollisionShape(minecraftClient.world, BlockPos.ORIGIN);
            if (!collisionShape.isEmpty() && !SearchHelper.is229(collisionShape.getBoundingBox())) {
                return true;
            }
        }
        return false;
    }
}
