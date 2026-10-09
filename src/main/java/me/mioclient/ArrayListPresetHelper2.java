package me.mioclient;

import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;
import me.mioclient.api.Setting;
import me.mioclient.feature.Progress;
import me.mioclient.module.Module;
import me.mioclient.module.client.UI;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ArrayListPresetHelper2.class */
public class ArrayListPresetHelper2 extends SearchHelper4_19 implements PresetHelper_2 {
    public final ArrayList<PresetHelper_5> arrayList;
    public final HUDHelper_2 hUDHelper_2;
    public final Progress progress;
    public final ZoomHelper zoomHelper;
    public final Module module;
    public int flashNum;
    public boolean flag;
    public float val;
    public static long num2 = 0;
    public static ArrayListPresetHelper2 arrayListPresetHelper2 = null;
    public static boolean flag2 = false;

    public ArrayListPresetHelper2(Module module, PresetEnumSettingHelper presetEnumSettingHelper, int i) {
        super(presetEnumSettingHelper, i);
        this.arrayList = new ArrayList<>();
        this.hUDHelper_2 = new HUDHelper_2((Supplier<Float>) () -> {
            return Float.valueOf(getUI1744().animSpeed.getValue().floatValue() * 2.0f);
        }, true);
        this.progress = new Progress((Supplier<Float>) () -> {
            return Float.valueOf(2.0f * UI.uI.animSpeed.getValue().floatValue());
        }, true);
        this.zoomHelper = new ZoomHelper();
        this.flashNum = 0;
        this.module = module;
        int i2 = this.num + get1743();
        Iterator<Setting<?>> it = module.getRegistry().iterator();
        while (it.hasNext()) {
            SettingSearchHelper419<?> settingSearchHelper419651 = getSettingSearchHelper419651(presetEnumSettingHelper, it.next());
            if (settingSearchHelper419651 != null) {
                settingSearchHelper419651.do653(i2);
                settingSearchHelper419651.do1399(Mode_13.PADDING_SHIFT);
                this.arrayList.add(settingSearchHelper419651);
            }
            i2 += 11;
        }
        if (!(module instanceof ModuleList) && !(module instanceof KeybindModule)) {
            this.arrayList.add(new KeybindSearchHelper419(i2, this));
        }
        this.hUDHelper_2.do171(get652());
    }

    @Nullable
    public SettingSearchHelper419<?> getSettingSearchHelper419651(PresetEnumSettingHelper presetEnumSettingHelper, Setting<?> setting) {
        Object object2324 = setting.getObject2324();
        Objects.requireNonNull(object2324);
        int typeSwitchIndex = object2324 instanceof Boolean ? 0
                : object2324 instanceof Number ? 1
                : object2324 instanceof Color ? 2
                : object2324 instanceof String ? 3
                : object2324 instanceof Enum ? 4
                : object2324 instanceof SearchIdentifier ? 5
                : -1;
        switch (typeSwitchIndex) {
            case 0:
                return new ProgressSettingSearchHelper419(presetEnumSettingHelper, this, setting);
            case 1:
                return new StopwatchSettingSearchHelper419(presetEnumSettingHelper, this, setting);
            case 2:
                return new StopwatchPresetHelper2(presetEnumSettingHelper, this, setting);
            case 3:
                return new PresetSettingSearchHelper419_2(presetEnumSettingHelper, this, setting);
            case 4:
                return new PresetSettingSearchHelper419(presetEnumSettingHelper, this, setting);
            case 5:
                return new SettingSearchHelper419_2(presetEnumSettingHelper, this, setting);
            default:
                return null;
        }
    }

    public Module getModule595() {
        return this.module;
    }

    @Override // me.mioclient.PresetHelper_5
    public int get93() {
        return (int) this.val;
    }

    public int get652() {
        int i = get1743();
        if (this.flag) {
            Iterator<PresetHelper_5> it = this.arrayList.iterator();
            while (it.hasNext()) {
                i += it.next().get93();
            }
        }
        return i;
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do653(int i) {
        this.num = i;
        int i2 = this.num + get1743();
        Iterator<PresetHelper_5> it = this.arrayList.iterator();
        while (it.hasNext()) {
            PresetHelper_5 next = it.next();
            next.do653(i2);
            i2 += next.get93();
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do91(double d, double d2) {
        this.hUDHelper_2.do1737(get652());
        this.val = this.hUDHelper_2.get172();
        this.arrayList.forEach(presetHelper_5 -> {
            presetHelper_5.do91(d, d2);
        });
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do20(double d, double d2, int i) {
        super.do20(d, d2, i);
        if (is92(d, d2) && i == 0 && is1398()) {
            this.module.do496();
        }
        if (is92(d, d2) && i == 1) {
            this.flag = !this.flag;
            if (this.flag && !BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().list.contains(this)) {
                BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().list.add(this);
            }
            this.presetEnumSettingHelper.do466();
            if (!BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().getTextFieldWidget2408().getText().isEmpty()) {
                do656();
            }
        }
        this.arrayList.forEach(presetHelper_5 -> {
            presetHelper_5.do20(d, d2, i);
        });
    }

    @Override // me.mioclient.PresetHelper_5
    public void do88(double d, double d2, int i) {
        this.arrayList.forEach(presetHelper_5 -> {
            presetHelper_5.do88(d, d2, i);
        });
    }

    @Override // me.mioclient.PresetHelper_5
    public void do89(int i) {
        this.arrayList.forEach(presetHelper_5 -> {
            presetHelper_5.do89(i);
        });
    }

    @Override // me.mioclient.PresetHelper_5
    public void do654(double d, double d2, double d3) {
        Iterator<PresetHelper_5> it = this.arrayList.iterator();
        while (it.hasNext()) {
            it.next().do654(d, d2, d3);
        }
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        super.do19(drawContext, matrixStack, d, d2);
        this.progress.do2139(this.module.isToggled());
        float f = this.progress.get172();
        float f2 = get1396();
        if (this.module.isToggled() || f > 0.01) {
            CrosshairHelper.do1707(matrixStack, this.presetEnumSettingHelper.getX() + 1.0f + ((this.presetEnumSettingHelper.get1635() - 1.0f) * f), this.presetEnumSettingHelper.getY() + this.num + 0.5f, (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 1.0f, ((this.presetEnumSettingHelper.getY() + this.num) + get1743()) - 0.5f, getUI1744().bgButton.getValue());
            CrosshairHelper.do1707(matrixStack, this.presetEnumSettingHelper.getX() + 1.0f, this.presetEnumSettingHelper.getY() + this.num + 0.5f, this.presetEnumSettingHelper.getX() + ((this.presetEnumSettingHelper.get1635() - 1.0f) * f), ((this.presetEnumSettingHelper.getY() + this.num) + get1743()) - 0.5f, getUI1744().bgEnabled.getValue());
        } else {
            CrosshairHelper.do1707(matrixStack, this.presetEnumSettingHelper.getX() + 1.0f, this.presetEnumSettingHelper.getY() + this.num + 0.5f, (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 1.0f, ((this.presetEnumSettingHelper.getY() + this.num) + get1743()) - 0.5f, getUI1744().bgButton.getValue());
        }
        if (this.flashNum > 0) {
            float f3 = this.zoomHelper.get172();
            CrosshairHelper.do1708(matrixStack, this.presetEnumSettingHelper.getX() + 1.0f, this.presetEnumSettingHelper.getY() + this.num + 0.5f, (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 1.0f, ((this.presetEnumSettingHelper.getY() + this.num) + get1743()) - 0.5f, MixinMessageIndicatorHelper_2.get822(1.0f, 0.0f, 0.0f, f3));
            if (f3 == 1.0f || f3 == 0.0f) {
                this.zoomHelper.do169((f3 + 1.0f) % 2.0f, 150L);
            }
            if (f3 == 0.0f) {
                this.flashNum = (this.flashNum + 1) % 4;
            }
        }
        FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, this.module.getName(), this.presetEnumSettingHelper.getX() + UI.uI.modulePadding.getValue().intValue(), ((this.presetEnumSettingHelper.getY() + get1742()) - f2) + this.num, this.module.isToggled() ? getUI1744().enabledColor.getValue() : getUI1744().textColor.getValue());
        if (getUI1744().gear.getValue().booleanValue() && !this.arrayList.isEmpty()) {
            String str = this.flag ? "-" : "+";
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, str, ((this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 4) - FontsSearchHelper4.fontsSearchHelper4.get1316(str), ((this.presetEnumSettingHelper.getY() + get1742()) - f2) + this.num, getUI1744().textColor.getValue());
        }
        net.minecraft.text.MutableText empty = Text.empty();
        if (this.module.isWip()) {
            empty.append("beta ");
        }
        if (getUI1744().binds.getValue().booleanValue() && this.module.getKeybind().get1945() != -1) {
            empty.append("[").append(this.module.getKeybind().getString773().toUpperCase(Locale.ROOT)).append("]");
        }
        if (!empty.getString().isEmpty()) {
            FontsSearchHelper4.fontsSearchHelper4.do1696(drawContext, empty, this.presetEnumSettingHelper.getX() + FontsSearchHelper4.fontsSearchHelper4.get1316(this.module.getName()) + getUI1744().modulePadding.getValue().intValue(), ((this.presetEnumSettingHelper.getY() + get1742()) - f2) + this.num + 1.0f, 0.5f, getUI1744().textColor.getValue());
        }
        matrixStack.push();
        if (this.val > get1743() || this.flag) {
            int clamp = (int) MathHelper.clamp(this.val, get1743(), (this.presetEnumSettingHelper.get93() * this.presetEnumSettingHelper.get1970()) - this.num);
            CrosshairHelper.do1597();
            SearchHelper4_17.do1106(this.presetEnumSettingHelper.getX(), this.presetEnumSettingHelper.getY() + this.num, this.presetEnumSettingHelper.get1635(), clamp);
            Iterator<PresetHelper_5> it = this.arrayList.iterator();
            while (it.hasNext()) {
                it.next().do19(drawContext, matrixStack, d, d2);
            }
            CrosshairHelper.do1597();
            SearchHelper4_17.do1107();
        }
        matrixStack.pop();
        if (getUI1744().descriptions.getValue().booleanValue() && is92(d, d2)) {
            if (getUI1744().delay.getValue().intValue() == 0) {
                BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().do2830(this.module.getDescription());
                return;
            }
            if (getUI1744().constantReset.getValue().booleanValue()) {
                if (arrayListPresetHelper2 != this) {
                    arrayListPresetHelper2 = this;
                    flag2 = false;
                    num2 = System.currentTimeMillis();
                }
            } else if (arrayListPresetHelper2 == null) {
                arrayListPresetHelper2 = this;
                num2 = System.currentTimeMillis();
                flag2 = false;
            } else if (arrayListPresetHelper2 != this) {
                arrayListPresetHelper2 = this;
                if (!flag2) {
                    num2 = System.currentTimeMillis();
                }
            }
            if (flag2) {
                BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().do2830(this.module.getDescription());
            } else if (System.currentTimeMillis() >= num2 + getUI1744().delay.getValue().intValue()) {
                flag2 = true;
                BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().do2830(this.module.getDescription());
            }
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do90(char c) {
        this.arrayList.forEach(presetHelper_5 -> {
            presetHelper_5.do90(c);
        });
    }

    @Override // me.mioclient.PresetHelper_5
    public void init() {
        Iterator<PresetHelper_5> it = this.arrayList.iterator();
        while (it.hasNext()) {
            it.next().init();
        }
    }

    @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public boolean is92(double d, double d2) {
        return d > ((double) this.presetEnumSettingHelper.getX()) && d < ((double) (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635())) && d2 > ((double) (this.presetEnumSettingHelper.getY() + this.num)) && d2 < ((double) ((this.presetEnumSettingHelper.getY() + get1743()) + this.num));
    }

    public static void do655() {
        num2 = 0L;
        arrayListPresetHelper2 = null;
        flag2 = false;
    }

    @Override // me.mioclient.PresetHelper_2
    public boolean isClosed() {
        return this.val <= ((float) (get1743() + 1)) && !this.flag;
    }

    public void do656() {
        this.zoomHelper.do171(0.0f);
        this.zoomHelper.do169(1.0f, 150L);
        this.flashNum = 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.module, ((ArrayListPresetHelper2) obj).module);
    }

    public int hashCode() {
        return Objects.hashCode(this.module);
    }
}
