package me.mioclient;

import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.mixin.ducks.DuckPlayerMoveC2SPacket;
import me.mioclient.module.client.AntiCheat;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/BaritoneSearchHelper4.class */
public final class BaritoneSearchHelper4 implements SearchHelper_4 {
    public final AntiCheat antiCheat;
    public boolean flag;

    public BaritoneSearchHelper4(AntiCheat antiCheat) {
        this.antiCheat = antiCheat;
    }

    public void do711() {
        if (is328()) {
            if (this.antiCheat.rotations.getValue() == AutoCrystalMode_6.SILENT) {
                AutoSignSearchHelper4.do2563(minecraftClient.player.getX(), minecraftClient.player.getY(), minecraftClient.player.getZ(), minecraftClient.player.getYaw(), minecraftClient.player.getPitch(), minecraftClient.player.isOnGround());
            }
            do1626(true);
        }
    }

    public void do1623(SendImmediatelyEvent sendImmediatelyEvent) {
        DuckPlayerMoveC2SPacket packet904 = (DuckPlayerMoveC2SPacket)(sendImmediatelyEvent.getPacket904());
        if (packet904 instanceof PlayerMoveC2SPacket) {
            DuckPlayerMoveC2SPacket duckPlayerMoveC2SPacket = (DuckPlayerMoveC2SPacket)((PlayerMoveC2SPacket) packet904);
            if (is1624((PlayerMoveC2SPacket) duckPlayerMoveC2SPacket)) {
                duckPlayerMoveC2SPacket.setYaw(((PlayerMoveC2SPacket) duckPlayerMoveC2SPacket).getYaw(0.0f) + 35999997952.0f);
            }
        }
    }

    public boolean is328() {
        return is1624((PlayerMoveC2SPacket) null);
    }

    public boolean is1624(PlayerMoveC2SPacket playerMoveC2SPacket) {
        if ((!HoleSnapSearchHelper4_3.is2181() && BaritoneHelper_3.feetPlaceSearchHelper4.get2634() < 5.0) || !this.antiCheat.f2b2t.getValue().booleanValue() || HoleSnapSearchHelper4.is955()) {
            return false;
        }
        if (playerMoveC2SPacket != null) {
            boolean z = (minecraftClient.currentScreen instanceof HandledScreen) && minecraftClient.player.currentScreenHandler == minecraftClient.player.playerScreenHandler;
            if (!playerMoveC2SPacket.changesLook() && !z) {
                return false;
            }
        }
        return this.antiCheat.flag2 || playerMoveC2SPacket == null;
    }

    public boolean is1625() {
        return this.flag;
    }

    public void do1626(boolean z) {
        this.flag = z;
    }
}
