package hkccpacmanrobot.controller.androidcontroller.activity;

import android.app.Activity;
import android.os.Bundle;
import hkccpacmanrobot.controller.androidcontroller.R;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
