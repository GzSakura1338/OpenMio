package me.mioclient.module;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.NumberSetting;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Size;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/Graph.class */
public class Graph extends me.mioclient.ModuleList {
    public Setting<Float> setting;
    public Setting<Integer> setting2;
    public Setting<Integer> setting3;
    public final List<Double> list;

    public Graph() {
        super("Graph", new String[0]);
        this.setting = add(new NumberSetting("Ceil", Float.valueOf(26.0f), Float.valueOf(10.0f), Float.valueOf(100.0f)));
        this.setting2 = add(new NumberSetting("Height", 30, 10, 40));
        this.setting3 = add(new NumberSetting("Width", 100, 50, 150));
        this.list = new ArrayList();
        do3019(new Size(this));
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.list.clear();
        for (int i = 0; i < 150; i++) {
            this.list.add(Double.valueOf(0.0d));
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        this.list.add(Double.valueOf(BaritoneHelper_3.feetPlaceSearchHelper4.get2635()));
        while (this.list.size() > this.setting3.getValue().intValue()) {
            this.list.remove(0);
        }
    }

    @Override // me.mioclient.ModuleList
    public void do364(DrawContext drawContext) {
        Tessellator tessellator = Tessellator.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        BufferBuilder begin = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        int i = 0;
        Iterator<Double> it = this.list.iterator();
        while (it.hasNext()) {
            float intBitsToFloat = 1.0f + ((float) ((this.setting2.getValue().intValue() - 2) * (1.0 - (Math.min(it.next().doubleValue(), this.setting.getValue().floatValue()) / this.setting.getValue().floatValue()))));
            begin.vertex(drawContext.getMatrices().peek().getPositionMatrix(), i, intBitsToFloat, 0.0f).color(MixinMessageIndicatorHelper_2.getColor816(getColor3018(intBitsToFloat + this.moduleListSearchHelper4.get124()), get2579(i)).hashCode());
            i++;
        }
        BufferRenderer.drawWithGlobalProgram(begin.end());
        RenderSystem.disableBlend();
    }

    public int get2579(int i) {
        if (i <= 10) {
            return (int) MathHelper.clamp((i / 10.0f) * 255.0f, 0.0f, 255.0f);
        }
        if (i >= this.setting3.getValue().intValue() - 10) {
            return (int) MathHelper.clamp((1.0f - (((i - this.setting3.getValue().intValue()) + 10) / 10.0f)) * 255.0f, 0.0f, 255.0f);
        }
        return 255;
    }

    @Override // me.mioclient.ModuleList
    public float[] getFloatArray365() {
        return new float[]{this.setting3.getValue().intValue(), this.setting2.getValue().intValue()};
    }
}
