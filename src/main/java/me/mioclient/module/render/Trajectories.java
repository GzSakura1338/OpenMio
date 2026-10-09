package me.mioclient.module.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.SearchHelper;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchHelper_4;
import me.mioclient.TrajectoriesSearchHelper4;
import me.mioclient.TrajectoriesVertexConsumer;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickPostEvent;
import me.mioclient.module.Module;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.BowItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL32C;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Trajectories.class */
public class Trajectories extends Module {
    public final TrajectoriesSearchHelper4 trajectoriesSearchHelper4;
    public Setting<Boolean> onlyOwn;
    public Setting<Boolean> predict;
    public Setting<Boolean> airborne;
    public Setting<Float> lineWidth;
    public Setting<Color> color;
    public Setting<Boolean> targets;
    public Setting<Boolean> bow;
    public Setting<Boolean> xBow;
    public Setting<Boolean> trident;
    public Setting<Boolean> pearls;
    public Setting<Boolean> exp;
    public Setting<Boolean> others;
    public BufferBuilder bufferBuilder;
    public Vec3d vec3d;
    public float val;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/Trajectories$Inner.class */
    private class Inner {
        public final List<Vec3d> list = new ArrayList();
        public boolean flag;
        public Box box;
        public Entity entity;
        public Entity entity2;

        public Inner() {
        }

        public void do1() {
            this.list.clear();
            this.flag = false;
            this.entity = null;
        }

        public void do3143(Entity entity) {
            this.entity2 = entity;
        }

        public void do3144() {
            do3145();
            for (int i = 0; i < 2000; i++) {
                HitResult hitResult1873 = Trajectories.this.trajectoriesSearchHelper4.getHitResult1873();
                if (hitResult1873 != null) {
                    do3146(hitResult1873);
                    return;
                }
                do3145();
            }
        }

        public void do3145() {
            this.list.add(new Vec3d(Trajectories.this.trajectoriesSearchHelper4.vector3d.x, Trajectories.this.trajectoriesSearchHelper4.vector3d.y, Trajectories.this.trajectoriesSearchHelper4.vector3d.z));
        }

        public void do3146(HitResult hitResult) {
            if (hitResult.getType() != HitResult.Type.BLOCK) {
                if (hitResult.getType() == HitResult.Type.ENTITY) {
                    this.entity = ((EntityHitResult) hitResult).getEntity();
                    return;
                }
                return;
            }
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            this.flag = true;
            this.box = new Box(blockHitResult.getPos(), blockHitResult.getPos());
            if (blockHitResult.getSide() == Direction.UP || blockHitResult.getSide() == Direction.DOWN) {
                this.box = this.box.expand(0.25, 0.0d, 0.25);
            } else if (blockHitResult.getSide() == Direction.NORTH || blockHitResult.getSide() == Direction.SOUTH) {
                this.box = this.box.expand(0.25, 0.25, 0.0d);
            } else {
                this.box = this.box.expand(0.0d, 0.25, 0.25);
            }
            this.list.add(hitResult.getPos());
        }

        public void do3147(MatrixStackEvent.Inner_3 inner_3) {
            Vec3d vec3d = null;
            for (Vec3d vec3d2 : this.list) {
                if (vec3d != null) {
                    SearchHelper_2.searchHelper_2.do572(inner_3.getMatrixStack472(), Trajectories.this.bufferBuilder, vec3d, vec3d2, Trajectories.this.color.getValue().getRGB(), Trajectories.this.color.getValue().getRGB());
                }
                vec3d = vec3d2;
            }
            if (this.flag) {
                PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), this.box, MixinMessageIndicatorHelper_2.getColor816(Trajectories.this.color.getValue(), 60));
                PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), this.box, Trajectories.this.color.getValue(), Trajectories.this.lineWidth.getValue().floatValue());
            }
            if (this.entity == null || this.entity2 != SearchHelper_4.minecraftClient.player) {
                return;
            }
            PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), SearchHelper.getBox233(this.entity, inner_3.get473()), MixinMessageIndicatorHelper_2.getColor816(Trajectories.this.color.getValue(), 60));
        }
    }

    public Trajectories() {
        super("Trajectories", "Draws trajectories for projectiles and bows.", Category.RENDER, new String[0]);
        this.trajectoriesSearchHelper4 = new TrajectoriesSearchHelper4();
        PhaseESPHelper.do1351(this);
        this.vec3d = Vec3d.ZERO;
        setDrawn(false);
    }

    @Listen
    public void onTickPost(TickPostEvent tickPostEvent) {
        this.vec3d = minecraftClient.player.getVelocity();
        this.val = BowItem.getPullProgress(minecraftClient.player.getItemUseTime());
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        BufferBuilder begin = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        this.bufferBuilder = begin;
        for (Entity entity : minecraftClient.world.getEntities()) {
            if (is1121(entity)) {
                Inner inner = new Inner();
                if (entity instanceof ProjectileEntity) {
                    if (this.trajectoriesSearchHelper4.is1869((ProjectileEntity) entity, true, inner_3.get473())) {
                        inner.do3144();
                        inner.do3143(entity);
                        inner.do3147(inner_3);
                        inner.do1();
                    }
                } else {
                    if (entity instanceof PlayerEntity) {
                        Entity entity2 = (PlayerEntity) entity;
                        if (!this.trajectoriesSearchHelper4.is1867(entity2, ((PlayerEntity) entity2).getStackInHand(((PlayerEntity) entity2).getActiveHand()), 0.0d, true, inner_3.get473())) {
                        }
                    }
                    inner.do3144();
                    inner.do3143(entity);
                    inner.do3147(inner_3);
                    inner.do1();
                }
            }
        }
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        SearchHelper_2.searchHelper_2.do563();
        GL32C.glLineWidth(this.lineWidth.getValue().floatValue());
        TrajectoriesVertexConsumer.do2599(begin);
        SearchHelper_2.searchHelper_2.do565();
    }

    public boolean is1121(Entity entity) {
        if (entity instanceof WitherSkullEntity) {
            return false;
        }
        return ((entity instanceof PlayerEntity) && this.predict.getValue().booleanValue()) ? entity == minecraftClient.player || !this.onlyOwn.getValue().booleanValue() : (entity instanceof ProjectileEntity) && this.airborne.getValue().booleanValue();
    }

    public Vec3d getVec3d1474() {
        return this.vec3d;
    }

    public float get1475() {
        return this.val;
    }
}
