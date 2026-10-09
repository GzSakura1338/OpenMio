package me.mioclient.module.movement;

import me.mioclient.BaritoneHelper_3;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_7;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import me.mioclient.module.player.SpeedMine;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/FastSwim.class */
public class FastSwim extends Module {
    public static SpeedMine speedmine = (SpeedMine) BaritoneHelper_3.baritoneHelper_4.getModule117(SpeedMine.class);
    public Setting<Boolean> water;
    public Setting<Float> speed;
    public Setting<Boolean> lava;
    public Setting<Float> speed2;
    public Setting<Boolean> vertical;
    public Setting<Float> vSpeed;
    public Setting<Float> glide;
    public Setting<Boolean> accelerate;
    public Setting<Float> accelMin;
    public Setting<Float> accelTime;
    public long num;
    public boolean flag;

    public FastSwim() {
        super("FastSwim", "Makes you move faster while in liquids.", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.speed.do2329("WaterSpeed");
        this.speed2.do2329("LavaSpeed");
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (((minecraftClient.player.isInLava() && this.lava.getValue().booleanValue()) || (HoleSnapSearchHelper4.is2006(minecraftClient.player) && this.water.getValue().booleanValue())) && !minecraftClient.player.isFallFlying() && minecraftClient.player.isOnGround()) {
            minecraftClient.player.horizontalSpeed = 0.0f;
        }
    }

    @Listen
    public void do242(MoveEvent moveEvent) {
        boolean z = speedmine.isToggled() && ((double) speedmine.get1052()) + speedmine.get1046() >= ((double) speedmine.damage.getValue().floatValue()) - 0.1 && speedmine.getBlockPos1051() != null && SearchHelper4_7.is2435(speedmine.getBlockPos1051());
        if (!((minecraftClient.player.isInLava() && this.lava.getValue().booleanValue()) || (HoleSnapSearchHelper4.is2006(minecraftClient.player) && this.water.getValue().booleanValue())) || minecraftClient.player.isFallFlying() || !BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(500L) || z) {
            this.num = System.currentTimeMillis();
            return;
        }
        if (!HoleSnapSearchHelper4_3.is2181()) {
            this.num = System.currentTimeMillis();
        }
        float intBitsToFloat = this.vertical.getValue().booleanValue() ? 0.11999999731779099f * this.vSpeed.getValue().floatValue() : 0.0f;
        float intBitsToFloat2 = 0.11999999731779099f * this.accelMin.getValue().floatValue();
        double intBitsToFloat3 = 0.11999999731779099f * this.speed2.getValue().floatValue();
        if (this.water.getValue().booleanValue() && HoleSnapSearchHelper4.is2006(minecraftClient.player)) {
            intBitsToFloat3 = 0.11999999731779099f * this.speed.getValue().floatValue();
        }
        if (this.accelerate.getValue().booleanValue() && intBitsToFloat2 < intBitsToFloat3) {
            intBitsToFloat3 = Math.min(intBitsToFloat2 + ((intBitsToFloat3 - intBitsToFloat2) * MathHelper.clamp(((float) (System.currentTimeMillis() - this.num)) / (this.accelTime.getValue().floatValue() * 1000.0f), 0.0f, 1.0f)), intBitsToFloat3);
        }
        if (minecraftClient.player.input.jumping && !this.flag) {
            moveEvent.setY(intBitsToFloat);
            minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, intBitsToFloat));
        } else if (minecraftClient.player.input.sneaking) {
            moveEvent.setY(-intBitsToFloat);
            minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, -intBitsToFloat));
            intBitsToFloat3 /= 2.0;
        } else {
            moveEvent.setY(0.0d);
            minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().withAxis(Direction.Axis.Y, 0.0d));
            if (!minecraftClient.player.verticalCollision && this.glide.getValue().floatValue() != 0.0f) {
                minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().add(0.0d, -this.glide.getValue().floatValue(), 0.0d));
                moveEvent.setY(minecraftClient.player.getVelocity().y);
            }
        }
        double[] doubleArray2507 = HoleSnapSearchHelper4_3.getDoubleArray2507(moveEvent, intBitsToFloat3);
        minecraftClient.player.setVelocity(doubleArray2507[0], minecraftClient.player.getVelocity().y, doubleArray2507[1]);
    }
}
