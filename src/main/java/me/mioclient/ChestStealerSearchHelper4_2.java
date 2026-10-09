package me.mioclient;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import me.mioclient.feature.Progress;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ChestStealerSearchHelper4_2.class */
public class ChestStealerSearchHelper4_2 implements SearchHelper_4 {
    public final Progress progress = new Progress(2.0f);
    public final ItemStack itemStack;
    public final PositionData positionData;
    public final PositionData positionData2;

    public ChestStealerSearchHelper4_2(ItemStack itemStack, PositionData positionData, PositionData positionData2) {
        this.itemStack = itemStack.copy();
        this.positionData = positionData2;
        this.positionData2 = positionData;
        this.progress.do2140(false);
    }

    public void do2548(DrawContext drawContext) {
        this.progress.do2139(true);
        float f = this.progress.get172();
        int i = (int) (this.positionData2.get476() + ((this.positionData.get476() - this.positionData2.get476()) * f));
        int i2 = (int) (this.positionData2.get1222() + ((this.positionData.get1222() - this.positionData2.get1222()) * f));
        drawContext.getMatrices().push();
        if (f > 0.8) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f - ((f - 0.800000011920929f) / 0.20000000298023224f));
        }
        drawContext.drawItem(this.itemStack, i, i2, 0, 100);
        drawContext.drawItemInSlot(minecraftClient.textRenderer, this.itemStack, i, i2);
        if (f > 0.8) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        drawContext.getMatrices().pop();
    }

    public ItemStack getItemStack2549() {
        return this.itemStack;
    }

    public PositionData getPositionData2550() {
        return this.positionData;
    }

    public boolean is2551() {
        return this.progress.get2138() == 2.0f;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChestStealerSearchHelper4_2 chestStealerSearchHelper4_2 = (ChestStealerSearchHelper4_2) obj;
        return Objects.equals(this.itemStack.getItem(), chestStealerSearchHelper4_2.itemStack.getItem()) && Objects.equals(this.positionData, chestStealerSearchHelper4_2.positionData);
    }

    public int hashCode() {
        return Objects.hash(this.itemStack.getItem(), this.positionData);
    }
}
