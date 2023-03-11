package com.example.qltaichinhcanhan.main.ui.accounts

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
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
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.tabs.TabLayout
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

