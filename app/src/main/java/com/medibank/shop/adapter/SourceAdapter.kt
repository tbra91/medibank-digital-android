package com.medibank.shop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.checkbox.MaterialCheckBox
import com.medibank.shop.R
import com.medibank.shop.data.SourceEntity

class SourceAdapter : ListAdapter<SourceEntity, SourceAdapter.SourceViewHolder>(DIFF_CALLBACK) {

    var onSourceCheckedChangeListener: ((source: SourceEntity, isChecked: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_source_item, parent, false)
        return SourceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val source = getItem(position)
        holder.bind(source)
    }

    override fun getItemId(position: Int): Long {
        val source = getItem(position)
        return source.id.hashCode().toLong()
    }

    inner class SourceViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bind(source: SourceEntity) {
            (itemView as MaterialCheckBox).apply {
                text = source.name
                isChecked = source.isSelected
                setOnCheckedChangeListener { _, isChecked ->
                    source.isSelected = isChecked
                    onSourceCheckedChangeListener?.invoke(source, isChecked)
                }
            }
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SourceEntity>() {

            override fun areItemsTheSame(oldItem: SourceEntity, newItem: SourceEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SourceEntity, newItem: SourceEntity): Boolean {
                return oldItem == newItem && oldItem.isSelected == newItem.isSelected
            }
        }
    }
}
