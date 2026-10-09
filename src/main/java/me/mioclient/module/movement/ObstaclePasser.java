package me.mioclient.module.movement;

import me.mioclient.ArgumentTypeHelper;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapMode;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.MixinLivingEntityHelper_2;
import me.mioclient.ObstaclePasserHelper_2;
import me.mioclient.ObstaclePasserMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_7;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.event.TickEvent_2;
import me.mioclient.event.TickPostEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.combat.AutoArmor;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/movement/ObstaclePasser.class */
public final class ObstaclePasser extends Module {
    public static final AutoArmor autoArmor = (AutoArmor) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoArmor.class);
    public static final ElytraFly elytraFly = (ElytraFly) BaritoneHelper_3.baritoneHelper_4.getModule117(ElytraFly.class);
    public final ObstaclePasserHelper_2 obstaclePasserHelper_2;
    public Setting<Boolean> setting;
    public Setting<Boolean> setting2;
    public Setting<Integer> setting3;
    public Setting<Integer> setting4;
    public final Stopwatch stopwatch;
    public float val;
    public BlockPos blockPos;
    public boolean flag;
    public boolean flag2;
    public boolean flag3;
    public int num;
    public ObstaclePasserMode obstaclePasserMode;

    public ObstaclePasser() {
        super("ObstaclePasser", new ArgumentTypeHelper().getArgumentTypeHelper2919(String.valueOf(Formatting.YELLOW)).getString2921("Passes obstacles in your way. \n\u0001Requires either ElytraFly or Speed enabled."), Category.MOVEMENT, new String[0]);
        this.obstaclePasserHelper_2 = new ObstaclePasserHelper_2(3);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.setting3.getSetting2338("~", HoleSnapMode.MIN);
        this.setting.do2339(() -> {
            this.obstaclePasserMode = null;
        });
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        if (is1469()) {
            do496();
            return;
        }
        this.flag = false;
        this.obstaclePasserMode = null;
        this.num = 0;
        this.val = Math.round(minecraftClient.player.getYaw());
        this.blockPos = minecraftClient.player.getBlockPos();
        this.obstaclePasserHelper_2.do978();
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        BaritoneHelper_3.obstaclePasserHelper.do706();
        if (this.flag && autoArmor.isToggled()) {
            autoArmor.elytra.do2333(true);
        }
        this.flag2 = false;
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (BaritoneHelper_3.obstaclePasserHelper.is702()) {
            return;
        }
        minecraftClient.player.setYaw(this.val);
    }

    @Listen(get219= Helper_7.num4)
    public void onTickPost(TickPostEvent tickPostEvent) {
        if (is926()) {
            this.stopwatch.reset();
        }
        do925();
        if (this.obstaclePasserMode == null) {
            this.obstaclePasserMode = ObstaclePasserMode.getObstaclePasserMode478(this.val);
        }
        if (this.num > 0) {
            this.num--;
        }
        this.obstaclePasserHelper_2.do466();
        do923();
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (channelRead0Event.getPacket904() instanceof PlayerPositionLookS2CPacket) {
            this.obstaclePasserHelper_2.do976();
        }
    }

    @Listen
    public void onTick(TickEvent_2 tickEvent_2) {
        if ((this.setting2.getValue().booleanValue() && this.stopwatch.is419(750L)) || is928()) {
            return;
        }
        if (is927() || (!minecraftClient.player.isFallFlying() && elytraFly.isToggled())) {
            tickEvent_2.getInput806().jumping = true;
        }
        tickEvent_2.getInput806().pressingForward = true;
        tickEvent_2.getInput806().movementForward = tickEvent_2.is808() ? tickEvent_2.get807() : 1.0f;
    }

    public void do923() {
        BlockPos blockPos710;
        if (BaritoneHelper_3.obstaclePasserHelper.is702() && (blockPos710 = BaritoneHelper_3.obstaclePasserHelper.getBlockPos710()) != null) {
            if (MixinLivingEntityHelper_2.get2583(minecraftClient.player.getPos(), blockPos710.toBottomCenterPos()) <= 1.0f) {
                BaritoneHelper_3.obstaclePasserHelper.do706();
                this.num = 10;
                if (elytraFly.isToggled()) {
                    this.flag3 = true;
                }
            }
        }
        if (!is924() || BaritoneHelper_3.obstaclePasserHelper.is702()) {
            return;
        }
        this.obstaclePasserHelper_2.do978();
        int y = this.setting3.getValue().intValue() == -1 ? this.blockPos.getY() : this.setting3.getValue().intValue();
        if (this.obstaclePasserMode != null && this.setting.getValue().booleanValue()) {
            BlockPos blockPos = null;
            for (int i = 5; i < this.setting4.getValue().intValue(); i++) {
                blockPos = this.obstaclePasserMode.function.apply(Integer.valueOf(i)).withY(y);
                if (((me.mioclient.mixin.ducks.DuckAbstractBlock) SearchHelper4_7.getBlock2449(blockPos.down())).isCollidable()) {
                    break;
                }
            }
            BaritoneHelper_3.obstaclePasserHelper.do704(blockPos);
            return;
        }
        Vec3d bottomCenterPos = this.blockPos.toBottomCenterPos();
        Vec3d vec3d930 = getVec3d930(this.val);
        BlockPos ofFloored = BlockPos.ofFloored(bottomCenterPos.add(vec3d930.multiply(MixinLivingEntityHelper_2.get2583(bottomCenterPos, minecraftClient.player.getPos()))));
        BlockPos blockPos2 = null;
        for (int i2 = 5; i2 < this.setting4.getValue().intValue(); i2++) {
            blockPos2 = ofFloored.add(BlockPos.ofFloored(vec3d930.multiply(i2)));
            if (((me.mioclient.mixin.ducks.DuckAbstractBlock) SearchHelper4_7.getBlock2449(blockPos2.down())).isCollidable()) {
                break;
            }
        }
        BaritoneHelper_3.obstaclePasserHelper.do704(blockPos2.withY(y));
    }

    public boolean is924() {
        if (this.num > 0) {
            return false;
        }
        return minecraftClient.player.horizontalCollision || this.obstaclePasserHelper_2.is977() || (!HoleSnapSearchHelper4.is955() && BaritoneHelper_3.antiPhaseSearchHelper4_2.get2231() > 3 && minecraftClient.player.isOnGround() && elytraFly.isToggled());
    }

    public void do925() {
        if (!BaritoneHelper_3.obstaclePasserHelper.is702()) {
            if (this.flag) {
                this.flag = false;
            }
        } else {
            if (this.flag) {
                return;
            }
            this.flag = true;
            AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY, 0);
            AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY, 0);
        }
    }

    public boolean is606() {
        return minecraftClient.player.getInventory().getArmorStack(EquipmentSlot.CHEST.getEntitySlotId()).isOf(Items.ELYTRA);
    }

    public boolean is926() {
        ClientChunkManager chunkManager = minecraftClient.world.getChunkManager();
        return ChunkPos.stream(minecraftClient.player.getChunkPos(), 2).allMatch(chunkPos -> {
            return chunkManager.isChunkLoaded(chunkPos.x, chunkPos.z);
        }) || !this.setting2.getValue().booleanValue();
    }

    public boolean is927() {
        return this.flag3 && elytraFly.isToggled();
    }

    public boolean is928() {
        return BaritoneHelper_3.obstaclePasserHelper.is702();
    }

    public boolean is929() {
        if (isToggled()) {
            return is928();
        }
        return false;
    }

    public Vec3d getVec3d930(float f) {
        return minecraftClient.player.getRotationVector(0.0f, f);
    }
}
