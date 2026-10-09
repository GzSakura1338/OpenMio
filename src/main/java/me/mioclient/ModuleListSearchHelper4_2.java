package me.mioclient;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ModuleListSearchHelper4_2.class */
public class ModuleListSearchHelper4_2 extends ModuleListSearchHelper4 {
    public final List<? extends CryptoHelper> list;

    public ModuleListSearchHelper4_2(ModuleList moduleList, CryptoHelper cryptoHelper) {
        this(moduleList, (List<? extends CryptoHelper>) Collections.singletonList(cryptoHelper));
    }

    public ModuleListSearchHelper4_2(ModuleList moduleList, List<? extends CryptoHelper> list) {
        super(moduleList);
        this.list = list;
    }

    @Override // me.mioclient.ModuleListSearchHelper4, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        super.do19(drawContext, matrixStack, d, d2);
        float f = get2954(get93());
        for (CryptoHelper cryptoHelper : this.list) {
            matrixStack.push();
            float clamp = (float) MathHelper.clamp(1.0 - Math.pow(1.0f - cryptoHelper.get2601(), 4.0), 0.0d, 1.0);
            Color color489 = getColor489(f, cryptoHelper);
            float f2 = cryptoHelper.hUDHelper_2.get172();
            matrixStack.translate(get2953(f2) - ((f2 * (1.0f - clamp)) * get2955()), f, 0.0f);
            cryptoHelper.do2600(drawContext, 0.0f, 0.0f, color489);
            if (clamp != 0.0f) {
                f += ((get93() * clamp) + 1.0f) * get2956();
            }
            matrixStack.pop();
        }
    }

    @Override // me.mioclient.ModuleListSearchHelper4
    public float[] getFloatArray365() {
        float f = 0.0f;
        float f2 = 0.0f;
        for (CryptoHelper cryptoHelper : this.list) {
            cryptoHelper.do466();
            if (cryptoHelper.get2601() != 0.0f) {
                float clamp = (float) MathHelper.clamp(1.0 - Math.pow(1.0f - cryptoHelper.get2601(), 4.0), 0.0d, 1.0);
                float f3 = FontsSearchHelper4.fontsSearchHelper4.get1316(cryptoHelper.getText1879().getString());
                f += (get93() * clamp) + 1.0f;
                if (f3 > f2) {
                    f2 = f3;
                }
            }
        }
        if (!flag && f2 == 0.0f) {
            f2 = 50.0f;
            f = get93();
        }
        return new float[]{f2, f};
    }

    public List<? extends CryptoHelper> getList1218() {
        return this.list;
    }

    public Color getColor489(float f, CryptoHelper cryptoHelper) {
        return this.moduleList.getColor3018(f);
    }
}
