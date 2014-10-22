package Helpers;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Calendar;
import java.util.Date;

import Enums.DayType;

/**
 * Created by Mihael on 22.10.2014..
 */
public class NewDepartureHelper {

    private static final String TAG = "NewDepartureHelper";

    private Calendar calendar;
    private Context context;
    private DayType dayType;

    public NewDepartureHelper(Context context){
        if(context != null){
            this.context = context;
            this.calendar = Calendar.getInstance();
            this.dayType = getDayOfTheWeek();
        }
        else{
            Log.e(TAG, "Provided context is null !");
        }
    }

    private DayType getDayOfTheWeek(){
        switch(calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SATURDAY:
                return DayType.SATURDAY;
            case Calendar.SUNDAY:
                return DayType.SUNDAY;
            default:
                return DayType.WORKING_DAY;
        }

    }
}
