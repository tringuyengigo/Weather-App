package gdsvn.tringuyen.weatherapp.presentation.weather.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import gdsvn.tringuyen.weatherapp.R
import gdsvn.tringuyen.weatherapp.presentation.entity.WeatherPresentation
import kotlinx.android.synthetic.main.weather_card.view.*
import timber.log.Timber

class WeatherListAdapter : RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    var weather = mutableListOf<WeatherPresentation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        Timber.v("onCreateViewHolder ");

        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_card, parent, false)
        return WeatherViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return weather.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weather[position])
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(weatherPublisherItem: WeatherPresentation) {
            with(itemView) {
                Timber.v("Data Weather at WeatherActivity =========>  ${Gson().toJson(weatherPublisherItem)}");
                textViewCardCityName.text = weatherPublisherItem.request?.query
                textViewCardCurrentTemp.text = weatherPublisherItem.current.temperature.toString() + "°"
                textViewCardWeatherDescription.text = weatherPublisherItem.current.weather_descriptions?.get(0).toString()
                textViewCardWeatherLocalTime.text = weatherPublisherItem.location.localtime
            }
        }

    }


    fun updateList(itemWeather: WeatherPresentation) {
        weather.clear()
        weather.add(itemWeather)
        notifyDataSetChanged()
    }
}