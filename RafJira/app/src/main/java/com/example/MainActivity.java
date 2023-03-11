package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.constants.Constants;
import com.example.raf_jira.R;
import com.example.raf_jira.databinding.ActivityMainBinding;
import com.example.view.fragments.LoginFragment;
import com.example.view.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        splashScreen.setKeepOnScreenCondition(() -> {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
            boolean isLogged = sharedPreferences.getBoolean(Constants.IS_LOGGED_KEY, false);
            if(isLogged)
                fragmentTransaction.replace(R.id.fragment_container_view_tag, new MainFragment(), Constants.MAIN_FRAGMENT_TAG);
            else
                fragmentTransaction.replace(R.id.fragment_container_view_tag, new LoginFragment(), Constants.LOGIN_FRAGMENT_TAG);

            fragmentTransaction.commit();
            return false;
        });
        getSupportActionBar().hide();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}