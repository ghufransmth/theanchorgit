package sijangkar.perhubungan.jatengprov.myapplication.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.holder.LoadingViewHolder;
import sijangkar.perhubungan.jatengprov.myapplication.holder.PeringatanViewHolder;
import sijangkar.perhubungan.jatengprov.myapplication.model.Peringatan;
import sijangkar.perhubungan.jatengprov.myapplication.util.SharedPrefManager;

public class PeringatanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<Peringatan> myArray;
    public ArrayList<Peringatan> usersList=new ArrayList<>();
    public ArrayList<Peringatan> selected_usersList=new ArrayList<>();
    private List<Peringatan> peringatanList;
    Context context;
    private LayoutInflater mInflater;
    private Typeface tf;
    private peringatanListener listener;
    private static OnItemClickListener listeners;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public PeringatanAdapter(ArrayList<Peringatan> myArray, Context context, peringatanListener listener) {

        this.myArray = myArray;
        this.peringatanList = myArray;
        this.usersList = myArray;
        this.selected_usersList = myArray;
        this.context = context;
        this.tf = ResourcesCompat.getFont(context, R.font.petitamedium);
        this.listener = listener;

    }

    @NonNull
    @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_custom_peringatan, parent, false);
            return new PeringatanViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }


    }

    @Override public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof PeringatanViewHolder) {
            populateItemRows((PeringatanViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }

    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
        Log.d("LOADING NEWS FEED", "showLoadingView: SHOWWWW");
    }
    private void populateItemRows(PeringatanViewHolder holder, int position) {
        final SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        holder.companyId.setText(peringatanList.get(position).getCompanyId());
        holder.companyName.setText(peringatanList.get(position).getCompanyName());
        holder.companySiup.setText(peringatanList.get(position).getCompanySweaty());
        holder.companyId.setVisibility(View.GONE);
        holder.companyName.setTypeface(tf);
        holder.companySiup.setTypeface(tf);

        if(selected_usersList.contains(peringatanList.get(position)))
            holder.ll_listitem.setBackgroundColor(ContextCompat.getColor(context, R.color.list_item_selected_state));
        else
            holder.ll_listitem.setBackgroundColor(ContextCompat.getColor(context, R.color.list_item_normal_state));

    }

    public void selectAll() {
        selected_usersList.clear();
        selected_usersList.addAll(peringatanList);
        notifyDataSetChanged();
    }

    @Override public int getItemCount() {
        return peringatanList.size();
    }

    public interface peringatanListener {
    }

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

}
