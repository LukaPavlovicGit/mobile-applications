package com.example.nutritiontracker.presentation.navigation.viewPager

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.nutritiontracker.presentation.fragments.CategoriesFragment
import com.example.nutritiontracker.presentation.fragments.LoginFragment

class PagerAdapterMainFragment(fm: FragmentManager) :  FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val ITEM_COUNT = 4

    override fun getItem(position: Int): Fragment {
        Log.e("POSITION", position.toString())
        when(position){
            FRAGMENT_1 -> CategoriesFragment()
            FRAGMENT_2 -> {}
            FRAGMENT_3 -> {}
            FRAGMENT_4 -> {}
        }

        return CategoriesFragment()
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    companion object {
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
        const val FRAGMENT_3 = 2
        const val FRAGMENT_4 = 3
    }
}