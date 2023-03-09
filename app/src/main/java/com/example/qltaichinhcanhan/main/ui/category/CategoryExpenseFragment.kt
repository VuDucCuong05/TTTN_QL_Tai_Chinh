package com.example.qltaichinhcanhan.main.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qltaichinhcanhan.R
import com.example.qltaichinhcanhan.adapter.AdapterIconCategory
import com.example.qltaichinhcanhan.databinding.FragmentCategoryExpenseBinding
import com.example.qltaichinhcanhan.databinding.FragmentCategoryInComeBinding
import com.example.qltaichinhcanhan.fragment.ReportFragment
import com.example.qltaichinhcanhan.main.Category1
import com.example.qltaichinhcanhan.main.CategoryViewModel
import com.example.qltaichinhcanhan.main.base.BaseFragment


class CategoryExpenseFragment : BaseFragment() {

    lateinit var binding: FragmentCategoryExpenseBinding
    private lateinit var adapterIconCategory: AdapterIconCategory
    private lateinit var categoryViewModel: CategoryViewModel

    fun newInstance(): CategoryExpenseFragment {
        val args = Bundle()
        val fragment = CategoryExpenseFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCategoryExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryViewModel = ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java)


        var listC = arrayListOf<Category1>(
            Category1(0, "cc", 1, 0F, R.drawable.ic_ms1, 2, false),
            Category1(0, "cc", 1, 0F, R.drawable.ic_ms1, 4, false),
            Category1(0, "cc", 1, 0F, R.drawable.ic_ms2, 3, false),
            Category1(0, "cc", 1, 0F, R.drawable.ic_ms3, 7, false),
            Category1(0, "cc", 1, 0F, R.drawable.ic_ms4, 5, false),
            Category1(0, "cc", 1, 0F, R.drawable.ic_gt, 3, false),
            Category1(0, "cc", 1, 0F, R.drawable.ic_da, 6, false),
            Category1(0, "cc", 1, 0F, R.drawable.ic_ms1, 5, false),
            Category1(5, "Tạo", 1, 0F, R.drawable.ic_add, 2, false),
        )

        adapterIconCategory = AdapterIconCategory(requireContext(), listC,
            AdapterIconCategory.LayoutType.TYPE1)

        binding.rcvIconCategory.adapter = adapterIconCategory

        binding.rcvIconCategory.layoutManager =
            GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)

        adapterIconCategory.setClickItemSelect {

            findNavController().navigate(R.id.actionExpenseToEditCategoryFragment)
            categoryViewModel.setSelectedCategory(it)


//            val navController = findNavController()
//            // loại fragment khỏi pop back stack
////            navController.popBackStack()
//            navController.navigate(R.id.editCategoryFragment)


        }

    }


}