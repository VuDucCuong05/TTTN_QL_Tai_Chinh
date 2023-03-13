package com.example.qltaichinhcanhan.main.m

import android.content.Context
import com.example.qltaichinhcanhan.R


data class Icon(val id: Int, val name: String, val url: Int, val color: Int)

data class IconCategory(val id: Int, val name: String, val icons: List<Icon>)

object IconCategoryData {
    val iconList = listOf(
        Icon(1, "ic_add", R.drawable.ic_add, 1),
        Icon(2, "ic_ms1", R.drawable.ic_ms1, 1),
        Icon(3, "ic_ms2", R.drawable.ic_ms2, 1),
        Icon(4, "ic_ms3", R.drawable.ic_ms3, 1),
        Icon(5, "ic_ms4", R.drawable.ic_ms4, 1),
        Icon(6, "ic_ms5", R.drawable.ic_ms5, 1),
        Icon(7, "ic_sk", R.drawable.ic_sk, 1)
    )

    private val categoryList = listOf(
        IconCategory(1, "Thể thao", iconList),
        IconCategory(2, "Giải trí", iconList)
    )

    fun getIconCategoryList(): List<IconCategory> = categoryList

    fun showICon(context: Context, name: String): Int {
        val resources = context.resources
        return resources.getIdentifier(name, "drawable", context.packageName)
    }
}
