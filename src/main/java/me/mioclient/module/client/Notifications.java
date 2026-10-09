package me.mioclient.module.client;

import java.awt.Color;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.Helper_7;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PlayerEntityEvent;
import me.mioclient.PresetHelper;
import me.mioclient.PresetHelper_4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.EnableEvent;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import net.minecraft.network.message.ChatVisibility;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.io.FileUtils;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/client/Notifications.class */
public class Notifications extends Module {
    public static IRC iRC = (IRC) BaritoneHelper_3.baritoneHelper_4.getModule117(IRC.class);
    public Setting<Color> watermark;
    public Setting<Color> brackets;
    public Setting<Boolean> modules;
    public Setting<Boolean> totemPops;
    public Setting<Boolean> self;
    public Setting<Color> primary;
    public Setting<Color> secondary;
    public Setting<Boolean> sayChat;
    public Setting<String> path;
    public Setting<Float> delay;
    public Setting<Boolean> random;
    public Setting<Boolean> refresh;
    public int num;
    public final List<String> list;
    public final Random random2;

    public Notifications() {
        super("Notifications", "Lets you know about what's going on in chat.", Category.CLIENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.num = 0;
        this.list = new ArrayList();
        this.random2 = new Random();
        setDrawn(false);
        this.sayChat.do2339(() -> {
            if (this.sayChat.getValue().booleanValue()) {
                do1248();
            }
        });
        this.refresh.do2339(() -> {
            if (this.refresh.getValue().booleanValue()) {
                this.refresh.do2333(false);
                do1248();
            }
        });
        this.random.do2339(() -> {
            this.num = 0;
        });
        this.path.do2339(this::do1248);
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        do1248();
    }

    @Listen
    public void onEvent(PlayerEntityEvent playerEntityEvent) {
        boolean z = minecraftClient.player == playerEntityEvent.getPlayerEntity1890();
        if (this.totemPops.getValue().booleanValue()) {
            if (!z || this.self.getValue().booleanValue()) {
                String name = z ? "You" : playerEntityEvent.getName();
                int i = playerEntityEvent.get1891();
                if (playerEntityEvent.getLogoutSpotsHelperMode1892() != PlayerEntityEvent.LogoutSpotsHelperMode.TOTEM_POP) {
                    do1244(name, " has died after popping ", i);
                    return;
                }
                do1244(name, new ArgumentTypeHelper().getArgumentTypeHelper2919(z ? " have" : " has").getString2921("\u0001 popped "), i);
                if (BaritoneHelper_3.searchHelper4_14.is519(name) || z || !this.sayChat.getValue().booleanValue() || this.list.isEmpty()) {
                    return;
                }
                executorService.submit(() -> {
                    try {
                        if (this.delay.getValue().floatValue() > 0.0f) {
                            Thread.sleep((long) ((this.delay.getValue().floatValue() * 1000.0f) + this.random2.nextLong(500L)));
                        }
                        String str = null;
                        if (!this.random.getValue().booleanValue()) {
                            str = this.list.get(this.num);
                            this.num = (this.num + 1) % this.list.size();
                        } else if (this.list.size() == 1) {
                            str = this.list.get(0);
                        } else {
                            while (str == null) {
                                int nextInt = this.random2.nextInt(this.list.size());
                                if (this.num != nextInt) {
                                    this.num = nextInt;
                                    str = this.list.get(nextInt);
                                }
                            }
                        }
                        String replace = str.replace("{name}", name).replace("{totems}", Integer.toString(i));
                        if (!iRC.isToggled() || !replace.startsWith(iRC.prefix.getValue())) {
                            MixinMessageIndicatorHelper.do347(replace);
                        }
                    } catch (Exception e) {
                    }
                });
            }
        }
    }

    @Listen
    public void onEnable2(EnableEvent enableEvent) {
        if (is1469() || !this.modules.getValue().booleanValue() || (enableEvent.getModule595() instanceof HUD)) {
            return;
        }
        MutableText empty = Text.empty();
        if (enableEvent.getModule595().isToggled()) {
            empty.append(Text.literal("[").styled(style -> {
                return style.withColor(getColor1245(true).darker().hashCode());
            }));
            empty.append(Text.literal("+").styled(style2 -> {
                return style2.withColor(getColor1245(true).hashCode());
            }));
            empty.append(Text.literal("] ").styled(style3 -> {
                return style3.withColor(getColor1245(true).darker().hashCode());
            }));
        } else {
            empty.append(Text.literal("[").styled(style4 -> {
                return style4.withColor(getColor1245(false).darker().hashCode());
            }));
            empty.append(Text.literal("-").styled(style5 -> {
                return style5.withColor(getColor1245(false).hashCode());
            }));
            empty.append(Text.literal("] ").styled(style6 -> {
                return style6.withColor(getColor1245(false).darker().hashCode());
            }));
        }
        empty.append(Text.literal(BaritoneHelper_3.notificationsHelper.getString397(enableEvent.getModule595())));
        BaritoneHelper_3.tooltipsSearchHelper4_2.do164(() -> {
            MixinMessageIndicatorHelper.do344((Text) empty, MixinMessageIndicatorHelper.getMessageSignatureData339(enableEvent.getModule595()));
        }, 0);
    }

    public String getString1243(int i) {
        return i == 1 ? " totem." : " totems.";
    }

    public void do1244(String str, String str2, int i) {
        if (minecraftClient.options.getChatVisibility().getValue() == ChatVisibility.HIDDEN) {
            return;
        }
        MixinMessageIndicatorHelper.do345(Text.empty().append(Text.literal(str).styled(style -> {
            return style.withColor(this.primary.getValue().hashCode());
        })).append(Text.literal(str2).styled(style2 -> {
            return style2.withColor(this.secondary.getValue().hashCode());
        })).append(Text.literal(String.valueOf(i)).styled(style3 -> {
            return style3.withColor(this.primary.getValue().hashCode());
        })).append(Text.literal(getString1243(i)).styled(style4 -> {
            return style4.withColor(this.secondary.getValue().hashCode());
        })), MixinMessageIndicatorHelper.getMessageSignatureData337(Math.abs(str.hashCode()) * (-1)), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode2);
    }

    public Color getColor1245(boolean z) {
        return z ? new Color(0, 190, 50) : new Color(Helper_7.num, 0, 0);
    }

    public Color getColor1246() {
        return this.watermark.getValue();
    }

    public Color getColor1247() {
        return this.brackets.getValue();
    }

    public void do1248() {
        try {
            Path path1566 = PresetHelper_4.getPath1566(PresetHelper.path.resolve(this.path.getValue()), ".txt");
            if (!path1566.toFile().exists()) {
                throw new RuntimeException("Invalid totem pop file path");
            }
            this.num = 0;
            this.list.clear();
            this.list.addAll(FileUtils.readLines(path1566.toFile(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            if (this.sayChat.getValue().booleanValue()) {
                MixinMessageIndicatorHelper.do345(Text.literal("Failed to open totem pop file").styled(style -> {
                    return style.withColor(Formatting.RED);
                }), MixinMessageIndicatorHelper.getMessageSignatureData337(e.toString().hashCode()), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode3);
                e.printStackTrace();
            }
        }
    }
}
