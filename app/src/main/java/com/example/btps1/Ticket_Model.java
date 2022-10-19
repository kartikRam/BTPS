package com.example.btps1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Ticket_Model {

    @SerializedName("ticket_id")
    @Expose
    private String ticket_id;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("bus_id")
    @Expose
    private String bus_id;

    @SerializedName("worker_id")
    @Expose
    private String worker_id;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("destination")
    @Expose
    private String destination;

    @SerializedName("time")
    @Expose
    private String time;


    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("ticket_rate")
    @Expose
    private String ticket_rate;


    @SerializedName("total_kilometer")
    @Expose
    private String total_kilometer;


    @SerializedName("no_ticket")
    @Expose
    private String no_ticket;

    public Ticket_Model(String ticket_id, String user_id, String bus_id, String worker_id, String source, String destination, String time,String date, String ticket_rate,String total_kilometer,String no_ticket) {
        this.ticket_id = ticket_id;
        this.user_id = user_id;
        this.bus_id = bus_id;
        this.worker_id = worker_id;
        this.source = source;
        this.destination = destination;
        this.time = time;
        this.date=date;
        this.ticket_rate = ticket_rate;
        this.total_kilometer=total_kilometer;
        this.no_ticket=no_ticket;
    }


    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTicket_rate() {
        return ticket_rate;
    }

    public void setTicket_rate(String ticket_rate) {
        this.ticket_rate = ticket_rate;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_kilometer() {
        return total_kilometer;
    }

    public void setTotal_kilometer(String total_kilometer) {
        this.total_kilometer = total_kilometer;
    }

    public String getNo_ticket() {
        return no_ticket;
    }

    public void setNo_ticket(String no_ticket) {
        this.no_ticket = no_ticket;
    }

}

