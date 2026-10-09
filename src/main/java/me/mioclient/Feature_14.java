package me.mioclient;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.arguments.StringArgumentType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.mioclient.LegacyCrystalSearchHelper4;
import me.mioclient.event.ChannelRead0Event;
import me.mioclient.event.DisconnectEvent;
import me.mioclient.event.Listen;
import me.mioclient.event.SendImmediatelyEvent;
import me.mioclient.feature.MotionEvent;
import me.mioclient.module.player.Freecam;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/Feature_14.class */
public final class Feature_14 extends Feature {
    public static Freecam freecam = (Freecam) BaritoneHelper_3.baritoneHelper_4.getModule117(Freecam.class);
    public final List<Record> list;
    public OtherClientPlayerEntity otherClientPlayerEntity;
    public boolean flag;
    public boolean flag2;
    public String string;
    public int current;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/Feature_14$OtherClientPlayerEntity.class */
    public static class OtherClientPlayerEntity extends net.minecraft.client.network.OtherClientPlayerEntity {
        public OtherClientPlayerEntity(PlayerEntity playerEntity, ClientWorld clientWorld, GameProfile gameProfile) {
            super(clientWorld, gameProfile);
            copyPositionAndRotation((Entity) playerEntity);
            this.prevYaw = getYaw();
            this.prevPitch = getPitch();
            this.headYaw = playerEntity.headYaw;
            this.prevHeadYaw = this.headYaw;
            this.bodyYaw = playerEntity.bodyYaw;
            this.prevBodyYaw = this.bodyYaw;
            setPose(playerEntity.getPose());
            this.capeX = getX();
            this.capeY = getY();
            this.capeZ = getZ();
            setHealth(20.0f);
            getInventory().clone(playerEntity.getInventory());
        }

        public void setHealth(float f) {
            super.setHealth(f);
            if (getHealth() <= 0.0f) {
                super.setHealth(9.0f);
                clearStatusEffects();
                addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                SearchHelper_4.minecraftClient.particleManager.addEmitter((Entity) this, ParticleTypes.TOTEM_OF_UNDYING, 30);
                ClientWorld clientWorld = SearchHelper_4.minecraftClient.world;
                PlayerEntity playerEntity = SearchHelper_4.minecraftClient.player;
                BlockPos blockPos = getBlockPos();
                SoundEvent soundEvent = SoundEvents.ITEM_TOTEM_USE;
                clientWorld.playSound(playerEntity, blockPos, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
                SearchHelper_4.baritoneHelper.getObject1794(new ChannelRead0Event(new EntityStatusS2CPacket(this, (byte) 35)));
            }
        }

        public void tick() {
            this.age++;
            if (this.age % 80 == 0) {
                addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 1));
                addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0));
                addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6000, 0));
                addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 3));
                setAbsorptionAmount(16.0f);
            }
            if (!getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                getInventory().setStack(40, new ItemStack(Items.TOTEM_OF_UNDYING, 1));
            }
            super.tick();
        }

        public boolean isImmuneToExplosion(Explosion explosion) {
            return false;
        }

        public boolean isAttackable() {
            return true;
        }

        public boolean isFireImmune() {
            return true;
        }

        public boolean isPushable() {
            return false;
        }

        public boolean isAlive() {
            return true;
        }

        public void updatePostDeath() {
            this.deathTime = 0;
        }

        public void pushAway(Entity entity) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/Feature_14$Record.class */
    public static final class Record {
        public final Vec3d vec3d;
        public final float val;
        public final float val2;
        public final float val3;
        public final Vec3d vec3d2;
        public final boolean flag;
        public final boolean flag2;

        public Record(Vec3d vec3d, float f, float f2, float f3, Vec3d vec3d2, boolean z, boolean z2) {
            this.vec3d = vec3d;
            this.val = f;
            this.val2 = f2;
            this.val3 = f3;
            this.vec3d2 = vec3d2;
            this.flag = z;
            this.flag2 = z2;
        }




        public Vec3d getVec3d77() {
            return this.vec3d;
        }

        public float get78() {
            return this.val;
        }

        public float get79() {
            return this.val2;
        }

        public float get80() {
            return this.val3;
        }

        public Vec3d getVec3d81() {
            return this.vec3d2;
        }

        public boolean is82() {
            return this.flag;
        }

        public boolean is83() {
            return this.flag2;
        }
    }

    public Feature_14() {
        super("fakeplayer");
        this.list = new ArrayList();
        baritoneHelper.do1796(this);
    }

    @Override // me.mioclient.Feature
    public void exec(com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(Feature.argument("name", StringArgumentType.word()).executes(commandContext -> {
            do1580((String) commandContext.getArgument("name", String.class));
            return 1;
        })).then(Feature.literal("record").executes(commandContext2 -> {
            this.flag = !this.flag;
            if (!this.flag) {
                MixinMessageIndicatorHelper.do344(Text.literal("Stopped recording."), MixinMessageIndicatorHelper.getMessageSignatureData337(-1));
                return 1;
            }
            this.list.clear();
            MixinMessageIndicatorHelper.do344(Text.literal("Started recording."), MixinMessageIndicatorHelper.getMessageSignatureData337(-1));
            return 1;
        })).then(Feature.literal("play").executes(commandContext3 -> {
            this.current = 0;
            this.flag2 = !this.flag2;
            return 1;
        })).executes(commandContext4 -> {
            do1580("Herobrine");
            return 1;
        });
    }

    public void do1580(String str) {
        this.list.clear();
        this.flag = false;
        this.flag2 = false;
        if (this.otherClientPlayerEntity != null) {
            minecraftClient.world.removeEntity(-9344, Entity.RemovalReason.DISCARDED);
            this.otherClientPlayerEntity = null;
            MixinMessageIndicatorHelper.do344(Text.literal(new ArgumentTypeHelper().getArgumentTypeHelper2919(this.string == null ? str : this.string).getString2921("\u0001 has been removed.")), MixinMessageIndicatorHelper.getMessageSignatureData337(-1));
            return;
        }
        this.otherClientPlayerEntity = new OtherClientPlayerEntity(minecraftClient.player, minecraftClient.world, new GameProfile(UUID.randomUUID(), str));
        if (freecam.isToggled()) {
            this.otherClientPlayerEntity.refreshPositionAndAngles(freecam.vec3d.x, freecam.vec3d.y, freecam.vec3d.z, minecraftClient.player.getYaw(), minecraftClient.player.getPitch());
        }
        this.otherClientPlayerEntity.setId(-9344);
        minecraftClient.world.addEntity(this.otherClientPlayerEntity);
        this.otherClientPlayerEntity.setOnGround(minecraftClient.player.isOnGround());
        this.string = str;
        MixinMessageIndicatorHelper.do344(Text.literal(new ArgumentTypeHelper().getArgumentTypeHelper2919(str).getString2921("\u0001 has been spawned.")), MixinMessageIndicatorHelper.getMessageSignatureData337(-1));
    }

    @Listen
    public void do388(MotionEvent motionEvent) {
        if (motionEvent.getKeyPearlMode1472() == KeyPearlMode.Post && this.flag) {
            this.list.add(new Record(minecraftClient.player.getPos(), minecraftClient.player.getYaw(), minecraftClient.player.getPitch(), minecraftClient.player.getHeadYaw(), minecraftClient.player.getVelocity(), minecraftClient.player.isSneaking(), minecraftClient.player.isOnGround()));
            return;
        }
        if (motionEvent.getKeyPearlMode1472() != KeyPearlMode.Pre || this.otherClientPlayerEntity == null || !this.flag2 || this.list.isEmpty()) {
            return;
        }
        this.current = (this.current + 1) % this.list.size();
        Record record = this.list.get(this.current);
        this.otherClientPlayerEntity.updateTrackedPositionAndAngles(record.vec3d.x, record.vec3d.y, record.vec3d.z, record.val, record.val2, 3);
        this.otherClientPlayerEntity.setVelocity(record.vec3d2);
        this.otherClientPlayerEntity.setYaw(record.val);
        this.otherClientPlayerEntity.setPitch(record.val2);
        this.otherClientPlayerEntity.setHeadYaw(record.val3);
        this.otherClientPlayerEntity.setSneaking(record.flag);
        this.otherClientPlayerEntity.setOnGround(record.flag2);
        if (this.otherClientPlayerEntity.isSneaking()) {
            this.otherClientPlayerEntity.setPose(EntityPose.CROUCHING);
        } else {
            this.otherClientPlayerEntity.setPose(EntityPose.STANDING);
        }
    }

    @Listen
    public void onDisconnect(DisconnectEvent disconnectEvent) {
        this.otherClientPlayerEntity = null;
    }

    @Listen
    public void do30(SendImmediatelyEvent sendImmediatelyEvent) {
        if (this.otherClientPlayerEntity == null) {
            return;
        }
        if ((sendImmediatelyEvent.getPacket904() instanceof DisconnectS2CPacket) || (sendImmediatelyEvent.getPacket904() instanceof DeathMessageS2CPacket)) {
            this.otherClientPlayerEntity = null;
        }
        PlayerInteractEntityC2SPacket packet904 = (sendImmediatelyEvent.getPacket904()) instanceof PlayerInteractEntityC2SPacket ? (PlayerInteractEntityC2SPacket) (sendImmediatelyEvent.getPacket904()) : null;
        if (packet904 instanceof PlayerInteractEntityC2SPacket) {
            PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket = packet904;
            if (LegacyCrystalSearchHelper4.getLegacyCrystalMode2611(playerInteractEntityC2SPacket) == LegacyCrystalSearchHelper4.LegacyCrystalMode.ATTACK && LegacyCrystalSearchHelper4.getEntity2610(playerInteractEntityC2SPacket).getId() == this.otherClientPlayerEntity.getId()) {
                minecraftClient.execute(() -> {
                    this.otherClientPlayerEntity.attack(LegacyCrystalSearchHelper4.getEntity2610(playerInteractEntityC2SPacket));
                });
            }
        }
    }

    @Listen
    public void do29(ChannelRead0Event channelRead0Event) {
        if (channelRead0Event.getPacket904() instanceof GameJoinS2CPacket) {
            this.otherClientPlayerEntity = null;
        }
        if (this.otherClientPlayerEntity == null) {
            return;
        }
        ExplosionS2CPacket packet904 = (channelRead0Event.getPacket904()) instanceof ExplosionS2CPacket ? (ExplosionS2CPacket) (channelRead0Event.getPacket904()) : null;
        if (packet904 instanceof ExplosionS2CPacket) {
            ExplosionS2CPacket explosionS2CPacket = packet904;
            double x = explosionS2CPacket.getX();
            double y = explosionS2CPacket.getY();
            double z = explosionS2CPacket.getZ();
            double distanceTo = this.otherClientPlayerEntity.getPos().distanceTo(new Vec3d(x, y, z)) / 12.0;
            if (distanceTo > 1.0) {
                return;
            }
            float radius = explosionS2CPacket.getRadius();
            double longBitsToDouble = (1.0 - distanceTo) * Explosion.getExposure(new Vec3d(x, y, z), this.otherClientPlayerEntity);
            this.otherClientPlayerEntity.damage(minecraftClient.world.getDamageSources().explosion(new Explosion(minecraftClient.world, minecraftClient.player, x, y, z, radius, false, Explosion.DestructionType.DESTROY, explosionS2CPacket.getAffectedBlocks())), (float) (((((longBitsToDouble * longBitsToDouble) + y) / 2.0) * 7.0 * radius * 2.0) + 1.0));
        }
    }
}
