package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import Helpers.SharedPrefsHelper;
import Listeners.SettingsListener;
import cvim.hr.samobus.MainActivity;
import cvim.hr.samobus.R;

/**
 * Created by Cveki on 24.10.2014..
 */
public class SettingsView extends RelativeLayout {

    private Context context;
    Button button;
    CheckBox zakljucajFavorite;
    CheckBox prikazBrojaUzLinije;
    RadioButton jednaLinija;
    RadioButton dvijelinije;

    private boolean zakljucajFavse;
    private boolean prikazBrojevaLinija;
    private int polazakaZaPrikaz = 1;

    private SettingsListener settingsListener;

    private View settingsView;
    private RelativeLayout relativeLayout;
    SharedPrefsHelper sharedPrefsHelper;

    public SettingsView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public SettingsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public void init() {

        sharedPrefsHelper = new SharedPrefsHelper(context);

        zakljucajFavse = sharedPrefsHelper.getBoolean(SharedPrefsHelper.ZAKLJUCAJ, false);
        prikazBrojevaLinija = sharedPrefsHelper.getBoolean(SharedPrefsHelper.PRIKAZ_BROJA, false);
        polazakaZaPrikaz = sharedPrefsHelper.getInt(SharedPrefsHelper.BROJ_LINIJA, 1);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            settingsView = layoutInflater.inflate(R.layout.settings, this, true);
        }

        this.button = (Button) findViewById(R.id.bPrimjeni);
        this.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean promjenaPrikazaBrojaLinija = ( sharedPrefsHelper.getBoolean(SharedPrefsHelper.PRIKAZ_BROJA, false) != prikazBrojevaLinija );
                boolean promjenaZakljucavanjaFavsa = ( sharedPrefsHelper.getBoolean(SharedPrefsHelper.ZAKLJUCAJ, false ) != zakljucajFavse );
                boolean promjenaPrikazaBroja = ( sharedPrefsHelper.getInt(SharedPrefsHelper.BROJ_LINIJA, 1) != polazakaZaPrikaz );

                updateSharedPreferences();

                if(settingsListener != null){
                    if(promjenaZakljucavanjaFavsa){
                        settingsListener.updatedSettings(SettingsListener.ZAKLJUVACANJE_FAVSA);
                    }
                    if(promjenaPrikazaBroja){
                        settingsListener.updatedSettings(SettingsListener.N_LINIJA_ZA_PRIKAZ);
                    }
                    if(promjenaPrikazaBrojaLinija){
                        settingsListener.updatedSettings(SettingsListener.BROJ_UZ_LINIJE);
                    }

                }
                hideSettings(relativeLayout);
            }
        });

        zakljucajFavorite = (CheckBox) findViewById(R.id.checkZakljucaj);
        zakljucajFavorite.setChecked(zakljucajFavse);               // tak da ako je u settingsima od prije da da -> kvacica je tu
        zakljucajFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zakljucajFavorite.isChecked()){
                    zakljucajFavse = true;
                }
                else{
                    zakljucajFavse = false;
                }
            }
        });

        prikazBrojaUzLinije = (CheckBox)findViewById(R.id.checkBroj);
        prikazBrojaUzLinije.setChecked(prikazBrojevaLinija);
        prikazBrojaUzLinije.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prikazBrojaUzLinije.isChecked()) {
                    prikazBrojevaLinija = true;
                } else {
                    prikazBrojevaLinija = false;
                }
            }
        });


        jednaLinija = (RadioButton) findViewById(R.id.rJedna);
        dvijelinije = (RadioButton) findViewById(R.id.rDvije);
        if(polazakaZaPrikaz == 1){
            jednaLinija.setChecked(true);
        }
        else{
            dvijelinije.setChecked(true);
        }

        jednaLinija.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jednaLinija.isChecked()) {
                    polazakaZaPrikaz = 1;
                }
            }
        });

        dvijelinije.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dvijelinije.isChecked()) {
                    polazakaZaPrikaz = 2;
                }
            }
        });

    }

    private void updateSharedPreferences() {
        sharedPrefsHelper.putBoolean(SharedPrefsHelper.ZAKLJUCAJ, zakljucajFavse);
        sharedPrefsHelper.putBoolean(SharedPrefsHelper.PRIKAZ_BROJA, prikazBrojevaLinija);
        sharedPrefsHelper.putInt(SharedPrefsHelper.BROJ_LINIJA, polazakaZaPrikaz);
    }


    public void showSettings(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
        if (relativeLayout.indexOfChild(this) == -1) {       //Provjera da nije vec inflatean taj child
            this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left));
            relativeLayout.addView(this);
        }
    }

    public void hideSettings(RelativeLayout relativeLayout) {
        if (relativeLayout.indexOfChild(this) != -1) {       // Znaci da ima child taj i da ga mozemo removat
            this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_to_left));
            relativeLayout.removeView(this);
        }
    }

    public void setSettingsListener(SettingsListener settingsListener) {
        this.settingsListener = settingsListener;
    }
}
