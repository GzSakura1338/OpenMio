package me.mioclient.module.movement;

import me.mioclient.BaritoneHelper_3;
import me.mioclient.Helper_7;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchIdentifier;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickEvent_2;
import me.mioclient.module.Module;
import net.minecraft.text.Text;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/AutoWalk.class */
public class AutoWalk extends Module {
    public Setting<Boolean> autoJump;
    public Setting<Boolean> stuckNotify;
    public Setting<SearchIdentifier> sound;
    public Setting<Float> volume;
    public static boolean flag;
    public boolean flag2;

    public AutoWalk() {
        super("AutoWalk", "Lets your forward key rest.", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.flag2 = true;
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        flag = true;
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (BaritoneHelper_3.feetPlaceSearchHelper4.get2634() != 0.0d) {
            this.flag2 = false;
            return;
        }
        if (!this.flag2 && this.stuckNotify.getValue().booleanValue()) {
            MixinMessageIndicatorHelper.do345(Text.literal("You are stuck!"), MixinMessageIndicatorHelper.getMessageSignatureData339(this), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode);
            BaritoneHelper_3.searchHelper4_11.getSearchHelper4_42970(this.sound.getValue()).do1820(this.volume.getValue().floatValue());
        }
        this.flag2 = true;
    }

    @Listen(get219= Helper_7.num4)
    public void do329(TickEvent_2 tickEvent_2) {
        tickEvent_2.getInput806().movementForward = tickEvent_2.is808() ? tickEvent_2.get807() : 1.0f;
        tickEvent_2.getInput806().pressingForward = true;
        if (this.autoJump.getValue().booleanValue()) {
            tickEvent_2.getInput806().jumping = true;
        }
    }

    @Listen(get219= Helper_7.num4)
    public static void onTick(TickEvent_2 tickEvent_2) {
        if (flag) {
            tickEvent_2.getInput806().movementForward = 0.0f;
            tickEvent_2.getInput806().pressingForward = false;
            tickEvent_2.getInput806().jumping = false;
            flag = false;
        }
    }

    static {
        baritoneHelper.do1797(AutoWalk.class);
    }
}
