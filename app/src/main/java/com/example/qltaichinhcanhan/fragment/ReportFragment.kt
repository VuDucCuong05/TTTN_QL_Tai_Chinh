package com.example.qltaichinhcanhan.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qltaichinhcanhan.MoneyTextWatcher
import com.example.qltaichinhcanhan.R
import com.example.qltaichinhcanhan.adapter.AdapterMoney
import com.example.qltaichinhcanhan.databinding.FragmentReportBinding
import com.example.qltaichinhcanhan.mode.Category
import com.example.qltaichinhcanhan.mode.Money
import com.example.qltaichinhcanhan.viewModel.CategoryViewModel
import com.example.qltaichinhcanhan.viewModel.MoneyViewModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


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

        activity?.let {
            moneyViewModel.readAllData.observe(it) {
                var moneyE = 0
                var moneyI = 0
                arrayMoney = it as ArrayList<Money>
                for (i in it) {
                    if (i.type == 1) {
                        if (i.currency == 1) {
                            moneyE += i.amount!!
                        } else if (i.currency == 2) {
                            moneyE += i.amount!! * 23700
                        }
                    } else if (i.type == 2) {
                        if (i.currency == 1) {
                            moneyI += i.amount!!
                        } else if (i.currency == 2) {
                            moneyI += i.amount!! * 23700
                        }
                    }
                }

                val formatter: NumberFormat = DecimalFormat("#,###")
                binding.txtMoenyIncome.text = formatter.format(moneyI) + " đ"
                moneyI.toString()
                binding.txtMoenyExpense.text = formatter.format(moneyE) + " đ"


                val sharedPreferences: SharedPreferences =
                    requireActivity().getSharedPreferences("money", Context.MODE_PRIVATE)
                val dataMoney = sharedPreferences.getInt("dataMoney", 0)

                binding.currentBalance.text = formatter.format(dataMoney + moneyI - moneyE) + " đ"
            }
        }

        var arrayCategory = arrayListOf<Category>()



        activity?.let {
            categoryViewModel.readAllData.observe(it) {
                arrayCategory = it as ArrayList<Category>
            }
        }

        adapterMoney =
            AdapterMoney(requireContext(), listOf(), listOf(), AdapterMoney.LayoutType.TYPE1)
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
                var list = checkMoney(startDate, endDate, arrayMoney)
                if (list.size == 0) {
                    binding.txtNoMoney.visibility = View.VISIBLE
                    binding.rcvMoney.visibility = View.INVISIBLE
                } else {
                    binding.txtNoMoney.visibility = View.GONE
                    binding.rcvMoney.visibility = View.VISIBLE
                    adapterMoney.updateData(list, arrayCategory)
                }
            } else {
                Toast.makeText(requireContext(),
                    "Ngày bắt đầu phải trước ngày kết thúc",
                    Toast.LENGTH_SHORT).show()
            }
        }

        adapterMoney.setClickItemSelect {

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
            var newCategory = Category(category.id, txtNameCategory, 1, category.select)
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