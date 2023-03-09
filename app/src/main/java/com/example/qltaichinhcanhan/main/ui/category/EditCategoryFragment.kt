package com.example.qltaichinhcanhan.main.ui.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qltaichinhcanhan.R
import com.example.qltaichinhcanhan.adapter.AdapterIConColor
import com.example.qltaichinhcanhan.adapter.AdapterIconCategory
import com.example.qltaichinhcanhan.databinding.FragmentEditCategoryBinding
import com.example.qltaichinhcanhan.main.Category1
import com.example.qltaichinhcanhan.main.CategoryViewModel
import com.example.qltaichinhcanhan.main.IConColor


class EditCategoryFragment : Fragment() {
    lateinit var binding: FragmentEditCategoryBinding
    private lateinit var adapterIconCategory: AdapterIconCategory
    private lateinit var adapterIConColor: AdapterIConColor

    private lateinit var categoryViewModel: CategoryViewModel

    private lateinit var listC: ArrayList<Category1>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel = ViewModelProvider(requireActivity())[CategoryViewModel::class.java]

        val selectedCategory = categoryViewModel.selectedCategory.value

        binding.imgIconCategory.setImageResource(selectedCategory!!.icon!!)
        binding.textNameCategory.setText(selectedCategory.name)
        binding.edtPlannedOutlay.setText(selectedCategory.lave.toString())

        binding.btnNavigation.setOnClickListener {
            findNavController().popBackStack()
        }

        // thêm vào list < hiện thị ngược từ dưới lên
        listC = arrayListOf<Category1>(
            Category1(0, "cc", 1, 0F, R.drawable.ic_gt, 0, false),
            Category1(1, "cc", 1, 0F, R.drawable.ic_ms1, 0, false),
            Category1(2, "cc", 1, 0F, R.drawable.ic_ms2, 0, false),
            Category1(3, "cc", 1, 0F, R.drawable.ic_ms3, 0, false),
            Category1(4, "cc", 1, 0F, R.drawable.ic_ms4, 0, false),
            Category1(5, "cc", 1, 0F, R.drawable.ic_more_horiz, 2, false),
        )

        adapterIconCategory = AdapterIconCategory(requireContext(), listC,
            AdapterIconCategory.LayoutType.TYPE2)

        binding.rcvIconCategory.adapter = adapterIconCategory

        val myLinearLayoutManager1 =
            object : GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        binding.rcvIconCategory.layoutManager = myLinearLayoutManager1


        adapterIconCategory.setClickItemSelect {
            binding.imgIconCategory.setImageResource(it.icon!!)
        }


        val listIConColor = arrayListOf<IConColor>(
            IConColor(0, 1, R.drawable.click_color_1, true),
            IConColor(1, 2, R.drawable.click_color_2, false),
            IConColor(2, 3, R.drawable.click_color_3, false),
            IConColor(3, 4, R.drawable.click_color_4, false),
            IConColor(4, 5, R.drawable.click_color_5, false),
            IConColor(5, 6, R.drawable.click_color_6, false),
            IConColor(6, 7, R.drawable.click_color_7, false)
        )

        adapterIConColor = AdapterIConColor(requireContext(), listIConColor)
        binding.rcvColor.adapter = adapterIConColor
        binding.rcvColor.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        adapterIConColor.setClickItemSelect {
            Log.e("ccccc", "${it.id}")
            when (it.idColor) {
                1 -> {
                    binding.imgIconCategory.setBackgroundResource(R.drawable.color_icon_1)
                }
                2 -> {
                    binding.imgIconCategory.setBackgroundResource(R.drawable.color_icon_2)
                }
                3 -> {
                    binding.imgIconCategory.setBackgroundResource(R.drawable.color_icon_3)
                }
                4 -> {
                    binding.imgIconCategory.setBackgroundResource(R.drawable.color_icon_4)
                }
                5 -> {
                    binding.imgIconCategory.setBackgroundResource(R.drawable.color_icon_5)
                }
                6 -> {
                    binding.imgIconCategory.setBackgroundResource(R.drawable.color_icon_6)
                }
                7 -> {
                    binding.imgIconCategory.setBackgroundResource(R.drawable.color_icon_7)
                }
            }
        }
    }

    fun updateCategory(
        category1: Category1,
        arrayList: ArrayList<Category1>,
    ): ArrayList<Category1> {
        arrayList.forEach {
            it.select = it.id == category1.id
        }
        return arrayList
    }

    fun updateCategory(arrayList: ArrayList<Category1>, iconColor: Int): ArrayList<Category1> {
        arrayList.forEach {
            if (it.select == true) {
                it.color = iconColor
            }
        }
        return arrayList
    }
}