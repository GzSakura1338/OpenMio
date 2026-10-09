package me.mioclient;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SearchHelper419.class */
public class SearchHelper419 extends SearchHelper4_19 {
    public final PresetSettingSearchHelper419 presetSettingSearchHelper419;
    public final String string;
    public final int index;

    public SearchHelper419(PresetSettingSearchHelper419 presetSettingSearchHelper419, String str, int i, int i2) {
        super(presetSettingSearchHelper419.getPresetEnumSettingHelper1394(), i);
        this.string = str;
        this.index = i2;
        this.presetSettingSearchHelper419 = presetSettingSearchHelper419;
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        super.do19(drawContext, matrixStack, d, d2);
        FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, this.string, (getPresetEnumSettingHelper1394().getX() + (getPresetEnumSettingHelper1394().get1635() / 2.0f)) - (FontsSearchHelper4.fontsSearchHelper4.get1316(this.string) / 2.0f), (((this.presetSettingSearchHelper419.getY() + this.num) + get1742()) - 1.0f) - get1396(), getUI1744().textColor.getValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do20(double d, double d2, int i) {
        if (!this.presetSettingSearchHelper419.is1669() && is92(d, d2) && this.presetSettingSearchHelper419.is623()) {
            super.do20(d, d2, i);
            if (this.presetSettingSearchHelper419.getPresetHelper_21667().is623() && i == 0) {
                for (Object obj : (Enum[]) this.presetSettingSearchHelper419.getSetting1668().getValue().getDeclaringClass().getEnumConstants()) {
                    if ((obj instanceof EnumSettingHelper) && ((EnumSettingHelper) obj).getName().equalsIgnoreCase(this.string)) {
                        ((me.mioclient.api.Setting) this.presetSettingSearchHelper419.getSetting1668()).do2333(obj);
                        this.presetSettingSearchHelper419.do1297(this.index);
                    }
                }
            }
        }
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public boolean is92(double d, double d2) {
        return d > ((double) this.presetSettingSearchHelper419.getX()) && d < ((double) (this.presetSettingSearchHelper419.getX() + getPresetEnumSettingHelper1394().get1635())) && d2 > ((double) (this.presetSettingSearchHelper419.getY() + this.num)) && d2 < ((double) ((this.presetSettingSearchHelper419.getY() + get1743()) + this.num));
    }

    @Override // me.mioclient.PresetHelper_5
    public int get93() {
        if (this.presetSettingSearchHelper419.is1669()) {
            return 0;
        }
        return super.get93();
    }

    public String getString2546() {
        return this.string;
    }
}
