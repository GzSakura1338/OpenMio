package me.mioclient.module;

import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import me.mioclient.CryptoHelper;
import me.mioclient.EnumSetting;
import me.mioclient.EnumSettingHelper;
import me.mioclient.ModuleListSearchHelper4_2;
import me.mioclient.SearchHelper_4;
import me.mioclient.StringSetting;
import me.mioclient.api.Setting;
import me.mioclient.event.Listen;
import me.mioclient.event.TickEvent;
import me.mioclient.feature.Stopwatch;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/module/Crypto.class */
public class Crypto extends me.mioclient.ModuleList {
    public static final String string = "MioClient/2.0";
    public Setting<String> setting;
    public Setting<CryptoMode> setting2;
    public final HttpClient httpClient;
    public String string2;
    public Formatting formatting;
    public final Stopwatch stopwatch;

    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/module/Crypto$CryptoMode.class */
    private enum CryptoMode implements EnumSettingHelper {
        USD("USD"),
        EUR("EUR"),
        RUB("RUB"),
        CNY("CNY"),
        TRY("TRY"),
        JPY("JPY"),
        PLN("PLN"),
        BRL("BRL");

        public final String name;

        CryptoMode(String str) {
            this.name = str;
        }

        @Override // me.mioclient.EnumSettingHelper
        public String getName() {
            return this.name;
        }
    }

    public Crypto() {
        super("Crypto", new String[0]);
        this.setting = add(new StringSetting("Coin", "BTC"));
        this.setting2 = add(new EnumSetting("Fiat", CryptoMode.USD));
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).build();
        this.string2 = "0.00";
        this.formatting = Formatting.WHITE;
        this.stopwatch = new Stopwatch();
        do3019(new ModuleListSearchHelper4_2(this, new CryptoHelper(() -> {
            return Text.literal("%s %s%s".formatted(this.setting.getValue().toUpperCase(), this.formatting, this.string2));
        }, () -> {
            return true;
        })));
        this.setting.do2339(() -> {
            this.formatting = Formatting.WHITE;
            this.string2 = "0.00";
            if (is214()) {
                this.stopwatch.setTime(5000L);
            } else {
                this.setting.do2333("UNKNOWN");
            }
        });
    }

    @Listen
    public void do27(TickEvent tickEvent) {
        if (this.stopwatch.is418(5.0, TimeUnit.SECONDS)) {
            executorService.submit(this::do212);
            this.stopwatch.reset();
        }
    }

    public void do212() {
        if (!is214()) {
            this.setting.do2333("UNKNOWN");
            this.string2 = "0.00";
            this.formatting = Formatting.WHITE;
            return;
        }
        String upperCase = this.setting.getValue().toUpperCase(Locale.ROOT);
        String upperCase2 = this.setting2.getValue().getName().toUpperCase(Locale.ROOT);
        try {
            HttpResponse send = this.httpClient.send(HttpRequest.newBuilder().GET().uri(new URI("https://api.coinconvert.net/convert/%s/%s?amount=1".formatted(upperCase, upperCase2))).header("Accept", "application/json").header("User-Agent", "MioClient/2.0").build(), HttpResponse.BodyHandlers.ofString());
            if (send.statusCode() != 200) {
                this.string2 = "UNKNOWN";
                this.formatting = Formatting.WHITE;
                return;
            }
            JsonObject jsonObject = (JsonObject) SearchHelper_4.gson.fromJson((String) send.body(), JsonObject.class);
            if (jsonObject.has("status") && jsonObject.has(upperCase2)) {
                String formatted = "%.2f".formatted(Float.valueOf(jsonObject.get(upperCase2).getAsFloat()));
                float parseFloat = Float.parseFloat(formatted);
                if (!this.string2.equalsIgnoreCase("0.00") && !this.string2.equalsIgnoreCase("UNKNOWN")) {
                    do213(parseFloat);
                }
                this.string2 = formatted;
            }
        } catch (Exception e) {
        }
    }

    public void do213(float f) {
        float parseFloat = Float.parseFloat(this.string2);
        if (Math.abs(f - parseFloat) < 0.01) {
            this.formatting = Formatting.WHITE;
        } else if (parseFloat > f) {
            this.formatting = Formatting.RED;
        } else if (parseFloat < f) {
            this.formatting = Formatting.GREEN;
        }
    }

    public boolean is214() {
        String value = this.setting.getValue();
        return !value.isEmpty() && value.matches("[a-zA-Z]+");
    }
}
