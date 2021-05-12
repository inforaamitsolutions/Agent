package com.codeclinic.agent.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codeclinic.agent.model.businesDataUpdate.FetchBusinessDataFormBodyModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormBodyModel;
import com.codeclinic.agent.model.customer.SaveCustomerFormEntries;
import com.codeclinic.agent.model.lead.FetchLeadFormBodyModel;

import io.reactivex.Flowable;

@Dao
public interface DAO {

    /*****************Customer Survey Form Table Queries*************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCustomerSurveyForm(FetchCustomerFormBodyModel entity);

    @Query("select * from CustomerFormBody")
    Flowable<FetchCustomerFormBodyModel> getCustomerSurveyForm();

    @Query("SELECT EXISTS(SELECT * FROM CustomerFormBody)")
    boolean isFormExists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCustomerEntries(SaveCustomerFormEntries entity);

    @Insert
    void saveCustomerFinalForm(CustomerFinalFormEntity entity);

    @Query("select * from CustomerForm")
    Flowable<CustomerFinalFormEntity> getCustomerFinalForm();

    @Delete
    void removeCustomerForm(FetchCustomerFormBodyModel entity);

    @Query("DELETE FROM CustomerForm")
    void deleteAllCustomerFinalForms();

    /*****************Lead Survey Form Table Queries*************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLeadSurveyForm(FetchLeadFormBodyModel entity);

    @Query("select * from LeadFormBody")
    Flowable<FetchLeadFormBodyModel> getLeadSurveyFormList();

    @Insert
    void saveLeadFinalForm(LeadFinalFormEntity entity);

    @Query("select * from LeadForm")
    Flowable<LeadFinalFormEntity> getLeadFinalForm();

    @Delete
    void removeLeadForm(FetchLeadFormBodyModel entity);

    @Query("DELETE FROM LeadForm")
    void deleteAllLeadFinalForms();

    /*****************Lead Survey Form Table Queries*************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addBusinessDataSurveyForm(FetchBusinessDataFormBodyModel entity);

    @Query("select * from BusinessDataFormBody")
    Flowable<FetchBusinessDataFormBodyModel> getBusinessDataSurveyFormList();

    @Insert
    void saveBusinessDataFinalForm(BusinessDataFinalFormEntity entity);

    @Query("select * from BusinessDataForm")
    Flowable<BusinessDataFinalFormEntity> getBusinessDataFinalForm();

    @Delete
    void removeBusinessDataForm(FetchBusinessDataFormBodyModel entity);

    @Query("DELETE FROM BusinessDataForm")
    void deleteAllBusinessDataFinalForms();


}
