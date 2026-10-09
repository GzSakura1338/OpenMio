package me.mioclient;

import java.awt.Color;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/GameStopwatchSearchHelper419_2.class */
public class GameStopwatchSearchHelper419_2 extends StopwatchSearchHelper419 {
    public boolean flag;
    public boolean flag2;
    public boolean flag3;
    public int num;
    public int num2;
    public int num3;
    public RandomHelper randomHelper;
    public Mode_7[] mode_7Arr;

    public GameStopwatchSearchHelper419_2(PresetEnumSettingHelper presetEnumSettingHelper, int i) {
        super(presetEnumSettingHelper, i, 22, 22);
        this.flag = false;
        this.flag2 = false;
        this.flag3 = false;
        this.num = 0;
        this.num2 = 0;
        this.num3 = 0;
    }

    @Override // me.mioclient.StopwatchSearchHelper419
    public void do1638() {
        if (this.randomHelper == null) {
            do1640();
        }
        if (this.flag2) {
            return;
        }
        if (!this.flag) {
            do1643();
        } else {
            this.flag = false;
            do1646();
        }
    }

    @Override // me.mioclient.StopwatchSearchHelper419
    public int get1639() {
        return 500;
    }

    @Override // me.mioclient.StopwatchSearchHelper419, me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
    public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
        super.do19(drawContext, matrixStack, d, d2);
        for (int i = 0; i < this.num2; i++) {
            for (int i2 = 0; i2 < this.num; i2++) {
                Mode_7 mode_71641 = getMode_71641(i2, (this.num2 - i) - 1);
                if (mode_71641 != Mode_7.NoShape) {
                    do2518(matrixStack, i2, i, mode_71641.getColor1125());
                }
            }
        }
        if (this.randomHelper.getMode_71173() != Mode_7.NoShape) {
            RandomHelper randomHelper = new RandomHelper();
            randomHelper.do1168(this.randomHelper.getMode_71173());
            for (int i3 = 0; i3 < 4; i3++) {
                randomHelper.do1169(i3, this.randomHelper.get1171(i3));
                randomHelper.do1170(i3, this.randomHelper.get1172(i3));
                do2518(matrixStack, this.num2 + this.randomHelper.get1171(i3), (this.num2 - (this.num3 - this.randomHelper.get1172(i3))) - 1, this.randomHelper.getMode_71173().getColor1125());
            }
            int i4 = this.num3;
            while (i4 > 0 && is1647(randomHelper, this.num2, i4 - 1)) {
                i4--;
            }
            for (int i5 = 0; i5 < 4; i5++) {
                do2518(matrixStack, this.num2 + randomHelper.get1171(i5), (this.num2 - (i4 - randomHelper.get1172(i5))) - 1, MixinMessageIndicatorHelper_2.getColor816(this.randomHelper.getMode_71173().getColor1125(), 50));
            }
        }
        if (!this.flag3) {
            FontsSearchHelper4.fontsSearchHelper4.do1695(drawContext, String.valueOf(this.num), getPresetEnumSettingHelper1394().getX() + 1.5f, getPresetEnumSettingHelper1394().getY() + this.num + 1, 0.75f, Color.white);
            return;
        }
        String string = new ArgumentTypeHelper().getArgumentTypeHelper2906(this.num).getString2921("Score \u0001. Press R.");
        FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, string, (float) ((getPresetEnumSettingHelper1394().getX() + (getPresetEnumSettingHelper1394().get1635() * FreecamHelper.val2)) - (FontsSearchHelper4.fontsSearchHelper4.get1316(string) * FreecamHelper.val2)), (float) (getPresetEnumSettingHelper1394().getY() + this.num + (get93() * FreecamHelper.val2)), Color.white);
    }

    @Override // me.mioclient.PresetHelper_5
    public void do89(int i) {
        switch (i) {
            case 32:
                do1642();
                return;
            case 68:
                do1643();
                return;
            case 82:
                do1640();
                return;
            case 262:
                is1648(this.randomHelper, this.num2 + 1, this.num3);
                return;
            case 263:
                is1648(this.randomHelper, this.num2 - 1, this.num3);
                return;
            case 264:
                is1648(this.randomHelper.getRandomHelper1178(), this.num2, this.num3);
                return;
            case 265:
                is1648(this.randomHelper.getRandomHelper1177(), this.num2, this.num3);
                return;
            default:
                return;
        }
    }

    public void do1640() {
        this.flag3 = false;
        this.randomHelper = new RandomHelper();
        this.mode_7Arr = new Mode_7[this.num * this.num2];
        do1644();
        do1646();
    }

    public Mode_7 getMode_71641(int i, int i2) {
        return this.mode_7Arr[(i2 * this.num) + i];
    }

    public void do1642() {
        for (int i = this.num3; i > 0 && is1648(this.randomHelper, this.num2, i - 1); i--) {
        }
        do1645();
    }

    public void do1643() {
        if (is1648(this.randomHelper, this.num2, this.num3 - 1)) {
            return;
        }
        do1645();
    }

    public void do1644() {
        this.num = 0;
        for (int i = 0; i < this.num2 * this.num; i++) {
            this.mode_7Arr[i] = Mode_7.NoShape;
        }
    }

    public void do1645() {
        for (int i = 0; i < 4; i++) {
            this.mode_7Arr[((this.num3 - this.randomHelper.get1172(i)) * this.num) + this.num2 + this.randomHelper.get1171(i)] = this.randomHelper.getMode_71173();
        }
        do1649();
        if (this.flag) {
            return;
        }
        do1646();
    }

    public void do1646() {
        this.randomHelper.do1174();
        this.num2 = (this.num / 2) + 1;
        this.num3 = (this.num2 - 1) + this.randomHelper.get1176();
        if (is1648(this.randomHelper, this.num2, this.num3)) {
            return;
        }
        this.randomHelper.do1168(Mode_7.NoShape);
        this.flag3 = true;
    }

    public boolean is1647(RandomHelper randomHelper, int i, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            int i4 = i + randomHelper.get1171(i3);
            int i5 = i2 - randomHelper.get1172(i3);
            if (i4 < 0 || i4 >= this.num || i5 < 0 || i5 >= this.num2 || getMode_71641(i4, i5) != Mode_7.NoShape) {
                return false;
            }
        }
        return true;
    }

    public boolean is1648(RandomHelper randomHelper, int i, int i2) {
        if (!is1647(randomHelper, i, i2)) {
            return false;
        }
        this.randomHelper = randomHelper;
        this.num2 = i;
        this.num3 = i2;
        return true;
    }

    public void do1649() {
        int i = 0;
        for (int i2 = this.num2 - 1; i2 >= 0; i2--) {
            boolean z = true;
            int i3 = 0;
            while (true) {
                if (i3 >= this.num) {
                    break;
                }
                if (getMode_71641(i3, i2) == Mode_7.NoShape) {
                    z = false;
                    break;
                }
                i3++;
            }
            if (z) {
                i++;
                for (int i4 = i2; i4 < this.num2 - 1; i4++) {
                    for (int i5 = 0; i5 < this.num; i5++) {
                        this.mode_7Arr[(i4 * this.num) + i5] = getMode_71641(i5, i4 + 1);
                    }
                }
            }
        }
        if (i > 0) {
            this.num += i;
            this.flag = true;
            this.randomHelper.do1168(Mode_7.NoShape);
        }
    }

    @Override // me.mioclient.PresetHelper_5
    public int get93() {
        return getPresetEnumSettingHelper1394().get1635();
    }
}
