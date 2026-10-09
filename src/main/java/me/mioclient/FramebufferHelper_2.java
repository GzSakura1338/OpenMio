package me.mioclient;

import me.mioclient.ByteBufferSearchHelper4;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/FramebufferHelper_2.class */
public class FramebufferHelper_2 {
    public static ByteBufferSearchHelper4 byteBufferSearchHelper4;
    public static final MatrixStack matrixStack = new MatrixStack();

    public static void init() {
        byteBufferSearchHelper4 = new ByteBufferSearchHelper4(ByteBufferSearchHelper4.Mode_2.Triangles, ByteBufferSearchHelper4.Mode.Vec2);
        byteBufferSearchHelper4.do1651();
        byteBufferSearchHelper4.do1657(byteBufferSearchHelper4.getByteBufferSearchHelper41653(-1.0, -1.0).get1655(), byteBufferSearchHelper4.getByteBufferSearchHelper41653(-1.0, 1.0).get1655(), byteBufferSearchHelper4.getByteBufferSearchHelper41653(1.0, 1.0).get1655(), byteBufferSearchHelper4.getByteBufferSearchHelper41653(1.0, -1.0).get1655());
        byteBufferSearchHelper4.do1659();
    }

    public static void do760() {
        byteBufferSearchHelper4.do1660(matrixStack);
    }

    public static void do865() {
        byteBufferSearchHelper4.do1661(matrixStack);
    }

    public static void do866() {
        byteBufferSearchHelper4.do866();
    }
}
