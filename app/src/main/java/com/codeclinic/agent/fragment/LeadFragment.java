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
import com.codeclinic.agent.activity.CreateLeadActivity;
import com.codeclinic.agent.adapter.LeadListAdapter;
import com.codeclinic.agent.databinding.FragmentLeadBinding;
import com.codeclinic.agent.model.MarketListModel;
import com.codeclinic.agent.model.StaffListModel;
import com.codeclinic.agent.model.ZoneListModel;
import com.codeclinic.agent.utils.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class LeadFragment extends Fragment {

    FragmentLeadBinding binding;
    private MainViewModel viewModel;
    private final List<String> zoneIds = new ArrayList<>();
    private final List<String> marketIds = new ArrayList<>();

    List<ZoneListModel> zoneList = new ArrayList<>();
    List<MarketListModel> marketList = new ArrayList<>();


    public LeadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lead, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        binding.headerLayout.imgBack.setVisibility(View.GONE);
        binding.headerLayout.txtHeading.setText("Leads");

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        binding.searchParentView.llSearchParent.setOnClickListener(view -> {

            if (binding.searchChildView.llSearchChild.getVisibility() == View.VISIBLE) {
                binding.searchChildView.llSearchChild.setVisibility(View.GONE);
            } else {
                binding.searchChildView.llSearchChild.setVisibility(View.VISIBLE);
            }
        });


        binding.btnAddLead.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), CreateLeadActivity.class));
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
                zoneList.clear();
                zoneList.addAll(list);
                zoneList.add(0, new ZoneListModel("--Select--"));
                ArrayAdapter<ZoneListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, zoneList);
                binding.searchChildView.spZone.setAdapter(adapter);
                binding.searchChildView.spZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            Log.i("parentId", "" + list.get(i).getId());
                            if (zoneIds.contains(list.get(i).getId() + "")) {
                                zoneIds.remove(list.get(i).getId() + "");
                            } else {
                                zoneIds.add(list.get(i).getId() + "");
                            }
                            viewModel.getMarketsAPI(list.get(i).getId() + "");
                        } else {
                            zoneIds.clear();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        viewModel.marketList.observe(getActivity(), list -> {
            if (list != null) {
                marketList.clear();
                marketList.addAll(list);
                marketList.add(0, new MarketListModel("--Select--"));
                ArrayAdapter<MarketListModel> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, marketList);
                binding.searchChildView.spMarket.setAdapter(adapter);
                binding.searchChildView.spMarket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            Log.i("marketId", "" + list.get(i).getId());
                            if (marketIds.contains(list.get(i).getId() + "")) {
                                marketIds.remove(list.get(i).getId() + "");
                            } else {
                                marketIds.add(list.get(i).getId() + "");
                            }
                        } else {
                            marketIds.clear();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        viewModel.lead.observe(getActivity(), lead -> {
            binding.loadingView.loader.setVisibility(View.GONE);
            if (lead != null) {
                if (lead.getLeadList() != null) {
                    binding.recyclerView.setAdapter(new LeadListAdapter(lead.getLeadList(), getActivity()));
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
        defaultSearches.add("Customer Name");
        defaultSearches.add("Customer ID");
        defaultSearches.add("Phone Number");
        defaultSearches.add("Others");
        ArrayAdapter<String> defaultAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_view, defaultSearches);
        binding.searchChildView.spDefaultSearch.setAdapter(defaultAdapter);
        binding.searchChildView.spDefaultSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 4) {
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


        List<String> channels = new ArrayList<>();
        channels.add("--Select--");
        channels.add("USSD");
        channels.add("App");
        ArrayAdapter<String> channelAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_view, channels);
        binding.searchChildView.spChannel.setAdapter(channelAdapter);


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
                Toast.makeText(getActivity(), "Please enter customer name", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 2
                    && isEmpty(binding.searchChildView.edtSearch.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter customer id", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 3
                    && isEmpty(binding.searchChildView.edtSearch.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter phone number", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 4
                    && isEmpty(binding.searchChildView.tvFromDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter from Date", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 4
                    && isEmpty(binding.searchChildView.tvToDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please enter to Date", Toast.LENGTH_SHORT).show();
            } else {
                binding.loadingView.loader.setVisibility(View.VISIBLE);
                if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 4) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("staffId", viewModel.staffList.getValue().get(binding.searchChildView.spStaff.getSelectedItemPosition()).getId());
                        jsonObject.put("fromDate", binding.searchChildView.tvFromDate.getText().toString());
                        jsonObject.put("toDate", binding.searchChildView.tvToDate.getText().toString());
                        if (binding.searchChildView.spChannel.getSelectedItemPosition() != 0) {
                            jsonObject.put("channel", binding.searchChildView.spChannel.getSelectedItem().toString());
                        }
                        JSONArray jsonArray = new JSONArray();
                        if (binding.searchChildView.chkNewLead.isChecked()) {
                            jsonArray.put(binding.searchChildView.chkNewLead.getText().toString());
                        }
                        if (binding.searchChildView.chkColdLead.isChecked()) {
                            jsonArray.put(binding.searchChildView.chkColdLead.getText().toString());
                        }
                        if (binding.searchChildView.chkProspectiveLead.isChecked()) {
                            jsonArray.put(binding.searchChildView.chkProspectiveLead.getText().toString());
                        }
                        jsonObject.put("customerStatuses", jsonArray);

                        JSONArray jsonGroupArray = new JSONArray();
                        if (binding.searchChildView.spAssignedTo.getSelectedItemPosition() == 1) {
                            jsonGroupArray.put(viewModel.staffList.getValue().get(binding.searchChildView.spStaff.getSelectedItemPosition()).getId());
                            jsonObject.put("groupIds", jsonGroupArray);
                        } else if (binding.searchChildView.spAssignedTo.getSelectedItemPosition() == 2) {
                            for (int i = 0; i < zoneIds.size(); i++) {
                                jsonGroupArray.put(zoneIds.get(i));
                            }
                            for (int i = 0; i < marketIds.size(); i++) {
                                jsonGroupArray.put(marketIds.get(i));
                            }
                            if (!marketIds.isEmpty() || !zoneIds.isEmpty()) {
                                jsonObject.put("groupIds", jsonGroupArray);
                            }
                        } /*else {
                            jsonGroupArray.put(sessionManager.getUserDetails().get(SessionManager.UserID));
                            jsonObject.put("groupIds", jsonGroupArray);
                        }*/


                        Log.i("jsonReq", jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    viewModel.getLeadsAPI(jsonObject);
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 1) {
                            jsonObject.put("name", binding.searchChildView.edtSearch.getText().toString());
                        } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 2) {
                            jsonObject.put("customerId", binding.searchChildView.edtSearch.getText().toString());
                        } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 3) {
                            jsonObject.put("custPhone", binding.searchChildView.edtSearch.getText().toString());
                        }

                        Log.i("jsonReq", jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    viewModel.getLeadsAPI(jsonObject);
                }

            }
        });

        binding.searchChildView.btnReset.setOnClickListener(v -> {
            binding.searchChildView.edtSearch.getText().clear();
            binding.searchChildView.tvFromDate.setText("");
            binding.searchChildView.tvToDate.setText("");

  /*          ArrayAdapter<StaffListModel> staffAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, viewModel.staffList.getValue());
            binding.searchChildView.spStaff.setAdapter(staffAdapter);

            ArrayAdapter<ZoneListModel> zoneAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, viewModel.zoneList.getValue());
            binding.searchChildView.spZone.setAdapter(zoneAdapter);
            binding.searchChildView.spZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("parentId", "" + viewModel.zoneList.getValue().get(i).getId());
                    viewModel.getMarketsAPI(viewModel.zoneList.getValue().get(i).getId() + "");
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });*/

        });
    }
}