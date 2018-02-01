package com.example.acdajsonclase.ui.E3ContactosGson.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by usuario on 1/02/18.
 */

public class Phone {

    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("work")
    @Expose
    private String work;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

}
