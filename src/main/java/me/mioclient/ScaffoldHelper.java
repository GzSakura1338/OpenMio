package me.mioclient;

import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.combat.AutoCrystal;
import me.mioclient.module.misc.Swing;
import me.mioclient.module.render.Hitmarker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Hand;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ScaffoldHelper.class */
public class ScaffoldHelper implements SearchHelper_4 {
    public static final Swing swing = (Swing) BaritoneHelper_3.baritoneHelper_4.getModule117(Swing.class);
    public static AutoCrystal ac = (AutoCrystal) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoCrystal.class);
    public static Hitmarker hitmarker = (Hitmarker) BaritoneHelper_3.baritoneHelper_4.getModule117(Hitmarker.class);
    public final Stopwatch stopwatch = new Stopwatch();
    public long num;
    public int current;

    public ScaffoldHelper() {
        baritoneHelper.do1796(this);
    }

    @Listen(get219= -300)
    public void do27(TickEvent tickEvent) {
        this.current = -1;
    }

    public void do1111(int i) {
        this.current = i;
    }

    public boolean is1112(int i) {
        return is1113(i, false);
    }

    public boolean is1113(int i, boolean z) {
        if (!is1114() && z) {
            return false;
        }
        if (this.current != -1 && !z) {
            return false;
        }
        int i2 = minecraftClient.player.getInventory().selectedSlot;
        boolean is1118 = is1118();
        this.current = i;
        AutoSignSearchHelper4.do2564(this.current);
        Entity entityById = minecraftClient.world.getEntityById(i);
        if (entityById != null && entityById.getWorld() != null && entityById != minecraftClient.player && entityById.getWorld().isClient && hitmarker.is1121(entityById) && (entityById instanceof EndCrystalEntity)) {
            hitmarker.num = System.currentTimeMillis();
            hitmarker.num2 = 255;
            hitmarker.flag = true;
        }
        Hand hand = minecraftClient.player.getOffHandStack().getItem() == Items.END_CRYSTAL ? Hand.OFF_HAND : Hand.MAIN_HAND;
        if (!(entityById instanceof EndCrystalEntity) && entityById != null) {
            hand = Hand.MAIN_HAND;
        }
        do1117(hand);
        if (z) {
            AutoSignSearchHelper4.do2559(Hand.MAIN_HAND);
        } else {
            minecraftClient.player.swingHand(hand);
        }
        if (!is1118 || ac.weakness.getValue() != ScaffoldHelperMode.SILENT) {
            return true;
        }
        ac.do1159();
        FireworksHelper.do456(i2);
        return true;
    }

    public boolean is1114() {
        return ac.instant.getValue() != AutoCrystalMode.NONE;
    }

    public int get1115() {
        return ac.delay2.getValue().intValue();
    }

    public int get1116() {
        return ac.ticksExisted.getValue().intValue();
    }

    public void do1117(Hand hand) {
        if (!this.stopwatch.is420(200 + this.num) || hand != Hand.MAIN_HAND || swing.hand.getValue() == Swing.ScaffoldHelperMode.scaffoldHelperMode2 || swing.type.getValue() == Swing.MixinLivingEntityMode.VANILLA) {
            return;
        }
        minecraftClient.player.resetLastAttackedTicks();
        this.num = (long) (Math.random() * 300.0);
    }

    public boolean is1118() {
        int i;
        if (ac.weakness.getValue() == ScaffoldHelperMode.NONE) {
            return false;
        }
        if (!minecraftClient.player.hasStatusEffect(StatusEffects.WEAKNESS) || (minecraftClient.player.getMainHandStack().getItem() instanceof ToolItem) || (i = FireworksHelper.get448(itemStack -> {
            return itemStack.getItem() instanceof SwordItem;
        })) == -1) {
            return false;
        }
        if (ac.weakness.getValue() == ScaffoldHelperMode.SILENT) {
            ac.do1159();
        }
        FireworksHelper.do456(i);
        return true;
    }
}
