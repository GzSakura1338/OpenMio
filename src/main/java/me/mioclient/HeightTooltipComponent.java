package me.mioclient;

import com.mojang.blaze3d.systems.RenderSystem;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.map.MapState;
import net.minecraft.util.Identifier;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HeightTooltipComponent.class */
public final class HeightTooltipComponent implements SearchHelper_4, TooltipComponent {
    public final int num;
    public final MapState mapState;
    public static final Identifier identifier = Identifier.of("textures/map/map_background.png");

    public HeightTooltipComponent(int i, MapState mapState) {
        this.num = i;
        this.mapState = mapState;
    }

    public int getHeight() {
        return 110;
    }

    public int getWidth(TextRenderer textRenderer) {
        return 108;
    }

    public void drawItems(TextRenderer textRenderer, int i, int i2, DrawContext drawContext) {
        MatrixStack matrices = drawContext.getMatrices();
        matrices.push();
        matrices.translate(i, i2, 0.0f);
        matrices.scale(1.5f, 1.5f, 0.0f);
        matrices.scale(1.125f, 1.125f, 0.0f);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        drawContext.drawTexture(identifier, 0, 0, 0, 0.0f, 0.0f, 64, 64, 64, 64);
        matrices.pop();
        VertexConsumerProvider.Immediate entityVertexConsumers = minecraftClient.getBufferBuilders().getEntityVertexConsumers();
        matrices.push();
        matrices.translate(i, i2, 0.0f);
        matrices.scale(0.75f, 0.75f, 0.0f);
        matrices.translate(8.0f, 8.0f, 0.0f);
        minecraftClient.gameRenderer.getMapRenderer().draw(matrices, (VertexConsumerProvider) entityVertexConsumers, new MapIdComponent(this.num), this.mapState, false, 15728880);
        entityVertexConsumers.draw();
        matrices.pop();
    }




    public int get391() {
        return this.num;
    }

    public MapState getMapState392() {
        return this.mapState;
    }
}
