package com.codeclinic.agent.fragment;

import android.content.Intent;
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
import com.codeclinic.agent.activity.CreateCustomerActivity;
import com.codeclinic.agent.adapter.CustomerListAdapter;
import com.codeclinic.agent.databinding.FragmentCustomerBinding;
import com.codeclinic.agent.model.MarketListModel;
import com.codeclinic.agent.model.ProductSegmentListModel;
import com.codeclinic.agent.model.StaffListModel;
import com.codeclinic.agent.model.StatusListModel;
import com.codeclinic.agent.model.ZoneListModel;
import com.codeclinic.agent.utils.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;


public class CustomerFragment extends Fragment {
    FragmentCustomerBinding binding;

    List<String> statuses = new ArrayList<>();
    private MainViewModel viewModel;

    private final List<String> zoneIds = new ArrayList<>();
    private final List<String> marketIds = new ArrayList<>();

    public CustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        binding.headerLayout.txtHeading.setText("Customers");
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        binding.btnAddCustomer.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), CreateCustomerActivity.class));
        });


        binding.searchParentView.llSearchParent.setOnClickListener(view -> {

            if (binding.searchChildView.llSearchChild.getVisibility() == View.VISIBLE) {
                binding.searchChildView.llSearchChild.setVisibility(View.GONE);
            } else {
                binding.searchChildView.llSearchChild.setVisibility(View.VISIBLE);
            }
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

        viewModel.getStatusAPI();
        viewModel.statusesList.observe(getActivity(), list -> {
            if (list != null) {
                ArrayAdapter<StatusListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, list);
                binding.searchChildView.spStatus.setAdapter(adapter);
                binding.searchChildView.spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (statuses.contains(list.get(i).getName())) {
                            statuses.remove(list.get(i).getName());
                        } else {
                            statuses.add(list.get(i).getName());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        viewModel.getSegmentsAPI();
        viewModel.productSegmentList.observe(getActivity(), list -> {
            if (list != null) {
                ArrayAdapter<ProductSegmentListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, list);
                binding.searchChildView.spSegment.setAdapter(adapter);
            }
        });


        viewModel.customer.observe(getActivity(), customer -> {
            binding.loadingView.loader.setVisibility(View.GONE);
            if (customer != null) {
                if (customer.getCustomerList() != null) {
                    binding.recyclerView.setAdapter(new CustomerListAdapter(customer.getCustomerList(), getActivity()));
                    if (binding.searchChildView.llSearchChild.getVisibility() == View.VISIBLE) {
                        binding.searchChildView.llSearchChild.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Error No Records", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Error No Records", Toast.LENGTH_SHORT).show();
            }
        });

        manageSpinners();

        return binding.getRoot();
    }

    private void manageSpinners() {

        List<String> defaultSearches = new ArrayList<String>();
        defaultSearches.add("--Select--");
        defaultSearches.add("Customer ID");
        defaultSearches.add("Business Name");
        defaultSearches.add("Customer Document Number");
        defaultSearches.add("Customer Phone Number");
        defaultSearches.add("Others");
        ArrayAdapter<String> defaultAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_view, defaultSearches);
        binding.searchChildView.spDefaultSearch.setAdapter(defaultAdapter);
        binding.searchChildView.spDefaultSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 5) {
                    binding.searchChildView.llAdvancedSearch.setVisibility(View.VISIBLE);
                    binding.searchChildView.llDefaultSearchInputs.setVisibility(View.GONE);
                } else if (i == 0) {
                    binding.searchChildView.llAdvancedSearch.setVisibility(View.GONE);
                    binding.searchChildView.llDefaultSearchInputs.setVisibility(View.GONE);
                } else {
                    binding.searchChildView.tvDefaultSearchTitle.setText(binding.searchChildView.spDefaultSearch.getSelectedItem().toString());
                    binding.searchChildView.llAdvancedSearch.setVisibility(View.GONE);
                    binding.searchChildView.llDefaultSearchInputs.setVisibility(View.VISIBLE);
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
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 2
                    && isEmpty(binding.searchChildView.edtSearch.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter customer business name", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 3
                    && isEmpty(binding.searchChildView.edtSearch.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter document number", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 4
                    && isEmpty(binding.searchChildView.edtSearch.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter phone number", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 5
                    && isEmpty(binding.searchChildView.tvFromDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter from Date", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 5
                    && isEmpty(binding.searchChildView.tvToDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter to Date", Toast.LENGTH_SHORT).show();
            } else {
                binding.loadingView.loader.setVisibility(View.VISIBLE);
                if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 5) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("staffId", viewModel.staffList.getValue().get(binding.searchChildView.spStaff.getSelectedItemPosition()).getId());
                        jsonObject.put("fromDate", binding.searchChildView.tvFromDate.getText().toString());
                        jsonObject.put("toDate", binding.searchChildView.tvToDate.getText().toString());
                        jsonObject.put("segment", binding.searchChildView.spSegment.getSelectedItem().toString());
                        jsonObject.put("customerRole", "MYMOBI_BUSINESS_CUSTOMER");
                        jsonObject.put("customerType", "BUSINESS");

                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < statuses.size(); i++) {
                            jsonArray.put(statuses.get(i));
                        }
                        jsonObject.put("customerStatuses", jsonArray);

                        JSONArray jsonGroupArray = new JSONArray();
                        if (binding.searchChildView.spAssignedTo.getSelectedItemPosition() == 1) {
                            jsonGroupArray.put(viewModel.staffList.getValue().get(binding.searchChildView.spStaff.getSelectedItemPosition()).getId());
                            jsonObject.put("groupIds", jsonGroupArray);
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


                        Log.i("jsonReq", jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    viewModel.getCustomersAPI(jsonObject);
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 1) {
                            jsonObject.put("customerId", binding.searchChildView.edtSearch.getText().toString());
                        } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 2) {
                            jsonObject.put("name", binding.searchChildView.edtSearch.getText().toString());
                        } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 3) {
                            jsonObject.put("documentNumber", binding.searchChildView.edtSearch.getText().toString());
                        } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 4) {
                            jsonObject.put("custPhone", binding.searchChildView.edtSearch.getText().toString());
                        }

                        Log.i("jsonReq", jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    viewModel.getCustomersAPI(jsonObject);
                }

            }
        });

        binding.searchChildView.btnReset.setOnClickListener(v -> {
            binding.searchChildView.edtSearch.getText().clear();

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


            ArrayAdapter<StatusListModel> statusAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, viewModel.statusesList.getValue());
            binding.searchChildView.spStatus.setAdapter(statusAdapter);
            binding.searchChildView.spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (statuses.contains(viewModel.statusesList.getValue().get(i).getName())) {
                        statuses.remove(viewModel.statusesList.getValue().get(i).getName());
                    } else {
                        statuses.add(viewModel.statusesList.getValue().get(i).getName());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            ArrayAdapter<ProductSegmentListModel> productSegmentAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, viewModel.productSegmentList.getValue());
            binding.searchChildView.spSegment.setAdapter(productSegmentAdapter);

        });
    }
}