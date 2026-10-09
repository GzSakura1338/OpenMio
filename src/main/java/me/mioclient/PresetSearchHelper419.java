package me.mioclient;

import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PresetSearchHelper419.class */
public class PresetSearchHelper419 extends SearchHelper4_19 {
    public final Supplier<String> supplier;
    public boolean flag;

    public PresetSearchHelper419(PresetEnumSettingHelper presetEnumSettingHelper, Supplier<String> supplier) {
        super(presetEnumSettingHelper, 0);
        this.supplier = supplier;
    }

    public PresetSearchHelper419(PresetEnumSettingHelper presetEnumSettingHelper, String str) {
        super(presetEnumSettingHelper, 0);
        this.supplier = () -> {
            return str;
        };
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        String[] split = this.supplier.get().split("\n");
        for (int i = 0; i < split.length; i++) {
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, split[i], this.presetEnumSettingHelper.getX() + getUI1744().modulePadding.getValue().intValue(), ((((this.presetEnumSettingHelper.getY() + this.num) + get1742()) + (i * FontsSearchHelper4.fontsSearchHelper4.get93())) + 1.0f) - ((this.flag && is92(d, d2)) ? 1.0f : 0.0f), Color.white);
        }
        super.do19(drawContext, matrixStack, d, d2);
    }

    @Override // me.mioclient.PresetHelper_5
    public int get93() {
        return super.get93() + (FontsSearchHelper4.fontsSearchHelper4.get93() * (this.supplier.get().split("\n").length - 1)) + 1;
    }

    public void do2386(boolean z) {
        this.flag = z;
    }
}
