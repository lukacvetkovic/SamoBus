package Models;

import java.util.List;

/**
 * Created by Mihael on 22.10.2014..
 */
public class SporaLinija {

    private String prekoCega;
    private List<String> vremena;

    public SporaLinija(String prekoCega, List<String> vremenaList) {
        this.vremena = vremenaList;
        this.prekoCega = prekoCega;
    }

    public String getPrekoCega() {
        return prekoCega;
    }


    public List<String> getVremenaList() {
        return vremena;
    }
}
