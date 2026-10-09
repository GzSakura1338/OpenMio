package me.mioclient.module.misc;

import java.awt.Color;
import java.util.Comparator;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.NameTagsHelperMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.module.Module;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Nullables;
import net.minecraft.world.GameMode;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/misc/ExtraTab.class */
public class ExtraTab extends Module {
    public Setting<MixinPlayerListHudMode> latency;
    public Setting<Float> scale;
    public Setting<Boolean> betterSkin;
    public Setting<Boolean> highlight;
    public Setting<Color> self;
    public Setting<Boolean> social;
    public Setting<Boolean> sort;
    public Setting<ExtraTabMode> mode;
    public Setting<Boolean> reverse;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/misc/ExtraTab$ExtraTabMode.class */
    public enum ExtraTabMode implements EnumSettingHelper {
        ALPHABET("Alphabet"),
        SOCIALS("Socials"),
        LATENCY("Latency"),
        LENGTH("Length");

        public final String name;

        ExtraTabMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/misc/ExtraTab$MixinPlayerListHudMode.class */
    public enum MixinPlayerListHudMode implements EnumSettingHelper {
        VANILLA("Vanilla"),
        TEXT("Text"),
        NONE("None");

        public final String name;

        MixinPlayerListHudMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public ExtraTab() {
        super("ExtraTab", "Allows you to customize your TAB-screen.", Category.MISC, new String[0]);
        PhaseESPHelper.do1351(this);
        setDrawn(false);
    }

    public float get1086() {
        return !isToggled() ? 1.0f : this.scale.getValue().floatValue();
    }

    public int get1087(PlayerListEntry playerListEntry) {
        switch (this.mode.getValue()) {
            case ALPHABET:
                return 0;
            case SOCIALS:
                NameTagsHelperMode nameTagsHelperMode525 = BaritoneHelper_3.searchHelper4_14.getNameTagsHelperMode525(playerListEntry.getProfile().getName());
                int i = 0;
                if (nameTagsHelperMode525 == NameTagsHelperMode.FRIEND) {
                    i = -1;
                }
                if (nameTagsHelperMode525 == NameTagsHelperMode.ENEMY) {
                    i = -2;
                }
                return i;
            case LATENCY:
                return -playerListEntry.getLatency();
            case LENGTH:
                return -minecraftClient.textRenderer.getWidth(playerListEntry.getProfile().getName());
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public Comparator<PlayerListEntry> getComparator1088() {
        Comparator<PlayerListEntry> thenComparing = Comparator.comparingInt((PlayerListEntry playerListEntry) -> {
            int i = playerListEntry.getGameMode() == GameMode.SPECTATOR ? 1 : 0;
            if (this.reverse.getValue().booleanValue()) {
                i *= -1;
            }
            return i;
        }).thenComparing(playerListEntry2 -> {
            return (String) Nullables.mapOrElse(playerListEntry2.getScoreboardTeam(), (v0) -> {
                return v0.getName();
            }, "");
        }).thenComparing(playerListEntry3 -> {
            return Integer.valueOf(get1087(playerListEntry3));
        }).thenComparing(playerListEntry4 -> {
            return playerListEntry4.getProfile().getName();
        }, (v0, v1) -> {
            return v0.compareToIgnoreCase(v1);
        });
        if (this.reverse.getValue().booleanValue()) {
            thenComparing = thenComparing.reversed();
        }
        return thenComparing;
    }
}
