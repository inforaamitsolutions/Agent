package com.codeclinic.agent.typeConverters;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.lead.LeadQuestionToFollowModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class LeadQuestionToFollowConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static LeadQuestionToFollowModel stringToSomeObjectList(String data) {
        if (data == null) {
            return new LeadQuestionToFollowModel();
        }

        Type listType = new TypeToken<LeadQuestionToFollowModel>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(LeadQuestionToFollowModel someObjects) {
        return gson.toJson(someObjects);
    }
}
