package com.example.qltaichinhcanhan.fragment.on_boarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.qltaichinhcanhan.R
import com.example.qltaichinhcanhan.adapter.FragmentAdapter
import com.example.qltaichinhcanhan.adapter.OnBoardingPagerAdapter
import com.example.qltaichinhcanhan.databinding.ActivityOnBoardingScreenBinding
import com.example.qltaichinhcanhan.fragment.login.LoginFragment
import com.example.qltaichinhcanhan.fragment.login.SignUpFragment

class OnBoardingScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingScreenBinding
    private lateinit var onBoardingPagerAdapter: OnBoardingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setColorStatusbar()
        onBoardingPagerAdapter = OnBoardingPagerAdapter(this)
        binding.viewPagerLogin.adapter = onBoardingPagerAdapter
        binding.indicator.setViewPager(binding.viewPagerLogin)


        binding.textLogin.setOnClickListener {
            switchToFragment(LoginFragment())
        }
        binding.textSignUp.setOnClickListener {
            switchToFragment(SignUpFragment())
        }
    }


    fun setColorStatusbar() {
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blu_mani)
        window.navigationBarColor = resources.getColor(R.color.blu_mani)
    }

    private fun switchToFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.ll_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}