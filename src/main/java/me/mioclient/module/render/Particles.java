package me.mioclient.module.render;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.ParticlesHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.AddParticleEvent;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import net.minecraft.client.particle.AscendingParticle;
import net.minecraft.client.particle.AshParticle;
import net.minecraft.client.particle.DamageParticle;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.client.particle.WhiteAshParticle;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Particles.class */
public class Particles extends Module {
    public final Random random;
    public Setting<Boolean> totems;
    public Setting<Float> totemsScale;
    public Setting<Float> totemsVelocity;
    public Setting<Color> totemsColor1;
    public Setting<Color> totemsColor2;
    public Setting<Boolean> ignoreSelf;
    public Setting<Boolean> rockets;
    public Setting<Float> rocketScale;
    public Setting<Color> rocketColor;
    public Setting<Boolean> damage;
    public Setting<Float> damageScale;
    public Setting<Float> damageVelocity;
    public Setting<Color> damageColor;
    public Setting<Boolean> portal;
    public Setting<Float> portalScale;
    public Setting<Color> portalColor;
    public Setting<Boolean> dust;
    public Setting<Color> from;
    public Setting<Color> to;

    public Particles() {
        super("Particles", "Allows you to adjust certain particles.", Category.RENDER, new String[0]);
        this.random = new Random();
        PhaseESPHelper.do1351(this);
        setDrawn(false);
    }

    @Listen
    public void onAddParticle(AddParticleEvent addParticleEvent) {
        TotemParticle particle982 = (addParticleEvent.getParticle982()) instanceof TotemParticle ? (TotemParticle) (addParticleEvent.getParticle982()) : null;
        if (particle982 instanceof TotemParticle) {
            TotemParticle totemParticle = particle982;
            if (this.ignoreSelf.getValue().booleanValue()) {
                if (minecraftClient.player.getBoundingBox().intersects(totemParticle.getBoundingBox())) {
                    addParticleEvent.do1162();
                }
            }
            if (!this.totems.getValue().booleanValue()) {
                return;
            }
            if (this.random.nextInt(4) == 0) {
                do2580((Particle) totemParticle, this.totemsColor1.getValue().hashCode());
            } else {
                do2580((Particle) totemParticle, this.totemsColor2.getValue().hashCode());
            }
            totemParticle.scale(this.totemsScale.getValue().floatValue());
            totemParticle.move(this.totemsVelocity.getValue().floatValue());
        }
        if (this.rockets.getValue().booleanValue()) {
            FireworksSparkParticle.FireworkParticle particle9822 = (FireworksSparkParticle.FireworkParticle)(addParticleEvent.getParticle982());
            if (particle9822 instanceof FireworksSparkParticle.FireworkParticle) {
                FireworksSparkParticle.FireworkParticle fireworkParticle = particle9822;
                do2580((Particle) fireworkParticle, this.rocketColor.getValue().hashCode());
                fireworkParticle.scale(this.rocketScale.getValue().floatValue());
            }
        }
        if (this.rockets.getValue().booleanValue()) {
            FireworksSparkParticle.Explosion particle9823 = (FireworksSparkParticle.Explosion)(addParticleEvent.getParticle982());
            if (particle9823 instanceof FireworksSparkParticle.Explosion) {
                FireworksSparkParticle.Explosion explosion = particle9823;
                do2580((Particle) explosion, this.rocketColor.getValue().hashCode());
                explosion.scale(this.rocketScale.getValue().floatValue());
            }
        }
        if (this.damage.getValue().booleanValue()) {
            DamageParticle particle9824 = (addParticleEvent.getParticle982()) instanceof DamageParticle ? (DamageParticle) (addParticleEvent.getParticle982()) : null;
            if (particle9824 instanceof DamageParticle) {
                DamageParticle damageParticle = particle9824;
                do2580((Particle) damageParticle, this.damageColor.getValue().hashCode());
                damageParticle.scale(this.damageScale.getValue().floatValue());
                damageParticle.move(this.damageVelocity.getValue().floatValue());
            }
        }
        if (this.portal.getValue().booleanValue()) {
            PortalParticle particle9825 = (addParticleEvent.getParticle982()) instanceof PortalParticle ? (PortalParticle) (addParticleEvent.getParticle982()) : null;
            if (particle9825 instanceof PortalParticle) {
                PortalParticle portalParticle = particle9825;
                do2580((Particle) portalParticle, this.portalColor.getValue().hashCode());
                portalParticle.scale(this.portalScale.getValue().floatValue());
            }
        }
        if (this.dust.getValue().booleanValue()) {
            Particle particle9826 = addParticleEvent.getParticle982();
            if (particle9826 instanceof AscendingParticle) {
                Particle particle = (AscendingParticle) particle9826;
                if (particle instanceof WhiteAshParticle) {
                    do2580(particle, this.to.getValue().brighter().hashCode());
                }
                if (particle instanceof AshParticle) {
                    do2580(particle, MixinMessageIndicatorHelper_2.getColor815(this.from.getValue(), this.to.getValue(), ThreadLocalRandom.current().nextFloat()).hashCode());
                }
            }
        }
    }

    public void do2580(Particle particle, int i) {
        float intBitsToFloat = (i >> 24) / 255.0f;
        particle.setColor(((i & 16711680) >> 16) / 255.0f, ((i & 65280) >> 8) / 255.0f, (i & 255) / 255.0f);
        ((ParticlesHelper) particle).mio$setInitialAlpha(intBitsToFloat);
    }
}
