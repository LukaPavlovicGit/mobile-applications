package com.example.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket.Ticket;
import com.example.ticket.ticketType.TicketPriority;
import com.example.ticket.ticketType.TicketState;
import com.example.ticket.ticketType.TicketType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketsViewModel extends ViewModel {
    private static int id = 0;
    private MutableLiveData<List<Ticket>> ticketsLD = new MutableLiveData<>();

    public TicketsViewModel(){
        initData();
    }

    private void initData(){

        List<Ticket> tickets = new ArrayList<>();
        TicketType type;
        TicketPriority priority;
        TicketState state;
        int estimation;
        String title = "Title", description = "Description";

        for(int i=0 ; i < 100 ; i++){
            if(i % 2 == 0) type = TicketType.BUG;
            else type = TicketType.ENHANCEMENT;

            if(i % 5 == 0) priority = TicketPriority.LOWEST;
            else if(i % 5 == 1) priority = TicketPriority.LOW;
            else if(i % 5 == 2) priority = TicketPriority.MEDIUM;
            else if(i % 5 == 3) priority = TicketPriority.HIGH;
            else priority = TicketPriority.HIGHEST;

            if(i % 5 == 0) estimation = 4;
            else if(i % 5 == 1) estimation = 1;
            else if(i % 5 == 2) estimation = 2;
            else if(i % 5 == 3) estimation = 5;
            else estimation = 3;

            if(i > 80) state = TicketState.DONE;
            else if(i > 40) state = TicketState.IN_PROGRESS;
            else state = TicketState.TODO;

            tickets.add(new Ticket(id, type, priority, state, estimation, title + id, description + id));

            ++id;
        }
        ticketsLD.setValue(tickets);
    }

    public LiveData<List<Ticket>> getTickets() {
        return ticketsLD;
    }


    public void addTicket(TicketType type, TicketPriority priority, int estimation, String title, String description){
        List<Ticket> tickets = ticketsLD.getValue();
        tickets.add(new Ticket(id, type, priority, estimation, title + id, description + id));
        ticketsLD.setValue(tickets);
        ++id;
    }

    public void updateTicket(int id, TicketType type, TicketPriority priority, TicketState state, Integer estimation, String title, String description){
        List<Ticket> tickets = ticketsLD.getValue();
        Optional<Ticket> optionalTicket = tickets.stream().filter(ticket -> ticket.getId() == id).findFirst();
        optionalTicket.ifPresent(ticket -> {
            ticket.setType(type);
            ticket.setPriority(priority);
            ticket.setState(state);
            if(estimation != null) ticket.setEstimation(estimation);
            if(title != null && !title.isEmpty()) ticket.setTitle(title);
            if(description != null && !description.isEmpty()) ticket.setDescription(description);

            ticketsLD.setValue(tickets);
        });
    }

    public void fromTodoToInProgress(int id){
        List<Ticket> tickets = ticketsLD.getValue();
        Optional<Ticket> optionalTicket = tickets.stream().filter(ticket -> ticket.getId() == id).findFirst();
        optionalTicket.ifPresent(ticket -> {
            ticket.setState(TicketState.IN_PROGRESS);
            ticketsLD.setValue(tickets);
        });
    }

    public void fromInProgressToDone(int id){
        List<Ticket> tickets = ticketsLD.getValue();
        Optional<Ticket> optionalTicket = tickets.stream().filter(ticket -> ticket.getId() == id).findFirst();
        optionalTicket.ifPresent(ticket -> {
            ticket.setState(TicketState.DONE);
            ticketsLD.setValue(tickets);
        });
    }

    public void fromInProgressToTodo(int id){
        List<Ticket> tickets = ticketsLD.getValue();
        Optional<Ticket> optionalTicket = tickets.stream().filter(ticket -> ticket.getId() == id).findFirst();
        optionalTicket.ifPresent(ticket -> {
            ticket.setState(TicketState.TODO);
            ticketsLD.setValue(tickets);
        });
    }

    public void removeTicket(int id){
        List<Ticket> tickets = ticketsLD.getValue();
        Optional<Ticket> optionalTicket = tickets.stream().filter(ticket -> ticket.getId() == id).findFirst();
        optionalTicket.ifPresent(ticket -> {
            tickets.remove(ticket);
            ticketsLD.setValue(tickets);
        });
    }


}
