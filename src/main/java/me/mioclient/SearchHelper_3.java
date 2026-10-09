package me.mioclient;

import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PiglinActivity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.WolfVariant;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SearchHelper_3.class */
public class SearchHelper_3 implements SearchHelper_4 {
    public static float get643() {
        return get644(minecraftClient.player);
    }

    public static float get644(Entity entity) {
        if (!(entity instanceof LivingEntity)) {
            return 2.0f;
        }
        LivingEntity livingEntity = (LivingEntity) entity;
        return livingEntity.getHealth() + livingEntity.getAbsorptionAmount();
    }

    public static boolean is645(Entity entity) {
        if (entity instanceof EndermanEntity) {
            return ((EndermanEntity) entity).isAngry();
        }
        if (entity instanceof Helper_3) {
            return ((Helper_3) entity).mio$isAttacking();
        }
        if (entity instanceof WolfEntity) {
            WolfEntity wolfEntity = (WolfEntity) entity;
            return wolfEntity.getTextureId() == ((WolfVariant) wolfEntity.getVariant().value()).getAngryTextureId();
        }
        if (entity instanceof Angerable) {
            return ((Angerable) entity).hasAngerTime();
        }
        if (entity instanceof AbstractPiglinEntity) {
            PiglinActivity activity = ((AbstractPiglinEntity) entity).getActivity();
            return activity == PiglinActivity.CROSSBOW_CHARGE || activity == PiglinActivity.CROSSBOW_HOLD || activity == PiglinActivity.ATTACKING_WITH_MELEE_WEAPON;
        }
        if (entity instanceof PolarBearEntity) {
            return true;
        }
        return entity instanceof Monster;
    }

    public static boolean is646(Entity entity) {
        return (entity instanceof FishEntity) || (entity instanceof PassiveEntity) || (entity instanceof SquidEntity) || (entity instanceof DolphinEntity);
    }

    public static boolean is647(LivingEntity livingEntity) {
        return !minecraftClient.world.isBlockSpaceEmpty((Entity) livingEntity, livingEntity.getBoundingBox().stretch(0.0d, -0.1, 0.0d));
    }

    public static boolean is648(Entity entity) {
        return minecraftClient.options.getPerspective() == Perspective.FIRST_PERSON && entity != minecraftClient.player && minecraftClient.cameraEntity == entity;
    }

    public static void do649(LivingEntity livingEntity, float f) {
        livingEntity.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(f);
    }
}
