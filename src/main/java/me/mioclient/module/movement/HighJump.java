package me.mioclient.module.movement;

import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import net.minecraft.entity.attribute.EntityAttributes;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/HighJump.class */
public class HighJump extends Module {
    public static final float val = 0.41999998688697815f;
    public Setting<Float> strength;
    public Setting<Boolean> inMovement;

    public HighJump() {
        super("HighJump", "Makes you jump higher.", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        if (is1469()) {
            return;
        }
        reset();
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (!this.inMovement.getValue().booleanValue() && HoleSnapSearchHelper4_3.is2181()) {
            reset();
        } else {
            do511(this.strength.getValue().floatValue());
        }
    }

    public void reset() {
        do511(0.41999998688697815f);
    }

    public void do511(float f) {
        minecraftClient.player.getAttributeInstance(EntityAttributes.GENERIC_JUMP_STRENGTH).setBaseValue(f);
    }
}
