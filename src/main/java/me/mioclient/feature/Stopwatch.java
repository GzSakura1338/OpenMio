package me.mioclient.feature;

import java.util.concurrent.TimeUnit;

/* compiled from: 0.java */
/* loaded from: mio-yarn.jar:me/mioclient/feature/Stopwatch.class */
public class Stopwatch {
    public long time = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: 0.java */
    /* loaded from: mio-yarn.jar:me/mioclient/feature/Stopwatch$Inner.class */
    public static /* synthetic */ class Inner {
        public static final /* synthetic */ int[] intArr = new int[TimeUnit.values().length];

        static {
            try {
                intArr[TimeUnit.NANOSECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                intArr[TimeUnit.MICROSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                intArr[TimeUnit.MILLISECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                intArr[TimeUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                intArr[TimeUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                intArr[TimeUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                intArr[TimeUnit.DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public boolean is418(double d, TimeUnit timeUnit) {
        return is419(get424(d, timeUnit));
    }

    public boolean is419(long j) {
        return System.currentTimeMillis() - this.time >= j;
    }

    public boolean is420(long j) {
        boolean is419 = is419(j);
        if (is419) {
            reset();
        }
        return is419;
    }

    public boolean is421(long j, TimeUnit timeUnit) {
        return is420(get424(j, timeUnit));
    }

    public long get422() {
        return System.currentTimeMillis() - this.time;
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }

    public void setTime(long j) {
        this.time = j;
    }

    public void do423(double d, TimeUnit timeUnit) {
        this.time = get424(d, timeUnit);
    }

    public static long get424(double d, TimeUnit timeUnit) {
        switch (Inner.intArr[timeUnit.ordinal()]) {
            case 1:
                return (long) (d * 0.001 * 0.001);
            case 2:
                return (long) (d * 0.001);
            case 3:
                return (long) d;
            case 4:
                return (long) (d * 1000.0);
            case 5:
                return (long) (d * 1000.0 * 60.0);
            case 6:
                return (long) (d * 1000.0 * 60.0 * 60.0);
            case 7:
                return (long) (d * 1000.0 * 60.0 * 60.0 * 24.0);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
