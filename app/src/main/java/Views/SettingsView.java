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

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            settingsView = layoutInflater.inflate(R.layout.settings, this, true);
        }

        /**
         * taj ce zvat static metodu iz maina
         */

        this.button = (Button) findViewById(R.id.bPrimjeni);
        this.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(settingsListener != null){
                    settingsListener.updatedSettings(SettingsListener.BROJ_UZ_LINIJE);
                }
                hideSettings(relativeLayout);
            }
        });

        zakljucajFavorite = (CheckBox) findViewById(R.id.checkZakljucaj);
        zakljucajFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zakljucajFavorite.isChecked()){
                    sharedPrefsHelper.putBoolean(SharedPrefsHelper.ZAKLJUCAJ,true);
                }
                else{
                    sharedPrefsHelper.putBoolean(SharedPrefsHelper.ZAKLJUCAJ,false);
                }
            }
        });

        prikazBrojaUzLinije=(CheckBox)findViewById(R.id.checkBroj);
        prikazBrojaUzLinije.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prikazBrojaUzLinije.isChecked()) {
                    sharedPrefsHelper.putBoolean(SharedPrefsHelper.PRIKAZ_BROJA,true);

                } else {
                    sharedPrefsHelper.putBoolean(SharedPrefsHelper.PRIKAZ_BROJA,false);
                }
            }
        });


        jednaLinija=(RadioButton) findViewById(R.id.rJedna);
        dvijelinije=(RadioButton) findViewById(R.id.rDvije);

        jednaLinija.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jednaLinija.isChecked()) {
                    sharedPrefsHelper.putInt(SharedPrefsHelper.BROJ_LINIJA, 1);
                }
            }
        });

        dvijelinije.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dvijelinije.isChecked()) {
                    sharedPrefsHelper.putInt(SharedPrefsHelper.BROJ_LINIJA, 2);
                }
            }
        });

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
