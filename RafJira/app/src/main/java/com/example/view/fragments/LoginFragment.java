package com.example.view.fragments;

import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.constants.Constants;
import com.example.raf_jira.R;
import com.example.raf_jira.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        binding.loginButton.setOnClickListener(view -> {
            String username = binding.usernameEt.getText().toString().trim();
            String email = binding.emailEt.getText().toString().trim();
            String password = binding.passwordEt.getText().toString().trim();

            if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(getActivity(), "Invalid inputs", Toast.LENGTH_SHORT).show();
                return;
            }
            if( !username.startsWith("admin") && !password.equals(Constants.USER_PASSWORD) ||
                    username.startsWith("admin") && !password.equals(Constants.ADMIN_PASSWORD) ){
                Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

            sharedPreferences
                    .edit()
                    .putBoolean(Constants.IS_LOGGED_KEY, true)
                    .putString(Constants.USERNAME_KEY, username)
                    .putString(Constants.EMAIL_KEY, email)
                    .putString(Constants.PASSWORD_KEY, password)
                    .apply();

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_container_view_tag, new MainFragment());
            transaction.commit();
        });
    }

}