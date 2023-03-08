package com.example.ticket;

import com.example.ticket.ticketType.TicketPriority;
import com.example.ticket.ticketType.TicketState;
import com.example.ticket.ticketType.TicketType;

public class Ticket {
    private int id;
    private TicketType type;
    private TicketPriority priority;
    private TicketState state;
    private int estimation;
    private String title;
    private String description;

    public Ticket(int id, TicketType type, TicketPriority priority, int estimation, String title, String description) {
        this.id = id;
        this.type = type;
        this.priority = priority;
        this.state = TicketState.TODO;
        this.estimation = estimation;
        this.title = title;
        this.description = description;
    }

    public Ticket(int id, TicketType type, TicketPriority priority, TicketState state, int estimation, String title, String description) {
        this.id = id;
        this.type = type;
        this.priority = priority;
        this.state = state;
        this.estimation = estimation;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public int getEstimation() {
        return estimation;
    }

    public void setEstimation(int estimation) {
        this.estimation = estimation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tittle) {
        this.title = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketState getState() {
        return state;
    }

    public void setState(TicketState state) {
        this.state = state;
    }
}
