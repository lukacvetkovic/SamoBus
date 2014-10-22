package cvim.hr.samobus;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Cveki on 22.10.2014..
 */
public class Prefs extends PreferenceActivity {
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}

