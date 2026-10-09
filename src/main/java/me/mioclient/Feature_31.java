package me.mioclient;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import java.util.Iterator;
import java.util.function.BiConsumer;
import me.mioclient.api.Setting;
import me.mioclient.module.Module;
import me.mioclient.module.client.Colors;
import me.mioclient.module.client.UI;
import net.minecraft.command.CommandSource;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/Feature_31.class */
public class Feature_31 extends Feature {
    public Feature_31() {
        super("global");
        do414("globalsync", "syncall");
    }

    @Override // me.mioclient.Feature
    public void exec(com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(Feature.literal("color").then(Feature.argument("value", BoolArgumentType.bool()).executes(commandContext -> {
            do2526((module, setting) -> {
                if ((module instanceof Colors) || (module instanceof UI) || !(setting instanceof ColorSetting)) {
                    return;
                }
                ((ColorSetting) setting).do2860(((Boolean) commandContext.getArgument("value", Boolean.class)).booleanValue());
            });
            return 1;
        })));
        literalArgumentBuilder.then(Feature.literal("linewidth").then(Feature.argument("value", FloatArgumentType.floatArg(0.10000000149011612f, 5.0f)).executes(commandContext2 -> {
            float floatValue = ((Float) commandContext2.getArgument("value", Float.class)).floatValue();
            do2526((module, setting) -> {
                if ((setting.getValue() instanceof Float) && setting.getName().equalsIgnoreCase("LineWidth")) {
                    ((NumberSetting) setting).do2333(Float.valueOf(floatValue));
                }
            });
            return 1;
        })));
    }

    public void do2526(BiConsumer<Module, Setting<?>> biConsumer) {
        for (Module module : BaritoneHelper_3.keyPearlSearchHelper4.getRegistry()) {
            Iterator<Setting<?>> it = module.getRegistry().iterator();
            while (it.hasNext()) {
                biConsumer.accept(module, it.next());
            }
        }
    }
}
