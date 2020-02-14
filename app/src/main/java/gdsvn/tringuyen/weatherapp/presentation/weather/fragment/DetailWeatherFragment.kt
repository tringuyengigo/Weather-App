package gdsvn.tringuyen.weatherapp.presentation.weather.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import gdsvn.tringuyen.weatherapp.R
import gdsvn.tringuyen.weatherapp.presentation.entity.WeatherPresentation
import gdsvn.tringuyen.weatherapp.presentation.weather.viewmodel.SharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailWeatherFragment : Fragment(), View.OnKeyListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var weatherPublisherItem: WeatherPresentation
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            val jsonString  = bundle.getString("json_item_weather", "")
            if(!jsonString.isNullOrBlank() || !jsonString.isNullOrEmpty()) {
                weatherPublisherItem = Gson().fromJson(jsonString, WeatherPresentation::class.java)
            }
            sharedViewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)

            sharedViewModel.dataStringToShare.observe(this, Observer<String> {
                Timber.e("Data at DetailWeatherFragment ShareViewModel $it")
            }
            )
            Timber.e("Data at DetailWeatherFragment $weatherPublisherItem")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_weather_fragment, container, false)
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {

                arguments = Bundle().apply {

                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                NavHostFragment.findNavController(this).popBackStack(R.id.mainFragment, false)

            }
        }
        return true
    }
}