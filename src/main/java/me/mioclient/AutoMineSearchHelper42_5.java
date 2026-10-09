package me.mioclient;

import me.mioclient.module.combat.AutoMine;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AutoMineSearchHelper42_5.class */
public final class AutoMineSearchHelper42_5 extends AutoMineSearchHelper4_2 {
    public AutoMineSearchHelper42_5(AutoMine autoMine) {
        super(autoMine);
    }

    @Override // me.mioclient.AutoMineHelper_2
    public void do722(AutoMineHelper autoMineHelper) {
        BlockPos blockPos2156 = getBlockPos2156();
        if ((this.autoMine.autoMineSearchHelper4.getPlayerEntity886() == null && blockPos2156 == null) || minecraftClient.player.isFallFlying()) {
            return;
        }
        Box boundingBox = minecraftClient.player.getBoundingBox();
        if (this.autoMine.onlyCrawl.getValue().booleanValue() && !is2157() && blockPos2156 == null) {
            return;
        }
        int i = (is2157() || blockPos2156 != null) ? 1500 : 100;
        BlockPos blockPos2155 = getBlockPos2155(boundingBox);
        if (blockPos2155 == null) {
            return;
        }
        autoMineHelper.do2901(i, autoMineHelper2 -> {
            autoMineHelper2.do667(blockPos2155);
        });
    }

    public BlockPos getBlockPos2155(Box box) {
        BlockPos down = BlockPos.ofFloored(minecraftClient.player.getPos()).down();
        BlockPos blockPos2156 = getBlockPos2156();
        if (is1212(down) && ((is2890(down) || is2157()) && is2158())) {
            return down;
        }
        BlockPos up = BlockPos.ofFloored(minecraftClient.player.getPos().add(0.0d, box.getLengthY() - SearchHelper.val, 0.0d)).up();
        if (blockPos2156 != null) {
            up = blockPos2156;
        }
        if (is1212(up)) {
            return up;
        }
        return null;
    }

    public BlockPos getBlockPos2156() {
        if (!this.autoMine.face.getValue().booleanValue()) {
            return null;
        }
        BlockPos ofFloored = BlockPos.ofFloored(minecraftClient.player.getEyePos());
        BlockState blockState = minecraftClient.world.getBlockState(ofFloored);
        if (blockState.isAir() || blockState.getBlock().getBlastResistance() < 600.0f || is2157() || !is1212(ofFloored)) {
            return null;
        }
        return ofFloored;
    }

    public boolean is2157() {
        return minecraftClient.player.getBoundingBox().getLengthY() <= 1.0;
    }

    public boolean is2158() {
        if (this.autoMine.downPriority.getValue().booleanValue()) {
            return true;
        }
        if (minecraftClient.world.isAir(HoleSnapSearchHelper4.getBlockPos1333().down(2))) {
            return false;
        }
        for (BlockPos blockPos : HoleSnapSearchHelper4.getList2010(minecraftClient.player)) {
            BlockState blockState = minecraftClient.world.getBlockState(blockPos.down());
            if (blockState.isAir() || blockState.getBlock().getBlastResistance() < 600.0f) {
                return false;
            }
        }
        return true;
    }

    @Override // me.mioclient.AutoMineSearchHelper4_2, me.mioclient.AutoMineHelper_2
    public boolean is465() {
        return this.autoMine.self.getValue().booleanValue();
    }
}
