package sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.fifth.child;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import sijangkar.perhubungan.jatengprov.myapplication.LoginActivity;
import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseMainFragment;
import sijangkar.perhubungan.jatengprov.myapplication.util.SharedPrefManager;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.BaseApiService;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.UtilsApi;

public class AccountActivity extends BaseMainFragment {

    private Toolbar mToolbar;
    private Button buttonlogout;
    private String[] mTitles;
    private String[] mContents;
    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    public static AccountActivity newInstance() {

        Bundle args = new Bundle();

        AccountActivity fragment = new AccountActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
//        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ButterKnife.bind(getActivity());
        mContext = getActivity();
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        sharedPrefManager = new SharedPrefManager(getActivity());
        buttonlogout = (Button) view.findViewById(R.id.button5);
        buttonlogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NAME, "");
                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, "");
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, "");
                Intent intent = new Intent(mContext,
                        LoginActivity.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }
}
