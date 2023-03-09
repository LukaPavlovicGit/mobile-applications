package com.example.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.raf_jira.R;
import com.example.raf_jira.databinding.FragmentSingleTicketBinding;
import com.example.ticket.Ticket;
import com.example.ticket.enumTicket.TicketType;

public class SingleTicketFragment extends Fragment {

    private FragmentSingleTicketBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSingleTicketBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        Bundle bundle = getArguments();
        if(bundle == null) return;

        Ticket ticket = (Ticket)bundle.getParcelable("Ticket");
        if(ticket.getType() == TicketType.ENHANCEMENT)
            binding.singleTicketImageView.setImageResource(R.drawable.ic_enhancement_icon);
        else if(ticket.getType() == TicketType.BUG)
            binding.singleTicketImageView.setImageResource(R.drawable.ic_bug_icon);
        binding.singeTicketTextView1.setText(ticket.getTitle());
        binding.ticketTypeHolderTv.setText(ticket.getType().name());
        binding.priorityHolderTv.setText(ticket.getPriority().name());
        binding.estimationHolderTv.setText(String.valueOf(ticket.getEstimation()));
        binding.loggedTimeHolderTv.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "POZ", Toast.LENGTH_SHORT).show();
        });
        binding.descriptionContentTv.setText(ticket.getDescription());

    }
}
