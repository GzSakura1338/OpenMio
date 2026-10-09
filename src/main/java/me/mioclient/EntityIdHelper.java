package me.mioclient;

import me.mioclient.feature.Stopwatch;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/EntityIdHelper.class */
public final class EntityIdHelper {
    public final Stopwatch stopwatch = new Stopwatch();
    public final int entityId;
    public final BlockPos blockPos;
    public float val;
    public float val2;
    public int num;

    public EntityIdHelper(int i, BlockPos blockPos) {
        this.entityId = i;
        this.blockPos = blockPos;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public Entity getEntity181() {
        return MinecraftClient.getInstance().world.getEntityById(this.entityId);
    }

    public BlockPos getBlockPos386() {
        return this.blockPos;
    }

    public float get2142() {
        return this.val;
    }

    public float get2814(float f) {
        return MathHelper.lerp(f, this.val2, this.val);
    }

    public void do2144(float f) {
        if (this.val == f) {
            return;
        }
        if (f == 0.0f) {
            this.val2 = 0.0f;
        } else {
            this.val2 = this.val;
        }
        do2816();
        this.val = MathHelper.clamp(f, 0.0f, 1.0f);
    }

    public int get2093() {
        return this.num;
    }

    public void do2815(int i) {
        this.num = i;
    }

    public void do2816() {
        this.stopwatch.reset();
    }

    public boolean is2817() {
        Entity entity181;
        if ((this.val == 1.0f && this.stopwatch.is419(2000L)) || (entity181 = getEntity181()) == null) {
            return true;
        }
        return entity181.getEyePos().squaredDistanceTo(this.blockPos.toCenterPos()) >= 64.0;
    }
}
