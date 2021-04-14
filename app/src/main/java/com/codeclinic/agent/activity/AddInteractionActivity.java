package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.InteractionCategoryListAdapter;
import com.codeclinic.agent.databinding.ActivityAddInteractionBinding;
import com.codeclinic.agent.model.InteractionCategoryFieldListModel;
import com.codeclinic.agent.model.InteractionCategoryListModel;
import com.codeclinic.agent.model.InteractionCategoryModel;
import com.codeclinic.agent.model.SubmitInteractionRecordModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;
import static com.codeclinic.agent.utils.CommonMethods.datePicker;
import static com.codeclinic.agent.utils.Constants.CustomerID;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class AddInteractionActivity extends AppCompatActivity implements InteractionCategoryListAdapter.SelectCategoryCallBack {
    ActivityAddInteractionBinding binding;
    String[] type = {"CALL", "VISIT", "SMS"};
    String[] channel = {"USD"};

    int fieldQuestionPosition = 0, radioButtonTextSize;

    CompositeDisposable disposable = new CompositeDisposable();
    InteractionCategoryListModel selectedInteractionCategory;
    List<InteractionCategoryFieldListModel> fieldList = new ArrayList<>();
    ArrayAdapter spAdapter;
    Map<Integer, String> answeredQuestions = new HashMap<>();

    String customerID;
    LinearLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_interaction);
        setSupportActionBar(binding.headerLayout.toolbar);
        binding.headerLayout.txtHeading.setText("Choose Interaction Category");

        customerID = getIntent().getStringExtra(CustomerID);

        binding.recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.imgBack.setOnClickListener(v -> {
            finish();
        });

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type);
        binding.typeSpinner.setAdapter(ad);

        binding.tvDate.setOnClickListener(v -> {
            if (fieldList.get(fieldQuestionPosition).getFieldType().equals("date")) {
                datePicker(binding.tvDate, this);
            } else {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddInteractionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //eReminderTime.setText( selectedHour + ":" + selectedMinute);
                        binding.tvDate.setText(selectedHour + " : " + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }

        });

        final float scale = getResources().getDisplayMetrics().density;
        radioButtonTextSize = (int) (14 * scale + 0.5f);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);

        binding.btnNext.setOnClickListener(view -> {
            if (validateAnswer()) {
                if (fieldList.size() > (fieldQuestionPosition + 1)) {
                    addAnswers();
                    fieldQuestionPosition++;
                    updatePage();
                } else {
                    addAnswers();
                    submitInteraction();
                }
            }
        });

        getInteractionCategoriesAPI();

    }

    private void getInteractionCategoriesAPI() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        disposable.add(RestClass.getClient().INTERACTION_CATEGORY_MODEL_SINGLE(sessionManager.getTokenDetails().get(SessionManager.AccessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<InteractionCategoryModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull InteractionCategoryModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getInteractionCategoryList() != null) {
                            binding.recyclerViewCategories.setAdapter(
                                    new InteractionCategoryListAdapter(response.getInteractionCategoryList(),
                                            AddInteractionActivity.this
                                            , AddInteractionActivity.this));
                        } else {
                            Toast.makeText(AddInteractionActivity.this, " " + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                    }
                }));
    }

    @SuppressLint("SetTextI18n")
    private void updatePage() {

        binding.tvQuestion.setText(fieldList.get(fieldQuestionPosition).getLabel());

        binding.tvQuestionToFollow.setVisibility(View.GONE);
        binding.rlQueToFollowSpinner.setVisibility(View.GONE);
        binding.rlSpinner.setVisibility(View.GONE);
        binding.edtAnswer.setVisibility(View.GONE);
        binding.tvDate.setVisibility(View.GONE);
        binding.radioGroup.setVisibility(View.GONE);
        binding.tvQuestionToFollow.setVisibility(View.GONE);
        binding.tvQuestionToFollow.setText("");

        if (fieldList.get(fieldQuestionPosition).getFieldType().equals("select")) {

            binding.rlSpinner.setVisibility(View.VISIBLE);


            spAdapter = new ArrayAdapter(AddInteractionActivity.this, R.layout.spinner_item_view, fieldList.get(fieldQuestionPosition).getInteractionFieldOptionList());
            binding.spLabel.setAdapter(spAdapter);

        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("textField")) {

            binding.edtAnswer.getText().clear();
            binding.edtAnswer.setVisibility(View.VISIBLE);


            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT);

        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("textArea")) {

            binding.edtAnswer.getText().clear();

            binding.edtAnswer.setVisibility(View.VISIBLE);


            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("number")) {

            binding.edtAnswer.getText().clear();


            binding.edtAnswer.setVisibility(View.VISIBLE);


            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);


        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("decimal")) {

            binding.edtAnswer.getText().clear();


            binding.edtAnswer.setVisibility(View.VISIBLE);


            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("checkbox")) {


            binding.radioGroup.setVisibility(View.VISIBLE);


            binding.radioGroup.removeAllViews();
            binding.radioGroup.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < fieldList.get(fieldQuestionPosition).getInteractionFieldOptionList().size(); i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setLayoutParams(layoutParams);
                rdbtn.setTextSize(radioButtonTextSize);
                rdbtn.setPadding(5, 5, 5, 5);
                rdbtn.setId(fieldList.get(fieldQuestionPosition).getInteractionFieldOptionList().get(i).getId());
                rdbtn.setText(fieldList.get(fieldQuestionPosition).getInteractionFieldOptionList().get(i).getName());
                binding.radioGroup.addView(rdbtn);
            }

        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("date")
                || fieldList.get(fieldQuestionPosition).getFieldType().equals("time")) {

            if (fieldList.get(fieldQuestionPosition).getFieldType().equals("date")) {
                binding.tvDate.setHint("Select Date");
            } else {
                binding.tvDate.setHint("Select Time");
            }

            binding.tvDate.setText("");
            binding.tvDate.setVisibility(View.VISIBLE);

        }
    }

    private void addAnswers() {
        if (fieldList.get(fieldQuestionPosition).getFieldType().equals("select")) {

            Log.i("answered", binding.spLabel.getSelectedItem().toString() + "");
            answeredQuestions.put(fieldQuestionPosition, binding.spLabel.getSelectedItem().toString());

        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("checkbox")) {

            int selectedId = binding.radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);
            Log.i("answered", selectedRadioButton.getText().toString() + "");
            answeredQuestions.put(fieldQuestionPosition, selectedRadioButton.getText().toString());

        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("date")
                || fieldList.get(fieldQuestionPosition).getFieldType().equals("time")) {

            Log.i("answered", binding.tvDate.getText().toString() + "");
            answeredQuestions.put(fieldQuestionPosition, binding.tvDate.getText().toString());

        } else {
            Log.i("answered", binding.edtAnswer.getText().toString() + "");
            answeredQuestions.put(fieldQuestionPosition, binding.edtAnswer.getText().toString());
        }


    }

    private boolean validateAnswer() {

        if (isEmpty(binding.edtAnswer.getText().toString()) && fieldList.get(fieldQuestionPosition).getFieldType().equals("text")) {
            Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isEmpty(binding.edtAnswer.getText().toString()) && fieldList.get(fieldQuestionPosition).getFieldType().equals("textField")) {
            Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isEmpty(binding.edtAnswer.getText().toString()) && fieldList.get(fieldQuestionPosition).getFieldType().equals("textArea")) {
            Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isEmpty(binding.edtAnswer.getText().toString()) && fieldList.get(fieldQuestionPosition).getFieldType().equals("decimal")) {
            Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isEmpty(binding.edtAnswer.getText().toString()) && fieldList.get(fieldQuestionPosition).getFieldType().equals("number")) {
            Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
            return false;
        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("checkbox") && binding.radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select one option", Toast.LENGTH_SHORT).show();
            return false;
        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("date") && isEmpty(binding.tvDate.getText().toString())) {
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (fieldList.get(fieldQuestionPosition).getFieldType().equals("time") && isEmpty(binding.tvDate.getText().toString())) {
            Toast.makeText(this, "Please enter time", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void submitInteraction() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("active", true);
            jsonObject.put("customerId", customerID);
            jsonObject.put("interactionCategoryName", selectedInteractionCategory.getName());
            jsonObject.put("interactionTypeName", binding.typeSpinner.getSelectedItem().toString());
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < fieldList.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("field", fieldList.get(i).getName());
                String value = answeredQuestions.get(i);
                object.put("value", value);
                jsonArray.put(object);
            }
            jsonObject.put("interactionSubmissions", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("formReq", jsonObject.toString());
        disposable.add(RestClass.getClient().SUBMIT_INTERACTION_RECORD_MODEL_SINGLE(sessionManager.getTokenDetails().get(SessionManager.AccessToken)
                , jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SubmitInteractionRecordModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull SubmitInteractionRecordModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getSuccessStatus().equals("success")) {
                            finish();
                        }
                        Toast.makeText(AddInteractionActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Toast.makeText(AddInteractionActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void selectInteractionCategory(InteractionCategoryListModel interactionCategory) {
        selectedInteractionCategory = interactionCategory;
        binding.headerLayout.txtHeading.setText("Add Interaction Record");
        binding.recyclerViewCategories.setVisibility(View.GONE);
        binding.llAddInteractions.setVisibility(View.VISIBLE);
        fieldList = selectedInteractionCategory.getInteractionCategoryFieldList();
        updatePage();
    }
}