package gdsvn.tringuyen.weatherapp.presentation.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import gdsvn.tringuyen.weatherapp.R
import gdsvn.tringuyen.weatherapp.presentation.entity.WeatherPresentation
import kotlinx.android.synthetic.main.weather_card.view.*
import timber.log.Timber


class WeatherListAdapter : RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    var weather = mutableListOf<WeatherPresentation>()

    var listener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        Timber.v("onCreateViewHolder ")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_card, parent, false)
        return WeatherViewHolder(view, listener)
    }

    fun setOnItemClickListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return weather.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weather[position])
        holder.position = position
    }

    fun updateList(itemWeather: WeatherPresentation) {
        weather.clear()
        weather.add(itemWeather)
        notifyDataSetChanged()
    }


    class WeatherViewHolder(
        itemView: View,
        listener: ItemClickListener?
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var view: View = itemView
        private var pos: Int = 0
        var listener: ItemClickListener? = null
        lateinit var dataOfRow: String


        init {
            view.setOnClickListener(this)
            this.listener = listener
        }

        fun setPosition(position: Int){
            this.pos = position
        }

        fun bind(weatherPublisherItem: WeatherPresentation) {
            with(itemView) {
                Timber.v("Data Weather at WeatherActivity =========>  ${Gson().toJson(weatherPublisherItem)}")
                dataOfRow = Gson().toJson(weatherPublisherItem)
                textViewCardCityName.text = weatherPublisherItem.request?.query
                textViewCardCurrentTemp.text = weatherPublisherItem.current.temperature.toString() + "°"
                textViewCardCurrentHum.text = weatherPublisherItem.current.humidity.toString()
                textViewCardWeatherDescription.text = weatherPublisherItem.current.weather_descriptions?.get(0).toString()
                textViewCardWeatherLocalTime.text = weatherPublisherItem.location.localtime
                Picasso.get().load(weatherPublisherItem.current.weather_icons?.get(0)).into(imageViewCardWeatherIcon)

            }
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v , pos,false, dataOfRow)
        }
    }
}