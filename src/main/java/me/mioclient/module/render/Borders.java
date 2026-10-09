package me.mioclient.module.render;

import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import me.mioclient.HoleSnapMode;
import me.mioclient.MatrixStackEvent;
import me.mioclient.PhaseESPHelper;
import me.mioclient.PhaseESPSearchHelper4;
import me.mioclient.SearchHelper_2;
import me.mioclient.SearchHelper_4;
import me.mioclient.api.Category;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.module.Module;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/render/Borders.class */
public class Borders extends Module {
    public Setting<Color> chunk;
    public Setting<Color> map;
    public Setting<Float> lineWidth;
    public Setting<Integer> mapSize;
    public Setting<Integer> level;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/render/Borders$Record.class */
    public static final class Record {
        public final int num;
        public final int num2;
        public final int num3;

        public Record(int i, int i2, int i3) {
            this.num = i;
            this.num2 = i2;
            this.num3 = i3;
        }

        public void do790(MatrixStack matrixStack, Color color, int i, float f) {
            double d = i;
            if (i == -1) {
                d = SearchHelper_4.minecraftClient.player.getLerpedPos(SearchHelper_2.get536()).y;
            }
            PhaseESPSearchHelper4.do1593(matrixStack, getBox791(d), color, f);
        }

        public Box getBox791(double d) {
            int i = this.num3 / 2;
            return new Box(this.num - i, d, this.num2 - i, this.num + i, d, this.num2 + i);
        }




        public int get476() {
            return this.num;
        }

        public int get477() {
            return this.num2;
        }

        public int get792() {
            return this.num3;
        }
    }

    public Borders() {
        super("Borders", "Shows region borders on your screen.", Category.RENDER, new String[0]);
        PhaseESPHelper.do1351(this);
        this.level.getSetting2338("~", HoleSnapMode.MIN);
    }

    @Listen
    public void onEvent(MatrixStackEvent.Inner_3 inner_3) {
        if (this.chunk.getValue().getAlpha() != 0) {
            Record record1948 = getRecord1948();
            MatrixStack matrixStack472 = inner_3.getMatrixStack472();
            record1948.do790(matrixStack472, this.chunk.getValue(), this.level.getValue().intValue(), this.lineWidth.getValue().floatValue());
        }
        if (this.map.getValue().getAlpha() != 0) {
            Record record1949 = getRecord1949();
            MatrixStack matrixStack4722 = inner_3.getMatrixStack472();
            record1949.do790(matrixStack4722, this.map.getValue(), this.level.getValue().intValue(), this.lineWidth.getValue().floatValue());
        }
    }

    public Record getRecord1948() {
        ChunkPos chunkPos = minecraftClient.player.getChunkPos();
        return new Record(chunkPos.getCenterX(), chunkPos.getCenterZ(), 16);
    }

    public Record getRecord1949() {
        int intValue = 128 * (1 << this.mapSize.getValue().intValue());
        return new Record(((MathHelper.floor((minecraftClient.player.getBlockX() + 64.0) / intValue) * intValue) + (intValue / 2)) - 64, ((MathHelper.floor((minecraftClient.player.getBlockZ() + 64.0) / intValue) * intValue) + (intValue / 2)) - 64, intValue);
    }
}
