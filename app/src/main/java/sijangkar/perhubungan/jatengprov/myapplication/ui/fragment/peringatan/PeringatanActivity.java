package sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.peringatan;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import sijangkar.perhubungan.jatengprov.myapplication.PeringatanKeduaActivity;
import sijangkar.perhubungan.jatengprov.myapplication.PeringatanKeempatActivity;
import sijangkar.perhubungan.jatengprov.myapplication.PeringatanKelimaActivity;
import sijangkar.perhubungan.jatengprov.myapplication.PeringatanKetigaActivity;
import sijangkar.perhubungan.jatengprov.myapplication.PeringatanPertamaActivity;
import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.SectionPageAdapter;
import sijangkar.perhubungan.jatengprov.myapplication.adapter.DiscoverFragmentAdapter;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseBackFragment;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseMainFragment;
import sijangkar.perhubungan.jatengprov.myapplication.util.SharedPrefManager;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.BaseApiService;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.UtilsApi;

public class PeringatanActivity extends BaseBackFragment {


    private SectionPageAdapter mSearchTabViewPagerAdapter;

//    public static PeringatanActivity newInstance() {
//        return new PeringatanActivity();
//    }

    public static PeringatanActivity newInstance(int number) {
        PeringatanActivity fragment = new PeringatanActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_peringatan, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mToolbar.setTitle(R.string.nav_header_title_peringatan);
        initToolbarNav(mToolbar);
        setupViewPager(mViewPager);

//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.peringatansatu));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.peringatandua));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.peringatantiga));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.pembekuan));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.pencabutan));
//        mViewPager.setAdapter(new DiscoverFragmentAdapter(getChildFragmentManager(), getString(R.string.peringatansatu), getString(R.string.peringatandua), getString(R.string.peringatantiga), getString(R.string.pembekuan), getString(R.string.pencabutan)));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
        adapter.addFragment(new PeringatanPertamaActivity(),"PERINGATAN I");
        adapter.addFragment(new PeringatanKeduaActivity(),"PERINGATAN II");
        adapter.addFragment(new PeringatanKetigaActivity(),"PERINGATAN III");
        adapter.addFragment(new PeringatanKeempatActivity(),"PEMBEKUAN");
        adapter.addFragment(new PeringatanKelimaActivity(),"PENCABUTAN");
        viewPager.setAdapter(adapter);
    }






}
