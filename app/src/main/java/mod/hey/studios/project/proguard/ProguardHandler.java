package mod.hey.studios.project.proguard;

import com.besome.sketch.design.DesignActivity.a;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Dp;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class ProguardHandler {

    public static final Type hashMapStringStringType = Helper.TYPE_STRING_MAP;
    public static final Type arrayListStringType = Helper.TYPE_STRING;
    public static String ANDROID_PROGUARD_RULES_PATH = createAndroidRules();
    public static String DEFAULT_PROGUARD_RULES_PATH = "";
    private final String config_path;
    private final String fm_config_path;

    public ProguardHandler(String sc_id) {
        DEFAULT_PROGUARD_RULES_PATH = createDefaultRules(sc_id);
        config_path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/proguard";
        fm_config_path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/proguard_fm";

        if (!FileUtil.isExistFile(config_path)) {
            FileUtil.writeFile(config_path, getDefaultConfig());
        }
    }

    private static String createAndroidRules() {
        String rulePath = FileUtil.getExternalStorageDir() + "/.sketchware/libs/android-proguard-rules.pro";

        if (!FileUtil.isExistFile(rulePath)) {
            FileUtil.writeFile(rulePath, "-dontusemixedcaseclassnames\n-dontskipnonpubliclibraryclasses\n-verbose\n\n-dontoptimize\n-dontpreverify\n\n-keepattributes *Annotation*\n-keep public class com.google.vending.licensing.ILicensingService\n-keep public class com.android.vending.licensing.ILicensingService\n\n-keepclasseswithmembernames class * {\n    native <methods>;\n}\n\n-keepclassmembers public class * extends android.view.View {\n   void set*(***);\n   *** get*();\n}\n\n-keepclassmembers class * extends android.app.Activity {\n   public void *(android.view.View);\n}\n\n-keepclassmembers enum * {\n    public static **[] values();\n    public static ** valueOf(java.lang.String);\n}\n\n-keepclassmembers class * implements android.os.Parcelable {\n  public static final android.os.Parcelable$Creator CREATOR;\n}\n\n-keepclassmembers class **.R$* {\n    public static <fields>;\n}\n\n-dontwarn android.support.**\n\n-keep class android.support.annotation.Keep\n\n-keep @android.support.annotation.Keep class * {*;}\n\n-keepclasseswithmembers class * {\n    @android.support.annotation.Keep <methods>;\n}\n\n-keepclasseswithmembers class * {\n    @android.support.annotation.Keep <fields>;\n}\n\n-keepclasseswithmembers class * {\n    @android.support.annotation.Keep <init>(...);\n}\n\n-dontwarn android.arch.**\n-dontwarn android.lifecycle.**\n-keep class android.arch.** { *; }\n-keep class android.lifecycle.** { *; }\n\n-dontwarn androidx.arch.**\n-dontwarn androidx.lifecycle.**\n-keep class androidx.arch.** { *; }\n-keep class androidx.lifecycle.** { *; }\n");
        }

        return rulePath;
    }

    private static String createDefaultRules(String sc_id) {
        String path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/proguard-rules.pro";

        if (!FileUtil.isExistFile(path)) {
            FileUtil.writeFile(path, "-repackageclasses\n-ignorewarnings\n-dontwarn\n-dontnote");
        }

        return path;
    }

    private String getDefaultConfig() {
        HashMap<String, String> defaultConfig = new HashMap<>();

        defaultConfig.put("enabled", "false");
        defaultConfig.put("debug", "false");

        return new Gson().toJson(defaultConfig);
    }

    public String getCustomProguardRules() {
        return DEFAULT_PROGUARD_RULES_PATH;
    }

    public boolean isDebugFilesEnabled() {
        boolean debugFiles = true;
        if (FileUtil.isExistFile(config_path)) {
            try {
                HashMap<String, String> config = new Gson().fromJson(FileUtil.readFile(config_path), hashMapStringStringType);

                if (!config.containsKey("debug")) return false;

                String debug = config.get("debug");
                if (debug != null) {
                    debugFiles = debug.equals("true");
                }

            } catch (Exception e) {
                debugFiles = false;
            }
        }

        return debugFiles;
    }

    public boolean isProguardEnabled() {
        boolean proguardEnabled = true;
        if (FileUtil.isExistFile(config_path)) {
            try {
                HashMap<String, String> config = new Gson().fromJson(FileUtil.readFile(config_path), hashMapStringStringType);

                String enabled = config.get("enabled");
                if (enabled == null) {
                    proguardEnabled = false;
                } else {
                    proguardEnabled = enabled.equals("true");
                }

            } catch (Exception e) {
                proguardEnabled = false;
            }
        }

        return proguardEnabled;
    }

    public void setProguardEnabled(boolean proguardEnabled) {
        HashMap<String, String> config = new Gson().fromJson(FileUtil.readFile(config_path), hashMapStringStringType);
        config.put("enabled", String.valueOf(proguardEnabled));

        FileUtil.writeFile(config_path, new Gson().toJson(config));
    }

    public boolean libIsProguardFMEnabled(String library) {
        boolean enabled;
        if (isProguardEnabled() && FileUtil.isExistFile(fm_config_path)) {
            String configContent = FileUtil.readFile(fm_config_path);

            if (configContent.isEmpty()) {
                return false;
            }

            try {
                ArrayList<String> config = new Gson().fromJson(configContent, arrayListStringType);
                enabled = config.contains(library);
                return enabled;
            } catch (Exception ignored) {
            }
        }

        return false;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        HashMap<String, Object> config = new Gson().fromJson(FileUtil.readFile(config_path), hashMapStringStringType);
        config.put("debug", String.valueOf(debugEnabled));

        FileUtil.writeFile(config_path, new Gson().toJson(config));
    }

    public void setProguardFMLibs(ArrayList<String> fullModeLibs) {
        FileUtil.writeFile(fm_config_path, new Gson().toJson(fullModeLibs));
    }

    public void start(a dialog, Dp dp) throws Exception {
        if (isProguardEnabled()) {
            if (dialog != null) dialog.c("ProGuarding classes...");
            dp.runProguard();
        }
    }
}
