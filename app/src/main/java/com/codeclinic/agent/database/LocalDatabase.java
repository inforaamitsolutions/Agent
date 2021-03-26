package com.codeclinic.agent.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.codeclinic.agent.model.customer.CustomerSurveyDefinitionPageModel;
import com.codeclinic.agent.model.lead.LeadSurveyDefinitionPageModel;


@Database(entities = {CustomerSurveyDefinitionPageModel.class, LeadSurveyDefinitionPageModel.class}, version = 1, exportSchema = false)
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
