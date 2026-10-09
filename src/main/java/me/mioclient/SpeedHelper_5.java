package me.mioclient;

import me.mioclient.Feature_14;
import me.mioclient.event.MoveEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.movement.Speed;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SpeedHelper_5.class */
public class SpeedHelper_5 extends SpeedHelper {
    public static AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public Box box;

    public SpeedHelper_5(Speed speed) {
        super(speed);
    }

    @Override // me.mioclient.SpeedHelper
    public void do242(MoveEvent moveEvent) {
        if (this.box != null && HoleSnapSearchHelper4_3.is2181()) {
            int i = 0;
            for (BlockEntity blockEntity : SearchHelper4_7.getList2427()) {
                if (blockEntity instanceof ShulkerBoxBlockEntity) {
                    ShulkerBoxBlockEntity shulkerBoxBlockEntity = (ShulkerBoxBlockEntity) blockEntity;
                    if (this.box.expand(0.5).intersects(shulkerBoxBlockEntity.getBoundingBox(Blocks.SHULKER_BOX.getDefaultState()).offset(blockEntity.getPos())) && shulkerBoxBlockEntity.getAnimationStage() != ShulkerBoxBlockEntity.AnimationStage.CLOSED) {
                        i += antiCheat.is238() ? 0 : 4;
                    }
                }
            }
            float intBitsToFloat = antiCheat.is238() ? 0.0f : 1.0f;
            for (Entity entity : minecraftClient.world.getEntities()) {
                if (!(entity instanceof Feature_14.OtherClientPlayerEntity) && entity != minecraftClient.player && entity.isPushable()) {
                    if (entity.getBoundingBox().intersects(this.box.expand(intBitsToFloat))) {
                        if (entity instanceof BoatEntity) {
                            i += 4;
                        }
                        i++;
                    }
                }
            }
            if (i == 0) {
                return;
            }
            moveEvent.do690(moveEvent.getVec3d689().add(moveEvent.getVec3d689().normalize().withAxis(Direction.Axis.Y, 0.0d).multiply(0.08 * i)));
        }
    }

    @Override // me.mioclient.SpeedHelper
    public void do388(MotionEvent motionEvent) {
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Post) {
            this.box = minecraftClient.player.getBoundingBox();
        }
    }
}
