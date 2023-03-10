package com.example.qltaichinhcanhan.main.ui.slideshow

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.qltaichinhcanhan.databinding.FragmentSlideshowBinding
import com.example.qltaichinhcanhan.main.base.BaseFragment
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.tabs.TabLayout
import java.util.*

class SlideshowFragment : BaseFragment() {

    lateinit var binding: FragmentSlideshowBinding
    lateinit var aaChartModel: AAChartModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNavigation.setOnClickListener {
            myCallback?.onCallback()
        }

        val monthTitles = arrayOf("1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25",
            "26",
            "27",
            "28",
            "29",
            "30")
        val data1 = arrayOf(7.0,
            6.9,
            9.5,
            14.5,
            18.2,
            21.5,
            25.2,
            26.5,
            23.3,
            18.3,
            13.9,
            9.6,
            8.3,
            11.5,
            15.7,
            20.0,
            22.0,
            24.8,
            24.1,
            20.1,
            14.1,
            8.6,
            2.5,
            2.3,
            7.5,
            14.5,
            18.4,
            22.5,
            24.7,
            24.0)
        val data2 = arrayOf(0.2,
            0.8,
            5.7,
            11.3,
            17.0,
            22.0,
            24.8,
            24.1,
            20.1,
            14.1,
            8.6,
            2.5,
            2.3,
            7.5,
            14.5,
            18.4,
            22.5,
            24.7,
            24.0,
            20.0,
            14.0,
            8.0,
            2.0,
            2.0,
            7.0,
            14.0,
            18.0,
            22.0,
            24.0,
            24.0)

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
        barDataSet1.setDrawValues(false)
        val barDataSet2 = BarDataSet(data2Entries, "Data 2")
        barDataSet2.color = Color.GRAY
        barDataSet2.setDrawValues(false)

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
        yAxis.setDrawZeroLine(true)

        // Kh???i t???o danh s??ch ch?? th??ch
        val entries = listOf(
            LegendEntry("Data 1", Legend.LegendForm.CIRCLE, 14f, 8f, null, Color.RED),
            LegendEntry("Data 2", Legend.LegendForm.CIRCLE, 14f, 8f, null, Color.GRAY)
        )

        // Kh???i t???o ?????i t?????ng Legend
        val legend = binding.barChart.legend
        legend.isEnabled = true
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        barChart.setExtraOffsets(0f, 0f, 0f, 15f) // C??ch ch?? th??ch v???i ti??u ????? 10px
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.setCustom(entries)

        //
        barChart.setVisibleXRangeMaximum(5f) // Cho ph??p hi???n th??? t???i ??a 6 th??ng tr??n m???t l???n hi???n th???
        barChart.moveViewToX(6f) // Hi???n th??? t??? th??ng th??? 6


        barChart.setScaleEnabled(false)        // T???t t??nh n??ng zoom
        barChart.setFitBars(true)
        barChart.description.isEnabled = false // V?? hi???u h??a m?? t??? bi???u ?????
        barChart.animateY(1500) // T???o hi???u ???ng ho???t h??nh cho bi???u ????? trong 1,5 gi??y
        barChart.isDragEnabled = true // Cho ph??p k??o ????? xem c??c th??ng kh??c
        barChart.legend.isEnabled


        barChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                // L???y gi?? tr??? d??? li???u v?? ch??? s??? c???t ???????c ch???n
                val value = e?.y ?: return
                val index = e?.x?.toInt() ?: return

                // L???y nh??n c???a lo???i c???t
                val dataSetIndex = h?.dataSetIndex ?: return
                val dataSet = barChart.data.getDataSetByIndex(dataSetIndex) as? BarDataSet ?: return
                val label = dataSet.getLabel()

                // Hi???n th??? th??ng b??o Toast v???i gi?? tr???, ch??? s??? c???t v?? nh??n c???a lo???i c???t ???????c ch???n
                val message = "Selected column $index with value $value for $label"
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected() {
                // Kh??ng c?? gi?? tr??? c???t n??o ???????c ch???n
            }
        })

        val tabLayout = binding.tabLayout
        val tabChung = tabLayout.newTab().setText("Chung")
        val tabChiPhi = tabLayout.newTab().setText("Chi Ph??")
        val tabThuNhap = tabLayout.newTab().setText("Thu Nh???p")
        tabLayout.addTab(tabChung)
        tabLayout.addTab(tabChiPhi)
        tabLayout.addTab(tabThuNhap)

        val tabLayoutChart = binding.tabLayoutChart
        val tabNgay = tabLayoutChart.newTab().setText("Ng??y")
        val tabThang = tabLayoutChart.newTab().setText("Th??ng")
        val tabNam = tabLayoutChart.newTab().setText("N??m")
        val tabTuan = tabLayoutChart.newTab().setText("Tu???n")
        tabLayoutChart.addTab(tabNgay)
        tabLayoutChart.addTab(tabThang)
        tabLayoutChart.addTab(tabNam)
        tabLayoutChart.addTab(tabTuan)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // X??? l?? khi m???t tab ???????c ch???n
                val position = tab?.position
                Toast.makeText(requireContext(), "tablayout: ${position}", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // X??? l?? khi m???t tab b??? b??? ch???n
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // X??? l?? khi m???t tab ???? ???????c ch???n l???i
            }
        })
    }

    fun setDataChart(monthTitles: Array<String>, data1: Array<Any>, data2: Array<Any>) {
        aaChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .backgroundColor("#FFFFFF")
            .tooltipEnabled(false)  // hi???n th??? th???ng k??
            .categories(monthTitles) // hi???n th??? th??ng
            .yAxisLabelsEnabled(false) // gi?? tr??? c???a tr???c oy
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

