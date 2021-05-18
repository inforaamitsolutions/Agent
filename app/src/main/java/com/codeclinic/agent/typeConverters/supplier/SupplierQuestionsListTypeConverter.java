package com.codeclinic.agent.typeConverters.supplier;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.supplier.SupplierQuestionListModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class SupplierQuestionsListTypeConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<SupplierQuestionListModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<SupplierQuestionListModel>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<SupplierQuestionListModel> someObjects) {
        return gson.toJson(someObjects);
    }
}
