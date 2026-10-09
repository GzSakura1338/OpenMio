package me.mioclient.module.misc;

import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FreecamHelper;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapEvent;
import me.mioclient.HoleSnapSearchHelper4_6;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.TridentItem;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/misc/AntiAim.class */
public class AntiAim extends Module {
    public Setting<Boolean> yaw2;
    public Setting<AntiAimPredicateMode> yaw;
    public Setting<Float> speed;
    public Setting<Float> static_2;
    public Setting<Float> flip;
    public Setting<Float> jitterRange;
    public Setting<Float> base;
    public Setting<Boolean> pitch;
    public Setting<AntiAimMode> pitch2;
    public Setting<Float> static_;
    public Setting<Boolean> enderman;
    public Setting<AntiAimMode_2> look;
    public boolean flag;
    public float val;
    public int num;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/misc/AntiAim$AntiAimMode.class */
    public enum AntiAimMode implements EnumSettingHelper {
        antiAimMode("Static") {
            @Override
            public float get579(AntiAim antiAim, float f) {
                return antiAim.static_.getValue().floatValue();
            }
        },
        antiAimMode2("Random") {
            @Override
            public float get579(AntiAim antiAim, float f) {
                return (float) ((Math.random() * 180.0d) - FreecamHelper.num2);
            }
        };

        public final String name;

        AntiAimMode(String str2) {
            this.name = str2;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public float get579(AntiAim antiAim, float f) {
            return 0.0f;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/misc/AntiAim$AntiAimMode_2.class */
    public enum AntiAimMode_2 implements EnumSettingHelper {
        AVOID("Avoid"),
        STARE("Stare");

        public final String name;

        AntiAimMode_2(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/misc/AntiAim$AntiAimPredicateMode.class */
    public enum AntiAimPredicateMode implements EnumSettingHelper {
        antiAimPredicateMode("Jitter") {
            @Override
            public float get2644(AntiAim antiAim, float f) {
                return (float) MathHelper.wrapDegrees((((Math.random() * antiAim.jitterRange.getValue().floatValue()) * 2.0d) - antiAim.jitterRange.getValue().floatValue()) + antiAim.base.getValue().floatValue());
            }
        },
        antiAimPredicateMode2("Spin") {
            @Override
            public float get2644(AntiAim antiAim, float f) {
                antiAim.val += antiAim.speed.getValue().floatValue();
                return MathHelper.wrapDegrees(antiAim.val);
            }
        },
        antiAimPredicateMode3("Flip") {
            @Override
            public float get2644(AntiAim antiAim, float f) {
                return MathHelper.wrapDegrees(antiAim.base.getValue().floatValue() + (SearchHelper_4.minecraftClient.player.age % 2 == 0 ? -antiAim.flip.getValue().floatValue() : antiAim.flip.getValue().floatValue()));
            }
        },
        antiAimPredicateMode4("Static") {
            @Override
            public float get2644(AntiAim antiAim, float f) {
                return antiAim.static_2.getValue().floatValue();
            }
        },
        antiAimPredicateMode5("Random") {
            @Override
            public float get2644(AntiAim antiAim, float f) {
                return (float) (Math.random() * FreecamHelper.num3);
            }
        };

        public final String name;

        AntiAimPredicateMode(String str2) {
            this.name = str2;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public float get2644(AntiAim antiAim, float f) {
            return 0.0f;
        }
    }

    public AntiAim() {
        super("AntiAim", "Sets your rotations server-side.", Category.MISC, new String[0]);
        PhaseESPHelper.do1351(this);
        this.yaw.do2329("YawMode");
        this.pitch2.do2329("PitchMode");
    }

    @Listen(get219= Helper_7.num4)
    public void onEvent(HoleSnapEvent holeSnapEvent) {
        EndermanEntity endermanEntity852;
        this.flag = false;
        if (BaritoneHelper_3.searchHelper4_8.getElytraFlyData2475() != null) {
            return;
        }
        if (this.num > 0) {
            this.num--;
            return;
        }
        float f = holeSnapEvent.get751();
        float f2 = holeSnapEvent.get752();
        if (this.yaw2.getValue().booleanValue()) {
            f = this.yaw.getValue().get2644(this, minecraftClient.player.getYaw());
            do857();
        }
        if (this.pitch.getValue().booleanValue()) {
            f2 = this.pitch2.getValue().get579(this, minecraftClient.player.getPitch());
            do857();
        }
        if (this.enderman.getValue().booleanValue() && (endermanEntity852 = getEndermanEntity852()) != null) {
            if (this.look.getValue() == AntiAimMode_2.AVOID) {
                f2 = 90.0f;
                do857();
            }
            if (this.look.getValue() == AntiAimMode_2.STARE) {
                float[] floatArray2484 = SearchHelper4_8.getFloatArray2484(endermanEntity852.getEyePos());
                f = floatArray2484[0];
                f2 = floatArray2484[1];
                do857();
            }
        }
        if (this.flag) {
            BaritoneHelper_3.searchHelper4_8.do2478(new float[]{f, f2}, -999, true);
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        Item item = minecraftClient.player.getMainHandStack().getItem();
        if (((item instanceof RangedWeaponItem) || (item instanceof TridentItem)) && minecraftClient.player.isUsingItem()) {
            this.num = 2;
        }
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        PlayerInteractItemC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof PlayerInteractItemC2SPacket ? (PlayerInteractItemC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if (packet904 instanceof PlayerInteractItemC2SPacket) {
            do854(packet904.getHand());
        }
        PlayerInteractBlockC2SPacket packet9042 = (sendImmediatelyEvent.getPacket904()) instanceof PlayerInteractBlockC2SPacket ? (PlayerInteractBlockC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if (packet9042 instanceof PlayerInteractBlockC2SPacket) {
            do854(packet9042.getHand());
        }
    }

    public EndermanEntity getEndermanEntity852() {
        for (Entity entity : minecraftClient.world.getEntities()) {
            if (entity instanceof EndermanEntity) {
                EndermanEntity endermanEntity = (EndermanEntity) entity;
                if (!endermanEntity.isAngry() && endermanEntity.isAlive() && (this.look.getValue() != AntiAimMode_2.AVOID || is853(endermanEntity))) {
                    if (HoleSnapSearchHelper4_6.is2788(entity)) {
                        return endermanEntity;
                    }
                }
            }
        }
        return null;
    }

    public boolean is853(EndermanEntity endermanEntity) {
        Vec3d normalize = minecraftClient.player.getRotationVec(1.0f).normalize();
        Vec3d vec3d = new Vec3d(endermanEntity.getX() - minecraftClient.player.getX(), endermanEntity.getEyeY() - minecraftClient.player.getEyeY(), endermanEntity.getZ() - minecraftClient.player.getZ());
        return normalize.dotProduct(vec3d.normalize()) > 1.0 - (0.025 / vec3d.length());
    }

    public void do854(Hand hand) {
        if (minecraftClient.player == null) {
            return;
        }
        ItemStack stackInHand = minecraftClient.player.getStackInHand(hand);
        if (stackInHand.isEmpty()) {
            return;
        }
        Item item = stackInHand.getItem();
        if ((item instanceof BlockItem) || PhaseESPSearchHelper4_2.is3053(item)) {
            this.num = 2;
        }
    }

    public boolean is855() {
        return this.flag;
    }

    public void do856() {
        this.num = 2;
    }

    public void do857() {
        this.flag = true;
    }
}
