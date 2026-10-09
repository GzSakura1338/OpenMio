package me.mioclient.module.misc;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import me.mioclient.ArgumentTypeHelper;
import me.mioclient.BaritoneHelper_3;
import me.mioclient.HoleSnapSearchHelper4;
import me.mioclient.KeyPearlMode;
import me.mioclient.MixinMessageIndicatorHelper;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PresetHelper;
import me.mioclient.PresetHelper_4;
import me.mioclient.SearchHelper4_4;
import me.mioclient.SearchHelper_3;
import me.mioclient.SearchIdentifier;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.AttackHookPostEvent;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.DisconnectEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.module.Module;
import me.mioclient.module.client.IRC;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.ThreadSafeRandom;
import org.apache.commons.io.FileUtils;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/misc/KillEffects.class */
public class KillEffects extends Module {
    public Setting<Boolean> killSound;
    public Setting<Boolean> players;
    public Setting<SearchIdentifier> sound;
    public Setting<Float> volume;
    public Setting<Boolean> self;
    public Setting<SearchIdentifier> sound2;
    public Setting<Float> volume3;
    public Setting<Boolean> killStreak;
    public Setting<Float> volume2;
    public Setting<Boolean> thunder;
    public Setting<Integer> lightnings;
    public Setting<Boolean> self2;
    public Setting<Boolean> ignoreNakeds;
    public Setting<Boolean> ashes;
    public Setting<Boolean> self3;
    public Setting<Boolean> autoEZ;
    public Setting<Float> delay;
    public Setting<String> path;
    public Setting<Boolean> random;
    public Setting<Boolean> refresh;
    public static IRC iRC = (IRC) BaritoneHelper_3.baritoneHelper_4.getModule117(IRC.class);
    public static final Random random2 = new ThreadSafeRandom(System.currentTimeMillis());
    public final ConcurrentHashMap<UUID, Integer> concurrentHashMap;
    public final ObjectList<UUID> objectList;
    public int num;
    public int num2;
    public List<String> list;

    public KillEffects() {
        super("KillEffects", "Does various things when someone dies.", Category.MISC, new String[0]);
        PhaseESPHelper.do1351(this);
        this.concurrentHashMap = new ConcurrentHashMap<>();
        this.objectList = ObjectLists.synchronize(new ObjectArrayList());
        this.num = 0;
        this.num2 = 0;
        this.list = Collections.synchronizedList(new ArrayList());
        this.players.do2329("KillSoundPlayers");
        this.sound.do2329("KillSoundPlayersSound");
        this.volume.do2329("KillSoundPlayersVolume");
        this.self.do2329("KillSoundSelf");
        this.sound2.do2329("KillSoundSelfSound");
        this.volume3.do2329("KillSoundSelfVolume");
        this.self2.do2329("ThunderSelf");
        this.volume2.do2329("KillStreakSoundVolume");
        this.self3.do2329("AshesSelf");
        this.autoEZ.do2339(() -> {
            if (this.autoEZ.getValue().booleanValue()) {
                do3004(false);
            }
        });
        this.refresh.do2339(() -> {
            if (this.refresh.getValue().booleanValue()) {
                do3004(true);
                this.refresh.do2333(false);
            }
        });
        this.random.do2339(() -> {
            this.num2 = 0;
        });
        this.path.do2339(() -> {
            do3004(true);
        });
        this.path.do2329("AutoEZPath");
        this.random.do2329("AutoEZRandom");
        this.refresh.do2329("AutoEZRefresh");
        setDrawn(false);
    }

    @Override // me.mioclient.module.Module
    public void onEnable() {
        this.num = 0;
        do3004(true);
    }

    @Listen
    public void onDisconnect(DisconnectEvent disconnectEvent) {
        this.num = 0;
    }

    @Listen
    public void onAttackHookPost(AttackHookPostEvent attackHookPostEvent) {
        PlayerEntity playerEntity;
        if (attackHookPostEvent.getKeyPearlMode1472() == KeyPearlMode.Post || is1469()) {
            return;
        }
        PlayerEntity entity181 = (attackHookPostEvent.getEntity181()) instanceof PlayerEntity ? (PlayerEntity) (attackHookPostEvent.getEntity181()) : null;
        if (!(entity181 instanceof PlayerEntity) || (playerEntity = entity181) == minecraftClient.player) {
            return;
        }
        if (BaritoneHelper_3.searchHelper4_14.is519(playerEntity.getName().getString()) || SearchHelper_3.get644((Entity) playerEntity) <= 0.0f) {
            return;
        }
        try {
            this.concurrentHashMap.put(playerEntity.getUuid(), Integer.valueOf(minecraftClient.player.age));
        } catch (Exception e) {
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (channelRead0Event.getPacket904() instanceof DeathMessageS2CPacket) {
            this.num = -1;
        }
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        PlayerEntity playerByUuid = null;
        String string3005;
        if (is1469()) {
            return;
        }
        if (this.num == -1) {
            this.num = 0;
            if (this.killSound.getValue().booleanValue() && this.self.getValue().booleanValue() && this.volume3.getValue().floatValue() > 0.0f) {
                SearchHelper4_4 searchHelper4_42970 = BaritoneHelper_3.searchHelper4_11.getSearchHelper4_42970(this.sound2.getValue());
                if (searchHelper4_42970 != null) {
                    searchHelper4_42970.do1820(this.volume3.getValue().floatValue());
                }
            }
            if (this.thunder.getValue().booleanValue() && this.self2.getValue().booleanValue()) {
                do3006(minecraftClient.player.getPos());
            }
            if (this.ashes.getValue().booleanValue() && this.self3.getValue().booleanValue()) {
                do3007(minecraftClient.player.getPos());
            }
        }
        this.objectList.clear();
        synchronized (this.concurrentHashMap) {
            Iterator it = this.concurrentHashMap.keySet().iterator();
            while (it.hasNext()) {
                UUID uuid = (UUID) it.next();
                try {
                    playerByUuid = minecraftClient.world.getPlayerByUuid(uuid);
                } catch (Exception e) {
                }
                if (playerByUuid == null) {
                    this.objectList.add(uuid);
                } else if (minecraftClient.player.age - this.concurrentHashMap.get(uuid).intValue() > 20) {
                    this.objectList.add(uuid);
                } else if (playerByUuid.getHealth() <= 0.0f) {
                    this.objectList.add(uuid);
                    this.num++;
                    if (this.autoEZ.getValue().booleanValue() && (string3005 = getString3005()) != null) {
                        String replace = string3005.replace("{name}", playerByUuid.getName().getString());
                        if (!iRC.isToggled() || !replace.startsWith(iRC.prefix.getValue())) {
                            Runnable runnable = () -> {
                                MixinMessageIndicatorHelper.do347(replace);
                            };
                            if (this.delay.is2327()) {
                                runnable.run();
                            } else {
                                BaritoneHelper_3.tooltipsSearchHelper4_2.do164(runnable, (int) (this.delay.getValue().floatValue() / 0.05000000074505806f));
                            }
                        }
                    }
                    if (this.thunder.getValue().booleanValue() && (HoleSnapSearchHelper4.is2013((LivingEntity) playerByUuid) || !this.ignoreNakeds.getValue().booleanValue())) {
                        do3006(playerByUuid.getPos());
                    }
                    if (this.ashes.getValue().booleanValue()) {
                        do3007(playerByUuid.getPos());
                    }
                    if (this.killSound.getValue().booleanValue() && this.players.getValue().booleanValue() && this.volume.getValue().floatValue() > 0.0f) {
                        SearchHelper4_4 searchHelper4_429702 = BaritoneHelper_3.searchHelper4_11.getSearchHelper4_42970(this.sound.getValue());
                        if (searchHelper4_429702 != null) {
                            searchHelper4_429702.do1820(this.volume.getValue().floatValue());
                        }
                    }
                    if (this.killStreak.getValue().booleanValue() && this.num >= 2 && this.volume2.getValue().floatValue() > 0.0f) {
                        SearchHelper4_4 searchHelper4_42968 = BaritoneHelper_3.searchHelper4_11.getSearchHelper4_42968(this.num);
                        if (searchHelper4_42968 != null) {
                            searchHelper4_42968.do1820(this.volume2.getValue().floatValue());
                        }
                    }
                }
            }
        }
        synchronized (this.objectList) {
            try {
                Iterator it2 = this.objectList.iterator();
                while (it2.hasNext()) {
                    this.concurrentHashMap.remove((UUID) it2.next());
                }
            } catch (Exception e2) {
            }
        }
    }

    public void do3003(PlayerEntity playerEntity) {
        if (is1469() || playerEntity == minecraftClient.player) {
            return;
        }
        if (BaritoneHelper_3.searchHelper4_14.is519(playerEntity.getName().getString())) {
            return;
        }
        try {
            this.concurrentHashMap.put(playerEntity.getUuid(), Integer.valueOf(minecraftClient.player.age));
        } catch (Exception e) {
        }
    }

    public void do3004(boolean z) {
        if (this.autoEZ.getValue().booleanValue() || z) {
            Path path1566 = PresetHelper_4.getPath1566(PresetHelper.path4.resolve(this.path.getValue()), ".txt");
            try {
                if (!path1566.toFile().exists()) {
                    throw new IOException("AutoEZ file %s not found".formatted(this.path.getValue()));
                }
                this.list.clear();
                this.list.addAll(FileUtils.readLines(path1566.toFile(), StandardCharsets.UTF_8));
                this.num2 = 0;
            } catch (Exception e) {
                if (this.autoEZ.getValue().booleanValue()) {
                    MixinMessageIndicatorHelper.do345(Text.literal(new ArgumentTypeHelper().getArgumentTypeHelper2919(String.valueOf(e)).getString2921("Failed to update AutoEZ lines: \u0001")).styled(style -> {
                        return style.withColor(Formatting.RED);
                    }), MixinMessageIndicatorHelper.getMessageSignatureData337(-9634482), MixinMessageIndicatorHelper.MixinClientConnectionMode.mixinClientConnectionMode3);
                    try {
                        Files.createFile(path1566, new FileAttribute[0]);
                    } catch (Exception e2) {
                    }
                }
            }
        }
    }

    public String getString3005() {
        try {
            if (this.list.isEmpty()) {
                return null;
            }
            if (this.random.getValue().booleanValue()) {
                List<String> list = this.list;
                return list.get(ThreadLocalRandom.current().nextInt(this.list.size()));
            }
            List<String> list2 = this.list;
            int i = this.num2;
            this.num2 = i + 1;
            return list2.get(i % this.list.size());
        } catch (Exception e) {
            return null;
        }
    }

    public void do3006(Vec3d vec3d) {
        for (int i = 0; i < this.lightnings.getValue().intValue(); i++) {
            Entity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, minecraftClient.world);
            ((LightningEntity) lightningEntity).setPosition(vec3d);
            ((LightningEntity) lightningEntity).refreshPositionAfterTeleport(vec3d);
            minecraftClient.world.addEntity(lightningEntity);
        }
    }

    public void do3007(Vec3d vec3d) {
        for (int i = 0; i < random2.nextInt(35) + 25; i++) {
            Particle addParticle = minecraftClient.particleManager.addParticle((ParticleEffect) (random2.nextBoolean() ? ParticleTypes.ASH : ParticleTypes.WHITE_ASH), vec3d.getX() + (random2.nextGaussian() * 0.22999999523162842), (vec3d.getY() + 2.0) - (random2.nextGaussian() * 0.5299999952316284), vec3d.getZ() + (random2.nextGaussian() * 0.22999999523162842), 0.0d, -0.1 - (random2.nextGaussian() * 0.5), 0.0d);
            if (addParticle != null) {
                addParticle.maxAge += 15;
            }
        }
    }
}
