package me.mioclient;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import me.mioclient.module.client.UI;
import me.mioclient.module.render.Blur;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/FontsSearchHelper4_2.class */
public class FontsSearchHelper4_2 extends Screen implements SearchHelper_4 {
    public final ArrayList<PresetEnumSettingHelper> arrayList;
    public final List<Helper_20> list;
    public final HUDHelper_2 hUDHelper_2;
    public final HUDHelper_2 hUDHelper_22;
    public boolean flag;
    public float val;
    public boolean flag2;
    public String string;
    public long num2;
    public long num3;
    public Screen screen;
    public boolean flag3;
    public static final int num = 25;
    public static Blur blur = (Blur) BaritoneHelper_3.baritoneHelper_4.getModule117(Blur.class);
    public static Mode_5 mode_5 = Mode_5.STANDARD;

    public FontsSearchHelper4_2() {
        super(Text.literal("mio"));
        this.arrayList = new ArrayList<>();
        this.list = new ArrayList();
        this.hUDHelper_2 = new HUDHelper_2(2.5f, true);
        this.hUDHelper_22 = new HUDHelper_2(2.5f, true);
        this.flag = false;
        this.string = "";
        this.num2 = 0L;
        do2827();
    }

    public void init() {
        int scaledWidth = minecraftClient.getWindow().getScaledWidth();
        int scaledHeight = minecraftClient.getWindow().getScaledHeight();
        if (getScreen1715() != null) {
            getScreen1715().init(minecraftClient, scaledWidth, scaledHeight);
        }
        if (!is1469()) {
            this.hUDHelper_2.do171(scaledWidth / 2.0f);
            this.hUDHelper_22.do171(scaledHeight / 2.0f);
        }
        this.flag = false;
        getArrayList2831().forEach(presetEnumSettingHelper -> {
            presetEnumSettingHelper.do1969(false);
        });
        this.val = UI.uI.guiScale.getValue().floatValue();
        this.flag2 = false;
        this.num3 = System.currentTimeMillis();
        Iterator<PresetEnumSettingHelper> it = this.arrayList.iterator();
        while (it.hasNext()) {
            it.next().init();
        }
    }

    public void render(DrawContext drawContext, int i, int i2, float f) {
        this.hUDHelper_2.do1737(i);
        this.hUDHelper_22.do1737(i2);
        float f2 = this.hUDHelper_2.get172();
        float f3 = this.hUDHelper_22.get172();
        if ((getScreen1715() instanceof TitleScreen) || (getScreen1715() instanceof MultiplayerScreen)) {
            GlStateManager._enablePolygonOffset();
            GlStateManager._polygonOffset(1.0f, 1000.0f);
            getScreen1715().render(drawContext, -99, -99, f);
            minecraftClient.getBufferBuilders().getEntityVertexConsumers().draw();
            drawContext.draw();
            GlStateManager._polygonOffset(1.0f, -1000.0f);
            GlStateManager._disablePolygonOffset();
        }
        mode_5.do935();
        mode_5 = Mode_5.STANDARD;
        this.list.removeIf((v0) -> {
            return v0.is2378();
        });
        if (this.list.size() < 25) {
            this.list.add(new Helper_20());
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, get189());
        float f4 = (float) SearchHelper4.get1481(this.val);
        SearchHelper4.do1479(this.val);
        drawContext.getMatrices().scale(f4, f4, 1.0f);
        int i3 = (int) (i / f4);
        int i4 = (int) (i2 / f4);
        float f5 = f2 / f4;
        float f6 = f3 / f4;
        this.string = "";
        if (!is2834() && blur.isToggled()) {
            BlurFramebuffer.do2002(() -> {
                Iterator<PresetEnumSettingHelper> it = getArrayList2831().iterator();
                while (it.hasNext()) {
                    PresetEnumSettingHelper next = it.next();
                    float f7 = next.get1970();
                    int i5 = next.get93();
                    Objects.requireNonNull(next);
                    int x = next.getX();
                    int y = next.getY();
                    int x2 = next.getX() + next.get1635();
                    int y2 = next.getY();
                    Objects.requireNonNull(next);
                    drawContext.fill(x, y, x2, (int) (y2 + 14 + ((i5 - 14) * f7)), -1);
                }
            }, blur.get992());
        }
        do204(drawContext);
        if (Mode_10.WINTER.is2576(LocalDate.now().getMonthValue())) {
            Iterator<Helper_20> it = this.list.iterator();
            while (it.hasNext()) {
                it.next().do364(drawContext);
            }
        }
        Iterator<PresetEnumSettingHelper> it2 = this.arrayList.iterator();
        while (it2.hasNext()) {
            PresetEnumSettingHelper next = it2.next();
            next.do1972(i3, i4);
            next.do19(drawContext, drawContext.getMatrices(), i3, i4);
        }
        if (!this.string.isBlank()) {
            CrosshairHelper.do1597();
            String[] split = this.string.split("\n");
            float f7 = 0.0f;
            for (String str : split) {
                float f8 = FontsSearchHelper4.fontsSearchHelper4.get1316(str);
                if (f8 > f7) {
                    f7 = f8;
                }
            }
            float intBitsToFloat = f5 + 9.0f;
            float intBitsToFloat2 = f5 + f7 + 10.0f;
            if (intBitsToFloat2 > minecraftClient.getWindow().getScaledWidth()) {
                intBitsToFloat = (f5 - f7) - 2.0f;
                intBitsToFloat2 = f5 - 1.0f;
            }
            float f9 = intBitsToFloat;
            float f10 = intBitsToFloat2;
            int i5 = (int) f6;
            float intBitsToFloat3 = (f6 - 1.0f) + ((FontsSearchHelper4.fontsSearchHelper4.get93() + 1.0f) * split.length);
            BlurFramebuffer.do2002(() -> {
                SearchHelper_2.searchHelper_2.do545(drawContext.getMatrices(), f9, i5 - 1.0f, f10, intBitsToFloat3, MixinMessageIndicatorHelper_2.get821(10, 10, 10, 140));
            }, 6.0f);
            SearchHelper_2.searchHelper_2.do546(drawContext.getMatrices(), intBitsToFloat, f6 - 1.0f, intBitsToFloat2, intBitsToFloat3, new Color(10, 10, 10, 80));
            int i6 = 0;
            for (String str2 : split) {
                FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, str2, intBitsToFloat + 1.0f, f6 + ((FontsSearchHelper4.fontsSearchHelper4.get93() + 1.0f) * i6), Color.white);
                i6++;
            }
            this.num2 = System.currentTimeMillis();
        } else if (!UI.uI.constantReset.getValue().booleanValue() && this.num2 != 0 && System.currentTimeMillis() > this.num2 + 750) {
            this.num2 = 0L;
            ArrayListPresetHelper2.do655();
        }
        do940(drawContext, i3, i4, f);
        CrosshairHelper.do1597();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        SearchHelper4.do604();
    }

    public boolean mouseClicked(double d, double d2, int i) {
        if (this.flag2) {
            return false;
        }
        double d3 = d / SearchHelper4.get1481(this.val);
        double d4 = d2 / SearchHelper4.get1481(this.val);
        boolean mouseClicked = super.mouseClicked(d3, d4, i);
        PresetEnumSettingHelper presetEnumSettingHelper = null;
        for (int size = this.arrayList.size() - 1; size >= 0; size--) {
            PresetEnumSettingHelper presetEnumSettingHelper2 = this.arrayList.get(size);
            if (presetEnumSettingHelper2.is1973(d3, d4) && i == 0 && presetEnumSettingHelper == null) {
                presetEnumSettingHelper = presetEnumSettingHelper2;
                presetEnumSettingHelper2.do1969(true);
                presetEnumSettingHelper2.num3 = (int) (d3 - presetEnumSettingHelper2.getX());
                presetEnumSettingHelper2.num4 = (int) (d4 - presetEnumSettingHelper2.getY());
            }
            presetEnumSettingHelper2.do20(d3, d4, i);
        }
        if (presetEnumSettingHelper != null) {
            this.arrayList.remove(presetEnumSettingHelper);
            this.arrayList.add(presetEnumSettingHelper);
        }
        reset();
        do941(d3, d4, i);
        return mouseClicked;
    }

    public boolean mouseReleased(double d, double d2, int i) {
        double d3 = d / SearchHelper4.get1481(this.val);
        double d4 = d2 / SearchHelper4.get1481(this.val);
        for (int size = this.arrayList.size() - 1; size >= 0; size--) {
            PresetEnumSettingHelper presetEnumSettingHelper = this.arrayList.get(size);
            presetEnumSettingHelper.do1969(false);
            presetEnumSettingHelper.do88(d3, d4, i);
        }
        do942(d3, d4, i);
        return super.mouseReleased(d3, d4, i);
    }

    public boolean mouseScrolled(double d, double d2, double d3, double d4) {
        double d5 = SearchHelper4.get1481(this.val);
        double d6 = d / d5;
        double d7 = d2 / d5;
        int i = 2 + FontsSearchHelper4.fontsSearchHelper4.get93() + UI.uI.buttonHeight.getValue().intValue();
        int orElse = this.arrayList.stream().mapToInt((v0) -> {
            return v0.get93();
        }).max().orElse(i);
        int scaledHeight = ((double) orElse) > ((double) (minecraftClient.getWindow().getScaledHeight() + 5)) / d5 ? orElse - ((int) ((minecraftClient.getWindow().getScaledHeight() - 5) / d5)) : (int) (-5.0 / d5);
        if (d4 != 0.0d) {
            int i2 = (int) (d4 * i);
            Iterator<PresetEnumSettingHelper> it = this.arrayList.iterator();
            while (it.hasNext()) {
                PresetEnumSettingHelper next = it.next();
                if (!next.is1974()) {
                    next.do654(d6, d7, d4);
                    int y = next.getY() + i2;
                    if (is205()) {
                        y = MathHelper.clamp(y, -scaledHeight, 5);
                    }
                    next.setY(y);
                }
            }
        }
        do2826(d6, d7, d4);
        return super.mouseScrolled(d6, d7, d3, d4);
    }

    public boolean charTyped(char c, int i) {
        for (int size = this.arrayList.size() - 1; size >= 0; size--) {
            this.arrayList.get(size).do90(c);
        }
        return super.charTyped(c, i);
    }

    public void tick() {
        if (!this.flag2 || System.currentTimeMillis() < this.num3 + 150) {
            return;
        }
        Iterator<PresetEnumSettingHelper> it = this.arrayList.iterator();
        while (it.hasNext()) {
            it.next().do1969(false);
        }
        if (this.screen != null) {
            minecraftClient.setScreen(this.screen);
        } else {
            close();
        }
        do2836(null);
    }

    public boolean keyPressed(int i, int i2, int i3) {
        this.flag3 = true;
        for (int size = this.arrayList.size() - 1; size >= 0; size--) {
            this.arrayList.get(size).do89(i);
        }
        if (i != 256 || !this.flag3) {
            return false;
        }
        do1714();
        return false;
    }

    public boolean shouldPause() {
        return false;
    }

    public void do940(DrawContext drawContext, int i, int i2, float f) {
    }

    public void do941(double d, double d2, int i) {
    }

    public void do942(double d, double d2, int i) {
    }

    public void do2826(double d, double d2, double d3) {
    }

    public void close() {
        super.close();
        Mode_5.STANDARD.do935();
    }

    public void reset() {
        ArrayListPresetHelper2.do655();
        this.num2 = 0L;
    }

    public void do2827() {
        this.list.clear();
        for (int i = 0; i < 25; i++) {
            this.list.add(new Helper_20());
        }
    }

    public float get189() {
        float currentTimeMillis = ((float) (System.currentTimeMillis() - this.num3)) / 150.0f;
        if (this.flag2) {
            currentTimeMillis = 1.0f - currentTimeMillis;
        }
        return MathHelper.clamp(currentTimeMillis, 0.0f, 1.0f);
    }

    public void do2828() {
        do2827();
        this.arrayList.sort(Comparator.comparing(presetEnumSettingHelper -> {
            if (presetEnumSettingHelper instanceof CategorySearchHelper4) {
                return Integer.valueOf(((CategorySearchHelper4) presetEnumSettingHelper).category.ordinal());
            }
            return 100;
        }));
        int i = 10;
        Iterator<PresetEnumSettingHelper> it = this.arrayList.iterator();
        while (it.hasNext()) {
            PresetEnumSettingHelper next = it.next();
            next.setX(i);
            i += next.get1635() + 3;
        }
    }

    public void do204(DrawContext drawContext) {
        drawContext.fill(0, 0, minecraftClient.getWindow().getScaledWidth(), minecraftClient.getWindow().getScaledHeight(), UI.uI.tint.getValue().hashCode());
        drawContext.fillGradient(0, 0, minecraftClient.getWindow().getScaledWidth(), minecraftClient.getWindow().getScaledHeight(), MixinMessageIndicatorHelper_2.get818(UI.uI.gradientColor.getValue(), 0), UI.uI.gradientColor.getValue().hashCode());
    }

    public void do1714() {
        if (this.flag2) {
            close();
        } else {
            this.flag = false;
            this.num3 = System.currentTimeMillis();
            if (UI.uI.isToggled()) {
                UI.uI.do496();
            }
        }
        this.flag2 = true;
    }

    public void do2829() {
        this.num2 = System.currentTimeMillis();
    }

    public void do2830(String str) {
        this.string = str;
    }

    public ArrayList<PresetEnumSettingHelper> getArrayList2831() {
        return this.arrayList;
    }

    public boolean is2832() {
        return this.flag;
    }

    public void do2833(boolean z) {
        this.flag = z;
    }

    public boolean is2834() {
        return this.flag2;
    }

    public void do2835() {
        Screen screen = minecraftClient.currentScreen;
        if (screen instanceof FontsSearchHelper4_2) {
            do2836(((FontsSearchHelper4_2) screen).screen);
        } else {
            do2836(minecraftClient.currentScreen);
        }
        minecraftClient.setScreen(this);
    }

    public boolean is205() {
        return true;
    }

    public void do2836(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen1715() {
        return this.screen;
    }

    public void do2837() {
        this.flag3 = false;
    }
}
