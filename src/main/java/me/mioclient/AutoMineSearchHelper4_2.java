package me.mioclient;

import me.mioclient.module.combat.AutoMine;
import me.mioclient.module.player.SpeedMine;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AutoMineSearchHelper4_2.class */
public abstract class AutoMineSearchHelper4_2 implements SearchHelper_4, AutoMineHelper_2 {
    public static SpeedMine speedmine = (SpeedMine) BaritoneHelper_3.baritoneHelper_4.getModule117(SpeedMine.class);
    public final AutoMine autoMine;

    public AutoMineSearchHelper4_2(AutoMine autoMine) {
        this.autoMine = autoMine;
    }

    public boolean is1212(BlockPos blockPos) {
        if (blockPos.equals(this.autoMine.speedMineHelper.getBlockPos1226())) {
            return false;
        }
        if (minecraftClient.player.getEyePos().distanceTo(blockPos.toCenterPos()) > this.autoMine.get1965() || !speedmine.is1056(blockPos)) {
            return false;
        }
        if (this.autoMine.strictDirection.getValue().booleanValue() && PhaseESPSearchHelper4_2.getList3031(blockPos).isEmpty()) {
            return false;
        }
        if (!this.autoMine.raytrace.getValue().booleanValue() || SearchHelper4_7.is2432(AutoCraftMode.X8.getList899(blockPos))) {
            return SearchHelper4_7.is2435(blockPos);
        }
        return false;
    }

    public boolean is2890(BlockPos blockPos) {
        if (blockPos == null) {
            return false;
        }
        return blockPos.equals(speedmine.getBlockPos1053()) || blockPos.equals(speedmine.getBlockPos1054());
    }

    public boolean is2891(Entity entity) {
        return minecraftClient.world.isSpaceEmpty(entity.getBoundingBox().stretch(0.0d, -0.1, 0.0d));
    }

    public boolean is465() {
        return false;
    }
}
