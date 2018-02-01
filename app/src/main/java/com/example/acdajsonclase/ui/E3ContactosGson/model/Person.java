package com.example.acdajsonclase.ui.E3ContactosGson.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by usuario on 1/02/18.
 */

public class Person {

    @SerializedName("contacts")
    @Expose
    private List<model.Contact> contacts = null;

    public List<model.Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<model.Contact> contacts) {
        this.contacts = contacts;
    }

}
