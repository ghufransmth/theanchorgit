package sijangkar.perhubungan.jatengprov.myapplication.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import sijangkar.perhubungan.jatengprov.myapplication.model.Bap;

public class responsebap {


    @SerializedName("data")
    public ArrayList<Bap> databap = new ArrayList<>();

    public ArrayList<Bap> getUsers() {
        return databap;
    }

}
