package me.mioclient.module.render;

import java.awt.Color;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.FreecamHelper;
import me.mioclient.MatrixStackEvent_2;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_2;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Markers.class */
public class Markers extends Module {
    public Setting<Float> setting;
    public Setting<Boolean> setting2;

    public Markers() {
        super("Markers", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    @Listen
    public void onEvent(MatrixStackEvent_2 matrixStackEvent_2) {
        float lerp = MathHelper.lerp(SearchHelper_2.get536(), minecraftClient.player.prevYaw, minecraftClient.player.getYaw());
        for (PlayerEntity playerEntity : minecraftClient.world.getPlayers()) {
            double distanceTo = minecraftClient.player.distanceTo((Entity) playerEntity);
            if (minecraftClient.player != playerEntity && distanceTo <= 32.0) {
                float longBitsToDouble = (float) (1.0 - MathHelper.clamp((distanceTo - 28.0) / 4.0, 0.0d, 1.0));
                if (longBitsToDouble >= 0.1) {
                    float f = SearchHelper4_8.getFloatArray2486(getVec3d2809(minecraftClient.player), getVec3d2809(playerEntity))[0];
                    if (!this.setting2.getValue().booleanValue() || MathHelper.angleBetween(f, lerp) > ((Integer) minecraftClient.options.getFov().getValue()).intValue() / 2.0f) {
                        double radians = Math.toRadians((f - lerp) - FreecamHelper.num2);
                        float f2 = FontsSearchHelper4.fontsSearchHelper4.get1316(">") * 0.5f;
                        float clamp = MathHelper.clamp(minecraftClient.player.getPitch() + 30.0f, -FreecamHelper.num2, FreecamHelper.num2) / FreecamHelper.num2;
                        float cos = (float) ((Math.cos(radians) * this.setting.getValue().floatValue()) + (matrixStackEvent_2.getDrawContext474().getScaledWindowWidth() * FreecamHelper.val2));
                        float sin = (float) ((Math.sin(radians) * this.setting.getValue().floatValue() * clamp) + (matrixStackEvent_2.getDrawContext474().getScaledWindowHeight() * FreecamHelper.val2));
                        matrixStackEvent_2.getMatrixStack472().push();
                        matrixStackEvent_2.getMatrixStack472().translate(cos, sin, 0.0f);
                        matrixStackEvent_2.getMatrixStack472().multiply(RotationAxis.POSITIVE_Z.rotation((float) radians));
                        FontsSearchHelper4.fontsSearchHelper4.do1689(matrixStackEvent_2.getDrawContext474(), ">", -f2, (float) ((-FontsSearchHelper4.fontsSearchHelper4.get93()) * FreecamHelper.val2), MixinMessageIndicatorHelper_2.getColor816(getColor2808(playerEntity), (int) (longBitsToDouble * 255.0f)));
                        matrixStackEvent_2.getMatrixStack472().pop();
                    }
                }
            }
        }
    }

    public Color getColor2808(PlayerEntity playerEntity) {
        if (BaritoneHelper_3.searchHelper4_14.is520(playerEntity)) {
            return BaritoneHelper_3.searchHelper4_14.getColor528();
        }
        if (BaritoneHelper_3.searchHelper4_14.is522(playerEntity)) {
            return BaritoneHelper_3.searchHelper4_14.getColor529();
        }
        return MixinMessageIndicatorHelper_2.getColor815(Color.red, Color.green, 1.0f - ((float) ((MathHelper.clamp(minecraftClient.player.getEyePos().distanceTo(playerEntity.getPos()), 8.0, 32.0) - 8.0) / 24.0)));
    }

    public Vec3d getVec3d2809(PlayerEntity playerEntity) {
        return new Vec3d(MathHelper.lerp(SearchHelper_2.get536(), playerEntity.prevX, playerEntity.getX()), MathHelper.lerp(SearchHelper_2.get536(), playerEntity.prevY, playerEntity.getY()), MathHelper.lerp(SearchHelper_2.get536(), playerEntity.prevZ, playerEntity.getZ()));
    }
}
