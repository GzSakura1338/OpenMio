package me.mioclient.module.render;

import java.awt.Color;
import me.mioclient.BlurFramebuffer;
import me.mioclient.FontsSearchHelper4_2;
import me.mioclient.Helper_7;
import me.mioclient.MatrixStackEvent_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Blur.class */
public class Blur extends Module {
    public Setting<Integer> radius;

    public Blur() {
        super("Blur", "Blurs the background in GUI's.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    @Listen(get219= Helper_7.num5)
    public void onEvent(MatrixStackEvent_2 matrixStackEvent_2) {
        do991(matrixStackEvent_2.getDrawContext474());
    }

    public void do991(DrawContext drawContext) {
        Screen screen = minecraftClient.currentScreen;
        if (screen instanceof FontsSearchHelper4_2) {
            return;
        }
        if ((minecraftClient.currentScreen == null || (minecraftClient.currentScreen instanceof ChatScreen)) ? false : true) {
            BlurFramebuffer.do2002(() -> {
                drawContext.fill(0, 0, minecraftClient.getWindow().getScaledWidth(), minecraftClient.getWindow().getScaledHeight(), new Color(0.0f, 0.0f, 0.0f, 1.0f).hashCode());
            }, get992());
        }
    }

    public int get992() {
        return this.radius.getValue().intValue();
    }
}
