package com.codeclinic.agent.typeConverters.business;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.businesDataUpdate.BusinessDataSurveyDefinitionPageModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class BusinessDataSurveyPageConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<BusinessDataSurveyDefinitionPageModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<BusinessDataSurveyDefinitionPageModel>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<BusinessDataSurveyDefinitionPageModel> someObjects) {
        return gson.toJson(someObjects);
    }
}
