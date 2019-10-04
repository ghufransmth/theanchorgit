package sijangkar.perhubungan.jatengprov.myapplication;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseMainFragment;
import sijangkar.perhubungan.jatengprov.myapplication.event.TabSelectedEvent;
import sijangkar.perhubungan.jatengprov.myapplication.eventbusactivityscope.EventBusActivityScope;
import sijangkar.perhubungan.jatengprov.myapplication.fragmentation.ISupportFragment;
import sijangkar.perhubungan.jatengprov.myapplication.fragmentation.SupportActivity;
import sijangkar.perhubungan.jatengprov.myapplication.fragmentation.SupportFragment;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.fifth.child.AccountActivity;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.fifth.FiveFragment;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.first.FirstFragment;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.first.child.HomeActivity;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.fourth.FourthFragment;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.fourth.child.InboxActivity;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.second.child.HistoryActivity;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.second.SecondFragment;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.third.child.HelpActivity;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.home.third.ThirdFragment;
import sijangkar.perhubungan.jatengprov.myapplication.ui.fragment.peringatan.PeringatanActivity;
import sijangkar.perhubungan.jatengprov.myapplication.ui.view.BottomBar;
import sijangkar.perhubungan.jatengprov.myapplication.ui.view.BottomBarTab;
import sijangkar.perhubungan.jatengprov.myapplication.util.SharedPrefManager;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.BaseApiService;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.UtilsApi;

public class Main2Activity extends SupportActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Button btnPeringatan;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int FIVE = 4;

    private long TOUCH_TIME = 0;
    private static final long WAIT_TIME = 2000L;

    private SupportFragment[] mFragments = new SupportFragment[5];

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SupportFragment firstFragment = findFragment(HomeActivity.class);
        if (firstFragment == null) {
            mFragments[FIRST] = FirstFragment.newInstance();
            mFragments[SECOND] = SecondFragment.newInstance();
            mFragments[THIRD] = ThirdFragment.newInstance();
            mFragments[FOURTH] = FourthFragment.newInstance();
            mFragments[FIVE] = FiveFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH],
                    mFragments[FIVE]);
        } else {
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(SecondFragment.class);
            mFragments[THIRD] = findFragment(ThirdFragment.class);
            mFragments[FOURTH] = findFragment(FourthFragment.class);
            mFragments[FIVE] = findFragment(FiveFragment.class);
        }

        initView();

    }

    private void initView() {

        ButterKnife.bind(this);
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);

        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);

        mBottomBar.addItem(new BottomBarTab(this, R.drawable.home))
                .addItem(new BottomBarTab(this, R.drawable.historyicon))
                .addItem(new BottomBarTab(this, R.drawable.helpicon))
                .addItem(new BottomBarTab(this, R.drawable.inboxicon))
                .addItem(new BottomBarTab(this, R.drawable.akun));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

                final SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                if (count > 1) {
                    if (currentFragment instanceof FirstFragment) {
                        currentFragment.popToChild(HomeActivity.class, false);
                    } else if (currentFragment instanceof SecondFragment) {
                        getSupportActionBar().hide();
                        currentFragment.popToChild(HistoryActivity.class, false);
                    } else if (currentFragment instanceof ThirdFragment) {
                        currentFragment.popToChild(HelpActivity.class, false);
                    } else if (currentFragment instanceof FourthFragment) {
                        getSupportActionBar().hide();
                        currentFragment.popToChild(InboxActivity.class, false);
                    } else if (currentFragment instanceof FiveFragment) {
                        getSupportActionBar().hide();
                        currentFragment.popToChild(AccountActivity.class, false);
                    }

                    return;
                }

                if (count == 1) {
                    EventBusActivityScope.getDefault(Main2Activity.this).post(new TabSelectedEvent(position));
                }

            }
        });


    }


    @Override
    public void onBackPressedSupport() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }



//
//---------------------------------------------------------------------
//
//    private ViewPager mViewPager;
//    private TabLayout tabLayout;
//    private int[] tabIcons = {
//            R.drawable.ic_home_black_24dp,
//            R.drawable.ic_history_black_24dp,
//            R.drawable.ic_live_help_black_24dp,
//            R.drawable.ic_mail_black_24dp,
//            R.drawable.ic_person_black_24dp
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        getSupportActionBar().hide();
//
//        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
////getting the fragments position to hide and show the toolbar
//
//                if(position == 0){
//                    getSupportActionBar().hide();
//                    tabLayout.getTabAt(0).getIcon().setAlpha(255);
//                    tabLayout.getTabAt(1).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(2).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(3).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(4).getIcon().setAlpha(100);
//                }
//                if(position == 1){
//                    getSupportActionBar().hide();
//                    tabLayout.getTabAt(0).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(1).getIcon().setAlpha(255);
//                    tabLayout.getTabAt(2).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(3).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(4).getIcon().setAlpha(100);
//                }
//                if(position == 2){
//                    getSupportActionBar().hide();
//                    tabLayout.getTabAt(0).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(1).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(2).getIcon().setAlpha(255);
//                    tabLayout.getTabAt(3).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(4).getIcon().setAlpha(100);
//                }
//                if(position == 3){
//                    getSupportActionBar().hide();
//                    tabLayout.getTabAt(0).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(1).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(2).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(3).getIcon().setAlpha(255);
//                    tabLayout.getTabAt(4).getIcon().setAlpha(100);
//                }
//                if(position == 4){
//                    getSupportActionBar().hide();
//                    tabLayout.getTabAt(0).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(1).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(2).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(3).getIcon().setAlpha(100);
//                    tabLayout.getTabAt(4).getIcon().setAlpha(255);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        setupViewPager(mViewPager);
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);
//        setupTabIcons();
//        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
//        int tabsCount = vg.getChildCount();
//        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.petitamedium);
//        for (int j = 0; j < tabsCount; j++) {
//            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
//            int tabChildsCount = vgTab.getChildCount();
//            for (int i = 0; i < tabChildsCount; i++) {
//                View tabViewChild = vgTab.getChildAt(i);
//                if (tabViewChild instanceof TextView) {
//                    ((TextView) tabViewChild).setTypeface(typeface2);
//                    ((TextView) tabViewChild).setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
//                }
//            }
//        }
//    }
//
//    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
//        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
//        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
//    }
//
//    private void setupViewPager(ViewPager viewPager) {
//        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
//        adapter.addFragment(new HomeActivity(),"Home");
//        adapter.addFragment(new HistoryActivity(),"History");
//        adapter.addFragment(new HelpActivity(),"Help");
//        adapter.addFragment(new InboxActivity(),"Inbox");
//        adapter.addFragment(new AccountActivity(),"Account");
//        viewPager.setAdapter(adapter);
//    }
}
