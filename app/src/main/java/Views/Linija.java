package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cvim.hr.samobus.R;

/**
 * Created by Mihael on 2.10.2014..
 */
public class Linija extends RelativeLayout {

    private int broj;

    private boolean isFavs = false;     // TODO negdje u prefse spremit listu onih koji su u favsim i u initu ih initat

    private String vozniRedFile;

    public ImageButton backGroundButton;
    public ImageButton favsButton;
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

    public void switchFavsState(LinearLayout favsLayout, LinearLayout linesLayout){

        if(isFavs){
            isFavs = false;
            if(linesLayout != null && favsLayout != null){
                favsLayout.removeView(this);
                linesLayout.addView(this);
                refreshImage();
            }
        }
        else{
            isFavs = true;
            if(linesLayout != null && favsLayout != null){
                linesLayout.removeView(this);
                favsLayout.addView(this);
                refreshImage();
            }
        }
    }

    public void setLineText(String text){
        if(text != null) {
           this.lineText.setText("" + text);
        }
    }

    private void init(){
        inflate(getContext(), R.layout.linija, this);
        this.backGroundButton = (ImageButton) findViewById(R.id.imgBtnBackground);
        this.favsButton = (ImageButton) findViewById(R.id.imgBtnFavs);
        this.lineText = (TextView) findViewById(R.id.txtLinija);
                                                                        // TODO init dal su u favs ili ne
        refreshImage();
    }

    private void refreshImage(){
        if(this.isFavs) {
            this.favsButton.setImageResource(R.drawable.unfav_button_gray);
        }
        else{
            this.favsButton.setImageResource(R.drawable.fav_button_gray);
        }
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
    }
}
