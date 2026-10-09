package me.mioclient;

import me.mioclient.module.render.ESP;
import net.minecraft.entity.Entity;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ShaderFramebufferHelper_3.class */
public class ShaderFramebufferHelper_3 extends ShaderFramebufferHelper {
    public static ESP eSP = (ESP) BaritoneHelper_3.baritoneHelper_4.getModule117(ESP.class);

    public ShaderFramebufferHelper_3() {
        do753("outline");
    }

    @Override // me.mioclient.FramebufferHelper
    public boolean is755() {
        return eSP.isToggled() && eSP.eSPSearchHelper4_2.is1765();
    }

    @Override // me.mioclient.FramebufferHelper
    public boolean is756(Entity entity) {
        return false;
    }

    @Override // me.mioclient.FramebufferHelper
    public void do759() {
        this.framebufferHelper_3.do1439("u_Texture", 0);
        this.framebufferHelper_3.do1439("u_Width", 2);
        this.framebufferHelper_3.do1438("u_FastLines", true);
        this.framebufferHelper_3.do1440("u_GlowMultiplier", 1.0);
    }
}
