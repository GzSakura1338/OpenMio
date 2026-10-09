package me.mioclient;

import com.mojang.blaze3d.systems.RenderSystem;
import me.mioclient.module.client.HUD;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SearchHelper4_10.class */
public class SearchHelper4_10 implements SearchHelper_4, PresetHelper_5 {
    public static HUD hud = (HUD) BaritoneHelper_3.baritoneHelper_4.getModule117(HUD.class);
    public final ArrayList<ModuleListSearchHelper4> arrayList = new ArrayList<>();
    public final ModuleListMode moduleListMode;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/SearchHelper4_10$ArrayList.class */
    public static final class ArrayList<T> extends java.util.ArrayList<T> {
        public T getObject1989() {
            return get(size() - 1);
        }
    }

    public SearchHelper4_10(ModuleListMode moduleListMode) {
        this.moduleListMode = moduleListMode;
    }

    @Override // me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        for (int i = 0; i < this.arrayList.size(); i++) {
            ModuleListSearchHelper4 moduleListSearchHelper4 = this.arrayList.get(i);
            moduleListSearchHelper4.do2944();
            boolean z = moduleListSearchHelper4.is2349() || moduleListSearchHelper4.get2142() > 0.0f;
            float[] floatArray365 = moduleListSearchHelper4.getFloatArray365();
            if (!z) {
                floatArray365 = new float[]{0.0f, 0.0f};
            }
            moduleListSearchHelper4.do2951(floatArray365);
            if (this.moduleListMode != ModuleListMode.NONE) {
                moduleListSearchHelper4.do2946(get2821(floatArray365[0], this.moduleListMode), true);
                if (i == 0) {
                    moduleListSearchHelper4.do2948(get2822(floatArray365[1], this.moduleListMode), true);
                } else if (this.moduleListMode == ModuleListMode.BOTTOM_LEFT || this.moduleListMode == ModuleListMode.BOTTOM_RIGHT) {
                    moduleListSearchHelper4.do2948(this.arrayList.get(i - 1).get124() - floatArray365[1], true);
                }
            }
            if (z) {
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, moduleListSearchHelper4.get2142());
                moduleListSearchHelper4.do19(drawContext, matrixStack, d, d2);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
            if (i < this.arrayList.size() - 1 && this.moduleListMode != ModuleListMode.NONE && get2820(this.moduleListMode) == 1) {
                this.arrayList.get(i + 1).do2948(moduleListSearchHelper4.get124() + floatArray365[1], true);
            }
        }
    }

    public ModuleListMode getModuleListMode2818() {
        return this.moduleListMode;
    }

    public ArrayList<ModuleListSearchHelper4> getArrayList2819() {
        return this.arrayList;
    }

    public int get2820(ModuleListMode moduleListMode) {
        switch (moduleListMode) {
            case TOP_LEFT:
            case TOP_RIGHT:
            case TOP_CENTER:
            case NONE:
                return 1;
            case BOTTOM_LEFT:
            case BOTTOM_RIGHT:
                return -1;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public float get2821(float f, ModuleListMode moduleListMode) {
        switch (moduleListMode) {
            case TOP_LEFT:
            case BOTTOM_LEFT:
                return hud.get737();
            case TOP_RIGHT:
            case BOTTOM_RIGHT:
                return (minecraftClient.getWindow().getScaledWidth() - f) - hud.get737();
            case TOP_CENTER:
                return (minecraftClient.getWindow().getScaledWidth() / 2.0f) - (f / 2.0f);
            case NONE:
                throw new IllegalArgumentException("dumb");
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public float get2822(float f, ModuleListMode moduleListMode) {
        switch (moduleListMode) {
            case TOP_LEFT:
            case TOP_CENTER:
                return hud.get738();
            case TOP_RIGHT:
                return hud.get735() + hud.get738();
            case NONE:
                throw new IllegalArgumentException("dumb");
            case BOTTOM_LEFT:
            case BOTTOM_RIGHT:
                return (minecraftClient.getWindow().getScaledHeight() - f) - get2823();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public float get2823() {
        return MathHelper.clamp(hud.get738() + hud.get734(), 0.0f, 14.0f);
    }
}
