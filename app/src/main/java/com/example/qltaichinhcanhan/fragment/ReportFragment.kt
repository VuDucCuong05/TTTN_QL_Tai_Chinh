package com.example.qltaichinhcanhan.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker.OnDateChangedListener
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qltaichinhcanhan.adapter.AdapterMoney
import com.example.qltaichinhcanhan.databinding.FragmentReportBinding
import com.example.qltaichinhcanhan.mode.Money
import com.example.qltaichinhcanhan.viewModel.MoneyViewModel
import java.util.*
import kotlin.collections.ArrayList


class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding
    private lateinit var moneyViewModel: MoneyViewModel
    private lateinit var adapterMoney: AdapterMoney

    var d = Calendar.getInstance().time
    private var date = d.date
    private var month = d.month + 1
    private var year = d.year + 1900


    fun newInstance(): ReportFragment {
        val args = Bundle()
        val fragment = ReportFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReportBinding.inflate(layoutInflater)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moneyViewModel = ViewModelProvider(this)[MoneyViewModel::class.java]
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {
        adapterMoney = AdapterMoney(requireContext(), listOf())
        binding.rcMoney.adapter = adapterMoney
        binding.rcMoney.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        var arrayMoney = arrayListOf<Money>()

        activity?.let {
            moneyViewModel.readAllData.observe(it) {
                arrayMoney = it as ArrayList<Money>
            }
        }

        binding.datePicker.setOnDateChangedListener(OnDateChangedListener { datePicker, i, i1, i2 ->
            print("date changed by user")
            date = datePicker.dayOfMonth
            month = datePicker.month + 1
            year = datePicker.year
            adapterMoney.updateData(checkMoney(date, month, year, arrayMoney))
        })
    }

    private fun checkMoney(
        day: Int,
        month: Int,
        year: Int,
        arrayMoney: ArrayList<Money>,
    ): ArrayList<Money> {
        var newArrayListMoney = arrayListOf<Money>()
        for (i in arrayMoney) {
            if (month == i.month && year == i.year && day == i.day) {
                newArrayListMoney.add(i)
            }
        }
        return newArrayListMoney
    }
}