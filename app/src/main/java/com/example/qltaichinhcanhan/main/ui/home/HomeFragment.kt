package com.example.qltaichinhcanhan.main.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.qltaichinhcanhan.databinding.FragmentHomeBinding
import com.example.qltaichinhcanhan.inf.CallBackDetail
import com.example.qltaichinhcanhan.inf.FragmentADelegate
import com.example.qltaichinhcanhan.inf.InterDetailToReport
import com.example.qltaichinhcanhan.main.inf.CallBackHome

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var callBackHome: CallBackHome? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgMenu.setOnClickListener {
            callBackHome?.callBackHomeToMainMenu()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CallBackHome) {
            callBackHome = context
        }
    }

//    android:imeOptions="actionSearch"
//    android:inputType="text"
//    android:maxLines="1"
}