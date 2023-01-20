package com.neeraj.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.browser.customtabs.CustomTabColorSchemeParams

class MainActivity : AppCompatActivity(), NewsItemClicked {

    lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyler_act_main.layoutManager = layoutManager

        mAdapter = NewsAdapter(this)
        recyler_act_main.adapter = mAdapter

        fetchNews()

    }


    private fun fetchNews() {

        progress_bar_act_main.visibility = View.VISIBLE

        val apiInterface = ApiInterface.create().getNews("in", "9d7d773f162f46dcb1b8ed03a6c464fb")

        apiInterface.enqueue(object : Callback<NewsResponseModel>{

            override fun onResponse(call: Call<NewsResponseModel>, response: Response<NewsResponseModel>) {

                if(response.isSuccessful) {

                    progress_bar_act_main.visibility = View.GONE

                    val articles = response.body()?.articles
                    if(null != articles) {

                        mAdapter.getNews(articles)

                    } else {
                        Toast.makeText(this@MainActivity ,getString(R.string.error_while_fetching_news), Toast.LENGTH_LONG).show()
                    }

                } else {
                    progress_bar_act_main.visibility = View.GONE
                    Toast.makeText(this@MainActivity ,getString(R.string.error_while_fetching_news), Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<NewsResponseModel>, t: Throwable) {
                progress_bar_act_main.visibility = View.GONE
                Toast.makeText(this@MainActivity ,getString(R.string.error_while_fetching_news), Toast.LENGTH_LONG).show()

            }
        })



    }

    override fun onItemClicked(news: NewsArticles?) {

        val builder =  CustomTabsIntent.Builder()

        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(getColor(R.color.purple_700))
            .build()
        builder.setDefaultColorSchemeParams(defaultColors)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(news?.url))

    }

}