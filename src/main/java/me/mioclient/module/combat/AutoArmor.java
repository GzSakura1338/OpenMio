package me.mioclient.module.combat;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.mioclient.ArmorSearchHelper4;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.FontsSearchHelper4_2;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper_3;
import me.mioclient.SearchHelper_4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.IllegalConstructorCall;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.screen.slot.SlotActionType;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/AutoArmor.class */
public class AutoArmor extends Module {
    public Setting<Integer> delay;
    public Setting<Boolean> preferences;
    public Setting<Boolean> bindingCurse;
    public Setting<Boolean> blast;
    public Setting<Boolean> thorns;
    public Setting<Boolean> elytra;
    public Setting<Boolean> turtleShell;
    public Setting<Boolean> noHelmet;
    public Setting<Boolean> onlySafe;
    public Setting<Boolean> safe;
    public Setting<Integer> safeAmount;
    public Setting<Boolean> inRange;
    public Setting<Float> range;
    public Setting<Boolean> allowFriends;
    public final Stopwatch stopwatch;
    public final Stopwatch stopwatch2;
    public final Stopwatch stopwatch3;
    public Inner inner;
    public boolean flag;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/AutoArmor$AutoArmorMode.class */
    public enum AutoArmorMode {
        HELMET(39, 3),
        CHESTPLATE(38, 2),
        LEGGINGS(37, 1),
        BOOTS(36, 0);

        public final int num;
        public final int num2;

        AutoArmorMode(int i, int i2) {
            this.num = i;
            this.num2 = i2;
        }

        public int get499() {
            return this.num;
        }

        public int get921() {
            return this.num2;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/AutoArmor$Inner.class */
    private class Inner {
        public boolean flag = false;
        public boolean flag2;
        public final AutoArmorMode autoArmorMode;
        public final int num;

        public Inner(AutoArmor autoArmor, AutoArmorMode autoArmorMode, int i) {
            this.autoArmorMode = autoArmorMode;
            this.num = i;
        }

        public void do2525(boolean z) {
            this.flag2 = z;
        }

        public boolean is2293() {
            return this.flag;
        }

        public void do1457() {
            if (!this.flag2) {
                int i = FireworksHelper.get453(this.num);
                BaritoneHelper_3.fireworksHelperSearchHelper4.do2630(true);
                FireworksHelper.do441(i, 8 - this.autoArmorMode.get921());
                BaritoneHelper_3.fireworksHelperSearchHelper4.do2630(false);
                this.flag = true;
                return;
            }
            SearchHelper_4.minecraftClient.interactionManager.clickSlot(SearchHelper_4.minecraftClient.player.currentScreenHandler.syncId, 8 - this.autoArmorMode.num2, 0, SlotActionType.QUICK_MOVE, SearchHelper_4.minecraftClient.player);
            this.flag = true;
        }
    }

    public AutoArmor() {
        super("AutoArmor", "Equips the best armor in your inventory for you.", Category.COMBAT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.stopwatch2 = new Stopwatch();
        this.stopwatch3 = new Stopwatch();
        this.inner = null;
        Runnable runnable = () -> {
            this.stopwatch.setTime(-1L);
        };
        this.blast.do2339(runnable);
        this.elytra.do2339(runnable);
        this.turtleShell.do2339(runnable);
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.inner = null;
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (this.flag != BaritoneHelper_3.holeSnapSearchHelper4_5.is2728()) {
            this.stopwatch3.reset();
        }
        this.flag = BaritoneHelper_3.holeSnapSearchHelper4_5.is2728();
        if (is1096(minecraftClient.currentScreen)) {
            if (this.stopwatch.is419(this.delay.getValue().intValue())) {
                if (minecraftClient.player.isFallFlying()) {
                    this.stopwatch2.reset();
                }
                if (HoleSnapSearchHelper4.is2014(minecraftClient.player) && !minecraftClient.player.isFallFlying() && !SearchHelper_3.is647(minecraftClient.player) && !this.stopwatch.is419(300L) && !this.stopwatch2.is419(500L)) {
                    AutoSignSearchHelper4.do948();
                    minecraftClient.player.startFallFlying();
                }
                boolean z = false;
                if (this.inRange.getValue().booleanValue()) {
                    for (AbstractClientPlayerEntity abstractClientPlayerEntity : minecraftClient.world.getPlayers()) {
                        if (abstractClientPlayerEntity != minecraftClient.player && abstractClientPlayerEntity.distanceTo(minecraftClient.player) <= this.range.getValue().floatValue()) {
                            if (!BaritoneHelper_3.searchHelper4_14.is519(abstractClientPlayerEntity.getGameProfile().getName()) || !this.allowFriends.getValue().booleanValue()) {
                                z = true;
                            }
                        }
                    }
                } else {
                    z = true;
                }
                if (z) {
                    if (this.inner != null && !this.inner.is2293()) {
                        this.inner.do1457();
                        this.stopwatch.reset();
                        return;
                    }
                    AutoArmorMode[] values = AutoArmorMode.values();
                    int length = values.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        AutoArmorMode autoArmorMode = values[i];
                        ItemStack stack = minecraftClient.player.getInventory().getStack(autoArmorMode.get499());
                        if (!IllegalConstructorCall.is1416(Enchantments.BINDING_CURSE, stack)) {
                            double d = get1095(stack, autoArmorMode);
                            int i2 = autoArmorMode.get499();
                            for (int i3 = 0; i3 < 36; i3++) {
                                double d2 = get1095(minecraftClient.player.getInventory().getStack(i3), autoArmorMode);
                                if (d2 > d) {
                                    d = d2;
                                    i2 = i3;
                                }
                            }
                            if (i2 != autoArmorMode.get499() && d != -1.0) {
                                this.inner = new Inner(this, autoArmorMode, i2);
                                break;
                            }
                        }
                        i++;
                    }
                    if (is1097()) {
                        if (this.inner == null || this.inner.is2293()) {
                            ItemStack stack2 = minecraftClient.player.getInventory().getStack(AutoArmorMode.HELMET.get499());
                            if (stack2.isEmpty() || IllegalConstructorCall.is1416(Enchantments.BINDING_CURSE, stack2)) {
                                return;
                            }
                            this.inner = new Inner(this, AutoArmorMode.HELMET, -1);
                            this.inner.do2525(true);
                        }
                    }
                }
            }
        }
    }

    public double get1095(ItemStack itemStack, AutoArmorMode autoArmorMode) {
        float f = FireworksHelper.get452(itemStack);
        if (this.safe.getValue().booleanValue() && f < this.safeAmount.getValue().intValue()) {
            return -1.0;
        }
        if (!this.bindingCurse.getValue().booleanValue() && IllegalConstructorCall.is1416(Enchantments.BINDING_CURSE, itemStack)) {
            return -1.0;
        }
        ArmorItem item = (itemStack.getItem()) instanceof ArmorItem ? (ArmorItem) (itemStack.getItem()) : null;
        if (item instanceof ArmorItem) {
            ArmorItem armorItem = item;
            if (armorItem.getSlotType().getEntitySlotId() == autoArmorMode.get921()) {
                double d = 0.0d;
                if (armorItem instanceof AnimalArmorItem) {
                    return -1.0;
                }
                if (is1097() && armorItem.getSlotType() == EquipmentSlot.HEAD) {
                    return -1.0;
                }
                if (this.turtleShell.getValue().booleanValue() && itemStack.isOf(Items.TURTLE_HELMET)) {
                    d = 9999.0;
                }
                double protection = d + (armorItem.getProtection() * 10) + (((int) armorItem.getToughness()) * 10);
                if (itemStack.hasEnchantments()) {
                    RegistryKey registryKey = (autoArmorMode == AutoArmorMode.LEGGINGS || this.blast.getValue().booleanValue()) ? Enchantments.BLAST_PROTECTION : Enchantments.PROTECTION;
                    for (Object2IntMap.Entry entry : itemStack.getEnchantments().getEnchantmentEntries()) {
                        if (!((RegistryEntry) entry.getKey()).isIn(EnchantmentTags.CURSE)) {
                            protection = entry.getKey() == registryKey ? protection + (entry.getValue().intValue() * 3) : protection + (entry.getValue().intValue() * 0.1);
                            if (((RegistryEntry) entry.getKey()).matchesKey(Enchantments.THORNS) && !this.thorns.getValue().booleanValue()) {
                                protection -= entry.getValue().intValue();
                            }
                        }
                    }
                }
                return protection;
            }
        }
        if (!itemStack.isOf(Items.ELYTRA) || autoArmorMode != AutoArmorMode.CHESTPLATE) {
            return -1.0;
        }
        if (!ElytraItem.isUsable(itemStack) || ArmorSearchHelper4.get1905(itemStack) <= 1) {
            return -1.0;
        }
        double longBitsToDouble = 1.0;
        if (this.elytra.getValue().booleanValue()) {
            longBitsToDouble *= 9999.0;
        }
        if (itemStack.hasEnchantments()) {
            longBitsToDouble = longBitsToDouble + IllegalConstructorCall.get1413(Enchantments.UNBREAKING, itemStack) + IllegalConstructorCall.get1413(Enchantments.MENDING, itemStack);
        }
        return longBitsToDouble;
    }

    public boolean is1096(Screen screen) {
        if (screen == null || (screen instanceof FontsSearchHelper4_2) || !(screen instanceof HandledScreen)) {
            return true;
        }
        return screen instanceof InventoryScreen;
    }

    public boolean is1097() {
        if (this.noHelmet.getValue().booleanValue()) {
            return !this.onlySafe.getValue().booleanValue() || (this.flag && this.stopwatch3.is419(500L));
        }
        return false;
    }
}
