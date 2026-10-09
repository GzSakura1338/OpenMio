package me.mioclient;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import me.mioclient.event.RenderWithTooltipEvent;
import me.mioclient.module.render.Tooltips;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.util.Formatting;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/HeightSearchHelper4.class */
public final class HeightSearchHelper4 implements SearchHelper_4, TooltipComponent {
    public final List<ItemStack> list;
    public static final Tooltips tooltips = (Tooltips) BaritoneHelper_3.baritoneHelper_4.getModule117(Tooltips.class);

    public HeightSearchHelper4(List<ItemStack> list) {
        this.list = list;
    }

    public int getHeight() {
        return 53;
    }

    public int getWidth(TextRenderer textRenderer) {
        return 155;
    }

    public void drawItems(TextRenderer textRenderer, int i, int i2, DrawContext drawContext) {
        int i3 = 0;
        int i4 = 0;
        int i5 = RenderWithTooltipEvent.num;
        int i6 = RenderWithTooltipEvent.num2;
        ItemStack itemStack = null;
        for (ItemStack itemStack2 : this.list) {
            int i7 = i + (17 * i3);
            int i8 = i2 + (17 * i4);
            boolean z = itemStack2.getCount() == 69;
            if (i5 >= i7 && i5 <= i7 + 17 && i6 >= i8 && i6 <= i8 + 17 && itemStack == null) {
                itemStack = itemStack2;
            }
            int id = ((MapIdComponent) itemStack2.getOrDefault(DataComponentTypes.MAP_ID, new MapIdComponent(-1))).id();
            MapState mapState2653 = BaritoneHelper_3.tooltipsSearchHelper4.getMapState2653(itemStack2, id);
            if (!tooltips.mapOverlay.getValue().booleanValue() || id == -1 || mapState2653 == null) {
                drawContext.drawItem(itemStack2, i7 + 1, i8 + 1);
                drawContext.drawItemInSlot(textRenderer, itemStack2, i7 + 1, i8 + 1, z ? new ArgumentTypeHelper().getArgumentTypeHelper2919(String.valueOf(Formatting.RED)).getString2921("\u00010") : null);
            } else {
                do2376(drawContext.getMatrices(), mapState2653, id, i7 + 1, i8 + 1);
                if (!EntityControlSearchHelper4.is2605(342)) {
                    drawContext.drawItemInSlot(textRenderer, itemStack2, i7 + 1, i8 + 1);
                }
            }
            i3++;
            if (i3 >= 9) {
                i3 = 0;
                i4++;
            }
        }
        if (itemStack == null || itemStack.isOf(Items.AIR)) {
            return;
        }
        drawContext.drawItemTooltip(minecraftClient.textRenderer, itemStack, i5, i6);
    }

    public static void do2376(MatrixStack matrixStack, MapState mapState, int i, float f, float f2) {
        if (mapState == null) {
            return;
        }
        VertexConsumerProvider.Immediate entityVertexConsumers = minecraftClient.getBufferBuilders().getEntityVertexConsumers();
        matrixStack.push();
        matrixStack.translate(f, f2, 0.0f);
        matrixStack.scale(0.125f, 0.125f, 0.0f);
        matrixStack.translate(0.0f, 0.0f, 0.0f);
        minecraftClient.gameRenderer.getMapRenderer().draw(matrixStack, (VertexConsumerProvider) entityVertexConsumers, new MapIdComponent(i), mapState, false, 15728880);
        entityVertexConsumers.draw();
        matrixStack.pop();
    }




    public List<ItemStack> getList2377() {
        return this.list;
    }
}
