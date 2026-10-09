package me.mioclient.module.movement;

import java.util.concurrent.TimeUnit;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapMode;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.InteractBlockEvent;
import me.mioclient.event.InteractItemEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.PlayEvent;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.mixin.ducks.DuckFireworkEntity;
import me.mioclient.module.Module;
import me.mioclient.module.exploit.RocketExtender;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/Fireworks.class */
public final class Fireworks extends Module {
    public static final RocketExtender rocketExtender = (RocketExtender) BaritoneHelper_3.baritoneHelper_4.getModule117(RocketExtender.class);
    public static final ElytraFly elytraFly = (ElytraFly) BaritoneHelper_3.baritoneHelper_4.getModule117(ElytraFly.class);
    public Setting<Boolean> antiWaste;
    public Setting<Boolean> boost;
    public Setting<Boolean> withElytraFly;
    public Setting<Float> boostFactor;
    public Setting<Boolean> autoLaunch;
    public Setting<Boolean> onlyElytraFly;
    public Setting<Boolean> onlyFirst;
    public Setting<Boolean> await;
    public Setting<Float> delay;
    public Setting<Boolean> fastLaunch;
    public Setting<Integer> ticks;
    public Setting<Boolean> flight;
    public Setting<Float> horizontal;
    public Setting<Float> vertical;
    public final Stopwatch stopwatch;
    public boolean flag;
    public int num;
    public int num2;
    public boolean flag2;
    public boolean flag3;

    public Fireworks() {
        super("Fireworks", "Enhances the usage of firework rockets.", Category.MOVEMENT, "rockets");
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.delay.getSetting2338("Auto", HoleSnapMode.MIN);
    }

    @Listen
    public void onInteractBlock(InteractBlockEvent interactBlockEvent) {
        if (this.antiWaste.getValue().booleanValue()) {
            if (minecraftClient.player.getStackInHand(interactBlockEvent.getHand2084()).isOf(Items.FIREWORK_ROCKET)) {
                minecraftClient.interactionManager.interactItem(minecraftClient.player, interactBlockEvent.getHand2084());
                minecraftClient.player.swingHand(interactBlockEvent.getHand2084());
                interactBlockEvent.do1162();
            }
        }
    }

    @Listen
    public void onInteractItem(InteractItemEvent interactItemEvent) {
        if (is144()) {
            if (minecraftClient.player.isHolding(Items.FIREWORK_ROCKET)) {
                this.num2 = 0;
                do145();
                this.num = this.ticks.getValue().intValue();
                this.flag3 = true;
            }
        }
    }

    @Listen
    public void do32(TickPostEvent tickPostEvent) {
        if (this.flag2) {
            minecraftClient.player.setSneaking(false);
            AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY, 0);
            this.flag2 = false;
            if (!HoleSnapSearchHelper4.is2014(minecraftClient.player)) {
                minecraftClient.player.stopFallFlying();
            }
        }
        if (this.num > 0) {
            do145();
            this.num--;
            this.flag2 = this.num == 0;
        }
        if (this.autoLaunch.getValue().booleanValue()) {
            if (elytraFly.isToggled() || !this.onlyElytraFly.getValue().booleanValue()) {
                if (!minecraftClient.player.isFallFlying()) {
                    this.stopwatch.setTime(-1L);
                }
                boolean z = (this.stopwatch.is418((double) this.delay.getValue().floatValue(), TimeUnit.SECONDS) && (!rocketExtender.isToggled() || rocketExtender.num == -1 || rocketExtender.blockPos == null)) ? false : true;
                if (is141()) {
                    z = true;
                }
                if (z) {
                    return;
                }
                if (!minecraftClient.player.isFallFlying()) {
                    this.flag = false;
                }
                if (this.flag && this.onlyFirst.getValue().booleanValue()) {
                    return;
                }
                do139();
            }
        }
    }

    @Listen(get219= Helper_7.num4)
    public void do28(MoveEvent moveEvent) {
        if (this.flight.getValue().booleanValue() && this.num > 1 && is141()) {
            do138(moveEvent);
        }
        if (minecraftClient.player.isFallFlying() && this.await.getValue().booleanValue() && this.autoLaunch.getValue().booleanValue() && !is141()) {
            moveEvent.do690(new Vec3d(0.0d, 0.0d, 0.0d));
        }
    }

    @Listen
    public void onPlay(PlayEvent playEvent) {
        if (this.num <= 0 || playEvent.getSoundInstance1914() == null || playEvent.getSoundInstance1914().getId() == null || !playEvent.getSoundInstance1914().getId().toString().contains("item.armor.equip")) {
            return;
        }
        playEvent.do1162();
    }

    public void do138(MoveEvent moveEvent) {
        float intBitsToFloat = -0.0010000000474974513f;
        if (minecraftClient.player.input.jumping) {
            intBitsToFloat = this.vertical.getValue().floatValue();
        } else if (minecraftClient.player.input.sneaking) {
            intBitsToFloat = -this.vertical.getValue().floatValue();
        }
        float floatValue = this.horizontal.getValue().floatValue();
        if (floatValue > 1.6) {
            floatValue = MathHelper.clamp(1.600000023841858f + (this.num2 * 0.10000000149011612f), 1.600000023841858f, floatValue);
            this.num2++;
        }
        double[] doubleArray2507 = HoleSnapSearchHelper4_3.getDoubleArray2507(moveEvent, floatValue);
        Vec3d vec3d = new Vec3d(doubleArray2507[0], intBitsToFloat, doubleArray2507[1]);
        minecraftClient.player.setVelocity(vec3d);
        moveEvent.do690(vec3d);
    }

    public void do139() {
        if (this.autoLaunch.getValue().booleanValue() && minecraftClient.player.isFallFlying()) {
            Hand hand450 = FireworksHelper.getHand450(Items.FIREWORK_ROCKET);
            int i = minecraftClient.player.getInventory().selectedSlot;
            int i2 = FireworksHelper.get443(Items.FIREWORK_ROCKET);
            int i3 = FireworksHelper.get447(Items.FIREWORK_ROCKET);
            if (hand450 != null) {
                minecraftClient.interactionManager.interactItem(minecraftClient.player, hand450);
                this.stopwatch.reset();
            } else if (i2 != -1) {
                boolean z = i3 == -1;
                do140(z ? i2 : i3, z);
                minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
                rocketExtender.vec3d = minecraftClient.player.getPos();
                do140(z ? i2 : i, z);
                this.stopwatch.reset();
            }
            this.flag = true;
        }
    }

    public void do140(int i, boolean z) {
        if (z) {
            FireworksHelper.do439(i);
        } else {
            FireworksHelper.do456(i);
        }
    }

    public boolean is141() {
        if (!this.stopwatch.is419(500L)) {
            return true;
        }
        for (Entity entity : minecraftClient.world.getEntities()) {
            if ((entity instanceof DuckFireworkEntity) && ((DuckFireworkEntity) entity).mio$getShooter() == minecraftClient.player) {
                return true;
            }
        }
        return false;
    }

    public float get142(boolean z) {
        if (!isToggled() || !this.boost.getValue().booleanValue()) {
            return 0.0f;
        }
        if ((z || this.withElytraFly.getValue().booleanValue()) && is141()) {
            return this.boostFactor.getValue().floatValue();
        }
        return 0.0f;
    }

    public int get143() {
        return this.num;
    }

    public boolean is144() {
        return (HoleSnapSearchHelper4.is2014(minecraftClient.player) || !isToggled() || !this.fastLaunch.getValue().booleanValue() || minecraftClient.player.isOnGround() || FireworksHelper.get443(Items.ELYTRA) == -1) ? false : true;
    }

    public void do145() {
        int i = FireworksHelper.get447(Items.ELYTRA);
        boolean z = i == -1;
        if (i == -1) {
            i = FireworksHelper.get443(Items.ELYTRA);
        }
        if (i == -1) {
            return;
        }
        int i2 = i;
        if (z) {
            i = 0;
            minecraftClient.interactionManager.clickSlot(0, i2, 0, SlotActionType.SWAP, minecraftClient.player);
        }
        minecraftClient.interactionManager.clickSlot(0, 6, i, SlotActionType.SWAP, minecraftClient.player);
        AutoSignSearchHelper4.do948();
        minecraftClient.player.startFallFlying();
        minecraftClient.interactionManager.clickSlot(0, 6, i, SlotActionType.SWAP, minecraftClient.player);
        if (z) {
            minecraftClient.interactionManager.clickSlot(0, i2, 0, SlotActionType.SWAP, minecraftClient.player);
        }
    }
}
