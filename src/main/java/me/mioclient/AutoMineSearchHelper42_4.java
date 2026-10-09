package me.mioclient;

import me.mioclient.module.combat.AutoMine;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.util.math.BlockPos;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AutoMineSearchHelper42_4.class */
public final class AutoMineSearchHelper42_4 extends AutoMineSearchHelper4_2 {
    public AutoMineSearchHelper42_4(AutoMine autoMine) {
        super(autoMine);
    }

    @Override // me.mioclient.AutoMineHelper_2
    public void do722(AutoMineHelper autoMineHelper) {
        BlockPos blockPos = null;
        for (BlockEntity blockEntity : BaritoneHelper_3.stashFinderSearchHelper4.getList1555()) {
            if (minecraftClient.player.getEyePos().distanceTo(blockEntity.getPos().toCenterPos()) <= 2.2 && (blockEntity instanceof EnderChestBlockEntity)) {
                if (is1212(blockEntity.getPos())) {
                    if (blockPos != null) {
                        if (is2890(blockEntity.getPos())) {
                        }
                    }
                    blockPos = blockEntity.getPos();
                }
            }
        }
        BlockPos blockPos2 = blockPos;
        if (blockPos2 == null) {
            return;
        }
        autoMineHelper.do2901(5, autoMineHelper2 -> {
            autoMineHelper2.do667(blockPos2);
        });
    }

    @Override // me.mioclient.AutoMineSearchHelper4_2, me.mioclient.AutoMineHelper_2
    public boolean is465() {
        return this.autoMine.enderChests.getValue().booleanValue();
    }
}
