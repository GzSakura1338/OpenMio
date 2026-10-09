package me.mioclient.module.render;

import me.mioclient.Helper_7;
import me.mioclient.PhaseESPHelper;
import me.mioclient.ZoomHelper;
import me.mioclient.ZoomSearchHelper4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.MouseScrollEvent;
import me.mioclient.module.Module;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Zoom.class */
public class Zoom extends Module {
    public Setting<Boolean> smooth;
    public Setting<Boolean> smoothCamera;
    public Setting<Boolean> scroll;
    public Setting<Integer> amount;
    public final ZoomSearchHelper4 zoomSearchHelper4;
    public int num;
    public boolean flag;
    public boolean flag2;
    public final ZoomHelper zoomHelper;
    public final ZoomHelper zoomHelper2;

    public Zoom() {
        super("Zoom", "Zooms your camera in.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.zoomSearchHelper4 = new ZoomSearchHelper4(this);
        this.zoomHelper = new ZoomHelper();
        this.zoomHelper2 = new ZoomHelper();
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        if (is1469()) {
            return;
        }
        this.zoomHelper.do169(0.0f, get919(100));
        this.zoomHelper2.do169(0.0f, get919(100));
        minecraftClient.options.smoothCameraEnabled = this.flag;
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        if (is1469()) {
            disable();
            return;
        }
        baritoneHelper.do1796(this.zoomSearchHelper4);
        if (!this.flag2) {
            this.num = ((Integer) minecraftClient.options.getFov().getValue()).intValue();
        }
        this.zoomHelper.do169(1.0f, get919(Helper_7.num));
        this.flag = minecraftClient.options.smoothCameraEnabled;
    }

    @Listen
    public void onMouseScroll(MouseScrollEvent mouseScrollEvent) {
        if (mouseScrollEvent.get2802() == 0.0d || !this.scroll.getValue().booleanValue()) {
            return;
        }
        int abs = (int) (this.num - (Math.abs((75 / this.amount.getValue().intValue()) - this.num) * get918()));
        this.zoomHelper2.do169((int) MathHelper.clamp((float) (this.zoomHelper2.get172() + (mouseScrollEvent.get2802() * 10.0)), abs - this.num, abs - 10), get919(100));
        mouseScrollEvent.do1162();
    }

    public void do916(int i) {
        ((me.mioclient.ZoomHelper_3) (Object) minecraftClient.options.getFov()).forceSetValue(Integer.valueOf(i));
    }

    public int get917() {
        int abs = (int) (this.num - (Math.abs((75 / this.amount.getValue().intValue()) - this.num) * get918()));
        return this.scroll.getValue().booleanValue() ? (int) MathHelper.clamp(abs - this.zoomHelper2.get172(), 10.0f, 150.0f) : abs;
    }

    public float get918() {
        return (float) (1.0 - Math.pow(1.0f - this.zoomHelper.get172(), 4.0));
    }

    public int get919(int i) {
        if (this.smooth.getValue().booleanValue()) {
            return i;
        }
        return 0;
    }
}
