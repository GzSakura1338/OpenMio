package me.mioclient;

import java.awt.Color;
import java.util.function.Supplier;
import me.mioclient.module.client.HUD;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/CryptoHelper.class */
public class CryptoHelper {
    public static HUD hud = (HUD) BaritoneHelper_3.baritoneHelper_4.getModule117(HUD.class);
    public final Supplier<Text> supplier;
    public final Supplier<Boolean> supplier2;
    public float val;
    public final HUDHelper_2 hUDHelper_2 = new HUDHelper_2(2.0f, true);

    public CryptoHelper(Supplier<Text> supplier, Supplier<Boolean> supplier2) {
        this.supplier = supplier;
        this.supplier2 = supplier2;
    }

    public void do2600(DrawContext drawContext, float f, float f2, Color color) {
        float f3 = FontsSearchHelper4.fontsSearchHelper4.get1316(getText1879().getString());
        if (hud.setting3.getValue().booleanValue()) {
            this.hUDHelper_2.do1737(f3);
        } else {
            this.hUDHelper_2.do171(f3);
        }
        if (this.val > 0.0f) {
            FontsSearchHelper4.fontsSearchHelper4.do1692(drawContext, getText1879(), f, f2, MixinMessageIndicatorHelper_2.getColor816(color, (int) (color.getAlpha() * MathHelper.clamp(this.val * 2.0f, 0.1, 1.0))));
        }
    }

    public void do466() {
        float intBitsToFloat = 1.0f / (BaritoneHelper_3.hitmarkerSearchHelper4.get3094() >> 2);
        if (isDrawn()) {
            this.val += intBitsToFloat;
        } else {
            this.val -= intBitsToFloat;
        }
        this.val = MathHelper.clamp(this.val, 0.0f, 1.0f);
    }

    public float get2601() {
        return this.val;
    }

    public boolean isDrawn() {
        return this.supplier2.get().booleanValue();
    }

    public Text getText1879() {
        return this.supplier.get();
    }
}
