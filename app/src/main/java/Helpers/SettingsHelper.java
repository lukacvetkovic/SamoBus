package Helpers;

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

import cvim.hr.samobus.R;

/**
 * Created by Cveki on 24.10.2014..
 */
public class SettingsHelper extends RelativeLayout {

    private Context context;
    Button button;
    CheckBox zakljucajFavorite;
    CheckBox prikazBrojaUzLinije;
    RadioButton jednaLinija;
    RadioButton dvijelinije;

    private View settingsView;
    private RelativeLayout relativeLayout;
    SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(context);

    public SettingsHelper(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SettingsHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public SettingsHelper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public void init() {

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

                hideSettings(relativeLayout);
            }
        });

        zakljucajFavorite = (CheckBox) findViewById(R.id.checkZakljucaj);
        zakljucajFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zakljucajFavorite.isChecked()){
                    sharedPrefsHelper.putBoolean("zakljucaj",true);
                }
                else{
                    sharedPrefsHelper.putBoolean("zakljucaj",false);

                }

                //dodaj u sharedpreffse
            }
        });

        prikazBrojaUzLinije=(CheckBox)findViewById(R.id.checkBroj);
        prikazBrojaUzLinije.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prikazBrojaUzLinije.isChecked()) {
                    sharedPrefsHelper.putBoolean("prikazBroja",true);

                } else {
                    sharedPrefsHelper.putBoolean("prikazBroja",false);

                }

                //dodaj u sharedpreffse
            }
        });


        jednaLinija=(RadioButton) findViewById(R.id.rJedna);
        dvijelinije=(RadioButton) findViewById(R.id.rDvije);

        jednaLinija.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jednaLinija.isChecked()) {
                    sharedPrefsHelper.putString("brojLinija","jedna");

                }

                //dodaj u sharedpreffse
            }
        });

        dvijelinije.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dvijelinije.isChecked()) {
                    sharedPrefsHelper.putString("brojLinija","dvije");

                }

                //dodaj u sharedpreffse
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
}
