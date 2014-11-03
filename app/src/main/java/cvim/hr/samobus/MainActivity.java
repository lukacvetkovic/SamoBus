package cvim.hr.samobus;

import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import Helpers.NewDepartureHelper;
import Helpers.SharedPrefsHelper;
import Listeners.SettingsListener;
import Views.SettingsView;
import Views.AllTimesLayout;
import Views.Linija;


public class MainActivity extends Activity implements SettingsListener{

    private Linija linija1;
    private Linija linija2;
    private Linija linija3;
    private Linija linija4;
    private Linija linija5;
    private Linija linija6;
    private Linija linija7;
    private Linija linija8;
    private Linija linija9;
    private Linija linija10;
    private Linija linija11;
    private Linija linija12;
    private Linija linija13;
    private Linija linija14;
    private Linija linija15;

    private RelativeLayout relativeLayout;

    private AllTimesLayout allTimesLayout;

    private NewDepartureHelper departureHelper;
    private SettingsView settingsView;

    private Boolean printDvijeLinije = false;
    private Boolean zakljucajFavorite= false;
    private Boolean prikazBrojaLinije = true;

    private boolean suPrikazaniBrojeviLinija;
    private boolean init = true;

    private SharedPrefsHelper sharedPrefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         /*
        Da bude sve full screen -- ova 2 reda ispod
         */
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        //nije dobro ak je full screen, ako ce trebat cemo vratit

        setContentView(R.layout.activity_main);

        departureHelper = new NewDepartureHelper(this);

        sharedPrefsHelper = new SharedPrefsHelper(MainActivity.this);

        settingsView = new SettingsView(this);

        allTimesLayout = new AllTimesLayout(this);

        getPrefs();

        initLinije();

        setValeuesToLinije();

        //settingsHelper.showSettings(relativeLayout);


    }



    private void setValeuesToLinije() {
        Linija[] linije = {linija1, linija2, linija3, linija4, linija5, linija6, linija7,
                linija8, linija9, linija10, linija11, linija12, linija13, linija14, linija15};

        Properties props = new Properties();
        InputStream inStream = null;

        try {
            inStream = MainActivity.this.getAssets().open("ImeLinije/ime.properties");
            props.load(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer n = 141;

        for (final Linija linija : linije) {
            if( n == 152 || n == 154 || n == 157 || n == 159 || n == 160 ){     //TODO ovo pokrpat kak krpamo linije
                n += 1;
            }
            linija.setBroj(n);
            if (suPrikazaniBrojeviLinija) {
                linija.setLineText(n.toString() + " - " + props.getProperty(n.toString()));
            } else {
                linija.setLineText(props.getProperty(n.toString()));
            }

            n += 1;
            linija.backGroundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //DepartureHelper.getNextDepartures(linija.getBroj(), MainActivity.this);       // Stari departure helper
                        departureHelper.getNextDepartures(linija.getBroj(), printDvijeLinije);
                        //linija.switchFavsState((LinearLayout) findViewById(R.id.favsLinearLayout), (LinearLayout) findViewById(R.id.LinijeLinearLayout));
                    } catch (Exception e) {
                        Log.e("MAIN", "Error reading file from assets");
                        Toast toast = Toast.makeText(MainActivity.this, "There is not txt file for " + linija.getBroj() + " line, yet ;)", Toast.LENGTH_LONG);
                        toast.show();
                        e.printStackTrace();
                    }
                }
            });
            linija.favsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        linija.switchFavsState((LinearLayout) findViewById(R.id.favsLinearLayout), (LinearLayout) findViewById(R.id.LinijeLinearLayout));
                    }
                    catch (Exception e){
                        Log.e("ERR", "Index out of bounds kod switchanja stanja");
                    }
                }
            });
            linija.timesListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    allTimesLayout.showAllDepartureTimes(relativeLayout, linija.getBroj());
                }
            });
        }

        if(inStream != null){
            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initLinije() {
        linija1 = (Linija) findViewById(R.id.linija1);
        linija2 = (Linija) findViewById(R.id.linija2);
        linija3 = (Linija) findViewById(R.id.linija3);
        linija4 = (Linija) findViewById(R.id.linija4);
        linija5 = (Linija) findViewById(R.id.linija5);
        linija6 = (Linija) findViewById(R.id.linija6);
        linija7 = (Linija) findViewById(R.id.linija7);
        linija8 = (Linija) findViewById(R.id.linija8);
        linija9 = (Linija) findViewById(R.id.linija9);
        linija10 = (Linija) findViewById(R.id.linija10);
        linija11 = (Linija) findViewById(R.id.linija11);
        linija12 = (Linija) findViewById(R.id.linija12);
        linija13 = (Linija) findViewById(R.id.linija13);
        linija14 = (Linija) findViewById(R.id.linija14);
        linija15 = (Linija) findViewById(R.id.linija15);

        relativeLayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);

    }

    public void updateLinije(){
        LinearLayout favsLayout = (LinearLayout) findViewById(R.id.favsLinearLayout);
        LinearLayout lineLayout = (LinearLayout) findViewById(R.id.LinijeLinearLayout);

        Linija linija;
        String text;
        if(suPrikazaniBrojeviLinija){
            int childCount = favsLayout.getChildCount();
            for(int i = 0; i < childCount; i++){
                linija =(Linija) favsLayout.getChildAt(i);
                text =(String) linija.lineText.getText();
                linija.setLineText(text.split(" \\- ")[1]);
            }
            childCount = lineLayout.getChildCount();
            for(int i = 0; i < childCount; i++){
                linija =(Linija) lineLayout.getChildAt(i);
                text =(String) linija.lineText.getText();
                linija.setLineText(text.split(" \\- ")[1]);
            }
            suPrikazaniBrojeviLinija = false;
        }
        else{
            int childCount = favsLayout.getChildCount();
            int brojLinije;
            for(int i = 0; i < childCount; i++){
                linija =(Linija) favsLayout.getChildAt(i);
                text =(String) linija.lineText.getText();
                brojLinije = linija.getBroj();
                linija.setLineText(brojLinije + " - " + text);
            }
            childCount = lineLayout.getChildCount();
            for(int i = 0; i < childCount; i++){
                linija =(Linija) lineLayout.getChildAt(i);
                text =(String) linija.lineText.getText();
                brojLinije = linija.getBroj();
                linija.setLineText(brojLinije + " - " + text);
            }
            suPrikazaniBrojeviLinija = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cool_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.aboutUs:
                Intent i = new Intent("cvim.hr.ABOUT");
                startActivity(i);
                break;

            case R.id.preferences:
                settingsView.showSettings(relativeLayout);
                break;

            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPrefs() {
        settingsView.setSettingsListener(this);
        int brojLinija = sharedPrefsHelper.getInt(SharedPrefsHelper.BROJ_LINIJA, 1);
        if(brojLinija == 1){
            printDvijeLinije = false;
        }
        else{
            printDvijeLinije = true;
        }
        zakljucajFavorite = sharedPrefsHelper.getBoolean(SharedPrefsHelper.ZAKLJUCAJ, false);
        prikazBrojaLinije = sharedPrefsHelper.getBoolean(SharedPrefsHelper.PRIKAZ_BROJA, true);
        if(init){
            suPrikazaniBrojeviLinija = prikazBrojaLinije;
            init = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLinije();
    }

    @Override
    public void updatedSettings(int whichSettings) {
        getPrefs();     // Update prefsa svaki puta nakon kaj se zatvori settingsView
        if(whichSettings == SettingsListener.BROJ_UZ_LINIJE){
            updateLinije();
        }
    }
}
