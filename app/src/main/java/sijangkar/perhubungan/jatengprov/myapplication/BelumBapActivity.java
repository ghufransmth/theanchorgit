package sijangkar.perhubungan.jatengprov.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sijangkar.perhubungan.jatengprov.myapplication.adapter.BelumBapAdapter;
import sijangkar.perhubungan.jatengprov.myapplication.adapter.PeringatanAdapter;
import sijangkar.perhubungan.jatengprov.myapplication.base.BaseBackFragment;
import sijangkar.perhubungan.jatengprov.myapplication.model.Bap;
import sijangkar.perhubungan.jatengprov.myapplication.model.Peringatan;
import sijangkar.perhubungan.jatengprov.myapplication.response.responsebap;
import sijangkar.perhubungan.jatengprov.myapplication.response.responseperingatan;
import sijangkar.perhubungan.jatengprov.myapplication.util.AlertDialogHelper;
import sijangkar.perhubungan.jatengprov.myapplication.util.RecyclerItemClickListener;
import sijangkar.perhubungan.jatengprov.myapplication.util.SharedPrefManager;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.BaseApiService;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.UtilsApi;

import static android.content.ContentValues.TAG;

public class BelumBapActivity extends BaseBackFragment implements BelumBapAdapter.bapListener, AlertDialogHelper.AlertDialogListener, SwipeRefreshLayout.OnRefreshListener{

    BaseApiService mApiService;
    Context mContext;
    SharedPrefManager sharedPrefManager;
    ArrayList<Bap> bapList;
    RecyclerView container;
    LinearLayoutManager layoutManager;
    boolean isMultiSelect = false;
    ActionMode mActionMode;
    Menu context_menu;
    private SwipeRefreshLayout mRefreshLayout;
    BelumBapAdapter adapter;

    ArrayList<Bap> user_list = new ArrayList<>();
    ArrayList<Bap> multiselect_list = new ArrayList<>();

    AlertDialogHelper alertDialogHelper;

    public static BelumBapActivity newInstance(int number) {
        BelumBapActivity fragment = new BelumBapActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_belum_bap,container,false);

        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        container = (RecyclerView) view.findViewById(R.id.player_container);

        ButterKnife.bind(getActivity());
        mContext = getActivity();
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(getActivity());
        alertDialogHelper =new AlertDialogHelper(getActivity().getApplicationContext());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        container.setLayoutManager(manager);
        container.setAdapter(adapter);

        container.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), container, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect)
                    multi_select(position);
                else
                    Toast.makeText(getActivity(), "Details Page", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<Bap>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = BelumBapActivity.this.getActivity().startActionMode(mActionModeCallback);
                    }
                }

                multi_select(position);

            }
        }));

        getBap();
    }

    //Menampilkan list data user
    private void getBap(){
        Log.d("TAG",SharedPrefManager.SP_TOKEN+"");
        Call<responsebap> call = mApiService.belumbapRequest(sharedPrefManager.getAccessToken(),"");
        call.enqueue(new Callback<responsebap>() {
            @Override
            public void onResponse(Call<responsebap> call, Response<responsebap> response) {

                Log.d("TAG",response.code()+"");

                responsebap resource = response.body();
                bapList = resource.databap;

                adapter = new BelumBapAdapter(bapList, getContext(), BelumBapActivity.this);
                container.setAdapter(adapter);
//                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<responsebap> call, Throwable t) {
                Log.d(TAG, "onFailure:usersGet: "+t.toString());
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("REFRESHNEWSFEED", "onRefresh: ");
                mRefreshLayout.setRefreshing(false);
//                offsetLoad = 30;
//                adapter.clear();
//                if(ids.isEmpty()){
//                    usersGet("",search);
//                }else{
//                    usersGet(sharedPrefManager.getSpId(),search);
//                }
            }
        }, 3000);
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(bapList.get(position)))
                multiselect_list.remove(bapList.get(position));
            else
                multiselect_list.add(bapList.get(position));

            if (multiselect_list.size() > 0)
                mActionMode.setTitle("" + multiselect_list.size());
            else
                mActionMode.setTitle("");

            refreshAdapter();

        }
    }

    public void refreshAdapter()
    {
        adapter.selected_usersList=multiselect_list;
        adapter.usersList=bapList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPositiveClick(int from) {
        if(from==1)
        {
            if(multiselect_list.size()>0)
            {
                for(int i=0;i<multiselect_list.size();i++){
                    Toast.makeText(getActivity(), multiselect_list.get(i).getFullnamePerusahaan(), Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(getActivity(), multiselect_list.get(i).getCompanyName(), Toast.LENGTH_SHORT).show();

                adapter.notifyDataSetChanged();

                if (mActionMode != null) {
                    mActionMode.finish();
                }
//                Toast.makeText(getActivity(), "Peringatan Terkirim", Toast.LENGTH_SHORT).show();
            }
        }
        else if(from==2)
        {
//            if (mActionMode != null) {
//                mActionMode.finish();
//            }
//
//            Peringatan mSample = new Peringatan("Name"+peringatanList.size(),"Designation"+peringatanList.size());
//            peringatanList.add(mSample);
//            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_select_all:
                    adapter.selectAll();
                    return true;
                case R.id.action_delete:
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<Bap>();
            refreshAdapter();
        }
    };

    public void beriperingatan(String id)
    {
        Call<responseperingatan> call = mApiService.beriperingatanRequest(sharedPrefManager.getAccessToken(), id);
        call.enqueue(new Callback<responseperingatan>() {
            @Override
            public void onResponse(Call<responseperingatan> call, Response<responseperingatan> response) {

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<responseperingatan> call, Throwable t) {
                Log.d(TAG, "onFailure:usersGet: "+t.toString());
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
