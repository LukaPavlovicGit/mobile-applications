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

import com.example.constants.Constants;
import com.example.raf_jira.R;
import com.example.raf_jira.databinding.FragmentNewEditTicketBinding;
import com.example.ticket.enumTicket.TicketPriority;
import com.example.ticket.enumTicket.TicketType;
import com.example.viewModels.TicketsViewModel;

public class NewEditTicketFragment extends Fragment {

    private FragmentNewEditTicketBinding binding;
    private TicketsViewModel ticketsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewEditTicketBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketsViewModel = new ViewModelProvider(requireActivity()).get(TicketsViewModel.class);
        initView();
        initListeners();
    }

    private void initView(){
        // Spinners
        Spinner spinnerTicketType = binding.spinnerType;
        ArrayAdapter<CharSequence> adapterTicketType = ArrayAdapter.createFromResource(requireActivity(), R.array.ticket_types, R.layout.spinner_item);
        adapterTicketType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTicketType.setAdapter(adapterTicketType);

        Spinner spinnerTicketPriority = binding.spinnerPriority;
        ArrayAdapter<CharSequence> adapterTicketPriority = ArrayAdapter.createFromResource(requireActivity(), R.array.ticket_priorities, R.layout.spinner_item);
        adapterTicketPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTicketPriority.setAdapter(adapterTicketPriority);


        Bundle bundle = getArguments();
        if(bundle != null){
            boolean setFields = bundle.getBoolean(Constants.POPULATE_FIELDS_KEY, false);
            if(setFields){

                TicketType.valueOf(ticketsViewModel.getTicketLD().getValue().getType().name());
                binding.editTicketBtn.setVisibility(View.VISIBLE);
                binding.newTicketBtn.setVisibility(View.INVISIBLE);
                binding.spinnerType.setSelection(ticketsViewModel.getTicketLD().getValue().getType().ordinal());
                binding.spinnerPriority.setSelection(ticketsViewModel.getTicketLD().getValue().getPriority().ordinal());
                binding.textView.setText("Edit ticket");
                binding.fragmentNewTicketEstimateTv.setText(String.valueOf(ticketsViewModel.getTicketLD().getValue().getEstimation()));
                binding.fragmentNewTicketTitleTv.setText(ticketsViewModel.getTicketLD().getValue().getTitle());
                binding.fragmentNewTicketDescTv.setText(ticketsViewModel.getTicketLD().getValue().getDescription());
            }
        }
    }

    private void initListeners(){

        binding.newTicketBtn.setOnClickListener(view -> {
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

        binding.editTicketBtn.setOnClickListener(view -> {
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

            int id = ticketsViewModel.getTicketLD().getValue().getId();
            Integer estimate = Integer.valueOf(binding.fragmentNewTicketEstimateTv.getText().toString());
            String title = binding.fragmentNewTicketTitleTv.getText().toString();
            String description = binding.fragmentNewTicketDescTv.getText().toString();

            ticketsViewModel.updateTicket(id, ticketType, ticketPriority, null, estimate, title, description);
            resetFields();
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void resetFields(){
        binding.fragmentNewTicketEstimateTv.setText("");
        binding.fragmentNewTicketTitleTv.setText("");
        binding.fragmentNewTicketDescTv.setText("");
    }
}
