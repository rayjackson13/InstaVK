package tk.rayjackson.instatovk;

import com.vk.sdk.VKSdk;

/**
 * Created by rayjackson on 11/14/17.
 */

public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
