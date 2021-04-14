package com.codeclinic.agent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.activity.ProfileActivity;
import com.codeclinic.agent.databinding.CustomCustomerListViewBinding;
import com.codeclinic.agent.model.customerList.CustomerListModel;

import java.util.List;

import static com.codeclinic.agent.utils.Constants.CustomerID;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.Holder> {
    CustomCustomerListViewBinding binding;
    private List<CustomerListModel> arrayList;
    Context context;

    public CustomerListAdapter(List<CustomerListModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public CustomerListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_customer_list_view, parent, false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.Holder holder, int position) {
        if (position == (arrayList.size() - 1)) {
            holder.binding.view.setVisibility(View.VISIBLE);
        } else {
            holder.binding.view.setVisibility(View.GONE);
        }

        holder.binding.tvCustomerName.setText(arrayList.get(position).getFullName());
        holder.binding.tvPhoneNumber.setText(arrayList.get(position).getPhoneNumber());
        holder.binding.tvLocation.setText(arrayList.get(position).getCountry());

        holder.binding.tvViewCustomer.setOnClickListener(v -> {
            context.startActivity(new Intent(context, ProfileActivity.class)
                    .putExtra(CustomerID, arrayList.get(position).getCustomerId() + ""));
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomCustomerListViewBinding binding;

        public Holder(CustomCustomerListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
