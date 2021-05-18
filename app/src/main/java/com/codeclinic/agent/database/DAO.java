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
import com.codeclinic.agent.model.supplier.FetchSupplierBodyModel;

import java.util.List;

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
    Flowable<List<CustomerFinalFormEntity>> getCustomerFinalForm();

    @Delete
    void removeCustomerForm(FetchCustomerFormBodyModel entity);

    @Delete
    void deleteCustomerFinalForms(List<CustomerFinalFormEntity> entity);

    /*****************Lead Survey Form Table Queries*************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLeadSurveyForm(FetchLeadFormBodyModel entity);

    @Query("select * from LeadFormBody")
    Flowable<FetchLeadFormBodyModel> getLeadSurveyFormList();

    @Insert
    void saveLeadFinalForm(LeadFinalFormEntity entity);

    @Query("select * from LeadForm")
    Flowable<List<LeadFinalFormEntity>> getLeadFinalForm();

    @Delete
    void removeLeadForm(FetchLeadFormBodyModel entity);

    @Delete
    void deleteLeadFinalForms(List<LeadFinalFormEntity> entity);

    /*****************Business Survey Form Table Queries*************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addBusinessDataSurveyForm(FetchBusinessDataFormBodyModel entity);

    @Query("select * from BusinessDataFormBody")
    Flowable<FetchBusinessDataFormBodyModel> getBusinessDataSurveyFormList();

    @Insert
    void saveBusinessDataFinalForm(BusinessDataFinalFormEntity entity);

    @Query("select * from BusinessDataForm")
    Flowable<List<BusinessDataFinalFormEntity>> getBusinessDataFinalForm();

    @Delete
    void removeBusinessDataForm(FetchBusinessDataFormBodyModel entity);

    @Delete
    void deleteBusinessDataFinalForms(List<BusinessDataFinalFormEntity> entity);

    /*****************Supplier Survey Form Table Queries*************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSupplierSurveyForm(FetchSupplierBodyModel entity);

    @Query("select * from SupplierFormBody")
    Flowable<FetchSupplierBodyModel> getSupplierSurveyFormList();

   /* @Insert
    void saveSupplierFinalForm(BusinessDataFinalFormEntity entity);*/

   /* @Query("select * from SupplierFormBody")
    Flowable<List<BusinessDataFinalFormEntity>> getBusinessDataFinalForm();*/

    @Delete
    void removeBusinessDataForm(FetchSupplierBodyModel entity);

    /*@Delete
    void deleteBusinessDataFinalForms(List<BusinessDataFinalFormEntity> entity);*/


}
