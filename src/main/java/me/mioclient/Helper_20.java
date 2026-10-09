package me.mioclient;

import java.awt.Color;
import java.util.Objects;
import me.mioclient.module.client.UI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/Helper_20.class */
public class Helper_20 {
    public final float val2 = (float) MathHelper.clamp(Math.random(), 0.07000000029802322, 0.18000000715255737);
    public final float val3 = PingSpoofHelper.get370((float) FreecamHelper.val2, 1.149999976158142f);
    public final double val4 = Math.random();
    public final double val5 = PingSpoofHelper.get370(-0.032999999821186066f, 0.032999999821186066f);
    public float val6 = PingSpoofHelper.get370(-0.44999998807907104f, 0.0f);
    public static final float val = 0.032999999821186066f;
    public static final String string = "❆";

    public void do364(DrawContext drawContext) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        float scaledWindowWidth = (float) ((this.val4 + (this.val6 * this.val5)) * drawContext.getScaledWindowWidth());
        float scaledWindowHeight = this.val6 * drawContext.getScaledWindowHeight() * 0.5f;
        Objects.requireNonNull(textRenderer);
        float intBitsToFloat = (scaledWindowHeight - 9.0f) - 1.0f;
        this.val6 += BaritoneHelper_3.hitmarkerSearchHelper4.get3095(0.5f) * this.val2;
        this.val6 = Math.min(this.val6, 1.0f);
        if (!UI.uI.snow.getValue().booleanValue() || this.val6 < 0.0f) {
            return;
        }
        int ceil = MathHelper.ceil(Math.max(255.0f - (this.val6 * 255.0f), 4.0f));
        drawContext.getMatrices().push();
        drawContext.getMatrices().scale(this.val3, this.val3, 1.0f);
        drawContext.getMatrices().translate(scaledWindowWidth / this.val3, intBitsToFloat / this.val3, 0.0f);
        drawContext.drawCenteredTextWithShadow(textRenderer, "❆", 0, 0, new Color(255, 255, 255, ceil).hashCode());
        drawContext.getMatrices().pop();
    }

    public double get515() {
        return this.val4;
    }

    public boolean is2378() {
        return this.val6 >= 1.0f;
    }
}
