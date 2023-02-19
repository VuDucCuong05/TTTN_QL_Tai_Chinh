package com.example.qltaichinhcanhan.fragment

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker.OnDateChangedListener
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qltaichinhcanhan.adapter.AdapterMoney
import com.example.qltaichinhcanhan.databinding.FragmentReportBinding
import com.example.qltaichinhcanhan.mode.Category
import com.example.qltaichinhcanhan.mode.Money
import com.example.qltaichinhcanhan.viewModel.CategoryViewModel
import com.example.qltaichinhcanhan.viewModel.MoneyViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding
    private lateinit var moneyViewModel: MoneyViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var adapterMoney: AdapterMoney

    private val calendar: Calendar = Calendar.getInstance()


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moneyViewModel = ViewModelProvider(this)[MoneyViewModel::class.java]
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        initView()
    }

    private fun initView() {

        var arrayMoney = arrayListOf<Money>()
        var moneyI = 0
        var moneyE = 0
        activity?.let {
            moneyViewModel.readAllData.observe(it) {
                arrayMoney = it as ArrayList<Money>
                for (i in it) {
                    if (i.type == 1) {
                        moneyE += i.amount!!
                    } else if (i.type == 2) {
                        moneyI += i.amount!!
                    }
                }

                binding.totalIncome.text = "Số tiền thu nhập: " + moneyI
                binding.totalSpent.text = "Số tiền đã tiêu: " + moneyE
                // tiền ban đầu + tiền thu nhập - tiền tiêu
                binding.currentBalance.text = (moneyI - moneyE).toString()
            }
        }

        var arrayCategory = arrayListOf<Category>()



        activity?.let {
            categoryViewModel.readAllData.observe(it) {
                arrayCategory = it as ArrayList<Category>
            }
        }

        adapterMoney = AdapterMoney(requireContext(), listOf(), listOf())
        binding.rcvMoney.adapter = adapterMoney
        binding.rcvMoney.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.startDate.setOnClickListener {
            showDatePicker(binding.startDate)
        }

        binding.endDate.setOnClickListener {
            showDatePicker(binding.endDate)
        }

        binding.searchButton.setOnClickListener {
            val startDate = binding.startDate.text.toString()
            val endDate = binding.endDate.text.toString()
            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(requireActivity(),
                    "Vui lòng nhập đầy đủ ngày bắt đầu và kết thúc",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (isStartDateBeforeEndDate(startDate, endDate)) {
                adapterMoney.updateData(checkMoney(startDate, endDate, arrayMoney), arrayCategory)
            } else {
                Toast.makeText(requireContext(),
                    "Ngày bắt đầu phải trước ngày kết thúc",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkMoney(
        startStr: String, endStr: String,
        arrayMoney: ArrayList<Money>,
    ): ArrayList<Money> {
        var newArrayListMoney = arrayListOf<Money>()
        for (i in arrayMoney) {
            val date = i.day.toString() + "/" + i.month + "/" + i.year
            if (isDateInRange(date, startStr, endStr)) {
                newArrayListMoney.add(i)
            }
        }
        return newArrayListMoney
    }

    fun isDateInRange(dateStr: String, startStr: String, endStr: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.parse(dateStr)
        val start = dateFormat.parse(startStr)
        val end = dateFormat.parse(endStr)

        return date in start..end
    }


    private fun showDatePicker(editText: EditText) {
        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val dateString: String = dateFormat.format(calendar.time)
                editText.setText(dateString)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun isStartDateBeforeEndDate(startDateString: String, endDateString: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        try {
            val startDate = dateFormat.parse(startDateString)
            val endDate = dateFormat.parse(endDateString)

            return startDate.before(endDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            return false
        }
    }


}