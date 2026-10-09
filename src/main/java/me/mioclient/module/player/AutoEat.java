package me.mioclient.module.player;

import java.util.Iterator;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FireworksHelper;
import me.mioclient.KeyPearlMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper_3;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.Module;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/player/AutoEat.class */
public class AutoEat extends Module {
    public Setting<Integer> health;
    public Setting<Integer> hunger;
    public Setting<Boolean> pauseBaritone;
    public Setting<Boolean> autoSwap;
    public Setting<AutoEatMode> sort;
    public Setting<Boolean> swapBack;
    public Setting<Boolean> autoEscape;
    public Setting<Boolean> autoDisable;
    public int num;
    public boolean flag;
    public int num2;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/player/AutoEat$AutoEatMode.class */
    public static enum AutoEatMode implements EnumSettingHelper {
        autoEatMode("Hunger") {
            @Override
            public double get872(FoodComponent foodComponent) {
                return foodComponent.nutrition();
            }
        },
        autoEatMode2("Saturation") {
            @Override
            public double get872(FoodComponent foodComponent) {
                return foodComponent.saturation();
            }
        },
        autoEatMode3("Health") {
            @Override
            public double get872(FoodComponent foodComponent) {
                Iterator it = foodComponent.effects().iterator();
                while (it.hasNext()) {
                    FoodComponent.StatusEffectEntry statusEffectEntry = (FoodComponent.StatusEffectEntry) it.next();
                    if (statusEffectEntry.effect().getEffectType() == StatusEffects.REGENERATION) {
                        return statusEffectEntry.effect().getAmplifier();
                    }
                }
                return 0.0d;
            }
        };

        public final String name;

        AutoEatMode(String str2) {
            this.name = str2;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public double get872(FoodComponent foodComponent) {
            return 0.0d;
        }
    }

    public AutoEat() {
        super("AutoEat", "Eats your food for you.", Category.PLAYER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.pauseBaritone.do2344();
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        do2089(false);
        this.num = -1;
        this.num2 = -1;
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        minecraftClient.options.useKey.setPressed(false);
        BaritoneHelper_3.obstaclePasserHelper.do700(this);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00e7, code lost:
    
        if (me.mioclient.module.player.AutoEat.minecraftClient.player.getInventory().getStack(r6).isOf(net.minecraft.item.Items.CHORUS_FRUIT) == false) goto L17;
     */
    @Listen
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onResetEvent(MotionEvent motionEvent) {
        int i;
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Post) {
            return;
        }
        BaritoneHelper_3.obstaclePasserHelper.do700(this);
        if (!this.autoSwap.getValue().booleanValue() && this.flag) {
            if (!minecraftClient.player.getMainHandStack().contains(DataComponentTypes.FOOD)) {
                minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);
                do2089(false);
                return;
            }
        }
        int i2 = get2085();
        if (i2 != -1) {
        }
        if (is2087() && (i = FireworksHelper.get443(Items.CHORUS_FRUIT)) != -1) {
            i2 = -1;
            this.num2 = i;
            do438(this.num2);
        }
        if (is2088() && !is2087()) {
            if (this.flag) {
                minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);
                do2089(false);
                if (this.autoSwap.getValue().booleanValue() && this.swapBack.getValue().booleanValue() && this.num != -1) {
                    FireworksHelper.do456(this.num);
                }
                if (this.num2 != -1) {
                    do438(this.num2);
                    this.num2 = -1;
                }
                if (this.autoDisable.getValue().booleanValue() && this.autoEscape.getValue().booleanValue()) {
                    disable();
                }
                this.num = -1;
                return;
            }
            return;
        }
        boolean z = i2 == 40;
        if (this.autoSwap.getValue().booleanValue() && !z && i2 != -1 && minecraftClient.player.getInventory().selectedSlot != i2) {
            this.num = minecraftClient.player.getInventory().selectedSlot;
            FireworksHelper.do456(i2);
        }
        ItemStack mainHandStack = minecraftClient.player.getMainHandStack();
        if (!minecraftClient.player.isUsingItem() && (z || mainHandStack.contains(DataComponentTypes.FOOD))) {
            minecraftClient.interactionManager.interactItem(minecraftClient.player, z ? Hand.OFF_HAND : Hand.MAIN_HAND);
            do2089(true);
        }
        if (this.pauseBaritone.getValue().booleanValue() && minecraftClient.player.isUsingItem()) {
            BaritoneHelper_3.obstaclePasserHelper.do699(this);
        }
    }

    public int get2085() {
        double longBitsToDouble = -1.0;
        int i = -1;
        int i2 = 0;
        while (true) {
            if (i2 >= 10) {
                break;
            }
            if (i2 == 9) {
                i2 = 40;
            }
            ItemStack stack = minecraftClient.player.getInventory().getStack(i2);
            FoodComponent foodComponent = (FoodComponent) stack.get(DataComponentTypes.FOOD);
            if (foodComponent != null && (foodComponent.canAlwaysEat() || minecraftClient.player.getHungerManager().getFoodLevel() < 20)) {
                double d = this.sort.getValue().get872(foodComponent);
                if (d > longBitsToDouble || (d == longBitsToDouble && i2 == minecraftClient.player.getInventory().selectedSlot)) {
                    longBitsToDouble = d;
                    i = i2;
                }
                if (stack.getItem() == Items.CHORUS_FRUIT && is2087()) {
                    i = i2;
                    break;
                }
            }
            i2++;
        }
        return i;
    }

    public boolean is2086() {
        return this.flag;
    }

    public boolean is2087() {
        if (!this.autoEscape.getValue().booleanValue()) {
            return false;
        }
        ClientPlayerEntity clientPlayerEntity = null;
        Iterator it = minecraftClient.world.getPlayers().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ClientPlayerEntity clientPlayerEntity2 = (ClientPlayerEntity)((PlayerEntity) it.next());
            if (((PlayerEntity) clientPlayerEntity2).distanceTo(minecraftClient.player) <= 12.0f && !BaritoneHelper_3.searchHelper4_14.is520((PlayerEntity) clientPlayerEntity2) && clientPlayerEntity2 != minecraftClient.player) {
                clientPlayerEntity = clientPlayerEntity2;
                break;
            }
        }
        return clientPlayerEntity != null && minecraftClient.player.age <= 50;
    }

    public boolean is2088() {
        return (this.hunger.getValue().intValue() == -1 || minecraftClient.player.getHungerManager().getFoodLevel() > this.hunger.getValue().intValue()) && (this.health.getValue().intValue() == 0 || SearchHelper_3.get643() > ((float) this.health.getValue().intValue()));
    }

    public void do2089(boolean z) {
        if (this.flag == z) {
            return;
        }
        this.flag = z;
    }

    public void do438(int i) {
        minecraftClient.interactionManager.clickSlot(minecraftClient.player.currentScreenHandler.syncId, i, minecraftClient.player.getInventory().selectedSlot, SlotActionType.SWAP, minecraftClient.player);
    }
}
