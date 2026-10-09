package me.mioclient;

import java.util.ArrayDeque;
import java.util.Iterator;
import me.mioclient.event.DisconnectEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Stopwatch;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/FeetPlaceSearchHelper4.class */
public class FeetPlaceSearchHelper4 implements SearchHelper_4 {
    public double val;
    public double val2;
    public final ArrayDeque<Double> arrayDeque = new ArrayDeque<>();
    public Stopwatch stopwatch = new Stopwatch();

    public FeetPlaceSearchHelper4() {
        baritoneHelper.do1796(this);
    }

    @Listen
    public void onDisconnect(DisconnectEvent disconnectEvent) {
        this.val = 0.0d;
        this.val2 = 0.0d;
        this.arrayDeque.clear();
        this.stopwatch.reset();
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        double d = get2636();
        if (d > 0.0d || minecraftClient.player.age % 4 == 0) {
            this.arrayDeque.add(Double.valueOf(d * BaritoneHelper_3.holeSnapSearchHelper4_2.get2019()));
        } else {
            this.arrayDeque.pollFirst();
        }
        while (!this.arrayDeque.isEmpty() && this.arrayDeque.size() > 9) {
            this.arrayDeque.poll();
        }
        double d2 = 0.0d;
        Iterator<Double> it = this.arrayDeque.iterator();
        while (it.hasNext()) {
            d2 += it.next().doubleValue();
        }
        this.val = this.arrayDeque.size() == 0 ? 0.0d : d2 / this.arrayDeque.size();
        if (this.val < 21.0) {
            this.val2 = this.val;
        } else if (this.stopwatch.is419(50L)) {
            this.val2 = this.val;
            this.stopwatch.reset();
        }
    }

    public double get2634() {
        return this.val;
    }

    public double get2635() {
        return this.val2;
    }

    public double get2636() {
        return Math.hypot(minecraftClient.player.hasVehicle() ? minecraftClient.player.getVehicle().getX() - minecraftClient.player.getVehicle().prevX : minecraftClient.player.getX() - minecraftClient.player.prevX, minecraftClient.player.hasVehicle() ? minecraftClient.player.getVehicle().getZ() - minecraftClient.player.getVehicle().prevZ : minecraftClient.player.getZ() - minecraftClient.player.prevZ) * 20.0 * 3.6;
    }
}
