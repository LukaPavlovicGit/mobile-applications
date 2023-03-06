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

public class SharedViewModel extends ViewModel {
    private static MutableLiveData<List<Ticket>> ticketsLiveData = new MutableLiveData<>();

    private static MutableLiveData<List<Ticket>> toDoTicketsLiveData = new MutableLiveData<>();
    private static MutableLiveData<List<Ticket>> inProgressTicketsLiveData = new MutableLiveData<>();
    private static MutableLiveData<List<Ticket>> doneTicketsLiveData = new MutableLiveData<>();

    private static MutableLiveData<Integer> totalToDo = new MutableLiveData<>();
    private static MutableLiveData<Integer> totalToDoEnhancement = new MutableLiveData<>();
    private static MutableLiveData<Integer> totalToDoBugs = new MutableLiveData<>();

    private static MutableLiveData<Integer> totalInProgress = new MutableLiveData<>();
    private static MutableLiveData<Integer> totalInProgressEnhancement = new MutableLiveData<>();
    private static MutableLiveData<Integer> totalInProgressBugs = new MutableLiveData<>();

    private static MutableLiveData<Integer> totalDone = new MutableLiveData<>();
    private static MutableLiveData<Integer> totalDoneEnhancement = new MutableLiveData<>();
    private static MutableLiveData<Integer> totalDoneBugs = new MutableLiveData<>();


    static{
        initData();
    }

    public LiveData<List<Ticket>> getTickets(){
        return ticketsLiveData;
    }

    public void setTicketsLiveData(List<Ticket> tickets){
        ticketsLiveData.setValue(tickets);
    }

    public LiveData<Integer> getTotalToDo(){
        return totalToDo;
    }

    public LiveData<Integer> getTotalToDoEnhancement(){
        return totalToDoEnhancement;
    }

    public LiveData<Integer> getTotalToDoBugs(){
        return totalToDoBugs;
    }

    public LiveData<Integer> getTotalInProgress (){
        return totalInProgress ;
    }

    public LiveData<Integer> getTotalInProgressEnhancement(){
        return totalInProgressEnhancement;
    }

    public LiveData<Integer> getTotalInProgressBugs(){
        return totalInProgressBugs;
    }

    public LiveData<Integer> getTotalDone(){
        return totalDone;
    }

    public LiveData<Integer> getTotalDoneEnhancement(){
        return totalDoneEnhancement;
    }

    public LiveData<Integer> getTotalDoneBugs(){
        return totalDoneBugs;
    }

    private static void initData(){

        List<Ticket> list = new ArrayList<>();

        // TO_DO
        list.add(new Ticket(TicketType.BUG, TicketPriority.HIGH, 3, "title1", "description1"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.HIGH, 2, "title2", "description2"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.LOW, 1, "title3", "description3"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.LOWEST, 1, "title4", "description4"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.HIGHEST, 5, "title5", "description5"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.HIGHEST, 2, "title16", "description16"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.LOW, 4, "title17", "description17"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.LOWEST, 2, "title18", "description18"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.MEDIUM, 6, "title19", "description19"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.MEDIUM, 4, "title20", "description20"));

        // IN_PROGRESS
        list.add(new Ticket(TicketType.BUG, TicketPriority.LOW, TicketState.IN_PROGRESS,1, "title6", "description6"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.HIGHEST, TicketState.IN_PROGRESS, 1, "title7", "description7"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.HIGH, TicketState.IN_PROGRESS, 5, "title8", "description8"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.MEDIUM, TicketState.IN_PROGRESS, 2, "title9", "description9"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.MEDIUM, TicketState.IN_PROGRESS, 2, "title10", "description10"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.LOWEST, TicketState.IN_PROGRESS, 1, "title11", "description11"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.HIGHEST, TicketState.IN_PROGRESS, 3, "title12", "description12"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.LOWEST, TicketState.IN_PROGRESS, 2, "title21", "description21"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.HIGH, TicketState.IN_PROGRESS, 2, "title22", "description22"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.MEDIUM, TicketState.IN_PROGRESS, 4, "title23", "description23"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.HIGHEST, TicketState.IN_PROGRESS, 2, "title24", "description24"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.MEDIUM, TicketState.IN_PROGRESS, 3, "title25", "description25"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.LOW, TicketState.IN_PROGRESS, 2, "title26", "description26"));

        // DONE
        list.add(new Ticket(TicketType.BUG, TicketPriority.LOWEST, TicketState.DONE, 3, "title13", "description13"));
        list.add(new Ticket(TicketType.BUG, TicketPriority.MEDIUM, TicketState.DONE, 5, "title14", "description14"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.HIGH, TicketState.DONE, 1, "title15", "description15"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.HIGHEST, TicketState.DONE, 1, "title27", "description27"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.HIGHEST, TicketState.DONE, 3, "title28", "description28"));
        list.add(new Ticket(TicketType.ENHANCEMENT, TicketPriority.LOW, TicketState.DONE, 1, "title29", "description29"));

        ticketsLiveData.setValue(list);
        setDataForStatView();
    }

    private static void setDataForStatView(){

        List<Ticket> list = ticketsLiveData.getValue();

        int toDoEnh = 0, toDoBugs = 0;
        int inProgressEnh = 0, inProgressBugs = 0;
        int doneEnh = 0, doneBugs = 0;

        for(Ticket ticket : list){
            if(ticket.getState().equals(TicketState.TODO)){
                if(ticket.getType().equals(TicketType.ENHANCEMENT))
                    toDoEnh++;
                else
                    toDoBugs++;
            }
            else if(ticket.getState().equals(TicketState.IN_PROGRESS)){
                if(ticket.getType().equals(TicketType.ENHANCEMENT))
                    inProgressEnh++;
                else
                    inProgressBugs++;
            }
            else if(ticket.getState().equals(TicketState.DONE)){
                if(ticket.getType().equals(TicketType.ENHANCEMENT))
                    doneEnh++;
                else
                    doneBugs++;
            }
        }

        totalToDo.setValue(toDoEnh+toDoBugs);
        totalToDoEnhancement.setValue(toDoEnh);
        totalToDoBugs.setValue(toDoBugs);

        totalInProgress.setValue(inProgressEnh+inProgressBugs);
        totalInProgressEnhancement.setValue(inProgressEnh);
        totalInProgressBugs.setValue(inProgressBugs);

        totalDone.setValue(doneEnh+doneBugs);
        totalDoneEnhancement.setValue(doneEnh);
        totalDoneBugs.setValue(doneBugs);
    }
}
