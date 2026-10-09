package me.mioclient;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AntiPhaseData.class */
public final class AntiPhaseData {
    public final BlockPos blockPos;
    public final Direction direction;
    public final Box box;

    public AntiPhaseData(BlockPos blockPos, Direction direction, Box box) {
        this.blockPos = blockPos;
        this.direction = direction;
        this.box = box;
    }

    public Vec3d getVec3d2748() {
        Vec3d centerPos = this.blockPos.toCenterPos();
        return centerPos.offset(this.direction, 0.5);
    }




    public BlockPos getBlockPos12() {
        return this.blockPos;
    }

    public Direction getDirection842() {
        return this.direction;
    }

    public Box getBox799() {
        return this.box;
    }
}
