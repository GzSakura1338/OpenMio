package me.mioclient.module.render;

import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/PhaseESP.class */
public class PhaseESP extends Module {
    public Setting<Color> safe;
    public Setting<Color> semiSafe;
    public Setting<Color> unsafe;
    public Setting<Boolean> outline;
    public Setting<Float> alpha;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/PhaseESP$PhaseESPMode.class */
    public static enum PhaseESPMode {
        phaseESPMode {
            @Override
            public Color getColor16(PhaseESP phaseESP) {
                return phaseESP.safe.getValue();
            }
        },
        phaseESPMode2 {
            @Override
            public Color getColor16(PhaseESP phaseESP) {
                return phaseESP.semiSafe.getValue();
            }
        },
        phaseESPMode3 {
            @Override
            public Color getColor16(PhaseESP phaseESP) {
                return phaseESP.unsafe.getValue();
            }
        };

        public Color getColor16(PhaseESP phaseESP) {
            return null;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/PhaseESP$Record.class */
    private static final class Record {
        public final BlockPos blockPos;
        public final PhaseESPMode phaseESPMode;

        public Record(BlockPos blockPos, PhaseESPMode phaseESPMode) {
            this.blockPos = blockPos;
            this.phaseESPMode = phaseESPMode;
        }




        public BlockPos getBlockPos12() {
            return this.blockPos;
        }

        public PhaseESPMode getPhaseESPMode841() {
            return this.phaseESPMode;
        }
    }

    public PhaseESP() {
        super("PhaseESP", "Highlights safe blocks to phase into.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        Box expand = minecraftClient.player.getBoundingBox().withMaxY(minecraftClient.player.getBoundingBox().minY).expand(0.01, 0.0d, 0.01);
        if (PhaseESPSearchHelper4_2.is3045(minecraftClient.player.getBlockPos()) || minecraftClient.player.isInSwimmingPose()) {
            return;
        }
        BlockPos.stream(expand).filter(this::is8).map(blockPos -> {
            return new Record(blockPos, getPhaseESPMode6(blockPos));
        }).forEach(record -> {
            VoxelShape outlineShape = minecraftClient.world.getBlockState(record.blockPos).getOutlineShape(minecraftClient.world, record.blockPos);
            if (outlineShape.isEmpty()) {
                outlineShape = VoxelShapes.cuboid(0.0d, 0.0d, 0.0d, 1.0, 1.0, 1.0);
            }
            Box offset = outlineShape.getBoundingBox().offset(record.blockPos);
            PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), offset.withMaxY(record.blockPos.getY()), record.phaseESPMode.getColor16(this));
            if (this.outline.getValue().booleanValue()) {
                PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), offset.withMaxY(record.blockPos.getY()), MixinMessageIndicatorHelper_2.getColor817(record.phaseESPMode.getColor16(this), this.alpha.getValue().floatValue()), 1.0f);
            }
        });
    }

    public PhaseESPMode getPhaseESPMode6(BlockPos blockPos) {
        boolean z = minecraftClient.world.getBlockState(blockPos).getBlock().getBlastResistance() >= 600.0f;
        if (PhaseESPSearchHelper4_2.is3045(blockPos.down()) && z) {
            if (minecraftClient.world.getBlockState(blockPos.down()).getBlock().getBlastResistance() >= 600.0f) {
                return (is7(blockPos) || is7(blockPos.down())) ? PhaseESPMode.phaseESPMode2 : PhaseESPMode.phaseESPMode;
            }
        }
        return PhaseESPMode.phaseESPMode3;
    }

    public boolean is7(BlockPos blockPos) {
        return PhaseESPSearchHelper4_2.getBlock3044(blockPos) != Blocks.BEDROCK;
    }

    public boolean is8(BlockPos blockPos) {
        BlockState blockState = minecraftClient.world.getBlockState(blockPos);
        return blockState.getBlock().getBlastResistance() >= 600.0f && PhaseESPSearchHelper4_2.is3045(blockPos) && !blockState.isReplaceable();
    }
}
