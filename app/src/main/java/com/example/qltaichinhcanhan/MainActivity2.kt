package com.example.qltaichinhcanhan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qltaichinhcanhan.adapter.AdapterCategory
import com.example.qltaichinhcanhan.databinding.ActivityMain2Binding
import com.example.qltaichinhcanhan.mode.Category
import com.example.qltaichinhcanhan.viewModel.CategoryViewModel
import com.example.qltaichinhcanhan.viewModel.MoneyViewModel

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    private lateinit var adapterCategory: AdapterCategory
    private lateinit var categoryViewModel: CategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        adapterCategory = AdapterCategory(this, arrayListOf())

        binding.rcvCategory.adapter = adapterCategory
        binding.rcvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        categoryViewModel.readAllData.observe(this) {
            adapterCategory.updateData(it as ArrayList<Category>)
        }

        binding.addCategory.setOnClickListener {
            categoryViewModel.addCategory(Category(0, "Tien nha6 new", 1,false))
        }
        setContentView(binding.root)
    }
}