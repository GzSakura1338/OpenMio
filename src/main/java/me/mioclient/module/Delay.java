package me.mioclient.module;

import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import me.mioclient.AntiPhaseSearchHelper4;
import me.mioclient.AutoCraftMode;
import me.mioclient.AutoCrystalMode_6;
import me.mioclient.AutoSignSearchHelper4;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.BooleanSetting;
import me.mioclient.ColorSetting;
import me.mioclient.FireworksHelper;
import me.mioclient.FreecamHelper;
import me.mioclient.Helper_7;
import me.mioclient.HoleSnapEvent;
import me.mioclient.HoleSnapHelper;
import me.mioclient.HoleSnapHelper_2;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.HoleSnapSearchHelper4_6;
import me.mioclient.KeyPearlMode;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.NewChunksHelper_4;
import me.mioclient.NumberSetting;
import me.mioclient.PhaseESPSearchHelper4_2;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchHelper4_8;
import me.mioclient.SearchHelper_4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.FinishUsingEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.MoveEvent_2;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.client.AntiCheat;
import me.mioclient.module.combat.Offhand;
import me.mioclient.module.exploit.ChorusControl;
import me.mioclient.module.movement.NoSlow;
import me.mioclient.module.player.SpeedMine;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/Delay.class */
public abstract class Delay extends Module {
    public static final AntiCheat antiCheat = (AntiCheat) BaritoneHelper_3.baritoneHelper_4.getModule117(AntiCheat.class);
    public static final ChorusControl chorusControl = (ChorusControl) BaritoneHelper_3.baritoneHelper_4.getModule117(ChorusControl.class);
    public static final NoSlow noslow = (NoSlow) BaritoneHelper_3.baritoneHelper_4.getModule117(NoSlow.class);
    public Setting<Integer> setting;
    public Setting<Integer> setting2;
    public Setting<Boolean> setting3;
    public Setting<Boolean> setting4;
    public Setting<Boolean> setting5;
    public Setting<Boolean> setting6;
    public Setting<Boolean> setting7;
    public Setting<Boolean> setting8;
    public Setting<Boolean> setting9;
    public Setting<Boolean> setting10;
    public Setting<Boolean> setting11;
    public Setting<Boolean> setting12;
    public Setting<Boolean> setting13;
    public Setting<Color> setting14;
    public Setting<Color> setting15;
    public Setting<Float> setting16;
    public Setting<Boolean> setting17;
    public Setting<Float> setting18;
    public final Stopwatch stopwatch;
    public final Stopwatch stopwatch2;
    public boolean flag;
    public Vec3d vec3d;
    public net.minecraft.util.math.Direction direction;
    public Entity entity;
    public int num;
    public int num2;
    public double val;
    public boolean flag2;
    public boolean flag3;
    public boolean flag4;
    public boolean flag5;
    public boolean flag6;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/Delay$Record.class */
    public static final class Record {
        public final boolean flag;
        public final Record_2 record_2;
        public static final Record record_ = new Record(false, null);

        public Record(boolean z, Record_2 record_2) {
            this.flag = z;
            this.record_2 = record_2;
        }




        public boolean is497() {
            return this.flag;
        }

        public Record_2 getRecord_2498() {
            return this.record_2;
        }
    }

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/Delay$Record_2.class */
    public static final class Record_2 {
        public final float[] floatArr;

        public Record_2(float[] fArr) {
            this.floatArr = fArr;
        }

        public boolean is984(BlockPos blockPos) {
            HitResult hitResult985 = getHitResult985();
            if (hitResult985.getType() != HitResult.Type.BLOCK) {
                return false;
            }
            return new Box(hitResult985.getPos().add(-0.01, -0.01, -0.01), hitResult985.getPos().add(0.01, 0.01, 0.01)).intersects(new Box(blockPos));
        }

        public HitResult getHitResult985() {
            float f = this.floatArr[1] * FreecamHelper.val4;
            float f2 = (-this.floatArr[0]) * FreecamHelper.val4;
            float cos = MathHelper.cos(f2);
            float sin = MathHelper.sin(f2);
            float cos2 = MathHelper.cos(f);
            Vec3d vec3d = new Vec3d(sin * cos2, -MathHelper.sin(f), cos * cos2);
            return HoleSnapSearchHelper4_6.getBlockHitResult2784(new HoleSnapHelper_2.Inner(SearchHelper_4.minecraftClient.player.getEyePos(), SearchHelper_4.minecraftClient.player.getEyePos().add(vec3d.x * 5.0, vec3d.y * 5.0, vec3d.z * 5.0)).getInner1604(HoleSnapHelper.getHoleSnapHelper1677(BaritoneHelper_3.stashFinderSearchHelper4.getMap1552().keySet())).getHoleSnapHelper_21606());
        }




        public float[] getFloatArray986() {
            return this.floatArr;
        }
    }

    public Delay(String str, String str2, Category category, String... strArr) {
        super(str, str2, category, strArr);
        this.setting = add(new NumberSetting("Delay", 50, 0, 250).getNumberSetting3023("ms"));
        this.setting2 = add(new NumberSetting("BPT", 8, 1, 8));
        this.setting3 = add(new BooleanSetting("AirPlace", false));
        this.setting4 = add(new BooleanSetting("StrictDirection", false));
        this.setting5 = add(new BooleanSetting("Raytrace", false));
        this.setting6 = add(new BooleanSetting("Rotate", false));
        this.setting7 = add(new BooleanSetting("Attack", true));
        this.setting8 = add(new BooleanSetting("AutoDisable", false).getSetting2337());
        this.setting9 = add(new BooleanSetting("Jump", true).getSetting2342(this.setting8));
        this.setting10 = add(new BooleanSetting("Chorus", false).getSetting2342(this.setting8));
        this.setting11 = add(new BooleanSetting("Pearl", false).getSetting2342(this.setting8));
        this.setting12 = add(new BooleanSetting("RubberBand", false).getSetting2342(this.setting8));
        this.setting13 = add(new BooleanSetting("Render", false).getSetting2336());
        this.setting14 = add(new ColorSetting("Fill", new Color(189, 153, 255, 8), color -> {
            return this.setting13.is623();
        }));
        this.setting15 = add(new ColorSetting("Outline", new Color(189, 153, 255, 91), color2 -> {
            return this.setting13.is623();
        }));
        this.setting16 = add(new NumberSetting("LineWidth", Float.valueOf(1.0f), Float.valueOf(0.10000000149011612f), Float.valueOf(3.0f)).getSetting2342(this.setting13));
        this.setting17 = add(new BooleanSetting("Fade", true).getSetting2342(this.setting13));
        this.setting18 = add(new NumberSetting("FadeTime", Float.valueOf(1.0f), Float.valueOf(0.10000000149011612f), Float.valueOf(1.0f)).getNumberSetting3023("s").getSetting2342(this.setting13, this.setting17));
        this.stopwatch = new Stopwatch();
        this.stopwatch2 = new Stopwatch();
        BaritoneHelper_3.antiPhaseSearchHelper4.register(new AntiPhaseSearchHelper4.Record(this, this.setting14, this.setting15, this.setting16, this.setting18, () -> {
            return true;
        }, this.setting17, 450));
    }

    public Delay(String str, Category category, String... strArr) {
        this(str, "", category, strArr);
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        if (!is1469()) {
            this.val = minecraftClient.player.getY();
        }
        this.num = 0;
        this.num2 = 0;
        this.flag6 = false;
    }

    @Override // me.mioclient.module.Module
    public <T> Setting<T> add(Setting<T> setting) {
        if (!getRegistry().contains(this.setting18)) {
            return super.add(setting);
        }
        List<Setting<?>> registry = getRegistry();
        List<Setting<?>> list = registry;
        list.add(getRegistry().indexOf(this.setting7), setting);
        return setting;
    }

    public abstract List<BlockPos> getList876();

    public boolean is1320(BlockPos blockPos) {
        return true;
    }

    public void do1321(BlockPos blockPos, boolean z) {
    }

    public boolean is1322() {
        return true;
    }

    public void do1323() {
        this.flag = false;
        if (this.flag6) {
            this.flag6 = false;
            return;
        }
        if (Offhand.is929() || SpeedMine.is1060() || minecraftClient.player.isSpectator() || !minecraftClient.player.isAlive() || minecraftClient.player.isRiding() || minecraftClient.player.isSleeping() || is1330()) {
            return;
        }
        if (this.flag3 && noslow.is3108()) {
            return;
        }
        if (this.stopwatch.is419(this.setting.getValue().intValue()) && is1322()) {
            List<BlockPos> list876 = getList876();
            int i = get499();
            synchronized (list876) {
                do1331(i, list876);
                do1324(list876);
                if (!list876.isEmpty()) {
                    NewChunksHelper_4.do2149(() -> {
                        do1325(list876);
                    });
                }
            }
            this.num = 0;
        }
    }

    public void do1324(List<BlockPos> list) {
        if (this.setting2.getValue().intValue() > 1) {
            list.sort(Comparator.comparing(blockPos -> {
                return Integer.valueOf(PhaseESPSearchHelper4_2.getDirection3029(blockPos, this.setting4.getValue().booleanValue()) != null ? -1 : 1);
            }));
        }
    }

    @Listen(get219= Helper_7.num2)
    public void onEvent(HoleSnapEvent holeSnapEvent) {
        this.vec3d = null;
        this.entity = null;
        do1323();
    }

    @Listen
    public void onMove(MoveEvent_2 moveEvent_2) {
        if (moveEvent_2.getKeyPearlMode1472() == KeyPearlMode.Post && moveEvent_2.get990() > FreecamHelper.val2 && this.setting9.getValue().booleanValue()) {
            disable();
        }
    }

    @Listen
    public void onFinishUsing(FinishUsingEvent finishUsingEvent) {
        if (finishUsingEvent.getItemStack2549().getItem() != Items.CHORUS_FRUIT || minecraftClient.player.isSneaking() || chorusControl.isToggled()) {
            return;
        }
        if (this.setting10.getValue().booleanValue()) {
            disable();
        } else {
            this.flag6 = true;
        }
    }

    @Listen
    public void onChannelRead0(ChannelRead0Event channelRead0Event) {
        if (this.setting7.getValue().booleanValue()) {
            EntitySpawnS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof EntitySpawnS2CPacket ? (EntitySpawnS2CPacket) (channelRead0Event.getPacket904()) : null;
            if (packet904 instanceof EntitySpawnS2CPacket) {
                EntitySpawnS2CPacket entitySpawnS2CPacket = packet904;
                if (entitySpawnS2CPacket.getEntityType() == EntityType.END_CRYSTAL) {
                    if (Offhand.is929()) {
                        return;
                    }
                    Vec3i ofFloored = BlockPos.ofFloored(entitySpawnS2CPacket.getX(), entitySpawnS2CPacket.getY(), entitySpawnS2CPacket.getZ());
                    List<BlockPos> list876 = getList876();
                    do1331(get499(), list876);
                    Iterator<BlockPos> it = list876.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        if (it.next().isWithinDistance(ofFloored, 2.0)) {
                            if (!BaritoneHelper_3.scaffoldHelper.is1113(entitySpawnS2CPacket.getEntityId(), true)) {
                                return;
                            } else {
                                AutoSignSearchHelper4.do2559(Hand.MAIN_HAND);
                            }
                        }
                    }
                    NewChunksHelper_4.do2149(() -> {
                        do1325(list876);
                    });
                }
            }
        }
        PlayerPositionLookS2CPacket packet9042 = (channelRead0Event.getPacket904()) instanceof PlayerPositionLookS2CPacket ? (PlayerPositionLookS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet9042 instanceof PlayerPositionLookS2CPacket) {
            PlayerPositionLookS2CPacket playerPositionLookS2CPacket = packet9042;
            this.val = playerPositionLookS2CPacket.getY();
            if (minecraftClient.player.getPos().squaredDistanceTo(playerPositionLookS2CPacket.getX(), playerPositionLookS2CPacket.getY(), playerPositionLookS2CPacket.getZ()) >= 1.0 && this.setting12.getValue().booleanValue()) {
                disable();
                return;
            }
        }
        if ((channelRead0Event.getPacket904() instanceof PlayerRespawnS2CPacket) && this.flag2) {
            disable();
        }
        OpenScreenS2CPacket packet9043 = (channelRead0Event.getPacket904()) instanceof OpenScreenS2CPacket ? (OpenScreenS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet9043 instanceof OpenScreenS2CPacket) {
            OpenScreenS2CPacket openScreenS2CPacket = packet9043;
            if (this.stopwatch.is419(100L)) {
                return;
            }
            AutoSignSearchHelper4.do2571(new CloseHandledScreenC2SPacket(openScreenS2CPacket.getSyncId()));
            channelRead0Event.do1162();
        }
    }

    public void do1325(List<BlockPos> list) {
        if (list.isEmpty()) {
            return;
        }
        Iterator<BlockPos> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            BlockPos next = it.next();
            if (this.setting7.getValue().booleanValue() && this.stopwatch2.is419(BaritoneHelper_3.scaffoldHelper.get1115())) {
                this.entity = PhaseESPSearchHelper4_2.getEntity3046(next, BaritoneHelper_3.scaffoldHelper.get1116());
                if (this.entity != null) {
                    if (this.setting6.getValue().booleanValue()) {
                        BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2487(SearchHelper4_8.getFloatArray2483(this.entity), antiCheat.get237()), get888() + 1);
                    }
                    this.stopwatch2.reset();
                }
            }
        }
        int i = get499();
        int i2 = minecraftClient.player.getInventory().selectedSlot;
        Record_2 record_2 = null;
        Iterator<BlockPos> it2 = list.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            BlockPos next2 = it2.next();
            if (this.num2 >= 30) {
                this.num2 = 0;
                MixinMessageIndicatorHelper.do345(Text.literal(getName()).append(" is out of blocks!"), MixinMessageIndicatorHelper.getMessageSignatureData337(-2), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode);
                this.num = 1337;
                disable();
                break;
            }
            if (i == -1 || minecraftClient.player.getInventory().getStack(i).isEmpty()) {
                break;
            }
            if (this.num >= this.setting2.getValue().intValue()) {
                break;
            }
            Record record1326 = getRecord1326(next2);
            if (record1326.is497()) {
                this.num2 = 0;
            }
            if (record1326.is497() && next2 != list.getLast() && is1329()) {
                Record_2 record_2498 = record1326.getRecord_2498();
                if (record_2 == null || !record_2.is984(next2)) {
                    if (next2 != list.getFirst() || !new Record_2(new float[]{minecraftClient.player.getYaw(), minecraftClient.player.getPitch()}).is984(next2)) {
                        AutoSignSearchHelper4.do2563(minecraftClient.player.getX(), minecraftClient.player.getY(), minecraftClient.player.getZ(), record_2498.getFloatArray986()[0], record_2498.getFloatArray986()[1], minecraftClient.player.isOnGround());
                        record_2 = record_2498;
                    }
                }
            }
        }
        this.num2++;
        if (!this.flag5) {
            this.flag5 = true;
            BaritoneHelper_3.tooltipsSearchHelper4_2.do164(() -> {
                this.flag5 = false;
            }, 1);
        }
        if (this.flag4 && is1332()) {
            AutoSignSearchHelper4.do2568();
        }
        FireworksHelper.do456(i2);
        this.flag4 = false;
    }

    public Record getRecord1326(BlockPos blockPos) {
        boolean z;
        if (Offhand.is929()) {
            return Record.record_;
        }
        this.direction = PhaseESPSearchHelper4_2.getDirection3030(blockPos, this.setting4.getValue().booleanValue(), this.setting5.getValue().booleanValue());
        if (this.direction == null && !this.setting3.getValue().booleanValue()) {
            return Record.record_;
        }
        float[] floatArray2485 = SearchHelper4_8.getFloatArray2485(blockPos.toCenterPos(), this.direction);
        boolean booleanValue = this.setting5.getValue().booleanValue();
        if (this.direction != null) {
            z = booleanValue & (!SearchHelper4_7.is2432(AutoCraftMode.X8.getList900(blockPos.offset(this.direction), this.direction.getOpposite())));
        } else {
            z = booleanValue & (!SearchHelper4_7.is2431((Vec3i) blockPos));
        }
        if (!is1320(blockPos)) {
            return Record.record_;
        }
        boolean z2 = true;
        if (!this.flag4) {
            FireworksHelper.do456(get499());
            if (is1332()) {
                AutoSignSearchHelper4.do2568();
            }
            this.flag4 = true;
        }
        if (!z && is1327(blockPos)) {
            if (SearchHelper4_8.is724() && antiCheat.is238()) {
                floatArray2485[0] = (float) (floatArray2485[0] + (Math.random() * 0.009999999776482582));
                floatArray2485[1] = (float) (floatArray2485[1] + (Math.random() * 0.009999999776482582));
            }
            this.stopwatch.reset();
            if (this.setting13.getValue().booleanValue()) {
                BaritoneHelper_3.antiPhaseSearchHelper4.do2132(this, blockPos);
            }
            this.vec3d = blockPos.toCenterPos();
            if (this.direction != null) {
                this.vec3d = this.vec3d.offset(this.direction, FreecamHelper.val2);
            }
            if (this.setting6.getValue().booleanValue()) {
                BaritoneHelper_3.searchHelper4_8.do2477(SearchHelper4_8.getFloatArray2487(floatArray2485, antiCheat.get237()), get888());
            }
            this.num++;
            z2 = false;
        }
        do1321(blockPos, z2);
        return new Record(!z2, new Record_2(floatArray2485));
    }

    public boolean is1327(BlockPos blockPos) {
        return PhaseESPSearchHelper4_2.is3037(blockPos, this.direction, this.setting3.getValue().booleanValue(), is1332() ? Hand.OFF_HAND : Hand.MAIN_HAND);
    }

    public int get499() {
        return FireworksHelper.get447(Items.OBSIDIAN) == -1 ? FireworksHelper.get448(itemStack -> {
            BlockItem item = (itemStack.getItem()) instanceof BlockItem ? (BlockItem) (itemStack.getItem()) : null;
            if (item instanceof BlockItem) {
                BlockItem blockItem = item;
                if (blockItem.getBlock() != Blocks.ANVIL && blockItem.getBlock().getBlastResistance() >= 600.0f) {
                    return true;
                }
            }
            return false;
        }) : FireworksHelper.get447(Items.OBSIDIAN);
    }

    public void do1328(BlockPos blockPos) {
        if (minecraftClient.world.getBlockState(blockPos).isReplaceable()) {
            return;
        }
        if (minecraftClient.world.getBlockState(blockPos).getHardness(minecraftClient.world, blockPos) == 0.0f) {
            minecraftClient.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, net.minecraft.util.math.Direction.DOWN));
            minecraftClient.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, net.minecraft.util.math.Direction.DOWN));
        }
    }

    public boolean is1329() {
        return !(antiCheat.movementSync.getValue().booleanValue() && antiCheat.rotations.getValue() == AutoCrystalMode_6.SILENT) && this.setting6.getValue().booleanValue() && this.setting2.getValue().intValue() > 1 && minecraftClient.player.isOnGround() && BaritoneHelper_3.antiPhaseSearchHelper4_2.get2231() > 1 && !this.flag5;
    }

    public boolean is1330() {
        double y = minecraftClient.player.getY() - this.val;
        if (minecraftClient.player.isOnGround()) {
            this.val = minecraftClient.player.getY();
        }
        if ((minecraftClient.player.getY() <= this.val + 0.1 || y < 0.4 || !this.setting9.getValue().booleanValue()) && !minecraftClient.player.isSleeping()) {
            return false;
        }
        do495(false);
        return true;
    }

    public void do1331(int i, List<BlockPos> list) {
        ArrayList arrayList = new ArrayList();
        Block block = null;
        if (i != -1) {
            BlockItem item = (minecraftClient.player.getInventory().getStack(i).getItem()) instanceof BlockItem ? (BlockItem) (minecraftClient.player.getInventory().getStack(i).getItem()) : null;
            if (item instanceof BlockItem) {
                block = item.getBlock();
            }
        }
        for (BlockPos blockPos : list) {
            double squaredDistanceTo = minecraftClient.player.getEyePos().squaredDistanceTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (!PhaseESPSearchHelper4_2.is3040(blockPos, block, true, this.setting7.getValue().booleanValue()) || squaredDistanceTo > 36.0) {
                arrayList.add(blockPos);
            } else if (PhaseESPSearchHelper4_2.getDirection3028(blockPos) == null) {
                this.flag = true;
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            list.remove((BlockPos) it.next());
        }
    }

    public boolean is1332() {
        return antiCheat.is238() && SearchHelper4_8.is724() && this.setting3.getValue().booleanValue() && this.flag;
    }

    public BlockPos getBlockPos1333() {
        return HoleSnapSearchHelper4.getBlockPos1333();
    }

    public int get888() {
        return 20;
    }

    public void do1334(boolean z) {
        this.flag2 = z;
    }

    public void do1335() {
        this.flag3 = true;
    }
}
