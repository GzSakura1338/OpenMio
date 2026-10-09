package me.mioclient.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BooleanSetting;
import me.mioclient.EnumSetting;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.NumberSetting;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_3;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Size;
import me.mioclient.module.render.NameTags;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/TextRadar.class */
public class TextRadar extends me.mioclient.ModuleList {
    public Setting<TextRadarMode> setting;
    public Setting<Boolean> setting2;
    public Setting<Boolean> setting3;
    public Setting<Boolean> setting4;
    public Setting<Boolean> setting5;
    public Setting<Boolean> setting6;
    public Setting<Boolean> setting7;
    public Setting<Boolean> setting8;
    public Setting<Integer> setting9;
    public Setting<Boolean> setting10;
    public Setting<Boolean> setting11;
    public Setting<Boolean> setting12;
    public final List<PlayerEntity> list;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/TextRadar$TextRadarMode.class */
    public enum TextRadarMode implements EnumSettingHelper {
        DISTANCE("Distance"),
        ANGLE("Angle");

        public final String name;

        TextRadarMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public TextRadar() {
        super("TextRadar", "playerlist");
        this.setting = add(new EnumSetting("Sort", TextRadarMode.DISTANCE));
        this.setting2 = add(new BooleanSetting("Health", true));
        this.setting3 = add(new BooleanSetting("Distance", false));
        this.setting4 = add(new BooleanSetting("TotemPops", false));
        this.setting5 = add(new BooleanSetting("FriendColor", true));
        this.setting6 = add(new BooleanSetting("EnemyColor", true));
        this.setting7 = add(new BooleanSetting("Armor", false));
        this.setting8 = add(new BooleanSetting("Limit", false).getSetting2336());
        this.setting9 = add(new NumberSetting("Max", 8, 1, 32).getSetting2342(this.setting8));
        this.setting10 = add(new BooleanSetting("Ignore", false).getSetting2337());
        this.setting11 = add(new BooleanSetting("Friends", false).getSetting2342(this.setting10));
        this.setting12 = add(new BooleanSetting("Nakeds", false).getSetting2342(this.setting10));
        this.list = new ArrayList();
        do3019(new Size(this));
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.list.clear();
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        synchronized (this.list) {
            this.list.clear();
            Stream limit = minecraftClient.world.getPlayers().stream().filter((v1) -> {
                return is2648(v1);
            }).sorted(Comparator.comparing((v1) -> {
                return get2647(v1);
            })).limit(this.setting8.getValue().booleanValue() ? this.setting9.getValue().intValue() : Long.MAX_VALUE);
            List<PlayerEntity> list = this.list;
            Objects.requireNonNull(list);
            limit.forEachOrdered((v1) -> {
                list.add((net.minecraft.entity.player.PlayerEntity) v1);
            });
        }
    }

    @Override // me.mioclient.ModuleList
    public void do364(DrawContext drawContext) {
        float f = this.moduleListSearchHelper4.get2954(FontsSearchHelper4.fontsSearchHelper4.get93()) - this.moduleListSearchHelper4.get124();
        for (PlayerEntity playerEntity : this.list) {
            Color color3018 = getColor3018(f);
            if (BaritoneHelper_3.searchHelper4_14.is520(playerEntity) && this.setting5.getValue().booleanValue()) {
                color3018 = BaritoneHelper_3.searchHelper4_14.getColor530(playerEntity.getGameProfile().getName(), color3018);
            } else if (BaritoneHelper_3.searchHelper4_14.is522(playerEntity) && this.setting6.getValue().booleanValue()) {
                color3018 = BaritoneHelper_3.searchHelper4_14.getColor530(playerEntity.getGameProfile().getName(), color3018);
            }
            String string2646 = getString2646(playerEntity);
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, string2646, this.moduleListSearchHelper4.get2953(FontsSearchHelper4.fontsSearchHelper4.get1316(string2646)) - this.moduleListSearchHelper4.get123(), f, color3018);
            f += (FontsSearchHelper4.fontsSearchHelper4.get93() + 1) * this.moduleListSearchHelper4.get2956();
        }
    }

    @Override // me.mioclient.ModuleList
    public float[] getFloatArray365() {
        float f = 0.0f;
        float f2 = 0.0f;
        Iterator<PlayerEntity> it = this.list.iterator();
        while (it.hasNext()) {
            float f3 = FontsSearchHelper4.fontsSearchHelper4.get1316(getString2646(it.next()));
            f += FontsSearchHelper4.fontsSearchHelper4.get93() + 1;
            if (f3 > f2) {
                f2 = f3;
            }
        }
        return new float[]{f2, f};
    }

    public String getString2646(PlayerEntity playerEntity) {
        int i;
        StringBuilder sb = new StringBuilder();
        float f = SearchHelper_3.get644((Entity) playerEntity);
        if (this.setting2.getValue().booleanValue()) {
            sb.append(getFormatting1313(f)).append(Math.round(f)).append(" ");
        }
        sb.append(Formatting.RESET);
        sb.append(BaritoneHelper_3.notificationsHelper.getString398(playerEntity.getGameProfile().getName()));
        if (this.setting4.getValue().booleanValue() && (i = BaritoneHelper_3.logoutSpotsHelper.get895(playerEntity)) > 0) {
            sb.append(NameTags.getFormatting1314(i)).append(" -").append(i);
        }
        if (this.setting3.getValue().booleanValue()) {
            sb.append(Formatting.WHITE).append(" ").append(Math.round(minecraftClient.player.distanceTo((Entity) playerEntity))).append("m");
        }
        if (this.setting7.getValue().booleanValue()) {
            String str = "G";
            Iterator it = playerEntity.getArmorItems().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ItemStack itemStack = (ItemStack) it.next();
                ArmorItem item = (itemStack.getItem()) instanceof ArmorItem ? (ArmorItem) (itemStack.getItem()) : null;
                if (item instanceof ArmorItem) {
                    RegistryEntry material = item.getMaterial();
                    if ((material != ArmorMaterials.DIAMOND && material != ArmorMaterials.NETHERITE) || !itemStack.hasEnchantments()) {
                        str = "";
                    }
                } else {
                    if (itemStack.getItem() == Items.ELYTRA) {
                        str = "W";
                        break;
                    }
                    str = "";
                }
            }
            if (!str.isEmpty()) {
                sb.append(Formatting.WHITE);
                sb.append(" [");
                sb.append(str);
                sb.append("]");
            }
        }
        return sb.toString();
    }

    public Formatting getFormatting1313(double d) {
        return d >= 20.0 ? Formatting.GREEN : d >= 16.0 ? Formatting.DARK_GREEN : d >= 10.0 ? Formatting.GOLD : d >= 4.0 ? Formatting.RED : Formatting.DARK_RED;
    }

    public double get2647(Entity entity) {
        return this.setting.getValue() == TextRadarMode.DISTANCE ? entity.squaredDistanceTo(minecraftClient.player) : MathHelper.angleBetween(minecraftClient.gameRenderer.getCamera().getYaw(), SearchHelper4_8.getFloatArray2483(entity)[0]);
    }

    public boolean is2648(PlayerEntity playerEntity) {
        if (!is3017() && minecraftClient.player == playerEntity) {
            return false;
        }
        if (this.setting11.getValue().booleanValue() && BaritoneHelper_3.searchHelper4_14.is520(playerEntity)) {
            return false;
        }
        return !this.setting12.getValue().booleanValue() || HoleSnapSearchHelper4.is2013((LivingEntity) playerEntity);
    }
}
