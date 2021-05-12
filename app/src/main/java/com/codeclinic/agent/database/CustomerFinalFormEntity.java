package com.codeclinic.agent.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity(tableName = "CustomerForm")
public class CustomerFinalFormEntity {

    @ColumnInfo(name = "mainId")
    @PrimaryKey(autoGenerate = true)
    @Expose
    private long mainId;

    @ColumnInfo(name = "request")
    @Expose
    private String request;

    public long getMainId() {
        return mainId;
    }

    public void setMainId(long mainId) {
        this.mainId = mainId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
