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
import sijangkar.perhubungan.jatengprov.myapplication.adapter.PeringatanAdapter;
import sijangkar.perhubungan.jatengprov.myapplication.model.Peringatan;
import sijangkar.perhubungan.jatengprov.myapplication.response.responseperingatan;
import sijangkar.perhubungan.jatengprov.myapplication.util.AlertDialogHelper;
import sijangkar.perhubungan.jatengprov.myapplication.util.RecyclerItemClickListener;
import sijangkar.perhubungan.jatengprov.myapplication.util.SharedPrefManager;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.BaseApiService;
import sijangkar.perhubungan.jatengprov.myapplication.util.api.UtilsApi;

import static android.content.ContentValues.TAG;

public class PeringatanPertamaActivity extends Fragment implements PeringatanAdapter.peringatanListener, AlertDialogHelper.AlertDialogListener, SwipeRefreshLayout.OnRefreshListener{

    BaseApiService mApiService;
    Context mContext;
    SharedPrefManager sharedPrefManager;
    ArrayList<Peringatan> peringatanList;
    RecyclerView container;
    LinearLayoutManager layoutManager;
    boolean isMultiSelect = false;
    ActionMode mActionMode;
    Menu context_menu;
    private SwipeRefreshLayout mRefreshLayout;
    PeringatanAdapter adapter;

    ArrayList<Peringatan> user_list = new ArrayList<>();
    ArrayList<Peringatan> multiselect_list = new ArrayList<>();

    AlertDialogHelper alertDialogHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_peringatan_pertama,container,false);

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
                    multiselect_list = new ArrayList<Peringatan>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = PeringatanPertamaActivity.this.getActivity().startActionMode(mActionModeCallback);
                    }
                }

                multi_select(position);

            }
        }));

        getPeringatan();
    }

    //Menampilkan list data user
    private void getPeringatan(){
        Log.d("TAG",SharedPrefManager.SP_TOKEN+"");
        Call<responseperingatan> call = mApiService.peringatanRequest(sharedPrefManager.getAccessToken(), "1");
        call.enqueue(new Callback<responseperingatan>() {
            @Override
            public void onResponse(Call<responseperingatan> call, Response<responseperingatan> response) {

                Log.d("TAG",response.code()+"");

                responseperingatan resource = response.body();
                peringatanList = resource.dataperingatan;

                adapter = new PeringatanAdapter(peringatanList, getContext(), PeringatanPertamaActivity.this);
                container.setAdapter(adapter);
//                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<responseperingatan> call, Throwable t) {
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
            if (multiselect_list.contains(peringatanList.get(position)))
                multiselect_list.remove(peringatanList.get(position));
            else
                multiselect_list.add(peringatanList.get(position));

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
        adapter.usersList=peringatanList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPositiveClick(int from) {
        if(from==1)
        {
            if(multiselect_list.size()>0)
            {
                for(int i=0;i<multiselect_list.size();i++){
                    Toast.makeText(getActivity(), multiselect_list.get(i).getCompanyName(), Toast.LENGTH_SHORT).show();
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
//                    alertDialogHelper.showAlertDialog("","Anda Yakin Akan Melakukan Peringatan ?","DELETE","CANCEL",1,false);
                    new AlertDialog.Builder(getActivity())
                    .setTitle("Penindakan")
                    .setMessage("Anda yakin akan melakukan peringatan?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                          //  deleteSuggestions(position);
                            if(multiselect_list.size()>0)
                            {
                                for(int i=0;i<multiselect_list.size();i++)
                                    beriperingatan(multiselect_list.get(i).getCompanyId());
                //                    peringatanList.remove(multiselect_list.get(i).getCompanyName());
//                                Toast.makeText(getActivity(), multiselect_list.get(i).getCompanyId(), Toast.LENGTH_SHORT).show();

                                adapter.notifyDataSetChanged();

                                if (mActionMode != null) {
                                    mActionMode.finish();
                                }
                //                Toast.makeText(getActivity(), "Peringatan Terkirim", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<Peringatan>();
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
