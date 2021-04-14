package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityCreateCustomerBinding;
import com.codeclinic.agent.model.customer.CustomerOptionsListModel;
import com.codeclinic.agent.model.customer.CustomerQuestionToFollowModel;
import com.codeclinic.agent.model.customer.CustomerQuestionsListModel;
import com.codeclinic.agent.model.customer.CustomerSubmitFormModel;
import com.codeclinic.agent.model.customer.CustomerSurveyDefinitionPageModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.AccessMediaUtil;
import com.codeclinic.agent.utils.LocationInfo;
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
import static com.codeclinic.agent.utils.CommonMethods.isPermissionGranted;
import static com.codeclinic.agent.utils.Constants.ACCESS_CAMERA_GALLERY;
import static com.codeclinic.agent.utils.Constants.PICTURE_PATH;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class CreateCustomerActivity extends AppCompatActivity {
    ActivityCreateCustomerBinding binding;

    CompositeDisposable disposable = new CompositeDisposable();

    String imagePath;
    int surveyPage = 0, questionPage = 0, questionToFollowPage = 0, radioButtonTextSize;
    ArrayAdapter spAdapter;

    List<CustomerSurveyDefinitionPageModel> surveyPagesList = new ArrayList<>();
    Map<Integer, List<CustomerQuestionsListModel>> questionList = new HashMap<>();

    Map<Integer, Map<Integer, String>> surveyQuestions = new HashMap<>();
    Map<Integer, String> answeredQuestions = new HashMap<>();
    Map<Integer, Map<Integer, String>> optionQuestions = new HashMap<>();
    Map<Integer, String> answeredToFollowQuestions = new HashMap<>();
    LinearLayout.LayoutParams layoutParams;

    boolean isSubmitForm = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_customer);
        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.txtHeading.setText("Create Customer");

        LocationInfo.getLastLocation(this, null);

        binding.headerLayout.imgBack.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.imgUser.setOnClickListener(v -> {
            selectImage();
        });

        binding.btnPrevious.setOnClickListener(v -> {
            if (binding.llQuestions.getVisibility() == View.GONE) {
                binding.llQuestions.setVisibility(View.VISIBLE);
                binding.linearUserDetail.setVisibility(View.GONE);
            } else {

                if (questionPage > 0) {
                    questionPage--;
                } else if (surveyPage > 0) {
                    surveyPage--;
                    questionPage = surveyPagesList.get(surveyPage).getQuestions().size() - 1;
                }

                updatePage();
            }
        });

        binding.btnNext1.setOnClickListener(view -> {
            if ((questionList.get(surveyPage).get(questionPage).getFieldType().equals("select_one")
                    || questionList.get(surveyPage).get(questionPage).getFieldType().equals("select_multiple"))) {
                CustomerQuestionToFollowModel questionToFollowList =
                        questionList.get(surveyPage).get(questionPage).getOptions().get(binding.spLabel.getSelectedItemPosition()).getQuestionToFollow();

                if (questionToFollowList != null) {
                    if (questionToFollowPage == 0) {
                        questionToFollowPage++;
                        updateQuestionToFollowPage();
                    } else if (validateQueToFollowAnswer(questionToFollowList)) {
                        addAnswersToFollowAnswers();
                        optionQuestions.put(questionPage, answeredToFollowQuestions);
                        Log.i("optionsQuestions", new Gson().toJson(optionQuestions));
                        answeredToFollowQuestions = new HashMap<>();
                        questionToFollowPage = 0;
                        moveToNextQuestion();
                    }
                } else {
                    moveToNextQuestion();
                }
            } else {
                moveToNextQuestion();
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
        disposable.add(localDatabase.getDAO().getCustomerSurveyForm()
                .subscribeOn(Schedulers.computation())
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

    private void submitForm() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerRole", "MYMOBI_INDIVIDUAL_CUSTOMER");
            jsonObject.put("firstName", binding.edtFirstName.getText().toString());
            jsonObject.put("lastName", binding.edtLastName.getText().toString());
            jsonObject.put("middleName", binding.edtMiddleName.getText().toString());
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
                            if (value.equals(options.get(j).getLabel())) {
                                if (options.get(j).getQuestionToFollow() != null) {
                                    JSONObject jObject = new JSONObject();
                                    jObject.put("fieldName", options.get(j).getQuestionToFollow().getFieldName());
                                    Map<Integer, String> answer = optionQuestions.get(entry.getKey());
                                    jObject.put("responseText", answer.get(0));
                                    jsonArray.put(jObject);
                                    break;
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

    private void moveToNextQuestion() {
        if (!isSubmitForm) {
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
                        isSubmitForm = true;
                        submitForm();
                    }
                }
            }
        } else {
            submitForm();
        }
    }

    @SuppressLint("SetTextI18n")
    private void updatePage() {

        binding.tvTitle.setText(surveyPagesList.get(surveyPage).getTitle());
        binding.tvQuestion.setText(questionList.get(surveyPage).get(questionPage).getQuestionText());

        binding.tvQuestionToFollow.setVisibility(View.GONE);
        binding.rlQueToFollowSpinner.setVisibility(View.GONE);
        binding.rlSpinner.setVisibility(View.GONE);
        binding.edtAnswer.setVisibility(View.GONE);
        binding.tvDate.setVisibility(View.GONE);
        binding.radioGroup.setVisibility(View.GONE);
        binding.imgUser.setVisibility(View.GONE);
        binding.tvQuestionToFollow.setVisibility(View.GONE);
        binding.tvQuestionToFollow.setText("");

        if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("select_one") || questionList.get(surveyPage).get(questionPage).getFieldType().equals("select_multiple")) {

            binding.tvQuestionToFollow.setVisibility(View.VISIBLE);
            binding.rlSpinner.setVisibility(View.VISIBLE);


            spAdapter = new ArrayAdapter(CreateCustomerActivity.this, R.layout.spinner_item_view, questionList.get(surveyPage).get(questionPage).getOptions());
            binding.spLabel.setAdapter(spAdapter);

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("textfield")) {

            binding.edtAnswer.getText().clear();
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

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("textArea")) {

            binding.edtAnswer.getText().clear();

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

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("number")) {

            binding.edtAnswer.getText().clear();


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

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("decimal")) {

            binding.edtAnswer.getText().clear();


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

        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("checkbox")) {


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
        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("geopoint")) {

            binding.tvDate.setText("");
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
        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("image")) {


            binding.radioGroup.setVisibility(View.GONE);
            binding.imgUser.setVisibility(View.VISIBLE);

            Glide.with(CreateCustomerActivity.this).load("").into(binding.imgUser);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        Glide.with(CreateCustomerActivity.this).load(data.get(questionPage)).into(binding.imgUser);
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                Glide.with(CreateCustomerActivity.this).load(answeredQuestions.get(questionPage)).into(binding.imgUser);
            }
        }
    }

    private void addAnswers() {
        if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("select_one")
                || questionList.get(surveyPage).get(questionPage).getFieldType().equals("select_multiple")) {

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


        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("geopoint")) {

            Log.i("answered", binding.tvDate.getText().toString() + "");
            answeredQuestions.put(questionPage, binding.tvDate.getText().toString());


        } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("image")) {

            Log.i("answered", imagePath + "");
            answeredQuestions.put(questionPage, imagePath);
            imagePath = "";


        } else {
            Log.i("answered", binding.edtAnswer.getText().toString() + "");
            answeredQuestions.put(questionPage, binding.edtAnswer.getText().toString());
        }


    }

    @SuppressLint("SetTextI18n")
    private void updateQuestionToFollowPage() {
        int pos = binding.spLabel.getSelectedItemPosition();
        CustomerQuestionToFollowModel question = questionList.get(surveyPage).get(questionPage).getOptions().get(pos).getQuestionToFollow();
        binding.tvQuestionToFollow.setText(question.getQuestionText());
        binding.tvQuestionToFollow.setVisibility(View.VISIBLE);
        binding.edtAnswer.getText().clear();
        binding.tvDate.setText("");
        Glide.with(CreateCustomerActivity.this).load("").into(binding.imgUser);

        binding.rlSpinner.setVisibility(View.GONE);
        binding.rlQueToFollowSpinner.setVisibility(View.GONE);
        binding.edtAnswer.setVisibility(View.GONE);
        binding.tvDate.setVisibility(View.GONE);
        binding.radioGroup.setVisibility(View.GONE);
        binding.imgUser.setVisibility(View.GONE);

        if (question.getFieldType().equals("select_one") || question.getFieldType().equals("select_multiple")) {

            binding.rlQueToFollowSpinner.setVisibility(View.VISIBLE);

            spAdapter = new ArrayAdapter(CreateCustomerActivity.this, R.layout.spinner_item_view, question.getOptions());
            binding.spQueToFollow.setAdapter(spAdapter);

        } else if (question.getFieldType().equals("textfield")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);
            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT);

        } else if (question.getFieldType().equals("textArea")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);

            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);


        } else if (question.getFieldType().equals("number")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);

            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);

        } else if (question.getFieldType().equals("decimal")) {

            binding.edtAnswer.setVisibility(View.VISIBLE);

            binding.edtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

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

        } else if (question.getFieldType().equals("geopoint")) {

            binding.tvDate.setVisibility(View.VISIBLE);
            binding.tvDate.setText(LocationInfo.location.getLongitude() + "," + LocationInfo.location.getLatitude());

        } else if (question.getFieldType().equals("image")) {
            binding.imgUser.setVisibility(View.VISIBLE);
        }
    }

    private void addAnswersToFollowAnswers() {

        int pos = binding.spLabel.getSelectedItemPosition();
        CustomerQuestionToFollowModel question = questionList.get(surveyPage).get(questionPage).getOptions().get(pos).getQuestionToFollow();

        if (question.getFieldType().equals("select_one")
                || question.getFieldType().equals("select_multiple")) {

            Log.i("followUpAnswered", binding.spQueToFollow.getSelectedItem().toString() + "");
            answeredToFollowQuestions.put(0, binding.spQueToFollow.getSelectedItem().toString());

        } else if (question.getFieldType().equals("checkbox")) {

            int selectedId = binding.radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);

            Log.i("followUpAnswered", selectedRadioButton.getText().toString() + "");
            answeredToFollowQuestions.put(0, selectedRadioButton.getText().toString());

        } else if (question.getFieldType().equals("date")) {

            Log.i("followUpAnswered", binding.tvDate.getText().toString() + "");
            answeredToFollowQuestions.put(0, binding.tvDate.getText().toString());


        } else if (question.getFieldType().equals("geopoint")) {

            Log.i("followUpAnswered", binding.tvDate.getText().toString() + "");
            answeredToFollowQuestions.put(0, binding.tvDate.getText().toString());


        } else if (question.getFieldType().equals("image")) {

            Log.i("followUpAnswered", imagePath + "");
            answeredToFollowQuestions.put(0, imagePath);
            imagePath = "";

        } else {
            Log.i("followUpAnswered", binding.edtAnswer.getText().toString() + "");
            answeredToFollowQuestions.put(0, binding.edtAnswer.getText().toString());
        }


    }

    private boolean validateAnswer() {
        if (binding.llQuestions.getVisibility() == View.VISIBLE) {
            if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionList.get(surveyPage).get(questionPage).getFieldType().equals("text")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionList.get(surveyPage).get(questionPage).getFieldType().equals("textfield")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionList.get(surveyPage).get(questionPage).getFieldType().equals("textArea")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionList.get(surveyPage).get(questionPage).getFieldType().equals("decimal")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionList.get(surveyPage).get(questionPage).getFieldType().equals("number")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("checkbox")
                    && binding.radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select one option", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("date")
                    && isEmpty(binding.tvDate.getText().toString())) {
                Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("geopoint")
                    && isEmpty(binding.tvDate.getText().toString())) {
                Toast.makeText(this, "Please enter your location coordinates", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionList.get(surveyPage).get(questionPage).getFieldType().equals("image")
                    && isEmpty(imagePath)) {
                Toast.makeText(this, "Please add image", Toast.LENGTH_SHORT).show();
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

    private boolean validateQueToFollowAnswer(CustomerQuestionToFollowModel questionToFollowList) {
        if (binding.llQuestions.getVisibility() == View.VISIBLE) {
            if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionToFollowList.getFieldType().equals("text")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionToFollowList.getFieldType().equals("textfield")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionToFollowList.getFieldType().equals("textArea")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionToFollowList.getFieldType().equals("decimal")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isEmpty(binding.edtAnswer.getText().toString())
                    && questionToFollowList.getFieldType().equals("number")) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionToFollowList.getFieldType().equals("checkbox")
                    && binding.radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select one option", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionToFollowList.getFieldType().equals("date")
                    && isEmpty(binding.tvDate.getText().toString())) {
                Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionToFollowList.getFieldType().equals("geopoint")
                    && isEmpty(binding.tvDate.getText().toString())) {
                Toast.makeText(this, "Please enter your coordinates", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionToFollowList.getFieldType().equals("image")
                    && isEmpty(imagePath)) {
                Toast.makeText(this, "Please add image", Toast.LENGTH_SHORT).show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            imagePath = data.getStringExtra(PICTURE_PATH);
            Glide.with(this).load(imagePath).into(binding.imgUser);
        }
    }

    public void selectImage() {
        if (isPermissionGranted(this)) {
            Intent gallery_Intent = new Intent(getApplicationContext(), AccessMediaUtil.class);
            startActivityForResult(gallery_Intent, ACCESS_CAMERA_GALLERY);
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
        super.onDestroy();
        disposable.clear();
    }
}