package com.example.qltaichinhcanhan.adapter

import android.annotation.SuppressLint
import android.content.Context
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
            binding.txtCategory.text = item.category.toString()
            binding.txtMoney.text = item.amount.toString() + item.currency
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

    fun updateData(newList: List<Money>) {
        list = newList
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