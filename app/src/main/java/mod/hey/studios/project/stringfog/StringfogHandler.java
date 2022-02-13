package mod.hey.studios.project.stringfog;

import com.besome.sketch.design.DesignActivity.a;
import com.google.gson.Gson;

import java.util.HashMap;

import a.a.a.Dp;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.project.proguard.ProguardHandler;

public class StringfogHandler {

    private final String config_path;

    public StringfogHandler(String sc_id) {
        config_path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/" + sc_id + "/stringfog");

        if (!FileUtil.isExistFile(config_path)) FileUtil.writeFile(config_path, getDefaultConfig());
    }

    private static String getDefaultConfig() {
        HashMap<String, String> config = new HashMap<>();
        config.put("enabled", "false");

        return new Gson().toJson(config);
    }

    public boolean isStringfogEnabled() {
        boolean enabled;

        if (FileUtil.isExistFile(config_path)) {
            HashMap<String, String> config = null;

            try {
                config = new Gson().fromJson(FileUtil.readFile(config_path), ProguardHandler.hashMapStringStringType);
            } finally {
                Object enabledValue;

                enabled = (config != null)
                        && (enabledValue = config.get("enabled")) != null
                        && enabledValue.equals("true");
            }

            return enabled;
        }

        return false;
    }

    public void setStringfogEnabled(boolean enabled) {
        HashMap<String, String> config = new Gson().fromJson(FileUtil.readFile(config_path), ProguardHandler.hashMapStringStringType);
        config.put("enabled", Boolean.valueOf(enabled).toString());

        FileUtil.writeFile(config_path, new Gson().toJson(config));
    }

    /**
     * Check if StringFog is enabled for the project, and run it if it is.
     *
     * @param dialog An optional {@link a} object, to update progress to the user
     * @param dp     The {@link Dp} object that's building the project
     */
    public void start(a dialog, Dp dp) {
        if (isStringfogEnabled()) {
            if (dialog != null) dialog.c("Running StringFog...");
            dp.runStringfog();
        }
    }
}
