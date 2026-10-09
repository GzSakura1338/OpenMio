package me.mioclient;

import me.mioclient.module.combat.HoleFill;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HoleFillHelper_2.class */
public class HoleFillHelper_2 implements HoleFillHelper {
    public final HoleFill holeFill;

    public HoleFillHelper_2(HoleFill holeFill) {
        this.holeFill = holeFill;
    }

    @Override // me.mioclient.HoleFillHelper
    public boolean is464(HoleSnapData holeSnapData) {
        if ((holeSnapData.getBox799().getLengthX() == 1.0 && holeSnapData.getBox799().getLengthZ() == 1.0) || this.holeFill.doubles.getValue().booleanValue()) {
            if (minecraftClient.player.getEyePos().distanceTo(holeSnapData.getBlockPos12().toCenterPos()) <= this.holeFill.range.getValue().floatValue() && Math.abs(minecraftClient.player.getEyePos().y - holeSnapData.getBlockPos12().toCenterPos().y) <= this.holeFill.verticalRange.getValue().floatValue()) {
                return true;
            }
        }
        return false;
    }
}
