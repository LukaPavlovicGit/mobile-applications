package com.example.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.raf_jira.databinding.FragmentStatisticsBinding;
import com.example.ticket.enumTicket.TicketState;
import com.example.ticket.enumTicket.TicketType;
import com.example.viewModels.TicketsViewModel;

import java.util.stream.Collectors;

public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding binding;
    private TicketsViewModel ticketsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketsViewModel = new ViewModelProvider(requireActivity()).get(TicketsViewModel.class);
        initObservers();
    }

    private void initObservers(){
        ticketsViewModel.getTickets().observe(getViewLifecycleOwner(), tickets -> {
            binding.statisticsViewTodoTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.TODO).collect(Collectors.toList()).size()));
            binding.statisticsViewTodoEnhTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.TODO && ticket.getType() == TicketType.ENHANCEMENT).collect(Collectors.toList()).size()));
            binding.statisticsViewTodoBugsTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.TODO && ticket.getType() == TicketType.BUG).collect(Collectors.toList()).size()));

            binding.statisticsViewInProgressTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.IN_PROGRESS).collect(Collectors.toList()).size()));
            binding.statisticsViewInProgressEnhTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.IN_PROGRESS && ticket.getType() == TicketType.ENHANCEMENT).collect(Collectors.toList()).size()));
            binding.statisticsViewInProgressBugsTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.IN_PROGRESS && ticket.getType() == TicketType.BUG).collect(Collectors.toList()).size()));

            binding.statisticsViewDoneTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.DONE).collect(Collectors.toList()).size()));
            binding.statisticsViewDoneEnhTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.DONE && ticket.getType() == TicketType.ENHANCEMENT).collect(Collectors.toList()).size()));
            binding.statisticsViewDoneBugsTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.DONE && ticket.getType() == TicketType.BUG).collect(Collectors.toList()).size()));
        });
    }
}
