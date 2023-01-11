package com.example.qltaichinhcanhan.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qltaichinhcanhan.R

class SummaryFragment : Fragment() {
    fun newInstance(): SummaryFragment {
        val args = Bundle()
        val fragment = SummaryFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

}