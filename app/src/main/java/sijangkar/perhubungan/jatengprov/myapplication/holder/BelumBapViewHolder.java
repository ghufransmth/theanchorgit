package sijangkar.perhubungan.jatengprov.myapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import sijangkar.perhubungan.jatengprov.myapplication.R;
import sijangkar.perhubungan.jatengprov.myapplication.adapter.BelumBapAdapter;

public class BelumBapViewHolder extends RecyclerView.ViewHolder{

    public TextView companyId;
    public TextView companyName;
    public TextView companySiup;
    public LinearLayout ll_listitem;
    private BelumBapAdapter.OnItemClickListener listener;

    public BelumBapViewHolder(View itemView) {
        super(itemView);
        companyId = (TextView) itemView.findViewById(R.id.companyId);
        companyName = (TextView) itemView.findViewById(R.id.companyName);
        companySiup = (TextView) itemView.findViewById(R.id.companySiup);
        ll_listitem = (LinearLayout) itemView.findViewById(R.id.ll_listitem);
    }


}
