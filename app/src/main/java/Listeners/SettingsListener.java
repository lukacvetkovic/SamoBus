package Listeners;

/**
 * Created by Mihael on 4.11.2014..
 */
public interface SettingsListener {

    public static final int BROJ_UZ_LINIJE = 100;
    public static final int ZAKLJUVACANJE_FAVSA = 200;
    public static final int N_LINIJA_ZA_PRIKAZ = 300;

    public void updatedSettings(int whichSettings);
}
