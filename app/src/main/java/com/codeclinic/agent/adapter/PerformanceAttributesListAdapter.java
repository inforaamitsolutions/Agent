package com.codeclinic.agent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomPerformaceAttributesListViewBinding;
import com.codeclinic.agent.model.PerformanceAttributesListModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class PerformanceAttributesListAdapter extends RecyclerView.Adapter<PerformanceAttributesListAdapter.Holder> {
    private final List<PerformanceAttributesListModel> arrayList;
    private final Context context;
    private final RecyclerView recyclerView;
    private final int UNSELECTED = -1;
    private CustomPerformaceAttributesListViewBinding binding;
    private int selectedItem = UNSELECTED;

    public PerformanceAttributesListAdapter(List<PerformanceAttributesListModel> arrayList, Context context, RecyclerView recyclerView) {
        this.arrayList = arrayList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public PerformanceAttributesListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_performace_attributes_list_view, parent, false);
        return new Holder(binding, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull PerformanceAttributesListAdapter.Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class Holder extends RecyclerView.ViewHolder implements ExpandableLayout.OnExpansionUpdateListener {
        CustomPerformaceAttributesListViewBinding binding;

        @SuppressLint("SetTextI18n")
        public Holder(@NonNull CustomPerformaceAttributesListViewBinding binding, int pos) {
            super(binding.getRoot());
            this.binding = binding;

            binding.tvAttributeName.setText(arrayList.get(pos).getAttibuteLabel());

            binding.tvTarget.setText(arrayList.get(pos).getTarget() + "");
            binding.tvActualPerformance.setText(arrayList.get(pos).getActual() + "");
            if (arrayList.get(pos).getPerformance() != null) {
                binding.progressBar.setProgress(arrayList.get(pos).getPerformance());
                binding.tvProgressPercentage.setText(arrayList.get(pos).getPerformance() + "%");
            } else {
                binding.progressBar.setProgress(0);
                binding.tvProgressPercentage.setText("0%");
            }

            binding.expandedDetail.setInterpolator(new OvershootInterpolator());
            binding.expandedDetail.setOnExpansionUpdateListener(this);


            binding.imgView.setOnClickListener(view -> {
                Holder holder = (Holder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
                if (holder != null) {
                    holder.binding.imgView.setSelected(false);
                    holder.binding.expandedDetail.collapse();
                    holder.binding.imgView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_add_circle));
                }

                int position = getAdapterPosition();
                if (position == selectedItem) {
                    selectedItem = UNSELECTED;
                } else {
                    binding.imgView.setSelected(true);
                    binding.expandedDetail.expand();
                    selectedItem = position;
                    binding.imgView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cancel));
                }
            });
        }

        private void bind() {
            int position = getAdapterPosition();
            boolean isSelected = position == selectedItem;
            binding.expandedDetail.setOnExpansionUpdateListener(this);
            binding.llHeader.setSelected(isSelected);
            binding.expandedDetail.setExpanded(isSelected, false);
        }


        @Override
        public void onExpansionUpdate(float expansionFraction, int state) {
            Log.d("ExpandableLayout", "State: " + state);
            if (state == ExpandableLayout.State.EXPANDING) {
                recyclerView.smoothScrollToPosition(getAdapterPosition());
            }
        }
    }
}
