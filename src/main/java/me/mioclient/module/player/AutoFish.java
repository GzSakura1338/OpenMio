package me.mioclient.module.player;

import java.util.concurrent.TimeUnit;
import me.mioclient.PhaseESPHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/player/AutoFish.class */
public class AutoFish extends Module {
    public Setting<Float> castDelay;
    public final Stopwatch stopwatch;

    public AutoFish() {
        super("AutoFish", "Fishes.", Category.PLAYER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.stopwatch.reset();
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        PlaySoundS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof PlaySoundS2CPacket ? (PlaySoundS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof PlaySoundS2CPacket) {
            PlaySoundS2CPacket playSoundS2CPacket = packet904;
            if (!playSoundS2CPacket.getSound().matches(registryKey -> {
                return registryKey.getValue().toString().equalsIgnoreCase("minecraft:entity.fishing_bobber.splash");
            }) || minecraftClient.player.fishHook == null) {
                return;
            }
            if (new Vec3d(playSoundS2CPacket.getX(), playSoundS2CPacket.getY(), playSoundS2CPacket.getZ()).distanceTo(minecraftClient.player.fishHook.getPos()) <= 4.0) {
                minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
                minecraftClient.player.swingHand(Hand.MAIN_HAND);
                this.stopwatch.reset();
            }
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (minecraftClient.player.getMainHandStack().isOf(Items.FISHING_ROD)) {
            if (!this.stopwatch.is418(1.0, TimeUnit.MINUTES)) {
                if (minecraftClient.player.fishHook != null) {
                    return;
                }
                if (!this.stopwatch.is418(this.castDelay.getValue().floatValue(), TimeUnit.SECONDS)) {
                    return;
                }
            }
            minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
            minecraftClient.player.swingHand(Hand.MAIN_HAND);
            this.stopwatch.reset();
        }
    }
}
