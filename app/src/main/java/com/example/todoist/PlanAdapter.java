package com.example.todoist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder>{

    private IClickItem iClickItem;
    private List<Plan> mListPlan;
    private Context context;
    private int layout;

    public PlanAdapter(List<Plan> mListPlan, IClickItem iClickItem) {
        this.mListPlan = mListPlan;
        this.iClickItem = iClickItem;
    }

    public PlanAdapter(List<Plan> mListPlan, Context context, int layout) {
        this.mListPlan = mListPlan;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_plan, parent, false);
        return new PlanViewHolder(view, iClickItem);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.PlanViewHolder holder, int position) {
        Plan plan = mListPlan.get(position);
        if (plan == null){
            return;
        }

        holder.tvName.setText(plan.getName() + "");
        holder.tvContent.setText(plan.getContent() + "");
        holder.tvDate.setText(plan.getDate() + "");
    }

    @Override
    public int getItemCount() {
        if (mListPlan != null){
            return mListPlan.size();
        }
        return 0;
    }

    public class PlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvName, tvContent, tvDate;
        IClickItem iClickItem;

        public PlanViewHolder(@NonNull View itemView, IClickItem iClickItem) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDate = itemView.findViewById(R.id.tv_date);
            this.iClickItem = iClickItem;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            iClickItem.onClickItemPlan(getAdapterPosition());
        }
    }
}
