package com.codeclinic.agent.typeConverters;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.businesDataUpdate.BusinessDataQuestionToFollowModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class BusinessDataQuestionToFollowConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static BusinessDataQuestionToFollowModel stringToSomeObjectList(String data) {
        if (data == null) {
            return new BusinessDataQuestionToFollowModel();
        }

        Type listType = new TypeToken<BusinessDataQuestionToFollowModel>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(BusinessDataQuestionToFollowModel someObjects) {
        return gson.toJson(someObjects);
    }

}
