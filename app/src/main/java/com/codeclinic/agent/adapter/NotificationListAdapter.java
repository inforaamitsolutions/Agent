package com.codeclinic.agent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomNotificationListViewBinding;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.Holder> {
    Context context;
    CustomNotificationListViewBinding binding;

    public NotificationListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_notification_list_view,parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomNotificationListViewBinding binding;
        public Holder(CustomNotificationListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
