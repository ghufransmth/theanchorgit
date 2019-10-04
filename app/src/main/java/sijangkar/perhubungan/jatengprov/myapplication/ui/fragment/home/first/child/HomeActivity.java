package sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.first.child;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.app.Config;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseMainFragment;
import sijangkar.perhubungan.jatengprov.myapplication.eventbusactivityscope.EventBusActivityScope;
import sijangkar.perhubungan.jatengprov.myapplication.fragmentation.SupportFragment;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.bap.BapActivity;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.peringatan.PeringatanActivity;
import sijangkar.perhubungan.jatengprov.myapplication.util.SharedPrefManager;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.BaseApiService;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.UtilsApi;

public class HomeActivity extends BaseMainFragment {

    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final String TAG = HomeActivity.class.getSimpleName();
    private Button buttonperingatan;
    private Button buttonbap;
    private TextView name,email;
    private TextView regisID;

    public static HomeActivity newInstance() {

        Bundle args = new Bundle();

        HomeActivity fragment = new HomeActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {


        ButterKnife.bind(getActivity());
        mContext = getActivity();
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        sharedPrefManager = new SharedPrefManager(getActivity());
        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.petitabold);
        Typeface typeface2 = ResourcesCompat.getFont(getActivity(), R.font.petitamedium);
        getListUser(sharedPrefManager.getAccessToken());


        name = (TextView) view.findViewById(R.id.textView6);
        email = (TextView) view.findViewById(R.id.textView7);

        name.setText(sharedPrefManager.getSpName());
        email.setText(sharedPrefManager.getSpEmail());
        name.setTypeface(typeface2);
        email.setTypeface(typeface2);

        buttonperingatan = (Button) view.findViewById(R.id.button4);
        buttonperingatan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                start(PeringatanActivity.newInstance(1));
//                PeringatanActivity fragment = findFragment(PeringatanActivity.class);
//                if (fragment == null) {
//                    fragment.startWithPopTo(PeringatanActivity.newInstance(), HomeActivity.class, false);
//                } else {
//                        // 如果已经在栈内,则以SingleTask模式start
//                    fragment.start(fragment, SupportFragment.SINGLETASK);
//                }

            }
        });

        buttonbap = (Button) view.findViewById(R.id.button2);
        buttonbap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                start(BapActivity.newInstance(1));

            }
        });

        //FIREBASE MESSAGING
        FirebaseApp.initializeApp(mContext);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getActivity(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };
        displayFirebaseRegId();
        //END FIREBASE MESSAGING
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }

    private void getListUser(String Token){
        //Finally, execute
        Call<ResponseBody> call = mApiService.userDataRequest(Token,"admin@gmail.com");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("TAG",response.code()+"");


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });


    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        FirebaseApp.initializeApp(mContext);
        FirebaseInstanceId.getInstance().getInstanceId()
        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();
                saveregId(token);

//                Toast.makeText(mContext, token, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveregId(String Id){
        mApiService.updateregId(sharedPrefManager.getAccessToken(),Id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
//                            Toast.makeText(mContext, "regId saved", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(mContext, "failed save regId", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
