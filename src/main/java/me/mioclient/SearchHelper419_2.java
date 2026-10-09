package me.mioclient;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SearchHelper419_2.class */
public abstract class SearchHelper419_2 extends SearchHelper4_19 {
    public final StopwatchPresetHelper2 stopwatchPresetHelper2;
    public final String name;
    public java.lang.Runnable runnable;

    public SearchHelper419_2(int i, StopwatchPresetHelper2 stopwatchPresetHelper2, String str, java.lang.Runnable runnable) {
        super(stopwatchPresetHelper2.getPresetEnumSettingHelper1394(), i);
        this.stopwatchPresetHelper2 = stopwatchPresetHelper2;
        this.name = str;
        this.runnable = runnable;
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        if (this.stopwatchPresetHelper2.is1669()) {
            return;
        }
        super.do19(drawContext, matrixStack, d, d2);
        FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, getName(), this.presetEnumSettingHelper.getX() + get123(), (((this.presetEnumSettingHelper.getY() + this.num) + get124()) + get1742()) - get1396(), getUI1744().textColor.getValue());
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do20(double d, double d2, int i) {
        if (this.stopwatchPresetHelper2.is1669()) {
            return;
        }
        super.do20(d, d2, i);
        if (is92(d, d2) && i == 0) {
            this.runnable.run();
        }
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public boolean is92(double d, double d2) {
        return d > ((double) (((float) this.presetEnumSettingHelper.getX()) + get123())) && d < ((double) ((((float) this.presetEnumSettingHelper.getX()) + get123()) + FontsSearchHelper4.fontsSearchHelper4.get1316(getName()))) && d2 > ((double) (((float) (this.presetEnumSettingHelper.getY() + this.num)) + get124())) && d2 < ((double) (((((float) (this.presetEnumSettingHelper.getY() + this.num)) + get124()) + ((float) FontsSearchHelper4.fontsSearchHelper4.get93())) + (get1742() * 2.0f)));
    }

    public abstract float get123();

    public abstract float get124();

    public String getName() {
        return this.name;
    }
}
