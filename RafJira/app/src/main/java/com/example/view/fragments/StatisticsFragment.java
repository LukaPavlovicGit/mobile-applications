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
            binding.statisticsViewTodoTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.Todo).collect(Collectors.toList()).size()));
            binding.statisticsViewTodoEnhTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.Todo && ticket.getType() == TicketType.Enhancement).collect(Collectors.toList()).size()));
            binding.statisticsViewTodoBugsTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.Todo && ticket.getType() == TicketType.Bug).collect(Collectors.toList()).size()));

            binding.statisticsViewInProgressTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.In_progress).collect(Collectors.toList()).size()));
            binding.statisticsViewInProgressEnhTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.In_progress && ticket.getType() == TicketType.Enhancement).collect(Collectors.toList()).size()));
            binding.statisticsViewInProgressBugsTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.In_progress && ticket.getType() == TicketType.Bug).collect(Collectors.toList()).size()));

            binding.statisticsViewDoneTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.Done).collect(Collectors.toList()).size()));
            binding.statisticsViewDoneEnhTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.Done && ticket.getType() == TicketType.Enhancement).collect(Collectors.toList()).size()));
            binding.statisticsViewDoneBugsTotal.setText(String.valueOf(tickets.stream().filter(ticket -> ticket.getState() == TicketState.Done && ticket.getType() == TicketType.Bug).collect(Collectors.toList()).size()));
        });
    }
}
