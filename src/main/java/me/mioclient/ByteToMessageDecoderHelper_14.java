package me.mioclient;

import io.netty.channel.ChannelHandlerContext;
import java.awt.Color;
import java.io.DataInputStream;
import java.util.Objects;
import me.mioclient.ByteToMessageDecoderHelper;
import me.mioclient.module.client.IRC;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/ByteToMessageDecoderHelper_14.class */
public class ByteToMessageDecoderHelper_14 implements ByteToMessageDecoderHelper {
    public static IRC iRC = (IRC) BaritoneHelper_3.baritoneHelper_4.getModule117(IRC.class);
    public String string;
    public String string2;
    public String string3;

    @Override // me.mioclient.ByteToMessageDecoderHelper
    public void do48(ChannelHandlerContext channelHandlerContext) {
        if (!iRC.chat.getValue().booleanValue() || MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().world == null) {
            return;
        }
        if (BaritoneHelper_3.nameTagsSearchHelper4.getSet2312().contains(this.string2.toLowerCase())) {
            return;
        }
        iRC.num++;
        Text empty = Text.empty();
        ((MutableText) empty).append("<");
        Color color814 = iRC.gradient.getValue().booleanValue() ? MixinMessageIndicatorHelper_2.getColor814(MixinMessageIndicatorHelper_2.getColor811(), MixinMessageIndicatorHelper_2.getColor811().darker(), 3000.0, 0.0d) : MixinMessageIndicatorHelper_2.getColor811();
        ((MutableText) empty).append(Text.literal(this.string).styled(style -> {
            Objects.requireNonNull(color814);
            return MixinMessageIndicatorHelper.getStyle340(style, color814::hashCode);
        }));
        ((MutableText) empty).append("> ");
        ((MutableText) empty).append(this.string3);
        MixinMessageIndicatorHelper.do344(empty, MixinMessageIndicatorHelper.getMessageSignatureData337(new ArgumentTypeHelper().getArgumentTypeHelper2906(iRC.num).getArgumentTypeHelper2919(this.string).getArgumentTypeHelper2919(this.string3).getString2921("\u0001\u0001\u0001").hashCode()));
        if (iRC.chatSound.getValue().booleanValue()) {
            if (this.string.contains(BaritoneHelper_3.welcomerHelper.getString2810())) {
                return;
            }
            if (this.string.contains(MinecraftClient.getInstance().getSession().getUsername())) {
                return;
            }
            BaritoneHelper_3.searchHelper4_11.getSearchHelper4_42970(iRC.mode.getValue()).do1820(iRC.volume2.getValue().floatValue());
        }
    }

    @Override // me.mioclient.ByteToMessageDecoderHelper
    /* renamed from: getByteToMessageDecoderHelper_141189, reason: merged with bridge method [inline-methods] */
    public ByteToMessageDecoderHelper_14 getByteToMessageDecoderHelper52(byte[] bArr) throws java.io.IOException {
        DataInputStream dataInputStream2177 = getDataInputStream2177(bArr);
        ByteToMessageDecoderHelper_14 byteToMessageDecoderHelper_14 = new ByteToMessageDecoderHelper_14();
        byteToMessageDecoderHelper_14.string = dataInputStream2177.readUTF();
        byteToMessageDecoderHelper_14.string2 = dataInputStream2177.readUTF();
        byteToMessageDecoderHelper_14.string3 = dataInputStream2177.readUTF();
        return byteToMessageDecoderHelper_14;
    }

    @Override // me.mioclient.ByteToMessageDecoderHelper
    public byte[] getByteArray50() throws java.io.IOException {
        ByteToMessageDecoderHelper.DataOutputStream dataOutputStream2178 = getDataOutputStream2178();
        dataOutputStream2178.writeUTF(this.string);
        dataOutputStream2178.writeUTF(this.string2);
        dataOutputStream2178.writeUTF(this.string3);
        return dataOutputStream2178.getByteArray1843();
    }

    @Override // me.mioclient.ByteToMessageDecoderHelper
    public short get51() {
        return (short) 5;
    }
}
