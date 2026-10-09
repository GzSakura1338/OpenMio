package me.mioclient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;
import me.mioclient.MatrixStackEvent;
import me.mioclient.module.render.ESP;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ESPSearchHelper4_2.class */
public class ESPSearchHelper4_2 implements SearchHelper_4 {
    public final ESP eSP;
    public final HashMap<UUID, String> hashMap = new HashMap<>();

    public ESPSearchHelper4_2(ESP esp) {
        this.eSP = esp;
    }

    public boolean is1763(Entity entity) {
        if (entity == null || entity.getBoundingBox() == null || !SearchHelper4_8.is2492(entity.getBoundingBox())) {
            return false;
        }
        Vec3d pos = minecraftClient.gameRenderer.getCamera().getPos();
        if (!(entity instanceof ItemEntity) || entity.getPos().distanceTo(pos) <= this.eSP.range.getValue().intValue()) {
            return ((entity instanceof PlayerEntity) && this.eSP.players.getValue().booleanValue() && entity != minecraftClient.player) || ((entity instanceof ItemEntity) && this.eSP.items.getValue().booleanValue() && this.eSP.mode.getValue() != ESPPredicateMode.TEXT) || (((entity instanceof ExperienceBottleEntity) && this.eSP.exp.getValue().booleanValue()) || ((((entity instanceof PassiveEntity) || (entity instanceof FishEntity) || (entity instanceof SquidEntity)) && this.eSP.animals.getValue().booleanValue()) || (((entity instanceof Monster) && this.eSP.hostiles.getValue().booleanValue()) || ((entity instanceof EnderPearlEntity) && this.eSP.pearls.getValue().booleanValue()))));
        }
        return false;
    }

    public boolean is1764(BlockEntity blockEntity) {
        if (blockEntity != null && SearchHelper4_8.is2492(new Box(blockEntity.getPos()))) {
            return (((blockEntity instanceof ChestBlockEntity) || (blockEntity instanceof BarrelBlockEntity)) && this.eSP.chests.getValue().booleanValue()) || ((blockEntity instanceof BedBlockEntity) && this.eSP.beds.getValue().booleanValue()) || (((blockEntity instanceof EnderChestBlockEntity) && this.eSP.eChests.getValue().booleanValue()) || (((blockEntity instanceof ShulkerBoxBlockEntity) && this.eSP.shulkers.getValue().booleanValue()) || (((blockEntity instanceof AbstractFurnaceBlockEntity) && this.eSP.furnaces.getValue().booleanValue()) || (((blockEntity instanceof DispenserBlockEntity) && this.eSP.dispensers.getValue().booleanValue()) || (((blockEntity instanceof HopperBlockEntity) && this.eSP.hoppers.getValue().booleanValue()) || (((blockEntity instanceof SignBlockEntity) && this.eSP.signs.getValue().booleanValue()) || ((blockEntity instanceof DecoratedPotBlockEntity) && this.eSP.pots.getValue().booleanValue())))))));
        }
        return false;
    }

    public boolean is1765() {
        return this.eSP.chests.getValue().booleanValue() || this.eSP.eChests.getValue().booleanValue() || this.eSP.shulkers.getValue().booleanValue() || this.eSP.beds.getValue().booleanValue() || this.eSP.signs.getValue().booleanValue() || this.eSP.dispensers.getValue().booleanValue() || this.eSP.hoppers.getValue().booleanValue() || this.eSP.furnaces.getValue().booleanValue();
    }

    public void do1766(MatrixStackEvent.Inner_3 inner_3, String str, Vec3d vec3d, float f, Color color, Color color2) {
        double d = PingSpoofHelper.get377(minecraftClient.gameRenderer.getCamera().getPos(), vec3d, f);
        if (color2 != null) {
            SearchHelper_2.searchHelper_2.do567(inner_3.getMatrixStack472(), vec3d, 0.0f, 0.0f, FontsSearchHelper4.fontsSearchHelper4.get1316(str), FontsSearchHelper4.fontsSearchHelper4.get93(), d, color2);
        }
        SearchHelper_2.searchHelper_2.do570(inner_3.getDrawContext474(), str, vec3d, 0.0f, 0.0f, d, color, true);
    }

    public void do1767(UUID uuid) {
        try {
            HttpClient newHttpClient = HttpClient.newHttpClient();
            try {
                JsonObject asJsonObject = JsonParser.parseString((String) newHttpClient.send(HttpRequest.newBuilder().uri(new URI(new ArgumentTypeHelper().getArgumentTypeHelper2919(uuid.toString().replace("-", "")).getString2921("https://sessionserver.mojang.com/session/minecraft/profile/\u0001"))).timeout(Duration.of(3L, ChronoUnit.SECONDS)).GET().build(), HttpResponse.BodyHandlers.ofString()).body()).getAsJsonObject();
                if (asJsonObject.has("name")) {
                    this.hashMap.put(uuid, asJsonObject.get("name").getAsString());
                }
                if (newHttpClient != null) {
                    newHttpClient.close();
                }
            } finally {
            }
        } catch (Exception e) {
        }
    }

    public String getString1768(Entity entity) {
        UUID uuid = null;
        if (!this.eSP.mobOwner.getValue().booleanValue()) {
            return null;
        }
        if (entity instanceof EnderPearlEntity) {
            EnderPearlEntity enderPearlEntity = (EnderPearlEntity) entity;
            if (enderPearlEntity.getOwner() != null) {
                return enderPearlEntity.getOwner().getName().getString();
            }
        }
        if (entity instanceof TameableEntity) {
            uuid = ((TameableEntity) entity).getOwnerUuid();
        }
        if (uuid == null) {
            return null;
        }
        PlayerEntity playerByUuid = minecraftClient.world.getPlayerByUuid(uuid);
        if (playerByUuid != null) {
            String name = playerByUuid.getGameProfile().getName();
            this.hashMap.putIfAbsent(uuid, name);
            return name;
        }
        if (this.hashMap.containsKey(uuid)) {
            return this.hashMap.get(uuid);
        }
        this.hashMap.put(uuid, null);
        UUID uuid2 = uuid;
        executorService.submit(() -> {
            do1767(uuid2);
        });
        return null;
    }

    public Color getColor1769(Entity entity, boolean z) {
        Color color = Color.white;
        if (entity instanceof PlayerEntity) {
            color = z ? this.eSP.fill6.getValue() : this.eSP.outline4.getValue();
        } else if (entity instanceof ItemEntity) {
            color = z ? this.eSP.fill4.getValue() : this.eSP.outline3.getValue();
        } else if (entity instanceof ExperienceBottleEntity) {
            color = z ? this.eSP.fill.getValue() : this.eSP.outline5.getValue();
        } else if ((entity instanceof PassiveEntity) || (entity instanceof FishEntity) || (entity instanceof SquidEntity)) {
            color = z ? this.eSP.fill2.getValue() : this.eSP.outline6.getValue();
        } else if (entity instanceof Monster) {
            color = z ? this.eSP.fill5.getValue() : this.eSP.outline2.getValue();
        } else if (entity instanceof EnderPearlEntity) {
            color = z ? this.eSP.fill3.getValue() : this.eSP.outline.getValue();
        }
        int clamp = (int) MathHelper.clamp(PingSpoofHelper.get382(entity.age - 1, entity.age) * 20.0f, 0.0f, color.getAlpha());
        if ((entity instanceof EnderPearlEntity) || (entity instanceof ExperienceBottleEntity)) {
            clamp = color.getAlpha();
        }
        return MixinMessageIndicatorHelper_2.getColor816(color, clamp);
    }

    public Color getColor1770(BlockEntity blockEntity) {
        int renderColor = minecraftClient.world.getBlockState(blockEntity.getPos()).getMapColor(minecraftClient.world, blockEntity.getPos()).getRenderColor(MapColor.Brightness.HIGH);
        if (blockEntity instanceof ShulkerBoxBlockEntity) {
            return new Color(255, 0, 175);
        }
        if (blockEntity instanceof EnderChestBlockEntity) {
            return new Color(125, 40, 180);
        }
        if (blockEntity instanceof BedBlockEntity) {
            renderColor = ((BedBlockEntity) blockEntity).getColor().getMapColor().getRenderColor(MapColor.Brightness.HIGH);
        }
        return new Color(renderColor & 255, (renderColor >> 8) & 255, (renderColor >> 16) & 255).brighter();
    }
}
