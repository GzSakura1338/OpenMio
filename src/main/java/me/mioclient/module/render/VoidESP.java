package me.mioclient.module.render;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/VoidESP.class */
public class VoidESP extends Module {
    public Setting<Float> lineWidth;
    public Setting<Float> height;
    public Setting<Integer> radius;
    public Setting<Boolean> fade;
    public Setting<Float> fadeRadius;
    public Setting<Boolean> colors;
    public Setting<Color> fill;
    public Setting<Color> line;
    public final List<BlockPos> list;

    public VoidESP() {
        super("VoidESP", "Highlights void blocks.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.list = new CopyOnWriteArrayList();
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        this.list.clear();
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        this.list.removeIf(blockPos -> {
            return PhaseESPSearchHelper4_2.getBlock3044(blockPos) == Blocks.BEDROCK;
        });
        for (BlockPos blockPos2 : SearchHelper4_7.getList2429(minecraftClient.gameRenderer.getCamera().getPos(), this.radius.getValue().intValue(), false)) {
            if (blockPos2.getY() == minecraftClient.world.getBottomY() && PhaseESPSearchHelper4_2.getBlock3044(blockPos2) != Blocks.BEDROCK && !this.list.contains(blockPos2)) {
                this.list.add(blockPos2);
            }
        }
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return String.valueOf(this.list.size());
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        for (BlockPos blockPos : this.list) {
            if (SearchHelper4_8.is2492(new Box(blockPos))) {
                Vec3d pos = minecraftClient.gameRenderer.getCamera().getPos();
                if (blockPos.isWithinDistance((Position) pos, this.radius.getValue().intValue())) {
                    Color value = this.fill.getValue();
                    Color value2 = this.line.getValue();
                    double distanceTo = pos.distanceTo(blockPos.toCenterPos());
                    if (this.fade.getValue().booleanValue() && distanceTo >= this.fadeRadius.getValue().floatValue()) {
                        float intBitsToFloat = 1.0f - ((float) MathHelper.clamp((distanceTo - this.fadeRadius.getValue().floatValue()) / (this.radius.getValue().intValue() - this.fadeRadius.getValue().floatValue()), 0.0d, 1.0));
                        value = MixinMessageIndicatorHelper_2.getColor816(value, (int) (intBitsToFloat * value.getAlpha()));
                        value2 = MixinMessageIndicatorHelper_2.getColor816(value2, (int) (intBitsToFloat * value2.getAlpha()));
                    }
                    PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), new Box(blockPos).withMaxY(blockPos.getY() + this.height.getValue().floatValue()), value);
                    PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), new Box(blockPos).withMaxY(blockPos.getY() + this.height.getValue().floatValue()), value2, this.lineWidth.getValue().floatValue());
                } else {
                    this.list.remove(blockPos);
                }
            }
        }
    }
}
