package me.mioclient;

import java.util.ArrayDeque;
import java.util.Queue;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Event_3;
import me.mioclient.feature.MotionEvent;
import me.mioclient.mixin.ducks.DuckClientPlayerEntity;
import me.mioclient.mixin.ducks.DuckWorldRenderer;
import me.mioclient.module.client.AntiCheat;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SearchHelper4_8.class */
public final class SearchHelper4_8 implements SearchHelper_4 {
    public static AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static boolean flag;
    public float val;
    public float val2;
    public float val3;
    public float val4;
    public float val5;
    public float val6;
    public float val7;
    public float val8;
    public float[] floatArr;
    public int num;
    public int num2;
    public final Queue<Packet<?>> queue = new ArrayDeque();
    public boolean flag2;
    public boolean flag3;
    public ElytraFlyData elytraFlyData;
    public boolean flag4;

    public SearchHelper4_8() {
        baritoneHelper.do1796(this);
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        this.flag4 = false;
    }

    @Listen
    public void do32(TickPostEvent tickPostEvent) {
        if (is724()) {
            do978();
            do2473();
        }
    }

    @Listen
    public void onJumpFix(Event_3 event_3) {
        boolean z = antiCheat.movementSync.getValue().booleanValue() || this.flag4;
        if (this.elytraFlyData == null || !z) {
            return;
        }
        float[] floatArray218 = this.elytraFlyData.getFloatArray218();
        float f = get2469(floatArray218);
        if (f != floatArray218[0] && f != MathHelper.wrapDegrees(floatArray218[0] - FreecamHelper.num) && f != MathHelper.wrapDegrees(floatArray218[0] + FreecamHelper.num)) {
            minecraftClient.player.setSprinting(false);
        }
        event_3.do1162();
        event_3.setYaw(f);
        event_3.setPitch(floatArray218[1]);
    }

    public static float get2469(float[] fArr) {
        if (minecraftClient.player.isFallFlying()) {
            return fArr[0];
        }
        float f = FreecamHelper.num3;
        float f2 = fArr[0];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= FreecamHelper.num3) {
                return f2;
            }
            float wrapDegrees = MathHelper.wrapDegrees(fArr[0] + i2);
            float angleBetween = MathHelper.angleBetween(wrapDegrees, minecraftClient.player.getYaw());
            if (angleBetween <= f) {
                f2 = wrapDegrees;
                f = angleBetween;
            }
            i = i2 + FreecamHelper.num2;
        }
    }

    @Listen
    public void do31(MotionEvent motionEvent) {
        if (this.num2 > 0 && this.floatArr != null) {
            do2480(this.floatArr);
            this.num2--;
        }
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Post) {
            if (is724()) {
                return;
            }
            do978();
        } else {
            if (!is724()) {
                do2473();
            }
            if (this.elytraFlyData != null) {
                motionEvent.do2257(this.elytraFlyData.getFloatArray218());
                this.elytraFlyData = null;
            }
        }
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if (!this.flag2 || sendImmediatelyEvent.is2403() || this.flag3) {
            return;
        }
        if (sendImmediatelyEvent.getPacket904() instanceof PlayerMoveC2SPacket) {
            sendImmediatelyEvent.do1162();
            AutoSignSearchHelper4.do2573(sendImmediatelyEvent.getPacket904());
            do978();
        } else {
            this.queue.add(sendImmediatelyEvent.getPacket904());
            sendImmediatelyEvent.do1162();
        }
    }

    public boolean is2470(ClientCommandC2SPacket.Mode mode) {
        return mode == ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY || mode == ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY;
    }

    public void do2471(boolean z) {
        this.flag2 = z;
    }

    public boolean is2472() {
        return this.flag2;
    }

    public void do978() {
        long j = 0;
        while (true) {
            if (this.queue.isEmpty()) {
                break;
            }
            j++;
            if (j > 500) {
                System.out.println("anticrash1337");
                break;
            }
            Packet<?> poll = this.queue.poll();
            if (poll != null) {
                this.flag3 = true;
                AutoSignSearchHelper4.do2571(poll);
                this.flag3 = false;
            }
        }
        this.queue.clear();
    }

    public void do2473() {
        this.flag2 = true;
        baritoneHelper.getObject1794(new HoleSnapEvent());
        this.flag2 = false;
        if (this.elytraFlyData == null) {
            do978();
        }
    }

    public float get2474() {
        return antiCheat.get237();
    }

    public ElytraFlyData getElytraFlyData2475() {
        return this.elytraFlyData;
    }

    public void do2476(ElytraFlyData elytraFlyData) {
        if (this.elytraFlyData == null || this.elytraFlyData.get219() <= elytraFlyData.get219()) {
            this.elytraFlyData = elytraFlyData;
        }
    }

    public void do2477(float[] fArr, int i) {
        do2478(fArr, i, false);
    }

    public void do2478(float[] fArr, int i, boolean z) {
        if (antiCheat.is238() && (minecraftClient.currentScreen instanceof HandledScreen)) {
            return;
        }
        if (!is1144() || z) {
            do2476(new ElytraFlyData(fArr, i));
        } else if (this.flag2) {
            do2480(fArr);
            AutoSignSearchHelper4.do2563(minecraftClient.player.getX(), minecraftClient.player.getY(), minecraftClient.player.getZ(), fArr[0], fArr[1], BaritoneHelper_3.antiPhaseSearchHelper4_2.is2228());
        }
    }

    public void do2479() {
        this.flag4 = true;
    }

    public void do2480(float[] fArr) {
        do2481(fArr, 0);
    }

    public void do2481(float[] fArr, int i) {
        this.num = minecraftClient.player.age;
        this.val4 = this.val;
        this.val5 = this.val2;
        this.val6 = this.val3;
        this.val3 = get2503(fArr[0], this.val6);
        this.val7 = this.val8;
        this.val8 = fArr[0];
        this.val = fArr[0];
        this.val2 = fArr[1];
        this.floatArr = fArr;
        if (i > 0) {
            this.num2 = i;
        }
    }

    public static float[] getFloatArray2482(BlockPos blockPos) {
        return getFloatArray2484(new Vec3d(blockPos.getX() + FreecamHelper.val2, blockPos.getY() + FreecamHelper.val2, blockPos.getZ() + FreecamHelper.val2));
    }

    public static float[] getFloatArray2483(Entity entity) {
        return getFloatArray2484(SearchHelper.getVec3d232(minecraftClient.player.getEyePos(), entity.getBoundingBox()));
    }

    public static float[] getFloatArray2484(Vec3d vec3d) {
        return getFloatArray2486(minecraftClient.player.getCameraPosVec(SearchHelper_2.get536()), vec3d);
    }

    public static float[] getFloatArray2485(Vec3d vec3d, Direction direction) {
        Vec3d offset;
        Vec3d cameraPosVec = minecraftClient.player.getCameraPosVec(SearchHelper_2.get536());
        if (direction == null) {
            offset = vec3d;
        } else {
            offset = vec3d.offset(direction, FreecamHelper.val2);
        }
        return getFloatArray2486(cameraPosVec, offset);
    }

    public static float[] getFloatArray2486(Vec3d vec3d, Vec3d vec3d2) {
        double d = vec3d2.x - vec3d.x;
        double longBitsToDouble = (vec3d2.y - vec3d.y) * -1.0;
        double d2 = vec3d2.z - vec3d.z;
        return new float[]{(float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(d2, d)) - FreecamHelper.num2), (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(longBitsToDouble, Math.sqrt((d * d) + (d2 * d2)))))};
    }

    public static float[] getFloatArray2487(float[] fArr, float f) {
        if (fArr == null) {
            return null;
        }
        float clamp = MathHelper.clamp(f, 0.10000000149011612f, 1.0f);
        if (clamp < 1.0f) {
            float lastYaw = ((DuckClientPlayerEntity)(Object) minecraftClient.player).lastYaw();
            float wrapDegrees = MathHelper.wrapDegrees(fArr[0] - lastYaw);
            if (Math.abs(wrapDegrees) > 180.0f * clamp) {
                fArr[0] = lastYaw + (wrapDegrees * ((180.0f * clamp) / Math.abs(wrapDegrees)));
            }
        }
        return new float[]{fArr[0], fArr[1]};
    }

    public static int get2488(float f) {
        return MathHelper.floor(((f * 8.0f) / FreecamHelper.num3) + FreecamHelper.val2) & 7;
    }

    public static int get2489() {
        return get2490(minecraftClient.gameRenderer.getCamera().getYaw());
    }

    public static int get2490(float f) {
        return MathHelper.floor(((f * 4.0f) / FreecamHelper.num3) + FreecamHelper.val2) & 3;
    }

    public static Direction getDirection2491(int i) {
        switch (i) {
            case 0:
                return Direction.SOUTH;
            case 1:
                return Direction.WEST;
            case 2:
                return Direction.NORTH;
            case 3:
                return Direction.EAST;
            default:
                return Direction.UP;
        }
    }

    public static boolean is2492(Box box) {
        return ((DuckWorldRenderer)(Object) minecraftClient.worldRenderer).getFrustum().isVisible(box);
    }

    public static boolean is2493(Vec3d vec3d) {
        return is2492(new Box(BlockPos.ofFloored((Position) vec3d)));
    }

    public void do2494(float f, float f2) {
        if (minecraftClient.player.age == this.num) {
            return;
        }
        do2480(new float[]{f, f2});
    }

    public static boolean is724() {
        if (antiCheat == null) {
            return false;
        }
        return antiCheat.movementSync.getValue().booleanValue();
    }

    public static boolean is1144() {
        return antiCheat != null && is724() && antiCheat.rotations.getValue() == AutoCrystalMode_6.SILENT;
    }

    public float get2495() {
        return this.val;
    }

    public float get2496() {
        return this.val2;
    }

    public float get2497() {
        return this.val3;
    }

    public float get2498() {
        return this.val4;
    }

    public float get2499() {
        return this.val5;
    }

    public float get2500() {
        return this.val6;
    }

    public float get2501() {
        return this.val7;
    }

    public float get2502() {
        return this.val8;
    }

    public int get1116() {
        return this.num;
    }

    public float get2503(float f, float f2) {
        if (minecraftClient.player.hasVehicle()) {
            return f;
        }
        float f3 = f2;
        double x = minecraftClient.player.getX() - minecraftClient.player.prevX;
        double z = minecraftClient.player.getZ() - minecraftClient.player.prevZ;
        if ((x * x) + (z * z) > 0.002500000176951289) {
            float atan2 = (((float) MathHelper.atan2(z, x)) * 57.2957763671875f) - FreecamHelper.num2;
            float abs = MathHelper.abs(MathHelper.wrapDegrees(f) - atan2);
            f3 = (95.0f >= abs || abs >= 265.0f) ? atan2 : atan2 - 180.0f;
        }
        if (minecraftClient.player.handSwingProgress > 0.0f) {
            f3 = f;
        }
        float wrapDegrees = MathHelper.wrapDegrees(f - (f2 + (MathHelper.wrapDegrees(f3 - f2) * 0.30000001192092896f)));
        if (wrapDegrees < -75.0f) {
            wrapDegrees = -75.0f;
        } else if (wrapDegrees >= 75.0f) {
            wrapDegrees = 75.0f;
        }
        float f4 = f - wrapDegrees;
        if (wrapDegrees * wrapDegrees > 2500.0f) {
            f4 += wrapDegrees * 0.20000000298023224f;
        }
        return f4;
    }
}
