package com.codeclinic.agent.adapter;

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

import net.cachapa.expandablelayout.ExpandableLayout;

public class PerformanceAttributesListAdapter extends RecyclerView.Adapter<PerformanceAttributesListAdapter.Holder> {
    private final Context context;
    private final RecyclerView recyclerView;
    private final int UNSELECTED = -1;
    private CustomPerformaceAttributesListViewBinding binding;
    private int selectedItem = UNSELECTED;

    public PerformanceAttributesListAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public PerformanceAttributesListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_performace_attributes_list_view, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PerformanceAttributesListAdapter.Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class Holder extends RecyclerView.ViewHolder implements ExpandableLayout.OnExpansionUpdateListener {
        CustomPerformaceAttributesListViewBinding binding;

        public Holder(@NonNull CustomPerformaceAttributesListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

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
