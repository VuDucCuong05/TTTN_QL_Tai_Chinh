package com.example.qltaichinhcanhan.main

import java.time.LocalDate
import java.util.*

data class ChartMoney(
    val id: Int,
    val time: LocalDate,
    var a: Int = 0,
    var b: Int = 0
)
