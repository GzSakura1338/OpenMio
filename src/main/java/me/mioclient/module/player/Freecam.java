package me.mioclient.module.player;

import me.mioclient.BaritoneHelper_3;
import me.mioclient.FreecamHelper;
import me.mioclient.FreecamSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.KeyPearlMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.InteractBlockEvent;
import me.mioclient.event.InteractItemEvent_2;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickEvent_2;
import me.mioclient.event.TickHookPreEvent;
import me.mioclient.event.UpdateMouseEvent;
import me.mioclient.event.UpdateSetPosEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.Module;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.option.Perspective;
import net.minecraft.item.BucketItem;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/player/Freecam.class */
public class Freecam extends Module {
    public final FreecamSearchHelper4 freecamSearchHelper4;
    public Setting<Float> speed;
    public Setting<Float> verticalSpeed;
    public Setting<Boolean> noHands;
    public Setting<Boolean> rotate;
    public Setting<Boolean> always;
    public Vec3d vec3d;
    public Vec3d vec3d2;
    public float[] floatArr;
    public Perspective perspective;
    public float val;
    public float val2;
    public float val3;
    public float val4;

    public Freecam() {
        super("Freecam", "Allows your camera to fly through walls.", Category.PLAYER, new String[0]);
        this.freecamSearchHelper4 = new FreecamSearchHelper4();
        PhaseESPHelper.do1351(this);
        this.floatArr = null;
        this.speed.do2351();
        this.verticalSpeed.do2351();
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        if (is1469()) {
            disable();
            return;
        }
        this.perspective = minecraftClient.options.getPerspective();
        new TickEvent_2(minecraftClient.player.input, 0.0f).reset();
        Vec3d pos = minecraftClient.gameRenderer.getCamera().getPos();
        this.vec3d = pos;
        this.vec3d2 = pos;
        this.floatArr = new float[]{minecraftClient.player.getYaw(), minecraftClient.player.getPitch()};
        float f = this.floatArr[0];
        this.val = f;
        this.val2 = f;
        float f2 = this.floatArr[1];
        this.val3 = f2;
        this.val4 = f2;
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        if (is1469()) {
            return;
        }
        minecraftClient.player.setYaw(this.floatArr[0]);
        minecraftClient.player.setPitch(this.floatArr[1]);
        minecraftClient.options.setPerspective(this.perspective);
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (minecraftClient.currentScreen instanceof DownloadingTerrainScreen) {
            disable();
        }
        minecraftClient.options.setPerspective(Perspective.FIRST_PERSON);
        this.vec3d2 = this.vec3d;
        double[] doubleArray2508 = HoleSnapSearchHelper4_3.getDoubleArray2508(this.val, this.freecamSearchHelper4, this.speed.getValue().floatValue());
        this.vec3d = this.vec3d.add(doubleArray2508[0], 0.0d, doubleArray2508[1]);
        if (minecraftClient.options.jumpKey.isPressed()) {
            this.vec3d = this.vec3d.add(0.0d, this.verticalSpeed.getValue().floatValue(), 0.0d);
        }
        if (minecraftClient.options.sneakKey.isPressed()) {
            this.vec3d = this.vec3d.subtract(0.0d, this.verticalSpeed.getValue().floatValue(), 0.0d);
        }
        this.freecamSearchHelper4.tick(false, 1.0f);
    }

    @Listen
    public void onTickHookPre(TickHookPreEvent tickHookPreEvent) {
        tickHookPreEvent.do1162();
    }

    @Listen(get219= 1000)
    public void do31(MotionEvent motionEvent) {
        boolean isBreakingBlock = minecraftClient.interactionManager.isBreakingBlock();
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Post || !this.rotate.getValue().booleanValue() || minecraftClient.crosshairTarget == null) {
            return;
        }
        if (!SearchHelper4_8.is724() || isBreakingBlock || this.always.getValue().booleanValue()) {
            motionEvent.do2257(SearchHelper4_8.getFloatArray2484(minecraftClient.crosshairTarget.getPos()));
        }
    }

    @Listen
    public void onInteractBlock(InteractBlockEvent interactBlockEvent) {
        if ((minecraftClient.player.getStackInHand(interactBlockEvent.getHand2084()).getItem() instanceof BucketItem) || (this.rotate.getValue().booleanValue() && SearchHelper4_8.is724())) {
            BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2484(interactBlockEvent.getBlockHitResult2585().getPos()), 1000);
        }
    }

    @Listen
    public void onInteractItem(InteractItemEvent_2 interactItemEvent_2) {
        if (!this.rotate.getValue().booleanValue() || minecraftClient.crosshairTarget == null || this.always.getValue().booleanValue()) {
            return;
        }
        interactItemEvent_2.do1818(SearchHelper4_8.getFloatArray2484(minecraftClient.crosshairTarget.getPos()));
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (channelRead0Event.getPacket904() instanceof DisconnectS2CPacket) {
            disable();
        }
    }

    @Listen
    public void onUpdateMouse(UpdateMouseEvent updateMouseEvent) {
        do173(updateMouseEvent.get1008() * 0.15000000596046448, updateMouseEvent.get1009() * 0.15000000596046448);
        updateMouseEvent.do1162();
    }

    @Listen
    public void onUpdateSetPos(UpdateSetPosEvent updateSetPosEvent) {
        float tickDelta = minecraftClient.getRenderTickCounter().getTickDelta(true);
        updateSetPosEvent.do1302(new Vec3d(get174(tickDelta), get175(tickDelta), get176(tickDelta)));
        updateSetPosEvent.do1162();
    }

    public void do173(double d, double d2) {
        this.val2 = this.val;
        this.val4 = this.val3;
        this.val = (float) (this.val + d);
        this.val3 = (float) (this.val3 + d2);
        this.val3 = MathHelper.clamp(this.val3, -FreecamHelper.num2, FreecamHelper.num2);
    }

    public double get174(float f) {
        return MathHelper.lerp(f, this.vec3d2.x, this.vec3d.x);
    }

    public double get175(float f) {
        return MathHelper.lerp(f, this.vec3d2.y, this.vec3d.y);
    }

    public double get176(float f) {
        return MathHelper.lerp(f, this.vec3d2.z, this.vec3d.z);
    }

    public double get177(float f) {
        return minecraftClient.currentScreen != null ? this.val : MathHelper.lerp(f, this.val2, this.val);
    }

    public double get178(float f) {
        return minecraftClient.currentScreen != null ? this.val3 : MathHelper.lerp(f, this.val4, this.val3);
    }

    public boolean is179() {
        return isToggled() && this.noHands.getValue().booleanValue();
    }
}
