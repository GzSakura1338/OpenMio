package me.mioclient;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import me.mioclient.api.Category;
import me.mioclient.feature.Game;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.client.HUD;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/MixinTitleScreenSearchHelper4.class */
public class MixinTitleScreenSearchHelper4 extends FontsSearchHelper4_2 implements SearchHelper_4 {
    public static final int[] intArr = {265, 265, 264, 264, 263, 262, 263, 262, 66, 65};
    public boolean flag2;
    public TextFieldWidget textFieldWidget;
    public String string;
    public SettingSearchHelper419<?> settingSearchHelper419 = null;
    public int current = 0;
    public final Stopwatch stopwatch = new Stopwatch();
    public List<ArrayListPresetHelper2> list = new ArrayList();
    public boolean flag = PresetHelper.path9.toFile().exists();

    public MixinTitleScreenSearchHelper4() {
        int i = 10;
        for (Category category : Category.values()) {
            if (category != Category.HUD) {
                CategorySearchHelper4 categorySearchHelper4 = new CategorySearchHelper4(category);
                categorySearchHelper4.setX(i);
                this.arrayList.add(categorySearchHelper4);
                i += categorySearchHelper4.get1635() + 3;
            }
        }
        if (this.flag) {
            Game game = new Game();
            game.setX(i);
            game.do1971(false);
            this.arrayList.add(game);
        }
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void init() {
        super.init();
        this.string = "";
        this.textFieldWidget = new TextFieldWidget(minecraftClient.textRenderer, 7, -100, 100, 9, Text.literal("Search"));
        this.textFieldWidget.setVisible(true);
        this.textFieldWidget.setEditable(true);
        this.textFieldWidget.setMaxLength(64);
        this.textFieldWidget.setChangedListener(str -> {
            if (!str.isEmpty()) {
                for (Module module : BaritoneHelper_3.keyPearlSearchHelper4.getRegistry()) {
                    if (!(module instanceof HUD) && !(module instanceof ModuleList) && module.getName().toLowerCase(Locale.ROOT).startsWith(str.toLowerCase(Locale.ROOT))) {
                        this.string = module.getName().substring(this.textFieldWidget.getCursor());
                        return;
                    }
                }
            }
            this.string = "";
        });
        addDrawableChild(this.textFieldWidget);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void do940(DrawContext drawContext, int i, int i2, float f) {
        if (this.list.size() >= 3) {
            ((ArrayListPresetHelper2) this.list.getFirst()).flag = false;
        }
        this.list.removeIf((v0) -> {
            return v0.isClosed();
        });
        if (!this.textFieldWidget.isFocused()) {
            if (BaritoneHelper_3.welcomerHelper.get2811() != 8) {
                FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, "Ctrl + F to activate search.", minecraftClient.getWindow().getScaledWidth() - FontsSearchHelper4.fontsSearchHelper4.get1316("Ctrl + F to activate search."), minecraftClient.getWindow().getScaledHeight() - FontsSearchHelper4.fontsSearchHelper4.get93(), MixinMessageIndicatorHelper_2.getColor814(Color.GRAY, Color.WHITE, 2000.0, 250.0));
                return;
            }
            return;
        }
        FontsSearchHelper4.fontsSearchHelper4.do1691(drawContext, new ArgumentTypeHelper().getArgumentTypeHelper2919(getString95()).getArgumentTypeHelper2919(String.valueOf(Formatting.WHITE)).getArgumentTypeHelper2919(this.string).getArgumentTypeHelper2919(String.valueOf(Formatting.GRAY)).getArgumentTypeHelper2919(this.textFieldWidget.getText()).getString2921("\u0001\u0001\u0001\u0001\u0001"), (minecraftClient.getWindow().getScaledWidth() / 2.0f) - (FontsSearchHelper4.fontsSearchHelper4.get1316(new ArgumentTypeHelper().getArgumentTypeHelper2919(this.string).getArgumentTypeHelper2919(this.textFieldWidget.getText()).getString2921("\u0001\u0001_")) / 2.0f), (minecraftClient.getWindow().getScaledHeight() / 2.0f) - (FontsSearchHelper4.fontsSearchHelper4.get93() / 2.0f), Color.white);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public boolean keyPressed(int i, int i2, int i3) {
        super.keyPressed(i, i2, i3);
        if (this.flag || intArr[this.current] != i) {
            this.current = 0;
        } else {
            this.current++;
            if (this.current == intArr.length) {
                try {
                    PresetHelper.path9.toFile().createNewFile();
                } catch (Exception e) {
                }
                this.flag = true;
                this.current = 0;
                Game game = new Game();
                game.do1971(true);
                game.setX((minecraftClient.getWindow().getScaledWidth() / 2) - (game.get1635() / 2));
                game.setY((minecraftClient.getWindow().getScaledHeight() / 2) - (game.get93() / 2));
                this.arrayList.add(game);
            }
        }
        switch (i) {
            case 70:
                if (GLFW.glfwGetKey(minecraftClient.getWindow().getHandle(), 341) != 1) {
                    return true;
                }
                if (this.textFieldWidget.isFocused()) {
                    reset();
                    return true;
                }
                setInitialFocus(this.textFieldWidget);
                return true;
            case 257:
                Iterator<PresetEnumSettingHelper> it = this.arrayList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Iterator<PresetHelper_5> it2 = it.next().getArrayList1968().iterator();
                        while (it2.hasNext()) {
                            PresetHelper_5 next = it2.next();
                            if (next instanceof ArrayListPresetHelper2) {
                                ArrayListPresetHelper2 arrayListPresetHelper2 = (ArrayListPresetHelper2) next;
                                if (arrayListPresetHelper2.getModule595().getName().equalsIgnoreCase(new ArgumentTypeHelper().getArgumentTypeHelper2919(this.string).getArgumentTypeHelper2919(this.textFieldWidget.getText()).getString2921("\u0001\u0001"))) {
                                    arrayListPresetHelper2.flag = true;
                                    arrayListPresetHelper2.do656();
                                }
                            }
                        }
                    } else {
                        break;
                    }
                }
                reset();
                return true;
            case 258:
                if (!this.textFieldWidget.isFocused()) {
                    return true;
                }
                this.textFieldWidget.setText(new ArgumentTypeHelper().getArgumentTypeHelper2919(this.string).getArgumentTypeHelper2919(this.textFieldWidget.getText()).getString2921("\u0001\u0001"));
                return true;
            case 259:
            case 261:
                if (!this.textFieldWidget.isFocused() || this.textFieldWidget.getText().isEmpty()) {
                    return true;
                }
                this.textFieldWidget.setText(this.textFieldWidget.getText().substring(0, this.textFieldWidget.getText().length() - 1));
                return true;
            default:
                return true;
        }
    }

    public void filesDragged(List<Path> list) {
        me.mioclient.feature.Category.is2716(this, list);
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void reset() {
        super.reset();
        setFocused(null);
        this.textFieldWidget.setFocused(false);
        this.textFieldWidget.setText("");
        this.textFieldWidget.setSuggestion("");
    }

    @Override // me.mioclient.FontsSearchHelper4_2
    public void do1714() {
        super.do1714();
        executorService.submit(() -> {
            BaritoneHelper_3.presetHelper.do41();
        });
    }

    public TextFieldWidget getTextFieldWidget2408() {
        return this.textFieldWidget;
    }

    public String getString95() {
        if (this.stopwatch.is419(500L)) {
            this.flag2 = !this.flag2;
            this.stopwatch.reset();
        }
        return this.flag2 ? "_" : "";
    }

    public SettingSearchHelper419<?> getSettingSearchHelper4192409() {
        return this.settingSearchHelper419;
    }

    public void do2410(SettingSearchHelper419<?> settingSearchHelper419) {
        this.settingSearchHelper419 = settingSearchHelper419;
    }
}
