package com.codeclinic.agent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomLoanAccountListViewBinding;
import com.codeclinic.agent.model.LoanAccountListModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class LoanAccountsListAdapter extends RecyclerView.Adapter<LoanAccountsListAdapter.Holder> {
    private final List<LoanAccountListModel> arrayList;
    private final Context context;
    private final RecyclerView recyclerView;
    private final int UNSELECTED = -1;
    private CustomLoanAccountListViewBinding binding;
    private int selectedItem = UNSELECTED;


    public LoanAccountsListAdapter(List<LoanAccountListModel> arrayList, Context context, RecyclerView recyclerView) {
        this.arrayList = arrayList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public LoanAccountsListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_loan_account_list_view, parent, false);
        return new Holder(binding, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanAccountsListAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements ExpandableLayout.OnExpansionUpdateListener {
        CustomLoanAccountListViewBinding binding;

        @SuppressLint("SetTextI18n")
        public Holder(@NonNull CustomLoanAccountListViewBinding binding, int pos) {
            super(binding.getRoot());
            this.binding = binding;

            binding.tvName.setText(arrayList.get(pos).getCustomerName() + "");
            binding.tvAmount.setText(arrayList.get(pos).getLoanAmount() + "");
            binding.tvBalance.setText(arrayList.get(pos).getRunningBalance() + "");
            binding.tvCharges.setText(arrayList.get(pos).getLoanCharges() + "");
            binding.tvDueDate.setText(arrayList.get(pos).getDueDate() + "");
            binding.tvInterest.setText(arrayList.get(pos).getLoanInterest() + "");
            binding.tvLoanNumber.setText(arrayList.get(pos).getLoanNumber() + "");
            binding.tvMainAmount.setText(arrayList.get(pos).getLoanAmount() + "");
            binding.tvMainStatus.setText(arrayList.get(pos).getStatus() + "");

            binding.expandedDetail.setInterpolator(new OvershootInterpolator());
            binding.expandedDetail.setOnExpansionUpdateListener(this);
            binding.llLoanDetails.setOnClickListener((View.OnClickListener) view -> {
                RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
                if (holder != null) {
                    binding.llLoanDetails.setSelected(false);
                    binding.expandedDetail.collapse();
                    binding.llLoanDetails.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimary)));
                    binding.tvView.setText("View");
                    binding.imgView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_add_circle));
                }

                int position = getAdapterPosition();
                if (position == selectedItem) {
                    selectedItem = UNSELECTED;
                } else {
                    binding.llLoanDetails.setSelected(true);
                    binding.expandedDetail.expand();
                    selectedItem = position;
                    binding.llLoanDetails.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black)));
                    binding.tvView.setText("Close");
                    binding.imgView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cancel));
                }
            });
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
