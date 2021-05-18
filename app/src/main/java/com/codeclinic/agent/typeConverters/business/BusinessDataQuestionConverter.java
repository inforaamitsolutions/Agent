package com.codeclinic.agent.typeConverters.business;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.businesDataUpdate.BusinessDataQuestionListModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class BusinessDataQuestionConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<BusinessDataQuestionListModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<BusinessDataQuestionListModel>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<BusinessDataQuestionListModel> someObjects) {
        return gson.toJson(someObjects);
    }
}
