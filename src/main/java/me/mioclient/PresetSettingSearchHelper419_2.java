package me.mioclient;

import me.mioclient.api.Setting;
import me.mioclient.feature.Stopwatch;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PresetSettingSearchHelper419_2.class */
public class PresetSettingSearchHelper419_2 extends SettingSearchHelper419<String> {
    public final Stopwatch stopwatch;
    public boolean flag;
    public String string;
    public int num;

    public PresetSettingSearchHelper419_2(PresetEnumSettingHelper presetEnumSettingHelper, PresetHelper_2 presetHelper_2, Setting<?> setting) {
        super(presetEnumSettingHelper, presetHelper_2, (StringSetting) setting);
        this.stopwatch = new Stopwatch();
        this.flag = false;
        this.num = 0;
    }

    @Override // me.mioclient.SettingSearchHelper419, me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do20(double d, double d2, int i) {
        if (is1669()) {
            this.flag2 = false;
            return;
        }
        super.do20(d, d2, i);
        if (is92(d, d2) && i == 0) {
            this.flag2 = !this.flag2;
            if (this.flag2) {
                if (getMixinTitleScreenSearchHelper41672().getSettingSearchHelper4192409() != null && getMixinTitleScreenSearchHelper41672().getSettingSearchHelper4192409() != this) {
                    getMixinTitleScreenSearchHelper41672().getSettingSearchHelper4192409().flag2 = false;
                }
                getMixinTitleScreenSearchHelper41672().do2410(this);
            } else if (getMixinTitleScreenSearchHelper41672().getSettingSearchHelper4192409() == this) {
                getMixinTitleScreenSearchHelper41672().do2410(null);
            }
            this.string = (String) this.setting.getValue();
            if (this.string == null || this.string.isEmpty()) {
                return;
            }
            this.num = this.string.length();
        }
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        if (is1669()) {
            if (this.flag2 && getMixinTitleScreenSearchHelper41672().getSettingSearchHelper4192409() == this) {
                getMixinTitleScreenSearchHelper41672().do2410(null);
            }
            this.flag2 = false;
            return;
        }
        super.do19(drawContext, matrixStack, d, d2);
        if (this.flag) {
            FontsSearchHelper4_2.mode_5 = Mode_5.INPUT;
        }
        CrosshairHelper.do1707(matrixStack, this.presetEnumSettingHelper.getX() + get1397(), this.presetEnumSettingHelper.getY() + this.num + 0.5f, (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 1, ((this.presetEnumSettingHelper.getY() + this.num) + get93()) - 0.5f, getUI1744().color.getValue());
        if (this.string != null) {
            this.num = MathHelper.clamp(this.num, 0, this.string.length());
        }
        if (!this.flag2) {
            do1670(matrixStack, new ArgumentTypeHelper().getArgumentTypeHelper2919((String) this.setting.getValue()).getArgumentTypeHelper2919(this.setting.getName()).getString2921("\u0001: \u0001"), () -> {
                FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, new ArgumentTypeHelper().getArgumentTypeHelper2919((String) this.setting.getValue()).getArgumentTypeHelper2919(this.setting.getName()).getString2921("\u0001: \u0001"), this.presetEnumSettingHelper.getX() + 4, ((this.presetEnumSettingHelper.getY() + get1742()) - get1396()) + this.num, getUI1744().textColor.getValue());
            });
            return;
        }
        matrixStack.push();
        String string95 = getString95();
        float f = FontsSearchHelper4.fontsSearchHelper4.get1316(new ArgumentTypeHelper().getArgumentTypeHelper2919(this.string.substring(0, this.num)).getString2921("\u0001_"));
        if (f > getPresetEnumSettingHelper1394().get1635() - (get1397() * 2)) {
            matrixStack.translate((getPresetEnumSettingHelper1394().get1635() - (get1397() * 2)) - f, 0.0f, 0.0f);
        }
        if (!this.string.isEmpty()) {
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, this.string, this.presetEnumSettingHelper.getX() + 4, ((this.presetEnumSettingHelper.getY() + get1742()) - get1396()) + this.num, getUI1744().textColor.getValue());
        }
        if (!string95.isBlank()) {
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, string95, this.presetEnumSettingHelper.getX() + 4 + FontsSearchHelper4.fontsSearchHelper4.get1316(this.string.substring(0, this.num)), ((this.presetEnumSettingHelper.getY() + get1742()) - get1396()) + this.num, getUI1744().textColor.getValue());
        }
        matrixStack.pop();
    }

    @Override // me.mioclient.PresetHelper_5
    public void do89(int i) {
        if (!is1669() && this.flag2) {
            BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().do2837();
            switch (i) {
                case 86:
                    if (GLFW.glfwGetKey(minecraftClient.getWindow().getHandle(), 341) == 1) {
                        this.string = new StringBuilder(this.string).insert(this.num, minecraftClient.keyboard.getClipboard()).toString();
                        return;
                    }
                    return;
                case 256:
                    this.flag2 = false;
                    return;
                case 257:
                    this.flag2 = false;
                    this.setting.do2333(this.string);
                    this.num = this.string.length();
                    return;
                case 259:
                    if (this.string.length() == 0 || this.num == 0) {
                        return;
                    }
                    int i2 = this.num - 1;
                    if (this.num == this.string.length()) {
                        this.string = this.string.substring(0, this.string.length() - 1);
                    } else if (i2 >= 0 && i2 <= this.string.length()) {
                        this.string = new StringBuilder(this.string).deleteCharAt(i2).toString();
                    }
                    this.num--;
                    return;
                case 262:
                    if (this.num < this.string.length()) {
                        this.num++;
                        return;
                    }
                    return;
                case 263:
                    if (this.num > 0) {
                        this.num--;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do90(char c) {
        if (!is1669() && this.flag2) {
            this.string = new StringBuilder(this.string).insert(this.num, c).toString();
            this.num++;
        }
    }

    public String getString95() {
        if (this.stopwatch.is419(500L)) {
            this.flag = !this.flag;
            this.stopwatch.reset();
        }
        return this.flag ? "_" : "";
    }
}
