package me.mioclient;

import com.mojang.blaze3d.platform.GlStateManager;
import me.mioclient.module.render.Shader;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ShaderFramebufferHelper_2.class */
public class ShaderFramebufferHelper_2 extends ShaderFramebufferHelper {
    public static final Identifier identifier = Identifier.of("mio-mount", "textures/overlay.png");
    public static Shader shader = (Shader) BaritoneHelper_3.baritoneHelper_4.getModule117(Shader.class);
    public final long num = System.currentTimeMillis();

    public ShaderFramebufferHelper_2() {
        do753("rainbow");
    }

    @Override // me.mioclient.FramebufferHelper
    public boolean is755() {
        return shader.isToggled() && shader.shader.getValue() == Shader.MixinHeldItemRendererMode.RAINBOW;
    }

    @Override // me.mioclient.FramebufferHelper
    public boolean is756(Entity entity) {
        if (shader == null || entity == null) {
            return false;
        }
        return shader.is1763(entity);
    }

    @Override // me.mioclient.FramebufferHelper
    public void do759() {
        this.framebufferHelper_3.do1439("u_Texture", 0);
        int glId = minecraftClient.getTextureManager().getTexture(identifier).getGlId();
        GlStateManager._activeTexture(33985);
        GlStateManager._bindTexture(glId);
        this.framebufferHelper_3.do1438("u_Image", shader.image.getValue().booleanValue());
        this.framebufferHelper_3.do1439("u_Overlay", 1);
        this.framebufferHelper_3.do1440("u_OverlayAlpha", shader.get3113());
        this.framebufferHelper_3.do1442("u_Fill", shader.is3114() ? MixinMessageIndicatorHelper_2.color : shader.getColor3112(shader.fill.getValue()));
        this.framebufferHelper_3.do1440("u_Fill_Offset", ((float) (System.currentTimeMillis() - this.num)) * 0.0010000000474974513f * shader.fillSpeed.getValue().floatValue());
        this.framebufferHelper_3.do1440("u_Fill_Strength", shader.fillStrength.getValue().floatValue());
        this.framebufferHelper_3.do1442("u_Outline", shader.outline2.getValue());
        this.framebufferHelper_3.do1440("u_Outline_Offset", ((float) (System.currentTimeMillis() - this.num)) * 0.0010000000474974513f * shader.outlineSpeed.getValue().floatValue());
        this.framebufferHelper_3.do1440("u_Outline_Strength", shader.outlineStrength.getValue().floatValue());
        this.framebufferHelper_3.do1439("u_Radius", shader.is3115() ? 0 : shader.lineWidth.getValue().intValue());
        this.framebufferHelper_3.do1440("u_GlowMultiplier", shader.glow.getValue().floatValue());
        this.framebufferHelper_3.do1438("u_FastLines", shader.fastLines.getValue().booleanValue() || ShaderSearchHelper4.flag2);
        this.framebufferHelper_3.do1439("u_Dots", shader.type.getValue().ordinal());
        this.framebufferHelper_3.do1439("u_DotsRadius", shader.radius.getValue().intValue());
        this.framebufferHelper_3.do1440("u_DotsAlpha", shader.alpha.getValue().floatValue());
    }
}
