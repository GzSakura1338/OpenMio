package me.mioclient.module.render;

import java.awt.Color;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchMode;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import me.mioclient.module.client.Colors;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL32C;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Tracers.class */
public class Tracers extends Module {
    public static Colors colors = (Colors) BaritoneHelper_3.baritoneHelper_4.getModule117(Colors.class);
    public Setting<TracersMode> hitbox;
    public Setting<Float> maxDistance;
    public Setting<Float> lineWidth;
    public Setting<Color> color;
    public Setting<Boolean> stem;
    public Setting<Boolean> distanceColor;
    public Setting<Boolean> enemyColor;
    public Setting<Boolean> ignoreFriends;
    public Setting<Boolean> ignoreNakeds;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/Tracers$TracersMode.class */
    private enum TracersMode implements EnumSettingHelper {
        HEAD("Head", 0.85f),
        BODY("Body", 0.5f),
        LEGS("Legs", 0.0f);

        public final String name;
        public final float val;

        TracersMode(String str, float f) {
            this.name = str;
            this.val = f;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public float get2792() {
            return this.val;
        }
    }

    public Tracers() {
        super("Tracers", "Draws lines from your crosshair to the players nearby.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        Camera camera = minecraftClient.gameRenderer.getCamera();
        Vec3d add = new Vec3d(0.0d, 0.0d, 100.0).rotateX(-((float) Math.toRadians(camera.getPitch()))).rotateY(-((float) Math.toRadians(camera.getYaw()))).add(minecraftClient.getEntityRenderDispatcher().camera.getPos());
        GL32C.glLineWidth(this.lineWidth.getValue().floatValue());
        for (PlayerEntity playerEntity : minecraftClient.world.getPlayers()) {
            if (minecraftClient.player != playerEntity) {
                if (minecraftClient.gameRenderer.getCamera().getPos().distanceTo(((AbstractClientPlayerEntity) playerEntity).getPos()) <= this.maxDistance.getValue().floatValue() && (!this.ignoreFriends.getValue().booleanValue() || !BaritoneHelper_3.searchHelper4_14.is520(playerEntity))) {
                    if (!this.ignoreNakeds.getValue().booleanValue() || HoleSnapSearchHelper4.is2013((LivingEntity) playerEntity)) {
                        Vec3d add2 = ((AbstractClientPlayerEntity) playerEntity).getLerpedPos(SearchHelper_2.get536()).add(0.0d, ((AbstractClientPlayerEntity) playerEntity).getHeight() * this.hitbox.getValue().get2792(), 0.0d);
                        Color value = this.color.getValue();
                        boolean booleanValue = this.distanceColor.getValue().booleanValue();
                        if (booleanValue) {
                            value = MixinMessageIndicatorHelper_2.getColor816(MixinMessageIndicatorHelper_2.getColor815(Color.red, Color.green, 1.0f - ((float) ((MathHelper.clamp(minecraftClient.player.getEyePos().distanceTo(add2), 8.0, 64.0) - 8.0) / 56.0))), this.color.getValue().getAlpha());
                        }
                        if (BaritoneHelper_3.searchHelper4_14.is520(playerEntity)) {
                            value = MixinMessageIndicatorHelper_2.getColor816(BaritoneHelper_3.searchHelper4_14.getColor528(), this.color.getValue().getAlpha());
                        } else if (BaritoneHelper_3.searchHelper4_14.is522(playerEntity) && this.enemyColor.getValue().booleanValue()) {
                            value = MixinMessageIndicatorHelper_2.getColor816(BaritoneHelper_3.searchHelper4_14.getColor529(), this.color.getValue().getAlpha());
                        }
                        SearchMode value2 = booleanValue ? colors.scheme.getValue() : SearchMode.NORMAL;
                        Color color = value;
                        value2.do1027(() -> {
                            SearchHelper_2.searchHelper_2.do561(inner_3.getMatrixStack472(), add, add2, color);
                            if (this.stem.getValue().booleanValue()) {
                                SearchHelper_2.searchHelper_2.do561(inner_3.getMatrixStack472(), playerEntity.getLerpedPos(SearchHelper_2.get536()), playerEntity.getLerpedPos(SearchHelper_2.get536()).add(0.0d, playerEntity.getEyeHeight(playerEntity.getPose()), 0.0d), color);
                            }
                        });
                    }
                }
            }
        }
        GL32C.glLineWidth(1.0f);
    }
}
