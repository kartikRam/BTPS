package com.example.btps1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Display_Conductor_Model {

    @SerializedName("worker_id")
    @Expose
    private String worker_id;

    @SerializedName("worker_name")
    @Expose
    private String worker_name;

    @SerializedName("worker_email")
    @Expose
    private String worker_email;

    @SerializedName("depo_manager_id")
    @Expose
    private String depo_manager_id;

    @SerializedName("worker_address")
    @Expose
    private String worker_address;

    @SerializedName("branch_name")
    @Expose
    private String branch_name;

    @SerializedName("worker_role")
    @Expose
    private String worker_role;

    @SerializedName("worker_contact_no")
    @Expose
    private String worker_contact_no;

    @SerializedName("worker_password")
    @Expose
    private String worker_password;

    @SerializedName("image")
    @Expose
    private String image;



    public Display_Conductor_Model(String worker_id, String worker_name, String worker_email, String depo_manager_id, String worker_address, String branch_name, String worker_role, String worker_contact_no, String worker_password, String image) {
        this.worker_id = worker_id;
        this.worker_name = worker_name;
        this.worker_email = worker_email;
        this.depo_manager_id = depo_manager_id;
        this.worker_address = worker_address;
        this.branch_name = branch_name;
        this.worker_role = worker_role;
        this.worker_contact_no = worker_contact_no;
        this.worker_password = worker_password;
        this.image = image;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getWorker_email() {
        return worker_email;
    }

    public void setWorker_email(String worker_email) {
        this.worker_email = worker_email;
    }

    public String getDepo_manager_id() {
        return depo_manager_id;
    }

    public void setDepo_manager_id(String depo_manager_id) {
        this.depo_manager_id = depo_manager_id;
    }

    public String getWorker_address() {
        return worker_address;
    }

    public void setWorker_address(String worker_address) {
        this.worker_address = worker_address;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getWorker_role() {
        return worker_role;
    }

    public void setWorker_role(String worker_role) {
        this.worker_role = worker_role;
    }

    public String getWorker_contact_no() {
        return worker_contact_no;
    }

    public void setWorker_contact_no(String worker_contact_no) {
        this.worker_contact_no = worker_contact_no;
    }

    public String getWorker_password() {
        return worker_password;
    }

    public void setWorker_password(String worker_password) {
        this.worker_password = worker_password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
