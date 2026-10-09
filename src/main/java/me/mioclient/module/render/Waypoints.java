package me.mioclient.module.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.FreecamHelper;
import me.mioclient.HoleSnapMode;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinLivingEntityHelper_2;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.PingSpoofHelper;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchHelper_4;
import me.mioclient.WaypointsEnumSettingHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import me.mioclient.module.client.Fonts;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL32C;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Waypoints.class */
public class Waypoints extends Module {
    public static Fonts fonts = (Fonts) BaritoneHelper_3.baritoneHelper_4.getModule117(Fonts.class);
    public Setting<Float> distance;
    public Setting<Float> lineWidth;
    public Setting<Boolean> box;
    public Setting<Boolean> beam;
    public Setting<Boolean> tracers;
    public Setting<Boolean> name;
    public Setting<WaypointsMode> info;
    public Setting<Float> textScale;
    public Setting<Boolean> textBackground;
    public Setting<Boolean> eyesAlign;
    public Setting<Color> color;
    public Setting<Integer> beamAlpha;
    public final CopyOnWriteArrayList<WaypointsEnumSettingHelper> copyOnWriteArrayList;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/Waypoints$WaypointsMode.class */
    private static enum WaypointsMode implements EnumSettingHelper {
        NONE("None"),
        waypointsMode("Coords") {
            @Override
            public String getString2061(Vec3d vec3d) {
                return String.format(Locale.US, "X: %.1f, Y: %.1f, Z: %.1f", Double.valueOf(vec3d.getX()), Double.valueOf(vec3d.getY()), Double.valueOf(vec3d.getZ()));
            }
        },
        waypointsMode2("Distance") {
            @Override
            public String getString2061(Vec3d vec3d) {
                return String.format("%.1fm", Double.valueOf(vec3d.distanceTo(SearchHelper_4.minecraftClient.player.getPos()))).replaceAll(",", ".");
            }
        };

        public final String name;

        WaypointsMode(String str2) {
            this.name = str2;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public String getString2061(Vec3d vec3d) {
            return "";
        }
    }

    public Waypoints() {
        super("Waypoints", "Renders certain places you mark using ;waypoints.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        this.distance.getSetting2338("Unlimited", HoleSnapMode.MAX);
        setDrawn(false);
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (minecraftClient.player.age % 2 == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (WaypointsEnumSettingHelper waypointsEnumSettingHelper : BaritoneHelper_3.waypointsSearchHelper4.getRegistry()) {
            if (waypointsEnumSettingHelper != null && (waypointsEnumSettingHelper.getString518() == null || waypointsEnumSettingHelper.getString517() == null || waypointsEnumSettingHelper.getName() == null)) {
                arrayList.add(waypointsEnumSettingHelper);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            BaritoneHelper_3.waypointsSearchHelper4.unregister((WaypointsEnumSettingHelper) it.next());
        }
        this.copyOnWriteArrayList.clear();
        for (WaypointsEnumSettingHelper waypointsEnumSettingHelper2 : BaritoneHelper_3.waypointsSearchHelper4.getRegistry()) {
            if (waypointsEnumSettingHelper2 != null && waypointsEnumSettingHelper2.getString517() != null && waypointsEnumSettingHelper2.getName() != null && waypointsEnumSettingHelper2.getString518() != null && waypointsEnumSettingHelper2.isToggled()) {
                if (minecraftClient.getCurrentServerEntry() != null || !waypointsEnumSettingHelper2.getString518().equalsIgnoreCase("singleplayer")) {
                    if (minecraftClient.getCurrentServerEntry() != null) {
                        if (waypointsEnumSettingHelper2.getString518().equalsIgnoreCase(minecraftClient.getCurrentServerEntry().address)) {
                        }
                    }
                }
                this.copyOnWriteArrayList.add(waypointsEnumSettingHelper2);
            }
        }
        this.copyOnWriteArrayList.sort(Comparator.comparing(waypointsEnumSettingHelper3 -> {
            return Double.valueOf(minecraftClient.player.squaredDistanceTo(waypointsEnumSettingHelper3.getVec3d1303()));
        }));
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (minecraftClient.options.hudHidden) {
            return;
        }
        Camera camera = minecraftClient.gameRenderer.getCamera();
        Vec3d add = new Vec3d(0.0d, 0.0d, 1.0).rotateX(-((float) Math.toRadians(camera.getPitch()))).rotateY(-((float) Math.toRadians(camera.getYaw()))).add(minecraftClient.getEntityRenderDispatcher().camera.getPos());
        Iterator<WaypointsEnumSettingHelper> it = this.copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WaypointsEnumSettingHelper next = it.next();
            if (next != null && next.getString517() != null && next.getName() != null && next.getString518() != null) {
                if (next.getVec3d1303().distanceTo(minecraftClient.gameRenderer.getCamera().getPos()) <= this.distance.getValue().floatValue() * 1000.0f || this.distance.getValue().floatValue() == 101.0f) {
                    Vec3d vec3d2100 = getVec3d2100(next);
                    if (vec3d2100 != null) {
                        Vec3d vec3d = vec3d2100;
                        if (MixinLivingEntityHelper_2.get2583(minecraftClient.player.getPos(), vec3d2100) >= 120.0f) {
                            float[] floatArray2484 = SearchHelper4_8.getFloatArray2484(vec3d2100);
                            Vec3d lerpedPos = minecraftClient.player.getLerpedPos(SearchHelper_2.get536());
                            vec3d = new Vec3d(lerpedPos.getX() + (100.0f * ((float) Math.cos(Math.toRadians(floatArray2484[0] + FreecamHelper.num2)))), vec3d2100.y, lerpedPos.getZ() + (100.0f * ((float) Math.sin(Math.toRadians(floatArray2484[0] + FreecamHelper.num2)))));
                        }
                        if (this.beam.getValue().booleanValue()) {
                            PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), new Box(vec3d.add(-FreecamHelper.val3, -FreecamHelper.val3, -FreecamHelper.val3), vec3d.add(FreecamHelper.val3, FreecamHelper.val3, FreecamHelper.val3)).withMaxY(minecraftClient.world.getTopY()).withMinY(minecraftClient.world.getBottomY()), MixinMessageIndicatorHelper_2.getColor816(this.color.getValue(), this.beamAlpha.getValue().intValue()));
                        }
                        if (this.box.getValue().booleanValue()) {
                            PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), new Box(vec3d2100.add(-FreecamHelper.val3, -FreecamHelper.val3, -FreecamHelper.val3), vec3d2100.add(FreecamHelper.val3, FreecamHelper.val3, FreecamHelper.val3)), this.color.getValue(), this.lineWidth.getValue().floatValue());
                        }
                        if (this.tracers.getValue().booleanValue()) {
                            GL32C.glLineWidth(this.lineWidth.getValue().floatValue());
                            SearchHelper_2.searchHelper_2.do561(inner_3.getMatrixStack472(), add, vec3d, this.color.getValue());
                            GL32C.glLineWidth(1.0f);
                        }
                        if (this.name.getValue().booleanValue()) {
                            if (this.eyesAlign.getValue().booleanValue()) {
                                vec3d = vec3d.withAxis(Direction.Axis.Y, MathHelper.lerp(inner_3.get473(), minecraftClient.player.prevY, minecraftClient.player.getY()) + minecraftClient.player.getEyeHeight(minecraftClient.player.getPose()));
                            }
                            String string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2919(this.info.getValue().getString2061(vec3d2100)).getArgumentTypeHelper2919(next.getName()).getString2921("\u0001 \u0001");
                            double d = PingSpoofHelper.get377(minecraftClient.gameRenderer.getCamera().getPos(), vec3d, this.textScale.getValue().floatValue());
                            float f = (float) FreecamHelper.val3;
                            if (this.textBackground.getValue().booleanValue()) {
                                SearchHelper_2.searchHelper_2.do567(inner_3.getMatrixStack472(), vec3d, -0.029999999329447746f, (float) FreecamHelper.val3, (-(FontsSearchHelper4.fontsSearchHelper4.get1316(string2921) / 2.0f)) - 2.0f, 5.0f, d * 2.0, new Color(0, 0, 0, 100));
                                if (!fonts.isToggled()) {
                                    f = 0.23000000417232513f;
                                }
                            }
                            SearchHelper_2.searchHelper_2.do571(inner_3.getDrawContext474(), string2921, vec3d, 0.0f, f, -(FontsSearchHelper4.fontsSearchHelper4.get1316(string2921) / 2.0f), 0.0f, d, Color.WHITE, true);
                        }
                    }
                }
            }
        }
    }

    public static Vec3d getVec3d2100(WaypointsEnumSettingHelper waypointsEnumSettingHelper) {
        Vec3d vec3d1303 = waypointsEnumSettingHelper.getVec3d1303();
        String lowerCase = SearchHelper4_7.getStashFinderMode2438().getString2175().toLowerCase();
        if (waypointsEnumSettingHelper.getString517().equals("overworld") && lowerCase.equals("nether")) {
            return vec3d1303.multiply(0.125, 1.0, 0.125);
        }
        if (waypointsEnumSettingHelper.getString517().equals("nether") && lowerCase.equals("overworld")) {
            return vec3d1303.multiply(8.0, 1.0, 8.0);
        }
        if (waypointsEnumSettingHelper.getString517().equals(lowerCase)) {
            return vec3d1303;
        }
        return null;
    }
}
