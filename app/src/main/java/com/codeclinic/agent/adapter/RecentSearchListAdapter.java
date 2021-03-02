package com.codeclinic.agent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.activity.FAQActivity;
import com.codeclinic.agent.databinding.CustomSearchListViewBinding;

public class RecentSearchListAdapter extends RecyclerView.Adapter<RecentSearchListAdapter.Holder> {
    Context context;
    CustomSearchListViewBinding binding;

    public RecentSearchListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecentSearchListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_search_list_view,parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentSearchListAdapter.Holder holder, int position) {
              binding.txtName.setOnClickListener(view -> {
                  context.startActivity(new Intent(context, FAQActivity.class));
              });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomSearchListViewBinding binding;
        public Holder(CustomSearchListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
