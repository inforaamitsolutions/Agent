package com.codeclinic.agent.typeConverters.business;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.businesDataUpdate.BusinessDataQuestionToFollowModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class BusinessDataQuestionToFollowConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<BusinessDataQuestionToFollowModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<BusinessDataQuestionToFollowModel>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<BusinessDataQuestionToFollowModel> someObjects) {
        return gson.toJson(someObjects);
    }

}
