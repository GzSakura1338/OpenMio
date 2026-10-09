package me.mioclient;

import java.util.Comparator;
import java.util.List;
import me.mioclient.module.combat.AutoMine;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AutoMineSearchHelper42_6.class */
public final class AutoMineSearchHelper42_6 extends AutoMineSearchHelper4_2 {
    public AutoMineSearchHelper42_6(AutoMine autoMine) {
        super(autoMine);
    }

    @Override // me.mioclient.AutoMineHelper_2
    public void do722(AutoMineHelper autoMineHelper) {
        boolean is2754 = this.autoMine.is2754();
        PlayerEntity playerEntity886 = this.autoMine.autoMineSearchHelper4.getPlayerEntity886();
        if (playerEntity886 == null) {
            return;
        }
        List list = BlockPos.stream(playerEntity886.getBoundingBox().expand(-SearchHelper.val, 0.0d, -SearchHelper.val).withMaxY(playerEntity886.getY())).map((v0) -> {
            return v0.toImmutable();
        }).filter(blockPos -> {
            return this.is1212(blockPos);
        }).filter(blockPos2 -> {
            Block block = minecraftClient.world.getBlockState(blockPos2).getBlock();
            return (block.getBlastResistance() < 600.0f || block == Blocks.COBWEB || block == Blocks.ANVIL) ? false : true;
        }).sorted(Comparator.comparing(blockPos3 -> {
            return is2890(blockPos3) ? Double.valueOf(-1.0) : Double.valueOf(playerEntity886.squaredDistanceTo(blockPos3.toCenterPos()));
        })).toList();
        if (list.isEmpty()) {
            return;
        }
        int i = 700;
        if (is2754 && list.size() == 1) {
            i = 500;
        }
        autoMineHelper.do2901(i, autoMineHelper2 -> {
            if (!is2754) {
                autoMineHelper2.do667((BlockPos) list.getFirst());
            } else if (list.size() == 1) {
                autoMineHelper2.do2899((BlockPos) list.getFirst());
            } else {
                autoMineHelper2.do667((BlockPos) list.getFirst());
                autoMineHelper2.do2899((BlockPos) list.get(1));
            }
        });
    }

    @Override // me.mioclient.AutoMineSearchHelper4_2, me.mioclient.AutoMineHelper_2
    public boolean is465() {
        return this.autoMine.burrow.getValue().booleanValue();
    }
}
