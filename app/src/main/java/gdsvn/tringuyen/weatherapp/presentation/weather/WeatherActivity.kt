package gdsvn.tringuyen.weatherapp.presentation.weather

import androidx.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gdsvn.tringuyen.weatherapp.R
import gdsvn.tringuyen.weatherapp.presentation.entity.Status
import kotlinx.android.synthetic.main.news_articles.*

import org.koin.android.viewmodel.ext.android.viewModel

class WeatherActivity : AppCompatActivity() {

    private val newsList: WeatherViewModel by viewModel()
    private lateinit var listAdapter: WeatherListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_articles)
//        listAdapter = WeatherListAdapter()
//
//        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        recycler_view.adapter = listAdapter
//
        newsList.fetchWeather()
    }

    override fun onStart() {
        super.onStart()
        newsList.getNewsLiveData().observe(this, Observer {
            when (it?.responseType) {
                Status.ERROR -> {
                    //Error handling
                }
                Status.LOADING -> {
                    //Progress
                }
                Status.SUCCESSFUL -> {
                    // On Successful response
                }
            }
            it?.data?.let {
                Log.e("Test" , "Data Weather at WeatherActivity $it")
            }
        })
    }
}