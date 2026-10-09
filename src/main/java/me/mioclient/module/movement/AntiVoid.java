package me.mioclient.module.movement;

import me.mioclient.EnumSettingHelper;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockStateRaycastContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/AntiVoid.class */
public class AntiVoid extends Module {
    public Setting<AntiVoidMode> mode;
    public Setting<Integer> height;
    public Setting<Boolean> forceGround;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/AntiVoid$AntiVoidMode.class */
    public enum AntiVoidMode implements EnumSettingHelper {
        TELEPORT("Teleport"),
        CANCEL("Cancel");

        public final String name;

        AntiVoidMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public AntiVoid() {
        super("AntiVoid", "Prevents you from falling into the void.", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
        setDrawn(false);
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return FontsSearchHelper4.getString1684(this.mode.getValue());
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (minecraftClient.player.isOnGround() || minecraftClient.player.fallDistance <= 0.0f) {
            return;
        }
        if (minecraftClient.world.raycast(new BlockStateRaycastContext(minecraftClient.player.getPos(), new Vec3d(minecraftClient.player.getX(), minecraftClient.world.getBottomY() - 1, minecraftClient.player.getZ()), blockState -> {
            return blockState.isSolid();
        })).getType() == HitResult.Type.MISS && minecraftClient.world.getBottomY() + this.height.getValue().intValue() >= minecraftClient.player.getY()) {
            if (this.mode.getValue() == AntiVoidMode.TELEPORT) {
                minecraftClient.player.setVelocity(0.0d, 10.0, 0.0d);
            } else {
                minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, 0.0d));
                if (this.forceGround.getValue().booleanValue()) {
                    minecraftClient.player.setOnGround(true);
                }
            }
        }
    }
}
