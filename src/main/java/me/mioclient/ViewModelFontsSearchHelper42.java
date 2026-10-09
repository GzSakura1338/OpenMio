package me.mioclient;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import me.mioclient.api.Setting;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.render.ViewModel;
import net.minecraft.client.gui.DrawContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ViewModelFontsSearchHelper42.class */
public class ViewModelFontsSearchHelper42 extends FontsSearchHelper4_2 {
    public static final Color color = new Color(255, 255, 255, 100);
    public final ViewModel viewmodel;
    public final LinkedList<Data_2> linkedList = new LinkedList<>();
    public final Stopwatch stopwatch = new Stopwatch();
    public Mode_11 mode_11;
    public Data_2 data_2;
    public double val;
    public double val2;
    public boolean flag;

    public ViewModelFontsSearchHelper42(ViewModel viewModel) {
        this.viewmodel = viewModel;
        this.data_2 = Data_2.getData_21364(viewModel);
        this.linkedList.add(this.data_2);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void do940(DrawContext drawContext, int i, int i2, float f) {
        Data_2 data_2 = this.data_2;
        if (getMode_11943() == Mode_11.NONE) {
            return;
        }
        float f2 = ((float) PingSpoofHelper.get368((this.val - i) * 0.004999999888241291, 2)) * (this.flag ? -1 : 1);
        float f3 = (float) PingSpoofHelper.get368((this.val2 - i2) * 0.004999999888241291, 2);
        if (EntityControlSearchHelper4.is2608()) {
            if (Math.abs(f2) > Math.abs(f3)) {
                f3 = 0.0f;
            } else {
                f2 = 0.0f;
            }
        }
        if (getMode_11943() == Mode_11.TRANSLATE) {
            if (!EntityControlSearchHelper4.is2607()) {
                this.viewmodel.offX.do2333(Float.valueOf(data_2.get1370() + f2));
                this.viewmodel.offY.do2333(Float.valueOf(data_2.get1371() + f3));
                this.viewmodel.mainX.do2333(Float.valueOf(data_2.get1367() + f2));
                this.viewmodel.mainY.do2333(Float.valueOf(data_2.get1368() + f3));
                return;
            }
            if (this.flag) {
                this.viewmodel.offX.do2333(Float.valueOf(data_2.get1370() + f2));
                this.viewmodel.offY.do2333(Float.valueOf(data_2.get1371() + f3));
                return;
            } else {
                this.viewmodel.mainX.do2333(Float.valueOf(data_2.get1367() + f2));
                this.viewmodel.mainY.do2333(Float.valueOf(data_2.get1368() + f3));
                return;
            }
        }
        if (getMode_11943() == Mode_11.SCALE) {
            if (EntityControlSearchHelper4.is2608()) {
                f2 = Math.abs(Math.max(f2, f3)) * Math.signum(f2);
                f3 = Math.abs(Math.max(f2, f3)) * Math.signum(f3);
            }
            float f4 = -f2;
            if (!EntityControlSearchHelper4.is2607()) {
                this.viewmodel.offScaleX.do2333(Float.valueOf(data_2.get1376() + f4));
                this.viewmodel.offScaleY.do2333(Float.valueOf(data_2.get1377() + f3));
                this.viewmodel.mainScaleX.do2333(Float.valueOf(data_2.get1373() + f4));
                this.viewmodel.mainScaleY.do2333(Float.valueOf(data_2.get1374() + f3));
                return;
            }
            if (this.flag) {
                this.viewmodel.offScaleX.do2333(Float.valueOf(data_2.get1376() + f4));
                this.viewmodel.offScaleY.do2333(Float.valueOf(data_2.get1377() + f3));
                return;
            } else {
                this.viewmodel.mainScaleX.do2333(Float.valueOf(data_2.get1373() + f4));
                this.viewmodel.mainScaleY.do2333(Float.valueOf(data_2.get1374() + f3));
                return;
            }
        }
        if (getMode_11943() == Mode_11.ROTATE) {
            float intBitsToFloat = f2 / 0.004999999888241291f;
            float intBitsToFloat2 = f3 / 0.004999999888241291f;
            if (!EntityControlSearchHelper4.is2607()) {
                this.viewmodel.offRotateX.do2333(Float.valueOf(PingSpoofHelper.get381(data_2.get1382() + intBitsToFloat2)));
                this.viewmodel.offRotateY.do2333(Float.valueOf(PingSpoofHelper.get381(data_2.get1383() + intBitsToFloat)));
                this.viewmodel.mainRotateX.do2333(Float.valueOf(PingSpoofHelper.get381(data_2.get1379() + intBitsToFloat2)));
                this.viewmodel.mainRotateY.do2333(Float.valueOf(PingSpoofHelper.get381(data_2.get1380() + intBitsToFloat)));
                return;
            }
            if (this.flag) {
                this.viewmodel.offRotateX.do2333(Float.valueOf(PingSpoofHelper.get381(data_2.get1382() + intBitsToFloat2)));
                this.viewmodel.offRotateY.do2333(Float.valueOf(PingSpoofHelper.get381(data_2.get1383() + intBitsToFloat)));
            } else {
                this.viewmodel.mainRotateX.do2333(Float.valueOf(PingSpoofHelper.get381(data_2.get1379() + intBitsToFloat2)));
                this.viewmodel.mainRotateY.do2333(Float.valueOf(PingSpoofHelper.get381(data_2.get1380() + intBitsToFloat)));
            }
        }
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void do941(double d, double d2, int i) {
        do946(getMode_11944(i));
        if (getMode_11943() != Mode_11.NONE) {
            this.data_2 = Data_2.getData_21364(this.viewmodel);
            this.linkedList.add(this.data_2);
            this.flag = d * SearchHelper4.get1481((float) this.val) < ((double) (((float) minecraftClient.getWindow().getScaledWidth()) / 2.0f));
            this.val = d;
            this.val2 = d2;
        }
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void do942(double d, double d2, int i) {
        do946(Mode_11.NONE);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public boolean mouseScrolled(double d, double d2, double d3, double d4) {
        if (d4 > -1.0 && d4 < 1.0) {
            return super.mouseScrolled(d, d2, d3, d4);
        }
        boolean z = d * SearchHelper4.get1481((float) this.val) < ((double) (((float) minecraftClient.getWindow().getScaledWidth()) / 2.0f));
        float longBitsToDouble = (float) (d4 * -0.10000000149011612);
        if (this.stopwatch.is419(100L)) {
            this.linkedList.add(Data_2.getData_21364(this.viewmodel));
        }
        this.stopwatch.reset();
        if (!EntityControlSearchHelper4.is2608()) {
            if (EntityControlSearchHelper4.is2609()) {
                do945(this.viewmodel.mainScaleZ, this.viewmodel.offScaleZ, z, longBitsToDouble);
                return super.mouseScrolled(d, d2, d3, d4);
            }
            do945(this.viewmodel.mainZ, this.viewmodel.offZ, z, longBitsToDouble);
            return super.mouseScrolled(d, d2, d3, d4);
        }
        float f = PingSpoofHelper.get381(this.viewmodel.offRotateZ.getValue().floatValue() + (((float) d4) * 10.0f));
        float f2 = PingSpoofHelper.get381(this.viewmodel.mainRotateZ.getValue().floatValue() + (((float) d4) * 10.0f));
        if (!EntityControlSearchHelper4.is2607()) {
            this.viewmodel.offRotateZ.do2333(Float.valueOf(f));
            this.viewmodel.mainRotateZ.do2333(Float.valueOf(f2));
        } else if (z) {
            this.viewmodel.offRotateZ.do2333(Float.valueOf(f));
        } else {
            this.viewmodel.mainRotateZ.do2333(Float.valueOf(f2));
        }
        return super.mouseScrolled(d, d2, d3, d4);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public boolean keyPressed(int i, int i2, int i3) {
        if (EntityControlSearchHelper4.is2607() && i == 90 && !this.linkedList.isEmpty()) {
            this.linkedList.getLast().do1366(this.viewmodel);
            if (this.linkedList.size() != 1) {
                this.linkedList.removeLast();
            }
        }
        if (i == 261) {
            this.data_2 = Data_2.getData_21365();
            this.data_2.do1366(this.viewmodel);
            this.linkedList.add(this.data_2);
        }
        return super.keyPressed(i, i2, i3);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void do204(DrawContext drawContext) {
        float scaledWindowWidth = drawContext.getScaledWindowWidth();
        float scaledWindowHeight = drawContext.getScaledWindowHeight();
        SearchHelper_2.searchHelper_2.do546(drawContext.getMatrices(), (scaledWindowWidth / 2.0f) - 1.0f, 0.0f, (scaledWindowWidth / 2.0f) + 1.0f, 48.0f, color);
        int i = 2;
        for (String str : List.of("Left Click - Rotate", "Right Click - Move", "Middle Click - Scale", "Mouse Scroll - Move by Z", "Mouse Scroll + Shift - Rotate by Z", "Mouse Scroll + Alt - Scale by Z", "Hold Ctrl - Modify one", "Hold Shift - Lock axis", "Ctrl + Z - Undo", "Delete - Reset to defaults")) {
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, str, (scaledWindowWidth - FontsSearchHelper4.fontsSearchHelper4.get1316(str)) / 2.0f, 50 + i, MixinMessageIndicatorHelper_2.getColor816(Color.white, 180));
            i += FontsSearchHelper4.fontsSearchHelper4.get93() + 1;
        }
        SearchHelper_2.searchHelper_2.do546(drawContext.getMatrices(), (scaledWindowWidth / 2.0f) - 1.0f, i + 52, (scaledWindowWidth / 2.0f) + 1.0f, scaledWindowHeight, color);
    }

    public Mode_11 getMode_11943() {
        return this.mode_11;
    }

    public Mode_11 getMode_11944(int i) {
        switch (i) {
            case 0:
                return Mode_11.ROTATE;
            case 1:
                return Mode_11.TRANSLATE;
            case 2:
                return Mode_11.SCALE;
            default:
                return Mode_11.NONE;
        }
    }

    public void do945(Setting<Float> setting, Setting<Float> setting2, boolean z, float f) {
        if (!EntityControlSearchHelper4.is2607()) {
            setting.do2333(Float.valueOf(setting.getValue().floatValue() + f));
            setting2.do2333(Float.valueOf(setting2.getValue().floatValue() + f));
        } else if (z) {
            setting2.do2333(Float.valueOf(setting2.getValue().floatValue() + f));
        } else {
            setting.do2333(Float.valueOf(setting.getValue().floatValue() + f));
        }
    }

    public void do946(Mode_11 mode_11) {
        this.mode_11 = mode_11;
    }
}
