package me.mioclient.module.render;

import java.awt.Color;
import me.mioclient.CrosshairHelper;
import me.mioclient.MatrixStackEvent_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.feature.Progress;
import me.mioclient.module.Module;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Crosshair.class */
public class Crosshair extends Module {
    public Setting<Boolean> thirdPerson;
    public Setting<Boolean> dot;
    public Setting<Boolean> shadow;
    public Setting<Integer> distance;
    public Setting<Boolean> dynamic;
    public Setting<Boolean> smooth;
    public Setting<Float> amplitude;
    public Setting<Integer> gap;
    public Setting<Integer> length;
    public Setting<Integer> width;
    public Setting<Color> color;
    public final Progress progress;

    public Crosshair() {
        super("Crosshair", "Allows you to customize your crosshair.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.progress = new Progress(2.0f, true);
        setDrawn(false);
    }

    @Listen
    public void onEvent(MatrixStackEvent_2 matrixStackEvent_2) {
        if (is1469()) {
            return;
        }
        if (!minecraftClient.gameRenderer.getCamera().isThirdPerson() || this.thirdPerson.getValue().booleanValue()) {
            float intValue = this.gap.getValue().intValue();
            if (this.dynamic.getValue().booleanValue()) {
                if (this.smooth.getValue().booleanValue()) {
                    this.progress.do2139((minecraftClient.player.input.movementForward == 0.0f && minecraftClient.player.input.movementSideways == 0.0f && !minecraftClient.player.input.jumping) ? false : true);
                } else {
                    this.progress.do2140((minecraftClient.player.input.movementForward == 0.0f && minecraftClient.player.input.movementSideways == 0.0f && !minecraftClient.player.input.jumping) ? false : true);
                }
                intValue += this.amplitude.getValue().floatValue() * this.progress.get172();
            }
            float scaleFactor = (float) minecraftClient.getWindow().getScaleFactor();
            matrixStackEvent_2.getMatrixStack472().push();
            matrixStackEvent_2.getMatrixStack472().scale(1.0f / scaleFactor, 1.0f / scaleFactor, 1.0f / scaleFactor);
            if (this.shadow.getValue().booleanValue()) {
                MatrixStack matrixStack472 = matrixStackEvent_2.getMatrixStack472();
                matrixStack472.push();
                matrixStack472.translate(this.distance.getValue().intValue(), this.distance.getValue().intValue(), 0.0f);
                do250(matrixStackEvent_2.getMatrixStack472(), this.length.getValue().intValue(), this.width.getValue().intValue() / 2.0f, intValue, Color.BLACK);
                matrixStack472.pop();
            }
            do250(matrixStackEvent_2.getMatrixStack472(), this.length.getValue().intValue(), this.width.getValue().intValue() / 2.0f, intValue, this.color.getValue());
            matrixStackEvent_2.getMatrixStack472().pop();
        }
    }

    public void do250(MatrixStack matrixStack, float f, float f2, float f3, Color color) {
        int width = minecraftClient.getWindow().getWidth() / 2;
        int height = minecraftClient.getWindow().getHeight() / 2;
        if (Math.ceil(f2) - f2 != 0.0d) {
            f3 += 0.5f;
        } else {
            width--;
            height++;
        }
        if (this.dot.getValue().booleanValue()) {
            CrosshairHelper.do1707(matrixStack, width - f2, height - f2, width + f2, height + f2, color);
        }
        if (f == 0.0f) {
            return;
        }
        CrosshairHelper.do1707(matrixStack, width - f2, (height - f3) - f, width + f2, height - f3, color);
        CrosshairHelper.do1707(matrixStack, width + f3, height - f2, width + f3 + f, height + f2, color);
        CrosshairHelper.do1707(matrixStack, width - f2, height + f3, width + f2, height + f3 + f, color);
        CrosshairHelper.do1707(matrixStack, (width - f3) - f, height - f2, width - f3, height + f2, color);
    }
}
