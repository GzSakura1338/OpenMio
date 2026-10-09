package me.mioclient.module.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.EntityControlSearchHelper4;
import me.mioclient.EntityListObjectSetting;
import me.mioclient.FireworksHelper;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapMode;
import me.mioclient.MainhandHelper;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.OffhandMode;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper_3;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.InteractBlockEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.RenderEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.player.NoInteract;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/combat/Offhand.class */
public class Offhand extends Module {
    public static final AutoCrystal autoCrystal = (AutoCrystal) BaritoneHelper_3.baritoneHelper_4.getModule117(AutoCrystal.class);
    public static final Offhand offhand = (Offhand) BaritoneHelper_3.baritoneHelper_4.getModule117(Offhand.class);
    public static final NoInteract noInteract = (NoInteract) BaritoneHelper_3.baritoneHelper_4.getModule117(NoInteract.class);
    public static AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public Setting<OffhandMode> item;
    public Setting<Integer> delay;
    public Setting<Float> health;
    public Setting<Boolean> lethal;
    public Setting<Boolean> f117;
    public Setting<Boolean> containers;
    public Setting<Boolean> gappleBind;
    public Setting<Float> safe;
    public Setting<Boolean> pickaxe;
    public Setting<Boolean> crystals;
    public Setting<Boolean> totems;
    public Setting<Boolean> crapple;
    public Setting<Set<Item>> custom;
    public final Stopwatch stopwatch;
    public final Stopwatch stopwatch2;
    public final Stopwatch stopwatch3;
    public final Stopwatch stopwatch4;
    public final List<Long> list;
    public int num;
    public boolean flag;
    public boolean flag2;
    public boolean flag3;
    public final MainhandHelper mainhandHelper;
    public static boolean flag4;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/combat/Offhand$Inner.class */
    class Inner extends MainhandHelper {
        public Inner() {
        }

        @Override // me.mioclient.MainhandHelper
        public boolean is465() {
            if (minecraftClient.player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                return false;
            }
            return Offhand.this.lethal.getValue().booleanValue() || Offhand.this.health.is2327();
        }
    }

    public Offhand() {
        super("Offhand", "Manages your offhand automatically.", Category.COMBAT, "autototem");
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        this.stopwatch2 = new Stopwatch();
        this.stopwatch3 = new Stopwatch();
        this.stopwatch4 = new Stopwatch();
        this.list = new ArrayList();
        this.num = -1;
        this.mainhandHelper = new Inner();
        this.health.getSetting2338("Adaptive", HoleSnapMode.MIN);
        this.lethal.do2343(bool -> {
            return !this.health.is2327();
        });
        this.gappleBind.do2329("GappleBind");
        this.safe.do2329("SafeHealth");
        do2666(this.custom);
    }

    @Override // me.mioclient.module.Module
    public String getInfo() {
        return this.item.getValue().toString();
    }

    @Override // me.mioclient.module.Module
    public void onToggle() {
        this.num = -1;
    }

    @Listen(get219= Helper_7.num2)
    public void onRender(RenderEvent renderEvent) {
        this.flag2 = renderEvent.is168();
        do869();
    }

    public void do869() {
        if (is1469()) {
            return;
        }
        if (minecraftClient.player.isAlive()) {
            this.flag3 = false;
        }
        flag4 = false;
        this.mainhandHelper.do466();
        if (!is2668() && is2669()) {
            if (this.stopwatch.is419(this.delay.getValue().intValue())) {
                this.list.removeIf(l -> {
                    return l == null || System.currentTimeMillis() > l.longValue() + 1000;
                });
                if (this.list.size() > 4) {
                    this.stopwatch2.reset();
                }
                do906();
            }
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        EntityStatusS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof EntityStatusS2CPacket ? (EntityStatusS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof EntityStatusS2CPacket) {
            EntityStatusS2CPacket entityStatusS2CPacket = packet904;
            if (!is2668() && is2669() && entityStatusS2CPacket.getEntity(minecraftClient.world) == minecraftClient.player && entityStatusS2CPacket.getStatus() == 35) {
                if (!minecraftClient.player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                    if (minecraftClient.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                        minecraftClient.player.getOffHandStack().decrement(1);
                    }
                }
                this.stopwatch4.reset();
                this.list.clear();
                do906();
            }
        }
        EntitySpawnS2CPacket packet9042 = (channelRead0Event.getPacket904()) instanceof EntitySpawnS2CPacket ? (EntitySpawnS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet9042 instanceof EntitySpawnS2CPacket) {
            EntitySpawnS2CPacket entitySpawnS2CPacket = packet9042;
            if (this.mainhandHelper.is465() && entitySpawnS2CPacket.getEntityType() == EntityType.END_CRYSTAL) {
                if (this.mainhandHelper.is469(new Vec3d(entitySpawnS2CPacket.getX(), entitySpawnS2CPacket.getY(), entitySpawnS2CPacket.getZ()))) {
                    this.stopwatch3.reset();
                }
            }
        }
        if (channelRead0Event.getPacket904() instanceof DeathMessageS2CPacket) {
            if (minecraftClient.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                do2667();
            }
        }
    }

    @Listen
    public void onInteractBlock(InteractBlockEvent interactBlockEvent) {
        if (interactBlockEvent.getHand2084() != Hand.MAIN_HAND || getOffhandMode2663((OffhandMode) null) == null || antiCheat.is238()) {
            return;
        }
        minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
        interactBlockEvent.do1162();
    }

    public OffhandMode getOffhandMode2661() {
        if (autoCrystal.isToggled() && autoCrystal.forceSuicide.getValue().booleanValue() && this.item.getValue() != OffhandMode.Totem) {
            return OffhandMode.Crystal;
        }
        boolean booleanValue = this.crapple.getValue().booleanValue();
        OffhandMode offhandMode = OffhandMode.Totem;
        if (this.mainhandHelper.is467()) {
            return OffhandMode.Totem;
        }
        if (SearchHelper_3.get643() > this.health.getValue().floatValue() && minecraftClient.player.fallDistance < 16.0f && FireworksHelper.get449(itemStack -> {
            return itemStack.isOf(this.item.getValue().getItem1241(booleanValue));
        }) > 0) {
            offhandMode = this.item.getValue();
        }
        return getOffhandMode2663(offhandMode);
    }

    public boolean is2662(Item item) {
        if ((item instanceof SwordItem) || (item instanceof AxeItem)) {
            return true;
        }
        if ((item instanceof PickaxeItem) && this.pickaxe.getValue().booleanValue()) {
            return true;
        }
        if (item == Items.TOTEM_OF_UNDYING && this.totems.getValue().booleanValue()) {
            return true;
        }
        return item == Items.END_CRYSTAL && this.crystals.getValue().booleanValue();
    }

    public OffhandMode getOffhandMode2663(OffhandMode offhandMode) {
        if (offhandMode != null) {
            if (offhandMode.getItem1241(this.crapple.getValue().booleanValue()) instanceof ShieldItem) {
                return offhandMode;
            }
        }
        if (SearchHelper4_7.is2450(minecraftClient.crosshairTarget) && !minecraftClient.player.isSneaking() && !noInteract.isToggled()) {
            return offhandMode;
        }
        float f = SearchHelper_3.get643();
        Item item = minecraftClient.player.getMainHandStack().getItem();
        boolean z = this.safe.getValue().floatValue() < f;
        if (item == Items.TOTEM_OF_UNDYING) {
            z = true;
        }
        return (this.gappleBind.getValue().booleanValue() && minecraftClient.options.useKey.isPressed() && z && is2662(item) && minecraftClient.currentScreen == null && this.item.getValue() != OffhandMode.Gapple) ? OffhandMode.Gapple : offhandMode;
    }

    public void do2664() {
        this.list.add(Long.valueOf(System.currentTimeMillis()));
        BaritoneHelper_3.fireworksHelperSearchHelper4.do2630(true);
        if (BaritoneHelper_3.holeSnapSearchHelper4_4.is2624()) {
            AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING, 0);
            this.flag = true;
        }
    }

    public void do2665() {
        BaritoneHelper_3.fireworksHelperSearchHelper4.do2630(false);
        if (!(minecraftClient.currentScreen instanceof HandledScreen)) {
            BaritoneHelper_3.fireworksHelperSearchHelper4.close();
        }
        if (this.flag) {
            AutoSignSearchHelper4.do2567(minecraftClient.player, ClientCommandC2SPacket.Mode.START_SPRINTING, 0);
            this.flag = false;
        }
    }

    public void do906() {
        OffhandMode offhandMode2661 = getOffhandMode2661();
        int i = offhandMode2661.get1240(this.crapple.getValue().booleanValue());
        if (this.num != -1 && (!(minecraftClient.currentScreen instanceof HandledScreen) || (minecraftClient.currentScreen instanceof InventoryScreen))) {
            if (minecraftClient.player.getInventory().getStack(this.num >= 36 ? this.num - 36 : this.num).isOf(offhandMode2661.getItem1241(this.crapple.getValue().booleanValue())) && i != -1) {
                i = this.num;
            }
        }
        if (i == -1) {
            return;
        }
        if ((this.containers.getValue().booleanValue() || !((minecraftClient.currentScreen instanceof ShulkerBoxScreen) || (minecraftClient.currentScreen instanceof GenericContainerScreen))) && i < minecraftClient.player.currentScreenHandler.slots.size()) {
            ItemStack cursorStack = minecraftClient.player.currentScreenHandler.getCursorStack();
            ItemStack stack = minecraftClient.player.currentScreenHandler.getSlot(i).getStack();
            if (!stack.contains(DataComponentTypes.FOOD) || this.flag2) {
                do2664();
                int i2 = minecraftClient.player.currentScreenHandler.syncId;
                if (!cursorStack.isEmpty() && !FireworksHelper.is454()) {
                    minecraftClient.interactionManager.clickSlot(i2, FireworksHelper.get443(Items.AIR), 0, SlotActionType.PICKUP, minecraftClient.player);
                }
                if (this.f117.getValue().booleanValue() && minecraftClient.player.currentScreenHandler.getCursorStack().isEmpty()) {
                    this.num = i;
                    minecraftClient.interactionManager.clickSlot(i2, i, 40, SlotActionType.SWAP, minecraftClient.player);
                    this.stopwatch.reset();
                } else {
                    do438(i);
                    this.num = i;
                }
                flag4 = true;
                do2665();
                if (EntityControlSearchHelper4.is1538(minecraftClient.options.useKey) && stack.contains(DataComponentTypes.FOOD)) {
                    minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
                }
            }
        }
    }

    public void do438(int i) {
        if (this.stopwatch.is419(this.delay.getValue().intValue()) && minecraftClient.player.currentScreenHandler.slots.size() > 45) {
            FireworksHelper.do441(i, 45);
            this.stopwatch.reset();
        }
    }

    public void do2666(Setting<Set<Item>> setting) {
        setting.do2339(() -> {
            EntityListObjectSetting entityListObjectSetting = (EntityListObjectSetting) setting;
            if (entityListObjectSetting.getObject3134() == null) {
                return;
            }
            ((Set) setting.getValue()).clear();
            ((Set) setting.getValue()).add((Item) entityListObjectSetting.getObject3134());
        });
    }

    public void do2667() {
        long j = this.stopwatch4.get422();
        int i = BaritoneHelper_3.holeSnapSearchHelper4_4.get1730();
        if (this.flag3) {
            return;
        }
        Text append = Text.literal("Since last pop: %dms. Latency: %dms.".formatted(Long.valueOf(j), Integer.valueOf(i))).append(Text.literal(" Possible death reasons: Damage override/Lag spike.*").styled(style -> {
            return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("More info in the game log.")));
        }));
        if (i > j) {
            MixinMessageIndicatorHelper.do345(append, MixinMessageIndicatorHelper.getMessageSignatureData337((Math.abs(getName().hashCode()) * (-1)) - 1), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode);
            System.out.println("*Death was most likely caused by you taking damage before the server receives the update slot packet, making you die holding a totem client-side.");
            this.flag3 = true;
        }
    }

    public boolean is2668() {
        return autoCrystal.isToggled() && autoCrystal.forceSuicide.getValue().booleanValue() && this.item.getValue() == OffhandMode.Totem;
    }

    public boolean is2669() {
        if (this.f117.getValue().booleanValue()) {
            return true;
        }
        return ((minecraftClient.currentScreen instanceof CreativeInventoryScreen) || minecraftClient.player.playerScreenHandler != minecraftClient.player.currentScreenHandler || (minecraftClient.currentScreen instanceof GenericContainerScreen)) ? false : true;
    }

    public static boolean is929() {
        return flag4 && offhand.isToggled();
    }
}
