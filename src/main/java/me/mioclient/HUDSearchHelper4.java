package me.mioclient;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import me.mioclient.api.Category;
import me.mioclient.feature.Progress;
import me.mioclient.feature.Size;
import net.minecraft.client.gui.DrawContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HUDSearchHelper4.class */
public class HUDSearchHelper4 extends FontsSearchHelper4_2 implements SearchHelper_4 {
    public static final List<SearchHelper4_10> list = Collections.synchronizedList(new ArrayList());
    public static final Helper_22 helper_22 = new Helper_22();
    public final ZoomHelper_2 zoomHelper_2 = new ZoomHelper_2();
    public final Progress progress = new Progress(2.0f, true);
    public boolean flag;
    public float val;
    public float val2;
    public float val3;
    public float val4;

    public HUDSearchHelper4() {
        for (ModuleListMode moduleListMode : ModuleListMode.values()) {
            list.add(new SearchHelper4_10(moduleListMode));
        }
        CategorySearchHelper4 categorySearchHelper4 = new CategorySearchHelper4(Category.HUD);
        categorySearchHelper4.getArrayList1968().sort(Comparator.comparing(presetHelper_5 -> {
            return presetHelper_5 instanceof ArrayListPresetHelper2 ? ((ArrayListPresetHelper2) presetHelper_5).getModule595().getName() : "";
        }));
        categorySearchHelper4.get1635();
        categorySearchHelper4.setX(85);
        categorySearchHelper4.setY(10);
        this.arrayList.add(categorySearchHelper4);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void render(DrawContext drawContext, int i, int i2, float f) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, get189());
        helper_22.do2424(false);
        this.val3 = i;
        this.val4 = i2;
        float scaledWindowWidth = drawContext.getScaledWindowWidth() / 2.0f;
        float scaledWindowHeight = drawContext.getScaledWindowHeight() / 2.0f;
        Color color817 = MixinMessageIndicatorHelper_2.getColor817(Color.white, RenderSystem.getShaderColor()[3] * 0.4000000059604645f);
        CrosshairHelper.do1707(drawContext.getMatrices(), scaledWindowWidth - 0.5f, 0.0f, scaledWindowWidth + 0.5f, scaledWindowHeight * 2.0f, color817);
        CrosshairHelper.do1707(drawContext.getMatrices(), 0.0f, scaledWindowHeight - 0.5f, scaledWindowWidth - 0.5f, scaledWindowHeight + 0.5f, color817);
        CrosshairHelper.do1707(drawContext.getMatrices(), scaledWindowWidth + 0.5f, scaledWindowHeight - 0.5f, scaledWindowWidth * 2.0f, scaledWindowHeight + 0.5f, color817);
        this.zoomHelper_2.do1010(drawContext, i, i2, f);
        do199(drawContext, i, i2);
        if (this.flag) {
            ModuleListSearchHelper4 object1989 = getSearchHelper4_10200(ModuleListMode.NONE).getArrayList2819().getObject1989();
            float[] floatArray2950 = object1989.getFloatArray2950();
            object1989.do2946(Math.max(0.0f, Math.min(drawContext.getScaledWindowWidth() - floatArray2950[0], i - this.val)), true);
            object1989.do2948(Math.max(0.0f, Math.min(drawContext.getScaledWindowHeight() - floatArray2950[1], i2 - this.val2)), true);
            object1989.do2957();
            if (Math.abs((object1989.get123() + (floatArray2950[0] / 2.0f)) - scaledWindowWidth) <= 5.0f) {
                float intBitsToFloat = scaledWindowWidth - (floatArray2950[0] / 2.0f);
                object1989.do2946(intBitsToFloat, true);
                do203(drawContext, intBitsToFloat - 0.5, true);
                do203(drawContext, intBitsToFloat + floatArray2950[0], true);
            }
            if (Math.abs((object1989.get124() + (floatArray2950[1] / 2.0f)) - scaledWindowHeight) <= 5.0f) {
                float intBitsToFloat2 = scaledWindowHeight - (floatArray2950[1] / 2.0f);
                object1989.do2948(intBitsToFloat2, true);
                do203(drawContext, intBitsToFloat2 - 0.5, false);
                do203(drawContext, intBitsToFloat2 + floatArray2950[1], false);
            }
        }
        float f2 = this.progress.get172();
        this.progress.do2139(helper_22.is2423());
        if (helper_22.is2423() || f2 > 0.1) {
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, helper_22.getString2419(), helper_22.get123() + (10.0f * (1.0f - f2) * helper_22.get2421()), helper_22.get124(), MixinMessageIndicatorHelper_2.getColor816(Color.white, (int) (f2 * 255.0f)));
        }
        super.render(drawContext, i, i2, f);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public boolean mouseClicked(double d, double d2, int i) {
        HoleSnapData_2 holeSnapData_2 = null;
        for (SearchHelper4_10 searchHelper4_10 : list) {
            for (int size = searchHelper4_10.getArrayList2819().size() - 1; size >= 0; size--) {
                ModuleListSearchHelper4 moduleListSearchHelper4 = searchHelper4_10.getArrayList2819().get(size);
                if (moduleListSearchHelper4.is2349() && i == 0 && holeSnapData_2 == null && ((!(moduleListSearchHelper4 instanceof Size) || !((Size) moduleListSearchHelper4).is2638(d, d2)) && moduleListSearchHelper4.is92(d, d2))) {
                    holeSnapData_2 = new HoleSnapData_2(searchHelper4_10.getModuleListMode2818(), moduleListSearchHelper4);
                    this.val = (int) (d - moduleListSearchHelper4.get123());
                    this.val2 = (int) (d2 - moduleListSearchHelper4.get124());
                    this.flag = true;
                    moduleListSearchHelper4.do2949(true);
                }
            }
        }
        if (holeSnapData_2 != null) {
            getSearchHelper4_10200((ModuleListMode) holeSnapData_2.getObject3119()).getArrayList2819().remove(holeSnapData_2.getObject3120());
            getSearchHelper4_10200(ModuleListMode.NONE).getArrayList2819().addLast((ModuleListSearchHelper4) holeSnapData_2.getObject3120());
            ((ModuleListSearchHelper4) holeSnapData_2.getObject3120()).do2952(ModuleListMode.NONE);
        } else {
            this.zoomHelper_2.do20(d, d2, i);
        }
        return super.mouseClicked(d, d2, i);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public boolean mouseReleased(double d, double d2, int i) {
        this.zoomHelper_2.do88(d, d2, i);
        if (this.flag) {
            this.flag = false;
            SearchHelper4_10 searchHelper4_10200 = getSearchHelper4_10200(ModuleListMode.NONE);
            ModuleListSearchHelper4 object1989 = searchHelper4_10200.getArrayList2819().getObject1989();
            object1989.do2949(false);
            searchHelper4_10200.getArrayList2819().remove(object1989);
            for (SearchHelper4_10 searchHelper4_10 : list) {
                Iterator<ModuleListSearchHelper4> it = searchHelper4_10.getArrayList2819().iterator();
                while (it.hasNext()) {
                    if (it.next().getData_32936().is1615(object1989.getData_32936())) {
                        getSearchHelper4_10200(searchHelper4_10.getModuleListMode2818()).getArrayList2819().addLast(object1989);
                        object1989.do2952(searchHelper4_10.getModuleListMode2818());
                        return true;
                    }
                }
            }
            for (ModuleListMode moduleListMode : ModuleListMode.values()) {
                if (moduleListMode.getData_32936().is1615(object1989.getData_32936().getData_31616(5.0f))) {
                    getSearchHelper4_10200(moduleListMode).getArrayList2819().addLast(object1989);
                    object1989.do2952(moduleListMode);
                    return true;
                }
            }
        }
        return super.mouseReleased(d, d2, i);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public boolean keyPressed(int i, int i2, int i3) {
        boolean z = false;
        Iterator<PresetEnumSettingHelper> it = this.arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (it.next().is92(this.val3, this.val4)) {
                z = true;
                break;
            }
        }
        if (!z) {
            ModuleListSearchHelper4 moduleListSearchHelper4202 = getModuleListSearchHelper4202();
            if (getPositionData206(i) == null || moduleListSearchHelper4202 == null || !moduleListSearchHelper4202.is2349()) {
                switch (i) {
                    case 73:
                        if (!(BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().getSettingSearchHelper4192409() instanceof PresetSettingSearchHelper419_2)) {
                            do201(ModuleListMode.BOTTOM_LEFT, ModuleListMode.BOTTOM_RIGHT);
                            do201(ModuleListMode.TOP_LEFT, ModuleListMode.TOP_RIGHT);
                            break;
                        }
                        break;
                    case 259:
                    case 261:
                        if (moduleListSearchHelper4202 != null && moduleListSearchHelper4202.is2349()) {
                            moduleListSearchHelper4202.getModuleList2958().do495(false);
                            break;
                        }
                        break;
                }
            } else {
                PositionData positionData = getPositionData206(i);
                moduleListSearchHelper4202.do2946(moduleListSearchHelper4202.get123() + positionData.get476(), true);
                moduleListSearchHelper4202.do2948(moduleListSearchHelper4202.get124() + positionData.get1222(), true);
                moduleListSearchHelper4202.do2957();
            }
        }
        return super.keyPressed(i, i2, i3);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public boolean mouseScrolled(double d, double d2, double d3, double d4) {
        return super.mouseScrolled(d, d2, d3, d4);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void close() {
        super.close();
        if (this.flag) {
            getSearchHelper4_10200(ModuleListMode.NONE).getArrayList2819().getObject1989().do2949(false);
            this.flag = false;
        }
    }

    public void do195(ModuleListMode moduleListMode, ModuleListSearchHelper4 moduleListSearchHelper4) {
        getSearchHelper4_10200(moduleListMode).getArrayList2819().add(moduleListSearchHelper4);
    }

    public void do196(ModuleListSearchHelper4 moduleListSearchHelper4) {
        do195(ModuleListMode.NONE, moduleListSearchHelper4);
    }

    public void do197(ModuleListSearchHelper4 moduleListSearchHelper4) {
        Iterator<SearchHelper4_10> it = getList198().iterator();
        while (it.hasNext() && !it.next().getArrayList2819().remove(moduleListSearchHelper4)) {
        }
    }

    public List<SearchHelper4_10> getList198() {
        return list;
    }

    public void do199(DrawContext drawContext, double d, double d2) {
        Iterator<SearchHelper4_10> it = getList198().iterator();
        while (it.hasNext()) {
            it.next().do19(drawContext, drawContext.getMatrices(), d, d2);
        }
        CrosshairHelper.do1597();
    }

    public SearchHelper4_10 getSearchHelper4_10200(ModuleListMode moduleListMode) {
        for (SearchHelper4_10 searchHelper4_10 : getList198()) {
            if (searchHelper4_10.getModuleListMode2818() == moduleListMode) {
                return searchHelper4_10;
            }
        }
        return null;
    }

    public void do201(ModuleListMode moduleListMode, ModuleListMode moduleListMode2) {
        SearchHelper4_10 searchHelper4_10200 = getSearchHelper4_10200(moduleListMode);
        SearchHelper4_10 searchHelper4_102002 = getSearchHelper4_10200(moduleListMode2);
        ArrayList arrayList = new ArrayList(searchHelper4_10200.getArrayList2819());
        searchHelper4_10200.getArrayList2819().clear();
        Iterator<ModuleListSearchHelper4> it = searchHelper4_102002.getArrayList2819().iterator();
        while (it.hasNext()) {
            searchHelper4_10200.getArrayList2819().add(it.next());
        }
        searchHelper4_102002.getArrayList2819().clear();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            searchHelper4_102002.getArrayList2819().add((ModuleListSearchHelper4) it2.next());
        }
    }

    public ModuleListSearchHelper4 getModuleListSearchHelper4202() {
        Iterator<SearchHelper4_10> it = list.iterator();
        while (it.hasNext()) {
            Iterator<ModuleListSearchHelper4> it2 = it.next().getArrayList2819().iterator();
            while (it2.hasNext()) {
                ModuleListSearchHelper4 next = it2.next();
                if (next.is92(this.val3, this.val4)) {
                    return next;
                }
            }
        }
        return null;
    }

    public void do203(DrawContext drawContext, double d, boolean z) {
        float f = (float) d;
        if (z) {
            CrosshairHelper.do1707(drawContext.getMatrices(), f, 0.0f, f + 0.5f, drawContext.getScaledWindowHeight(), Color.yellow);
        } else {
            CrosshairHelper.do1707(drawContext.getMatrices(), 0.0f, f, drawContext.getScaledWindowWidth(), f + 0.5f, Color.yellow);
        }
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void do204(DrawContext drawContext) {
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public boolean is205() {
        return false;
    }

    public PositionData getPositionData206(int i) {
        switch (i) {
            case 262:
                return new PositionData(1, 0);
            case 263:
                return new PositionData(-1, 0);
            case 264:
                return new PositionData(0, 1);
            case 265:
                return new PositionData(0, -1);
            default:
                return null;
        }
    }
}
