package gdsvn.tringuyen.weatherapp.presentation.weather.fragment

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import gdsvn.tringuyen.weatherapp.R
import gdsvn.tringuyen.weatherapp.presentation.weather.adapter.WeatherListAdapter
import gdsvn.tringuyen.weatherapp.presentation.weather.viewmodel.WeatherViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import gdsvn.tringuyen.weatherapp.presentation.entity.Status
import kotlinx.android.synthetic.main.fragment_main.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val weatherViewModel: WeatherViewModel by viewModel()
    private lateinit var listAdapter: WeatherListAdapter
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true);
        listAdapter = WeatherListAdapter()

        weatherViewModel.fetchWeather()

    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        recycler_view.layoutManager = FrameLayout(this, RecyclerView.VERTICAL, false)
//        recycler_view.adapter = listAdapter
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
//    }


//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
//        recyclerView = rootView.findViewById(R.id.list_recycler_view) as RecyclerView
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        recyclerView.adapter = listAdapter
//        return rootView
//    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.v("onViewCreated()");
        recyclerView = view.findViewById(R.id.list_recycler_view) as RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }
    }


    override fun onStart() {
        super.onStart()
        weatherViewModel.getWeatherLiveData().observe(this, Observer {
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
                Timber.v("Data Weather at WeatherActivity ${Gson().toJson(it)}");
                listAdapter.updateList(it)
            }

        })
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_refresh) {
            Timber.d("Click R.id.action_refresh")
            return true
        }
        if (id == R.id.action_search) {
            Timber.d("Click R.id.action_search")
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}