package com.codeclinic.agent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomTimelineStatusListViewBinding;
import com.codeclinic.agent.model.LoanTimeLineStateListModel;

import java.util.List;

public class TimeLineStatusListAdapter extends RecyclerView.Adapter<TimeLineStatusListAdapter.Holder> {
    private final List<LoanTimeLineStateListModel> arrayList;
    private final Context context;
    private CustomTimelineStatusListViewBinding binding;

    public TimeLineStatusListAdapter(List<LoanTimeLineStateListModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeLineStatusListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_timeline_status_list_view, parent, false);
        return new Holder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TimeLineStatusListAdapter.Holder holder, int position) {
        holder.binding.tvStatus.setText(arrayList.get(position).getStateName() + "");
        holder.binding.tvStartDate.setText(arrayList.get(position).getStartDateTime() + "");
        holder.binding.tvEndDate.setText(arrayList.get(position).getStopDateTime() + "");
        holder.binding.tvActive.setText(arrayList.get(position).getStateStatus() + "");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomTimelineStatusListViewBinding binding;

        public Holder(@NonNull CustomTimelineStatusListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
