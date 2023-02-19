package com.example.qltaichinhcanhan.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltaichinhcanhan.databinding.ItemCategoryBinding
import com.example.qltaichinhcanhan.databinding.ItemMoneyExpenseBinding
import com.example.qltaichinhcanhan.mode.Category
import com.example.qltaichinhcanhan.mode.Money

class AdapterMoney(
    var context: Context,
    var list: List<Money>,
    var arrayCategory: List<Category>,
) : RecyclerView.Adapter<AdapterMoney.ViewHolder>() {

    inner class ViewHolder(binding: ItemMoneyExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal val binding: ItemMoneyExpenseBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoneyExpenseBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder) {
            if (item.type == 1) {
                binding.root.isActivated = true
            } else if (item.type == 2) {
                binding.root.isActivated = false
            }

//            binding.txtDate.text =
//                item.day.toString() + "/" + item.month.toString() + "/" + item.year.toString()

            for (i in arrayCategory) {
                if (item.category == i.id) {
                    binding.txtCategory.text = i.name
                }
                Log.e("ccccc","${item.category}: = ${i.id} : ${i.name}")
            }
            var nameCurrency = ""
            if(item.currency == 1){
                nameCurrency = "VND"
            }else if(item.currency == 2){
                nameCurrency = "USD"
            }
            binding.txtMoney.text = item.amount.toString() +" "+ nameCurrency
            binding.txtNote.text = item.note.toString()

            binding.root.setOnClickListener {
                clickItemSelect?.let {
                    it(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(newList: List<Money>,newCategory: List<Category>) {
        list = newList
        arrayCategory = newCategory
        reloadData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData() {
        notifyDataSetChanged()
    }


    private var clickItemSelect: ((Int) -> Unit)? = null
    fun setClickItemSelect(listener: (Int) -> Unit) {
        clickItemSelect = listener
    }
}