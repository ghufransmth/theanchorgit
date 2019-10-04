package sijangkar.perhubungan.jatengprov.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ghufran on 11/26/2017.
 */

public class SharedPrefManager {

    public static final String SP_MAHASISWA_APP = "spMahasiswaApp";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    public static final String SP_RETURN = "success";

    public static final String SP_TOKEN = "token";

    public static final String SP_NAME = "name";

    public static final String SP_EMAIL = "email";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_MAHASISWA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }



    public String getAccessToken(){
        return sp.getString(SP_TOKEN, "");
    }

    public String getSpName(){
        return sp.getString(SP_NAME, "");
    }

    public String getSpEmail(){
        return sp.getString(SP_EMAIL, "");
    }
}
