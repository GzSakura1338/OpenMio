package me.mioclient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import me.mioclient.api.Setting;
import me.mioclient.feature.Stopwatch;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/StopwatchSettingSearchHelper419.class */
public class StopwatchSettingSearchHelper419 extends SettingSearchHelper419<Number> {
    public static final NumberFormat numberFormat = NumberFormat.getNumberInstance();
    public final HUDHelper_2 hUDHelper_2;
    public final Stopwatch stopwatch;
    public int barX;
    public int barY;
    public boolean flag;
    public boolean flag2;
    public float val;
    public String string;

    public StopwatchSettingSearchHelper419(PresetEnumSettingHelper presetEnumSettingHelper, PresetHelper_2 presetHelper_2, Setting<?> setting) {
        super(presetEnumSettingHelper, presetHelper_2, (Setting) setting);
        this.hUDHelper_2 = new HUDHelper_2(6.0f, false);
        this.stopwatch = new Stopwatch();
        this.flag2 = false;
        this.string = "";
    }

    public static double get87(double d) {
        return new BigDecimal(d).setScale(4, RoundingMode.HALF_UP).doubleValue();
    }

    @Override // me.mioclient.SettingSearchHelper419, me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do20(double d, double d2, int i) {
        if (is1669()) {
            this.flag2 = false;
            return;
        }
        super.do20(d, d2, i);
        if (is92(d, d2) && i == 0 && this.presetHelper_2.is623()) {
            this.flag = true;
        }
        if (is92(d, d2) && i == 1 && this.presetHelper_2.is623()) {
            this.flag2 = !this.flag2;
            if (!this.flag2) {
                if (getMixinTitleScreenSearchHelper41672().getSettingSearchHelper4192409() == this) {
                    getMixinTitleScreenSearchHelper41672().do2410(null);
                }
            } else {
                if (getMixinTitleScreenSearchHelper41672().getSettingSearchHelper4192409() != null && getMixinTitleScreenSearchHelper41672().getSettingSearchHelper4192409() != this) {
                    getMixinTitleScreenSearchHelper41672().getSettingSearchHelper4192409().flag2 = false;
                }
                getMixinTitleScreenSearchHelper41672().do2410(this);
                this.string = ((Number) this.setting.getValue()).toString();
            }
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do88(double d, double d2, int i) {
        this.flag = false;
    }

    @Override // me.mioclient.PresetHelper_5
    public void do89(int i) {
        if (!is1669() && this.flag2 && this.presetHelper_2.is623()) {
            switch (i) {
                case 256:
                    this.flag2 = false;
                    BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().do2837();
                    return;
                case 257:
                case 335:
                    this.flag2 = false;
                    boolean z = this.string == null || this.string.isBlank();
                    try {
                        String simpleName = ((Number) this.setting.getValue()).getClass().getSimpleName();
                        int z2 = -1;
                        switch (simpleName.hashCode()) {
                            case -672261858:
                                if (simpleName.equals("Integer")) {
                                    z2 = 0;
                                    break;
                                }
                                break;
                            case 67973692:
                                if (simpleName.equals("Float")) {
                                    z2 = 1;
                                    break;
                                }
                                break;
                            case 2052876273:
                                if (simpleName.equals("Double")) {
                                    z2 = 2;
                                    break;
                                }
                                break;
                        }
                        switch (z2) {
                            case 0:
                                this.setting.do2333(z ? (Number) this.setting.getValue() : Long.valueOf(Math.round(Double.parseDouble(this.string))));
                                break;
                            case 1:
                                this.setting.do2333(z ? (Number) this.setting.getValue() : Float.valueOf(Float.parseFloat(new ArgumentTypeHelper().getArgumentTypeHelper2919(this.string).getString2921("\u0001f"))));
                                break;
                            case 2:
                                this.setting.do2333(z ? (Number) this.setting.getValue() : Double.valueOf(Double.parseDouble(new ArgumentTypeHelper().getArgumentTypeHelper2919(this.string).getString2921("\u0001d"))));
                                break;
                        }
                        return;
                    } catch (Exception e) {
                        return;
                    }
                case 259:
                    if (this.string.length() > 0) {
                        this.string = this.string.substring(0, this.string.length() - 1);
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
        if (!is1669() && this.flag2 && this.presetHelper_2.is623() && FontsSearchHelper4.is1685("1234567890.-", c)) {
            if (c == '.' && FontsSearchHelper4.is1685(this.string, c)) {
                return;
            }
            this.string = new ArgumentTypeHelper().getArgumentTypeHelper2904(c).getArgumentTypeHelper2919(this.string).getString2921("\u0001\u0001");
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do91(double d, double d2) {
        if (is1669()) {
            this.hUDHelper_2.do171(0.0f);
        }
        this.barY = this.presetEnumSettingHelper.getY() + this.num;
        this.barX = this.presetEnumSettingHelper.getX();
        double d3 = getPresetEnumSettingHelper1394().get1635() - 2;
        double min = Math.min(d3, Math.max(0.0d, d - this.barX));
        double doubleValue = ((Number) this.setting.getObject2325()).doubleValue();
        double doubleValue2 = ((Number) this.setting.getObject2326()).doubleValue();
        double longBitsToDouble = ((Number) this.setting.getValue()).getClass().getSimpleName().equalsIgnoreCase("Integer") ? 1.0 : 0.1;
        this.val = (float) ((d3 * ((longBitsToDouble == 1.0 ? ((Number) this.setting.getValue()).intValue() : ((Number) this.setting.getValue()).floatValue()) - doubleValue)) / (doubleValue2 - doubleValue));
        if (this.flag) {
            if (min != 0.0d) {
                float f = (float) (((min / d3) * (doubleValue2 - doubleValue)) + doubleValue);
                float longBitsToDouble2 = (float) (1.0 / longBitsToDouble);
                double max = Math.max(doubleValue, Math.min(doubleValue2, f));
                if (longBitsToDouble == 0.1) {
                    this.setting.do2333(Double.valueOf(get87(((float) Math.round(max * longBitsToDouble2)) / longBitsToDouble2)));
                } else {
                    this.setting.do2333(Double.valueOf(get87(max)));
                }
            } else if (longBitsToDouble == 1.0) {
                this.setting.do2333(Integer.valueOf((int) ((Number) this.setting.getObject2325()).floatValue()));
            } else {
                this.setting.do2333(Float.valueOf(((Number) this.setting.getObject2325()).floatValue()));
            }
        }
        if (((Number) this.setting.getValue()).floatValue() >= ((Number) this.setting.getObject2326()).floatValue()) {
            this.val = getPresetEnumSettingHelper1394().get1635() - 2;
        }
        this.hUDHelper_2.do1737(this.val);
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
        if (this.flag && this.flag2) {
            FontsSearchHelper4_2.mode_5 = Mode_5.INPUT;
        }
        CrosshairHelper.do1707(matrixStack, this.barX + get1397(), this.barY + 0.5f, (int) (this.barX + MathHelper.clamp(this.hUDHelper_2.get172(), 0.0f, this.presetEnumSettingHelper.get1635() - 3) + 2.0f), (this.barY + get93()) - 0.5f, getUI1744().color.getValue());
        String string2921 = this.flag2 ? this.string == null ? "" : this.string : this.setting.is2348() ? new ArgumentTypeHelper().getArgumentTypeHelper2919(this.setting.getString2331()).getArgumentTypeHelper2919(this.setting.getName()).getString2921("\u0001: \u0001") : new ArgumentTypeHelper().getArgumentTypeHelper2919(((NumberSetting) this.setting).getString3024()).getArgumentTypeHelper2919(String.format("%s: %s", this.setting.getName(), numberFormat.format(((Number) this.setting.getValue()).doubleValue())).replace(",", ".")).getString2921("\u0001\u0001");
        do1670(matrixStack, getString94(string2921), () -> {
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, new ArgumentTypeHelper().getArgumentTypeHelper2919(getString95()).getArgumentTypeHelper2919(string2921).getString2921("\u0001\u0001"), this.presetEnumSettingHelper.getX() + 4, ((this.presetEnumSettingHelper.getY() + get1742()) - get1396()) + this.num, getUI1744().textColor.getValue());
        });
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public boolean is92(double d, double d2) {
        return super.is92(d, d2) || this.flag;
    }

    @Override // me.mioclient.SettingSearchHelper419, me.mioclient.PresetHelper_5
    public int get93() {
        if (this.setting.is2349()) {
            return get1743();
        }
        return 0;
    }

    public String getString94(String str) {
        return new ArgumentTypeHelper().getArgumentTypeHelper2919(this.flag2 ? "_" : "").getArgumentTypeHelper2919(str).getString2921("\u0001\u0001");
    }

    public boolean cursorBlink;
    public String getString95() {
        // 光标闪烁: 每 500ms 切换独立的 cursorBlink, 不再切换 flag2(编辑模式标志)。
        // 反编译把 obf 的光标闪烁字段和编辑标志都并成了 flag2, 导致每个滑块每 500ms
        // 进出"编辑模式"→显示在值和空缓冲间闪烁。仅在真正编辑(flag2)且闪烁可见时显示 "_"。
        if (this.stopwatch.is419(500L)) {
            this.cursorBlink = !this.cursorBlink;
            this.stopwatch.reset();
        }
        return (this.cursorBlink && this.flag2) ? "_" : "";
    }

    static {
        numberFormat.setMinimumFractionDigits(1);
        numberFormat.setMaximumFractionDigits(4);
        numberFormat.setGroupingUsed(false);
    }
}
