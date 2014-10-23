package cvim.hr.samobus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Created by Cveki on 22.10.2014..
 */
public class StartingPoint extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(1);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent openStartingPoint= new Intent("cvim.hr.START");
                    startActivity(openStartingPoint);
                }
            };
        };
        timer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
