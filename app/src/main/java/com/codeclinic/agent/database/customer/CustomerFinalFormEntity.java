package com.codeclinic.agent.database.customer;

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

    @ColumnInfo(name = "name")
    @Expose
    private String name;

    @ColumnInfo(name = "number")
    @Expose
    private String number;

    @ColumnInfo(name = "request")
    @Expose
    private String request;

    public long getMainId() {
        return mainId;
    }

    public void setMainId(long mainId) {
        this.mainId = mainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
