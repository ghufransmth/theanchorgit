package sijangkar.perhubungan.jatengprov.myapplication.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import sijangkar.perhubungan.jatengprov.myapplication.model.Peringatan;

public class responseperingatan {

    @SerializedName("success")
    public int success;

    @SerializedName("data")
    public ArrayList<Peringatan> dataperingatan = new ArrayList<>();

    public ArrayList<Peringatan> getUsers() {
        return dataperingatan;
    }

}
