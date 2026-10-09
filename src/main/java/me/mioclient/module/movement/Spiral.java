package me.mioclient.module.movement;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinLivingEntityHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_2;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/Spiral.class */
public class Spiral extends Module {
    public Setting<Integer> setting;
    public final List<Vec3d> list;
    public int current;

    public Spiral() {
        super("Spiral", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.list = new ArrayList(5000);
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.current = 0;
        this.list.clear();
        Vec3d pos = minecraftClient.player.getPos();
        float f = 0.0f;
        for (int i = 0; i < 5000 * 4; i++) {
            this.list.add(pos);
            pos = pos.add(Vec3d.fromPolar(0.0f, f).multiply(i * this.setting.getValue().intValue() * 16));
            f = MathHelper.wrapDegrees(f + 90.0f);
        }
    }

    public Vec3d getVec3d2994(int i) {
        return this.list.get(i);
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        for (int max = Math.max(this.current, 1); max < Math.max(this.current, 1) + 2; max++) {
            SearchHelper_2.searchHelper_2.do561(inner_3.getMatrixStack472(), this.list.get(max - 1), this.list.get(max), Color.white);
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        Vec3d vec3d2994 = getVec3d2994(this.current);
        minecraftClient.player.setYaw(SearchHelper4_8.getFloatArray2484(vec3d2994)[0]);
        if (MixinLivingEntityHelper_2.get2583(vec3d2994, minecraftClient.player.getPos()) < 3.0f) {
            this.current++;
        }
    }
}
