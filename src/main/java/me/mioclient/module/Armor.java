package me.mioclient.module;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import me.mioclient.ArmorSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BooleanSetting;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.FreecamHelper;
import me.mioclient.MatrixStackEvent_2;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.feature.Size;
import me.mioclient.module.client.Fonts;
import me.mioclient.module.client.HUD;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/Armor.class */
public class Armor extends me.mioclient.ModuleList {
    public static HUD hud = (HUD) BaritoneHelper_3.baritoneHelper_4.getModule117(HUD.class);
    public Setting<Boolean> setting;
    public Setting<Boolean> setting2;
    public Setting<Boolean> setting3;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/Armor$Inner.class */
    class Inner extends Size {
        public Inner(Armor armor, me.mioclient.ModuleList moduleList) {
            super(moduleList);
        }

        @Override // me.mioclient.ModuleListSearchHelper4
        public boolean is2349() {
            return false;
        }
    }

    public Armor() {
        super("Armor", new String[0]);
        this.setting = add(new BooleanSetting("Durability", true));
        this.setting2 = add(new BooleanSetting("Percentage", true));
        this.setting3 = add(new BooleanSetting("BarColor", true));
        do3019(new Inner(this, this));
    }

    @Listen(get219= 1)
    public void onEvent(MatrixStackEvent_2 matrixStackEvent_2) {
        if (hud.isToggled() || is3017()) {
            int scaledWindowWidth = matrixStackEvent_2.getDrawContext474().getScaledWindowWidth();
            int scaledWindowHeight = matrixStackEvent_2.getDrawContext474().getScaledWindowHeight();
            int i = 15;
            for (int i2 = 3; i2 >= 0; i2--) {
                ItemStack itemStack = (ItemStack) minecraftClient.player.getInventory().armor.get(i2);
                if (!itemStack.isEmpty()) {
                    do366(matrixStackEvent_2.getDrawContext474(), itemStack, (scaledWindowWidth / 2) + i, scaledWindowHeight - get367());
                    if (itemStack.isDamageable() && this.setting.getValue().booleanValue()) {
                        String format = String.format("%d%s", Integer.valueOf(ArmorSearchHelper4.get1905(itemStack)), "%");
                        float intBitsToFloat = Fonts.fonts.isToggled() ? 0.699999988079071f : 0.6499999761581421f;
                        if (!this.setting2.getValue().booleanValue()) {
                            format = format.substring(0, format.length() - 1);
                            intBitsToFloat = 1.0f;
                        }
                        float intBitsToFloat2 = ((scaledWindowHeight - get367()) - 3.799999952316284f) - (this.setting2.getValue().booleanValue() ? 0 : 2);
                        FontsSearchHelper4.fontsSearchHelper4.do1695(matrixStackEvent_2.getDrawContext474(), format, ((((scaledWindowWidth / 2.0f) + i) + 9.0f) - ((FontsSearchHelper4.fontsSearchHelper4.get1316(format) * 0.5f) * intBitsToFloat)) - intBitsToFloat, intBitsToFloat2, intBitsToFloat, this.setting3.getValue().booleanValue() ? new Color(itemStack.getItemBarColor(), false) : getColor3018(intBitsToFloat2));
                    }
                    i += 18;
                }
            }
        }
    }

    @Override // me.mioclient.ModuleList
    public void do364(DrawContext drawContext) {
        super.do364(drawContext);
    }

    @Override // me.mioclient.ModuleList
    public float[] getFloatArray365() {
        return new float[]{0.0f, 0.0f};
    }

    public void do366(DrawContext drawContext, ItemStack itemStack, int i, int i2) {
        drawContext.drawItem(itemStack, i, i2);
        drawContext.drawItemInSlot(minecraftClient.textRenderer, itemStack, i, i2);
        RenderSystem.enableBlend();
    }

    public int get367() {
        int i;
        if ((minecraftClient.player.isSubmergedInWater() || minecraftClient.player.getAir() != minecraftClient.player.getMaxAir()) && minecraftClient.player.getAir() > 0 && !minecraftClient.player.isCreative()) {
            i = 65;
        } else {
            if (minecraftClient.player.hasVehicle()) {
                if (minecraftClient.player.getVehicle() instanceof LivingEntity) {
                    i = (int) (FreecamHelper.num + (Math.ceil((((LivingEntity) minecraftClient.player.getVehicle()).getMaxHealth() - 1.0f) / 20.0f) * 10.0));
                }
            }
            i = 55 - (minecraftClient.player.isCreative() ? 17 : 0);
        }
        return i;
    }
}
