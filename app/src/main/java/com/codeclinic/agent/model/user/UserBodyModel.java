package com.codeclinic.agent.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBodyModel {
    @SerializedName("staff")
    @Expose
    private Object staff;
    @SerializedName("user")
    @Expose
    private UserDetailsModel user;

    public Object getStaff() {
        return staff;
    }

    public void setStaff(Object staff) {
        this.staff = staff;
    }

    public UserDetailsModel getUser() {
        return user;
    }

    public void setUser(UserDetailsModel user) {
        this.user = user;
    }
}
