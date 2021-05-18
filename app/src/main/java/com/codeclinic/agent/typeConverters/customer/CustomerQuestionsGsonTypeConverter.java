package com.codeclinic.agent.typeConverters.customer;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.customer.CustomerQuestionsListModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class CustomerQuestionsGsonTypeConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<CustomerQuestionsListModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<CustomerQuestionsListModel>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<CustomerQuestionsListModel> someObjects) {
        return gson.toJson(someObjects);
    }
}
