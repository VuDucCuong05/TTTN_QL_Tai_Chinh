package com.example.qltaichinhcanhan.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qltaichinhcanhan.R


class InComeFragment : Fragment() {

    fun newInstance(): InComeFragment {
        val args = Bundle()
        val fragment = InComeFragment()
        fragment.arguments = args
        return fragment
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_in_come, container, false)
    }
}