package com.eat.erp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class BeatAdapter extends RecyclerView.Adapter<BeatAdapter.MyViewHolder> {
    private Context mContext;
    private List<Beat> beatList;
    public MyAdapterListener onClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView old_plan, sales_rep, new_plan, r_id;
        public Button approved_route1, reject;

        public MyViewHolder(View view) {
            super(view);
            sales_rep = (TextView) view.findViewById(R.id.sales_rep);
            old_plan = (TextView) view.findViewById(R.id.old_plan);
            new_plan= (TextView) view.findViewById(R.id.new_plan);
            approved_route1=(Button) view.findViewById(R.id.approved_route1);
            reject=(Button) view.findViewById(R.id.reject);
            r_id=(TextView) view.findViewById(R.id.r_id);
            approved_route1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.approveTextViewOnClick(v, getAdapterPosition());
                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.rejectTextViewOnClick(v, getAdapterPosition());
                }
            });
        }
    }

    public interface MyAdapterListener {

        void approveTextViewOnClick(View v, int position);
        void rejectTextViewOnClick(View v, int position);
        //void iconImageViewOnClick(View v, int position);
    }

    public BeatAdapter(Context mContext, List<Beat> beatList, MyAdapterListener listener) {
        this.mContext = mContext;
        this.beatList = beatList;
        onClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_approved_route, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Beat beat = beatList.get(position);
        myViewHolder.sales_rep.setText(beat.getsales_rep());
        myViewHolder.old_plan.setText(beat.getold_plan());
        myViewHolder.new_plan.setText(beat.getnew_plan());
        myViewHolder.r_id.setText(beat.getr_id());
    }

    @Override
    public int getItemCount() {
        return beatList.size();
    }
}
