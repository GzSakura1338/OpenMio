package me.mioclient.module.render;

import java.awt.Color;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.HoleSnapData;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/HoleESP.class */
public class HoleESP extends Module {
    public Setting<HoleESPMode> fill;
    public Setting<Float> lineWidth;
    public Setting<Float> height;
    public Setting<Integer> radius;
    public Setting<Boolean> hideOwn;
    public Setting<Boolean> fade;
    public Setting<Float> fadeRadius;
    public Setting<Boolean> safe;
    public Setting<Color> safeFill;
    public Setting<Color> safeOutline;
    public Setting<Boolean> unsafe;
    public Setting<Color> unsafeFill;
    public Setting<Color> unsafeOutline;
    public Setting<Boolean> trapped;
    public Setting<Color> trappedFill;
    public Setting<Color> trappedOutline;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/HoleESP$HoleESPMode.class */
    public enum HoleESPMode implements EnumSettingHelper {
        NONE("None"),
        SOLID("Solid"),
        GRADIENT("Gradient");

        public final String name;

        HoleESPMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public HoleESP() {
        super("HoleESP", "Highlights the spots that are safe from end crystals.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return String.valueOf(BaritoneHelper_3.holeSnapSearchHelper4_5.getList2726().size());
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:41:0x03c7. Please report as an issue. */
    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        double lerp = MathHelper.lerp(inner_3.get473(), minecraftClient.player.prevY, minecraftClient.player.getY());
        Vec3d pos = minecraftClient.gameRenderer.getCamera().getPos();
        Box box = new Box(HoleSnapSearchHelper4.getBlockPos1333());
        for (HoleSnapData holeSnapData : BaritoneHelper_3.holeSnapSearchHelper4_5.getList2726()) {
            if (SearchHelper4_8.is2492(holeSnapData.getBox799()) && (this.trapped.getValue().booleanValue() || !holeSnapData.is2171())) {
                if (holeSnapData.getBlockPos12().isWithinDistance((Position) pos, this.radius.getValue().intValue())) {
                    if (!minecraftClient.player.getBoundingBox().intersects(holeSnapData.getBox799()) || !this.hideOwn.getValue().booleanValue() || this.fade.getValue().booleanValue()) {
                        Color value = this.safeFill.getValue();
                        Color value2 = this.safeOutline.getValue();
                        if (holeSnapData.is2171()) {
                            value = this.trappedFill.getValue();
                            value2 = this.trappedOutline.getValue();
                        } else if (holeSnapData.getHoleSnapDataMode2170() == HoleSnapData.HoleSnapDataMode.UNSAFE) {
                            value = this.unsafeFill.getValue();
                            value2 = this.unsafeOutline.getValue();
                        }
                        double distanceTo = pos.distanceTo(holeSnapData.getBlockPos12().toCenterPos());
                        boolean z = holeSnapData.getBox799().intersects(box) && this.hideOwn.getValue().booleanValue();
                        if (this.fade.getValue().booleanValue() && (distanceTo >= this.fadeRadius.getValue().floatValue() || z)) {
                            float intBitsToFloat = 1.0f - ((float) MathHelper.clamp((distanceTo - this.fadeRadius.getValue().floatValue()) / (this.radius.getValue().intValue() - this.fadeRadius.getValue().floatValue()), 0.0d, 1.0));
                            if (z) {
                                intBitsToFloat = (float) (lerp - Math.floor(lerp));
                            }
                            value = MixinMessageIndicatorHelper_2.getColor816(value, (int) (intBitsToFloat * value.getAlpha()));
                            value2 = MixinMessageIndicatorHelper_2.getColor816(value2, (int) (intBitsToFloat * value2.getAlpha()));
                        }
                        switch (this.fill.getValue().ordinal()) {
                            case 1:
                                PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), holeSnapData.getBox799().withMaxY(holeSnapData.getBlockPos12().getY() + this.height.getValue().floatValue()), value);
                                break;
                            case 2:
                                PhaseESPSearchHelper4.do1591(inner_3.getMatrixStack472(), holeSnapData.getBox799().withMaxY(holeSnapData.getBlockPos12().getY() + 1), value);
                                break;
                        }
                        PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), holeSnapData.getBox799().withMaxY(holeSnapData.getBlockPos12().getY() + this.height.getValue().floatValue()), value2, this.lineWidth.getValue().floatValue());
                    }
                }
            }
        }
    }
}
