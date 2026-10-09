package me.mioclient.module.render;

import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.SearchHelper;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper4_9;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchHelper_3;
import me.mioclient.SearchIdentifier;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.DisconnectEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.client.Colors;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.chunk.Chunk;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Search.class */
public class Search extends Module {
    public static Colors colors = (Colors) BaritoneHelper_3.baritoneHelper_4.getModule117(Colors.class);
    public static final BlockPos.Mutable mutable = new BlockPos.Mutable();
    public Setting<Set<Block>> whitelist;
    public Setting<Set<EntityType<?>>> entityTypes;
    public Setting<Boolean> entities;
    public Setting<Boolean> ignoreNeg;
    public Setting<Boolean> fill;
    public Setting<Float> fillOpacity;
    public Setting<Boolean> outline;
    public Setting<Boolean> tracers;
    public Setting<Float> tracerOpacity;
    public Setting<Boolean> sound;
    public Setting<SearchIdentifier> type;
    public Setting<Float> volume;
    public Setting<Boolean> ignoreNatural;
    public Setting<Boolean> dungeon;
    public Setting<Boolean> fortress;
    public Setting<Boolean> bastion;
    public Setting<Boolean> ancientCity;
    public Setting<Boolean> trialChambers;
    public final Stopwatch stopwatch;
    public final Set<BlockPos> set;
    public final SearchHelper4_9 searchHelper4_9;
    public boolean flag;

    public Search() {
        super("Search", "Helps finding whitelisted stuff.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.set = Collections.synchronizedSet(new HashSet());
        this.searchHelper4_9 = new SearchHelper4_9(1, (chunkPos, worldChunk) -> {
            if (this.set.size() > 100000) {
                return;
            }
            Set<Block> value = this.whitelist.getValue();
            for (int i = 0; i < 16; i++) {
                for (int bottomY = this.ignoreNeg.getValue().booleanValue() ? 0 : minecraftClient.world.getBottomY(); bottomY < minecraftClient.world.getTopY(); bottomY++) {
                    for (int i2 = 0; i2 < 16; i2++) {
                        mutable.set(chunkPos.getStartX() + i, bottomY, chunkPos.getStartZ() + i2);
                        BlockState blockState = worldChunk.getBlockState(mutable);
                        if (!blockState.isAir()) {
                            if (this.ignoreNatural.getValue().booleanValue()) {
                                if (SearchHelper4_7.is2428((Chunk) worldChunk, mutable, this.fortress.getValue().booleanValue(), this.bastion.getValue().booleanValue(), this.ancientCity.getValue().booleanValue(), this.dungeon.getValue().booleanValue(), this.trialChambers.getValue().booleanValue())) {
                                }
                            }
                            if (value.contains(blockState.getBlock())) {
                                this.flag = true;
                                this.set.add(mutable.toImmutable());
                            }
                        }
                    }
                }
            }
        }, (chunkPos2, worldChunk2) -> {
            this.set.removeIf(blockPos -> {
                return blockPos.getX() >= chunkPos2.getStartX() && blockPos.getX() <= chunkPos2.getEndX() && blockPos.getZ() >= chunkPos2.getStartZ() && blockPos.getZ() <= chunkPos2.getEndZ();
            });
        }, (blockPos, blockState) -> {
            if (!this.whitelist.getValue().contains(blockState.getBlock())) {
                this.set.remove(blockPos);
            } else if (!blockState.isAir() && this.set.add(blockPos)) {
                this.flag = true;
            }
        });
        this.whitelist.do2339(() -> {
            if (minecraftClient.world == null || this.searchHelper4_9.getExecutorService2690() == null) {
                return;
            }
            this.set.clear();
            this.searchHelper4_9.do2689();
        });
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.flag = false;
        this.searchHelper4_9.do1640();
        if (is1469()) {
            return;
        }
        this.searchHelper4_9.do2689();
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        this.set.clear();
        this.searchHelper4_9.do2687();
    }

    @Listen
    public void onDisconnect(DisconnectEvent disconnectEvent) {
        do59();
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (channelRead0Event.getPacket904() instanceof PlayerRespawnS2CPacket) {
            do59();
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (this.sound.getValue().booleanValue() && this.flag) {
            this.flag = false;
            if (this.stopwatch.is419(100L)) {
                BaritoneHelper_3.searchHelper4_11.getSearchHelper4_42970(this.type.getValue()).do1820(this.volume.getValue().floatValue());
                this.stopwatch.reset();
            }
        }
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (this.entities.getValue().booleanValue()) {
            for (Entity entity : minecraftClient.world.getEntities()) {
                if (this.entityTypes.getValue().contains(entity.getType())) {
                    Box box233 = SearchHelper.getBox233(entity, inner_3.get473());
                    Color color61 = getColor61(entity);
                    colors.scheme.getValue().do1027(() -> {
                        if (SearchHelper4_8.is2492(box233)) {
                            if (this.fill.getValue().booleanValue()) {
                                PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), box233, MixinMessageIndicatorHelper_2.getColor816(color61, (int) (255.0f * this.fillOpacity.getValue().floatValue())));
                            }
                            if (this.outline.getValue().booleanValue()) {
                                PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), box233, color61, 1.0f);
                            }
                        }
                        if (this.tracers.getValue().booleanValue()) {
                            Camera camera = minecraftClient.gameRenderer.getCamera();
                            SearchHelper_2.searchHelper_2.do561(inner_3.getMatrixStack472(), new Vec3d(0.0d, 0.0d, 1.0).rotateX(-((float) Math.toRadians(camera.getPitch()))).rotateY(-((float) Math.toRadians(camera.getYaw()))).add(minecraftClient.getEntityRenderDispatcher().camera.getPos()), box233.getCenter(), MixinMessageIndicatorHelper_2.getColor816(color61, (int) (255.0f * this.tracerOpacity.getValue().floatValue())));
                        }
                    });
                }
            }
        }
        synchronized (this.set) {
            for (BlockPos blockPos : this.set) {
                BlockState blockState = minecraftClient.world.getBlockState(blockPos);
                if (!blockState.isAir() && !blockState.isOf(Blocks.AIR)) {
                    VoxelShape outlineShape = blockState.getOutlineShape(minecraftClient.world, blockPos);
                    if (outlineShape.isEmpty()) {
                        outlineShape = VoxelShapes.cuboid(0.0d, 0.0d, 0.0d, 1.0, 1.0, 1.0);
                    }
                    Box offset = outlineShape.getBoundingBox().offset(blockPos);
                    Color color60 = getColor60(blockState, blockPos);
                    colors.scheme.getValue().do1027(() -> {
                        if (SearchHelper4_8.is2492(offset)) {
                            if (this.fill.getValue().booleanValue()) {
                                PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), offset, MixinMessageIndicatorHelper_2.getColor816(color60, (int) (255.0f * this.fillOpacity.getValue().floatValue())));
                            }
                            if (this.outline.getValue().booleanValue()) {
                                PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), offset, color60, 1.0f);
                            }
                        }
                        if (this.tracers.getValue().booleanValue()) {
                            Camera camera = minecraftClient.gameRenderer.getCamera();
                            SearchHelper_2.searchHelper_2.do561(inner_3.getMatrixStack472(), new Vec3d(0.0d, 0.0d, 1.0).rotateX(-((float) Math.toRadians(camera.getPitch()))).rotateY(-((float) Math.toRadians(camera.getYaw()))).add(minecraftClient.getEntityRenderDispatcher().camera.getPos()), Vec3d.ofCenter((Vec3i) blockPos), MixinMessageIndicatorHelper_2.getColor816(color60, (int) (255.0f * this.tracerOpacity.getValue().floatValue())));
                        }
                    });
                }
            }
        }
    }

    public void do59() {
        this.searchHelper4_9.do2688();
        this.set.clear();
    }

    public Color getColor60(BlockState blockState, BlockPos blockPos) {
        if (blockState.getBlock() == Blocks.NETHER_PORTAL) {
            return new Color(144, 28, 255);
        }
        if (blockState.getBlock() == Blocks.ENDER_CHEST) {
            return new Color(125, 40, 180);
        }
        int renderColor = blockState.getMapColor(minecraftClient.world, blockPos).getRenderColor(MapColor.Brightness.HIGH);
        BedBlock block = (blockState.getBlock()) instanceof BedBlock ? (BedBlock) (blockState.getBlock()) : null;
        if (block instanceof BedBlock) {
            renderColor = block.getColor().getMapColor().getRenderColor(MapColor.Brightness.HIGH);
        }
        return new Color(renderColor & 255, (renderColor >> 8) & 255, (renderColor >> 16) & 255);
    }

    public Color getColor61(Entity entity) {
        Color color = Color.GRAY;
        if (entity instanceof AbstractMinecartEntity) {
            color = Color.blue;
        }
        if (SearchHelper_3.is645(entity)) {
            color = Color.RED;
        }
        if (entity instanceof PlayerEntity) {
            color = BaritoneHelper_3.searchHelper4_14.getColor530(((PlayerEntity) entity).getName().getString(), color);
        }
        if ((entity instanceof AnimalEntity) || (entity instanceof FishEntity)) {
            color = Color.GREEN;
        }
        return color;
    }
}
