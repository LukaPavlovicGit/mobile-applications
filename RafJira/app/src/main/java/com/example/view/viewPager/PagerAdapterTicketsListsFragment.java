package com.example.view.viewPager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.view.fragments.DoneTicketsFragment;
import com.example.view.fragments.InProgressTicketsFragment;
import com.example.view.fragments.TodoTicketsFragment;

public class PagerAdapterTicketsListsFragment extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 3;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;

    public PagerAdapterTicketsListsFragment(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FRAGMENT_1: return new TodoTicketsFragment();
            case FRAGMENT_2: return new InProgressTicketsFragment();
            default: return new DoneTicketsFragment();
        }
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case FRAGMENT_1: return "ToDO";
            case FRAGMENT_2: return "in progress";
            default: return "Done";
        }
    }
}
