package me.mioclient.module.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.Helper_7;
import me.mioclient.LegacyCrystalSearchHelper4;
import me.mioclient.MatrixStackEvent_2;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchIdentifier;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.mixin.ducks.DuckPlayerInteractEntityC2SPacket;
import me.mioclient.module.Module;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Hitmarker.class */
public class Hitmarker extends Module {
    public Setting<Boolean> draw;
    public Setting<Float> length;
    public Setting<Float> time;
    public Setting<Float> fadeTime;
    public Setting<Color> color;
    public Setting<Color> outlineColor;
    public Setting<Boolean> sound2;
    public Setting<SearchIdentifier> sound;
    public Setting<Float> volume;
    public Setting<Boolean> targets;
    public Setting<Boolean> animals;
    public Setting<Boolean> hostiles;
    public Setting<Boolean> players;
    public Setting<Boolean> crystals;
    public static Crosshair crosshair = (Crosshair) BaritoneHelper_3.baritoneHelper_4.getModule117(Crosshair.class);
    public long num;
    public int num2;
    public boolean flag;

    public Hitmarker() {
        super("Hitmarker", "Marks your screen when you hit entities.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.num = -1L;
        this.num2 = 255;
        this.flag = false;
        setDrawn(false);
        this.sound.do2329("Type");
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        this.num = -1L;
        this.num2 = 255;
        this.flag = false;
    }

    @Listen
    public void onEvent(MatrixStackEvent_2 matrixStackEvent_2) {
        if (is1469() || this.num == -1) {
            return;
        }
        if (this.flag) {
            this.flag = false;
            if (this.sound2.getValue().booleanValue()) {
                BaritoneHelper_3.searchHelper4_11.do2971(this.sound.getValue(), this.volume.getValue().floatValue());
            }
        }
        long currentTimeMillis = System.currentTimeMillis() - this.num;
        if (currentTimeMillis > this.time.getValue().floatValue() * 1000.0f) {
            this.num = -1L;
            return;
        }
        do1120(currentTimeMillis);
        if (this.num2 <= 0 || !this.draw.getValue().booleanValue()) {
            return;
        }
        float scaledWidth = minecraftClient.getWindow().getScaledWidth() / 2.0f;
        float scaledHeight = (minecraftClient.getWindow().getScaledHeight() / 2.0f) - 0.5f;
        if (crosshair.isToggled()) {
            scaledHeight = (float) (scaledHeight + 0.5);
        } else {
            scaledWidth = (float) (scaledWidth - 0.5);
        }
        Color color816 = MixinMessageIndicatorHelper_2.getColor816(this.outlineColor.getValue(), this.num2);
        Color color8162 = MixinMessageIndicatorHelper_2.getColor816(this.color.getValue(), this.num2);
        float intBitsToFloat = 2.0f + this.length.getValue().floatValue();
        int i = 0;
        while (i < 2) {
            Color color = i == 0 ? color816 : color8162;
            matrixStackEvent_2.getMatrixStack472().push();
            if (crosshair.isToggled()) {
                matrixStackEvent_2.getMatrixStack472().translate(-0.6, 0.0d, 0.0d);
            }
            do1122(matrixStackEvent_2.getMatrixStack472(), color, scaledWidth - intBitsToFloat, scaledHeight - intBitsToFloat, scaledWidth - 2.0f, scaledHeight - 2.0f);
            do1122(matrixStackEvent_2.getMatrixStack472(), color, scaledWidth - intBitsToFloat, scaledHeight + intBitsToFloat, scaledWidth - 2.0f, scaledHeight + 2.0f);
            do1122(matrixStackEvent_2.getMatrixStack472(), color, scaledWidth + 2.0f, scaledHeight - 2.0f, scaledWidth + intBitsToFloat, scaledHeight - intBitsToFloat);
            do1122(matrixStackEvent_2.getMatrixStack472(), color, scaledWidth + 2.0f, scaledHeight + 2.0f, scaledWidth + intBitsToFloat, scaledHeight + intBitsToFloat);
            matrixStackEvent_2.getMatrixStack472().pop();
            i++;
        }
    }

    @Listen(get219= Helper_7.num4)
    public void onSendImmediately(SendImmediatelyEvent sendImmediatelyEvent) {
        if (is1469() || sendImmediatelyEvent.is2403()) {
            return;
        }
        DuckPlayerInteractEntityC2SPacket packet904 = (DuckPlayerInteractEntityC2SPacket)(sendImmediatelyEvent.getPacket904());
        if (packet904 instanceof PlayerInteractEntityC2SPacket) {
            DuckPlayerInteractEntityC2SPacket duckPlayerInteractEntityC2SPacket = (DuckPlayerInteractEntityC2SPacket)((PlayerInteractEntityC2SPacket) packet904);
            if (LegacyCrystalSearchHelper4.getLegacyCrystalMode2611((PlayerInteractEntityC2SPacket) duckPlayerInteractEntityC2SPacket) == LegacyCrystalSearchHelper4.LegacyCrystalMode.ATTACK) {
                Entity entityById = minecraftClient.world.getEntityById(duckPlayerInteractEntityC2SPacket.getEntityId());
                if (entityById == null || entityById.getWorld() == null || entityById == minecraftClient.player || !entityById.getWorld().isClient || !is1121(entityById)) {
                    return;
                }
                this.num = System.currentTimeMillis();
                this.num2 = 255;
                this.flag = true;
            }
        }
    }

    public void do1120(long j) {
        if (j < (this.time.getValue().floatValue() * 1000.0f) - (this.fadeTime.getValue().floatValue() * 1000.0f)) {
            this.num2 = 255;
            return;
        }
        this.num2 = (int) (this.num2 - ((255.0f / this.fadeTime.getValue().floatValue()) * BaritoneHelper_3.hitmarkerSearchHelper4.get3095(1.0f)));
        this.num2 = MathHelper.clamp(this.num2, 0, 255);
    }

    public boolean is1121(Entity entity) {
        return (this.animals.getValue().booleanValue() && (entity instanceof PassiveEntity)) || (this.hostiles.getValue().booleanValue() && (entity instanceof Monster)) || ((this.players.getValue().booleanValue() && (entity instanceof PlayerEntity)) || (this.crystals.getValue().booleanValue() && (entity instanceof EndCrystalEntity)));
    }

    public static void do1122(MatrixStack matrixStack, Color color, float f, float f2, float f3, float f4) {
        float[] fArr = {color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f};
        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
        BufferBuilder begin = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        begin.vertex(positionMatrix, f, f2, 0.0f).color(fArr[0], fArr[1], fArr[2], fArr[3]);
        begin.vertex(positionMatrix, f3, f4, 0.0f).color(fArr[0], fArr[1], fArr[2], fArr[3]);
        SearchHelper_2.searchHelper_2.do562();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferRenderer.drawWithGlobalProgram(begin.end());
        SearchHelper_2.searchHelper_2.do564();
    }
}
