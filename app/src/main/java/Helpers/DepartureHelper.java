package Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Mihael on 2.10.2014..
 */
public class DepartureHelper {

    public static String getNextDepartures(int number, Context context)  throws IOException{

        Date date = new Date();
        int now = date.getHours()*60 + date.getMinutes();
        int nextDepartureIndex1 = 0;
        int nextDepartureIndex2 = 0;

        String nextDeparture1;
        String nextDeparture2;

        String polazakIz1;
        String polazakIz2;

        String filename = "Vremena/"+number+".txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

        do{
            polazakIz1 = reader.readLine();
        }
        while (! polazakIz1.contains("Polasci"));
        polazakIz1 = polazakIz1.split("\\s+")[2];

        String vremena1[] = reader.readLine().split("\\s+");

        int i=0;
        for(String vrijeme : vremena1){
            nextDepartureIndex1 = parseTimeToMinutes(vrijeme);
            if(nextDepartureIndex1 > now){
                nextDepartureIndex1 = i;
                break;
            }
            i++;
        }


        do{
            polazakIz2 = reader.readLine();
        }
        while (! polazakIz2.contains("Polasci"));
        polazakIz2 = polazakIz2.split("\\s+")[2];

        String vremena2[] = reader.readLine().split("\\s+");

        i=0;
        for(String vrijeme : vremena2){
            nextDepartureIndex2 = parseTimeToMinutes(vrijeme);
            if(nextDepartureIndex2 > now){
                nextDepartureIndex2 = i;
                break;
            }
            i++;
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle("Slijedeci busevi");

        alertDialogBuilder
                .setMessage("Polazak iz " + polazakIz1 + " \n" +
                        "\t\t Polazi u " + vremena1[nextDepartureIndex1] + ", za " + (parseTimeToMinutes(vremena1[nextDepartureIndex1]) - now) + " minuta." + "\n\n" +
                        "Polazak iz " + polazakIz2 + "\n" +
                        "\t\t Polazi u " + vremena2[nextDepartureIndex2] + ", za " + (parseTimeToMinutes(vremena2[nextDepartureIndex2]) - now) + " minuta.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


        return "test";
    }

    private static int parseTimeToMinutes(String time) {
        String parts[] = time.split("\\.");
        return (Integer.parseInt(parts[0]) * 60) + Integer.parseInt(parts[1]);
    }

}
