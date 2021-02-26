package com.codeclinic.agent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.InteractionListViewBinding;

public class InteractionAdapter extends RecyclerView.Adapter<InteractionAdapter.Holder> {
    Context context;
    InteractionListViewBinding binding;

    public InteractionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public InteractionAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.interaction_list_view,parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InteractionAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class Holder extends RecyclerView.ViewHolder {
        InteractionListViewBinding binding;
        public Holder(InteractionListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
