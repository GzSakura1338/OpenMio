package me.mioclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.DisconnectEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.mixin.ducks.DuckAbstractBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkLoadDistanceS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShapes;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/StashFinderSearchHelper4.class */
public class StashFinderSearchHelper4 implements SearchHelper_4 {
    public ItemStack itemStack;
    public ItemStack itemStack2;
    public int num;
    public final List<BlockEntity> list = Collections.synchronizedList(new ArrayList());
    public final Map<BlockPos, BlockData> map = Collections.synchronizedMap(new HashMap());
    public final Set<BlockPos> set = Collections.synchronizedSet(new HashSet());
    public final Set<BlockPos> set2 = Collections.synchronizedSet(new HashSet());
    public final Set<BlockStateSearchHelper4> set3 = Collections.synchronizedSet(new HashSet());

    @Deprecated
    public BlockPos unconfirmedBreak = null;
    public final Stopwatch stopwatch = new Stopwatch();

    public StashFinderSearchHelper4() {
        baritoneHelper.do1796(this);
    }

    @Listen
    public void onDisconnect(DisconnectEvent disconnectEvent) {
        this.map.clear();
        this.set.clear();
        this.list.clear();
        this.set3.clear();
    }

    @Listen(get219= Helper_7.num)
    public void do32(TickPostEvent tickPostEvent) {
        do1554();
    }

    @Listen(get219= Helper_7.num5)
    public void do27(TickEvent tickEvent) {
        this.set2.clear();
        if (this.stopwatch.is419(150L)) {
            this.unconfirmedBreak = null;
        }
        this.set.removeIf(blockPos -> {
            return !blockPos.isWithinDistance(minecraftClient.player.getBlockPos(), 128.0);
        });
        synchronized (this.map) {
            ArrayList arrayList = new ArrayList();
            for (Map.Entry<BlockPos, BlockData> entry : this.map.entrySet()) {
                if (System.currentTimeMillis() > entry.getValue().get798() + 250) {
                    arrayList.add(entry.getKey());
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.map.remove((BlockPos) it.next());
            }
        }
        synchronized (getList1555()) {
            this.list.clear();
            this.list.addAll(SearchHelper4_7.getList2427());
            this.list.removeIf((v0) -> {
                return Objects.isNull(v0);
            });
            this.list.sort(Comparator.comparing(blockEntity -> {
                return Double.valueOf(minecraftClient.player.getPos().squaredDistanceTo(blockEntity.getPos().toCenterPos()));
            }));
        }
        synchronized (this.set3) {
            for (BlockStateSearchHelper4 blockStateSearchHelper4 : this.set3) {
                if (blockStateSearchHelper4.is1775()) {
                    minecraftClient.world.setBlockState(blockStateSearchHelper4.getBlockPos12(), blockStateSearchHelper4.getBlockState2780());
                }
            }
        }
        this.set3.removeIf((v0) -> {
            return v0.is1775();
        });
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        PlayerInteractBlockC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof PlayerInteractBlockC2SPacket ? (PlayerInteractBlockC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if (packet904 instanceof PlayerInteractBlockC2SPacket) {
            PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket = packet904;
            BlockItem item = (getItemStack1553(playerInteractBlockC2SPacket.getHand()).getItem()) instanceof BlockItem ? (BlockItem) (getItemStack1553(playerInteractBlockC2SPacket.getHand()).getItem()) : null;
            if (item instanceof BlockItem) {
                BlockItem blockItem = item;
                BlockHitResult blockHitResult = playerInteractBlockC2SPacket.getBlockHitResult();
                BlockPos offset = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                this.map.put(offset, BlockData.getBlockData2589(blockItem.getBlock()));
                this.set2.add(offset);
                do1554();
            }
        }
    }

    @Listen
    public void onEvent3(SpeedMineEvent speedMineEvent) {
        do1554();
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        BlockUpdateS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof BlockUpdateS2CPacket ? (BlockUpdateS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof BlockUpdateS2CPacket) {
            BlockUpdateS2CPacket blockUpdateS2CPacket = packet904;
            this.set3.removeIf(blockStateSearchHelper4 -> {
                return blockStateSearchHelper4.getBlockPos12().equals(blockUpdateS2CPacket.getPos());
            });
            if (blockUpdateS2CPacket.getState().isAir()) {
                this.set.remove(blockUpdateS2CPacket.getPos());
                this.map.remove(blockUpdateS2CPacket.getPos());
            } else {
                if (this.map.remove(blockUpdateS2CPacket.getPos()) != null) {
                    this.set.add(blockUpdateS2CPacket.getPos());
                }
            }
        }
        ChunkDeltaUpdateS2CPacket packet9042 = (channelRead0Event.getPacket904()) instanceof ChunkDeltaUpdateS2CPacket ? (ChunkDeltaUpdateS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet9042 instanceof ChunkDeltaUpdateS2CPacket) {
            packet9042.visitUpdates((blockPos, blockState) -> {
                this.set3.removeIf(blockStateSearchHelper42 -> {
                    return blockStateSearchHelper42.getBlockPos12().equals(blockPos);
                });
            });
        }
        GameJoinS2CPacket packet9043 = (channelRead0Event.getPacket904()) instanceof GameJoinS2CPacket ? (GameJoinS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet9043 instanceof GameJoinS2CPacket) {
            this.num = packet9043.viewDistance();
        }
        ChunkLoadDistanceS2CPacket packet9044 = (channelRead0Event.getPacket904()) instanceof ChunkLoadDistanceS2CPacket ? (ChunkLoadDistanceS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet9044 instanceof ChunkLoadDistanceS2CPacket) {
            this.num = packet9044.getDistance();
        }
    }

    @Listen
    public void onEvent(VoxelShapeEvent voxelShapeEvent) {
        if (voxelShapeEvent.getBlockPos386() == null || minecraftClient.player == null) {
            return;
        }
        if (this.map.containsKey(voxelShapeEvent.getBlockPos386())) {
            BlockData blockData = this.map.get(voxelShapeEvent.getBlockPos386());
            if (blockData == null || blockData.getBlock2590() == null) {
                return;
            }
            Box box = new Box(voxelShapeEvent.getBlockPos386());
            if (minecraftClient.player.getBoundingBox().intersects(box) || !((DuckAbstractBlock) blockData.getBlock2590()).isCollidable()) {
                return;
            }
            if (!minecraftClient.world.getEntitiesByClass(Entity.class, box, entity -> {
                return ((entity instanceof ExperienceBottleEntity) || (entity instanceof ItemEntity) || (entity instanceof ExperienceOrbEntity) || (entity instanceof ArrowEntity) || (entity instanceof EnderPearlEntity)) ? false : true;
            }).isEmpty()) {
                return;
            }
            voxelShapeEvent.do666(blockData.getBlock2590().getDefaultState().getOutlineShape(minecraftClient.world, voxelShapeEvent.getBlockPos386()));
        }
        if (SearchHelper4_8.is724() && BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(300L) && voxelShapeEvent.getBlockPos386().equals(this.unconfirmedBreak)) {
            voxelShapeEvent.do666(VoxelShapes.empty());
        }
    }

    public void do1549(BlockPos blockPos) {
        this.set3.add(BlockStateSearchHelper4.getBlockStateSearchHelper42779(blockPos));
        minecraftClient.interactionManager.breakBlock(blockPos);
    }

    public void do1550(BlockPos blockPos) {
        this.unconfirmedBreak = blockPos;
        this.stopwatch.reset();
    }

    public BlockPos getBlockPos1551() {
        return this.unconfirmedBreak;
    }

    public Map<BlockPos, BlockData> getMap1552() {
        return this.map;
    }

    public ItemStack getItemStack1553(Hand hand) {
        return hand == Hand.MAIN_HAND ? this.itemStack : this.itemStack2;
    }

    public void do1554() {
        this.itemStack = minecraftClient.player.getMainHandStack().copy();
        this.itemStack2 = minecraftClient.player.getOffHandStack().copy();
    }

    public List<BlockEntity> getList1555() {
        return this.list;
    }

    public boolean is1556(BlockPos blockPos) {
        return this.set.contains(blockPos) || this.map.containsKey(blockPos);
    }

    public boolean is1557(BlockPos blockPos) {
        return !minecraftClient.world.getBlockState(blockPos).isReplaceable() || this.set2.contains(blockPos);
    }

    public int get1558() {
        return this.num;
    }
}
