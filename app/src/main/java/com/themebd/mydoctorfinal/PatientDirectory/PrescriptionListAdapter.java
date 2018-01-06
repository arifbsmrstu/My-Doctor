package com.themebd.mydoctorfinal.PatientDirectory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.themebd.mydoctorfinal.R;

import java.util.ArrayList;

/**
 * Created by arif on 23-Dec-17.
 */

public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.MdViewHolder> {
    ArrayList<PrescriptionSample> list = new ArrayList<>();
    Context context;

    public PrescriptionListAdapter(Context context,ArrayList<PrescriptionSample> list){
        this.context = context;
        this.list = list;
    }
    
    public PrescriptionListAdapter(){}

    @Override
    public MdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_prescription_list, parent, false);

        return new MdViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MdViewHolder holder, int position) {
        holder.mSubject.setText("Subject : "+list.get(position).getSubject());
        holder.name.setText(list.get(position).getMedicine());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MdViewHolder extends RecyclerView.ViewHolder {
        public TextView name,mSubject;

        public MdViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.mdcn_list);
            mSubject = (TextView) view.findViewById(R.id.subject);
        }
    }
}
