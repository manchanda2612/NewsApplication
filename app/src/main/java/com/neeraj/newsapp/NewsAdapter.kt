package com.neeraj.newsapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(val newsItemClickedListener : NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {

    private var mNewsList : ArrayList<NewsArticles>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun getNews(newsList : ArrayList<NewsArticles>) {
        mNewsList = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_news, parent, false)
        return NewsViewHolder(view)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.mTxvTitle.text = mNewsList?.get(position)?.title
        holder.mTxvAuthor.text = mNewsList?.get(position)?.author
        Glide.with(holder.mTxvTitle.context).load(mNewsList?.get(position)?.urlToImage).into(holder.mImgView)

        holder.itemView.setOnClickListener {
            newsItemClickedListener.onItemClicked(mNewsList?.get(position))
        }


    }

    override fun getItemCount(): Int {
        if(null != mNewsList) {
            return mNewsList!!.size
        } else {
            return 0
        }
    }


}


class NewsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val mImgView = itemView.findViewById<ImageView>(R.id.img_adapter_news)
    val mTxvTitle = itemView.findViewById<TextView>(R.id.txv_adapter_news_title)
    val mTxvAuthor = itemView.findViewById<TextView>(R.id.txv_adapter_news_author)

}


interface NewsItemClicked {
    fun onItemClicked(news : NewsArticles?)
}