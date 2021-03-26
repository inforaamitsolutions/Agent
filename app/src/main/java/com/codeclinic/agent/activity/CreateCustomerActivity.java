package com.codeclinic.agent.activity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityCreateCustomerBinding;
import com.codeclinic.agent.model.customer.CustomerQuestionsListModel;
import com.codeclinic.agent.model.customer.CustomerSubmitFormModel;
import com.codeclinic.agent.model.customer.CustomerSurveyDefinitionPageModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.SessionManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;
import static com.codeclinic.agent.database.LocalDatabase.localDatabase;
import static com.codeclinic.agent.utils.CommonMethods.datePicker;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class CreateCustomerActivity extends AppCompatActivity {
    ActivityCreateCustomerBinding binding;
    String[] products = {"MobiDrinks"};

    CompositeDisposable disposable = new CompositeDisposable();

    int surveyPage = 0, questionPage = 0, radioButtonTextSize;
    ArrayAdapter spAdapter;

    List<CustomerSurveyDefinitionPageModel> surveyPagesList = new ArrayList<>();
    Map<Integer, List<CustomerQuestionsListModel>> questionList = new HashMap<>();


    Map<Integer, Map<Integer, String>> surveyQuestions = new HashMap<>();
    Map<Integer, String> answeredQuestions = new HashMap<>();
    LinearLayout.LayoutParams layoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_customer);
        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.txtHeading.setText("Create Customer");

        binding.headerLayout.imgBack.setOnClickListener(view -> {
            onBackPressed();
        });


        binding.linearCustomers.setOnClickListener(view -> {
            binding.imgDropDown2.setVisibility(View.GONE);

            binding.linearCustomers.setBackgroundColor(getResources().getColor(R.color.black));
            binding.txtCustomer.setTextColor(getResources().getColor(R.color.white));
            binding.txtDistributors.setTextColor(getResources().getColor(R.color.black));
            binding.linearDistributors.setBackgroundColor(getResources().getColor(R.color.white));

        });

        binding.linearDistributors.setOnClickListener(view -> {
            binding.imgDropDown2.setVisibility(View.VISIBLE);


            binding.linearCustomers.setBackgroundColor(getResources().getColor(R.color.white));
            binding.txtCustomer.setTextColor(getResources().getColor(R.color.black));
            binding.txtDistributors.setTextColor(getResources().getColor(R.color.white));
            binding.linearDistributors.setBackgroundColor(getResources().getColor(R.color.black));
        });

        binding.btnPrevious.setOnClickListener(v -> {
            if (binding.llQuestions.getVisibility() == View.GONE) {
                binding.llQuestions.setVisibility(View.VISIBLE);
                binding.linearUserDetail.setVisibility(View.GONE);
            } else {
                if (questionPage > 0) {
                    questionPage--;
                    updatePage();
                } else if (surveyPage > 0) {
                    surveyPage--;
                    questionPage = surveyPagesList.get(surveyPage).getQuestions().size() - 1;
                    updatePage();
                }
            }
        });

        binding.btnNext1.setOnClickListener(view -> {
            if (validateAnswer()) {
                if (surveyPagesList.get(surveyPage).getQuestions().size() > (questionPage + 1)) {

                    addAnswers();
                    questionPage++;
                    updatePage();

                } else if (surveyPagesList.size() > (surveyPage + 1)) {

                    addAnswers();

                    surveyQuestions.put(surveyPage, answeredQuestions);
                    Log.i("surveyQuestions", new Gson().toJson(surveyQuestions));
                    answeredQuestions = new HashMap<>();
                    questionPage = 0;
                    surveyPage++;
                    updatePage();

                } else {

                    if (binding.llQuestions.getVisibility() == View.VISIBLE) {
                        binding.llQuestions.setVisibility(View.GONE);
                        binding.linearUserDetail.setVisibility(View.VISIBLE);
                    } else {
                        addAnswers();
                        surveyQuestions.put(surveyPage, answeredQuestions);
                        Log.i("surveyQuestions", new Gson().toJson(surveyQuestions));
                        answeredQuestions = new HashMap<>();
                        questionPage = 0;
                        submitForm();
                    }

                }
            }
        });

        binding.tvDate.setOnClickListener(v -> datePicker(binding.tvDate, this));

        final float scale = getResources().getDisplayMetrics().density;
        radioButtonTextSize = (int) (14 * scale + 0.5f);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);


        //callCustomerForm();
        getSurveyForm();
    }

    private void getSurveyForm() {
        disposable.add(localDatabase.getDAO().getCustomerSurveyFormList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {

                            surveyPagesList = list;
                            for (int i = 0; i < surveyPagesList.size(); i++) {
                                questionList.put(i, surveyPagesList.get(i).getQuestions());
                            }
                            Log.i("surveyPages", new Gson().toJson(surveyPagesList));
                            updatePage();

                        },
                        throwable -> {
                            if (throwable.getMessage() != null)
                                Log.i("customerSurveyForm", "Error == " + throwable.getMessage());
                        }
                )
        );
    }

    private void callCustomerForm() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        disposable.add(RestClass.getClient().FETCH_CUSTOMER_FORM_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                "Customer Registration Form")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FetchCustomerFormModel>() {
                    @Override
                    public void onSuccess(@NonNull FetchCustomerFormModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getBody() != null) {
                            surveyPagesList = response.getBody().getSurveyDefinitionPages();

                            for (int i = 0; i < surveyPagesList.size(); i++) {
                                questionList.put(i, surveyPagesList.get(i).getQuestions());
                            }
                            updatePage();

                        } else {
                            Toast.makeText(CreateCustomerActivity.this, "" + response.getSuccessStatus(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(CreateCustomerActivity.this, "Server error " + e.getMessage(), Toast.LENGTH_LONG).show();
                        binding.loadingView.loader.setVisibility(View.GONE);
                    }
                }));
    }

    private void submitForm() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerRole", "MYMOBI_INDIVIDUAL_CUSTOMER");
            jsonObject.put("firstName", binding.edtFirstName.getText().toString());
            jsonObject.put("lastName", binding.edtLastName.getText().toString());
            jsonObject.put("middleName", binding.edtMiddleName.getText().toString());
            jsonObject.put("staffId", sessionManager.getUserCredentials().get(SessionManager.UserID));
            jsonObject.put("status", "COMPLETED");
            jsonObject.put("surveyName", "Customer Registration Form");

            JSONArray jsonArrayPages = new JSONArray();
            JSONObject jsonObject1 = new JSONObject();
            for (int i = 0; i < surveyPagesList.size(); i++) {
                JSONArray jsonArray = new JSONArray();

                Map<Integer, String> mapAnswered = surveyQuestions.get(i);

               /* for (int j =0 ; j<surveyPagesList.get(i).getQuestions().size();j++){
                    JSONObject object = new JSONObject();
                    object.put("fieldName", surveyPagesList.get(i).getQuestions().get(j).getFieldName());
                    object.put("responseText", surveyQuestions.get(i).get(j));
                    jsonArray.put(object);
                }*/
                for (Map.Entry<Integer, String> entry : mapAnswered.entrySet()) {
                    String value = entry.getValue();
                    JSONObject object = new JSONObject();
                    object.put("fieldName", surveyPagesList.get(i).getQuestions().get(entry.getKey()).getFieldName());
                    object.put("responseText", value);
                    jsonArray.put(object);
                }
                if (i == 0) {
                    jsonObject1.put("businessData", jsonArray);
                } else if (i == 1) {
                    jsonObject1.put("customerBiodata", jsonArray);
                } else if (i == 2) {
                    jsonObject1.put("supplierData", jsonArray);
                } else {
                    jsonObject1.put("socialData", jsonArray);
                }

            }

            jsonArrayPages.put(jsonObject1);
            jsonObject.put("pages", jsonArrayPages);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("formReq", jsonObject.toString());

        disposable.add(RestClass.getClient().CUSTOMER_SUBMIT_FORM_MODEL_SINGLE_CALL(sessionManager.getTokenDetails().get(SessionManager.AccessToken)
                , jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CustomerSubmitFormModel>() {
                    @Override
                    public void onSuccess(@NonNull CustomerSubmitFormModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getResponseCode() == 0) {
                            finish();
                        }
                        Toast.makeText(CreateCustomerActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Toast.makeText(CreateCustomerActivity.this, "Server Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void updatePage() {

        binding.tvTitle.setText(surveyPagesList.get(surveyPage).getTitle());
        binding.tvQuestion.setText(questionList.get(surveyPage).get(questionPage).getQuestionText());

        if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("select")) {
            binding.rlSpinner.setVisibility(View.VISIBLE);
            binding.edtAnswer.setVisibility(View.GONE);
            binding.tvDate.setVisibility(View.GONE);
            binding.radioGroup.setVisibility(View.GONE);

            spAdapter = new ArrayAdapter(CreateCustomerActivity.this, R.layout.spinner_item_view, questionList.get(surveyPage).get(questionPage).getOptions());
            binding.spLabel.setAdapter(spAdapter);

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("text")) {

            binding.edtAnswer.getText().clear();

            binding.rlSpinner.setVisibility(View.GONE);
            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.tvDate.setVisibility(View.GONE);
            binding.radioGroup.setVisibility(View.GONE);

            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.edtAnswer.setText(data.get(questionPage));
                    }
                }
            }

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("textarea")) {

            binding.edtAnswer.getText().clear();

            binding.rlSpinner.setVisibility(View.GONE);
            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.tvDate.setVisibility(View.GONE);
            binding.radioGroup.setVisibility(View.GONE);

            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.edtAnswer.setText(data.get(questionPage));
                    }
                }
            }

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("number")) {

            binding.edtAnswer.getText().clear();

            binding.rlSpinner.setVisibility(View.GONE);
            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.tvDate.setVisibility(View.GONE);
            binding.radioGroup.setVisibility(View.GONE);

            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.edtAnswer.setText(data.get(questionPage));
                    }
                }
            }

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("checkbox")) {

            binding.rlSpinner.setVisibility(View.GONE);
            binding.edtAnswer.setVisibility(View.GONE);
            binding.tvDate.setVisibility(View.GONE);
            binding.radioGroup.setVisibility(View.VISIBLE);

            binding.radioGroup.removeAllViews();
            binding.radioGroup.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < questionList.get(surveyPage).get(questionPage).getOptions().size(); i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setLayoutParams(layoutParams);
                rdbtn.setTextSize(radioButtonTextSize);
                rdbtn.setPadding(5, 5, 5, 5);
                rdbtn.setId(questionList.get(surveyPage).get(questionPage).getOptions().get(i).getId());
                rdbtn.setText(questionList.get(surveyPage).get(questionPage).getOptions().get(i).getLabel());
                binding.radioGroup.addView(rdbtn);
            }

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("date")) {

            binding.tvDate.setText("");

            binding.rlSpinner.setVisibility(View.GONE);
            binding.edtAnswer.setVisibility(View.GONE);
            binding.tvDate.setVisibility(View.VISIBLE);
            binding.radioGroup.setVisibility(View.GONE);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.tvDate.setText(data.get(questionPage));
                    }
                }
            }
        }
    }

    private void addAnswers() {
        if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("select")) {

            Log.i("answered", binding.spLabel.getSelectedItem().toString() + "");
            answeredQuestions.put(questionPage, binding.spLabel.getSelectedItem().toString());

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("checkbox")) {

            int selectedId = binding.radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);
            Log.i("answered", selectedRadioButton.getText().toString() + "");
            answeredQuestions.put(questionPage, selectedRadioButton.getText().toString());

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("date")) {

            Log.i("answered", binding.tvDate.getText().toString() + "");
            answeredQuestions.put(questionPage, binding.tvDate.getText().toString());


        } else {
            Log.i("answered", binding.edtAnswer.getText().toString() + "");
            answeredQuestions.put(questionPage, binding.edtAnswer.getText().toString());
        }
    }

    private boolean validateAnswer() {
        if (binding.llQuestions.getVisibility() == View.VISIBLE) {
            if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionList.get(surveyPage).get(questionPage).getFieldType().equals("text")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionList.get(surveyPage).get(questionPage).getFieldType().equals("textarea")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionList.get(surveyPage).get(questionPage).getFieldType().equals("number")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } /*else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("text")
                    && !isEmpty(questionList.get(surveyPage).get(questionPage).getRegularExpression())
                    && !binding.edtAnswer.getText().toString().matches(questionList.get(surveyPage).get(questionPage).getRegularExpression())) {
                Toast.makeText(this, "Please enter valid detail", Toast.LENGTH_SHORT).show();
                return false;
            }*/ else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("checkbox")
                    && binding.radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select one option", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("date")
                    && isEmpty(binding.tvDate.getText().toString())) {
                Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (binding.linearUserDetail.getVisibility() == View.VISIBLE) {
            if (isEmpty(binding.edtFirstName.getText().toString())) {
                Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtMiddleName.getText().toString())) {
                Toast.makeText(this, "Please enter middle name", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtLastName.getText().toString())) {
                Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}