package me.mioclient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;
import me.mioclient.api.Setting;
import me.mioclient.feature.Progress;
import me.mioclient.module.client.UI;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PresetSettingSearchHelper419.class */
public class PresetSettingSearchHelper419 extends SettingSearchHelper419<Enum<?>> {
    public final ArrayList<SearchHelper419> arrayList;
    public final HUDHelper_2 hUDHelper_2;
    public final Progress progress;
    public boolean flag;
    public int num;
    public int x;
    public int y;
    public int num2;
    public final int num3;

    public PresetSettingSearchHelper419(PresetEnumSettingHelper presetEnumSettingHelper, PresetHelper_2 presetHelper_2, Setting<?> setting) {
        super(presetEnumSettingHelper, presetHelper_2, (Setting) setting);
        this.arrayList = new ArrayList<>();
        this.hUDHelper_2 = new HUDHelper_2((Supplier<Float>) () -> {
            return Float.valueOf(2.0f * getUI1744().animSpeed.getValue().floatValue());
        }, true);
        this.progress = new Progress((Supplier<Float>) () -> {
            return Float.valueOf(2.0f * UI.uI.animSpeed.getValue().floatValue());
        }, true);
        this.flag = false;
        int i = get1743();
        int i2 = 0;
        for (Enum r0 : (Enum[]) ((Enum) this.setting.getObject2324()).getDeclaringClass().getEnumConstants()) {
            if (!EnumSettingConverterHelper.is1630(r0)) {
                this.arrayList.add(new SearchHelper419(this, EnumSettingConverter.getString913(r0), i, i2));
                i += get1743();
                i2++;
            }
        }
        this.num3 = i2;
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do653(int i) {
        this.num = i;
    }

    @Override // me.mioclient.SettingSearchHelper419, me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do20(double d, double d2, int i) {
        if (is1669()) {
            return;
        }
        super.do20(d, d2, i);
        this.arrayList.forEach(searchHelper419 -> {
            searchHelper419.do20(d, d2, i);
        });
        if (is92(d, d2)) {
            if (i == 0) {
                this.setting.do2333(EnumSettingConverter.getEnum912((Enum) this.setting.getValue()));
                this.num2 = (this.num2 + 1) % this.num3;
            }
            if (i == 1) {
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    if (i3 >= ((Enum[]) ((Enum) this.setting.getValue()).getDeclaringClass().getEnumConstants()).length) {
                        break;
                    }
                    Enum r0 = ((Enum[]) ((Enum) this.setting.getValue()).getDeclaringClass().getEnumConstants())[i3];
                    if (r0 == this.setting.getValue()) {
                        this.num2 = i2;
                        break;
                    } else {
                        if (!EnumSettingConverterHelper.is1630(r0)) {
                            i2++;
                        }
                        i3++;
                    }
                }
                this.flag = !this.flag;
                getPresetEnumSettingHelper1394().do466();
            }
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do91(double d, double d2) {
        if (is1669()) {
            return;
        }
        this.y = getPresetEnumSettingHelper1394().getY() + this.num;
        this.x = getPresetEnumSettingHelper1394().getX();
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        if (is1669()) {
            this.progress.reset();
            return;
        }
        super.do19(drawContext, matrixStack, d, d2);
        this.progress.do2139(true);
        if (this.flag) {
            this.hUDHelper_2.do1737(this.num2 * get1743());
            float f = this.hUDHelper_2.get172();
            CrosshairHelper.do1707(matrixStack, this.presetEnumSettingHelper.getX() + get1397() + 1, (((this.presetEnumSettingHelper.getY() + this.num) + f) + get1743()) - 0.5f, (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 2, (((this.presetEnumSettingHelper.getY() + this.num) + (get1743() * 2)) + f) - 0.5f, getUI1744().color.getValue());
        } else {
            this.hUDHelper_2.do1737(-get1743());
        }
        int i = this.flag ? 1 : 0;
        CrosshairHelper.do1707(matrixStack, this.presetEnumSettingHelper.getX() + get1397() + i, this.presetEnumSettingHelper.getY() + this.num + 0.5f + i, (this.presetEnumSettingHelper.getX() + Math.max((this.presetEnumSettingHelper.get1635() * this.progress.get172()) - 1.0f, 2.0f)) - i, ((this.presetEnumSettingHelper.getY() + this.num) + get1743()) - 0.5f, getUI1744().color.getValue());
        if (this.flag) {
            this.arrayList.forEach(searchHelper419 -> {
                searchHelper419.do19(drawContext, matrixStack, d, d2);
            });
            if (UI.uI.line.getValue().booleanValue()) {
                CrosshairHelper.do1705(matrixStack, this.presetEnumSettingHelper.getX() + get1397(), this.presetEnumSettingHelper.getY() + this.num + 0.5f, (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 2, ((this.presetEnumSettingHelper.getY() + this.num) + get93()) - 1.5f, getUI1744().color.getValue());
            }
        }
        String string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2919(EnumSettingConverter.getString913((Enum) this.setting.getValue())).getArgumentTypeHelper2919(this.setting.getName()).getString2921("\u0001: \u0001");
        do1670(matrixStack, string2921, () -> {
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, string2921, this.presetEnumSettingHelper.getX() + 4, ((this.presetEnumSettingHelper.getY() + get1742()) - get1396()) + this.num, getUI1744().textColor.getValue());
        });
        int i2 = get1743();
        Iterator<SearchHelper419> it = this.arrayList.iterator();
        while (it.hasNext()) {
            it.next().do653(i2);
            i2 += get1743();
        }
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public boolean is92(double d, double d2) {
        return d > ((double) this.x) && d < ((double) (this.x + getPresetEnumSettingHelper1394().get1635())) && d2 > ((double) this.y) && d2 < ((double) (this.y + 11));
    }

    @Override // me.mioclient.SettingSearchHelper419, me.mioclient.PresetHelper_5
    public int get93() {
        if (!is1669()) {
            return this.flag ? get1743() + (this.num3 * get1743()) + 1 : super.get93();
        }
        this.hUDHelper_2.do171(-get1743());
        return 0;
    }

    @Override // me.mioclient.PresetHelper_5
    public void init() {
        do1297(((Enum) this.setting.getValue()).ordinal());
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    public boolean is623() {
        return this.flag;
    }

    public int get1296() {
        return this.num2;
    }

    public void do1297(int i) {
        this.num2 = i;
    }
}
