package me.mioclient.module.combat;

import java.util.ArrayList;
import java.util.List;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.MatrixStackEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PingSpoofHelper;
import me.mioclient.SearchHelper_4;
import me.mioclient.mixin.ducks.DuckMinecraftClient;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.exploit.MultiTask;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/AutoClicker.class */
public class AutoClicker extends Module {
    public static final MultiTask multitask = (MultiTask) BaritoneHelper_3.baritoneHelper_4.getModule117(MultiTask.class);
    public Setting<AutoClickerMode> mode;
    public Setting<Integer> cps;
    public Setting<Float> delay;
    public Setting<Boolean> friendProtect;
    public final Stopwatch stopwatch;
    public float val;
    public final List<Long> list;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/AutoClicker$AutoClickerMode.class */
    public enum AutoClickerMode implements EnumSettingHelper {
        LEFT("Left"),
        RIGHT("Right");

        public final String name;

        AutoClickerMode(String str) {
            this.name = str;
        }

        public void do2025() {
            if (this == LEFT) {
                ((DuckMinecraftClient)(Object) SearchHelper_4.minecraftClient).attack();
            } else {
                ((DuckMinecraftClient)(Object) SearchHelper_4.minecraftClient).interact();
            }
        }

        public KeyBinding getKeyBinding2026() {
            return this == LEFT ? SearchHelper_4.minecraftClient.options.attackKey : SearchHelper_4.minecraftClient.options.useKey;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public AutoClicker() {
        super("AutoClicker", "Spams attack as you hold down the attack button.", Category.COMBAT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.val = 1.0f;
        this.list = new ArrayList();
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return String.valueOf(this.list.size());
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner inner) {
        this.list.removeIf(l -> {
            return System.currentTimeMillis() - l.longValue() >= 1000;
        });
        long intValue = 1000 / this.cps.getValue().intValue();
        if (!this.delay.is2327()) {
            intValue = (long) (((float) intValue) * this.val);
        }
        if (this.stopwatch.is419(intValue)) {
            do1074();
            this.list.add(Long.valueOf(System.currentTimeMillis()));
            this.stopwatch.reset();
            this.val = PingSpoofHelper.get370(1.0f, this.delay.getValue().floatValue());
        }
    }

    public boolean is1073(Entity entity) {
        return isToggled() && this.friendProtect.getValue().booleanValue() && (entity instanceof PlayerEntity) && BaritoneHelper_3.searchHelper4_14.is520((PlayerEntity) entity);
    }

    public void do1074() {
        if (!multitask.isToggled() || (!minecraftClient.player.isUsingItem() && minecraftClient.currentScreen == null)) {
            KeyBinding.onKeyPressed(this.mode.getValue().getKeyBinding2026().boundKey);
        } else {
            this.mode.getValue().do2025();
        }
    }
}
