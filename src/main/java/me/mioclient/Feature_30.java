package me.mioclient;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/Feature_30.class */
public final class Feature_30 extends Feature {
    public Feature_30() {
        super("yaw");
    }

    @Override // me.mioclient.Feature
    public void exec(com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(Feature.argument("yaw", FloatArgumentType.floatArg(-180.0f, 180.0f)).executes(commandContext -> {
            minecraftClient.player.setYaw(((Float) commandContext.getArgument("yaw", Float.class)).floatValue());
            MixinMessageIndicatorHelper.do344(Text.literal(new ArgumentTypeHelper().getArgumentTypeHelper2919(String.valueOf(commandContext.getArgument("yaw", Float.class))).getString2921("Player's yaw has been set to \u0001.")), MixinMessageIndicatorHelper.getMessageSignatureData337(-1));
            return 1;
        }));
    }
}
