package com.example.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.raf_jira.R;
import com.example.ticket.Ticket;
import com.example.ticket.enumTicket.TicketPriority;
import com.example.ticket.enumTicket.TicketState;
import com.example.ticket.enumTicket.TicketType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class TicketsViewModel extends ViewModel {
    private static int id = 0;
    private final MutableLiveData<List<Ticket>> ticketsLD = new MutableLiveData<>();
    private final MutableLiveData<Ticket> ticketLD = new MutableLiveData<>();
    private List<Ticket> tickets = new ArrayList<>();

    public TicketsViewModel(){
        initData();
    }

    private void initData(){

        TicketType type;
        TicketPriority priority;
        TicketState state;
        int estimation;
        String title = "Title", description = "Description";

        for(int i=0 ; i < 100 ; i++){
            if(i % 2 == 0) type = TicketType.Bug;
            else type = TicketType.Enhancement;

            if(i % 5 == 0) priority = TicketPriority.Lowest;
            else if(i % 5 == 1) priority = TicketPriority.Low;
            else if(i % 5 == 2) priority = TicketPriority.Medium;
            else if(i % 5 == 3) priority = TicketPriority.High;
            else priority = TicketPriority.Highest;

            if(i % 5 == 0) estimation = 4;
            else if(i % 5 == 1) estimation = 1;
            else if(i % 5 == 2) estimation = 2;
            else if(i % 5 == 3) estimation = 5;
            else estimation = 3;

            if(i > 90) state = TicketState.Done;
            else if(i > 45) state = TicketState.In_progress;
            else state = TicketState.Todo;

            tickets.add(new Ticket(id, type, priority, state, estimation, generateTitle(), generateDescription()));
            ++id;
        }
        // We are doing this because cars.setValue in the background is first checking if the reference on the object is same
        // and if it is it will not do notifyAll. By creating a new list, we get the new reference everytime
        ArrayList<Ticket> listToSubmit = new ArrayList<>(tickets);
        ticketsLD.setValue(listToSubmit);
    }

    public LiveData<List<Ticket>> getTickets() {
        return ticketsLD;
    }

    public LiveData<Ticket> getTicketLD(){
        return ticketLD;
    }

    public void filterTickets(String prefix){
        ticketsLD.setValue( tickets.stream().filter(ticket -> ticket.getTitle().toLowerCase().startsWith(prefix.toLowerCase())).collect(Collectors.toList()) );
    }

    public void incrementLoggedTime(){
        Ticket ticket = ticketLD.getValue();
        ticket.setLoggedTime(ticket.getLoggedTime() + 1);
        ticketLD.setValue(ticket);
    }
    public void decrementLoggedTime(){
        Ticket ticket = ticketLD.getValue();
        ticket.setLoggedTime(ticket.getLoggedTime() - 1);
        ticketLD.setValue(ticket);
    }

    public void setTicketLD(int id){
        tickets
            .stream()
            .filter(ticket -> ticket.getId() == id)
            .findFirst()
            .ifPresent(ticket ->  ticketLD.setValue(ticket) );
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
            if(type != null) ticket.setType(type);
            if(priority != null )ticket.setPriority(priority);
            if(state != null) ticket.setState(state);
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
            ticket.setState(TicketState.In_progress);
            ticketsLD.setValue(tickets);
        });
    }

    public void fromInProgressToDone(int id){
        List<Ticket> tickets = ticketsLD.getValue();
        Optional<Ticket> optionalTicket = tickets.stream().filter(ticket -> ticket.getId() == id).findFirst();
        optionalTicket.ifPresent(ticket -> {
            ticket.setState(TicketState.Done);
            ticketsLD.setValue(tickets);
        });
    }

    public void fromInProgressToTodo(int id){
        List<Ticket> tickets = ticketsLD.getValue();
        Optional<Ticket> optionalTicket = tickets.stream().filter(ticket -> ticket.getId() == id).findFirst();
        optionalTicket.ifPresent(ticket -> {
            ticket.setState(TicketState.Todo);
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

    private String generateTitle(){
        StringBuffer title = new StringBuffer();
        Character vowels[] = {'a', 'e', 'i', 'o', 'y'};
        Character consonants[] = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};
        Random random = new Random();
        Integer len = random.nextInt(10) + 6;

        for(int i=0 ; i<len ; i++){
            if(random.nextInt(100) < 36) title.append(vowels[random.nextInt(vowels.length)]);
            else title.append(consonants[random.nextInt(consonants.length)]);

        }

        return title.toString().substring(0, 1).toUpperCase() + title.toString().substring(1);
    }

    private String generateDescription(){
        StringBuffer title = new StringBuffer();
        Character vowels[] = {'a', 'e', 'i', 'o', 'y'};
        Character consonants[] = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};
        Random random = new Random();
        Integer len = random.nextInt(30) + 6;

        for(int i=0 ; i<len ; i++){
            if(random.nextInt(100) < 36) title.append(vowels[random.nextInt(vowels.length)]);
            else title.append(consonants[random.nextInt(consonants.length)]);

        }

        return title.toString().substring(0, 1).toUpperCase() + title.toString().substring(1);
    }
}
