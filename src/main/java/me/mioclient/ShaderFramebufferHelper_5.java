package me.mioclient;

import com.mojang.blaze3d.platform.GlStateManager;
import me.mioclient.module.render.Shader;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ShaderFramebufferHelper_5.class */
public class ShaderFramebufferHelper_5 extends ShaderFramebufferHelper {
    public static Shader shader = (Shader) BaritoneHelper_3.baritoneHelper_4.getModule117(Shader.class);
    public static final Identifier identifier = Identifier.of("mio-mount", "textures/overlay.png");
    public final long num = System.currentTimeMillis();

    public ShaderFramebufferHelper_5() {
        do753("gradient");
    }

    @Override // me.mioclient.FramebufferHelper
    public boolean is755() {
        return shader.isToggled() && shader.shader.getValue() == Shader.MixinHeldItemRendererMode.GRADIENT;
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
        this.framebufferHelper_3.do1439("u_Width", shader.is3115() ? 0 : shader.lineWidth.getValue().intValue());
        this.framebufferHelper_3.do1438("u_FastLines", shader.fastLines.getValue().booleanValue() || ShaderSearchHelper4.flag2);
        this.framebufferHelper_3.do1439("u_ShapeMode", 2);
        this.framebufferHelper_3.do1440("u_GlowMultiplier", shader.glow.getValue().floatValue());
        this.framebufferHelper_3.do1440("u_Time", ((float) (System.currentTimeMillis() - this.num)) * 0.009999999776482582f * shader.speed.getValue().floatValue());
        if (shader.is3114()) {
            this.framebufferHelper_3.do1442("u_FillColor", MixinMessageIndicatorHelper_2.color);
            this.framebufferHelper_3.do1442("u_FillColor2", MixinMessageIndicatorHelper_2.color);
        } else {
            this.framebufferHelper_3.do1442("u_FillColor", shader.getColor3112(shader.fill.getValue()));
            this.framebufferHelper_3.do1442("u_FillColor2", shader.getColor3112(shader.fillSecond.getValue()));
        }
        this.framebufferHelper_3.do1442("u_OutlineColor", shader.outline2.getValue());
        this.framebufferHelper_3.do1442("u_OutlineColor2", shader.outlineSecond.getValue());
        this.framebufferHelper_3.do1439("u_Dots", shader.type.getValue().ordinal());
        this.framebufferHelper_3.do1439("u_DotsRadius", shader.radius.getValue().intValue());
        this.framebufferHelper_3.do1440("u_DotsAlpha", shader.alpha.getValue().floatValue());
        this.framebufferHelper_3.do1440("u_Step", shader.step.getValue().floatValue());
    }
}
