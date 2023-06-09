package com.example.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.raf_jira.databinding.FragmentTicketsListsBinding;
import com.example.view.viewPager.PagerAdapterTicketsListsFragment;
import com.example.viewModels.TicketsViewModel;

public class TicketsListsFragment extends Fragment {

    private FragmentTicketsListsBinding binding;
    private TicketsViewModel ticketsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTicketsListsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketsViewModel = new ViewModelProvider(requireActivity()).get(TicketsViewModel.class);
        init();
    }

    private void init(){
        initTabs();
    }

    private void initTabs() {
        binding.viewPager.setAdapter(new PagerAdapterTicketsListsFragment(getChildFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }
}
