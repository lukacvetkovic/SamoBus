package cvim.hr.samobus;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import Helpers.DepartureHelper;
import Helpers.NewDepartureHelper;
import Views.Linija;


public class MainActivity extends ActionBarActivity {

    private Linija linija1;
    private Linija linija2;
    private Linija linija3;
    private Linija linija4;
    private Linija linija5;
    private Linija linija6;
    private Linija linija7;
    private Linija linija8;
    private Linija linija9;
    private Linija linija10;
    private Linija linija11;
    private Linija linija12;
    private Linija linija13;
    private Linija linija14;
    private Linija linija15;

    private NewDepartureHelper departureHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         /*
        Da bude sve full screen -- ova 2 reda ispod
         */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        departureHelper = new NewDepartureHelper(this);

        linija1 = (Linija) findViewById(R.id.linija1);
        linija2 = (Linija) findViewById(R.id.linija2);
        linija3 = (Linija) findViewById(R.id.linija3);
        linija4 = (Linija) findViewById(R.id.linija4);
        linija5 = (Linija) findViewById(R.id.linija5);
        linija6 = (Linija) findViewById(R.id.linija6);
        linija7 = (Linija) findViewById(R.id.linija7);
        linija8 = (Linija) findViewById(R.id.linija8);
        linija9 = (Linija) findViewById(R.id.linija9);
        linija10 = (Linija) findViewById(R.id.linija10);
        linija11 = (Linija) findViewById(R.id.linija11);
        linija12 = (Linija) findViewById(R.id.linija12);
        linija13 = (Linija) findViewById(R.id.linija13);
        linija14 = (Linija) findViewById(R.id.linija14);
        linija15 = (Linija) findViewById(R.id.linija15);

        Linija[] linije = {linija1, linija2, linija3, linija4, linija5, linija6, linija7,
                linija8, linija9, linija10, linija11, linija12, linija13, linija14, linija15};

        int n = 142;
        for(final Linija linija : linije){
            linija.setBroj(n++);
            linija.refresh();
            linija.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //DepartureHelper.getNextDepartures(linija.getBroj(), MainActivity.this);       // Stari departure helper
                        departureHelper.getNextDepartures(linija.getBroj());
                    } catch (Exception e) {
                        Log.e("MAIN", "Error reading file from assets");
                        Toast toast = Toast.makeText(MainActivity.this, "There is not txt file for "+  linija.getBroj() +" line, yet ;)", Toast.LENGTH_LONG);
                        toast.show();
                        e.printStackTrace();
                    }
                }
            });
        }

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
