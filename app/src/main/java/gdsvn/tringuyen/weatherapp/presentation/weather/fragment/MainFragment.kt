package gdsvn.tringuyen.weatherapp.presentation.weather.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import gdsvn.tringuyen.weatherapp.R
import gdsvn.tringuyen.weatherapp.presentation.entity.Status
import gdsvn.tringuyen.weatherapp.presentation.weather.adapter.WeatherListAdapter
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
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var cityCurrent = "";

    private val weatherViewModel: WeatherViewModel by viewModel()
    private lateinit var listAdapter: WeatherListAdapter
    private lateinit var recyclerView : RecyclerView
    private var progressDialog: ProgressDialog? = null
    private var mFabPrompt: MaterialTapTargetPrompt? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true);

        listAdapter = WeatherListAdapter()
        createDialog(context)
    }

    private fun addTapTarget() {


        MaterialTapTargetPrompt.Builder(this)
            .setTarget(R.id.txt_hello)
            .setIcon(R.drawable.ic_search_white_24dp)
            .setPrimaryText("Add your first City")
            .setSecondaryText("Tap the search icon and add your favorites cities to get weather updates")
            .setBackButtonDismissEnabled(true)
            .setAnimationInterpolator(FastOutSlowInInterpolator())
            .setPromptStateChangeListener(PromptStateChangeListener { prompt: MaterialTapTargetPrompt?, state: Int ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_DISMISSING) {
                    mFabPrompt = null
                    //Do something such as storing a value so that this prompt is never shown again
                }
            })
            .create()?.show()
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
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTapTarget()
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
                    showErrorDialog(it.error)
                }
                Status.LOADING -> {
                    Timber.v("Loading data........... LOADING");
                    showDialog()
                }
                Status.SUCCESSFUL -> {
                    dismissDialog()
                    Timber.v("Loading data........... SUCCESSFUL");
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

}