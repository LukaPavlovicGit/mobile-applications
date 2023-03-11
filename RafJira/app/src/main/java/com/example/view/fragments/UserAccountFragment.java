package com.example.view.fragments;

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

import com.example.constants.Constants;
import com.example.raf_jira.R;
import com.example.raf_jira.databinding.FragmentUserAccountBinding;

public class UserAccountFragment extends Fragment {

    private FragmentUserAccountBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        initView();
        initListeners();
    }

    private void initView(){
        String username = sharedPreferences.getString(Constants.USERNAME_KEY, "");
        String email = sharedPreferences.getString(Constants.EMAIL_KEY, "");

        binding.usernameHolderTv.setText(username);
        binding.emailHolderTv.setText(email);
    }

    private void initListeners(){
        binding.logOutBtn.setOnClickListener(view -> {
            sharedPreferences
                    .edit()
                    .clear()
                    .apply();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_view_tag, new LoginFragment(), Constants.LOGIN_FRAGMENT_TAG);
            transaction.commit();
        });
    }
}
