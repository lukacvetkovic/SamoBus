package Views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cvim.hr.samobus.R;

/**
 * Created by Mihael on 27.10.2014..
 */
public class TimeContainer extends RelativeLayout{

    private ImageButton imgButton;
    private TextView timeText;
    private Context context;

    private String text;

    private View timeContainer;

    public TimeContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TimeContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.text = attrs.getAttributeValue(0);
        init();
    }

    public TimeContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init(){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            timeContainer = layoutInflater.inflate(R.layout.time_container, this, true);
        }
        imgButton = (ImageButton) findViewById(R.id.imgBtnAllTimes);
        timeText = (TextView) findViewById(R.id.txtTime);
    }

    public void setTime(boolean afterNow){
        if(afterNow){
            timeText.setTextColor(Color.parseColor("#777777"));
        }
        else{
            timeText.setTextColor(Color.parseColor("#00ff00"));
        }
        timeText.setText(this.text);
    }

    public void setTimeText(String text){
        this.text = text;
    }
}
