package com.example.qltaichinhcanhan.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qltaichinhcanhan.viewModel.MoneyViewModel
import com.example.qltaichinhcanhan.MoneyTextWatcher
import com.example.qltaichinhcanhan.R
import com.example.qltaichinhcanhan.adapter.AdapterCategory
import com.example.qltaichinhcanhan.databinding.FragmentExpenseBinding
import com.example.qltaichinhcanhan.mode.Category
import com.example.qltaichinhcanhan.mode.Money
import java.util.*

class ExpenseFragment : Fragment() {
    private lateinit var binding: FragmentExpenseBinding
    private lateinit var adapterCategory: AdapterCategory
    private lateinit var moneyViewModel: MoneyViewModel

    var category = ""

    fun newInstance(): ExpenseFragment {
        val args = Bundle()
        val fragment = ExpenseFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentExpenseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moneyViewModel = ViewModelProvider(requireActivity())[MoneyViewModel::class.java]
        init()
    }

    private fun init() {
        initView()
    }

    private fun initView() {
        adapterCategory = AdapterCategory(requireActivity(), listOf())
        binding.rcvCategory.adapter = adapterCategory
        binding.rcvCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        var listCategory = arrayListOf<Category>()
        listCategory.add(Category(1, "Tien nha", true))
        listCategory.add(Category(2, "Tien hoc", false))
        listCategory.add(Category(3, "Tien nha2", false))
        listCategory.add(Category(4, "Tien nha4", false))
        listCategory.add(Category(5, "Tien nha5", false))
        listCategory.add(Category(6, "Tien nha6", false))
        listCategory.add(Category(7, "Tien nha7", false))
        listCategory.add(Category(8, "Tien nha7", false))
        listCategory.add(Category(9, "Tien nha7", false))
        listCategory.add(Category(10, "Tien nha7", false))
        listCategory.add(Category(11, "Tien nha7", false))
        listCategory.add(Category(12, "Tien nha7", false))
        listCategory.add(Category(13, "Tien nha7", false))
        listCategory.add(Category(14, "Tien nha7", false))
        listCategory.add(Category(15, "Tien nha7", false))
        listCategory.add(Category(16, "Tien nha7", false))
        listCategory.add(Category(17, "Tien nha7", false))
        listCategory.add(Category(18, "Tien nha7", false))
        listCategory.add(Category(19, "Tien nha7", false))
        adapterCategory.updateData(listCategory)


        adapterCategory.setClickItemSelect {
            for (i in listCategory) {
                i.select = false
            }
            listCategory[it].select = true
            adapterCategory.updateData(listCategory)
            for (i in listCategory) {
                if(i.select == true){
                    category = i.name.toString()
                }
            }
        }
        binding.edtExpenseAmount.addTextChangedListener(MoneyTextWatcher(binding.edtExpenseAmount))



        binding.btnAdd.setOnClickListener {
            getAllMoney()
        }
    }

    private fun getAllMoney() {

        val d = Calendar.getInstance().time
        val date: Int = d.date
        val month: Int = d.month + 1
        val year: Int = d.year + 1900


        var amount = 0
        val value =
            MoneyTextWatcher.parseCurrencyValue(binding.edtExpenseAmount.getText().toString())
        val temp = value.toString()
        if (temp != "") {
            try {
                amount = temp.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(super@ExpenseFragment.getContext(),
                    "Hãy nhập lại số tiền",
                    Toast.LENGTH_SHORT).show()
            }
        }
        val note = binding.edtNote.text.toString()

        var currency = 1

        when (binding.radioGroup.checkedRadioButtonId) {
            R.id.rd_vnd -> {
                currency = 1
            }
            R.id.rd_usd -> {
                currency = 2
            }
        }

        if (amount > 0) {
            var money = Money(1, 1, date, month, year, currency, amount, note, category)
            Log.e("aaaa", "${money.id} - ${money.type} - ${money.day}/${money.month}/" +
                    "${money.year} -Đơn vi:${money.currency} -Tiền: ${money.amount} - Note: ${money.note} - ${money.category} -")
        }else{
            Toast.makeText(super@ExpenseFragment.getContext(),
                "Thêm không thành công",
                Toast.LENGTH_SHORT).show()
        }

    }

}