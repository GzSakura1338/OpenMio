package me.mioclient.module.client;

import me.mioclient.AntiCheatVelocityHelper;
import me.mioclient.AutoCrystalMode_6;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BaritoneSearchHelper4;
import me.mioclient.FreecamHelper;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.KeyPearlMode;
import me.mioclient.KeybindModule;
import me.mioclient.Mode_6;
import me.mioclient.PhaseESPHelper;
import me.mioclient.VelocityHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.movement.Velocity;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/client/AntiCheat.class */
public class AntiCheat extends KeybindModule {
    public static final Velocity velocity = (Velocity) BaritoneHelper_3.baritoneHelper_4.getModule117(Velocity.class);
    public static final float val = (float) (((Math.pow(0.20000000298023224, 3.0) * 8.0) * 0.15) - 0.001);
    public Setting<Boolean> f2b2t;
    public Setting<Float> yawStep;
    public Setting<Boolean> movementSync;
    public Setting<AutoCrystalMode_6> rotations;
    public Setting<Mode_6> strictDirection;
    public final BaritoneSearchHelper4 baritoneSearchHelper4;
    public boolean flag;
    public boolean flag2;

    public AntiCheat() {
        super("AntiCheat", "Manages the client's behavior on different anti-cheats.", Category.CLIENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.baritoneSearchHelper4 = new BaritoneSearchHelper4(this);
        baritoneHelper.do1796(this);
    }

    @Listen
    public void do32(TickPostEvent tickPostEvent) {
        this.baritoneSearchHelper4.do1626(false);
    }

    @Listen
    public void do31(MotionEvent motionEvent) {
        this.flag2 = motionEvent.getKeyPearlMode1472() == KeyPearlMode.Pre;
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Pre && this.movementSync.getValue().booleanValue()) {
            if ((is238() && (minecraftClient.currentScreen instanceof HandledScreen) && !this.baritoneSearchHelper4.is1625()) || HoleSnapSearchHelper4.is955()) {
                return;
            }
            if (velocity.isToggled()) {
                VelocityHelper velocityHelper1894 = velocity.getVelocityHelper1894();
                if ((velocityHelper1894 instanceof AntiCheatVelocityHelper) && ((AntiCheatVelocityHelper) velocityHelper1894).is951()) {
                    return;
                }
            }
            float intBitsToFloat = val * 2.0f * (this.flag ? -1 : 1);
            this.flag = !this.flag;
            motionEvent.setPitch(MathHelper.clamp(motionEvent.get752() + intBitsToFloat, -FreecamHelper.num2, FreecamHelper.num2));
        }
    }

    @Listen(get219= 999)
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        PlayerActionC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof PlayerActionC2SPacket ? (PlayerActionC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if (packet904 instanceof PlayerActionC2SPacket) {
            PlayerActionC2SPacket playerActionC2SPacket = packet904;
            if (is238() && playerActionC2SPacket.getAction() == PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) {
                AutoSignSearchHelper4.do2565(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, playerActionC2SPacket.getPos(), playerActionC2SPacket.getDirection());
            }
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (is238()) {
            CloseScreenS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof CloseScreenS2CPacket ? (CloseScreenS2CPacket) (channelRead0Event.getPacket904()) : null;
            if ((packet904 instanceof CloseScreenS2CPacket) && packet904.getSyncId() == 0) {
                channelRead0Event.do1162();
            }
        }
    }

    public float get237() {
        return this.yawStep.getValue().floatValue();
    }

    public boolean is238() {
        return this.f2b2t.getValue().booleanValue();
    }

    public BaritoneSearchHelper4 getBaritoneSearchHelper4239() {
        return this.baritoneSearchHelper4;
    }
}
