package me.mioclient.module;

import me.mioclient.ArgumentTypeHelper;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BooleanSetting;
import me.mioclient.CryptoHelper;
import me.mioclient.HoleSnapMode;
import me.mioclient.ModuleListMode;
import me.mioclient.ModuleListSearchHelper4_2;
import me.mioclient.NumberSetting;
import me.mioclient.PingSpoofHelper;
import me.mioclient.PositionData;
import me.mioclient.SearchHelper4_7;
import me.mioclient.StashFinderMode;
import me.mioclient.api.Setting;
import me.mioclient.module.player.Freecam;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/Position.class */
public class Position extends me.mioclient.ModuleList {
    public static Freecam freecam = (Freecam) BaritoneHelper_3.baritoneHelper_4.getModule117(Freecam.class);
    public Setting<Float> setting;
    public Setting<Boolean> setting2;
    public Setting<Boolean> setting3;
    public PositionData positionData;

    public Position() {
        super("Position", "coordinates");
        this.setting = add(new NumberSetting("SafeRange", Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(100.0f)).getNumberSetting3023("k"));
        this.setting2 = add(new BooleanSetting("Nether", true));
        this.setting3 = add(new BooleanSetting("Fake", false));
        this.setting.getSetting2338("None", HoleSnapMode.MIN);
        this.setting3.do2339(() -> {
            BaritoneHelper_3.tooltipsSearchHelper4_2.do164(() -> {
                boolean is380 = PingSpoofHelper.is380(50);
                boolean is3802 = PingSpoofHelper.is380(50);
                int longBitsToDouble = ((int) (((-minecraftClient.player.getX()) * 2.0) + (Math.random() * minecraftClient.player.getX() * 4.0))) + 13;
                int longBitsToDouble2 = ((int) (((-minecraftClient.player.getZ()) * 2.0) + ((Math.random() * minecraftClient.player.getZ()) * 4.0))) - 37;
                if (is380) {
                    longBitsToDouble = -longBitsToDouble;
                }
                if (is3802) {
                    longBitsToDouble2 = -longBitsToDouble2;
                }
                this.positionData = new PositionData(longBitsToDouble, longBitsToDouble2);
            }, 0);
        });
        do3019(new ModuleListSearchHelper4_2(this, new CryptoHelper(() -> {
            if (!is1469()) {
                if (this.setting.getValue().floatValue() > 0.0f) {
                    if (!minecraftClient.player.getPos().isInRange(Vec3d.ZERO, this.setting.getValue().floatValue() * 1000.0f)) {
                    }
                }
                boolean contains = minecraftClient.world.getRegistryKey().getValue().getPath().contains("nether");
                Vec3d pos = minecraftClient.player.getPos();
                if (freecam.isToggled()) {
                    pos = minecraftClient.gameRenderer.getCamera().getPos().subtract(0.0d, minecraftClient.player.getHeight(), 0.0d);
                }
                if (this.setting3.getValue().booleanValue() && this.positionData != null) {
                    if (this.positionData.get476() == 0 && this.positionData.get1222() == 0) {
                        this.setting3.do2340();
                    }
                    pos = pos.add(this.positionData.get476(), 0.0d, this.positionData.get1222());
                }
                BlockPos ofFloored = BlockPos.ofFloored((net.minecraft.util.math.Position) pos);
                BlockPos ofFloored2 = contains ? BlockPos.ofFloored(pos.multiply(8.0, 1.0, 8.0)) : BlockPos.ofFloored(pos.multiply(0.125, 1.0, 0.125));
                String string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2919(String.valueOf(Formatting.WHITE)).getString2921("XYZ: \u0001");
                String string29212 = new ArgumentTypeHelper().getArgumentTypeHelper2906(ofFloored.getZ()).getArgumentTypeHelper2906(ofFloored.getY()).getArgumentTypeHelper2906(ofFloored.getX()).getString2921("\u0001, \u0001, \u0001.");
                String string29213 = new ArgumentTypeHelper().getArgumentTypeHelper2906(ofFloored2.getZ()).getArgumentTypeHelper2906(ofFloored2.getY()).getArgumentTypeHelper2906(ofFloored2.getX()).getString2921(" (\u0001, \u0001, \u0001.)");
                if (this.setting2.getValue().booleanValue() && SearchHelper4_7.getStashFinderMode2438() != StashFinderMode.THE_END) {
                    string29212 = new ArgumentTypeHelper().getArgumentTypeHelper2919(string29213).getArgumentTypeHelper2919(string29212).getString2921("\u0001\u0001");
                }
                return Text.literal(new ArgumentTypeHelper().getArgumentTypeHelper2919(string29212).getArgumentTypeHelper2919(string2921).getString2921("\u0001\u0001"));
            }
            return Text.literal("XYZ: %sREDACTED".formatted(Formatting.WHITE));
        }, () -> {
            return true;
        })));
        getModuleListSearchHelper43020().do2952(ModuleListMode.BOTTOM_LEFT);
    }
}
