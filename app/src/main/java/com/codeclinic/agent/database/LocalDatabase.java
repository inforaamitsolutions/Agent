package com.codeclinic.agent.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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


@Database(entities = {FetchCustomerFormBodyModel.class,
        FetchLeadFormBodyModel.class,
        FetchBusinessDataFormBodyModel.class,
        SaveCustomerFormEntries.class,
        CustomerFinalFormEntity.class,
        LeadFinalFormEntity.class,
        BusinessDataFinalFormEntity.class,
        FetchSupplierBodyModel.class,
        SupplierFinalFormEntity.class,
        CustomerFormResumeEntity.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract DAO getDAO();

    public static LocalDatabase localDatabase;

    public static void createInstance(Application application) {
        if (localDatabase == null) {
            localDatabase = Room.databaseBuilder(application.getApplicationContext(),
                    LocalDatabase.class,
                    "AgentLocalDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }
}
