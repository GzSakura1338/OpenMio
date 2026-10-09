package me.mioclient;

import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/MatrixStackCharacterVisitor.class */
public class MatrixStackCharacterVisitor implements CharacterVisitor {
    public final float val;
    public final FontHelper fontHelper;
    public final int num;
    public final MatrixStack matrixStack;
    public float val2;
    public float val3;

    public MatrixStackCharacterVisitor(MatrixStack matrixStack, int i, float f, FontHelper fontHelper) {
        this.num = i;
        this.val = f;
        this.fontHelper = fontHelper;
        this.matrixStack = matrixStack;
    }

    public int get859(OrderedText orderedText, float f, float f2) {
        if (orderedText == null) {
            return 0;
        }
        this.val2 = f * 2.0f;
        this.val3 = f2 * 2.0f;
        do860(this.matrixStack, orderedText);
        return (int) (this.val2 / 4.0f);
    }

    public void do860(MatrixStack matrixStack, OrderedText orderedText) {
        matrixStack.push();
        matrixStack.translate(FontsSearchHelper4.get1699(), FontsSearchHelper4.get1697(), 0.0f);
        matrixStack.scale(0.5f, 0.5f, 1.0f);
        if (!AdvanceGlyph.trajectoriesVertexConsumer.is1662()) {
            AdvanceGlyph.trajectoriesVertexConsumer.getBufferBuilder2595(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        }
        orderedText.accept(this);
        matrixStack.pop();
    }

    public boolean accept(int i, Style style, int i2) {
        float intBitsToFloat;
        float intBitsToFloat2;
        float intBitsToFloat3;
        TextColor color = style.getColor();
        float intBitsToFloat4 = ((this.num >> 24) & 255) / 255.0f;
        if (color != null) {
            int rgb = color.getRgb();
            intBitsToFloat = ((rgb >> 16) & 255) / 255.0f;
            intBitsToFloat2 = ((rgb >> 8) & 255) / 255.0f;
            intBitsToFloat3 = (rgb & 255) / 255.0f;
        } else {
            intBitsToFloat = ((this.num >> 16) & 255) / 255.0f;
            intBitsToFloat2 = ((this.num >> 8) & 255) / 255.0f;
            intBitsToFloat3 = (this.num & 255) / 255.0f;
        }
        float f = intBitsToFloat * this.val;
        float f2 = intBitsToFloat2 * this.val;
        float f3 = intBitsToFloat3 * this.val;
        this.fontHelper.do1780();
        this.val2 += this.fontHelper.get1782(this.matrixStack, AdvanceGlyph.trajectoriesVertexConsumer, Character.toString(i2).charAt(0), this.val2, this.val3, f, f3, f2, intBitsToFloat4);
        return true;
    }
}
