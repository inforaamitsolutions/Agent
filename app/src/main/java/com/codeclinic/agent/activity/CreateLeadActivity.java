package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
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
import com.codeclinic.agent.database.lead.LeadFinalFormEntity;
import com.codeclinic.agent.database.lead.LeadFormResumeEntity;
import com.codeclinic.agent.databinding.ActivityCreateLeadBinding;
import com.codeclinic.agent.model.lead.LeadOptionsListModel;
import com.codeclinic.agent.model.lead.LeadQuestionToFollowModel;
import com.codeclinic.agent.model.lead.LeadQuestionsListModel;
import com.codeclinic.agent.model.lead.LeadSubmitFormModel;
import com.codeclinic.agent.model.lead.LeadSurveyDefinitionPageModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.AccessMediaUtil;
import com.codeclinic.agent.utils.Connection_Detector;
import com.codeclinic.agent.utils.LocationInfo;
import com.codeclinic.agent.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import static com.codeclinic.agent.utils.CommonMethods.datePicker;
import static com.codeclinic.agent.utils.CommonMethods.isPermissionGranted;
import static com.codeclinic.agent.utils.Constants.ACCESS_CAMERA_GALLERY;
import static com.codeclinic.agent.utils.Constants.ACCESS_SIGNATURE;
import static com.codeclinic.agent.utils.Constants.PICTURE_PATH;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class CreateLeadActivity extends AppCompatActivity {

    ActivityCreateLeadBinding binding;

    CompositeDisposable disposable = new CompositeDisposable();
    LeadFormResumeEntity leadFormResumeEntity;

    String imagePath, signaturePath;
    int surveyPage = 0, questionPage = 0, questionToFollowPage = -1, radioButtonTextSize, edtHeight;
    ArrayAdapter spAdapter;

    List<LeadSurveyDefinitionPageModel> surveyPagesList = new ArrayList<>();
    Map<Integer, List<LeadQuestionsListModel>> questionList = new HashMap<>();
    List<FormSummaryModel> summaryList = new ArrayList<>();

    HashMap<Integer, Map<Integer, String>> surveyQuestions = new LinkedHashMap<>();
    Map<Integer, String> answeredQuestions = new HashMap<>();
    HashMap<Integer, Map<Integer, String>> optionQuestions = new LinkedHashMap<>();

    Map<Integer, String> answeredToFollowQuestions = new HashMap<>();
    LinearLayout.LayoutParams layoutParams;

    boolean isSubmitForm = false, isFormSubmitted = false, isSignatureSelection = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_lead);

        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.txtHeading.setText("Create Lead");


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

        binding.btnPrevious.setOnClickListener(v -> {
            questionToFollowPage = -1;
            if (binding.llQuestions.getVisibility() == View.GONE) {
                binding.llQuestions.setVisibility(View.VISIBLE);
                binding.linearUserDetail.setVisibility(View.GONE);
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

        LocationInfo.getLastLocation(this, null);

        binding.btnNext1.setOnClickListener(view -> {
            if ((questionList.get(surveyPage).get(questionPage).getFieldType().equals("select_one")
                    || questionList.get(surveyPage).get(questionPage).getFieldType().equals("select_multiple"))) {
                List<LeadQuestionToFollowModel> questionToFollowList =
                        questionList.get(surveyPage).get(questionPage).getOptions().get(binding.spLabel.getSelectedItemPosition()).getQuestionToFollow();

                if (questionToFollowList != null && binding.llQuestions.getVisibility() == View.VISIBLE) {
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

        binding.tvTime.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(CreateLeadActivity.this, (timePicker, selectedHour, selectedMinute) -> {
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


        //callCustomerForm();
        getLeadFormResume();
        //getSurveyForm();

    }

    private boolean checkFormSavedInDB() {
        return localDatabase.getDAO().isLeadFormResumeExists();
    }

    @SuppressLint("SetTextI18n")
    private void getLeadFormResume() {
        disposable.add(Single.fromCallable(this::checkFormSavedInDB)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((isExist) -> {
                    if (isExist) {
                        askToContinueDraftForm();
                    } else {
                        getSurveyForm();
                    }
                }));
    }

    private void askToContinueDraftForm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("We found an old form which was not submitted yet exist, Do you want to continue with the same form?");
        builder.setPositiveButton("Yes", (dialog, id) -> {
            extractLeadFromLocal();
        });
        builder.setNegativeButton("No", (dialog, id) -> {
            dialog.dismiss();

        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void extractLeadFromLocal() {
        disposable.add(localDatabase.getDAO().getLeadFormResume()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(form -> {
                            if (form != null) {
                                leadFormResumeEntity = form;
                                surveyQuestions = new Gson().fromJson(form.getSurveyQuestions(), new TypeToken<HashMap<Integer, Map<Integer, String>>>() {
                                }.getType());
                                if (!isEmpty(form.getOptionQuestions())) {
                                    optionQuestions = new Gson().fromJson(form.getOptionQuestions(), new TypeToken<HashMap<Integer, Map<Integer, String>>>() {
                                    }.getType());
                                }
                                Log.i("leadFormResume", "Data stored is " + new Gson().toJson(surveyQuestions));
                            }

                            getSurveyForm();
                        },
                        throwable -> {
                            if (throwable.getMessage() != null) {
                                Log.i("leadFormResume", "Error == " + throwable.getMessage());
                            }
                            getSurveyForm();
                        }
                )
        );
    }

    private void getSurveyForm() {
        disposable.add(localDatabase.getDAO().getLeadSurveyFormList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {

                            surveyPagesList = entity.getSurveyDefinitionPages();
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

    private void saveLeadFormToLocal(String request) {
        LeadFinalFormEntity leadFinalFormEntity = new LeadFinalFormEntity();
        leadFinalFormEntity.setMainId(0);
        leadFinalFormEntity.setRequest(request);
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                .saveLeadFinalForm(leadFinalFormEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(CreateLeadActivity.this, "Lead Saved to local", Toast.LENGTH_SHORT).show();
                        Log.i("leadForm", "lead final form saved to local");
                        isFormSubmitted = true;
                        manageResumeForm();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("leadForm", "Error  ==  " + e.getMessage());
                        Toast.makeText(CreateLeadActivity.this, "Error  ==  " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    manageResumeForm();

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
                        //submitForm();
                        manageResumeForm();
                        renderSummary();
                    }
                }
            }
        } else {
            //submitForm();
            renderSummary();
        }
    }

    private void submitForm() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerRole", "MYMOBI_LEAD");
            jsonObject.put("firstName", binding.edtFirstName.getText().toString());
            jsonObject.put("lastName", binding.edtLastName.getText().toString());
            jsonObject.put("middleName", binding.edtMiddleName.getText().toString());
            jsonObject.put("staffId", sessionManager.getUserDetails().get(SessionManager.UserID));
            jsonObject.put("status", "COMPLETED");
            jsonObject.put("surveyName", "lead_registration_form");

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

                    if ((surveyPagesList.get(i).getQuestions().get(entry.getKey()).getFieldType().equals("select_one")
                            || surveyPagesList.get(i).getQuestions().get(entry.getKey()).getFieldType().equals("select_multiple"))) {
                        List<LeadOptionsListModel> options = surveyPagesList.get(i).getQuestions().get(entry.getKey()).getOptions();
                        for (int j = 0; j < options.size(); j++) {

                            if (options.get(j).getQuestionToFollow() != null && value.equals(options.get(j).getValue())) {

                                if (options.get(j).getQuestionToFollow().size() != 0) {

                                    if (optionQuestions.containsKey(options.get(j).getId())) {
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
            disposable.add(RestClass.getClient().LEAD_SUBMIT_FORM_MODEL_SINGLE_CALL(sessionManager.getTokenDetails().get(SessionManager.AccessToken)
                    , jsonObject.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<LeadSubmitFormModel>() {
                        @Override
                        public void onSuccess(@NonNull LeadSubmitFormModel response) {
                            binding.loadingView.loader.setVisibility(View.GONE);
                            if (response.getSuccessStatus().equals("success")) {
                                isFormSubmitted = true;
                                manageResumeForm();
                                finish();
                            }
                            Toast.makeText(CreateLeadActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            binding.loadingView.loader.setVisibility(View.GONE);
                            Toast.makeText(CreateLeadActivity.this, "Server Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }));
        } else {
            binding.loadingView.loader.setVisibility(View.GONE);
            saveLeadFormToLocal(jsonObject.toString());
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
                    List<LeadOptionsListModel> options = surveyPagesList.get(i).getQuestions().get(entry.getKey()).getOptions();

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

    @SuppressLint("SetTextI18n")
    private void updatePage() {

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
        binding.tvQuestionToFollow.setVisibility(View.GONE);
        binding.tvQuestionToFollow.setText("");
        binding.tvDate.setText("");
        binding.tvTime.setText("");
        binding.edtAnswer.getText().clear();

        LeadQuestionsListModel question = questionList.get(surveyPage).get(questionPage);
        Glide.with(CreateLeadActivity.this).load("").into(binding.imgUser);
        Glide.with(CreateLeadActivity.this).load("").into(binding.imgSignature);

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


            spAdapter = new ArrayAdapter(CreateLeadActivity.this, R.layout.spinner_item_view, question.getOptions());
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
            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        Glide.with(CreateLeadActivity.this).load(data.get(questionPage)).into(binding.imgUser);
                        imagePath = data.get(questionPage);
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                Glide.with(CreateLeadActivity.this).load(answeredQuestions.get(questionPage)).into(binding.imgUser);
                imagePath = answeredQuestions.get(questionPage);
            }
        } else if (question.getFieldType().equals("signature")) {

            binding.imgSignature.setVisibility(View.VISIBLE);

            if (surveyQuestions.containsKey(surveyPage)) {
                Map<Integer, String> data = surveyQuestions.get(surveyPage);
                if (data != null) {
                    if (data.containsKey(questionPage)) {
                        Glide.with(CreateLeadActivity.this).load(data.get(questionPage)).into(binding.imgSignature);
                        signaturePath = data.get(questionPage);
                    }
                }
            } else if (answeredQuestions.containsKey(questionPage)) {
                Glide.with(CreateLeadActivity.this).load(answeredQuestions.get(questionPage)).into(binding.imgSignature);
                signaturePath = answeredQuestions.get(questionPage);
            }
        }
    }

    private void addAnswers() {
        LeadQuestionsListModel question = questionList.get(surveyPage).get(questionPage);
        if (question.getFieldType().equals("select_one")
                || question.getFieldType().equals("select_multiple")) {

            Log.i("answered", binding.spLabel.getSelectedItem().toString() + "");
            //answeredQuestions.put(questionPage, binding.spLabel.getSelectedItem().toString());
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


        } else {
            Log.i("answered", binding.edtAnswer.getText().toString() + "");
            answeredQuestions.put(questionPage, binding.edtAnswer.getText().toString());
        }


    }

    @SuppressLint("SetTextI18n")
    private void updateQuestionToFollowPage() {
        int pos = binding.spLabel.getSelectedItemPosition();
        LeadQuestionToFollowModel question = questionList.get(surveyPage).get(questionPage).getOptions().get(pos).getQuestionToFollow().get(questionToFollowPage);

        int optionPageKey = questionList.get(surveyPage).get(questionPage).getOptions().get(pos).getId();

        binding.tvQuestionToFollow.setText(question.getQuestionText());
        binding.tvQuestionToFollow.setVisibility(View.VISIBLE);
        binding.edtAnswer.getText().clear();
        binding.tvDate.setText("");
        binding.tvTime.setText("");
        Glide.with(CreateLeadActivity.this).load("").into(binding.imgUser);
        Glide.with(CreateLeadActivity.this).load("").into(binding.imgSignature);

        binding.rlSpinner.setVisibility(View.GONE);
        binding.rlQueToFollowSpinner.setVisibility(View.GONE);
        binding.edtAnswer.setVisibility(View.GONE);
        binding.tvDate.setVisibility(View.GONE);
        binding.tvTime.setVisibility(View.GONE);
        binding.radioGroup.setVisibility(View.GONE);
        binding.imgUser.setVisibility(View.GONE);

        RelativeLayout.LayoutParams lp;
        if (question.getFieldType().equals("textArea")) {
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, edtHeight);
        } else {
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        binding.edtAnswer.setLayoutParams(lp);

        if (question.getFieldType().equals("select_one") || question.getFieldType().equals("select_multiple")) {

            binding.rlQueToFollowSpinner.setVisibility(View.VISIBLE);

            spAdapter = new ArrayAdapter(CreateLeadActivity.this, R.layout.spinner_item_view, question.getOptions());
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
        }
    }

    private void addAnswersToFollowAnswers() {

        int pos = binding.spLabel.getSelectedItemPosition();
        LeadQuestionToFollowModel question = questionList.get(surveyPage).get(questionPage).getOptions().get(pos).getQuestionToFollow().get(questionToFollowPage);

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

        } else {
            Log.i("followUpAnswered", binding.edtAnswer.getText().toString() + "");
            answeredToFollowQuestions.put(questionToFollowPage, binding.edtAnswer.getText().toString());
        }


    }

    private boolean validateAnswer() {
        if (binding.llQuestions.getVisibility() == View.VISIBLE) {

            LeadQuestionsListModel question = questionList.get(surveyPage).get(questionPage);


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

    private boolean validateQueToFollowAnswer(List<LeadQuestionToFollowModel> questionToFollow) {
        LeadQuestionToFollowModel questionToFollowList = questionToFollow.get(questionToFollowPage);
        if (binding.llQuestions.getVisibility() == View.VISIBLE) {

            if (questionToFollowList.getFieldType().equals("text") || questionToFollowList.getFieldType().equals("textfield")
                    || questionToFollowList.getFieldType().equals("textArea") || questionToFollowList.getFieldType().equals("decimal")
                    || questionToFollowList.getFieldType().equals("number") || questionToFollowList.getFieldType().equals("integer")
                    || questionToFollowList.getFieldType().equals("textField")) {

                if (isEmpty(binding.edtAnswer.getText().toString())) {
                    Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (isEmpty(questionToFollowList.getRegularExpression())) {
                    return true;
                } else {
                    String answer = binding.edtAnswer.getText().toString();
                    if (answer.matches(questionToFollowList.getRegularExpression())) {
                        return true;
                    } else {
                        Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }

            } else if (questionToFollowList.getFieldType().equals("checkbox")
                    && binding.radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select one option", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionToFollowList.getFieldType().equals("date")
                    && isEmpty(binding.tvDate.getText().toString())) {
                Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionToFollowList.getFieldType().equals("time")
                    && isEmpty(binding.tvTime.getText().toString())) {
                Toast.makeText(this, "Please enter time", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionToFollowList.getFieldType().equals("geopoint")
                    && isEmpty(binding.tvDate.getText().toString())) {
                Toast.makeText(this, "Please enter your coordinates", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionToFollowList.getFieldType().equals("image")
                    && isEmpty(imagePath)) {
                Toast.makeText(this, "Please add image", Toast.LENGTH_SHORT).show();
                return false;
            } else if (questionToFollowList.getFieldType().equals("signature")
                    && isEmpty(signaturePath)) {
                Toast.makeText(this, "Please add signature", Toast.LENGTH_SHORT).show();
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
        //manageResumeForm();
        super.onDestroy();
    }

    synchronized private void manageResumeForm() {
        if (!isFormSubmitted) {
            if (!surveyQuestions.isEmpty()) {
                LeadFormResumeEntity entity = new LeadFormResumeEntity();
                entity.setMainId(0);
                entity.setSurveyQuestions(new Gson().toJson(surveyQuestions));
                entity.setOptionQuestions(optionQuestions.isEmpty() ? "" : new Gson().toJson(optionQuestions));
                disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                        .saveLeadFormResume(entity))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                //Toast.makeText(CreateCustomerActivity.this, "Customer Resume Saved to local", Toast.LENGTH_SHORT).show();
                                Log.i("leadForm", "Customer Resume form saved to local");
                                //disposable.clear();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("leadFormResume", "Error  ==  " + e.getMessage());
                                Toast.makeText(CreateLeadActivity.this, "Error  ==  " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                //disposable.clear();
                            }
                        }));
            }
        } else {
            removeLeadForm();
        }
    }

    private void removeLeadForm() {
        disposable.add(localDatabase.getDAO().getLeadFormResume()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::removeFormFromLocal,
                        throwable -> {
                            if (throwable.getMessage() != null) {
                                Log.i("leadFormResume", "Error == " + throwable.getMessage());
                            }
                        }
                )
        );
    }

    private void removeFormFromLocal(LeadFormResumeEntity entity) {
        if (entity != null) {
            disposable.add(Completable.fromAction(() -> localDatabase.getDAO().removeLeadFormResume(entity))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableCompletableObserver() {

                        @Override
                        public void onComplete() {
                            Log.i("leadFormResume", "formDeleted");
                            disposable.clear();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("leadFormResume", "Error = > " + e.getMessage());
                            disposable.clear();
                        }
                    }));
        }
    }

}