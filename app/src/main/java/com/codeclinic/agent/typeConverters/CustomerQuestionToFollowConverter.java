package com.codeclinic.agent.typeConverters;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.customer.CustomerQuestionToFollowModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class CustomerQuestionToFollowConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<CustomerQuestionToFollowModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<CustomerQuestionToFollowModel>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<CustomerQuestionToFollowModel> someObjects) {
        return gson.toJson(someObjects);
    }

}
