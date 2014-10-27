package Models;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import Enums.DayType;

/**
 * Created by Mihael on 27.10.2014..
 */
public class DepartureParser {

    private static final String TAG = "NewDepartureHelper";

    private Calendar calendar;
    private Context context;
    private DayType dayType;
    private List<SporaLinija> firstSporaLinijaList;
    private List<SporaLinija> secondSporaLinijaList;

    private List<Integer> brojeviFuckedUpLinija;

    private StringBuilder stringBuilder;

    private int now;

    private List<String> napomeneList;


    public DepartureParser(Context context) {
        if (context != null) {
            this.context = context;
            this.firstSporaLinijaList = new ArrayList<SporaLinija>();
            this.secondSporaLinijaList = new ArrayList<SporaLinija>();
            this.napomeneList = new ArrayList<String>();
            this.stringBuilder = new StringBuilder();

            this.brojeviFuckedUpLinija = Arrays.asList(142, 150, 157, 159, 160);
        } else {
            Log.e(TAG, "Provided context is null !");
        }
    }

    public List<String> getNextDepartures(int linijaNumber) throws IOException {

        List<String> stringDatalist = new LinkedList<String>();
        this.calendar = Calendar.getInstance();
        dayType = getDayOfTheWeek();
        firstSporaLinijaList.clear();
        secondSporaLinijaList.clear();
        napomeneList.clear();
        String day = dayType.toString();
        String fileName = "Vremena/" + linijaNumber + getPeriod() + ".txt";
        String line;

        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
        while (!(line = reader.readLine()).contains(day))
            ;    // -> pozicioniranje na pocetak radnog dana, subote ili nedelje

        String[] firstDepartureStartWords = reader.readLine().split("\\s+");    // "polazak iz IME IME IME:"
        //String firstDepartureStart = reader.readLine().split("\\s+")[2];
        for(int i=0; i<firstDepartureStartWords.length; i++){
            if(i>=2){
                stringBuilder.append(firstDepartureStartWords[i]).append(" ");
            }
        }
        String firstDepartureStart = stringBuilder.toString();          // "IME IME IME:"
        stringBuilder.setLength(0);     // flusha string builder
        stringDatalist.add(firstDepartureStart);


        stringDatalist.add(reader.readLine());        // Vremena svih polazaka


        // Napravim objekt spora linija sa preko cega ide i svim vremenima i dodam u listu sporih linija na toj liniji, tak za sve spore linije
        line = reader.readLine();
        while (line.contains("preko")) {
            firstSporaLinijaList.add(new SporaLinija(line, Arrays.asList(reader.readLine().split("\\s+"))));
            line = reader.readLine();
        }

        String[] secondDepartureStartWords = line.split("\\s+");
        //String secondDepartureStart = line.split("\\s+")[2];       // "Polazak iz OVO SA DRUGE STRANE RELACIJE:"
        for(int i=0; i<secondDepartureStartWords.length; i++){
            if(i>=2){
                stringBuilder.append(secondDepartureStartWords[i]).append(" ");
            }
        }
        String secondDepartureStart = stringBuilder.toString();          // "OVO SA DRUGE STRANE RELACIJE:"
        stringBuilder.setLength(0);
        stringDatalist.add(secondDepartureStart);

        stringDatalist.add(reader.readLine());        // Vremena svih polazaka

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

        reader.close();
        reader = null;

        return stringDatalist;
        //za obicne linije sadrzi.. start1, start2, vremena1, vremena2
        //za spore linije dohvatit sporaLinija list i gledat dal su prazne ili ne

            /*buildDialog(firstDepartureStart, firstDepartureTimes, firstDepartureTimeIndex,
                    secondDepartureStart, secondDepartureTimes, secondDepartureTimeIndex);*/

    }

    public List<SubLinija> obradiLinijeSViseSubLinija(int linijaNumber) throws IOException {
        List<SubLinija> subLinijaList = new LinkedList<SubLinija>();

        this.calendar = Calendar.getInstance();
        dayType = getDayOfTheWeek();
        firstSporaLinijaList.clear();
        secondSporaLinijaList.clear();
        napomeneList.clear();
        String day = dayType.toString();
        String fileName = "Vremena/" + linijaNumber + getPeriod() + ".txt";
        String line;

        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
        while (!(line = reader.readLine()).contains(day))
            ;    // -> pozicioniranje na pocetak radnog dana, subote ili nedelje

        line = reader.readLine();
        while( line.contains("Polasci")){
            subLinijaList.add(new SubLinija(line, reader.readLine(), reader.readLine(), reader.readLine()));
            line = reader.readLine();
        }

        while (line != null) {
            if (line.contains("NAPOMENA")) {
                break;
            } else {
                line = reader.readLine();
            }
        }

        line = reader.readLine();
        while (line != null) {
            if (line.contains(DayType.SUBOTA.toString()) || line.contains(DayType.NEDJELJA_BLAGDAN.toString())) {
                break;
            } else {
                napomeneList.add(line);
                line = reader.readLine();
            }
        }

        reader.close();
        reader = null;

        return subLinijaList;

        /*buildFuckedUpDialog(subLinijaList);*/

    }

    public int getNow(){
        return now = calendar.getTime().getHours() * 60 + calendar.getTime().getMinutes();
    }

    public List<String> getNapomeneList(){
        return napomeneList;
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
    private String getPeriod() {
        if(this.calendar.get(Calendar.YEAR) < 2015){
            return  "zima";
        }
        else{
            if(this.calendar.get(Calendar.MONTH) < Calendar.JUNE){
                return "zima";
            }
            else{
                if(this.calendar.get(Calendar.DAY_OF_MONTH) < 16){
                    return "zima";
                }
                else{
                    return "ljeto";
                }
            }
        }
    }

}
