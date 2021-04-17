package com.codeclinic.agent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomInstallmentsListViewBinding;
import com.codeclinic.agent.model.InstallmentListModel;

import java.util.List;

public class InstallmentListAdapter extends RecyclerView.Adapter<InstallmentListAdapter.Holder> {
    private final List<InstallmentListModel> arrayList;
    private final Context context;
    private CustomInstallmentsListViewBinding binding;

    public InstallmentListAdapter(List<InstallmentListModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public InstallmentListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_installments_list_view, parent, false);
        return new Holder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull InstallmentListAdapter.Holder holder, int position) {
        holder.binding.tvDueDate.setText(arrayList.get(position).getDueDate() + " ");
        holder.binding.tvExpectedAmount.setText(arrayList.get(position).getExpectedTotal() + " ");
        holder.binding.tvPaidAmount.setText(arrayList.get(position).getPaidTotal() + " ");
        holder.binding.tvStatus.setText(arrayList.get(position).getStatus() + " ");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomInstallmentsListViewBinding binding;

        public Holder(@NonNull CustomInstallmentsListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
