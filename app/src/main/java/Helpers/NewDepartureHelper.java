package Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import Enums.DayType;
import Models.SporaLinija;

/**
 * Class which helps to get when is the next bus acording to current time.
 * Created by Mihael on 22.10.2014..
 */
public class NewDepartureHelper {

    private static final String TAG = "NewDepartureHelper";

    private Calendar calendar;
    private Context context;
    private DayType dayType;
    private List<SporaLinija> firstSporaLinijaList;
    private List<SporaLinija> secondSporaLinijaList;

    private int now;

    private List<String> napomeneList;

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    public NewDepartureHelper(Context context) {
        if (context != null) {
            this.context = context;
            this.calendar = Calendar.getInstance();
            this.firstSporaLinijaList = new ArrayList<SporaLinija>();
            this.secondSporaLinijaList = new ArrayList<SporaLinija>();
            this.alertDialogBuilder = new AlertDialog.Builder(context);
            this.napomeneList = new ArrayList<String>();
        } else {
            Log.e(TAG, "Provided context is null !");
        }
    }

    public void getNextDepartures(int linijaNumber) throws IOException {
        switch (linijaNumber) {
            case 142:
                obradiLinijeSVisePolazaka(linijaNumber);
                break;
            case 150:
                obradiLinijeSVisePolazaka(linijaNumber);
                break;
            case 157:
                obradiLinijeSVisePolazaka(linijaNumber);
                break;
            case 159:
                obradiLinijeSVisePolazaka(linijaNumber);
                break;
            case 160:
                obradiLinijeSVisePolazaka(linijaNumber);
                break;
            default:
                Calendar calendar1 = Calendar.getInstance();
                dayType = getDayOfTheWeek();
                firstSporaLinijaList.clear();
                secondSporaLinijaList.clear();
                napomeneList.clear();
                String day = dayType.toString();
                String fileName = "Vremena/" + linijaNumber + getPeriod() + ".txt";
                String line;

                int firstDepartureTimeIndex, secondDepartureTimeIndex;

                BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
                while (!(line = reader.readLine()).contains(day))
                    ;    // -> pozicioniranje na pocetak radnog dana, subote ili nedelje

                String firstDepartureStart = reader.readLine().split("\\s+")[2];       // polazak iz "OVO_OVDJE:"
                String[] firstDepartureTimes = reader.readLine().split("\\s+");        // Vremena svih polazaka

                // Napravim objekt spora linija sa preko cega ide i svim vremenima i dodam u listu sporih linija na toj liniji, tak za sve spore linije
                line = reader.readLine();
                while (line.contains("preko")) {
                    firstSporaLinijaList.add(new SporaLinija(line, Arrays.asList(reader.readLine().split("\\s+"))));
                    line = reader.readLine();
                }

                String secondDepartureStart = line.split("\\s+")[2];       // Polazak iz "OVO_SA_DRUGE_STRANE_RELACIJE"
                String[] secondDepartureTimes = reader.readLine().split("\\s+");        // Vremena svih polazaka

                line = reader.readLine();
                while (line != null && line.contains("preko")) {
                    secondSporaLinijaList.add(new SporaLinija(line, Arrays.asList(reader.readLine().split("\\s+"))));      // Ista stvar ko gore
                    line = reader.readLine();
                }

                while (line != null) {
                    if (line.contains("NAPOMENA")) {
                        break;
                    } else {
                        line = reader.readLine();
                    }
                }        // Dodemo do napomena iako bi mozda vec trebali biti na njima pozicionirani

                line = reader.readLine();
                while (line != null) {
                    if (line.contains(DayType.SUBOTA.toString()) || line.contains(DayType.NEDJELJA_BLAGDAN.toString())) {
                        break;
                    } else {
                        napomeneList.add(line);
                        line = reader.readLine();
                    }
                }

                now = calendar1.getTime().getHours() * 60 + calendar1.getTime().getMinutes();

                firstDepartureTimeIndex = -1;                            // Tu nadjemo index vremena koje je slijedeci polazak
                int i = 0;                                              // Koristim i tak da provjerim poslje dal ima bus ili ne if(first.. == -1)->nema busa
                if (firstDepartureTimes.length != 1) {
                    for (String departureTime : firstDepartureTimes) {
                        if (parseTimeToMinutes(departureTime) > now) {        // TODO neki "pametniji" search ako ce nam se dat.. (Umjesto foreach petlji)
                            firstDepartureTimeIndex = i;
                            break;
                        } else {
                            i = i + 1;
                        }
                    }
                }
                secondDepartureTimeIndex = -1;
                i = 0;
                if (secondDepartureTimes.length != 1) {
                    for (String departureTime : secondDepartureTimes) {
                        if (parseTimeToMinutes(departureTime) > now) {
                            secondDepartureTimeIndex = i;
                            break;
                        } else {
                            i = i + 1;
                        }
                    }
                }

                reader.close();
                reader = null;

                buildDialog(firstDepartureStart, firstDepartureTimes, firstDepartureTimeIndex,
                        secondDepartureStart, secondDepartureTimes, secondDepartureTimeIndex);
        }

    }

    public void obradiLinijeSVisePolazaka(int linijaNumber) throws IOException {
        //treba napravit
    }


    private DayType getDayOfTheWeek() {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
                return DayType.SUBOTA;
            case Calendar.SUNDAY:
                return DayType.NEDJELJA_BLAGDAN;
            default:
                return DayType.RADNI_DAN;
        }
    }

    private int parseTimeToMinutes(String time) {
        time = time.replace("*", "");
        String[] times = time.split("\\.");
        return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
    }

    /**
     * @return 'zima' or 'ljeto' depending on active bus schedule
     */
    private String getPeriod() {         // // TODO odrediti dal je ljetni ili zimski raspored pa ? "zima" : "ljeto"

        return "zima";
    }

    private String getBrzaIliSpora(String time, int firstOrSecond) {
        String spora = "Spora linija ";
        List<SporaLinija> lista;
        if (firstOrSecond == 1) {
            lista = firstSporaLinijaList;
        } else {
            lista = secondSporaLinijaList;
        }
        for (SporaLinija sporaLinija : lista) {
            if (sporaLinija.getVremenaList().contains(time)) {
                String[] words = sporaLinija.getPrekoCega().replaceAll(":", "").split("\\s+");
                if (words.length >= 3) {
                    String s = "";
                    for (int i = 2; i < words.length; i++) {
                        s = s + " " + words[i];
                    }
                    return spora + "preko" + s;
                } else return spora;
            }
        }
        return "Brza linija";
    }

    // TODO UBACIT STRING BUILDER TU
    private void buildDialog(String firstDepartureStart, String[] firstDepartureTimes, int firstDepartureTimeIndex,
                             String secondDepartureStart, String[] secondDepartureTimes, int secondDepartureTimeIndex) {
        alertDialogBuilder.setTitle("Slijedeci busevi");

        String message = "Polazak iz " + firstDepartureStart + " \n";       // TODO ubaciti da se vise buseva pokaze koji idu
        if (firstDepartureTimeIndex != -1) {
            message = message + "\t\t Polazi u " + firstDepartureTimes[firstDepartureTimeIndex] + "," +
                    " za " + (parseTimeToMinutes(firstDepartureTimes[firstDepartureTimeIndex]) - now) + " minuta.";
            if (firstSporaLinijaList.size() != 0) {
                message = message + "\n\t\t " + getBrzaIliSpora(firstDepartureTimes[firstDepartureTimeIndex], 1);
            }
        } else {
            message = message + "\t\t Nema slijedeceg busa do sutra :(";        // TODO tu ubacit za taxi pitanje sa Intentom
        }

        message = message + "\n\n" + "Polazak iz " + secondDepartureStart + "\n";
        if (secondDepartureTimeIndex != -1) {
            message = message + "\t\t Polazi u " + secondDepartureTimes[secondDepartureTimeIndex] + "," +
                    " za " + (parseTimeToMinutes(secondDepartureTimes[secondDepartureTimeIndex]) - now) + " minuta.";
            if (secondSporaLinijaList.size() != 0) {
                message = message + "\n\t\t " + getBrzaIliSpora(secondDepartureTimes[secondDepartureTimeIndex], 2);
            }
        } else {
            message = message + "\t\t Nema slijedeceg busa do sutra :(";
        }

        if (napomeneList.size() != 0) {       // ima napomena
            message = message + "\n\n Napomene:";

            for (String hint : napomeneList) {
                message = message + "\n " + hint;
            }
        }

        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {        //TODO, discuss -> Mozda tu isto gumb koji pokazuje brojeve svih taksija?
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
