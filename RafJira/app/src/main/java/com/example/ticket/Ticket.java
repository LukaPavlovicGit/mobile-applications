package com.example.ticket;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.ticket.enumTicket.TicketPriority;
import com.example.ticket.enumTicket.TicketState;
import com.example.ticket.enumTicket.TicketType;

public class Ticket implements Parcelable {
    private int id;
    private TicketType type;
    private TicketPriority priority;
    private TicketState state;
    private int estimation;
    private int loggedTime;
    private String title;
    private String description;

    public Ticket(int id, TicketType type, TicketPriority priority, int estimation, String title, String description) {
        this.id = id;
        this.type = type;
        this.priority = priority;
        this.state = TicketState.TODO;
        this.loggedTime = 0;
        this.estimation = estimation;
        this.title = title;
        this.description = description;
    }

    public Ticket(int id, TicketType type, TicketPriority priority, TicketState state, int estimation, String title, String description) {
        this.id = id;
        this.type = type;
        this.priority = priority;
        this.loggedTime = 0;
        this.state = state;
        this.estimation = estimation;
        this.title = title;
        this.description = description;
    }

    protected Ticket(Parcel in) {
        id = in.readInt();
        estimation = in.readInt();
        loggedTime = in.readInt();
        title = in.readString();
        description = in.readString();
        type = TicketType.valueOf(in.readString());
        priority = TicketPriority.valueOf(in.readString());
        state = TicketState.valueOf(in.readString());
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

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

    public int getLoggedTime() { return loggedTime; }

    public void setLoggedTime(int loggedTime) { this.loggedTime = loggedTime; }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(estimation);
        parcel.writeInt(loggedTime);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(type.name());
        parcel.writeString(priority.name());
        parcel.writeString(state.name());
    }
}
