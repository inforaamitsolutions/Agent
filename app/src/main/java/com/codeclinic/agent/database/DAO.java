package com.codeclinic.agent.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codeclinic.agent.model.customer.CustomerSurveyDefinitionPageModel;
import com.codeclinic.agent.model.lead.LeadSurveyDefinitionPageModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DAO {

    /*****************Customer Survey Form Table Queries*************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCustomerSurveyForm(List<CustomerSurveyDefinitionPageModel> list);

    @Query("select * from CustomerSurveyPages")
    Flowable<List<CustomerSurveyDefinitionPageModel>> getCustomerSurveyFormList();

    /*****************Lead Survey Form Table Queries*************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLeadSurveyForm(List<LeadSurveyDefinitionPageModel> list);

    @Query("select * from LeadSurveyPages")
    Flowable<List<LeadSurveyDefinitionPageModel>> getLeadSurveyFormList();


}
