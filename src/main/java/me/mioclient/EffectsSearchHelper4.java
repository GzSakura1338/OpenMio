package me.mioclient;

import java.util.function.Supplier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/EffectsSearchHelper4.class */
public class EffectsSearchHelper4 extends CryptoHelper implements SearchHelper_4 {
    public final RegistryEntry<StatusEffect> registryEntry;

    public EffectsSearchHelper4(RegistryEntry<StatusEffect> registryEntry, Supplier<Boolean> supplier) {
        super(() -> {
            StatusEffectInstance statusEffect = minecraftClient.player.getStatusEffect(registryEntry);
            if (statusEffect == null) {
                return Text.empty();
            }
            String string = StatusEffectUtil.getDurationText(statusEffect, 1.0f, minecraftClient.world.getTickManager().getTickRate()).getString();
            if (string.startsWith("0")) {
                string = string.substring(1);
            }
            return Text.literal("%s %s%s%s".formatted(((StatusEffect) registryEntry.value()).getName().getString(), statusEffect.getAmplifier() == 0 ? "" : new ArgumentTypeHelper().getArgumentTypeHelper2906(statusEffect.getAmplifier() + 1).getString2921("\u0001 "), Formatting.WHITE, string));
        }, supplier);
        this.registryEntry = registryEntry;
    }

    public RegistryEntry<StatusEffect> getRegistryEntry830() {
        return this.registryEntry;
    }
}
