package com.example.qltaichinhcanhan.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltaichinhcanhan.databinding.ItemCategoryBinding
import com.example.qltaichinhcanhan.mode.Category

class AdapterCategory(
    var context: Context,
    var list: List<Category>
) : RecyclerView.Adapter<AdapterCategory.ViewHolder>() {

    inner class ViewHolder(binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        internal val binding: ItemCategoryBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder) {
            binding.rdCategory.isChecked = item.select!!
            binding.rdCategory.text = item.name

            binding.rdCategory.setOnClickListener {
                clickItemSelect?.let {
                    it(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(newList: List<Category>) {
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