package me.mioclient.module.combat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BooleanSetting;
import me.mioclient.NumberSetting;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/SelfTrap.class */
public class SelfTrap extends Range {
    public final Object object;
    public Object object2;
    public Object object3;
    public Object object4;

    public SelfTrap() {
        super("SelfTrap", "Covers you with blocks.", Category.COMBAT);
        this.object = getObject1082();
        do1076();
        unregister((Setting<?>) this.setting);
        unregister((Setting<?>) this.setting7);
        unregister((Setting<?>) this.setting8);
    }

    @Override // me.mioclient.module.combat.Range, me.mioclient.module.Module
    public String getInfo() {
        return null;
    }

    @Override // me.mioclient.module.combat.Range, me.mioclient.module.Delay
    public List<BlockPos> getList876() {
        List<BlockPos> list876 = super.getList876();
        if ((is1078() && getEntity1081() == null) || (is1077() && !BaritoneHelper_3.holeSnapSearchHelper4_5.is2728())) {
            return Collections.emptyList();
        }
        if (list876.isEmpty() && is1083()) {
            do495(false);
        }
        return list876;
    }

    @Override // me.mioclient.module.combat.Range
    public PlayerEntity getPlayerEntity886() {
        return minecraftClient.player;
    }

    public void do1076() {
        this.object2 = add(new BooleanSetting("OnlyHole", false));
        this.object3 = add(new BooleanSetting("Smart", false).getSetting2336());
        this.object4 = add(new NumberSetting("EnemyRange", Float.valueOf(3.0f), Float.valueOf(1.0f), Float.valueOf(6.0f), f -> {
            return is1079();
        }).getNumberSetting3023("m"));
    }

    public boolean is1077() {
        return ((Boolean) ((Setting) this.object2).getValue()).booleanValue();
    }

    public boolean is1078() {
        return ((Boolean) ((Setting) this.object3).getValue()).booleanValue();
    }

    public boolean is1079() {
        return ((Setting) this.object3).is623();
    }

    public float get1080() {
        return ((Float) ((Setting) this.object4).getValue()).floatValue();
    }

    public Entity getEntity1081() {
        Stream<Entity> filter = StreamSupport.stream(minecraftClient.world.getEntities().spliterator(), false).filter(entity -> {
            if (entity != minecraftClient.player && minecraftClient.player.distanceTo(entity) <= get1080() && (entity instanceof PlayerEntity)) {
                if (!BaritoneHelper_3.searchHelper4_14.is519(entity.getName().getString()) && entity.isAlive()) {
                    return true;
                }
            }
            return false;
        });
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
        Objects.requireNonNull(clientPlayerEntity);
        return (Entity) filter.min(Comparator.comparing(clientPlayerEntity::squaredDistanceTo)).orElse(null);
    }

    public Object getObject1082() {
        return add(new BooleanSetting("Complete", false).getSetting2342(this.setting8), this.setting8);
    }

    public boolean is1083() {
        return ((Boolean) ((Setting) this.object).getValue()).booleanValue();
    }
}
