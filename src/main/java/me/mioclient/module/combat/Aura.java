package me.mioclient.module.combat;

import java.awt.Color;
import java.util.Comparator;
import java.util.function.Predicate;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EnumSettingHelper;
import me.mioclient.FireworksHelper;
import me.mioclient.FreecamHelper;
import me.mioclient.HoleSnapEvent;
import me.mioclient.HoleSnapMode;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_6;
import me.mioclient.MatrixStackEvent;
import me.mioclient.MixinMessageIndicatorHelper_2;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.SearchHelper;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchHelper_3;
import me.mioclient.SearchHelper_4;
import me.mioclient.ZoomHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.feature.IllegalConstructorCall;
import me.mioclient.mixin.ducks.DuckPlayerMoveC2SPacket;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.combat.Criticals;
import me.mioclient.module.movement.FastSwim;
import me.mioclient.module.movement.Flight;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MaceItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/Aura.class */
public class Aura extends Module {
    public static final Criticals criticals = (Criticals) BaritoneHelper_3.baritoneHelper_4.getModule117(Criticals.class);
    public static final FastSwim fastSwim = (FastSwim) BaritoneHelper_3.baritoneHelper_4.getModule117(FastSwim.class);
    public static final Flight flight = (Flight) BaritoneHelper_3.baritoneHelper_4.getModule117(Flight.class);
    public static final AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public Setting<AuraMode_3> sorting;
    public Setting<Float> fov;
    public Setting<Float> delay;
    public Setting<Float> range;
    public Setting<Float> wallRange;
    public Setting<Boolean> tPSSync;
    public Setting<Boolean> pauseBaritone;
    public Setting<Boolean> rotate;
    public Setting<Float> limit;
    public Setting<Boolean> render;
    public Setting<Color> fill;
    public Setting<Color> outline;
    public Setting<Float> lineWidth;
    public Setting<Boolean> weapons;
    public Setting<AuraPredicateMode> weapon;
    public Setting<AuraMode> swap;
    public Setting<AuraMode_2> mace;
    public Setting<Boolean> lagBack;
    public Setting<Boolean> pauseEating;
    public Setting<Boolean> targets;
    public Setting<Boolean> players;
    public Setting<Boolean> ignoreNakeds;
    public Setting<Boolean> animals;
    public Setting<Boolean> hostiles;
    public Setting<Boolean> neutrals;
    public Setting<Boolean> crystals;
    public Setting<Boolean> projectiles;
    public Setting<Boolean> ignoreNamed;
    public final ZoomHelper zoomHelper;
    public int num;
    public boolean flag;
    public boolean flag2;
    public Entity entity;
    public Entity entity2;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/Aura$AuraMode.class */
    public enum AuraMode implements EnumSettingHelper {
        NORMAL("Normal"),
        SWAPBACK("SwapBack"),
        SILENT("Silent");

        public final String name;

        AuraMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/Aura$AuraMode_2.class */
    public enum AuraMode_2 implements EnumSettingHelper {
        NONE("None", itemStack -> {
            return false;
        }),
        BREACH("Breach", itemStack2 -> {
            return IllegalConstructorCall.is1416(Enchantments.BREACH, itemStack2);
        }),
        DENSITY("Density", itemStack3 -> {
            return IllegalConstructorCall.is1416(Enchantments.DENSITY, itemStack3) && SearchHelper_4.minecraftClient.player.fallDistance > 3.0f;
        }),
        SMART("Smart", itemStack4 -> {
            return (SearchHelper_4.minecraftClient.player.fallDistance > 3.0f || BaritoneHelper_3.antiPhaseSearchHelper4_2.get2231() <= 1) ? IllegalConstructorCall.is1416(Enchantments.DENSITY, itemStack4) : IllegalConstructorCall.is1416(Enchantments.BREACH, itemStack4);
        });

        public final Predicate<ItemStack> predicate;
        public final String name;

        AuraMode_2(String str, Predicate<ItemStack> predicate) {
            this.predicate = predicate;
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public boolean is774(ItemStack itemStack) {
            return this.predicate.test(itemStack);
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/Aura$AuraMode_3.class */
    public enum AuraMode_3 implements EnumSettingHelper {
        DISTANCE("Distance", Comparator.comparing((Entity entity) -> {
            return Float.valueOf(entity.distanceTo(SearchHelper_4.minecraftClient.player));
        })),
        CROSSHAIR("Crosshair", Comparator.comparing((Entity entity2) -> {
            return Float.valueOf(MathHelper.angleBetween(SearchHelper_4.minecraftClient.player.getYaw(), SearchHelper4_8.getFloatArray2483(entity2)[0]));
        })),
        HEALTH("Health", Comparator.comparing(SearchHelper_3::get644));

        public final String name;
        public final Comparator<Entity> comparator;

        AuraMode_3(String str, Comparator comparator) {
            this.name = str;
            this.comparator = comparator;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }

        public Comparator<Entity> getComparator1088() {
            return this.comparator;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/Aura$AuraPredicateMode.class */
    public enum AuraPredicateMode implements EnumSettingHelper {
        NONE("None"),
        REQUIRE("Require"),
        SWAP("Swap");

        public final String name;

        AuraPredicateMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public Aura() {
        super("Aura", "Attacks entities nearby.", Category.COMBAT, "killaura", "forcefield", "ka");
        PhaseESPHelper.do1351(this);
        this.zoomHelper = new ZoomHelper();
        this.num = -1;
        this.limit.getSetting2338("None", HoleSnapMode.MIN);
        this.pauseBaritone.do2344();
    }

    @Override // me.mioclient.module.Module
    public void onDisable() {
        if (is1469()) {
            this.num = -1;
        } else {
            do2391();
        }
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        this.entity = null;
        this.zoomHelper.do171(0.0f);
        BaritoneHelper_3.obstaclePasserHelper.do700(this);
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        if (this.entity == null || !(this.entity instanceof PlayerEntity)) {
            return null;
        }
        return this.entity.getName().getString();
    }

    @Listen
    public void onEvent2(HoleSnapEvent holeSnapEvent) {
        boolean z = (this.entity == null || this.entity.isAlive()) ? false : true;
        if ((!is2390(minecraftClient.player.getMainHandStack().getItem()) && this.weapon.getValue() == AuraPredicateMode.REQUIRE) || z) {
            this.entity = null;
            return;
        }
        if (minecraftClient.player.currentScreenHandler == minecraftClient.player.playerScreenHandler || !antiCheat.is238()) {
            this.entity = SearchHelper4_7.getEntity2441(this.players.getValue().booleanValue(), this.neutrals.getValue().booleanValue(), this.animals.getValue().booleanValue(), this.hostiles.getValue().booleanValue(), this.crystals.getValue().booleanValue(), this.projectiles.getValue().booleanValue(), this.wallRange.getValue().floatValue() + 0.5f, this.range.getValue().floatValue() + 0.5f, this.ignoreNamed.getValue().booleanValue(), this.ignoreNakeds.getValue().booleanValue(), this.sorting.getValue().getComparator1088(), this::is2393);
            BaritoneHelper_3.obstaclePasserHelper.do700(this);
            if (this.pauseBaritone.getValue().booleanValue() && this.entity != null) {
                BaritoneHelper_3.obstaclePasserHelper.do699(this);
            }
            if (this.entity == null) {
                do2391();
                return;
            }
            if (!SearchHelper4_8.is724() || is2388(this.entity) || minecraftClient.player.isFallFlying()) {
                do906();
                if (this.rotate.getValue().booleanValue()) {
                    if (SearchHelper4_8.is724() || this.limit.getValue().floatValue() == 0.0f || this.limit.getValue().floatValue() * this.delay.getValue().floatValue() <= get2389()) {
                        BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2487(SearchHelper4_8.getFloatArray2483(this.entity), BaritoneHelper_3.searchHelper4_8.get2474()), 5);
                    }
                }
            }
        }
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        PlayerMoveC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof PlayerMoveC2SPacket ? (PlayerMoveC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if (packet904 instanceof PlayerMoveC2SPacket) {
            PlayerMoveC2SPacket playerMoveC2SPacket = packet904;
            if (minecraftClient.player.fallDistance > 3.0f) {
                if (minecraftClient.world.isSpaceEmpty(minecraftClient.player.getBoundingBox().stretch(0.0d, minecraftClient.player.getVelocity().y * 1.1, 0.0d)) || !this.flag) {
                    return;
                }
                ((DuckPlayerMoveC2SPacket) playerMoveC2SPacket).setY(playerMoveC2SPacket.getY(0.0d) + 2.0);
                minecraftClient.player.setPos(minecraftClient.player.getX(), minecraftClient.player.getY() + 3.0, minecraftClient.player.getZ());
                this.flag = false;
            }
        }
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (this.render.getValue().booleanValue()) {
            this.zoomHelper.do169(this.entity == null ? 0.0f : 1.0f, 250L);
            float f = this.zoomHelper.get172();
            Box box1747 = getBox1747();
            if (box1747 == null || f == 0.0f) {
                return;
            }
            Color color816 = MixinMessageIndicatorHelper_2.getColor816(this.fill.getValue(), (int) (this.fill.getValue().getAlpha() * f));
            Color color8162 = MixinMessageIndicatorHelper_2.getColor816(this.outline.getValue(), (int) (this.outline.getValue().getAlpha() * f));
            PhaseESPSearchHelper4.do1590(inner_3.getMatrixStack472(), box1747, color816);
            PhaseESPSearchHelper4.do1593(inner_3.getMatrixStack472(), box1747, color8162, this.lineWidth.getValue().floatValue());
        }
    }

    public Box getBox1747() {
        Box box = null;
        Entity entity = this.entity;
        if (entity == null) {
            entity = this.entity2;
        }
        if (entity != null) {
            box = SearchHelper.getBox233(entity, SearchHelper_2.get536());
            this.entity2 = entity;
        }
        return box;
    }

    public void do906() {
        if (this.entity != null && is2388(this.entity)) {
            if (SearchHelper4_7.get2443(this.entity) > (HoleSnapSearchHelper4_6.is2788(this.entity) ? this.range.getValue() : this.wallRange.getValue()).floatValue()) {
                return;
            }
            boolean z = minecraftClient.player.fallDistance <= 0.0f && !minecraftClient.player.isOnGround();
            if (minecraftClient.player.isOnGround() && BaritoneHelper_3.antiPhaseSearchHelper4_2.get2231() <= 1) {
                z = true;
            }
            if (minecraftClient.player.hasVehicle()) {
                z = false;
            }
            if (!flight.isToggled() && !minecraftClient.player.getAbilities().flying && !minecraftClient.player.isFallFlying() && !HoleSnapSearchHelper4.is2007(minecraftClient.player) && !minecraftClient.player.isClimbing() && (this.entity instanceof LivingEntity) && z && (!criticals.isToggled() || criticals.mode.getValue() != Criticals.CriticalsMode.GRIM)) {
                if (!minecraftClient.player.hasStatusEffect(StatusEffects.LEVITATION)) {
                    return;
                }
            }
            int i = minecraftClient.player.getInventory().selectedSlot;
            int i2 = FireworksHelper.get448(itemStack -> {
                return (itemStack.getItem() instanceof AxeItem) || (itemStack.getItem() instanceof SwordItem);
            });
            boolean z2 = this.swap.getValue() == AuraMode.SILENT && is2394();
            if (is2394()) {
                if ((!z2 || this.pauseEating.getValue().booleanValue()) && i2 != i && minecraftClient.player.isUsingItem() && minecraftClient.player.getActiveHand() == Hand.MAIN_HAND) {
                    return;
                }
                FireworksHelper.do456(i2);
                if (i != i2) {
                    this.num = i;
                }
            }
            boolean isSprinting = minecraftClient.player.isSprinting();
            if (isSprinting) {
                minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
            }
            int i3 = get2392();
            LivingEntity livingEntity = (this.entity) instanceof LivingEntity ? (LivingEntity) (this.entity) : null;
            if ((livingEntity instanceof LivingEntity) && !HoleSnapSearchHelper4.is2013(livingEntity)) {
                i3 = -1;
            }
            if (i3 != -1) {
                FireworksHelper.do456(i3);
            }
            PhaseESPSearchHelper4_2.do3047(this.entity);
            minecraftClient.player.resetLastAttackedTicks();
            if (isSprinting) {
                minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
            }
            fastSwim.flag = false;
            if (i3 != -1) {
                if (this.lagBack.getValue().booleanValue()) {
                    this.flag = IllegalConstructorCall.is1416(Enchantments.DENSITY, minecraftClient.player.getInventory().getStack(i3));
                }
                FireworksHelper.do456(i);
            }
            if (!z2 || i2 == -1) {
                return;
            }
            FireworksHelper.do456(i);
        }
    }

    public boolean is2388(Entity entity) {
        if (!(this.entity instanceof PlayerEntity) && (entity instanceof LivingEntity)) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (SearchHelper_3.get644((Entity) livingEntity) <= 1.0 && livingEntity.getMaxHealth() <= 4.0f) {
                return true;
            }
        }
        return ((double) get2389()) >= ((double) this.delay.getValue().floatValue());
    }

    public float get2389() {
        double d = FreecamHelper.val2;
        if (this.tPSSync.getValue().booleanValue()) {
            d -= 20.0f - BaritoneHelper_3.holeSnapSearchHelper4_4.get2620();
        }
        return minecraftClient.player.getAttackCooldownProgress((float) d);
    }

    public boolean is2390(Item item) {
        return (item instanceof SwordItem) || (item instanceof TridentItem) || (item instanceof AxeItem) || (item instanceof MaceItem) || ((item instanceof PickaxeItem) && FireworksHelper.get448(itemStack -> {
            return (itemStack.getItem() instanceof SwordItem) || (itemStack.getItem() instanceof AxeItem);
        }) == -1);
    }

    public void do2391() {
        if (is2394() && this.swap.getValue() == AuraMode.SWAPBACK && this.num != -1) {
            FireworksHelper.do456(this.num);
            this.num = -1;
        }
    }

    public int get2392() {
        if (this.swap.getValue() == AuraMode.SILENT && is2394()) {
            return -1;
        }
        int i = FireworksHelper.get448(itemStack -> {
            return itemStack.isOf(Items.MACE) && this.mace.getValue().is774(itemStack);
        });
        return (this.mace.getValue() == AuraMode_2.SMART && minecraftClient.player.fallDistance > 3.0f && i == -1) ? FireworksHelper.get448(itemStack2 -> {
            return itemStack2.isOf(Items.MACE);
        }) : i;
    }

    public boolean is2393(Entity entity) {
        float[] floatArray2483 = SearchHelper4_8.getFloatArray2483(entity);
        return MathHelper.angleBetween(floatArray2483[0], minecraftClient.player.getYaw()) <= this.fov.getValue().floatValue() && Math.abs(minecraftClient.player.getPitch() - floatArray2483[1]) <= this.fov.getValue().floatValue();
    }

    public boolean is2394() {
        return this.weapon.getValue() == AuraPredicateMode.SWAP;
    }
}
