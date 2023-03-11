package com.example.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.constants.Constants;
import com.example.raf_jira.R;
import com.example.raf_jira.databinding.FragmentSingleTicketBinding;
import com.example.ticket.Ticket;
import com.example.ticket.enumTicket.TicketType;
import com.example.viewModels.TicketsViewModel;

public class SingleTicketFragment extends Fragment {

    private FragmentSingleTicketBinding binding;
    private TicketsViewModel ticketsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSingleTicketBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketsViewModel = new ViewModelProvider(requireActivity()).get(TicketsViewModel.class);

        init();
        initObservers();
        initListeners();
    }

    private void init() {
        Bundle bundle = getArguments();
        if(bundle == null) return;

        boolean hideEditButton = bundle.getBoolean(Constants.HIDE_EDIT_BUTTON, false);
        if(hideEditButton)
            binding.editButton.setVisibility(View.INVISIBLE);

        Ticket ticket = bundle.getParcelable("Ticket");
        if(ticket.getType() == TicketType.ENHANCEMENT)
            binding.singleTicketImageView.setImageResource(R.drawable.ic_enhancement_icon);
        else if(ticket.getType() == TicketType.BUG)
            binding.singleTicketImageView.setImageResource(R.drawable.ic_bug_icon);
        binding.singeTicketTextView1.setText(ticket.getTitle());
        binding.ticketTypeHolderTv.setText(ticket.getType().name());
        binding.priorityHolderTv.setText(ticket.getPriority().name());
        binding.estimationHolderTv.setText(String.valueOf(ticket.getEstimation()));
        binding.loggedTimeHolderTv.setText(String.valueOf(ticket.getLoggedTime()));
        binding.descriptionContentTv.setText(ticket.getDescription());


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Constants.USERNAME_KEY, "");
        if(!username.startsWith("admin"))
            binding.editButton.setVisibility(View.INVISIBLE);
    }

    private void initObservers(){
        ticketsViewModel.getTicketLD().observe(getViewLifecycleOwner(),ticket -> {
            if(ticket.getType() == TicketType.ENHANCEMENT)
                binding.singleTicketImageView.setImageResource(R.drawable.ic_enhancement_icon);
            else if(ticket.getType() == TicketType.BUG)
                binding.singleTicketImageView.setImageResource(R.drawable.ic_bug_icon);
            binding.singeTicketTextView1.setText(ticket.getTitle());
            binding.ticketTypeHolderTv.setText(ticket.getType().name());
            binding.priorityHolderTv.setText(ticket.getPriority().name());
            binding.estimationHolderTv.setText(String.valueOf(ticket.getEstimation()));
            binding.loggedTimeHolderTv.setText(String.valueOf(ticket.getLoggedTime()));
            binding.descriptionContentTv.setText(ticket.getDescription());
        });
    }

    private void initListeners(){
        binding.loggedTimeHolderTv.setOnClickListener(view -> ticketsViewModel.incrementLoggedTime());
        binding.loggedTimeHolderTv.setOnLongClickListener(view -> {
            ticketsViewModel.decrementLoggedTime();
            return true;
        });
        binding.editButton.setOnClickListener(view -> {
            Fragment fragment = new NewEditTicketFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.POPULATE_FIELDS_KEY, true);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_container_view_tag, fragment, Constants.SINGLE_TICKET_FRAGMENT_TAG);
            transaction.commit();
        });
    }
}
