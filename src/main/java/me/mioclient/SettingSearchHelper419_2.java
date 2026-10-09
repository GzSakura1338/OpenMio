package me.mioclient;

import me.mioclient.api.Setting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SettingSearchHelper419_2.class */
public class SettingSearchHelper419_2 extends SettingSearchHelper419<SearchIdentifier> {
    public SettingSearchHelper419_2(PresetEnumSettingHelper presetEnumSettingHelper, PresetHelper_2 presetHelper_2, Setting<?> setting) {
        super(presetEnumSettingHelper, presetHelper_2, (Setting<SearchIdentifier>) setting);
    }

    @Override // me.mioclient.SettingSearchHelper419, me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do20(double d, double d2, int i) {
        if (!is1669() && is92(d, d2) && i == 0) {
            minecraftClient.setScreen(new SettingFontsSearchHelper42(this.setting, getMixinTitleScreenSearchHelper41672()));
        }
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        if (is1669()) {
            return;
        }
        super.do19(drawContext, matrixStack, d, d2);
        if (is92(d, d2)) {
            FontsSearchHelper4_2.mode_5 = Mode_5.POINTER;
        }
        CrosshairHelper.do1707(matrixStack, this.presetEnumSettingHelper.getX() + get1397(), this.presetEnumSettingHelper.getY() + this.num + 0.5f, (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 1, ((this.presetEnumSettingHelper.getY() + this.num) + get93()) - 0.5f, getUI1744().color.getValue());
        String string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2919(((SearchIdentifier) this.setting.getValue()).getName()).getArgumentTypeHelper2919(this.setting.getName()).getString2921("\u0001: \u0001");
        do1670(matrixStack, string2921, () -> {
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, string2921, this.presetEnumSettingHelper.getX() + 4, ((this.presetEnumSettingHelper.getY() + get1742()) - get1396()) + this.num, getUI1744().textColor.getValue());
        });
    }
}
