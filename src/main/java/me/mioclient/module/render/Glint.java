package me.mioclient.module.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import me.mioclient.KeyPearlMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.EndDrawingEvent;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Glint.class */
public class Glint extends Module {
    public static final Identifier identifier = Identifier.of("mio-mount", "textures/shine.png");
    public Setting<Color> color;

    public Glint() {
        super("Glint", "Changes your enchantment glint's color.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    @Listen
    public void onEndDrawing(EndDrawingEvent endDrawingEvent) {
        if (endDrawingEvent.getKeyPearlMode1472() != KeyPearlMode.Pre) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            return;
        }
        Color value = this.color.getValue();
        MinecraftClient.getInstance().getTextureManager().getTexture(identifier).setFilter(true, false);
        RenderSystem.setShaderTexture(0, identifier);
        RenderSystem.setShaderColor(value.getRed() / 255.0f, value.getGreen() / 255.0f, value.getBlue() / 255.0f, 1.0f);
    }
}
