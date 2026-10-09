package me.mioclient;

import java.awt.Color;
import me.mioclient.AntiPhaseSearchHelper4;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/Feature_38.class */
public class Feature_38 extends Feature {
    public static final Color color = new Color(255, 0, 0, 120);
    public static final Color color2 = new Color(0, 0, 0, 0);

    public Feature_38() {
        super("highlight");
        BaritoneHelper_3.antiPhaseSearchHelper4.register(new AntiPhaseSearchHelper4.Record(this, () -> {
            return color;
        }, () -> {
            return color2;
        }, () -> {
            return Float.valueOf(1.0f);
        }, () -> {
            return Float.valueOf(5.0f);
        }, () -> {
            return true;
        }, () -> {
            return true;
        }, 1000));
    }

    @Override // me.mioclient.Feature
    public void exec(com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(Feature.argument("pos", new ExamplesArgumentType()).then(Feature.literal("beam").executes(commandContext -> {
            BaritoneHelper_3.antiPhaseSearchHelper4.do2133(this, Box.of((Vec3d) commandContext.getArgument("pos", Vec3d.class), 0.25, 0.25, 0.25).withMaxY(minecraftClient.world.getTopY()).withMinY(minecraftClient.world.getBottomY()));
            return 1;
        })).executes(commandContext2 -> {
            BaritoneHelper_3.antiPhaseSearchHelper4.do2133(this, Box.of((Vec3d) commandContext2.getArgument("pos", Vec3d.class), 0.25, 0.25, 0.25));
            return 1;
        }));
    }
}
