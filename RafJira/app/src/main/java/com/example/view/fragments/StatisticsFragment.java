package com.example.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.raf_jira.databinding.FragmentLoginBinding;
import com.example.raf_jira.databinding.FragmentMainBinding;
import com.example.raf_jira.databinding.FragmentStatisticsBinding;
import com.example.viewModels.SharedViewModel;

public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding binding;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        initObservers();
    }

    private void initObservers(){
        sharedViewModel.getTotalToDo().observe(getViewLifecycleOwner(), totalToDo -> binding.statisticsViewTodoTotal.setText(String.valueOf(totalToDo)));
        sharedViewModel.getTotalToDoEnhancement().observe(getViewLifecycleOwner(), totalToDoEnh -> binding.statisticsViewTodoEnhTotal.setText(String.valueOf(totalToDoEnh)));
        sharedViewModel.getTotalToDoBugs().observe(getViewLifecycleOwner(), totalToDoBugs -> binding.statisticsViewTodoBugsTotal.setText(String.valueOf(totalToDoBugs)));

        sharedViewModel.getTotalInProgress().observe(getViewLifecycleOwner(), totalInProgress -> binding.statisticsViewInProgressTotal.setText(String.valueOf(totalInProgress)));
        sharedViewModel.getTotalInProgressEnhancement().observe(getViewLifecycleOwner(), totalInProgressEnh -> binding.statisticsViewInProgressEnhTotal.setText(String.valueOf(totalInProgressEnh)));
        sharedViewModel.getTotalInProgressBugs().observe(getViewLifecycleOwner(), totalInProgressBugs -> binding.statisticsViewInProgressBugsTotal.setText(String.valueOf(totalInProgressBugs)));

        sharedViewModel.getTotalDone().observe(getViewLifecycleOwner(), totalDone -> binding.statisticsViewDoneTotal.setText(String.valueOf(totalDone)));
        sharedViewModel.getTotalDoneEnhancement().observe(getViewLifecycleOwner(), totalDoneEnh -> binding.statisticsViewDoneEnhTotal.setText(String.valueOf(totalDoneEnh)));
        sharedViewModel.getTotalDoneBugs().observe(getViewLifecycleOwner(), totalDoneBugs -> binding.statisticsViewDoneBugsTotal.setText(String.valueOf(totalDoneBugs)));
    }
}
