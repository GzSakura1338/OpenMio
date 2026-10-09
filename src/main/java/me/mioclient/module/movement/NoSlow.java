package me.mioclient.module.movement;

import java.util.Iterator;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FireworksHelper;
import me.mioclient.FreecamHelper;
import me.mioclient.HUDHelper_2;
import me.mioclient.HUDSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.KeyPearlMode;
import me.mioclient.KeyPearlModeEvent;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinTextFieldWidgetHelper;
import me.mioclient.MixinTitleScreenSearchHelper4;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.PresetEnumSettingHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.InteractBlockEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickMovementEvent;
import me.mioclient.feature.Game;
import me.mioclient.feature.Stopwatch;
import me.mioclient.mixin.ducks.DuckPlayerMoveC2SPacket;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AbstractCommandBlockScreen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.gui.screen.ingame.StructureBlockScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/NoSlow.class */
public class NoSlow extends Module {
    public static AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public Setting<Boolean> guiMove;
    public Setting<Boolean> portals;
    public Setting<Boolean> items;
    public Setting<Boolean> sneak;
    public Setting<Boolean> crawl;
    public Setting<Boolean> strict;
    public Setting<NoSlowMode> mode;
    public Setting<Boolean> strictInventory;
    public Setting<Boolean> multiTask;
    public Setting<Boolean> blocks;
    public Setting<Boolean> ice;
    public Setting<Boolean> slime;
    public Setting<Boolean> noSlimeBounce;
    public Setting<Boolean> soulSand;
    public Setting<Boolean> honey;
    public Setting<Boolean> berryBush;
    public boolean flag;
    public boolean flag2;
    public boolean flag3;
    public final Stopwatch stopwatch;
    public final HUDHelper_2 hUDHelper_2;
    public final HUDHelper_2 hUDHelper_22;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/NoSlow$NoSlowMode.class */
    public enum NoSlowMode implements EnumSettingHelper {
        NONE("None"),
        NCP("NCP"),
        GRIM("Grim"),
        GRIMV3("Slow"),
        CONSTANTIAM("Constantiam");

        public final String name;

        NoSlowMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public NoSlow() {
        super("NoSlow", "Cancels several things that may slow you down.", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.hUDHelper_2 = new HUDHelper_2(2.0f, true);
        this.hUDHelper_22 = new HUDHelper_2(2.0f, true);
        setDrawn(false);
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        DuckPlayerMoveC2SPacket packet904 = (DuckPlayerMoveC2SPacket)(sendImmediatelyEvent.getPacket904());
        if (packet904 instanceof PlayerMoveC2SPacket) {
            DuckPlayerMoveC2SPacket duckPlayerMoveC2SPacket = (DuckPlayerMoveC2SPacket)((PlayerMoveC2SPacket) packet904);
            ItemStack stackInHand = minecraftClient.player.getStackInHand(minecraftClient.player.getActiveHand());
            if (minecraftClient.player.isUsingItem() && this.items.getValue().booleanValue() && !minecraftClient.player.isRiding() && !minecraftClient.player.isFallFlying() && stackInHand.contains(DataComponentTypes.FOOD) && is3106()) {
                minecraftClient.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(minecraftClient.player.getInventory().selectedSlot));
            }
            DuckPlayerMoveC2SPacket duckPlayerMoveC2SPacket2 = duckPlayerMoveC2SPacket;
            if (this.guiMove.getValue().booleanValue() && this.strictInventory.getValue().booleanValue() && !is3107() && this.flag && !minecraftClient.player.isRiding() && minecraftClient.player.isOnGround() && this.stopwatch.is419(200L) && !minecraftClient.player.isFallFlying() && this.mode.getValue() != NoSlowMode.GRIMV3) {
                duckPlayerMoveC2SPacket2.setOnGround(false);
                this.flag = false;
            }
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        this.flag2 = false;
        this.flag3 = false;
        if (minecraftClient.player.isOnGround()) {
            return;
        }
        this.stopwatch.reset();
    }

    @Listen
    public void onInteractBlock(InteractBlockEvent interactBlockEvent) {
        if (this.mode.getValue() == NoSlowMode.CONSTANTIAM && this.items.getValue().booleanValue() && !minecraftClient.player.isFallFlying()) {
            if (minecraftClient.player.getStackInHand(interactBlockEvent.getHand2084()).contains(DataComponentTypes.FOOD) && !PhaseESPSearchHelper4_2.is3051(interactBlockEvent.getBlockHitResult2585().getBlockPos())) {
                minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
                interactBlockEvent.do1162();
            }
        }
    }

    @Listen
    public void onEvent2(KeyPearlModeEvent keyPearlModeEvent) {
        if (keyPearlModeEvent.getKeyPearlMode1472() != KeyPearlMode.Pre || !this.strictInventory.getValue().booleanValue() || is3107() || !this.guiMove.getValue().booleanValue()) {
            if (keyPearlModeEvent.getKeyPearlMode1472() == KeyPearlMode.Post && HoleSnapSearchHelper4_3.is2181() && is3106() && this.guiMove.getValue().booleanValue()) {
                if (minecraftClient.player.isSprinting()) {
                    minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
                }
                if (minecraftClient.player.isSneaking()) {
                    minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
                    return;
                }
                return;
            }
            return;
        }
        if (!this.multiTask.getValue().booleanValue() && !this.flag2) {
            minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);
            this.flag2 = true;
        }
        if (HoleSnapSearchHelper4_3.is2181() && this.mode.getValue() != NoSlowMode.GRIMV3) {
            if (BaritoneHelper_3.antiPhaseSearchHelper4_2.get2231() > 1 && !this.flag3) {
                AutoSignSearchHelper4.do2562(minecraftClient.player.getX(), minecraftClient.player.getY() + 0.0626, minecraftClient.player.getZ(), false);
                this.flag3 = true;
                this.flag = true;
            }
            if (minecraftClient.player.isSprinting()) {
                minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
            }
            if (minecraftClient.player.isSneaking()) {
                minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
            }
        }
    }

    @Listen
    public void onTickMovement(TickMovementEvent tickMovementEvent) {
        if (this.items.getValue().booleanValue() && minecraftClient.player.isUsingItem() && !minecraftClient.player.hasVehicle()) {
            boolean z = true;
            if (this.mode.getValue() == NoSlowMode.GRIMV3) {
                z = minecraftClient.player.age % 3 == 0 || minecraftClient.player.age % 4 == 0;
                if (minecraftClient.player.age % 12 == 0) {
                    z = false;
                }
                if (minecraftClient.player.isFallFlying()) {
                    z = false;
                }
            }
            if (z) {
                minecraftClient.player.input.movementSideways /= 0.20000000298023224f;
                minecraftClient.player.input.movementForward /= 0.20000000298023224f;
            }
            if (is3107()) {
                do3102(minecraftClient.player.getActiveHand());
            }
        }
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (!is2669() || !this.guiMove.getValue().booleanValue() || (minecraftClient.currentScreen instanceof MixinTitleScreenSearchHelper4) || (minecraftClient.currentScreen instanceof HUDSearchHelper4)) {
            this.hUDHelper_22.do171(minecraftClient.player.getYaw());
            this.hUDHelper_2.do171(minecraftClient.player.getPitch());
            return;
        }
        if (is3105(264)) {
            this.hUDHelper_2.do1737(Math.min(MathHelper.lerp(inner_3.get473(), minecraftClient.player.prevPitch, minecraftClient.player.getPitch()) + 8.0f, FreecamHelper.num2));
        } else if (is3105(265)) {
            this.hUDHelper_2.do1737(Math.max(MathHelper.lerp(inner_3.get473(), minecraftClient.player.prevPitch, minecraftClient.player.getPitch()) - 8.0f, -FreecamHelper.num2));
        }
        if (is3105(262)) {
            this.hUDHelper_22.do1737(MathHelper.lerp(inner_3.get473(), minecraftClient.player.prevYaw, minecraftClient.player.getYaw()) + 8.0f);
        } else if (is3105(263)) {
            this.hUDHelper_22.do1737(MathHelper.lerp(inner_3.get473(), minecraftClient.player.prevYaw, minecraftClient.player.getYaw()) - 8.0f);
        }
        if (is3104()) {
            minecraftClient.player.setPitch(this.hUDHelper_2.get172());
            minecraftClient.player.setYaw(this.hUDHelper_22.get172());
        }
    }

    public void do3102(Hand hand) {
        Hand hand2015 = HoleSnapSearchHelper4.getHand2015(hand);
        if (!is3103(minecraftClient.player.getStackInHand(hand2015))) {
            AutoSignSearchHelper4.do2557(hand2015);
            return;
        }
        if (hand2015 == Hand.MAIN_HAND) {
            int i = minecraftClient.player.getInventory().selectedSlot;
            FireworksHelper.do438((i + 1) % 9);
            AutoSignSearchHelper4.do2557(hand2015);
            FireworksHelper.do438(i);
            return;
        }
        int i2 = FireworksHelper.get453(FireworksHelper.get445(itemStack -> {
            return !is3103(itemStack);
        }, true));
        minecraftClient.interactionManager.clickSlot(0, i2, 40, SlotActionType.SWAP, minecraftClient.player);
        AutoSignSearchHelper4.do2557(hand2015);
        minecraftClient.interactionManager.clickSlot(0, i2, 40, SlotActionType.SWAP, minecraftClient.player);
    }

    public boolean is3103(ItemStack itemStack) {
        return itemStack.contains(DataComponentTypes.FOOD) || itemStack.isOf(Items.BOW) || itemStack.isOf(Items.SHIELD) || itemStack.isOf(Items.ENDER_PEARL);
    }

    public boolean is2669() {
        if (minecraftClient.currentScreen instanceof MixinTitleScreenSearchHelper4) {
            Iterator<PresetEnumSettingHelper> it = BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().getArrayList2831().iterator();
            while (it.hasNext()) {
                PresetEnumSettingHelper next = it.next();
                if ((next instanceof Game) && next.is623()) {
                    return false;
                }
            }
            if (BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().textFieldWidget.isFocused() || !BaritoneHelper_3.getMixinTitleScreenSearchHelper42216().textFieldWidget.getText().isBlank()) {
                return false;
            }
        }
        return (minecraftClient.currentScreen == null || (minecraftClient.currentScreen instanceof ChatScreen) || (minecraftClient.currentScreen instanceof SignEditScreen) || (minecraftClient.currentScreen instanceof AnvilScreen) || (minecraftClient.currentScreen instanceof AbstractCommandBlockScreen) || (minecraftClient.currentScreen instanceof StructureBlockScreen) || (MixinTextFieldWidgetHelper.textFieldWidget != null && MixinTextFieldWidgetHelper.textFieldWidget.isActive())) ? false : true;
    }

    public boolean is3104() {
        return is3105(264) || is3105(265) || is3105(262) || is3105(263);
    }

    public boolean is3105(int i) {
        return GLFW.glfwGetKey(minecraftClient.getWindow().getHandle(), i) == 1;
    }

    public boolean is3106() {
        return this.mode.getValue() == NoSlowMode.NCP;
    }

    public boolean is3107() {
        return this.mode.getValue() == NoSlowMode.GRIM;
    }

    public boolean is3108() {
        return isToggled() && !this.multiTask.getValue().booleanValue() && minecraftClient.player.isUsingItem() && !minecraftClient.player.hasVehicle() && minecraftClient.player.getActiveHand() == Hand.MAIN_HAND;
    }
}
