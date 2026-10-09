package me.mioclient.module.combat;

import java.awt.Color;
import me.mioclient.AntiPhaseData;
import me.mioclient.AntiPhaseHelper;
import me.mioclient.AntiPhaseHelper3;
import me.mioclient.AntiPhaseHelper3_2;
import me.mioclient.AntiPhaseHelper_2;
import me.mioclient.AntiPhaseHelper_3;
import me.mioclient.AntiPhaseMode;
import me.mioclient.AntiPhaseSearchHelper4;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.HoleSnapEvent;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.movement.NoSlow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/AntiPhase.class */
public class AntiPhase extends Module {
    public static final NoSlow noSlow = (NoSlow) BaritoneHelper_3.baritoneHelper_4.getModule117(NoSlow.class);
    public Setting<AntiPhaseMode> material;
    public Setting<Float> range;
    public Setting<Integer> delay;
    public Setting<Integer> bpt;
    public Setting<Boolean> strictDirection;
    public Setting<Boolean> rotate;
    public Setting<Boolean> noCrawl;
    public Setting<Boolean> render;
    public Setting<Color> fill;
    public Setting<Color> outline;
    public Setting<Float> lineWidth;
    public Setting<Boolean> fade;
    public Setting<Float> fadeTime;
    public final Stopwatch stopwatch;

    public AntiPhase() {
        super("AntiPhase", "Prevents your enemies from pearl-phasing into nearest block.", Category.COMBAT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        BaritoneHelper_3.antiPhaseSearchHelper4.register(new AntiPhaseSearchHelper4.Record(this, this.fill, this.outline, this.lineWidth, this.fadeTime, () -> {
            return true;
        }, this.fade, 450));
    }

    @Listen
    public void onEvent(HoleSnapEvent holeSnapEvent) {
        AntiPhaseData antiPhaseData844;
        if (!this.stopwatch.is420(this.delay.getValue().intValue()) || noSlow.is3108()) {
            return;
        }
        int i = 0;
        for (PlayerEntity playerEntity : minecraftClient.world.getPlayers()) {
            if (is266(playerEntity) && (antiPhaseData844 = getAntiPhaseHelper_3268().getAntiPhaseData844(this, playerEntity)) != null) {
                if (is265(antiPhaseData844)) {
                    if (this.rotate.getValue().booleanValue()) {
                        BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2484(antiPhaseData844.getVec3d2748()), 25);
                    }
                    if (this.render.getValue().booleanValue()) {
                        BaritoneHelper_3.antiPhaseSearchHelper4.do2133(this, antiPhaseData844.getBox799());
                    }
                    i++;
                }
                if (i >= this.bpt.getValue().intValue()) {
                    return;
                }
            }
        }
    }

    public boolean is265(AntiPhaseData antiPhaseData) {
        int i = FireworksHelper.get447(this.material.getValue().getItem581());
        int i2 = minecraftClient.player.getInventory().selectedSlot;
        if (i == -1) {
            return false;
        }
        FireworksHelper.do438(i);
        BlockHitResult blockHitResult = new BlockHitResult(antiPhaseData.getVec3d2748(), antiPhaseData.getDirection842(), antiPhaseData.getBlockPos12(), false);
        BaritoneHelper_3.antiPhaseSearchHelper4_2.do2227(() -> {
            AutoSignSearchHelper4.do2556(Hand.MAIN_HAND, blockHitResult);
        });
        AutoSignSearchHelper4.do2559(Hand.MAIN_HAND);
        FireworksHelper.do438(i2);
        return true;
    }

    public boolean is266(PlayerEntity playerEntity) {
        if (!playerEntity.isAlive() || minecraftClient.player == playerEntity || playerEntity.isSwimming() || BaritoneHelper_3.searchHelper4_14.is520(playerEntity) || HoleSnapSearchHelper4.getSet2011((LivingEntity) playerEntity).size() != 1 || is267(HoleSnapSearchHelper4.getBlockPos2008((LivingEntity) playerEntity)) || !HoleSnapSearchHelper4.is2013((LivingEntity) playerEntity)) {
            return false;
        }
        return (!this.noCrawl.getValue().booleanValue() || playerEntity.getBoundingBox().getLengthY() > 1.0) && playerEntity.distanceTo(minecraftClient.player) <= this.range.getValue().floatValue();
    }

    public boolean is267(BlockPos blockPos) {
        Box box = new Box(blockPos);
        for (Entity entity : minecraftClient.world.getEntities()) {
            if ((entity instanceof ItemFrameEntity) && entity.getBoundingBox().intersects(box)) {
                return true;
            }
        }
        return false;
    }

    public AntiPhaseHelper_3 getAntiPhaseHelper_3268() {
        switch (this.material.getValue()) {
            case LADDER:
                return new AntiPhaseHelper();
            case SCAFFOLDING:
                return new AntiPhaseHelper3();
            case ITEMFRAME:
                return new AntiPhaseHelper3_2();
            case VINE:
                return new AntiPhaseHelper_2();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
