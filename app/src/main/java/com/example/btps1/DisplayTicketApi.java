package com.example.btps1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DisplayTicketApi {

    @GET ("view_ticket.php")
    Call<List<Ticket_Model>> Ticket_Model();

}
