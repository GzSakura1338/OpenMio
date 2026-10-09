package me.mioclient.module.combat;

import me.mioclient.AntiCheatVelocityHelper;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.LegacyCrystalSearchHelper4;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/Criticals.class */
public class Criticals extends Module {
    public static final Aura aura = (Aura) BaritoneHelper_3.baritoneHelper_4.getModule117(Aura.class);
    public Setting<CriticalsMode> mode;
    public Setting<Boolean> pauseOnMove;

    /* JADX INFO: Access modifiers changed from: protected */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/Criticals$CriticalsMode.class */
    public enum CriticalsMode implements EnumSettingHelper {
        PACKET("Packet"),
        NCP("NCP"),
        GRIM("Grim"),
        GRIMV3("2b2t");

        public final String name;

        CriticalsMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public Criticals() {
        super("Criticals", "Turns your hits into critical ones.", Category.COMBAT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.pauseOnMove.do2343(bool -> {
            return this.mode.getValue() != CriticalsMode.GRIMV3;
        });
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return this.mode.getValue().getName();
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        Entity entity2610;
        PlayerInteractEntityC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof PlayerInteractEntityC2SPacket ? (PlayerInteractEntityC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if (packet904 instanceof PlayerInteractEntityC2SPacket) {
            PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket = packet904;
            if (!is909(playerInteractEntityC2SPacket) || (entity2610 = LegacyCrystalSearchHelper4.getEntity2610(playerInteractEntityC2SPacket)) == null) {
                return;
            }
            if (!sendImmediatelyEvent.is2403()) {
                do906();
            }
            minecraftClient.execute(() -> {
                minecraftClient.player.addCritParticles(entity2610);
            });
        }
    }

    public void do906() {
        float[] fArr = {minecraftClient.player.getYaw(), minecraftClient.player.getPitch()};
        if (aura.entity != null && aura.isToggled()) {
            fArr = SearchHelper4_8.getFloatArray2487(SearchHelper4_8.getFloatArray2483(aura.entity), BaritoneHelper_3.searchHelper4_8.get2474());
        }
        switch (this.mode.getValue().ordinal()) {
            case 1:
                if (HoleSnapSearchHelper4.is2005(minecraftClient.player)) {
                    do908(0.0625101, 0.0d, 0.0125, 0.0d);
                    return;
                } else {
                    do908(0.11, 0.1100013579);
                    return;
                }
            case 2:
                AutoSignSearchHelper4.do2563(minecraftClient.player.getX(), minecraftClient.player.getY() - 1e-06, minecraftClient.player.getZ(), fArr[0], fArr[1], false);
                return;
            case 3:
                float clamp = Math.clamp(fArr[1], -90.0f + AntiCheat.val, 90.0f - AntiCheat.val);
                AutoSignSearchHelper4.do2563(minecraftClient.player.getX(), minecraftClient.player.getY() + 0.0626, minecraftClient.player.getZ(), fArr[0], clamp + AntiCheat.val, false);
                AutoSignSearchHelper4.do2563(minecraftClient.player.getX(), minecraftClient.player.getY() + 0.0455, minecraftClient.player.getZ(), fArr[0], clamp - AntiCheat.val, false);
                return;
            default:
                do908(0.0625101, 0.0d);
                return;
        }
    }

    public boolean is907() {
        if ((this.pauseOnMove.getValue().booleanValue() || this.mode.getValue() == CriticalsMode.GRIMV3) && HoleSnapSearchHelper4_3.is2181()) {
            return false;
        }
        if (this.mode.getValue() == CriticalsMode.GRIMV3) {
            return minecraftClient.player.isOnGround() && AntiCheatVelocityHelper.is1884();
        }
        if (this.mode.getValue() != CriticalsMode.GRIM || minecraftClient.player.isOnGround()) {
            return minecraftClient.player.isOnGround() && minecraftClient.player.verticalCollision && !minecraftClient.player.isInLava() && !minecraftClient.player.isTouchingWater();
        }
        return true;
    }

    public void do908(double... dArr) {
        for (double d : dArr) {
            AutoSignSearchHelper4.do2571(new PlayerMoveC2SPacket.PositionAndOnGround(minecraftClient.player.getX(), minecraftClient.player.getY() + d, minecraftClient.player.getZ(), false));
        }
    }

    public boolean is909(PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket) {
        if (LegacyCrystalSearchHelper4.getLegacyCrystalMode2611(playerInteractEntityC2SPacket) != LegacyCrystalSearchHelper4.LegacyCrystalMode.ATTACK || !is907()) {
            return false;
        }
        Entity entity2610 = LegacyCrystalSearchHelper4.getEntity2610(playerInteractEntityC2SPacket);
        return ((aura.isToggled() && aura.flag2 && this.mode.getValue() != CriticalsMode.GRIM) || entity2610 == null || !entity2610.isAlive() || (entity2610 instanceof EndCrystalEntity)) ? false : true;
    }
}
