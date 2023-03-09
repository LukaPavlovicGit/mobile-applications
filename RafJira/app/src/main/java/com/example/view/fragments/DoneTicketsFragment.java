package com.example.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raf_jira.R;
import com.example.raf_jira.databinding.FragmentDoneTicketsBinding;
import com.example.raf_jira.databinding.FragmentInprogressTicketsBinding;
import com.example.ticket.ticketType.TicketState;
import com.example.view.recycler.adapter.TicketAdapter;
import com.example.view.recycler.differ.TicketDiffItemCallback;
import com.example.viewModels.TicketsViewModel;

import java.util.stream.Collectors;

public class DoneTicketsFragment extends Fragment implements TicketAdapter.TicketClickInterface {
    private FragmentDoneTicketsBinding binding;
    private TicketsViewModel ticketsViewModel;
    private TicketAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDoneTicketsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketsViewModel = new ViewModelProvider(requireActivity()).get(TicketsViewModel.class);
        Button b = view.findViewById(R.id.button1);
        initRecyclerView();
        initObservers();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        adapter = new TicketAdapter(new TicketDiffItemCallback(), this);
        recyclerView.setAdapter(adapter);
    }

    private void initObservers(){
        ticketsViewModel.getTickets().observe(getViewLifecycleOwner(), tickets -> {
            adapter.submitList(tickets.stream().filter(ticket -> ticket.getState() == TicketState.DONE).collect(Collectors.toList()));
        });
    }

}
