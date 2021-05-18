package com.codeclinic.agent.typeConverters.customer;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.customer.CustomerSurveyDefinitionPageModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class CustomerSurveyPageTypeConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<CustomerSurveyDefinitionPageModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<CustomerSurveyDefinitionPageModel>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<CustomerSurveyDefinitionPageModel> someObjects) {
        return gson.toJson(someObjects);
    }

}
