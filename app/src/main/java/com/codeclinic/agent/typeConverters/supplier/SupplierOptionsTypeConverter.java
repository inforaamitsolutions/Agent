package com.codeclinic.agent.typeConverters.supplier;

import androidx.room.TypeConverter;

import com.codeclinic.agent.model.supplier.SupplierOptionsListModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class SupplierOptionsTypeConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<SupplierOptionsListModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<SupplierOptionsListModel>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<SupplierOptionsListModel> someObjects) {
        return gson.toJson(someObjects);
    }
}
