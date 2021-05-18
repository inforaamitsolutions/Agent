package com.codeclinic.agent.model.supplier;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.codeclinic.agent.typeConverters.supplier.SupplierQuestionToFollowConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "SupplierQuestionsOptions")
public class SupplierOptionsListModel {
    @ColumnInfo(name = "optionMainId")
    @PrimaryKey(autoGenerate = true)
    @Expose
    private long mainId;

    @ColumnInfo(name = "optionId")
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    @Expose
    private Integer id;

    @ColumnInfo(name = "label")
    @SerializedName("label")
    @Expose
    private String label;

    @ColumnInfo(name = "value")
    @SerializedName("value")
    @Expose
    private String value;

    @ColumnInfo(name = "order")
    @SerializedName("order")
    @Expose
    private int order;

    @ColumnInfo(name = "active")
    @SerializedName("active")
    @Expose
    private Boolean active;

    @SerializedName("questionToFollow")
    @Expose
    @TypeConverters(SupplierQuestionToFollowConverter.class)
    private List<SupplierQuestionToFollowModel> questionToFollow = null;

    public long getMainId() {
        return mainId;
    }

    public void setMainId(long mainId) {
        this.mainId = mainId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<SupplierQuestionToFollowModel> getQuestionToFollow() {
        return questionToFollow;
    }

    public void setQuestionToFollow(List<SupplierQuestionToFollowModel> questionToFollow) {
        this.questionToFollow = questionToFollow;
    }

    @Override
    public String toString() {
        return label;
    }
}
