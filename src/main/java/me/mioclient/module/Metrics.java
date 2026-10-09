package me.mioclient.module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BooleanSetting;
import me.mioclient.CryptoHelper;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.ModuleListMode;
import me.mioclient.ModuleListSearchHelper4_2;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/Metrics.class */
public class Metrics extends me.mioclient.ModuleList {
    public Setting<Boolean> setting;
    public Setting<Boolean> setting2;
    public Setting<Boolean> setting3;
    public Setting<Boolean> setting4;
    public Setting<Boolean> setting5;
    public Setting<Boolean> setting6;
    public Setting<Boolean> setting7;
    public Setting<Boolean> setting8;
    public Setting<Boolean> setting9;
    public final List<CryptoHelper> list;
    public int num;

    public Metrics() {
        super("Metrics", "infohud", "info");
        this.setting = add(new BooleanSetting("Speed", true).getSetting2336());
        this.setting2 = add(new BooleanSetting("BPS", false).getSetting2342(this.setting));
        this.setting3 = add(new BooleanSetting("TPS", true));
        this.setting4 = add(new BooleanSetting("Ping", true));
        this.setting5 = add(new BooleanSetting("FPS", true));
        this.setting6 = add(new BooleanSetting("ServerBrand", true));
        this.setting7 = add(new BooleanSetting("Durability", false));
        this.setting8 = add(new BooleanSetting("Chest", false).getSetting2336());
        this.setting9 = add(new BooleanSetting("Double", false).getSetting2342(this.setting8));
        this.list = new ArrayList();
        do3019(new ModuleListSearchHelper4_2(this, this.list));
        getModuleListSearchHelper43020().do2952(ModuleListMode.BOTTOM_RIGHT);
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.list.clear();
        List<CryptoHelper> list = this.list;
        Supplier supplier = () -> {
            String string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2919(this.setting2.getValue().booleanValue() ? "b/s" : "km/h").getString2921("Speed %s%.2f\u0001");
            Object[] objArr = new Object[2];
            objArr[0] = Formatting.WHITE;
            objArr[1] = Double.valueOf(BaritoneHelper_3.feetPlaceSearchHelper4.get2635() / (this.setting2.getValue().booleanValue() ? 3.6 : 1.0));
            return Text.literal(string2921.formatted(objArr).replace(",", "."));
        };
        Setting<Boolean> setting = this.setting;
        Objects.requireNonNull(setting);
        CryptoHelper cryptoHelper = new CryptoHelper(supplier, setting::getValue);
        Supplier supplier2 = () -> {
            return Text.literal("TPS %s%.2f".formatted(Formatting.WHITE, Float.valueOf(BaritoneHelper_3.holeSnapSearchHelper4_4.get2620())).replace(",", "."));
        };
        Setting<Boolean> setting2 = this.setting3;
        Objects.requireNonNull(setting2);
        CryptoHelper cryptoHelper2 = new CryptoHelper(supplier2, setting2::getValue);
        Supplier supplier3 = () -> {
            return Text.literal("Ping %s%dms".formatted(Formatting.WHITE, Integer.valueOf(BaritoneHelper_3.holeSnapSearchHelper4_4.get1730())));
        };
        Setting<Boolean> setting3 = this.setting4;
        Objects.requireNonNull(setting3);
        CryptoHelper cryptoHelper3 = new CryptoHelper(supplier3, setting3::getValue);
        Supplier supplier4 = () -> {
            return Text.literal("FPS %s%d".formatted(Formatting.WHITE, Integer.valueOf(BaritoneHelper_3.hitmarkerSearchHelper4.get3094())));
        };
        Setting<Boolean> setting4 = this.setting5;
        Objects.requireNonNull(setting4);
        CryptoHelper cryptoHelper4 = new CryptoHelper(supplier4, setting4::getValue);
        Supplier supplier5 = () -> {
            return Text.literal("ServerBrand %s%s".formatted(Formatting.WHITE, minecraftClient.player.networkHandler.getBrand()));
        };
        Setting<Boolean> setting5 = this.setting6;
        Objects.requireNonNull(setting5);
        CryptoHelper cryptoHelper5 = new CryptoHelper(supplier5, setting5::getValue);
        CryptoHelper cryptoHelper6 = new CryptoHelper(() -> {
            return Text.literal("Durability %s%s".formatted(Formatting.WHITE, Integer.valueOf(minecraftClient.player.getMainHandStack().getMaxDamage() - minecraftClient.player.getMainHandStack().getDamage())));
        }, () -> {
            return Boolean.valueOf(this.setting7.getValue().booleanValue() && minecraftClient.player.getMainHandStack().isDamageable());
        });
        Supplier supplier6 = () -> {
            return Text.literal("Chests %s%d".formatted(Formatting.WHITE, Integer.valueOf(this.num)));
        };
        Setting<Boolean> setting6 = this.setting8;
        Objects.requireNonNull(setting6);
        list.addAll(List.of(cryptoHelper, cryptoHelper2, cryptoHelper3, cryptoHelper4, cryptoHelper5, cryptoHelper6, new CryptoHelper(supplier6, setting6::getValue)));
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        this.list.sort(Comparator.comparing(cryptoHelper -> {
            return Float.valueOf(-FontsSearchHelper4.fontsSearchHelper4.get1316(cryptoHelper.getText1879().getString()));
        }));
        this.num = 0;
        for (BlockEntity blockEntity : BaritoneHelper_3.stashFinderSearchHelper4.getList1555()) {
            if ((blockEntity instanceof ChestBlockEntity) || (blockEntity instanceof BarrelBlockEntity)) {
                this.num++;
            }
        }
        if (this.setting9.getValue().booleanValue()) {
            this.num /= 2;
        }
    }
}
