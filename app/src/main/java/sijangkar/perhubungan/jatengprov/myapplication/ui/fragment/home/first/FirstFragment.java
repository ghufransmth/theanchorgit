package sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseMainFragment;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.first.child.HomeActivity;


public class FirstFragment extends BaseMainFragment {

    public static FirstFragment newInstance() {

        Bundle args = new Bundle();

        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (findChildFragment(HomeActivity.class) == null) {
            loadRootFragment(R.id.fl_first_container, HomeActivity.newInstance());
        }
    }

}
