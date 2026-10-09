package me.mioclient.module;

import me.mioclient.feature.Size;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/Chat.class */
public class Chat extends me.mioclient.ModuleList {
    public Chat() {
        super("Chat", new String[0]);
        do3019(new Size(this));
    }

    public float get987() {
        if (this.moduleListSearchHelper4.getFloatArray2950() == null) {
            return 0.0f;
        }
        return ((this.moduleListSearchHelper4.get124() - (minecraftClient.getWindow().getScaledHeight() - 40.0f)) / get989()) + get990();
    }

    public float get988() {
        if (this.moduleListSearchHelper4.getFloatArray2950() == null) {
            return 0.0f;
        }
        return this.moduleListSearchHelper4.get123() / get989();
    }

    public float get989() {
        return ((Double) minecraftClient.options.getChatScale().getValue()).floatValue();
    }

    public float get990() {
        return ChatHud.getHeight(((Double) minecraftClient.options.getChatHeightUnfocused().getValue()).floatValue());
    }

    @Override // me.mioclient.ModuleList
    public void do364(DrawContext drawContext) {
        super.do364(drawContext);
    }

    @Override // me.mioclient.ModuleList
    public float[] getFloatArray365() {
        return new float[]{(((Double) minecraftClient.options.getChatWidth().getValue()).floatValue() * 280.0f) + 40.0f + (12.0f * get989()), get990() * get989()};
    }
}
