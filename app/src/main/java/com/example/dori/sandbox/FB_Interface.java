/**
 * Created by dori on 2/27/2018.
 */
import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class FB_Interface {

    private Application app;

    public void onCreate(Application main) {
        app = main;
        AppEventsLogger.activateApp(app);
    }
}
