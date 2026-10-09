package me.mioclient.module.movement;

import java.util.concurrent.TimeUnit;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapData;
import me.mioclient.HoleSnapData_2;
import me.mioclient.HoleSnapEvent;
import me.mioclient.HoleSnapHelper;
import me.mioclient.HoleSnapHelper_2;
import me.mioclient.HoleSnapMode;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.HoleSnapSearchHelper4_6;
import me.mioclient.HoleSnapSearchHelper4_7;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent;
import me.mioclient.event.TickEvent_2;
import me.mioclient.feature.Event_3;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/HoleSnap.class */
public class HoleSnap extends Module {
    public Setting<Boolean> autoDisable;
    public Setting<Boolean> directional;
    public Setting<Boolean> shift;
    public Setting<Boolean> pauseStep;
    public Setting<Float> height;
    public Setting<Float> timeout;
    public Setting<Float> range;
    public Setting<Float> speed;
    public Setting<Float> pitch;
    public final HoleSnapSearchHelper4_7 holeSnapSearchHelper4_7;
    public final Stopwatch stopwatch;
    public final Stopwatch stopwatch2;
    public boolean flag;
    public boolean flag2;

    public HoleSnap() {
        super("HoleSnap", "Pushes you into holes as you go past them.", Category.MOVEMENT, "holetp", "anchor");
        PhaseESPHelper.do1351(this);
        this.holeSnapSearchHelper4_7 = new HoleSnapSearchHelper4_7(this);
        this.stopwatch = new Stopwatch();
        this.stopwatch2 = new Stopwatch();
        this.flag2 = false;
        this.pitch.getSetting2338("None", HoleSnapMode.MIN);
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        this.stopwatch2.setTime(-1L);
    }

    @Listen
    public void onEvent2(HoleSnapEvent holeSnapEvent) {
        boolean is2728 = BaritoneHelper_3.holeSnapSearchHelper4_5.is2728();
        if (minecraftClient.player.isOnGround() && is2728 != this.flag) {
            this.flag = is2728;
            this.stopwatch.reset();
        }
        if (!is130()) {
            if (this.stopwatch2.is418(this.timeout.getValue().floatValue(), TimeUnit.SECONDS)) {
                HoleSnapData_2<Vec3d, Double> holeSnapData_2129 = getHoleSnapData_2129();
                if (holeSnapData_2129 == null) {
                    this.flag2 = false;
                    BaritoneHelper_3.inner.do2017(this);
                    return;
                }
                if (SearchHelper4_8.is724()) {
                    BaritoneHelper_3.searchHelper4_8.do2478(SearchHelper4_8.getFloatArray2484(holeSnapData_2129.getObject3119()), 1337, true);
                    this.flag2 = true;
                }
                HoleSnapSearchHelper4_7.flag = this.holeSnapSearchHelper4_7.is3092();
                if (HoleSnapSearchHelper4_7.flag) {
                    return;
                }
                BaritoneHelper_3.inner.do2017(this);
                return;
            }
        }
        BaritoneHelper_3.inner.do2017(this);
    }

    @Listen
    public void do28(MoveEvent moveEvent) {
        if (is130()) {
            return;
        }
        if (BaritoneHelper_3.holeSnapSearchHelper4_5.is2723(HoleSnapSearchHelper4.getBlockPos1333())) {
            if (this.autoDisable.getValue().booleanValue()) {
                disable();
                this.flag2 = false;
                return;
            }
            this.stopwatch2.reset();
        }
        if (!this.stopwatch2.is418(this.timeout.getValue().floatValue(), TimeUnit.SECONDS) || SearchHelper4_8.is724()) {
            return;
        }
        HoleSnapData_2<Vec3d, Double> holeSnapData_2129 = getHoleSnapData_2129();
        if (holeSnapData_2129 == null) {
            this.flag2 = false;
            return;
        }
        Vec3d multiply = holeSnapData_2129.getObject3119().subtract(minecraftClient.player.getPos()).normalize().multiply(Math.min(HoleSnapSearchHelper4_3.get2511(true) * this.speed.getValue().floatValue(), holeSnapData_2129.getObject3120().doubleValue()));
        moveEvent.do691(multiply.x, multiply.z);
    }

    @Listen(get219= Helper_7.num4)
    public void onTick(TickEvent_2 tickEvent_2) {
        if (!this.flag2 || is130()) {
            return;
        }
        if (this.stopwatch2.is418(this.timeout.getValue().floatValue(), TimeUnit.SECONDS)) {
            tickEvent_2.getInput806().movementForward = tickEvent_2.is808() ? tickEvent_2.get807() : 1.0f;
            tickEvent_2.getInput806().pressingForward = true;
        }
    }

    @Listen
    public void do33(Event_3 event_3) {
        HoleSnapData_2<Vec3d, Double> holeSnapData_2129;
        if (!this.flag2 || is130()) {
            return;
        }
        if (!this.stopwatch2.is418(this.timeout.getValue().floatValue(), TimeUnit.SECONDS) || (holeSnapData_2129 = getHoleSnapData_2129()) == null) {
            return;
        }
        float[] floatArray2484 = SearchHelper4_8.getFloatArray2484(holeSnapData_2129.getObject3119());
        event_3.setYaw(floatArray2484[0]);
        event_3.setPitch(floatArray2484[1]);
        event_3.do1162();
    }

    public HoleSnapData_2<Vec3d, Double> getHoleSnapData_2129() {
        double longBitsToDouble = 9999.0;
        Vec3d vec3d = null;
        Vec3d pos = minecraftClient.player.getPos();
        for (HoleSnapData holeSnapData : BaritoneHelper_3.holeSnapSearchHelper4_5.getList2726()) {
            Vec3d center = holeSnapData.getBox799().getCenter();
            Vec3d withAxis = center.withAxis(Direction.Axis.Y, MathHelper.clamp(pos.getY(), center.y, (center.y + this.height.getValue().floatValue()) - 0.5));
            if (holeSnapData.getBlockPos12().getY() < pos.getY() && !holeSnapData.is2171()) {
                double distanceTo = pos.distanceTo(withAxis);
                if (!this.directional.getValue().booleanValue() || HoleSnapSearchHelper4_3.is2516(withAxis) || distanceTo <= 0.05) {
                    if (distanceTo <= this.range.getValue().floatValue()) {
                        if (minecraftClient.world.isSpaceEmpty(holeSnapData.getBox799().withMaxY(minecraftClient.player.getBoundingBox().maxY)) && !is131(pos, withAxis)) {
                            if (!is131(pos.add(0.0d, 1.7999999523162842, 0.0d), withAxis.add(0.0d, 1.7999999523162842, 0.0d)) && distanceTo < longBitsToDouble) {
                                longBitsToDouble = distanceTo;
                                vec3d = withAxis;
                            }
                        }
                    }
                }
            }
        }
        if (longBitsToDouble > this.range.getValue().floatValue()) {
            return null;
        }
        return new HoleSnapData_2<>(vec3d, Double.valueOf(longBitsToDouble));
    }

    public boolean is130() {
        if (minecraftClient.player.isInSneakingPose()) {
            return true;
        }
        return (minecraftClient.player.getPitch() < this.pitch.getValue().floatValue() && this.pitch.getValue().floatValue() != 0.0f) || !BaritoneHelper_3.holeSnapSearchHelper4_4.getStopwatch2615().is419(250L) || minecraftClient.player.isSpectator() || minecraftClient.player.isFallFlying();
    }

    public boolean is131(Vec3d vec3d, Vec3d vec3d2) {
        return HoleSnapSearchHelper4_6.getBlockHitResult2784(new HoleSnapHelper_2(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, minecraftClient.player, HoleSnapHelper.holeSnapHelper)).getType() != HitResult.Type.MISS;
    }

    public boolean is132() {
        return !this.stopwatch.is419(400L) && BaritoneHelper_3.holeSnapSearchHelper4_5.is2728() && this.pauseStep.getValue().booleanValue();
    }
}
