package com.codeclinic.agent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.InteractionListViewBinding;
import com.codeclinic.agent.model.InteractionListModel;

import java.util.List;

public class AllInteractionsListAdapter extends RecyclerView.Adapter<AllInteractionsListAdapter.Holder> {
    private final List<InteractionListModel> arrayList;
    private final Context context;
    private InteractionListViewBinding binding;

    public AllInteractionsListAdapter(List<InteractionListModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllInteractionsListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.interaction_list_view, parent, false);
        return new Holder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AllInteractionsListAdapter.Holder holder, int position) {
        holder.binding.tvCustomerID.setText(arrayList.get(position).getCustomerId());
        holder.binding.tvCategoryName.setText(arrayList.get(position).getInteractionCategoryName());
        holder.binding.tvType.setText(arrayList.get(position).getInteractionTypeName());
        holder.binding.tvActive.setText(arrayList.get(position).getActive() + "");

        if (position == (arrayList.size() - 1)) {
            holder.binding.view.setVisibility(View.VISIBLE);
        } else {
            holder.binding.view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        InteractionListViewBinding binding;

        public Holder(@NonNull InteractionListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
