package com.codeclinic.agent.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.activity.CustomerInfoActivity;
import com.codeclinic.agent.databinding.CustomLoanListViewBinding;

public class LoanListAdapter extends RecyclerView.Adapter<LoanListAdapter.Holder> {
    Context context;
    CustomLoanListViewBinding binding;
    public LoanListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LoanListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_loan_list_view,parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanListAdapter.Holder holder, int position) {
          if (position%2==0){
              binding.card.setCardBackgroundColor(Color.parseColor("#ffffff"));
              binding.txtName.setTextColor(context.getResources().getColor(R.color.black));
              binding.txtDescription.setTextColor(context.getResources().getColor(R.color.black));
              binding.txtLocation.setTextColor(context.getResources().getColor(R.color.black));
              binding.txtDueDate.setTextColor(context.getResources().getColor(R.color.black));
              binding.txtAmount.setTextColor(context.getResources().getColor(R.color.white));
              binding.txtAmount.setBackground(context.getResources().getDrawable(R.drawable.bg_orange_circle));
              binding.imgLocation.setImageResource(R.drawable.ic_location_black);
              binding.imgArrow.setImageResource(R.drawable.arrow_right);
          }
          else{


              binding.card.setCardBackgroundColor(Color.parseColor("#454545"));
              binding.txtName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
              binding.txtDescription.setTextColor(context.getResources().getColor(R.color.white));
              binding.txtLocation.setTextColor(context.getResources().getColor(R.color.white));
              binding.txtDueDate.setTextColor(context.getResources().getColor(R.color.colorPrimary));
              binding.txtAmount.setTextColor(context.getResources().getColor(R.color.black));
              binding.txtAmount.setBackground(context.getResources().getDrawable(R.drawable.bg_white_circle));
              binding.imgLocation.setImageResource(R.drawable.ic_location_white);
              binding.imgArrow.setImageResource(R.drawable.arrow_right_orange);
          }
          binding.linear1.setOnClickListener(view -> {
              context.startActivity(new Intent(context, CustomerInfoActivity.class));
          });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomLoanListViewBinding binding;
        public Holder(CustomLoanListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
