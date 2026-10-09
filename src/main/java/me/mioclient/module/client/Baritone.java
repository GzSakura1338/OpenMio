package me.mioclient.module.client;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import java.awt.Color;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FontsEvent;
import me.mioclient.KeybindModule;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.movement.Jesus;
import me.mioclient.module.movement.SafeWalk;
import me.mioclient.module.movement.Step;
import me.mioclient.module.player.AutoTool;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/client/Baritone.class */
public class Baritone extends KeybindModule {
    public static final AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static final Step step = (Step) BaritoneHelper_3.baritoneHelper_4.getModule117(Step.class);
    public static final AutoTool autoTool = (AutoTool) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoTool.class);
    public static SafeWalk safeWalk = (SafeWalk) BaritoneHelper_3.baritoneHelper_4.getModule117(SafeWalk.class);
    public static Jesus jesus = (Jesus) BaritoneHelper_3.baritoneHelper_4.getModule117(Jesus.class);
    public Setting<Boolean> setting;
    public Setting<Boolean> setting2;
    public Setting<Boolean> setting3;
    public Setting<Boolean> setting4;
    public Setting<Boolean> setting5;
    public Setting<Color> setting6;
    public Setting<Color> setting7;
    public boolean flag;
    public boolean flag2;

    public Baritone() {
        super("Baritone", "Manages baritone settings.", Category.CLIENT, new String[0]);
        PhaseESPHelper.do1351(this);
        BaritoneAPI.getSettings().chatControl.value = false;
        baritoneHelper.do1796(this);
    }

    @Listen
    public void onEvent(FontsEvent fontsEvent) {
        do58();
        BaritoneAPI.getSettings().disconnectOnArrival.value = this.setting2.getValue();
        BaritoneAPI.getSettings().freeLook.value = this.setting.getValue();
        BaritoneAPI.getSettings().blockFreeLook.value = this.setting.getValue();
        BaritoneAPI.getSettings().elytraFreeLook.value = this.setting.getValue();
        BaritoneAPI.getSettings().censorCoordinates.value = this.setting4.getValue();
        BaritoneAPI.getSettings().censorRanCommands.value = this.setting4.getValue();
        if (this.setting5.getValue().booleanValue()) {
            Color color816 = MixinMessageIndicatorHelper_2.getColor816(this.setting6.getValue(), 255);
            Color color8162 = MixinMessageIndicatorHelper_2.getColor816(this.setting7.getValue(), 255);
            BaritoneAPI.getSettings().colorBestPathSoFar.value = color816;
            BaritoneAPI.getSettings().colorGoalBox.value = color8162;
            BaritoneAPI.getSettings().colorInvertedGoalBox.value = color8162;
            BaritoneAPI.getSettings().colorCurrentPath.value = color816;
            BaritoneAPI.getSettings().colorMostRecentConsidered.value = color816;
            BaritoneAPI.getSettings().colorBlocksToBreak.value = color816;
            BaritoneAPI.getSettings().colorBlocksToPlace.value = color816;
            BaritoneAPI.getSettings().colorBlocksToWalkInto.value = color816;
            BaritoneAPI.getSettings().colorNextPath.value = color816;
            BaritoneAPI.getSettings().pathRenderLineWidthPixels.value = Float.valueOf(1.5f);
            BaritoneAPI.getSettings().goalRenderLineWidthPixels.value = Float.valueOf(1.5f);
        }
        if (this.setting3.getValue().booleanValue()) {
            BaritoneAPI.getSettings().assumeStep.value = Boolean.valueOf(step.isToggled());
            BaritoneAPI.getSettings().assumeExternalAutoTool.value = Boolean.valueOf(autoTool.isToggled());
            BaritoneAPI.getSettings().assumeSafeWalk.value = Boolean.valueOf(safeWalk.isToggled());
            BaritoneAPI.getSettings().assumeWalkOnWater.value = Boolean.valueOf(jesus.isToggled() && jesus.is240());
        }
    }

    public void do58() {
        Settings.Setting setting = BaritoneAPI.getSettings().antiCheatCompatibility;
        boolean is1625 = antiCheat.getBaritoneSearchHelper4239().is1625();
        if (is1625 != this.flag2) {
            if (is1625) {
                this.flag = ((Boolean) setting.value).booleanValue();
            } else {
                setting.value = Boolean.valueOf(this.flag);
            }
        } else if (is1625) {
            setting.value = false;
        }
        this.flag2 = is1625;
    }
}
