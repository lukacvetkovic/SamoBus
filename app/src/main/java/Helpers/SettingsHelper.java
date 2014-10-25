package Helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import cvim.hr.samobus.R;

/**
 * Created by Cveki on 24.10.2014..
 */
public class SettingsHelper extends RelativeLayout{

    private Context context;

    public SettingsHelper(Context context) {
        super(context);
        this.context=context;
    }

    public SettingsHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public SettingsHelper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
    }

    public void init(){

        inflate(getContext(), R.layout.settings,this);
    }
}
