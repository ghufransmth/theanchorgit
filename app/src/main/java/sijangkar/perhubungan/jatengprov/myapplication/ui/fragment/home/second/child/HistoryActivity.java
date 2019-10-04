package sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.second.child;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseMainFragment;

public class HistoryActivity extends BaseMainFragment {

    private Toolbar mToolbar;
    private String[] mTitles;
    private String[] mContents;

    public static HistoryActivity newInstance() {

        Bundle args = new Bundle();

        HistoryActivity fragment = new HistoryActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_history, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);


    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

}
