package com.example.btps1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Get_Institute_Details {

    @SerializedName("institute_id")
    @Expose
    private String institute_id;

    @SerializedName("institute_name")
    @Expose
    private String institute_name;

    @SerializedName("institute_email")
    @Expose
    private String institute_email;

    @SerializedName("institute_district")
    @Expose
    private String institute_district;

    @SerializedName("institute_address")
    @Expose
    private String institute_address;

    @SerializedName("institute_password")
    @Expose
    private String institute_password;

    @SerializedName("institute_contact_no")
    @Expose
    private String institute_contact_no;


    @SerializedName("image")
    @Expose
    private String image;

    public Get_Institute_Details(String institute_id, String institute_name, String institute_email, String institute_district, String institute_address, String institute_password, String institute_contact_no, String image) {
        this.institute_id = institute_id;
        this.institute_name = institute_name;
        this.institute_email = institute_email;
        this.institute_district = institute_district;
        this.institute_address = institute_address;
        this.institute_password = institute_password;
        this.institute_contact_no = institute_contact_no;
        this.image = image;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getInstitute_name() {
        return institute_name;
    }

    public void setInstitute_name(String institute_name) {
        this.institute_name = institute_name;
    }

    public String getInstitute_email() {
        return institute_email;
    }

    public void setInstitute_email(String institute_email) {
        this.institute_email = institute_email;
    }

    public String getInstitute_district() {
        return institute_district;
    }

    public void setInstitute_district(String institute_district) {
        this.institute_district = institute_district;
    }

    public String getInstitute_address() {
        return institute_address;
    }

    public void setInstitute_address(String institute_address) {
        this.institute_address = institute_address;
    }

    public String getInstitute_password() {
        return institute_password;
    }

    public void setInstitute_password(String institute_password) {
        this.institute_password = institute_password;
    }

    public String getInstitute_contact_no() {
        return institute_contact_no;
    }

    public void setInstitute_contact_no(String institute_contact_no) {
        this.institute_contact_no = institute_contact_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
