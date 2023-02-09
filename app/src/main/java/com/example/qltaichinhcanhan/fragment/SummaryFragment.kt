package com.example.qltaichinhcanhan.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qltaichinhcanhan.R
import com.example.qltaichinhcanhan.adapter.AdapterCategory
import com.example.qltaichinhcanhan.adapter.AdapterMoney
import com.example.qltaichinhcanhan.databinding.FragmentExpenseBinding
import com.example.qltaichinhcanhan.databinding.FragmentSummaryBinding
import com.example.qltaichinhcanhan.mode.Money
import com.example.qltaichinhcanhan.viewModel.CategoryViewModel
import com.example.qltaichinhcanhan.viewModel.MoneyViewModel

class SummaryFragment : Fragment() {
    private lateinit var binding: FragmentSummaryBinding
    private lateinit var moneyViewModel: MoneyViewModel
    private lateinit var adapterMoneyE: AdapterMoney
    private lateinit var adapterMoneyI: AdapterMoney

    lateinit var arrayMoneyE: ArrayList<Money>
    lateinit var arrayMoneyI: ArrayList<Money>
    fun newInstance(): SummaryFragment {
        val args = Bundle()
        val fragment = SummaryFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moneyViewModel = ViewModelProvider(requireActivity())[MoneyViewModel::class.java]
        initView()
    }

    private fun initView() {
        adapterMoneyE = AdapterMoney(requireContext(), listOf())
        binding.rcvMoneyE.adapter = adapterMoneyE
        binding.rcvMoneyE.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapterMoneyI = AdapterMoney(requireContext(), listOf())

        binding.rcvMoneyI.adapter = adapterMoneyI
        binding.rcvMoneyI.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)



        activity?.let {
            moneyViewModel.readAllData.observe(it) {
                arrayMoneyE = arrayListOf()
                arrayMoneyI = arrayListOf()
                for (i in it) {
                    if (i.type == 1) {
                        arrayMoneyE.add(i)
                    } else if (i.type == 2) {
                        arrayMoneyI.add(i)
                    }
                }
                adapterMoneyE.updateData(arrayMoneyE)
                adapterMoneyI.updateData(arrayMoneyI)
            }
        }
    }

}