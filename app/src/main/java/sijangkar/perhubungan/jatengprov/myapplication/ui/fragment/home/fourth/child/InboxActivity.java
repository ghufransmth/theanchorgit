package sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.fourth.child;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseMainFragment;

public class InboxActivity extends BaseMainFragment {

    private Toolbar mToolbar;
    private String[] mTitles;
    private String[] mContents;

    public static InboxActivity newInstance() {

        Bundle args = new Bundle();

        InboxActivity fragment = new InboxActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_inbox, container, false);
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
