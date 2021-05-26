package com.codeclinic.agent.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codeclinic.agent.database.business.BusinessDataFinalFormEntity;
import com.codeclinic.agent.database.customer.CustomerFinalFormEntity;
import com.codeclinic.agent.database.customer.CustomerFormResumeEntity;
import com.codeclinic.agent.database.lead.LeadFinalFormEntity;
import com.codeclinic.agent.database.supplier.SupplierFinalFormEntity;
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

    @Query("SELECT EXISTS(SELECT * FROM CustomerFormResume)")
    boolean isCustomerFormResumeExists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCustomerFormResume(CustomerFormResumeEntity entity);

    @Query("select * from CustomerFormResume")
    Flowable<CustomerFormResumeEntity> getCustomerFormResume();

    @Delete
    void removeCustomerFormResume(CustomerFormResumeEntity entity);

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

    @Insert
    void saveSupplierFinalForm(SupplierFinalFormEntity entity);

    @Query("select * from SupplierForm")
    Flowable<List<SupplierFinalFormEntity>> getSupplierFinalForm();

    @Delete
    void removeSupplierDataForm(FetchSupplierBodyModel entity);

    @Delete
    void deleteSupplierFinalForms(List<SupplierFinalFormEntity> entity);


}
