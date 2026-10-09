package me.mioclient;

import me.mioclient.module.render.Trajectories;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.EggItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SnowballItem;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/TrajectoriesSearchHelper4.class */
public class TrajectoriesSearchHelper4 implements SearchHelper_4 {
    public static Trajectories trajectories = (Trajectories) BaritoneHelper_3.baritoneHelper_4.getModule117(Trajectories.class);
    public static final net.minecraft.util.hit.HitResult hitResult = new HitResult(Vec3d.ZERO);
    public static final BlockPos.Mutable mutable = new BlockPos.Mutable();
    public static Vec3d vec3d = new Vec3d(0.0d, 0.0d, 0.0d);
    public static Vec3d vec3d2 = new Vec3d(0.0d, 0.0d, 0.0d);
    public final Vector3d vector3d = new Vector3d();
    public final Vector3d vector3d2 = new Vector3d();
    public double val;
    public double val2;
    public double val3;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/TrajectoriesSearchHelper4$HitResult.class */
    static class HitResult extends net.minecraft.util.hit.HitResult {
        public HitResult(Vec3d vec3d) {
            super(vec3d);
        }

        public HitResult.Type getType() {
            return HitResult.Type.MISS;
        }
    }

    public boolean is1867(Entity entity, ItemStack itemStack, double d, boolean z, double d2) {
        ChargedProjectilesComponent chargedProjectilesComponent;
        Item item = itemStack.getItem();
        if ((item instanceof BowItem) && trajectories.bow.getValue().booleanValue()) {
            double lerp = MathHelper.lerp(d2, trajectories.get1475(), BowItem.getPullProgress(minecraftClient.player.getItemUseTime()));
            if (lerp <= 0.0d) {
                lerp = 1.0;
            }
            do1868(entity, 0.0d, lerp * 3.0, d, 0.05000000074505806, 0.6, z, d2);
            return true;
        }
        if ((item instanceof CrossbowItem) && trajectories.xBow.getValue().booleanValue()) {
            if (!CrossbowItem.isCharged(itemStack) || (chargedProjectilesComponent = (ChargedProjectilesComponent) itemStack.get(DataComponentTypes.CHARGED_PROJECTILES)) == null) {
                return false;
            }
            if (chargedProjectilesComponent.contains(Items.FIREWORK_ROCKET)) {
                do1868(entity, 0.0d, chargedProjectilesComponent.contains(Items.FIREWORK_ROCKET) ? 1.6f : 3.15f, d, 0.0d, 0.6, z, d2);
                return true;
            }
            do1868(entity, 0.0d, chargedProjectilesComponent.contains(Items.FIREWORK_ROCKET) ? 1.6f : 3.15f, d, 0.05000000074505806, 0.6, z, d2);
            return true;
        }
        if ((item instanceof FishingRodItem) && trajectories.others.getValue().booleanValue()) {
            do1872(entity, d2);
            return true;
        }
        if ((item instanceof TridentItem) && trajectories.trident.getValue().booleanValue()) {
            do1868(entity, 0.0d, 2.5, d, 0.05000000074505806, 0.99, z, d2);
            return true;
        }
        if ((((item instanceof SnowballItem) || (item instanceof EggItem)) && trajectories.others.getValue().booleanValue()) || ((item instanceof EnderPearlItem) && trajectories.pearls.getValue().booleanValue())) {
            do1868(entity, 0.0d, 1.5, d, 0.03, 0.8, z, d2);
            return true;
        }
        if ((item instanceof ExperienceBottleItem) && trajectories.exp.getValue().booleanValue()) {
            do1868(entity, -20.0, 0.7, d, 0.07, 0.8, z, d2);
            return true;
        }
        if (!(item instanceof ThrowablePotionItem) || !trajectories.others.getValue().booleanValue()) {
            return false;
        }
        do1868(entity, -20.0, FreecamHelper.val2, d, 0.05, 0.8, z, d2);
        return true;
    }

    public void do1868(Entity entity, double d, double d2, double d3, double d4, double d5, boolean z, double d6) {
        double d7;
        double d8;
        double d9;
        this.vector3d.set(MathHelper.lerp(d6, entity.prevX, entity.getX()), MathHelper.lerp(d6, entity.prevY, entity.getY()) + entity.getEyeHeight(entity.getPose()), MathHelper.lerp(d6, entity.prevZ, entity.getZ()));
        double lerp = MathHelper.lerp(d6, entity.prevYaw, entity.getYaw());
        double lerp2 = MathHelper.lerp(d6, entity.prevPitch, entity.getPitch());
        if (entity == minecraftClient.player) {
            lerp = MathHelper.lerp(d6, BaritoneHelper_3.searchHelper4_8.get2498(), BaritoneHelper_3.searchHelper4_8.get2495());
            lerp2 = MathHelper.lerp(d6, BaritoneHelper_3.searchHelper4_8.get2499(), BaritoneHelper_3.searchHelper4_8.get2496());
        }
        if (d3 == 0.0d) {
            d7 = (-Math.sin(lerp * FreecamHelper.val4)) * Math.cos(lerp2 * FreecamHelper.val4);
            d8 = -Math.sin((lerp2 + d) * FreecamHelper.val4);
            d9 = Math.cos(lerp * FreecamHelper.val4) * Math.cos(lerp2 * FreecamHelper.val4);
        } else {
            Vec3d oppositeRotationVector = entity.getOppositeRotationVector(1.0f);
            Quaterniondc angleAxis = new Quaterniond().setAngleAxis(d3, oppositeRotationVector.x, oppositeRotationVector.y, oppositeRotationVector.z);
            Vec3d rotationVec = entity.getRotationVec(1.0f);
            Vector3d vector3d = new Vector3d(rotationVec.x, rotationVec.y, rotationVec.z);
            vector3d.rotate(angleAxis);
            d7 = vector3d.x;
            d8 = vector3d.y;
            d9 = vector3d.z;
        }
        this.vector3d2.set(d7, d8, d9).normalize().mul(d2);
        if (z) {
            Vec3d velocity = entity.getVelocity();
            if (minecraftClient.player == entity) {
                this.vector3d2.add(-MathHelper.lerp(d6, trajectories.getVec3d1474().x, velocity.x), entity.isOnGround() ? 0.0d : MathHelper.lerp(d6, trajectories.getVec3d1474().y, velocity.y), -MathHelper.lerp(d6, trajectories.getVec3d1474().z, velocity.z));
            } else {
                this.vector3d2.add(-velocity.x, entity.isOnGround() ? 0.0d : velocity.y, -velocity.z);
            }
        }
        this.val = d4;
        this.val2 = 0.99;
        this.val3 = d5;
    }

    public boolean is1869(Entity entity, boolean z, double d) {
        if (!minecraftClient.world.isSpaceEmpty(entity, entity.getBoundingBox().expand(SearchHelper.val))) {
            return false;
        }
        if (entity instanceof ArrowEntity) {
            do1870(entity, ((ArrowEntity) entity).getVelocity().length(), 0.05000000074505806, 0.6, z, d);
        } else if ((entity instanceof EnderPearlEntity) || (entity instanceof SnowballEntity) || (entity instanceof EggEntity)) {
            do1870(entity, 1.5, 0.03, 0.8, z, d);
            if (BlockPos.stream(entity.getBoundingBox().expand(0.0d, 2.0, 0.0d)).map((v0) -> {
                return v0.toImmutable();
            }).anyMatch(blockPos -> {
                return minecraftClient.world.getBlockState(blockPos).getBlock() == Blocks.BUBBLE_COLUMN;
            })) {
                Vec3d velocity = entity.getVelocity();
                Vec3d vec3d3 = new Vec3d(velocity.x, -999.0, velocity.z);
                do1871(entity, vec3d3, 1.0, 1.5);
            }
        } else if (entity instanceof TridentEntity) {
            do1870(entity, 2.5, 0.05000000074505806, 0.99, z, d);
        } else if (entity instanceof ExperienceBottleEntity) {
            do1870(entity, 0.7, 0.07, 0.8, z, d);
        } else if (entity instanceof ThrownEntity) {
            do1870(entity, FreecamHelper.val2, 0.05, 0.8, z, d);
        } else {
            if (!(entity instanceof WitherSkullEntity) && !(entity instanceof FireballEntity) && !(entity instanceof DragonFireballEntity)) {
                return false;
            }
            do1870(entity, 0.95, 0.0d, 0.8, z, d);
        }
        if (!entity.hasNoGravity()) {
            return true;
        }
        this.val = 0.0d;
        return true;
    }

    public void do1870(Entity entity, double d, double d2, double d3, boolean z, double d4) {
        this.vector3d.set(MathHelper.lerp(d4, entity.prevX, entity.getX()), MathHelper.lerp(d4, entity.prevY, entity.getY()), MathHelper.lerp(d4, entity.prevZ, entity.getZ()));
        do1871(entity, entity.getVelocity(), d4, d);
        if (z) {
            Vec3d velocity = entity.getVelocity();
            Vector3d vector3d = this.vector3d2;
            double d5 = velocity.x;
            double d6 = entity.isOnGround() ? 0.0d : velocity.y;
            vector3d.add(d5, d6, velocity.z);
        }
        this.val = d2;
        this.val2 = 0.99;
        this.val3 = d3;
    }

    public void do1871(Entity entity, Vec3d vec3d3, double d, double d2) {
        CameraYawHelper cameraYawHelper = (CameraYawHelper) entity;
        this.vector3d2.set(MathHelper.lerp(d, cameraYawHelper.mio$getPrevVelocity().x, vec3d3.x), MathHelper.lerp(d, cameraYawHelper.mio$getPrevVelocity().y, vec3d3.y), MathHelper.lerp(d, cameraYawHelper.mio$getPrevVelocity().z, vec3d3.z)).normalize().mul(d2);
    }

    public void do1872(Entity entity, double d) {
        double lerp = MathHelper.lerp(d, entity.prevYaw, entity.getYaw());
        double lerp2 = MathHelper.lerp(d, entity.prevPitch, entity.getPitch());
        double cos = Math.cos(((-lerp) * FreecamHelper.val4) - 3.1415927410125732);
        double sin = Math.sin(((-lerp) * FreecamHelper.val4) - 3.1415927410125732);
        double d2 = -Math.cos((-lerp2) * FreecamHelper.val4);
        double sin2 = Math.sin((-lerp2) * FreecamHelper.val4);
        this.vector3d.set(MathHelper.lerp(d, entity.prevX, entity.getX()), MathHelper.lerp(d, entity.prevY, entity.getY()) + entity.getEyeHeight(entity.getPose()), MathHelper.lerp(d, entity.prevZ, entity.getZ()));
        this.vector3d.sub(sin * 0.3, 0.0d, cos * 0.3);
        this.vector3d2.set(-sin, MathHelper.clamp(-(sin2 / d2), -5.0, 5.0), -cos);
        double length = this.vector3d2.length();
        this.vector3d2.mul((0.6 / length) + FreecamHelper.val2, (0.6 / length) + FreecamHelper.val2, (0.6 / length) + FreecamHelper.val2);
        this.val = 0.03;
        this.val2 = 0.92;
        this.val3 = 0.0d;
    }

    public net.minecraft.util.hit.HitResult getHitResult1873() {
        vec3d2 = new Vec3d(this.vector3d.x, this.vector3d.y, this.vector3d.z);
        this.vector3d.add(this.vector3d2);
        this.vector3d2.mul(is1874() ? this.val3 : this.val2);
        this.vector3d2.sub(0.0d, this.val, 0.0d);
        if (this.vector3d.y < minecraftClient.world.getBottomY()) {
            return hitResult;
        }
        if (!minecraftClient.world.getChunkManager().isChunkLoaded((int) (this.vector3d.x / 16.0), (int) (this.vector3d.z / 16.0))) {
            return hitResult;
        }
        vec3d = new Vec3d(this.vector3d.x, this.vector3d.y, this.vector3d.z);
        net.minecraft.util.hit.HitResult hitResult1875 = getHitResult1875();
        if (hitResult1875.getType() == HitResult.Type.MISS) {
            return null;
        }
        return hitResult1875;
    }

    public boolean is1874() {
        mutable.set(this.vector3d.x, this.vector3d.y, this.vector3d.z);
        FluidState fluidState = minecraftClient.world.getFluidState(mutable);
        return (fluidState.getFluid() == Fluids.WATER || fluidState.getFluid() == Fluids.FLOWING_WATER) && this.vector3d.y - ((double) ((int) this.vector3d.y)) <= ((double) fluidState.getHeight());
    }

    public net.minecraft.util.hit.HitResult getHitResult1875() {
        Vec3d vec3d3 = vec3d2;
        net.minecraft.util.hit.HitResult raycast = minecraftClient.world.raycast(new RaycastContext(vec3d3, vec3d, RaycastContext.ShapeType.COLLIDER, this.val3 == 0.0d ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, minecraftClient.player));
        if (raycast.getType() != HitResult.Type.MISS) {
            vec3d3 = raycast.getPos();
        }
        net.minecraft.util.hit.HitResult entityCollision = ProjectileUtil.getEntityCollision(minecraftClient.world, minecraftClient.player, vec3d3, vec3d, new Box(this.vector3d.x, this.vector3d.y, this.vector3d.z, this.vector3d.x, this.vector3d.y, this.vector3d.z).stretch(minecraftClient.player.getVelocity()).expand(1.0), entity -> {
            return !entity.isSpectator() && entity.isAlive() && entity.canHit();
        });
        if (entityCollision != null) {
            raycast = entityCollision;
        }
        return raycast;
    }
}
