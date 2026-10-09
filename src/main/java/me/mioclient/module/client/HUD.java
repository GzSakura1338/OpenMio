package me.mioclient.module.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BooleanSetting;
import me.mioclient.ColorSetting;
import me.mioclient.EnumSetting;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FontsSearchHelper4_2;
import me.mioclient.HUDHelper_2;
import me.mioclient.HUDSearchHelper4;
import me.mioclient.MatrixStackEvent_2;
import me.mioclient.MixinTitleScreenMode;
import me.mioclient.ModuleListSearchHelper4;
import me.mioclient.NumberSetting;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import me.mioclient.module.exploit.AntiLevitation;
import me.mioclient.module.render.NoRender;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/client/HUD.class */
public class HUD extends Module {
    public static AntiLevitation antiLevitation = (AntiLevitation) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiLevitation.class);
    public static NoRender norender = (NoRender) BaritoneHelper_3.baritoneHelper_4.getModule117(NoRender.class);
    public final Predicate<Boolean> predicate;
    public Setting<Boolean> setting;
    public Setting<HUDMode> setting2;
    public Setting<Boolean> setting3;
    public Setting<Integer> setting4;
    public Setting<Integer> setting5;
    public Setting<Boolean> setting6;
    public Setting<Color> setting7;
    public Setting<MixinTitleScreenMode> setting8;
    public Setting<Integer> setting9;
    public Setting<Color> setting10;
    public Setting<Integer> setting11;
    public Setting<Integer> setting12;
    public Setting<Boolean> setting13;
    public final List<Setting<Color>> list;
    public final HUDHelper_2 hUDHelper_2;
    public final HUDHelper_2 hUDHelper_22;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/client/HUD$HUDMode.class */
    public enum HUDMode implements EnumSettingHelper {
        KEEP("Keep"),
        MOVE("Move"),
        HIDE("Hide");

        public final String name;

        HUDMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/client/HUD$ModuleListMode.class */
    public enum ModuleListMode implements EnumSettingHelper {
        ALPHABET("Alphabet"),
        LENGTH("Length");

        public final String name;

        ModuleListMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public HUD() {
        super("HUD", "Draws useful HUD elements on your screen.", Category.CLIENT, new String[0]);
        this.predicate = bool -> {
            Screen screen = minecraftClient.currentScreen;
            return !(screen instanceof FontsSearchHelper4_2) || ((FontsSearchHelper4_2) screen).getScreen1715() == null;
        };
        this.setting = add(new BooleanSetting("HudEditor", false, this.predicate));
        this.setting2 = add(new EnumSetting("Icons", HUDMode.MOVE));
        this.setting3 = add(new BooleanSetting("SmoothWidth", false));
        this.setting4 = add(new NumberSetting("SafeX", 1, 0, 20));
        this.setting5 = add(new NumberSetting("SafeY", 1, 0, 10));
        this.setting6 = add(new BooleanSetting("Colors", true).getSetting2337());
        this.setting7 = add(new ColorSetting("Color", new Color(189, 153, 255, 255)).getSetting2341().getSetting2342(this.setting6));
        this.setting8 = add(new EnumSetting("Fade", MixinTitleScreenMode.NONE, mixinTitleScreenMode -> {
            return this.setting6.is623();
        }));
        this.setting9 = add(new NumberSetting("FadeOffset", 5, 1, 30, num -> {
            return (this.setting8.getValue() != MixinTitleScreenMode.NONE || ((ColorSetting) this.setting7).is2862()) && this.setting6.is623();
        }));
        this.setting10 = add(new ColorSetting("SecondColor", new Color(105, 86, 143, 255), color -> {
            return this.setting8.getValue() == MixinTitleScreenMode.PULSE && this.setting6.is623();
        }).getSetting2341());
        this.setting11 = add(new NumberSetting("ColorAmount", 5, 2, 9, num2 -> {
            return this.setting8.getValue() == MixinTitleScreenMode.RAW && this.setting6.is623();
        }));
        this.setting12 = add(new NumberSetting("FadeSpeed", 50, 20, 100, num3 -> {
            return this.setting8.getValue() == MixinTitleScreenMode.RAW && this.setting6.is623();
        }));
        this.setting13 = add(new BooleanSetting("MixColors", true, bool2 -> {
            return this.setting8.getValue() == MixinTitleScreenMode.RAW && this.setting6.is623();
        }));
        this.list = new ArrayList(this.setting11.getObject2326().intValue());
        this.hUDHelper_2 = new HUDHelper_2(2.0f);
        this.hUDHelper_22 = new HUDHelper_2(1.5f);
        this.setting.do2339(() -> {
            if (this.setting.getValue().booleanValue()) {
                this.setting.do2333(false);
                if (is1469()) {
                    return;
                }
                minecraftClient.setScreen(BaritoneHelper_3.getHUDSearchHelper42217());
            }
        });
        setDrawn(false);
        do495(true);
        for (int i = 1; i <= this.setting11.getObject2326().intValue(); i++) {
            int i2 = i;
            this.list.add(add(new ColorSetting(new ArgumentTypeHelper().getArgumentTypeHelper2906(i).getString2921("Color\u0001"), Color.white, color2 -> {
                return this.setting8.getValue() == MixinTitleScreenMode.RAW && this.setting6.is623() && i2 <= this.setting11.getValue().intValue();
            }).getSetting2341()));
        }
    }

    @Listen
    public void onEvent(MatrixStackEvent_2 matrixStackEvent_2) {
        int i = 0;
        if (this.setting2.getValue() == HUDMode.MOVE && !(minecraftClient.currentScreen instanceof InventoryScreen)) {
            for (StatusEffectInstance statusEffectInstance : minecraftClient.player.getStatusEffects()) {
                RegistryEntry effectType = statusEffectInstance.getEffectType();
                if (!antiLevitation.isToggled() || (effectType != StatusEffects.SLOW_FALLING && effectType != StatusEffects.LEVITATION)) {
                    if (!norender.isToggled() || !norender.darkness.getValue().booleanValue() || effectType != StatusEffects.DARKNESS) {
                        if (i == 0) {
                            i++;
                        }
                        if (!((StatusEffect) statusEffectInstance.getEffectType().value()).isBeneficial()) {
                            i = 2;
                        }
                    }
                }
            }
        }
        if (minecraftClient.currentScreen instanceof InventoryScreen) {
            i = 0;
        }
        this.hUDHelper_2.do1737((24 * i) + 4);
        this.hUDHelper_2.get172();
        float f = this.hUDHelper_22.get172();
        float scaledWidth = minecraftClient.getWindow().getScaledWidth();
        float scaledHeight = minecraftClient.getWindow().getScaledHeight();
        if (f > 1.0f && !(minecraftClient.currentScreen instanceof ChatScreen)) {
            matrixStackEvent_2.getDrawContext474().fill(2, (int) ((scaledHeight - 2.0f) - f), (int) (scaledWidth - 2.0f), (int) (scaledHeight - 2.0f), minecraftClient.options.getTextBackgroundColor(Integer.MIN_VALUE));
        }
        ModuleListSearchHelper4.flag = true;
        if (!(minecraftClient.currentScreen instanceof HUDSearchHelper4)) {
            BaritoneHelper_3.getHUDSearchHelper42217().do199(matrixStackEvent_2.getDrawContext474(), 0.0d, 0.0d);
        }
        ModuleListSearchHelper4.flag = false;
    }

    public float get734() {
        return this.hUDHelper_22.val;
    }

    public float get735() {
        return this.hUDHelper_2.val == 4.0f ? 1.0f : this.hUDHelper_2.val;
    }

    public void do736(float f) {
        this.hUDHelper_22.do1737(f);
    }

    public int get737() {
        return this.setting4.getValue().intValue();
    }

    public int get738() {
        return this.setting5.getValue().intValue();
    }

    public void do739(int i) {
        this.setting4.do2333(Integer.valueOf(i));
    }

    public void do740(int i) {
        this.setting5.do2333(Integer.valueOf(i));
    }
}
