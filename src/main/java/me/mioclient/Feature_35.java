package me.mioclient;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.event.events.ChatEvent;
import baritone.api.event.events.TabCompleteEvent;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.BlockPos;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/Feature_35.class */
public class Feature_35 extends Feature {
    public static final String string = "#";

    public Feature_35() {
        super("baritone");
        do414("b");
    }

    @Override // me.mioclient.Feature
    public void exec(com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(Feature.argument("command", StringArgumentType.greedyString()).suggests((commandContext, suggestionsBuilder) -> {
            IBaritone primaryBaritone = BaritoneAPI.getProvider().getPrimaryBaritone();
            String str = "#";
            try {
                str = new ArgumentTypeHelper().getArgumentTypeHelper2919(suggestionsBuilder.getRemaining()).getArgumentTypeHelper2919(str).getString2921("\u0001\u0001");
            } catch (Exception e) {
            }
            TabCompleteEvent tabCompleteEvent = new TabCompleteEvent(str);
            do2699(() -> {
                primaryBaritone.getGameEventHandler().onPreTabComplete(tabCompleteEvent);
            });
            if (tabCompleteEvent.completions == null || tabCompleteEvent.completions.length == 0) {
                return suggestionsBuilder.buildFuture();
            }
            String input = commandContext.getInput();
            String substring = input.substring(0, Math.min(input.length(), suggestionsBuilder.getStart() + suggestionsBuilder.getRemaining().length()));
            StringRange between = StringRange.between(substring.lastIndexOf(" ") + 1, substring.length());
            return CompletableFuture.completedFuture(new Suggestions(between, (List) Arrays.stream(tabCompleteEvent.completions).map(str2 -> {
                return new Suggestion(between, str2.startsWith("#") ? str2.substring(1) : str2);
            }).collect(Collectors.toList())));
        }).executes(commandContext2 -> {
            BlockPos blockPos2700;
            IBaritone primaryBaritone = BaritoneAPI.getProvider().getPrimaryBaritone();
            String str = (String) commandContext2.getArgument("command", String.class);
            if ("goto".equalsIgnoreCase(str)) {
                BaritoneHelper_3.obstaclePasserHelper.do704(minecraftClient.gameRenderer.getCamera().getBlockPos());
                return 1;
            }
            if ("goto ping".equalsIgnoreCase(str)) {
                BlockPos blockPos27002 = getBlockPos2700();
                if (blockPos27002 != null) {
                    BaritoneHelper_3.obstaclePasserHelper.do704(blockPos27002);
                    return 1;
                }
            } else if ("elytra ping".equalsIgnoreCase(str) && (blockPos2700 = getBlockPos2700()) != null) {
                BaritoneHelper_3.obstaclePasserHelper.do705(blockPos2700.withY(minecraftClient.player.getBlockY()));
                return 1;
            }
            primaryBaritone.getGameEventHandler().onSendChatMessage(new ChatEvent(new ArgumentTypeHelper().getArgumentTypeHelper2919(str).getArgumentTypeHelper2919(IBaritoneChatControl.FORCE_COMMAND_PREFIX).getString2921("\u0001\u0001")));
            return 1;
        }));
    }

    public void do2699(java.lang.Runnable runnable) {
        String str = (String) BaritoneAPI.getSettings().prefix.value;
        BaritoneAPI.getSettings().prefix.value = "#";
        runnable.run();
        BaritoneAPI.getSettings().prefix.value = str;
    }

    public BlockPos getBlockPos2700() {
        synchronized (BaritoneHelper_3.nameTagsSearchHelper4.getList2307()) {
            for (SpawnTimeHelper spawnTimeHelper : BaritoneHelper_3.nameTagsSearchHelper4.getList2307()) {
                if (spawnTimeHelper.is796()) {
                    if (!spawnTimeHelper.getString793().equalsIgnoreCase(minecraftClient.player.getName().getString())) {
                        if (((float) minecraftClient.player.getEyePos().distanceTo(spawnTimeHelper.getBlockPos386().toCenterPos())) < 9999.0f) {
                            return spawnTimeHelper.getBlockPos386();
                        }
                    }
                }
            }
            return null;
        }
    }
}
