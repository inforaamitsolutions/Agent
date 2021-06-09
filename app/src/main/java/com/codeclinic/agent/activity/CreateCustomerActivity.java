package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.FormSummaryModel;
import com.codeclinic.agent.adapter.SummaryFormAdapter;
import com.codeclinic.agent.database.customer.CustomerFinalFormEntity;
import com.codeclinic.agent.database.customer.CustomerFormResumeEntity;
import com.codeclinic.agent.databinding.ActivityCreateCustomerBinding;
import com.codeclinic.agent.databinding.CheckCustomerDialogBinding;
import com.codeclinic.agent.model.CheckCustomerExistModel;
import com.codeclinic.agent.model.customer.CustomerOptionsListModel;
import com.codeclinic.agent.model.customer.CustomerQuestionToFollowModel;
import com.codeclinic.agent.model.customer.CustomerQuestionsListModel;
import com.codeclinic.agent.model.customer.CustomerSubmitFormModel;
import com.codeclinic.agent.model.customer.CustomerSurveyDefinitionPageModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.AccessMediaUtil;
import com.codeclinic.agent.utils.Connection_Detector;
import com.codeclinic.agent.utils.LoadingDialog;
import com.codeclinic.agent.utils.LocationInfo;
import com.codeclinic.agent.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.obsez.android.lib.filechooser.ChooserDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;
import static com.codeclinic.agent.database.LocalDatabase.localDatabase;
import static com.codeclinic.agent.utils.CommonMethods.birthDatePicker;
import static com.codeclinic.agent.utils.CommonMethods.datePicker;
import static com.codeclinic.agent.utils.CommonMethods.isPermissionGranted;
import static com.codeclinic.agent.utils.Constants.ACCESS_CAMERA_GALLERY;
import static com.codeclinic.agent.utils.Constants.ACCESS_SIGNATURE;
import static com.codeclinic.agent.utils.Constants.PICTURE_PATH;
import static com.codeclinic.agent.utils.Constants.SIGNATURE_PATH;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class CreateCustomerActivity extends AppCompatActivity {
    ActivityCreateCustomerBinding binding;

    CompositeDisposable disposable = new CompositeDisposable();
    CustomerFormResumeEntity customerFormResumeEntity;
    String imagePath, filePath, signaturePath;
    int surveyPage = 0, questionPage = 0, questionToFollowPage = -1, radioButtonTextSize, edtHeight;
    ArrayAdapter spAdapter;
    AlertDialog alertDialog;

    List<CustomerSurveyDefinitionPageModel> surveyPagesList = new ArrayList<>();
    List<FormSummaryModel> summaryList = new ArrayList<>();
    Map<Integer, List<CustomerQuestionsListModel>> questionList = new HashMap<>();

    HashMap<Integer, Map<Integer, String>> surveyQuestions = new LinkedHashMap<>();
    Map<Integer, String> answeredQuestions = new HashMap<>();
    HashMap<Integer, Map<Integer, String>> optionQuestions = new LinkedHashMap<>();
    Map<Integer, String> answeredToFollowQuestions = new HashMap<>();
    LinearLayout.LayoutParams layoutParams;

    boolean isSubmitForm = false, isFormSubmitted = false, isSignatureSelection = false;
    private ChooserDialog chooserDialog;
    private LoadingDialog loadingDialog;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_customer);

        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.txtHeading.setText("Create Customer");
        loadingDialog = new LoadingDialog(this);

        LocationInfo.getLastLocation(this, null);

        binding.recyclerViewSummary.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerViewSummary.setNestedScrollingEnabled(false);

        binding.headerLayout.imgBack.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.imgUser.setOnClickListener(v -> {
            isSignatureSelection = false;
            selectImage();
        });

        binding.imgSignature.setOnClickListener(v -> {
            isSignatureSelection = true;
            selectImage();
        });

        chooseFile();

        binding.llFile.setOnClickListener(v -> {
            chooserDialog.show();
        });

        binding.btnConfirm.setOnClickListener(v -> {
            if (isEmpty(binding.edtFullName.getText().toString())) {
                Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();
            } /*else if (!binding.edtFullName.getText().toString().matches("^(\\\\w.+\\\\s).+$")) {
                Toast.makeText(this, "Please enter valid name", Toast.LENGTH_SHORT).show();
            }*/ else if (isEmpty(binding.edtMobileNo.getText().toString())) {
                Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            } else if (!binding.edtMobileNo.getText().toString().matches("^(?:254|\\\\+254|0)((?:7|1)[0-9]{8})$")) {
                Toast.makeText(this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
            } else if (isEmpty(binding.edtDocumentNo.getText().toString())) {
                Toast.makeText(this, "Please enter ID number", Toast.LENGTH_SHORT).show();
            } else if (!binding.edtDocumentNo.getText().toString().matches("^[0-9]{5,10}$")) {
                Toast.makeText(this, "Please enter valid ID number", Toast.LENGTH_SHORT).show();
            } else if (isEmpty(binding.tvBirthDate.getText().toString())) {
                Toast.makeText(this, "Please enter customers birth date", Toast.LENGTH_SHORT).show();
            } else {
                binding.llSections.setVisibility(View.VISIBLE);
                binding.llCustomerDetails.setVisibility(View.GONE);
            }
        });

        binding.btnPrevious.setOnClickListener(v -> {
            questionToFollowPage = -1;
            if (questionPage == 0 && surveyPage == 0) {
                binding.llSections.setVisibility(View.GONE);
                binding.llCustomerDetails.setVisibility(View.VISIBLE);
            } else if (binding.llQuestions.getVisibility() == View.GONE) {
                binding.llQuestions.setVisibility(View.VISIBLE);
                isSubmitForm = false;
                questionPage = surveyPagesList.get(surveyPage).getQuestions().size() - 1;
            } else {
                if (questionPage > 0) {
                    questionPage--;
                } else if (surveyPage > 0) {
                    surveyPage--;
                    questionPage = surveyPagesList.get(surveyPage).getQuestions().size() - 1;
                }

            }
            updatePage();
        });

        binding.btnNext1.setOnClickListener(view -> {
            if ((questionList.get(surveyPage).get(questionPage).getFieldType().equals("select_one")
                    || questionList.get(surveyPage).get(questionPage).getFieldType().equals("select_multiple"))) {
                List<CustomerQuestionToFollowModel> questionToFollowList = questionList.get(surveyPage).get(questionPage).getOptions().get(binding.spLabel.getSelectedItemPosition()).getQuestionToFollow();


                if (questionToFollowList != null && binding.llSections.getVisibility() == View.VISIBLE) {
                    if (questionToFollowList.size() != 0) {
                        if (questionToFollowPage == -1) {
                            questionToFollowPage = 0;
                            updateQuestionToFollowPage();
                        } else if (questionToFollowList.size() > (questionToFollowPage + 1)) {
                            if (validateQueToFollowAnswer(questionToFollowList)) {
                                addAnswersToFollowAnswers();
                                questionToFollowPage = questionToFollowPage + 1;
                                updateQuestionToFollowPage();
                            }
                        } else if (validateQueToFollowAnswer(questionToFollowList)) {
                            int optionPageKey = questionList.get(surveyPage).get(questionPage).getOptions().get(binding.spLabel.getSelectedItemPosition()).getId();
                            addAnswersToFollowAnswers();
                            optionQuestions.put(optionPageKey, answeredToFollowQuestions);
                            Log.i("optionsQuestions", new Gson().toJson(optionQuestions));
                            answeredToFollowQuestions = new HashMap<>();
                            questionToFollowPage = -1;
                            moveToNextQuestion();
                        }
                    } else {
                        moveToNextQuestion();
                    }
                } else {
                    moveToNextQuestion();
                }
            } else {
                moveToNextQuestion();
            }
        });

        binding.btnBack.setOnClickListener(v -> {
            isSubmitForm = false;
            binding.llSections.setVisibility(View.VISIBLE);
            binding.llSummary.setVisibility(View.GONE);
        });

        binding.btnSubmit.setOnClickListener(v -> submitForm());

        binding.tvDate.setOnClickListener(v -> datePicker(binding.tvDate, this));

        binding.tvBirthDate.setOnClickListener(v -> {
            birthDatePicker(binding.tvBirthDate, binding.tvAge, this);
        });

        binding.tvTime.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {
                binding.tvTime.setText(selectedHour + " : " + selectedMinute);
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });


        final float scale = getResources().getDisplayMetrics().density;
        radioButtonTextSize = (int) (14 * scale + 0.5f);
        edtHeight = (int) (100 * scale + 0.5f);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);

        getCustomerFormResume();
    }

    @SuppressLint("CheckResult")
    public void checkCustomerExistDialog() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        CheckCustomerDialogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.check_customer_dialog, viewGroup, false);

        dialogBinding.imgClose.setOnClickListener(v -> {
            alertDialog.dismiss();
            finish();
        });

        dialogBinding.btnConfirm.setOnClickListener(view -> {
            if (isEmpty(dialogBinding.edtMobileNo.getText().toString())) {
                Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            } else if (!dialogBinding.edtMobileNo.getText().toString().matches("^(?:254|\\\\+254|0)((?:7|1)[0-9]{8})$")) {
                Toast.makeText(this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
            } else if (isEmpty(dialogBinding.edtDocumentNo.getText().toString())) {
                Toast.makeText(this, "Please enter ID number", Toast.LENGTH_SHORT).show();
            } else if (!dialogBinding.edtDocumentNo.getText().toString().matches("^[0-9]{5,10}$")) {
                Toast.makeText(this, "Please enter valid ID number", Toast.LENGTH_SHORT).show();
            } else {
                loadingDialog.showProgressDialog("");

                disposable.add(RestClass.getClient().CHECK_CUSTOMER_EXIST_MODEL_SINGLE(sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                        dialogBinding.edtDocumentNo.getText().toString(),
                        dialogBinding.edtMobileNo.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CheckCustomerExistModel>() {
                            @Override
                            public void onSuccess(CheckCustomerExistModel response) {
                                loadingDialog.hideProgressDialog();
                                if (response != null) {
                                    Toast.makeText(CreateCustomerActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                                    binding.edtMobileNo.setText(dialogBinding.edtMobileNo.getText().toString());
                                    binding.edtDocumentNo.setText(dialogBinding.edtDocumentNo.getText().toString());
                                    if (response.getMessage().equals("existing_mobiloan_customer") || response.getMessage().equals("existing_mymobi_customer")) {
                                        binding.rbYes.setChecked(true);
                                        binding.rbNo.setVisibility(View.GONE);
                                    } else {
                                        binding.rbNo.setChecked(true);
                                        binding.rbYes.setVisibility(View.GONE);
                                    }
                                    alertDialog.dismiss();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                loadingDialog.hideProgressDialog();
                                Toast.makeText(CreateCustomerActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            }
                        }));
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogBinding.getRoot());

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    private void getSurveyForm() {
        disposable.add(localDatabase.getDAO().getCustomerSurveyForm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(form -> {

                            surveyPagesList = form.getSurveyDefinitionPages();
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

    @SuppressLint("SetTextI18n")
    private void getCustomerFormResume() {
        disposable.add(Single.fromCallable(this::checkFormSavedInDB)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((isExist) -> {
                    if (isExist) {
                        disposable.add(localDatabase.getDAO().getCustomerFormResume()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(form -> {
                                            if (form != null) {
                                                customerFormResumeEntity = form;
                                                surveyQuestions = new Gson().fromJson(form.getSurveyQuestions(), new TypeToken<HashMap<Integer, Map<Integer, String>>>() {
                                                }.getType());
                                                if (!isEmpty(form.getOptionQuestions())) {
                                                    optionQuestions = new Gson().fromJson(form.getOptionQuestions(), new TypeToken<HashMap<Integer, Map<Integer, String>>>() {
                                                    }.getType());
                                                }
                                                Log.i("customerFormResume", "Data stored is " + new Gson().toJson(surveyQuestions));
                                            }
                                            binding.edtFullName.setText(form.getName() + "");
                                            binding.edtDocumentNo.setText(form.getIdNumber() + "");
                                            binding.edtMobileNo.setText(form.getNumber());
                                            binding.tvBirthDate.setText(form.getBirthDate() + "");
                                            binding.tvAge.setText(form.getAge() + "");
                                            if (form.getExist().equals("yes")) {
                                                binding.rbYes.setChecked(true);
                                                binding.rbNo.setVisibility(View.GONE);
                                            } else {
                                                binding.rbYes.setVisibility(View.GONE);
                                                binding.rbNo.setChecked(true);
                                            }
                                            getSurveyForm();
                                        },
                                        throwable -> {
                                            if (throwable.getMessage() != null) {
                                                Log.i("customerFormResume", "Error == " + throwable.getMessage());
                                            }
                                            getSurveyForm();
                                        }
                                )
                        );
                    } else {
                        if (Connection_Detector.isInternetAvailable(this)) {
                            checkCustomerExistDialog();
                        }
                        getSurveyForm();
                    }
                }));
    }

    private boolean checkFormSavedInDB() {
        return localDatabase.getDAO().isCustomerFormResumeExists();
    }

    private void saveCustomerFormToLocal(String request) {
        CustomerFinalFormEntity customerFinalFormEntity = new CustomerFinalFormEntity();
        customerFinalFormEntity.setMainId(0);
        customerFinalFormEntity.setRequest(request);
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                .saveCustomerFinalForm(customerFinalFormEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(CreateCustomerActivity.this, "Customer Saved to local", Toast.LENGTH_SHORT).show();
                        finish();
                        Log.i("customerForm", "Customer final form saved to local");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("customerForm", "Error  ==  " + e.getMessage());
                        Toast.makeText(CreateCustomerActivity.this, "Error  ==  " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void moveToNextQuestion() {
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

                addAnswers();
                surveyQuestions.put(surveyPage, answeredQuestions);
                Log.i("surveyQuestions", new Gson().toJson(surveyQuestions));
                answeredQuestions = new HashMap<>();
                questionPage = 0;
                isSubmitForm = true;
                renderSummary();
            }
        }
    }


    private void renderSummary() {
        binding.llSections.setVisibility(View.GONE);
        binding.llSummary.setVisibility(View.VISIBLE);
        //binding.loadingView.loader.setVisibility(View.VISIBLE);
        summaryList.clear();
        for (int i = 0; i < surveyPagesList.size(); i++) {

            Map<Integer, String> mapAnswered = surveyQuestions.get(i);

            FormSummaryModel section = new FormSummaryModel();
            section.setAnswer("");
            section.setQuestion("");
            section.setSection(surveyPagesList.get(i).getPageName());
            summaryList.add(section);

            for (Map.Entry<Integer, String> entry : mapAnswered.entrySet()) {
                String value = entry.getValue();

                FormSummaryModel object = new FormSummaryModel();
                object.setQuestion(surveyPagesList.get(i).getQuestions().get(entry.getKey()).getQuestionText());
                object.setAnswer(value);
                object.setSection("");
                summaryList.add(object);


                if ((surveyPagesList.get(i).getQuestions().get(entry.getKey()).getFieldType().equals("select_one")
                        || surveyPagesList.get(i).getQuestions().get(entry.getKey()).getFieldType().equals("select_multiple"))) {
                    List<CustomerOptionsListModel> options = surveyPagesList.get(i).getQuestions().get(entry.getKey()).getOptions();

                    for (int j = 0; j < options.size(); j++) {

                        if (options.get(j).getQuestionToFollow() != null && value.equals(options.get(j).getValue())) {

                            if (options.get(j).getQuestionToFollow().size() != 0) {

                                if (optionQuestions.containsKey(options.get(j).getId())) {
                                    Map<Integer, String> questionToFollowAnswered = optionQuestions.get(options.get(j).getId());

                                    if (questionToFollowAnswered != null) {
                                        for (Map.Entry<Integer, String> item : questionToFollowAnswered.entrySet()) {
                                            FormSummaryModel subQuestions = new FormSummaryModel();
                                            subQuestions.setQuestion(options.get(j).getQuestionToFollow().get(item.getKey()).getQuestionText());
                                            subQuestions.setAnswer(item.getValue());
                                            subQuestions.setSection("");
                                            summaryList.add(subQuestions);
                                        }
                                    }
                                }

                            }

                        }

                    }
                }
            }
        }

        Log.i("formReq", "Summary List is => " + new Gson().toJson(summaryList));

        binding.recyclerViewSummary.setAdapter(new SummaryFormAdapter(summaryList, this));


    }

    private void submitForm() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerRole", "MYMOBI_INDIVIDUAL_CUSTOMER");
            jsonObject.put("customerName", binding.edtFullName.getText().toString());
            jsonObject.put("mobileNumber", binding.edtMobileNo.getText().toString());
            jsonObject.put("dateOfBirth", binding.tvBirthDate.getText().toString());
            jsonObject.put("customerAge", binding.tvAge.getText().toString());
            jsonObject.put("idNumber", binding.edtDocumentNo.getText().toString());
            jsonObject.put("existingCustomer", binding.rbYes.isChecked() ? "existing" : "new");
            jsonObject.put("staffId", sessionManager.getUserDetails().get(SessionManager.UserID));
            jsonObject.put("status", "COMPLETED");
            jsonObject.put("surveyName", "customer_registration_form");

            JSONArray jsonArrayPages = new JSONArray();
            JSONObject jsonObject1 = new JSONObject();
            for (int i = 0; i < surveyPagesList.size(); i++) {
                JSONArray jsonArray = new JSONArray();

                Map<Integer, String> mapAnswered = surveyQuestions.get(i);


                for (Map.Entry<Integer, String> entry : mapAnswered.entrySet()) {
                    String value = entry.getValue();
                    JSONObject object = new JSONObject();
                    object.put("fieldName", surveyPagesList.get(i).getQuestions().get(entry.getKey()).getFieldName());
                    object.put("responseText", value);
                    jsonArray.put(object);

                    if ((surveyPagesList.get(i).getQuestions().get(entry.getKey()).getFieldType().equals("select_one")
                            || surveyPagesList.get(i).getQuestions().get(entry.getKey()).getFieldType().equals("select_multiple"))) {
                        List<CustomerOptionsListModel> options = surveyPagesList.get(i).getQuestions().get(entry.getKey()).getOptions();

                        for (int j = 0; j < options.size(); j++) {

                            if (options.get(j).getQuestionToFollow() != null && value.equals(options.get(j).getValue())) {

                                if (options.get(j).getQuestionToFollow().size() != 0) {

                                    if (options.get(j).getQuestionToFollow().size() != 0) {
                                        Map<Integer, String> questionToFollowAnswered = optionQuestions.get(options.get(j).getId());

                                        if (questionToFollowAnswered != null) {
                                            for (Map.Entry<Integer, String> item : questionToFollowAnswered.entrySet()) {
                                                JSONObject jObject = new JSONObject();
                                                jObject.put("fieldName", options.get(j).getQuestionToFollow().get(item.getKey()).getFieldName());
                                                jObject.put("responseText", item.getValue());
                                                jsonArray.put(jObject);
                                            }
                                        }
                                    }

                                }

                            }

                        }
                    }
                }

                jsonObject1.put(surveyPagesList.get(i).getPageName(), jsonArray);

            }

            jsonArrayPages.put(jsonObject1);
            jsonObject.put("pages", jsonArrayPages);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("formReq", jsonObject.toString());

        if (Connection_Detector.isInternetAvailable(this)) {
            disposable.add(RestClass.getClient().CUSTOMER_SUBMIT_FORM_MODEL_SINGLE_CALL(sessionManager.getTokenDetails().get(SessionManager.AccessToken)
                    , jsonObject.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<CustomerSubmitFormModel>() {
                        @Override
                        public void onSuccess(@NonNull CustomerSubmitFormModel response) {
                            binding.loadingView.loader.setVisibility(View.GONE);
                            if (response.getSuccessStatus().equals("success")) {
                                finish();
                                isFormSubmitted = true;
                            }
                            Toast.makeText(CreateCustomerActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            binding.loadingView.loader.setVisibility(View.GONE);
                            if (e.getMessage().contains("401")) {
                                Toast.makeText(CreateCustomerActivity.this, "Session Time out you have to login again", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(CreateCustomerActivity.this, "Server Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    }));
        } else {
            binding.loadingView.loader.setVisibility(View.GONE);
            saveCustomerFormToLocal(jsonObject.toString());
        }


    }

    @SuppressLint("SetTextI18n")
    synchronized private void updatePage() {

        binding.tvTitle.setText(surveyPagesList.get(surveyPage).getTitle());
        binding.tvQuestion.setText(questionList.get(surveyPage).get(questionPage).getQuestionText());

        binding.tvQuestionToFollow.setVisibility(View.GONE);
        binding.rlQueToFollowSpinner.setVisibility(View.GONE);
        binding.rlSpinner.setVisibility(View.GONE);
        binding.edtAnswer.setVisibility(View.GONE);
        binding.tvDate.setVisibility(View.GONE);
        binding.tvTime.setVisibility(View.GONE);
        binding.radioGroup.setVisibility(View.GONE);
        binding.imgUser.setVisibility(View.GONE);
        binding.imgSignature.setVisibility(View.GONE);
        binding.llFile.setVisibility(View.GONE);
        binding.tvQuestionToFollow.setVisibility(View.GONE);
        binding.tvQuestionToFollow.setText("");
        binding.tvDate.setText("");
        binding.tvTime.setText("");
        binding.tvFileName.setText("");
        binding.edtAnswer.getText().clear();

        CustomerQuestionsListModel question = questionList.get(surveyPage).get(questionPage);

        RelativeLayout.LayoutParams lp;
        if (question.getFieldType().equals("textArea")) {
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, edtHeight);
        } else {
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        binding.edtAnswer.setLayoutParams(lp);

        try {
            binding.edtAnswer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(question.getMax())});
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (question.getFieldType().equals("select_one") || question.getFieldType().equals("select_multiple")) {

            binding.tvQuestionToFollow.setVisibility(View.VISIBLE);
            binding.rlSpinner.setVisibility(View.VISIBLE);


            spAdapter = new ArrayAdapter(CreateCustomerActivity.this, R.layout.spinner_item_view, question.getOptions());
            binding.spLabel.setAdapter(spAdapter);

        } else if (question.getFieldType().equals("textfield")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.edtAnswer.setText(data.get(questionPage));
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                binding.edtAnswer.setText(answeredQuestions.get(questionPage));
            }

        } else if (question.getFieldType().equals("textField")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT);


            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.edtAnswer.setText(data.get(questionPage));
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                binding.edtAnswer.setText(answeredQuestions.get(questionPage));
            }

        } else if (question.getFieldType().equals("textArea")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.edtAnswer.setText(data.get(questionPage));
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                binding.edtAnswer.setText(answeredQuestions.get(questionPage));
            }

        } else if (question.getFieldType().equals("number")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.edtAnswer.setText(data.get(questionPage));
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                binding.edtAnswer.setText(answeredQuestions.get(questionPage));
            }

        } else if (question.getFieldType().equals("decimal")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.edtAnswer.setText(data.get(questionPage));
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                binding.edtAnswer.setText(answeredQuestions.get(questionPage));
            }

        } else if (question.getFieldType().equals("integer")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.edtAnswer.setText(data.get(questionPage));
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                binding.edtAnswer.setText(answeredQuestions.get(questionPage));
            }

        } else if (question.getFieldType().equals("checkbox")) {


            binding.radioGroup.setVisibility(View.VISIBLE);


            binding.radioGroup.removeAllViews();
            binding.radioGroup.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < question.getOptions().size(); i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setLayoutParams(layoutParams);
                rdbtn.setTextSize(radioButtonTextSize);
                rdbtn.setPadding(5, 5, 5, 5);
                rdbtn.setId(question.getOptions().get(i).getId());
                rdbtn.setText(question.getOptions().get(i).getLabel());
                binding.radioGroup.addView(rdbtn);
            }

        } else if (question.getFieldType().equals("date")) {

            binding.tvDate.setVisibility(View.VISIBLE);


            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.tvDate.setText(data.get(questionPage));
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                binding.tvDate.setText(answeredQuestions.get(questionPage));
            }
        } else if (question.getFieldType().equals("time")) {

            binding.tvTime.setVisibility(View.VISIBLE);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.tvTime.setText(data.get(questionPage));
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                binding.tvTime.setText(answeredQuestions.get(questionPage));
            }
        } else if (question.getFieldType().equals("geopoint")) {

            binding.tvDate.setVisibility(View.VISIBLE);

            binding.tvDate.setText(LocationInfo.location.getLongitude() + "," + LocationInfo.location.getLatitude());

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.tvDate.setText(data.get(questionPage));
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                binding.tvDate.setText(answeredQuestions.get(questionPage));
            }
        } else if (question.getFieldType().equals("image")) {

            binding.imgUser.setVisibility(View.VISIBLE);

            Glide.with(CreateCustomerActivity.this).load("").into(binding.imgUser);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        Glide.with(CreateCustomerActivity.this).load(data.get(questionPage)).into(binding.imgUser);
                        imagePath = data.get(questionPage);
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                Glide.with(CreateCustomerActivity.this).load(answeredQuestions.get(questionPage)).into(binding.imgUser);
                imagePath = answeredQuestions.get(questionPage);
            }
        } else if (question.getFieldType().equals("signature")) {

            binding.imgSignature.setVisibility(View.VISIBLE);

            Glide.with(CreateCustomerActivity.this).load("").into(binding.imgSignature);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        Glide.with(CreateCustomerActivity.this).load(data.get(questionPage)).into(binding.imgSignature);
                        signaturePath = data.get(questionPage);
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                Glide.with(CreateCustomerActivity.this).load(answeredQuestions.get(questionPage)).into(binding.imgSignature);
                signaturePath = answeredQuestions.get(questionPage);
            }
        } else if (question.getFieldType().equals("file")) {

            binding.llFile.setVisibility(View.VISIBLE);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        binding.tvFileName.setText(data.get(questionPage) + "");
                        filePath = data.get(questionPage) + "";
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                binding.tvFileName.setText(answeredQuestions.get(questionPage) + "");
                filePath = answeredQuestions.get(questionPage) + "";
            }
        }
    }

    synchronized private void addAnswers() {
        CustomerQuestionsListModel question = questionList.get(surveyPage).get(questionPage);

        if (question.getFieldType().equals("select_one")
                || question.getFieldType().equals("select_multiple")) {

            Log.i("answered", binding.spLabel.getSelectedItem().toString() + "");
            answeredQuestions.put(questionPage, question.getOptions().get(binding.spLabel.getSelectedItemPosition()).getValue());

        } else if (question.getFieldType().equals("checkbox")) {

            int selectedId = binding.radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);
            Log.i("answered", selectedRadioButton.getText().toString() + "");
            answeredQuestions.put(questionPage, selectedRadioButton.getText().toString());

        } else if (question.getFieldType().equals("date")) {

            Log.i("answered", binding.tvDate.getText().toString() + "");
            answeredQuestions.put(questionPage, binding.tvDate.getText().toString());


        } else if (question.getFieldType().equals("time")) {

            Log.i("answered", binding.tvTime.getText().toString() + "");
            answeredQuestions.put(questionPage, binding.tvTime.getText().toString());


        } else if (question.getFieldType().equals("geopoint")) {

            Log.i("answered", binding.tvDate.getText().toString() + "");
            answeredQuestions.put(questionPage, binding.tvDate.getText().toString());


        } else if (question.getFieldType().equals("image")) {

            Log.i("answered", imagePath + "");
            answeredQuestions.put(questionPage, imagePath);
            imagePath = "";


        } else if (question.getFieldType().equals("signature")) {

            Log.i("answered", signaturePath + "");
            answeredQuestions.put(questionPage, signaturePath);
            signaturePath = "";


        } else if (question.getFieldType().equals("file")) {

            Log.i("answered", filePath + "");
            answeredQuestions.put(questionPage, filePath);
            filePath = "";


        } else {
            Log.i("answered", binding.edtAnswer.getText().toString() + "");
            answeredQuestions.put(questionPage, binding.edtAnswer.getText().toString());
        }


    }

    @SuppressLint("SetTextI18n")
    synchronized private void updateQuestionToFollowPage() {
        int pos = binding.spLabel.getSelectedItemPosition();

        CustomerQuestionToFollowModel question = questionList.get(surveyPage).get(questionPage).getOptions().get(pos).getQuestionToFollow().get(questionToFollowPage);
        binding.tvQuestionToFollow.setText(question.getQuestionText());

        int optionPageKey = questionList.get(surveyPage).get(questionPage).getOptions().get(pos).getId();

        binding.tvQuestionToFollow.setVisibility(View.VISIBLE);
        binding.rlSpinner.setVisibility(View.GONE);
        binding.rlQueToFollowSpinner.setVisibility(View.GONE);
        binding.edtAnswer.setVisibility(View.GONE);
        binding.tvDate.setVisibility(View.GONE);
        binding.tvTime.setVisibility(View.GONE);
        binding.radioGroup.setVisibility(View.GONE);
        binding.imgUser.setVisibility(View.GONE);
        binding.imgSignature.setVisibility(View.GONE);
        binding.llFile.setVisibility(View.GONE);
        binding.edtAnswer.getText().clear();
        binding.tvFileName.setText("");
        binding.tvDate.setText("");
        binding.tvTime.setText("");
        Glide.with(CreateCustomerActivity.this).load("").into(binding.imgUser);
        Glide.with(CreateCustomerActivity.this).load("").into(binding.imgSignature);


        RelativeLayout.LayoutParams lp;
        if (question.getFieldType().equals("textArea")) {
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, edtHeight);
        } else {
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        binding.edtAnswer.setLayoutParams(lp);


        if (question.getFieldType().equals("select_one") || question.getFieldType().equals("select_multiple")) {

            binding.rlQueToFollowSpinner.setVisibility(View.VISIBLE);

            spAdapter = new ArrayAdapter(CreateCustomerActivity.this, R.layout.spinner_item_view, question.getOptions());
            binding.spQueToFollow.setAdapter(spAdapter);

        } else if (question.getFieldType().equals("textfield")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT);

            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        binding.edtAnswer.setText(data.get(questionToFollowPage));
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {
                binding.edtAnswer.setText(answeredToFollowQuestions.get(questionToFollowPage));
            }

        } else if (question.getFieldType().equals("textField")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT);

            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        binding.edtAnswer.setText(data.get(questionToFollowPage));
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {
                binding.edtAnswer.setText(answeredToFollowQuestions.get(questionToFollowPage));
            }

        } else if (question.getFieldType().equals("textArea")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);

            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        binding.edtAnswer.setText(data.get(questionToFollowPage));
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {
                binding.edtAnswer.setText(answeredToFollowQuestions.get(questionToFollowPage));
            }


        } else if (question.getFieldType().equals("number")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);

            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        binding.edtAnswer.setText(data.get(questionToFollowPage));
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {
                binding.edtAnswer.setText(answeredToFollowQuestions.get(questionToFollowPage));
            }

        } else if (question.getFieldType().equals("integer")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);

            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        binding.edtAnswer.setText(data.get(questionToFollowPage));
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {
                binding.edtAnswer.setText(answeredToFollowQuestions.get(questionToFollowPage));
            }

        } else if (question.getFieldType().equals("decimal")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);

            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        binding.edtAnswer.setText(data.get(questionToFollowPage));
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {
                binding.edtAnswer.setText(answeredToFollowQuestions.get(questionToFollowPage));
            }

        } else if (question.getFieldType().equals("checkbox")) {

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

        } else if (question.getFieldType().equals("date")) {

            binding.tvDate.setVisibility(View.VISIBLE);

            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        binding.tvDate.setText(data.get(questionToFollowPage));
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {
                binding.tvDate.setText(answeredToFollowQuestions.get(questionToFollowPage));
            }


        } else if (question.getFieldType().equals("time")) {

            binding.tvTime.setVisibility(View.VISIBLE);

            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        binding.tvTime.setText(data.get(questionToFollowPage));
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {
                binding.tvTime.setText(answeredToFollowQuestions.get(questionToFollowPage));
            }

        } else if (question.getFieldType().equals("geopoint")) {

            binding.tvDate.setVisibility(View.VISIBLE);
            binding.tvDate.setText(LocationInfo.location.getLongitude() + "," + LocationInfo.location.getLatitude());

            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        binding.tvDate.setText(data.get(questionToFollowPage));
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {
                binding.tvDate.setText(answeredToFollowQuestions.get(questionToFollowPage));
            }

        } else if (question.getFieldType().equals("image")) {
            binding.imgUser.setVisibility(View.VISIBLE);
            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        Glide.with(this).load(data.get(questionToFollowPage)).into(binding.imgUser);
                        imagePath = data.get(questionToFollowPage);
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {

                Glide.with(this).load(answeredToFollowQuestions.get(questionToFollowPage)).into(binding.imgUser);
                imagePath = answeredToFollowQuestions.get(questionToFollowPage);
            }
        } else if (question.getFieldType().equals("signature")) {
            binding.imgSignature.setVisibility(View.VISIBLE);
            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        Glide.with(this).load(data.get(questionToFollowPage)).into(binding.imgSignature);
                        signaturePath = data.get(questionToFollowPage);
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {

                Glide.with(this).load(answeredToFollowQuestions.get(questionToFollowPage)).into(binding.imgSignature);
                signaturePath = answeredToFollowQuestions.get(questionToFollowPage);
            }
        } else if (question.getFieldType().equals("file")) {
            binding.llFile.setVisibility(View.VISIBLE);
            if (optionQuestions.containsKey(optionPageKey)) {
                Map<Integer, String> data = optionQuestions.get(optionPageKey);
                if (data != null) {
                    if (data.containsKey(questionToFollowPage)) {
                        binding.tvFileName.setText(data.get(questionToFollowPage) + "");
                        filePath = data.get(questionToFollowPage) + "";
                    }
                }
            } else if (answeredToFollowQuestions.containsKey(questionToFollowPage)) {
                binding.tvFileName.setText(answeredToFollowQuestions.get(questionToFollowPage) + "");
                filePath = answeredToFollowQuestions.get(questionToFollowPage) + "";
            }
        }
    }

    synchronized private void addAnswersToFollowAnswers() {

        int pos = binding.spLabel.getSelectedItemPosition();
        CustomerQuestionToFollowModel question = questionList.get(surveyPage).get(questionPage).getOptions().get(pos).getQuestionToFollow().get(questionToFollowPage);

        if (question.getFieldType().equals("select_one")
                || question.getFieldType().equals("select_multiple")) {

            Log.i("followUpAnswered", binding.spQueToFollow.getSelectedItem().toString() + "");
            //answeredToFollowQuestions.put(questionToFollowPage, binding.spQueToFollow.getSelectedItem().toString());
            answeredToFollowQuestions.put(questionToFollowPage, question.getOptions().get(binding.spQueToFollow.getSelectedItemPosition()).getValue());

        } else if (question.getFieldType().equals("checkbox")) {

            int selectedId = binding.radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);

            Log.i("followUpAnswered", selectedRadioButton.getText().toString() + "");
            answeredToFollowQuestions.put(questionToFollowPage, selectedRadioButton.getText().toString());

        } else if (question.getFieldType().equals("date")) {

            Log.i("followUpAnswered", binding.tvDate.getText().toString() + "");
            answeredToFollowQuestions.put(questionToFollowPage, binding.tvDate.getText().toString());


        } else if (question.getFieldType().equals("time")) {

            Log.i("followUpAnswered", binding.tvTime.getText().toString() + "");
            answeredToFollowQuestions.put(questionToFollowPage, binding.tvTime.getText().toString());


        } else if (question.getFieldType().equals("geopoint")) {

            Log.i("followUpAnswered", binding.tvDate.getText().toString() + "");
            answeredToFollowQuestions.put(questionToFollowPage, binding.tvDate.getText().toString());


        } else if (question.getFieldType().equals("image")) {

            Log.i("followUpAnswered", imagePath + "");
            answeredToFollowQuestions.put(questionToFollowPage, imagePath);
            imagePath = "";

        } else if (question.getFieldType().equals("signature")) {

            Log.i("followUpAnswered", signaturePath + "");
            answeredToFollowQuestions.put(questionToFollowPage, signaturePath);
            signaturePath = "";

        } else if (question.getFieldType().equals("file")) {

            Log.i("followUpAnswered", filePath + "");
            answeredToFollowQuestions.put(questionToFollowPage, filePath);
            filePath = "";

        } else {
            Log.i("followUpAnswered", binding.edtAnswer.getText().toString() + "");
            answeredToFollowQuestions.put(questionToFollowPage, binding.edtAnswer.getText().toString());
        }


    }

    private boolean validateAnswer() {

        CustomerQuestionsListModel question = questionList.get(surveyPage).get(questionPage);

        if (question.getFieldType().equals("text") || question.getFieldType().equals("textfield")
                || question.getFieldType().equals("textArea") || question.getFieldType().equals("decimal")
                || question.getFieldType().equals("number") || question.getFieldType().equals("integer")
                || question.getFieldType().equals("textField")) {

            if (isEmpty(binding.edtAnswer.getText().toString())) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (binding.edtAnswer.getText().toString().length() < question.getMin()) {
                Toast.makeText(this, "Please enter minimum " + question.getMin() + " characters", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(question.getRegularExpression())) {
                return true;
            } else if (!binding.edtAnswer.getText().toString().matches(question.getRegularExpression())) {
                Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                return false;
            }

        } else if (question.getFieldType().equals("checkbox")
                && binding.radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select one option", Toast.LENGTH_SHORT).show();
            return false;
        } else if (question.getFieldType().equals("date")
                && isEmpty(binding.tvDate.getText().toString())) {
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (question.getFieldType().equals("time")
                && isEmpty(binding.tvTime.getText().toString())) {
            Toast.makeText(this, "Please enter time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (question.getFieldType().equals("geopoint")
                && isEmpty(binding.tvDate.getText().toString())) {
            Toast.makeText(this, "Please enter your location coordinates", Toast.LENGTH_SHORT).show();
            return false;
        } else if (question.getFieldType().equals("image")
                && isEmpty(imagePath)) {
            Toast.makeText(this, "Please add image", Toast.LENGTH_SHORT).show();
            return false;
        } else if (question.getFieldType().equals("signature")
                && isEmpty(signaturePath)) {
            Toast.makeText(this, "Please add signature", Toast.LENGTH_SHORT).show();
            return false;
        } else if (question.getFieldType().equals("file")
                && isEmpty(filePath)) {
            Toast.makeText(this, "Please attach file", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private boolean validateQueToFollowAnswer(List<CustomerQuestionToFollowModel> questionToFollowList) {
        if (questionToFollowList.get(questionToFollowPage).getFieldType().equals("text") || questionToFollowList.get(questionToFollowPage).getFieldType().equals("textfield")
                || questionToFollowList.get(questionToFollowPage).getFieldType().equals("textArea") || questionToFollowList.get(questionToFollowPage).getFieldType().equals("decimal")
                || questionToFollowList.get(questionToFollowPage).getFieldType().equals("number") || questionToFollowList.get(questionToFollowPage).getFieldType().equals("integer")
                || questionToFollowList.get(questionToFollowPage).getFieldType().equals("textField")) {

            if (isEmpty(binding.edtAnswer.getText().toString())) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(questionToFollowList.get(questionToFollowPage).getRegularExpression())) {
                return true;
            } else {
                String answer = binding.edtAnswer.getText().toString();
                if (answer.matches(questionToFollowList.get(questionToFollowPage).getRegularExpression())) {
                    return true;
                } else {
                    Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        } else if (questionToFollowList.get(questionToFollowPage).getFieldType().equals("checkbox")
                && binding.radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select one option", Toast.LENGTH_SHORT).show();
            return false;
        } else if (questionToFollowList.get(questionToFollowPage).getFieldType().equals("date")
                && isEmpty(binding.tvDate.getText().toString())) {
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (questionToFollowList.get(questionToFollowPage).getFieldType().equals("time")
                && isEmpty(binding.tvTime.getText().toString())) {
            Toast.makeText(this, "Please enter time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (questionToFollowList.get(questionToFollowPage).getFieldType().equals("geopoint")
                && isEmpty(binding.tvDate.getText().toString())) {
            Toast.makeText(this, "Please enter your coordinates", Toast.LENGTH_SHORT).show();
            return false;
        } else if (questionToFollowList.get(questionToFollowPage).getFieldType().equals("image")
                && isEmpty(imagePath)) {
            Toast.makeText(this, "Please add image", Toast.LENGTH_SHORT).show();
            return false;
        } else if (questionToFollowList.get(questionToFollowPage).getFieldType().equals("signature")
                && isEmpty(signaturePath)) {
            Toast.makeText(this, "Please add signature", Toast.LENGTH_SHORT).show();
            return false;
        } else if (questionToFollowList.get(questionToFollowPage).getFieldType().equals("file")
                && isEmpty(filePath)) {
            Toast.makeText(this, "Please attach file", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void chooseFile() {

        chooserDialog = new ChooserDialog(this, R.style.FileChooserStyle);

        chooserDialog
                .withResources(
                        R.string.fileChooser,
                        R.string.title_choose, R.string.dialog_cancel)
                .withOptionResources(R.string.option_create_folder, R.string.options_delete,
                        R.string.new_folder_cancel, R.string.new_folder_ok)
                .disableTitle(false)
                .enableOptions(false)
                .titleFollowsDir(false)
                .displayPath(true)
                .enableDpad(true);

        chooserDialog.withFilter(false, true);
        chooserDialog.withChosenListener((dir, dirFile) -> {
            Toast.makeText(this, (dirFile.isDirectory() ? "FOLDER: " : "FILE: ") + dir, Toast.LENGTH_SHORT).show();
            /*if (dirFile.isFile()) _iv.setImageBitmap(decodeFile(dirFile));*/
            filePath = dirFile.getPath();
        });
        chooserDialog.withOnBackPressedListener(dialog -> chooserDialog.goBack());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ACCESS_CAMERA_GALLERY) {
                imagePath = data.getStringExtra(PICTURE_PATH);
                Glide.with(this).load(imagePath).into(binding.imgUser);
            } else {
                signaturePath = data.getStringExtra(SIGNATURE_PATH);
                Glide.with(this).load(signaturePath).into(binding.imgSignature);
            }
        }
    }

    public void selectImage() {
        if (isPermissionGranted(this)) {
            if (isSignatureSelection) {
                Intent gallery_Intent = new Intent(getApplicationContext(), SignatureActivity.class);
                startActivityForResult(gallery_Intent, ACCESS_SIGNATURE);
            } else {
                Intent gallery_Intent = new Intent(getApplicationContext(), AccessMediaUtil.class);
                startActivityForResult(gallery_Intent, ACCESS_CAMERA_GALLERY);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (!isPermissionGranted(this)) {
                Toast.makeText(this, "You have to allow all the permissions to access content from camera and gallery", Toast.LENGTH_SHORT).show();
            } else {
                selectImage();
            }
        }
    }

    @Override
    protected void onDestroy() {
        manageResumeForm();
        super.onDestroy();
    }

    synchronized private void manageResumeForm() {
        if (!isFormSubmitted) {
            if (!surveyQuestions.isEmpty()) {
                CustomerFormResumeEntity entity = new CustomerFormResumeEntity();
                entity.setMainId(0);
                entity.setName(binding.edtFullName.getText().toString() + "");
                entity.setNumber(binding.edtMobileNo.getText().toString() + "");
                entity.setIdNumber(binding.edtDocumentNo.getText().toString() + "");
                entity.setBirthDate(binding.tvBirthDate.getText().toString() + "");
                entity.setAge(binding.tvAge.getText().toString() + "");
                entity.setExist(binding.rbYes.isChecked() ? "yes" : "no");
                entity.setSurveyQuestions(new Gson().toJson(surveyQuestions));
                entity.setOptionQuestions(optionQuestions.isEmpty() ? "" : new Gson().toJson(optionQuestions));
                disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                        .saveCustomerFormResume(entity))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                //Toast.makeText(CreateCustomerActivity.this, "Customer Resume Saved to local", Toast.LENGTH_SHORT).show();
                                finish();
                                Log.i("customerForm", "Customer Resume form saved to local");
                                disposable.clear();


                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("customerFormResume", "Error  ==  " + e.getMessage());
                                Toast.makeText(CreateCustomerActivity.this, "Error  ==  " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                disposable.clear();
                            }
                        }));
            }
        } else {
            if (customerFormResumeEntity != null) {
                disposable.add(Completable.fromAction(() -> localDatabase.getDAO().removeCustomerFormResume(customerFormResumeEntity))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {

                            @Override
                            public void onComplete() {
                                Log.i("customerFormResume", "formDeleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("customerFormResume", "Error = > " + e.getMessage());
                                disposable.clear();
                            }
                        }));
            }
        }
    }

}