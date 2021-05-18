package com.codeclinic.agent.typeConverters.supplier;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.supplier.SupplierSurveyDefinitionPageModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class SupplierSurveyPageTypeConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<SupplierSurveyDefinitionPageModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<SupplierSurveyDefinitionPageModel>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<SupplierSurveyDefinitionPageModel> someObjects) {
        return gson.toJson(someObjects);
    }
}
