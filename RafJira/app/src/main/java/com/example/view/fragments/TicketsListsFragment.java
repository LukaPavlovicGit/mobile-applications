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
import com.example.viewModels.SharedViewModel;

public class TicketsListsFragment extends Fragment {

    private FragmentTicketsListsBinding binding;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTicketsListsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        init();

    }

    private void init(){
        initTabs();
        initObservers();
    }

    private void initTabs() {
        // da li treba getParentFragmentManager() ili getChildFragmentManager()
        binding.viewPager.setAdapter(new PagerAdapterTicketsListsFragment(getChildFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void initObservers(){

    }
}
