package me.mioclient;

import me.mioclient.feature.Stopwatch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/MainhandHelper.class */
public abstract class MainhandHelper implements SearchHelper_4 {
    public final Stopwatch stopwatch = new Stopwatch();

    public abstract boolean is465();

    public void do466() {
        if (is468()) {
            this.stopwatch.reset();
        }
    }

    public boolean is467() {
        return is465() && !this.stopwatch.is419(750L);
    }

    public boolean is468() {
        synchronized (minecraftClient.world.getEntities()) {
            for (Entity entity : minecraftClient.world.getEntities()) {
                if (is470(entity)) {
                    if (is469(entity.getPos())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public boolean is469(Vec3d vec3d) {
        if (vec3d.squaredDistanceTo(minecraftClient.player.getPos()) > 144.0) {
            return false;
        }
        for (int i = 4; i >= 0; i--) {
            Box box1109 = BaritoneHelper_3.mainhandHelper_2.getBox1109(minecraftClient.player, i);
            if (ArmorSearchHelper4.get1900(vec3d, minecraftClient.player, box1109, 6.0, true, (BlockPos) null, (BlockPos) null) >= SearchHelper_3.get643()) {
                return true;
            }
        }
        return false;
    }

    public boolean is470(Entity entity) {
        return (entity instanceof EndCrystalEntity) && minecraftClient.player.squaredDistanceTo(entity) <= 144.0;
    }
}
