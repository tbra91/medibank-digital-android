package com.medibank.shop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.medibank.shop.data.ArticleEntity
import com.medibank.shop.databinding.ViewArticleItemBinding

class ArticleAdapter : ListAdapter<ArticleEntity, ArticleAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    var onArticleClickListener: ((article: ArticleEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewArticleItemBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ArticleViewHolder(private val binding: ViewArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleEntity) {
            with(binding) {
                titleTextView.text = article.title
                descriptionTextView.text = article.description
                authorTextView.text = article.author

                Glide.with(root).load(article.urlToImage).centerCrop().into(imageView)

                root.setOnClickListener { onArticleClickListener?.invoke(article) }
            }
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleEntity>() {

            override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(
                oldItem: ArticleEntity, newItem: ArticleEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}