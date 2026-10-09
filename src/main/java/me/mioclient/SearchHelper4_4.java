package me.mioclient;

import java.io.ByteArrayInputStream;
import me.mioclient.mixin.ducks.DuckAbstractSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/SearchHelper4_4.class */
public class SearchHelper4_4 implements SearchHelper_4 {
    public static final SearchHelper4_4 searchHelper4_4 = new Inner(null);
    public final byte[] byteArr;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/SearchHelper4_4$Inner.class */
    static class Inner extends SearchHelper4_4 {
        public Inner(byte[] bArr) {
            super(bArr);
        }

        @Override // me.mioclient.SearchHelper4_4
        public void do1820(float f) {
        }

        @Override // me.mioclient.SearchHelper4_4
        public void do1821(Vec3d vec3d, float f) {
        }
    }

    public SearchHelper4_4(byte[] bArr) {
        this.byteArr = bArr;
    }

    public void do1016() {
        do1820(1.0f);
    }

    public void do1819(Vec3d vec3d) {
        do1821(vec3d, 1.0f);
    }

    public void do1820(float f) {
        if (minecraftClient.getSoundManager() != null) {
            try {
                minecraftClient.execute(() -> {
                    SoundInstance inputStreamAbstractSoundInstance = new InputStreamAbstractSoundInstance(new ByteArrayInputStream(getByteArray1822()));
                    ((DuckAbstractSoundInstance) inputStreamAbstractSoundInstance).setVolume(f);
                    minecraftClient.getSoundManager().play(inputStreamAbstractSoundInstance);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void do1821(Vec3d vec3d, float f) {
        if (minecraftClient.world != null) {
            try {
                minecraftClient.execute(() -> {
                    SoundInstance inputStreamAbstractSoundInstance = new InputStreamAbstractSoundInstance(vec3d, new ByteArrayInputStream(getByteArray1822()));
                    ((DuckAbstractSoundInstance) inputStreamAbstractSoundInstance).setVolume(f);
                    minecraftClient.getSoundManager().play(inputStreamAbstractSoundInstance);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getByteArray1822() {
        return this.byteArr;
    }
}
