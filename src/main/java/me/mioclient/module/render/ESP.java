package me.mioclient.module.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.ESPHelper;
import me.mioclient.ESPPredicateMode;
import me.mioclient.ESPSearchHelper4;
import me.mioclient.ESPSearchHelper4_2;
import me.mioclient.ESPSearchHelper4_3;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.Helper_7;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.PingSpoofHelper;
import me.mioclient.SearchHelper;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper_2;
import me.mioclient.ShaderSearchHelper4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import me.mioclient.module.client.Colors;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.chunk.light.ChunkLightingView;
import org.joml.Matrix4f;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/ESP.class */
public class ESP extends Module {
    public static Colors colors = (Colors) BaritoneHelper_3.baritoneHelper_4.getModule117(Colors.class);
    public Setting<Boolean> targets;
    public Setting<Boolean> mobOwner;
    public Setting<Boolean> light;
    public Setting<Boolean> hostiles;
    public Setting<Color> fill5;
    public Setting<Color> outline2;
    public Setting<Boolean> players;
    public Setting<Color> fill6;
    public Setting<Color> outline4;
    public Setting<Boolean> animals;
    public Setting<Color> fill2;
    public Setting<Color> outline6;
    public Setting<Boolean> pearls;
    public Setting<Color> fill3;
    public Setting<Color> outline;
    public Setting<Boolean> items;
    public Setting<ESPPredicateMode> mode;
    public Setting<Integer> range;
    public Setting<Boolean> group;
    public Setting<Color> itemsText;
    public Setting<Color> background;
    public Setting<Float> scale;
    public Setting<Color> fill4;
    public Setting<Color> outline3;
    public Setting<Boolean> exp;
    public Setting<Color> fill;
    public Setting<Color> outline5;
    public Setting<Boolean> chorus;
    public Setting<Color> chorusText;
    public Setting<Boolean> blocks;
    public Setting<Boolean> chests;
    public Setting<Boolean> eChests;
    public Setting<Boolean> shulkers;
    public Setting<Boolean> beds;
    public Setting<Boolean> signs;
    public Setting<Boolean> dispensers;
    public Setting<Boolean> hoppers;
    public Setting<Boolean> furnaces;
    public Setting<Boolean> pots;
    public Setting<Float> lineWidth;
    public boolean flag;
    public final ESPSearchHelper4_2 eSPSearchHelper4_2;
    public final Map<Vec3d, Long> map;

    public ESP() {
        super("ESP", "Highlights entities through walls.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.eSPSearchHelper4_2 = new ESPSearchHelper4_2(this);
        this.map = Collections.synchronizedMap(new HashMap());
        this.fill2.do2329("AnimalsFill");
        this.outline6.do2329("AnimalsOutline");
        this.fill5.do2329("HostilesFill");
        this.outline2.do2329("HostilesOutline");
        this.fill6.do2329("PlayersFill");
        this.outline4.do2329("PlayersOutline");
        this.fill4.do2329("ItemsFill");
        this.outline3.do2329("ItemsOutline");
        this.fill.do2329("ExpFill");
        this.outline5.do2329("ExpOutline");
        this.mode.do2329("ItemsMode");
        this.range.do2329("ItemRange");
        setDrawn(false);
    }

    @Listen
    public void onEvent2(MatrixStackEvent.Inner inner) {
        if (this.light.getValue().booleanValue()) {
            do1917(inner);
        }
    }

    @Listen(get219= -99999999)
    public void onEvent3(MatrixStackEvent.Inner_3 inner_3) {
        if (is1469() || !this.eSPSearchHelper4_2.is1765()) {
            return;
        }
        ESPSearchHelper4_3.do2888(ShaderSearchHelper4.shaderFramebufferHelper5, false);
        inner_3.getMatrixStack472().push();
        this.flag = true;
        Vec3d pos = minecraftClient.gameRenderer.getCamera().getPos();
        OutlineVertexConsumerProvider outlineVertexConsumerProvider = ShaderSearchHelper4.shaderFramebufferHelper5.outlineVertexConsumerProvider;
        ESPHelper.do1104();
        for (BlockEntity blockEntity : BaritoneHelper_3.stashFinderSearchHelper4.getList1555()) {
            if (this.eSPSearchHelper4_2.is1764(blockEntity)) {
                double x = blockEntity.getPos().getX() - pos.x;
                double y = blockEntity.getPos().getY() - pos.y;
                double z = blockEntity.getPos().getZ() - pos.z;
                inner_3.getMatrixStack472().push();
                inner_3.getMatrixStack472().translate(x, y, z);
                Color color1596 = PhaseESPSearchHelper4.getColor1596(this.eSPSearchHelper4_2.getColor1770(blockEntity), colors.scheme.getValue().getFloatArray1028());
                outlineVertexConsumerProvider.setColor(color1596.getRed(), color1596.getGreen(), color1596.getBlue(), 255);
                if (minecraftClient.getBlockEntityRenderDispatcher().get(blockEntity) == null) {
                    minecraftClient.getBlockRenderManager().renderBlock(blockEntity.getCachedState(), blockEntity.getPos(), minecraftClient.world, inner_3.getMatrixStack472(), outlineVertexConsumerProvider.getBuffer(RenderLayer.getSolid()), false, minecraftClient.world.getRandom());
                } else {
                    minecraftClient.getBlockEntityRenderDispatcher().render(blockEntity, inner_3.get473(), inner_3.getMatrixStack472(), (VertexConsumerProvider) outlineVertexConsumerProvider);
                }
                inner_3.getMatrixStack472().pop();
            }
        }
        ESPHelper.do1105();
        minecraftClient.getBufferBuilders().getEntityVertexConsumers().draw();
        this.flag = false;
        inner_3.getMatrixStack472().pop();
        ESPSearchHelper4_3.do2889(false);
    }

    @Listen(get219= Helper_7.num5)
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (is1469()) {
            return;
        }
        ArrayList<Entity> arrayList = new ArrayList();
        Iterator it = minecraftClient.world.getEntities().iterator();
        while (it.hasNext()) {
            arrayList.add((Entity) it.next());
        }
        arrayList.sort(Comparator.comparing(entity -> {
            return Integer.valueOf(-entity.age);
        }));
        ArrayList arrayList2 = new ArrayList();
        for (Entity entity2 : arrayList) {
            if (this.eSPSearchHelper4_2.is1763(entity2)) {
                PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), SearchHelper.getBox233(entity2, inner_3.get473()), this.eSPSearchHelper4_2.getColor1769(entity2, true));
                PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), SearchHelper.getBox233(entity2, inner_3.get473()), this.eSPSearchHelper4_2.getColor1769(entity2, false), this.lineWidth.getValue().floatValue());
            }
            if (entity2 instanceof ItemEntity) {
                ItemEntity itemEntity = (ItemEntity) entity2;
                if (this.items.getValue().booleanValue() && (this.mode.getValue() == ESPPredicateMode.BOTH || this.mode.getValue() == ESPPredicateMode.TEXT)) {
                    Vec3d pos = minecraftClient.gameRenderer.getCamera().getPos();
                    if (!(entity2 instanceof ItemEntity) || entity2.getPos().distanceTo(pos) <= this.range.getValue().intValue()) {
                        if (this.group.getValue().booleanValue()) {
                            boolean z = false;
                            Iterator it2 = arrayList2.iterator();
                            while (it2.hasNext()) {
                                if (((ESPSearchHelper4) it2.next()).is1749(itemEntity)) {
                                    z = true;
                                }
                            }
                            if (!z) {
                                arrayList2.add(new ESPSearchHelper4(itemEntity));
                            }
                        } else {
                            String string = itemEntity.getStack().getName().getString();
                            if (itemEntity.getStack().getCount() > 1) {
                                string = new ArgumentTypeHelper().getArgumentTypeHelper2906(itemEntity.getStack().getCount()).getArgumentTypeHelper2919(string).getString2921("\u0001 x\u0001");
                            }
                            this.eSPSearchHelper4_2.do1766(inner_3, string, itemEntity.getLerpedPos(inner_3.get473()).add(0.0d, 0.6000000238418579, 0.0d), this.scale.getValue().floatValue(), this.itemsText.getValue(), this.background.getValue());
                        }
                    }
                }
            }
            String string1768 = this.eSPSearchHelper4_2.getString1768(entity2);
            if (string1768 != null) {
                Vec3d add = entity2.getLerpedPos(inner_3.get473()).add(0.0d, 0.2, 0.0d);
                if (!(entity2 instanceof EnderPearlEntity)) {
                    add = add.add(0.0d, 1.3, 0.0d);
                }
                this.eSPSearchHelper4_2.do1766(inner_3, string1768, add, 1.0f, Color.white, (Color) null);
            }
        }
        if (this.group.getValue().booleanValue()) {
            do1915(inner_3, arrayList2);
        }
        if (this.chorus.getValue().booleanValue()) {
            synchronized (this.map) {
                for (Map.Entry<Vec3d, Long> entry : this.map.entrySet()) {
                    this.eSPSearchHelper4_2.do1766(inner_3, "Player teleport", entry.getKey(), 1.0f, this.chorusText.getValue(), (Color) null);
                }
            }
        }
    }

    public void do1915(MatrixStackEvent matrixStackEvent, List<ESPSearchHelper4> list) {
        for (ESPSearchHelper4 eSPSearchHelper4 : list) {
            if (!this.group.getValue().booleanValue()) {
                return;
            }
            Vec3d center = eSPSearchHelper4.getBox1748().getCenter();
            double d = PingSpoofHelper.get377(minecraftClient.gameRenderer.getCamera().getPos(), center, this.scale.getValue().floatValue());
            float f = FontsSearchHelper4.fontsSearchHelper4.get93();
            float f2 = 0.0f;
            if (eSPSearchHelper4.getMap1750().size() == 1) {
                do1916(matrixStackEvent.getMatrixStack472(), eSPSearchHelper4, d, eSPSearchHelper4.getMap1750().size() * f);
            } else {
                do1916(matrixStackEvent.getMatrixStack472(), eSPSearchHelper4, d, f);
                do1916(matrixStackEvent.getMatrixStack472(), eSPSearchHelper4, d, ((eSPSearchHelper4.getMap1750().size() - 1) * (-f)) - 2.0f);
            }
            for (Map.Entry<String, Integer> entry : eSPSearchHelper4.getMap1750().entrySet()) {
                String string1752 = ESPSearchHelper4.getString1752(entry.getKey(), entry.getValue().intValue());
                SearchHelper_2.searchHelper_2.do571(matrixStackEvent.getDrawContext474(), string1752, center, 0.0f, 0.0f, (-FontsSearchHelper4.fontsSearchHelper4.get1316(string1752)) / 2.0f, f2, d, this.itemsText.getValue(), true);
                f2 -= f;
            }
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        PlaySoundS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof PlaySoundS2CPacket ? (PlaySoundS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof PlaySoundS2CPacket) {
            PlaySoundS2CPacket playSoundS2CPacket = packet904;
            if (((SoundEvent) playSoundS2CPacket.getSound().value()).equals(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT)) {
                this.map.put(new Vec3d(playSoundS2CPacket.getX(), playSoundS2CPacket.getY(), playSoundS2CPacket.getZ()), Long.valueOf(System.currentTimeMillis()));
            }
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        this.map.entrySet().removeIf(entry -> {
            return System.currentTimeMillis() - ((Long) entry.getValue()).longValue() > 3500;
        });
    }

    public void do1916(MatrixStack matrixStack, ESPSearchHelper4 eSPSearchHelper4, double d, float f) {
        SearchHelper_2.searchHelper_2.do567(matrixStack, eSPSearchHelper4.getBox1748().getCenter(), 0.0f, 0.0f, eSPSearchHelper4.get1751() + 1.0f, f, d, this.background.getValue());
    }

    public void do1917(MatrixStackEvent matrixStackEvent) {
        float intBitsToFloat = 0.75f;
        Vec3d pos = minecraftClient.getEntityRenderDispatcher().camera.getPos();
        MatrixStack matrixStack472 = matrixStackEvent.getMatrixStack472();
        BufferBuilder begin = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        ChunkLightingView chunkLightingView = minecraftClient.world.getLightingProvider().get(LightType.BLOCK);
        for (BlockPos blockPos : SearchHelper4_7.getList2429(minecraftClient.player.getEyePos(), 12.0f, true)) {
            BlockState blockState = minecraftClient.world.getBlockState(blockPos);
            if (SearchHelper4_7.is2446(blockPos.up())) {
                if (chunkLightingView.getLightLevel(blockPos.up()) <= 7) {
                    boolean matchesKey = minecraftClient.world.getBiome(blockPos).matchesKey(BiomeKeys.MUSHROOM_FIELDS);
                    if (blockState.allowsSpawning(minecraftClient.world, blockPos, EntityType.ZOMBIE) && !matchesKey) {
                        double distanceTo = minecraftClient.gameRenderer.getCamera().getPos().distanceTo(blockPos.toCenterPos());
                        float intBitsToFloat2 = 1.0f - ((float) MathHelper.clamp((distanceTo - 8.0) / 2.0, 0.0d, 1.0));
                        int hashCode = Color.red.hashCode();
                        if (distanceTo >= 8.0) {
                            hashCode = MixinMessageIndicatorHelper_2.get819(Color.red, intBitsToFloat2);
                        }
                        matrixStack472.push();
                        matrixStack472.translate(blockPos.getX() - pos.x, (blockPos.getY() - pos.y) + 1.02, blockPos.getZ() - pos.z);
                        Matrix4f positionMatrix = matrixStack472.peek().getPositionMatrix();
                        begin.vertex(positionMatrix, 1.0f - intBitsToFloat, 0.0f, 1.0f - intBitsToFloat).color(hashCode);
                        begin.vertex(positionMatrix, intBitsToFloat, 0.0f, intBitsToFloat).color(hashCode);
                        begin.vertex(positionMatrix, intBitsToFloat, 0.0f, 1.0f - intBitsToFloat).color(hashCode);
                        begin.vertex(positionMatrix, 1.0f - intBitsToFloat, 0.0f, intBitsToFloat).color(hashCode);
                        matrixStack472.pop();
                    }
                }
            }
        }
        BuiltBuffer endNullable = begin.endNullable();
        if (endNullable == null) {
            return;
        }
        BufferRenderer.drawWithGlobalProgram(endNullable);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

    public boolean is1918() {
        return this.flag;
    }
}
