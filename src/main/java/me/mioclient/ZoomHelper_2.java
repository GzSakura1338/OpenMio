package me.mioclient;

import java.awt.Color;
import me.mioclient.module.client.HUD;
import net.minecraft.client.gui.DrawContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ZoomHelper_2.class */
public final class ZoomHelper_2 implements SearchHelper_4 {
    public static HUD hud = (HUD) BaritoneHelper_3.baritoneHelper_4.getModule117(HUD.class);
    public boolean flag;
    public boolean flag2;
    public boolean flag3;
    public boolean flag4;
    public final ZoomHelper zoomHelper = new ZoomHelper();
    public final ZoomHelper zoomHelper2 = new ZoomHelper();

    public void do1010(DrawContext drawContext, int i, int i2, float f) {
        int scaledWindowWidth = drawContext.getScaledWindowWidth();
        int scaledWindowHeight = drawContext.getScaledWindowHeight();
        boolean z = is1011((double) i) || this.flag3;
        boolean z2 = is1012((double) i2) || this.flag4;
        this.zoomHelper.do169(z ? 140.0f : 0.0f, 250L);
        this.zoomHelper2.do169((z2 && this.zoomHelper.get172() == 0.0f) ? 140.0f : 0.0f, 250L);
        if (this.zoomHelper.get172() > 0.0f) {
            if (this.flag3) {
                hud.do739(this.flag ? Math.abs(i - scaledWindowWidth) : i);
            }
            int i3 = MixinMessageIndicatorHelper_2.get818(Color.black, (int) this.zoomHelper.get172());
            CrosshairHelper.do1708(drawContext.getMatrices(), 0.0f, 0.0f, hud.get737(), scaledWindowHeight, i3);
            CrosshairHelper.do1708(drawContext.getMatrices(), scaledWindowWidth - hud.get737(), 0.0f, scaledWindowWidth, scaledWindowHeight, i3);
            return;
        }
        if (this.zoomHelper2.get172() > 0.0f) {
            if (this.flag4) {
                hud.do740(this.flag2 ? Math.abs(i2 - scaledWindowHeight) : i2);
            }
            int i4 = MixinMessageIndicatorHelper_2.get818(Color.black, (int) this.zoomHelper2.get172());
            CrosshairHelper.do1708(drawContext.getMatrices(), 0.0f, 0.0f, scaledWindowWidth, hud.get738(), i4);
            CrosshairHelper.do1708(drawContext.getMatrices(), 0.0f, scaledWindowHeight - hud.get738(), scaledWindowWidth, scaledWindowHeight, i4);
        }
    }

    public void do20(double d, double d2, int i) {
        if (i != 0) {
            return;
        }
        this.flag3 = is1011(d);
        if (!this.flag3) {
            this.flag4 = is1012(d2);
        }
        this.flag = d > ((double) (((float) minecraftClient.getWindow().getScaledWidth()) / 2.0f));
        this.flag2 = d2 > ((double) (((float) minecraftClient.getWindow().getScaledHeight()) / 2.0f));
    }

    public void do88(double d, double d2, int i) {
        if (i != 0) {
            return;
        }
        this.flag3 = false;
        this.flag4 = false;
    }

    public boolean is1011(double d) {
        return d < ((double) (hud.get737() + 1)) || d > ((double) ((minecraftClient.getWindow().getScaledWidth() - hud.get737()) - 1));
    }

    public boolean is1012(double d) {
        return d < ((double) (hud.get738() + 1)) || d > ((double) ((minecraftClient.getWindow().getScaledHeight() - hud.get738()) - 1));
    }
}
