package me.mioclient;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.client.sound.AudioStream;
import net.minecraft.client.sound.OggAudioStream;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundLoader;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/InputStreamAbstractSoundInstance.class */
public class InputStreamAbstractSoundInstance extends AbstractSoundInstance {
    public InputStream inputStream;

    public InputStreamAbstractSoundInstance(Vec3d vec3d, InputStream inputStream) {
        super(Identifier.of(SearchHelper_4.is1471() ? "mioloader" : "mio", "sound"), SoundCategory.MASTER, SoundInstance.createRandom());
        this.x = vec3d.x;
        this.y = vec3d.y;
        this.z = vec3d.z;
        this.inputStream = inputStream;
    }

    public InputStreamAbstractSoundInstance(InputStream inputStream) {
        super(Identifier.of(SearchHelper_4.is1471() ? "mioloader" : "mio", "sound"), SoundCategory.MASTER, SoundInstance.createRandom());
        this.inputStream = inputStream;
        this.relative = true;
        this.x = 0.0d;
        this.y = 0.0d;
        this.z = -1.0;
    }

    public CompletableFuture<AudioStream> getAudioStream(SoundLoader soundLoader, Identifier identifier, boolean z) {
        try {
            return CompletableFuture.completedFuture(new OggAudioStream(this.inputStream));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
