package me.mioclient.module.combat;

import me.mioclient.BaritoneHelper_3;
import me.mioclient.HoleSnapMode;
import me.mioclient.NumberSetting;
import me.mioclient.SearchHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/FishTrap.class */
public class FishTrap extends Range {
    public Object object;

    public FishTrap() {
        super("FishTrap", "Traps your enemies in fish (crawling) position.", Category.COMBAT);
        do2704();
        do1334(false);
        unregister((Setting<?>) this.setting3);
        unregister((Setting<?>) this.setting4);
        unregister((Setting<?>) this.setting5);
        unregister((Setting<?>) this.setting6);
        this.setting4.do2333(true);
        this.setting5.do2333(false);
        this.setting6.do2333(false);
    }

    @Override // me.mioclient.module.combat.Range
    public Vec3d getVec3d885(PlayerEntity playerEntity) {
        if (getSetting2705().is2327()) {
            return super.getVec3d885(playerEntity);
        }
        return SearchHelper.getVec3d222(BaritoneHelper_3.mainhandHelper_2.getBox1109(playerEntity, getSetting2705().getValue().intValue()));
    }

    @Override // me.mioclient.module.combat.Range
    public boolean is884(PlayerEntity playerEntity) {
        if (playerEntity.getBoundingBox().getLengthY() > 1.0) {
            return false;
        }
        return super.is884(playerEntity);
    }

    public void do2704() {
        Setting add = add(new NumberSetting("Extrapolation", 0, 0, 6));
        add.getSetting2338("None", HoleSnapMode.MIN);
        this.object = add;
    }

    public Setting<Integer> getSetting2705() {
        return (Setting) this.object;
    }
}
