package com.codeclinic.agent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomAttributeListViewBinding;
import com.codeclinic.agent.model.CustomerAttributesListModel;

import java.util.List;

public class AttributeListAdapter extends RecyclerView.Adapter<AttributeListAdapter.Holder> {
    private final List<CustomerAttributesListModel> arrayList;
    private final Context context;
    private CustomAttributeListViewBinding binding;

    public AttributeListAdapter(List<CustomerAttributesListModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AttributeListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_attribute_list_view, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AttributeListAdapter.Holder holder, int position) {
        holder.binding.tvAttributeName.setText(arrayList.get(position).getName());
        holder.binding.tvAttributeValue.setText(arrayList.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomAttributeListViewBinding binding;

        public Holder(@NonNull CustomAttributeListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
