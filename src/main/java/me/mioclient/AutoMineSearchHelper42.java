package me.mioclient;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;
import me.mioclient.module.combat.AutoMine;
import me.mioclient.module.combat.Range;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/AutoMineSearchHelper42.class */
public final class AutoMineSearchHelper42 extends AutoMineSearchHelper4_2 {
    public static Range range = (Range) BaritoneHelper_3.baritoneHelper_4.getModule117(Range.class);

    public AutoMineSearchHelper42(AutoMine autoMine) {
        super(autoMine);
    }

    @Override // me.mioclient.AutoMineHelper_2
    public void do722(AutoMineHelper autoMineHelper) {
        int i;
        LivingEntity playerEntity886 = this.autoMine.autoMineSearchHelper4.getPlayerEntity886();
        if (playerEntity886 == null || ((PlayerEntity) playerEntity886).isCrawling() || is2891((Entity) playerEntity886)) {
            return;
        }
        Box boundingBox = ((PlayerEntity) playerEntity886).getBoundingBox();
        boolean is2754 = this.autoMine.is2754();
        boolean anyMatch = BlockPos.stream(boundingBox.withMaxY(boundingBox.minY)).anyMatch(blockPos -> {
            return PhaseESPSearchHelper4_2.getBlock3044(blockPos) == Blocks.BEDROCK;
        });
        if (BlockPos.stream(boundingBox.shrink(0.05, 0.05, 0.05)).filter(blockPos2 -> {
            return (PhaseESPSearchHelper4_2.getBlock3044(blockPos2).getBlastResistance() < 600.0f || is724() || PhaseESPSearchHelper4_2.getBlock3044(blockPos2) == Blocks.AIR) ? false : true;
        }).toList().isEmpty()) {
            AtomicReference atomicReference = new AtomicReference((BlockPos) BlockPos.stream(boundingBox.withMaxY(boundingBox.maxY + 0.5)).map((v0) -> {
                return v0.toImmutable();
            }).filter(this::is723).max(Comparator.comparing(blockPos3 -> {
                return Boolean.valueOf(this.is2890(blockPos3));
            })).orElse(null));
            AtomicReference atomicReference2 = new AtomicReference((BlockPos) HoleSnapSearchHelper4.getList2010(playerEntity886).stream().map((v0) -> {
                return v0.up();
            }).map((v0) -> {
                return v0.toImmutable();
            }).filter(this::is723).max(Comparator.comparing(blockPos4 -> {
                return Boolean.valueOf(this.is2890(blockPos4));
            })).orElse(null));
            if (atomicReference.get() == null && atomicReference2.get() != null && !is724()) {
                atomicReference.set((BlockPos) atomicReference2.get());
                atomicReference2.set(null);
            }
            if (atomicReference.get() == null) {
                return;
            }
            int i2 = 599;
            if (is724()) {
                if (anyMatch) {
                    i = is2754 ? 900 : 1000;
                } else {
                    i = is2754 ? 499 : 599;
                }
                autoMineHelper.do2901(i, autoMineHelper2 -> {
                    if (!is2754) {
                        autoMineHelper2.do667((BlockPos) atomicReference.get());
                    } else if (atomicReference2.get() == null) {
                        autoMineHelper2.do667((BlockPos) atomicReference.get());
                    } else {
                        autoMineHelper2.do2899((BlockPos) atomicReference.get());
                        autoMineHelper2.do667((BlockPos) atomicReference2.get());
                    }
                });
                return;
            }
            if (is2890((BlockPos) atomicReference.get())) {
                i2 = 650;
            }
            if (this.autoMine.trapOverride.getValue().booleanValue() && range.isToggled()) {
                i2 = 650;
            }
            autoMineHelper.do2901(i2, autoMineHelper3 -> {
                autoMineHelper3.do667((BlockPos) atomicReference.get());
            });
        }
    }

    @Override // me.mioclient.AutoMineSearchHelper4_2, me.mioclient.AutoMineHelper_2
    public boolean is465() {
        return this.autoMine.head.getValue().booleanValue();
    }

    public boolean is723(BlockPos blockPos) {
        return ((SearchHelper4_7.is2435(blockPos) && (PhaseESPSearchHelper4_2.getBlock3044(blockPos) == Blocks.OBSIDIAN || is724())) || is2890(blockPos)) && (minecraftClient.world.isAir(blockPos.up()) || is724());
    }

    public boolean is724() {
        return this.autoMine.logic.getValue() == Mode_12.GRIMV3;
    }
}
