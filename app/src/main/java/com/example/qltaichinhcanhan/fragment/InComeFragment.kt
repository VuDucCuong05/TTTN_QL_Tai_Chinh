package com.example.qltaichinhcanhan.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qltaichinhcanhan.MoneyTextWatcher
import com.example.qltaichinhcanhan.R
import com.example.qltaichinhcanhan.adapter.AdapterCategory
import com.example.qltaichinhcanhan.databinding.FragmentInComeBinding
import com.example.qltaichinhcanhan.mode.Category
import com.example.qltaichinhcanhan.mode.Money
import com.example.qltaichinhcanhan.viewModel.CategoryViewModel
import com.example.qltaichinhcanhan.viewModel.MoneyViewModel
import java.util.*
import kotlin.collections.ArrayList


class InComeFragment : Fragment() {
    private lateinit var binding: FragmentInComeBinding
    private lateinit var adapterCategory: AdapterCategory
    private lateinit var moneyViewModel: MoneyViewModel
    private lateinit var categoryViewModel: CategoryViewModel

    var category = 0
    lateinit var listCategory: ArrayList<Category>

    fun newInstance(): InComeFragment {
        val args = Bundle()
        val fragment = InComeFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInComeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moneyViewModel = ViewModelProvider(requireActivity())[MoneyViewModel::class.java]
        categoryViewModel = ViewModelProvider(requireActivity())[CategoryViewModel::class.java]
        init()
    }

    private fun init() {
        initView()
    }

    private fun initView() {
        adapterCategory = AdapterCategory(requireActivity(), arrayListOf())
        binding.rcvCategory.adapter = adapterCategory
        binding.rcvCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        listCategory = arrayListOf()
        listCategory = creakArrayListCategory()
        creakData(listCategory)

        activity?.let {
            categoryViewModel.readAllData.observe(it) {
                listCategory = arrayListOf()
                for(i in it){
                    if(i.type == 2){
                        listCategory.add(i)
                    }
                }
                clearRadioCategory()
            }
        }

        adapterCategory.setClickItemSelect {
            for (i in listCategory) {
                i.select = i.id == it.id
            }
            category = it.id
            adapterCategory.updateData(listCategory)
        }

        adapterCategory.setClickLongItemSelect {
            createDialogUpdateOrDeleteCategory(Gravity.CENTER, it)
        }

        binding.btnAddCategory.setOnClickListener {
            createDialogAddCategory(Gravity.CENTER)
        }

        binding.edtExpenseAmount.addTextChangedListener(MoneyTextWatcher(binding.edtExpenseAmount))

        binding.btnAdd.setOnClickListener {
            val money = getAllMoney()
            if (money.amount!! <= 0) {
                Toast.makeText(requireContext(), "Bạn chưa nhập số tiền!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                moneyViewModel.addMoney(money)
                Toast.makeText(requireContext(),
                    "Thêm thành công số tiền: ${money.amount}",
                    Toast.LENGTH_SHORT).show()
                clearText()
                clearRadioCategory()
            }
        }
    }

    private fun creakArrayListCategory(): ArrayList<Category> {
        var listCategory = arrayListOf<Category>()
        listCategory.add(Category(0, "Tiền lương", 2, true))
        listCategory.add(Category(0, "Cho thuê", 2, false))
        listCategory.add(Category(0, "Cổ tức", 2, false))
        listCategory.add(Category(0, "Tiền thưởng", 2, false))
        listCategory.add(Category(0, "Bán hàng", 2, false))
        listCategory.add(Category(0, "Khác", 2, false))
        return listCategory
    }

    private fun creakData(listCategory: ArrayList<Category>) {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("categoryInCome", Context.MODE_PRIVATE)
        val checkCategory = sharedPreferences.getBoolean("saveCategoryInCome", false)
        if (!checkCategory) {
            for (i in listCategory) {
                categoryViewModel.addCategory(i)
            }
            val editor = sharedPreferences.edit()
            editor.putBoolean("saveCategoryInCome", true)
            editor.commit()
            Log.e("ccccc", "Khoi tao category thanh cong")
        }
    }

    private fun getAllMoney(): Money {
        val d = Calendar.getInstance().time
        val date: Int = d.date
        val month: Int = d.month + 1
        val year: Int = d.year + 1900

        var amount = 0
        val value = MoneyTextWatcher.parseCurrencyValue(binding.edtExpenseAmount.text.toString())
        val temp = value.toString()
        if (temp != "") {
            try {
                amount = temp.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(context,
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
        return Money(0, 2, date, month, year, currency, amount, note, category)
    }

    private fun clearText() {
        binding.edtExpenseAmount.setText("")
        binding.edtExpenseAmount.requestFocus()
        binding.edtNote.setText("")
    }

    private fun clearRadioCategory() {
        for (i in listCategory) {
            i.select = false
        }
        if (listCategory.size > 0) {
            listCategory[0].select = true
        }
        adapterCategory.updateData(listCategory)
    }

    private fun createDialogAddCategory(gravity: Int) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_category)

        val window = dialog.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val wLayoutParams = window.attributes
        wLayoutParams.gravity = gravity
        window.attributes = wLayoutParams

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(false)
        } else {
            dialog.setCancelable(false)
        }
        dialog.show()
        val edtNameCategory = dialog.findViewById<TextView>(R.id.edt_name_category)
        val btnApp = dialog.findViewById<TextView>(R.id.btn_app_category)
        val imgClose = dialog.findViewById<ImageView>(R.id.img_close)

        btnApp.setOnClickListener {
            val txtNameCategory = edtNameCategory.text.trim().toString()
            var ckNameCategory = true
            for (i in listCategory) {
                if (i.name == txtNameCategory) {
                    ckNameCategory = false
                }
            }
            if (ckNameCategory) {
                categoryViewModel.addCategory(Category(0, txtNameCategory, 2, false))
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(),
                    "Đã tồn tại loại tiền, hãy nhập tên khác!",
                    Toast.LENGTH_SHORT).show()
            }
        }

        imgClose.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun createDialogUpdateOrDeleteCategory(gravity: Int, category: Category) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_update_or_delete_category)

        val window = dialog.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val wLayoutParams = window.attributes
        wLayoutParams.gravity = gravity
        window.attributes = wLayoutParams

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(false)
        } else {
            dialog.setCancelable(false)
        }
        dialog.show()

        val edtNameCategory = dialog.findViewById<TextView>(R.id.edt_name_category)

        edtNameCategory.text = category.name

        val btnUpdate = dialog.findViewById<TextView>(R.id.btn_app_category)
        val imgClose = dialog.findViewById<ImageView>(R.id.img_close)
        val imgDelete = dialog.findViewById<ImageView>(R.id.img_delete)

        btnUpdate.setOnClickListener {
            val txtNameCategory = edtNameCategory.text.toString()
            var newCategory = Category(category.id, txtNameCategory, 2, category.select)
            categoryViewModel.updateBook(newCategory)
            dialog.dismiss()
        }

        imgClose.setOnClickListener {
            dialog.dismiss()
        }

        imgDelete.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                categoryViewModel.deleteBook(category)
                dialog.dismiss()
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete ${category.name} ?")
            builder.setMessage("Are you sure to remove ${category.name} ?")
            builder.create().show()
        }
    }
}