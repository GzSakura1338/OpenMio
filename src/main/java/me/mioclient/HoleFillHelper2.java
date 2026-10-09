package me.mioclient;

import me.mioclient.module.combat.HoleFill;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HoleFillHelper2.class */
public class HoleFillHelper2 extends HoleFillHelper_2 {
    public HoleFillHelper2(HoleFill holeFill) {
        super(holeFill);
    }

    @Override // me.mioclient.HoleFillHelper_2, me.mioclient.HoleFillHelper
    public boolean is464(HoleSnapData holeSnapData) {
        for (LivingEntity livingEntity : minecraftClient.world.getPlayers()) {
            if (!this.holeFill.ignoreNakeds.getValue().booleanValue() || HoleSnapSearchHelper4.is2013(livingEntity)) {
                Box box1109 = this.holeFill.extrapolation.getValue().booleanValue() ? BaritoneHelper_3.mainhandHelper_2.getBox1109((PlayerEntity) livingEntity, SearchHelper.get231((PlayerEntity) livingEntity, this.holeFill.ticks)) : SearchHelper.getBox234(livingEntity);
                boolean z = !HoleSnapSearchHelper4_3.is2510((PlayerEntity) livingEntity) || HoleSnapSearchHelper4_3.is2515((PlayerEntity) livingEntity, holeSnapData.getBlockPos12().toCenterPos());
                if (MixinLivingEntityHelper_2.get2583(holeSnapData.getBlockPos12().toCenterPos(), SearchHelper.getVec3d222(box1109)) <= this.holeFill.smartRange.getValue().floatValue() && ((AbstractClientPlayerEntity) livingEntity).getY() > holeSnapData.getBlockPos12().getY() && box1109.minY - holeSnapData.getBlockPos12().getY() <= 4.0 && !((AbstractClientPlayerEntity) livingEntity).isDead() && minecraftClient.player != livingEntity) {
                    if (BaritoneHelper_3.searchHelper4_14.is519(((AbstractClientPlayerEntity) livingEntity).getGameProfile().getName())) {
                        continue;
                    } else if (!BaritoneHelper_3.holeSnapSearchHelper4_5.is2723(BlockPos.ofFloored(((AbstractClientPlayerEntity) livingEntity).getPos())) && z) {
                        if (this.holeFill.selfSafety.getValue().booleanValue()) {
                            if (!BaritoneHelper_3.holeSnapSearchHelper4_5.is2723(BlockPos.ofFloored(minecraftClient.player.getPos()))) {
                                if (minecraftClient.player.getPos().distanceTo(holeSnapData.getBlockPos12().toCenterPos()) < 2.0) {
                                }
                            }
                        }
                        return super.is464(holeSnapData);
                    }
                }
            }
        }
        return false;
    }
}
