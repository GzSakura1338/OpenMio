package me.mioclient;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.nio.ByteBuffer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4fStack;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ByteBufferSearchHelper4.class */
public class ByteBufferSearchHelper4 implements SearchHelper_4 {
    public boolean flag = false;
    public double val = 1.0;
    public final Mode_2 mode_2;
    public final int num;
    public final int num2;
    public final int num3;
    public final int num4;
    public ByteBuffer byteBuffer;
    public long num5;
    public long num6;
    public ByteBuffer byteBuffer2;
    public long num7;
    public int num8;
    public int num9;
    public boolean flag2;
    public boolean flag3;
    public double val2;
    public double val3;
    public boolean flag4;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/ByteBufferSearchHelper4$Mode.class */
    public enum Mode {
        Float(1, 4, false),
        Vec2(2, 4, false),
        Vec3(3, 4, false),
        Color(4, 1, true);

        public final int num;
        public final int num2;
        public final boolean flag;

        Mode(int i, int i2, boolean z) {
            this.num = i;
            this.num2 = i * i2;
            this.flag = z;
        }

        public int get2505() {
            return this == Color ? 5121 : 5126;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/ByteBufferSearchHelper4$Mode_2.class */
    public enum Mode_2 {
        Lines(2),
        Triangles(3);

        public final int num;

        Mode_2(int i) {
            this.num = i;
        }

        public int get3099() {
            switch (this) {
                case Lines:
                    return 1;
                case Triangles:
                    return 4;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
    }

    public ByteBufferSearchHelper4(Mode_2 mode_2, Mode... modeArr) {
        int i = 0;
        for (Mode mode : modeArr) {
            i += mode.num2;
        }
        this.mode_2 = mode_2;
        this.num = i * mode_2.num;
        this.byteBuffer = BufferUtils.createByteBuffer(this.num * 256 * 4);
        this.num5 = MemoryUtil.memAddress0(this.byteBuffer);
        this.byteBuffer2 = BufferUtils.createByteBuffer(mode_2.num * 512 * 4);
        this.num7 = MemoryUtil.memAddress0(this.byteBuffer2);
        this.num2 = FramebufferHelperSearchHelper4.get271();
        FramebufferHelperSearchHelper4.do281(this.num2);
        this.num3 = FramebufferHelperSearchHelper4.get272();
        FramebufferHelperSearchHelper4.do282(this.num3);
        this.num4 = FramebufferHelperSearchHelper4.get272();
        FramebufferHelperSearchHelper4.do283(this.num4);
        int i2 = 0;
        for (int i3 = 0; i3 < modeArr.length; i3++) {
            Mode mode2 = modeArr[i3];
            FramebufferHelperSearchHelper4.do287(i3);
            FramebufferHelperSearchHelper4.do288(i3, mode2.num, mode2.get2505(), mode2.flag, i, i2);
            i2 += mode2.num2;
        }
        FramebufferHelperSearchHelper4.do281(0);
        FramebufferHelperSearchHelper4.do282(0);
        FramebufferHelperSearchHelper4.do283(0);
    }

    public void do1650() {
        FramebufferHelperSearchHelper4.do275(this.num4);
        FramebufferHelperSearchHelper4.do275(this.num3);
        FramebufferHelperSearchHelper4.do276(this.num2);
    }

    public void do1651() {
        if (this.flag2) {
            throw new IllegalStateException("Mesh.end() called while already building.");
        }
        this.num6 = this.num5;
        this.num8 = 0;
        this.num9 = 0;
        this.flag2 = true;
        Vec3d pos = minecraftClient.gameRenderer.getCamera().getPos();
        this.val2 = pos.x;
        this.val3 = pos.z;
    }

    public ByteBufferSearchHelper4 getByteBufferSearchHelper41652(double d, double d2, double d3) {
        long j = this.num6;
        MemoryUtil.memPutFloat(j, (float) (d - this.val2));
        MemoryUtil.memPutFloat(j + 4, (float) d2);
        MemoryUtil.memPutFloat(j + 8, (float) (d3 - this.val3));
        this.num6 += 12;
        return this;
    }

    public ByteBufferSearchHelper4 getByteBufferSearchHelper41653(double d, double d2) {
        long j = this.num6;
        MemoryUtil.memPutFloat(j, (float) d);
        MemoryUtil.memPutFloat(j + 4, (float) d2);
        this.num6 += 8;
        return this;
    }

    public ByteBufferSearchHelper4 getByteBufferSearchHelper41654(Color color) {
        long j = this.num6;
        MemoryUtil.memPutByte(j, (byte) color.getRed());
        MemoryUtil.memPutByte(j + 1, (byte) color.getGreen());
        MemoryUtil.memPutByte(j + 2, (byte) color.getBlue());
        MemoryUtil.memPutByte(j + 3, (byte) (color.getAlpha() * ((float) this.val)));
        this.num6 += 4;
        return this;
    }

    public int get1655() {
        int i = this.num8;
        this.num8 = i + 1;
        return i;
    }

    public void do1656(int i, int i2) {
        long j = this.num7 + (this.num9 * 4);
        MemoryUtil.memPutInt(j, i);
        MemoryUtil.memPutInt(j + 4, i2);
        this.num9 += 2;
        do1658();
    }

    public void do1657(int i, int i2, int i3, int i4) {
        long j = this.num7 + (this.num9 * 4);
        MemoryUtil.memPutInt(j, i);
        MemoryUtil.memPutInt(j + 4, i2);
        MemoryUtil.memPutInt(j + 8, i3);
        MemoryUtil.memPutInt(j + 12, i3);
        MemoryUtil.memPutInt(j + 16, i4);
        MemoryUtil.memPutInt(j + 20, i);
        this.num9 += 6;
        do1658();
    }

    public void do1658() {
        if ((this.num8 + 1) * this.num >= this.byteBuffer.capacity()) {
            int i = get1664();
            int capacity = this.byteBuffer.capacity() * 2;
            if (capacity % this.num != 0) {
                capacity += capacity % this.num;
            }
            ByteBuffer createByteBuffer = BufferUtils.createByteBuffer(capacity);
            MemoryUtil.memCopy(MemoryUtil.memAddress0(this.byteBuffer), MemoryUtil.memAddress0(createByteBuffer), i);
            this.byteBuffer = createByteBuffer;
            this.num5 = MemoryUtil.memAddress0(this.byteBuffer);
            this.num6 = this.num5 + i;
        }
        if (this.num9 * 4 >= this.byteBuffer2.capacity()) {
            int capacity2 = this.byteBuffer2.capacity() * 2;
            if (capacity2 % this.mode_2.num != 0) {
                capacity2 += capacity2 % (this.mode_2.num * 4);
            }
            ByteBuffer createByteBuffer2 = BufferUtils.createByteBuffer(capacity2);
            MemoryUtil.memCopy(MemoryUtil.memAddress0(this.byteBuffer2), MemoryUtil.memAddress0(createByteBuffer2), this.num9 * 4);
            this.byteBuffer2 = createByteBuffer2;
            this.num7 = MemoryUtil.memAddress0(this.byteBuffer2);
        }
    }

    public void do1659() {
        if (!this.flag2) {
            throw new IllegalStateException("Mesh.end() called while not building.");
        }
        if (this.num9 > 0) {
            FramebufferHelperSearchHelper4.do282(this.num3);
            FramebufferHelperSearchHelper4.do285(34962, this.byteBuffer.limit(get1664()), 35048);
            FramebufferHelperSearchHelper4.do282(0);
            FramebufferHelperSearchHelper4.do283(this.num4);
            FramebufferHelperSearchHelper4.do285(34963, this.byteBuffer2.limit(this.num9 * 4), 35048);
            FramebufferHelperSearchHelper4.do283(0);
        }
        this.flag2 = false;
    }

    public void do1660(MatrixStack matrixStack) {
        FramebufferHelperSearchHelper4.do310();
        if (this.flag) {
            FramebufferHelperSearchHelper4.do312();
        } else {
            FramebufferHelperSearchHelper4.do313();
        }
        FramebufferHelperSearchHelper4.do314();
        FramebufferHelperSearchHelper4.do317();
        FramebufferHelperSearchHelper4.do320();
        if (this.flag3) {
            Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
            modelViewStack.pushMatrix();
            if (matrixStack != null) {
                modelViewStack.mul(matrixStack.peek().getPositionMatrix());
            }
            modelViewStack.translate(0.0f, (float) (-minecraftClient.gameRenderer.getCamera().getPos().y), 0.0f);
        }
        this.flag4 = true;
    }

    public void do1661(MatrixStack matrixStack) {
        if (this.flag2) {
            do1659();
        }
        if (this.num9 > 0) {
            boolean z = this.flag4;
            if (!z) {
                do1660(matrixStack);
            }
            do1663();
            FramebufferHelper_3.framebufferHelper_3.do1444();
            FramebufferHelperSearchHelper4.do281(this.num2);
            FramebufferHelperSearchHelper4.do286(this.mode_2.get3099(), this.num9, 5125);
            FramebufferHelperSearchHelper4.do281(0);
            if (z) {
                return;
            }
            do866();
        }
    }

    public void do866() {
        if (this.flag3) {
            RenderSystem.getModelViewStack().popMatrix();
        }
        FramebufferHelperSearchHelper4.do311();
        this.flag4 = false;
    }

    public boolean is1662() {
        return this.flag2;
    }

    public void do1663() {
    }

    public int get1664() {
        return (int) (this.num6 - this.num5);
    }
}
