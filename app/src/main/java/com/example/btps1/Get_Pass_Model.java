package com.example.btps1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_Pass_Model {

    @SerializedName("pass_id")
    @Expose
    private String pass_id;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("source")
    @Expose
    private String source;


    @SerializedName("destination")
    @Expose
    private String destination;

    @SerializedName("submit_date")
    @Expose
    private String submit_date;


    @SerializedName("starting_date")
    @Expose
    private String starting_date;


    @SerializedName("ending_date")
    @Expose
    private String ending_date;

    @SerializedName("days")
    @Expose
    private String days;

    @SerializedName("amount")
    @Expose
    private String amount;


    @SerializedName("payment")
    @Expose
    private String payment;

    @SerializedName("is_checked")
    @Expose
    private String is_checked;


    @SerializedName("pass_status")
    @Expose
    private String pass_status;


    @SerializedName("depo_manager_id")
    @Expose
    private String depo_manager_id;


    @SerializedName("kilometer")
    @Expose
    private String kilometer;


    @SerializedName("id_card")
    @Expose
    private String id_card;


    @SerializedName("pass_type")
    @Expose
    private String pass_type;


    public String getSubmit_date() {
        return submit_date;
    }

    public void setSubmit_date(String submit_date) {
        this.submit_date = submit_date;
    }

    public Get_Pass_Model(String pass_id, String user_id, String source, String destination, String submit_date, String starting_date, String ending_date, String days, String amount, String payment, String is_checked, String pass_status, String depo_manager_id, String kilometer,String id_card,String pass_type) {
        this.pass_id = pass_id;
        this.user_id = user_id;
        this.source = source;
        this.destination = destination;
        this.submit_date = submit_date;
        this.starting_date=starting_date;
        this.ending_date=ending_date;
        this.days = days;
        this.amount = amount;
        this.payment=payment;
        this.is_checked = is_checked;
        this.pass_status=pass_status;
        this.depo_manager_id=depo_manager_id;
        this.kilometer=kilometer;
        this.id_card=id_card;
        this.pass_type=pass_type;
    }

    public String getPass_type() {
        return pass_type;
    }

    public void setPass_type(String pass_type) {
        this.pass_type = pass_type;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getKilometer() {
        return kilometer;
    }

    public void setKilometer(String kilometer) {
        this.kilometer = kilometer;
    }

    public String getPass_id() {
        return pass_id;
    }

    public void setPass_id(String pass_id) {
        this.pass_id = pass_id;
    }


    public String getPass_status() {
        return pass_status;
    }

    public void setPass_status(String pass_status) {
        this.pass_status = pass_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSource() {
        return source;
    }

    public String getDepo_manager_id() {
        return depo_manager_id;
    }

    public void setDepo_manager_id(String depo_manager_id) {
        this.depo_manager_id = depo_manager_id;
    }

    public String getEnding_date() {
        return ending_date;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setEnding_date(String ending_date) {
        this.ending_date = ending_date;
    }

    public String getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(String starting_date) {
        this.starting_date = starting_date;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSubmitDate() {
        return submit_date;
    }

    public void setSubmitDate(String date) {
        this.submit_date = submit_date;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String month) {
        this.days = days;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIs_checked() {
        return is_checked;
    }

    public void setIs_checked(String is_checked) {
        this.is_checked = is_checked;
    }

}
