package me.mioclient.module.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.FreecamHelper;
import me.mioclient.Helper_7;
import me.mioclient.KeyPearlMode;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.AddMessageEvent;
import me.mioclient.event.KeyEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickEvent_2;
import me.mioclient.event.UpdateMouseEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.util.Hand;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/misc/AntiAFK.class */
public class AntiAFK extends Module {
    public Setting<Float> delay;
    public Setting<Boolean> autoReply;
    public Setting<Boolean> jump;
    public Setting<Boolean> sneak;
    public Setting<Boolean> rotate;
    public Setting<Boolean> attack;
    public Setting<Boolean> onlySwing;
    public final Stopwatch stopwatch;
    public final Stopwatch stopwatch2;
    public boolean flag;
    public boolean flag2;

    public AntiAFK() {
        super("AntiAFK", "Prevents you from being kicked for AFK-ing.", Category.MISC, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.stopwatch2 = new Stopwatch();
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.stopwatch.reset();
        this.stopwatch2.reset();
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (is1583()) {
            if (minecraftClient.options.jumpKey.isPressed() && this.jump.getValue().booleanValue()) {
                minecraftClient.options.jumpKey.setPressed(false);
            }
            if (this.stopwatch2.is418(this.delay.getValue().floatValue(), TimeUnit.SECONDS)) {
                List<Runnable> list1582 = getList1582();
                Collections.shuffle(list1582);
                if (!list1582.isEmpty()) {
                    list1582.get(0).run();
                }
                this.stopwatch2.reset();
            }
        }
    }

    @Listen(get219= Helper_7.num2)
    public void onAddMessage(AddMessageEvent addMessageEvent) {
        if (this.autoReply.getValue().booleanValue() && is1583() && addMessageEvent.getKeyPearlMode1472() == KeyPearlMode.Pre && addMessageEvent.getText2279() != null && MixinMessageIndicatorHelper.is335(addMessageEvent.getText2279().getString())) {
            minecraftClient.executeSync(() -> {
                minecraftClient.player.networkHandler.sendChatCommand(new ArgumentTypeHelper().getArgumentTypeHelper2906((int) Math.floor(Math.random() * 10000.0)).getArgumentTypeHelper2919(minecraftClient.player.getName().getString()).getString2921("r Hello! This is auto reply talking. \u0001 is currently AFK :'). [\u0001]"));
            });
        }
    }

    @Listen
    public void onUpdateMouse(UpdateMouseEvent updateMouseEvent) {
        this.stopwatch.reset();
    }

    @Listen
    public void onKey(KeyEvent keyEvent) {
        this.stopwatch.reset();
    }

    @Listen
    public void onTick(TickEvent_2 tickEvent_2) {
        if (this.flag) {
            tickEvent_2.getInput806().jumping = true;
        }
        if (this.flag2) {
            tickEvent_2.getInput806().sneaking = true;
        }
        this.flag2 = false;
        this.flag = false;
    }

    public List<Runnable> getList1582() {
        ArrayList<Runnable> arrayList = new ArrayList<>();
        if (this.jump.getValue().booleanValue()) {
            arrayList.add(() -> {
                this.flag = true;
            });
        }
        if (this.sneak.getValue().booleanValue()) {
            arrayList.add(() -> {
                this.flag2 = true;
            });
        }
        if (this.rotate.getValue().booleanValue()) {
            Random random = new Random();
            arrayList.add(() -> {
                minecraftClient.player.setYaw(random.nextFloat(FreecamHelper.num3));
                minecraftClient.player.setPitch(random.nextFloat(180.0f) - FreecamHelper.num2);
            });
        }
        if (this.attack.getValue().booleanValue()) {
            arrayList.add(() -> {
                if (!this.onlySwing.getValue().booleanValue()) {
                    ((me.mioclient.mixin.ducks.DuckMinecraftClient) (Object) minecraftClient).attack();
                } else {
                    minecraftClient.player.swingHand(Hand.MAIN_HAND);
                }
            });
        }
        return arrayList;
    }

    public boolean is1583() {
        return this.stopwatch.is419((long) (this.delay.getValue().floatValue() * 1000.0f));
    }
}
