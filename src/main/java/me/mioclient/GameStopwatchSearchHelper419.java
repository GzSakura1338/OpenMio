package me.mioclient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/GameStopwatchSearchHelper419.class */
public class GameStopwatchSearchHelper419 extends StopwatchSearchHelper419 {
    public Mode_3 mode_3;
    public Mode_3 mode_32;
    public final List<PositionData> list;
    public PositionData positionData;
    public int num;
    public boolean flag;

    public GameStopwatchSearchHelper419(PresetEnumSettingHelper presetEnumSettingHelper, int i) {
        super(presetEnumSettingHelper, i);
        this.mode_3 = Mode_3.UP;
        this.mode_32 = null;
        this.list = new ArrayList();
        do1640();
    }

    @Override // me.mioclient.StopwatchSearchHelper419
    public void do1638() {
        if (this.flag) {
            return;
        }
        if (this.mode_32 != null) {
            this.mode_3 = this.mode_32;
            this.mode_32 = null;
        }
        if (this.list.isEmpty()) {
            return;
        }
        PositionData positionData1221 = ((PositionData) this.list.getFirst()).getPositionData1221(this.mode_3.getPositionData244());
        if (positionData1221.get476() > this.num - 1) {
            positionData1221 = new PositionData(0, positionData1221.get1222());
        } else if (positionData1221.get476() < 0) {
            positionData1221 = new PositionData(this.num - 1, positionData1221.get1222());
        } else if (positionData1221.get1222() > this.num2 - 1) {
            positionData1221 = new PositionData(positionData1221.get476(), 0);
        } else if (positionData1221.get1222() < 0) {
            positionData1221 = new PositionData(positionData1221.get476(), this.num2 - 1);
        }
        if (this.list.contains(positionData1221)) {
            this.flag = true;
            return;
        }
        this.list.addFirst(positionData1221);
        if (!((PositionData) this.list.getFirst()).equals(this.positionData)) {
            this.list.removeLast();
        } else {
            this.positionData = getPositionData3122();
            this.num++;
        }
    }

    @Override // me.mioclient.StopwatchSearchHelper419, me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        super.do19(drawContext, matrixStack, d, d2);
        do2518(matrixStack, this.positionData.get476(), this.positionData.get1222(), Color.red);
        boolean z = false;
        for (PositionData positionData : this.list) {
            if (z) {
                do2518(matrixStack, positionData.get476(), positionData.get1222(), this.flag ? Color.GRAY : new Color(0, 150, 0));
            } else {
                do2518(matrixStack, positionData.get476(), positionData.get1222(), Color.GREEN);
                z = true;
            }
        }
        if (!this.flag) {
            FontsSearchHelper4.fontsSearchHelper4.do1695(drawContext, String.valueOf(this.num), getPresetEnumSettingHelper1394().getX() + 1.5f, getPresetEnumSettingHelper1394().getY() + this.num + 1, 0.75f, Color.white);
        } else {
            String string = new ArgumentTypeHelper().getArgumentTypeHelper2906(this.num).getString2921("Score \u0001. Press R.");
            FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, string, (float) ((getPresetEnumSettingHelper1394().getX() + (getPresetEnumSettingHelper1394().get1635() * FreecamHelper.val2)) - (FontsSearchHelper4.fontsSearchHelper4.get1316(string) * FreecamHelper.val2)), (float) (getPresetEnumSettingHelper1394().getY() + this.num + (get93() * FreecamHelper.val2)), Color.white);
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public void do89(int i) {
        Mode_3 mode_3 = null;
        switch (i) {
            case 82:
                do1640();
                break;
            case 262:
                mode_3 = Mode_3.RIGHT;
                break;
            case 263:
                mode_3 = Mode_3.LEFT;
                break;
            case 264:
                mode_3 = Mode_3.DOWN;
                break;
            case 265:
                mode_3 = Mode_3.UP;
                break;
        }
        if (mode_3 == null || mode_3 == this.mode_3.getMode_3245()) {
            return;
        }
        this.mode_32 = mode_3;
    }

    @Override // me.mioclient.StopwatchSearchHelper419
    public int get1639() {
        if (GLFW.glfwGetKey(minecraftClient.getWindow().getHandle(), 32) == 1) {
            return Helper_7.num;
        }
        return 500;
    }

    @Override // me.mioclient.PresetHelper_5
    public int get93() {
        return getPresetEnumSettingHelper1394().get1635();
    }

    public void do1640() {
        this.list.clear();
        this.list.add(new PositionData(11, 11));
        this.positionData = getPositionData3122();
        this.num = 0;
        this.flag = false;
    }

    public PositionData getPositionData3122() {
        PositionData positionData = (PositionData) this.list.getFirst();
        while (true) {
            PositionData positionData2 = positionData;
            if (!this.list.contains(positionData2)) {
                return positionData2;
            }
            positionData = getPositionData3123();
        }
    }

    public PositionData getPositionData3123() {
        return new PositionData((int) (Math.random() * this.num), (int) (Math.random() * this.num2));
    }
}
