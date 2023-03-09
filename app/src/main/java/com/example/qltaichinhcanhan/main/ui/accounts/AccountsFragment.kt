package com.example.qltaichinhcanhan.main.ui.accounts

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.qltaichinhcanhan.databinding.FragmentAccountsBinding
import com.example.qltaichinhcanhan.main.ChartMoney
import com.example.qltaichinhcanhan.main.base.BaseFragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

class AccountsFragment : BaseFragment() {

    lateinit var binding: FragmentAccountsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNavigation.setOnClickListener {
            myCallback?.onCallback()
        }

        val listData = mutableListOf<ChartMoney>()

        val startDate = LocalDate.of(2023, 10, 10)
        val endDate = LocalDate.of(2023, 12, 10)
        var currentDate = startDate

        while (currentDate <= endDate) {
            listData.add(ChartMoney(
                id = listData.size + 1,
                time = currentDate,
                a = 1000,
                b = 1011
            ))
            currentDate = currentDate.plusDays(1)
        }


        val chart = binding.chart
// Lấy đối tượng Chart từ layout

// Cấu hình cho Chart
        chart.apply {
            // Đặt cấu hình cho Chart
            setDrawBarShadow(false)
            setDrawValueAboveBar(true)
            description.isEnabled = false
            legend.isEnabled = false
            setScaleEnabled(false)
            setPinchZoom(false)
            setDrawGridBackground(false)
            setDrawBorders(false)
            setTouchEnabled(false)

            // Thiết lập giá trị trục X
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawLabels(true)
                granularity = 1f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        // Lấy giá trị thời gian từ listData tại vị trí value
                        val date = Date.from(listData[value.toInt()].time.atStartOfDay(ZoneOffset.UTC).toInstant())
                        // Định dạng lại chuỗi thời gian
                        return SimpleDateFormat("dd/MM/yyyy").format(date)
                    }
                }
            }

            // Thiết lập giá trị trục Y
            axisLeft.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawLabels(true)
                textColor = Color.BLACK
                axisMinimum = 0f
                axisMaximum = getMaxValue(listData) // Hàm getMaxValue() sẽ được giải thích bên dưới
            }

            // Thiết lập giá trị trục Y bên phải
            axisRight.apply {
                isEnabled = false
            }
        }
// Khởi tạo List chứa các cột A, B của ChartMoney
        val aEntries = mutableListOf<BarEntry>()
        val bEntries = mutableListOf<BarEntry>()

// Lặp qua List ChartMoney và thêm các giá trị vào List aEntries, bEntries
        for (i in listData.indices) {
            val chartMoney = listData[i]

            aEntries.add(BarEntry(i.toFloat(), chartMoney.a.toFloat()))
            bEntries.add(BarEntry(i.toFloat(), chartMoney.b.toFloat()))
        }

// Tạo đối tượng DataSet cho cột A
        val aDataSet = BarDataSet(aEntries, "A").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

// Tạo đối tượng DataSet cho cột B
        val bDataSet = BarDataSet(bEntries, "B").apply {
            color = Color.YELLOW
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

// Thêm DataSet vào Chart
        chart.data = BarData(aDataSet, bDataSet)

// Cập nhật lại biểu đồ
        chart.invalidate()

    }


    fun getMaxValue(listData: List<ChartMoney>): Float {
        var max = 0f
        for (chartMoney in listData) {
            if (chartMoney.a > max) {
                max = chartMoney.a.toFloat()
            }
            if (chartMoney.b > max) {
                max = chartMoney.b.toFloat()
            }
        }
        return max
    }

}

