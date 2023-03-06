package com.example.ticket;

import com.example.ticket.ticketType.TicketPriority;
import com.example.ticket.ticketType.TicketState;
import com.example.ticket.ticketType.TicketType;

public class Ticket {
    private TicketType type;
    private TicketPriority priority;
    private TicketState state;
    private int estimation;
    private String tittle;
    private String description;

    public Ticket(TicketType type, TicketPriority priority, int estimation, String tittle, String description) {
        this.type = type;
        this.priority = priority;
        this.state = TicketState.TODO;
        this.estimation = estimation;
        this.tittle = tittle;
        this.description = description;
    }

    public Ticket(TicketType type, TicketPriority priority, TicketState state, int estimation, String tittle, String description) {
        this.type = type;
        this.priority = priority;
        this.state = state;
        this.estimation = estimation;
        this.tittle = tittle;
        this.description = description;
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

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
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
