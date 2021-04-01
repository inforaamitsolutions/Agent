package com.codeclinic.agent.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codeclinic.agent.model.customer.FetchCustomerFormBodyModel;
import com.codeclinic.agent.model.lead.FetchLeadFormBodyModel;

import io.reactivex.Flowable;

@Dao
public interface DAO {

    /*****************Customer Survey Form Table Queries*************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCustomerSurveyForm(FetchCustomerFormBodyModel entity);

    @Query("select * from CustomerFormBody")
    Flowable<FetchCustomerFormBodyModel> getCustomerSurveyForm();

    @Delete
    void removeCustomerForm(FetchCustomerFormBodyModel entity);

    /*****************Lead Survey Form Table Queries*************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLeadSurveyForm(FetchLeadFormBodyModel entity);

    @Query("select * from LeadFormBody")
    Flowable<FetchLeadFormBodyModel> getLeadSurveyFormList();

    @Delete
    void removeLeadForm(FetchLeadFormBodyModel entity);


}
