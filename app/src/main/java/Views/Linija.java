package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Helpers.SharedPrefsHelper;
import cvim.hr.samobus.R;

/**
 * Created by Mihael on 2.10.2014..
 */
public class Linija extends RelativeLayout {

    private int broj;

    private boolean isFavs = false;     // TODO negdje u prefse spremit listu onih koji su u favsim i u initu ih initat
    private boolean suZakljucaniFavsi = false;

    private String vozniRedFile;

    private SharedPrefsHelper sharedPrefsHelper;

    public ImageButton backGroundButton;
    public ImageButton favsButton;
    public ImageButton timesListButton;
    public TextView lineText;

    public Linija(Context context) {
        super(context);
        init();
    }

    public Linija(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Linija(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setFavs(boolean isFavs, boolean zakljucaniFavsi){       // TODO kod inicijalizacije linija postavit ovo svakoj
        this.isFavs = isFavs;
        this.suZakljucaniFavsi = zakljucaniFavsi;
    }

    public void setZakljucaniFavsi(boolean suZakljucaniFavsi){
        this.suZakljucaniFavsi = suZakljucaniFavsi;
        if(suZakljucaniFavsi){
            lockFavs();
        }
        else{
            unlockFavs();
        }
    }

    public void switchFavsState(LinearLayout favsLayout, LinearLayout linesLayout){

        if(! suZakljucaniFavsi) {
            if (isFavs) {
                isFavs = false;
                if (linesLayout != null && favsLayout != null) {
                    favsLayout.removeView(this);
                    linesLayout.addView(this, getInsertionIndex(this, linesLayout));
                    refreshImage();
                }
                Set<String> set = sharedPrefsHelper.getStringSet(SharedPrefsHelper.FAVS_LISTA, null);
                set.remove(String.valueOf(this.broj));
                sharedPrefsHelper.putStringSet(SharedPrefsHelper.FAVS_LISTA, set);

                Log.i("FAVS", set.toString());

            } else {
                isFavs = true;
                if (linesLayout != null && favsLayout != null) {
                    linesLayout.removeView(this);
                    favsLayout.addView(this, getInsertionIndex(this, favsLayout));
                    refreshImage();
                }
                Set<String> set = sharedPrefsHelper.getStringSet(SharedPrefsHelper.FAVS_LISTA, null);
                set.add(String.valueOf(this.broj));
                sharedPrefsHelper.putStringSet(SharedPrefsHelper.FAVS_LISTA, set);

                Log.i("FAVS", set.toString());
            }
        }
    }

    public void setLineText(String text){
        if(text != null) {
           this.lineText.setText("" + text);
        }
    }

    private int getInsertionIndex(Linija linija, LinearLayout linearLayout){
        List<Linija> lineList = new LinkedList<Linija>();
        int childCount = linearLayout.getChildCount();
        for(int i=0; i<childCount; i++){
            lineList.add((Linija)linearLayout.getChildAt(i));
        }
        childCount = 0;     // recikliram varijablu i koristim ko counter sad
        for(Linija line : lineList){
            if(line.getBroj() > this.broj){
                return childCount;
            }
            else{
                childCount++;
            }
        }
        return lineList.size();
    }

    private void init(){
        inflate(getContext(), R.layout.linija, this);
        this.backGroundButton = (ImageButton) findViewById(R.id.imgBtnBackground);
        this.favsButton = (ImageButton) findViewById(R.id.imgBtnFavs);
        this.timesListButton = (ImageButton) findViewById(R.id.imgBtnAllTimes);
        this.lineText = (TextView) findViewById(R.id.txtLinija);
        this.sharedPrefsHelper = new SharedPrefsHelper(getContext());
        refreshImage();
    }

    private void refreshImage(){
        if(this.isFavs) {
            this.favsButton.setImageResource(R.drawable.unfav_icon_s);
        }
        else{
            this.favsButton.setImageResource(R.drawable.fav_icon_s);
        }
        this.timesListButton.setImageResource(R.drawable.swipe_to_all_times_s);
    }

    private void lockFavs(){
        this.favsButton.setImageResource(R.drawable.lock_icon_s);
    }

    private void unlockFavs(){
        refreshImage();
    }

    public String getVozniRedFile() {
        return vozniRedFile;
    }

    public void setVozniRedFile(String vozniRedFile) {
        this.vozniRedFile = vozniRedFile;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
        if(sharedPrefsHelper.getStringSet(SharedPrefsHelper.FAVS_LISTA, null).contains(String.valueOf(this.broj))){
            this.isFavs = true;
        }
    }
}
