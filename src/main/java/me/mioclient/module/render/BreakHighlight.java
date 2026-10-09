package me.mioclient.module.render;

import java.awt.Color;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EntityIdHelper;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.PingSpoofHelper;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/BreakHighlight.class */
public class BreakHighlight extends Module {
    public Setting<Double> range;
    public Setting<Boolean> friends;
    public Setting<Boolean> enemyColor;
    public Setting<Color> fill;
    public Setting<Color> outline;

    public BreakHighlight() {
        super("BreakHighlight", "Highlights the blocks that are being broken.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        for (EntityIdHelper entityIdHelper : BaritoneHelper_3.breakHighlightSearchHelper4.getList1517()) {
            if (PingSpoofHelper.get384(entityIdHelper.getBlockPos386()) <= this.range.getValue().doubleValue() && SearchHelper4_8.is2492(new Box(entityIdHelper.getBlockPos386())) && SearchHelper4_7.is2435(entityIdHelper.getBlockPos386())) {
                Color value = this.fill.getValue();
                Color value2 = this.outline.getValue();
                VoxelShape outlineShape = minecraftClient.world.getBlockState(entityIdHelper.getBlockPos386()).getOutlineShape(minecraftClient.world, entityIdHelper.getBlockPos386());
                float f = entityIdHelper.get2814(inner_3.get473());
                PlayerEntity entity181 = (entityIdHelper.getEntity181()) instanceof PlayerEntity ? (PlayerEntity) (entityIdHelper.getEntity181()) : null;
                Box offset = getBox1531(outlineShape.isEmpty() ? new Box(0.0d, 0.0d, 0.0d, 1.0, 1.0, 1.0) : outlineShape.getBoundingBox(), f).offset(entityIdHelper.getBlockPos386());
                if (entity181 instanceof PlayerEntity) {
                    PlayerEntity playerEntity = entity181;
                    Color color530 = BaritoneHelper_3.searchHelper4_14.getColor530(playerEntity.getGameProfile().getName(), (Color) null);
                    if (this.friends.getValue().booleanValue() || !BaritoneHelper_3.searchHelper4_14.is520(playerEntity)) {
                        if (color530 != null) {
                            if (!BaritoneHelper_3.searchHelper4_14.is521(playerEntity.getGameProfile().getName()) || this.enemyColor.getValue().booleanValue()) {
                                value = MixinMessageIndicatorHelper_2.getColor816(color530, this.fill.getValue().getAlpha());
                                value2 = MixinMessageIndicatorHelper_2.getColor816(color530, this.outline.getValue().getAlpha());
                            }
                        }
                    }
                }
                PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), getBox1531(offset, f), value);
                PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), getBox1531(offset, f), value2, 1.0f);
            }
        }
    }

    public Box getBox1531(Box box, float f) {
        float clamp = MathHelper.clamp(f, 0.0f, 1.0f);
        double abs = Math.abs(box.getLengthX());
        double abs2 = Math.abs(box.getLengthY());
        double abs3 = Math.abs(box.getLengthZ());
        Vec3d center = box.getCenter();
        return new Box(center.subtract((clamp * abs) / 2.0, (clamp * abs2) / 2.0, (clamp * abs3) / 2.0), center.add((clamp * abs) / 2.0, (clamp * abs2) / 2.0, (clamp * abs3) / 2.0));
    }
}
