package Helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import cvim.hr.samobus.R;

/**
 * Created by Cveki on 24.10.2014..
 */
public class SettingsHelper extends RelativeLayout{

    private Context context;
    private Button button;

    private View settingsView;
    private RelativeLayout relativeLayout;

    public SettingsHelper(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public SettingsHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public SettingsHelper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        init();
    }

    public void init(){

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
    }

    public void showSettings(RelativeLayout relativeLayout){
        this.relativeLayout = relativeLayout;
        if(relativeLayout.indexOfChild(this) == -1) {       //Provjera da nije vec inflatean taj child
            this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left));
            relativeLayout.addView(this);
        }
    }

    public void hideSettings(RelativeLayout relativeLayout){
        if(relativeLayout.indexOfChild(this) != -1) {       // Znaci da ima child taj i da ga mozemo removat
            this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_to_left));
            relativeLayout.removeView(this);
        }
    }
}
