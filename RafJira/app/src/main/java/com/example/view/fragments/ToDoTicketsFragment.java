package com.example.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.raf_jira.databinding.FragmentTodoTicketsBinding;
import com.example.view.viewPager.TicketAdapter;
import com.example.viewModels.SharedViewModel;

public class ToDoTicketsFragment extends Fragment {

    private FragmentTodoTicketsBinding binding;
    private SharedViewModel sharedViewModel;
    private TicketAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTodoTicketsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        initRecyclerView();
        initObservers();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = binding.toDoRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        adapter = new TicketAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initObservers(){
        sharedViewModel.getToDoTicketsLiveData().observe(getViewLifecycleOwner(), tickets -> {
            adapter.setTickets(tickets);
        });
    }
}
