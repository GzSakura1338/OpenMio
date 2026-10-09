package me.mioclient.module.render;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.Color;
import java.util.Comparator;
import java.util.function.Supplier;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.ESPSearchHelper4_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_3;
import me.mioclient.ShaderFramebufferHelper;
import me.mioclient.ShaderSearchHelper4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import me.mioclient.module.player.Freecam;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Shader.class */
public class Shader extends Module {
    public static Freecam freecam = (Freecam) BaritoneHelper_3.baritoneHelper_4.getModule117(Freecam.class);
    public static NoRender norender = (NoRender) BaritoneHelper_3.baritoneHelper_4.getModule117(NoRender.class);
    public Setting<MixinHeldItemRendererMode> shader;
    public Setting<Integer> range2;
    public Setting<Integer> lineWidth;
    public Setting<Boolean> fastLines;
    public Setting<Float> glow;
    public Setting<Integer> quality;
    public Setting<Float> step;
    public Setting<Float> speed;
    public Setting<Float> fillSpeed;
    public Setting<Float> outlineSpeed;
    public Setting<Float> fillStrength;
    public Setting<Float> outlineStrength;
    public Setting<Boolean> image;
    public Setting<Float> overlayAlpha;
    public Setting<Boolean> decorator;
    public Setting<Mode> type;
    public Setting<Integer> radius;
    public Setting<Float> alpha;
    public Setting<Boolean> colors;
    public Setting<Color> fill;
    public Setting<Color> fillSecond;
    public Setting<Color> outline2;
    public Setting<Color> outlineSecond;
    public Setting<Boolean> targets;
    public Setting<Boolean> hands;
    public Setting<Boolean> fill2;
    public Setting<Boolean> outline;
    public Setting<Boolean> crystals;
    public Setting<Integer> range;
    public Setting<Boolean> items;
    public Setting<Integer> range3;
    public Setting<Boolean> animals;
    public Setting<Boolean> hostiles;
    public Setting<Boolean> players;
    public Setting<Boolean> self;
    public Setting<Boolean> exp;
    public Setting<Boolean> pearls;
    public Setting<Boolean> minecarts;
    public Setting<Boolean> dead;
    public final ObjectArrayList<Entity> objectArrayList;
    public float val;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/Shader$MixinHeldItemRendererMode.class */
    public enum MixinHeldItemRendererMode implements EnumSettingHelper {
        SOLID("Solid", () -> {
            return ShaderSearchHelper4.shaderFramebufferHelper;
        }),
        RAINBOW("Rainbow", () -> {
            return ShaderSearchHelper4.shaderFramebufferHelper2;
        }),
        GRADIENT("Gradient", () -> {
            return ShaderSearchHelper4.shaderFramebufferHelper3;
        }),
        BLOOM("Bloom", () -> {
            return ShaderSearchHelper4.shaderFramebufferHelper4;
        });

        public final String name;
        public final Supplier<ShaderFramebufferHelper> supplier;

        MixinHeldItemRendererMode(String str, Supplier supplier) {
            this.name = str;
            this.supplier = supplier;
        }

        public ShaderFramebufferHelper getShaderFramebufferHelper21() {
            return this.supplier.get();
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/Shader$Mode.class */
    public enum Mode implements EnumSettingHelper {
        NONE("None"),
        DOTS("Dots"),
        GRID("Grid");

        public final String name;

        Mode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public Shader() {
        super("Shader", "Advanced ESP using shaders.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.objectArrayList = new ObjectArrayList<>();
        this.val = 1.0f;
        this.radius.do2329("DecoratorRadius");
        this.alpha.do2329("DecoratorAlpha");
        this.fill2.do2329("HandsFill");
        this.outline.do2329("HandsOutline");
        this.range3.do2329("ItemRange");
        this.range.do2329("CrystalRange");
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return FontsSearchHelper4.getString1684(this.shader.getValue());
    }

    @Listen(get219= -99999999)
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        ShaderFramebufferHelper shaderFramebufferHelper21 = this.shader.getValue().getShaderFramebufferHelper21();
        this.objectArrayList.clear();
        for (Entity entity : minecraftClient.world.getEntities()) {
            if (is1763(entity)) {
                this.objectArrayList.add(entity);
            }
        }
        this.objectArrayList.sort(Comparator.comparing(entity2 -> {
            return Double.valueOf(minecraftClient.gameRenderer.getCamera().getPos().squaredDistanceTo(entity2.getPos()));
        }));
        ESPSearchHelper4_3.do2887(shaderFramebufferHelper21, false, () -> {
            inner_3.getMatrixStack472().push();
            this.objectArrayList.forEach(entity3 -> {
                do3110(inner_3, entity3, shaderFramebufferHelper21);
            });
            inner_3.getMatrixStack472().pop();
        });
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        this.val = get3111((Entity) this.objectArrayList.stream().min(Comparator.comparing(this::get3111)).orElse(null));
    }

    public void do3110(MatrixStackEvent matrixStackEvent, Entity entity, ShaderFramebufferHelper shaderFramebufferHelper) {
        if (entity.isAlive() || this.dead.getValue().booleanValue()) {
            Vec3d pos = minecraftClient.gameRenderer.getCamera().getPos();
            float f = matrixStackEvent.get473();
            double lerp = MathHelper.lerp(f, entity.lastRenderX, entity.getX()) - pos.x;
            double lerp2 = MathHelper.lerp(f, entity.lastRenderY, entity.getY()) - pos.y;
            double lerp3 = MathHelper.lerp(f, entity.lastRenderZ, entity.getZ()) - pos.z;
            float lerp4 = MathHelper.lerp(f, entity.prevYaw, entity.getYaw());
            shaderFramebufferHelper.outlineVertexConsumerProvider.setColor(255, 255, 255, 255);
            minecraftClient.getEntityRenderDispatcher().render(entity, lerp, lerp2, lerp3, lerp4, f, matrixStackEvent.getMatrixStack472(), shaderFramebufferHelper.outlineVertexConsumerProvider, 0);
        }
    }

    public float get3111(Entity entity) {
        if (entity != null && norender.isToggled() && norender.noCluster.getValue().booleanValue()) {
            double distanceTo = minecraftClient.player.getPos().distanceTo(entity.getPos());
            float width = entity.getWidth();
            if (distanceTo <= width && entity != minecraftClient.player) {
                float f = (float) (distanceTo / width);
                float intValue = norender.clusterAlpha.getValue().intValue() / 255.0f;
                return intValue + (f * (1.0f - intValue));
            }
        }
        return 1.0f;
    }

    public Color getColor3112(Color color) {
        float f = this.val;
        if (ShaderSearchHelper4.flag2) {
            f = 1.0f;
        }
        return MixinMessageIndicatorHelper_2.getColor816(color, (int) (f * color.getAlpha()));
    }

    public float get3113() {
        return ShaderSearchHelper4.flag2 ? this.overlayAlpha.getValue().floatValue() : this.overlayAlpha.getValue().floatValue() * this.val;
    }

    public boolean is1763(Entity entity) {
        float intValue = this.range2.getValue().intValue();
        if (entity instanceof EndCrystalEntity) {
            intValue = this.range.getValue().intValue();
        }
        if (entity instanceof ItemEntity) {
            intValue = this.range3.getValue().intValue();
        }
        boolean z = !SearchHelper4_8.is2492(entity.getBoundingBox()) || minecraftClient.player.distanceTo(entity) > intValue;
        if (((entity instanceof PlayerEntity) && ((PlayerEntity) entity).isDead()) || z || SearchHelper_3.is648(entity)) {
            return false;
        }
        boolean z2 = minecraftClient.gameRenderer.getCamera().isThirdPerson() || freecam.isToggled();
        if (!(entity instanceof PlayerEntity)) {
            return ((entity instanceof ItemEntity) && this.items.getValue().booleanValue()) || ((entity instanceof ExperienceBottleEntity) && this.exp.getValue().booleanValue()) || ((((entity instanceof PassiveEntity) || (entity instanceof FishEntity) || (entity instanceof SquidEntity)) && this.animals.getValue().booleanValue()) || (((entity instanceof Monster) && this.hostiles.getValue().booleanValue()) || (((entity instanceof EnderPearlEntity) && this.pearls.getValue().booleanValue()) || (((entity instanceof EndCrystalEntity) && this.crystals.getValue().booleanValue()) || ((entity instanceof AbstractMinecartEntity) && this.minecarts.getValue().booleanValue())))));
        }
        if (z2 && this.self.getValue().booleanValue() && entity == minecraftClient.player) {
            return true;
        }
        return this.players.getValue().booleanValue() && entity != minecraftClient.player;
    }

    public boolean is3114() {
        return ShaderSearchHelper4.flag2 && !this.fill2.getValue().booleanValue();
    }

    public boolean is3115() {
        return ShaderSearchHelper4.flag2 && !this.outline.getValue().booleanValue();
    }
}
