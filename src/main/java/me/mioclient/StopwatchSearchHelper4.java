package me.mioclient;

import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.mixin.ducks.DuckVehicleMoveC2SPacket;
import me.mioclient.module.movement.EntityControl;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/StopwatchSearchHelper4.class */
public final class StopwatchSearchHelper4 implements SearchHelper_4 {
    public final EntityControl entityControl;
    public int num;
    public int num2;
    public Vec3d vec3d;
    public final Stopwatch stopwatch = new Stopwatch();
    public boolean flag = true;

    public StopwatchSearchHelper4(EntityControl entityControl) {
        this.entityControl = entityControl;
    }

    public boolean is2735() {
        if (!minecraftClient.player.hasVehicle()) {
            return true;
        }
        long j = 1000;
        if (this.num >= 3) {
            j = 250;
        }
        if (this.stopwatch.is419(j)) {
            do2737();
        }
        Vec3d pos = minecraftClient.player.getVehicle().getPos();
        if (this.num2 > 0) {
            this.num2--;
        }
        if (this.vec3d == null || 0 != 0 || Math.abs(pos.y - this.vec3d.y) >= 18.0) {
            do2737();
            this.vec3d = pos;
        }
        return this.flag;
    }

    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if ((sendImmediatelyEvent.getPacket904() instanceof PlayerMoveC2SPacket) || (sendImmediatelyEvent.getPacket904() instanceof BoatPaddleStateC2SPacket)) {
            sendImmediatelyEvent.do1162();
        }
    }

    public void do2736(Vec3dEvent vec3dEvent) {
        this.flag = true;
        Entity vehicle = minecraftClient.player.getVehicle();
        if (vehicle == null) {
            return;
        }
        Vec3d vec3d1954 = vec3dEvent.getVec3d1954();
        vehicle.updatePosition(vec3d1954.getX(), vec3d1954.getY(), vec3d1954.getZ());
        do2738(0.0f);
        vec3dEvent.do1162();
        this.num2 = 2;
    }

    public void do2737() {
        Entity vehicle = minecraftClient.player.getVehicle();
        if (vehicle == null) {
            return;
        }
        if (this.vec3d == null || this.vec3d.getY() >= vehicle.getY()) {
            this.num = 0;
        } else {
            this.num++;
        }
        do2738(1000.0f);
        this.stopwatch.reset();
        this.flag = false;
    }

    public void do2738(float f) {
        Entity vehicle = minecraftClient.player.getVehicle();
        if (vehicle == null) {
            return;
        }
        Packet vehicleMoveC2SPacket = new VehicleMoveC2SPacket(vehicle);
        ((DuckVehicleMoveC2SPacket) vehicleMoveC2SPacket).setY(((VehicleMoveC2SPacket) vehicleMoveC2SPacket).getY() + f);
        AutoSignSearchHelper4.do2571(vehicleMoveC2SPacket);
    }

    public boolean is2739(int i) {
        return this.num >= i;
    }
}
