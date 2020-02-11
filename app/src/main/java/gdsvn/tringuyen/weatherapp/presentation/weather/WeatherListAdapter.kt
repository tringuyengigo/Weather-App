package gdsvn.tringuyen.weatherapp.presentation.weather

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import gdsvn.tringuyen.weatherapp.R
import gdsvn.tringuyen.weatherapp.presentation.entity.WeatherPresentation

import kotlinx.android.synthetic.main.news_item.view.*

class WeatherListAdapter : RecyclerView.Adapter<WeatherListAdapter.NewsViewHolder>() {

    var articles = mutableListOf<WeatherPresentation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(weatherPublisherItem: WeatherPresentation) {
            with(itemView) {
                Log.e("Test", "Data news newsPublisherItem.source?.name ${weatherPublisherItem.current}")
            }
        }
    }

    fun updateList(list: List<WeatherPresentation>) {
        if (list.isNotEmpty()) {
            articles.clear()
            articles.addAll(list)
            notifyDataSetChanged()
        }
    }
}