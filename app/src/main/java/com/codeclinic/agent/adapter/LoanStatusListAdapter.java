package com.codeclinic.agent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomLoanStatusListViewBinding;
import com.codeclinic.agent.model.CustomerStatusListModel;

import java.util.List;

public class LoanStatusListAdapter extends RecyclerView.Adapter<LoanStatusListAdapter.Holder> {
    private final List<CustomerStatusListModel> arrayList;
    private final Context context;
    private CustomLoanStatusListViewBinding binding;

    public LoanStatusListAdapter(List<CustomerStatusListModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public LoanStatusListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_loan_status_list_view, parent, false);
        return new Holder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LoanStatusListAdapter.Holder holder, int position) {
        holder.binding.tvStatus.setText(arrayList.get(position).getStatusName() + "");
        holder.binding.tvStartDate.setText(arrayList.get(position).getStartDateTime() + "");
        holder.binding.tvEndDate.setText(arrayList.get(position).getStopDateTime() + "");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomLoanStatusListViewBinding binding;

        public Holder(@NonNull CustomLoanStatusListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
