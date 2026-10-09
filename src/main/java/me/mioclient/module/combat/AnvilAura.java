package me.mioclient.module.combat;

import java.util.ArrayList;
import java.util.List;
import me.mioclient.AnvilAuraData;
import me.mioclient.AnvilAuraMode;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.module.Delay;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/AnvilAura.class */
public class AnvilAura extends Delay {
    public static final BlockPos.Mutable mutable = new BlockPos.Mutable();
    public Setting<Float> targetRange;
    public Setting<Boolean> ignoreNakeds;
    public boolean flag;

    public AnvilAura() {
        super("AnvilAura", "Drops anvils on your enemies.", Category.COMBAT, "autoanvil");
        PhaseESPHelper.do1351(this);
        unregister((Setting<?>) this.setting10);
    }

    @Override // me.mioclient.module.Delay
    public List<BlockPos> getList876() {
        ArrayList arrayList = new ArrayList();
        this.flag = false;
        AnvilAuraData anvilAuraData1909 = getAnvilAuraData1909();
        if (anvilAuraData1909 == null) {
            return arrayList;
        }
        if (anvilAuraData1909.getAnvilAuraMode2248() == AnvilAuraMode.CAGE) {
            for (int i = 0; i < 3; i++) {
                arrayList.add(HoleSnapSearchHelper4.getBlockPos2008(anvilAuraData1909.getPlayerEntity2247()).offset(anvilAuraData1909.getDirection842()).up(i));
            }
        } else {
            arrayList.add(HoleSnapSearchHelper4.getBlockPos2008(anvilAuraData1909.getPlayerEntity2247()).up(2));
            this.flag = true;
        }
        return arrayList;
    }

    @Override // me.mioclient.module.Delay
    public int get499() {
        return this.flag ? FireworksHelper.get447(Items.ANVIL) : super.get499();
    }

    public AnvilAuraData getAnvilAuraData1909() {
        AnvilAuraData anvilAuraData;
        double longBitsToDouble = 1.7976931348623157e+308;
        AnvilAuraData anvilAuraData2 = null;
        for (LivingEntity livingEntity : minecraftClient.world.getPlayers()) {
            if (((AbstractClientPlayerEntity) livingEntity).isAlive() && minecraftClient.player != livingEntity && !BaritoneHelper_3.searchHelper4_14.is520((PlayerEntity) livingEntity) && (!this.ignoreNakeds.getValue().booleanValue() || HoleSnapSearchHelper4.is2013(livingEntity))) {
                BlockPos up = HoleSnapSearchHelper4.getBlockPos2008(livingEntity).up(2);
                double distanceTo = minecraftClient.player.getEyePos().distanceTo(((AbstractClientPlayerEntity) livingEntity).getPos());
                if (distanceTo <= this.targetRange.getValue().floatValue()) {
                    if (minecraftClient.world.getBlockState(up.down()).isReplaceable() && PhaseESPSearchHelper4_2.is3041(up, false)) {
                        if (PhaseESPSearchHelper4_2.getDirection3029(up, this.setting4.getValue().booleanValue()) != null) {
                            anvilAuraData = new AnvilAuraData((PlayerEntity) livingEntity, AnvilAuraMode.ANVIL, null);
                        } else {
                            Direction direction1910 = getDirection1910(up);
                            if (direction1910 != null) {
                                anvilAuraData = new AnvilAuraData((PlayerEntity) livingEntity, AnvilAuraMode.CAGE, direction1910);
                            } else {
                                continue;
                            }
                        }
                        if (distanceTo < longBitsToDouble) {
                            anvilAuraData2 = anvilAuraData;
                            longBitsToDouble = distanceTo;
                        }
                    }
                }
            }
        }
        return anvilAuraData2;
    }

    public Direction getDirection1910(BlockPos blockPos) {
        List<Direction> list3031 = PhaseESPSearchHelper4_2.getList3031(blockPos);
        int i = 1337;
        Direction direction = null;
        for (Direction direction2 : Direction.values()) {
            if (list3031.contains(direction2.getOpposite()) || !this.setting4.getValue().booleanValue()) {
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    if (i3 >= 3) {
                        break;
                    }
                    mutable.set(blockPos.getX() + direction2.getOffsetX(), blockPos.getY() - i3, blockPos.getZ() + direction2.getOffsetZ());
                    if (!minecraftClient.world.getBlockState(mutable).isReplaceable()) {
                        i2 = i3;
                        break;
                    }
                    i2 = -1;
                    i3++;
                }
                if (i2 != -1 && i2 < i) {
                    i = i2;
                    direction = direction2;
                }
            }
        }
        return direction;
    }
}
