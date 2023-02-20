package com.example.qltaichinhcanhan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.qltaichinhcanhan.adapter.FragmentAdapter
import com.example.qltaichinhcanhan.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterPager: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        setColorStatusbar()
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
                0 -> tab.text = resources.getString(R.string.report)
                1 -> tab.text = resources.getString(R.string.add)
                2 -> tab.text = resources.getString(R.string.summary)
            }
        }.attach()
    }

    fun setColorStatusbar() {
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_color)
        window.navigationBarColor = resources.getColor(R.color.main_color)
    }
}