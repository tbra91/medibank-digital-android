package com.medibank.shop.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.checkbox.MaterialCheckBox
import com.kwabenaberko.newsapilib.models.Source

class SourceAdapter : ListAdapter<Source, SourceAdapter.SourceViewHolder>(DIFF_CALLBACK) {

    class SourceViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(source: Source) {
            (itemView as MaterialCheckBox).text = source.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val checkBox = MaterialCheckBox(parent.context)
        return SourceViewHolder(checkBox)
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val source = getItem(position)
        holder.bind(source)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Source>() {
            override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.name == newItem.name
                        && oldItem.description == newItem.description
                        && oldItem.url == newItem.url
                        && oldItem.category == newItem.category
                        && oldItem.language == newItem.language
                        && oldItem.country == newItem.country
            }
        }
    }
}