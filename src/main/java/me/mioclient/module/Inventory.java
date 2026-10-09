package me.mioclient.module;

import java.awt.Color;
import me.mioclient.BooleanSetting;
import me.mioclient.ColorSetting;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.SearchHelper_2;
import me.mioclient.ZoomHelper;
import me.mioclient.api.Setting;
import me.mioclient.feature.Size;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/Inventory.class */
public class Inventory extends me.mioclient.ModuleList {
    public Setting<Color> setting;
    public Setting<Color> setting2;
    public Setting<Boolean> setting3;
    public final ZoomHelper zoomHelper;

    public Inventory() {
        super("Inventory", new String[0]);
        this.setting = add(new ColorSetting("Background", new Color(10, 10, 10, 50)));
        this.setting2 = add(new ColorSetting("Outline", new Color(10, 10, 10, 100)));
        this.setting3 = add(new BooleanSetting("HideEmpty", true));
        this.zoomHelper = new ZoomHelper();
        Size size = new Size(this);
        size.do2637(this);
        do3019(size);
    }

    @Override // me.mioclient.ModuleList
    public void do364(DrawContext drawContext) {
        this.zoomHelper.do170(!isEmpty(), 200L);
        float f = this.zoomHelper.get172();
        if (!this.setting3.getValue().booleanValue()) {
            f = 1.0f;
        }
        Color color817 = MixinMessageIndicatorHelper_2.getColor817(this.setting.getValue(), (this.setting.getValue().getAlpha() / 255.0f) * f);
        Color color8172 = MixinMessageIndicatorHelper_2.getColor817(this.setting2.getValue(), (this.setting2.getValue().getAlpha() / 255.0f) * f);
        SearchHelper_2.searchHelper_2.do546(drawContext.getMatrices(), 0.0f, 0.0f, 162.0f, 54.0f, color817);
        SearchHelper_2.searchHelper_2.do539(drawContext.getMatrices(), -1.0f, -1.0f, 162.0f, 54.0f, color8172);
        DefaultedList defaultedList = minecraftClient.player.getInventory().main;
        for (int i = 0; i < defaultedList.size() - 9; i++) {
            int i2 = (i % 9) * 18;
            int i3 = (i / 9) * 18;
            ItemStack itemStack = (ItemStack) defaultedList.get(i + 9);
            drawContext.drawItem(itemStack, i2, i3);
            drawContext.drawItemInSlot(minecraftClient.textRenderer, itemStack, i2, i3);
        }
    }

    public boolean isEmpty() {
        DefaultedList defaultedList = minecraftClient.player.getInventory().main;
        for (int i = 0; i < defaultedList.size() - 9; i++) {
            if (!((ItemStack) defaultedList.get(i + 9)).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override // me.mioclient.ModuleList
    public float[] getFloatArray365() {
        return new float[]{162.0f, 54.0f};
    }
}
