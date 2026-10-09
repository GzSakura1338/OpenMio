package me.mioclient;

import java.util.function.Supplier;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PresetSearchHelper419_2.class */
public class PresetSearchHelper419_2 extends PresetSearchHelper419 {
    public final java.lang.Runnable runnable;

    public PresetSearchHelper419_2(PresetEnumSettingHelper presetEnumSettingHelper, String str, java.lang.Runnable runnable) {
        this(presetEnumSettingHelper, (Supplier<String>) () -> {
            return str;
        }, runnable);
    }

    public PresetSearchHelper419_2(PresetEnumSettingHelper presetEnumSettingHelper, Supplier<String> supplier, java.lang.Runnable runnable) {
        super(presetEnumSettingHelper, supplier);
        this.runnable = runnable;
        do2386(true);
    }

    @Override // me.mioclient.PresetSearchHelper419, me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        if (is960()) {
            CrosshairHelper.do1707(matrixStack, this.presetEnumSettingHelper.getX() + 1, this.presetEnumSettingHelper.getY() + this.num + 0.5f, (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 1, ((this.presetEnumSettingHelper.getY() + this.num) + get93()) - 0.5f, getUI1744().color.getValue());
        }
        super.do19(drawContext, matrixStack, d, d2);
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do20(double d, double d2, int i) {
        super.do20(d, d2, i);
        if (is92(d, d2) && i == 0) {
            this.runnable.run();
        }
    }

    public boolean is960() {
        return true;
    }
}
