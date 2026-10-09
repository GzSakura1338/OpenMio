package me.mioclient.module.client;

import java.awt.Color;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.FreecamHelper;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinLivingEntityHelper_2;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.PingSpoofHelper;
import me.mioclient.SearchHelper4_11;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchHelper_4;
import me.mioclient.SearchIdentifier;
import me.mioclient.SpawnTimeHelper;
import me.mioclient.SpawnTimeHelperEvent;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Event;
import me.mioclient.module.Module;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/client/IRC.class */
public class IRC extends Module {
    public static final Identifier identifier = Identifier.of("mio", "textures/warning.png");
    public Setting<Boolean> capes;
    public Setting<Boolean> gradient;
    public Setting<Boolean> chat;
    public Setting<String> prefix;
    public Setting<Boolean> chatSound;
    public Setting<SearchIdentifier> mode;
    public Setting<Float> volume2;
    public Setting<Boolean> pings;
    public Setting<IRCMode> info;
    public Setting<Boolean> pingSound;
    public Setting<SearchIdentifier> mode2;
    public Setting<Float> volume;
    public int num;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/client/IRC$IRCMode.class */
    public enum IRCMode implements EnumSettingHelper {
        DISTANCE("Distance"),
        COORDS("Coords"),
        NONE("None");

        public final String name;

        IRCMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public String getString2864(SpawnTimeHelper spawnTimeHelper) {
            if (this == DISTANCE) {
                return "%.1fm".formatted(Double.valueOf(SearchHelper_4.minecraftClient.player.getEyePos().distanceTo(spawnTimeHelper.getBlockPos386().toCenterPos())));
            }
            Vec3d centerPos = spawnTimeHelper.getBlockPos386().toCenterPos();
            return "X:%.1f, Y:%.1f, Z:%.1f".formatted(Double.valueOf(centerPos.x), Double.valueOf(centerPos.y), Double.valueOf(centerPos.z));
        }

        public boolean is594() {
            return this != NONE;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/client/IRC$Mode.class */
    public enum Mode implements EnumSettingHelper {
        NONE("none", null),
        MIO("mio", Identifier.of("mio", "capes/mio.png")),
        PEPSI("pepsi", Identifier.of("mio", "capes/pepsi.png")),
        NIGHTLY("nightly", Identifier.of("mio", "capes/nightly.png")),
        TETRIS("tetris", Identifier.of("mio", "capes/tetris.png"));

        public final String name;
        public final Identifier identifier;

        Mode(String str, Identifier identifier) {
            this.name = str;
            this.identifier = identifier;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public Identifier getIdentifier2642() {
            return this.identifier;
        }
    }

    public IRC() {
        super("IRC", "Client user chat.", Category.CLIENT, new String[0]);
        PhaseESPHelper.do1351(this);
        setDrawn(false);
        this.capes.do2339(() -> {
            BaritoneHelper_3.nameTagsSearchHelper4.do2304(getString2847());
        });
        this.mode.do2329("C-SoundMode");
        this.volume2.do2329("C-SoundVolume");
        this.mode2.do2329("P-SoundMode");
        this.volume.do2329("P-SoundVolume");
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        BaritoneHelper_3.nameTagsSearchHelper4.do2297();
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        BaritoneHelper_3.nameTagsSearchHelper4.do639();
    }

    @Listen
    public void onEvent2(SpawnTimeHelperEvent spawnTimeHelperEvent) {
        if (((float) minecraftClient.player.getEyePos().distanceTo(spawnTimeHelperEvent.getSpawnTimeHelper1787().getBlockPos386().toCenterPos())) >= 9999.0f) {
            spawnTimeHelperEvent.do1162();
            return;
        }
        if (this.pingSound.getValue().booleanValue() && spawnTimeHelperEvent.getSpawnTimeHelper1787().is796()) {
            SearchHelper4_11 searchHelper4_11 = BaritoneHelper_3.searchHelper4_11;
            SearchIdentifier value = this.mode2.getValue();
            searchHelper4_11.do2972(value, spawnTimeHelperEvent.getSpawnTimeHelper1787().getBlockPos386().toCenterPos(), this.volume.getValue().floatValue());
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        BaritoneHelper_3.nameTagsSearchHelper4.getList2307().removeIf(spawnTimeHelper -> {
            return System.currentTimeMillis() - spawnTimeHelper.getSpawnTime() > 7500;
        });
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (this.pings.getValue().booleanValue()) {
            synchronized (BaritoneHelper_3.nameTagsSearchHelper4.getList2307()) {
                for (SpawnTimeHelper spawnTimeHelper : BaritoneHelper_3.nameTagsSearchHelper4.getList2307()) {
                    if (spawnTimeHelper.is796()) {
                        float distanceTo = (float) minecraftClient.player.getEyePos().distanceTo(spawnTimeHelper.getBlockPos386().toCenterPos());
                        if (distanceTo < 9999.0f) {
                            Vec3d of = Vec3d.of(spawnTimeHelper.getBlockPos386());
                            Vec3d vec3d = of;
                            if (distanceTo >= FreecamHelper.num2) {
                                float[] floatArray2484 = SearchHelper4_8.getFloatArray2484(of);
                                vec3d = new Vec3d(minecraftClient.player.getX() + (70.0f * ((float) Math.cos(Math.toRadians(floatArray2484[0] + FreecamHelper.num2)))), of.y, minecraftClient.player.getZ() + (70.0f * ((float) Math.sin(Math.toRadians(floatArray2484[0] + FreecamHelper.num2)))));
                            }
                            float clamp = MathHelper.clamp(1.0f - (((float) ((System.currentTimeMillis() - spawnTimeHelper.getSpawnTime()) - 6500)) / 1000.0f), 0.0f, 1.0f);
                            float f = get2845(MathHelper.clamp(((float) (System.currentTimeMillis() - spawnTimeHelper.getSpawnTime())) / 1500.0f, 0.0f, 1.0f));
                            float f2 = get2846(clamp);
                            float min = Math.min(MixinLivingEntityHelper_2.get2583(minecraftClient.player.getLerpedPos(inner_3.get473()), of), 6.0f) / 6.0f;
                            Vec3d add = vec3d.add(0.5, 0.5, 0.5);
                            double d = PingSpoofHelper.get377(minecraftClient.gameRenderer.getCamera().getPos(), add, 3.0);
                            PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), new Box(add.add(-FreecamHelper.val3, -FreecamHelper.val3, -FreecamHelper.val3), add.add(FreecamHelper.val3, FreecamHelper.val3, FreecamHelper.val3)).withMaxY(MathHelper.lerp(f, add.y, minecraftClient.world.getTopY())).withMinY(MathHelper.lerp(f, add.y, minecraftClient.world.getBottomY())), MixinMessageIndicatorHelper_2.getColor817(MixinMessageIndicatorHelper_2.getColor814(Color.red, Color.yellow, 1000.0, 0.0d), 0.30000001192092896f * f * f2 * min));
                            SearchHelper_2.searchHelper_2.do568(inner_3.getDrawContext474(), add, 256.0f, 256.0f, (d / 25.6) * f * f2, MixinMessageIndicatorHelper_2.getColor814(Color.red, Color.yellow, 1000.0, 0.0d), identifier);
                            if (this.info.getValue().is594()) {
                                double d2 = PingSpoofHelper.get377(minecraftClient.gameRenderer.getCamera().getPos(), add, 1.5) * f * f2;
                                String string2864 = this.info.getValue().getString2864(spawnTimeHelper);
                                float intBitsToFloat = -0.20000000298023224f * ((float) d);
                                SearchHelper_2.searchHelper_2.do567(inner_3.getMatrixStack472(), add, 0.0f, intBitsToFloat, (-(FontsSearchHelper4.fontsSearchHelper4.get1316(string2864) / 2.0f)) - 2.0f, 5.0f, d2 * 2.0, new Color(0, 0, 0, 100));
                                SearchHelper_2.searchHelper_2.do571(inner_3.getDrawContext474(), string2864, add, 0.0f, intBitsToFloat, -(FontsSearchHelper4.fontsSearchHelper4.get1316(string2864) / 2.0f), 0.0f, d2, Color.WHITE, true);
                            }
                        }
                    }
                }
            }
        }
    }

    public float get2845(float f) {
        double longBitsToDouble = (2.0 * FreecamHelper.val) / 3.0;
        if (f == 0.0f) {
            return 0.0f;
        }
        return (float) (f == 1.0f ? 1.0 : (Math.pow(2.0, -10.0f * f) * Math.sin(((f * 10.0f) - 0.75) * longBitsToDouble)) + 1.0);
    }

    public float get2846(float f) {
        return f * f * f;
    }

    @Listen
    public void onDabigbulletz(Event event) {
        if (this.chat.getValue().booleanValue() && event.getString2649().length() > this.prefix.getValue().length()) {
            if (event.getString2649().startsWith(this.prefix.getValue())) {
                event.do1162();
                BaritoneHelper_3.nameTagsSearchHelper4.do1201(event.getString2649().substring(this.prefix.getValue().length()).trim());
            }
        }
    }

    public String getString2847() {
        if (!this.capes.getValue().booleanValue()) {
            return "none";
        }
        switch (BaritoneHelper_3.welcomerHelper.get2811()) {
            case 3:
                return "pepsi";
            case 5:
                return "tetris";
            default:
                return "mio";
        }
    }
}
