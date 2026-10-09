package me.mioclient;

import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/Feature_11.class */
public final class Feature_11 extends Feature {
    public Feature_11() {
        super("coords");
    }

    @Override // me.mioclient.Feature
    public void exec(com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(Feature.argument("name", new ExamplesArgumentType_3()).executes(commandContext -> {
            do1387((String) commandContext.getArgument("name", String.class), getString1386());
            return 1;
        })).executes(commandContext2 -> {
            try {
                minecraftClient.keyboard.setClipboard(getString1386());
                MixinMessageIndicatorHelper.do344(Text.literal("Your position has been copied to the clipboard."), MixinMessageIndicatorHelper.getMessageSignatureData337(-1));
                return 1;
            } catch (Exception e) {
                return 1;
            }
        });
    }

    public String getString1386() {
        if (minecraftClient.player == null) {
            return "";
        }
        return new ArgumentTypeHelper().getArgumentTypeHelper2906((int) Math.floor(Math.random() * 10000.0)).getArgumentTypeHelper2919(SearchHelper4_7.getStashFinderMode2438().getString2175()).getArgumentTypeHelper2906((int) minecraftClient.player.getZ()).getArgumentTypeHelper2906((int) minecraftClient.player.getY()).getArgumentTypeHelper2906((int) minecraftClient.player.getX()).getString2921("X: \u0001, Y: \u0001, Z: \u0001 in The \u0001 [\u0001]");
    }

    public void do1387(String str, String str2) {
        minecraftClient.player.networkHandler.sendChatCommand(new ArgumentTypeHelper().getArgumentTypeHelper2919(str2).getArgumentTypeHelper2919(str).getString2921("w \u0001 \u0001"));
    }
}
