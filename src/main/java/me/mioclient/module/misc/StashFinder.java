package me.mioclient.module.misc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.SearchHelper4_11;
import me.mioclient.SearchHelper4_7;
import me.mioclient.SearchIdentifier;
import me.mioclient.StashFinderHelper;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.LoadChunkFromPacketEvent;
import me.mioclient.feature.Stopwatch;
import me.mioclient.module.Module;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/misc/StashFinder.class */
public class StashFinder extends Module {
    public Setting<Boolean> chests;
    public Setting<Integer> chestCount;
    public Setting<Boolean> shulkers;
    public Setting<Boolean> stackedCarts;
    public Setting<Boolean> notify;
    public Setting<Boolean> censorCoords;
    public Setting<Boolean> log;
    public Setting<Boolean> sound;
    public Setting<SearchIdentifier> type;
    public Setting<Float> volume;
    public Setting<Boolean> ignoreNatural;
    public Setting<Boolean> dungeon;
    public Setting<Boolean> fortress;
    public Setting<Boolean> bastion;
    public Setting<Boolean> ancientCity;
    public Setting<Boolean> trialChambers;
    public final Stopwatch stopwatch;

    public StashFinder() {
        super("StashFinder", "Logs and/or notifies you about possible stashes.", Category.MISC, new String[0]);
        PhaseESPHelper.do1351(this);
        this.stopwatch = new Stopwatch();
        setDrawn(false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x01b6, code lost:
    
        if (me.mioclient.BaritoneHelper_3.stashFinderSearchHelper4.is1556(r0.getPos()) == false) goto L16;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0287 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0063 A[SYNTHETIC] */
    @Listen
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onLoadChunkFromPacket(LoadChunkFromPacketEvent loadChunkFromPacketEvent) {
        String format = new SimpleDateFormat("MM/dd HH:mm a").format(new Date());
        WorldChunk worldChunk2555 = loadChunkFromPacketEvent.getWorldChunk2555();
        ChunkPos pos = worldChunk2555.getPos();
        int i = 0;
        for (BlockEntity blockEntity : worldChunk2555.getBlockEntities().values()) {
            if (this.ignoreNatural.getValue().booleanValue()) {
                if (SearchHelper4_7.is2428(minecraftClient.world.getChunk(blockEntity.getPos()), blockEntity.getPos(), this.fortress.getValue().booleanValue(), this.bastion.getValue().booleanValue(), this.ancientCity.getValue().booleanValue(), this.dungeon.getValue().booleanValue(), this.trialChambers.getValue().booleanValue())) {
                    continue;
                }
            }
            if (!(blockEntity instanceof ChestBlockEntity)) {
                if (blockEntity instanceof BarrelBlockEntity) {
                }
                if (!this.chests.getValue().booleanValue() && i >= this.chestCount.getValue().intValue()) {
                    String string2921 = new ArgumentTypeHelper().getArgumentTypeHelper2906(i).getString2921("\u0001 chests");
                    if (is255(getStashFinderHelper254(new ArgumentTypeHelper().getArgumentTypeHelper2919(format).getArgumentTypeHelper2919(string2921).getString2921("\u0001 (\u0001)"), pos.getCenterX(), pos.getCenterZ()), string2921)) {
                        break;
                    }
                } else if (blockEntity instanceof ShulkerBoxBlockEntity) {
                    continue;
                } else {
                    ShulkerBoxBlockEntity shulkerBoxBlockEntity = (ShulkerBoxBlockEntity) blockEntity;
                    if (this.shulkers.getValue().booleanValue()) {
                        if (!BaritoneHelper_3.stashFinderSearchHelper4.is1556(shulkerBoxBlockEntity.getPos())) {
                            if (is255(getStashFinderHelper254(new ArgumentTypeHelper().getArgumentTypeHelper2919(format).getArgumentTypeHelper2919("a shulkerbox").getString2921("\u0001 (\u0001)"), pos.getCenterX(), pos.getCenterZ()), "a shulkerbox")) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
            i++;
            if (!this.chests.getValue().booleanValue()) {
            }
            if (blockEntity instanceof ShulkerBoxBlockEntity) {
            }
        }
        if (this.stackedCarts.getValue().booleanValue()) {
            ArrayList arrayList = new ArrayList();
            for (Entity chestMinecartEntity : minecraftClient.world.getEntities()) {
                if (chestMinecartEntity instanceof ChestMinecartEntity) {
                    ChestMinecartEntity chestMinecartEntity2 = (ChestMinecartEntity) chestMinecartEntity;
                    if (chestMinecartEntity2.getChunkPos().equals(chestMinecartEntity2.getChunkPos())) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            if (chestMinecartEntity2.getBoundingBox().intersects(((ChestMinecartEntity) it.next()).getBoundingBox())) {
                                is255(getStashFinderHelper254(new ArgumentTypeHelper().getArgumentTypeHelper2919(format).getString2921("Stacked minecarts (\u0001)"), pos.getCenterX(), pos.getCenterZ()), "Stacked minecarts");
                                return;
                            }
                        }
                        arrayList.add(chestMinecartEntity2);
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    public StashFinderHelper getStashFinderHelper254(String str, int i, int i2) {
        return new StashFinderHelper(str, i, i2, SearchHelper4_7.getStashFinderMode2438().name(), minecraftClient.player.networkHandler.getServerInfo() == null ? "singleplayer" : minecraftClient.player.networkHandler.getServerInfo().address);
    }

    public boolean is255(StashFinderHelper stashFinderHelper, String str) {
        if (!BaritoneHelper_3.searchHelper4_15.getOptional2404(stashFinderHelper2 -> {
            return stashFinderHelper2.get515() == stashFinderHelper.get515() && stashFinderHelper2.get516() == stashFinderHelper.get516();
        }).isEmpty() || !BaritoneHelper_3.searchHelper4_15.register(stashFinderHelper)) {
            return false;
        }
        if (this.notify.getValue().booleanValue()) {
            MutableText append = Text.literal(getName()).append(new ArgumentTypeHelper().getArgumentTypeHelper2919(str).getString2921(" has found \u0001 at: "));
            if (!this.censorCoords.getValue().booleanValue()) {
                append.append(stashFinderHelper.getString514());
            }
            MixinMessageIndicatorHelper.do345((Text) append, MixinMessageIndicatorHelper.getMessageSignatureData337(-13579), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode3);
        }
        if (!this.sound.getValue().booleanValue()) {
            return true;
        }
        if (!this.stopwatch.is418(5.0, TimeUnit.SECONDS)) {
            return true;
        }
        SearchHelper4_11 searchHelper4_11 = BaritoneHelper_3.searchHelper4_11;
        searchHelper4_11.do2971(this.type.getValue(), this.volume.getValue().floatValue());
        this.stopwatch.reset();
        return true;
    }
}
