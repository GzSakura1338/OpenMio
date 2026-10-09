package me.mioclient;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SearchHelper4_19.class */
public abstract class SearchHelper4_19 implements SearchHelper_4, PresetHelper_5 {
    public final PresetEnumSettingHelper presetEnumSettingHelper;
    public int num;
    public int num2;
    public final HUDHelper_2 hUDHelper_2 = new HUDHelper_2(4.0f);
    public boolean flag = false;

    public SearchHelper4_19(PresetEnumSettingHelper presetEnumSettingHelper, int i) {
        this.presetEnumSettingHelper = presetEnumSettingHelper;
        this.num = i;
    }

    public PresetEnumSettingHelper getPresetEnumSettingHelper1394() {
        return this.presetEnumSettingHelper;
    }

    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        SearchHelper4_4 searchHelper4_42970;
        if (is92(d, d2)) {
            if (!this.flag && getUI1744().hover.getValue().booleanValue() && !is1400(Mode_13.SILENT) && (searchHelper4_42970 = BaritoneHelper_3.searchHelper4_11.getSearchHelper4_42970(getUI1744().sound2.getValue())) != null) {
                searchHelper4_42970.do1820(getUI1744().volume.getValue().floatValue());
            }
            this.flag = true;
            BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().do2829();
            FontsSearchHelper4_2.mode_5 = Mode_5.POINTER;
        } else {
            this.flag = false;
        }
        if (getUI1744().bounce.getValue().booleanValue()) {
            this.hUDHelper_2.do1737(is92(d, d2) ? 1.0f : 0.0f);
        } else {
            this.hUDHelper_2.do1737(0.0f);
        }
        PresetHelper_5.super.do19(drawContext, matrixStack, d, d2);
    }

    public void do653(int i) {
        this.num = i;
    }

    @Override // me.mioclient.PresetHelper_5
    public int get1395() {
        return this.num;
    }

    public float get1396() {
        return this.hUDHelper_2.get172();
    }

    public void do20(double d, double d2, int i) {
        SearchHelper4_4 searchHelper4_42970;
        SearchHelper4_4 searchHelper4_429702;
        if (!is92(d, d2) || is1400(Mode_13.SILENT)) {
            return;
        }
        if (i == 0 && is1398()) {
            if (!getUI1744().leftClick.getValue().booleanValue() || (searchHelper4_429702 = BaritoneHelper_3.searchHelper4_11.getSearchHelper4_42970(getUI1744().sound3.getValue())) == null) {
                return;
            }
            searchHelper4_429702.do1820(getUI1744().volume3.getValue().floatValue());
            return;
        }
        if (i == 1 && getUI1744().rightClick.getValue().booleanValue() && (searchHelper4_42970 = BaritoneHelper_3.searchHelper4_11.getSearchHelper4_42970(getUI1744().sound.getValue())) != null) {
            searchHelper4_42970.do1820(getUI1744().volume2.getValue().floatValue());
        }
    }

    public boolean is92(double d, double d2) {
        return d > ((double) this.presetEnumSettingHelper.getX()) && d < ((double) (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635())) && d2 > ((double) (this.presetEnumSettingHelper.getY() + this.num)) && d2 < ((double) ((this.presetEnumSettingHelper.getY() + get93()) + this.num));
    }

    public int get1397() {
        return is1400(Mode_13.PADDING_SHIFT) ? 2 : 1;
    }

    public boolean is1398() {
        return ((this instanceof ArrayListPresetHelper2) && (((ArrayListPresetHelper2) this).getModule595() instanceof KeybindModule)) ? false : true;
    }

    public void do1399(Mode_13... mode_13Arr) {
        for (Mode_13 mode_13 : mode_13Arr) {
            this.num2 |= mode_13.get2796();
        }
    }

    public boolean is1400(Mode_13 mode_13) {
        return (this.num2 & mode_13.get2796()) == mode_13.get2796();
    }
}
