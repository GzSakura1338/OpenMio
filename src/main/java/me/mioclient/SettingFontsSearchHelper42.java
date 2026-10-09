package me.mioclient;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import me.mioclient.api.Setting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SettingFontsSearchHelper42.class */
public class SettingFontsSearchHelper42 extends FontsSearchHelper4_2 {
    public Setting<SearchIdentifier> setting;
    public final Screen screen;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/SettingFontsSearchHelper42$Inner.class */
    public class Inner extends PresetSearchHelper419 {
        public final SearchIdentifier searchIdentifier;

        public Inner(PresetEnumSettingHelper presetEnumSettingHelper, SearchIdentifier searchIdentifier) {
            super(presetEnumSettingHelper, searchIdentifier.getName());
            this.searchIdentifier = searchIdentifier;
        }

        @Override // me.mioclient.PresetSearchHelper419, me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
        public void do19(DrawContext drawContext, MatrixStack matrixStack, double d, double d2) {
            if (SettingFontsSearchHelper42.this.setting.getValue().equals(this.searchIdentifier)) {
                SearchHelper_2.searchHelper_2.do546(matrixStack, this.presetEnumSettingHelper.getX() + 1, this.presetEnumSettingHelper.getY() + this.num + 0.5f, (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 1, ((this.presetEnumSettingHelper.getY() + this.num) + get93()) - 0.5f, getUI1744().color.getValue());
            } else {
                SearchHelper_2.searchHelper_2.do546(matrixStack, this.presetEnumSettingHelper.getX() + 1, this.presetEnumSettingHelper.getY() + this.num + 0.5f, (this.presetEnumSettingHelper.getX() + this.presetEnumSettingHelper.get1635()) - 1, ((this.presetEnumSettingHelper.getY() + this.num) + get93()) - 0.5f, getUI1744().bgButton.getValue());
            }
            super.do19(drawContext, matrixStack, d, d2);
        }

        @Override // me.mioclient.SearchHelper4_19, me.mioclient.PresetHelper_5
        public void do20(double d, double d2, int i) {
            if (is92(d, d2)) {
                if (i == 0) {
                    SettingFontsSearchHelper42.this.setting.do2333(this.searchIdentifier);
                    SettingFontsSearchHelper42.this.do1714();
                } else if (i == 1) {
                    BaritoneHelper_3.searchHelper4_11.do2971(this.searchIdentifier, 1.0f);
                }
            }
        }
    }

    public SettingFontsSearchHelper42(Setting<SearchIdentifier> setting, Screen screen) {
        this.setting = setting;
        this.screen = screen;
        do1716();
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void do1714() {
        minecraftClient.setScreen(this.screen);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public Screen getScreen1715() {
        Screen screen = this.screen;
        return screen instanceof FontsSearchHelper4_2 ? ((FontsSearchHelper4_2) screen).getScreen1715() : this.screen;
    }

    public void filesDragged(List<Path> list) {
        for (Path path : list) {
            if (path.toFile().getName().endsWith(".ogg")) {
                Path resolve = PresetHelper.path8.resolve("custom");
                resolve.toFile().mkdirs();
                try {
                    Files.copy(path, resolve.resolve(path.getFileName()), new CopyOption[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        BaritoneHelper_3.searchHelper4_11.do2973();
        do1716();
    }

    public void do1716() {
        getArrayList2831().clear();
        synchronized (BaritoneHelper_3.searchHelper4_11.getSet2969()) {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            BaritoneHelper_3.searchHelper4_11.getSet2969().stream().map(searchIdentifier -> {
                return searchIdentifier.getString1610();
            }).filter(str -> {
                return !str.isEmpty();
            }).distinct().forEach(str2 -> {
                PresetEnumSettingHelper presetEnumSettingHelper = new PresetEnumSettingHelper(FontsSearchHelper4.getString1684(str2));
                getArrayList2831().add(presetEnumSettingHelper);
                presetEnumSettingHelper.setX(presetEnumSettingHelper.getX() + ((presetEnumSettingHelper.get1635() + 3) * atomicInteger.getAndIncrement()));
            });
            for (SearchIdentifier searchIdentifier2 : BaritoneHelper_3.searchHelper4_11.getSet2969()) {
                Iterator<PresetEnumSettingHelper> it = getArrayList2831().iterator();
                while (true) {
                    if (it.hasNext()) {
                        PresetEnumSettingHelper next = it.next();
                        if (next.getName().equalsIgnoreCase(searchIdentifier2.getString1610())) {
                            // JADX emitted an orphaned lambda here (never assigned/invoked); removed.
                            next.getArrayList1968().add(new Inner(next, searchIdentifier2));
                            break;
                        }
                    }
                }
            }
        }
    }
}
