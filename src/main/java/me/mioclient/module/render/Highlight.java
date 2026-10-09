package me.mioclient.module.render;

import java.awt.Color;
import java.util.Iterator;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.MatrixStackEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.DrawBlockOutlineEvent;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import me.mioclient.module.exploit.Reach;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Highlight.class */
public class Highlight extends Module {
    public static final Reach reach = (Reach) BaritoneHelper_3.baritoneHelper_4.getModule117(Reach.class);
    public Setting<Boolean> complex;
    public Setting<Boolean> unreachable;
    public Setting<Float> lineWidth;
    public Setting<Color> color;
    public Setting<Color> fillColor;

    public Highlight() {
        super("Highlight", "Highlights the block you're looking at.", Category.RENDER, "blockhighlight");
        PhaseESPHelper.do1351(this);
        setDrawn(false);
        this.unreachable.do2343(bool -> {
            return reach.isToggled() && (reach.liquidPlace.getValue().booleanValue() || reach.airPlace.getValue().booleanValue());
        });
    }

    @Listen
    public void onDrawBlockOutline(DrawBlockOutlineEvent drawBlockOutlineEvent) {
        if (is409()) {
            return;
        }
        drawBlockOutlineEvent.do1162();
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        BlockPos blockPos406;
        if (is1469() || (blockPos406 = getBlockPos406()) == null) {
            return;
        }
        BlockState blockState = minecraftClient.world.getBlockState(blockPos406);
        if (is408() || !(blockState.getBlock() instanceof AirBlock)) {
            VoxelShape outlineShape = blockState.getOutlineShape(minecraftClient.world, blockPos406);
            if (outlineShape.isEmpty()) {
                if (is408()) {
                    do407(inner_3.getMatrixStack472(), VoxelShapes.fullCube().getBoundingBox(), blockPos406);
                }
            } else {
                if (is409()) {
                    return;
                }
                if (!this.complex.getValue().booleanValue()) {
                    do407(inner_3.getMatrixStack472(), outlineShape.getBoundingBox(), blockPos406);
                    return;
                }
                Iterator it = outlineShape.getBoundingBoxes().iterator();
                while (it.hasNext()) {
                    do407(inner_3.getMatrixStack472(), (Box) it.next(), blockPos406);
                }
            }
        }
    }

    public BlockPos getBlockPos406() {
        BlockHitResult blockHitResult1232;
        if (reach.isToggled() && reach.ghostHand.getValue().booleanValue() && (blockHitResult1232 = reach.getBlockHitResult1232()) != null && blockHitResult1232.getType() != HitResult.Type.MISS && blockHitResult1232.getBlockPos() != null) {
            return reach.getBlockHitResult1232().getBlockPos();
        }
        if (is408()) {
            BlockHitResult hitResult1229 = (Reach.getHitResult1229()) instanceof BlockHitResult ? (BlockHitResult) (Reach.getHitResult1229()) : null;
            if (hitResult1229 instanceof BlockHitResult) {
                return hitResult1229.getBlockPos();
            }
            return null;
        }
        BlockHitResult blockHitResult = (minecraftClient.crosshairTarget) instanceof BlockHitResult ? (BlockHitResult) (minecraftClient.crosshairTarget) : null;
        if (!(blockHitResult instanceof BlockHitResult)) {
            return null;
        }
        BlockHitResult blockHitResult2 = blockHitResult;
        if (blockHitResult2.getType() != HitResult.Type.MISS) {
            return blockHitResult2.getBlockPos();
        }
        return null;
    }

    public void do407(MatrixStack matrixStack, Box box, BlockPos blockPos) {
        PhaseESPSearchHelper4.do1590(matrixStack, box.offset(blockPos).expand(0.002), this.fillColor.getValue());
        PhaseESPSearchHelper4.do1593(matrixStack, box.offset(blockPos).expand(0.002), this.color.getValue(), this.lineWidth.getValue().floatValue());
    }

    public boolean is408() {
        return reach.isToggled() && reach.airPlace.getValue().booleanValue() && ((minecraftClient.player.getMainHandStack().getItem() instanceof BlockItem) || (minecraftClient.player.getOffHandStack().getItem() instanceof BlockItem));
    }

    public boolean is409() {
        return this.unreachable.getValue().booleanValue() && this.unreachable.is2349();
    }
}
