package com.example.btps1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Get_Bus_Model {

    @SerializedName("bus_id")
    @Expose
    private String bus_id;

    @SerializedName("bus_number")
    @Expose
    private String bus_number;

    @SerializedName("via")
    @Expose
    private String via;

    @SerializedName("hours")
    @Expose
    private String hours;

    @SerializedName("worker_id")
    @Expose
    private String worker_id;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("destination")
    @Expose
    private String destination;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("bus_hire")
    @Expose
    private String bus_hire;

    @SerializedName("bus_time")
    @Expose
    private String bus_time;

    @SerializedName("branch_name")
    @Expose
    private String branch_name;


    public Get_Bus_Model(String bus_id, String bus_number, String via, String hours, String worker_id, String source, String destination,
                         String date, String amount, String bus_hire,String bus_time,String branch_name) {
        this.bus_id = bus_id;
        this.bus_number = bus_number;
        this.via = via;
        this.worker_id = worker_id;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.amount = amount;
        this.bus_hire = bus_hire;
        this.hours = hours;
        this.bus_time=bus_time;
        this.branch_name=branch_name;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getSource() {
        return source;
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

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBus_time() {
        return bus_time;
    }

    public void setBus_time(String bus_time) {
        this.bus_time = bus_time;
    }

    public String getBus_hire() {
        return bus_hire;
    }

    public void setBus_hire(String bus_hire) {
        this.bus_hire = bus_hire;
    }

    public String getTime() {
        return bus_time;
    }

    public void setTime(String bus_time) {
        this.bus_time = bus_time;
    }
}
