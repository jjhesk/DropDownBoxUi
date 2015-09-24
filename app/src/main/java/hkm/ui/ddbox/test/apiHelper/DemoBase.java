package hkm.ui.ddbox.test.apiHelper;

import android.app.Application;

import me.drakeet.library.CrashWoodpecker;

/**
 * Created by hesk on 24/9/15.
 */
public class DemoBase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashWoodpecker.fly().to(this);
    }

}
