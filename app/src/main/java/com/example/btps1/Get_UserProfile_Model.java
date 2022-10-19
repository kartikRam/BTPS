package com.example.btps1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_UserProfile_Model {

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("user_name")
    @Expose
    private String user_name;

    @SerializedName("enrollment_no")
    @Expose
    private String enrollment_no;

    @SerializedName("institute_id")
    @Expose
    private String institute_id;

    @SerializedName("user_password")
    @Expose
    private String user_password;

    @SerializedName("user_email")
    @Expose
    private String user_email;

    @SerializedName("user_address")
    @Expose
    private String user_address;

    @SerializedName("user_contact_no")
    @Expose
    private String user_contact_no;

    @SerializedName("is_student")
    @Expose
    private String is_student;

    @SerializedName("image")
    @Expose
    private String image;

    public Get_UserProfile_Model(String user_id, String user_name, String enrollment_no, String institute_id, String user_password, String user_email, String user_address, String user_contact_no, String is_student, String image) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.enrollment_no = enrollment_no;
        this.institute_id = institute_id;
        this.user_password = user_password;
        this.user_email = user_email;
        this.user_address = user_address;
        this.user_contact_no = user_contact_no;
        this.is_student = is_student;
        this.image = image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEnrollment_no() {
        return enrollment_no;
    }

    public void setEnrollment_no(String enrollment_no) {
        this.enrollment_no = enrollment_no;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_contact_no() {
        return user_contact_no;
    }

    public void setUser_contact_no(String user_contact_no) {
        this.user_contact_no = user_contact_no;
    }

    public String getIs_student() {
        return is_student;
    }

    public void setIs_student(String is_student) {
        this.is_student = is_student;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
