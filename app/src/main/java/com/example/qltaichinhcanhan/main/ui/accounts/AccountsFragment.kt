package com.example.qltaichinhcanhan.main.ui.accounts

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.qltaichinhcanhan.databinding.FragmentAccountsBinding
import com.example.qltaichinhcanhan.main.ChartMoney
import com.example.qltaichinhcanhan.main.base.BaseFragment
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAAxis
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAColumnrange
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAPlotOptions
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAScrollablePlotArea
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*
import kotlin.math.min

class AccountsFragment : BaseFragment() {

    lateinit var binding: FragmentAccountsBinding
    lateinit var aaChartModel: AAChartModel
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

//        // data theo 12 tháng
        val monthTitles1 = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")

        val data12: Array<Any> =
            arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6)
        val data22: Array<Any> =
            arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5)

        setDataChart(monthTitles1, data12, data22)
        binding.aaChartView.aa_drawChartWithChartModel(aaChartModel)

//        // data theo tuan trong tháng
//        val monthTitles1 = arrayOf("6/2", "13/2", "20/2", "27/2", "6/3")
//        val data11: Array<Any> =
//            arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6, 14.5, 18.2, 21.5, 25.2
//                , 14.5, 18.2, 21.5, 25.2, 14.5, 18.2, 21.5, 25.2, 14.5, 18.2, 21.5, 25.2, 14.5, 18.2, 21.5, 25.2)
//        val data21: Array<Any> =
//            arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6,5.7, 11.3, 17.0, 22.0, 24.8,
//                24.1, 20.1, 14.1, 8.6,5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6,24.8, 24.1, 20.1, 14.1, 8.6, 2.5)
//
//        setDataChart(monthTitles1, data11, data21)
//        binding.aaChartView.aa_drawChartWithChartModel(aaChartModel)


//        val monthTitles = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30")
//        val data1 : Array<Any> = arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6, 8.3, 11.5, 15.7, 20.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5, 2.3, 7.5, 14.5, 18.4, 22.5, 24.7, 24.0)
//        val data2 : Array<Any> = arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5, 2.3, 7.5, 14.5, 18.4, 22.5, 24.7, 24.0, 20.0, 14.0, 8.0, 2.0, 2.0, 7.0, 14.0, 18.0, 22.0, 24.0, 24.0)


        //

//        val monthTitles = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
//        val data1 = arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6)
//        val data2 = arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5)
//
//        val barChart = binding.barChart
//
//// Tạo danh sách các cột dữ liệu cho từng tháng
//        val entries = ArrayList<BarEntry>()
//        for (i in data1.indices) {
//            entries.add(BarEntry(i.toFloat(), floatArrayOf(data1[i].toFloat(), data2[i].toFloat())))
//        }
//
//// Tạo dataset cho biểu đồ
//        val barDataSet1 = BarDataSet(entries, "Data")
//        barDataSet1.colors = listOf(Color.YELLOW, Color.BLUE)
//
//// Tạo danh sách các nhãn trục x
//        val xAxisLabels = ArrayList<String>()
//        for (i in monthTitles.indices) {
//            xAxisLabels.add(monthTitles[i])
//        }
//
//// Cấu hình trục x
//        val xAxis = barChart.xAxis
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.setDrawGridLines(false)
//        xAxis.setCenterAxisLabels(true)
//        xAxis.granularity = 1f
//        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
//
//// Cấu hình trục y
//        val yAxis = barChart.axisLeft
//        yAxis.setDrawGridLines(true)
//
//// Tạo đối tượng dữ liệu cho biểu đồ
//        val data = BarData(barDataSet1)
//        data.setValueFormatter(LargeValueFormatter())
//
//// Cấu hình hiển thị biểu đồ
//        barChart.setFitBars(true)
//        barChart.data = data
//        barChart.description.isEnabled = false
//        barChart.animateY(1000)
//        barChart.legend.isEnabled = true
//        barChart.invalidate()


        val monthTitles = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30")
        val data1 = arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6, 8.3, 11.5, 15.7, 20.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5, 2.3, 7.5, 14.5, 18.4, 22.5, 24.7, 24.0)
        val data2 = arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5, 2.3, 7.5, 14.5, 18.4, 22.5, 24.7, 24.0, 20.0, 14.0, 8.0, 2.0, 2.0, 7.0, 14.0, 18.0, 22.0, 24.0, 24.0)

        val barChart = binding.barChart
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false

        val groupSpace = 0.50f
        val barSpace = 0.03f
        val barWidth = 0.20f

        val data1Entries = ArrayList<BarEntry>()
        for (i in data1.indices) {
            data1Entries.add(BarEntry(i.toFloat(), data1[i].toFloat()))
        }
        val data2Entries = ArrayList<BarEntry>()
        for (i in data2.indices) {
            data2Entries.add(BarEntry(i.toFloat(), data2[i].toFloat()))
        }
        val barDataSet1 = BarDataSet(data1Entries, "Data 1")
        barDataSet1.color = Color.RED
        val barDataSet2 = BarDataSet(data2Entries, "Data 2")
        barDataSet2.color = Color.GRAY


        val barData = BarData(barDataSet1, barDataSet2)
        barData.barWidth = barWidth

        barData.groupBars(0f, groupSpace, barSpace)

        barChart.data = barData

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.labelCount = monthTitles.size
        xAxis.valueFormatter = IndexAxisValueFormatter(monthTitles)
        xAxis.setCenterAxisLabels(true)

        val yAxis = barChart.axisLeft
        yAxis.setDrawAxisLine(false)
        yAxis.setDrawLabels(false)
        val yAxisR = barChart.axisRight
        yAxisR.setDrawAxisLine(false)
        yAxisR.setDrawLabels(false)

        barChart.setVisibleXRangeMaximum(5f) // Cho phép hiển thị tối đa 6 tháng trên một lần hiển thị
        barChart.moveViewToX(6f) // Hiển thị từ tháng thứ 6


        barChart.setScaleEnabled(false)        // Tắt tính năng zoom
        barChart.setFitBars(true)
        barChart.description.isEnabled = false // Vô hiệu hóa mô tả biểu đồ
        barChart.animateY(1500) // Tạo hiệu ứng hoạt hình cho biểu đồ trong 1,5 giây
        barChart.isDragEnabled = true // Cho phép kéo để xem các tháng khác
        barChart.legend.isEnabled
    }

    fun setDataChart(monthTitles: Array<String>, data1: Array<Any>, data2: Array<Any>) {
        aaChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .backgroundColor("#FFFFFF")
            .tooltipEnabled(false)  // hiện thị thống kê
            .categories(monthTitles) // hiện thị tháng
            .yAxisLabelsEnabled(false) // giá trị của trục oy
            .yAxisGridLineWidth(0f)
            .yAxisTitle("")

            .series(arrayOf(
                AASeriesElement()
                    .name("Tokyo")
                    .data(data1),
                AASeriesElement()
                    .name("NewYork")
                    .data(data2),
            ))
    }

}

