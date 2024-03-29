package com.example.raftrading.features.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.raftrading.R
import com.example.raftrading.databinding.FragmentMainBinding
import com.example.raftrading.features.navigation.viewPager.PagerAdapterMainFragment

class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initViewPager()
        initNavigation()
    }

    private fun initViewPager() {
        viewPager = binding.viewPager
        viewPager.adapter = PagerAdapterMainFragment(childFragmentManager)
    }

    private fun initNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_1 -> viewPager.setCurrentItem(PagerAdapterMainFragment.FRAGMENT_1,false)
                R.id.navigation_2 -> viewPager.setCurrentItem(PagerAdapterMainFragment.FRAGMENT_2,false)
                R.id.navigation_3 -> viewPager.setCurrentItem(PagerAdapterMainFragment.FRAGMENT_3,false)
            }
            true
        }
    }

}