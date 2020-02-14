package gdsvn.tringuyen.weatherapp.presentation.weather.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import gdsvn.tringuyen.weatherapp.R
import gdsvn.tringuyen.weatherapp.presentation.entity.Status
import gdsvn.tringuyen.weatherapp.presentation.entity.WeatherPresentation
import gdsvn.tringuyen.weatherapp.presentation.weather.adapter.ItemClickListener
import gdsvn.tringuyen.weatherapp.presentation.weather.adapter.WeatherListAdapter
import gdsvn.tringuyen.weatherapp.presentation.weather.viewmodel.SharedViewModel
import gdsvn.tringuyen.weatherapp.presentation.weather.viewmodel.WeatherViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.PromptStateChangeListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() , ItemClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var cityCurrent = ""
    private val MENU_SEARCH: Int = 0
    private val MENU_REFRESH: Int = 1

    private val weatherViewModel: WeatherViewModel by viewModel()
    private lateinit var listAdapter: WeatherListAdapter
    private lateinit var recyclerView : RecyclerView
    private var progressDialog: ProgressDialog? = null
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        sharedViewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)

        setHasOptionsMenu(true)
        listAdapter = WeatherListAdapter()
        listAdapter.setOnItemClickListener(this)
        createDialog(context)
    }

    private fun createDialog(context: Context?) {
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage(context!!.getString(R.string.downloading_data))
        progressDialog!!.setCanceledOnTouchOutside(false)
    }

    private fun showDialog() {
        if(!progressDialog?.isShowing!!) {
            progressDialog?.show()
        }
    }

    private fun showErrorDialog(errorMessage: Error?) {
        var cancelDialog : ProgressDialog = ProgressDialog(context)
        cancelDialog.setTitle(context!!.getString(R.string.error_loading))
        cancelDialog.setMessage(errorMessage?.message)
        cancelDialog.setButton(context!!.getString(R.string.retry_loading),
            DialogInterface.OnClickListener { dialog, which ->
                if (cityCurrent.isNotEmpty()) {
                    Timber.d("Search for city: $cityCurrent")
                    weatherViewModel.fetchWeather(cityCurrent)
                }
                return@OnClickListener
            })
        cancelDialog.show()
    }


    private fun dismissDialog() {
        if(progressDialog?.isShowing!!) {
            progressDialog?.dismiss()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.list_recycler_view) as RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }
    }


    override fun onStart() {
        super.onStart()
        weatherViewModel.getWeatherLiveData().observe(this, Observer { data ->
            when (data?.responseType) {
                Status.ERROR -> {
                    showErrorDialog(data.error)
                }
                Status.LOADING -> {
                    Timber.v("Loading data........... LOADING")
                    showDialog()
                }
                Status.SUCCESSFUL -> {
                    dismissDialog()
                    Timber.v("Loading data........... SUCCESSFUL")
                }
            }
            data?.data?.let {
                Timber.v("Data Weather at WeatherActivity ${Gson().toJson(it)}")
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
        Handler().post { addMaterialTapTarget(this.activity, context!!.getString(R.string.add_your_first_city), context!!.getString(R.string.tap_the_search_icon), MENU_SEARCH) }
    }

    private fun addMaterialTapTarget(activity: FragmentActivity?, stringTitle: String, stringNotify: String, intDefine: Int) {
        activity?.let {
            if(intDefine == MENU_SEARCH) {
                MaterialTapTargetPrompt.Builder(it)
                    .setTarget(R.id.action_search)
                    .setIcon(R.drawable.ic_search_white_48dp)
                    .setBackgroundColour(resources.getColor(R.color.colorPrimary))
                    .setPrimaryText(stringTitle)
                    .setSecondaryText(stringNotify)
                    .setCaptureTouchEventOnFocal(true)
                    .setPromptStateChangeListener(PromptStateChangeListener { prompt: MaterialTapTargetPrompt?, state: Int ->
                        if (state == MaterialTapTargetPrompt.STATE_DISMISSING || state == MaterialTapTargetPrompt.STATE_FINISHED) {
                            addMaterialTapTarget(this.activity, context!!.getString(R.string.refresh_weather), context!!.getString(R.string.tap_the_refresh_icon), MENU_REFRESH)
                        }
                    })
                    .create()?.show()
            } else {
                MaterialTapTargetPrompt.Builder(it)
                    .setTarget(R.id.action_refresh)
                    .setIcon(R.drawable.ic_refresh_white_48dp)
                    .setBackgroundColour(resources.getColor(R.color.colorPrimary))
                    .setPrimaryText(stringTitle)
                    .setSecondaryText(stringNotify)
                    .setCaptureTouchEventOnFocal(true)
                    .setPromptStateChangeListener(PromptStateChangeListener { prompt: MaterialTapTargetPrompt?, state: Int ->
                        if (state == MaterialTapTargetPrompt.STATE_DISMISSING || state == MaterialTapTargetPrompt.STATE_FINISHED) { }
                    })
                    .create()?.show()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_refresh) {
            Timber.d("Click R.id.action_refresh")
            if (cityCurrent.isNotEmpty()) {
                Timber.d("Search for city: $cityCurrent")
                weatherViewModel.fetchWeather(cityCurrent)
                showDialog()
            }
            return true
        }
        if (id == R.id.action_search) {
            Timber.d("Click R.id.action_search")
            searchCities()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchCities() {
        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.maxLines = 1
        input.isSingleLine = true
        val inputLayout = TextInputLayout(context)
        inputLayout.setPadding(32, 0, 32, 0)
        inputLayout.addView(input)
        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        alert.setTitle(this.getString(R.string.search_title))
        alert.setView(inputLayout)
        alert.setPositiveButton(R.string.dialog_ok,
            DialogInterface.OnClickListener { dialog, whichButton ->
                val cityCurrent = input.text.toString()
                if (cityCurrent.isNotEmpty()) {
                    Timber.d("Search for city: $cityCurrent")
                    weatherViewModel.fetchWeather(cityCurrent)
                }
            })
        alert.setNegativeButton(R.string.dialog_cancel,
            DialogInterface.OnClickListener { dialog, whichButton ->
                // Cancelled
            })
        alert.show()
    }

    override fun onClick(view: View?, position: Int, isLongClick: Boolean, jsonItemWeather: String) {
        val bundle = Bundle()
        bundle.putString("json_item_weather", jsonItemWeather)
        sharedViewModel.updateData(jsonItemWeather)
        findNavController(this).navigate(R.id.action_blankFragment_to_detailWeatherFragment, bundle)

    }




}