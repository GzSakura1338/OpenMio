package me.mioclient.module.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import me.mioclient.FreecamHelper;
import me.mioclient.Helper_7;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper_2;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL32C;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Trails.class */
public class Trails extends Module {
    public final HashMap<Integer, List<Inner>> hashMap;
    public Setting<Boolean> render;
    public Setting<Float> delay;
    public Setting<Boolean> targets;
    public Setting<Boolean> self;
    public Setting<Boolean> players;
    public Setting<Boolean> pearls;
    public Setting<Boolean> arrows;
    public Setting<Boolean> exp;
    public Setting<Float> lineWidth;
    public Setting<Boolean> fade;
    public Setting<Float> fadeDelay;
    public Setting<Float> fadeDuration;
    public Setting<Color> color;
    public Setting<Boolean> rainbow;
    public Setting<Double> threshold;
    public final Stopwatch stopwatch;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/Trails$Inner.class */
    public static class Inner {
        public final long num = System.currentTimeMillis();
        public final Vec3d vec3d;

        public Inner(Vec3d vec3d) {
            this.vec3d = vec3d;
        }

        public long get2652() {
            return this.num;
        }

        public Vec3d getVec3d1954() {
            return this.vec3d;
        }
    }

    public Trails() {
        super("Trails", "Draws trails behind certain entities.", Category.RENDER, new String[0]);
        this.hashMap = new HashMap<>();
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        setDrawn(false);
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        this.hashMap.clear();
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (is1469() || !this.render.getValue().booleanValue()) {
            return;
        }
        for (Map.Entry<Integer, List<Inner>> entry : this.hashMap.entrySet()) {
            if (entry.getValue().size() > 2) {
                do3148(inner_3.getMatrixStack472(), entry.getValue());
            }
        }
    }

    @Listen(get219= Helper_7.num4)
    public void onEvent2(MatrixStackEvent.Inner inner) {
        if (is1469()) {
            return;
        }
        if (this.delay.getValue().floatValue() != 0.0f) {
            if (!this.stopwatch.is418(this.delay.getValue().floatValue(), TimeUnit.SECONDS)) {
                return;
            }
        }
        this.stopwatch.reset();
        synchronized (this.hashMap) {
            if (minecraftClient.player.age % 4 == 0) {
                int i = 0;
                Iterator<Integer> it = this.hashMap.keySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Integer next = it.next();
                    List<Inner> list = this.hashMap.get(next);
                    list.removeIf(inner2 -> {
                        return this.fade.getValue().booleanValue() && System.currentTimeMillis() - inner2.num > get3150() + get3151();
                    });
                    if (list.size() == 0) {
                        i = next.intValue();
                        break;
                    }
                }
                if (i != 0) {
                    this.hashMap.remove(Integer.valueOf(i));
                }
            }
            for (Entity entity : minecraftClient.world.getEntities()) {
                if (is1763(entity)) {
                    this.hashMap.compute(Integer.valueOf(entity.getId()), (num, list2) -> {
                        Inner inner3 = new Inner(entity.getLerpedPos(SearchHelper_2.get536()));
                        if (list2 == null) {
                            return new ArrayList(Collections.singleton(inner3));
                        }
                        list2.add(inner3);
                        return list2;
                    });
                }
            }
        }
    }

    public void do3148(MatrixStack matrixStack, List<Inner> list) {
        BufferBuilder begin = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        SearchHelper_2.searchHelper_2.do563();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableBlend();
        GL32C.glLineWidth(this.lineWidth.getValue().floatValue());
        RenderSystem.lineWidth(this.lineWidth.getValue().floatValue());
        float[] RGBtoHSB = Color.RGBtoHSB(this.color.getValue().getRed(), this.color.getValue().getGreen(), this.color.getValue().getBlue(), (float[]) null);
        for (int i = 0; i < list.size() - 2; i++) {
            SearchHelper_2.searchHelper_2.do572(matrixStack, begin, list.get(i).getVec3d1954(), list.get(i + 1).getVec3d1954(), getColor3149(RGBtoHSB, list.get(i)).hashCode(), getColor3149(RGBtoHSB, list.get(i + 1)).hashCode());
        }
        BufferRenderer.drawWithGlobalProgram(begin.end());
        SearchHelper_2.searchHelper_2.do565();
        GL32C.glLineWidth(1.0f);
    }

    public Color getColor3149(float[] fArr, Inner inner) {
        Color hSBColor = this.rainbow.getValue().booleanValue() ? Color.getHSBColor((float) ((Math.ceil(inner.get2652() / (20.0 * this.threshold.getValue().doubleValue())) % FreecamHelper.num3) / FreecamHelper.num3), fArr[1], fArr[2]) : this.color.getValue();
        return (!this.fade.getValue().booleanValue() || System.currentTimeMillis() - inner.get2652() < get3150()) ? MixinMessageIndicatorHelper_2.getColor816(hSBColor, this.color.getValue().getAlpha()) : MixinMessageIndicatorHelper_2.getColor816(hSBColor, (int) MathHelper.clamp((1.0f - (((float) ((System.currentTimeMillis() - inner.get2652()) - get3150())) / ((float) get3151()))) * this.color.getValue().getAlpha(), 0.0f, 255.0f));
    }

    public boolean is1763(Entity entity) {
        return (entity == minecraftClient.player && this.self.getValue().booleanValue()) || ((entity instanceof PlayerEntity) && this.players.getValue().booleanValue() && entity != minecraftClient.player) || (((entity instanceof EnderPearlEntity) && this.pearls.getValue().booleanValue()) || (((entity instanceof ExperienceBottleEntity) && this.exp.getValue().booleanValue()) || ((entity instanceof ArrowEntity) && this.arrows.getValue().booleanValue())));
    }

    public long get3150() {
        return (long) (this.fadeDelay.getValue().floatValue() * 1000.0f);
    }

    public long get3151() {
        return (long) (this.fadeDuration.getValue().floatValue() * 1000.0f);
    }
}
