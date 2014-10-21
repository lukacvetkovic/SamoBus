package cvim.hr.samobus;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import Helpers.DepartureHelper;
import Views.Linija;


public class MainActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Button1 = (Button)findViewById(R.id.linija1);
        Button Button2=(Button)findViewById(R.id.linija2);


        obradiGumb(155,Button1);
        obradiGumb(156,Button2);
        /*linija.setBroj(156);
        linija.refresh();
        Button1.setText("156");

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DepartureHelper.getNextDepartures(linija.getBroj(), MainActivity.this);
                } catch (IOException e) {
                    Log.e("MAIN", "Error reading file from assets");
                    e.printStackTrace();
                }
            }
        });*/

    }

    private void obradiGumb(int linijaBroj, Button button) {
        final Linija linija=new Linija(this);
        linija.setBroj(linijaBroj);
        linija.refresh();
        button.setText(linijaBroj);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DepartureHelper.getNextDepartures(linija.getBroj(), MainActivity.this);
                } catch (IOException e) {
                    Log.e("MAIN", "Error reading file from assets");
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
