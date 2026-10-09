package me.mioclient;

import java.util.Iterator;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AutoCrystalSearchHelper4.class */
public class AutoCrystalSearchHelper4 implements SearchHelper_4 {
    public boolean flag;
    public boolean flag3;
    public boolean flag5;
    public boolean flag6;
    public boolean flag7;
    public boolean flag8;
    public int num;
    public boolean flag2 = true;
    public boolean flag4 = true;
    public float val = 6.0f;

    public boolean is2070(BlockPos blockPos) {
        if (!this.flag7 && !SearchHelper4_7.is2445(blockPos)) {
            return false;
        }
        BlockPos add = blockPos.add(0, 1, 0);
        Block block = minecraftClient.world.getBlockState(add).getBlock();
        boolean z = (block instanceof AirBlock) || add.equals(BaritoneHelper_3.stashFinderSearchHelper4.getBlockPos1551());
        if (this.flag4) {
            if (!z && block != Blocks.FIRE) {
                return false;
            }
        } else if (!z) {
            return false;
        }
        if (this.flag) {
            if (!(minecraftClient.world.getBlockState(blockPos.add(0, 2, 0)).getBlock() instanceof AirBlock)) {
                return false;
            }
        }
        Box withMaxY = new Box(add).withMaxY(add.getY() + (this.flag5 ? 1 : 2));
        int i = 0;
        Iterator it = minecraftClient.world.getEntities().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            NameTagsHelper nameTagsHelper = (NameTagsHelper)((Entity) it.next());
            if (nameTagsHelper != null && !((Entity) nameTagsHelper).isRemoved() && ((Entity) nameTagsHelper).isAlive()) {
                Box boundingBox = ((Entity) nameTagsHelper).getBoundingBox();
                if (!(nameTagsHelper instanceof PlayerEntity) || nameTagsHelper == minecraftClient.player) {
                    if ((nameTagsHelper instanceof EndCrystalEntity) && !this.flag3) {
                        if (minecraftClient.player.getEyePos().squaredDistanceTo(((Entity) nameTagsHelper).getPos()) <= this.val) {
                            continue;
                        }
                    }
                    if ((nameTagsHelper instanceof ItemEntity) && this.flag8) {
                    }
                } else {
                    boundingBox = SearchHelper.getBox234((LivingEntity) nameTagsHelper);
                    if (this.flag6 && !nameTagsHelper.mio$isNextToWall()) {
                        boundingBox = boundingBox.expand(0.0001, 0.0d, 0.0001);
                    }
                    for (int i2 = 1; i2 <= this.num; i2++) {
                        if (BaritoneHelper_3.mainhandHelper_2.getBox1109((PlayerEntity) nameTagsHelper, i2).withMaxY(boundingBox.maxY).withMinY(boundingBox.minY).intersects(withMaxY)) {
                            i++;
                        }
                    }
                }
                if (boundingBox.intersects(withMaxY)) {
                    i++;
                    break;
                }
            }
        }
        return !this.flag2 || i == 0;
    }

    public AutoCrystalSearchHelper4 getAutoCrystalSearchHelper42071(boolean z) {
        this.flag = z;
        return this;
    }

    public AutoCrystalSearchHelper4 getAutoCrystalSearchHelper42072(boolean z) {
        this.flag2 = z;
        return this;
    }

    public AutoCrystalSearchHelper4 getAutoCrystalSearchHelper42073(boolean z) {
        this.flag3 = z;
        return this;
    }

    public AutoCrystalSearchHelper4 getAutoCrystalSearchHelper42074(boolean z) {
        this.flag4 = z;
        return this;
    }

    public AutoCrystalSearchHelper4 getAutoCrystalSearchHelper42075(boolean z) {
        this.flag5 = z;
        return this;
    }

    public AutoCrystalSearchHelper4 getAutoCrystalSearchHelper42076(boolean z) {
        this.flag6 = z;
        return this;
    }

    public AutoCrystalSearchHelper4 getAutoCrystalSearchHelper42077(boolean z) {
        this.flag7 = z;
        return this;
    }

    public AutoCrystalSearchHelper4 getAutoCrystalSearchHelper42078(boolean z) {
        this.flag8 = z;
        return this;
    }

    public AutoCrystalSearchHelper4 getAutoCrystalSearchHelper42079(int i) {
        this.num = i;
        return this;
    }

    public AutoCrystalSearchHelper4 getAutoCrystalSearchHelper42080(float f) {
        this.val = f * f;
        return this;
    }
}
