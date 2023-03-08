package com.example.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.raf_jira.R;
import com.example.raf_jira.databinding.FragmentNewTicketBinding;
import com.example.ticket.Ticket;
import com.example.ticket.ticketType.TicketPriority;
import com.example.ticket.ticketType.TicketType;
import com.example.viewModels.TicketsViewModel;

public class NewTicketFragment extends Fragment {

    private FragmentNewTicketBinding binding;
    private TicketsViewModel ticketsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewTicketBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketsViewModel = new ViewModelProvider(requireActivity()).get(TicketsViewModel.class);
        initSpinners();
        initListeners();
    }

    private void initSpinners(){
        Spinner spinnerTicketType = binding.spinnerType;
        ArrayAdapter<CharSequence> adapterTicketType = ArrayAdapter.createFromResource(requireActivity(), R.array.ticket_types, R.layout.spinner_item);
        adapterTicketType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTicketType.setAdapter(adapterTicketType);

        Spinner spinnerTicketPriority = binding.spinnerPriority;
        ArrayAdapter<CharSequence> adapterTicketPriority = ArrayAdapter.createFromResource(requireActivity(), R.array.ticket_priorities, R.layout.spinner_item);
        adapterTicketPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTicketPriority.setAdapter(adapterTicketPriority);
    }

    private void initListeners(){
        binding.addNewTicket.setOnClickListener(view -> {
            TicketType ticketType = null;
            TicketPriority ticketPriority = null;

            if(binding.spinnerType.getSelectedItem().toString().equals("Enhancement"))
                ticketType = TicketType.ENHANCEMENT;
            else if(binding.spinnerType.getSelectedItem().toString().equals("Bug"))
                ticketType = TicketType.BUG;

            if(binding.spinnerPriority.getSelectedItem().toString().equals("Highest"))
                ticketPriority = TicketPriority.HIGHEST;
            else if(binding.spinnerPriority.getSelectedItem().toString().equals("High"))
                ticketPriority = TicketPriority.HIGH;
            else if(binding.spinnerPriority.getSelectedItem().toString().equals("Medium"))
                ticketPriority = TicketPriority.MEDIUM;
            else if(binding.spinnerPriority.getSelectedItem().toString().equals("Low"))
                ticketPriority = TicketPriority.LOW;
            else if(binding.spinnerPriority.getSelectedItem().toString().equals("Lowest"))
                ticketPriority = TicketPriority.LOWEST;

            if( binding.fragmentNewTicketEstimateTv.getText().toString().isEmpty() ||
                    binding.fragmentNewTicketTitleTv.getText().toString().isEmpty() ||
                    binding.fragmentNewTicketDescTv.getText().toString().isEmpty() ){

                Toast.makeText(requireActivity(), "Invalid inputs", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer estimate = Integer.valueOf(binding.fragmentNewTicketEstimateTv.getText().toString());
            String title = binding.fragmentNewTicketTitleTv.getText().toString();
            String description = binding.fragmentNewTicketDescTv.getText().toString();

            ticketsViewModel.addTicket(ticketType, ticketPriority, estimate, title, description);
            resetFields();
        });
    }

    private void resetFields(){
        binding.fragmentNewTicketEstimateTv.setText("");
        binding.fragmentNewTicketTitleTv.setText("");
        binding.fragmentNewTicketDescTv.setText("");
    }
}
