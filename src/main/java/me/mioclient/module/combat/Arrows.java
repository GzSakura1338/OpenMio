package me.mioclient.module.combat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import me.mioclient.AutoCrystalHelper_3;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.HoleSnapEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.StopUsingItemEvent;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/Arrows.class */
public class Arrows extends Module {
    public static final AtomicBoolean atomicBoolean = new AtomicBoolean();
    public Setting<Boolean> autoShoot;
    public Setting<Float> fov;
    public Setting<Integer> assumeDuration;
    public final Map<StatusEffect, Long> map;
    public final Stopwatch stopwatch;
    public int num;
    public Potion potion;

    public Arrows() {
        super("Arrows", "Will swap between effect arrows in your inventory.", Category.COMBAT, "projectiles");
        PhaseESPHelper.do1351(this);
        this.map = new HashMap();
        this.stopwatch = new Stopwatch();
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        if (atomicBoolean.get()) {
            disable();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x008f, code lost:
    
        if (get1935(me.mioclient.module.combat.Arrows.minecraftClient.player.getInventory().getStack(r5.num), r0) <= 0) goto L13;
     */
    @Listen
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onStopUsingItem(StopUsingItemEvent stopUsingItemEvent) {
        PlayerEntity playerEntity1934 = getPlayerEntity1934();
        Hand activeHand = minecraftClient.player.getActiveHand();
        if (activeHand == null || !(minecraftClient.player.getStackInHand(activeHand).getItem() instanceof RangedWeaponItem)) {
            return;
        }
        if (playerEntity1934 != null) {
            if (this.num != -1) {
            }
            int i = minecraftClient.player.getInventory().selectedSlot;
            FireworksHelper.do456((i + 1) % 9);
            AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN);
            FireworksHelper.do456(i);
            stopUsingItemEvent.do1162();
            return;
        }
        if (this.stopwatch.is419(50L)) {
            for (int i2 = 0; i2 < minecraftClient.player.getInventory().size(); i2++) {
                if (minecraftClient.player.getInventory().getStack(i2).getItem() instanceof ArrowItem) {
                    if (i2 != this.num) {
                        this.stopwatch.reset();
                        BaritoneHelper_3.fireworksHelperSearchHelper4.do2630(true);
                        FireworksHelper.do441(FireworksHelper.get453(this.num), FireworksHelper.get453(i2));
                        BaritoneHelper_3.fireworksHelperSearchHelper4.do2630(false);
                        this.num = -1;
                    }
                    if (this.potion == null) {
                        return;
                    }
                    Iterator it = this.potion.getEffects().iterator();
                    while (it.hasNext()) {
                        this.map.put((StatusEffect) ((StatusEffectInstance) it.next()).getEffectType().value(), Long.valueOf(System.currentTimeMillis()));
                    }
                    return;
                }
            }
        }
    }

    @Listen
    public void onTickPost(TickPostEvent tickPostEvent) {
        if (minecraftClient.player.getMainHandStack().getItem() instanceof RangedWeaponItem) {
            ClientPlayerEntity playerEntity1934 = (ClientPlayerEntity)(getPlayerEntity1934());
            if (playerEntity1934 == null) {
                playerEntity1934 = minecraftClient.player;
            }
            this.map.entrySet().removeIf(entry -> {
                return ((Long) entry.getValue()).longValue() + 750 < System.currentTimeMillis();
            });
            int i = -1;
            int i2 = -1;
            for (int i3 = 0; i3 < minecraftClient.player.getInventory().size(); i3++) {
                int i4 = get1935(minecraftClient.player.getInventory().getStack(i3), playerEntity1934);
                if (i4 >= 0 && i4 > i) {
                    i2 = i3;
                    i = i4;
                }
            }
            this.num = i2;
            if (this.num != -1) {
                this.potion = AutoCrystalHelper_3.getPotion1565(minecraftClient.player.getInventory().getStack(this.num));
            }
        }
    }

    @Listen
    public void onEvent2(HoleSnapEvent holeSnapEvent) {
        if (this.autoShoot.getValue().booleanValue()) {
            if (this.num != -1) {
                if (get1935(minecraftClient.player.getInventory().getStack(this.num), minecraftClient.player) > 0) {
                    do1937();
                    return;
                }
            }
            disable();
        }
    }

    public PlayerEntity getPlayerEntity1934() {
        if (!this.autoShoot.getValue().booleanValue() && minecraftClient.player.getPitch() >= -85.0f) {
            AbstractClientPlayerEntity abstractClientPlayerEntity = null;
            double longBitsToDouble = 3.4028234663852886e+38;
            for (AbstractClientPlayerEntity abstractClientPlayerEntity2 : minecraftClient.world.getPlayers()) {
                if (minecraftClient.player != abstractClientPlayerEntity2 && !BaritoneHelper_3.searchHelper4_14.is520((PlayerEntity) abstractClientPlayerEntity2)) {
                    float[] floatArray2483 = SearchHelper4_8.getFloatArray2483((Entity) abstractClientPlayerEntity2);
                    float angleBetween = MathHelper.angleBetween(minecraftClient.player.getYaw(), floatArray2483[0]) - (abstractClientPlayerEntity2.getDimensions(abstractClientPlayerEntity2.getPose()).width() * 0.5f);
                    float angleBetween2 = MathHelper.angleBetween(minecraftClient.player.getPitch(), floatArray2483[1]) - (abstractClientPlayerEntity2.getDimensions(abstractClientPlayerEntity2.getPose()).height() * 0.5f);
                    if (angleBetween <= this.fov.getValue().floatValue() && angleBetween2 <= this.fov.getValue().floatValue()) {
                        double hypot = Math.hypot(angleBetween, angleBetween2);
                        if (hypot < longBitsToDouble) {
                            abstractClientPlayerEntity = abstractClientPlayerEntity2;
                            longBitsToDouble = hypot;
                        }
                    }
                }
            }
            return abstractClientPlayerEntity;
        }
        return minecraftClient.player;
    }

    public int get1935(ItemStack itemStack, Object obj) {
        if (!(itemStack.getItem() instanceof ArrowItem) || !(obj instanceof PlayerEntity)) {
            return -1;
        }
        PlayerEntity playerEntity = (PlayerEntity) obj;
        Potion potion1565 = AutoCrystalHelper_3.getPotion1565(itemStack);
        boolean z = playerEntity == minecraftClient.player || BaritoneHelper_3.searchHelper4_14.is520(playerEntity);
        int i = potion1565.getEffects().isEmpty() ? z ? -1 : 1 : 0;
        for (StatusEffectInstance statusEffectInstance : potion1565.getEffects()) {
            RegistryEntry<StatusEffect> effectType = statusEffectInstance.getEffectType();
            boolean hasStatusEffect = playerEntity.hasStatusEffect(effectType);
            if (hasStatusEffect && ((me.mioclient.ArrowsHelper)(Object) playerEntity.getStatusEffect(effectType)).mio$getDurationRation() * 100.0f < this.assumeDuration.getValue().intValue()) {
                hasStatusEffect = false;
            }
            if (!hasStatusEffect) {
                if (!this.map.containsKey(effectType.value())) {
                    int amplifier = (!((StatusEffect) effectType.value()).isInstant() ? statusEffectInstance.getAmplifier() : 0) + 1;
                    if (is1936(effectType) != z) {
                        amplifier *= -1;
                    }
                    i += amplifier;
                }
            }
        }
        return i;
    }

    public boolean is1936(RegistryEntry<StatusEffect> registryEntry) {
        if (registryEntry == StatusEffects.SLOW_FALLING) {
            return false;
        }
        return ((StatusEffect) registryEntry.value()).isBeneficial();
    }

    public void do1937() {
        int i = minecraftClient.player.getInventory().selectedSlot;
        int i2 = FireworksHelper.get447(Items.BOW);
        BaritoneHelper_3.searchHelper4_8.do2477(new float[]{minecraftClient.player.getYaw(), -90.0f}, 999);
        if (i2 == -1 || minecraftClient.player.isUsingItem()) {
            return;
        }
        FireworksHelper.do438(i2);
        minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
        if (atomicBoolean.get()) {
            return;
        }
        atomicBoolean.set(true);
        BaritoneHelper_3.tooltipsSearchHelper4_2.do164(() -> {
            minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);
            FireworksHelper.do438(i);
            atomicBoolean.set(false);
            disable();
        }, 3);
    }
}
