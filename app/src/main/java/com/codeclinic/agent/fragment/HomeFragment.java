package com.codeclinic.agent.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeclinic.agent.R;
import com.codeclinic.agent.activity.CreateCustomerActivity;
import com.codeclinic.agent.activity.CustomerInfoActivity;
import com.codeclinic.agent.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        Drawable draw=getContext().getDrawable(R.drawable.custom_progressbar);

        binding.progressBar.setProgressDrawable(draw);
        binding.progressBar.setProgress(2*10);
        binding.progressBar2.setProgress(5*10);

        binding.card1.setOnClickListener(view -> {
            if (binding.leadLayout.linearLead.getVisibility()== View.VISIBLE){
                binding.leadLayout.linearLead.setVisibility(View.GONE);

            }
            else{
                //binding.imgOption.setImageResource(R.drawable.cancel);
                binding.leadLayout.linearLead.setVisibility(View.VISIBLE);
            }

        });
        binding.card2.setOnClickListener(view -> {
            if (binding.loanLayout.linearLoan.getVisibility()== View.VISIBLE){
                binding.loanLayout.linearLoan.setVisibility(View.GONE);
                binding.imgOption.setImageResource(R.drawable.ic_option);
            }
            else{
                binding.loanLayout.linearLoan.setVisibility(View.VISIBLE);
                binding.imgOption.setImageResource(R.drawable.cancel);
            }

        });

        binding.leadLayout.linearCreateCustomer.setOnClickListener(view ->
        {
             startActivity(new Intent(getContext(), CreateCustomerActivity.class));
        });
  binding.card3.setOnClickListener(view -> {
            if (binding.defaultingLinear.linearDefaulting.getVisibility()== View.VISIBLE){
                binding.defaultingLinear.linearDefaulting.setVisibility(View.GONE);
         binding.imgCancel.setVisibility(View.GONE);
         binding.txtCount1.setVisibility(View.VISIBLE);
            }
            else{
                binding.imgCancel.setVisibility(View.VISIBLE);
                binding.txtCount1.setVisibility(View.GONE);
                binding.defaultingLinear.linearDefaulting.setVisibility(View.VISIBLE);
            }

        });
binding.card4.setOnClickListener(view -> {
            if (binding.defaultersLinear.linearDefaulters.getVisibility()== View.VISIBLE){
                binding.defaultersLinear.linearDefaulters.setVisibility(View.GONE);
                binding.imgCancel2.setVisibility(View.GONE);
                binding.txtCount2.setVisibility(View.VISIBLE);
            }
            else{
                binding.imgCancel2.setVisibility(View.VISIBLE);
                binding.txtCount2.setVisibility(View.GONE);
                binding.defaultersLinear.linearDefaulters.setVisibility(View.VISIBLE);
            }

        });

binding.defaultersLinear.linearCustomer.setOnClickListener(view -> {
    startActivity(new Intent(getContext(), CustomerInfoActivity.class));
});
        return binding.getRoot();
    }
  
}