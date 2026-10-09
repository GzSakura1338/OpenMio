package me.mioclient;

import java.util.Iterator;
import java.util.List;
import me.mioclient.module.combat.BedAura;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/BedAuraHelper.class */
public class BedAuraHelper implements SearchHelper4_6<LivingEntity, BedAuraData> {
    public static final BlockPos.Mutable mutable = new BlockPos.Mutable();
    public final BedAura bedAura;
    public Direction direction;
    public Vec3d vec3d;
    public float val;

    public BedAuraHelper(BedAura bedAura) {
        this.bedAura = bedAura;
    }

    @Override // me.mioclient.SearchHelper4_6
    /* renamed from: getBedAuraData964, reason: merged with bridge method [inline-methods] */
    public BedAuraData getObject970(LivingEntity livingEntity) {
        this.val = this.bedAura.range2.getValue().floatValue();
        Vec3d pos = livingEntity.getPos();
        float intBitsToFloat = 2.0f;
        BedAuraData bedAuraData = null;
        float f = -intBitsToFloat;
        while (true) {
            float f2 = f;
            if (f2 >= intBitsToFloat) {
                return bedAuraData;
            }
            float f3 = -intBitsToFloat;
            while (true) {
                float f4 = f3;
                if (f4 < 3.0f) {
                    float f5 = -intBitsToFloat;
                    while (true) {
                        float f6 = f5;
                        if (f6 < intBitsToFloat) {
                            this.vec3d = null;
                            mutable.set(pos.x + f2, pos.y + f4, pos.z + f6);
                            BedAuraData bedAuraData965 = getBedAuraData965(livingEntity, mutable);
                            if (bedAuraData965 != null && (bedAuraData == null || bedAuraData965.get155() >= bedAuraData.get155())) {
                                bedAuraData = bedAuraData965;
                            }
                            f5 = f6 + 1.0f;
                        } else {
                            break;
                        }
                    }
                    f3 = f4 + 1.0f;
                } else {
                    break;
                }
            }
            f = f2 + 1.0f;
        }
    }

    public BedAuraData getBedAuraData965(LivingEntity livingEntity, BlockPos blockPos) {
        if (!minecraftClient.world.getBlockState(blockPos).isReplaceable()) {
            if (!this.bedAura.is1426(blockPos, BedPart.HEAD)) {
                return null;
            }
        }
        BlockPos blockPos2 = null;
        Direction direction = null;
        Iterator it = Direction.Type.HORIZONTAL.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Direction direction2 = (Direction) it.next();
            BlockPos offset = blockPos.offset(direction2);
            if (!PhaseESPSearchHelper4_2.is3042(offset, true, false)) {
                if (!this.bedAura.is1426(offset, BedPart.FOOT)) {
                    continue;
                }
            }
            direction = getDirection966(direction2.getOpposite(), offset, this.bedAura.strictDirection.getValue().booleanValue());
            if (direction != null) {
                this.direction = direction2.getOpposite();
                blockPos2 = offset;
                break;
            }
        }
        if (blockPos2 == null) {
            return null;
        }
        float f = ArmorSearchHelper4.get1898(blockPos.toCenterPos(), livingEntity, this.bedAura.getBox1428(livingEntity));
        if (f < this.bedAura.minDamage.getValue().floatValue()) {
            return null;
        }
        float f2 = ArmorSearchHelper4.get1897(blockPos.toCenterPos(), minecraftClient.player);
        if (f2 > this.bedAura.maxSelfDamage.getValue().floatValue()) {
            return null;
        }
        if (this.vec3d == null && !this.bedAura.rotate.getValue().booleanValue()) {
            this.vec3d = blockPos2.toCenterPos().offset(direction, 0.5);
        }
        return new BedAuraData(blockPos2, blockPos.toImmutable(), f, f2, getBlockHitResult969(direction, blockPos2));
    }

    public Direction getDirection966(Direction direction, BlockPos blockPos, boolean z) {
        boolean booleanValue = this.bedAura.airPlace.getValue().booleanValue();
        for (Direction direction2 : Direction.values()) {
            BlockPos offset = blockPos.offset(direction2);
            if (offset.getY() < minecraftClient.world.getTopY() && (SearchHelper4_7.is2448(offset) || booleanValue)) {
                if (z) {
                    if (!PhaseESPSearchHelper4_2.getList3031(offset).contains(direction2.getOpposite())) {
                        continue;
                    }
                }
                if (this.bedAura.rotate.getValue().booleanValue()) {
                    this.vec3d = getVec3d967(direction2, direction, blockPos);
                    if (this.vec3d == null) {
                    }
                }
                return direction2;
            }
        }
        return null;
    }

    public Vec3d getVec3d967(Direction direction, Direction direction2, BlockPos blockPos) {
        for (Vec3d vec3d : getList968(blockPos, direction)) {
            if (vec3d.distanceTo(minecraftClient.player.getEyePos()) <= this.val && direction2 == Direction.fromRotation(SearchHelper4_8.getFloatArray2484(vec3d)[0])) {
                return vec3d;
            }
        }
        return null;
    }

    public List<Vec3d> getList968(BlockPos blockPos, Direction direction) {
        return direction == null ? AutoCraftMode.X8.getList899(blockPos) : AutoCraftMode.X8.getList900(blockPos, direction);
    }

    public BlockHitResult getBlockHitResult969(Direction direction, BlockPos blockPos) {
        if ((!this.bedAura.airPlace.getValue().booleanValue() || SearchHelper4_7.is2448(blockPos.offset(direction))) && !(this.bedAura.liquidPlace.getValue().booleanValue() && SearchHelper4_7.is2447(blockPos))) {
            return new BlockHitResult(this.vec3d, direction.getOpposite(), blockPos.offset(direction), false);
        }
        this.vec3d = getVec3d967((Direction) null, this.direction, blockPos);
        return new BlockHitResult(this.vec3d, direction, blockPos, false);
    }
}
