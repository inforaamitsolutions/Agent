package com.codeclinic.agent.typeConverters.lead;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.lead.LeadOptionsListModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class LeadOptionsGsonTypeConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<LeadOptionsListModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<LeadOptionsListModel>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<LeadOptionsListModel> someObjects) {
        return gson.toJson(someObjects);
    }

}
