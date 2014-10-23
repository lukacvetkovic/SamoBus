package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Mihael on 2.10.2014..
 */
public class Linija extends Button {

    private int broj;

    private String vozniRedFile;

    public Linija(Context context) {
        super(context);
    }

    public Linija(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Linija(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLineText(String text){
        if(text != null) {
            this.setText("" + text);
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
