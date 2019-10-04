package sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.peringatan;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.adapter.PagerAdapter;
import sijangkar.perhubungan.jatengprov.myapplication.base.MySupportFragment;
import sijangkar.perhubungan.jatengprov.myapplication.listener.OnItemClickListener;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.CycleFragment;
import sijangkar.perhubungan.jatengprov.myapplication.util.SharedPrefManager;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.BaseApiService;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.UtilsApi;


public class PagerChildFragment extends MySupportFragment {
    private static final String ARG_FROM = "arg_from";

    private int mFrom;

    private RecyclerView mRecy;
    private PagerAdapter mAdapter;
    BaseApiService mApiService;
    Context mContext;
    SharedPrefManager sharedPrefManager;

    public static PagerChildFragment newInstance(int from) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);

        PagerChildFragment fragment = new PagerChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mFrom = args.getInt(ARG_FROM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);

        ButterKnife.bind(_mActivity);
        mContext = _mActivity;
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(_mActivity);

        mAdapter = new PagerAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (getParentFragment() instanceof PeringatanActivity) {
//                    ((PeringatanActivity) getParentFragment()).start(CycleFragment.newInstance(1));
                }
            }
        });


    }


}
