package me.mioclient;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Font;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AdvanceGlyph.class */
public class AdvanceGlyph implements Glyph {
    public static RenderLayer renderLayer;
    public final TextHandler textHandler = new TextHandler(this);
    public final FontHelper fontHelper;
    public float val;
    public float val2;
    public static final TrajectoriesVertexConsumer trajectoriesVertexConsumer = TrajectoriesVertexConsumer.getTrajectoriesVertexConsumer2592();
    public static final TrajectoriesVertexConsumer trajectoriesVertexConsumer2 = TrajectoriesVertexConsumer.getTrajectoriesVertexConsumer2592();
    public static final VertexConsumerProvider vertexConsumerProvider = renderLayer2 -> {
        if (renderLayer != null && renderLayer != renderLayer2) {
            CrosshairHelper.do1597();
        }
        if (!trajectoriesVertexConsumer2.is1662()) {
            trajectoriesVertexConsumer2.getBufferBuilder2595(renderLayer2.getDrawMode(), renderLayer2.getVertexFormat());
        }
        renderLayer = renderLayer2;
        return trajectoriesVertexConsumer2;
    };
    public static final int[] intArr = new int[32];

    public AdvanceGlyph(FontHelper fontHelper) {
        this.fontHelper = fontHelper;
    }

    public static AdvanceGlyph getAdvanceGlyph2758(String str, int i, Mode_8 mode_8) {
        FontHelper fontHelper;
        char[] cArr = new char[1501];
        for (int i2 = 0; i2 < cArr.length - 1; i2++) {
            cArr[i2] = (char) i2;
        }
        cArr[cArr.length - 1] = 8734;
        switch (mode_8) {
            case PLAIN:
                fontHelper = new FontHelper(new Font(str, 0, i), true, true);
                break;
            case BOLD:
                fontHelper = new FontHelper(new Font(str, 1, i), true, true);
                break;
            case ITALIC:
                fontHelper = new FontHelper(new Font(str, 2, i), true, true);
                break;
            case BOLDITALIC:
                fontHelper = new FontHelper(new Font(str, 3, i), true, true);
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        FontHelper fontHelper2 = fontHelper;
        fontHelper2.do1778(cArr);
        fontHelper2.do1779();
        return new AdvanceGlyph(fontHelper2);
    }

    public int get2759(MatrixStack matrixStack, String str, float f, float f2, int i) {
        return get2766(matrixStack, str, f, f2, i, true);
    }

    public int get2760(MatrixStack matrixStack, String str, double d, double d2, int i) {
        return get2766(matrixStack, str, (float) d, (float) d2, i, true);
    }

    public int get2761(MatrixStack matrixStack, String str, float f, float f2, int i) {
        return get2766(matrixStack, str, f, f2, i, false);
    }

    public int get2762(MatrixStack matrixStack, String str, double d, double d2, int i) {
        return get2766(matrixStack, str, (float) d, (float) d2, i, false);
    }

    public int get2763(MatrixStack matrixStack, String str, double d, double d2, int i) {
        return get2766(matrixStack, str, ((float) d) - (get2772(str) / 2.0f), (float) d2, i, false);
    }

    public int get2764(MatrixStack matrixStack, String str, double d, double d2, int i) {
        return get2766(matrixStack, str, ((float) d) - (get2772(str) / 2.0f), (float) d2, i, true);
    }

    public int get2765(MatrixStack matrixStack, OrderedText orderedText, float f, float f2, int i, boolean z) {
        return z ? Math.max(new MatrixStackCharacterVisitor(matrixStack, i, 0.25f, this.fontHelper).get859(orderedText, f + FontsSearchHelper4.get1698(), f2 + FontsSearchHelper4.get1698()), new MatrixStackCharacterVisitor(matrixStack, i, 1.0f, this.fontHelper).get859(orderedText, f, f2)) : new MatrixStackCharacterVisitor(matrixStack, i, 1.0f, this.fontHelper).get859(orderedText, f, f2);
    }

    public int get2766(MatrixStack matrixStack, String str, float f, float f2, int i, boolean z) {
        return z ? Math.max(get2767(matrixStack, str, f + FontsSearchHelper4.get1698(), f2 + FontsSearchHelper4.get1698(), i, true), get2767(matrixStack, str, f, f2, i, false)) : get2767(matrixStack, str, f, f2, i, false);
    }

    public int get2767(MatrixStack matrixStack, String str, float f, float f2, int i, boolean z) {
        if (str == null) {
            return 0;
        }
        if ((i & (-67108864)) == 0) {
            i |= -16777216;
        }
        if (z) {
            i = ((i & 16579836) >> 2) | (i & (-16777216));
        }
        this.val = f * 2.0f;
        this.val2 = f2 * 2.0f;
        do2768(matrixStack, str, z, i);
        return (int) (this.val / 4.0f);
    }

    public void do2768(MatrixStack matrixStack, String str, boolean z, int i) {
        float intBitsToFloat = ((i >> 24) & 255) / 255.0f;
        float intBitsToFloat2 = ((i >> 16) & 255) / 255.0f;
        float intBitsToFloat3 = ((i >> 8) & 255) / 255.0f;
        float intBitsToFloat4 = (i & 255) / 255.0f;
        matrixStack.push();
        matrixStack.translate(FontsSearchHelper4.get1699(), FontsSearchHelper4.get1697(), 0.0f);
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        if (!trajectoriesVertexConsumer.is1662()) {
            trajectoriesVertexConsumer.getBufferBuilder2595(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        }
        int i2 = 0;
        while (i2 < str.length()) {
            char charAt = str.charAt(i2);
            if (charAt != 167 || i2 + 1 >= str.length()) {
                this.fontHelper.do1780();
                do2769(this.fontHelper.get1782(matrixStack, trajectoriesVertexConsumer, charAt, this.val, this.val2, intBitsToFloat2, intBitsToFloat4, intBitsToFloat3, intBitsToFloat), this.fontHelper);
            } else {
                int indexOf = "0123456789abcdefklmnor".indexOf(str.toLowerCase(Locale.ENGLISH).charAt(i2 + 1));
                if (indexOf < 16) {
                    if (indexOf < 0) {
                        indexOf = 15;
                    }
                    if (z) {
                        indexOf += 16;
                    }
                    int i3 = intArr[indexOf];
                    intBitsToFloat2 = ((i3 >> 16) & 255) / 255.0f;
                    intBitsToFloat3 = ((i3 >> 8) & 255) / 255.0f;
                    intBitsToFloat4 = (i3 & 255) / 255.0f;
                } else {
                    intBitsToFloat2 = ((i >> 16) & 255) / 255.0f;
                    intBitsToFloat3 = ((i >> 8) & 255) / 255.0f;
                    intBitsToFloat4 = (i & 255) / 255.0f;
                }
                i2++;
            }
            i2++;
        }
        matrixStack.pop();
    }

    public void do2769(float f, FontHelper fontHelper) {
        this.val += f;
    }

    public FontHelper getFontHelper2770() {
        return this.fontHelper;
    }

    public int get2771() {
        return this.fontHelper.get1784() / 2;
    }

    public int get2772(String str) {
        if (str == null) {
            return 0;
        }
        int i = 0;
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            if (str.charAt(i2) != 167 || i2 + 1 >= length) {
                i += (int) (getFontHelper2770().get1783(str.charAt(i2)) - 8.0f);
            } else {
                i2++;
            }
            i2++;
        }
        return (i / 2) + 1;
    }

    public float get2773(char c) {
        return (getFontHelper2770().get1783(c) - 8.0f) / 2.0f;
    }

    public void do1597() {
        if (trajectoriesVertexConsumer.is1662()) {
            FontHelper fontHelper2770 = getFontHelper2770();
            GlStateManager._enableBlend();
            GlStateManager._blendFunc(770, 771);
            fontHelper2770.do1780();
            RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
            RenderSystem.disableDepthTest();
            TrajectoriesVertexConsumer.do2598(trajectoriesVertexConsumer);
            RenderSystem.enableDepthTest();
            fontHelper2770.do1781();
        }
        do2774();
    }

    public static void do2774() {
        if (!trajectoriesVertexConsumer2.is1662() || renderLayer == null) {
            return;
        }
        renderLayer.startDrawing();
        RenderSystem.disableDepthTest();
        BufferRenderer.drawWithGlobalProgram(trajectoriesVertexConsumer2.getBuiltBuffer2597());
        RenderSystem.enableDepthTest();
        renderLayer.endDrawing();
        renderLayer = null;
    }

    public VertexConsumerProvider getVertexConsumerProvider2775() {
        return vertexConsumerProvider;
    }

    public TextHandler getTextHandler2776() {
        return this.textHandler;
    }

    public float getAdvance() {
        return 0.0f;
    }

    public GlyphRenderer bake(Function<RenderableGlyph, GlyphRenderer> function) {
        return null;
    }

    static {
        for (int i = 0; i < 32; i++) {
            int i2 = ((i >> 3) & 1) * 85;
            int i3 = (((i >> 2) & 1) * 170) + i2;
            int i4 = (((i >> 1) & 1) * 170) + i2;
            int i5 = ((i & 1) * 170) + i2;
            if (i == 6) {
                i3 += 85;
            }
            if (i >= 16) {
                i3 /= 4;
                i4 /= 4;
                i5 /= 4;
            }
            intArr[i] = ((i3 & 255) << 16) | ((i4 & 255) << 8) | (i5 & 255);
        }
    }
}
