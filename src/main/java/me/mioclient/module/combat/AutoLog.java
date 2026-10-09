package me.mioclient.module.combat;

import java.text.DecimalFormat;
import java.util.Iterator;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.ArmorSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.FireworksHelper;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper_3;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.Listen;
import me.mioclient.event.RenderEvent;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.exploit.IllegalDisconnect;
import me.mioclient.module.misc.AutoReconnect;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/AutoLog.class */
public class AutoLog extends Module {
    public static final IllegalDisconnect illegaldisconnect = (IllegalDisconnect) BaritoneHelper_3.baritoneHelper_4.getModule117(IllegalDisconnect.class);
    public static AutoReconnect autoreconnect = (AutoReconnect) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoReconnect.class);
    public Setting<Integer> health;
    public Setting<Float> invincTimeout;
    public Setting<Boolean> autoDisable;
    public Setting<Boolean> gearedOnly;
    public Setting<Boolean> unfocusedOnly;
    public Setting<Boolean> onRender;
    public Setting<Boolean> crystal;
    public Setting<Boolean> tNTMinecart;
    public Setting<Boolean> totems;
    public Setting<Integer> totemCount;
    public Setting<Boolean> elytra;
    public Setting<Integer> durability;
    public Setting<Boolean> assumeInventory;
    public Setting<Boolean> yLevel;
    public Setting<Float> y;
    public Runnable runnable;
    public final Stopwatch stopwatch;

    public AutoLog() {
        super("AutoLog", "Logs you out so you don't have to fight anyone.", Category.COMBAT, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.runnable = null;
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return new DecimalFormat("0.0").format(this.health.getValue());
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (is1469() || minecraftClient.player.isSpectator() || minecraftClient.player.isInCreativeMode()) {
            return;
        }
        if (!this.gearedOnly.getValue().booleanValue() || HoleSnapSearchHelper4.is2013(minecraftClient.player)) {
            if (this.elytra.getValue().booleanValue() && is2366()) {
                do2367("Your elytra durability is too low!");
                return;
            }
            if (this.yLevel.getValue().booleanValue() && minecraftClient.player.getY() < this.y.getValue().floatValue()) {
                do2367(new ArgumentTypeHelper().getArgumentTypeHelper2919(String.valueOf(this.y.getValue())).getString2921("You are below Y level \u0001"));
                return;
            }
            if (minecraftClient.player.age / 20.0f < this.invincTimeout.getValue().floatValue()) {
                return;
            }
            if (this.onRender.getValue().booleanValue()) {
                for (PlayerEntity playerEntity : minecraftClient.world.getPlayers()) {
                    if (!BaritoneHelper_3.searchHelper4_14.is519(playerEntity.getName().getString()) && playerEntity != minecraftClient.player) {
                        do2367(new ArgumentTypeHelper().getArgumentTypeHelper2919(playerEntity.getName().getString()).getString2921("\u0001 has entered your render distance!"));
                    }
                }
            }
            if (!minecraftClient.world.getEntitiesByClass(TntMinecartEntity.class, minecraftClient.player.getBoundingBox().expand(4.0, 4.0, 4.0), (v0) -> {
                return v0.isPrimed();
            }).isEmpty() && this.tNTMinecart.getValue().booleanValue()) {
                do2367("You are at risk of getting blown up by a TNT minecart!");
            }
            if (this.crystal.getValue().booleanValue()) {
                minecraftClient.world.getEntities().forEach(entity -> {
                    if (!(entity instanceof EndCrystalEntity) || minecraftClient.player.squaredDistanceTo(entity) > 128.0) {
                        return;
                    }
                    Vec3d pos = entity.getPos();
                    LivingEntity livingEntity = minecraftClient.player;
                    if (ArmorSearchHelper4.get1900(pos, livingEntity, minecraftClient.player.getBoundingBox(), 6.0, true, (BlockPos) null, (BlockPos) null) >= SearchHelper_3.get643()) {
                        do2367("You are at risk of getting blown up by an end crystal!");
                    }
                });
            }
            int i = (int) SearchHelper_3.get643();
            if (minecraftClient.player.isDead() || i == 0) {
                return;
            }
            if (minecraftClient.isWindowFocused() && this.unfocusedOnly.getValue().booleanValue()) {
                return;
            }
            int sum = minecraftClient.player.getInventory().main.stream().filter(itemStack -> {
                return itemStack.getItem() == Items.TOTEM_OF_UNDYING;
            }).mapToInt((v0) -> {
                return v0.getCount();
            }).sum();
            if (minecraftClient.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) {
                sum += minecraftClient.player.getOffHandStack().getCount();
            }
            boolean z = sum <= this.totemCount.getValue().intValue();
            if (!this.totems.getValue().booleanValue()) {
                z = true;
            }
            if (i > this.health.getValue().intValue() || !z) {
                return;
            }
            do2367(new ArgumentTypeHelper().getArgumentTypeHelper2906(i).getString2921("Your health is too low! (\u0001)."));
        }
    }

    @Listen
    public void onRender(RenderEvent renderEvent) {
        if (is1469()) {
            this.runnable = null;
        }
        if (this.runnable == null || !this.stopwatch.is419(1500L)) {
            return;
        }
        this.runnable.run();
        this.runnable = null;
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (channelRead0Event.getPacket904() instanceof DisconnectS2CPacket) {
            this.runnable = null;
        }
    }

    public boolean is2366() {
        ItemStack armorStack = minecraftClient.player.getInventory().getArmorStack(EquipmentSlot.CHEST.getEntitySlotId());
        if (!armorStack.isOf(Items.ELYTRA) || !armorStack.isDamageable()) {
            return false;
        }
        if (FireworksHelper.get452(armorStack) > ((float) this.durability.getValue().intValue())) {
            return false;
        }
        if (!this.assumeInventory.getValue().booleanValue()) {
            return true;
        }
        Iterator it = minecraftClient.player.getInventory().main.iterator();
        while (it.hasNext()) {
            ItemStack itemStack = (ItemStack) it.next();
            if (itemStack.isOf(Items.ELYTRA) && (!itemStack.isDamageable() || FireworksHelper.get452(itemStack) > this.durability.getValue().intValue())) {
                return false;
            }
        }
        return true;
    }

    public void do2367(String str) {
        if (this.autoDisable.getValue().booleanValue()) {
            disable();
        }
        ClientConnection connection = minecraftClient.player.networkHandler.getConnection();
        if (connection != null) {
            BaritoneHelper_3.holeSnapSearchHelper4_4.flag3 = true;
            autoreconnect.disable();
            Runnable runnable = () -> {
                connection.disconnect(Text.empty().append(Text.of(new ArgumentTypeHelper().getArgumentTypeHelper2919(str).getArgumentTypeHelper2919(String.valueOf(Formatting.RED)).getArgumentTypeHelper2919(String.valueOf(Formatting.GRAY)).getString2921("\u0001[AutoLog] \u0001\u0001"))));
            };
            boolean z = (illegaldisconnect.ignoreProxy.getValue().booleanValue() && IllegalDisconnect.is642()) ? false : true;
            if (!illegaldisconnect.isToggled() || !z) {
                runnable.run();
                return;
            }
            IllegalDisconnect.do640();
            if (this.runnable == null) {
                this.stopwatch.reset();
            }
            this.runnable = runnable;
        }
    }
}
