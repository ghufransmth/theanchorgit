package sijangkar.perhubungan.jatengprov.myapplication.util.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sijangkar.perhubungan.jatengprov.myapplication.response.responsebap;
import sijangkar.perhubungan.jatengprov.myapplication.response.responseperingatan;

/**
 * Created by Ghufran on 11/26/2017.
 */

public interface BaseApiService {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("userdata")
    Call<ResponseBody> userDataRequest(@Header("Authorization") String token,
                                       @Field("email") String email);

    @FormUrlEncoded
    @POST("peringatan")
    Call<responseperingatan> peringatanRequest(@Header("Authorization") String token,
                                               @Field("id") String id);

    @FormUrlEncoded
    @POST("beriperingatan")
    Call<responseperingatan> beriperingatanRequest(@Header("Authorization") String token,
                                               @Field("id") String id);

    @FormUrlEncoded
    @POST("dataperusahaan")
    Call<responsebap> belumbapRequest(@Header("Authorization") String token,@Field("id") String id);

    @FormUrlEncoded
    @POST("saveregid")
    Call<ResponseBody> updateregId(@Header("Authorization") String token, @Field("id") String id);

}
