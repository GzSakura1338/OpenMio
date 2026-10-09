package me.mioclient;

import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.file.Path;
import me.mioclient.feature.Category;
import me.mioclient.feature.List;
import me.mioclient.feature.Preset;
import net.minecraft.client.gui.DrawContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PresetFontsSearchHelper42.class */
public class PresetFontsSearchHelper42 extends FontsSearchHelper4_2 {
    public final List list = new List(this);
    public final Preset preset = new Preset(this);
    public PresetHelperMode presetHelperMode = PresetHelperMode.MODULES;
    public final ZoomHelper zoomHelper = new ZoomHelper();

    public PresetFontsSearchHelper42() {
        this.arrayList.add(this.list);
        this.preset.setX(this.list.getX() + this.list.get1635() + 3);
        this.arrayList.add(this.preset);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void do940(DrawContext drawContext, int i, int i2, float f) {
        this.zoomHelper.do170(!minecraftClient.isWindowFocused(), 250L);
        float f2 = this.zoomHelper.get172();
        if (f2 == 0.0f) {
            return;
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.75f * f2);
        drawContext.drawCenteredTextWithShadow(minecraftClient.textRenderer, "Drop preset files here", drawContext.getScaledWindowWidth() / 2, drawContext.getScaledWindowHeight() / 2, -1);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void filesDragged(java.util.List<Path> list) {
        Category.is2716(this, list);
    }

    public List getList2318() {
        return this.list;
    }

    public Preset getPreset2319() {
        return this.preset;
    }

    public PresetHelperMode getPresetHelperMode2320() {
        return this.presetHelperMode;
    }

    public void do2321(PresetHelperMode presetHelperMode) {
        this.presetHelperMode = presetHelperMode;
    }
}
