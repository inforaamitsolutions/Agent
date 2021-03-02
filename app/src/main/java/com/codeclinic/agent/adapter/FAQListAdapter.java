package com.codeclinic.agent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.CustomFaqListViewBinding;

import java.util.ArrayList;
import java.util.List;

public class FAQListAdapter extends RecyclerView.Adapter<FAQListAdapter.Holder> {
    Context context;
     CustomFaqListViewBinding binding;
    List<String> heading = new ArrayList<>();
    List<String> description = new ArrayList<>();
    public FAQListAdapter(Context context, List<String> heading, List<String> description) {
        this.context = context;
        this.heading = heading;
        this.description = description;
    }

    @NonNull
    @Override
    public FAQListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_faq_list_view,parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQListAdapter.Holder holder, int position) {
       holder.binding.txtHeading.setOnClickListener(view -> {
           /*if (binding.imgAdd.getResources().getDrawable(R.drawable.ic_add_circle)==context.getResources().getDrawable(R.drawable.ic_add_circle)){
               binding.imgAdd.setImageResource(R.drawable.ic_minus_circle);
               //binding.txtDescription.setVisibility(View.VISIBLE);
           }
           else{
               binding.imgAdd.setImageResource(R.drawable.ic_add_circle);
               //binding.txtDescription.setVisibility(View.GONE);
           }*/
           // if (holder.getAdapterPosition()==position){

                if (holder.binding.txtDescription.getVisibility()== View.VISIBLE){
                    holder.binding.imgAdd.setImageResource(R.drawable.ic_add_circle);
                    holder.binding.txtDescription.setVisibility(View.GONE);

                }
                else {
                    holder.binding.txtDescription.setVisibility(View.VISIBLE);
                    holder.binding.imgAdd.setImageResource(R.drawable.ic_minus_circle);

                }
        //    }

       });
    }

    @Override
    public int getItemCount() {
        return heading.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomFaqListViewBinding binding;

        public Holder(CustomFaqListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
