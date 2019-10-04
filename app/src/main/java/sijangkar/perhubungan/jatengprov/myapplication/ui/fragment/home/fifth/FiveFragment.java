package sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.fifth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseMainFragment;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.fifth.child.AccountActivity;


public class FiveFragment extends BaseMainFragment {

    public static FiveFragment newInstance() {

        Bundle args = new Bundle();

        FiveFragment fragment = new FiveFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(AccountActivity.class) == null) {
            loadRootFragment(R.id.fl_fifth_container, AccountActivity.newInstance());
        }
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

}
