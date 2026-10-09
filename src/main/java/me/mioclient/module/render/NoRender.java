package me.mioclient.module.render;

import java.util.Set;
import me.mioclient.EntityEvent;
import me.mioclient.EntityEvent_2;
import me.mioclient.EnumSettingHelper;
import me.mioclient.Helper_7;
import me.mioclient.PhaseESPHelper;
import me.mioclient.ScaffoldMode_2;
import me.mioclient.SearchHelper4_7;
import me.mioclient.ShaderSearchHelper4;
import me.mioclient.StashFinderMode;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.AddParticleEvent;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.GetStatusEffectEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.RenderLabelEvent;
import me.mioclient.module.Module;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.client.particle.ExplosionSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.BlockPos;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/NoRender.class */
public class NoRender extends Module {
    public Setting<Boolean> self;
    public Setting<Boolean> hurtCam;
    public Setting<Boolean> potions;
    public Setting<Boolean> darkness;
    public Setting<Boolean> blindness;
    public Setting<Boolean> newSneaking;
    public Setting<Boolean> totemOverlay;
    public Setting<Boolean> hands;
    public Setting<Float> opacity2;
    public Setting<Boolean> ui;
    public Setting<Boolean> messageIndicator;
    public Setting<Boolean> unsecureServer;
    public Setting<Boolean> tutorialToast;
    public Setting<Boolean> textShadow;
    public Setting<Boolean> bossBars;
    public Setting<Boolean> scoreBoard;
    public Setting<Boolean> tabIcons;
    public Setting<Boolean> heldTooltips;
    public Setting<Boolean> tint;
    public Setting<Boolean> world;
    public Setting<Boolean> explosions;
    public Setting<Boolean> worldBorder;
    public Setting<Boolean> skyLight;
    public Setting<Boolean> fog;
    public Setting<Float> range;
    public Setting<Boolean> sky;
    public Setting<Boolean> particles;
    public Setting<Set<ParticleType<?>>> particleTypes;
    public Setting<ScaffoldMode_2> selection2;
    public Setting<Boolean> noBlockBreaking;
    public Setting<Boolean> entities;
    public Setting<Boolean> hurt;
    public Setting<Boolean> nameTags;
    public Setting<Boolean> interpolation;
    public Setting<Boolean> fire;
    public Setting<Boolean> others;
    public Setting<Boolean> armor;
    public Setting<Float> opacity;
    public Setting<Boolean> noCluster;
    public Setting<Integer> clusterAlpha;
    public Setting<Boolean> tileEntities;
    public Setting<Integer> tileDistance;
    public Setting<Boolean> ignoreESP;
    public Setting<Boolean> wardens;
    public Setting<Integer> wardenDistance;
    public Setting<Boolean> entities2;
    public Setting<Set<EntityType<?>>> entityTypes;
    public Setting<NoRenderMode> removal;
    public Setting<ScaffoldMode_2> selection;
    public float val;
    public BlockPos blockPos;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/NoRender$NoRenderMode.class */
    public enum NoRenderMode implements EnumSettingHelper {
        PARTIAL("Partial"),
        FULL("Full");

        public final String name;

        NoRenderMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public NoRender() {
        super("NoRender", "Removes the annoying stuff from your screen.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        setDrawn(false);
        this.entities.do2329("EntitiesPage");
        this.selection2.do2329("ParticleSelection");
        this.selection.do2329("EntitySelection");
        this.opacity2.do2329("HandsOpacity");
    }

    @Listen(get219= Helper_7.num4)
    public void onEvent(EntityEvent entityEvent) {
        if (!this.noCluster.getValue().booleanValue()) {
            this.val = 1.0f;
            return;
        }
        double distanceTo = minecraftClient.player.getPos().distanceTo(entityEvent.getEntity181().getPos());
        float width = entityEvent.getEntity181().getWidth();
        if (distanceTo > width || entityEvent.getEntity181() == minecraftClient.player) {
            this.val = 1.0f;
            return;
        }
        float f = (float) (distanceTo / width);
        float intValue = this.clusterAlpha.getValue().intValue() / 255.0f;
        entityEvent.do190(intValue + (f * (1.0f - intValue)));
        this.val = entityEvent.get189();
    }

    @Listen
    public void onEvent2(EntityEvent_2.Inner inner) {
        if (is1992(inner.getEntity181()) && inner.getEntity181() != minecraftClient.player && this.removal.getValue() == NoRenderMode.FULL) {
            inner.do1162();
        }
    }

    @Listen
    public void onRenderLabel(RenderLabelEvent renderLabelEvent) {
        if (this.nameTags.getValue().booleanValue()) {
            renderLabelEvent.do1162();
        }
    }

    @Listen
    public void onAddParticle(AddParticleEvent addParticleEvent) {
        Particle particle982 = addParticleEvent.getParticle982();
        if (((particle982 instanceof ExplosionLargeParticle) || (particle982 instanceof ExplosionSmokeParticle)) && this.explosions.getValue().booleanValue()) {
            addParticleEvent.do1162();
        }
        if (this.potions.getValue().booleanValue() && (particle982 instanceof SpellParticle)) {
            addParticleEvent.do1162();
        }
    }

    @Listen
    public void onGetStatusEffect(GetStatusEffectEvent getStatusEffectEvent) {
        if (this.blindness.getValue().booleanValue() && getStatusEffectEvent.getRegistryEntry830() == StatusEffects.BLINDNESS) {
            getStatusEffectEvent.do1162();
        }
        if (this.darkness.getValue().booleanValue() && getStatusEffectEvent.getRegistryEntry830() == StatusEffects.DARKNESS) {
            getStatusEffectEvent.do1162();
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (this.fire.getValue().booleanValue() && SearchHelper4_7.getStashFinderMode2438() == StashFinderMode.THE_END) {
            BlockUpdateS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof BlockUpdateS2CPacket ? (BlockUpdateS2CPacket) (channelRead0Event.getPacket904()) : null;
            if (packet904 instanceof BlockUpdateS2CPacket) {
                BlockUpdateS2CPacket blockUpdateS2CPacket = packet904;
                if (this.blockPos != null) {
                    if (!blockUpdateS2CPacket.getPos().equals(this.blockPos)) {
                        return;
                    }
                    if (!blockUpdateS2CPacket.getState().isOf(Blocks.FIRE)) {
                        return;
                    }
                    this.blockPos = null;
                    channelRead0Event.do1162();
                }
            }
            EntitySpawnS2CPacket packet9042 = (channelRead0Event.getPacket904()) instanceof EntitySpawnS2CPacket ? (EntitySpawnS2CPacket) (channelRead0Event.getPacket904()) : null;
            if (packet9042 instanceof EntitySpawnS2CPacket) {
                EntitySpawnS2CPacket entitySpawnS2CPacket = packet9042;
                if (entitySpawnS2CPacket.getEntityType() == EntityType.END_CRYSTAL) {
                    this.blockPos = BlockPos.ofFloored(entitySpawnS2CPacket.getX(), entitySpawnS2CPacket.getY(), entitySpawnS2CPacket.getZ());
                }
            }
        }
    }

    public boolean is179() {
        return isToggled() && this.hands.getValue().booleanValue() && !ShaderSearchHelper4.flag2 && !ShaderSearchHelper4.flag && this.opacity2.getValue().floatValue() == 0.0f;
    }

    public boolean is1990() {
        return isToggled() && this.tabIcons.getValue().booleanValue();
    }

    public boolean is1991(ParticleEffect particleEffect) {
        if (isToggled() && this.particles.getValue().booleanValue()) {
            return !this.selection2.getValue().is1391(particleEffect.getType(), this.particleTypes.getValue());
        }
        return false;
    }

    public boolean is1992(Entity entity) {
        return is1993(entity.getType());
    }

    public boolean is1993(EntityType<?> entityType) {
        if (isToggled() && this.entities2.getValue().booleanValue()) {
            return !this.selection.getValue().is1391(entityType, this.entityTypes.getValue());
        }
        return false;
    }

    public boolean is1994() {
        return isToggled() && this.noBlockBreaking.getValue().booleanValue();
    }

    public float get1995() {
        if (!isToggled() || !this.hands.getValue().booleanValue() || ShaderSearchHelper4.flag || ShaderSearchHelper4.flag2) {
            return 1.0f;
        }
        float floatValue = this.opacity2.getValue().floatValue();
        if (floatValue == 0.0f) {
            return 0.0f;
        }
        return Math.max(floatValue, 0.10999999940395355f);
    }

    public float get1996() {
        if (!isToggled() || !this.armor.getValue().booleanValue()) {
            return 1.0f;
        }
        float floatValue = this.opacity.getValue().floatValue();
        if (floatValue == 0.0f) {
            return 0.0f;
        }
        return Math.max(floatValue, 0.10999999940395355f);
    }

    public float get1997() {
        return !isToggled() ? 1.0f : this.val;
    }

    public boolean is1998() {
        return isToggled() && this.tint.getValue().booleanValue();
    }
}
