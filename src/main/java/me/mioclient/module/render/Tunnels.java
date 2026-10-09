package me.mioclient.module.render;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import me.mioclient.MatrixStackEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper4_9;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Tunnels.class */
public class Tunnels extends Module {
    public Setting<Boolean> verticals;
    public Setting<Float> lineWidth;
    public Setting<Float> height;
    public Setting<Integer> minLength;
    public Setting<Color> fill;
    public Setting<Color> outline;
    public static final BlockPos.Mutable mutable = new BlockPos.Mutable();
    public final Stopwatch stopwatch;
    public final List<Inner> list;
    public final Queue<Inner> queue;
    public final ObjectSet<Inner> objectSet;
    public final SearchHelper4_9 searchHelper4_9;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/Tunnels$Inner.class */
    public static class Inner {
        public final boolean flag;
        public Box box;

        public Inner(BlockPos blockPos, boolean z) {
            this(new Box(blockPos), z);
        }

        public Inner(Box box, boolean z) {
            this.box = box;
            this.flag = z;
        }

        public double get696() {
            return Math.max(Math.max(this.box.getLengthX(), this.box.getLengthZ()), this.box.getLengthY());
        }

        public boolean is697(Inner inner) {
            if (this.flag != inner.flag) {
                return false;
            }
            Box box = inner.box;
            if (!this.flag) {
                if (box.minY != this.box.minY) {
                    return false;
                }
                return (this.box.minX > box.maxX ? 1 : (this.box.minX == box.maxX ? 0 : -1)) == 0 || (this.box.maxX > box.minX ? 1 : (this.box.maxX == box.minX ? 0 : -1)) == 0 ? this.box.minZ == box.minZ && this.box.maxZ == box.maxZ : ((this.box.minZ > box.maxZ ? 1 : (this.box.minZ == box.maxZ ? 0 : -1)) == 0 || (this.box.maxZ > box.minZ ? 1 : (this.box.maxZ == box.minZ ? 0 : -1)) == 0) && this.box.minX == box.minX && this.box.maxX == box.maxX;
            }
            if (box.minY == this.box.maxY || box.maxY == this.box.minY) {
                return ((this.box.minX > box.minX ? 1 : (this.box.minX == box.minX ? 0 : -1)) == 0 && (this.box.maxX > box.maxX ? 1 : (this.box.maxX == box.maxX ? 0 : -1)) == 0) && ((this.box.minZ > box.minZ ? 1 : (this.box.minZ == box.minZ ? 0 : -1)) == 0 && (this.box.maxZ > box.maxZ ? 1 : (this.box.maxZ == box.maxZ ? 0 : -1)) == 0);
            }
            return false;
        }

        public void do698(BlockPos blockPos) {
            this.box = this.box.union(new Box(blockPos));
        }

        public int hashCode() {
            return Objects.hash(this.box, Boolean.valueOf(this.flag));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Inner)) {
                return false;
            }
            Inner inner = (Inner) obj;
            return inner.box.equals(this.box) && this.flag == inner.flag;
        }
    }

    public Tunnels() {
        super("Tunnels", "Highlights dug-out tunnels.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.list = Collections.synchronizedList(new ArrayList());
        this.queue = new ArrayDeque();
        this.objectSet = new ObjectOpenHashSet();
        this.searchHelper4_9 = new SearchHelper4_9(1, (chunkPos, worldChunk) -> {
            do1979(chunkPos);
        }, (chunkPos2, worldChunk2) -> {
            this.list.removeIf(inner -> {
                return is1982(BlockPos.ofFloored(inner.box.getCenter()), chunkPos2);
            });
        }, null);
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.searchHelper4_9.do1640();
        if (is1469()) {
            return;
        }
        this.searchHelper4_9.do2689();
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        this.list.clear();
        this.searchHelper4_9.do2687();
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (is1469()) {
            return;
        }
        synchronized (this.list) {
            if ((!this.queue.isEmpty()) && this.stopwatch.is419(1000L)) {
                this.stopwatch.reset();
                executorService.submit(() -> {
                    do1978(new ArrayList(this.list));
                });
            }
            this.list.removeIf(inner -> {
                return Math.sqrt(minecraftClient.player.getEyePos().squaredDistanceTo(inner.box.getCenter())) > 256.0;
            });
        }
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (is1469()) {
            return;
        }
        synchronized (this.list) {
            for (Inner inner : this.list) {
                if (inner.get696() >= this.minLength.getValue().intValue()) {
                    float floatValue = this.height.getValue().floatValue();
                    if (inner.flag) {
                        floatValue = (float) inner.box.getLengthY();
                    }
                    Box withMaxY = inner.box.withMaxY(inner.box.minY + floatValue);
                    if (SearchHelper4_8.is2492(withMaxY)) {
                        PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), withMaxY, this.fill.getValue());
                        PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), withMaxY, this.outline.getValue(), this.lineWidth.getValue().floatValue());
                    }
                }
            }
        }
    }

    public void do1978(List<Inner> list) {
        Inner poll;
        while (!this.queue.isEmpty() && (poll = this.queue.poll()) != null) {
            boolean z = false;
            Iterator<Inner> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Inner next = it.next();
                if (next.is697(poll)) {
                    next.do698(BlockPos.ofFloored(poll.box.getCenter()));
                    z = true;
                    break;
                }
            }
            if (!z) {
                list.add(poll);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            for (int i2 = i; i2 < list.size(); i2++) {
                Inner inner = list.get(i);
                Inner inner2 = list.get(i2);
                if (inner.is697(inner2)) {
                    this.objectSet.add(inner2);
                }
            }
        }
        Iterator it2 = this.objectSet.iterator();
        while (it2.hasNext()) {
            list.remove((Inner) it2.next());
        }
        this.objectSet.clear();
        synchronized (this.list) {
            this.list.clear();
            this.list.addAll(list);
            list.clear();
        }
    }

    public void do1979(ChunkPos chunkPos) {
        boolean is1981;
        for (int i = 0; i < 16; i++) {
            for (int bottomY = minecraftClient.world.getBottomY(); bottomY < minecraftClient.world.getTopY(); bottomY++) {
                for (int i2 = 0; i2 < 16; i2++) {
                    mutable.set(chunkPos.getStartX() + i, bottomY, chunkPos.getStartZ() + i2);
                    if (is1983(mutable) && ((is1981 = is1981(mutable)) || is1980(mutable))) {
                        if (is1981) {
                            this.queue.add(new Inner((BlockPos) mutable, true));
                        } else {
                            Iterator it = Direction.Type.HORIZONTAL.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (is1980(mutable.offset((Direction) it.next()))) {
                                        this.queue.add(new Inner((BlockPos) mutable, false));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean is1980(BlockPos blockPos) {
        if (!is1983(blockPos.up()) || is1983(blockPos.down()) || is1983(blockPos.up().up())) {
            return false;
        }
        return (is1983(blockPos.north()) && is1983(blockPos.south()) && is1983(blockPos.up().north()) && is1983(blockPos.up().south())) ? (is1983(blockPos.east()) || is1983(blockPos.west()) || is1983(blockPos.up().east()) || is1983(blockPos.up().west())) ? false : true : is1983(blockPos.east()) && is1983(blockPos.west()) && is1983(blockPos.up().east()) && is1983(blockPos.up().west()) && !is1983(blockPos.north()) && !is1983(blockPos.south()) && !is1983(blockPos.up().north()) && !is1983(blockPos.up().south());
    }

    public boolean is1981(BlockPos blockPos) {
        if (!this.verticals.getValue().booleanValue()) {
            return false;
        }
        Iterator it = Direction.Type.HORIZONTAL.iterator();
        while (it.hasNext()) {
            BlockState blockState = minecraftClient.world.getBlockState(blockPos.offset((Direction) it.next()));
            if (!blockState.isSolid() || blockState.isOf(Blocks.BAMBOO_BLOCK) || blockState.isOf(Blocks.BASALT)) {
                return false;
            }
        }
        Iterator it2 = Direction.Type.VERTICAL.iterator();
        while (it2.hasNext()) {
            Direction direction = (Direction) it2.next();
            BlockPos offset = blockPos.offset(direction);
            if (!is1983(offset)) {
                return false;
            }
            if (!is1983(offset.offset(direction))) {
                return false;
            }
        }
        return true;
    }

    public boolean is1982(BlockPos blockPos, ChunkPos chunkPos) {
        return blockPos.getX() >= chunkPos.getStartX() && blockPos.getX() <= chunkPos.getEndX() && blockPos.getZ() >= chunkPos.getStartZ() && blockPos.getZ() <= chunkPos.getEndZ();
    }

    public boolean is1983(BlockPos blockPos) {
        return !minecraftClient.world.getBlockState(blockPos).isSolid();
    }
}
