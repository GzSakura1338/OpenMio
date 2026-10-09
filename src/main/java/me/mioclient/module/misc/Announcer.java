package me.mioclient.module.misc;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.PingSpoofHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.BreakBlockEvent;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.FinishUsingEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.text.Text;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/misc/Announcer.class */
public class Announcer extends Module {
    public Setting<Boolean> clientSide;
    public Setting<Boolean> compact;
    public Setting<Boolean> joins;
    public Setting<Boolean> friends;
    public Setting<Boolean> enemies;
    public Setting<Boolean> others;
    public Setting<Boolean> move;
    public Setting<Boolean> break_;
    public Setting<Boolean> eat;
    public Setting<Double> delay;
    public final Stopwatch stopwatch;
    public double val;
    public int num;
    public int num2;

    public Announcer() {
        super("Announcer", "Lets the others know what you're doing.", Category.MISC, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        setDrawn(false);
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.num = 0;
        this.num2 = 0;
        this.stopwatch.reset();
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if (sendImmediatelyEvent.getPacket904() instanceof ChatMessageC2SPacket) {
            this.stopwatch.reset();
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (this.joins.getValue().booleanValue()) {
            if (this.stopwatch.is418(this.delay.getValue().doubleValue(), TimeUnit.SECONDS)) {
                PlayerListS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof PlayerListS2CPacket ? (PlayerListS2CPacket) (channelRead0Event.getPacket904()) : null;
                if (packet904 instanceof PlayerListS2CPacket) {
                    for (PlayerListS2CPacket.Entry entry : packet904.getPlayerAdditionEntries()) {
                        if (entry.listed()) {
                            String name = entry.profile().getName();
                            if (!name.equals(minecraftClient.player.getName().getString())) {
                                boolean is519 = BaritoneHelper_3.searchHelper4_14.is519(name);
                                boolean is521 = BaritoneHelper_3.searchHelper4_14.is521(name);
                                if (this.friends.getValue().booleanValue() || !is519) {
                                    if (this.enemies.getValue().booleanValue() || !is521) {
                                        if (this.others.getValue().booleanValue() || is519 || is521) {
                                            if (this.clientSide.getValue().booleanValue() && this.compact.getValue().booleanValue()) {
                                                String string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2919(name).getString2921("\u0001 joined");
                                                if (is519) {
                                                    string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2919(string2921).getString2921("\u0001 [friend]");
                                                } else if (is521) {
                                                    string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2919(string2921).getString2921("\u0001 [enemy]");
                                                }
                                                String str = string2921;
                                                minecraftClient.executeSync(() -> {
                                                    do1927(str, MixinMessageIndicatorHelper.getMessageSignatureData337(-254721));
                                                });
                                            } else {
                                                if (is519) {
                                                    name = new ArgumentTypeHelper().getArgumentTypeHelper2919(name).getString2921("My friend \u0001");
                                                }
                                                String str2 = name;
                                                minecraftClient.executeSync(() -> {
                                                    do1927(new ArgumentTypeHelper().getArgumentTypeHelper2919(str2).getString2921("\u0001 has joined the game!"), MixinMessageIndicatorHelper.getMessageSignatureData337(-254721));
                                                });
                                            }
                                            this.stopwatch.reset();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                PlayerRemoveS2CPacket packet9042 = (channelRead0Event.getPacket904()) instanceof PlayerRemoveS2CPacket ? (PlayerRemoveS2CPacket) (channelRead0Event.getPacket904()) : null;
                if (packet9042 instanceof PlayerRemoveS2CPacket) {
                    for (UUID uuid : packet9042.profileIds()) {
                        for (PlayerListEntry playerListEntry : minecraftClient.player.networkHandler.getListedPlayerListEntries()) {
                            if (playerListEntry.getProfile().getId().equals(uuid)) {
                                String name2 = playerListEntry.getProfile().getName();
                                if (this.clientSide.getValue().booleanValue() && this.compact.getValue().booleanValue()) {
                                    String string29212 = new ArgumentTypeHelper().getArgumentTypeHelper2919(name2).getString2921("\u0001 left");
                                    if (BaritoneHelper_3.searchHelper4_14.is519(string29212)) {
                                        string29212 = new ArgumentTypeHelper().getArgumentTypeHelper2919(string29212).getString2921("\u0001 [friend]");
                                    } else if (BaritoneHelper_3.searchHelper4_14.is521(string29212)) {
                                        string29212 = new ArgumentTypeHelper().getArgumentTypeHelper2919(string29212).getString2921("\u0001 [enemy]");
                                    }
                                    String str3 = string29212;
                                    minecraftClient.executeSync(() -> {
                                        do1927(str3, MixinMessageIndicatorHelper.getMessageSignatureData337(-254722));
                                    });
                                } else {
                                    if (BaritoneHelper_3.searchHelper4_14.is519(name2)) {
                                        name2 = new ArgumentTypeHelper().getArgumentTypeHelper2919(name2).getString2921("My friend \u0001");
                                    }
                                    String str4 = name2;
                                    minecraftClient.executeSync(() -> {
                                        do1927(new ArgumentTypeHelper().getArgumentTypeHelper2919(str4).getString2921("\u0001 has left the game!"), MixinMessageIndicatorHelper.getMessageSignatureData337(-254722));
                                    });
                                }
                                this.stopwatch.reset();
                            }
                        }
                    }
                }
            }
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (is1469()) {
            return;
        }
        this.val += (BaritoneHelper_3.feetPlaceSearchHelper4.get2636() / 3.6) / 20.0;
        if (this.val >= 1000.0) {
            this.val = 0.0d;
        }
        if (!this.move.getValue().booleanValue() || this.val < 1.0) {
            return;
        }
        if (this.stopwatch.is419((long) (1000.0 * this.delay.getValue().doubleValue()))) {
            do1926(getString1923().replace("{blocks}", new DecimalFormat("0.00").format(this.val)));
            this.val = 0.0d;
            this.stopwatch.reset();
        }
    }

    @Listen
    public void onFinishUsing(FinishUsingEvent finishUsingEvent) {
        if (is1469()) {
            return;
        }
        int i = PingSpoofHelper.get371(1, 6);
        if (this.eat.getValue().booleanValue()) {
            if (finishUsingEvent.getItemStack2549().contains(DataComponentTypes.FOOD)) {
                this.num++;
                if (this.num >= i) {
                    if (this.stopwatch.is419((long) (1000.0 * this.delay.getValue().doubleValue()))) {
                        do1926(getString1925().replace("{amount}", new ArgumentTypeHelper().getArgumentTypeHelper2906(this.num).getString2921("\u0001")).replace("{name}", new ArgumentTypeHelper().getArgumentTypeHelper2919(finishUsingEvent.getItemStack2549().getItem().getName().getString()).getString2921("\u0001")));
                        this.num = 0;
                        this.stopwatch.reset();
                    }
                }
            }
        }
    }

    @Listen
    public void onBreakBlock(BreakBlockEvent breakBlockEvent) {
        if (is1469()) {
            return;
        }
        int i = PingSpoofHelper.get371(1, 6);
        this.num2++;
        if (this.break_.getValue().booleanValue() && this.num2 >= i && this.stopwatch.is419((long) (1000.0 * this.delay.getValue().doubleValue()))) {
            String[] strArr = {new ArgumentTypeHelper().getArgumentTypeHelper2919(PhaseESPSearchHelper4_2.getBlock3044(breakBlockEvent.getBlockPos386()).getName().getString()).getString2921("\u0001"), "Air", "Bedrock", "Barrier"};
            do1926(getString1924().replace("{amount}", new ArgumentTypeHelper().getArgumentTypeHelper2906(this.num2).getString2921("\u0001")).replace("{name}", strArr[new Random().nextInt(strArr.length)]));
            this.num2 = 0;
            this.stopwatch.reset();
        }
    }

    public String getString1923() {
        String[] strArr = {"I just flew over {blocks} blocks thanks to mioclient.me!", "Я только что пролетел над {blocks} блоками с помощью mioclient.me!", "mioclient.me sayesinde {blocks} blok uçtum!", "我刚刚用 mioclient.me 走了 {blocks} 米!", "Dank mioclient.me bin ich gerade über {blocks} Blöcke geflogen!", "Jag hoppade precis över {blocks} blocks tack vare mioclient.me!", "Właśnie przeleciałem ponad {blocks} bloki dzięki mioclient.me!", "Es tikko nolidoju {blocks} blokus, paldies mioclient.me!", "Я щойно пролетів понад {blocks} блоками завдяки mioclient.me!", "I just fwew ovew {blocks} bwoccs thanks to miocwient.me! :3", "Ho appena camminato per {blocks} blocchi grazie a mioclient.me!", "עכשיו עפתי {blocks} הודות ל mioclient.me!", "Právě jsem proletěl {blocks} bloků díky mioclient.me!", "Lensin juuri yli {blocks} blokkia mioclient.me:n ansiosta!", "Ravnokar sem preletel {blocks} blokov v zahvali mioclient.me!", "أنا هلق طاير فوق {blocks} بلوكس بفضل mioclient.me!"};
        return strArr[new Random().nextInt(strArr.length)];
    }

    public String getString1924() {
        String[] strArr = {"I just destroyed {amount} {name} with the power of mioclient.me!", "Я только что разрушил {amount} {name} с помощью mioclient.me!", "Az önce {amount} tane {name} kırdım. TeŞekkürler mioclient.me!", "我刚刚用 mioclient.me 破坏了 {amount} {name}!", "Ich habe gerade {amount} {name} mit der Kraft von mioclient.me zerstört!", "Jag förstörde precis {amount} {name} tack vare mioclient.me!", "Właśnie zniszczyłem {amount} {name} za pomocą mioclient.me", "Es tikko salauzu {amount} {name} ar spēku mioclient.me!", "Я щойно знищив {amount} {name} за допомогою mioclient.me!", "I just destwoyed {amount} {name} with the powew of miocwient.me! :3", "Ho appena distrutto {amount} {name} grazie al potere di mioclient.me!", "בדיוק חצבתי {amount} {name} בעזרת הכוח של mioclient.me!", "Právě jsem zničil {amount} {name} díky mioclient.me!", "Rikoin juuri {amount} {name}ia mioclient.me:n ansiosta!", "Ravnokar sem uničil {amount} {name} z močjo mioclient.me", "أنا هلق دمّرت {amount} {name} بقوة mioclient.me!"};
        return strArr[new Random().nextInt(strArr.length)];
    }

    public String getString1925() {
        String[] strArr = {"I just ate {amount} {name} thanks to mioclient.me!", "Я только что съел {amount} {name} с помощью mioclient.me!", "Tam olarak {amount} tane {name} yedim. TeŞekkürler mioclient.me", "我刚用 mioclient.me 吃了 {amount} 个 {name}!", "Ich habe gerade {amount} {name} dank mioclient.me gegessen!", "Jag åt precis {amount} {name} tack vare mioclient.me", "Właśnie zjadłem {amount} {name} dzięki mioclient.me", "Es tikko apēdu {amount} {name} ar mioclient.me spēku!", "Я щойно з’їв {amount} {name} завдяки mioclient.me!", "I just ate {amount} {name} thanks to miocwient.me! ^-^", "Ho appena mangiato {amount} {name} grazie a mioclient.me!", "כרגע אכלתי {amount} {name} הודות לmioclient.me!", "Právě jsem snědl {amount} {name} díky mioclient.me", "Söin juuri {amount} {name}a mioclient.me:n ansiosta!", "Ravnokar sem pojedel {amount} {name} v zahvali mioclient.me", "أنا هلق أكلت {amount} {name} بفضل miocliet.me!"};
        return strArr[new Random().nextInt(strArr.length)];
    }

    public void do1926(String str) {
        do1927(str, MixinMessageIndicatorHelper.getMessageSignatureData339(this));
    }

    public void do1927(String str, MessageSignatureData messageSignatureData) {
        if (!this.clientSide.getValue().booleanValue()) {
            minecraftClient.player.networkHandler.sendChatMessage(str);
        } else {
            MixinMessageIndicatorHelper.do344(Text.literal(str).styled(style -> {
                return style.withColor(new Color(161, 161, 161).hashCode());
            }), messageSignatureData);
        }
    }
}
