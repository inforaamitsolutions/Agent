package com.codeclinic.agent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomTransactionsListViewBinding;
import com.codeclinic.agent.model.LoanAccountEntryListModel;

import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.Holder> {
    private final List<LoanAccountEntryListModel> arrayList;
    private final Context context;
    private CustomTransactionsListViewBinding binding;

    public TransactionListAdapter(List<LoanAccountEntryListModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_transactions_list_view, parent, false);
        return new Holder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TransactionListAdapter.Holder holder, int position) {
        holder.binding.tvDate.setText(arrayList.get(position).getDateTimeCreated() + "");
        holder.binding.tvTransactionType.setText(arrayList.get(position).getTransactionType() + "");
        holder.binding.tvRefNumber.setText(arrayList.get(position).getReferenceNo() + "");
        holder.binding.tvAmount.setText(arrayList.get(position).getAmount() + "");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomTransactionsListViewBinding binding;

        public Holder(@NonNull CustomTransactionsListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
