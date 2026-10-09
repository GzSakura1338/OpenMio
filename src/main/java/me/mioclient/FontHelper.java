package me.mioclient;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import javax.imageio.ImageIO;
import me.mioclient.module.client.Fonts;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.BufferUtils;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/FontHelper.class */
public class FontHelper {
    public static Fonts fonts = (Fonts) BaritoneHelper_3.baritoneHelper_4.getModule117(Fonts.class);
    public final Font font;
    public final boolean flag;
    public final boolean flag2;
    public int num;
    public BufferedImage bufferedImage;
    public AbstractTexture abstractTexture;
    public final HashMap<Character, Inner> hashMap = new HashMap<>();
    public int num2 = -1;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/FontHelper$Inner.class */
    static class Inner {
        public int x;
        public int y;
        public int num;
        public int num2;

        public Inner(int i, int i2, int i3, int i4) {
            this.x = i;
            this.y = i2;
            this.num = i3;
            this.num2 = i4;
        }

        public Inner() {
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int get1635() {
            return this.num;
        }

        public int get93() {
            return this.num2;
        }
    }

    public FontHelper(Font font, boolean z, boolean z2) {
        this.font = font;
        this.flag = z;
        this.flag2 = z2;
    }

    public void do1() {
        this.abstractTexture.clearGlId();
        this.abstractTexture.close();
        this.hashMap.clear();
    }

    public void do1778(char[] cArr) {
        double longBitsToDouble = -1.0;
        double longBitsToDouble2 = -1.0;
        FontRenderContext fontRenderContext = new FontRenderContext(new AffineTransform(), this.flag, this.flag2);
        for (char c : cArr) {
            Rectangle2D stringBounds = this.font.getStringBounds(Character.toString(c), fontRenderContext);
            if (longBitsToDouble < stringBounds.getWidth()) {
                longBitsToDouble = stringBounds.getWidth();
            }
            if (longBitsToDouble2 < stringBounds.getHeight()) {
                longBitsToDouble2 = stringBounds.getHeight();
            }
        }
        double longBitsToDouble3 = longBitsToDouble + 2.0;
        double longBitsToDouble4 = longBitsToDouble2 + 2.0;
        this.num = ((int) Math.ceil(Math.max(Math.ceil(Math.sqrt((longBitsToDouble3 * longBitsToDouble3) * cArr.length) / longBitsToDouble3), Math.ceil(Math.sqrt((longBitsToDouble4 * longBitsToDouble4) * cArr.length) / longBitsToDouble4)) * Math.max(longBitsToDouble3, longBitsToDouble4))) + 1;
        this.bufferedImage = new BufferedImage(this.num, this.num, 2);
        Graphics2D createGraphics = this.bufferedImage.createGraphics();
        createGraphics.setFont(this.font);
        createGraphics.setColor(new Color(255, 255, 255, 0));
        createGraphics.fillRect(0, 0, this.num, this.num);
        createGraphics.setColor(Color.white);
        if (fonts.antiAlias.getValue().booleanValue() && fonts.isToggled()) {
            createGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            createGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            createGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        } else {
            createGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.flag2 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            createGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.flag ? RenderingHints.VALUE_ANTIALIAS_OFF : RenderingHints.VALUE_ANTIALIAS_ON);
            createGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.flag ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
        FontMetrics fontMetrics = createGraphics.getFontMetrics();
        int i = 0;
        int i2 = 0;
        int i3 = 1;
        for (char c2 : cArr) {
            Inner inner = new Inner();
            Rectangle2D stringBounds2 = fontMetrics.getStringBounds(Character.toString(c2), createGraphics);
            inner.num = stringBounds2.getBounds().width + 8;
            inner.num2 = stringBounds2.getBounds().height;
            if (i2 + inner.num >= this.num) {
                i2 = 0;
                i3 += i;
                i = 0;
            }
            inner.x = i2;
            inner.y = i3;
            if (inner.num2 > this.num2) {
                this.num2 = inner.num2;
            }
            if (inner.num2 > i) {
                i = inner.num2;
            }
            createGraphics.drawString(Character.toString(c2), i2 + 2, i3 + fontMetrics.getAscent());
            i2 += inner.num;
            this.hashMap.put(Character.valueOf(c2), inner);
        }
        this.bufferedImage.flush();
        createGraphics.dispose();
    }

    public void do1779() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(this.bufferedImage, "png", byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            ByteBuffer put = BufferUtils.createByteBuffer(byteArray.length).put(byteArray);
            put.flip();
            this.abstractTexture = new NativeImageBackedTexture(NativeImage.read(put));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void do1780() {
        GlStateManager._bindTexture(this.abstractTexture.getGlId());
        if (fonts.antiAlias.getValue().booleanValue()) {
            GlStateManager._texParameter(3553, 10240, 9729);
        } else {
            GlStateManager._texParameter(3553, 10240, 9728);
        }
        RenderSystem.setShaderTexture(0, this.abstractTexture.getGlId());
    }

    public void do1781() {
        RenderSystem.setShaderTexture(0, 0);
        if (fonts.antiAlias.getValue().booleanValue()) {
            GlStateManager._texParameter(3553, 10240, 9728);
        }
    }

    public float get1782(MatrixStack matrixStack, VertexConsumer vertexConsumer, char c, float f, float f2, float f3, float f4, float f5, float f6) {
        Inner inner = this.hashMap.get(Character.valueOf(c));
        if (inner == null) {
            return 0.0f;
        }
        float f7 = f3 * RenderSystem.getShaderColor()[0];
        float f8 = f5 * RenderSystem.getShaderColor()[1];
        float f9 = f4 * RenderSystem.getShaderColor()[2];
        float f10 = f6 * RenderSystem.getShaderColor()[3];
        float f11 = inner.x / (float) this.num;
        float f12 = inner.y / (float) this.num;
        float f13 = inner.num / (float) this.num;
        float f14 = inner.num2 / (float) this.num;
        float f15 = inner.num;
        float f16 = inner.num2;
        vertexConsumer.vertex(matrixStack.peek().getPositionMatrix(), f, f2 + f16, FontsSearchHelper4.get1701()).color(f7, f8, f9, f10).texture(f11, f12 + f14);
        vertexConsumer.vertex(matrixStack.peek().getPositionMatrix(), f + f15, f2 + f16, FontsSearchHelper4.get1701()).color(f7, f8, f9, f10).texture(f11 + f13, f12 + f14);
        vertexConsumer.vertex(matrixStack.peek().getPositionMatrix(), f + f15, f2, FontsSearchHelper4.get1701()).color(f7, f8, f9, f10).texture(f11 + f13, f12);
        vertexConsumer.vertex(matrixStack.peek().getPositionMatrix(), f, f2, FontsSearchHelper4.get1701()).color(f7, f8, f9, f10).texture(f11, f12);
        return f15 - 8.0f;
    }

    public float get1783(char c) {
        if (!this.hashMap.containsKey(Character.valueOf(c))) {
            c = ' ';
        }
        return this.hashMap.get(Character.valueOf(c)).num;
    }

    public int get1784() {
        return this.num2;
    }

    public boolean is1785() {
        return this.flag;
    }

    public boolean is1786() {
        return this.flag2;
    }
}
