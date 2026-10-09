package me.mioclient;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.awt.Color;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;
import me.mioclient.mixin.ducks.DuckHandledScreen;
import me.mioclient.module.misc.ChestSearchBar;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HopperScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/MixinTextFieldWidgetHelper.class */
public class MixinTextFieldWidgetHelper {
    public static TextFieldWidget textFieldWidget;
    public static final MixinTextFieldWidgetHelper mixinTextFieldWidgetHelper = new MixinTextFieldWidgetHelper();
    public static ChestSearchBar chestSearchBar = (ChestSearchBar) BaritoneHelper_3.baritoneHelper_4.getModule117(ChestSearchBar.class);
    public static String text = "";
    public static int num = 0;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/MixinTextFieldWidgetHelper$AfterInit.class */
    public static class AfterInit implements ScreenEvents.AfterInit {
        public void afterInit(MinecraftClient minecraftClient, Screen screen, int i, int i2) {
            if (!MixinTextFieldWidgetHelper.chestSearchBar.isToggled()) {
                MixinTextFieldWidgetHelper.textFieldWidget = null;
                return;
            }
            if (!(screen instanceof GenericContainerScreen) && !(screen instanceof ShulkerBoxScreen) && !(screen instanceof HopperScreen)) {
                MixinTextFieldWidgetHelper.textFieldWidget = null;
                return;
            }
            DuckHandledScreen duckHandledScreen = (DuckHandledScreen)((HandledScreen) screen);
            MixinTextFieldWidgetHelper.textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, duckHandledScreen.getX() + 81, duckHandledScreen.getY() + 5, 88, 10, Text.literal(""));
            MixinTextFieldWidgetHelper.textFieldWidget.setText(MixinTextFieldWidgetHelper.text);
            MixinTextFieldWidgetHelper.textFieldWidget.setFocused(false);
            MixinTextFieldWidgetHelper.textFieldWidget.setMaxLength(32);
            MixinTextFieldWidgetHelper.textFieldWidget.setDrawsBackground(false);
            MixinTextFieldWidgetHelper.textFieldWidget.setChangedListener(str -> {
                MixinTextFieldWidgetHelper.text = str;
            });
            Screens.getButtons(screen).add(MixinTextFieldWidgetHelper.textFieldWidget);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/MixinTextFieldWidgetHelper$BiPredicate.class */
    public interface BiPredicate extends java.util.function.BiPredicate<String, String> {
    }

    public void init() {
        textFieldWidget = null;
        text = "";
        ScreenEvents.AFTER_INIT.register(new AfterInit());
    }

    public void onDrawGui(DrawContext drawContext, Screen screen, MatrixStack matrixStack, float f, float f2, float f3) {
        if (!chestSearchBar.isToggled() || textFieldWidget == null) {
            return;
        }
        matrixStack.push();
        matrixStack.translate(0.0f, 0.0f, 500.0f);
        DuckHandledScreen duckHandledScreen = (DuckHandledScreen) screen;
        if (!textFieldWidget.getText().isBlank() && (screen instanceof HandledScreen)) {
            ScreenHandler screenHandler = ((HandledScreen) screen).getScreenHandler();
            int x = duckHandledScreen.getX();
            int y = duckHandledScreen.getY();
            num = 0;
            Iterator it = screenHandler.slots.iterator();
            while (it.hasNext()) {
                Slot slot = (Slot) it.next();
                int i = x + slot.x;
                int i2 = y + slot.y;
                ItemStack stack = slot.getStack();
                if (namesMatch(stack, textFieldWidget.getText())) {
                    num++;
                } else {
                    RenderSystem.disableDepthTest();
                    SearchHelper_2.searchHelper_2.do545(matrixStack, i, i2, i + 16, i2 + 16, -1442840576);
                }
                if (isFull(stack, textFieldWidget.getText())) {
                    drawContext.fill(i, i2, i + 16, i2 + 16, -500, MixinMessageIndicatorHelper_2.get818(Color.green, 100));
                }
            }
        }
        if (num != 0 || textFieldWidget.getText().isEmpty()) {
            textFieldWidget.setEditableColor(16777215);
        } else {
            textFieldWidget.setEditableColor(16733525);
        }
        DiffuseLighting.enableGuiDepthLighting();
        matrixStack.pop();
    }

    public static boolean namesMatch(ItemStack itemStack, String str) {
        ContainerComponent containerComponent;
        String strip = Formatting.strip(str.trim().toLowerCase(Locale.ROOT));
        if (strip == null || strip.isEmpty()) {
            return true;
        }
        if (itemStack.isEmpty()) {
            return false;
        }
        Item item = itemStack.getItem();
        if (item.getTranslationKey().contains("shulker_box") && (containerComponent = (ContainerComponent) itemStack.get(DataComponentTypes.CONTAINER)) != null) {
            Iterator it = containerComponent.iterateNonEmpty().iterator();
            while (it.hasNext()) {
                if (namesMatch((ItemStack) it.next(), strip)) {
                    return true;
                }
            }
        }
        String strip2 = Formatting.strip(itemStack.getName().getString().trim().toLowerCase(Locale.ROOT));
        BiPredicate biPredicate = (v0, v1) -> {
            return v0.contains(v1);
        };
        if (strip.length() >= 3 && strip.startsWith("\"") && strip.endsWith("\"")) {
            strip = strip.substring(1, strip.length() - 1);
            biPredicate = (v0, v1) -> {
                return v0.equals(v1);
            };
        }
        if (strip.length() >= 3 && strip.startsWith("/") && strip.endsWith("/")) {
            strip = strip.substring(1, strip.length() - 1);
            biPredicate = (str2, str3) -> {
                return Pattern.compile(str3).matcher(str2).find();
            };
        }
        ItemEnchantmentsComponent itemEnchantmentsComponent = (ItemEnchantmentsComponent) itemStack.get(DataComponentTypes.STORED_ENCHANTMENTS);
        if (itemEnchantmentsComponent != null) {
            Iterator it2 = itemEnchantmentsComponent.getEnchantmentEntries().iterator();
            while (it2.hasNext()) {
                if (testEnchant(biPredicate, strip, (Object2IntMap.Entry) it2.next())) {
                    return true;
                }
            }
        }
        if (itemStack.hasEnchantments()) {
            Iterator it3 = itemStack.getEnchantments().getEnchantmentEntries().iterator();
            while (it3.hasNext()) {
                if (testEnchant(biPredicate, strip, (Object2IntMap.Entry) it3.next())) {
                    return true;
                }
            }
        }
        if (biPredicate.test(I18n.translate(item.getTranslationKey(itemStack), new Object[0]).toLowerCase(Locale.ROOT), strip)) {
            return true;
        }
        PotionContentsComponent potionContentsComponent = (PotionContentsComponent) itemStack.get(DataComponentTypes.POTION_CONTENTS);
        if (potionContentsComponent != null) {
            Iterator it4 = potionContentsComponent.getEffects().iterator();
            while (it4.hasNext()) {
                if (biPredicate.test(I18n.translate(((StatusEffectInstance) it4.next()).getTranslationKey(), new Object[0]).toLowerCase(Locale.ROOT), strip)) {
                    return true;
                }
            }
        }
        return biPredicate.test(strip2, strip);
    }

    public static boolean testEnchant(BiPredicate biPredicate, String str, Object2IntMap.Entry<RegistryEntry<Enchantment>> entry) {
        if (entry == null) {
            return false;
        }
        return biPredicate.test(Enchantment.getName((RegistryEntry) entry.getKey(), entry.getIntValue()).toString().toLowerCase(Locale.ROOT).replaceAll("enchantment", ""), str);
    }

    public static boolean isFull(ItemStack itemStack, String str) {
        ContainerComponent containerComponent;
        if (!chestSearchBar.highlightFull.getValue().booleanValue()) {
            return false;
        }
        String strip = Formatting.strip(str.trim().toLowerCase(Locale.ROOT));
        if (strip == null || strip.isEmpty() || itemStack.isEmpty() || !itemStack.getItem().getTranslationKey().contains("shulker_box") || (containerComponent = (ContainerComponent) itemStack.get(DataComponentTypes.CONTAINER)) == null || containerComponent.stream().toList().size() != 27) {
            return false;
        }
        Iterator it = containerComponent.stream().toList().iterator();
        while (it.hasNext()) {
            if (!namesMatch((ItemStack) it.next(), strip)) {
                return false;
            }
        }
        return true;
    }
}
