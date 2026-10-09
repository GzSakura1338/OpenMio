package me.mioclient.module.combat;

import java.util.ArrayList;
import java.util.List;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BlockerPredicateMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.module.Delay;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/Blocker.class */
public class Blocker extends Delay {
    public Setting<BlockerPredicateMode> offsets;
    public Setting<Boolean> corners;
    public Setting<Float> blockDamage;

    public Blocker() {
        super("Blocker", "Blocks certain offsets to prevent crystal damage.", Category.COMBAT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.setting9.do2334(false);
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return this.offsets.getValue().getName();
    }

    @Override // me.mioclient.module.Delay
    public List<BlockPos> getList876() {
        ArrayList<BlockPos> arrayList = new ArrayList();
        for (Vec3i vec3i : this.offsets.getValue().getVec3iArray2842()) {
            BlockPos add = BlockPos.ofFloored(minecraftClient.player.getPos()).add(vec3i);
            if (BaritoneHelper_3.breakHighlightSearchHelper4.get1516(add) >= this.blockDamage.getValue().floatValue()) {
                arrayList.add(add);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (BlockPos blockPos : arrayList) {
            Vec3i[] vec3iArray2842 = BlockerPredicateMode.SURROUND.getVec3iArray2842();
            int length = vec3iArray2842.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Vec3i vec3i2 = vec3iArray2842[i];
                if (blockPos.getY() != ((int) minecraftClient.player.getPos().getY())) {
                    break;
                }
                Vec3i ofFloored = BlockPos.ofFloored(minecraftClient.player.getPos());
                if (!this.corners.getValue().booleanValue()) {
                    if (blockPos.subtract(ofFloored).equals(vec3i2)) {
                        arrayList2.add(blockPos.add(vec3i2));
                        break;
                    }
                } else if (!blockPos.add(vec3i2).equals(ofFloored)) {
                    arrayList2.add(blockPos.add(vec3i2));
                }
                i++;
            }
            if (blockPos.getY() == minecraftClient.player.getY() + 3.0) {
                arrayList2.add(blockPos.down());
            } else {
                arrayList2.add(blockPos.up());
            }
        }
        return arrayList2;
    }
}
