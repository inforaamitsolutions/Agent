package com.codeclinic.agent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomInteractionCategoryListViewBinding;
import com.codeclinic.agent.model.InteractionCategoryListModel;

import java.util.List;

public class InteractionCategoryListAdapter extends RecyclerView.Adapter<InteractionCategoryListAdapter.Holder> {
    private final List<InteractionCategoryListModel> arrayList;
    private final Context context;
    private final SelectCategoryCallBack selectCategoryCallBack;
    private CustomInteractionCategoryListViewBinding binding;

    public InteractionCategoryListAdapter(List<InteractionCategoryListModel> arrayList,
                                          Context context,
                                          SelectCategoryCallBack selectCategoryCallBack) {
        this.arrayList = arrayList;
        this.context = context;
        this.selectCategoryCallBack = selectCategoryCallBack;
    }

    @NonNull
    @Override
    public InteractionCategoryListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_interaction_category_list_view, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InteractionCategoryListAdapter.Holder holder, int position) {
        holder.binding.tvCategoryName.setText(arrayList.get(position).getName());
        holder.binding.cardView.setOnClickListener(v -> {
            selectCategoryCallBack.selectInteractionCategory(arrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface SelectCategoryCallBack {
        void selectInteractionCategory(InteractionCategoryListModel interactionCategory);
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomInteractionCategoryListViewBinding binding;

        public Holder(@NonNull CustomInteractionCategoryListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
