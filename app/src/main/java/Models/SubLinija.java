package Models;

import Containers.SBcontainer;

/**
 * Model za fucked up linije -> linije koje u sebi sadrze vise pod linija -> SubLinije
 *
 * Created by Mihael on 27.10.2014..
 */
public class SubLinija {

    private String relacija;        // samobor - terihaj npr za 142

    private String firstPolazak;        // "Samobor"
    private String secondPolazak;       // "Terihaj"

    private String firstVremena;        // Sva vremana polaska iz samobora
    private String secondVremena;       // Sva vremena polaska iz terihaja

    public SubLinija(String firstPolazak, String firstVremena, String secondPolazak, String secondVremena) {
        this.firstPolazak = parsePolazak(firstPolazak);
        this.secondPolazak = parsePolazak(secondPolazak);
        this.firstVremena = firstVremena;
        this.secondVremena = secondVremena;
        this.relacija = parseRelacija();
    }

    /**
     *
     * Formats and returns string output for this subLinija
     *
     * @param now - int value of time
     * @return
     */
    public SBcontainer getNextDepartures(int now, boolean printDvijeLinije){
        String message = "";
        int firstDepartureTimeIndex = -1;
        int secondDepartureTimeIndex = -1;
        String[] firstDepartureTimes = firstVremena.split("\\s+");
        String[] secondDepartureTimes = secondVremena.split("\\s+");
        boolean hasNapmena = false;


        int i = 0;                                               // Koristim i tak da provjerim poslje dal ima bus ili ne if(first.. == -1)->nema busa
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

        message = message + "Relacija " + relacija + ":\n";

        message = message + "\tPolazak iz " + firstPolazak + "\n";

        if(! printDvijeLinije) {
            if (firstDepartureTimeIndex != -1) {
                message = message + "\t\t Polazi u " + firstDepartureTimes[firstDepartureTimeIndex] + "," +
                        " za " + (parseTimeToMinutes(firstDepartureTimes[firstDepartureTimeIndex]) - now) + " minuta.\n";
                if(firstDepartureTimes[firstDepartureTimeIndex].contains("*")){
                    hasNapmena = true;
                }
            } else {
                message = message + "\t\t Nema slijedeceg busa do sutra :(\n";
            }
        }
        else{
            if (firstDepartureTimeIndex != -1) {
                message = message + "\t\t Polazi u " + firstDepartureTimes[firstDepartureTimeIndex] + "," +
                        " za " + (parseTimeToMinutes(firstDepartureTimes[firstDepartureTimeIndex]) - now) + " minuta.\n";
                if(firstDepartureTimes[firstDepartureTimeIndex].contains("*")){
                    hasNapmena = true;
                }
                firstDepartureTimeIndex++;
                if(firstDepartureTimes.length > firstDepartureTimeIndex){
                    message = message + "\t\t Polazi u " + firstDepartureTimes[firstDepartureTimeIndex] + "," +
                            " za " + (parseTimeToMinutes(firstDepartureTimes[firstDepartureTimeIndex]) - now) + " minuta.\n";
                    if(firstDepartureTimes[firstDepartureTimeIndex].contains("*")){
                        hasNapmena = true;
                    }
                }
                else{
                    message = message + "\t\t Nema slijedeceg busa do sutra :(\n";
                }
            } else {
                message = message + "\t\t Nema slijedeceg busa do sutra :(\n";
                message = message + "\t\t Polazi u "  + firstDepartureTimes[0] + "," +
                        " za " + (parseTimeToMinutes(firstDepartureTimes[0]) - now) + " minuta.\n";     // TODO ovde je bug mozda kod oduzimanja al preumoran sam da ga skuzim xD
                if(firstDepartureTimes[0].contains("*")){
                    hasNapmena = true;
                }
            }
        }

        message = message + "\tPolazak iz " + secondPolazak + "\n";

        if(! printDvijeLinije) {
            if (secondDepartureTimeIndex != -1) {
                message = message + "\t\t Polazi u " + secondDepartureTimes[secondDepartureTimeIndex] + "," +
                        " za " + (parseTimeToMinutes(secondDepartureTimes[secondDepartureTimeIndex]) - now) + " minuta.\n";
                if(secondDepartureTimes[secondDepartureTimeIndex].contains("*")){
                    hasNapmena = true;
                }
            } else {
                message = message + "\t\t Nema slijedeceg busa do sutra :(\n";
            }
        }
        else{
            if (secondDepartureTimeIndex != -1) {
                message = message + "\t\t Polazi u " + secondDepartureTimes[secondDepartureTimeIndex] + "," +
                        " za " + (parseTimeToMinutes(secondDepartureTimes[secondDepartureTimeIndex]) - now) + " minuta.\n";
                if(secondDepartureTimes[secondDepartureTimeIndex].contains("*")){
                    hasNapmena = true;
                }
                secondDepartureTimeIndex++;
                if(secondDepartureTimes.length > secondDepartureTimeIndex){
                    message = message + "\t\t Polazi u " + secondDepartureTimes[secondDepartureTimeIndex] + "," +
                            " za " + (parseTimeToMinutes(secondDepartureTimes[secondDepartureTimeIndex]) - now) + " minuta.\n";
                    if(secondDepartureTimes[secondDepartureTimeIndex].contains("*")){
                        hasNapmena = true;
                    }
                }
                else{
                    message = message + "\t\t Nema slijedeceg busa do sutra :(\n";
                }
            } else {
                message = message + "\t\t Nema slijedeceg busa do sutra :(\n";
                message = message + "\t\t Polazi u " + secondDepartureTimes[0] + "," +
                        " za " + (parseTimeToMinutes(secondDepartureTimes[0]) - now) + " minuta.\n";
                if(secondDepartureTimes[0].contains("*")){
                    hasNapmena = true;
                }
            }
        }

        SBcontainer sBcontainer = new SBcontainer(message, hasNapmena);
        return sBcontainer;
    }

    private int parseTimeToMinutes(String time) {
        time = time.replace("*", "");
        String[] times = time.split("\\.");
        return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
    }

    private String parseRelacija(){     // TODO napravit da iz first polazak i second polazak naparvi "SAMOBOR - TERIHAJ" il tak nes
        StringBuilder stringBuilder = new StringBuilder();
        String adjustedFirstpolazak = firstPolazak.replaceAll(":", "").replaceAll("\\s+", "");
        String adjustedSecondPolazak = secondPolazak.replaceAll(":", "").replaceAll("\\s+", "");
        if(adjustedFirstpolazak.endsWith("A")){
            adjustedFirstpolazak = adjustedFirstpolazak.substring(0, adjustedFirstpolazak.length()-1);
        }
        else if(adjustedFirstpolazak.endsWith("E")){
            adjustedFirstpolazak = adjustedFirstpolazak.substring(0, adjustedFirstpolazak.length()-1);
            adjustedFirstpolazak = adjustedFirstpolazak + "A";
        }
        if(adjustedSecondPolazak.endsWith("A")){
            adjustedSecondPolazak = adjustedSecondPolazak.substring(0, adjustedSecondPolazak.length()-1);
        }
        else if(adjustedSecondPolazak.endsWith("E")){
            adjustedSecondPolazak = adjustedSecondPolazak.substring(0, adjustedSecondPolazak.length()-1);
            adjustedSecondPolazak = adjustedSecondPolazak + "A";
        }

        stringBuilder.append(adjustedFirstpolazak)
                .append(" - ")
                .append(adjustedSecondPolazak);
        return stringBuilder.toString();
    }

    private String parsePolazak(String line){
        StringBuilder stringBuilder = new StringBuilder();
        String[] lineWords = line.split("\\s+");
        for(int i=0; i<lineWords.length; i++){
            if(i>=2){
                stringBuilder.append(lineWords[i]).append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
