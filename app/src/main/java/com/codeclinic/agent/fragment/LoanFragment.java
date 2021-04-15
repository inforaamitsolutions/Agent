package com.codeclinic.agent.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.MainViewModel;
import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.LoanAccountsListAdapter;
import com.codeclinic.agent.databinding.FragmentLoanBinding;
import com.codeclinic.agent.model.LoanAccountListModel;
import com.codeclinic.agent.model.LoanProductListModel;
import com.codeclinic.agent.model.LoanStatusListModel;
import com.codeclinic.agent.model.MarketListModel;
import com.codeclinic.agent.model.StaffListModel;
import com.codeclinic.agent.model.SupplierListModel;
import com.codeclinic.agent.model.TimeLineStatusListModel;
import com.codeclinic.agent.model.ZoneListModel;
import com.codeclinic.agent.utils.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;


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

        binding.searchChildView.tvCustomerIdFromDate.setOnClickListener(v -> {
            CommonMethods.datePicker(binding.searchChildView.tvCustomerIdFromDate, getActivity());
        });

        binding.searchChildView.tvCustomerIdToDate.setOnClickListener(v -> {
            CommonMethods.datePicker(binding.searchChildView.tvCustomerIdToDate, getActivity());
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
                        if (timeLineStatus.contains(list.get(i).getStateId() + "")) {
                            timeLineStatus.remove(list.get(i).getStateId() + "");
                        } else {
                            timeLineStatus.add(list.get(i).getStateId() + "");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        viewModel.loanAccounts.observe(getActivity(), account -> {
            if (account != null) {
                if (account.getLoanAccountList() != null) {
                    binding.recyclerView.setAdapter(new LoanAccountsListAdapter(account.getLoanAccountList(), getActivity(), binding.recyclerView));
                    binding.searchChildView.llSearchChild.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "" + account.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.loanAccountsByNo.observe(getActivity(), account -> {
            if (account != null) {
                if (account.getLoanAccountsByNoDetail() != null) {
                    if (account.getLoanAccountsByNoDetail().getAccount() != null) {
                        List<LoanAccountListModel> list = new ArrayList<>();
                        list.add(account.getLoanAccountsByNoDetail().getAccount());
                        binding.recyclerView.setAdapter(new LoanAccountsListAdapter(list, getActivity(), binding.recyclerView));
                        binding.searchChildView.llSearchChild.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getActivity(), "No Records Found", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }

    private void manageSpinners() {

        List<String> defaultSearches = new ArrayList<String>();
        defaultSearches.add("--Select--");
        defaultSearches.add("Customer ID");
        defaultSearches.add("Loan Number");
        defaultSearches.add("Others");
        ArrayAdapter<String> defaultAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_view, defaultSearches);
        binding.searchChildView.spDefaultSearch.setAdapter(defaultAdapter);
        binding.searchChildView.spDefaultSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 3) {
                    binding.searchChildView.llAdvancedSearch.setVisibility(View.VISIBLE);
                    binding.searchChildView.llDefaultSearchInputs.setVisibility(View.GONE);
                } else if (i == 0) {
                    binding.searchChildView.llAdvancedSearch.setVisibility(View.GONE);
                    binding.searchChildView.llDefaultSearchInputs.setVisibility(View.GONE);
                } else {
                    binding.searchChildView.tvDefaultSearchTitle.setText(binding.searchChildView.spDefaultSearch.getSelectedItem().toString());
                    binding.searchChildView.llAdvancedSearch.setVisibility(View.GONE);
                    binding.searchChildView.llDefaultSearchInputs.setVisibility(View.VISIBLE);
                    binding.searchChildView.llCustomerIdDates.setVisibility(i == 1 ? View.VISIBLE : View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        List<String> assigned = new ArrayList<>();
        assigned.add("--Select--");
        assigned.add("Staff");
        assigned.add("Zones and Markets");
        ArrayAdapter<String> assignedAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_view, assigned);
        binding.searchChildView.spAssignedTo.setAdapter(assignedAdapter);

        binding.searchChildView.spAssignedTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    binding.searchChildView.llStaff.setVisibility(View.GONE);
                    binding.searchChildView.llZoneAndMarkets.setVisibility(View.GONE);
                } else if (i == 1) {
                    binding.searchChildView.llStaff.setVisibility(View.VISIBLE);
                    binding.searchChildView.llZoneAndMarkets.setVisibility(View.GONE);
                } else {
                    binding.searchChildView.llStaff.setVisibility(View.GONE);
                    binding.searchChildView.llZoneAndMarkets.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.searchChildView.btnSearch.setOnClickListener(v -> {
            if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 0) {
                Toast.makeText(getActivity(), "Please select one filter option", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 1
                    && isEmpty(binding.searchChildView.edtSearch.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter customer id", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 1
                    && isEmpty(binding.searchChildView.tvCustomerIdFromDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter from date", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 1
                    && isEmpty(binding.searchChildView.tvCustomerIdToDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter to date", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 2
                    && isEmpty(binding.searchChildView.edtSearch.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter loan number", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 3
                    && isEmpty(binding.searchChildView.tvReferenceDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter reference Date", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 3
                    && isEmpty(binding.searchChildView.tvFromDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter from Date", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 3
                    && isEmpty(binding.searchChildView.tvToDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter to Date", Toast.LENGTH_SHORT).show();
            } else {
                binding.loadingView.loader.setVisibility(View.VISIBLE);
                if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 3) {
                    JSONObject jsonObject = new JSONObject();
                    try {

                        jsonObject.put("referenceDate", binding.searchChildView.tvReferenceDate.getText().toString());
                        jsonObject.put("startDate", binding.searchChildView.tvFromDate.getText().toString());
                        jsonObject.put("endDate", binding.searchChildView.tvToDate.getText().toString());
                        jsonObject.put("productId", viewModel.productList.getValue().get(binding.searchChildView.spLoanProducts.getSelectedItemPosition()).getProductId());
                        jsonObject.put("partnerId", viewModel.supplierList.getValue().get(binding.searchChildView.spSupplier.getSelectedItemPosition()).getCustomerId());


                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < loanStatus.size(); i++) {
                            jsonArray.put(loanStatus.get(i));
                        }
                        jsonObject.put("loanStates", jsonArray);


                        JSONArray jsonGroupArray = new JSONArray();
                        if (binding.searchChildView.spAssignedTo.getSelectedItemPosition() == 1) {
                            jsonObject.put("staff", viewModel.staffList.getValue().get(binding.searchChildView.spStaff.getSelectedItemPosition()).getId());
                        } else if (binding.searchChildView.spAssignedTo.getSelectedItemPosition() == 2) {
                           /* jsonGroupArray.put(viewModel.zoneList.getValue().get(binding.searchChildView.spZone.getSelectedItemPosition()).getId());
                            jsonGroupArray.put(viewModel.marketList.getValue().get(binding.searchChildView.spMarket.getSelectedItemPosition()).getId());*/
                            for (int i = 0; i < zoneIds.size(); i++) {
                                jsonGroupArray.put(zoneIds.get(i));
                            }
                            for (int i = 0; i < marketIds.size(); i++) {
                                jsonGroupArray.put(marketIds.get(i));
                            }
                            jsonObject.put("groupIds", jsonGroupArray);
                        }


                        JSONArray jsonTimeLineArray = new JSONArray();
                        for (int i = 0; i < timeLineStatus.size(); i++) {
                            jsonTimeLineArray.put(timeLineStatus.get(i));
                        }
                        jsonObject.put("timelineStates", jsonTimeLineArray);


                        Log.i("jsonReq", jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //viewModel.getCustomersAPI(jsonObject);
                } else {
                    if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 1) {
                        String customerID = binding.searchChildView.edtSearch.getText().toString();
                        viewModel.fetchLoanAccountsByCustomerIDAPI(customerID,
                                binding.searchChildView.tvCustomerIdFromDate.getText().toString(),
                                binding.searchChildView.tvCustomerIdToDate.getText().toString());

                    } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 2) {
                        viewModel.fetchLoanAccountsByLoanNoAPI(binding.searchChildView.edtSearch.getText().toString());
                    }

                }

            }
        });

        binding.searchChildView.btnReset.setOnClickListener(v -> {
            binding.searchChildView.edtSearch.getText().clear();
            binding.searchChildView.tvCustomerIdFromDate.setText("");
            binding.searchChildView.tvCustomerIdToDate.setText("");
            binding.searchChildView.tvReferenceDate.setText("");
            binding.searchChildView.tvFromDate.setText("");
            binding.searchChildView.tvToDate.setText("");

            ArrayAdapter<StaffListModel> staffAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, viewModel.staffList.getValue());
            binding.searchChildView.spStaff.setAdapter(staffAdapter);

            ArrayAdapter<ZoneListModel> zoneAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, viewModel.zoneList.getValue());
            binding.searchChildView.spZone.setAdapter(zoneAdapter);
            binding.searchChildView.spZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("parentId", "" + viewModel.zoneList.getValue().get(i).getId());
                    viewModel.getMarketsAPI(viewModel.zoneList.getValue().get(i).getParentId() + "");
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            ArrayAdapter<LoanProductListModel> productAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, viewModel.productList.getValue());
            binding.searchChildView.spLoanProducts.setAdapter(productAdapter);
            binding.searchChildView.spLoanProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("productID", "" + viewModel.productList.getValue().get(i).getProductId());

                    viewModel.getTimeLoanStatusAPI(viewModel.productList.getValue().get(i).getProductId() + "");
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter<SupplierListModel> supplierAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, viewModel.supplierList.getValue());
            binding.searchChildView.spSupplier.setAdapter(supplierAdapter);

            ArrayAdapter<LoanStatusListModel> loanStatusAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, viewModel.loanStatusList.getValue());
            binding.searchChildView.spLoanStatus.setAdapter(loanStatusAdapter);
            binding.searchChildView.spLoanStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("loanStatus", "" + viewModel.loanStatusList.getValue().get(i).getName());
                    if (loanStatus.contains(viewModel.loanStatusList.getValue().get(i).getName())) {
                        loanStatus.remove(viewModel.loanStatusList.getValue().get(i).getName());
                    } else {
                        loanStatus.add(viewModel.loanStatusList.getValue().get(i).getName());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        });
    }
}