package me.mioclient.module.render;

import java.awt.Color;
import java.util.Calendar;
import me.mioclient.EnumSettingHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.biome.Biome;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Ambience.class */
public class Ambience extends Module {
    public Setting<MixinEntityRendererMode> brightness;
    public Setting<Color> color;
    public Setting<Integer> lightLevel;
    public Setting<Boolean> worldTime;
    public Setting<Boolean> sync;
    public Setting<Float> time;
    public Setting<Boolean> worldWeather;
    public Setting<AmbiencePredicateMode> weather;
    public Setting<Boolean> force;
    public Setting<Float> amount;
    public double val;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/Ambience$AmbiencePredicateMode.class */
    public enum AmbiencePredicateMode implements EnumSettingHelper {
        CLEAR("Clear", Biome.Precipitation.NONE),
        SNOW("Snow", Biome.Precipitation.SNOW),
        RAIN("Rain", Biome.Precipitation.RAIN),
        DUSTY("Dusty", Biome.Precipitation.NONE);

        public final String name;
        public final Biome.Precipitation precipitation;

        AmbiencePredicateMode(String str, Biome.Precipitation precipitation) {
            this.name = str;
            this.precipitation = precipitation;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public Biome.Precipitation getPrecipitation2196() {
            return this.precipitation;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/Ambience$MixinEntityRendererMode.class */
    public enum MixinEntityRendererMode implements EnumSettingHelper {
        SCREEN("Screen"),
        GAMMA("Gamma"),
        SKY("Sky"),
        POTION("Potion"),
        NONE("None");

        public final String name;

        MixinEntityRendererMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public Ambience() {
        super("Ambience", "Changes several ambient things.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.lightLevel.do2339(() -> {
            if (this.brightness.getValue() != MixinEntityRendererMode.SKY || minecraftClient.world == null) {
                return;
            }
            minecraftClient.worldRenderer.reload();
        });
        this.brightness.do2339(() -> {
            if (is1469()) {
                return;
            }
            if (minecraftClient.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                if (minecraftClient.player.getStatusEffect(StatusEffects.NIGHT_VISION).getAmplifier() == 68) {
                    minecraftClient.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                }
            }
            if (this.brightness.getValue() != MixinEntityRendererMode.GAMMA) {
                ((me.mioclient.ZoomHelper_3) (Object) minecraftClient.options.getGamma()).forceSetValue(Double.valueOf(this.val));
            }
        });
        setDrawn(false);
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.val = ((Double) minecraftClient.options.getGamma().getValue()).doubleValue();
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        if (!is1469()) {
            minecraftClient.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
        ((me.mioclient.ZoomHelper_3) (Object) minecraftClient.options.getGamma()).forceSetValue(Double.valueOf(this.val));
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        if (minecraftClient.worldRenderer == null) {
            return;
        }
        if (this.brightness.getValue() == MixinEntityRendererMode.SKY || this.brightness.getValue() == MixinEntityRendererMode.SCREEN) {
            minecraftClient.worldRenderer.reload();
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (is1469()) {
            return;
        }
        if (this.worldTime.getValue().booleanValue()) {
            long j = get2923();
            minecraftClient.world.setTime(j);
            minecraftClient.world.setTimeOfDay(j);
        }
        if (this.brightness.getValue() == MixinEntityRendererMode.GAMMA) {
            ((me.mioclient.ZoomHelper_3) (Object) minecraftClient.options.getGamma()).forceSetValue(Double.valueOf(1000.0));
        } else if (this.brightness.getValue() == MixinEntityRendererMode.POTION) {
            if (minecraftClient.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                return;
            }
            minecraftClient.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, -1, 68));
        }
    }

    public long get2923() {
        if (!this.sync.getValue().booleanValue()) {
            return (long) ((this.time.getValue().floatValue() * 1000.0f) + 18000.0f);
        }
        Calendar calendar = Calendar.getInstance();
        return (long) (((calendar.get(11) + (calendar.get(12) / 60.0f)) * 1000.0f) + 18000.0f);
    }

    public boolean is2924() {
        return isToggled() && this.worldWeather.getValue().booleanValue() && this.force.getValue().booleanValue();
    }
}
