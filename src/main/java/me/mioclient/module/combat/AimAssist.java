package me.mioclient.module.combat;

import java.util.Comparator;
import me.mioclient.FreecamHelper;
import me.mioclient.MatrixStackEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_2;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/AimAssist.class */
public class AimAssist extends Module {
    public Setting<Float> setting;
    public Setting<Float> setting2;
    public Setting<Float> setting3;
    public Setting<Boolean> setting4;
    public Setting<Boolean> setting5;
    public Setting<Boolean> setting6;
    public Setting<Boolean> setting7;
    public Setting<Boolean> setting8;
    public AbstractClientPlayerEntity abstractClientPlayerEntity;

    public AimAssist() {
        super("AimAssist", "Helps you aiming at players.", Category.COMBAT, new String[0]);
        PhaseESPHelper.do1351(this);
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (minecraftClient.currentScreen != null) {
            return;
        }
        if ((minecraftClient.player.getMainHandStack().getItem() instanceof AxeItem) || (minecraftClient.player.getMainHandStack().getItem() instanceof SwordItem) || !this.setting4.getValue().booleanValue()) {
            if (this.abstractClientPlayerEntity == null || !this.setting6.getValue().booleanValue()) {
                this.abstractClientPlayerEntity = (AbstractClientPlayerEntity) minecraftClient.world.getPlayers().stream().sorted(Comparator.comparing(abstractClientPlayerEntity -> {
                    return Float.valueOf(minecraftClient.player.distanceTo((Entity) abstractClientPlayerEntity));
                })).filter((v1) -> {
                    return is3081(v1);
                }).findFirst().orElse(null);
            }
            if (!is3081(this.abstractClientPlayerEntity)) {
                this.abstractClientPlayerEntity = null;
                return;
            }
            if (minecraftClient.targetedEntity == this.abstractClientPlayerEntity) {
                return;
            }
            if (this.setting8.getValue().booleanValue()) {
                float[] floatArray2484 = SearchHelper4_8.getFloatArray2484(this.abstractClientPlayerEntity.getBoundingBox().getCenter());
                minecraftClient.player.setYaw(floatArray2484[0]);
                minecraftClient.player.setPitch(floatArray2484[1]);
                return;
            }
            double d = get3082(this.abstractClientPlayerEntity);
            if (d > 1.0 || d < -1.0) {
                minecraftClient.player.setYaw(minecraftClient.player.getYaw(SearchHelper_2.get536()) - ((float) ((d * this.setting2.getValue().floatValue()) * 0.009999999776482582)));
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x00d6, code lost:
    
        if (me.mioclient.BaritoneHelper_3.searchHelper4_14.is519(((net.minecraft.entity.player.PlayerEntity) r5).getGameProfile().getName()) == false) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean is3081(Entity entity) {
        if ((entity instanceof PlayerEntity) && entity != minecraftClient.player && entity.isAlive() && (!this.setting5.getValue().booleanValue() || !entity.isInvisible())) {
            if (is3083(entity, this.setting3.getValue().floatValue()) && minecraftClient.player.distanceTo(entity) <= this.setting.getValue().floatValue()) {
                if (this.setting7.getValue().booleanValue()) {
                }
                if (is2788(entity)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean is2788(Entity entity) {
        Vec3d vec3d = new Vec3d(minecraftClient.player.getX(), minecraftClient.player.getY() + minecraftClient.player.getStandingEyeHeight(), minecraftClient.player.getZ());
        if (minecraftClient.world.raycast(new RaycastContext(vec3d, new Vec3d(entity.getX(), entity.getY(), entity.getZ()), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, minecraftClient.player)).getType() == HitResult.Type.MISS) {
            return true;
        }
        return minecraftClient.world.raycast(new RaycastContext(vec3d, new Vec3d(entity.getX(), entity.getY() + ((double) entity.getStandingEyeHeight()), entity.getZ()), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, minecraftClient.player)).getType() == HitResult.Type.MISS;
    }

    public static double get3082(Entity entity) {
        return ((((minecraftClient.player.getYaw(SearchHelper_2.get536()) - get3084(entity)) % FreecamHelper.num3) + 540.0) % FreecamHelper.num3) - 180.0;
    }

    public static boolean is3083(Entity entity, double d) {
        double d2 = (float) (d * FreecamHelper.val2);
        double yaw = ((((minecraftClient.player.getYaw(SearchHelper_2.get536()) - get3084(entity)) % FreecamHelper.num3) + 540.0) % FreecamHelper.num3) - 180.0;
        return (yaw > 0.0d && yaw < d2) || ((-d2) < yaw && yaw < 0.0d);
    }

    public static float get3084(Entity entity) {
        return (float) (Math.atan2(entity.getX() - minecraftClient.player.getX(), entity.getZ() - minecraftClient.player.getZ()) * 57.2957795 * -1.0);
    }
}
