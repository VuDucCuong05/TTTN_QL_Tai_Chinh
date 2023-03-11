package com.example.qltaichinhcanhan.main.ui.home

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qltaichinhcanhan.R
import com.example.qltaichinhcanhan.adapter.AdapterCategory
import com.example.qltaichinhcanhan.databinding.FragmentHomeBinding
import com.example.qltaichinhcanhan.main.DataChart
import com.example.qltaichinhcanhan.main.ItemColor
import com.example.qltaichinhcanhan.main.base.BaseFragment
import com.example.qltaichinhcanhan.mode.Category
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.google.android.material.tabs.TabLayout

class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var adapterCategory: AdapterCategory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgMenu.setOnClickListener {
            myCallback?.onCallback()
        }

        val tabLayout = binding.tabLayout
        val tabChiPhi = tabLayout.newTab().setText("Chi Phí")
        val tabThuNhap = tabLayout.newTab().setText("Thu Nhập")
        tabLayout.addTab(tabChiPhi)
        tabLayout.addTab(tabThuNhap)

        val tabLayoutChart = binding.tabLayoutChart
        val tabNgay = tabLayoutChart.newTab().setText("Ngày")
        val tabThang = tabLayoutChart.newTab().setText("Tháng")
        val tabNam = tabLayoutChart.newTab().setText("Năm")
        val tabTuan = tabLayoutChart.newTab().setText("Tuần")
        tabLayoutChart.addTab(tabNgay)
        tabLayoutChart.addTab(tabThang)
        tabLayoutChart.addTab(tabNam)
        tabLayoutChart.addTab(tabTuan)

        var data = listOf<DataChart>()

        val customHorizontalBar = binding.customHorizontalBar
        val pieChart = binding.barChart

        customHorizontalBar.setData(data)
        pieChart(data, pieChart, "VNĐ")

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Xử lý khi một tab được chọn
                val position = tab?.position
                when (position) {
                    0 -> {
                        data = listOf<DataChart>(
                            DataChart(1000000.0F, 1),
                            DataChart(140000.0F, 2),
                            DataChart(600000.0F, 3),
                        )
                        customHorizontalBar.setData(data)
                        pieChart(data, pieChart, "VNĐ")
                    }
                    1 -> {
                        data = listOf()
                        customHorizontalBar.setData(data)
                        pieChart(data, pieChart, "VNĐ")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Xử lý khi một tab bị bỏ chọn
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Xử lý khi một tab đã được chọn lại
            }
        })

        tabLayoutChart.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Xử lý khi một tab được chọn
                val position = tab?.position
                when (position) {
                    0 -> {
                        customHorizontalBar.visibility = View.VISIBLE
                        binding.imgAdd2.visibility = View.VISIBLE
                        binding.textValueMoney.visibility = View.VISIBLE
                        binding.imgAdd1.visibility = View.GONE
                        pieChart.visibility = View.GONE
                    }
                    1 -> {
                        customHorizontalBar.visibility = View.GONE
                        binding.imgAdd2.visibility = View.GONE
                        binding.textValueMoney.visibility = View.GONE
                        binding.imgAdd1.visibility = View.VISIBLE
                        pieChart.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Xử lý khi một tab bị bỏ chọn
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Xử lý khi một tab đã được chọn lại
            }
        })

        var listCategory = arrayListOf<Category>()
        listCategory.add(Category(0, "Tiền nhà", 1, true))
        listCategory.add(Category(0, "Giao thông", 1, false))
        listCategory.add(Category(0, "Du lịch", 1, false))
        listCategory.add(Category(0, "Du lịch", 1, false))
        listCategory.add(Category(0, "Du lịch", 1, false))
        listCategory.add(Category(0, "Du lịch", 1, false))
        listCategory.add(Category(0, "Du lịch", 1, false))
        listCategory.add(Category(0, "Du lịch", 1, false))
        listCategory.add(Category(0, "Du lịch", 1, false))
        listCategory.add(Category(0, "Du lịch", 1, false))
        listCategory.add(Category(0, "Mua sắm", 1, false))
        listCategory.add(Category(0, "Rượu và đồ uống", 1, false))
        listCategory.add(Category(0, "Học tập", 1, false))

        adapterCategory = AdapterCategory(requireActivity(), listCategory)
        binding.rcvM.adapter = adapterCategory
        binding.rcvM.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rcvM.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var dyTotal = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                dyTotal += dy
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (dyTotal > 0) {
                        Log.d("RecyclerView", "Đang vuốt lên")
                        customHorizontalBar.visibility = View.VISIBLE
                        binding.imgAdd2.visibility = View.VISIBLE
                        binding.textValueMoney.visibility = View.VISIBLE
                        binding.imgAdd1.visibility = View.GONE
                        pieChart.visibility = View.GONE
                    } else if (dyTotal < 0) {
                        Log.d("RecyclerView", "Đang vuốt xuống")
                        customHorizontalBar.visibility = View.GONE
                        binding.imgAdd2.visibility = View.GONE
                        binding.textValueMoney.visibility = View.GONE
                        binding.imgAdd1.visibility = View.VISIBLE
                        pieChart.visibility = View.VISIBLE
                    }
                    dyTotal = 0
                }
            }
        })


    }


    fun pieChart(data: List<DataChart>, pieChart: PieChart, type: String) {
        val entries = ArrayList<PieEntry>()
        val colors = ArrayList<Int>()

        if (data.isNotEmpty()) {
            for (i in data.indices) {
                entries.add(PieEntry(data[i].value))
                colors.add(ItemColor.getColorForId(requireContext(), data[i].color))
            }
        } else {
            entries.add(PieEntry(1f))
            colors.add(Color.GRAY)
        }

        val dataSet = PieDataSet(entries, "Data")
        dataSet.colors = colors
        dataSet.sliceSpace = 1.5f
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 0f
        val pieData = PieData(dataSet)
        pieChart.invalidate()
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setUsePercentValues(false)
        pieChart.setTouchEnabled(false)
        pieChart.holeRadius = 70f
        if (data.isNotEmpty()) {
            val total = data.sumOf { it.value.toDouble() }
            pieChart.centerText = String.format("%.1f", total) + type
            pieChart.animateY(1500, Easing.EaseInOutQuad)
            pieChart.setCenterTextSize(14f)
        } else {
            pieChart.centerText = "Bạn chưa có dữ liệu"
            pieChart.setCenterTextSize(14f)
        }
        pieChart.setCenterTextColor(Color.BLACK)
    }

}