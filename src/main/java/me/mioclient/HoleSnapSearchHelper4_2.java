package me.mioclient;

import java.util.concurrent.ConcurrentHashMap;
import me.mioclient.event.EnableEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HoleSnapSearchHelper4_2.class */
public class HoleSnapSearchHelper4_2 implements SearchHelper_4 {
    public final ConcurrentHashMap<Module, Boolean> concurrentHashMap = new ConcurrentHashMap<>();
    public float val = 1.0f;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/HoleSnapSearchHelper4_2$Inner.class */
    public static class Inner extends HoleSnapSearchHelper4_2 {
        @Listen(get219= Helper_7.num5)
        public void onTick(TickEvent tickEvent) {
            if (get2019() == 1.0f) {
                return;
            }
            for (int i = 1; i <= get2019() - 1.0f; i++) {
                MixinLivingEntityHelper.do869();
            }
        }
    }

    public HoleSnapSearchHelper4_2() {
        baritoneHelper.do1796(this);
    }

    @Listen
    public void onEnable2(EnableEvent enableEvent) {
        if (enableEvent.getModule595().isToggled()) {
            return;
        }
        do2017(enableEvent.getModule595());
    }

    public void do2017(Module module) {
        this.concurrentHashMap.compute(module, (module2, bool) -> {
            if (Boolean.FALSE.equals(bool)) {
                do2020(1.0f);
            }
            return true;
        });
    }

    public void do2018(Module module, float f) {
        this.concurrentHashMap.compute(module, (module2, bool) -> {
            return false;
        });
        do2020(f);
    }

    public float get2019() {
        return this.val;
    }

    public void do2020(float f) {
        this.val = f;
    }
}
