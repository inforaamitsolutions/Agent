package com.codeclinic.agent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomCustomerListViewBinding;

public class DefaultListAdapter extends RecyclerView.Adapter<DefaultListAdapter.Holder> {
    Context context;
    CustomCustomerListViewBinding binding;
    public DefaultListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DefaultListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_customer_list_view,parent,false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultListAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomCustomerListViewBinding binding;
        public Holder(CustomCustomerListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
