package me.mioclient.module.render;

import me.mioclient.MatrixStackEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.feature.Progress;
import me.mioclient.module.Module;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/ViewClip.class */
public class ViewClip extends Module {
    public Setting<Double> range;
    public Setting<Boolean> smooth;
    public final Progress progress;

    public ViewClip() {
        super("ViewClip", "Allows you to clip into blocks using 3rd person camera.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.progress = new Progress(1.2999999523162842f, true);
        setDrawn(false);
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (minecraftClient.gameRenderer.getCamera().isThirdPerson()) {
            this.progress.do2139(true);
            return;
        }
        this.progress.do171(1.0f);
    }
}
