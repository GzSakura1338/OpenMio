package me.mioclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PlayerEntitySearchHelper4.class */
public class PlayerEntitySearchHelper4 implements SearchHelper_4 {
    public static final float val = -0.07840000092983246f;
    public static final float val2 = HoleSnapSearchHelper4_3.val;
    public static final float val3 = val2 * 0.30000001192092896f;
    public static final float val4 = (val2 * 1.399999976158142f) + 0.20000000298023224f;
    public final List<Box> list = new ArrayList();
    public final PlayerEntity playerEntity;

    public PlayerEntitySearchHelper4(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void do466() {
        synchronized (this.list) {
            this.list.clear();
            double x = this.playerEntity.getX() - this.playerEntity.prevX;
            double y = this.playerEntity.getY() - this.playerEntity.prevY;
            double z = this.playerEntity.getZ() - this.playerEntity.prevZ;
            double hypot = Math.hypot(x, z);
            double clamp = MathHelper.clamp(hypot, this.playerEntity.isSneaking() ? val3 : val2, val4);
            if (hypot != 0.0d) {
                x = (x / hypot) * clamp;
                z = (z / hypot) * clamp;
            }
            if (SearchHelper_3.is647(this.playerEntity) && y > 0.0d) {
                y = -0.07840000092983246;
            }
            Box box234 = SearchHelper.getBox234(this.playerEntity);
            if (y > 0.0d) {
                y = get2709(y);
            }
            if (hypot < HoleSnapSearchHelper4_3.val2) {
                x = 0.0d;
                z = 0.0d;
            }
            if (hypot < HoleSnapSearchHelper4_3.val2 && Math.abs(y) < 0.1) {
                this.list.addAll(Collections.nCopies(20, box234));
                return;
            }
            for (int i = 0; i <= 20; i++) {
                y = !is2711(this.playerEntity, box234.offset(0.0d, -0.01, 0.0d)) ? get2709(y) : -0.07840000092983246;
                List entityCollisions = minecraftClient.world.getEntityCollisions(this.playerEntity, box234.stretch(x, y, z));
                ArrayList arrayList = new ArrayList();
                synchronized (BaritoneHelper_3.stashFinderSearchHelper4.getMap1552()) {
                    for (Map.Entry<BlockPos, BlockData> entry : BaritoneHelper_3.stashFinderSearchHelper4.getMap1552().entrySet()) {
                        arrayList.add(entry.getValue().getBlock2590().getDefaultState().getOutlineShape(minecraftClient.world, entry.getKey()));
                    }
                }
                Box stretched2711 = box234.stretch(x, y, z);
                arrayList.addAll(entityCollisions);
                net.minecraft.world.border.WorldBorder worldBorder = minecraftClient.world.getWorldBorder();
                if (worldBorder.canCollide(this.playerEntity, stretched2711)) {
                    arrayList.add(worldBorder.asVoxelShape());
                }
                for (VoxelShape voxelShape : minecraftClient.world.getBlockCollisions(this.playerEntity, stretched2711)) {
                    arrayList.add(voxelShape);
                }
                box234 = getBox2710(box234, arrayList, x, y, z);
                this.list.add(box234);
            }
        }
    }

    public static double get2709(double d) {
        return (d + 0.08) * 0.98;
    }

    public static Box getBox2710(Box box, List<VoxelShape> list, double d, double d2, double d3) {
        if (list.isEmpty()) {
            return box.offset(d, d2, d3);
        }
        if (d2 != 0.0d) {
            d2 = VoxelShapes.calculateMaxOffset(Direction.Axis.Y, box, list, d2);
            if (d2 != 0.0d) {
                box = box.offset(0.0d, d2, 0.0d);
            }
        }
        boolean z = Math.abs(d) < Math.abs(d3);
        if (z && d3 != 0.0d) {
            d3 = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, box, list, d3);
            if (d3 != 0.0d) {
                box = box.offset(0.0d, 0.0d, d3);
            }
        }
        if (d != 0.0d) {
            d = VoxelShapes.calculateMaxOffset(Direction.Axis.X, box, list, d);
            if (!z && d != 0.0d) {
                box = box.offset(d, 0.0d, 0.0d);
            }
        }
        if (!z && d3 != 0.0d) {
            d3 = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, box, list, d3);
        }
        return box.offset(d, d2, d3);
    }

    public boolean is2711(PlayerEntity playerEntity, Box box) {
        return minecraftClient.world.getBlockCollisions((Entity) playerEntity, box).iterator().hasNext();
    }

    public synchronized List<Box> getList2712() {
        return this.list;
    }
}
