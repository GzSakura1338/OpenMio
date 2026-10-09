package me.mioclient;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL32C;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/FramebufferHelperSearchHelper4.class */
public class FramebufferHelperSearchHelper4 implements SearchHelper_4 {
    public static boolean flag;
    public static boolean flag2;
    public static boolean flag3;
    public static boolean flag4;
    public static int num;
    public static int num2;
    public static final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
    public static final StateHelper stateHelper = getStateHelper326("DEPTH");
    public static final StateHelper stateHelper2 = getStateHelper326("BLEND");
    public static final StateHelper stateHelper3 = getStateHelper326("CULL");
    public static final StateHelper stateHelper4 = getStateHelper326("SCISSOR");
    public static boolean flag5 = true;

    public static int get271() {
        return GlStateManager._glGenVertexArrays();
    }

    public static int get272() {
        return GlStateManager._glGenBuffers();
    }

    public static int get273() {
        return GlStateManager._genTexture();
    }

    public static int get274() {
        return GlStateManager.glGenFramebuffers();
    }

    public static void do275(int i) {
        GlStateManager._glDeleteBuffers(i);
    }

    public static void do276(int i) {
        GlStateManager._glDeleteVertexArrays(i);
    }

    public static void do277(int i) {
        GlStateManager.glDeleteShader(i);
    }

    public static void do278(int i) {
        GlStateManager._deleteTexture(i);
    }

    public static void do279(int i) {
        GlStateManager._glDeleteFramebuffers(i);
    }

    public static void do280(int i) {
        GlStateManager.glDeleteProgram(i);
    }

    public static void do281(int i) {
        GlStateManager._glBindVertexArray(i);
        if (flag5) {
            BufferRenderer.currentVertexBuffer = null;
        }
    }

    public static void do282(int i) {
        GlStateManager._glBindBuffer(34962, i);
    }

    public static void do283(int i) {
        if (i != 0) {
            num2 = num;
        }
        GlStateManager._glBindBuffer(34963, i != 0 ? i : num2);
    }

    public static void do284(int i) {
        GlStateManager._glBindFramebuffer(36160, i);
    }

    public static void do285(int i, ByteBuffer byteBuffer, int i2) {
        GlStateManager._glBufferData(i, byteBuffer, i2);
    }

    public static void do286(int i, int i2, int i3) {
        GlStateManager._drawElements(i, i2, i3, 0L);
    }

    public static void do287(int i) {
        GlStateManager._enableVertexAttribArray(i);
    }

    public static void do288(int i, int i2, int i3, boolean z, int i4, long j) {
        GlStateManager._vertexAttribPointer(i, i2, i3, z, i4, j);
    }

    public static int get289(int i) {
        return GlStateManager.glCreateShader(i);
    }

    public static void do290(int i, String str) {
        GlStateManager.glShaderSource(i, ImmutableList.of(str));
    }

    public static String getString291(int i) {
        GlStateManager.glCompileShader(i);
        if (GlStateManager.glGetShaderi(i, 35713) == 0) {
            return GlStateManager.glGetShaderInfoLog(i, 512);
        }
        return null;
    }

    public static int get292() {
        return GlStateManager.glCreateProgram();
    }

    public static String getString293(int i, int i2, int i3) {
        GlStateManager.glAttachShader(i, i2);
        GlStateManager.glAttachShader(i, i3);
        GlStateManager.glLinkProgram(i);
        if (GlStateManager.glGetProgrami(i, 35714) == 0) {
            return GlStateManager.glGetProgramInfoLog(i, 512);
        }
        return null;
    }

    public static void do294(int i) {
        GlStateManager._glUseProgram(i);
    }

    public static int get295(int i, String str) {
        return GlStateManager._glGetUniformLocation(i, str);
    }

    public static void do296(int i, int i2) {
        GlStateManager._glUniform1i(i, i2);
    }

    public static void do297(int i, float f) {
        GL32C.glUniform1f(i, f);
    }

    public static void do298(int i, float f, float f2) {
        GL32C.glUniform2f(i, f, f2);
    }

    public static void do299(int i, float f, float f2, float f3) {
        GL32C.glUniform3f(i, f, f2, f3);
    }

    public static void do300(int i, float f, float f2, float f3, float f4) {
        GL32C.glUniform4f(i, f, f2, f3, f4);
    }

    public static void do301(int i, float[] fArr) {
        GL32C.glUniform3fv(i, fArr);
    }

    public static void do302(int i, Matrix4f matrix4f) {
        matrix4f.get(floatBuffer);
        GlStateManager._glUniformMatrix4(i, false, floatBuffer);
    }

    public static void do303(int i, int i2) {
        GlStateManager._pixelStore(i, i2);
    }

    public static void do304(int i, int i2, int i3) {
        GlStateManager._texParameter(i, i2, i3);
    }

    public static void do305(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, ByteBuffer byteBuffer) {
        GL32C.glTexImage2D(i, i2, i3, i4, i5, i6, i7, i8, byteBuffer);
    }

    public static void do306() {
        do303(3312, 0);
        do303(3313, 0);
        do303(3314, 0);
        do303(32878, 0);
        do303(3315, 0);
        do303(3316, 0);
        do303(32877, 0);
        do303(3317, 4);
    }

    public static void do307(int i) {
        GL32C.glGenerateMipmap(i);
    }

    public static void do308(int i, int i2, int i3, int i4, int i5) {
        GlStateManager._glFramebufferTexture2D(i, i2, i3, i4, i5);
    }

    public static void do309(int i) {
        GlStateManager._clearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager._clear(i, false);
    }

    public static void do310() {
        flag = stateHelper.getState();
        flag2 = stateHelper2.getState();
        flag3 = stateHelper3.getState();
        flag4 = stateHelper4.getState();
    }

    public static void do311() {
        stateHelper.set(flag);
        stateHelper2.set(flag2);
        stateHelper3.set(flag3);
        stateHelper4.set(flag4);
        do321();
    }

    public static void do312() {
        GlStateManager._enableDepthTest();
    }

    public static void do313() {
        GlStateManager._disableDepthTest();
    }

    public static void do314() {
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(770, 771);
    }

    public static void do315() {
        GlStateManager._disableBlend();
    }

    public static void do316() {
        GlStateManager._enableCull();
    }

    public static void do317() {
        GlStateManager._disableCull();
    }

    public static void do318() {
        GlStateManager._enableScissorTest();
    }

    public static void do319() {
        GlStateManager._disableScissorTest();
    }

    public static void do320() {
        GL32C.glEnable(2848);
        GL32C.glLineWidth(1.0f);
    }

    public static void do321() {
        GL32C.glDisable(2848);
    }

    public static void do322(Identifier identifier) {
        GlStateManager._activeTexture(33984);
        minecraftClient.getTextureManager().bindTexture(identifier);
    }

    public static void do323(int i, int i2) {
        GlStateManager._activeTexture(33984 + i2);
        GlStateManager._bindTexture(i);
    }

    public static void do324(int i) {
        do323(i, 0);
    }

    public static void do325() {
        GlStateManager._activeTexture(33984);
    }

    public static StateHelper getStateHelper326(String str) {
        try {
            Field declaredField = GlStateManager.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            Object obj = declaredField.get(null);
            String mapClassName = FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "com.mojang.blaze3d.platform.GlStateManager$class_1018");
            Field field = null;
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            int length = declaredFields.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Field field2 = declaredFields[i];
                if (field2.getType().getName().equals(mapClassName)) {
                    field = field2;
                    break;
                }
                i++;
            }
            field.setAccessible(true);
            return (StateHelper) field.get(obj);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }
}
