package me.mioclient;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/Feature_29.class */
public final class Feature_29 extends Feature {
    public Feature_29() {
        super("clip");
    }

    @Override // me.mioclient.Feature
    public void exec(com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(Feature.literal("v").then(Feature.argument("value", FloatArgumentType.floatArg()).executes(commandContext -> {
            float clamp = Math.clamp(((Float) commandContext.getArgument("value", Float.class)).floatValue(), -30000000.0f, 30000000.0f);
            int ceil = (int) Math.ceil(Math.abs(clamp / 10.0f));
            if (ceil > 20) {
                ceil = 1;
            }
            if (minecraftClient.player.hasVehicle()) {
                for (int i = 0; i < ceil - 1; i++) {
                    AutoSignSearchHelper4.do2571(new VehicleMoveC2SPacket(minecraftClient.player.getVehicle()));
                }
                minecraftClient.player.getVehicle().setPosition(minecraftClient.player.getVehicle().getX(), minecraftClient.player.getVehicle().getY() + clamp, minecraftClient.player.getVehicle().getZ());
                return 1;
            }
            for (int i2 = 0; i2 < ceil - 1; i2++) {
                AutoSignSearchHelper4.do2571(new PlayerMoveC2SPacket.OnGroundOnly(true));
            }
            minecraftClient.player.setPosition(minecraftClient.player.getX(), minecraftClient.player.getY() + clamp, minecraftClient.player.getZ());
            AutoSignSearchHelper4.do2562(minecraftClient.player.getX(), minecraftClient.player.getY() + clamp, minecraftClient.player.getZ(), true);
            return 1;
        }))).then(Feature.literal("h").then(Feature.argument("value", FloatArgumentType.floatArg()).executes(commandContext2 -> {
            float clamp = Math.clamp(((Float) commandContext2.getArgument("value", Float.class)).floatValue(), -30000000.0f, 30000000.0f);
            double cos = Math.cos(Math.toRadians(minecraftClient.player.getYaw() + FreecamHelper.num2));
            double sin = Math.sin(Math.toRadians(minecraftClient.player.getYaw() + FreecamHelper.num2));
            if (minecraftClient.player.hasVehicle()) {
                minecraftClient.player.getVehicle().setPosition(minecraftClient.player.getVehicle().getX() + (clamp * cos), minecraftClient.player.getVehicle().getY(), minecraftClient.player.getVehicle().getZ() + (clamp * sin));
                return 1;
            }
            minecraftClient.player.setPosition(minecraftClient.player.getX() + (clamp * cos), minecraftClient.player.getY(), minecraftClient.player.getZ() + (clamp * sin));
            AutoSignSearchHelper4.do2562(minecraftClient.player.getX() + (clamp * cos), minecraftClient.player.getY(), minecraftClient.player.getZ() + (clamp * sin), true);
            return 1;
        })));
    }
}
