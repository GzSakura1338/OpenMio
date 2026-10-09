package me.mioclient.module.misc;

import me.mioclient.api.Category;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/misc/Heaven.class */
public class Heaven extends Module {
    public Heaven() {
        super("Heaven", "Brings you to heaven after you die.", Category.MISC, new String[0]);
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (minecraftClient.currentScreen instanceof DeathScreen) {
            if (minecraftClient.player.verticalCollision) {
                minecraftClient.player.setBoundingBox(new Box(0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d));
            }
            ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
            Vec3d velocity = minecraftClient.player.getVelocity();
            clientPlayerEntity.setVelocity(velocity.withAxis(Direction.Axis.Y, 3.0));
        }
    }
}
