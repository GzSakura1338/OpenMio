package me.mioclient;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4f;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/FramebufferHelper_3.class */
public class FramebufferHelper_3 implements SearchHelper_4 {
    public static FramebufferHelper_3 framebufferHelper_3;
    public final int num;
    public final Object2IntMap<String> object2IntMap = new Object2IntOpenHashMap();

    public FramebufferHelper_3(String str, String str2) {
        int i = FramebufferHelperSearchHelper4.get289(35633);
        FramebufferHelperSearchHelper4.do290(i, getString1435(str));
        String string291 = FramebufferHelperSearchHelper4.getString291(i);
        if (string291 != null) {
            System.err.println(new ArgumentTypeHelper().getArgumentTypeHelper2919(string291).getArgumentTypeHelper2919(str).getString2921("Failed to compile vertex shader (\u0001): \u0001"));
            throw new java.lang.RuntimeException(new ArgumentTypeHelper().getArgumentTypeHelper2919(string291).getArgumentTypeHelper2919(str).getString2921("Failed to compile vertex shader (\u0001): \u0001"));
        }
        int i2 = FramebufferHelperSearchHelper4.get289(35632);
        FramebufferHelperSearchHelper4.do290(i2, getString1435(str2));
        String string2912 = FramebufferHelperSearchHelper4.getString291(i2);
        if (string2912 != null) {
            System.err.println(new ArgumentTypeHelper().getArgumentTypeHelper2919(string2912).getArgumentTypeHelper2919(str2).getString2921("Failed to compile fragment shader (\u0001): \u0001"));
            throw new java.lang.RuntimeException(new ArgumentTypeHelper().getArgumentTypeHelper2919(string2912).getArgumentTypeHelper2919(str2).getString2921("Failed to compile fragment shader (\u0001): \u0001"));
        }
        this.num = FramebufferHelperSearchHelper4.get292();
        String string293 = FramebufferHelperSearchHelper4.getString293(this.num, i, i2);
        if (string293 != null) {
            System.err.println(new ArgumentTypeHelper().getArgumentTypeHelper2919(string293).getString2921("Failed to link program: \u0001"));
            throw new java.lang.RuntimeException(new ArgumentTypeHelper().getArgumentTypeHelper2919(string293).getString2921("Failed to link program: \u0001"));
        }
        FramebufferHelperSearchHelper4.do277(i);
        FramebufferHelperSearchHelper4.do277(i2);
    }

    public String getString1435(String str) {
        try {
            return IOUtils.toString(getClass().getResourceAsStream(new ArgumentTypeHelper().getArgumentTypeHelper2919(str).getString2921("/assets/mio/shaders\u0001")), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void do1436() {
        FramebufferHelperSearchHelper4.do294(this.num);
        framebufferHelper_3 = this;
    }

    public int get1437(String str) {
        if (this.object2IntMap.containsKey(str)) {
            return this.object2IntMap.getInt(str);
        }
        int i = FramebufferHelperSearchHelper4.get295(this.num, str);
        this.object2IntMap.put(str, i);
        return i;
    }

    public void do1438(String str, boolean z) {
        FramebufferHelperSearchHelper4.do296(get1437(str), z ? 1 : 0);
    }

    public void do1439(String str, int i) {
        FramebufferHelperSearchHelper4.do296(get1437(str), i);
    }

    public void do1440(String str, double d) {
        FramebufferHelperSearchHelper4.do297(get1437(str), (float) d);
    }

    public void do1441(String str, double d, double d2) {
        FramebufferHelperSearchHelper4.do298(get1437(str), (float) d, (float) d2);
    }

    public void do1442(String str, Color color) {
        FramebufferHelperSearchHelper4.do300(get1437(str), color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }

    public void do1443(String str, Matrix4f matrix4f) {
        FramebufferHelperSearchHelper4.do302(get1437(str), matrix4f);
    }

    public void do1444() {
        do1443("u_Proj", RenderSystem.getProjectionMatrix());
        do1443("u_ModelView", RenderSystem.getModelViewStack());
    }
}
