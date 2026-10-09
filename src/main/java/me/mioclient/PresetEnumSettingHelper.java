package me.mioclient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import me.mioclient.feature.Progress;
import me.mioclient.module.client.UI;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/PresetEnumSettingHelper.class */
public class PresetEnumSettingHelper implements EnumSettingHelper, PresetHelper_5, Helper_9<PresetHelper_5, List<PresetHelper_5>> {
    public final String name;
    public int num2 = 14;
    public int num4;
    public int num5;
    public final ArrayList<PresetHelper_5> registry = new ArrayList<>();
    public Progress progress = new Progress((Supplier<Float>) () -> {
        return Float.valueOf(getUI1744().animSpeed.getValue().floatValue() * 2.0f);
    }, true);
    public final int num = 92;
    public int num3 = 0;
    public boolean flag = true;
    public int y = 5;
    public int x = 5;
    public boolean flag2 = false;

    public PresetEnumSettingHelper(String str) {
        this.num2 = 14;
        this.num2 = 14;
        this.name = str;
        this.progress.do2140(true);
    }

    @Override // me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        CrosshairHelper.do1707(drawContext.getMatrices(), this.x, this.y, this.x + get1635(), this.y + 14, UI.uI.color.getValue());
        FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, this.name, this.x + 2, (this.y + 7.0f) - (FontsSearchHelper4.fontsSearchHelper4.get93() / 2.0f), Color.white);
        this.progress.do2139(this.flag);
        float f = this.progress.get172();
        if (f != 1.0f && this.num5 != 0) {
            CrosshairHelper.do1597();
            SearchHelper4_17.do1106(this.x - 1, this.y - 1, get1635() + 2, ((int) (14.0f + (this.num5 * f))) + 3);
        }
        if (f > 0.0f && !this.registry.isEmpty()) {
            CrosshairHelper.do1707(drawContext.getMatrices(), this.x, this.y + 14, this.x + get1635(), this.y + this.num5 + 0.5f, UI.uI.bgColor.getValue());
            this.registry.forEach(presetHelper_5 -> {
                if (is1772(presetHelper_5)) {
                    presetHelper_5.do91(d, d2);
                    presetHelper_5.do19(drawContext, matrixStack, d, d2);
                }
            });
            if (UI.uI.windowShadow.getValue().booleanValue()) {
                CrosshairHelper.do1707(drawContext.getMatrices(), this.x + UI.uI.shadowSize.getValue().floatValue(), this.y + this.num5 + 0.5f, this.x + get1635(), this.y + this.num5 + 0.5f + UI.uI.shadowSize.getValue().floatValue(), UI.uI.shadowColor.getValue());
                CrosshairHelper.do1707(drawContext.getMatrices(), this.x + get1635(), this.y + UI.uI.shadowSize.getValue().floatValue(), this.x + get1635() + UI.uI.shadowSize.getValue().floatValue(), this.y + this.num5 + 0.5f + UI.uI.shadowSize.getValue().floatValue(), UI.uI.shadowColor.getValue());
            }
        }
        if (UI.uI.line.getValue().booleanValue()) {
            CrosshairHelper.do1705(drawContext.getMatrices(), this.x - 1, this.y - 1, this.x + get1635(), this.y + (f == 0.0f ? 14.0f : this.num5 + 0.5f), UI.uI.color.getValue());
        }
        if (f != 1.0f && this.num5 != 0) {
            CrosshairHelper.do1597();
            SearchHelper4_17.do1107();
        }
        Objects.requireNonNull(this);
        int i = 15;
        Iterator<PresetHelper_5> it = this.registry.iterator();
        while (it.hasNext()) {
            PresetHelper_5 next = it.next();
            if (is1772(next)) {
                next.do653(i);
                i += next.get93();
            } else {
                next.do653(0);
            }
        }
        this.num5 = f == 0.0f ? 15 : i;
    }

    @Override // me.mioclient.PresetHelper_5
    public void do20(double d, double d2, int i) {
        if (is1973(d, d2) && i == 1) {
            do1971(!this.flag);
        }
        if (this.flag) {
            Iterator<PresetHelper_5> it = this.registry.iterator();
            while (it.hasNext()) {
                it.next().do20(d, d2, i);
            }
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do88(double d, double d2, int i) {
        if (this.flag) {
            Iterator<PresetHelper_5> it = this.registry.iterator();
            while (it.hasNext()) {
                it.next().do88(d, d2, i);
            }
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do654(double d, double d2, double d3) {
        if (this.flag) {
            Iterator<PresetHelper_5> it = this.registry.iterator();
            while (it.hasNext()) {
                it.next().do654(d, d2, d3);
            }
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do89(int i) {
        if (this.flag) {
            Iterator<PresetHelper_5> it = this.registry.iterator();
            while (it.hasNext()) {
                it.next().do89(i);
            }
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do90(char c) {
        if (this.flag) {
            Iterator<PresetHelper_5> it = this.registry.iterator();
            while (it.hasNext()) {
                it.next().do90(c);
            }
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void init() {
        if (this.flag) {
            Iterator<PresetHelper_5> it = this.registry.iterator();
            while (it.hasNext()) {
                it.next().init();
            }
        }
    }

    public ArrayList<PresetHelper_5> getArrayList1968() {
        return this.registry;
    }

    public void do1969(boolean z) {
        if (z && !BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().is2832()) {
            BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().do2833(true);
            this.flag2 = true;
        } else {
            if (BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().is2832()) {
                BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().do2833(false);
            }
            this.flag2 = false;
        }
    }

    public boolean is1772(PresetHelper_5 presetHelper_5) {
        return true;
    }

    public boolean is623() {
        return this.flag && this.progress.val == 2.0f;
    }

    public float get1970() {
        return this.progress.get2138() - 1.0f;
    }

    public void do1971(boolean z) {
        this.flag = z;
    }

    public void do466() {
        Objects.requireNonNull(this);
        int i = 15;
        Iterator<PresetHelper_5> it = this.registry.iterator();
        while (it.hasNext()) {
            PresetHelper_5 next = it.next();
            next.do653(i);
            i += next.get93();
        }
        this.num5 = i;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int i) {
        this.x = i;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int i) {
        this.y = i;
    }

    public int get1635() {
        return this.num + UI.uI.frameWidth.getValue().intValue();
    }

    @Override // me.mioclient.PresetHelper_5
    public int get93() {
        return this.num5;
    }

    public void do1972(int i, int i2) {
        if (this.flag2) {
            setX(Math.max(0, Math.min(SearchHelper_4.minecraftClient.getWindow().getScaledWidth() - get1635(), i - this.num3)));
            setY(Math.max(0, Math.min(SearchHelper_4.minecraftClient.getWindow().getScaledHeight() - get93(), i2 - this.num4)));
        }
    }

    public boolean is1973(double d, double d2) {
        if (d >= this.x && d <= this.x + get1635() && d2 >= this.y) {
            int i = this.y;
            Objects.requireNonNull(this);
            if (d2 <= i + 14) {
                return true;
            }
        }
        return false;
    }

    @Override // me.mioclient.PresetHelper_5
    public boolean is92(double d, double d2) {
        return d >= ((double) this.x) && d <= ((double) (this.x + get1635())) && d2 >= ((double) this.y) && d2 <= ((double) (this.y + this.num5));
    }

    public boolean is1974() {
        return this.flag2;
    }

    @Override // me.mioclient.EnumSettingHelper
    public String getName() {
        return this.name;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // me.mioclient.Helper_9
    public List<PresetHelper_5> getRegistry() {
        return this.registry;
    }

    @Override // me.mioclient.Helper_9
    /* renamed from: is1975, reason: merged with bridge method [inline-methods] */
    public boolean register(PresetHelper_5 presetHelper_5) {
        return getRegistry().add(presetHelper_5);
    }

    @Override // me.mioclient.Helper_9
    /* renamed from: is1976, reason: merged with bridge method [inline-methods] */
    public boolean unregister(PresetHelper_5 presetHelper_5) {
        return getRegistry().add(presetHelper_5);
    }
}
