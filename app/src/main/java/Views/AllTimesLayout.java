package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Helpers.DepartureHelper;
import Models.DepartureParser;
import cvim.hr.samobus.R;

/**
 * Created by Mihael on 27.10.2014..
 */
public class AllTimesLayout extends RelativeLayout {

    private static final String TAG = "AllTimesLayout";

    private Context context;

    private View allTimesView;

    private LinearLayout leftLineatLayout;
    private LinearLayout rightLinearLayout;
    private Button backButton;

    private DepartureParser departureParser;
    private List<Integer> brojeviFuckedUpLinija;

    private RelativeLayout relativeLayout;

    private List<TimeContainer> leftTimeContainers;
    private List<TimeContainer> rightTimeContainers;

    public AllTimesLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AllTimesLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public AllTimesLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init(){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            allTimesView = layoutInflater.inflate(R.layout.all_departure_times, this, true);
        }
        leftLineatLayout = (LinearLayout) findViewById(R.id.LeftLinearLayout);
        rightLinearLayout = (LinearLayout) findViewById(R.id.RightLinearLayout);
        backButton = (Button) findViewById(R.id.backButton);
        departureParser = new DepartureParser(context);
        this.brojeviFuckedUpLinija = Arrays.asList(142, 150, 157, 159, 160);

        leftTimeContainers = new LinkedList<TimeContainer>();
        rightTimeContainers = new LinkedList<TimeContainer>();

        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideAllDepartureTimes();
            }
        });
    }

    public void showAllDepartureTimes(RelativeLayout relativeLayout, int lineNumber){
        this.relativeLayout = relativeLayout;
        if(relativeLayout.indexOfChild(this) == -1) {       //Provjera da nije vec inflatean taj child
            this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right));
            relativeLayout.addView(this);
        }

        populateDeparturetimes(lineNumber);

    }

    public void hideAllDepartureTimes(){
        if(relativeLayout.indexOfChild(this) != -1) {       // Znaci da ima child taj i da ga mozemo removat
            this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_to_right));
            leftLineatLayout.removeAllViews();
            rightLinearLayout.removeAllViews();
            relativeLayout.removeView(this);
        }
    }

    private void populateDeparturetimes(int lineNumber){
        if(brojeviFuckedUpLinija.contains(lineNumber)){
            populatefuckedDepartureTimes(lineNumber);
        }
        else{
            List<String> stringData = null;
            try {
                stringData = departureParser.getNextDepartures(lineNumber);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error parsing file");
            }

            if(stringData != null) {
                String leftDeparture = stringData.get(0);
                String[] leftDepartureTimes = stringData.get(1).split("\\s+");
                String rightDeparture = stringData.get(2);
                String[] rightDepartureTimes = stringData.get(3).split("\\s+");
                TimeContainer timeContainer = new TimeContainer(context);

                int now = departureParser.getNow();

                timeContainer.setTimeText(leftDeparture);
                timeContainer.setTime(false);
                leftTimeContainers.add(timeContainer);
                for(String depTime : leftDepartureTimes){
                    timeContainer = new TimeContainer(context);
                    timeContainer.setTimeText(depTime);
                    if(parseTimeToMinutes(depTime) < now){
                        timeContainer.setTime(false);
                    }
                    else{
                        timeContainer.setTime(true);
                    }
                    leftTimeContainers.add(timeContainer);
                }

                timeContainer = new TimeContainer(context);
                timeContainer.setTimeText(rightDeparture);
                timeContainer.setTime(false);
                rightTimeContainers.add(timeContainer);
                for(String depTime : rightDepartureTimes){
                    timeContainer = new TimeContainer(context);
                    timeContainer.setTimeText(depTime);
                    if(parseTimeToMinutes(depTime) < now){
                        timeContainer.setTime(false);
                    }
                    else{
                        timeContainer.setTime(true);
                    }
                    rightTimeContainers.add(timeContainer);
                }

                Log.i(TAG, "" + leftTimeContainers.size());
                Log.i(TAG, "" + rightTimeContainers.size());

                for(TimeContainer leftTimeContainer : leftTimeContainers){
                    leftLineatLayout.addView(leftTimeContainer, leftLineatLayout.getChildCount());
                    int brojDjece = leftLineatLayout.getChildCount();
                    for(int i=0; i< brojDjece; i++){
                        TimeContainer timeContainer1 = (TimeContainer) leftLineatLayout.getChildAt(i);
                    }
                }
                for(TimeContainer rigtTimeContainer : rightTimeContainers){
                    rightLinearLayout.addView(rigtTimeContainer, rightLinearLayout.getChildCount());
                }
            }
            Log.i(TAG, "nesto");

        }

    }

    private void populatefuckedDepartureTimes(int lineNumber){

    }

    private int parseTimeToMinutes(String time) {
        time = time.replace("*", "");
        String[] times = time.split("\\.");
        return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
    }
}
