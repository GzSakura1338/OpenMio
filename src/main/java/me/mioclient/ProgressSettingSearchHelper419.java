package me.mioclient;

import java.util.function.Supplier;
import me.mioclient.api.Setting;
import me.mioclient.feature.Progress;
import me.mioclient.module.client.UI;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ProgressSettingSearchHelper419.class */
public class ProgressSettingSearchHelper419 extends SettingSearchHelper419<Boolean> {
    public final Progress progress;

    public ProgressSettingSearchHelper419(PresetEnumSettingHelper presetEnumSettingHelper, PresetHelper_2 presetHelper_2, Setting<?> setting) {
        super(presetEnumSettingHelper, presetHelper_2, (BooleanSetting) setting);
        this.progress = new Progress((Supplier<Float>) () -> {
            return Float.valueOf(2.0f * UI.uI.animSpeed.getValue().floatValue());
        }, true);
    }

    @Override // me.mioclient.SettingSearchHelper419, me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do20(double d, double d2, int i) {
        if (is1669()) {
            return;
        }
        if (this.setting.flag4 && i == 0) {
            return;
        }
        super.do20(d, d2, i);
        if (is92(d, d2)) {
            if (i == 0) {
                this.setting.do2333(Boolean.valueOf(!((Boolean) this.setting.getValue()).booleanValue()));
            }
            if (i == 1 && this.setting.flag2) {
                this.setting.flag = !this.setting.flag;
            }
        }
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        if (is1669()) {
            this.progress.reset();
            return;
        }
        this.progress.do2139(((Boolean) this.setting.getValue()).booleanValue());
        float f = this.progress.get172();
        super.do19(drawContext, matrixStack, d, d2);
        if ((((Boolean) this.setting.getValue()).booleanValue() || f > 0.01) && !this.setting.flag4) {
            CrosshairHelper.do1707(matrixStack, this.presetEnumSettingHelper.getX() + get1397(), this.presetEnumSettingHelper.getY() + this.num + 0.5f, this.presetEnumSettingHelper.getX() + Math.max((this.presetEnumSettingHelper.get1635() * f) - 1.0f, 2.0f), ((this.presetEnumSettingHelper.getY() + this.num) + get93()) - 0.5f, getUI1744().color.getValue());
        }
        String name = this.setting.getName();
        if (!name.isEmpty()) {
            do1670(matrixStack, name, () -> {
                FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, name, this.presetEnumSettingHelper.getX() + 4, ((this.presetEnumSettingHelper.getY() + get1742()) - get1396()) + this.num, getUI1744().textColor.getValue());
            });
        }
        if (this.setting.flag2 && !this.setting.flag4) {
            String str = this.setting.flag ? "-" : "+";
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, str, ((this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 4) - FontsSearchHelper4.fontsSearchHelper4.get1316(str), ((this.presetEnumSettingHelper.getY() + get1742()) - get1396()) + this.num, getUI1744().textColor.getValue());
        }
        if (this.setting.flag4) {
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, "...", ((this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 4) - FontsSearchHelper4.fontsSearchHelper4.get1316("..."), ((this.presetEnumSettingHelper.getY() + get1742()) - get1396()) + this.num, getUI1744().textColor.getValue());
        }
    }
}
