package com.codeclinic.agent.typeConverters;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.customer.CustomerQuestionToFollowModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CustomerQuestionToFollowConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static CustomerQuestionToFollowModel stringToSomeObjectList(String data) {
        if (data == null) {
            return new CustomerQuestionToFollowModel();
        }

        Type listType = new TypeToken<CustomerQuestionToFollowModel>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(CustomerQuestionToFollowModel someObjects) {
        return gson.toJson(someObjects);
    }

}
