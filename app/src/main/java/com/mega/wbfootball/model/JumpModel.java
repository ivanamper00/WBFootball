package com.mega.wbfootball.model;

import com.google.gson.annotations.SerializedName;

public class JumpModel {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;

    public JumpModel(Integer id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
