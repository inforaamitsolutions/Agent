package com.codeclinic.agent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomSearchListViewBinding;

public class TopSearchListAdapter extends RecyclerView.Adapter<TopSearchListAdapter.Holder> {
    Context context;
    CustomSearchListViewBinding binding;

    public TopSearchListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TopSearchListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_search_list_view,parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopSearchListAdapter.Holder holder, int position) {
    binding.txtName.setText("Victor Gachangi");
    binding.imgSearch.setImageResource(R.drawable.img_alert);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomSearchListViewBinding binding;
        public Holder(CustomSearchListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
