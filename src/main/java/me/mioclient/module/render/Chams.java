package me.mioclient.module.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.ChamsHelper;
import me.mioclient.ChamsHelper_2;
import me.mioclient.ChamsMode;
import me.mioclient.ChamsMode_2;
import me.mioclient.ESPHelper;
import me.mioclient.EntityEvent;
import me.mioclient.EntityEvent_2;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapMode;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.OtherClientPlayerEntity;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PlayerEntityEvent;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchHelper_3;
import me.mioclient.ShaderSearchHelper4;
import me.mioclient.SpawnTimeHelper_2;
import me.mioclient.TrajectoriesVertexConsumer;
import me.mioclient.ZoomHelper_3;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import me.mioclient.module.combat.AutoCrystal;
import me.mioclient.module.player.Freecam;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Chams.class */
public class Chams extends Module {
    public Setting<Integer> range;
    public Setting<Boolean> fade;
    public Setting<Integer> fadeRadius;
    public Setting<Float> lineWidth;
    public Setting<Boolean> extraLayer;
    public Setting<Boolean> model;
    public Setting<Boolean> xqz;
    public Setting<Integer> opacity;
    public Setting<Boolean> shine2;
    public Setting<Float> speed;
    public Setting<Float> strength;
    public Setting<Integer> progress;
    public Setting<Boolean> targets;
    public Setting<ChamsMode> animals;
    public Setting<ChamsMode> hostiles;
    public Setting<ChamsMode> players;
    public Setting<ChamsMode> self;
    public Setting<ChamsMode> crystals;
    public Setting<Boolean> pop;
    public Setting<Boolean> death;
    public Setting<ChamsMode_2> wireframe;
    public Setting<Boolean> animate;
    public Setting<Boolean> boost;
    public Setting<Float> time;
    public Setting<Float> motion;
    public Setting<Boolean> colors;
    public Setting<Boolean> friends;
    public Setting<Color> fill;
    public Setting<Color> outline;
    public Setting<Color> shine;
    public Setting<Color> popFill;
    public Setting<Color> popLine;
    public final TrajectoriesVertexConsumer trajectoriesVertexConsumer;
    public final Map<OtherClientPlayerEntity, Long> map;
    public boolean flag3;
    public static final Identifier identifier = Identifier.of("mio-mount", "textures/shine.png");
    public static AutoCrystal autoCrystal = (AutoCrystal) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoCrystal.class);
    public static NoRender norender = (NoRender) BaritoneHelper_3.baritoneHelper_4.getModule117(NoRender.class);
    public static Animations animations = (Animations) BaritoneHelper_3.baritoneHelper_4.getModule117(Animations.class);
    public static final Freecam freecam = (Freecam) BaritoneHelper_3.baritoneHelper_4.getModule117(Freecam.class);
    public static boolean flag = false;
    public static boolean flag2 = false;

    public Chams() {
        super("Chams", "Wallhack on entities.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.trajectoriesVertexConsumer = TrajectoriesVertexConsumer.getTrajectoriesVertexConsumer2592();
        this.map = new HashMap();
        this.wireframe.do2329("PopMode");
        this.shine.do2329("ShineColor");
        this.speed.getSetting2338("Static", HoleSnapMode.MIN);
    }

    @Listen(get219= Helper_7.num)
    public void onEvent2(MatrixStackEvent.Inner inner) {
        if (this.shine2.getValue().booleanValue()) {
            this.trajectoriesVertexConsumer.getBufferBuilder2595(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        }
        float intBitsToFloat = ((Integer) minecraftClient.options.getFov().getValue()).intValue() > 115 && is2047() ? 2.299999952316284f : 1.0f;
        ESPHelper.do1104();
        RenderSystem.enablePolygonOffset();
        RenderSystem.polygonOffset(1.0f, -3500000.0f * intBitsToFloat);
        for (Entity entity : minecraftClient.world.getEntities()) {
            if (is1763(entity)) {
                if (this.model.getValue().booleanValue() && this.xqz.getValue().booleanValue() && !norender.is1992(entity) && this.opacity.getValue().intValue() > 0) {
                    this.flag3 = true;
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.opacity.getValue().intValue() / 100.0f);
                    SearchHelper_2.searchHelper_2.do574(entity, inner.get473(), inner.getMatrixStack472(), minecraftClient.getBufferBuilders().getEntityVertexConsumers());
                    this.flag3 = false;
                }
                if (this.shine2.getValue().booleanValue()) {
                    flag = true;
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    inner.getMatrixStack472().push();
                    SearchHelper_2.do578(inner.getMatrixStack472());
                    SearchHelper_2.searchHelper_2.do574(entity, inner.get473(), inner.getMatrixStack472(), minecraftClient.getBufferBuilders().getEntityVertexConsumers());
                    inner.getMatrixStack472().pop();
                    flag = false;
                }
            }
        }
        if (this.model.getValue().booleanValue() && this.xqz.getValue().booleanValue()) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.opacity.getValue().intValue() / 100.0f);
            minecraftClient.getBufferBuilders().getEntityVertexConsumers().draw();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        RenderSystem.disablePolygonOffset();
        RenderSystem.polygonOffset(1.0f, 3500000.0f * intBitsToFloat);
        ESPHelper.do1105();
    }

    @Listen(get219= -1000)
    public void onEvent3(MatrixStackEvent.Inner_3 inner_3) {
        ChamsHelper_2.do620(this.lineWidth.getValue().floatValue());
        for (Entity entity : minecraftClient.world.getEntities()) {
            if (is1763(entity) && getSetting2050(entity).getValue().is594()) {
                getSetting2050(entity).getValue().do591(this, entity, inner_3.getMatrixStack472());
            }
        }
        if (this.pop.getValue().booleanValue()) {
            synchronized (this.map) {
                this.map.entrySet().removeIf(entry -> {
                    return ((float) ((Long) entry.getValue()).longValue()) + (this.time.getValue().floatValue() * 1000.0f) < ((float) System.currentTimeMillis());
                });
                this.map.forEach((otherClientPlayerEntity, l) -> {
                    float intBitsToFloat = 1.0f - MathHelper.clamp(((float) (System.currentTimeMillis() - l.longValue())) / (this.time.getValue().floatValue() * 1000.0f), 0.0f, 1.0f);
                    otherClientPlayerEntity.setPosition(otherClientPlayerEntity.getX(), otherClientPlayerEntity.getVec3d147().y + ((1.0f - intBitsToFloat) * this.motion.getValue().floatValue()), otherClientPlayerEntity.getZ());
                    Color[] colorArray593 = this.wireframe.getValue().getColorArray593(this);
                    ChamsHelper_2.do612(MixinMessageIndicatorHelper_2.getColor817(colorArray593[0], (colorArray593[0].getAlpha() / 255.0f) * intBitsToFloat), MixinMessageIndicatorHelper_2.getColor817(colorArray593[1], (colorArray593[1].getAlpha() / 255.0f) * intBitsToFloat));
                    ChamsHelper_2.do615(inner_3.getMatrixStack472(), otherClientPlayerEntity);
                });
            }
        }
        if (this.xqz.getValue().booleanValue()) {
            minecraftClient.getBufferBuilders().getEntityVertexConsumers().draw();
        }
        if (this.shine2.getValue().booleanValue()) {
            double doubleValue = ((Double) minecraftClient.options.getGlintSpeed().getValue()).doubleValue();
            double doubleValue2 = ((Double) minecraftClient.options.getGlintStrength().getValue()).doubleValue();
            ((ZoomHelper_3) (Object) minecraftClient.options.getGlintSpeed()).forceSetValue(Double.valueOf(this.speed.getValue().doubleValue()));
            minecraftClient.options.getGlintStrength().setValue(Double.valueOf(this.strength.getValue().doubleValue()));
            Color value = this.shine.getValue();
            RenderSystem.enableBlend();
            flag2 = true;
            RenderLayer.getArmorEntityGlint().startDrawing();
            MinecraftClient.getInstance().getTextureManager().getTexture(identifier).setFilter(true, false);
            RenderSystem.setShaderTexture(0, identifier);
            RenderSystem.setShaderColor(value.getRed() / 255.0f, value.getGreen() / 255.0f, value.getBlue() / 255.0f, 1.0f);
            RenderSystem.disableDepthTest();
            this.trajectoriesVertexConsumer.do865();
            RenderSystem.enableDepthTest();
            RenderLayer.getArmorEntityGlint().endDrawing();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            flag2 = false;
            minecraftClient.options.getGlintSpeed().setValue(Double.valueOf(doubleValue));
            minecraftClient.options.getGlintStrength().setValue(Double.valueOf(doubleValue2));
        }
    }

    @Listen
    public void onEvent4(EntityEvent_2.Inner inner) {
        if (ShaderSearchHelper4.flag || this.model.getValue().booleanValue() || !is1763(inner.getEntity181()) || this.flag3) {
            return;
        }
        inner.do1162();
    }

    @Listen
    public void onEvent(EntityEvent entityEvent) {
        if (is1763(entityEvent.getEntity181())) {
            entityEvent.do192(15728880);
        }
    }

    @Listen
    public void onEvent5(PlayerEntityEvent playerEntityEvent) {
        PlayerEntity playerEntity1890;
        if (this.pop.getValue().booleanValue()) {
            if ((playerEntityEvent.getLogoutSpotsHelperMode1892() != PlayerEntityEvent.LogoutSpotsHelperMode.DEATH || this.death.getValue().booleanValue()) && (playerEntity1890 = playerEntityEvent.getPlayerEntity1890()) != minecraftClient.player) {
                OtherClientPlayerEntity otherClientPlayerEntity = new OtherClientPlayerEntity(minecraftClient.world);
                otherClientPlayerEntity.do146(playerEntity1890);
                otherClientPlayerEntity.limbAnimator.pos *= 2.0f;
                if (!this.animate.getValue().booleanValue()) {
                    otherClientPlayerEntity.limbAnimator.pos = 0.0f;
                    otherClientPlayerEntity.limbAnimator.speed = 0.0f;
                    otherClientPlayerEntity.limbAnimator.prevSpeed = 0.0f;
                } else if (this.boost.getValue().booleanValue()) {
                    otherClientPlayerEntity.do149(true);
                    otherClientPlayerEntity.handSwingProgress = 0.0f;
                    otherClientPlayerEntity.limbAnimator.speed = (float) ((Math.random() * 0.800000011920929) + 0.20000000298023224);
                    otherClientPlayerEntity.limbAnimator.pos = 0.0f;
                }
                synchronized (this.map) {
                    this.map.putIfAbsent(otherClientPlayerEntity, Long.valueOf(System.currentTimeMillis()));
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0141  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean is1763(Entity entity) {
        boolean z;
        if (LogoutSpots.is1291()) {
            return false;
        }
        try {
            if (SearchHelper4_8.is2492(entity.getBoundingBox())) {
                if (minecraftClient.gameRenderer.getCamera().getPos().distanceTo(entity.getPos()) <= this.range.getValue().intValue()) {
                    z = false;
                    if (!z || SearchHelper_3.is648(entity)) {
                        return false;
                    }
                    if (entity instanceof EndCrystalEntity) {
                        if (entity instanceof PlayerEntity) {
                            return entity == minecraftClient.player ? this.self.getValue().is594() && (freecam.isToggled() || minecraftClient.options.getPerspective() != Perspective.FIRST_PERSON) : this.players.getValue().is594();
                        }
                        return ((entity instanceof PassiveEntity) && this.animals.getValue().is594()) || ((entity instanceof Monster) && this.hostiles.getValue().is594());
                    }
                    SpawnTimeHelper_2 spawnTimeHelper_2 = (SpawnTimeHelper_2)((EndCrystalEntity) entity);
                    if (System.currentTimeMillis() - spawnTimeHelper_2.getSpawnTime() > Math.max(BaritoneHelper_3.holeSnapSearchHelper4_4.get1730(), 50) && ((EndCrystalEntity) spawnTimeHelper_2).age < 10 && autoCrystal.isToggled() && autoCrystal.inhibit.getValue().booleanValue() && spawnTimeHelper_2.isMioAttacked()) {
                        return false;
                    }
                    return this.crystals.getValue().is594();
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public float get2044(Entity entity) {
        return (float) (1.0 - MathHelper.clamp(((minecraftClient.gameRenderer.getCamera().getPos().distanceTo(entity.getPos()) / this.range.getValue().intValue()) - (this.fadeRadius.getValue().intValue() / 100.0f)) / (1.0f - (this.fadeRadius.getValue().intValue() / 100.0f)), 0.0d, 1.0));
    }

    public boolean is2045(Entity entity) {
        if (isToggled() && this.xqz.getValue().booleanValue()) {
            return is1763(entity);
        }
        return false;
    }

    public boolean is2046(Entity entity) {
        return isToggled() && (getSetting2050(entity).getValue().is594() || entity == minecraftClient.player) && this.shine2.getValue().booleanValue() && flag;
    }

    public boolean is2047() {
        float intBitsToFloat = 0.30000001192092896f;
        Vec3d eyePos = minecraftClient.player.getEyePos();
        BlockPos ofFloored = BlockPos.ofFloored((Position) eyePos);
        if (!minecraftClient.world.isBlockSpaceEmpty(minecraftClient.player, Box.of(eyePos, intBitsToFloat, intBitsToFloat, intBitsToFloat))) {
            if (minecraftClient.world.getBlockState(ofFloored).getCollisionShape(minecraftClient.world, ofFloored).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean is2048() {
        return animations.is1000();
    }

    public TrajectoriesVertexConsumer getTrajectoriesVertexConsumer2049() {
        return this.trajectoriesVertexConsumer;
    }

    public Setting<? extends ChamsHelper> getSetting2050(Entity entity) {
        return entity == minecraftClient.player ? this.self : entity instanceof EndCrystalEntity ? this.crystals : entity instanceof PlayerEntity ? this.players : entity instanceof PassiveEntity ? this.animals : this.hostiles;
    }
}
