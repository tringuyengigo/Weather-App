package gdsvn.tringuyen.weatherapp.presentation.weather.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import gdsvn.tringuyen.weatherapp.R



class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        if (savedInstanceState == null) {
            val host = NavHostFragment.create(R.navigation.nav_graph)
            supportFragmentManager.beginTransaction().replace(R.id.container, host).setPrimaryNavigationFragment(host).commit()
        }
    }

}