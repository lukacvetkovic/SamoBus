package Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Set;

/**
 * Created by Mihael on 25.10.2014..
 */
public class SharedPrefsHelper {

    public static final String ZAKLJUCAJ = "zakljucaj";
    public static final String PRIKAZ_BROJA = "prikazBroja";
    public static final String BROJ_LINIJA = "brojLinija";

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    private String prefsName = "cvim.smbus";

    private Context context;

    public SharedPrefsHelper(Context context) {
        this.context = context;
        Log.i("CON", "" + context);
        sharedPrefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();
    }

    public String getString(String varName, String defaulrReturnValue){
        return sharedPrefs.getString(varName, defaulrReturnValue);
    }

    public int getInt(String varName, int defaulrReturnValue){
        return sharedPrefs.getInt(varName, defaulrReturnValue);
    }

    public boolean getBoolean(String varName, boolean defaulrReturnValue){
        return sharedPrefs.getBoolean(varName, defaulrReturnValue);
    }

    public Set<String> getString(String varName, Set<String> defaulrReturnValue){
        return sharedPrefs.getStringSet(varName, defaulrReturnValue);
    }

    public void putString(String varName, String value){
        sharedPrefsEditor.putString(varName, value);
        sharedPrefsEditor.apply();
    }

    public void putInt(String varName, int value){
        sharedPrefsEditor.putInt(varName, value);
        sharedPrefsEditor.apply();
    }

    public void putBoolean(String varName, boolean value){
        sharedPrefsEditor.putBoolean(varName, value);
        sharedPrefsEditor.apply();
    }

    public void putStringSet(String varName, Set<String> value){
        sharedPrefsEditor.putStringSet(varName, value);
        sharedPrefsEditor.apply();
    }

    public boolean doesExist(String varName){
        return sharedPrefs.contains(varName);
    }



}
