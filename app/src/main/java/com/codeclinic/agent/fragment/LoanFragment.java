package com.codeclinic.agent.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.MainViewModel;
import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.FragmentLoanBinding;
import com.codeclinic.agent.model.LoanProductListModel;
import com.codeclinic.agent.model.LoanStatusListModel;
import com.codeclinic.agent.model.MarketListModel;
import com.codeclinic.agent.model.StaffListModel;
import com.codeclinic.agent.model.SupplierListModel;
import com.codeclinic.agent.model.TimeLineStatusListModel;
import com.codeclinic.agent.model.ZoneListModel;
import com.codeclinic.agent.utils.CommonMethods;

import java.util.ArrayList;
import java.util.List;


public class LoanFragment extends Fragment {

    public FragmentLoanBinding binding;
    private MainViewModel viewModel;

    private final List<String> zoneIds = new ArrayList<>();
    private final List<String> marketIds = new ArrayList<>();
    private final List<String> loanStatus = new ArrayList<>();
    private final List<String> timeLineStatus = new ArrayList<>();

    public LoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loan, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        binding.headerLayout.txtHeading.setText("Loans");


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);
        //binding.recyclerView.setAdapter(new LoanListAdapter(getContext()));

        binding.searchParentView.llSearchParent.setOnClickListener(view -> {
            if (binding.searchChildView.llSearchChild.getVisibility() == View.VISIBLE) {
                binding.searchChildView.llSearchChild.setVisibility(View.GONE);
            } else {
                binding.searchChildView.llSearchChild.setVisibility(View.VISIBLE);
            }
        });

        binding.searchChildView.tvReferenceDate.setOnClickListener(v -> {
            CommonMethods.datePicker(binding.searchChildView.tvReferenceDate, getActivity());
        });

        binding.searchChildView.tvFromDate.setOnClickListener(v -> {
            CommonMethods.datePicker(binding.searchChildView.tvFromDate, getActivity());
        });

        binding.searchChildView.tvToDate.setOnClickListener(v -> {
            CommonMethods.datePicker(binding.searchChildView.tvToDate, getActivity());
        });


        viewModel.staffList.observe(getActivity(), list -> {
            if (list != null) {
                ArrayAdapter<StaffListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, list);
                binding.searchChildView.spStaff.setAdapter(adapter);
            }
        });

        viewModel.zoneList.observe(getActivity(), list -> {
            if (list != null) {
                ArrayAdapter<ZoneListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, list);
                binding.searchChildView.spZone.setAdapter(adapter);
                binding.searchChildView.spZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i("parentId", "" + list.get(i).getId());
                        if (zoneIds.contains(list.get(i).getId() + "")) {
                            zoneIds.remove(list.get(i).getId() + "");
                        } else {
                            zoneIds.add(list.get(i).getId() + "");
                        }
                        viewModel.getMarketsAPI(list.get(i).getParentId() + "");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        viewModel.marketList.observe(getActivity(), list -> {
            if (list != null) {
                ArrayAdapter<MarketListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, list);
                binding.searchChildView.spMarket.setAdapter(adapter);
                binding.searchChildView.spMarket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i("marketId", "" + list.get(i).getId());
                        if (marketIds.contains(list.get(i).getId() + "")) {
                            marketIds.remove(list.get(i).getId() + "");
                        } else {
                            marketIds.add(list.get(i).getId() + "");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        viewModel.productList.observe(getActivity(), list -> {
            if (list != null) {
                ArrayAdapter<LoanProductListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, list);
                binding.searchChildView.spLoanProducts.setAdapter(adapter);
                binding.searchChildView.spLoanProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i("productID", "" + list.get(i).getProductId());

                        viewModel.getTimeLoanStatusAPI(list.get(i).getProductId() + "");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        viewModel.supplierList.observe(getActivity(), list -> {
            if (list != null) {
                ArrayAdapter<SupplierListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, list);
                binding.searchChildView.spSupplier.setAdapter(adapter);
            }
        });

        viewModel.loanStatusList.observe(getActivity(), list -> {
            if (list != null) {
                ArrayAdapter<LoanStatusListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, list);
                binding.searchChildView.spLoanStatus.setAdapter(adapter);
                binding.searchChildView.spLoanStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i("loanStatus", "" + list.get(i).getName());
                        if (loanStatus.contains(list.get(i).getName())) {
                            loanStatus.remove(list.get(i).getName());
                        } else {
                            loanStatus.add(list.get(i).getName());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        viewModel.timeLineStatusList.observe(getActivity(), list -> {
            if (list != null) {
                ArrayAdapter<TimeLineStatusListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, list);
                binding.searchChildView.spTimeLineStatus.setAdapter(adapter);
                binding.searchChildView.spTimeLineStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i("timeLineStates", "" + list.get(i).getTimelineState());
                        if (timeLineStatus.contains(list.get(i).getTimelineState())) {
                            timeLineStatus.remove(list.get(i).getTimelineState());
                        } else {
                            timeLineStatus.add(list.get(i).getTimelineState());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });


        return binding.getRoot();
    }
}