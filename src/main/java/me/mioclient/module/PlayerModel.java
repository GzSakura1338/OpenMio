package me.mioclient.module;

import me.mioclient.feature.Size;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/PlayerModel.class */
public class PlayerModel extends me.mioclient.ModuleList {
    public static boolean flag = false;

    public PlayerModel() {
        super("PlayerModel", new String[0]);
        Size size = new Size(this);
        size.do2637(this);
        do3019(size);
    }

    @Override // me.mioclient.ModuleList
    public void do364(DrawContext drawContext) {
        LivingEntity livingEntity = minecraftClient.player;
        Quaternionf rotateZ = new Quaternionf().rotateZ(3.1415927410125732f);
        Quaternionf rotateX = new Quaternionf().rotateX(0.0f);
        rotateZ.mul((Quaternionfc) rotateX);
        Vector3f vector3f = new Vector3f(0.0f, ((ClientPlayerEntity) livingEntity).getHeight() / 2.0f, 0.0f);
        flag = true;
        InventoryScreen.drawEntity(drawContext, 25.0f, 40.0f, 35.0f, vector3f, rotateZ, rotateX, livingEntity);
        flag = false;
    }

    @Override // me.mioclient.ModuleList
    public float[] getFloatArray365() {
        return new float[]{50.0f, 80.0f};
    }
}
