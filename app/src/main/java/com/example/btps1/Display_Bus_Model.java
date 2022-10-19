package com.example.btps1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Display_Bus_Model {

    @SerializedName("bus_id")
    @Expose
    private String bus_id;

    @SerializedName("bus_time")
    @Expose
    private String bus_time;

    @SerializedName("bus_number")
    @Expose
    private String bus_number;

    @SerializedName("via")
    @Expose
    private String via;

    @SerializedName("worker_id")
    @Expose
    private String worker_id;

    @SerializedName("depo_manager_id")
    @Expose
    private String depo_manager_id;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("destination")
    @Expose
    private String destination;

    @SerializedName("bus_hire")
    @Expose
    private String bus_hire;


    public Display_Bus_Model(String bus_id, String bus_time, String bus_number, String via, String worker_id, String depo_manager_id, String source, String destination, String bus_hire) {
        this.bus_id = bus_id;
        this.bus_time = bus_time;
        this.bus_number = bus_number;
        this.via = via;
        this.worker_id = worker_id;
        this.depo_manager_id = depo_manager_id;
        this.source = source;
        this.destination = destination;
        this.bus_hire = bus_hire;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getBus_time() {
        return bus_time;
    }

    public void setBus_time(String bus_time) {
        this.bus_time = bus_time;
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

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getDepo_manager_id() {
        return depo_manager_id;
    }

    public void setDepo_manager_id(String depo_manager_id) {
        this.depo_manager_id = depo_manager_id;
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

    public String getBus_hire() {
        return bus_hire;
    }

    public void setBus_hire(String bus_hire) {
        this.bus_hire = bus_hire;
    }
}
