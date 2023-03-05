package com.example.view.viewPager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.view.fragments.NewTicketFragment;
import com.example.view.fragments.StatisticsFragment;
import com.example.view.fragments.TicketsListsFragment;
import com.example.view.fragments.UserAccountFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 3;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;
    public static final int FRAGMENT_4 = 3;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FRAGMENT_1: return new StatisticsFragment();
            case FRAGMENT_2: return new NewTicketFragment();
            case FRAGMENT_3: return new TicketsListsFragment();
            default: return new UserAccountFragment();
        }
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }
    
}
