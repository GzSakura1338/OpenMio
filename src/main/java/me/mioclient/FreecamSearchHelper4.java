package me.mioclient;

import me.mioclient.mixin.ducks.DuckKeyBinding;
import me.mioclient.module.movement.NoSlow;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/FreecamSearchHelper4.class */
public class FreecamSearchHelper4 extends Input implements SearchHelper_4 {
    public static NoSlow noslow = (NoSlow) BaritoneHelper_3.baritoneHelper_4.getModule117(NoSlow.class);

    public void tick(boolean z, float f) {
        this.movementForward = get1537(is1538(minecraftClient.options.forwardKey), is1538(minecraftClient.options.backKey));
        this.movementSideways = get1537(is1538(minecraftClient.options.leftKey), is1538(minecraftClient.options.rightKey));
    }

    public float get1537(boolean z, boolean z2) {
        if (z == z2) {
            return 0.0f;
        }
        return z ? 1.0f : -1.0f;
    }

    public boolean is1538(KeyBinding keyBinding) {
        return (noslow.isToggled() && noslow.is2669() && noslow.guiMove.getValue().booleanValue()) ? GLFW.glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), ((DuckKeyBinding) keyBinding).getKey().getCode()) == 1 : keyBinding.isPressed();
    }
}
