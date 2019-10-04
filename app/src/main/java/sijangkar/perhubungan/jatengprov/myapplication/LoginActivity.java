package sijangkar.perhubungan.jatengprov.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sijangkar.perhubungan.jatengprov.myapplication.util.SharedPrefManager;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.BaseApiService;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.UtilsApi;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    TextView txtUsername,txtPassword,txtAccount;
    private ProgressBar progressBar2;
    Button login;
    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    private String ids;
    private Boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        sharedPrefManager = new SharedPrefManager(this);

        ids = sharedPrefManager.getAccessToken();
//        if(ids.isEmpty()){
//
//        }else{
//            Intent intent = new Intent(LoginActivity.this,
//                    Main2Activity.class);
//            startActivity(intent);
//        }

        txtAccount = (TextView) findViewById(R.id.textView2);
        txtUsername = (TextView) findViewById(R.id.textView4);
        txtPassword = (TextView) findViewById(R.id.textView5);
        username = (EditText) findViewById(R.id.txtusername);
        password = (EditText) findViewById(R.id.txtpassword);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        login = (Button) findViewById(R.id.button);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.petitabold);
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.petitamedium);
        username.setTypeface(typeface2);
        password.setTypeface(typeface2);
        login.setTypeface(typeface2);
        txtUsername.setTypeface(typeface2);
        txtPassword.setTypeface(typeface2);
        txtAccount.setTypeface(typeface2);
        progressBar2.setVisibility(View.GONE);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                requestLogin();

            }
        });
    }

    private void requestLogin(){
        mApiService.loginRequest(username.getText().toString(),password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                result = jsonRESULTS.getBoolean(SharedPrefManager.SP_RETURN);
                                if (result == true){
                                    progressBar2.setVisibility(View.VISIBLE);
                                    // Jika login berhasil maka data nama yang ada di response API
                                    Toast.makeText(mContext, "Berhasil Login", Toast.LENGTH_SHORT).show();

                                    String access_token = jsonRESULTS.getString("token");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, access_token);

                                    // Shared pref name
                                    String name = jsonRESULTS.getString("name");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAME, name);

                                    // Shared pref email
                                    String email = jsonRESULTS.getString("email");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);

                                    // Shared Pref ini berfungsi untuk menjadi trigger session login
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);

                                    startActivity(new Intent(mContext, Main2Activity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();

                                } else {
                                    String error_message = jsonRESULTS.getString("message");
//                                    DialogForm(error_message);
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Gagal login", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }
}
