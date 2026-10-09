package me.mioclient.module.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FreecamHelper;
import me.mioclient.MatrixStackEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.TrajectoriesVertexConsumer;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.mixin.ducks.DuckLivingEntityRenderer;
import me.mioclient.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Skeleton.class */
public class Skeleton extends Module {
    public static Animations animations = (Animations) BaritoneHelper_3.baritoneHelper_4.getModule117(Animations.class);
    public Setting<Color> color;

    public Skeleton() {
        super("Skeleton", "Draws skeletons for players.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        setDrawn(false);
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        MatrixStack matrixStack472 = inner_3.getMatrixStack472();
        float f = inner_3.get473();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(MinecraftClient.isFancyGraphicsOrBetter());
        RenderSystem.enableCull();
        BufferBuilder begin = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        matrixStack472.push();
        for (AbstractClientPlayerEntity abstractClientPlayerEntity : minecraftClient.world.getPlayers()) {
            if (!abstractClientPlayerEntity.isDead() && abstractClientPlayerEntity != minecraftClient.player) {
                Color value = this.color.getValue();
                Vec3d vec3d2641 = getVec3d2641((Entity) abstractClientPlayerEntity, f);
                DuckLivingEntityRenderer duckLivingEntityRenderer = (DuckLivingEntityRenderer)(Object) minecraftClient.getEntityRenderDispatcher().getRenderer(abstractClientPlayerEntity);
                PlayerEntityModel model = ((PlayerEntityRenderer) duckLivingEntityRenderer).getModel();
                DuckLivingEntityRenderer duckLivingEntityRenderer2 = duckLivingEntityRenderer;
                float lerpAngleDegrees = MathHelper.lerpAngleDegrees(f, abstractClientPlayerEntity.prevBodyYaw, abstractClientPlayerEntity.bodyYaw);
                float lerp = MathHelper.lerp(f, abstractClientPlayerEntity.prevPitch, abstractClientPlayerEntity.getPitch());
                if (!animations.is1000()) {
                    float lerpAngleDegrees2 = MathHelper.lerpAngleDegrees(f, abstractClientPlayerEntity.prevHeadYaw, abstractClientPlayerEntity.headYaw) - MathHelper.lerpAngleDegrees(f, abstractClientPlayerEntity.prevBodyYaw, abstractClientPlayerEntity.bodyYaw);
                    float mio$getAnimationProgress = duckLivingEntityRenderer2.mio$getAnimationProgress(abstractClientPlayerEntity, f);
                    float f2 = 0.0f;
                    float f3 = 0.0f;
                    if (!abstractClientPlayerEntity.hasVehicle() && abstractClientPlayerEntity.isAlive()) {
                        f2 = Math.min(abstractClientPlayerEntity.limbAnimator.getSpeed(f), 1.0f);
                        f3 = abstractClientPlayerEntity.limbAnimator.getPos(f);
                        if (abstractClientPlayerEntity.isBaby()) {
                            f3 *= 3.0f;
                        }
                    }
                    model.animateModel((LivingEntity) abstractClientPlayerEntity, f3, f2, f);
                    model.setAngles((LivingEntity) abstractClientPlayerEntity, f3, f2, mio$getAnimationProgress, lerpAngleDegrees2, lerp);
                }
                boolean isInSwimmingPose = abstractClientPlayerEntity.isInSwimmingPose();
                boolean z = abstractClientPlayerEntity.isSneaking() || animations.is1001();
                boolean isFallFlying = abstractClientPlayerEntity.isFallFlying();
                ModelPart modelPart = model.head;
                ModelPart modelPart2 = model.leftArm;
                ModelPart modelPart3 = model.rightArm;
                ModelPart modelPart4 = model.leftLeg;
                ModelPart modelPart5 = model.rightLeg;
                matrixStack472.translate(vec3d2641.x, vec3d2641.y, vec3d2641.z);
                if (isInSwimmingPose) {
                    matrixStack472.translate(0.0f, 0.3499999940395355f, 0.0f);
                }
                matrixStack472.multiply(new Quaternionf().setAngleAxis(((lerpAngleDegrees + 180.0f) * FreecamHelper.val) / 180.0, 0.0d, -1.0, 0.0d));
                if (isInSwimmingPose || isFallFlying) {
                    matrixStack472.multiply(new Quaternionf().setAngleAxis(((FreecamHelper.num2 + lerp) * FreecamHelper.val) / 180.0, -1.0, 0.0d, 0.0d));
                }
                if (isInSwimmingPose) {
                    matrixStack472.translate(0.0f, -0.949999988079071f, 0.0f);
                }
                if (animations.is999()) {
                    matrixStack472.scale(animations.playerScale.getValue().floatValue(), animations.playerScale.getValue().floatValue(), animations.playerScale.getValue().floatValue());
                }
                Matrix4f positionMatrix = matrixStack472.peek().getPositionMatrix();
                begin.vertex(positionMatrix, 0.0f, z ? 0.6000000238418579f : 0.699999988079071f, z ? 0.23000000417232513f : 0.0f).color(value.getRGB());
                begin.vertex(positionMatrix, 0.0f, z ? 1.0499999523162842f : 1.399999976158142f, 0.0f).color(value.getRGB());
                begin.vertex(positionMatrix, -0.3700000047683716f, z ? 1.0499999523162842f : 1.350000023841858f, 0.0f).color(value.getRGB());
                begin.vertex(positionMatrix, 0.3700000047683716f, z ? 1.0499999523162842f : 1.350000023841858f, 0.0f).color(value.getRGB());
                begin.vertex(positionMatrix, -0.15000000596046448f, z ? 0.6000000238418579f : 0.699999988079071f, z ? 0.23000000417232513f : 0.0f).color(value.getRGB());
                begin.vertex(positionMatrix, 0.15000000596046448f, z ? 0.6000000238418579f : 0.699999988079071f, z ? 0.23000000417232513f : 0.0f).color(value.getRGB());
                matrixStack472.push();
                matrixStack472.translate(0.0f, z ? 1.0499999523162842f : 1.399999976158142f, 0.0f);
                do2640(matrixStack472, modelPart);
                Matrix4f positionMatrix2 = matrixStack472.peek().getPositionMatrix();
                begin.vertex(positionMatrix2, 0.0f, 0.0f, 0.0f).color(value.getRGB());
                begin.vertex(positionMatrix2, 0.0f, 0.15000000596046448f, 0.0f).color(value.getRGB());
                matrixStack472.pop();
                matrixStack472.push();
                matrixStack472.translate(0.15000000596046448f, z ? 0.6000000238418579f : 0.699999988079071f, z ? 0.23000000417232513f : 0.0f);
                do2640(matrixStack472, modelPart5);
                Matrix4f positionMatrix3 = matrixStack472.peek().getPositionMatrix();
                begin.vertex(positionMatrix3, 0.0f, 0.0f, 0.0f).color(value.getRGB());
                begin.vertex(positionMatrix3, 0.0f, -0.6000000238418579f, 0.0f).color(value.getRGB());
                matrixStack472.pop();
                matrixStack472.push();
                matrixStack472.translate(-0.15000000596046448f, z ? 0.6000000238418579f : 0.699999988079071f, z ? 0.23000000417232513f : 0.0f);
                do2640(matrixStack472, modelPart4);
                Matrix4f positionMatrix4 = matrixStack472.peek().getPositionMatrix();
                begin.vertex(positionMatrix4, 0.0f, 0.0f, 0.0f).color(value.getRGB());
                begin.vertex(positionMatrix4, 0.0f, -0.6000000238418579f, 0.0f).color(value.getRGB());
                matrixStack472.pop();
                matrixStack472.push();
                matrixStack472.translate(0.3700000047683716f, z ? 1.0499999523162842f : 1.350000023841858f, 0.0f);
                do2640(matrixStack472, modelPart3);
                Matrix4f positionMatrix5 = matrixStack472.peek().getPositionMatrix();
                begin.vertex(positionMatrix5, 0.0f, 0.0f, 0.0f).color(value.getRGB());
                begin.vertex(positionMatrix5, 0.0f, -0.550000011920929f, 0.0f).color(value.getRGB());
                matrixStack472.pop();
                matrixStack472.push();
                matrixStack472.translate(-0.3700000047683716f, z ? 1.0499999523162842f : 1.350000023841858f, 0.0f);
                do2640(matrixStack472, modelPart2);
                Matrix4f positionMatrix6 = matrixStack472.peek().getPositionMatrix();
                begin.vertex(positionMatrix6, 0.0f, 0.0f, 0.0f).color(value.getRGB());
                begin.vertex(positionMatrix6, 0.0f, -0.550000011920929f, 0.0f).color(value.getRGB());
                matrixStack472.pop();
                if (isInSwimmingPose) {
                    matrixStack472.translate(0.0f, 0.949999988079071f, 0.0f);
                }
                if (isInSwimmingPose || isFallFlying) {
                    matrixStack472.multiply(new Quaternionf().setAngleAxis(((FreecamHelper.num2 + lerp) * FreecamHelper.val) / 180.0, 1.0, 0.0d, 0.0d));
                }
                if (isInSwimmingPose) {
                    matrixStack472.translate(0.0f, -0.3499999940395355f, 0.0f);
                }
                matrixStack472.multiply(new Quaternionf().setAngleAxis(((lerpAngleDegrees + 180.0f) * FreecamHelper.val) / 180.0, 0.0d, 1.0, 0.0d));
                matrixStack472.translate(-vec3d2641.x, -vec3d2641.y, -vec3d2641.z);
            }
        }
        matrixStack472.pop();
        TrajectoriesVertexConsumer.do2599(begin);
        RenderSystem.disableCull();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
    }

    public void do2640(MatrixStack matrixStack, ModelPart modelPart) {
        if (modelPart.roll != 0.0f) {
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotation(modelPart.roll));
        }
        if (modelPart.yaw != 0.0f) {
            matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotation(modelPart.yaw));
        }
        if (modelPart.pitch != 0.0f) {
            matrixStack.multiply(RotationAxis.NEGATIVE_X.rotation(modelPart.pitch));
        }
    }

    public Vec3d getVec3d2641(Entity entity, double d) {
        return new Vec3d((entity.prevX + ((entity.getX() - entity.prevX) * d)) - minecraftClient.getEntityRenderDispatcher().camera.getPos().x, (entity.prevY + ((entity.getY() - entity.prevY) * d)) - minecraftClient.getEntityRenderDispatcher().camera.getPos().y, (entity.prevZ + ((entity.getZ() - entity.prevZ) * d)) - minecraftClient.getEntityRenderDispatcher().camera.getPos().z);
    }
}
