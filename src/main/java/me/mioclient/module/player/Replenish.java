package me.mioclient.module.player;

import java.util.List;
import java.util.Set;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.PhaseESPHelper;
import me.mioclient.ScaffoldMode_2;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.combat.AutoCrystal;
import me.mioclient.module.exploit.XCarry;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/player/Replenish.class */
public class Replenish extends Module {
    public static XCarry xCarry = (XCarry) BaritoneHelper_3.baritoneHelper_4.getModule117(XCarry.class);
    public static AutoCrystal autoCrystal = (AutoCrystal) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoCrystal.class);
    public Setting<Set<Item>> items;
    public Setting<ScaffoldMode_2> selection;
    public Setting<Integer> delay;
    public Setting<Integer> threshold;
    public Setting<Boolean> sort;
    public Setting<Boolean> unstackable;
    public Setting<Boolean> limit;
    public Setting<Boolean> motion;
    public Setting<Boolean> falling;
    public final List<Item> list;
    public final Stopwatch stopwatch;

    public Replenish() {
        super("Replenish", "Replenishes your hotbar.", Category.PLAYER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.list = DefaultedList.ofSize(9, Items.AIR);
        this.stopwatch = new Stopwatch();
        setDrawn(false);
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (this.stopwatch.is419(this.delay.getValue().intValue()) && minecraftClient.player.currentScreenHandler.getCursorStack().isEmpty()) {
            if (HoleSnapSearchHelper4_3.is2181() && this.motion.getValue().booleanValue()) {
                return;
            }
            if (minecraftClient.player.getVelocity().getY() <= 0.0d || !this.falling.getValue().booleanValue()) {
                boolean z = minecraftClient.currentScreen instanceof HandledScreen;
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = minecraftClient.player.getInventory().getStack(i);
                    Item item = this.list.get(i);
                    if (this.unstackable.getValue().booleanValue() && stack.isEmpty() && item != Items.AIR && !z) {
                        if (is2849(item, i)) {
                            this.stopwatch.reset();
                            return;
                        }
                    }
                    this.list.set(i, stack.getItem());
                }
                if (!z || (minecraftClient.currentScreen instanceof InventoryScreen)) {
                    for (int i2 = 0; i2 < 9; i2++) {
                        if (is2852(i2)) {
                            this.stopwatch.reset();
                            return;
                        }
                    }
                }
            }
        }
    }

    public boolean is2849(Item item, int i) {
        if (!this.selection.getValue().is1391(item, this.items.getValue())) {
            return false;
        }
        boolean z = autoCrystal.isToggled() && autoCrystal.forceSuicide.getValue().booleanValue();
        int i2 = FireworksHelper.get444(item, itemStack -> {
            return (itemStack == minecraftClient.player.getOffHandStack() || (z && itemStack.getItem() == Items.TOTEM_OF_UNDYING)) ? false : true;
        }, true);
        if (i2 == -1 && item == Items.TOTEM_OF_UNDYING && !z) {
            i2 = FireworksHelper.get445(itemStack2 -> {
                return is2851(itemStack2.getItem()) && itemStack2 != minecraftClient.player.getOffHandStack();
            }, true);
        }
        if (i2 >= 36 || i2 == -1) {
            return false;
        }
        if (!is2850(i)) {
            FireworksHelper.do441(i2, FireworksHelper.get453(i));
            return true;
        }
        minecraftClient.interactionManager.clickSlot(minecraftClient.player.currentScreenHandler.syncId, i2, 0, SlotActionType.QUICK_MOVE, minecraftClient.player);
        return true;
    }

    public boolean is2850(int i) {
        for (int i2 = 0; i2 < 9; i2++) {
            if (i2 != i) {
                if (minecraftClient.player.getInventory().getStack(i2).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean is2851(Item item) {
        return item == Items.ENCHANTED_GOLDEN_APPLE || item == Items.GOLDEN_APPLE;
    }

    public boolean is2852(int i) {
        ItemStack stack = minecraftClient.player.getInventory().getStack(i);
        if (!this.selection.getValue().is1391(stack.getItem(), this.items.getValue())) {
            return false;
        }
        float intValue = this.threshold.getValue().intValue() / 64.0f;
        if (stack.isEmpty() || stack.getItem() == Items.AIR || !stack.isStackable() || stack.getCount() / stack.getMaxCount() >= intValue) {
            return false;
        }
        int i2 = -1;
        int i3 = 0;
        for (int i4 = 9; i4 < 36; i4++) {
            ItemStack stack2 = minecraftClient.player.getInventory().getStack(i4);
            boolean areItemsAndComponentsEqual = ItemStack.areItemsAndComponentsEqual(stack2, stack);
            if (!stack2.isEmpty() && areItemsAndComponentsEqual && stack2.getCount() > i3) {
                i2 = i4;
                i3 = stack2.getCount();
            }
        }
        if (xCarry.isToggled() && !(minecraftClient.currentScreen instanceof InventoryScreen)) {
            for (int i5 = 0; i5 <= 3; i5++) {
                ItemStack itemStack = (ItemStack) minecraftClient.player.playerScreenHandler.getCraftingInput().getHeldStacks().get(i5);
                boolean areItemsAndComponentsEqual2 = ItemStack.areItemsAndComponentsEqual(itemStack, stack);
                if (!itemStack.isEmpty() && areItemsAndComponentsEqual2 && itemStack.getCount() > i3) {
                    i2 = i5 + 1;
                    i3 = itemStack.getCount();
                }
            }
        }
        if (i2 == -1) {
            return false;
        }
        int i6 = minecraftClient.player.currentScreenHandler.syncId;
        if (!this.sort.getValue().booleanValue() || i3 == 1 || stack.getMaxCount() - stack.getCount() <= i3) {
            if (!FireworksHelper.is455()) {
                FireworksHelper.do441(i2, FireworksHelper.get453(i));
                return true;
            }
            minecraftClient.interactionManager.clickSlot(i6, i2, 0, SlotActionType.QUICK_MOVE, minecraftClient.player);
            return true;
        }
        BaritoneHelper_3.fireworksHelperSearchHelper4.do2630(true);
        minecraftClient.interactionManager.clickSlot(i6, i2, 0, SlotActionType.PICKUP, minecraftClient.player);
        minecraftClient.interactionManager.clickSlot(i6, i2, 1, SlotActionType.PICKUP, minecraftClient.player);
        minecraftClient.interactionManager.clickSlot(i6, FireworksHelper.get453(i), 0, SlotActionType.PICKUP, minecraftClient.player);
        BaritoneHelper_3.fireworksHelperSearchHelper4.do2630(false);
        return true;
    }
}
