package com.codeclinic.agent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomFormSummaryListViewBinding;

import java.util.List;

import static android.text.TextUtils.isEmpty;

public class SummaryFormAdapter extends RecyclerView.Adapter<SummaryFormAdapter.Holder> {
    private final List<FormSummaryModel> arrayList;
    private final Context context;
    private CustomFormSummaryListViewBinding binding;

    public SummaryFormAdapter(List<FormSummaryModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public SummaryFormAdapter.Holder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_form_summary_list_view, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull SummaryFormAdapter.Holder holder, int position) {
        holder.binding.llSection.setVisibility(isEmpty(arrayList.get(position).getSection()) ? View.GONE : View.VISIBLE);
        holder.binding.llQuestion.setVisibility(isEmpty(arrayList.get(position).getSection()) ? View.VISIBLE : View.GONE);

        holder.binding.tvAnswer.setText(arrayList.get(position).getAnswer());
        holder.binding.tvQuestion.setText(arrayList.get(position).getQuestion());
        holder.binding.tvSection.setText(arrayList.get(position).getSection());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomFormSummaryListViewBinding binding;

        public Holder(CustomFormSummaryListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
