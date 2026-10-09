package me.mioclient;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import me.mioclient.module.client.Fonts;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ModuleListSearchHelper4.class */
public abstract class ModuleListSearchHelper4 implements SearchHelper_4, PresetHelper_5 {
    public static final Color color = new Color(0, 0, 0, 120);
    public static boolean flag = false;
    public final ModuleList moduleList;
    public final ZoomHelper zoomHelper = new ZoomHelper();
    public final ZoomHelper zoomHelper2 = new ZoomHelper();
    public ModuleListMode moduleListMode = ModuleListMode.NONE;
    public float val = 0.5f;
    public float val2 = 0.5f;
    public int num;
    public int num2;
    public float[] floatArr;
    public boolean flag2;
    public long num3;

    public ModuleListSearchHelper4(ModuleList moduleList) {
        this.moduleList = moduleList;
    }

    @Override // me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        float[] floatArray2950 = getFloatArray2950();
        if (!is92(d, d2) || this.flag2) {
            this.num3 = -1L;
        } else {
            if (this.num3 == -1) {
                this.num3 = System.currentTimeMillis();
            }
            if (this.num3 + 300 <= System.currentTimeMillis()) {
                HUDSearchHelper4.helper_22.do2418(this.moduleList.getName(), get2955() == 1 ? get123() + floatArray2950[0] + 2.0f : (get123() - 2.0f) - FontsSearchHelper4.fontsSearchHelper4.get1316(this.moduleList.getName()), get124(), get2955());
            }
        }
        if (flag) {
            return;
        }
        float f = get123();
        float f2 = get124();
        CrosshairHelper.do1708(matrixStack, f, f2, f + Math.max(floatArray2950[0], 4.0f), f2 + Math.max(floatArray2950[1], 4.0f), MixinMessageIndicatorHelper_2.get819(color, ((BaritoneHelper_3.getHUDSearchHelper42217().get189() * RenderSystem.getShaderColor()[3]) * color.getAlpha()) / 255.0f));
        float f3 = this.zoomHelper.get172();
        if (f3 > 0.0f) {
            CrosshairHelper.do1705(matrixStack, f - 1.0f, f2 - 1.0f, f + Math.max(floatArray2950[0], 4.0f), f2 + Math.max(floatArray2950[1], 4.0f), MixinMessageIndicatorHelper_2.getColor816(MixinMessageIndicatorHelper_2.getColor811(), (int) (f3 * 255.0f)));
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public boolean is92(double d, double d2) {
        float[] floatArray2950 = getFloatArray2950();
        return d >= ((double) get123()) && d <= ((double) (get123() + Math.max(floatArray2950[0], 4.0f))) && d2 >= ((double) get124()) && d2 <= ((double) (get124() + Math.max(floatArray2950[1], 4.0f)));
    }

    public void do2944() {
        this.zoomHelper2.do169(is2349() ? 1.0f : 0.0f, 250L);
    }

    public abstract float[] getFloatArray365();

    public float get123() {
        return Math.min(this.val * minecraftClient.getWindow().getScaledWidth(), minecraftClient.getWindow().getScaledWidth() - getFloatArray2950()[0]);
    }

    public float get2945() {
        return this.val;
    }

    public void do2946(float f, boolean z) {
        if (z) {
            this.val = f / minecraftClient.getWindow().getScaledWidth();
        } else {
            this.val = MathHelper.clamp(f, 0.0f, 1.0f);
        }
    }

    public float get124() {
        return Math.min(this.val2 * minecraftClient.getWindow().getScaledHeight(), minecraftClient.getWindow().getScaledHeight() - getFloatArray2950()[1]);
    }

    public float get2947() {
        return this.val2;
    }

    public void do2948(float f, boolean z) {
        if (z) {
            this.val2 = f / minecraftClient.getWindow().getScaledHeight();
        } else {
            this.val2 = MathHelper.clamp(f, 0.0f, 1.0f);
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public int get93() {
        int i = FontsSearchHelper4.fontsSearchHelper4.get93();
        if (Fonts.fonts.isToggled()) {
            i--;
        }
        return i;
    }

    public void do2949(boolean z) {
        if (this.flag2 == z) {
            return;
        }
        this.zoomHelper.do169(z ? 1.0f : 0.0f, 250L);
        this.flag2 = z;
    }

    public boolean is1974() {
        return this.flag2;
    }

    public float[] getFloatArray2950() {
        return this.floatArr;
    }

    public void do2951(float[] fArr) {
        this.floatArr = fArr;
    }

    public ModuleListMode getModuleListMode2818() {
        return this.moduleListMode;
    }

    public void do2952(ModuleListMode moduleListMode) {
        this.moduleListMode = moduleListMode;
    }

    public boolean is2349() {
        return this.moduleList.isToggled();
    }

    public float get2953(float f) {
        return this.moduleListMode == ModuleListMode.TOP_CENTER ? (get123() + (this.floatArr[0] / 2.0f)) - (f / 2.0f) : get2955() == -1 ? (get123() + this.floatArr[0]) - f : get123();
    }

    public float get2954(float f) {
        return get2956() == -1 ? (get124() + this.floatArr[1]) - f : get124();
    }

    public int get2955() {
        if (this.num == 0) {
            do2957();
        }
        return this.num;
    }

    public int get2956() {
        if (this.num2 == 0) {
            do2957();
        }
        return this.num2;
    }

    public void do2957() {
        this.num = get123() + (this.floatArr[0] / 2.0f) > ((float) minecraftClient.getWindow().getScaledWidth()) / 2.0f ? -1 : 1;
        this.num2 = get124() + (this.floatArr[1] / 2.0f) > ((float) minecraftClient.getWindow().getScaledHeight()) / 2.0f ? -1 : 1;
    }

    public Data_3 getData_32936() {
        return Data_3.getData_31612(this.val * minecraftClient.getWindow().getScaledWidth(), this.val2 * minecraftClient.getWindow().getScaledHeight(), this.floatArr[0], this.floatArr[1]);
    }

    public ModuleList getModuleList2958() {
        return this.moduleList;
    }

    public float get2142() {
        return this.zoomHelper2.get172();
    }
}
