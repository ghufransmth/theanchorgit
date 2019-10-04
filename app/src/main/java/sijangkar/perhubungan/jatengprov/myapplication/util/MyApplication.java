package sijangkar.perhubungan.jatengprov.myapplication.util;

import android.app.Application;

/**
 * Created by Ghufran on 1/10/2018.
 */

public class MyApplication extends Application {

    // Gloabl declaration of variable to use in whole app

    public static boolean activityVisible; // Variable that will check the
    // current activity state

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }


}
