package com.example.raftrading.features.navigation.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.raftrading.features.discover.DiscoverFragment
import com.example.raftrading.features.login.LoginFragment

class PagerAdapterMainFragment(fm: FragmentManager) :  FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val ITEM_COUNT = 3

    override fun getItem(position: Int): Fragment {

        when(position){
            FRAGMENT_1 -> return DiscoverFragment()
            FRAGMENT_2 -> {}
            FRAGMENT_3 -> {}
        }

        return LoginFragment()
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    companion object {
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
        const val FRAGMENT_3 = 2
    }
}