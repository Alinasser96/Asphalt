package droidlol.aly.asphalt.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.androidnetworking.AndroidNetworking;

public class AsphaltApp extends Application {
    private static PackageInfo packageInfo;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        AndroidNetworking.initialize(getApplicationContext());

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //disable firebase while debug
//        Fabric.with(this, new Crashlytics.Builder()
//                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return context;
    }

    public static class Device {

        public static final String MANUFACTURE;
        public static final String MODEL;

        static {
            MANUFACTURE = Build.MANUFACTURER;
            MODEL = Build.MODEL;
        }

        public static class OS {

            public static final String TYPE;
            public static final String VERSION;

            static {
                TYPE = "Android";
                VERSION = Build.VERSION.RELEASE;
            }
        }
    }

    public static class Application {

        public static final int VERSION_CODE;
        public static final String VERSION_NAME;

        static {
            VERSION_CODE = packageInfo.versionCode;
            VERSION_NAME = packageInfo.versionName;
        }
    }
}
