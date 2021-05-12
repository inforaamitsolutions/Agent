package com.codeclinic.agent.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.codeclinic.agent.model.businesDataUpdate.FetchBusinessDataFormBodyModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormBodyModel;
import com.codeclinic.agent.model.customer.SaveCustomerFormEntries;
import com.codeclinic.agent.model.lead.FetchLeadFormBodyModel;


@Database(entities = {FetchCustomerFormBodyModel.class,
        FetchLeadFormBodyModel.class,
        FetchBusinessDataFormBodyModel.class,
        SaveCustomerFormEntries.class,
        CustomerFinalFormEntity.class,
        LeadFinalFormEntity.class,
        BusinessDataFinalFormEntity.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract DAO getDAO();

    public static LocalDatabase localDatabase;

    public static void createInstance(Application application) {
        if (localDatabase == null) {
            localDatabase = Room.databaseBuilder(application.getApplicationContext(),
                    LocalDatabase.class,
                    "AgentLocalDB")
                    .build();
        }
    }
}
