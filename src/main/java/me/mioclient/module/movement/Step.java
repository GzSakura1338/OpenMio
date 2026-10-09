package me.mioclient.module.movement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FontsSearchHelper4;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.KeyPearlMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper_3;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent_2;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickPostEvent;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/Step.class */
public class Step extends Module {
    public static final HoleSnap holeSnap = (HoleSnap) BaritoneHelper_3.baritoneHelper_4.getModule117(HoleSnap.class);
    public static AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public Setting<StepMode> mode;
    public Setting<Float> height;
    public Setting<Boolean> safeDisable;
    public Setting<Boolean> useTimer;
    public Setting<Float> timerSpeed;
    public boolean flag;
    public int num;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/movement/Step$StepMode.class */
    public enum StepMode implements EnumSettingHelper {
        VANILLA("Vanilla"),
        NORMAL("Normal");

        public final String name;

        StepMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public Step() {
        super("Step", "Allows you to step up blocks without jumping.", Category.MOVEMENT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.num = 0;
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        this.flag = false;
        this.num = 0;
        if (is1469()) {
            return;
        }
        SearchHelper_3.do649(minecraftClient.player, 0.6000000238418579f);
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return FontsSearchHelper4.getString1684(this.mode.getValue());
    }

    @Listen(get219= Helper_7.num4)
    public void do32(TickPostEvent tickPostEvent) {
        if (this.num > 0) {
            if (BaritoneHelper_3.holeSnapSearchHelper4_5.is2723(HoleSnapSearchHelper4.getBlockPos1333()) && this.safeDisable.getValue().booleanValue()) {
                disable();
            }
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        this.flag = false;
    }

    @Listen
    public void onMove(MoveEvent_2 moveEvent_2) {
        if (holeSnap.is132()) {
            return;
        }
        if (!minecraftClient.player.isOnGround() || minecraftClient.player.fallDistance > 0.0f || !minecraftClient.player.verticalCollision) {
            BaritoneHelper_3.holeSnapSearchHelper4_2.do2017(this);
            return;
        }
        if (moveEvent_2.getKeyPearlMode1472() == KeyPearlMode.Pre) {
            moveEvent_2.do1473(this.height.getValue().floatValue());
        }
        if (moveEvent_2.getKeyPearlMode1472() == KeyPearlMode.Post) {
            if (moveEvent_2.get990() <= 0.6) {
                BaritoneHelper_3.holeSnapSearchHelper4_2.do2017(this);
                return;
            }
            if (this.mode.getValue() == StepMode.VANILLA) {
                this.num++;
                return;
            }
            ArrayList arrayList = new ArrayList();
            if (this.useTimer.getValue().booleanValue() && !HoleSnapSearchHelper4.is2005(minecraftClient.player)) {
                BaritoneHelper_3.holeSnapSearchHelper4_2.do2018(this, this.timerSpeed.getValue().floatValue());
                this.flag = true;
            }
            this.num++;
            if (moveEvent_2.get990() <= 1.0f) {
                arrayList.addAll(List.of(Double.valueOf(0.42 * moveEvent_2.get990()), Double.valueOf(0.75 * moveEvent_2.get990())));
            } else if (moveEvent_2.get990() <= 1.5) {
                arrayList.addAll(List.of(Double.valueOf(0.42), Double.valueOf(0.75), Double.valueOf(1.0), Double.valueOf(1.16), Double.valueOf(1.23), Double.valueOf(1.2)));
            } else if (moveEvent_2.get990() <= this.height.getValue().floatValue()) {
                arrayList.addAll(List.of(Double.valueOf(0.42), Double.valueOf(0.78), Double.valueOf(0.63), Double.valueOf(0.51), Double.valueOf(0.9), Double.valueOf(1.21), Double.valueOf(1.45), Double.valueOf(1.43)));
            }
            if (arrayList.isEmpty()) {
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                minecraftClient.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(minecraftClient.player.getX(), minecraftClient.player.getY() + ((Double) it.next()).doubleValue(), minecraftClient.player.getZ(), minecraftClient.player.isOnGround()));
            }
        }
    }

    public boolean is638() {
        return this.flag;
    }
}
