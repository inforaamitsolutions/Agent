package com.codeclinic.agent.model.customer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "CustomerFormEntries")
public class SaveCustomerFormEntries {

    @ColumnInfo(name = "mainID")
    @PrimaryKey(autoGenerate = true)
    private long id;


    @ColumnInfo(name = "customerFormEntries")
    private String customerFormEntries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerFormEntries() {
        return customerFormEntries;
    }

    public void setCustomerFormEntries(String customerFormEntries) {
        this.customerFormEntries = customerFormEntries;
    }
}
