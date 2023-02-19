package com.example.qltaichinhcanhan

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.qltaichinhcanhan.databinding.ActivityMain2Binding
import com.example.qltaichinhcanhan.viewModel.CategoryViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    private lateinit var categoryViewModel: CategoryViewModel
    private val calendar: Calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        setContentView(binding.root)

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
                Toast.makeText(this,
                    "Vui lòng nhập đầy đủ ngày bắt đầu và kết thúc",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (isStartDateBeforeEndDate(startDate, endDate)) {
                Log.e("ccccc", "Show list")
            } else {
                Toast.makeText(this, "Ngày bắt đầu phải trước ngày kết thúc", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

    private fun showDatePicker(editText: EditText) {
        val datePickerDialog = DatePickerDialog(
            this,
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