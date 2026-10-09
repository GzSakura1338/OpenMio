package me.mioclient.module.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.ChamsHelper_2;
import me.mioclient.EnumSettingHelper;
import me.mioclient.Feature_14;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.HoleSnapMode;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.MatrixStackEvent;
import me.mioclient.OtherClientPlayerEntity;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.PingSpoofHelper;
import me.mioclient.SearchHelper4_11;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchHelper_3;
import me.mioclient.SearchHelper_4;
import me.mioclient.SearchIdentifier;
import me.mioclient.StashFinderMode;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.ConnectEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.RemoveEntityEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import me.mioclient.module.client.Fonts;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/LogoutSpots.class */
public class LogoutSpots extends Module {
    public static final Fonts fonts = (Fonts) BaritoneHelper_3.baritoneHelper_4.getModule117(Fonts.class);
    public static boolean flag = false;
    public Setting<Float> distance;
    public Setting<LogoutSpotsMode_2> model;
    public Setting<Boolean> textured;
    public Setting<Float> alpha;
    public Setting<Float> width;
    public Setting<Boolean> tracer;
    public Setting<Float> width2;
    public Setting<Boolean> nameTag;
    public Setting<Float> textScale;
    public Setting<LogoutSpotsMode> position;
    public Setting<Boolean> time;
    public Setting<Boolean> health;
    public Setting<Boolean> totems;
    public Setting<Boolean> sounds;
    public Setting<Boolean> login;
    public Setting<SearchIdentifier> sound2;
    public Setting<Boolean> logout;
    public Setting<SearchIdentifier> sound;
    public Setting<Boolean> colors;
    public Setting<Color> modelFill;
    public Setting<Color> modelLine;
    public Setting<Color> boxFill;
    public Setting<Color> boxLine;
    public Setting<Color> nameTagColor;
    public Setting<Color> fillColor;
    public Setting<Boolean> filter;
    public Setting<Boolean> ignoreFriends;
    public Setting<Boolean> ignoreNakeds;
    public final Object2ObjectOpenHashMap<String, Record> object2ObjectOpenHashMap;
    public final List<Record_2> list;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/LogoutSpots$LogoutSpotsMode.class */
    public enum LogoutSpotsMode implements EnumSettingHelper {
        NONE("None"),
        COORDINATES("Coordinates"),
        DISTANCE("Distance");

        public final String name;

        LogoutSpotsMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/LogoutSpots$LogoutSpotsMode_2.class */
    public enum LogoutSpotsMode_2 implements EnumSettingHelper {
        NONE("None"),
        SIMPLE("Simple"),
        COMPLEX("Complex"),
        BOTH("Both");

        public final String name;

        LogoutSpotsMode_2(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/LogoutSpots$Record.class */
    public static final class Record {
        public final long num;
        public final Box box;
        public final StashFinderMode stashFinderMode;
        public final int num2;
        public final int num3;
        public final Vec3d vec3d;
        public final OtherClientPlayerEntity otherClientPlayerEntity;
        public final String string;

        public Record(long j, Box box, StashFinderMode stashFinderMode, int i, int i2, Vec3d vec3d, OtherClientPlayerEntity otherClientPlayerEntity, String str) {
            this.num = j;
            this.box = box;
            this.stashFinderMode = stashFinderMode;
            this.num2 = i;
            this.num3 = i2;
            this.vec3d = vec3d;
            this.otherClientPlayerEntity = otherClientPlayerEntity;
            this.string = str;
        }

        public static Record getRecord797(PlayerEntity playerEntity, String str) {
            OtherClientPlayerEntity otherClientPlayerEntity = new OtherClientPlayerEntity(SearchHelper_4.minecraftClient.world);
            otherClientPlayerEntity.do146(playerEntity);
            otherClientPlayerEntity.deathTime = 0;
            otherClientPlayerEntity.hurtTime = 0;
            return new Record(System.currentTimeMillis(), playerEntity.getBoundingBox(), SearchHelper4_7.getStashFinderMode2438(), Math.round(SearchHelper_3.get644((Entity) playerEntity)), BaritoneHelper_3.logoutSpotsHelper.get895(playerEntity), playerEntity.getPos(), otherClientPlayerEntity, str);
        }




        public long get798() {
            return this.num;
        }

        public Box getBox799() {
            return this.box;
        }

        public StashFinderMode getStashFinderMode800() {
            return this.stashFinderMode;
        }

        public int get801() {
            return this.num2;
        }

        public int get802() {
            return this.num3;
        }

        public Vec3d getVec3d803() {
            return this.vec3d;
        }

        public OtherClientPlayerEntity getOtherClientPlayerEntity804() {
            return this.otherClientPlayerEntity;
        }

        public String getString805() {
            return this.string;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/LogoutSpots$Record_2.class */
    private static final class Record_2 {
        public final PlayerEntity playerEntity;
        public final long num;
        public final AtomicBoolean atomicBoolean;

        public Record_2(PlayerEntity playerEntity, long j, AtomicBoolean atomicBoolean) {
            this.playerEntity = playerEntity;
            this.num = j;
            this.atomicBoolean = atomicBoolean;
        }

        public boolean is1235() {
            return System.currentTimeMillis() - this.num >= 300 || this.atomicBoolean.get();
        }




        public PlayerEntity getPlayerEntity1236() {
            return this.playerEntity;
        }

        public long get1237() {
            return this.num;
        }

        public AtomicBoolean getAtomicBoolean1238() {
            return this.atomicBoolean;
        }
    }

    public LogoutSpots() {
        super("LogoutSpots", "Highlights log out spots.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.object2ObjectOpenHashMap = new Object2ObjectOpenHashMap<>();
        this.list = new ArrayList();
        this.distance.getSetting2338("Unlimited", HoleSnapMode.MAX);
        this.sound2.do2329("LoginSoundPath");
        this.sound.do2329("LogoutSoundPath");
        setDrawn(false);
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        this.object2ObjectOpenHashMap.clear();
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        Camera camera = minecraftClient.gameRenderer.getCamera();
        Vec3d add = new Vec3d(0.0d, 0.0d, 1.0).rotateX(-((float) Math.toRadians(camera.getPitch()))).rotateY(-((float) Math.toRadians(camera.getYaw()))).add(minecraftClient.getEntityRenderDispatcher().camera.getPos());
        synchronized (this.object2ObjectOpenHashMap) {
            Iterator it = this.object2ObjectOpenHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (((Record) entry.getValue()).getString805().equals(getString518()) && ((Record) entry.getValue()).getStashFinderMode800() == SearchHelper4_7.getStashFinderMode2438()) {
                    if (((Record) entry.getValue()).box.getCenter().distanceTo(minecraftClient.gameRenderer.getCamera().getPos()) <= this.distance.getValue().floatValue() || this.distance.getValue().floatValue() == 301.0f) {
                        if (this.model.getValue() == LogoutSpotsMode_2.SIMPLE || this.model.getValue() == LogoutSpotsMode_2.BOTH) {
                            PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), ((Record) entry.getValue()).getBox799(), this.boxFill.getValue());
                            PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), ((Record) entry.getValue()).getBox799(), this.boxLine.getValue(), this.width.getValue().floatValue());
                        }
                        if (this.model.getValue() == LogoutSpotsMode_2.COMPLEX || this.model.getValue() == LogoutSpotsMode_2.BOTH) {
                            Entity otherClientPlayerEntity804 = ((Record) entry.getValue()).getOtherClientPlayerEntity804();
                            if (this.textured.getValue().booleanValue()) {
                                flag = true;
                                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha.getValue().floatValue());
                                SearchHelper_2.searchHelper_2.do574(otherClientPlayerEntity804, 1.0f, inner_3.getMatrixStack472(), minecraftClient.getBufferBuilders().getEntityVertexConsumers());
                                minecraftClient.getBufferBuilders().getEntityVertexConsumers().draw();
                                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                                flag = false;
                            }
                            ChamsHelper_2.do620(this.width.getValue().floatValue());
                            ChamsHelper_2.do612(this.modelLine.getValue(), this.modelFill.getValue());
                            ChamsHelper_2.do615(inner_3.getMatrixStack472(), otherClientPlayerEntity804);
                        }
                        if (this.tracer.getValue().booleanValue()) {
                            SearchHelper_2.searchHelper_2.do560(inner_3.getMatrixStack472(), add, ((Record) entry.getValue()).getBox799().getCenter(), this.modelLine.getValue(), this.width2.getValue().floatValue());
                        }
                        if (this.nameTag.getValue().booleanValue()) {
                            Vec3d withAxis = ((Record) entry.getValue()).getBox799().getCenter().withAxis(Direction.Axis.Y, ((Record) entry.getValue()).getBox799().maxY + 0.5);
                            String string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2919((String) entry.getKey()).getString2921("\u0001 logout spot");
                            String string1289 = getString1289((Record) entry.getValue());
                            float intBitsToFloat = fonts.isToggled() ? 1.0f : 0.0f;
                            if (!string1289.isEmpty()) {
                                string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2919(string1289).getArgumentTypeHelper2919(string2921).getString2921("\u0001\u0001");
                            }
                            double d = PingSpoofHelper.get377(minecraftClient.gameRenderer.getCamera().getPos(), withAxis, this.textScale.getValue().floatValue());
                            SearchHelper_2.searchHelper_2.do567(inner_3.getMatrixStack472(), withAxis, -0.029999999329447746f, 0.0f, (-(FontsSearchHelper4.fontsSearchHelper4.get1316(string2921) / 2.0f)) - 2.0f, 5.0f + intBitsToFloat, d * 2.0, this.fillColor.getValue());
                            SearchHelper_2.searchHelper_2.do571(inner_3.getDrawContext474(), string2921, withAxis, 0.0f, 0.0f, -(FontsSearchHelper4.fontsSearchHelper4.get1316(string2921) / 2.0f), 2.0f, d, this.nameTagColor.getValue(), true);
                        }
                    }
                }
            }
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        for (Record_2 record_2 : this.list) {
            PlayerEntity playerEntity1236 = record_2.getPlayerEntity1236();
            if (is1290(playerEntity1236)) {
                record_2.atomicBoolean.set(true);
                this.object2ObjectOpenHashMap.put(playerEntity1236.getGameProfile().getName(), Record.getRecord797(playerEntity1236, getString518()));
                if (this.sounds.getValue().booleanValue() && this.logout.getValue().booleanValue()) {
                    SearchHelper4_11 searchHelper4_11 = BaritoneHelper_3.searchHelper4_11;
                    searchHelper4_11.do2971(this.sound.getValue(), 1.0f);
                }
            }
        }
        this.list.removeIf((v0) -> {
            return v0.is1235();
        });
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        PlayerListS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof PlayerListS2CPacket ? (PlayerListS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof PlayerListS2CPacket) {
            for (PlayerListS2CPacket.Entry entry : packet904.getPlayerAdditionEntries()) {
                minecraftClient.executeSync(() -> {
                    GameProfile profile = entry.profile();
                    String name = profile == null ? "" : profile.getName();
                    if (this.sounds.getValue().booleanValue() && this.login.getValue().booleanValue() && this.object2ObjectOpenHashMap.containsKey(name)) {
                        BaritoneHelper_3.searchHelper4_11.do2971(this.sound2.getValue(), 1.0f);
                    }
                    this.object2ObjectOpenHashMap.remove(name);
                });
            }
        }
    }

    @Listen
    public void onConnect(ConnectEvent connectEvent) {
        if (BaritoneHelper_3.holeSnapSearchHelper4_4.getServerInfo2622() == null || connectEvent.getString518().equals(BaritoneHelper_3.holeSnapSearchHelper4_4.getServerInfo2622().address)) {
            this.object2ObjectOpenHashMap.clear();
        }
    }

    @Listen
    public void onRemoveEntity(RemoveEntityEvent removeEntityEvent) {
        PlayerEntity entityById = (minecraftClient.world.getEntityById(removeEntityEvent.getId())) instanceof PlayerEntity ? (PlayerEntity) (minecraftClient.world.getEntityById(removeEntityEvent.getId())) : null;
        if (entityById instanceof PlayerEntity) {
            PlayerEntity playerEntity = entityById;
            if (!(playerEntity instanceof Feature_14.OtherClientPlayerEntity) && playerEntity.deathTime <= 0 && SearchHelper_3.get644((Entity) playerEntity) > 0.0f) {
                if (this.ignoreFriends.getValue().booleanValue() && BaritoneHelper_3.searchHelper4_14.is520(playerEntity)) {
                    return;
                }
                if (!this.ignoreNakeds.getValue().booleanValue() || HoleSnapSearchHelper4.is2013((LivingEntity) playerEntity)) {
                    this.list.add(new Record_2(playerEntity, System.currentTimeMillis(), new AtomicBoolean()));
                }
            }
        }
    }

    public String getString1289(Record record) {
        StringBuilder sb = new StringBuilder();
        if (this.position.getValue() == LogoutSpotsMode.COORDINATES) {
            Vec3d vec3d803 = record.getVec3d803();
            sb.append(" ");
            sb.append(String.format(Locale.US, "X: %.1f, Y: %.1f, Z: %.1f", Double.valueOf(vec3d803.getX()), Double.valueOf(vec3d803.getY()), Double.valueOf(vec3d803.getZ())));
        }
        if (this.position.getValue() == LogoutSpotsMode.DISTANCE) {
            double distanceTo = minecraftClient.player.getPos().distanceTo(record.getVec3d803());
            sb.append(" ");
            sb.append("%.1fm".formatted(Double.valueOf(distanceTo)));
        }
        if (this.time.getValue().booleanValue()) {
            sb.append(" %s".formatted(new SimpleDateFormat("HH:mm").format(new Date(record.get798()))));
        }
        if (this.health.getValue().booleanValue()) {
            sb.append(" (");
            sb.append(record.num2);
            sb.append("hp)");
        }
        if (record.num3 > 0 && this.totems.getValue().booleanValue()) {
            sb.append(" -");
            sb.append(record.num3);
        }
        return sb.toString();
    }

    public boolean is1290(PlayerEntity playerEntity) {
        return minecraftClient.player.networkHandler.getPlayerList().stream().noneMatch(playerListEntry -> {
            return playerListEntry.getProfile().getName().equals(playerEntity.getGameProfile().getName());
        });
    }

    public static boolean is1291() {
        return flag;
    }

    public String getString518() {
        return (minecraftClient.world == null || minecraftClient.isInSingleplayer() || minecraftClient.player.networkHandler.getServerInfo() == null) ? "singleplayer" : minecraftClient.player.networkHandler.getServerInfo().address;
    }
}
