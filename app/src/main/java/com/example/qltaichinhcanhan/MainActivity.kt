package com.example.qltaichinhcanhan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.qltaichinhcanhan.adapter.FragmentAdapter
import com.example.qltaichinhcanhan.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterPager: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init() {
        initView()
    }

    private fun initView() {
        adapterPager = FragmentAdapter(this)
        binding.viewPagerMain.adapter = adapterPager

        TabLayoutMediator(
            binding.tabLayoutMain,
            binding.viewPagerMain
        ) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.add)
                1 -> tab.text = resources.getString(R.string.summary)
                2 -> tab.text = resources.getString(R.string.report)
            }
        }.attach()
    }
}