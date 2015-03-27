package edu.hkcc.pacmanrobot.controller.AndroidController.old.src.hkccpacmanrobot.controller.androidcontroller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GameSurface(this));
    }

    @Override
    public void onPause(){
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
