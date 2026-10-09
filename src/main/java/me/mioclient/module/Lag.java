package me.mioclient.module;

import java.awt.Color;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.ColorSetting;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.api.Setting;
import me.mioclient.feature.Progress;
import me.mioclient.feature.Size;
import net.minecraft.client.gui.DrawContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/Lag.class */
public class Lag extends me.mioclient.ModuleList {
    public Setting<Color> setting;
    public final Progress progress;

    public Lag() {
        super("Lag'O'Meter", "lag", "lagometer");
        this.setting = add(new ColorSetting("Color", Color.gray));
        this.progress = new Progress(1.0f);
        do3019(new Size(this));
    }

    @Override // me.mioclient.ModuleList
    public void do364(DrawContext drawContext) {
        String string773 = getString773();
        this.progress.do2139(string773 != null);
        if (string773 == null) {
            return;
        }
        FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, string773, 0.0f, (1.0f - this.progress.get172()) * (-FontsSearchHelper4.fontsSearchHelper4.get93()), this.setting.getValue());
    }

    @Override // me.mioclient.ModuleList
    public float[] getFloatArray365() {
        String string773 = getString773();
        return string773 == null ? new float[]{0.0f, 0.0f} : new float[]{FontsSearchHelper4.fontsSearchHelper4.get1316(string773), FontsSearchHelper4.fontsSearchHelper4.get93() + 1};
    }

    public String getString773() {
        if ((Math.max(System.currentTimeMillis() - BaritoneHelper_3.holeSnapSearchHelper4_4.get2618(), 0L) <= 1000 || minecraftClient.isInSingleplayer()) && !is3017()) {
            return null;
        }
        return "The server is not responding for %.1fs".formatted(Float.valueOf(((float) (System.currentTimeMillis() - BaritoneHelper_3.holeSnapSearchHelper4_4.get2618())) / 1000.0f));
    }
}
