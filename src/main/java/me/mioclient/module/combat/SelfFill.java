package me.mioclient.module.combat;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.FreecamHelper;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_3;
import me.mioclient.KeyPearlMode;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.NewChunksHelper_4;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.ScaffoldMode_2;
import me.mioclient.SearchHelper;
import me.mioclient.SearchHelper4_8;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/SelfFill.class */
public class SelfFill extends Module {
    public Setting<ScaffoldMode_2> mode;
    public Setting<Boolean> rotate;
    public Setting<Boolean> persistent;
    public Setting<Boolean> jumpDisable;
    public Setting<Set<Block>> whitelist;
    public Setting<Boolean> attack;
    public Setting<Boolean> noLagBack;
    public Entity entity;
    public boolean flag;
    public double val;
    public final List<Double> list;

    public SelfFill() {
        super("SelfFill", "Places solid blocks inside you blocking your lower hitbox.", Category.COMBAT, "burrow");
        PhaseESPHelper.do1351(this);
        this.list = List.of(Double.valueOf(0.4), Double.valueOf(0.75), Double.valueOf(1.01), Double.valueOf(1.15));
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        if (is1469()) {
            disable();
        } else {
            this.val = minecraftClient.player.getY();
            this.flag = false;
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (minecraftClient.player.getY() > this.val && this.persistent.getValue().booleanValue() && this.jumpDisable.getValue().booleanValue()) {
            do496();
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        PlayerPositionLookS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof PlayerPositionLookS2CPacket ? (PlayerPositionLookS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof PlayerPositionLookS2CPacket) {
            PlayerPositionLookS2CPacket playerPositionLookS2CPacket = packet904;
            if (minecraftClient.player.getPos().squaredDistanceTo(new Vec3d(playerPositionLookS2CPacket.getX(), playerPositionLookS2CPacket.getY(), playerPositionLookS2CPacket.getZ())) > 9.0) {
                this.val = playerPositionLookS2CPacket.getY();
            }
        }
    }

    @Listen
    public void do31(MotionEvent motionEvent) {
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Post || minecraftClient.player.hasVehicle()) {
            return;
        }
        int i = 0;
        int i2 = -8;
        while (true) {
            if (i2 >= 8) {
                break;
            }
            if (i2 != 0 && i2 != 1) {
                BlockPos add = BlockPos.ofFloored(minecraftClient.player.getPos()).add(0, i2, 0);
                if (minecraftClient.world.getBlockState(add).getCollisionShape(minecraftClient.world, add).isEmpty()) {
                    i = i2;
                    break;
                }
            }
            i2++;
        }
        BlockPos blockPos1333 = HoleSnapSearchHelper4.getBlockPos1333();
        if (minecraftClient.world.getEntitiesByClass(PlayerEntity.class, new Box(blockPos1333), playerEntity -> {
            return playerEntity != minecraftClient.player;
        }).isEmpty()) {
            BlockState blockState = minecraftClient.world.getBlockState(blockPos1333);
            Box expand = minecraftClient.player.getBoundingBox().expand(-SearchHelper.val);
            if (((BlockPos.stream(expand.withMaxY(expand.minY)).count() != 1) && HoleSnapSearchHelper4_3.is2181()) || !blockState.isReplaceable() || blockState.isLiquid() || !minecraftClient.player.isOnGround() || i == 0) {
                return;
            }
            if (this.attack.getValue().booleanValue()) {
                this.entity = PhaseESPSearchHelper4_2.getEntity3046(blockPos1333, 0);
            }
            if (this.entity != null) {
                return;
            }
            int i3 = FireworksHelper.get448(itemStack -> {
                BlockItem item = (itemStack.getItem()) instanceof BlockItem ? (BlockItem) (itemStack.getItem()) : null;
                if (item instanceof BlockItem) {
                    BlockItem blockItem = item;
                    if (this.mode.getValue().is1392(blockItem.getBlock(), this.whitelist)) {
                        return true;
                    }
                }
                return false;
            });
            int i4 = minecraftClient.player.getInventory().selectedSlot;
            if (i3 == -1) {
                MixinMessageIndicatorHelper.do345(Text.literal(getName()).append(" is out of blocks!"), MixinMessageIndicatorHelper.getMessageSignatureData337(-2), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode);
                disable();
                return;
            }
            Direction direction3028 = PhaseESPSearchHelper4_2.getDirection3028(blockPos1333);
            if (direction3028 == null) {
                return;
            }
            float[] fArr = {motionEvent.get751(), FreecamHelper.num2};
            if (this.entity != null && this.rotate.getValue().booleanValue()) {
                fArr = SearchHelper4_8.getFloatArray2483(this.entity);
            }
            int i5 = i;
            NewChunksHelper_4.do2149(() -> {
                do3075(blockPos1333, direction3028, i4, i3, i5);
            });
            if (this.rotate.getValue().booleanValue()) {
                BaritoneHelper_3.searchHelper4_8.do2477(fArr, 101);
            }
            if (!this.flag || this.persistent.getValue().booleanValue()) {
                return;
            }
            this.flag = false;
            disable();
        }
    }

    public void do3075(BlockPos blockPos, Direction direction, int i, int i2, int i3) {
        boolean z = true;
        int i4 = 1;
        while (true) {
            if (i4 >= 3) {
                break;
            }
            if (!minecraftClient.world.getBlockState(blockPos.up(i4)).isReplaceable()) {
                z = false;
                break;
            }
            i4++;
        }
        if (z) {
            minecraftClient.player.setSneaking(false);
            minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
            FireworksHelper.do456(i2);
            boolean isCollidable = ((me.mioclient.mixin.ducks.DuckAbstractBlock) ((BlockItem) minecraftClient.player.getMainHandStack().getItem()).getBlock()).isCollidable();
            if (isCollidable) {
                Iterator<Double> it = this.list.iterator();
                while (it.hasNext()) {
                    minecraftClient.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(minecraftClient.player.getX(), minecraftClient.player.getY() + it.next().doubleValue(), minecraftClient.player.getZ(), minecraftClient.player.getYaw(), FreecamHelper.num2, false));
                }
            }
            Box boundingBox = minecraftClient.player.getBoundingBox();
            minecraftClient.player.setBoundingBox(new Box(0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d));
            if (this.noLagBack.getValue().booleanValue()) {
                PhaseESPSearchHelper4_2.is3036(blockPos, (Vec3d) null, direction, false, Hand.MAIN_HAND);
            } else {
                PhaseESPSearchHelper4_2.is3037(blockPos, direction, false, Hand.MAIN_HAND);
            }
            minecraftClient.player.setBoundingBox(boundingBox);
            if (isCollidable) {
                do3076(i3);
            }
            FireworksHelper.do456(i);
            minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
            this.flag = true;
        }
    }

    public void do3076(double d) {
        if (!this.noLagBack.getValue().booleanValue()) {
            minecraftClient.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(minecraftClient.player.getX(), minecraftClient.player.getY() + d, minecraftClient.player.getZ(), minecraftClient.player.getYaw(), FreecamHelper.num2, false));
        } else {
            minecraftClient.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(minecraftClient.player.getX(), minecraftClient.player.getY() + 1.0, minecraftClient.player.getZ(), minecraftClient.player.getYaw(), FreecamHelper.num2, true));
            BaritoneHelper_3.tooltipsSearchHelper4_2.do164(() -> {
                minecraftClient.player.setPosition(minecraftClient.player.getX(), minecraftClient.player.getY() + 1.0, minecraftClient.player.getZ());
            }, 1);
        }
    }
}
