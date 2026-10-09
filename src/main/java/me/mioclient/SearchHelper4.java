package me.mioclient;

import net.fabricmc.loader.api.FabricLoader;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SearchHelper4.class */
public class SearchHelper4 implements SearchHelper_4 {
    public static double val;
    public static boolean flag = FabricLoader.getInstance().isModLoaded("advanced-ui-scale");

    public static void do1478() {
        do1479(1.0f);
    }

    public static void do1479(float f) {
        if (is1482()) {
            return;
        }
        val = minecraftClient.getWindow().getScaleFactor();
        minecraftClient.getWindow().setScaleFactor(get1480() * f);
    }

    public static void do604() {
        if (is1482()) {
            return;
        }
        if (val == -1.0) {
            throw new UnsupportedOperationException();
        }
        minecraftClient.getWindow().setScaleFactor(val);
        val = -1.0;
    }

    public static float get1480() {
        return minecraftClient.getWindow().calculateScaleFactor(2, minecraftClient.forcesUnicodeFont());
    }

    public static double get1481(float f) {
        return is1482() ? 1.0 : (get1480() / minecraftClient.getWindow().getScaleFactor()) * f;
    }

    public static boolean is1482() {
        return flag;
    }
}
