package me.mioclient;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ESPSearchHelper4.class */
public class ESPSearchHelper4 implements SearchHelper_4 {
    public final Map<String, Integer> map = new TreeMap(Comparator.comparing(obj -> {
        return (String) obj;
    }).reversed());
    public final Box box;
    public Box box2;
    public float val;

    public ESPSearchHelper4(ItemEntity itemEntity) {
        float tickDelta = minecraftClient.getRenderTickCounter().getTickDelta(true);
        this.box = Box.of(itemEntity.getLerpedPos(tickDelta), 5.0, 5.0, 5.0);
        this.box2 = SearchHelper.getBox233((Entity) itemEntity, tickDelta);
        is1749(itemEntity);
    }

    public Box getBox1747() {
        return this.box;
    }

    public Box getBox1748() {
        return this.box2;
    }

    public boolean is1749(ItemEntity itemEntity) {
        if (!this.box.intersects(itemEntity.getBoundingBox())) {
            return false;
        }
        float tickDelta = minecraftClient.getRenderTickCounter().getTickDelta(true);
        String string = itemEntity.getName().getString();
        this.map.compute(string, (str, num) -> {
            return Integer.valueOf(itemEntity.getStack().getCount() + (num == null ? 0 : num.intValue()));
        });
        this.val = Math.max(FontsSearchHelper4.fontsSearchHelper4.get1316(getString1752(string, this.map.get(string).intValue())), this.val);
        if (new Vec3d(itemEntity.getX() - itemEntity.prevX, itemEntity.getY() - itemEntity.prevY, itemEntity.getZ() - itemEntity.prevZ).lengthSquared() >= 0.0001) {
            return true;
        }
        this.box2 = this.box2.union(SearchHelper.getBox233((Entity) itemEntity, tickDelta));
        return true;
    }

    public Map<String, Integer> getMap1750() {
        return this.map;
    }

    public float get1751() {
        return this.val;
    }

    public static String getString1752(String str, int i) {
        return i <= 1 ? str : new ArgumentTypeHelper().getArgumentTypeHelper2906(i).getArgumentTypeHelper2919(str).getString2921("\u0001 x\u0001");
    }
}
