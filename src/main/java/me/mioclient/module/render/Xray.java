package me.mioclient.module.render;

import java.util.Set;
import me.mioclient.PhaseESPHelper;
import me.mioclient.ZoomHelper_3;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.module.Module;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Xray.class */
public class Xray extends Module {
    public static Xray xray;
    public Setting<Boolean> bypass;
    public Setting<Set<Block>> whitelist;
    public double val;

    public Xray() {
        super("Xray", "Wallhack on ores.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        xray = this;
        this.whitelist.do2339(this::do2973);
        this.bypass.do2339(this::do2973);
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.val = ((Double) minecraftClient.options.getGamma().getValue()).doubleValue();
        ((ZoomHelper_3<Double>)(Object) minecraftClient.options.getGamma()).forceSetValue(Double.valueOf(1000.0));
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        ((ZoomHelper_3<Double>)(Object) minecraftClient.options.getGamma()).forceSetValue(Double.valueOf(this.val));
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        do2973();
    }

    public boolean is3071(BlockPos blockPos, Block block) {
        return (block == null || blockPos == null || is1469() || !this.whitelist.getValue().contains(block) || (this.bypass.getValue().booleanValue() && !is3072(blockPos))) ? false : true;
    }

    public boolean is3072(BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            if (!minecraftClient.world.getBlockState(blockPos.offset(direction)).isOpaque()) {
                return true;
            }
        }
        return false;
    }

    public void do2973() {
        if (minecraftClient.world != null) {
            minecraftClient.worldRenderer.reload();
        }
    }

    public static Xray getXray3073() {
        return xray;
    }
}
