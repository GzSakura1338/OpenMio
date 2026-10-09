package me.mioclient;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import me.mioclient.module.render.ViewModel;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/Data_2.class */
public final class Data_2 {
    public final float val;
    public final float val2;
    public final float val3;
    public final float val4;
    public final float val5;
    public final float val6;
    public final float val7;
    public final float val8;
    public final float val9;
    public final float val10;
    public final float val11;
    public final float val12;
    public final float val13;
    public final float val14;
    public final float val15;
    public final float val16;
    public final float val17;
    public final float val18;

    public Data_2(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17, float f18) {
        this.val = f;
        this.val2 = f2;
        this.val3 = f3;
        this.val4 = f4;
        this.val5 = f5;
        this.val6 = f6;
        this.val7 = f7;
        this.val8 = f8;
        this.val9 = f9;
        this.val10 = f10;
        this.val11 = f11;
        this.val12 = f12;
        this.val13 = f13;
        this.val14 = f14;
        this.val15 = f15;
        this.val16 = f16;
        this.val17 = f17;
        this.val18 = f18;
    }

    public static Data_2 getData_21364(ViewModel viewModel) {
        return new Data_2(viewModel.mainX.getValue().floatValue(), viewModel.mainY.getValue().floatValue(), viewModel.mainZ.getValue().floatValue(), viewModel.offX.getValue().floatValue(), viewModel.offY.getValue().floatValue(), viewModel.offZ.getValue().floatValue(), viewModel.mainScaleX.getValue().floatValue(), viewModel.mainScaleY.getValue().floatValue(), viewModel.mainScaleZ.getValue().floatValue(), viewModel.offScaleX.getValue().floatValue(), viewModel.offScaleY.getValue().floatValue(), viewModel.offScaleZ.getValue().floatValue(), viewModel.mainRotateX.getValue().floatValue(), viewModel.mainRotateY.getValue().floatValue(), viewModel.mainRotateZ.getValue().floatValue(), viewModel.offRotateX.getValue().floatValue(), viewModel.offRotateY.getValue().floatValue(), viewModel.offRotateZ.getValue().floatValue());
    }

    public static Data_2 getData_21365() {
        return new Data_2(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void do1366(ViewModel viewModel) {
        viewModel.mainX.do2333(Float.valueOf(this.val));
        viewModel.mainY.do2333(Float.valueOf(this.val2));
        viewModel.mainZ.do2333(Float.valueOf(this.val3));
        viewModel.offX.do2333(Float.valueOf(this.val4));
        viewModel.offY.do2333(Float.valueOf(this.val5));
        viewModel.offZ.do2333(Float.valueOf(this.val6));
        viewModel.mainScaleX.do2333(Float.valueOf(this.val7));
        viewModel.mainScaleY.do2333(Float.valueOf(this.val8));
        viewModel.mainScaleZ.do2333(Float.valueOf(this.val8));
        viewModel.offScaleX.do2333(Float.valueOf(this.val10));
        viewModel.offScaleY.do2333(Float.valueOf(this.val11));
        viewModel.offScaleZ.do2333(Float.valueOf(this.val11));
        viewModel.mainRotateX.do2333(Float.valueOf(this.val13));
        viewModel.mainRotateY.do2333(Float.valueOf(this.val14));
        viewModel.mainRotateZ.do2333(Float.valueOf(this.val15));
        viewModel.offRotateX.do2333(Float.valueOf(this.val16));
        viewModel.offRotateY.do2333(Float.valueOf(this.val17));
        viewModel.offRotateZ.do2333(Float.valueOf(this.val18));
    }




    public float get1367() {
        return this.val;
    }

    public float get1368() {
        return this.val2;
    }

    public float get1369() {
        return this.val3;
    }

    public float get1370() {
        return this.val4;
    }

    public float get1371() {
        return this.val5;
    }

    public float get1372() {
        return this.val6;
    }

    public float get1373() {
        return this.val7;
    }

    public float get1374() {
        return this.val8;
    }

    public float get1375() {
        return this.val9;
    }

    public float get1376() {
        return this.val10;
    }

    public float get1377() {
        return this.val11;
    }

    public float get1378() {
        return this.val12;
    }

    public float get1379() {
        return this.val13;
    }

    public float get1380() {
        return this.val14;
    }

    public float get1381() {
        return this.val15;
    }

    public float get1382() {
        return this.val16;
    }

    public float get1383() {
        return this.val17;
    }

    public float get1384() {
        return this.val18;
    }
}
