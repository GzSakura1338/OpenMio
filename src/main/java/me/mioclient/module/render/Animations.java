package me.mioclient.module.render;

import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.module.Module;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Animations.class */
public class Animations extends Module {
    public Setting<Boolean> crystals;
    public Setting<Float> floatFactor;
    public Setting<Float> rotationSpeed;
    public Setting<Float> crystalScale;
    public Setting<Boolean> parts;
    public Setting<Boolean> inner;
    public Setting<Boolean> outer;
    public Setting<Boolean> core;
    public Setting<Boolean> bottom;
    public Setting<Boolean> players;
    public Setting<Boolean> static_;
    public Setting<Boolean> sneak;
    public Setting<Float> playerScale;

    public Animations() {
        super("Animations", "Modifies entity animations.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    public boolean is999() {
        return isToggled() && this.players.getValue().booleanValue() && this.playerScale.getValue().floatValue() != 1.0f;
    }

    public boolean is1000() {
        return isToggled() && this.players.getValue().booleanValue() && this.static_.getValue().booleanValue();
    }

    public boolean is1001() {
        return isToggled() && this.players.getValue().booleanValue() && this.sneak.getValue().booleanValue();
    }

    public boolean is1002() {
        return isToggled() && this.crystals.getValue().booleanValue();
    }

    public boolean is1003(int i) {
        switch (i) {
            case 0:
                return is1007();
            case 1:
                return is1005();
            case 2:
                return is1004();
            case 3:
                return is1006();
            default:
                return false;
        }
    }

    public boolean is1004() {
        return is1002() && !this.inner.getValue().booleanValue();
    }

    public boolean is1005() {
        return is1002() && !this.outer.getValue().booleanValue();
    }

    public boolean is1006() {
        return is1002() && !this.core.getValue().booleanValue();
    }

    public boolean is1007() {
        return is1002() && !this.bottom.getValue().booleanValue();
    }
}
