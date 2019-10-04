package sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.bap;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sijangkar.perhubungan.jatengprov.myapplication.BelumBapActivity;
import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseBackFragment;

public class BapActivity extends BaseBackFragment {



//    public static PeringatanActivity newInstance() {
//        return new PeringatanActivity();
//    }

    public static BapActivity newInstance(int number) {
        BapActivity fragment = new BapActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bap, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
//        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
//
//        mToolbar.setTitle(R.string.nav_header_title_peringatan);

        CardView card_view = (CardView) view.findViewById(R.id.CardView1);
        CardView card_view2 = (CardView) view.findViewById(R.id.CardView2);
        CardView card_view3 = (CardView) view.findViewById(R.id.CardView3);

        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
                start(BelumBapActivity.newInstance(1));
            }
        });
    }
}
