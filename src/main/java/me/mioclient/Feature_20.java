package me.mioclient;

import com.mojang.brigadier.arguments.StringArgumentType;
import me.mioclient.MixinMessageIndicatorHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/Feature_20.class */
public final class Feature_20 extends Feature {
    public Feature_20() {
        super("print");
    }

    @Override // me.mioclient.Feature
    public void exec(com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(Feature.argument("message", StringArgumentType.greedyString()).executes(commandContext -> {
            minecraftClient.inGameHud.getChatHud().addMessage(Text.of((String) commandContext.getArgument("message", String.class)), MixinMessageIndicatorHelper.getMessageSignatureData337((int) (Math.random() * 10000.0)), MixinMessageIndicatorHelper.messageIndicator);
            return 1;
        })).executes(commandContext2 -> {
            MixinMessageIndicatorHelper.do345(Text.literal("Please enter a message to print."), MixinMessageIndicatorHelper.getMessageSignatureData337(-1), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode3);
            return 1;
        });
    }
}
